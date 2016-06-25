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
                    <h1 class="page-header">Transportistas <span class="btn btn-primary" id="nuevoTransportista"><span  class="fa fa-file-o fa-fw"> </span>Nuevo</span></h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Transportistas
                            <input type="hidden" id="id_proveedor" name="id_proveedor" value="<%= proveedor.getId() %>">
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblTransportista">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Nombre</th>
                                            <th>DNI</th>
                                            <th>Vencimiento Carnet</th>
                                            <th>Vencimiento Seguro</th>                                            
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
        var id_proveedor  = $('#id_proveedor').val();
        loadDataTransportista({id_proveedor:id_proveedor});
        $('#nuevoTransportista').click(function(){
            var id_proveedor  = $('#id_proveedor').val();
            var index = 0;
            var nombre = "";
            var dni     = "";
            var vencimiento_carnet = "";
            var vencimiento_seguro = "";            
            agregarTransportista({id:index,id_proveedor:id_proveedor,nombre:nombre,dni:dni,vencimiento_carnet:vencimiento_carnet,vencimiento_seguro:vencimiento_seguro});
            
        });    
    });
    function loadDataTransportista(data){
         var $tabla = $('#tblTransportista');


        $.ajax({
               url: '<%= PathCfg.TRANSPORTISTA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableTransportista(data.Records));
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                      
                        $('.btn-del').click(borrarTransportista);
                        $('.btn-edit').click(editarTransportista);
//                        $tabla.DataTable({
//                                responsive: true,
//                                paging:true,
//                                ordering:false,
//                                searching:false,
//                                lengthChange:false,                                
//                                bInfo:false,
//                                language: {
//                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
//                                }
//                        });
                   }
               }
           });
    }

    function createTableTransportista(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',d.nombre,'');
            html += wrapTag('td',d.dni,'');
            html += wrapTag('td',convertirFecha(d.vencimiento_carnet),'');
            html += wrapTag('td',convertirFecha(d.vencimiento_seguro),'');
            
//           var htmlEdit = "<a href='<%= PathCfg.TRANSPORTISTA_EDIT%>?id="+ d.id +"&id_proveedor="+ d.id_proveedor +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
           var htmlEdit = "<span  data-index='"+ d.id + "' data-nombre='"+ d.nombre + "' data-dni='"+ d.dni + "' data-vencimiento_carnet='"+d.vencimiento_carnet+"' data-vencimiento_seguro='"+d.vencimiento_seguro+"' class='btn btn-xs btn-circle btn-warning btn-edit'><span class='fa fa-edit fw'></span></span>";
           var htmlDel = "<span data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
    function borrarTransportista(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.TRANSPORTISTA_DEL %>',{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     function editarTransportista(){
        var index  = $(this).data('index');
        var id_proveedor  = $('#id_proveedor').val();
        var nombre = $(this).data('nombre');
        var dni    = $(this).data('dni');
        var vencimiento_carnet = convertirFecha($(this).data('vencimiento_carnet'));
        var vencimiento_seguro = convertirFecha($(this).data('vencimiento_seguro')) ;
        agregarTransportista({id:index,id_proveedor:id_proveedor,nombre:nombre,dni:dni,vencimiento_carnet:vencimiento_carnet,vencimiento_seguro:vencimiento_seguro});
    }
    function agregarTransportista(data){     
        console.log(data);
        var titulo = data.id?"Editar Transportista":"Nuevo Transportista";
        bootbox.dialog({
                title: titulo,
                message: '<div class="row">  ' +
                    '<div class="col-md-12"> ' +                    
                    '<form class="form-vertical"> ' +
                    '<input id="id" name="id" type="hidden" class="" value=' + data.id + ' >' +                     
                    '<input id="id_proveedor" name="id_proveedor" type="hidden" class="" value=' + data.id_proveedor + ' >' +                     
                     '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="nombre">Nombre:</label> ' +
                        '<div class="col-md-8"> ' +
                        '<input id="nombre" name="nombre" type="text" class="form-control input-md" value="'+ data.nombre +'"> ' +
                     '</div>' + 
                     '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="nombre">DNI:</label> ' +
                        '<div class="col-md-8"> ' +
                        '<input id="dni" name="dni" type="text" class="form-control input-md" value="'+ data.dni +'"> ' +
                     '</div>' + 
                     
                    '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="vencimiento_carnet">Vencimiento Carnet: </label>' +
                        '<div class="col-md-8"> ' +
                        '<input id="vencimiento_carnet" name="vencimiento_carnet" type="text" class="form-control input-md date-picker" value="' + data.vencimiento_carnet + '"> ' +
                        '</div> ' + 
                    '</div>'+ 
                     '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="vencimiento_seguro">Vencimiento Seguro: </label>' +
                        '<div class="col-md-8"> ' +
                        '<input id="vencimiento_seguro" name="vencimiento_seguro" type="text" class="form-control input-md date-picker" value="' + data.vencimiento_seguro + '"> ' +
                        '</div> ' + 
                    '</div>'+ 
                      '</div> ' +  
                '</form>' + 
                '</div>'+
                '</div>',
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var id     = $('#id').val();                        
                            var id_proveedor = $('#id_proveedor').val();                        
                            var nombre = $('#nombre').val();
                            var dni    = $('#dni').val();
                            var vencimiento_seguro = $('#vencimiento_seguro').val();
                            var vencimiento_carnet  = $('#vencimiento_carnet').val();                            
                            //var activo = $('#activo').prop('checked')?'1':'';
                            //activo = 1;
                            var data = {id:id,id_proveedor:id_proveedor,nombre:nombre,dni:dni,vencimiento_carnet:vencimiento_carnet,vencimiento_seguro:vencimiento_seguro};
                            if (!validar(data)) return;                        
                            $.ajax({
                                url:'<%= PathCfg.TRANSPORTISTA_EDIT%>',
                                data: data,
                                method:'POST',
                                dataType:'json',
                                success:function(){
                                    loadDataTransportista({id_proveedor:id_proveedor});
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
     function validar(data){
        if(!data.nombre){
            bootbox.alert("Ingrese el nombre del transportista");
            return false;
        }
        console.log(data.vencimiento_carnet)
        if(!data.vencimiento_carnet || !validarFecha(data.vencimiento_carnet)) {
            bootbox.alert("Ingrese la fecha de vencimiento de VTV");
            return false;
        }
        if(!data.vencimiento_seguro || !validarFecha(data.vencimiento_seguro)) {
            bootbox.alert("Ingrese la fecha de vencimiento del seguro");
            return false;
        }
        return true;    
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
