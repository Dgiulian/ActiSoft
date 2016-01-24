<%@page import="transaccion.TSite"%>
<%@page import="bd.Site"%>
<%@page import="utils.TFecha"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TCliente"%>
<%@page import="bd.Activo"%>
<%@page import="transaccion.TActivo"%>
<%@page import="transaccion.TContrato"%>
<%@page import="bd.Contrato"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bd.Remito_detalle"%>
<%@page import="bd.Remito"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<%    
    Remito remito = (Remito) request.getAttribute("remito");
    Remito remito_ref = (Remito) request.getAttribute("referencia");
    
    ArrayList<Remito_detalle >  detalle = (ArrayList<Remito_detalle>) request.getAttribute("detalle");
    
    Contrato contrato = new TContrato().getById(remito.getId_contrato());
    if (contrato==null) contrato = new Contrato();
    
    Cliente cliente = new TCliente().getById(remito.getId_cliente());
    if(cliente==null) cliente = new Cliente();
    Site site = new TSite().getById(remito.getId_site());
    if(site==null) site = new Site();
    
    String tipoRemito="";
    for(Option o:OptionsCfg.getTipoRemitos()) {
        if (o.getId()==remito.getId_tipo_remito()) {tipoRemito=o.getDescripcion();
            break;
            }
    }
    
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
                    <h1 class="page-header">
                        <% if (remito_ref!=null) {%>
                            Origen: 
                            <% } %>
                        
                        Remito de <%= StringEscapeUtils.escapeHtml4(tipoRemito) %></h1>
                        <div class="button-bar">
                       <% if(remito.getId_tipo_remito() == OptionsCfg.REMITO_DEVOLUCION 
                          && remito.getId_estado() == OptionsCfg.REMITO_ESTADO_CERRADO
                          && remito.getFacturado() == 0) {%>
                        <a class="btn btn-info" href="<%=PathCfg.PRETICKET_EDIT %>?id_remito=<%= remito.getId()%>"><i class="fa fa-dollar fa-fw"></i> Preticket</a>
                        <%}%>
                        </div>
                        <input type="hidden" name="id_remito" id="id_remito" value="<%= remito.getId()%>">
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <form action="<%= PathCfg.REMITO_EDIT%>" method="POST"  role="form">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> 
                          <% if (remito_ref!=null) {%>
                            Traza del remito N&deg;: <%= remito_ref.getNumero() %>
                          <%} else {%>
                            Datos b&aacute;sicos del remito 
                            <% } %>
                      </div>
                      <div class="panel-body">
                          <div class="row">
                              <fieldset disabled>
                               <div class="col-lg-3 " >
                                    <div class="form-group-sm">
                                       <label for="cliente_cuit">N&uacute;mero contrato</label>                                       
                                       <input class="form-control" name="contrato" id="contrato" value="<%= contrato.getNumero() %>">                                        
                                   </div>
                               </div>
                                   <div class="col-lg-2 " >
                                     <div class="form-group ">
                                        <label for="punto_venta">Punto de venta</label>                                       
                                       <input class="form-control" name="punto_venta" id="punto_venta" value="<%= remito.getPunto_venta()%>">                                        
                                     </div>
                                   </div>
                                   <div class="col-lg-3 " >
                                    <div class="form-group-sm ">
                                        <label for="cliente_cuit">N&uacute;mero remito</label>                                       
                                       <input class="form-control" name="numero" id="numero" value="<%= remito.getNumero() %>">                                        
                                   </div>
                                       
                                 </div>
                                   <div class="col-lg-3 " >
                                    <div class="form-group ">
                                        <label for="fecha">Fecha remito</label>                                       
                                        <input class="form-control date-picker" name="fecha" id="fecha" value="<%= TFecha.formatearFechaBdVista(remito.getFecha())%>">                                        
                                   </div>
                                       
                                 </div> 
                               </fieldset>
                          </div>
                          <div class="row">
                              <div class="col-lg-12">
                                <% String display= "";
                                String nuevo = "";
                                       if (!remito.getArchivo().equals("")) {
                                           nuevo = "nuevo";
                                            display = "style='display: none'"; 
                                   %>
                                       <div class="form-group">                            
                                           <a href="<%=PathCfg.DOWNLOAD%>?type=remito&id=<%=remito.getId()%>" class="btn btn-success"><span> </span> Archivo asociado: <b><%= remito.getArchivo() %></b></a>                                         
                                           
                                       </div>
                                   <% }  %>
                                   <span  class="btn btn-default" data-toggle="modal" data-target="#mdlRemitoUpload" data-id="'+ d.id+'" ><span class="fa  fa-upload fa-fw"></span> Asociar <%=nuevo%> archivo</span>     
                              
                              </div>
                          </div>
                          <hr>
                          <span class="pull-right btn btn-default fa fa-chevron-up" id="btnHideData"></span>
                      <div class="row" id="data">
                          
                        <div class="col-lg-6 " >
                             <h3 class="">Cliente</h3>                             
                             <fieldset disabled>                                 
                                 <div class="form-group">
                                    <label for="cliente_nombre">Nombre</label>
                                    <input class="form-control" name="cliente_nombre" id="cliente_nombre" value="<%= cliente.getNombre_comercial() %>" >
                                </div>
                                 <div class="form-group">
                                    <label for="cliente_direccion">Direcci&oacute;n</label>
                                    <input class="form-control" name="cliente_direccion" id="cliente_direccion"  value="<%= cliente.getDireccion_fisica() %>">
                                </div>
                                 <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_iva">IVA</label>
                                       <select class="form-control" name="cliente_iva" id="cliente_iva" >
                                           <option value="0"></option>
                                           <% for(Option o:OptionsCfg.getClasesIva()){ 
                                           String selected = "";%>
                                           <option value="<%=o.getId()%>" <%= selected %>><%= o.getDescripcion()%></option>
                                           <%  } %>
                                           %>
                                       </select>
                                   </div>
                                 </div>
                                  <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_cuit">CUIT</label>
                                       <input class="form-control" name="cliente_cuit" id="cliente_cuit"  value="<%= cliente.getCuit() %>">
                                   </div>
                                 </div>     
                                           
                        </div>
                         <!-- ./Cliente -->
                        <div class="col-lg-6 " >
                            <h3 class="">Locaci&oacute;n</h3>
                            <fieldset disabled>
                                <div class="form-group">
                                    <label for="">Area</label>
                                    <input class="form-control" name="area" id="area" value="<%=site.getArea()%>" >
                                </div>
                                <div class="col-lg-6 " >
                                    <div class="form-group">
                                        <label for="">Pozo</label>
                                        <input class="form-control" name="pozo" id="pozo" value="<%=site.getPozo()%>" >
                                    </div>
                                </div>
                                <div class="col-lg-6 " >
                                    <div class="form-group-sm">
                                        <label for="">Equipo</label>
                                        <input class="form-control" name="pozo" id="pozo" value="<%=site.getEquipo()%>" >
                                    </div>
                                </div>     
                                </fieldset>
                        </div>
                          <!-- ./Locacion -->
                        <!-- /.col-lg-6 (nested) -->
                        <!-- /.row (nested) -->
                        </div>  
                          <!-- ./row -->                     
                          <hr>
                          
                        <div class="row">
                            <div class="col-lg-12">
                                <h3>Activos</h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblRemito">
                                    <thead>
                                        <tr>
                                            <th>C&oacute;digo</th>
                                            <th>Posici&oacute;n</th>
                                            <th>Descripci&oacute;n</th>
                                            <th>Cantidad</th>
                                            <!--<th></th>-->
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <% TActivo ta = new TActivo();
                                            for(Remito_detalle rm: detalle){ 
                                                Activo activo = ta.getById(rm.getId_activo());
                                        %>
                                        
                                        <tr>
                                            <td><%= activo.getCodigo()%></td>
                                            <td><%= rm.getPosicion() %></td>
                                            <td><%= StringEscapeUtils.escapeHtml4(activo.getDesc_larga())%></td>
                                            <td><%= rm.getCantidad()%></td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tfoot>
                                </table>
                                
                                <!--<button type="submit" class="btn btn-default">Guardar</button>-->
<!--                            <button type="reset" class="btn btn-default">Reset Button</button>-->
                            </div>
                            <div class="col-lg-12">
                               <div class="form-group">
                                   <label for="observaciones">Observaciones</label>
                                   <textarea name="observaciones" id="observaciones" class="form-control" readonly=""><%=remito.getObservaciones()%></textarea>
                               </div>
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
    
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/invesoft.js"></script>
    <script>
        $(document).ready(function(){
            $('#mdlRemitoUpload').on('shown.bs.modal',function(e){
            var $invoker = $(e.relatedTarget);
            var $id_remito = $('#id_remito');
            var id = $id_remito.val();            
            $('#id').val(id);            
        });
        });
    </script>
<%@include  file="remito_upload_mdl.jsp" %>
<%@include file="tpl_footer.jsp"%>
</body>

</html>