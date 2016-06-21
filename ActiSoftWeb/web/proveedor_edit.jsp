<%@page import="utils.TFecha"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="transaccion.TLocalidad"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Proveedor"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    Proveedor proveedor = (Proveedor) request.getAttribute("proveedor");
    boolean nuevo = false;
    if (proveedor==null) {
        proveedor = new Proveedor();
        nuevo = true;
    }
    List<Provincia> lProvincias = new TProvincia().getList();    
    Integer idProv = proveedor.getId_provincia();
    List<Localidad> lLocalidades = new TLocalidad().recuperarLocalidades(idProv);
    Integer idLoc = proveedor.getId_localidad();
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else{%>Editar<%}%> Proveedor</h1>                    
                    <% if (!nuevo) {%>                     
                    <div class="button-bar" >
                        <a class="btn btn-info" href="<%=PathCfg.TRANSPORTISTA %>?id_proveedor=<%= proveedor.getId()%>"><i class="fa fa-male fa-fw"></i> Transportistas</a>
                        <a class="btn btn-info" href="<%=PathCfg.VEHICULO %>?id_proveedor=<%= proveedor.getId()%>"><i class="fa fa-truck fa-fw"></i> Vehiculos</a>
                    </div>
                    <% } %>
                </div>
                
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                  <%
                    String action = PathCfg.PROVEEDOR_EDIT;
                    action += (!nuevo)?"?id="+proveedor.getId():"";
                %>                
                <form action="<%= action%>" method="POST"  role="form">                
                    <% if(!nuevo) { %>
                    <input type="hidden" name="id" id ="id" value="<%= proveedor.getId() %>">
                    <%}%>
                <div class="col-lg-12">
                    <div class="panel panel-default">
<!--                      <div class="panel-heading">
                            Datos b&aacute;sicos del proveedor
                        </div>-->
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                           <li class="active"><a href="#tab1" data-toggle="tab">Datos b&aacute;sicos</a></li>
                           <li><a href="#tab2" data-toggle="tab">Domicilio</a></li>
                           <li><a href="#tab3" data-toggle="tab">Contacto</a></li>
                           <li><a href="#tab4" data-toggle="tab">Datos financieros</a></li>
