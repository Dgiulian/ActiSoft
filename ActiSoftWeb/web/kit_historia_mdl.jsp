<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<div id="mdlKitHistoria" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content modal-lg">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <!--<h4 class="modal-title">Historia del activo</h4>-->
        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab">Historial</a></li>
<!--                    <li><a href="#tab2" data-toggle="tab">Certificados</a></li>
                    <li><a href="#tab3" data-toggle="tab">Compras</a></li>
                    <li><a href="#tab4" data-toggle="tab">Correctivos</a></li>                           -->
            </ul>
      </div>
      <div class="modal-body">        
        <div  class="tab-content">
         <div class="tab-pane active" id="tab1">          
                 
            <div class="dataTable_wrapper">
                  <table class="table table-striped table-bordered table-hover" id="tblKitHistoria">
                      <thead>
                          <tr>
                             <th>Activo</th>
                             <th>Fecha</th>
                             <th>Accion</th>
                          </tr>
                      </thead>
                      <tbody>
                      </tbody>
                  </table>
              </div>
            </div> <!-- tab1 -->
            <div class="tab-pane" id="tab2">
                <div class="dataTable_wrapper">                    
                </div>
            </div>
            <div class="tab-pane" id="tab3">
                <div class="dataTable_wrapper">
                                
                </div>
            </div>            
            </div><!-- tab-content -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>
<script>
    $('#mdlKitHistoria').on('show.bs.modal',function(e){
          $invoker = $(e.relatedTarget);
          var id_kit = $invoker.data('index');
          var fecha_alta = $invoker.data('fecha_alta');
          
          console.log($invoker);
          var data = {id_kit:id_kit};
          loadDataKitHistoria(data);          
          
       });
       
    function loadDataKitHistoria(data){    
        var $tabla = $('#tblKitHistoria');
        $.ajax({
               url: '<%= PathCfg.KIT_HISTORIA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   $('#selTodos').prop('checked',false);
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableKitHist(data.Records));
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [1, "asc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 1 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });        
                   }
               }
        });
    }
    function createTableKitHist(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           var d = data[i];
//           console.log("D",d);
           html += wrapTag('td',d.activo, '');
            html += wrapTag('td',convertirFechayHora(d.fecha), '');
            html += wrapTag('td',d.accion, '');            
           html +="</tr>";
       }
       return html;
    }    
</script>