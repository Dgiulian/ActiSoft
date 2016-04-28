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
<!--          <div class="row">
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
          <hr>-->
          <div class="dataTable_wrapper">
                <table class="table table-striped table-bordered table-hover" id="tblRemito">
                        <colgroup>
                            <col span="1" style="width: 10%; text-align: right;"> <!-- Numero -->
                            <col span="1" style="width: 13%;"> <!-- Tipo -->
                            <col span="1" style="width: 10%;"> <!-- Fecha -->
                            <col span="1" style="text-align: center"> <!-- Cliente-->
                            <col span="1" style="width: 12%;"> <!-- Contrato -->
                            <col span="1" style="width: 11%;text-align: center">
                            <col span="1" style="width: 3%;text-align: center">
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
        loadDataRemito(data);
    }
    
    
    function loadDataRemito(data){    
        var $tabla = $('#tblRemito');
        $.ajax({
               url: '<%= PathCfg.REMITO_LIST %>',
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
                       $tabla.find('tbody').html(createTableRemito(result.Records));
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 2, "desc" ,1,"desc"],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 2 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });    
                   }
               }
        });
    }
    function createTableRemito(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
//            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.numero,'');
            html += wrapTag('td',d.tipo_remito ,'');
            html += wrapTag('td',convertirFecha(d.fecha),'');
            html += wrapTag('td',d.cliente,'');
            html += wrapTag('td',d.contrato,'');
            html += wrapTag('td',d.facturado!==0?"Si":"No",'');
            html += wrapTag('td',d.estado,'');
             var htmlSelect = "<input type='checkbox' class='chkSelActivo' data-index='" + d.id + "' data-pos='" + d.posicion + "' data-index='"+ d.id +"' data-codigo='"+d.codigo+"' data-descripcion='" + d.desc_larga + "' data-cant='"+d.cantidad+"'" ;
            html +=wrapTag('td',htmlSelect ,'');

           html +="</tr>";
       }
       return html;
    }
   
     </script>