<%@page import="transaccion.TLocalidad"%>

<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    boolean nuevo = false;
    if (cliente==null) {
        cliente = new Cliente();
        nuevo = true;
    }
    
    List<Provincia> lProvincias = new TProvincia().getList();    
    Integer idProv = cliente.getId_provincia();
    List<Localidad> lLocalidades = new TLocalidad().recuperarLocalidades(idProv);
    Integer idLoc = cliente.getId_localidad();
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else{%>Editar<%}%> Cliente</h1>                    
                    <% if (!nuevo) {%>                     
                    <div class="button-bar" >
                        <a   class="btn btn-info" href="<%=PathCfg.SITE %>?id_cliente=<%= cliente.getId()%>"><i class="fa fa-map-marker fa-fw"></i> Sites</a>
                        <a   class="btn btn-info" href="#" data-toggle="modal" data-target="#mdlClienteHistoria"><i class="fa fa-history fa-fw"></i> Historia</a>
                    </div>
                    <% } %>
                </div>
                
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                  <%
                    String action = PathCfg.CLIENTE_EDIT;
                    action += (!nuevo)?"?id="+cliente.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>">
                <form action="<%= PathCfg.CLIENTE_EDIT%>" method="POST">
                    <% if(!nuevo) { %>
                    <input type="hidden" name="id" id ="id" value="<%= cliente.getId() %>">
                    <%}%>
                <div class="col-lg-12">
                    <div class="panel panel-default">
<!--                      <div class="panel-heading">
                            Datos b&aacute;sicos del cliente
                        </div>-->
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                           <li class="active"><a href="#tab1" data-toggle="tab">Datos b&aacute;sicos</a></li>
                           <li><a href="#tab2" data-toggle="tab">Domicilio</a></li>
                           <li><a href="#tab3" data-toggle="tab">Contacto</a></li>
                           <!--<li><a href="#tab4" data-toggle="tab">Datos financieros</a></li>-->
                           <li><a href="#tab5" data-toggle="tab">Observaciones</a></li>
                       </ul>
                        <div class="panel-body">
                            
                            <div class="tab-content ">
                                <div class="tab-pane active" id="tab1">
                                   <div class="col-lg-6" >
                                        <div class="form-group">
                                            <label for="nombre">Nombre</label>
                                            <input class="form-control" name="nombre" id="nombre"  value="<%=cliente.getNombre()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="cuit">CUIT</label>
                                            <input class="form-control" name="cuit" id="cuit" value="<%=cliente.getCuit()%>">
                                        </div>
                                       
                                        <div class="form-group">
                                            <label for="nombre_comercial">Nombre comercial</label>
                                            <input class="form-control"  name="nombre_comercial" id="nombre_comercial" value="<%=cliente.getNombre_comercial()%>">
                                            <!--<p class="help-block"></p>-->
                                        </div>

                                    </div>
                                    <div class="col-lg-6">
                                            <div class="form-group">
                                            <label for="dni">DNI</label>
                                            <input class="form-control" name="dni" id="dni"  value="<%=cliente.getDni()%>">
                                        </div>   
                                        <div class="form-group">
                                            <label>Activo</label>
                                            <% String checked = (cliente.getId_estado()!=0)?"checked":""; %>
                                            <input type="checkbox" name="id_estado" id="id_estado" <%= checked%> >
                                        </div>
                                    </div>
                                        
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                <div class="tab-pane" id="tab2" >
                                    <div class="col-lg-6"> 
                                        <div class="form-group">
                                            <label for="direccion_fisica">Direcci&oacute;n f&iacute;sica</label>
                                            <input class="form-control"  name="direccion_fisica" id="direccion_fisica"  value="<%=cliente.getDireccion_fisica()%>">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>C&oacute;digo postal</label>
                                            <input class="form-control"  name="codigo_postal" id="codigo_postal" value="<%=cliente.getCodigo_postal()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="id_provincia">Provincia</label>
                                            <select class="form-control" id="id_provincia" name="id_provincia">
                                                    <option value="0">Seleccione una provincia</option>
                                                    <%
                                                        for (Provincia prov : lProvincias) {
                                                    %>
                                                    <option value="<%=prov.getProv_id()%>" 
                                                            <% if (prov.getProv_id().equals(idProv)) {%> selected <% }%>

                                                            > <%=StringEscapeUtils.escapeHtml4(prov.getProv_descripcion())%></option>

                                                    <% }%>
                                                </select>            
                                        </div>
                                        <div class="form-group">
                                           <label for="id_localidad">Localidad</label>
                                           <select class="form-control" name="id_localidad" id="id_localidad" >
                                                <option value="0"> Seleccione su localidad</option>
                                                    <% if (lLocalidades != null) {
                                                            for (Localidad loc : lLocalidades) {%>
                                                            <option value="<%= loc.getLoc_id()%>"                                                                        
                                                                    <% if (loc.getLoc_id().equals(idLoc)) {%> selected <% }%>
                                                            ><%=StringEscapeUtils.escapeHtml4(loc.getLoc_descripcion())%></option>
                                                        <% }
                                                    }%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                             <label>Direcci&oacute;n legal</label>
                                            <input class="form-control" name="direccion_legal" id="direccion_legal" value="<%=cliente.getDireccion_legal()%>">
                                        </div>
                                    </div>
                                </div>
                                 <div class="tab-pane" id="tab3">
                                   <div class="col-lg-6">
                                     <div class="form-group">
                                            <label>Persona de contacto</label>
                                            <input class="form-control"  name="contacto" id="contacto" value="<%=cliente.getContacto()%>" >
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                     <div class="form-group">
                                            <label>Telefono fijo</label>
                                            <input class="form-control"  name="telefono" id="telefono" value="<%=cliente.getTelefono()%>">
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                     <div class="form-group">
                                            <label>Telefono de contacto</label>
                                            <input class="form-control"  name="celular" id="celular" value="<%=cliente.getCelular()%>">
                                            <!--<p class="help-block"></p>-->
                                     </div>
                                </div>
                                 </div>

                                  <div class="tab-pane" id="tab5">
                                      <div  class="col-lg-12">
                                       <div class="form-group">
                                            <label for="observaciones">Observaciones</label>
                                            <textarea class="form-control" rows="3"  name="observaciones" id="observaciones" ><%= cliente.getObservaciones()%></textarea>
                                        </div>
                                      </div>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                
                            </div>
                            <!-- /.row (nested) -->
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.CLIENTE%>">Cancelar</a>
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
//            $('#mdlClienteHistoria').on('show.bs.modal',function(){
//                var $id_cliente = $('#id');
//                if ($id_cliente!==null && $id_cliente.val()!=="")
//                    loadDataActivoHistoria({id_cliente:$id_cliente.val()});
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
    <%--<%@include file="cliente_historia_mdl.jsp" %>--%>
        <%@include file="tpl_footer.jsp" %>
</body>

</html>
