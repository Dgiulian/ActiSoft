<%@page import="utils.PathCfg"%>
<%
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
                    <h1 class="page-header">Par&aacute;metro <span id="btnNuevo" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</span></h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Parametros
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblParametro">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>N&uacute;mero</th>
                                            <th>C&oacute;digo</th>
                                            <th>Nombre</th>
                                            <th>Valor</th>
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
    <script src="js/handlebars.runtime-v4.0.5.js"></script>    
    
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
    
    <script src="js/hnd/parametro.list.js"></script>
    <script src="js/hnd/parametro.edit.js"></script>
    

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        loadData({});
        $('#btnNuevo').click(function(){
            agregarParametro({id:0,codigo:'',nombre:'',valor:'',activo:1});
        });

    });
    function loadData(data){
        var $tabla = $('#tblParametro');
        $.ajax({
               url: '<%= PathCfg.PARAMETRO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(result) {
                   if(result.Result === "OK") {
                       $tabla.find('tbody').html(createTable(result.Records));
                        $('.btn-del').click(borrarParametro);
                        $('.btn-edit').click(editarParametro);
                   }
               }
           });
    }
    function borrarParametro(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.PARAMETRO_DEL %>',{id:id},function(result) {
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
    function createTable(data){        
        return Handlebars.templates['parametro.list']({records:data});       
    }
     function editarParametro(){
        var codigo = $(this).data('codigo');
        var nombre = $(this).data('nombre');
        var valor  = $(this).data('valor');
        var index  = $(this).data('index');
        var activo = $(this).data('activo');
        agregarParametro({codigo:codigo,nombre:nombre,id:index,valor:valor,activo:activo});
    }
    function agregarParametro(data){
        data.checked = (data.activo)?"checked":"";
        var template = Handlebars.templates['parametro.edit'];
        bootbox.dialog({
                title: "Configuraci&oacute;n de par&aacute;metro",
                message: template(data), 
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var data = recuperarCampos();
                            $.ajax({
                                url:'<%= PathCfg.PARAMETRO_EDIT%>',
                                data: data,
                                method:'POST',
                                dataType:'json',
                                success:function(){
                                    loadData();
                                }
                                });
                            //bootbox.alert("Nombre " + nombre + ". Email: <b>" + email + "</b>");
                        }
                    },
                    cancel: {
                        label: "Cancelar",
                        callback: function () {
                        }
                    }
                }
            });
    }
    function recuperarCampos(){
        var data = {};
        data.id     = $('#id').val();
        data.nombre = $('#nombre').val();
        data.codigo = $('#codigo').val();
        data.valor  = $('#valor').val();
        data.activo = $('#activo').prop('checked')?'1':'';
        data.activo = 1;
        return data;
    }
</script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
