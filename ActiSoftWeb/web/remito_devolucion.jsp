<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Kit"%>
<%@page import="transaccion.TKit"%>
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
     <%@include  file="tpl_head.jsp" %>
</head>

<body>

    <div id="wrapper">

      <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Remito de <%= StringEscapeUtils.escapeHtml4(tipoRemito) %> del remito <%= remito.getNumero()%></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <%
                    String action = PathCfg.REMITO_DEV;
                    action += "?id="+remito.getId();
                %>                
                <form action="<%= action%>" method="POST"  role="form">
                <input type="hidden" name="id_contrato" id="id_contrato" value="<%=contrato.getId()%>">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del remito</div>            
                      <div class="panel-body">

                        <input type='hidden' name="id" id="id" value="<%= remito.getId() %>">
                        <div class="row">
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
                                                    <input type="radio" name="tipoEntrega" id="rdParcial" value="1">Transitorio
                                                </label>
                                            </div>
                                        </div>
                            </div>
                             <div class="col-lg-2">
                                 <div class="form-group">
                                      <label class="" for="numero_entrega">N&uacute;mero de remito de entrega</label>
                                      <input class="form-control" type="text" name="numero_entrega" id="numero_entrega" disabled>
                                  </div>
                              </div>
                              <div class="col-lg-2">
                                 <div class="form-group">
                                      <label class="" for="fecha_entrega">Fecha del remito de entrega</label>
                                      <input class="form-control date-picker" type="text" name="fecha_entrega" id="fecha_entrega" disabled>
                                  </div>
                              </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <input class="form-control" type="hidden" name="id_activo_transporte" id="id_activo_transporte" >
                                <input class="form-control" type="hidden" name="pos_activo_transporte" id="pos_activo_transporte" >
                                <div class="col-lg-10">
<!--                                    <div class="form-group">
                                         <label class="" for="id_activo_transporte">Transporte</label>
                                         <input class="form-control" type="text" name="activo_transporte" id="activo_transporte" disabled>
                                     </div>                                -->
                                    <span class="form-group">
                                        <label class="" for="id_activo_transporte">Transporte</label>
                                           <span class="input-group">
                                           <span class="input-group-addon"  data-toggle="modal" data-target="#mdlActivo"><span class="fa fa-search fa-fw"></span></span>                                    
                                           <input type="text" class="form-control" name="activo_transporte" id="activo_transporte" placeholder="" disabled>
                                           </span>                                    
                                    </span>
                                 </div>                                
                               
<!--                                 <div class="col-lg-2">
                                    <div class="form-group ">
                                    <label class="" for="">&nbsp;</label>
                                    <span class="btn btn-default" id="" data-toggle="modal" data-target="#mdlActivo"> Agregar</span> 
                                    </div>
                                </div>-->
                                 <div class="col-lg-2">
                                    <div class="form-group ">
                                        <label class="" for="id_activo_transporte">Cantidad</label>
                                        <input class="form-control" type="text" name="transporte_cantidad" id="transporte_cantidad">                                
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                          <div class="row">
                            <div class="col-lg-12">
                                <h3>Activos  <span class="btn btn-primary" data-toggle="modal" data-target="#mdlRemito" id="btnAgregar">Agregar remito</span></h3>
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
                                        <% TActivo ta = new TActivo();
                                           TKit tk = new TKit();
                                           for(Remito_detalle rm: detalle){ 
                                               if(rm.getId_activo()!=0){
                                               Activo activo = ta.getById(rm.getId_activo());
                                               if(activo==null) continue;
                                        %>
                                        <tr>
                                            <input type="hidden" name="detalle" value="<%= rm.getId() %>">
                                            <td><%= activo.getCodigo()%></td>
                                            <td><%= rm.getPosicion() %></td>
                                            <td><%= StringEscapeUtils.escapeHtml4(activo.getDesc_larga())%></td>
                                            <td><%= rm.getCantidad()%></td>
                                            <td><span class="btn btn-xs btn-circle btn-danger btnDelActivo" style="display:none"> <span class="fa fa-minus fw"></span></span></td>
                                        </tr>
                                        <% } else { 
                                            Kit kit = tk.getById(rm.getId_kit());
                                            if (kit==null) continue;
                                        %>
                                        <tr>
                                            <input type="hidden" name="detalle" value="<%= rm.getId() %>">
                                            <td><%= kit.getCodigo()%></td>
                                            <td><%= rm.getPosicion() %></td>
                                            <td><%= StringEscapeUtils.escapeHtml4(kit.getNombre())%></td>
                                            <td><%= rm.getCantidad()%></td>
                                            <td><span class="btn btn-xs btn-circle btn-danger btnDelActivo" style="display:none"> <span class="fa fa-minus fw"></span></span></td>
                                        </tr>
                                        <% } %>
                                        <% } %>
                                    </tbody>
