<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="transaccion.TRubro"%>
<%   
    HashMap<String,String> mapRubro = new HashMap<String,String>();
    HashMap<String,String> mapSubrubro = new HashMap<String,String>();
    
    mapRubro.put("id_estado","1");
    List<Rubro> lstRubros = new TRubro().getListFiltro(mapRubro);

    mapSubrubro.put("id_estado","1");
    mapSubrubro.put("id_rubro","1");
    List<Subrubro> lstSubrubros = new TSubrubro().getListFiltro(mapSubrubro);
%>

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
   <%@include  file="tpl_head.jsp" %>
</head>

<body>

    <div id="wrapper">
        <%@include file="tpl_navbar.jsp" %>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Activos <a href="<%= PathCfg.ACTIVO_EDIT%>" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>                    
                </div>

            </div>
            <!-- /.row -->
            <div class="row">
<!--                <div class="row">-->
                <div class="col-lg-4">
                    <div class="form-group">
                        <label class="" for="id_rubro">Rubro</label>
                        <select class="form-control" name="id_rubro" id="id_rubro">
                            <% for(Rubro r:lstRubros){%>
                              <option value="<%=r.getId()%>"><%=StringEscapeUtils.escapeHtml4(r.getDescripcion())%></option>
                            <%}%>
                            <option value="0">Todos</option>
                        </select>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="form-group">
                        <label class="" for="id_rubro">Subrubro</label>
                        
                        <select class="form-control" name="id_subrubro" id="id_subrubro"> 
                            <option value="0">Todos</option>
                             <% for(Subrubro sr:lstSubrubros){%>
                              <option value="<%=sr.getId()%>"><%=StringEscapeUtils.escapeHtml4(sr.getDescripcion())%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div clas="form-group">
                        <label for="">C&oacute;digo</label>
                        <span class="input-group">                                                                                            
                            <input type="text" class="form-control uppercase" name="codigo" id="codigo" size="20" value="">
                            <span class="input-group-addon" id="btnBuscar" ><span class="fa fa-search fa-fw"></span></span>
                        </span>
                    </div>
                </div>
<!--                <div class="form-group" >
                    <span  class="btn btn-default" data-toggle="modal" data-target="#mdlUpload"><span class="fa fa-search fa-fw"></span> Seleccionar</span>
                </div>  -->
         <!--</div>-->
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Activos
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-condensed" id="tblActivo">
                                    <thead>
                                        <tr>
                                            <th>C&oacute;digo</th>
                                            <th>Descripci&oacute;n</th>
                                            <th>Estado</th>
                                            <th>Stock</th>                                            
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
    var $invoker;
    $(document).ready(function() {
       loadDataActivo({id_rubro:1});
       $('#id_rubro').change(function(){rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val(),id_estado:1})});
       $('#id_subrubro').change(subrubroChange);
       
       
       $('#btnBuscar').click(buscar);
       
    });
    function buscar(){
        var $codigo = $('#codigo'); 
        var data =  ($codigo.val()!=="")? {codigo:$codigo.val()}:{id_rubro:$('#id_rubro').val(),id_subrubro:$('#id_subrubro').val()};
        loadDataActivo(data);
    }
    
    function createSelect(data){
        var html = "<option value='0'>Todos</option>";
        for (var i=0;i<data.length;i++){
            d = data[i];                
            html += "<option value='" + d.id + "'>" + d.descripcion+ "</option>";
        }
        return html;
    }
    function loadDataActivo(data){
        var $tabla = $('#tblActivo');
        $tabla.DataTable().destroy();
        $.ajax({
               url: '<%= PathCfg.ACTIVO_LIST %>',
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
                      
                        $('.btn-del').click(borrarActivo);
                        
                        $tabla.DataTable({
                                responsive: true,
                                retrieve: true,
                                paging: false,
                                ordering: true,
                                searching: false,
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
    function borrarActivo(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.ACTIVO_DEL %>',{id:id},function(result) {     
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
           
//           html +=wrapTag('td',d.familia,'');
//           html +=wrapTag('td',d.cod_rubro,'');
//           html +=wrapTag('td',d.rubro,'');
           html +=wrapTag('td',d.desc_larga,'');
//           html +=wrapTag('td',d.subrubro,'');
           
//           html +=wrapTag('td',d.medida,'');
//           html +=wrapTag('td',d.conexion,'');
//           html +=wrapTag('td',d.longitud,'');
//           html +=wrapTag('td',d.num_serie,'');
           var htmlEstado = '<a href="#" data-toggle="modal" data-target="#mdlActivoHistoria" data-index="'+ d.id + '" data-fecha_alta=' +  d.fecha_alta + '>' + d.estado + ' </a>';
           html +=wrapTag('td',htmlEstado,'');
           html += wrapTag('td',d.stock,'');
           
//           html +=wrapTag('td',d.num_rfid,'');
            var htmlEdit = "<a href='<%= PathCfg.ACTIVO_EDIT%>?id="+ d.id +"' class='btn btn-xs btn-circle  btn-warning'><span class='fa fa-edit fw'></span></a> ";
            var htmlDel = "<span href='' data-index='"+ d.id + "' class='btn btn-xs btn-danger btn-circle btn-del'><span class='fa fa-trash fw'></span></span>";
            html +='<td style="width:75px"  >' + htmlEdit + htmlDel + '</td>';
//            html +=wrapTag('td',htmlEdit + htmlDel,'');
           html +="</tr>";
       }      
       return html;
    }
   
     </script>
     <%@include  file="upload_mdl.jsp" %>
     <%@include  file="activo_historia_mdl.jsp" %>
     <%@include file="tpl_footer.jsp"%>
</body>

</html>
