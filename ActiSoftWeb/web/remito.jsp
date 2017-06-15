<%@page import="transaccion.TEquipo"%>
<%@page import="bd.Equipo"%>
<%@page import="transaccion.TPozo"%>
<%@page import="bd.Pozo"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<!DOCTYPE html>
<html lang="en">

<head>
     <%@include  file="tpl_head.jsp" %>
</head>

<body>

    <div id="wrapper">

        <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Remitos <a href="<%= PathCfg.REMITO_ALTA%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a>
                    <span class="btn btn-info" data-toggle="modal" data-target="#mdlRemitoExport"><span class="fa fa-file-excel-o fa-fw"></span> Exportar</a>
                    </h1>
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Remitos
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-2">
                                    <div class="form-group">
                                        <label for="">Tipo de Remito</label>
                                        <select class="form-control" name="tipo_remito" id="tipo_remito">
                                            <option value="0"> Todos</option>
                                            <%
                                                for (Option o:OptionsCfg.getTipoRemitos()){ %>
                                                <option value="<%= o.getId()%>"><%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %> </option>
                                            <%  } %>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2">
                                    <div class="form-group">
                                        <label for="">Estado</label>
                                        <select class="form-control" name="estado_remito" id="estado_remito">
                                            <option value="0"> Todos</option>
                                            <% for (Option o:OptionsCfg.getEstadoRemitos()){ %>
                                                <option value="<%= o.getId()%>"><%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %> </option>
                                            <%  } %>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2">
                                    <div clas="form-group">
                                        <label for="numero">N&uacute;mero </label>
                                        <span class="input-group">
                                            <input type="text" class="form-control" name="numero" id="numero" size="20" value="">
                                            <span class="input-group-addon" id="btnBuscar" ><span class="fa fa-search fa-fw"></span></span>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-lg-1">
                                    <div class="form-group">
                                        <label for="">Preticket</label>
                                        <select class="form-control" name="facturado" id="facturado">
                                            <option value=""> Todos</option>
                                            <option value="1"> Si</option>
                                            <option value="0"> No</option>
                                        </select>
                                    </div>
                                </div>
                             <div class="col-lg-2 " >
                                <div class="form-group " >
                                     <label for="pozo">Pozo</label>
                                     <select class="form-control" name="id_pozo" id="id_pozo" >
                                         <option value="0">Todos</option>
                                         <% for(Pozo pozo: new TPozo().getList()) { %>
                                           <option value="<%=pozo.getId()%>" ><%= pozo.getNombre()%></option>
                                         <% } %>
                                     </select>
                                 </div>
                            </div>
                             <div class="col-lg-2 " >
                                     <div class="form-group " >
                                          <label for="equipo">Equipo</label>
                                          <select class="form-control" name="id_equipo" id="id_equipo" >
                                              <option value="0">Todos</option>
                                              <% for(Equipo equipo: new TEquipo().getList()) {
                                              %>
                                              
                                                <option value="<%=equipo.getId()%>" ><%= equipo.getNombre()%></option>
                                              <% } %>
                                          </select>
                                      </div>
                                 </div>                
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover table-responsive" id="tblRemito">
                                    <colgroup>
                                        <col span="1" style="width: 9%; text-align: right;"> <!-- Numero -->
                                        <col span="1" style="width: 8%;"> <!-- Tipo -->
                                        <col span="1" style="width: 9%;"> <!-- Fecha -->
                                        <col span="1" style="width: 10%;text-align: center"> <!-- Cliente-->
                                        <col span="1" style="width: 10%;"> <!-- Contrato -->
                                        <col span="1" style="width: 7%;"> <!-- Pozo -->
                                        <col span="1" style="width: 10%;"> <!-- Equipo -->
                                        <col span="1" style="width: 9%;text-align: center">
                                        <col span="1" style="width: 8%;text-align: center">
                                        <!--<col span="1" style="width: 24%;text-align: center">-->
                                     </colgroup>
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>N&uacute;mero</th>
                                            <th>Tipo</th>
                                            <th>Fecha</th>
                                            <th>Cliente</th>
                                            <th>Contrato</th>
                                            <th>Pozo</th>
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
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <script src="bower_components/datatables-plugins/sorting/date-uk.js"></script>

    <script src="js/bootbox.min.js"></script>

    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>

    <script src="js/invesoft.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        loadData({});

    $('#estado_remito').change(cargarDatos);
    $('#tipo_remito').change(cargarDatos);
    $('#facturado').change(cargarDatos);
    $('#id_pozo').change(cargarDatos);
    $('#id_equipo').change(cargarDatos);
    $('#mdlRemito').on('shown.bs.modal', function (e) {
        loadDataRemito({id_remito:7});
    });
    $('#btnParcial').click(submitForm);
    $('#btnTotal').click(submitForm);
    $('#btnBuscar').click(buscar);
    $('#mdlRemitoUpload').on('shown.bs.modal',function(e){
        var $invoker = $(e.relatedTarget);
        var id = $invoker.data('id');
        console.log(id);
        $('#objecto').val('db.Remito');
        $('#key').val('id');
        $('#keyValue').val(id);
        $('#id').val(id);
    });
    

    });
    function buscar(){
        var $numero = $('#numero');
        var data = ($numero.val()!=="")?{numero:$numero.val()}:{id_tipo:$('#tipo_remito').val(),id_estado:$('#estado_remito').val()
        };
        loadData(data);
    }
    function validar(){
        return true;
    }
    function cargarDatos(){
        var data =recuperarDatos();
        loadData(data);
    }
    function recuperarDatos(){
        var data ={};
        
        data.id_tipo = $('#tipo_remito').val();
        data.id_estado = $('#estado_remito').val();
        data.id_pozo   = $('#id_pozo').val();
        data.id_equipo = $('#id_equipo').val();
        data.facturado = $('#facturado').val(); //$('#facturado').prop('checked')?1:0;
        return data;
    }
    function loadData(data){
        var $tabla = $('#tblRemito');
        $tabla.DataTable().destroy();
        $.ajax({
               url: '<%= PathCfg.REMITO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   $tabla.find('tbody').html("");
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTable(data.Records));
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                        $('.btn-del').click(deleteData);
                        $('.btn-dev').click(devolucion);
                        $('.btn-print').click(impresion);            
                        $('.btn-diario').click(diario);
                        $('.btn-relacionado').click(relacionado);
                        $tabla.DataTable({
                                responsive: true,
                                paging: false,
                                retrieve: true,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo: false,
                                order: [ 2, "desc" ],
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
    function createTable(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
//            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.numero,'');

            var tipoRemitoLnk = d.tipo_remito;
            //if (d.id_tipo_remito === <%= OptionsCfg.REMITO_DEVOLUCION %>) {
            if (d.id_referencia !== 0) {
                tipoRemitoLnk = '<a href="<%= PathCfg.REMITO_VIEW %>?id='+ d.id +'&ref='+d.id_referencia+'">'+ d.tipo_remito + '</a>';
            }
            html += wrapTag('td',tipoRemitoLnk,'');
            html += wrapTag('td',convertirFecha(d.fecha),'');
            var clienteLnk  = '<a href="<%= PathCfg.CLIENTE_EDIT %>?id='+d.id_cliente+ '">'+ d.cliente + '</a>';
            var contratoLnk = '<a href="<%= PathCfg.CONTRATO_EDIT %>?id='+d.id_contrato+ '">'+ d.contrato + '</a>';
            html += wrapTag('td',clienteLnk,'');
            html += wrapTag('td',contratoLnk,'');
            html += wrapTag('td',d.pozo,'');
            html += wrapTag('td',d.equipo,'');
            html += wrapTag('td',d.facturado!==0?"Si":"No",'');
            
            var estadoLnk= "<a href='#' class='btn-relacionado' data-index='" + d.id + "'>"+d.estado +"</a>";
            html += wrapTag('td',estadoLnk,'');
            
            var htmlEdit = "<a href='<%= PathCfg.REMITO_VIEW%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-info'><span class='glyphicon glyphicon-sunglasses fw'></span></a> ";
  //            var htmlDel = "";
            var htmlDel = " <span data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span> ";

            var htmlPrint  = "<span target='_blank' href='<%= PathCfg.REMITO_PRINT%>?id=" + d.id + "' data-index='" + d.id + "' class='btn btn-xs btn-circle  btn-default btn-print'><span class='fa fa-print fw'></span></span> ";

            var htmlDev = "";
            var htmlDiario = "";
            if (d.id_estado === <%= OptionsCfg.REMITO_ESTADO_ABIERTO %> ) {
               // htmlDev = "<span  data-index='"+ d.id + "' class='btn btn-xs btn-circle  btn-warning  btn-dev'><span class='fa fa-exchange fw'></span></span> ";
               // htmlDev = "<span  data-index='"+ d.id + "' class='btn btn-xs btn-circle  btn-warning ' data-toggle='modal' data-target='#mdlRemito'><span class='fa fa-exchange fw'></span></span> ";
               htmlDev = "<a href='<%= PathCfg.REMITO_DEV %>?id=" + d.id + "' class='btn btn-xs btn-circle  btn-warning '><span class='fa fa-exchange fw'></span></a> ";
               htmlDiario = "<span target='_blank' href='<%= PathCfg.REMITO_DIARIO%>?id="+ d.id + "' data-index='" + d.id + "' class='btn btn-xs btn-circle  btn-default btn-diario'><span class='fa fa-archive fw'></span></span> ";
            }
            var htmlUp = '<span  class="btn btn-circle btn-default" data-toggle="modal" data-target="#mdlRemitoUpload" data-id="'+ d.id+'" ><span class="fa  fa-upload fa-fw"></span> </span>';
            html +='<td  style="width:120px">' + htmlEdit   + htmlPrint + htmlDev + htmlUp + htmlDiario + htmlDel + '</td>';
           html +="</tr>";
       }
       return html;
    }
    function relacionado(){
        $('#mdlRelacionado').modal('show');
        var id_remito = $(this).data('index');
        loadDataRelacionado({id_remito:id_remito});
    }
    function devolucion(){
        var id = $(this).data('index');

        $tr = $(this).parent().parent();
        bootbox.prompt({
           title:'Remito de devoluci&oacute;n',
            size: 'small',
            message: "Ingrese el N&uacute;mero de remito de devoluci&oacute;n",
            callback: function(result){ /* your callback code */
                if(result) {
                    // href='<%= PathCfg.REMITO_DEV%>?id="+ d.id +"'
                    //window.location = '<%= PathCfg.REMITO_DEV%>?id="+ ' + id + '"';

                    $.ajax({
                        url: '<%= PathCfg.REMITO_DEV %>', //DATAENTRY_DEL
                        data: {id:id,
                               numero:result
                            },
                        method:"POST",
                        dataType: "json",
                        success: function(data) {
                            if(data.Result === "OK") {
                                window.location.reload();
                            } else if (data.Message) bootbox.alert(data.Message);
                        }
                    });
                }
             }
        });
        return;
    }
    function impresion(){
        var id = $(this).data('index');

        $tr = $(this).parent().parent();
        bootbox.dialog({
                title:"",
                message: "&iquest;Desea Imprimir el remito?",
                buttons:{
                    imprimir:{
                        label: "Imprimir",
                        className: "btn-success",
                        callback: function(result){
                            console.log(result);
                            if(result) {
                                location.href='<%= PathCfg.REMITO_PRINT %>?id=' + id;
                            }
                        }
                    },
                    cancel:{
                        label: "Cancelar",
                        className:"btn-cancel"
                    }
            }
        });
        return;
    }
    function diario(){
        var id = $(this).data('index');
        $tr = $(this).parent().parent();
        $('#id').val(id);
        $('#mdlRemitoDiario').modal('show');
        return;
    }
    function deleteData(){
        var id = $(this).data('index');

        $tr = $(this).parent().parent();
        bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
             if(result) {
                 $.ajax({
                 url: '<%= PathCfg.REMITO_DEL %>',
                 data: {id:id },
                 method:"POST",
                 dataType: "json",
                 success: function(data) {
                     if(data.Result === "OK") {
                         cargarDatos();
                         //$tr.remove();
                     } else if (data.Message) bootbox.alert(data.Message);
                 }
             });
             }
         });
    }
     </script>

</body>
<%@include  file="remito_upload_mdl.jsp" %>
<%@include  file="remito_diario_mdl.jsp" %>
<%@include  file="remito_relacionado_mdl.jsp" %>
<%@include  file="remito_export_mdl.jsp" %>
<%@include file="tpl_footer.jsp"%>
</html>
