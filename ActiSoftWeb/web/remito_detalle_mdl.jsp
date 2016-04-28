<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<div id="mdlRemito" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content modal-lg">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Buscar remito</h4>
      </div>
      <div class="modal-body">
          <div class="row">
            <div class="col-lg-4">
             <div clas="form-group">
                  <label for="numero_search">N&uacute;mero </label>
                  <span class="input-group">                                                                                            
                      <input type="text" class="form-control" name="numero_search" id="numero_search" size="20" value="">
                      <span class="input-group-addon" id="btnBuscar" ><span class="fa fa-search fa-fw"></span></span>
                  </span>
              </div>
            </div>              
        </div>
          <hr>
          <div class="dataTable_wrapper">
                <table class="table table-striped table-bordered table-hover" id="tblRemitoDetalle">
                    <thead>
                       <tr>
                            <th>C&oacute;digo</th>
                            <th>Posici&oacute;n</th>
                            <th>Descripci&oacute;n</th>
                            <th>Cantidad</th>
                            <th><input class="checkbox" type="checkbox" value="0" id="selTodos"></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnSelActivosRemito">Seleccionar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    $(document).ready(function(){
       $('#btnBuscar').click(buscar);
       $('#btnSelActivosRemito').click(selActivos);
       $('#selTodos').change(function(e){
        selTodos('input.chkSelActivo',$(this).prop('checked'));
       });
    });
     function buscar(){
          var $numero = $('#numero_search');
          if($numero === undefined || $numero.val()==='') {
              bootbox.alert("Ingrese el n&uacute;mero de remito a buscar");          
              return;
        };
        var data = {numero:$numero.val()};
        $.ajax({
               url: '<%= PathCfg.REMITO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){                    
                    $('#tblRemitoDetalle').find('tbody').html("");
               },
               success: function(result) {      
                   $('#selTodos').prop('checked',false);
                   if(result.Result === "OK") {
                       if(result.TotalRecordCount ===0){
                           bootbox.alert("No se encontr&oacute; el remito");
                           return;
                       }else{
                           var d = result.Records[0];
                           if(d.id_tipo_remito !== <%=OptionsCfg.REMITO_ENTREGA%>){
                            bootbox.alert("No es un remito de entrega. Solo pueden realizarse devoluciones sobre remitos de entrega");
                            return;
                           }
                           
                           if(d.tiene_devolucion){
                            bootbox.alert("Ya existe remito de devoluci&oacute;n para este remito.");
                            return;
                           }
                           loadDataRemitoDetalle({id_remito:d.id});
                       } 
                   }
               }
           });
    }
    
    
    function loadDataRemitoDetalle(data){    
        var $tabla = $('#tblRemitoDetalle');
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
                       $tabla.find('tbody').html(createTableRemitoDetalle(result.Records));    
                   }
               }
        });
    }
    function createTableRemitoDetalle(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];           
            html += wrapTag('td',d.codigo, '');
            html += wrapTag('td',d.posicion, '');            
            html += wrapTag('td',d.desc_larga, '');            
            html += wrapTag('td',d.cantidad, '');
            if (d.id_referencia === 0){
               var htmlSelect = "<input type='checkbox' class='chkSelActivo' data-pos='" + d.posicion + "' data-index='"+ d.id +"' data-codigo='"+d.codigo+"' data-descripcion='" + d.desc_larga + "' data-cant='"+d.cantidad+"'" ;
        }  else htmlSelect = "";
            html +=wrapTag('td',htmlSelect ,'');
           html +="</tr>";
       }
       return html;
    }
   
     </script>