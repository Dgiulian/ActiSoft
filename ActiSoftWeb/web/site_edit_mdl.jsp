<%@page import="utils.PathCfg"%>
<!--<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA"></script>
<script src="bower_components/mapplace/maplace-0.1.3.js"></script>-->

<div id="mdlSiteEdit" class="modal fade" role="dialog">
  <div class="modal-dialog">  
    <div class="modal-content">     <!-- Modal content-->     

            <input type="hidden" name="id" id="id"   value="">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">Crear Site</h4>
            </div>
       <div class="modal-body">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                           <li class="active"><a href="#tab1" data-toggle="tab">Datos b&aacute;sicos</a></li>
                           <li><a href="#tab2" data-toggle="tab">Contacto</a></li>
                           <!--<li><a href="#tab3" data-toggle="tab"></a></li>-->
                           <!--<li><a href="#tab4" data-toggle="tab">Datos financieros</a></li>-->
                           <li><a href="#tab3" data-toggle="tab">Observaciones</a></li>
                           <li><a href="#tab4" data-toggle="tab">Mapa</a></li>
                       </ul>
<!--                      <div class="panel-heading"> Datos b&aacute;sicos del site </div>            -->
                      <div class="panel-body">
                          <div class="tab-content ">
                            <div class="tab-pane active" id="tab1">
                        <div class="row">
                          <div class="col-lg-12 " >
                              <h3 class="">Site</h3>
                              <fieldset>
                                  <input type="hidden" name="id_cliente" value="">                                

                                  <div class="col-lg-12 " >
                                      <div class="form-group">
                                          <label for="nombre">Nombre</label>
                                          <input class="form-control" name="nombre" id="nombre"   value="">
                                      </div>
                                  </div>
                                  <div class="col-lg-3 " >
                                      <div class="form-group">
                                          <label for="area">Area</label>
                                          <input class="form-control" name="area" id="area_mdl" value="">
                                      </div>
                                  </div>
                                  <!--<div class="col-lg-12 ">-->

                                  <div class="col-lg-3 " >
                                       <div class="form-group " >
                                            <label for="pozo">Pozo</label>
                                            <input class="form-control" name="pozo" id="pozo_mdl" value="">
                                        </div>
                                   </div>
                                  <div class="col-lg-3 " >
                                       <div class="form-group " >
                                            <label for="equipo">Equipo</label>
                                            <input class="form-control" name="equipo" id="equipo_mdl" value="">
                                        </div>
                                  </div>
                                  <div class="row" >
                                      <div class="form-group">
                                          <label for="id_estado">Activo</label>                                        
                                          <input class="" type="checkbox" readonly name="id_estado" id="id_estado" value="1" checked  >
                                      </div>
                                  </div>
                                  <div class="col-lg-6 " >
                                      <div class="form-group">
                                          <label for="latitud">Latitud</label>
                                          <input class="form-control" type="number"  name="latitud" id="latitud" value="">
                                      </div>
                                  </div>

                                  <div class="col-lg-6 " >
                                      <div class="form-group">
                                          <label for="latitud">Longitud</label>
                                          <input class="form-control" type="number"  name="longitud" id="longitud" value="">
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
                                    <input class="form-control" type="text" name="telefono" id="telefono" value="">
                                </div>
                                <div class="form-group">
                                    <label for="encargado">Encargado</label>
                                    <input class="form-control" type="text" name="encargado" id="encargado" value="">
                                </div>

                                <div class="form-group">
                                    <label for="horario">Horario</label>
                                    <input class="form-control" type="text" name="horario" id="horario" value="">
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab3">
                            <div class="col-lg-6 " >
                                <div class="form-group " >
                                    <label for="fecha_Creacion">Fecha Alta</label>
                                    <input type="text" class="form-control" disabled name="fecha_creacion" id="fecha_creacion" value="">
                                </div>
                                <div class="form-group " >
                                    <label for="observaciones">Observaciones</label>
                                    <textarea class="form-control" name="observaciones" id="observaciones"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab4">
                            <div id="gmap" class="Flexible-container"></div>
                        </div>
                    </div> <!-- /tab-content -->                       
                        <!-- ./row -->
                        </div>
                        </div> <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    
                </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btnSiteEdit">Crear</button>
        <button type="cancel" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
       
    </div>
  </div>
</div>
<script>
$(document).ready(function() {
    $('.date-picker').datepicker({
                   language: 'es'
   }); 
   $('.date-picker').mask('99/99/9999');
  
   $('#btnSiteEdit').on('shown.bs.modal',function(){
    
   
       $('#fecha_diario').val("");
       $('#numero_diario').val(""); 
   });
   $('#mdlSiteEdit').on('show.bs.modal',function(){
     
   });
   $('#btnSiteEdit').click(function(){
       var nombre  = $('#nombre').val();
       var area  = $('#area_mdl').val();
       var pozo  = $('#pozo_mdl').val();
       var equipo  = $('#equipo_mdl').val();
       var id_estado  = $('#id_estado').val();
       var latitud  = $('#latitud').val();
       var longitud  = $('#longitud').val();
       var telefono  = $('#telefono').val();
       var encargado  = $('#encargado').val();
       var horario  = $('#horario').val();
       var observaciones  = $('#observaciones').val();
       var id_cliente = $('#idCliente').val();      
       data = { id_cliente: id_cliente,
                nombre : nombre,
                area : area,
                pozo : pozo,
                equipo : equipo,
                id_estado : id_estado,
                latitud : latitud,
                longitud : longitud,
                telefono : telefono,
                encargado : encargado,
                horario : horario,
                observaciones : observaciones,
                tipo:'modal',
                id:0,
            };

      $('#mdlSiteEdit').modal('hide');
      $.ajax({
            url:'<%= PathCfg.SITE_EDIT%>',
            data: data,
            method:'POST',
            dataType:'json',
            success:function(result){
                if(result.Result === "OK") {
                    //window.location.reload();
                     console.log(result);
                     $('#area').val(result.Record.area);
                     $('#pozo').val(result.Record.pozo);
                     $('#equipo').val(result.Record.equipo);
                     $('#id_site').val(result.Record.id);
                  // location.href='<%= PathCfg.REMITO_PRINT %>?id=' + data.Record.id;
                  
                } else if (result.Message) bootbox.alert(result.Message);
            }
       });
   });
    var ApyKey = "AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA";

            var lat = $('#latitud').val();
            var lon = $('#longitud').val();

            //$('#btnSubmit').click(submitForm);
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
</script>