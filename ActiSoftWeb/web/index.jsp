<%@page import="transaccion.TParametro"%>
<%@page import="bd.Parametro"%>
<%@page import="utils.PathCfg"%>
<%
    Parametro entorno = new TParametro().getById(PathCfg.PARAMETRO_ENTORNO);
    if (entorno==null) entorno = new Parametro();
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
#map-container{
    /*position:absolute;*/
    width:450px;
    height:450px;

}
</style>
</head>

<body>

    <div id="wrapper">
        <%@include file="tpl_navbar.jsp" %>


        <div id="page-wrapper">
        <% if(entorno.getValor().equalsIgnoreCase("desarrollo")){ %>
           <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header " style="text-transform:uppercase"><center><%= PathCfg.PAGE_TITLE + " - " + entorno.getValor() %></center></h1>
                </div>
                 <!--/.col-lg-12-->
            </div> <!-- /.row -->
        <% } %>


            <!-- /.row -->
            <div class="row">
                <div class="col-lg-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-map-marker fa-fw"></i> Mapa de Sites
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                           <div id="map-container" class="col-md-6"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Area Chart Example
                            <div class="pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-area-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Activos alquilados por empresa
                            <div class="pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-hover table-striped">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Date</th>
                                                    <th>Time</th>
                                                    <th>Amount</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>3326</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:29 PM</td>
                                                    <td>$321.33</td>
                                                </tr>
                                                <tr>
                                                    <td>3325</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:20 PM</td>
                                                    <td>$234.34</td>
                                                </tr>
                                                <tr>
                                                    <td>3324</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:03 PM</td>
                                                    <td>$724.17</td>
                                                </tr>
                                                <tr>
                                                    <td>3323</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:00 PM</td>
                                                    <td>$23.71</td>
                                                </tr>
                                                <tr>
                                                    <td>3322</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:49 PM</td>
                                                    <td>$8345.23</td>
                                                </tr>
                                                <tr>
                                                    <td>3321</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:23 PM</td>
                                                    <td>$245.12</td>
                                                </tr>
                                                <tr>
                                                    <td>3320</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:15 PM</td>
                                                    <td>$5663.54</td>
                                                </tr>
                                                <tr>
                                                    <td>3319</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>$943.45</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.col-lg-4 (nested) -->
                                <div class="col-lg-8">
                                    <div id="morris-bar-chart"></div>
                                </div>
                                <!-- /.col-lg-8 (nested) -->
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-8 -->
                <div class="col-lg-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bell fa-fw"></i> Grilla de alertas
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-comment fa-fw"></i> New Comment
                                    <span class="pull-right text-muted small"><em>4 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small"><em>12 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-envelope fa-fw"></i> Message Sent
                                    <span class="pull-right text-muted small"><em>27 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-tasks fa-fw"></i> New Task
                                    <span class="pull-right text-muted small"><em>43 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                    <span class="pull-right text-muted small"><em>11:32 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-bolt fa-fw"></i> Server Crashed!
                                    <span class="pull-right text-muted small"><em>11:13 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-warning fa-fw"></i> Server Not Responding
                                    <span class="pull-right text-muted small"><em>10:57 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-shopping-cart fa-fw"></i> New Order Placed
                                    <span class="pull-right text-muted small"><em>9:49 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-money fa-fw"></i> Payment Received
                                    <span class="pull-right text-muted small"><em>Yesterday</em>
                                    </span>
                                </a>
                            </div>
                            <!-- /.list-group -->
                            <a href="#" class="btn btn-default btn-block">View All Alerts</a>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Activos por estado
                        </div>
                        <div class="panel-body">
                            <div id="morris-donut-chart"></div>
                            <a href="<%=PathCfg.ACTIVO%>" class="btn btn-default btn-block">Ver detalle</a>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-4 -->
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

    <!-- Morris Charts JavaScript -->
    <script src="bower_components/raphael/raphael-min.js"></script>
    <script src="bower_components/morrisjs/morris.min.js"></script>
    <script src="js/morris-data.js"></script>

    <script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA"></script>
    <!--<script src="bower_components/mapplace/maplace-0.1.3.js"></script>-->
        <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
<script>
        var var_map ;
        $(document).ready(function(){
            var ApyKey = "AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA";

            var lat = $('#latitud').val();
            var lon = $('#longitud').val();
            initMap();
            loadSites({});
        });
        function initMap() {
            var var_location = new google.maps.LatLng(-38.9503642,-68.041789);

            var var_mapoptions = {
              center: var_location,
              zoom: 9,
              type: google.maps.MapTypeId.SATELLITE
            };

//            var var_marker = new google.maps.Marker({
//                    position: var_location,
//                    map: var_map,
//                    title:""});

        var_map = new google.maps.Map(document.getElementById("map-container"), var_mapoptions);
//        var_marker.setMap(var_map);

       }
    function loadSites(data){
        $.ajax({
               url: '<%= PathCfg.SITE_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){},
               success: function(result) {
                   if(result.Result === "OK") {
                       var data = result.Records;
                        for(var i = 0;i<data.length;i++){
                            var d = data[i];
                             addMarker(var_map,d);
                        }

                   }
               }
           });
    }
    function addMarker(var_map,d){

        var var_location = new google.maps.LatLng(d.latitud,d.longitud);
        var var_marker = new google.maps.Marker({
                position:var_location,
                map: var_map,
                title:d.nombre});
                var_marker.id = d.id;
                var_marker.addListener('click', function() {

                loadInfoWindow(var_map,var_marker,{id_site:d.id});
            });
    }
    function loadInfoWindow(map,marker,data){
        data.id_estado = 1;
        $.ajax({
               url: '<%= PathCfg.ACTIVO_HISTORIA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){},
               success: function(result) {
                if(result.Result === "OK") {
                       var data = result.Records;
                       html = '<table class="table table-striped table-bordered table-condensed" >' +
                                    '<thead>' +
                                        '<tr>' +
                                            '<th>C&oacute;digo</th>'+
                                            '<th>Remito</th>'+
                                        '</tr>'+
                                    '</thead>'+
                                    '<tbody>';
                        for(var i = 0;i<data.length;i++){
                            var d = data[i];
                            html += '<tr>';
                            html += wrapTag('td',d.codigo);
                            html += wrapTag('td',d.numero);
                            html += '</tr>';
                        }
                        html +=  '</tbody>' + '</table>';


                    var infowindowContent = '<div id="content" style="width:250px">' +
                    '<div id="siteNotice"> </div>'+
                    '<h3 id="firstHeading" class="firstHeading">Activos en el site</h3>' +
                    '<div id="bodyContent">' +
                    html +
                    '</div>' +
                    '</div>';
                    var infowindow = new google.maps.InfoWindow({
                            content: infowindowContent
                    });
                     infowindow.open(map, marker);
                 }
               }
       });
    }


    </script>
<!--    <script async defer
        src="https://maps.googleapis.com/maps/api/js?signed_in=true&callback=initMap">
    </script>-->
<%@include file="tpl_footer.jsp"%>

</body>

</html>
