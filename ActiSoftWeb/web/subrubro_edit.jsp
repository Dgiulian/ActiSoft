<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="bd.Subrubro"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    Subrubro subrubro = (Subrubro) request.getAttribute("subrubro");
    Rubro rubro = (Rubro) request.getAttribute("rubro");
    boolean nuevo = false;
    if (subrubro==null) {
        subrubro = new Subrubro();
        subrubro.setId_estado(1);
        nuevo = true;
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else{%>Editar<%}%> Subrubro</h1>                    
                    <% if (!nuevo) {%>                     
<!--                    <div class="button-bar" >
                        <a   class="btn btn-info" href="<%=PathCfg.SUBRUBRO %>?id_rubro=<%= subrubro.getId()%>"><i class="fa fa-fw"></i> Subrubros</a>                        
                    </div>-->
                    <% } %>
                </div>
                
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
               <%
                    String action = PathCfg.SUBRUBRO_EDIT;
                    action += "?id_rubro="+rubro.getId();
                    action += (!nuevo)?"&id="+subrubro.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>">
                <form action="<%= PathCfg.RUBRO_EDIT%>" method="POST">
                    <input type="hidden" name="id_rubro" id ="id_rubro" value="<%= rubro.getId() %>">
                    <% if(!nuevo) { %>
                    <input type="hidden" name="id" id ="id" value="<%= subrubro.getId() %>">
                    
                    <%}%>
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading">
                            Datos b&aacute;sicos del subrubro
                        </div>
                        
                        <div class="panel-body">                           
                            <div class="row" >
                                   <div class="col-lg-6" >
                                        <div class="form-group">
                                            <label for="codigo">C&oacute;digo</label>
                                            <input class="form-control" name="codigo" id="codigo"  value='<%=subrubro.getCodigo()%>'>
                                        </div>
                                        <div class="form-group">
                                            <label for="descripcion">Descripci&oacute;n</label>
                                            <input class="form-control" name="descripcion" id="descripcion" value='<%=StringEscapeUtils.escapeHtml4(subrubro.getDescripcion())%>'>
                                        </div>
                                         <div class="form-group">
                                            <label for="descripcion">Descripci&oacute;n Opcional</label>
                                            <input class="form-control" name="desc_opcional" id="desc_opcional" value='<%=StringEscapeUtils.escapeHtml4(subrubro.getDesc_opcional())%>'>
                                        </div>
                                        <div class="form-group">
                                            <% String checked = subrubro.getId_estado()!=0?"checked":"";%>
                                            <label for="id_estado">
                                                Activo <input type="checkbox" class="checkbox checkbox-inline" name="id_estado" id="id_estado" value='1' <%=checked%>> </label>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">                                        
                                    </div>
                                        
                                </div>
                                
                                  
                                <!-- /.col-lg-6 (nested) -->
                                
                            <!-- /.row (nested) -->
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.RUBRO%>">Cancelar</a>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
                </form>
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
    <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>    
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>    
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>        

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>
    <script>
        $(document).ready(function(){
            $('#id_provincia').change(function() {
                    $('#id_localidad').html("");
                    var url = "<%= PathCfg.LOCALIDAD_LIST%>?idProv=" + $(this).val();
                    $.ajax({
                        url: url,
                        success: function(data) {
                            console.log(data.Result);
                            var html = "";
                            if(data.Result==="OK"){
                               $('#id_localidad').html(createSelect(data.Records));                               
                            }
                        }
                    });
            });
//            $('#mdlSubrubroHistoria').on('show.bs.modal',function(){
//                var $id_rubro = $('#id');
//                if ($id_rubro!==null && $id_rubro.val()!=="")
//                    loadDataActivoHistoria({id_rubro:$id_rubro.val()});
//            });
        });
        function createSelect(data){
            var html = "<option value='0'></option>";
            for (var i=0;i<data.length;i++){
                d = data[i];
                html += "<option value='" + d.loc_id + "'>" + d.loc_descripcion+ "</option>";
            }
            return html;
        }
        
    </script>
    <%--<%@include file="rubro_historia_mdl.jsp" %>--%>
        <%@include file="tpl_footer.jsp" %>
</body>

</html>
