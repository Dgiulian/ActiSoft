<%@page import="bd.Cliente"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="bd.Site"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="java.util.List"%>
<%
    Site site = (Site) request.getAttribute("site");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    boolean nuevo = false;
    if (site==null) {
        site= new Site();
        site.setLatitud(-38.9503642f);
        site.setLatitud(-68.041789f);
    }
    nuevo = site.getId()==0;
%>
<!DOCTYPE html>
<html lang="en">

<head>
     <%@include  file="tpl_head.jsp" %>
    <style>
        Flexible-container {
    position: relative;
    padding-bottom: 56.25%;
    padding-top: 30px;
    height: 0;
    overflow: hidden;
}

.Flexible-container iframe,
.Flexible-container object,
.Flexible-container embed {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
#gmap{
    position:absolute;
    width:450px;
    height:450px;
}
        </style>
</head>

<body>

    <div id="wrapper">

      <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else {%>Editar<%}%> site</h1>
                     <% if (!nuevo) {%>
                    <div class="button-bar" >

                        <a   class="btn btn-info" href="#" data-toggle="modal" data-target="#mdlSiteHistoria"><i class="fa fa-history fa-fw"></i> Historia</a>
                    </div>
                    <% } %>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                 <%
                    String action = PathCfg.SITE_EDIT;
                    action += "?id_cliente=" + cliente.getId();
                    action += (!nuevo)?"&id="+site.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>" >
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                           <li class="active"><a href="#tab1" data-toggle="tab">Datos b&aacute;sicos</a></li>
                           <li><a href="#tab2" data-toggle="tab">Contacto</a></li>
                           <!--<li><a href="#tab3" data-toggle="tab"></a></li>-->
                           <!--<li><a href="#tab4" data-toggle="tab">Datos financieros</a></li>-->
                           <li><a href="#tab5" data-toggle="tab">Observaciones</a></li>
                       </ul>
