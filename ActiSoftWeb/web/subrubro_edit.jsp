<%@page import="java.util.List"%>
<%@page import="bd.Subrubro"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    Subrubro subrubro = (Subrubro) request.getAttribute("subrubro");
    Rubro rubro = (Rubro) request.getAttribute("rubro");
    boolean nuevo = false;
    if (subrubro==null) {
        subrubro = new Subrubro();
        nuevo = true;
    }      
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%= PathCfg.PAGE_TITLE %></title>
     <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <!-- Bootstrap Core CSS -->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
                                            <input class="form-control" name="codigo" id="codigo"  value="<%=subrubro.getCodigo()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="descripcion">Descripci&oacute;n</label>
                                            <input class="form-control" name="descripcion" id="descripcion" value="<%=subrubro.getDescripcion()%>">
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
