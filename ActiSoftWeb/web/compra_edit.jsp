<%@page import="java.io.File"%>
<%@page import="transaccion.TParametro"%>
<%@page import="bd.Parametro"%>
<%@page import="bd.Parametro"%>
<%@page import="java.util.ArrayList"%>
<%@page import="transaccion.TProveedor"%>
<%@page import="bd.Proveedor"%>
<%@page import="bd.Activo"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="bd.Compra"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<% 
    Compra compra = (Compra) request.getAttribute("compra");
    Activo activo = (Activo) request.getAttribute("activo");
    boolean nuevo = false;
    if (compra==null) {
        compra= new Compra();
    }    
    nuevo = compra.getId()==0;
    List<Proveedor> lstProveedores = new TProveedor().getList();
    if ( lstProveedores==null) lstProveedores = new ArrayList<Proveedor>();
    String compra_url = PathCfg.DOWNLOAD;
            
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
                     <h3><span class="activo-heading">Activo: <a href="<%=PathCfg.ACTIVO_EDIT%>?id=<%=activo.getId()%>"><%= activo.getCodigo() %> - <%= activo.getDesc_larga()%></a> </span></h3>
                 </div>
             </div>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><% if(nuevo) {%>Nueva<%}else {%>Editar<%}%> compra</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                  <%
                    String action = PathCfg.COMPRA_EDIT;
                    action += "?id_activo=" + activo.getId();
                    action += (!nuevo)?"&id="+compra.getId():"";
                    String disabled=nuevo?"":"disabled";
                %>
                <form role="form" method="POST" action="<%=action%>" enctype="multipart/form-data" >
                    
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos de la compra </div>            
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-12 " >
                            <h3 class="">Compra</h3>
                            <fieldset>
                                <input type="hidden" name="id_activo" value="<%= activo.getId()%>">
                                <!--<input type="hidden" name="id_proveedor" value="<%= compra.getId_proveedor()%>">-->
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= compra.getId()%>">                                    
                                <% }%>
                                
                                <div class="col-lg-2 " >
                                    <div class="form-group">
                                        <label for="fecha">Fecha</label>
                                        <input class="form-control date-picker" name="fecha" id="fecha" size="10"  value="<%=TFecha.formatearFechaBdVista(compra.getFecha()) %>">
                                    </div>
                                </div>
                                <div class="col-lg-2 " >   
                                    <div class="form-group">
                                        <label for="id_accion">Stock</label>
                                        <select name="id_accion" class="form-control" <%=disabled%> >
                                            <% for (Option o: OptionsCfg.getCompraAcciones()) {
                                            String selAccion = (o.getId().equals(compra.getId_accion()))?"selected":"";
                                            %>
                                            <option value="<%=o.getId()%>" <%=selAccion%> ><%=o.getDescripcion()%></option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2 " >   
                                    <div class="form-group">
                                        <label for="cantidad">Cantidad</label>
                                        <input class="form-control" name="cantidad" id="cantidad" value="<%=compra.getCantidad() %>" <%=disabled%>>
                                    </div>
                                </div>

                                <div class="col-lg-2 " >
                                     <div class="form-group " >
                                          <label for="id_divisa">Divisa</label>
                                          <select class="form-control" name="id_divisa" id="id_divisa" >
                                               <%
                                                 for(int i=0;i<2;i++){
                                                         String divisa = i==0?"Dolares":"Pesos";
                                                         String selected = i== compra.getId_divisa()?"selected":"";
                                                 %>
                                                 <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                 <% }%>                                                
                                          </select>
                                     </div>
                                </div>                                   
                                
                                <div class="col-lg-2 " >   
                                     <div class="form-group " >
                                          <label for="precio_unit">Precio</label>
                                          <input class="form-control" name="precio_unit" id="precio_unit" value="<%= compra.getPrecio_unit() %>">
                                      </div>                                        
                                 </div>
                                <div class="col-lg-2 " >   
                                     <div class="form-group " >
                                          <label for="precio_tot">Total</label>
                                          <input class="form-control" name="precio_tot" id="precio_tot" value="<%= compra.getPrecio_unit() %>">
                                      </div>                                        
                                 </div>
                                  <div class="col-lg-4 " >   
                                     <div class="form-group " >
                                          <label for="id_proveedor">Proveedor</label>
                                          <select name="id_proveedor" id="id_proveedor" class="form-control">
                                            <% for(Proveedor p: lstProveedores)  {
                                            String checked = p.getId()==compra.getId_proveedor()?"checked":"";%>
                                            <option value="<%=p.getId()%>" <%= checked %>><%= p.getNombre_comercial()%></option>
                                            <%}%>
                                          </select>
                                      </div>                                        
                                 </div>
                                <div class="col-lg-3 " >   
                                     <div class="form-group " >
                                          <label for="factura">Factura</label>
                                          <input class="form-control" name="factura" id="factura" value="<%= compra.getFactura() %>">
                                      </div>                                        
                                 </div>
                                 </div>
                            </div>
                            <div class="row">
                                  <div class="col-lg-12 " >
                                  <div class="col-lg-4 " >
                                          <% String display1 = ""; %>
                                          <div class="form-group"  >
                                            <label for="certificado_fabricacion">Certificado de fabricaci&oacute;n</label>
                                    <%
                                        if (!"".equals(compra.getCertificado_fabricacion())) { 
                                             display1 = "style='display: none'";     
                                    %>                                  
                                        <div class="form-group">                            
                                            <a href='<%=compra_url + "?type=certificado_fabricacion&id="+ compra.getId()%>' target="_blank"><%=compra.getCertificado_fabricacion()%></a>
                                            </a>
                                             <span class="btn btn-default cambiarArchivo" data-target="selectFile1">Cambiar</span>
                                        </div>
                                        <% }  %>
                                        <div  <%=display1 %>  id="selectFile1">
                                            <input type="file" name="certificado_fabricacion" id="certificado_fabricacion" value="">
                                        </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 " >
                                          <% String dspFactura_compra = ""; %>
                                          <div class="form-group"  >
                                            <label for="factura_compra">Factura de compra</label>
                                    <%
                                        if (!"".equals(compra.getFactura_compra())) { 
                                             dspFactura_compra = "style='display: none'";     
                                    %>                                  
                                        <div class="form-group">                            
                                            <a href='<%=compra_url + "?type=factura_compra&id="+ compra.getId()%>' target="_blank"><%=compra.getFactura_compra()%></a>
                                            </a>
                                             <span class="btn btn-default cambiarArchivo" data-target="selectFile2">Cambiar</span>
                                        </div>
                                        <% }  %>
                                        <div  <%=dspFactura_compra %>  id="selectFile2">
                                            <input type="file" name="factura_compra" id="factura_compra" value="">
                                        </div>
                                        </div>
                                    </div>        
                                    </div> <!-- row-->
                               <!--</div>-->
                            
                        </div>
                        </fieldset>
                        
                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        <!--</div>-->
                        <div class="row">
                            <div class="col-lg-12">
                            <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                            <a type="reset" class="btn btn-default" href="<%=PathCfg.COMPRA%>?id_activo=<%= activo.getId() %>">Cancelar</a>
                        </div>
                                     </div>
                          <!-- ./row -->
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
    $(document).ready(function(){
        $('#fecha').mask('99/99/9999');
        $('#cantidad').change(completarTotal);
        $('#precio_unit').change(completarTotal);
        $('#btnSubmit').click(function(e){
           e.preventDefault();  
           limpiarErrores();
           if(validar(recuperarDatos())){
                $('form').submit();
           }
        });
         $('.cambiarArchivo').click(function(){
            target = $(this).data('target');
            if(target!==undefined)
                $('#' + target).slideDown();
        });
    });
    function recuperarDatos(){
       var data = {};
       data.fecha = $('#fecha').val();
       data.cant  = $('#cantidad').val()
       data.precio_unit = $('#precio_unit').val();
       data.precio_tot = $('#precio_tot').val();
       return data;
    }
    function completarTotal(){
        var data = recuperarDatos();
        if(data.cant ===undefined || 
           data.precio_unit ===undefined  || 
           data.precio_tot ===undefined  ) return;        
        if(data.cant === "") return;
        if(data.precio_unit ==="") return;        
        $('#precio_tot').val(data.cant * data.precio_unit);
    }
    function errorCampoFecha(){                
        $('#fecha').parent().addClass("has-error");
        bootbox.alert("Debe ingresar la fecha de compra");
    }
    function errorCampoCant(){        
        $('#cantidad').parent().addClass("has-error");
        bootbox.alert("Debe ingresar la cantidad de la compra");
    }
    function errorCampoPrecio(){
       $('#precio_unit').parent().addClass("has-error");
       bootbox.alert("Debe ingresar el precio unitario de la compra");
    }
    function errorCampoTotal(){
        $('#precio_tot').parent().addClass("has-error");
        bootbox.alert("Debe ingresar el precio total de la compra");
    }
    function limpiarErrores(){
       $('#fecha').parent().removeClass("has-error");
       $('#cantidad').parent().removeClass("has-error");
       $('#precio_unit').parent().removeClass("has-error");
       $('#precio_tot').parent().removeClass("has-error");
    };
    function validar(data){
        if(data.fecha===undefined || data.fecha==="" || !validarFecha(data.fecha)){            
            errorCampoFecha();
            return false;
        }
        if(data.cant===undefined || data.cant==="" || parseInt(data.cant) === 0){            
            errorCampoCant();
            return false;
        }
        if(data.precio_unit===undefined || data.precio_unit==="" || parseFloat(data.precio_unit)===0){            
            errorCampoPrecio();
            return false;
        }        
        if(data.precio_tot===undefined || data.precio_tot==="" || parseFloat(data.precio_tot)===0){            
            return false;
        }
        return true;
    }
   
    </script>
    <!-- Modal -->

<%@include file="tpl_footer.jsp"%>   
    
</body>

</html>
