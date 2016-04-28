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
                    <h1 class="page-header">Pretickets
                        <!--<a href="<%= PathCfg.PRETICKET_EDIT%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a>-->
                    </h1>
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Pretickets
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tblPreticket">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Punto venta</th>
                                            <th>N&uacute;mero</th>
                                            <th>Fecha</th>
                                            <th>Cliente</th>
                                            <th>Contrato</th>
                                            <th>Monto</th>
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
    <script src="js/bootbox.min.js"></script>
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
    $('#mdlPreticket').on('shown.bs.modal', function (e) {
        loadDataPreticket({id_remito:7});
    });
    $('#btnParcial').click(submitForm);
    $('#btnTotal').click(submitForm);
    });

    function validar(){
        return true;
    }
    function cargarDatos(){
        loadData({
            id_tipo:$('#tipo_remito').val(),
            id_estado:$('#estado_remito').val()
        });
    }
    function loadData(data){

        var $tabla = $('#tblPreticket');
        $.ajax({
               url: '<%= PathCfg.PRETICKET_LIST %>',
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
                        $('.btn-print').click(impresion);
                       $tabla.DataTable({
                            responsive: true,
                            retrieve: true,
                            paging: false,
                            ordering: true,
                            searching: false,
                            lengthChange:false,
                            bInfo: false,
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
            html += wrapTag('td',d.punto_venta,'');
            html += wrapTag('td',d.numero,'');


            html += wrapTag('td',convertirFecha(d.fecha),'');
            var clienteLnk  = '<a href="<%= PathCfg.CLIENTE_EDIT %>?id='+d.id_cliente+ '">'+ d.cliente + '</a>';
            var contratoLnk = '<a href="<%= PathCfg.CONTRATO_EDIT %>?id='+d.id_contrato+ '">'+ d.contrato + '</a>';
            html += wrapTag('td',clienteLnk,'');
            html += wrapTag('td',contratoLnk,'');
            var divisa = (d.id_divisa===0)?"U$S":"$";

            html += wrapTag('td',divisa + " " + d.total,'');

            var htmlEdit = "<a href='<%= PathCfg.PRETICKET_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-info'><span class='glyphicon glyphicon-sunglasses fw'></span></a> ";
            var htmlDel = " <span data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span> ";
//            var htmlDel = "";
            var htmlPrint = "<a target='_blank' href='<%= PathCfg.PRETICKET_PRINT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-default'><span class='fa fa-print fw'></span></a> ";

//            htmlPrint = "";
            html +='<td  style="width:120px">' + htmlEdit  + htmlPrint + htmlDel + '</td>';
           html +="</tr>";
       }
       return html;
    }
    function impresion(){
        var id = $(this).data('index');

        $tr = $(this).parent().parent();
        bootbox.confirm("Esta seguro que desea imprimir el registro?",function(result){
            if(result) {
                $.ajax({
                    url: '<%= PathCfg.PRETICKET_PRINT %>', //DATAENTRY_DEL
                    data: {id:id,
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
        });
        return;
    }

    function deleteData(){
        var id = $(this).data('index');

        $tr = $(this).parent().parent();
        bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
             if(result) {
                 $.ajax({
                 url: '<%= PathCfg.PRETICKET_DEL %>', //DATAENTRY_DEL
                 data: {id:id },
                 method:"POST",
                 dataType: "json",
                 success: function(data) {
                     if(data.Result === "OK") {
                         $tr.remove();
                     } else if (data.Message) bootbox.alert(data.Message);
                 }
             });
             }
         });
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>



</html>
