<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Pozo"%>
<%
    Pozo pozo = (Pozo) request.getAttribute("pozo");
    if(pozo==null) pozo = new Pozo();
%>
<!DOCTYPE html>
<html lang="en">

<head>
     <%@include  file="tpl_head.jsp" %>
      <style>
         .pozo-heading{
                background-color: #f5f5f5;
               line-height: 58px;
               display: inline-block;
               width: 100%;
         }
     </style>
</head>

<body>

    <div id="wrapper">

        <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">                    
                    <h1 class="page-header">Equipos <span class="btn btn-primary" id="nuevoEquipo"><span  class="fa fa-file-o fa-fw"> </span>Nuevo</span>
                    </h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Equipos
                            <input type="hidden" id="id_pozo" name="id_pozo" value="<%= pozo.getId() %>">
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblEquipo">
                                      <colgroup>
                                        <col style=""></col>
                                        <col style="width:10%"></col>
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Nombre</th>
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
    <script src="js/handlebars.runtime-v4.0.5.js"></script>
    
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>    
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
    
    <script src="js/hnd/equipo.list.js"></script>
    <script src="js/hnd/equipo.edit.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        var id_pozo  = $('#id_pozo').val();
        loadDataEquipo({id_pozo:id_pozo});
        $('#nuevoEquipo').click(function(){
            var id_pozo  = $('#id_pozo').val();
            var index = 0;
            var nombre = "";
            var descripcion = "";
            agregarEquipo({id:index,nombre:nombre,descripcion:descripcion,});
        });    
    });
    function loadDataEquipo(data){
        var $tabla = $('#tblEquipo');
        console.log("load data");
        $.ajax({
               url: PathCfg.EQUIPO_LIST ,
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableEquipo(data.Records));
                        $('.btn-del').click(borrarEquipo);
                        $('.btn-edit').click(editarEquipo);
                   }
               }
           });
    }

    function createTableEquipo(data){
        Handlebars.registerHelper("convertirFecha",convertirFecha);
        return Handlebars.templates['equipo.list']({records:data});
    }
    function borrarEquipo(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData(PathCfg.EQUIPO_DEL ,{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     function editarEquipo(){
         var data = {};
        data.id = $(this).data('index');
        data.nombre = $(this).data('nombre');
        data.descripcion    = $(this).data('descripcion');
        agregarEquipo(data);
    }
    function agregarEquipo(data){     
        var titulo = data.id?"Editar Equipo":"Nuevo Equipo";
        bootbox.dialog({
                title: titulo,
                message: Handlebars.templates['equipo.edit'](data),
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var data = recuperarCampos();
                            if (!validar(data)) return;                        
                            $.ajax({
                                url:PathCfg.EQUIPO_EDIT,
                                data: data,
                                method:'POST',
                                dataType:'json',
                                success:function(){
                                    loadDataEquipo({id_pozo:data.id_pozo});
                                }
                            });                            
                        }
                    },
                    cancel: {
                        label: "Cancelar",
                        callback: function () {}
                    }
                }
            }).init(function(){
                if($().datepicker) {
                    $('.date-picker').datepicker({
                        language: 'es',
                        locale:'es-AR',
                        format:'dd/mm/yyyy',
                        dateFormat:'dd/mm/yyyy',
                        autoclose: true
                    });
                  }
        });
    }
    function recuperarCampos(){
        var data = {};
        data.id     = $('#id').val();                        
        data.id_pozo = $('#id_pozo').val();                        
        data.nombre = $('#nombre').val();
        data.descripcion    = $('#descripcion').val();
       
        return data;
    }
     function validar(data){
        if(!data.nombre){
            bootbox.alert("Ingrese el nombre del equipo");
            return false;
        }
        return true;    
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
