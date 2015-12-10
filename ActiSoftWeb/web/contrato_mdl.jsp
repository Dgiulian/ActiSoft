<%@page import="utils.PathCfg"%>
<div id="mdlContrato" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Busqueda de contratos</h4>
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
              <table class="table table-bordered table-condensed" id="tblContrato">
                  <thead>
                      <tr>
                        <th>Numero</th>
                        <th>Cliente</th>
                        <th>Fecha</th>
                        <th>Inicio</th>
                        <th>Fin</th>
                        <th>Monto</th>
                        <th>&percnt;</th>
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
    function loadDataContrato(data){    
        var $tabla = $('#tblContrato');
    
        $.ajax({
               url: '<%= PathCfg.CONTRATO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableContrato(data.Records));                        
                       $('.btnSelContrato').click(function(){
                           var id = $(this).data('index');
                           var id_cliente = $(this).data('cliente');
                           var contrato = $(this).data('contrato');
                           
                           $('#id_contrato').val(id);
                           $('#contrato').val(contrato);
                           $('#mdlContrato').modal('hide');
                           buscarCliente({id: id_cliente });
                           $('#contrato').trigger('change');
                       });
                   } else {
                      bootbox.alert(data.Message);
                   }
               }
        });
    }
    function createTableContrato(data){    
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];   
//           wrapTag('td',d.id,'');
          html += wrapTag('td',d.numero,'');
          html += wrapTag('td',d.nombre_cliente,'');
          html += wrapTag('td',d.fecha,'');
          html += wrapTag('td',d.fecha_inicio,'');
          html += wrapTag('td',d.fecha_fin,'');
          var divisa = d.id_divisa==0?"U$s":"$";
          html += wrapTag('td',divisa + d.monto,'');
          html += wrapTag('td',d.porcentaje,'');
          var htmlSelect = "<span data-index='"+ d.id +"' data-contrato='"+ d.numero +"' data-cliente='"+ d.id_cliente +"'class='btn btn-xs btn-circle  btn-warning btnSelContrato'><span class='fa fa-plus fw'></span></span> ";            
          html +=wrapTag('td',htmlSelect ,'');
            
           html +="</tr>";
       }
       return html;
    }
   
     </script>