<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="transaccion.TContrato_detalle"%>
<%@page import="bd.Contrato_detalle"%>
<%@page import="transaccion.TCliente"%>
<%@page import="bd.Contrato"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% 
    Contrato contrato = (Contrato) request.getAttribute("contrato");
    boolean nuevo = false;
    if (contrato==null) {
        contrato = new Contrato();
        nuevo = true;
    }
    Integer idCliente = (contrato!=null)?contrato.getId_cliente():0;
    Cliente cliente = new TCliente().getById(idCliente);
    if (cliente==null) cliente = new Cliente();
    List<Contrato_detalle> lstDetalle = new TContrato_detalle().getByContratoId(contrato.getId());
    
    if (lstDetalle == null) lstDetalle = new ArrayList<Contrato_detalle>(); 
    TSubrubro ts = new TSubrubro();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    
     <%@include  file="tpl_head.jsp" %>  
     <style>
         /*.form-control{padding:0;}*/
    </style>
</head>

<body>

    <div id="wrapper">

      <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else {%>Editar<%}%> contrato</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <%
                    String action = PathCfg.CONTRATO_EDIT;
                    action += (!nuevo)?"?id="+contrato.getId():"";
                %>                
                <form action="<%= action%>" method="POST"  role="form">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del contrato </div>            
                      <div class="panel-body">
                          <span class="pull-right btn btn-default fa fa-chevron-up" id="btnHideData"></span>
                      <div class="row" id="data">
                        <div class="col-lg-6 " >
                            <h3 class="">Contrato</h3>
                            <fieldset>
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= contrato.getId()%>">
                                <% }%>
                                
                                <div class="form-group">
                                    <label for="numero">N&uacute;mero</label>
                                    <input class="form-control" name="numero" id="numero" value="<%=contrato.getNumero() %>">
                                </div>
                                <!--<div class="col-lg-12 ">-->
                                    <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="fecha">Fecha</label>
                                            <input class="form-control date-picker" name="fecha" id="fecha" size="0"  value="<%=TFecha.formatearFechaBdVista(contrato.getFecha()) %>">
                                        </div>
                                    </div>

                                     <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="fecha_inicio">Inicio</label>
                                            <input class="form-control date-picker" name="fecha_inicio" id="fecha_inicio"  value="<%=TFecha.formatearFechaBdVista(contrato.getFecha_inicio()) %>">
                                        </div>
                                    </div>
                                     <div class="col-lg-4 " >
                                        <div class="form-group">
                                            <label for="fecha_fin">Fin</label>
                                            <input class="form-control date-picker" name="fecha_fin" id="fecha_fin"  value="<%= TFecha.formatearFechaBdVista(contrato.getFecha_fin())%>">
                                        </div>
                                    </div>