<!--                      <div class="panel-heading"> Datos b&aacute;sicos del site </div>            -->
                      <div class="panel-body">
                          <div class="tab-content ">
                            <div class="tab-pane active" id="tab1">
                      <div class="row">
                        <div class="col-lg-6 " >
                            <h3 class="">Site</h3>
                            <fieldset>
                                <input type="hidden" name="id_cliente" value="<%= cliente.getId()%>">
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" id="id" value="<%= site.getId()%>">
                                <% }%>

                                <div class="col-lg-12 " >
                                    <div class="form-group">
                                        <label for="nombre">Nombre</label>
                                        <input class="form-control" name="nombre" id="nombre"   value="<%=site.getNombre() %>">
                                    </div>
                                </div>
                                <div class="col-lg-3 " >
                                    <div class="form-group">
                                        <label for="area">Area</label>
                                        <input class="form-control" name="area" id="area" value="<%=site.getArea() %>">
                                    </div>
                                </div>
                                <!--<div class="col-lg-12 ">-->

                                <div class="col-lg-3 " >
                                     <div class="form-group " >
                                          <label for="pozo">Pozo</label>
                                          <input class="form-control" name="pozo" id="pozo" value="<%= site.getPozo() %>">
                                      </div>
                                 </div>
                                <div class="col-lg-3 " >
                                     <div class="form-group " >
                                          <label for="equipo">Equipo</label>
                                          <input class="form-control" name="equipo" id="equipo" value="<%= site.getEquipo() %>">
                                      </div>
                                </div>
                                <div class="row" >
                                    <div class="form-group">
                                        <label for="id_estado">Activo</label>
                                        <% String chkActivo = site.getId_estado()!=0?"checked":""; %>
                                        <input class="" type="checkbox" name="id_estado" id="id_estado" <%=chkActivo%> >
                                    </div>
                                </div>
                                <div class="col-lg-6 " >
                                    <div class="form-group">
                                        <label for="latitud">Latitud</label>
                                        <input class="form-control" type="number"  name="latitud" id="latitud" value="<%= site.getLatitud()%>">
                                    </div>
                                </div>

                                <div class="col-lg-6 " >
                                    <div class="form-group">
                                        <label for="latitud">Longitud</label>
                                        <input class="form-control" type="number"  name="longitud" id="longitud" value="<%= site.getLongitud()%>">
                                    </div>
                                </div>

                               <!--</div>-->
                            </fieldset>
                        </div> <!-- /.col-lg-6 (nested) -->
                        <div class="col-lg-6">
                            <div id="gmap" class="Flexible-container"></div>
                        </div>
                        </div><!-- /.row (nested) -->
                        </div><!-- /tab1 -->
                        <div class="tab-pane" id="tab2">
                            <div class="col-lg-6 " >
                                <div class="form-group">
                                    <label for="telefono">Tel&eacute;fono</label>
                                    <input class="form-control" type="text" name="telefono" id="telefono" value="<%= site.getTelefono()%>">
                                </div>
                                <div class="form-group">
                                    <label for="encargado">Encargado</label>
                                    <input class="form-control" type="text" name="encargado" id="encargado" value="<%= site.getEncargado()%>">
                                </div>

                                <div class="form-group">
                                    <label for="horario">Horario</label>
                                    <input class="form-control" type="text" name="horario" id="horario" value="<%= site.getHorario()%>">
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab5">
                            <div class="col-lg-6 " >
                                <div class="form-group " >
                                    <label for="fecha_Creacion">Fecha Alta</label>
                                    <input type="text" class="form-control" disabled name="fecha_creacion" id="fecha_creacion" value="<%=TFecha.formatearFechaBdVista(site.getFecha_creacion())%>">
                                </div>
                                <div class="form-group " >
                                    <label for="observaciones">Observaciones</label>
                                    <textarea class="form-control" name="observaciones" id="observaciones"><%= site.getObservaciones()%></textarea>
                                </div>
                            </div>
                        </div>
                    </div> <!-- /tab-content -->

                        <div class="row">
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.SITE%>?id_cliente=<%= cliente.getId() %>">Cancelar</a>
                            </div>
                        </div>
                          <!-- ./row -->
                        </div>
                        </div> <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    </form>
                </div>
                <!-- /.col-lg-12 -->
                
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

    <script src="bower_components/jquery-form/jquery.form.min.js"></script>

    <script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA"></script>
    <script src="bower_components/mapplace/maplace-0.1.3.js"></script>

    <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>

    <script>
        var m ;
        $(document).ready(function(){
            var ApyKey = "AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA";

            var lat = $('#latitud').val();
            var lon = $('#longitud').val();

            $('#btnSubmit').click(submitForm);
            m = new Maplace({
                locations: [{
                    lat: lat,
                    lon: lon,
//                    zoom: 10
                }],
                //set_center: [-38.9503642,-68.041789],
                map_options: {
                    mapTypeId: google.maps.MapTypeId.SATELLITE,
                    zoom: 10
                },
                controls_on_map: false,
                listeners: {
                    click: function(map, event) {
                        placeMarker(event.latLng);
                    //map.setOptions({scrollwheel: true});
                }
}
            }).Load();
            $('#mdlSiteHistoria').on('show.bs.modal',function(){
                var $id_site = $('#id');
                if ($id_site!==null && $id_site.val()!=="")
                    loadDataActivoHistoria({id_site:$id_site.val()});
            });
        });
        function placeMarker(location) {
            var lat = location.k.toFixed(6);
            var lon = location.D.toFixed(6);

            $('#latitud').val(lat);
            $('#longitud').val(lon);

            m.RemoveLocations(m.ln-1);
            m.AddLocation({
                lat:lat,
                lon:lon,
    //            zoom:10
            },0,true);

        }
        function validar(){
//            var $fecha = $('#fecha');
//
//            if($fecha===undefined || $fecha.val()==="" || !validarFecha($fecha.val())){
//                bootbox.alert("Debe ingresar la fecha del certificado");
//                $fecha.parent().addClass("has-error");
//                return false;
//            } else $fecha.parent().removeClass("has-error");
            return true;
        }

    </script>
    <!-- Modal -->


    <%@include file="site_historia_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
