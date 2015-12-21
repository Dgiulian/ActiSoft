<%@page import="bd.Usuario"%>
<%@page import="bd.Usuario"%>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    if (usuario==null)
        usuario = new Usuario();
%>
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
                    
                    <input type="hidden" name="id_usuario" id="id_usuario" value="<%= usuario.getId()%>">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Auditoria de Usuario
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tblAuditoria">
                                    <thead>
                                        <tr>
                                           <!--<th>Id</th>-->
                                            <th>Email</th>
                                            <th>Tipo</th>
                                            <th>Modulo</th>
                                            <th>Acci&oacute;n</th>
                                            <th>Fecha</th>
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
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $id_usuario= $('#id_usuario');
        if ($id_usuario!==undefined && $id_usuario.val()!=="")
        loadData({id_usuario : $id_usuario.val()});

    });
    function loadData(data){
         var $tabla = $('#tblAuditoria');
        $.ajax({
               url: '<%= PathCfg.AUDITORIA_LIST %>',
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
   
    function createTable(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];           
//            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.email,'');
            html += wrapTag('td',d.tipo,'');
            html += wrapTag('td',d.modulo,'');
            html += wrapTag('td',d.accion,'');
            html += wrapTag('td',convertirFechayHora(d.fecha),'');

           html +="</tr>";
       }
       return html;
    }
   
        </script>
    </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
