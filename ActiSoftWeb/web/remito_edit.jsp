<%@page import="transaccion.TActivo"%>
<%@page import="bd.Activo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.TFecha"%>
<%@page import="transaccion.TSite"%>
<%@page import="bd.Site"%>
<%@page import="transaccion.TCliente"%>
<%@page import="transaccion.TCliente"%>
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
    List<Remito_detalle> detalle = (List<Remito_detalle>) request.getAttribute("detalle");
    boolean nuevo = false;
    if(remito==null){
        remito = new Remito();
        nuevo= true;
    }
    if(detalle==null) detalle = new ArrayList<Remito_detalle>();

    Contrato contrato = new TContrato().getById(remito.getId_contrato());
    if(contrato==null) contrato=new Contrato();

    Cliente cliente = new TCliente().getById(remito.getId_cliente());
    if(cliente==null) cliente = new Cliente();

    Site site = new TSite().getById(remito.getId_site());
    if(site==null) site = new Site();
    TActivo ta = new TActivo();
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<% } else { %> Editar <% } %> remito de entrega</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <form action="<%= PathCfg.REMITO_EDIT%>" method="POST"  role="form">
                    <input type="hidden" name="id" id="id" value="<%=remito.getId()%>">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del remito </div>
                      <div class="panel-body">
                          <div class="row">
                               <div class="col-lg-3 " >
                                <div class="form-group">
                                <span class="input-group">
                                    <label for="contrato">N&uacute;mero contrato</label>
                                    <span class="input-group">
                                    <span class="input-group-addon"  data-toggle="modal" data-target="#mdlContrato"><span class="fa fa-search fa-fw"></span></span>
                                    <input type="text" class="form-control"  name="contrato" id="contrato" placeholder="N&uacute;mero de contrato" size="20" value="<%= contrato.getNumero()%>">
                                    </span>
                                    <input type="hidden" name="id_contrato" id="id_contrato" value="<%=contrato.getId()%>">
                                </span>
                                </div>
                               </div>
                              <div class="col-lg-2 " >
                                    <div class="form-group ">
                                        <label for="punto_venta">Punto de venta</label>
                                       <input class="form-control" name="punto_venta" id="punto_venta" value="<%=remito.getPunto_venta()%>">
                                   </div>

                                 </div>
                                   <div class="col-lg-3 " >
                                    <div class="form-group ">
                                        <label for="numero">N&uacute;mero remito</label>
                                       <input class="form-control" name="numero" id="numero" value="<%= remito.getNumero()%>">
                                   </div>

                                 </div>
                              <div class="col-lg-3 " >
                                    <div class="form-group ">
                                        <label for="fecha">Fecha remito</label>
                                        <input class="form-control date-picker" name="fecha" id="fecha" value="<%= TFecha.formatearFechaBdVista(remito.getFecha())%>" >
                                   </div>

                                 </div>
                          </div>

                          <hr>
                      <div class="row">
                        <div class="col-lg-6 " >
                             <h3 class="">Cliente</h3>
                             <input type="hidden" name="idCliente" id="idCliente" value="<%= cliente.getId() %>" >
                             <fieldset disabled>
                                 <div class="form-group">
                                    <label for="cliente_nombre">Nombre</label>
                                    <input class="form-control" name="cliente_nombre" id="cliente_nombre" value="<%= cliente.getNombre_comercial()%>">
                                </div>
                                 <div class="form-group">
                                    <label for="cliente_direccion">Direcci&oacute;n</label>
                                    <input class="form-control" name="cliente_direccion" id="cliente_direccion"  value="<%= cliente.getDireccion_fisica() %>">
                                </div>
                                 <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_iva">IVA</label>
                                           <select class="form-control" name="cliente_iva" id="cliente_iva" >
                                           <option value="0"></option>
                                           <% for(Option o:OptionsCfg.getClasesIva()){
                                           String selected = "";%>
                                           <option value="<%=o.getId()%>" <%= selected %>><%= o.getDescripcion()%></option>
                                           <%  } %>
                                           %>
                                       </select>
                                   </div>
                                 </div>
                                  <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_cuit">CUIT</label>
                                       <input class="form-control" name="cliente_cuit" id="cliente_cuit"  value="<%= cliente.getCuit() %>" >
                                   </div>
                                 </div>

                            </fieldset>
                            <div class="form-group" >
                                <span  class="btn btn-default" id="btnSelCliente" data-toggle="modal" data-target="#mdlCliente"><span class="fa fa-search fa-fw"></span> Seleccionar</span>
                            </div>

                        </div>
                          <!-- ./Cliente -->
                        <div class="col-lg-6 " >
                            <h3 class="">Locaci&oacute;n</h3>
                            <input type="hidden" name="id_site" id="id_site" value="<%=site.getId()%>" />
                             <fieldset disabled>
                                <div class="form-group">
                                    <label for="">Area</label>
                                    <input class="form-control" name="area" id="area" value="<%=site.getArea()%>" >
                                </div>
                                <div class="col-lg-6 " >
                                    <div class="form-group">
                                        <label for="">Pozo</label>
                                        <input class="form-control" name="pozo" id="pozo" value="<%=site.getPozo()%>" >
                                    </div>
                                </div>
                                <div class="col-lg-6 " >
                                    <div class="form-group-sm">
                                        <label for="">Equipo</label>
                                        <input class="form-control" name="equipo" id="equipo" value="<%=site.getEquipo()%>" >
                                    </div>
                                </div>
                                </fieldset>
                             <div class="form-group-sm " >
                                <span  class="btn btn-default btnSelSite" data-toggle="modal" data-target="#mdlSite"><span class="fa fa-search fa-fw"></span> Seleccionar</span>
                                <span  class="btn btn-primary btnSiteEdit" id="btnSiteNuevo" ><span class="fa fa-file fa-fw"></span> Nuevo</span>
                              </div>
                        </div>
                          <!-- ./Locacion -->
                        <!-- /.col-lg-6 (nested) -->
                        <!-- /.row (nested) -->
                        </div>
                          <!-- ./row -->
                          <hr>

                        <div class="row">
                            <div class="col-lg-12">
                                <h3>Activos <span class="btn btn-default" id="btnAgregar"> Agregar</span>
                                    <span class="btn btn-default "  data-toggle="modal" data-target="#mdlContratoView"><span class="fa fa-file fa-fw"></span>Ver contrato</span>
                                    <!--<span class="btn btn-default" id="btn"> Ver contrato</span></h3>-->
                                    </h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblRemito">
                                    <thead>
                                        <tr>
                                            <th>C&oacute;digo</th>
                                            <th>Posici&oacute;n</th>
                                            <th>Descripci&oacute;n</th>
                                            <th>Cantidad</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <% Activo activo = null;
                                            for(Remito_detalle d:detalle) {
                                            activo = ta.getById(d.getId_activo());
                                        %>
                                        <tr>
                                            <td style="width:150px">
                                                <span class="input-group">
                                                <span class="input-group-addon" data-toggle="modal" data-target="#mdlActivo"><span class="fa fa-search fa-fw"></span></span>
                                                <input type="text" class="form-control inCodigo" data-rubro="" data-subrubro="" name="codigoActivo" placeholder="C&oacute;digo" size="20" value="<%= activo.getCodigo()%>"></span>
                                            </td>
                                            <td style="width:80px">
                                                    <input type="number" name="posicion" class="form-control" size="5" min="0" value="<%= d.getPosicion() %>" disabled>
                                                    <input type="hidden" name="posicion" class="form-control"  min="0" value="<%= d.getPosicion() %>">
                                                </td>


                                                <td class="descripcion"><%= StringEscapeUtils.escapeHtml4(activo.getDesc_larga())%></td>
                                                <td style="width:90px">
                                                    <span class="form-group">
                                                    <input class="form-control cant" name="cantActivo" id="" type="number" step="any" min="0" value="<%=d.getCantidad()%>"></span>
                                                </td>

                                                <td style="width:60px">
                                                        <span class="btn btn-xs btn-circle btn-danger btnDelActivo"> <span class="fa fa-minus fw"></span></span>
                                                </td>
                                            </tr>
                                        <% } %>
                                        <%--<%@include file="remito_test.jsp" %>--%>
                                    </tbody>
