<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="transaccion.TSite"%>
<%@page import="bd.Site"%>
<%@page import="transaccion.TContrato"%>
<%@page import="bd.Contrato"%>
<%@page import="transaccion.TRemito"%>
<%@page import="bd.Remito"%>
<%@page import="bd.Preticket"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="utils.TFecha"%>
<%@page import="transaccion.TRemito_contrato"%>
<%@page import="bd.Remito_contrato"%>
<%@page import="transaccion.TCliente"%>

<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<% 
    Preticket preticket = (Preticket) request.getAttribute("preticket");
    boolean nuevo = false;
    if (preticket==null) {
        preticket = new Preticket();
        preticket.setFecha(TFecha.ahora(TFecha.formatoBD));
        nuevo = true;
    }
    TRemito tr = new TRemito();
    Remito remito_cierre  = (Remito) request.getAttribute("remito_cierre");
    Remito remito_inicio  = (Remito)  request.getAttribute("remito_inicio"); //tr.getById(remito_cierre.getId_referencia());
    Contrato contrato     = new TContrato().getById(remito_cierre.getId_contrato());
    Cliente cliente       = new TCliente().getById(remito_cierre.getId_cliente());
    Site site             = new TSite().getById(remito_cierre.getId_site());
    boolean transitorio = new TRemito().esTransitorio(remito_cierre);
    
    if(contrato ==null) contrato = new Contrato();
    if(cliente==null) cliente = new Cliente();
    if(site == null) site = new Site();
    List<Remito_contrato> lstDetalle = (List<Remito_contrato>) request.getAttribute("detalle");
    
    if (lstDetalle == null) lstDetalle = new ArrayList<Remito_contrato>();    
    HashMap<Integer,OptionsCfg.Option> mapUnidades = OptionsCfg.getMap(OptionsCfg.getUnidades());
%>
<!DOCTYPE html>
<html lang="en">

<head>
     <%@include  file="tpl_head.jsp" %>
</head>

<body>

    <div id="wrapper">

      <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%} else {%>Editar<%}%> preticket <% if (transitorio) {%> transitorio <%} %></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <form action="<%= PathCfg.PRETICKET_EDIT%>?id_remito=<%= remito_cierre.getId()%>" method="POST"  role="form">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del preticket </div>            
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-6 " >
                            <h3 class="">Preticket</h3>
                            <fieldset>
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= preticket.getId()%>">                                    
                                <% }%>           
                                <input type="hidden" name="id_remito" id="id_remito" value="<%= remito_cierre.getId()%>">
                                <input type="hidden" name="id_contrato" id="id_contrato" value="<%= contrato.getId()%>">                                
                                <input type="hidden" name="id_site" id="id_site" value="<%= site.getId()%>">
                                <!--<div class="col-lg-12 ">-->
                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="fecha">Fecha</label>
                                            <input class="form-control date-picker" name="fecha" id="fecha" size="0"  value="<%=TFecha.formatearFechaBdVista(preticket.getFecha()) %>">
                                        </div>
                                    </div>
                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="numero">Punto de venta</label>
                                            <input class="form-control" name="punto_venta" id="punto_venta" value="<%=preticket.getPunto_venta() %>">
                                        </div>
                                    </div>
                                     <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="numero">N&uacute;mero</label>
                                            <input class="form-control" name="numero" id="numero" value="<%=preticket.getNumero() %>">
                                        </div>
                                    </div>
                                     
<!--                                     <div class="col-lg-3 " >
                                         
                                    </div>-->
                                <!--</div>-->
                               <!--<div class="form-group col-lg-12 " >-->
