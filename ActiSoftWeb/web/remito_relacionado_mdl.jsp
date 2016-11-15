<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<div id="mdlRelacionado" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Remitos relacionados</h4>
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
             <table class="table table-striped table-bordered table-hover table-responsive" id="tblRelacionado">
                <colgroup>
                    <col span="1" style="width: 10%; text-align: right;"> <!-- Numero -->
                    <col span="1" style="width: 10%;"> <!-- Tipo -->
                    <col span="1" style="width: 9%;"> <!-- Fecha -->
                    <col span="1" style="width: 10%;text-align: center"> <!-- Cliente-->
                    <col span="1" style="width: 10%;"> <!-- Contrato -->
                    <col span="1" style="width: 12%;"> <!-- Equipo -->
                    <col span="1" style="width: 9%;text-align: center">
                    <col span="1" style="width: 8%;text-align: center">
                    <!--<col span="1" style="width: 24%;text-align: center">-->
                 </colgroup>
                <thead>
                    <tr>
                        <!--<th>Id</th>-->
                        <th>N&uacute;mero</th>
                        <th>Tipo remito</th>
                        <th>Fecha</th>
                        <th>Cliente</th>
                        <th>Contrato</th>
                        <th>Equipo</th>
                        <th>Preticket</th>
                        <th>Estado</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
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
    function loadDataRelacionado(data){    
        console.log(data);
        var $tabla = $('#tblRelacionado');
        $.ajax({
               url: '<%= PathCfg.REMITO_RELACIONADO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableRelacionado(data.Records));
                   }
               }
        });
    }
    function createTableRelacionado(data){    
       
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
//            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.numero,'');
            
            var tipoRemitoLnk = d.tipo_remito;            
            if (d.id_referencia !== 0) {
                tipoRemitoLnk = '<a href="<%= PathCfg.REMITO_VIEW %>?id='+ d.id +'&ref='+d.id_referencia+'">'+ d.tipo_remito + '</a>';
            }
            html += wrapTag('td',tipoRemitoLnk,'');
            html += wrapTag('td',convertirFecha(d.fecha),'');
            var clienteLnk  = '<a href="<%= PathCfg.CLIENTE_EDIT %>?id='+d.id_cliente+ '">'+ d.cliente + '</a>';
            var contratoLnk = '<a href="<%= PathCfg.CONTRATO_EDIT %>?id='+d.id_contrato+ '">'+ d.contrato + '</a>';
            html += wrapTag('td',clienteLnk,'');
            html += wrapTag('td',contratoLnk,'');
            html += wrapTag('td',d.equipo,'');
            html += wrapTag('td',d.facturado!==0?"Si":"No",'');
            html += wrapTag('td',d.estado,'');
            var htmlEdit = "<a href='<%= PathCfg.REMITO_VIEW%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-info'><span class='glyphicon glyphicon-sunglasses fw'></span></a> ";
            html +='<td  style="width:120px">' + htmlEdit + '</td>';
           html +="</tr>";
       }
       if(data.length==0){
           html="<tr><td colspan='9'><center>No se encontr&oacute; ning&uacute;n remito relacionado</center> </td></tr>"
       }
       return html;
    
    }
   
     </script>