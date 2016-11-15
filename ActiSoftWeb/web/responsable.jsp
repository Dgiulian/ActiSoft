<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Proveedor"%>
<%
    Proveedor proveedor = (Proveedor) request.getAttribute("proveedor");
    if(proveedor==null) proveedor = new Proveedor();
%>
<!DOCTYPE html>
<html lang="en">

<head>
     <%@include  file="tpl_head.jsp" %>
      <style>
         .proveedor-heading{
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
                    <h3 class="proveedor-heading">Proveedor: <a href="<%=PathCfg.PROVEEDOR_EDIT%>?id=<%=proveedor.getId()%>" ><%=proveedor.getNombre_comercial()%></a></h3>
                    <h1 class="page-header">Responsables <span class="btn btn-primary" id="nuevoResponsable"><span  class="fa  fa-file-o fa-fw"> </span>Nuevo</span></h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Responsables
                            <input type="hidden" id="id_proveedor" name="id_proveedor" value="<%= proveedor.getId() %>">
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblResponsable">
                                    
                                        <colgroup>
                                            <col></col>
                                            <col></col>
                                            <col style="width:10%"></col>
                                        </colgroup>
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Nombre</th>                                            
                                            <th>Apellido</th>                                            
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
    
    <script src="js/hnd/responsable.list.js"></script>
    <script src="js/hnd/responsable.edit.js"></script>
    <script src="js/hnd/habilitacion.edit.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        var id_proveedor  = $('#id_proveedor').val();
        loadDataResponsable({id_proveedor:id_proveedor});
        $('#nuevoResponsable').click(function(){
            var id_proveedor  = $('#id_proveedor').val();
            var index    = 0;
            var nombre   = "";
            var apellido = "";
            agregarResponsable({id:index,id_proveedor:id_proveedor,nombre:nombre,apellido:apellido});
        });    
        $('#mdlHabilitacion').on('show.bs.modal',function(e){           
           $invoker = $(e.relatedTarget);
           $id = $invoker.data('index');
           $('#id_responsable').val($id);
            loadDataHabilitacion({id_responsable: $id}); 
       });
    });
    function loadDataResponsable(data){
        var $tabla = $('#tblResponsable');
        $.ajax({
               url: '<%=PathCfg.RESPONSABLE_LIST%>' ,
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableResponsable(data.Records));
                       $tabla.find('.btn-del').click(borrarResponsable);
                        $tabla.find('.btn-edit').click(editarResponsable);
                   }
               }
           });
    }
    function createTableResponsable(data){
        Handlebars.registerHelper("convertirFecha",convertirFecha);
        return Handlebars.templates['responsable.list']({records:data});
    }
    function borrarResponsable(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%=PathCfg.RESPONSABLE_DEL%>' ,{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     function editarResponsable(){
        var index  = $(this).data('index');
        var id_proveedor = $('#id_proveedor').val();
        var nombre       = $(this).data('nombre');
        var apellido     = $(this).data('apellido');

        agregarResponsable({id:index,id_proveedor:id_proveedor,nombre:nombre,apellido:apellido});
    }
    
    function agregarResponsable(data){
        var titulo = data.id?"Editar Responsable":"Nuevo Responsable";
        bootbox.dialog({
                title: titulo,
                message: Handlebars.templates['responsable.edit'](data),
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var data = recuperarCampos();
                            if (!validar(data)) return;                        
                            $.ajax({
                                url:'<%=PathCfg.RESPONSABLE_EDIT%>',
                                data: data,
                                method:'POST',
                                dataType:'json',
                                success:function(){
                                    loadDataResponsable({id_proveedor:data.id_proveedor});
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
        data.id           = $('#id').val();
        data.id_proveedor = $('#id_proveedor').val();                        
        data.nombre       = $('#nombre').val();
        data.apellido     = $('#apellido').val();
        console.log(data);
        return data;
    }
     function validar(data){
        if(!data.nombre){
            bootbox.alert("Ingrese el nombre del responsable");
            return false;
        }        
        if(!data.id_proveedor){
            bootbox.alert("Debe seleccionar el proveedor");
            return false;
        }    
        return true;    
    }
     </script>
<%@include file="habilitacion_mdl.jsp"%>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