<!--                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tfoot>-->
                                </table>


                            </div>
                                <div class="col-lg-12">
                                   <div class="form-group">
                                       <label for="observaciones">Observaciones</label>
                                       <textarea name="observaciones" id="observaciones" class="form-control"></textarea>
                                   </div>
                               </div>
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.REMITO%>">Cancelar</a>
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
    <script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyD-jKlyAoMh8GxoIyaDbuvhI1WVw8XSpGA"></script>
    <script src="bower_components/mapplace/maplace-0.1.3.js"></script>

    <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/invesoft.js"></script>

    <script type="text/javascript">
        var $pos_item;
        var $tr;
        var $invoker = null;

    $(document).ready(function(){


            $('#btnSelActivos').click(selActivos);

            $('.date-picker').datepicker({
                language: 'es'
            });
            $('.date-picker').mask('99/99/9999');
            $('#selTodos').change(function(e){
                selTodos('input.chkSelActivo',$(this).prop('checked'));
            });

            $('#mdlActivo').on('show.bs.modal', function (e) {
                 var $id_contrato = $('#id_contrato') ;
                 if ($id_contrato===null || $id_contrato.val()===""){
                      $('#mdlActivo').modal('hide');
                     bootbox.alert("Ingrese el n&uacute;mero de contrato");
                     return;
                }
                $invoker = $(e.relatedTarget);
                $tr = $invoker.parent().parent().parent();
                $codigo = $tr.find('input[name="codigoActivo"]');

                $('#id_rubro').prop('disabled',false);
                $('#id_subrubro').prop('disabled',false);
                if ($codigo!==undefined){
                    if ( $codigo.data('rubro')!== undefined && $codigo.data('rubro')!==''){
//                        $('#id_rubro').val($codigo.data('rubro'));
//                        $('#id_rubro').prop('disabled',true);
                    }
                    if ( $codigo.data('subrubro')!== undefined && $codigo.data('subrubro')!=='' ) {
//                        $('#id_subrubro').val($codigo.data('subrubro'));
//                        $('#id_subrubro').prop('disabled',true);
                    }
                }
                var $id_rubro = $('#id_rubro');
                var $id_subrubro = $('#id_subrubro');
                $id_contrato = $('#id_contrato');
                if ($id_contrato===null || $id_contrato.val()==="0" ){
                     bootbox.alert("Ingrese el n&uacute;mero de contrato");
                     return;
                }
                var data = { id_rubro: $id_rubro.val(),
                              id_subrubro: $id_subrubro.val(),
                              id_contrato: $id_contrato.val(),
                };

                loadDataActivo(data);
                loadDataKit(data);



            });
             $('#mdlActivo').on('hide.bs.modal', function (e) {
                 if($invoker!==null)
                    $invoker.parent().find('input').focus();

            });

            $('#mdlCliente').on('shown.bs.modal', function (e) {
                loadDataCliente();
            });
             $('#mdlContrato').on('shown.bs.modal', function (e) {
                loadDataContrato();
            });
             $('#mdlSite').on('shown.bs.modal', function (e) {
                 $idCliente = $('#idCliente');

                loadDataSite({id_cliente:$idCliente.val()});
            });

             $('#mdlContratoView').on('show.bs.modal', function (e) {
                 $id_contrato = $('#id_contrato') ;
                 if ($id_contrato===null || $id_contrato.val()===""){
                     bootbox.alert("Ingrese el n&uacute;mero de contrato");
                     return;
                }
                loadDataContratoView({id_contrato:$id_contrato.val()});
            });


            //$('#mdlActivo').modal('show');

           // $('.inCodigo').focusout(buscarActivo);
            $('#btnAgregar').click(function(){agregarActivo({})});
            $('#id_rubro').change(function(){
                rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val(),id_contrato:$('#id_contrato').val(),id_estado:1})
            });
            $('#id_subrubro').change(function() {
                var $id_rubro = $('#id_rubro');
                var $id_contrato = $('#id_contrato');
                var data = {id_subrubro:$(this).val(),
                            id_rubro:$id_rubro.val(),
                            id_contrato: $id_contrato.val(),
                };
                loadDataActivo(data);
                loadDataKit(data);
            });

            $('#contrato').change(function(){
                var numero = $(this).val();
                if (numero!=="")
                    buscarContrato({numero:numero});
                    $('#btnSelCliente').remove();
            });
            $('#btnSubmit').click(function (e){
                e.preventDefault();
                if(!validar()) return false;
                validarCertificados();
            });
              $('#btnSiteNuevo').click(function(){
                var id_cliente = $('#idCliente');
                if(id_cliente===undefined ||  id_cliente.val() === '0' ) {
                    bootbox.alert('Seleccione el cliente');
                    $('#mdlSiteEdit').hide();
                    return;
                }
                $('#mdlSiteEdit').modal('show');
            });
            $('form').bind('keypress keydown',function(e){
                var code = e.keyCode || e.which;
                if(code===13){
                    e.preventDefault();
                    return false;
                }
            });
            agregarActivo({});


        });

        function selActivos(){
            var $arr = $('input.chkSelActivo:checked');
            for(var i = 0;i<$arr.length;i++){
                var $codigo = $($arr[i]).data('codigo');
                var $descripcion = $($arr[i]).data('descripcion');
                var $pos = $($arr[i]).data('pos');
                var $cant = $tr.find('input[name="cantActivo"]');
                if ($cant.val()==="") $cant.val(1);
                var html = generarHtml({codigo:$codigo,pos:$pos ,cant:$cant.val(),descripcion:$descripcion,disabled:true});
                //$tr.after(html);
                $tr.before(html);
                //agregarActivo({codigo:$codigo,pos:$pos.val(),cant:$cant.val(),descripcion:$descripcion});
            }
            //$tr.remove();
            $('.btnDelActivo').click(deleteActivo);
            //agregarActivo({});
            //$('#mdlActivo').modal('hide');
        }
        function validar(){
            var $id_contrato = $('#id_contrato');
            var $punto_venta = $('#punto_venta');
            var $numero = $('#numero');
            var $idCliente = $('#idCliente');
            var $fecha = $("#fecha");

            if(!validarCampo($id_contrato,"Ingrese el n&uacute;mero de contrato del remito",validarNoCero)) return false;
            if(!validarCampo($punto_venta,"Ingrese el punto de venta del remito",validarNoCero)) return false;
            if(!validarCampo($numero,"Ingrese el n&uacute;mero de remito",validarNoCero)) return false;

            if(!validarCampo($fecha,"Ingrese la fecha del remito",validarCampoFecha)) return false;
            if(!validarCampo($idCliente,"Seleccione el cliente del remito",validarNoCero)) return false;


             // Validar que ingrese los campos de detalle
            rows = $('#tblRemito tbody tr');
            arrCodigos = [];
            arrPos = [];
            var funcObjects = [];
            for(var i=0;i<rows.length;i++) {
               var row = $(rows[i]);
               $pos = row.find('input[name="posicion"]');
               $codigo = row.find('input[name="codigoActivo"]');
               $cant = row.find('input[name="cantActivo"]');
                if($pos.val()!=="" && $codigo.val()===""){
                    bootbox.alert("Seleccione un activo para la posici&oacute;n o elimine el registro");
                    row.addClass('danger');
                    return false;
                }
               if ($codigo=== undefined || $codigo.val() === "") continue;
                 if($cant === undefined || $cant.val()===""|| $cant.val()==="0"){
                     bootbox.alert("Ingrese una cantidad mayor a cero");
                     row.addClass('danger');
                     return false;
                 }
                row.removeClass('danger');
                for(j=0;j<arrCodigos.length;j++){
                     if ($codigo.val() === arrCodigos[j].val()) {
                         bootbox.alert("No se puede ingresar m&aacute;s de una vez el mismo c&oacute;digo de activo");
                         row.addClass('danger');
                         return false;
                     }
                 }
//                 funcObjects.push(validarActivo({id_activo:$codigo.val()}));
                 arrCodigos.push($codigo);
                 arrPos.push($pos);
            }

            return true;
        }
        function validarCertificados(){
            var funcObjects = [];
            rows = $('#tblRemito tbody tr');
            arrCodigos = [];
            arrPos = [];
            var funcObjects = [];
            for(var i=0;i<rows.length;i++) {
               var row = $(rows[i]);
               $codigo = row.find('input[name="codigoActivo"]');
               if ($codigo===undefined || $codigo.val() === "") continue;
               funcObjects.push(validarActivo({codigo:$codigo.val()}));
            }
                $.when.apply(validarActivo,funcObjects).done(function(){
                    var mostrar = false;
                    var mensajes = "";
                    mensajes += "<ul>";
                    if(Array.isArray(arguments[0])) {
                        for (var i = 0;i<arguments.length;i++){
                            var data = arguments[i][0];
                            console.log(data);
                            if(data && data.Result!=='OK') {
                                mostrar = true;
                                mensajes += "<li>" + data.Message + "</li>";
                            }
                        }
                    } else {
                        var data = arguments[0];
                        if(data && data.Result!=='OK') {
                            mostrar = true;
                            mensajes += "<li>" + data.Message + "</li>";
                        }
                    }
                    mensajes += "</ul>";
                    mensajes += "<span>Desea crear el remito de todas formas?</span>";
                    if(mostrar){
                        bootbox.confirm(mensajes,function(result){
                            if(result) $('form').submit();
                            else return;
                        });
                    } else $('form').submit();
            });
        }

        function validarActivo(data){
             return $.ajax({
               url: '<%= PathCfg.ACTIVO_CHECK %>',
               data: data,
               method:"POST",
               dataType: "json",
//               async: false,
               beforeSend:function(){},
               success: function(result) {
                   if(result.Result !== "OK") {
//                       bootbox.alert(result.Message);
                   }
               }
           });

        }
        function agregarActivo(data){

           var html = generarHtml(data);

                        $('#tblRemito tbody').append(html);

//                        $('#tblRemito').find('tbody').append(html);

            var elem = $('#tblRemito tbody tr:last').find('.inCodigo');
            //elem.focusout(buscarActivo);

            elem.on('keypress keydown',function(e){
                var codigo = e.keyPress || e.which;

                if(codigo ===13) {
                    e.preventDefault();
//                    var elem = $('#tblRemito tbody tr:last').find('.inCodigo');
//                    elem.css('background-color','#000');
                    buscarActivo.bind(this)();
                    //$(this).trigger('focusout');
                }
            });
            //$('.inCodigo').trigger('focusout');
            $('.btnDelActivo').click(deleteActivo);
        }

        function deleteActivo(){
//          bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
//             if(result) {
                var $tr = $(this).parent().parent();
                $tr.remove();
//                }
//            });
        }
        function generarHtml(data){
           var codigo = (data.codigo !==undefined)? data.codigo:"";
           var pos = (data.pos !== undefined )?data.pos:"";
           var cant = (data.cant !== undefined )?data.cant:"";
           var descripcion = (data.descripcion!==undefined)?data.descripcion:"";
           var disabled = (data.disabled!==undefined && data.disabled )?"disabled":"";
           disabled = "disabled";
           var rubro = (data.rubro!==undefined)?data.rubro:"";
           var subrubro = (data.subrubro!==undefined)?data.subrubro:"";
           var html = '<tr>';
               html += '<td style="width:150px">' +
                           '<span class="input-group">' +
                               '<span class="input-group-addon"  data-toggle="modal" data-target="#mdlActivo"><span class="fa fa-search fa-fw"></span></span>' +
                               '<input type="text" class="form-control inCodigo" data-rubro="'+ rubro+'"  data-subrubro="'+ subrubro+'" name="codigoActivo" placeholder="C&oacute;digo" size="20" value="' + codigo +'">' +
                           '</span>' +
                       '</td>';
                html+= '<td style="width:20px"><input type="number" name="posicion" class="posicion form-control" size="2" min="0" value="'+ pos +'" ' + disabled+ '>';
                if (disabled){
                    html += '<input type="hidden" name="posicion" class="posicion form-control" size="2" min="0" value="'+ pos +'" >';
                }
                html += '</span></td>';
                html += '<td class="descripcion">'+ descripcion +'</td>' +
                        '<td style="width:90px">  ' +
                            '<span class="form-group">                                    ' +
                            '<input class="form-control cant" name="cantActivo" id="" type="number" step="any" min="0" value="' + cant + '">' +
                            '</span>' +
                        '</td>' +
                        '<td style="width:60px"><span class="btn btn-xs btn-circle btn-danger btnDelActivo"> <span class="fa fa-minus fw"></span></span></td>' +
                    '</tr>';
            return html;
        }


        function buscarActivo(){

        // Combino los parametros por si no viene definido ninguno
        var $tr = $(this).parent().parent().parent();
        var codigo = $(this).val().trim();
        console.log($tr,codigo);
        var id_contrato = $('#id_contrato').val();
        var cant = 1;

        if (codigo==='') {completarActivo({desc:'',
                                          cant:1,
                                          c_posicion:''},$tr);
                          return;
                        }
        var data={codigo:codigo,
                id_contrato:id_contrato};
        searchData('<%= PathCfg.ACTIVO_CONTRATO_SEARCH %>',data,function(result) {
                if(result.Result === "OK") {
                    if(result.TotalRecordCount===1){
                        completarActivo({desc:result.Record.desc_larga,
                                         cant:1,
                                         c_posicion:result.Record.c_posicion
                                        },$tr);
                        agregarActivo({});
                        var elem = $('#tblRemito tbody tr:last').find('.inCodigo');
                        elem.focus();
                    }else {
                        completarActivo({
                            desc:'',
                            cant:0,
                            c_posicion:''
                        },$tr)
                    }
                }
            });
        }
        function completarActivo(data,$tr){
            var $desc = $tr.find('td.descripcion');
            var $cant = $tr.find('td input.cant');
            var $posicion = $tr.find('td input.posicion');
            $desc.text(data.desc);
            $cant.val(data.cant);
            $posicion.val(data.c_posicion);
        }
        function buscarCliente(data){
            var $tr = $(this).parent().parent().parent();
            searchData('<%= PathCfg.CLIENTE_SEARCH %>',data,function(result) {
                   var todoOk = true;
                   if(result.Result === "OK") {
                       if(result.TotalRecordCount==1){completarCliente(result.Record)}

                   }
                   if (!todoOk){
                       completarCliente({
                        idCliente:'',
                        nombre:'',
                        cuit:'',
                        dni:'',
                        direccion_fisica:'',
                        id_iva:0,
                        });
                   }
               });
        }

        function completarCliente(data){
            $('#idCliente').val(data.id);
            $('#cliente_nombre').val(data.nombre);
            $('#cliente_direccion').val(data.direccion_fisica);
            if(data.cuit!='')
                $('#cliente_cuit').val(data.cuit);
            else $('#cliente_cuit').val(data.dni);
            $('#cliente_iva').val(data.id_iva);
        }
        function buscarContrato(data){
            var $tr = $(this).parent().parent().parent();
            searchData('<%= PathCfg.CONTRATO_SEARCH %>',data,function(result) {
                   var todoOk = true;
                   if(result.Result === "OK") {
                       if(result.TotalRecordCount===1){
                           buscarCliente({id:result.Record.id_cliente});
                           $('#id_contrato').val(result.Record.id);
                           var detalle = result.Record.detalle;
                            //$('#tblRemito tbody').html("");
                           /* for(var i=0;i<detalle.length;i++){
                               d = detalle[i];
                               agregarActivo({pos:d.posicion,rubro:d.id_rubro,subrubro:d.id_subrubro});
                           } */
                       } else todoOk = false;
                   } else todoOk = false;
                   if (!todoOk){
                       completarCliente({
                        idCliente:'',
                        nombre:'',
                        cuit:'',
                        dni:'',
                        direccion_fisica:'',
                        id_iva:0,
                        });
                   }
               });
        }
    </script>
    <!-- Modal -->

    <%@include file="cliente_mdl.jsp" %>
    <%@include file="site_mdl.jsp" %>
    <%@include file="site_edit_mdl.jsp" %>
    <%--<%@include file="activo_mdl.jsp" %>--%>
    <%@include file="activo_contrato_mdl.jsp" %>
    <%@include file="contrato_mdl.jsp" %>
    <%@include file="contrato_view_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
