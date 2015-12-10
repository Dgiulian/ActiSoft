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
    Remito remito_cierre  = (Remito) request.getAttribute("remito");
    Remito remito_inicio  = tr.getById(remito_cierre.getId_referencia());
    Contrato contrato     = new TContrato().getById(remito_cierre.getId_contrato());
    Cliente cliente       = new TCliente().getById(remito_cierre.getId_cliente());
    Site site             = new TSite().getById(remito_cierre.getId_site());
    boolean transitorio = new TRemito().esTransitorio(remito_cierre);
    
    if(contrato ==null) contrato = new Contrato();
    if(cliente==null) cliente = new Cliente();
    if(site == null) site = new Site();
    List<Remito_contrato> lstDetalle = (List<Remito_contrato>) request.getAttribute("detalle");
    
    if (lstDetalle == null) lstDetalle = new ArrayList<Remito_contrato>();     
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%= PathCfg.PAGE_TITLE %></title>
     <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <!-- Bootstrap Core CSS -->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="bower_components/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

   
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
                                <input type="hidden" name="id_remito" value="<%= remito_cierre.getId()%>">
                                <input type="hidden" name="id_contrato" value="<%= contrato.getId()%>">                                
                                <input type="hidden" name="id_site" value="<%= site.getId()%>">
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
                        <div class="row">
                            <div class="col-lg-12">
                                <!--<h3>Items <span class="btn btn-default" id="btnAgregar"> Agregar</span></h3>-->
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblContrato">
                                    <colgroup>
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 7%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 7%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1" style="width: 5%;">
                                        <col span="1">
                                        <col span="1" style="width: 8%;">
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
                                            <th style="width:50px">Precio unitario</th>
                                            <th style="width:50px;" >Total</th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <%  Float total = 0f;
                                            Float subtotal = 0f;
                                            String divisa = "";
                                            Integer dias;
                                            for( Remito_contrato detalle: lstDetalle) {
                                                if(detalle.getActivo_id_rubro()!=14)
                                                    dias = TFecha.diferenciasDeFechas( remito_inicio.getFecha(),remito_cierre.getFecha()) + 1;
                                                else dias = !transitorio?1:2;
                                                divisa = detalle.getContrato_detalle_id_divisa()==0?"U$S":"$";
                                                subtotal = dias * detalle.getRemito_detalle_cantidad() * detalle.getContrato_detalle_precio() ;                                                
                                                
//                                                subtotal = Float.parseFloat(String.format("%,2f",subtotal));
                                                
                                                
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
                                                    <input type="hidden" name ="fecha_inicio" value="<%= TFecha.formatearFechaBdVista(remito_inicio.getFecha()) %>">
                                                </td>
                                                
                                                <td>
                                                    <%= remito_cierre.getNumero() %>
                                                    <input type="hidden" name ="remito_cierre" value="<%= remito_cierre.getNumero() %>">
                                                </td>
                                                <td style="width:20px;"> 
                                                    <%= TFecha.formatearFechaBdVista(remito_cierre.getFecha()) %>
                                                    <input type="hidden" name ="fecha_cierre" value="<%= TFecha.formatearFechaBdVista(remito_cierre.getFecha()) %>">
                                                </td>
                                                <th>
                                                    <%=dias %>
                                                    <input type="hidden" name ="dias" value="<%=dias %>">
                                                </th>
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
                                                    <td style="width:75px">
                                                       
                                                       <%= detalle.getContrato_detalle_precio()%>
                                                       <%=  divisa %>
                                                       <input type="hidden" name ="precio" value="<%= detalle.getContrato_detalle_precio()%>">
                                                       <input type="hidden" name ="id_divisa" value="<%=  detalle.getContrato_detalle_id_divisa() %>">
                                                    </td>                                                    
                                                    <td>
                                                        <%= subtotal %> 
                                                        <input type="hidden" name ="subtotal" value="<%= subtotal %>">
                                                        <%= divisa %>                                                        
                                                    </td>
                                                </tr>
                                         <% } %>
                                         
                                         <%--<%@include file="preticket_test.jsp" %>--%>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="9"></td>
                                            <td>
                                                <%= total %> <%=divisa%>
                                                <input type="hidden" name ="total" value="<%= total %>">
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
        
    </script>
    <!-- Modal -->

    <%@include file="cliente_mdl.jsp" %>
    <%@include file="site_mdl.jsp" %>
    <%@include file="rubro_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