<!--                                     <div class="col-lg-3 " >
                                         
                                    </div>-->
                                <!--</div>-->
                               <!--<div class="form-group col-lg-12 " >-->
                                   <div class="col-lg-4 " >
                                        <div class="form-group " >
                                             <label for="divisa_contrato">Divisa</label>
                                             <select class="form-control" name="divisa_contrato" id="divisa_contrato" >
                                                  <%
                                                    for(int i=0;i<2;i++){
                                                            String divisa = i==0?"Dolares":"Pesos";
                                                            String selected = i== contrato.getId_divisa()?"selected":"";
                                                    %>
                                                    <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                    <% }%>                                                
                                             </select>
                                        </div>
                                   </div>
                                    <div class="col-lg-4 " >   
                                   <div class="form-group " >
                                        <label for="">Monto previsto</label>
                                        <input class="form-control" name="monto_contrato" id="monto_contrato"  value="<%= contrato.getMonto().longValue() %>" >
                                    </div>                                        
                                    </div>
                                    <div class="col-lg-4 " >   
                                   <div class="form-group " >
                                        <label for="">Porcentaje</label>
                                        <input class="form-control" name="porcentaje_contrato" id="porcentaje_contrato" value="100">
                                    </div>                                        
                                    </div>
                               <!--</div>-->
                            </fieldset>
                        </div>
                        <div class="col-lg-6 " >
                             <h3 class="">Cliente</h3>
                             <input type="hidden" name="idCliente" id="idCliente" value="<%= cliente.getId() %>" >
                             <fieldset disabled>                                 
                                 <div class="form-group">
                                    <label for="cliente_nombre">Nombre</label>
                                    <input class="form-control" name="cliente_nombre" id="cliente_nombre" value="<%= cliente.getNombre() %>">
                                </div>
                                 <div class="form-group">
                                    <label for="cliente_direccion">Direcci&oacute;n</label>
                                    <input class="form-control" name="cliente_direccion" id="cliente_direccion" value="<%= cliente.getDireccion_fisica() %>">
                                </div>
                                     
                                 <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label class="label" for="cliente_iva">IVA</label>
                                       <select class="form-control" name="cliente_iva" id="cliente_iva" >
                                           <option value="0"></option>
                                           <% for(Option o:OptionsCfg.getClasesIva()){ 
                                           String selected = o.getId()==cliente.getId_iva()?"selected":""; 
                                           %>
                                           <option value="<%=o.getId()%>" <%= selected%>>
                                               <%= StringEscapeUtils.escapeHtml4(o.getDescripcion())%>
                                           </option>
                                           <%  } %>
                                           %>
                                       </select>
                                   </div>
                                 </div>
                                  <div class="col-lg-6 " >
                                    <div class="form-group">
                                       <label for="cliente_cuit">CUIT</label>
                                       <input class="form-control" name="cliente_cuit" id="cliente_cuit" value="<%= cliente.getCuit() %>">                                        
                                   </div>
                                 </div>  
                            </fieldset>
                            <div class="form-group" >
                                <span  class="btn btn-default btnSelCliente" data-toggle="modal" data-target="#mdlCliente"><span class="fa fa-search fa-fw"></span> Seleccionar</span>
                             </div>              
                        </div>
                          <!-- ./Cliente -->
                        
                        <!-- ./Locacion -->
                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        </div>  
                          <!-- ./row -->
                        <hr>
                        <div class="row">
                            <div class="col-lg-12">
                                <h3>Items <span class="btn btn-default" id="btnAgregar"> Agregar</span></h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblContrato">
                                    <thead>
                                        <tr>
                                            <th>Posici&oacute;n</th>
                                            <th>Descripci&oacute;n</th>
                                            <th>Divisa</th>
                                            <th>Precio</th>
                                            <th><center>&percnt;</center></th>
                                            <th>Unidad</th>
                                            <th>C&oacute;digo</th>
                                            <th>Subrubro</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                        <% 
                                            for( Contrato_detalle detalle:lstDetalle) {
                                            Subrubro sr = ts.getById(detalle.getId_subrubro());
                                            if (sr==null) sr = new Subrubro();
                                        %>
                                            <%--<%@include file="contrato_detalle.jsp" %>--%>
                                            <tr>
                                                <td width="30px">
                                                    <input class="form-control form-inline" type="number" name="posicion" min="0" value="<%= detalle.getPosicion() %>">
                                                </td>
                                                <td>
                                                    <input class="form-control form-inline" name="descripcion" value="<%= StringEscapeUtils.escapeHtml4(detalle.getDescripcion()) %>">
                                                </td>
                                                <td style="width:110px;" >
                                                    <select class="form-control" name="divisa">
                                                        <%
                                                    for(int i=0;i<2;i++){
                                                        String divisa = i==0?"Dolares":"Pesos";
                                                        String selected = i== detalle.getId_divisa()?"selected":"";
                                                    %>
                                                    <option value="<%= i%>" <%= selected %> > <%= divisa %></option>
                                                    <% }%>      
                                                    </select>
                                                </td>

                                                    <td style="width:75px">
                                                        <input class="form-control " name="precio" value="<%= detalle.getPrecio()%>">
                                                    </td>
                                                    <td style="width:75px">  
                                                        <span class="form-group">                                    
                                                            <input class="form-control cant" name="porcentaje" id="porcentaje" type="number" value="<%= detalle.getPorcentaje()%>">
                                                        </span>
                                                    </td>
                                                    <td style="width:75px">  
                                                        <span class="form-group">                                    
                                                            <select class="form-control" name="id_unidad" id="id_unidad" type="id_unidad" >
                                                                <!--<option value="0"></option>-->
                                                                <% for(Option o:OptionsCfg.getUnidades()){ 
                                                                    String selected = detalle.getId_unidad() == o.getId()? "selected":"";
                                                                %>                                                                
                                                                <option value="<%= o.getCodigo() %>" <%= selected %> ><%= o.getDescripcion() %></option>
                                                                <% } %>
                                                            </select>
                                                        </span>
                                                    </td>
                                                    <td style="width:150px">
                                                        <span class="input-group">
                                                            <span class="input-group-addon" data-toggle="modal" data-target="#mdlSubrubro"><span class="fa fa-search fa-fw"></span></span>
                                                            <input type="text" class="form-control inCodigo" data-index="1" name="codigo" placeholder="C&oacute;digo" size="20" value="<%= sr.getCodigo() %>">
                                                        </span>
                                                    </td>
                                                    <td class="subrubro" ><%= StringEscapeUtils.escapeHtml4(sr.getDescripcion()) %></td>    
                                                    <td style="width:60px">
                                                        <span class="btn btn-xs btn-circle btn-danger btnDelItem">
                                                            <span class="fa fa-minus fw"></span>

                                                        </span>
                                                    </td>
                                                </tr>
                                         <% } %>
                                         
                                         <%--<%@include file="contrato_test.jsp" %>--%>
                                    </tbody>
