
<%@page import="transaccion.TParametro"%>
<%@page import="bd.Parametro"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Rubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="bd.Activo"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    
    Activo activo = (Activo) request.getAttribute("activo");
    boolean nuevo = (activo ==null);
    if (nuevo) activo = new Activo();
    Rubro rubro = new TRubro().getById(activo.getId_rubro());
    if(rubro==null) rubro = new Rubro();
    
    List<Rubro> lstRubro = new TRubro().getList();
    
    List<Subrubro> lstSubrubro = new TSubrubro().getByRubroId(activo.getId_rubro());
    
    ArrayList<Option> lstEstados = OptionsCfg.getEstadoActivo();    
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
                    <h1 class="page-header"><% if (nuevo) {%> Nuevo <% } else {%> Editar<% }%>  activo </h1>
                    <% if (!nuevo) {%>                     
                    <div class="button-bar">
                       <% if(rubro.getAplica_certificado()!=0) {%>
                        <a   class="btn btn-info" href="<%=PathCfg.CERTIFICADO %>?id_activo=<%= activo.getId()%>"><i class="fa fa-certificate fa-fw"></i> Certificados</a>
                        <%}%>
                       <% if(activo.getAplica_compra()!=0) {%>
                            <a  class="btn btn-success" href="<%=PathCfg.COMPRA %>?id_activo=<%= activo.getId()%>"><i class="fa fa-money fa-fw"></i> Compras</a>
                        <% } %>
                        <a  class="btn btn-success" href="<%=PathCfg.CORRECTIVO %>?id_activo=<%= activo.getId()%>"><i class="fa  fa-stethoscope fa-fw"></i> Correctivo</a>
                       
                    </div>
                    <% } %>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <%
                String action = PathCfg.ACTIVO_EDIT;
                action += (!nuevo)?"?id="+activo.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>" enctype="multipart/form-data" >
                    <%if (!nuevo) {%> 
                    <input type="hidden" name="id" id="id" value="<%= activo.getId()%>"/>
                    <% } %>
                <div class="col-lg-12">
                    <div class="panel panel-default">
<!--                        <div class="panel-heading">-->
<!--                            <ul class="nav nav-tabs responsive" id="myTab">
                                <li class="active"><a href="#home">Datos b&aacute;sicos</a></li>
                                <li><a href="#profile">Profile</a></li>
                                <li><a href="#messages">Messages</a></li>
                            </ul>-->
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                           <li class="active"><a href="#tab1" data-toggle="tab">Datos b&aacute;sicos</a></li>
                           <li><a href="#tab2" data-toggle="tab">Medidas</a></li>
                           <li><a href="#tab3" data-toggle="tab">Identificaci&oacute;n</a></li>
                           <li><a href="#tab4" data-toggle="tab">Volumen</a></li>
                           <li><a href="#tab5" data-toggle="tab">Observaciones</a></li>
                           <li><a href="#tab6" data-toggle="tab">Imagenes</a></li>
                       </ul>
                        <!--</div>-->

                        <div class="panel-body">
                            <div  class="tab-content">
                                
                                <div class="tab-pane active" id="tab1">
                                    <div class="col-lg-6">
                                        <div class="row">
                                            <div class="col-lg-6" >
                                                <div class="form-group">
                                                    <label for="codigo" >C&oacute;digo</label>
                                                    <input name="codigo" id="codigo" class="form-control uppercase" value="<%= activo.getCodigo()%>">
                                                </div>
                                            </div>
                                            <div class="col-lg-6" >
                                                <div class="form-group">
                                                    <label for="codigo" >C&oacute;digo New</label>
                                                    <input name="codigo" id="codigo" class="form-control" value="<%= activo.getCodigoNew()%>" disabled>
                                                </div>
                                            </div>
                                        </div>

