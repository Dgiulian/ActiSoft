<%@page import="java.util.HashMap"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<%@page import="java.util.ArrayList"%>
<%@page import="transaccion.TRubro"%>
<%   
    HashMap<String,String> mapRubro = new HashMap<String,String>();
    HashMap<String,String> mapSubrubro = new HashMap<String,String>();
    
    mapRubro.put("id_estado","1");
    List<Rubro> lstRubros = new TRubro().getListFiltro(mapRubro);    
%>
<div id="mdlSubrubro" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Busqueda de subrubros</h4>
      </div>
      <div class="modal-body">
          <div class="row">
          <div class="col-lg-12">
              <div class="form-group">
                  <label for="id_rubro">Rubro</label>
                  <select class="form-control" name="id_rubro" id="id_rubro">
                      
                      <% for(Rubro r:lstRubros){
                          String selected="";
                        %>
                        <option value="<%=r.getId()%>" <%= selected %>><%=r.getDescripcion()%></option>
                      <%}%>
                      <option value="0">Todos</option>
                  </select>
              </div>
          </div>
         
                      <hr>      
        <div class="row">
          <div class="col-lg-12 dataTable_wrapper">
                <table class="table table-striped table-bordered table-hover" id="tblSubrubro">
                    <thead>
                        <tr>
                            <!--<th>Id</th>-->
                            <th>C&oacute;digo</th>                                
                            <th>Descripci&oacute;n</th>                            
                            <th><input class="checkbox" type="checkbox" value="0" id="selTodos"></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            </div>
        </div>
      <!--</div>-->
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="btnSelSubrubros">Seleccionar</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        </div>
    </div>
  </div>
</div>
<script>
    function selRubroChange(){
        $value= $(this).val();   
        loadDataSubrubro({id_rubro:$value,id_estado:1});
    }
    function loadDataSubrubro(data){    
        var $tabla = $('#tblSubrubro');    
        $.ajax({
               url: '<%= PathCfg.SUBRUBRO_LIST %>',
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
                       
                       $tabla.find('tbody').html(createTableSubrubro(data.Records));
                       $('.btnSelSubrubro').click(function(){
                           var id = $(this).data('index');
                           var codigo = $(this).data('codigo');
                           if($invoker !=undefined){
                               $invoker.parent().find('input').val(codigo);
                           }                           
                           $('#mdlSubrubro').modal('hide');
                           
                       });
                   }
               }
        });
    }
    function createTableSubrubro(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
//           html +=wrapTag('td',d.id,'');
           html +=wrapTag('td',d.codigo,'');
           html +=wrapTag('td',d.descripcion,'');           
          //var htmlSelect = "<span data-index='"+ d.id +"' data-codigo='"+d.codigo+"' class='btn btn-xs btn-circle  btn-warning btnSelSubrubro'><span class='fa fa-plus fw'></span></span> ";
          var htmlSelect = "<input type='checkbox' class='chkSelSubrubro' data-index='"+ d.id +"' data-codigo='"+d.codigo + "' data-descripcion='" + d.descripcion + "' >"  ;
           html +=wrapTag('td',htmlSelect ,'');
           html +="</tr>";
       }
       return html;
    }
   
     </script>