<!--                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                            <td></td> 
                                            <td></td>
                                            <td></td>
                                            <td id="total"> <span class="input-group">
                                                    <span class="input-group-addon"  data-toggle="modal" data-target="#mdlSubrubro"><span class="fa fa-money fa-fw"></span></span>
                                                    <input type="text" class="form-control" data-index="1" placeholder="Total" size="20" disabled>
                                                </span></td>
                                            <td></td>
                                        </tr>
                                    </tfoot>
-->
                                </table>
                                
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.CONTRATO%>">Cancelar</a>
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
        var $pos_item;
        var $tr;
        var arr_unidad = [];
        var arr_divisa = ["U$s","$"];
        $(document).ready(function(){
            var i = 0;
            arr_unidad[i] = {id:0,desc:''};
            <% Integer i = 0;
               for(Option o:OptionsCfg.getUnidades()){
            %> 
            
            arr_unidad [++i] = {id: <%= o.getId() %>, desc: '<%= o.getDescripcion() %>'};
            <% 
                i+=1; 
               } 
            %>   
        
            $('#btnSelSubrubros').click(selSubrubros);
         
            $('#selTodos').change(function(e){
                selTodos('input.chkSelSubrubro',$(this).prop('checked'));
            });
            
            $('#mdlSubrubro').on('shown.bs.modal', function (e) {
                loadDataSubrubro({id_rubro:$('#id_rubro').val()});
                 $invoker = $(e.relatedTarget);
                 $tr = $invoker.parent().parent().parent();
            });
            
            $('#mdlCliente').on('shown.bs.modal', function (e) {
                loadDataCliente();  
            });
            
             $('#mdlSubrubro').on('hide.bs.modal', function (e) {
                $invoker.parent().find('input').focus();  

            });
            
           // $('.inCodigo').focusout(buscarRubro);
            $('#btnAgregar').click(function(){agregarItem({})});
            $('.date-picker').mask('99/99/9999');

            $('.date-picker').datepicker({
                language: 'es'
            }); 
            
            
            $('#id_rubro').change(selRubroChange);            
            $('#btnSubmit').click(submitForm);
            agregarItem({});
           
        });
        
        function selSubrubros(){
                var $arr = $('input.chkSelSubrubro:checked');
                for(var i = 0;i<$arr.length;i++){
                    var $codigo = $($arr[i]).data('codigo'); 
                    var $subrubro = $($arr[i]).data('descripcion'); 
                    
                   // console.log($tr);
                    var $pos = $tr.find('input[name="posicion"]');
                    var $desc = $tr.find('input[name="descripcion"]');               
                    var $divisa = $tr.find('select[name="divisa"]');
                    
                    var $precio = $tr.find('input[name="precio"]');
                    var $porcentaje = $tr.find('input[name="porcentaje"]');          
                    var $id_unidad = $tr.find('select[name="id_unidad"]');                    
                        
                    console.log($pos.val(),$desc.val(),$divisa.val(),$precio.val(),$porcentaje.val(),$codigo,$subrubro);
                    
                    var html = generarHtml({pos:$pos.val(),
                                 descripcion:$desc.val(),
                                 divisa:$divisa.val(),
                                 precio:$precio.val(),
                                 porcentaje:$porcentaje.val(),
                                 codigo: $codigo,
                                 subrubro: $subrubro,
                                 id_unidad:$id_unidad.val(),
                             });
                             $tr.after(html);
                    
                }
                $tr.remove();
                agregarItem({});
                $('#mdlSubrubro').modal('hide');                
        }
        function validar(){
            
            // Validar que ingrese el contrato
            var $numero = $('#numero');
            var idCliente = $('#idCliente');
            var $fecha = $("#fecha");
            var $fecha_inicio = $("#fecha_inicio");
            var $fecha_fin = $("#fecha_fin");
            var $divisa_contrato = $("#divisa_contrato");
            var $monto_contrato = $("#monto_contrato");
            var $porcentaje_contrato = $("#porcentaje_contrato");
            
            if($numero===null || $numero.val() === "") {
                bootbox.alert("Debe ingresar el n&uacute;mero de contrato");
              $numero.parent().addClass("has-error");
                return false;
            } else $numero.parent().removeClass("has-error");
            if(idCliente===null||idCliente.val()==="" || idCliente.val()==="0"){
                bootbox.alert("Debe seleccionar el cliente del contrato");
                return false;
            }
            if ($fecha === null || $fecha.val() === "" || !validarFecha($fecha.val())) {
                bootbox.alert("Ingrese la fecha valida para el contrato");
                $fecha.parent().addClass("has-error");
                return false;
            } else $fecha.parent().removeClass("has-error");
                
            if ($fecha_inicio === null || $fecha_inicio.val() === "" || !validarFecha($fecha_inicio.val())) {                                
                bootbox.alert("Ingrese una fecha v&aacute;lida de inicio del contrato");
                $fecha_inicio.parent().addClass('has-error');
                return false;
            } else $fecha_inicio.parent().removeClass("has-error");
            
            if ($fecha_fin === null || $fecha_fin.val() === "" || !validarFecha($fecha_fin.val())) {
                bootbox.alert("Ingrese una fecha v&aacute;lida de fin del contrato");
               $fecha_fin.parent().addClass('has-error');
                return false;
            } else $fecha_fin.parent().removeClass("has-error");
            
            if ($divisa_contrato === null || $divisa_contrato.val() === "") {
                bootbox.alert("Ingrese la divisa del contrato");
               $divisa_contrato.parent().addClass('has-error');
                return false;
            } else $divisa_contrato.parent().removeClass("has-error");
            if ($monto_contrato === null || $monto_contrato.val() === "" || !$.isNumeric($monto_contrato.val())) {
                bootbox.alert("Ingrese un monto v&aacute;lido para el contrato");
               $monto_contrato.parent().addClass('has-error');
                return false;
            } else $monto_contrato.parent().removeClass("has-error");
            if ($porcentaje_contrato === null || $porcentaje_contrato.val() === ""  || !$.isNumeric($porcentaje_contrato.val())) {
                bootbox.alert("Ingrese un porcentaje v&aacute;lido para el contrato");
               $porcentaje_contrato.parent().addClass('has-error');
                return false;
            } else $porcentaje_contrato.parent().removeClass("has-error");
            // Validar que ingrese los campos de detalle
            var rows = $('#tblContrato tbody tr');
            var arrCodigos = [];
            var arrPos = [];
            
            for(var i=0;i<rows.length;i++) {
               var row = $(rows[i]);
               var $pos = row.find('input[name="posicion"]');
               var $desc = row.find('input[name="descripcion"]');               
               var $precio = row.find('input[name="precio"]');
               var $porcentaje = row.find('input[name="porcentaje"]');
               var $codigo = row.find('input[name="codigo"]');
               
                if ($codigo==null || $codigo.val() === "") continue;
                               
                row.removeClass('danger');
                if( $pos === null || $pos.val() === ""){
                     row.addClass('danger');
                     bootbox.alert("Debe ingresar la posici$oacute;n");
                     return false;
                 }
                 if( $desc === null || $desc.val() === ""){
                     row.addClass('danger');
                     bootbox.alert("Debe ingresar la descripci&oacute;n");
                     return false;
                 }
                 if( $precio === null || $precio.val() === ""){
                     row.addClass('danger');
                     bootbox.alert("Debe ingresar el precio");
                     return false;
                 }
                  if( !$.isNumeric($precio.val()) ){
                     row.addClass('danger');
                     bootbox.alert("Debe ingresar un precio v&aacute;lido.");
                     return false;
                  }
                 if( $porcentaje === null || $porcentaje.val() === ""){
                     row.addClass('danger');
                     bootbox.alert("");
                     return false;
                 }
                 if( $codigo === null || $codigo === ""){                     
                     row.addClass('danger');
                     bootbox.message("Debe ingresar el c&oacute;digo");
                     return false;
                 }
                 var todoOk = true;
                 /* Validamos que el código de subrubro no se repita. 
                  * Si se repite para distinas posiciones solicitamos confirmación al usuario.
                  * Si se repote para la misma posicion, no permitimos la carga.
                  * */
                 for(j=0;j<arrCodigos.length;j++){
                     
                     if ($codigo.val() === arrCodigos[j].val()) {
                         if($pos.val() != arrPos[j].val() ) { 
                            todoOk = confirm("Ha ingresado el mismo codigo " + $codigo.val() + " para distintas posiciones esta seguro de continuar?");                            
                         }else  {
                             //arrCodigos[j].parent().parent().parent().addClass('danger');                                       
                            alert("Ha ingresado el mismo codigo " + $codigo.val() + " para la misma posicion");
                            todoOk = false;
                            
                         }
                         if(!todoOk) {
                            row.addClass('danger');                         
                            return false;
                         }    
                         
                     }                     
                 }                 
                 arrCodigos.push($codigo);
                 arrPos.push($pos);
            }
           // bootbox.alert("");
        
            return true;
        }
        
        function agregarItem(data){
            var html = generarHtml(data);
            
            $('#tblContrato tbody').append(html);
            $('.inCodigo').focusout(buscarSubrubro);
           // $('.inCodigo').trigger('focusout');
            $('.btnDelItem').click(deleteItem);
        }
        function generarHtml(data){
                        
            var pos = (data.pos !== undefined )?data.pos:"";
            var descripcion = (data.descripcion!==undefined)?data.descripcion.replace('"','\"') :"";
            
            var precio = (data.precio!== undefined)? data.precio:"0";
            var porcentaje  = (data.porcentaje!== undefined)? data.porcentaje:"100";
            var codigo = (data.codigo !==undefined)? data.codigo:"";           
            var subrubro = (data.subrubro!==undefined )?data.subrubro:"";
            var id_unidad = (data.id_unidad!==undefined )?data.id_unidad:0;
            var divisa    = (data.divisa!==undefined )?data.divisa:0;
            
            var  html = '<tr >' +
                        '<td width="90px"><input class="form-control form-inline" type="number" name="posicion" min="0" value="'+pos+'"></td>' +                         
                        "<td><input class='form-control form-inline' name='descripcion' value='" + descripcion +"'></td>" +
                        '<td style="width:80px;">' +
                           htmlDivisa(divisa) +
                        '</td>' +
                        '<td style="width:75px"><input class="form-control " name="precio" value="'+ precio +'"></td>' + 
                        '<td style="width:75px">  ' +
                            '<span class="form-group">                                    ' +
                            '<input class="form-control cant" name="porcentaje" id="porcentaje" type="number" value="'+ porcentaje +'">' +
                            '</span>' +
                        '</td>' +
                        '<td>' +
                        htmlUnidad(id_unidad)      +
                        '</td>'                    +
                        '<td style="width:135px">' +
                            '<span class="input-group">' +
                                '<span class="input-group-addon"  data-toggle="modal" data-target="#mdlSubrubro"><span class="fa fa-search fa-fw"></span></span>' +
                                '<input type="text" class="form-control inCodigo" data-index="1" name="codigo" placeholder="C&oacute;digo" size="20" value="'+ codigo +'">' +
                            '</span>' +
                        '</td>' +
                        '<td class="subrubro" style="width:300px">'+ subrubro +'</td>' +                        
                        '<td style="width:60px"><span class="btn btn-xs btn-circle btn-danger btnDelItem"> <span class="fa fa-minus fw"></span></span></td>' + 
                    '</tr>';
            return html;
        }
        function deleteItem(){
//          bootbox.confirm("Esta seguro que desea eliminar el registro?",function(result){
//             if(result) {            
                var $tr = $(this).parent().parent();
                $tr.remove();         
                
//                }
//            });
        }
        function htmlUnidad(id_unidad){
            var html = '<select class="form-control" name="id_unidad">';  
            for(var i=0;i<arr_unidad.length;i++){
                var d = arr_unidad[i];
                var selected = d.id == id_unidad?"selected":"";
                html += '<option value="' + d.id + '" ' + selected + '>' + d.desc + '</option>';
            }
            html += '</select>';
            return html;
        }
        
        function htmlDivisa(divisa){
            
            var html = '<select class="form-control" name="divisa">';
            for(var i=0;i<arr_divisa.length;i++){
                var d = arr_divisa[i];
                var selected = i == divisa?"selected":"";
                html += '<option value="' + i + '" ' + selected + '>' + d + '</option>';
            }
            html += '</select>';            
            return html;
        }
        
        function buscarSubrubro(){            
           // Combino los parametros por si no viene definido ninguno                      
        var $tr = $(this).parent().parent().parent();
        var codigo = $(this).val().trim();
        if (codigo==='') {completarSubrubro($tr,{subrubro:''}); return; }
        var data={codigo:codigo};
        searchData('<%= PathCfg.SUBRUBRO_SEARCH %>',data,function(result) {
                   if(result.Result === "OK") {
                       if(result.TotalRecordCount===1){                                               
                           completarSubrubro($tr ,{subrubro:result.Record.descripcion});
                       }else {
                           completarSubrubro($tr,{subrubro:''});
                       }
                   }
               });
        }
        function completarSubrubro($tr,data){
            var $subrubro = $tr.find('td.subrubro');
           $subrubro.text(data.subrubro);
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
        
    </script>
    <!-- Modal -->

    <%@include file="cliente_mdl.jsp" %>
    <%@include file="site_mdl.jsp" %>
    <%@include file="rubro_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>    
</body>

</html>
