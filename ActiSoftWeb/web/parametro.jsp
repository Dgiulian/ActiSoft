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
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
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
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',d.id,'');
            html += wrapTag('td',d.numero,'');
            html += wrapTag('td',d.codigo,'');
            html += wrapTag('td',d.nombre,'');
            html += wrapTag('td',d.valor,'');            
           var htmlEdit = "<span href='<%= PathCfg.PARAMETRO_EDIT%>?id="+ d.id +"' data-codigo='" + d.codigo + "' data-nombre='"+ d.nombre +" ' data-valor='"+ d.valor +"' class='btn btn-xs btn-circle  btn-warning  btn-edit'><span class='fa fa-edit fw'></span></span> ";
           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
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
        
        //var checked = (data.activo)?"checked":"";
        bootbox.dialog({
                title: "Configuraci&oacute;n de par&aacute;metro",
                message: '<div class="row">  ' +
                    '<div class="col-md-12"> ' +                    
                    '<form class="form-vertical"> ' +
                    '<input id="id" name="id" type="hidden" class="" value=' + data.id + ' >' +
                     '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="codigo">Codigo:</label> ' +
                        '<div class="col-md-8"> ' +
                        '<input id="codigo" name="codigo" type="text" class="form-control input-md" value="'+ data.codigo +'"> ' +
                     '</div>' + 
                     '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="nombre">Nombre:</label> ' +
                        '<div class="col-md-8"> ' +
                        '<input id="nombre" name="nombre" type="text" class="form-control input-md" value="'+ data.nombre +'"> ' +
                     '</div>' + 
//                    '</div>'+
                    '<div class="form-group"> ' +
                        '<label class="col-md-4 control-label" for="valor">Email:</label>' +
                        '<div class="col-md-8"> ' +
                        '<input id="valor" name="valor" type="text" class="form-control input-md" value="' + data.valor + '"> ' +
                        '</div> ' + 
                    '</div>'+ 
                    
//                      '<div class="form-group"> ' +
//                        '<label class="col-md-4 control-label" for="activo">Activo</label> ' +
//                        '<div class="col-md-8"> ' +
//                        '<input id="activo" name="activo" type="checkbox" class="checkbox input-md" ' + checked + ' value="'+ data.activo +'"> ' +
//                        '</div>' +
                      '</div> ' +  
//                    '</div>' +
                    '</form>' + 
                    '</div>'+
                    '</div>',
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var id     = $('#id').val();                        
                            var nombre = $('#nombre').val();
                            var codigo = $('#nombre').val();
                            var valor = $('#email').val();                            
                            var activo = $('#activo').prop('checked')?'1':'';
                            
                            $.ajax({
                                url:'<%= PathCfg.PARAMETRO_EDIT%>',
                                data: {id:id,codigo:codigo,nombre:nombre,valor:valor,activo:activo},
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
</script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
