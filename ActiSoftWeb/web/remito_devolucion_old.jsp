<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TCliente"%>
<%@page import="bd.Activo"%>
<%@page import="transaccion.TActivo"%>
<%@page import="transaccion.TContrato"%>
<%@page import="bd.Contrato"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bd.Remito_detalle"%>
<%@page import="bd.Remito"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<%    
    Remito remito = (Remito) request.getAttribute("remito");
    if (remito==null) remito = new Remito();
    
    ArrayList<Remito_detalle >  detalle = (ArrayList<Remito_detalle>) request.getAttribute("detalle");
    
    Contrato contrato = new TContrato().getById(remito.getId_contrato());
    if (contrato==null) contrato = new Contrato();
    
    Cliente cliente = new TCliente().getById(remito.getId_cliente());
    if(cliente==null) cliente = new Cliente();
    String tipoRemito="devolución";
//    for(Option o:OptionsCfg.getTipoRemitos()) {
//        if (o.getId()==remito.getId_tipo_remito()) {tipoRemito=o.getDescripcion();
//            break;
//            }
//    }
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
    <link href="bower_components/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet">
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
                    <h1 class="page-header">Remito de <%= StringEscapeUtils.escapeHtml4(tipoRemito) %></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <%
                    String action = PathCfg.REMITO_DEV;
                    action += "?id="+remito.getId();
                %>                
                <form action="<%= action%>" method="POST"  role="form">
                
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del remito </div>            
                      <div class="panel-body">

                        <input type='hidden' name="id" id="id" value="<%= remito.getId() %>">
                        <div class="row">
<!--                            <div class="col-lg-2 " >
                                <div class="form-group ">
                                    <label for="punto_venta">Punto de venta</label>                                       
                                   <input class="form-control" name="punto_venta" id="punto_venta" >                                        
                               </div>
                            </div>-->
                            <div class="col-lg-3">
                                  <div class="form-group">
                                      <label class="" for="numero_devolucion">N&uacute;mero de remito de devoluci&oacute;n</label>
                                      <input class="form-control" type="text" name="numero_devolucion" id="numero_devolucion">
                                  </div>
                            </div>
                            <div class="col-lg-3 " >
                                <div class="form-group ">
                                    <label for="fecha">Fecha remito</label>                                       
                                   <input class="form-control date-picker" name="fecha" id="fecha" >                                        
                               </div>                                       
                            </div>                       
                            <div class="col-lg-1">
                                <div class="form-group">
                                            <label for="tipoEntrega">Tipo</label>
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="tipoEntrega" id="rdTotal" value="0" checked>Definitivo
                                                </label>
                                            </div>   
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="tipoEntrega" id="rdParcial" value="1" ="">Transitorio
                                                </label>
                                            </div>
                                        </div>
                            </div>
                                <div class="col-lg-3">
                                 <div class="form-group">
                                      <label class="" for="numero_entrega">N&uacute;mero de remito de entrega</label>
                                      <input class="form-control" type="text" name="numero_entrega" id="numero_entrega" disabled>
                                  </div>
                              </div>

                        </div>
                          <div class="row">
                            <div class="col-lg-12">
                                <h3>Activos</h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblRemito">
                                    <thead>
                                        <tr>
                                            <th>Posici&oacute;n</th>
                                            <th>C&oacute;digo</th>
                                            <th>Descripci&oacute;n</th>
                                            <th>Cantidad</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <% TActivo ta = new TActivo();
                                            for(Remito_detalle rm: detalle){ 
                                                Activo activo = ta.getById(rm.getId_activo());
                                        %>
                                        <tr>
                                            <input type="hidden" name="detalle" value="<%= rm.getId() %>">
                                            <td><%= rm.getPosicion() %></td>
                                            <td><%= activo.getCodigo()%></td>
                                            <td><%= StringEscapeUtils.escapeHtml4(activo.getDesc_larga())%></td>
                                            <td><%= rm.getCantidad()%></td>
                                            <td><span class="btn btn-xs btn-circle btn-danger btnDelActivo" > <span class="fa fa-minus fw"></span></span></td>
                                        </tr>
                                        <% } %>
                                    </tbody>
<!--                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tfoot>-->
                                </table>
                                 <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                            
                            <a type="reset" href="<%=PathCfg.REMITO%>" class="btn btn-default" >Cerrar</a>
                                <!--<button type="submit" class="btn btn-default">Guardar</button>-->
<!--                            <button type="reset" class="btn btn-default">Reset Button</button>-->
                            </div>
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
            $('.date-picker').datepicker({
                language: 'es',
            }); 
            $('.date-picker').mask('99/99/9999');            
            $('.btnDelActivo').click(deleteActivo);
            $('#btnSubmit').click(submitForm);      
            $('#rdTotal').change(radioChange);
            $('#rdParcial').change(radioChange);
        });
        function radioChange(){
            $('#rdTotal').change(radioChange);
            $('#rdParcial').change(radioChange);
            var parcial = $('#rdParcial').prop('checked');
            $('#numero_entrega').prop('disabled',!parcial);
        }
        function validar(){
            var $num_dev = $('#numero_devolucion');
            var $num_ent = $('#numero_entrega');
            var $rd_parcial = $('#rdParcial');
            var $rd_total = $('#rdTotal');
            var $fecha = $("#fecha");
            var parcial = $rd_parcial.prop('checked');
            
            if($num_dev===undefined  || $num_dev.val()===""){
                bootbox.alert("Debe ingresar el n&uacute;mero de remito de devoluci&oacute;n");
                $num_dev.parent().addClass("has-error");
                return false;
             } else $num_dev.parent().removeClass("has-error");
             
             if(parcial && ($num_ent===undefined  || $num_ent.val()==="")){
                bootbox.alert("Debe ingresar el n&uacute;mero del nuevo remito de entrega");
                $num_ent.parent().addClass("has-error");
                return false;
             } else $num_ent.parent().removeClass("has-error");
              
            if ($fecha === null || $fecha.val() === "" || !validarFecha($fecha.val())) {
                bootbox.alert("Ingrese la fecha del remito");
                $fecha.parent().addClass("has-error");
                return false;
            } else $fecha.parent().removeClass("has-error");
            
             return true;
        }
        function deleteActivo(){
//          bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
//             if(result) {            
                var $tr = $(this).parent().parent();
                $tr.remove();         
//                }
//            });
        }
        </script>
        <%@include file="tpl_footer.jsp"%>
</body>

</html>