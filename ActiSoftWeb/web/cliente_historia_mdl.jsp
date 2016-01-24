<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<div id="mdlClienteHistoria" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content modal-lg">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Historia del cliente</h4>
      </div>
      <div class="modal-body">
          <div class="dataTable_wrapper">
                <table class="table table-striped table-bordered table-hover" id="tblActivoHistoria">
                    <thead>
                        <tr>
                            <th>C&oacute;digo</th>
                           <th>Descripci&oacute;n</th>
                           <th>Tipo</th>
                           <th>Estado</th>
                           <th>Cliente</th>
                           <th>Contrato</th>
                           <th>Fecha</th>
                           <th>Punto Venta</th>
                           <th>Numero</th>
                          <th>Cantidad</th>                            
                        </tr>
                    </thead>
                    <tbody>
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
    $(document).ready(function(){
        $('#mdlClienteHistoria').on('show.bs.modal',function(){
            var $id_cliente = $('#id');
            if ($id_cliente!==null && $id_cliente.val()!=="") {
                loadDataClienteHistoria({id_cliente:$id_cliente.val()});
            } else {
                
            }        
        });
    });
    
    function loadDataClienteHistoria(data){    
        var $tabla = $('#tblActivoHistoria');
        $.ajax({
               url: '<%= PathCfg.ACTIVO_HISTORIA_LIST %>',
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
                       $tabla.find('tbody').html(createTableClienteHistoria(data.Records));    
                   }
               }
        });
    }
    function createTableClienteHistoria(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
           
           html += wrapTag('td',d.codigo, '');
           html += wrapTag('td',d.desc_larga, '');
           html += wrapTag('td',d.tipo_remito, '');
            html += wrapTag('td',d.estado, '');
            html += wrapTag('td',d.cliente, '');
            html += wrapTag('td',d.contrato, '');
            
            html += wrapTag('td',convertirFecha(d.fecha), '');
            html += wrapTag('td',d.punto_venta, '');
            html += wrapTag('td',d.numero, '');
            html += wrapTag('td',d.cantidad, '');
           html +="</tr>";
       }
       return html;
    }
   
     </script>