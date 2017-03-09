<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="transaccion.TPreticket_detalle"%>
<%@page import="bd.Preticket_detalle"%>
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
    }

    Contrato contrato = new TContrato().getById(preticket.getId_contrato());
    Cliente cliente   = new TCliente().getById(preticket.getId_cliente());
    Site site         = new TSite().getById(preticket.getId_site());
    
    if(contrato==null) contrato = new Contrato();
    if(cliente==null)  cliente = new Cliente();
    if(site == null)   site = new Site();
    
    List<Preticket_detalle> lstDetalle = new TPreticket_detalle().getByPreticketId(preticket.getId());    
    if (lstDetalle == null) lstDetalle = new ArrayList<Preticket_detalle>();
    
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
                    <h1 class="page-header">Vista r&aacute;pida de preticket</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del preticket </div>            
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-6 " >
                            <h3 class="">Preticket</h3>
                            <form action="<%=PathCfg.PRETICKET_EDIT%>" method="POST">
                                <input type="hidden" id="id" name="id" value="<%=preticket.getId()%>">
                            <fieldset>                                
                                
                                <!--<div class="col-lg-12 ">-->
                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="fecha">Fecha</label>
                                            <input class="form-control date-picker" name="fecha" id="fecha" size="0"  disabled value="<%=TFecha.formatearFechaBdVista(preticket.getFecha()) %>">
                                        </div>
                                    </div>
                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="numero">Punto de venta</label>
                                            <input class="form-control" name="punto_venta" id="punto_venta" disabled  value="<%=preticket.getPunto_venta() %>">
                                        </div>
                                    </div>
                                     <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="numero">N&uacute;mero</label>
                                            <input class="form-control" name="numero" id="numero" disabled value="<%=preticket.getNumero() %>">
                                        </div>
                                    </div>
                                     
<!--                                     <div class="col-lg-3 " >
                                         
                                    </div>-->
                                <!--</div>-->
                               <div class="row" >
                                   <div class="col-lg-4 " >
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
                               </div>
                               <div class="row">
                                   
                                    <div class="col-lg-4 " >   
                                        <div class="form-group " >
                                             <label for="">Certificado</label>
                                             <input class="form-control" name="nro_certificado" id="nro_certificado"  value="<%= preticket.getNro_certificado() %>" >
                                         </div>                                        
                                    </div>
                                    <div class="col-lg-4 " >   
                                        <div class="form-group " >
                                             <label for="">Habilitaci&oacute;n</label>
                                             <input class="form-control" name="nro_habilitacion" id="nro_habilitacion"  value="<%= preticket.getNro_habilitacion() %>"  >
                                        </div>                                        
                                    </div> 
                                     <div class="col-lg-4 " >
                                        <div class="form-group " >
                                             <label for="">Numero Factura</label>
                                             <input class="form-control" name="nro_factura" id="nro_factura"  value="<%= preticket.getNro_factura() %>"  >
                                        </div>
                                   </div>    
                               </div>
                            </fieldset>
                              </form>          
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
                                        <col span="1" style="width: 8%;">
                                        <col span="1">
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
                                            <th style="width:50px">Cantidad</th>
                                            <th style="width:50px">Posici&oacute;n</th>
                                            <th>Descripci&oacute;n</th>
                                            
                                            <th style="width:50px">Dias Herramientas</th>
                                            <th style="width:50px">Unidad</th>
                                            <th style="width:50px">Precio unitario</th>
                                            <th style="width:50px;" >Total</th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <% 
                                            for( Preticket_detalle detalle: lstDetalle) {        
                                                String divisa = detalle.getId_divisa()==0?"U$S":"$";
                                                Float dias_herramienta = detalle.getCantidad() * detalle.getDias();
                                        %>
                                            <%--<%@include file="preticket_detalle.jsp" %>--%>                                            
                                            <tr>
                                                <td >
                                                    <%= detalle.getRemito_inicio()%>
                                                    </td>
                                                
                                                <td style="width:20px;"> 
                                                    <%= TFecha.formatearFechaBdVista(detalle.getFecha_inicio()) %>
                                                </td>
                                                
                                                <td>
                                                    <%= detalle.getRemito_cierre() %>
                                                </td>
                                                <td style="width:20px;"> 
                                                    <%= TFecha.formatearFechaBdVista(detalle.getFecha_cierre()) %>
                                                </td>
                                                <th>
                                                    <%=detalle.getDias()%>
                                                    
                                                </th>
                                                <td  >
                                                    <%= detalle.getCantidad()%>
                                                </td>
                                                <td >
                                                    <%= detalle.getPosicion() %>
                                                </td>
                                                <td style="width:290px;">
                                                    <%= detalle.getDescripcion() %>
                                                </td>
                                                
                                                <td  >
                                                    <%= dias_herramienta %>
                                                    <input type="hidden" name ="dias_herramienta " value="<%= dias_herramienta %>">
                                                </td>
                                                <td  >
                                                    <% 
                                                        Option o = mapUnidades.get(detalle.getId_unidad());
                                                        if (o!=null) { %>
                                                                <%= o.getDescripcion() %> 
                                                    <% } %>
                                                    <input type="hidden" name ="id_unidad" value="<%= detalle.getId_unidad()%>">
                                                </td>
                                                    <td style="width:75px">                                                       
                                                       <%= detalle.getPrecio()%>
                                                       <%=  divisa %>
                                                       
                                                    </td>                                                    
                                                    <td>
                                                        <%= detalle.getSubtotal() %> 
                                                        <%= divisa %>                                                        
                                                    </td>
                                                </tr>
                                         <% } %>
                                         
                                         <%--<%@include file="preticket_test.jsp" %>--%>
                                    </tbody>
                                </table>                                
                                <button type="submit" class="btn btn-default" id="btnSubmit">Guardar</button>    
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.PRETICKET%>">Volver</a>
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
            
            $('.date-picker').mask('99/99/9999');
            $('.date-picker').datepicker({
                language: 'es'
            }); 
            $('#btnSubmit').click(submitForm);
        });
        
        
       function validar(){
        var $punto_venta = $('#punto_venta');
        var $numero  = $('#numero');
        var $fecha = $('#fecha');
        
        if(!validarCampoFecha($fecha,"Debe ingresar la fecha"));   
        if(!validarCampo($punto_venta,"Debe ingresar el punto de venta",function(e){return parseInt(e.val())===0})) return false;
        if(!validarCampo($numero,"Debe ingresar el n&uacute;mero de preticket",function(e){console.log(e);return parseInt(e.val())===0})) return false;
        return true;
       }
      
      function validarCampo (campo,mensajeError,check){     
          if(check ===undefined) { check = false;}          
          if(campo ===undefined || campo.val()=== "" || check(campo)){
            bootbox.alert(mensajeError);
            campo.parent().addClass("has-error");
            return false;
        } else campo.parent().removeClass("has-error");
        return true;
      }
      function validarCampoFecha(campo){
           if(campo===undefined || campo.val()==="" || !validarFecha(campo.val())){            
            bootbox.alert(mensajeError);
            campo.parent().addClass("has-error");
            return false;
        } else campo.parent().removeClass("has-error");
        return true;
      }
        
        
    </script>
    <!-- Modal -->

    <%@include file="cliente_mdl.jsp" %>
    <%@include file="site_mdl.jsp" %>
    <%@include file="rubro_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>    
</body>

</html>
