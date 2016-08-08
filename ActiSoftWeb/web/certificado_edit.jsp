<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Activo"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="bd.Certificado"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="java.util.List"%>

<% 
    Certificado certificado = (Certificado) request.getAttribute("certificado");
    Activo activo = (Activo) request.getAttribute("activo");
    boolean nuevo = false;
    if (certificado==null) {
        certificado= new Certificado();    
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_APTO);
    }    
    nuevo = certificado.getId()==0;
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else {%>Editar<%}%> certificado</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
                
             <div class="row">
                 <div class="col-lg-12">
                     <h3><span class="activo-heading">Activo: <%= activo.getCodigo() %> - <%= activo.getDesc_larga()%> </span></h3>
                 </div>
             </div>
            <div class="row">
                 <%
                    String action = PathCfg.CERTIFICADO_EDIT;
                    action += "?id_activo=" + activo.getId();
                    action += (!nuevo)?"&id="+certificado.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>" enctype="multipart/form-data">                 
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del certificado </div>            
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-6" >
                            <fieldset>
                                <input type="hidden" name="id_activo" value="<%= activo.getId()%>">
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= certificado.getId()%>">                                    
                                <% }%>
                                
                                <div class="col-lg-4 " >
                                    <div class="form-group">
                                        <label for="fecha">Fecha alta</label>
                                        <input class="form-control date-picker" name="fecha" id="fecha" size="0"  value="<%=TFecha.formatearFechaBdVista(certificado.getFecha()) %>">
                                    </div>
                                </div>   
                                <div class="col-lg-4 " >
                                    <div class="form-group">
                                        <label for="fecha_desde">Fecha desde</label>
                                        <input class="form-control date-picker" name="fecha_desde" id="fecha_desde" size="0"  value="<%=TFecha.formatearFechaBdVista(certificado.getFecha_desde()) %>">
                                    </div>
                                </div>     
                                <div class="col-lg-4 " >
                                    <div class="form-group">
                                        <label for="fecha_hasta">Fecha hasta</label>
                                        <input class="form-control date-picker" name="fecha_hasta" id="fecha_hasta" size="0"  value="<%=TFecha.formatearFechaBdVista(certificado.getFecha_hasta()) %>">
                                    </div>
                                </div>  
                                 
                                    
                                    
                                <div class="col-lg-3 " >   
                                    <div class="form-group">
                                        <label for="codigo">Certificado</label>
                                        <input class="form-control" name="codigo" id="codigo" value="<%=certificado.getCodigo() %>">
                                    </div>
                                </div>
                                <!--<div class="col-lg-12 ">-->

                                <div class="col-lg-3 " >   
                                     <div class="form-group " >
                                          <label for="precinto">Precinto</label>
                                          <input class="form-control" name="precinto" id="precinto" value="<%= certificado.getPrecinto() %>">
                                      </div>                                        
                                 </div>
