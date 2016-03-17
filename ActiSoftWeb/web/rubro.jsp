<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
<%!
     List<Rubro> lstRubros = new TRubro().getList();
     List<Subrubro> lstSubrubros = new TSubrubro().getList();

     private Rubro getRubroById(Integer id_rubro){
         for(Rubro r:lstRubros){
             if (r.getId() == id_rubro) return r;
         }
         return new Rubro();
     }
      private Subrubro getSrubroById(Integer id_subrubro){
         for(Subrubro s: lstSubrubros){
             if (s.getId() == id_subrubro) return s;
         }
         return new Subrubro();
     }
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
                    <h1 class="page-header">Rubros <a href="<%= PathCfg.RUBRO_EDIT%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Rubros
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div class="col-lg-12">
                                     <div class="form-group">
                                            <label for="id_estado">
                                            <input type="checkbox" class="checkbox checkbox-inline" name="id_estado" id="id_estado" value='1'> Mostrar eliminados</label>
                                    </div>
                                </div>
                                
                                <table class="table table-striped table-bordered table-condensed " id="tblRubro">
                                    <colgroup>
                                        <col style="width:10%;">
                                        <col style="width:20%;">
                                        <col style="">
                                        <col style="width:7%;">   
                                    </colgroup>
                                    <thead>
                                        
                                        <tr>
                                            <!--<th>Id</th>-->
                                            <th>C&oacute;digo</th>                                            
                                            <th>Descripci&oacute;n</th>
                                            <th>Subrubros</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%--<%@include file="subrubro_test.jsp" %>--%>
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
    <script src="js/invesoft.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        loadData({id_estado:1});
       $('#id_estado').change(function(){
           var id_estado = $('#id_estado').prop('checked')?0:1;
           loadData({id_estado:id_estado});
       });
    });
    function loadData(data){
         var $tabla = $('#tblRubro');
        $.ajax({
               url: '<%= PathCfg.RUBRO_LIST %>',
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
//                     $('#btnExcel').attr('href','Excel?type=GRC&pageNro='+page);
                      
                        $('.btn-del').click(borrarRubro);
                        $('.btn-del-sr').click(borrarSubrubro);
                        $('.btn-edit').click(editRubro);

                   }
               }
           });
    }
    function borrarRubro(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.RUBRO_DEL %>',{id:id},function(result) {     
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
    function borrarSubrubro(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.SUBRUBRO_DEL %>',{id:id},function(result) {     
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
//           html +=wrapTag('td',d.id,'');
           html +=wrapTag('td',d.codigo,'');
           html +=wrapTag('td',d.descripcion,'');
           html +=wrapTag('td',createTableSubrubro(d.id,d.subrubro));
           var htmlEdit = "<a href='<%= PathCfg.RUBRO_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
//          var htmlEdit = "<span data-index='"+ d.id + "' class='btn btn-xs btn-circle btn-warning btn-edit'><span class='fa fa-edit fw'></span></span> ";
           var htmlDel = "<span data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
           if(d.id_estado===0) htmlDel = "";
            html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
   function editRubro(){
    bootbox.prompt("Descripci&oacute;n del rubro",function(result){
        if(result){
            
        }
    });
   }
   function createTableSubrubro(id_rubro,data){       
    var html = "";
    
    html += '<table class="table table-bordered table-condensed">';
    html +=  '<colgroup>';
    html +=  '    <col span="1" style="width: 10%;">';
    html +=  '    <col span="1" style="width: 80%;">';
    html +=  '    <col span="1" style="width: 10%;">';
    html +=  ' </colgroup>';
    html += '        <thead>';
    html += '            <tr>';
//    html += '                <th>Id</th>' ;
    html += '                <th>C&oacute;digo</th>';  
    html += '                <th>Descripci&oacute;n </th>';  
    html += '                <th><a href="<%= PathCfg.SUBRUBRO_EDIT%>?id_rubro=' + id_rubro + '" class="btn btn-sm btn-primary pull-right"><span class="fa fa-file-o fa-fw"> </span> Nuevo</a></th>';  
    html += '            </tr>';  
    html += '        </thead>';  
    html += '        <tbody>';  
    for(var i = 0;i< data.length;i++){
        var d = data[i];
        html += '            <tr>';  
//        html += '                <td>'+ d.id +'</td>'; 
        html += '                <td>'+ d.codigo +'</td>';  
        html += '                <td>'+ d.descripcion +'</td>';  
        
        var htmlEdit = "<a href='<%= PathCfg.SUBRUBRO_EDIT%>?id_rubro="+ id_rubro + "&id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
        var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del-sr'><span class='fa fa-trash fw'></span></span>";
        if(d.id_estado===0) htmlDel = "";
        html +=wrapTag('td',htmlEdit + htmlDel,'');
        html += '            </tr>'; 
    } 
    html += '        </tbody>';  
    html += '    </table>';
    return html;
   
   }
     </script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
