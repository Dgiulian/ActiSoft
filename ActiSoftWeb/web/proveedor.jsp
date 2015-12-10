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
                    <h1 class="page-header">Proveedores <a href="<%= PathCfg.PROVEEDOR_EDIT%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                    
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Proveedores
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tblProveedor">
                                    <thead>
                                        <tr>
                                           <!--<th>Id</th>-->
                                            <th>Nombre</th>
<!--                                            <th>Cuit</th>
                                            <th>Dni</th>-->
                                            <th>Nombre comercial</th>
                                            <th>Direccion fisica</th>
                                            <!--<th>Direccion legal</th>-->
                                            <th>Provincia</th>
                                            <th>Localidad</th>
                                            <!--<th>Codigo_postal</th>-->
<!--                                            <th>Telefono</th>
                                            <th>Celular</th>-->
                                            <th>Contacto</th>
                                            <th>Observaciones</th>
                                            <!--<th>Fecha_alta</th>-->
                                            <!--<th>Activo</th>-->
                                            <th></th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
<!--                            <div class="well">
                                <h4>DataTables Usage Information</h4>
                                <p>DataTables is a very flexible, advanced tables plugin for jQuery. In SB Admin, we are using a specialized version of DataTables built for Bootstrap 3. We have also customized the table headings to use Font Awesome icons in place of images. For complete documentation on DataTables, visit their website at <a target="_blank" href="https://datatables.net/">https://datatables.net/</a>.</p>
                                <a class="btn btn-default btn-lg btn-block" target="_blank" href="https://datatables.net/">View DataTables Documentation</a>
                            </div>-->
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
    
    <script src="bower_components/datatables-responsive/js/dataTables.responsive.js"></script>
    
    <script src="js/bootbox.min.js"></script>        
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/invesoft.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        loadData({});

    });
    function loadData(data){
         var $tabla = $('#tblProveedor');


    
        $.ajax({
               url: '<%= PathCfg.PROVEEDOR_LIST %>',
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
                       
                       $('.btn-del').click(borrarProveedor);
                       
                       $tabla.DataTable({
                            responsive: true,
                            retrieve: true,
                            paging: false,
                            ordering: true,
                            searching: false,
                            lengthChange: false,
                            bInfo: false,
                            language: {
                                url:'bower_components/datatables-plugins/i18n/Spanish.json',
                            }
                        });
                   }
               }
           });
    }
    function borrarProveedor(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.PROVEEDOR_DEL %>',{id:id},function(result) {
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
            html += wrapTag('td',d.nombre,'');
//            html += wrapTag('td',d.cuit,'');
//            html += wrapTag('td',d.dni,'');
            html += wrapTag('td',d.nombre_comercial,'');
            html += wrapTag('td',d.direccion_fisica,'');
//            html += wrapTag('td',d.direccion_legal,'');
            html += wrapTag('td',d.provincia,'');
            html += wrapTag('td',d.localidad,'');
//            html += wrapTag('td',d.codigo_postal,'');
//            html += wrapTag('td',d.telefono,'');
//            html += wrapTag('td',d.celular,'');
            html += wrapTag('td',d.contacto,'');
            html += wrapTag('td',d.observaciones,'');
//            html += wrapTag('td',d.fecha_alta,'');
//            html += wrapTag('td',d.activo,'');
            var htmlEdit = "<a href='<%= PathCfg.PROVEEDOR_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
            var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
            html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
   
        </script>
    </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