<!--                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tfoot>-->
                                </table>
                               
                                <!--<button type="submit" class="btn btn-default">Guardar</button>-->
<!--                            <button type="reset" class="btn btn-default">Reset Button</button>-->
                            </div>
                             <div class="col-lg-12">
                                <div class="form-group">
                                    <label for="observaciones">Observaciones</label>
                                    <textarea name="observaciones" id="observaciones" class="form-control"></textarea>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>                            
                                <a type="reset" href="<%=PathCfg.REMITO%>" class="btn btn-default" >Cerrar</a>
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
            //$('#btnAgregar').hide();  
            $('#id_rubro').change(function(){
                rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val(),id_contrato:$('#id_contrato').val(),id_estado:1})
            });
            $('#id_subrubro').change(function() {
                console.log("Subrubro change");
                var $id_rubro = $('#id_rubro');
                var $id_contrato = $('#id_contrato');
                var data = {id_subrubro:$(this).val(),
                            id_rubro:$id_rubro.val(),
                            id_contrato: $id_contrato.val(),
                };
               loadDataActivo(data);
               // loadDataKit(data);
            });
            $('#mdlActivo').on('shown.bs.modal',showModal);
            $('#mdlActivo').on('hide.bs.modal', hideModal);
            $('#btnSelActivos').click(selActivoTransporte);
        });
        function radioChange(){
//            $('#rdTotal').change(radioChange);
//            $('#rdParcial').change(radioChange);
            var parcial = $('#rdParcial').prop('checked');
            $('#numero_entrega').prop('disabled',!parcial);
            $('#fecha_entrega').prop('disabled',!parcial);
//            if(parcial) {
//                $('#btnAgregar').show();                
//                $('.btnDelActivo').show();
//            }
//            else {
//                $('#btnAgregar').hide();
//                $('.btnDelActivo').hide();
//            }
            
            
            
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
             if($num_ent.val()==$num_dev.val()) {
                 bootbox.alert("El n&uacute;mero de entrega no puede ser el mismo que el de devoluci&oacute;n");
                $num_ent.parent().addClass("has-error");
                $num_dev.parent().addClass("has-error");
                return false;
             }
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
        function selActivos(){
            var $arr = $('input.chkSelRemitoDetalle:checked');
            var $todos = $('input.chkSelRemitoDetalle');
            var parcial = $('#rdParcial').prop('checked');
            if (!parcial && $arr.length < $todos.length){
                bootbox.alert("En un remito de entrega definitivo se deben agregar todos los activos del remito");
                return false;
            }
            
            for(var i = 0;i<$arr.length;i++){
                $codigo = $($arr[i]).data('codigo'); 
                $descripcion = $($arr[i]).data('descripcion'); 
                $pos = $($arr[i]).data('pos'); 
                $cant = $($arr[i]).data('cant');                 
                $index = $($arr[i]).data('index');                                 
                var html = generarHtml({codigo:$codigo,pos:$pos ,cant:$cant,descripcion:$descripcion,disabled:true,id:$index});
                if($('#tblRemito').find('tbody tr:last').length==0)
                    $('#tblRemito').find('tbody').html(html);
                else $('#tblRemito').find('tbody tr:last').after(html);
            }            
            $('.btnDelActivo').click(deleteActivo);       
            //Cuando se agregan items al remito, no se puede volver a poner en entrega definitiva
            $('input[name=tipoEntrega]').prop('readonly',true);
            
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
           var id = (data.id!==undefined)?data.id:"";
           var parcial = $('#rdParcial').prop('checked');
           var html = '<tr>';
               html += '<input type="hidden" name="detalle" value="'+ id +'">';
               html += '<td style="width:150px">' + codigo + '</td>';
               html += '<td style="width:20px">' + pos + '</td>' ;                
               html += '</span></td>';               
               html += '<td class="descripcion">'+ descripcion +'</td>';
               html += '<td style="width:90px">  ' + cant + '</td>';
               html += '<td style="width:60px">';
               var disp= (parcial)?"":"style=display:none;";
                html += '<span class="btn btn-xs btn-circle btn-danger btnDelActivo" '+  disp + '> <span class="fa fa-minus fw"></span></span>';
               html += '</td>';
               html += '</tr>'; 
            return html;
        }
       
         function showModal(e) {
            
                 var $id_contrato = $('#id_contrato') ;
                 if ($id_contrato===null || $id_contrato.val()===""){
                      $('#mdlActivo').modal('hide');
                     bootbox.alert("Ingrese el n&uacute;mero de contrato");
                     return;
                }
                $invoker = $(e.relatedTarget);
                $tr = $invoker.parent().parent().parent();
                $codigo = $tr.find('input[name="codigoActivo"]');
                
                $('#id_rubro').prop('disabled',true);
                $('#id_subrubro').prop('disabled',false);
                $('#id_rubro').val(<%=OptionsCfg.RUBRO_TRANSPORTE%>);
                $('#id_rubro').trigger("change");
                //$('#id_subrubro').trigger("change");
                $('#liKit').hide();
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
        console.log(data);
               // loadDataActivo(data);
        };
        
        function hideModal(e) {
            if($invoker!==null)             
               $invoker.parent().find('input').focus();   
           $('#mdlActivo>table').html('');

        }
        function selActivoTransporte(){           
            var $arr = $('input.chkSelActivo:checked');
            for(var i = 0;i<$arr.length;i++){
                var $codigo = $($arr[i]).data('codigo'); 
                var $descripcion = $($arr[i]).data('descripcion'); 
                var $pos = $($arr[i]).data('pos'); 
                var $cant = $($arr[i]).data('cant');                 
                
                $('#id_activo_transporte').val($codigo);
                $('#pos_activo_transporte').val($pos);
                $('#activo_transporte').val($descripcion);
                $('#mdlActivo').modal('hide');     
                break;
            }
        }
//         function selActivos*old(){           
//            var $arr = $('input.chkSelActivo:checked');
//            for(var i = 0;i<$arr.length;i++){
//                var $codigo = $($arr[i]).data('codigo'); 
//                var $descripcion = $($arr[i]).data('descripcion'); 
//                var $pos = $($arr[i]).data('pos'); 
//                var $cant = $($arr[i]).data('cant');                                           
//                var html = generarHtml({codigo:$codigo,pos:$pos ,cant:$cant,descripcion:$descripcion,disabled:true});
//                //$tr.after(html);
//                $('#tblRemito').append(html);
//                //agregarActivo({codigo:$codigo,pos:$pos.val(),cant:$cant.val(),descripcion:$descripcion});
//            }
//            //$tr.remove();
//            $('.btnDelActivo').click(deleteActivo);
//            //agregarActivo({});
//            //$('#mdlActivo').modal('hide');
//        }
        </script>
        <%@include file="remito_detalle_mdl.jsp"%>
        <%@include file="activo_contrato_mdl.jsp" %>
        <%@include file="tpl_footer.jsp"%>
        
</body>

</html>