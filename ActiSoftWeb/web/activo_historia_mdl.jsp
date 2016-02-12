<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<div id="mdlActivoHistoria" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content modal-lg">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <!--<h4 class="modal-title">Historia del activo</h4>-->
        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab">Historial</a></li>
                    <li><a href="#tab2" data-toggle="tab">Certificados</a></li>
                    <li><a href="#tab3" data-toggle="tab">Compras</a></li>
                    <li><a href="#tab4" data-toggle="tab">Correctivos</a></li>                           
            </ul>
      </div>
      <div class="modal-body">        
        <div  class="tab-content">
         <div class="tab-pane active" id="tab1">
            <div class="col-lg-3" >
                <div class="form-group">
                    <label for="fecha_alta" >Fecha de Alta</label>
                    <input name="fecha_alta" id="fecha_alta" class="form-control uppercase" value=''>
                </div>
            </div>
                 
            <div class="dataTable_wrapper">
                  <table class="table table-striped table-bordered table-hover" id="tblActivoHistoria">
                      <thead>
                          <tr>
                             <th>Tipo</th>
                             <th>Estado</th>
                             <th>Cliente</th>
                             <th>Contrato</th>
                             <th>Fecha</th>
                             <th>Venta</th>
                             <th>Remito</th>
                            <th>Cantidad</th>                            
  <!--                          <th>Remito</th>                            
                            <th>Venta</th>                            -->
                          </tr>
                      </thead>
                      <tbody>
                      </tbody>
                  </table>
              </div>
            </div> <!-- tab1 -->
            <div class="tab-pane" id="tab2">
                <div class="dataTable_wrapper">
                    <table class="table table-striped table-bordered table-condensed" id="tblCertificado">
                        <thead>
                            <tr>
                                <!--<th>Id</th>-->
                                <th>Fecha</th>
                                <th>Certificado</th>
                                <th>Precinto</th>
                                <th>Resultado</th>
                                <th>Externo</th>
                                <th>Observaciones</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tab-pane" id="tab3">
                <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblCompra">
                                    <thead>
                                        <tr>
                                            <th>Fecha</th>
                                            <th>Cantidad</th>
                                            <th>Divisa</th>
                                            <th>Precio</th>                                            
                                            <th>Total</th>
                                            <th>Proveedor</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
            </div>
            <div class="tab-pane" id="tab4">
                <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-condensed" id="tblCorrectivo">
                            <thead>
                                <tr>
                                    <!--<th>Id</th>-->
                                    <th>Fecha</th>
                                    <th>Actividad</th>
                                    <th>Resultado</th>                                            
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
            </div>
            </div><!-- tab-content -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    $('#mdlActivoHistoria').on('show.bs.modal',function(e){
          $invoker = $(e.relatedTarget);
          var id_activo = $invoker.data('index');
          var fecha_alta = $invoker.data('fecha_alta');
          
          console.log($invoker);
          var data = {id_activo:id_activo};
          loadDataActivoHistoria(data);
          loadDataCertificado(data);
          loadDataCompra(data);
          loadDataCorrectivo(data);
          if(fecha_alta!==undefined && fecha_alta!=="") $('#fecha_alta').val(convertirFecha(fecha_alta));
       });
       
    function loadDataActivoHistoria(data){    
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
                       $tabla.find('tbody').html(createTableActivoHist(data.Records));
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 4, "desc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 4 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });        
                   }
               }
        });
    }
    function createTableActivoHist(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           var d = data[i];
//           console.log("D",d);
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
    /********** CERTIFICADO */
    function loadDataCertificado(data){
         var $tabla = $('#tblCertificado');


        $.ajax({
               url: '<%= PathCfg.CERTIFICADO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCertificado(data.Records));
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 0, "desc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 0 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });      
                   }
               }
           });
    }

    function createTableCertificado(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',convertirFecha(d.fecha),'');
            html += wrapTag('td',d.codigo,'');
            html += wrapTag('td',d.precinto,'');
            html += wrapTag('td',d.resultado,'');
            var externo = (d.externo)?"Si":"No";
            html += wrapTag('td',externo,'');
            html += wrapTag('td',d.observaciones,'');
           var htmlEdit = "<a href='<%= PathCfg.CERTIFICADO_EDIT%>?id="+ d.id +"&id_activo="+ d.id_activo +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
//           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit,'');
           html +="</tr>";
       }
       return html;
    }
   /***** COMPRA */ 
    function loadDataCompra(data){
         var $tabla = $('#tblCompra');
         $.ajax({
               url: '<%= PathCfg.COMPRA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCompra(data.Records));

//                        $('.btn-del').click(borrarCompra);
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 0, "desc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 0 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });      
                   }
               }
           });
    }
    
    function createTableCompra(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',convertirFecha(d.fecha),'');
            var divisa = d.id_divisa===0?"Dolar":"Peso";
            html += wrapTag('td',d.cantidad,'');
            html += wrapTag('td',divisa,'');
            html += wrapTag('td',d.precio_unit,'');
            html += wrapTag('td',d.precio_tot,'');
            html += wrapTag('td',d.proveedor,'');
           var htmlEdit = "<a href='<%= PathCfg.COMPRA_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
           //var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit ,'');
           html +="</tr>";
       }
       return html;
    }
    
    /* CORRECTIVO */ 
    function loadDataCorrectivo(data){
         var $tabla = $('#tblCorrectivo');
        $.ajax({
               url: '<%= PathCfg.CORRECTIVO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCorrectivo(data.Records));
                       $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 0, "desc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 0 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });  
                   }
               }
           });
    }
    
    function createTableCorrectivo(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',convertirFecha(d.fecha),'');
            html += wrapTag('td',d.id_actividad,'');            
            html += wrapTag('td',d.id_resultado,'');            
           var htmlEdit = "<a href='<%= PathCfg.CORRECTIVO_EDIT%>?id="+ d.id +"&id_activo="+ d.id_activo +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
//           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit,'');
           html +="</tr>";
       }
       return html;
    }
    
    
     </script>