<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.nio.charset.Charset"%>
<%@page import="bd.Certificado"%>
<%@page import="bd.Activo"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Kit"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
<%
    Activo activo = null;
    Kit kit = null;
    Integer id_modulo = (Integer) request.getAttribute("id_modulo");
    Integer id_objeto;
    boolean moduloKit = id_modulo.equals(OptionsCfg.MODULO_KIT);
    if(moduloKit) {
        kit    = (Kit) request.getAttribute("kit");
        id_objeto = kit.getId();
    } else {
        activo = (Activo) request.getAttribute("activo");
        id_objeto = activo.getId();
    }
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
     <%@include  file="tpl_head.jsp" %>
      <style>
         .activo-heading{
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
                     <h3><span class="activo-heading">
                        <% if(moduloKit) { %>
                             Kit:    <a href="<%=PathCfg.KIT_EDIT%>?id=<%=kit.getId()%>"><%= kit.getCodigo() %> - <%= kit.getNombre()%></a> </span>                             
                        <% } else { %>
                             Activo: <a href="<%=PathCfg.ACTIVO_EDIT%>?id=<%=activo.getId()%>"><%= activo.getCodigo() %> - <%= activo.getDesc_larga()%></a> </span>
                        <% } %>
                     </h3>
                 </div>
             </div>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Certificados <a href="<%= PathCfg.CERTIFICADO_EDIT %>?id_modulo=<%=id_modulo%>&id_objeto=<%= id_objeto %>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                    <input type="hidden" id="id_objeto" name="id_objeto" value="<%= id_objeto %>">
                    <input type="hidden" id="id_modulo" name="id_modulo" value="<%= id_modulo %>">
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Certificados
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblCertificado">
                                    <thead>
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>Fecha</th>
                                            <th>Certificado</th>
                                            <th>Precinto</th>
                                            <th>Resultado</th>
                                            <th>Externo</th>
                                            <th>Observaciones</th>
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
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        var id_objeto = $('#id_objeto').val();
        var id_modulo = $('#id_modulo').val();
        loadDataCertificado({id_modulo:id_modulo,id_objeto:id_objeto});
    });
    function loadDataCertificado(data){
         var $tabla = $('#tblCertificado');


        $.ajax({
               url: '<%= PathCfg.CERTIFICADO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               //contentType: "charset=utf-8",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCertificado(data.Records));
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                      
                        $('.btn-del').click(borrarCertificado);
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

    function createTableCertificado(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',convertirFecha(d.fecha),'');
            html += wrapTag('td',d.codigo,'');
            html += wrapTag('td',d.precinto,'');
            html += wrapTag('td',d.resultado,'');
            var externo = (d.externo)?"Si":"No";
            html += wrapTag('td',externo,'');
            html += wrapTag('td',d.observaciones,'');
           var htmlEdit = "<a href='<%= PathCfg.CERTIFICADO_EDIT%>?id="+ d.id +"&id_modulo="+ d.id_modulo +"&id_objeto="+ d.id_objeto +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
    function borrarCertificado(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.CERTIFICADO_DEL %>',{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
