<%@page import="transaccion.TEquipo"%>
<%@page import="bd.Equipo"%>
<%@page import="transaccion.TPozo"%>
<%@page import="bd.Pozo"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Cliente"%>
<%@page import="bd.Site"%>
<%@page import="bd.Activo"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
  <%@page import="utils.PathCfg"%>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    if(cliente==null) cliente = new Cliente();
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
                    <h1 class="page-header">Site <a href="<%= PathCfg.SITE_EDIT %>?id_cliente=<%= cliente.getId() %>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <input type="hidden" value="<%=cliente.getId()%>">
               <div class="col-lg-3 " >
                    <div class="form-group " >
                         <label for="id_pozo">Pozo</label>
                         <select class="form-control" name="src_pozo" id="src_pozo" >
                             <option value="0">Todos</option>
                             <% for(Pozo pozo: new TPozo().getList()) { %>
                               <option value="<%=pozo.getId()%>" ><%= pozo.getNombre()%></option>
                             <% } %>
                         </select>
                     </div>
                </div>
               <div class="col-lg-3 " >
                    <div class="form-group " >
                         <label for="equipo">Equipo</label>
                         <select class="form-control" name="src_equipo" id="src_equipo" >
                             <option value="0">Todos</option>
                             <% for(Equipo equipo: new TEquipo().getList()) { %>

                               <option value="<%=equipo.getId()%>" ><%= equipo.getNombre()%></option>
                             <% } %>
                         </select>
                     </div>
                </div>
           </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Sites
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblParametro">
                                    <thead>
                                        <tr>
                                            <th>Locaci&oacute;n</th>
                                            <th>Area</th>
                                            <th>Pozo</th>
                                            <th>Equipo</th>
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
        //loadData({id_cliente:<%= cliente.getId()%>});
        filtrarSite();    
        $('#src_pozo').change(filtrarSite);
        $('#src_equipo').change(filtrarSite);
    });
    function filtrarSite(){
        var data = {};
        data.id_pozo = $('#src_pozo').val();
        data.id_equipo = $('#src_equipo').val();
        data.id_cliente = $('#id_cliente').val();
        loadDataSite(data);
    }
    function loadDataSite(data){
        var $tabla = $('#tblParametro');
        $.ajax({
               url: '<%= PathCfg.SITE_LIST %>',
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
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                      
                        $('.btn-del').click(borrarSite);
                        $tabla.DataTable({
                                responsive: true,
                                retrieve:true,
                                paging: false,
                                ordering:false,
                                searching:false,
                                lengthChange:false,
                                bInfo: false,
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });
                   }
               }
           });
    }
    function borrarSite(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.SITE_DEL %>',{id:id},function(result) {     
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
            html += wrapTag('td',d.nombre,'');
            html += wrapTag('td',d.area,'');
            html += wrapTag('td',d.pozo,'');
            html += wrapTag('td',d.equipo,'');            
           var htmlEdit = "<a href='<%= PathCfg.SITE_EDIT%>?id="+ d.id +"&id_cliente="+ d.id_cliente +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }

     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
