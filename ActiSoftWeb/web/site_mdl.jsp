<%@page import="utils.PathCfg"%>
<div id="mdlSite" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Busqueda de Site</h4>
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
              <table class="table table-bordered table-condensed" id="tblSite">
                  <thead>
                      <tr>
                        <th>Nombre</th>
                        <th>Area</th>
                        <th>Pozo</th>
                        <th>Equipo</th>
                        <th></th>                            
                      </tr>
                  </thead>
                  <tbody class="table-content">
                      
                  </tbody>
              </table>
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    function loadDataSite(data){    
        var $tabla = $('#tblSite');    
        $.ajax({
               url: '<%= PathCfg.SITE_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableSite(data.Records));                        
                       $('.btnSelSite').click(function(){
                           var id = $(this).data('index');
                           var area = $(this).data('area');
                           var pozo = $(this).data('pozo');
                           var equipo = $(this).data('equipo');
                           console.log(equipo);
                           $('#id_site').val(id);
                           $('#area').val(area);
                           $('#pozo').val(pozo);
                           $('#equipo').val(equipo);                           
                           $('#mdlSite').modal('hide');                           
                       });
                   }
               }
        });
    }
    function createTableSite(data){    
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];   
//           wrapTag('td',d.id,'');
          html += wrapTag('td',d.nombre,'');
          html += wrapTag('td',d.area,'');
          html += wrapTag('td',d.pozo,'');
          html += wrapTag('td',d.equipo,'');          
          var htmlSelect = "<span data-index='"+ d.id +"' data-area='"+ d.area +"' data-pozo='"+ d.pozo +"' data-equipo='"+ d.equipo +"' class='btn btn-xs btn-circle  btn-warning btnSelSite'><span class='fa fa-plus fw'></span></span> ";            
          html +=wrapTag('td',htmlSelect ,'');
            
           html +="</tr>";
       }
       return html;
    }
   
     </script>