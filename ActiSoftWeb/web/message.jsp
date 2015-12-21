<%
    String titulo  = (String ) request.getAttribute("titulo");
    String mensaje = (String) request.getAttribute("mensaje");
    String back    = (String) request.getAttribute("back");
    if (titulo==null){ titulo= "";}
    if (mensaje==null){ mensaje= "";}
    
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
                        <h1 class="page-header"><%=titulo%></h1>
                        <%=mensaje%>
                    </div>
                    <div class="col-lg-12">
                        <%if (back!=null) {%>
                        <a href="<%=back%>">Volver</a>
                        <%}%>
                    </div>
                    </div> <!-- /.row -->
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

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
