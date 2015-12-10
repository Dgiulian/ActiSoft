<%@page import="utils.PathCfg"%>
<div id="mdlCliente" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Busqueda de cliente</h4>
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
              <table class="table table-bordered table-condensed" id="tblCliente">
                  <thead>
                      <tr>
                        <th>Id</th>                              
                        <th>Nombre</th>
                        <th>CUIT / DNI</th>
                        <th>Direcci&oacute;n</th>
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
      </div>
    </div>
  </div>
</div>

<script>
    function loadDataCliente(data){    
        var $tabla = $('#tblCliente');
        $.ajax({
               url: '<%= PathCfg.CLIENTE_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCliente(data.Records));                        
                       $('.btnSelCliente').click(function(){
                           var id = $(this).data('index');
                            buscarCliente({id:id});                           
                           
                           $('#idCliente').val(id);
                           $('#mdlCliente').modal('hide');
                       });
                   }
               }
        });
    }
    function createTableCliente(data){    
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];           
            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.nombre,'');
            if (d.cuit!='')
                html += wrapTag('td',d.cuit,'');
            else html += wrapTag('td',d.dni,'');
            html += wrapTag('td',d.direccion_fisica,'');

            var htmlSelect = "<span data-index='"+ d.id +"' class='btn btn-xs btn-circle  btn-warning btnSelCliente'><span class='fa fa-plus fw'></span></span> ";            
            html +=wrapTag('td',htmlSelect ,'');
            
           html +="</tr>";
       }
       return html;
    }
   
     </script>