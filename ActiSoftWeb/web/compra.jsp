<%@page import="bd.Activo"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
    <%
        Activo activo = (Activo) request.getAttribute("activo");
        if(activo==null) activo = new Activo();
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
                     <h3><span class="activo-heading">Activo: <a href="<%=PathCfg.ACTIVO_EDIT%>?id=<%=activo.getId()%>"><%= activo.getCodigo() %> - <%= activo.getDesc_larga()%></a> </span></h3>
                 </div>
             </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Compras <a href="<%= PathCfg.COMPRA_EDIT %>?id_activo=<%= activo.getId() %>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                </div>
            </div>
          
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Compras
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblCompra">
                                    <thead>
                                        <tr>
                                            <th>Fecha</th>
                                            <th>Cantidad</th>
                                            <th>Divisa</th>
                                            <th>Precio</th>                                            
                                            <th>Total</th>
                                            <th>Proveedor</th>
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
        loadDataCompra({id_activo:<%= activo.getId()%>});
    });
    function loadDataCompra(data){
         var $tabla = $('#tblCompra');
         $.ajax({
               url: '<%= PathCfg.COMPRA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableCompra(data.Records));

                        $('.btn-del').click(borrarCompra);
                        $tabla.DataTable({
                                responsive: true,
                                retrieve:true,
                                paging: false,
                                ordering: true,
                                searching: false,
                                lengthChange: false,
                                bInfo:false,
                                order: [ 0, "desc" ],
                                columnDefs: [                                   
                                    { type: 'date-uk', targets: 0 },
                               ],
                                language: {
                                    url:'bower_components/datatables-plugins/i18n/Spanish.json',
                                }
                        });
                   }
               }
           });
    }
    
    function createTableCompra(data){
        var html = "";
        for(var i = 0;i< data.length;i++){
           html +="<tr class=''>";
           d = data[i];
            html += wrapTag('td',convertirFecha(d.fecha),'');
            var divisa = d.id_divisa===0?"Dolar":"Peso";
            html += wrapTag('td',d.cantidad,'');
            html += wrapTag('td',divisa,'');
            html += wrapTag('td',d.precio_unit,'');
            html += wrapTag('td',d.precio_tot,'');
            html += wrapTag('td',d.proveedor,'');
           var htmlEdit = "<a href='<%= PathCfg.COMPRA_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
           var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
    function borrarCompra(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.COMPRA_DEL %>',{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
