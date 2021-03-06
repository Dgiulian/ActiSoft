<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Kit"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
    <%
        Kit kit = (Kit) request.getAttribute("kit");
        if(kit==null) kit = new Kit();
        
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
                    <h1 class="page-header">Kits <a href="<%= PathCfg.KIT_EDIT %>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                    <input type="hidden" id="id_activo" name="id_activo" value="<%= kit.getId()%>">
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
<!--                <div class="col-lg-4">
                    <div class="form-group">
                        <label class="" for="id_estado">Estado</label>
                        <select class="form-control" name="id_estado" id="id_estado">
                            <option value="0" selected>Todos</option>
                            <% for(Option o:OptionsCfg.getEstadoKit()){%>
                              <option value="<%=o.getId()%>"><%=StringEscapeUtils.escapeHtml4(o.getDescripcion())%></option>
                            <%}%>                            
                        </select>
                    </div>
                </div>-->

                <div class="col-lg-3">
                    <div clas="form-group">
                        <label for="">C&oacute;digo</label>
                        <span class="input-group">                                                                                            
                            <input type="text" class="form-control uppercase" name="codigo" id="codigo" size="20" value="">
                            <span class="input-group-addon" id="btnBuscar" ><span class="fa fa-search fa-fw"></span></span>
                        </span>
                    </div>
                </div>                            
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="activo">
                        <input type="checkbox" class="checkbox checkbox-inline" name="activo" id="activo" value='1' > Mostrar eliminados</label>
                    </div>
               </div>
                                
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Kits
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblKit">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <!--<th>Fecha</th>-->
                                            <th>C&oacute;digo</th>
                                            <th>Nombre</th>
                                            <th>Rubro</th>                                            
                                            <th>Subrubro</th>
                                            <th>Estado</th>
                                            <th>Certificado</th>
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
        filtrar();
        $('#id_estado').change(filtrar);
        $('#activo').change(filtrar);
        $('#btnBuscar').click(filtrar);
         $('#codigo').keydown(function(e){
           if(e.keyCode===13) filtrar();
       });
    });
    function filtrar(){
        
        var data = getData();
        loadData(data);
    }
    function getData(){
        var data = {};
        data.id_estado = $('#id_estado').val();
        data.activo = $('#activo').prop('checked')?1:0;
        data.codigo = $('#codigo').val();
        data.id_kit =  $('#id_activo').val();
        return data;
    }   
    function loadData(data){
         var $tabla = $('#tblKit');


        $.ajax({
               url: '<%= PathCfg.KIT_LIST %>',
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
                      
                        $('.btn-del').click(borrarKit);
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
    function borrarKit(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.KIT_DEL %>',{id:id},function(result) {     
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
//            html += wrapTag('td',convertirFecha(d.fecha_creacion),'');            
            html += wrapTag('td',d.codigo,'');
            html += wrapTag('td',d.nombre,'');
            html += wrapTag('td',d.rubro,'');
            html += wrapTag('td',d.subrubro,'');
            
            var htmlEstado = '<a href="#" data-toggle="modal" data-target="#mdlKitHistoria" data-index="'+ d.id + '" >' + d.estado + ' </a>';
            html += wrapTag('td',htmlEstado,'');            
            if(d.id_certificado!==0){
            var urlCertificado = '<%=PathCfg.CERTIFICADO_EDIT%>?id_modulo=20&id='+ d.id_certificado + '&id_activo=' + d.id;
           } else {
               var urlCertificado = '<%=PathCfg.CERTIFICADO%>?id_modulo=20&id_objeto=' + d.id;
           }
           var htmlCertificado = '<a href="' + urlCertificado +'">'+ d.certificado +'</a>';
           html += wrapTag('td',htmlCertificado,'');
           var htmlEdit = "<a href='<%= PathCfg.KIT_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
            if(d.activo )
           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           else var htmlDel = "";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }    
     </script>
     <%@include file="kit_historia_mdl.jsp"%>
<%@include file="tpl_footer.jsp"%>

</body>

</html>