<!--                           <li><a href="#tab5" data-toggle="tab">Datos transportista</a></li>-->
                           <li><a href="#tab6" data-toggle="tab">Observaciones</a></li>
                       </ul>
                        <div class="panel-body">
                            
                            <div class="tab-content ">
                                <div class="tab-pane active" id="tab1">
                                   <div class="col-lg-6" >
                                        <div class="form-group">
                                            <label for="razon_social">Raz&oacute;n social</label>
                                            <input class="form-control" name="razon_social" id="razon_social"  value="<%=proveedor.getRazon_social()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="cuit">CUIT</label>
                                            <input class="form-control" name="cuit" id="cuit" value="<%=proveedor.getCuit()%>">
                                        </div>
                                       
                                        <div class="form-group">
                                            <label for="nombre_comercial">Nombre</label>
                                            <input class="form-control"  name="nombre_comercial" id="nombre_comercial" value="<%=proveedor.getNombre_comercial()%>">                                            <!--<p class="help-block"></p>-->
                                        </div>

                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="dni">DNI</label>
                                            <input class="form-control" name="dni" id="dni"  value="<%=proveedor.getDni()%>">
                                        </div>
                                        <div class="form-group " >
                                             <label for="id_forma_pago">Tipo</label>
                                             <select class="form-control" name="id_forma_pago" id="id_forma_pago" >
                                                  <% for(Option o: OptionsCfg.getTiposProveedor()){
                                                        String selected = o.getId()== proveedor.getId_tipo_proveedor()?"selected":"";
                                                   %>
                                                    <option value="<%= o.getId() %>" <%= selected %> > <%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div> 
                                        <div class="form-group">
                                            <label>Activo</label>
                                            <% String checked = (proveedor.getId_estado()!=0)?"checked":""; %>
                                            <input type="checkbox" name="id_estado" id="id_estado" <%= checked%> >
                                        </div>
                                        <%if (!nuevo) {%>
                                        <div class="form-group">
                                            <label>Fecha de alta</label>                                            
                                                <input type="text" name="fecha_alta" id="fecha_alta" class="form-control" disabled value="<%=TFecha.formatearFechaBdVista(proveedor.getFecha_alta())%>" >                                            
                                        </div>
                                        <% } %>
                                    </div> 
                                        
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                <div class="tab-pane" id="tab2" >
                                    <div class="col-lg-6"> 
                                        <div class="form-group">
                                            <label for="direccion_fisica">Direcci&oacute;n f&iacute;sica</label>
                                            <input class="form-control"  name="direccion_fisica" id="direccion_fisica"  value="<%=proveedor.getDireccion_fisica()%>">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>C&oacute;digo postal</label>
                                            <input class="form-control"  name="codigo_postal" id="codigo_postal" value="<%=proveedor.getCodigo_postal()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="id_provincia">Provincia</label>
                                            <select class="form-control" id="id_provincia" name="id_provincia">
                                                    <option value="0">Seleccione una provincia</option>
                                                    <%
                                                        for (Provincia prov : lProvincias) {
                                                    %>
                                                    <option value="<%=prov.getProv_id()%>" 
                                                            <% if (prov.getProv_id().equals(idProv)) {%> selected <% }%>

                                                            > <%=StringEscapeUtils.escapeHtml4(prov.getProv_descripcion())%></option>

                                                    <% }%>
                                                </select>            
                                        </div>
                                        <div class="form-group">
                                           <label for="id_localidad">Localidad</label>
                                           <select class="form-control" name="id_localidad" id="id_localidad" >
                                                <option value="0"> Seleccione su localidad</option>
                                                    <% if (lLocalidades != null) {
                                                            for (Localidad loc : lLocalidades) {%>
                                                            <option value="<%= loc.getLoc_id()%>"                                                                        
                                                                    <% if (loc.getLoc_id().equals(idLoc)) {%> selected <% }%>
                                                            ><%=StringEscapeUtils.escapeHtml4(loc.getLoc_descripcion())%></option>
                                                        <% }
                                                    }%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                             <label>Direcci&oacute;n legal</label>
                                            <input class="form-control" name="direccion_legal" id="direccion_legal" value="<%=proveedor.getDireccion_legal()%>">
                                        </div>
                                    </div>
                                </div>
                                 <div class="tab-pane" id="tab3">
                                   <div class="col-lg-6">
                                     <div class="form-group">
                                            <label>Persona de contacto</label>
                                            <input class="form-control"  name="contacto" id="contacto" value="<%=proveedor.getContacto()%>" >
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                     <div class="form-group">
                                            <label>Telefono fijo</label>
                                            <input class="form-control"  name="telefono" id="telefono" value="<%=proveedor.getTelefono()%>">
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                     <div class="form-group">
                                            <label>Telefono de contacto</label>
                                            <input class="form-control"  name="celular" id="celular" value="<%=proveedor.getCelular()%>">
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                            <div class="form-group">
                                            <label>Email</label>
                                            <input class="form-control"  name="email" id="email" value="<%=proveedor.getEmail()%>">
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                </div>
                                 </div>
                                <div class="tab-pane" id="tab4">
                                     <div  class="col-lg-6">
                                       <div class="form-group">
                                            <label for="descuento_especial">Descuento especial</label>
                                            <input class="form-control"   name="descuento_especial" id="descuento_especial" value="<%= proveedor.getDescuento_especial()%>">
                                       </div>
                                        <div class="form-group">
                                            <label for="descuento_pronto_pago">Descuento pronto pago</label>
                                            <input class="form-control"  name="descuento_pronto_pago" id="descuento_pronto_pago" value="<%= proveedor.getDescuento_pronto_pago()%>">                                                   
                                        </div>
                                        <div class="form-group " >
                                             <label for="id_divisa">Divisa</label>
                                             <select class="form-control" name="id_divisa" id="id_divisa" >
                                                  <% for(int i=0;i<2;i++){
                                                            String divisa = i==0?"Dolares":"Pesos";
                                                            String selected = i== proveedor.getId_divisa()?"selected":"";
                                                   %>
                                                    <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div>
                                       <div class="form-group " >
                                             <label for="id_forma_pago">Forma Pago</label>
                                             <select class="form-control" name="id_forma_pago" id="id_forma_pago" >
                                                  <% for(Option o: OptionsCfg.getFormasPagos()){
                                                            
                                                            String selected = o.getId()== proveedor.getId_forma_pago()?"selected":"";
                                                   %>
                                                    <option value="<%= o.getId() %>" <%= selected %> > <%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div>
                                        
                                        <div class="form-group " >
                                             <label for="id_forma_pago">Situaci&oacute;n IVA</label>
                                             <select class="form-control" name="id_forma_pago" id="id_forma_pago" >
                                                  <% for(Option o: OptionsCfg.getClasesIva()){
                                                        String selected = o.getId()== proveedor.getId_iva()?"selected":"";
                                                   %>
                                                    <option value="<%= o.getId() %>" <%= selected %> > <%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div> 
                                        </div> 
                                        <div class="col-lg-6">
                                            <div class="form-group">
                                            <label for="monto_maximo">Monto m&aacute;ximo</label>
                                            <input class="form-control"  name="monto_maximo" id="monto_maximo" value="<%= proveedor.getMonto_maximo()%>">                                                   
                                        </div>
                                        <div class="form-group">
                                            <label for="banco1">Banco 1</label>
                                            <input class="form-control"  name="banco1" id="banco1" value="<%= proveedor.getBanco1()%>">                                                   
                                        </div> 
                                        <div class="form-group">
                                            <label for="banco1">Cuenta 1</label>
                                            <input class="form-control"  name="cuenta1" id="cuenta1" value="<%= proveedor.getCuenta1()%>">                                                   
                                        </div>   
                                        <div class="form-group">
                                            <label for="banco2">Banco 2</label>
                                            <input class="form-control"  name="banco2" id="banco2" value="<%= proveedor.getBanco2()%>">                                                   
                                        </div>
                                        <div class="form-group">
                                            <label for="banco1">Cuenta 2</label>
                                            <input class="form-control"  name="cuenta2" id="cuenta2" value="<%= proveedor.getCuenta2()%>">                                                   
                                        </div>   
                                      
                                         
                                        </div>
                                </div>
<!--                                <div class="tab-pane" id="tab5">
                                    <div  class="col-lg-6">
                                     <div class="form-group">
                                          <label for="nombre_transportista">Nombre</label>
                                          <input class="form-control" rows="3"  name="nombre_transportista" id="nombre_transportista" value="<%= proveedor.getNombre_transportista()%>">
                                      </div>
                                       <div class="form-group">
                                          <label for="vencimiento_carnet">Vencimiento carnet</label>
                                          <input class="form-control date-picker" rows="3"   name="vencimiento_carnet" id="vencimiento_carnet" value="<%= "".equals(proveedor.getVencimiento_carnet())?"":TFecha.formatearFechaBdVista(proveedor.getVencimiento_carnet())%>">
                                      </div>
                                      <div class="form-group">
                                          <label for="vehiculos"></label>
                                          <input class="form-control" rows="3"  name="vehiculos" id="vehiculos" value="<%= proveedor.getVehiculos()%>">
                                      </div>
                                      <div class="form-group">
                                          <label for="dominios"></label>
                                          <input class="form-control" rows="3"  name="dominios" id="dominios" value="<%= proveedor.getDominios()%>">
                                      </div>
                                      <div class="form-group">
                                          <label for="dni_conductor"></label>
                                          <input class="form-control" rows="3"  name="dni_conductor" id="dni_conductor" value="<%= proveedor.getDni_conductor()%>">
                                      </div>
                                    </div>
                                </div>-->
                                  <div class="tab-pane" id="tab6">
                                      <div  class="col-lg-12">
                                       <div class="form-group">
                                            <label for="observaciones">Observaciones</label>
                                            <textarea class="form-control" rows="3"  name="observaciones" id="observaciones" ><%= proveedor.getObservaciones()%></textarea>
                                        </div>
                                      </div>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                
                            </div>
                            <!-- /.row (nested) -->
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.PROVEEDOR%>">Cancelar</a>
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
        $(document).ready(function(){
            $('#id_provincia').change(function() {
                    $('#id_localidad').html("");
                    var url = "<%= PathCfg.LOCALIDAD_LIST%>?idProv=" + $(this).val();
                    $.ajax({
                        url: url,
                        success: function(data) {
                            console.log(data.Result);
                            var html = "";
                            if(data.Result==="OK"){
                               $('#id_localidad').html(createSelect(data.Records));                               
                            }
                        }
                    });
            });
            $('#mdlProveedorHistoria').on('show.bs.modal',function(){
                var $id_proveedor = $('#id');
                if ($id_proveedor!==null && $id_proveedor.val()!=="")
                    loadDataActivoHistoria({id_proveedor:$id_proveedor.val()});
            });
        });
        function createSelect(data){
            var html = "<option value='0'></option>";
            for (var i=0;i<data.length;i++){
                d = data[i];
                html += "<option value='" + d.loc_id + "'>" + d.loc_descripcion+ "</option>";
            }
            return html;
        }
        
    </script>
    <%--<%@include file="proveedor_historia_mdl.jsp" %>--%>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
