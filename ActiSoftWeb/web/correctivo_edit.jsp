<%@page import="bd.Activo"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="bd.Correctivo"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="java.util.List"%>
<%
    Correctivo correctivo = (Correctivo) request.getAttribute("correctivo");
    Activo activo = (Activo) request.getAttribute("activo");
    boolean nuevo = false;
    if (correctivo==null) {
        correctivo= new Correctivo();
    }
    nuevo = correctivo.getId()==0;
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else {%>Editar<%}%> correctivo</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                 <%
                    String action = PathCfg.CORRECTIVO_EDIT;
                    action += "?id_activo=" + activo.getId();
                    action += (!nuevo)?"&id="+correctivo.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>" >
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del correctivo </div>
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-6" >
                            <fieldset>
                                <input type="hidden" name="id_activo" value="<%= activo.getId()%>">
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= correctivo.getId()%>">
                                <% }%>
                                <div class="row">
                                <div class="col-lg-4 " >
                                    <div class="form-group">
                                        <label for="fecha">Fecha</label>
                                        <input class="form-control date-picker" name="fecha" id="fecha" size="0"  value="<%=TFecha.formatearFechaBdVista(correctivo.getFecha()) %>">
                                    </div>
                                </div>

                                <div class="col-lg-8 " >

                                </div>
                                    </div>
                                <div class="row">

                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="id_actividad">Actividad</label>
                                            <select class="form-control " name="id_actividad" id="id_actividad">
                                                <%
                                                    for(Option o:OptionsCfg.getTipoActividades()){
                                                        String selected = (correctivo.getId_actividad() == o.getId())?"selected":"";
                                                %>
                                                    <option value="<%= o.getId() %>" <%= selected %> > <%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %></option>
                                                    <% }%>
                                            </select>
                                        </div>
                                    </div>


    <!--                                     <div class="col-lg-3 " >

                                        </div>-->
                                    <!--</div>-->
                                   <!--<div class="form-group col-lg-12 " >-->
                                       <div class="col-lg-3 " >
                                            <div class="form-group " >
                                                 <label for="id_resultado">Resultado</label>
                                                 <select class="form-control" name="id_resultado" id="id_resultado" >
                                                      <%
                                                        for(Option o:OptionsCfg.getEstadoCertificados()){
                                                            String selected = (correctivo.getId_resultado() == o.getId())?"selected":"";
                                                        %>
                                                        <option value="<%= o.getId() %>" <%= selected %> > <%= StringEscapeUtils.escapeHtml4(o.getDescripcion()) %></option>
                                                        <% }%>
                                                 </select>
                                            </div>
                                       </div>
                                    </div>

<!--                                   <div class="col-lg-12 " >
                                    <div class="form-group " >
                                         <label for="observaciones">Observaciones</label>
                                         <textarea class="form-control" name="observaciones" id="observaciones"></textarea>
                                     </div>
                                    </div>-->

                               <!--</div>-->
                            </fieldset>
                        </div>
                        <div class="col-lg-6">
                              <div class="col-lg-12 " >
                                    <div class="form-group">
                                        <label for="detalle_problema">Problema</label>
                                        <textarea class="form-control" name="detalle_problema" id="detalle_problema" value=""><%=correctivo.getDetalle_problema()%></textarea>
                                    </div>
                                </div>
                                <!--<div class="col-lg-12 ">-->

                                <div class="col-lg-12 " >
                                    <div class="form-group">
                                        <label for="detalle_solucion">Soluci&oacute;n</label>
                                        <textarea class="form-control" name="detalle_solucion" id="detalle_solucion" value=""><%=correctivo.getDetalle_solucion()%></textarea>
                                    </div>
                                </div>
                        </div>


                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.CORRECTIVO%>?id_activo=<%= activo.getId() %>">Cancelar</a>
                            </div>
                        </div>
                          <!-- ./row -->
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

<!--    <script src="bower_components/jquery-form/jquery.form.min.js"></script>    -->

    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>

    <!-- Custom Theme JavaScript -->

    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>

    <script>
        $(document).ready(function(){
            $('.date-picker').mask('99/99/9999');

            $('.date-picker').datepicker({
                language: 'es'
            });
            $('#btnSubmit').click(submitForm);
            $('#cambiarArchivo').click(function(){
                $('#selectFile').slideDown();
            });
        });
        function validar(){
            var $fecha = $('#fecha');
            var $id_actividad = $('#id_actividad');
            var $id_resultado = $('#id_resultado');
            var $detalle_problema = $('#detalle_problema');
            var $detalle_solucion = $('#detalle_solucion');

           if(!validarCampo($fecha,"Ingrese la fecha",validarCampoFecha)) return false;
//           if(!validarCampo($fecha,"Ingrese la actividad",validarNoCero)) return false;
//           if(!validarCampo($fecha,"Ingrese el resultado",validarNoCero)) return false;

//            if(!validarCampo($detalle_problema,"Ingrese el problema",function(){return true;})) return false;
//            if(!validarCampo($detalle_solucion,"Ingrese la soluci&oacute;n",function(){return true;})) return false;

            return true;
        }

    </script>
    <!-- Modal -->



</body>

</html>