<!--                                     <div class="col-lg-3 " >
                                         
                                    </div>-->
                                <!--</div>-->
                               <!--<div class="form-group col-lg-12 " >-->
                                   <div class="col-lg-4 " >
                                        <div class="form-group " >
                                             <label for="id_resultado">Resultado</label>
                                             <select class="form-control" name="id_resultado" id="id_resultado" >
                                                  <% 
                                                    for(Option o:OptionsCfg.getEstadoCertificados()){                                                            
                                                        String selected = (certificado.getId_resultado() == o.getId())?"selected":"";
                                                    %>
                                                    <option value="<%= o.getId() %>" <%= selected %> > <%= o.getDescripcion() %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div>
                                   </div>
                                    <div class="col-lg-2 " >   
                                     <div class="form-group " >
                                         <div class="checkbox">
                                            <label for="externo">
                                            <% String checked = (certificado.getExterno(true))?"checked":"";%>
                                            <input class="" name="externo" id="externo" type="checkbox" value="1" <%= checked %>>
                                            Externo </label>
                                          </div>
                                      </div>
                                    </div>
                                  <div class="col-lg-12 " >   
                                     <div class="form-group " >                                         
                                            <label for="nombre_proveedor">Nombre proveedor </label>
                                            <input class="form-control" name="nombre_proveedor" id="nombre_proveedor" type="text" value="<%= certificado.getNombre_proveedor() %>" >
                                      </div>
                                    </div>           
                                      
                                  

                               <!--</div>-->
                            </fieldset>
                        </div>
                        <div class="col-lg-6 " >
                            <div class="col-lg-12" >   
                                    <% String display= "";
                                        if (!certificado.getArchivo().equals("")) { 
                                             display = "style='display: none'"; 
                                    %>
                                        <div class="form-group">                            
                                            <a href="<%=PathCfg.DOWNLOAD%>?type=certificado&id=<%=certificado.getId()%>" class="btn btn-success"><span> </span> Archivo asociado: <b><%= certificado.getArchivo() %></b></a>                                         
                                            <span class="btn btn-default" id="cambiarArchivo" name="cambiarArchivo">Cambiar</span>
                                        </div>
                                    <% }  %>
                                    
                                    <div class="form-group" <%=display %> id="selectFile">
                                        <label  for="archivo">Seleccionar archivo</label>
                                        <input class="form-control" type="file" size="60" id="archivo" name="archivo">                                        
                                    </div>
                                
                                </div>  
                                
                                <div class="col-lg-12 " >   
                                    <div class="form-group " >
                                         <label for="observaciones">Observaciones</label>
                                         <textarea class="form-control" name="observaciones" id="observaciones"><%= certificado.getObservaciones()%></textarea>
                                     </div>                                        
                                </div>
                            <div class="col-lg-12" id="interno">
                                <table class="table">
                                    <tbody>
                                        <tr>
            <td><label for="desmontaje">Desmontaje</label></td>
                <td><input type="checkbox" name="desmontaje" id="desmontaje" <% if (certificado.getDesmontaje()!=0) {%> checked <% } %></td>
            </tr>
            <tr>
                <td><label for="limpieza">Limpieza</label></td>
                <td><input type="checkbox" name="limpieza" id="limpieza" <% if (certificado.getLimpieza()!=0) {%> checked <% } %></td>
            </tr>
                    <tr>
                <td><label for="inspeccion_visual">inspecci&oacute;n visual</label></td>
                <td><input type="checkbox" name="inspeccion_visual" id="inspeccion_visual" <% if (certificado.getInspeccion_visual()!=0) {%> checked <% } %></td>
            </tr>
                    <tr>
                <td><label for="ultrasonido">Ultrasonido</label></td>
                <td><input type="checkbox" name="ultrasonido" id="ultrasonido" <% if (certificado.getUltrasonido()!=0) {%> checked <% } %></td>
            </tr>

            <tr>
                <td><label for="calibres">Calibres</label></td>
                <td><input type="checkbox" name="calibres" id="calibres" <% if (certificado.getCalibres()!=0) {%> checked <% } %></td>
            </tr>

            <tr>
                <td><label for="particulas_magentizables">Particulas magentizables</label></td>
                <td><input type="checkbox" name="particulas_magentizables" id="particulas_magentizables" <% if (certificado.getParticulas_magentizables()!=0) {%> checked <% } %></td>
            </tr>
            <tr>
            <td><label for="prueba_presion">Prueba de presi&oacute;n</label></td>
            <td><input type="checkbox" name="prueba_presion" id="prueba_presion" <% if (certificado.getPrueba_presion()!=0) {%> checked <% } %></label></td>
        </tr>
                <tr>
            <td><label for="pintado">Pintado</label></td>
            <td><input type="checkbox" name="pintado" id="pintado" <% if (certificado.getPintado()!=0) {%> checked <% } %></label></td>
        </tr>

                <tr>
            <td><label for="anillo_segmento">Reemplazo anillo reten de segmento</label></td>
            <td><input type="checkbox" name="anillo_segmento" id="anillo_segmento" <% if (certificado.getAnillo_segmento()!=0) {%> checked <% } %></label></td>
        </tr>


        <tr>
            <td><label for="segmentos">Reemplazo segmentos</label></td>
            <td><input type="checkbox" name="segmentos" id="segmentos" <% if (certificado.getSegmentos()!=0) {%> checked <% } %></label></td>
        </tr>
                <tr>
            <td><label for="tuerca">Reemplazo tuerca</label></td>
            <td><input type="checkbox" name="tuerca" id="tuerca" <% if (certificado.getTuerca()!=0) {%> checked <% } %></label></td>
        </tr>
                                    </tbody>
                                </table>
                            </div>
                               
                        </div>
                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.CERTIFICADO%>?id_activo=<%= activo.getId() %>">Cancelar</a>
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
            
            $('#btnSubmit').click(submitForm);
            $('#cambiarArchivo').click(function(){
                $('#selectFile').slideDown();                
            });   
            
            $('#externo').change(function(){
               if ($('#externo').prop('checked')){ 
                   $('#interno').slideUp()
               }else $('#interno').slideDown()
            });
            $('#externo').trigger('change');
        });
        function validar(){
            var $fecha = $('#fecha');
            var $fecha_hasta = $('#fecha_hasta');
            var $fecha_desde = $('#fecha_desde');
            
            if($fecha===undefined || $fecha.val()==="" || !validarFecha($fecha.val())){
                bootbox.alert("Debe ingresar la fecha del certificado");
                $fecha.parent().addClass("has-error");
                return false;
            } else $fecha.parent().removeClass("has-error");
            
            if($fecha_hasta===undefined || $fecha_hasta.val()==="" || !validarFecha($fecha_hasta.val())){
                bootbox.alert("Debe ingresar la fecha hasta del certificado");
                $fecha_hasta.parent().addClass("has-error");
                return false;
            } else $fecha_hasta.parent().removeClass("has-error");
            
            if($fecha_desde===undefined || $fecha_desde.val()==="" || !validarFecha($fecha_desde.val())){
                bootbox.alert("Debe ingresar la fecha desde del certificado");
                $fecha_desde.parent().addClass("has-error");
                return false;
            } else $fecha_desde.parent().removeClass("has-error");
            
            return true;
        }
                   
    </script>
    <!-- Modal -->

   
<%@include file="tpl_footer.jsp"%>    
</body>

</html>