<!--                                       <div class="form-group">
                                           <label for="desc_corta">Descripci&oacute;n</label>
                                           <input name="desc_corta" id="desc_corta" class="form-control" value="<%= StringEscapeUtils.escapeHtml4(activo.getDesc_corta())%>">
                                       </div>-->
                                       
                                       <div class="form-group">
                                           <label for="desc_larga">Descripci&oacute;n </label>
                                           <input name="desc_larga" id="desc_larga" disabled class="form-control" value="<%= StringEscapeUtils.escapeHtml4(activo.getDesc_larga())%>">
                                       </div>
                                        <div class="form-group">
                                            <label for="id_rubro">Rubro</label>
                                            <select name="id_rubro" id="id_rubro" class="form-control">
                                                <option value="0">Seleccione el rubro</option>
                                                <% for(Rubro r:lstRubro){ 
                                                    String selected = (r.getId() == activo.getId_rubro())?"selected":"";
                                                %>
                                                    <option value="<%= r.getId()%>" <%= selected  %>>
                                                        <%= r.getCodigo() + " - " +  StringEscapeUtils.escapeHtml4(r.getDescripcion())%>
                                                    </option>
                                                <% } %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="id_subrubro">Subrubro</label>
                                            <select name="id_subrubro" id="id_subrubro" class="form-control" >
                                                 <option value="0">Seleccione el subrubro</option>
                                                <% for(Subrubro s:lstSubrubro){
                                                    String selected = (s.getId() == activo.getId_subrubro())?"selected":"";
                                                    %>                                                
                                                    <option value="<%= s.getId()%>"  <%= selected %>>
                                                        <%= s.getCodigo() + " - " + StringEscapeUtils.escapeHtml4(s.getDescripcion())%>
                                                    </option>
                                                <% } %>
                                                
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="marca">Marca</label>
                                            <input name="marca" id="marca" class="form-control" value="<%= StringEscapeUtils.escapeHtml4(activo.getMarca())%>">
                                        </div>
                                    <div class="col-lg-6">
                                            <div class="form-group">
                                                <label for="aplica_stock">Aplica Stock</label>
                                                <% String checked = (activo.getAplica_stock() ==1)?"checked":""; %>
                                                <input type="checkbox" name="aplica_stock" id="aplica_stock" value="<%=activo.getAplica_stock() %>" <%= checked %> >
                                            </div>
                                    </div>
                                    <div class="col-lg-6">
                                            <div class="form-group">
                                                <label for="aplica_stock">Aplica Compra</label>
                                                <% String chkCompra = (activo.getAplica_compra() ==1)?"checked":""; %>
                                                <input type="checkbox" name="aplica_compra" id="aplica_compra" value="<%=activo.getAplica_compra() %>" <%= chkCompra %> >
                                            </div>
                                    </div>
                                        
                                    
                                    <div class="col-lg-6">
                                             <div class="form-group">
                                                <label for="desc_larga">Divisa</label>
                                                <select  class="form-control" name="divisa" id="divisa"  value="<%=activo.getId_divisa()%>">
                                                    <%
                                                        for(int i=0;i<2;i++){
                                                            String divisa = i==0?"Dolares":"Pesos";
                                                            String selected = i== activo.getId_divisa()?"selected":"";
                                                    %>
                                                    <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                    <% }%>
                                                </select>
                                             </div>
                                        </div>
                                        <div class="col-lg-6">
                                             <div class="form-group">
                                                <label for="precio">Precio</label>
                                                <input name="precio" id="precio" class="form-control" value="<%=activo.getPrecio()%>">
                                             </div>
                                         </div>
                                         <div class="col-lg-5">
                                        <div class="form-group">
                                            <label for="id_estado">Estado</label>
                                            <input type="hidden" name="id_estado" id="id_estado" value="<%=activo.getId_estado()%>">
                                            <select name="id_estado" id="id_estado" class="form-control" disabled>
                                                <option value="0"></option>
                                                <% for(Option o:lstEstados){ 
                                                    String selected = o.getId() == activo.getId_estado()?"selected":"";
                                                %>
                                                    
                                                    <option value="<%= o.getId() %>" <%= selected  %>>
                                                        <%= StringEscapeUtils.escapeHtml4(o.getDescripcion())%>
                                                    </option>
                                                <% } %>
                                            </select>
                                        </div>
                                    </div>    
                                </div>
                                    
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                <div class="tab-pane" id="tab2">
                                    <div class="col-lg-6 ">
                                    <fieldset >
                                        <div class="form-group">
                                            <label for="anillo">Anillos</label>
                                            <input name="anillo" id="anillo" class="form-control"  value="<%= activo.getAnillo()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="medida">Medida</label>
                                            <input name="medida" id="medida" class="form-control"  value="<%= activo.getMedida()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="conexion">Conexion</label>
                                            <input name="conexion" id="conexion" class="form-control" value="<%= activo.getConexion()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="longitud">Longitud</label>
                                            <input name="longitud" id="longitud" class="form-control"  value="<%= activo.getLongitud()%>">
                                        </div>
                                        
                                    </fieldset>
                                    </div>
                                </div>
                                <div class="col-lg-12 tab-pane" id="tab3">
                                     <div class="col-lg-6" >
                                     <div class="form-group">
                                            <label for="num_serie" id="num_serie">N&uacute;mero de serie</label>
                                            <input name="num_serie" class="form-control" value="<%= activo.getNum_serie()%>">
                                    </div>
                                     <div class="form-group">
                                           <label for="stock_minimo">Stock minimo</label>
                                           <input name="stock_minimo" id="stock_minimo" class="form-control" value="<%= activo.getStock_minimo()%>">
                                       </div>
                                     </div>
                                    <div class="col-lg-6 " >
                                           <div class="form-group">
                                            <label for="num_rfid">RFID</label>
                                            <input name="num_rfid" id="num_rfid" class="form-control"  value="<%= activo.getNum_rfid()%>">
                                           </div>
                                       </div>
                                    <div class="col-lg-6 " >
                                       <div class="form-group">
                                           <label for="codigo_aduana">C&oacute;digo Aduana</label>
                                        <input name="codigo_aduana" id="codigo_aduana" class="form-control"  value="<%= activo.getCodigo_aduana()%>">
                                       </div>
                                   </div>
                                </div>
                                
                                <div class="col-lg-6 tab-pane" id="tab4">
                                    <div class="form-group">
                                        <label for="alto">Alto</label>
                                        <input name="alto" id="alto" class="form-control"  value="<%= activo.getAlto()%>">
                                    </div>
                                    <div class="form-group">
                                        <label for="ancho">Ancho</label>
                                        <input name="ancho" id="ancho" class="form-control"  value="<%= activo.getAncho()%>">
                                    </div>
                                    <div class="form-group">
                                        <label for="profundidad">Profundidad</label>
                                        <input name="profundidad" id="profundidad" class="form-control"  value="<%= activo.getProfundidad()%>">
                                    </div>
                                    <div class="form-group">
                                        <label for="peso">Peso</label>
                                        <input name="profundidad" id="peso" class="form-control"  value="<%= activo.getPeso()%>">
                                    </div>
                                    
                                </div>
                                <div class="col-lg-6 tab-pane" id="tab5">
                                    <div class="form-group">
                                      <label for="observaciones">Observaciones</label>
                                      <textarea name="observaciones" id="observaciones" class="form-control" ><%= activo.getObservaciones()%></textarea>
                                  </div>
                              </div>
                                <div class="tab-pane" id="tab6">
                                    <div class="col-lg-6">
                                         <% 
                                          String display1 = "";
                                          String display2 = "";
                                          String display3 = "";
                                          
                                        if (!"".equals(activo.getArchivo_1())) { 
                                             display1 = "style='display: none'";     
                                        %>                                  
                                        <div class="form-group">                            
                                            <!--<a href="<%=PathCfg.DOWNLOAD%>?type=activo&id=<%=activo.getId()%>" class="btn btn-success"><span> </span> Archivo asociado: <b><%= activo.getArchivo_1() %></b></a>-->
                                            <a href="<%=activo.getArchivo_2_url()%>" target="_blank">
                                            <img src="<%=activo.getArchivo_1_url()%>" width="250" height="250" >
                                            </a>
                                             <span class="btn btn-default cambiarArchivo" data-target="selectFile1">Cambiar</span>
                                        </div>
                                        <% }  %>
                                        <div class="form-group"  <%=display1 %> id="selectFile1" >
                                            <label for="archivo_1">Imagen 1</label>
                                            <input type="file" name="archivo_1" id="archivo_1" value="">
                                        </div>
                                      <% if (!"".equals(activo.getArchivo_2())) { 
                                             display2 = "style='display: none'";     
                                        %>                                  
                                        <div class="form-group">
                                            
                                            <!--<a href="<%=PathCfg.DOWNLOAD%>?type=activo&id=<%=activo.getId()%>" class="btn btn-success"><span> </span> Archivo asociado: <b><%= activo.getArchivo_2() %></b></a>-->                                         
                                            <a href="<%=activo.getArchivo_2_url()%>" target="_blank">
                                            <img src="<%=activo.getArchivo_2_url()%>" width="250" height="250" >
                                            </a>
                                            <span class="btn btn-default cambiarArchivo" data-target="selectFile2">Cambiar</span>
                                        </div>
                                        <% }  %>
                                         <div class="form-group" <%=display2 %>  id="selectFile2">
                                            <label for="archivo_2">Imagen 2</label>
                                            <input type="file" name="archivo_2" id="archivo_2" value="">
                                        </div>
                                        <% if (!"".equals(activo.getArchivo_3())) { 
                                             display3 = "style='display: none'";     
                                        %>                                  
                                        <div class="form-group">                            
                                            <!--<a href="<%=PathCfg.DOWNLOAD%>?type=activo&id=<%=activo.getId()%>" class="btn btn-success"><span> </span> Archivo asociado: <b><%= activo.getArchivo_3() %></b></a>-->                                         
                                            <a href="<%=activo.getArchivo_3_url()%>" target="_blank">
                                                <img src="<%=activo.getArchivo_3_url()%>" width="250" height="250" >
                                            </a>
                                             <span class="btn btn-default cambiarArchivo" data-target="selectFile3">Cambiar</span>
                                            
                                        </div>
                                        <% }  %>
                                        
                                        
                                            
                                        
                                            
                                        <div class="form-group" <%=display3 %>  id="selectFile3">
                                            <label for="archivo_3"  >Imagen 3</label>
                                            <input type="file" name="archivo_3" id="archivo_3">
                                        </div>
                                    </div>
                                </div>
                                    
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.ACTIVO%>">Cancelar</a>
                            </div>
                            </form>
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
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>    
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
    $(document).ready(function() {
        $('#tabs').tab();
        $('#id_rubro').change(selRubroChange);
       
        $('.cambiarArchivo').click(function(){
            target = $(this).data('target');
            if(target!==undefined)
                $('#' + target).slideDown();
        });
        
    });
    function selRubroChange(){
        $value= $(this).val();   
        if ($value==0) {$('#id_subrubro').html("");return;}
        loadDataSubrubro({id_rubro:$value});
    }
    function loadDataSubrubro(data){    
        $.ajax({
               url: '<%= PathCfg.SUBRUBRO_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               success: function(data) {
                   if(data.Result === "OK") {
                        $('#id_subrubro').html(createSelectSubrubro(data.Records));                       
                   }
               }
        });
    }
    function createSelectSubrubro(data){
        var html = "<option value='0'>Seleccione el subrubro</option>";
        for (var i=0;i<data.length;i++){
            d = data[i];                
            html += "<option value='" + d.id + "'>" + d.descripcion+ "</option>";
        }
        return html;
    }
    </script>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
