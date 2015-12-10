<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
 <%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>

<div id="mdlRemito" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Remito de devoluci&oacute;n</h4>
      </div>
      <div class="modal-body">
          <form action="<%= PathCfg.REMITO_DEV %>" method="POST" >
          <input type='hidden' name="id" id="id" value="">;
          <div class="row">
              <div class="col-lg-6">
                    <div class="form-group">
                        <label class="" for="numero">N&uacute;mero de remito</label>
                        <input class="form-control" type="text" name="numero">
                    </div>
                </div>
          </div>
          <div class="dataTable_wrapper">
                <!--<h3>Activos <span class="btn btn-default" id="btnAgregar"> Agregar</span></h3>-->
                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblRemitoDev">
                    <thead>
                        <tr>
                            <th>Pos. Contrato</th>
                            <th>C&oacute;digo</th>
                            <th>Descripci&oacute;n</th>
                            <th>Cantidad</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody class="table-content">
                        <%--<%@include file="remito_test.jsp" %>--%>
                    </tbody>
<!--                    <tfoot>
                        <tr>
                            <td colspan="4"></td>
                        </tr>
                    </tfoot>-->
                </table>
            </div>
            </form>
      </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-default" name="btnParcial" id="btnParcial">Parcial</button>
        <button type="button" class="btn btn-default" name="btnTotal" id="btnTotal">Total</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    function loadDataRemito(data){    
        var $tabla = $('#tblRemitoDev');
        $.ajax({
               url: '<%= PathCfg.REMITO_DET_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(result) {
                   $('#selTodos').prop('checked',false);
                   if(result.Result === "OK") {
                       $tabla.find('tbody').html(createTableActivo(result.Records));   
                       $('#id').val(data.id_remito);
                       $('.btnDelActivo').click(deleteActivo);
                   }
               }
        });
    }
    function createTableActivo(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
            d = data[i];
            html +="<tr class=''>";
            html+='<input type="hidden" name="id" value="'+ d.id +'"> ';
            html += wrapTag('td',d.posicion,'');
            html += wrapTag('td',d.codigo,'');
            html +=wrapTag('td',d.desc_larga,'');
            html += wrapTag('td',d.cantidad,'');
            var htmlSelect = '<span class="btn btn-xs btn-circle btn-danger btnDelActivo data-index="'+ d.id + '" data-codigo="'+d.codigo+'"> <span class="fa fa-minus fw"></span></span>' ;        
            html +=wrapTag('td',htmlSelect ,'');

               html +="</tr>";
       }
       
       return html;
    }
            function deleteActivo(){
//          bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
//             if(result) {            
                var $tr = $(this).parent().parent();
                $tr.remove();         
//                }
//            });
        }
   
     </script>