<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<%   
    List<Rubro> lstRubros = new TRubro().getList();
    List<Subrubro> lstSubrubros = new TSubrubro().getByRubroId(1);
%>
<div id="mdlActivo" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content modal-lg">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Busqueda de activo para el contrato</h4>
      </div>
      <div class="modal-body">
          <div class="row">
              <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="id_rubro">Rubro</label>
                        <select class="form-control" name="id_rubro" id="id_rubro">
                            <% for(Rubro r:lstRubros){%>
                              <option value="<%=r.getId()%>"><%=StringEscapeUtils.escapeHtml4(r.getDescripcion())%></option>
                            <%}%>
                            <option value="0">Todos</option>
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="id_rubro">Subrubro</label>
                        
                        <select class="form-control" name="id_subrubro" id="id_subrubro"> 
                            <option value="0">Todos</option>
                             <% for(Subrubro sr:lstSubrubros){%>
                              <option value="<%=sr.getId()%>"><%=StringEscapeUtils.escapeHtml4(sr.getDescripcion())%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
          </div>
          <div class="dataTable_wrapper">
                <table class="table table-striped table-bordered table-hover" id="tblActivo">
                    <thead>
                        <tr>
                            <th>Posici&oacute;n</th>
                            <th>C&oacute;digo</th>                                
                            <!--<th>Rubro</th>-->
                            <!--<th>Subrubro</th>-->
                            <th>Descripci&oacute;n</th>
                            <th>Stock</th>
                            <th><input class="checkbox" type="checkbox" value="0" id="selTodos"></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnSelActivos">Seleccionar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    function loadDataActivo(data){    
        
        var $tabla = $('#tblActivo');  
        $tabla.find('tbody').html("")
        $tabla.find('tfoot').html("");
        $.ajax({
               url: '<%= PathCfg.ACTIVO_CONTRATO_LIST %>',
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
                      if(data.TotalRecordCount>0) {                            
                            $tabla.find('tbody').html(createTableActivo(data.Records));    

                            $('.btnSelActivo').click(function(){
                                var id = $(this).data('index');
                                var codigo = $(this).data('codigo');
                                if($invoker !=undefined){
                                    $invoker.parent().find('input').val(codigo);
                                }                           
                                $('#mdlActivo').modal('hide');

                            });
                    } else {                        
                        $tabla.find('tfoot').html("<tr><th colspan='5'><center>No se encontr&oacute; ning&uacute;n activo</center></th></tr>");
                    }
                   }
               }
        });
    }
    function createTableActivo(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
           html += wrapTag('td',d.c_posicion,'');
//           html += wrapTag('td',d.id_rubro,'');
//           html += wrapTag('td',d.id_subrubro,'');
           html +=wrapTag('td',d.codigo,'');
           html +=wrapTag('td',d.desc_larga,'');
           var divisa = d.divisa == 0?"$":"U$s";           
           html +=wrapTag('td',d.stock,'');
           if (d.id_estado === <%= OptionsCfg.ACTIVO_ESTADO_DISPONIBLE %>){
               var htmlSelect = "<input type='checkbox' class='chkSelActivo' data-pos='" + d.c_posicion + "' data-index='"+ d.id +"' data-codigo='"+d.codigo+"' data-descripcion='" + d.desc_larga + "'" ;
//            var htmlSelect = "<span data-index='"+ d.id +"' data-codigo='"+d.codigo+"' class='btn btn-xs btn-circle  btn-warning btnSelActivo'><span class='fa fa-plus fw'></span></span> ";
        }  else htmlSelect = "";
            html +=wrapTag('td',htmlSelect ,'');

           html +="</tr>";
       }
       return html;
    }
   
     </script>