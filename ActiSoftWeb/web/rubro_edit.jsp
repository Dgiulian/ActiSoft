<%@page import="java.util.List"%>
<%@page import="bd.Rubro"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    Rubro rubro = (Rubro) request.getAttribute("rubro");
    boolean nuevo = false;
    if (rubro==null) {
        rubro = new Rubro();
        rubro.setId_estado(1);
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else{%>Editar<%}%> Rubro</h1>                    
                    <% if (!nuevo) {%>                     
<!--                    <div class="button-bar" >
                        <a   class="btn btn-info" href="<%=PathCfg.SUBRUBRO %>?id_rubro=<%= rubro.getId()%>"><i class="fa fa-fw"></i> Subrubros</a>                        
                    </div>-->
                    <% } %>
                </div>
                
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                  <%
                    String action = PathCfg.RUBRO_EDIT;
                    action += (!nuevo)?"?id="+rubro.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>">
                <form action="<%= PathCfg.RUBRO_EDIT%>" method="POST">
                    <% if(!nuevo) { %>
                    <input type="hidden" name="id" id ="id" value="<%= rubro.getId() %>">
                    <%}%>
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading">
                            Datos b&aacute;sicos del rubro
                        </div>
                        
                        <div class="panel-body">
                            <div class="row" >
                                   <div class="col-lg-6" >
                                        <div class="form-group">
                                            <label for="codigo">C&oacute;digo</label>
                                            <input class="form-control" name="codigo" id="codigo"  value='<%=rubro.getCodigo()%>'>
                                        </div>
                                        <div class="form-group">
                                            <label for="descripcion">Descripci&oacute;n</label>
                                            <input class="form-control" name="descripcion" id="descripcion" value='<%=StringEscapeUtils.escapeHtml4(rubro.getDescripcion())%>'>
                                        </div>
                                       <div class="form-group">
                                            <label for="descripcion">Descripci&oacute;n Opcional</label>
                                            <input class="form-control" name="desc_opcional" id="desc_opcional" value='<%=StringEscapeUtils.escapeHtml4(rubro.getDesc_opcional())%>'>
                                        </div>
                                        <div class="form-group">
                                            <label for="aplica_certificado">Aplica certificado</label>
                                            <% String checked = rubro.getAplica_certificado()!=0?"checked":""; %>
                                            <input type="checkbox" name="aplica_certificado" id="aplica_certificado" <%= checked%>>
                                            <!--<p class="help-block"></p>-->
                                        </div>
                                         <div class="form-group">
                                            <% String checked_activo = rubro.getId_estado()!=0?"checked":"";%>
                                            <label for="id_estado"> 
                                                Activo <input type="checkbox" class="checkbox checkbox-inline" name="id_estado" id="id_estado" value='1' <%=checked_activo%>></label>
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
//            $('#mdlRubroHistoria').on('show.bs.modal',function(){
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