<!--                                   <div class="col-lg-4 " >
                                        <div class="form-group " >
                                             <label for="">Divisa</label>
                                             <select class="form-control" name="id_divisa" id="id_divisa" disabled>
                                                  <%
                                                    for(int i=0;i<2;i++){
                                                            String divisa = i==0?"Dolares":"Pesos";
                                                            String selected = i== preticket.getId_divisa()?"selected":"";
                                                            
                                                    %>
                                                    <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div>
                                   </div>
                                    <div class="col-lg-4 " >   
                                        <div class="form-group " >
                                             <label for="">Monto</label>
                                             <input class="form-control" name="total" id="total"  value="<%= preticket.getTotal() %>" disabled >
                                         </div>                                        
                                    </div>                                    
                               </div>-->
                            </fieldset>
                        </div>
                        <div class="col-lg-6 " >
                             <h3 class="">Cliente</h3>
                             <input type="hidden" name="id_cliente" id="id_cliente" value="<%= cliente.getId() %>" >
                             <fieldset disabled>                                 
                                 <div class="form-group">
                                    <label for="cliente_nombre">Nombre</label>
                                    <input class="form-control" name="cliente_nombre" id="cliente_nombre" value="<%= cliente.getNombre() %>">
                                </div>
                                 <div class="form-group">
                                    <label for="cliente_direccion">Direcci&oacute;n</label>
                                    <input class="form-control" name="cliente_direccion" id="cliente_direccion" value="<%= cliente.getDireccion_fisica() %>">
                                </div>
                                     
                                 <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_iva">IVA</label>
                                       <select class="form-control" name="cliente_iva" id="cliente_iva" >
                                           <option value="0"></option>
                                           <% for(Option o:OptionsCfg.getClasesIva()){ 
                                           String selected = o.getId()==cliente.getId_iva()?"selected":""; 
                                           %>
                                           <option value="<%=o.getId()%>" <%= selected%>>
                                               <%= o.getDescripcion()%>
                                           </option>
                                           <%  } %>
                                           
                                       </select>
                                   </div>
                                 </div>
                                  <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_cuit">CUIT</label>
                                       <input class="form-control" name="cliente_cuit" id="cliente_cuit" value="<%= cliente.getCuit() %>">                                        
                                   </div>
                                 </div>  
                            </fieldset>
                                        
                        </div>
                          <!-- ./Cliente -->
                        
                        <!-- ./Locacion -->
                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        </div>  
                          <!-- ./row -->
                        
                        
                        <hr>
                        <span class="btn btn-primary" data-toggle="modal" data-target="#mdlRemito">Agregar Remito</span>
                        <div class="row">
                            <div class="col-lg-12">
                                <!--<h3>Items <span class="btn btn-default" id="btnAgregar"> Agregar</span></h3>-->
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblPreticket">
                                    <colgroup>
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 7%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 7%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 8%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 8%;">
                                        <col span="1" style="width: 10%;text-align: right">
                                     </colgroup>
                                    <thead>
                                        <tr>
                                            <th style="width:50px">Remito inicio</th>
                                            <th style="width:40px">Fecha inicio</th>
                                            <th style="width:50px;">Remito cierre</th>
                                            <th style="width:40px">Fecha cierre</th>
                                            <th style="width:50px">Q Dias</th>
                                            <th style="width:50px">Posici&oacute;n</th>
                                            <th>Descripci&oacute;n</th>
                                            <th style="width:50px">Cantidad</th>
                                            <th style="width:50px">Dias Herramientas</th>
                                            <th style="width:50px">Unidad</th>
                                            <th style="width:50px">Precio unitario</th>
                                            <th style="width:50px;text-align: right" >Total</th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <%  Float total = 0f;
                                            Float subtotal = 0f;
                                            String divisa = "";
                                            Integer dias;
                                            Float dias_herramienta ;
                                            for( Remito_contrato detalle: lstDetalle) {
                                                if(detalle.getActivo_id_rubro()!=14)
                                                    dias = TFecha.diferenciasDeFechas( remito_inicio.getFecha(),remito_cierre.getFecha()) + 1;
                                                else dias = !transitorio?1:2;
                                                divisa = detalle.getContrato_detalle_id_divisa()==0?"U$S":"$";
                                                
                                                dias_herramienta = dias * detalle.getRemito_detalle_cantidad();
                                                subtotal = dias_herramienta * detalle.getContrato_detalle_precio() ;
//                                                subtotal =
//                                                subtot Float.parseFloat(String.format("%,2f",subtotal));
                                                
                                                
                                                total +=subtotal;
                                        %>
                                            <%--<%@include file="preticket_detalle.jsp" %>--%>                                            
                                            <tr>
                                                <td >
                                                    <%= remito_inicio.getNumero()%>
                                                    <input type="hidden" name ="remito_inicio" value="<%= remito_inicio.getNumero()%>">
                                                </td>
                                                
                                                <td style="width:20px;"> 
                                                    <%= TFecha.formatearFechaBdVista(remito_inicio.getFecha()) %>
                                                    <input type="hidden" name ="fecha_inicio" value="<%= remito_inicio.getFecha() %>">                                                </td>
                                                
                                                <td>
                                                    <%= remito_cierre.getNumero() %>
                                                    <input type="hidden" name ="remito_cierre" value="<%= remito_cierre.getNumero() %>">
                                                </td>
                                                <td style="width:20px;"> 
                                                    <%= TFecha.formatearFechaBdVista(remito_cierre.getFecha()) %>
                                                    <input type="hidden" name ="fecha_cierre" value="<%= remito_cierre.getFecha() %>">
                                                </td>
                                                <td>
                                                    <%=dias %>
                                                    <input type="hidden" name ="dias" value="<%=dias %>">
                                                </td>
                                                <td >
                                                    <%= detalle.getPosicion() %>
                                                    <input type="hidden" name ="posicion" value="<%= detalle.getPosicion() %>">
                                                </td>
                                                <td style="width:290px;">
                                                    <%= detalle.getActivo_desc_larga() %>
                                                    <input type="hidden" name ="descripcion" value="<%= detalle.getActivo_desc_larga() %>">
                                                </td>
                                                <td  >
                                                    <%= detalle.getRemito_detalle_cantidad()%>
                                                    <input type="hidden" name ="cantidad" value="<%= detalle.getRemito_detalle_cantidad()%>">
                                                </td>
                                                <td  >
                                                    <%= dias_herramienta %>
                                                    <input type="hidden" name ="dias_herramienta " value="<%= dias_herramienta %>">
                                                </td>
                                                <td  >
                                                    <% 
                                                        Option o = mapUnidades.get(detalle.getContrato_detalle_id_unidad());
                                                        if (o!=null) { %>
                                                                <%= o.getDescripcion() %> 
                                                    <% } %>
                                                    <input type="hidden" name ="id_unidad" value="<%= detalle.getContrato_detalle_id_unidad()%>">
                                                </td>
                                                    <td style="width:75px">
                                                       <%= divisa %>
                                                       <%= detalle.getContrato_detalle_precio()%>
                                                       
                                                       <input type="hidden" name ="precio" value="<%= detalle.getContrato_detalle_precio()%>">
                                                       <input type="hidden" name ="id_divisa" value="<%=  detalle.getContrato_detalle_id_divisa() %>">
                                                    </td>                                                    
                                                    <td syle="text-align:right" >
                                                            <%= divisa %> <span syle="width:100%;text-align:right"><%= subtotal %> </span>
                                                        <input type="hidden" name ="subtotal" value="<%= subtotal %>">
                                                                                                              
                                                    </td>
                                                </tr>
                                         <% } %>
                                         
                                         <%--<%@include file="preticket_test.jsp" %>--%>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="11"></td>
                                            <td>
                                                <span id="spn_divisa"><%=divisa%></span> <span id="spn_total"><%= total %></span>
                                                <input type="hidden" name ="total" id="total" value="<%= total %>">
                                            </td>
<!--                                            <td id="total"> 
                                                <span class="input-group">
                                                    <span class="input-group-addon"  data-toggle="modal" data-target="#mdlSubrubro"><span class="fa fa-money fa-fw"></span></span>
                                                    <input type="text" class="form-control" data-index="1" placeholder="Total" size="20" disabled value="">
                                                </span></td>-->
                                        </tr>
                                    </tfoot>

                                </table>
                                
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.PRETICKET%>">Cancelar</a>
                            </div>
                        </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
                </form>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>    
    
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>    
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>        
    <!-- Custom Theme JavaScript -->
    
    <!-- DataTables JavaScript -->
    <script src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <script src="bower_components/datatables-plugins/sorting/date-uk.js"></script>
    
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
    
    <script>
        var $pos_item;
        var $tr;
        var arr_unidad = [];
        $(document).ready(function(){                   
            
//            $('.date-picker').mask('99/99/9999');
//            $('.date-picker').datepicker({
//                language: 'es'
//            }); 
            $('#btnSubmit').click(submitForm);
        });
        
        
       function validar(){
        var $punto_venta = $('#punto_venta');
        var $numero  = $('#numero');
        var $fecha = $('#fecha');
        
        if(!validarCampo($fecha,"Debe ingresar la fecha",validarCampoFecha));   
        if(!validarCampo($punto_venta,"Debe ingresar el punto de venta",validarNoCero)) return false;
        if(!validarCampo($numero,"Debe ingresar el n&uacute;mero de preticket",validarNoCero)) return false;
           
        return true;
       }
      
//      function validarCampo (campo,mensajeError,check){     
//          console.log(check);
//          if(check ===undefined) { check = false;}          
//          if(campo ===undefined || campo.val()=== "" || check(campo)){
//            bootbox.alert(mensajeError);
//            campo.parent().addClass("has-error");
//            return false;
//        } else campo.parent().removeClass("has-error");
//        return true;
//      }
//      function validarCampoFecha(campo,mensajeError){
//           if(campo===undefined || campo.val()==="" || !validarFecha(campo.val())){            
//            bootbox.alert(mensajeError);
//            campo.parent().addClass("has-error");
//            return false;
//        } else campo.parent().removeClass("has-error");
//        return true;
//      }
//       
    $(document).ready(function(){
       $('#mdlRemito').on('show.bs.modal',function(){
           var $id_remito = parseInt($('#id_remito').val());           
           var data = {id_tipo:2, facturado:0,id_estado :2,exclude: $id_remito};
           loadDataRemito(data);
       })
    });
    function selActivos(){           
            var $arr = $('input.chkSelActivo:checked');
            for(var i = 0;i<$arr.length;i++){
                var $id = $($arr[i]).data('index');    
                console.log($id);
                loadDataRemitoContrato({id_remito:$id});
                $('#mdlRemito').modal('hide');
            }            
    }
    function loadDataRemitoContrato(data){
        var $tabla = $('#tblPreticket');
        $.ajax({
               url: '<%= PathCfg.REMITO_CONTRATO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
//                    var cant_cols = $tabla.find('thead th').length;
//                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(result) {
                   $('#selTodos').prop('checked',false);
                   if(result.Result === "OK") {
                       $tabla.find('tbody').append(createTableRemitoContrato(result.Records));    
                       actualizarTotal();
                   }
               }
        });
    }
    function createTableRemitoContrato(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
         
            var d = data[i];
            var dias_herramienta = d.dias * d.remito_detalle_cantidad;
            var subtotal = dias_herramienta * d.contrato_detalle_precio;
            html +="<tr class=''>";            
//            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.remito_inicio.numero,'');            
            html += wrapTag('td',convertirFecha(d.remito_inicio.fecha),'');
            html += wrapTag('td',d.remito_cierre.numero,'');            
            html += wrapTag('td',convertirFecha(d.remito_cierre.fecha),'');
            html += wrapTag('td',d.dias,'');
            
            html += wrapTag('td',d.posicion,'');
            html += wrapTag('td',d.activo_desc_larga,'');
            html += wrapTag('td',d.remito_detalle_cantidad,'');
            
            html += wrapTag('td',dias_herramienta,'');            
            html += wrapTag('td',d.unidad,'');             
            html += wrapTag('td',d.divisa + " " + d.contrato_detalle_precio,''); 
            html += wrapTag('td',d.divisa + " " + subtotal,''); 
//             var htmlSelect = "<input type='checkbox' class='chkSelActivo' data-index='" + d.id + "' data-pos='" + d.posicion + "' data-index='"+ d.id +"' data-codigo='"+d.codigo+"' data-descripcion='" + d.desc_larga + "' data-cant='"+d.cantidad+"'" ;
//            html +=wrapTag('td',htmlSelect ,'');
            html += '<input type="hidden" name ="remito_inicio"     value="' + d.remito_inicio.numero + '">';
            html += '<input type="hidden" name ="fecha_inicio"      value="' + d.remito_inicio.fecha.split(" ")[0] + '">';
            html += '<input type="hidden" name ="remito_cierre"     value="' + d.remito_cierre.numero + '">';
            html += '<input type="hidden" name ="fecha_cierre"      value="' + d.remito_cierre.fecha.split(" ")[0] + '">';
            html += '<input type="hidden" name ="dias"              value="' + d.dias + '">';
            html += '<input type="hidden" name ="posicion"          value="' + d.posicion + '">';
            html += '<input type="hidden" name ="descripcion"       value="' + d.activo_desc_larga + '">';
            html += '<input type="hidden" name ="cantidad"          value="' + d.remito_detalle_cantidad + '">';
            html += '<input type="hidden" name ="dias_herramienta " value="' + dias_herramienta  + '">';
            html += '<input type="hidden" name ="id_unidad"         value="' + d.contrato_detalle_id_unidad + '">';
            html += '<input type="hidden" name ="precio"            value="' + d.contrato_detalle_precio + '">';
            html += '<input type="hidden" name ="id_divisa"         value="' + d.contrato_detalle_id_divisa + '">';
            html += '<input type="hidden" name ="subtotal"          value="' + subtotal + '">';

           html +="</tr>";
       }
       return html;
    }
    function actualizarTotal(){
        var total = 0;
        var arrST = $('input[name="subtotal"]');
        for(var i=0;i<arrST.length;i++) {
            var $st = $(arrST[i]).val();
            total += parseFloat($st);
            
        }
        
        $('#spn_total').text(total);
        $('#total').val(total);
    }
    </script>
    <!-- Modal -->

    <%--<%@include file="cliente_mdl.jsp" %>--%>
    <%--<%@include file="site_mdl.jsp" %>--%>
    <%--<%@include file="rubro_mdl.jsp" %>--%>
    <%@include file="remito_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>
    
</body>

</html>
