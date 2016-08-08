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
                    <h3 class="proveedor-heading">Proveedor: <%=proveedor.getNombre_comercial()%></h3>
                    <h1 class="page-header">Vehiculos <span class="btn btn-primary" id="nuevoVehiculo"><span  class="fa fa-file-o fa-fw"> </span>Nuevo</span></h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Vehiculos
                            <input type="hidden" id="id_proveedor" name="id_proveedor" value="<%= proveedor.getId() %>">
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblVehiculo">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Dominio</th>
                                            <th>Vencimiento vtc</th>
                                            <th>Seguro</th>
                                            <th>Poliza</th>
                                            <th>Vencimiento Poliza</th>
                                            <th>RSV</th>
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
    
    <script src="js/hnd/vehiculo.list.js"></script>
    <script src="js/hnd/vehiculo.edit.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        var id_proveedor  = $('#id_proveedor').val();
        loadDataVehiculo({id_proveedor:id_proveedor});
        $('#nuevoVehiculo').click(nuevoVehiculo);
    });
    function nuevoVehiculo(e){
            var data = {};
            data.id_proveedor  = $('#id_proveedor').val();
            data.index = 0;
            data.dominio = "";
            data.vencimiento_vtv = "";
            data.seguro = "";
            data.poliza = "";
            data.vencimiento_poliza = "";
            data.rsv = "";
            agregarVehiculo(data);        
    }
    function loadDataVehiculo(data){
        var $tabla = $('#tblVehiculo');         
        $.ajax({
               url: PathCfg.VEHICULO_LIST,
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableVehiculo(data.Records));
                        $('.btn-del').click(borrarVehiculo);
                        $('.btn-edit').click(editarVehiculo);
                   }
               }
           });
    }

    function createTableVehiculo(data){
        Handlebars.registerHelper("convertirFecha",convertirFecha);
        return Handlebars.templates['vehiculo.list']({records:data});
    }
    function borrarVehiculo(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData(PathCfg.VEHICULO_DEL,{id:id},function(result) {
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     function editarVehiculo(){
        var index  = $(this).data('index');
        var id_proveedor  = $('#id_proveedor').val();
        var dominio = $(this).data('dominio');
        var vencimiento_vtv    = convertirFecha($(this).data('vencimiento_vtv')) ;
        var seguro  = $(this).data('seguro');
        var poliza  = $(this).data('poliza');
        var vencimiento_poliza = convertirFecha($(this).data('vencimiento_poliza'));
        var rsv     = $(this).data('rsv');
        
        agregarVehiculo({id:index,id_proveedor:id_proveedor, dominio:dominio, vencimiento_vtv: vencimiento_vtv, seguro:seguro, poliza:poliza, vencimiento_poliza:vencimiento_poliza,rsv:rsv});
    }
    function agregarVehiculo(data){
        
        var titulo = data.id?"Editar Vehiculo":"Nuevo Vehiculo";
        bootbox.dialog({
                title: titulo,
                message: Handlebars.templates['vehiculo.edit'](data),
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var data = recuperarCampos();
                            if(!validar(data)) return;
                            $.ajax({
                                url: PathCfg.VEHICULO_EDIT,
                                data: data,
                                method:'POST',
                                dataType:'json',
                                success:function(result){
                                    if(result.Result==='OK')
                                        loadDataVehiculo({id_proveedor:data.id_proveedor});
                                    else bootbox.alert(result.Message);
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
        data.dominio      = $('#dominio').val();
        data.vencimiento_vtv = $('#vencimiento_vtv').val();
        data.seguro = $('#seguro').val();
        data.poliza = $('#poliza').val();                            
        data.vencimiento_poliza  = $('#vencimiento_poliza').val();
        data.rsv  = $('#rsv').val();
        return data;
    }
    function validar(data){
        if(!data.dominio){
            bootbox.alert("Ingrese el dominio del vehiculo");
            return false;
        }
        console.log(data.vencimiento_vtv)
        if(!data.vencimiento_vtv || !validarFecha(data.vencimiento_vtv)) {
            bootbox.alert("Ingrese la fecha de vencimiento de VTV");
            return false;
        }
        if(!data.vencimiento_poliza || !validarFecha(data.vencimiento_poliza)) {
            bootbox.alert("Ingrese la fecha de vencimiento de poliza");
            return false;
        }

        return true;    
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
