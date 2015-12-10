<%@page import="utils.PathCfg"%>
<div id="mdlContratoView" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Vista r&aacute;pida de contrato</h4>
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
              <table class="table table-bordered table-condensed" id="tblContratoView">
                  <thead>
                      <tr>
                        <th>Posici&oacute;n</th>
                        <th>Descripci&oacute;n</th>                        
                        <th>Monto</th>
                        <th>&percnt;</th>                        
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
    function loadDataContratoView(data){    
        var $tabla = $('#tblContratoView');
        $.ajax({
               url: '<%= PathCfg.CONTRATO_DETALLE_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableContratoView(data.Records));                       
                   } else {
                       bootbox.alert(data.Message);
                       $('#mdlContratoView').modal('hide');
                   }
               }
        });
    }
    function createTableContratoView(data){    
        console.log(data);
        var html = "";
        var objPos = {};
        for(var i = 0;i< data.length;i++){
           
           html +="<tr class=''>";
           d = data[i];   
           // Filtramos para que solo aparezca una vez cáda posición
           if( objPos["p_" + d.posicion] !== undefined)   continue;
           objPos["p_" + d.posicion] = d.posicion;
//           wrapTag('td',d.id,'');
          html += wrapTag('td',d.posicion,'');
          html += wrapTag('td',d.descripcion,'');          
          var divisa = d.id_divisa==0?"U$s":"$";
          html += wrapTag('td',divisa + " " + d.precio,'');
          html += wrapTag('td',d.porcentaje,'');           
           html +="</tr>";
       }
       return html;
    }
   
     </script>