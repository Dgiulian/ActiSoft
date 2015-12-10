<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
<!DOCTYPE html>
<html lang="en">

<head>    
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%= PathCfg.PAGE_TITLE %></title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <!-- Bootstrap Core CSS -->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="bower_components/datatables-responsive/css/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Contratos <a href="<%= PathCfg.CONTRATO_EDIT%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Contratos
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tblContrato">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>N&uacute;mero</th>
                                            <th>Fecha</th>
                                            <th>Inicio</th>
                                            <th>Fin</th>
                                            <th>Divisa</th>
                                            <th>Monto</th>
                                            <th style="width:20px"><center>&percnt;</center></th>
                                            <th>Cliente</th>                                          
                                            <th style="width:100px"></th>
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
        var $invoker;
    $(document).ready(function() {
        loadData({});
       $('#mdlContratoView').on('show.bs.modal',function(e){           
           $invoker = $(e.relatedTarget);
           $id = $invoker.data('index');
           console.log($id);
            loadDataContratoView({id_contrato:$id}); 
       });
    });
    function loadData(data){
        var $tabla = $('#tblContrato');
        $.ajax({
               url: '<%= PathCfg.CONTRATO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTable(data.Records));
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                      
                        $('.btn-del').click(borrarContrato);
                        $tabla.DataTable({
                                responsive: true,
                                retrieve: true,
                                paging: false,
                                ordering: true,
                                searching: false,
                                lengthChange:false,
                                bInfo:false,
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });
                   }
               }
           });
    }
    function borrarContrato(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.CONTRATO_DEL %>',{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
    function createTable(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
//            html += wrapTag('td',d.id,'');            
            html += wrapTag('td',d.numero,'');
            html += wrapTag('td',convertirFecha(d.fecha),'');
            html += wrapTag('td',convertirFecha(d.fecha_inicio),'');
            html += wrapTag('td',convertirFecha(d.fecha_fin),'');
            var divisa = (d.id_divisa == 0?"U$s":"$");
            html += wrapTag('td',divisa,'');
            html += wrapTag('td',d.monto,'');
            html += wrapTag('td',d.porcentaje,'');
            var clienteLnk = '<a href="<%= PathCfg.CLIENTE_EDIT %>?id='+d.id_cliente+ '">'+ d.nombre_cliente + '</a>';
            html += wrapTag('td',clienteLnk,'');

            var htmlEdit = "<a href='<%= PathCfg.CONTRATO_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
            
            var htmlView = "<span data-toggle='modal' data-target='#mdlContratoView' data-index='"+ d.id + "' class='btn btn-xs  btn-primary btn-circle'><span class='fa fa-file fw'></span></span> ";
            var htmlDel = "<span  data-index='"+ d.id + "' class='btn btn-xs btn-danger  btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
            html +=wrapTag('td',htmlEdit  + htmlView + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
   
     </script>
     <%@include file="contrato_view_mdl.jsp" %>
     <%@include file="tpl_footer.jsp"%>
</body>

</html>
