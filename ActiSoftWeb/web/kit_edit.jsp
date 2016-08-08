<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bd.Kit_detalle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="transaccion.TActivo"%>
<%@page import="bd.Activo"%>
<%@page import="transaccion.TKit_detalle"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Kit"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="utils.TFecha"%>
<%@page import="bd.Rubro"%>

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="java.util.List"%>
<%     
    Kit kit = (Kit) request.getAttribute("kit");
    boolean nuevo = false;
    if (kit==null) {
        kit= new Kit();
        nuevo = true;
    }    
    List<Kit_detalle> lstDetalle = new TKit_detalle().getByKitId(kit.getId());
    if(lstDetalle==null) lstDetalle = new ArrayList<Kit_detalle>();
    TActivo ta = new TActivo();
    Rubro rubro = new TRubro().getById(kit.getId_rubro());
    if(rubro==null) rubro = new Rubro();
    
    
    HashMap<Integer,Rubro>    mapRubros = new TRubro().getMap();
    HashMap<Integer,Subrubro> mapSubrubros = new TSubrubro().getMap();
    
    HashMap<String,String> filtroRubro = new HashMap<String,String>();
    HashMap<String,String> filtroSubrubro = new HashMap<String,String>();
    
    filtroRubro.put("id_estado","1");
    List<Rubro> lstRubro = new TRubro().getListFiltro(filtroRubro);

    filtroSubrubro.put("id_estado","1");
    filtroSubrubro.put("id_rubro",kit.getId_rubro().toString());
    List<Subrubro> lstSubrubro = new TSubrubro().getListFiltro(filtroSubrubro);
    String checked = kit.getActivo()!=0?"checked":"";
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
                    <h1 class="page-header"><% if(nuevo) {%>Nuevo<%}else {%>Editar<%}%> Kit</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                 <%
                    String action = PathCfg.KIT_EDIT;                   
                    action += (!nuevo)?"?id="+kit.getId():"";
                %>
                <form role="form" method="POST" action="<%=action%>" >                 
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Datos b&aacute;sicos del kit </div>            
                      <div class="panel-body">
                      <div class="row">
                        <div class="col-lg-6" >
                            <fieldset>                                
                                <% if (!nuevo) {%>
                                    <input type="hidden" name="id" value="<%= kit.getId()%>">                                    
                                <% }%>
                                <div class="row">
                                    <div class="col-lg-3 " >
                                        <div class="form-group">
                                            <label for="nombre">C&oacute;digo</label>
                                            <input type="text" id="codigo" name="codigo" class="form-control" value="<%=kit.getCodigo()%>">
                                        </div>
                                    </div>
                                    <div class="col-lg-9">
                                        <div class="form-group">
                                            <label for="nombre">Nombre</label>
                                            <input type="text" id="nombre" name="nombre" class="form-control" value="<%=kit.getNombre()%>">
                                        </div>
                                    </div>  
                                </div>
                                <div class="row">
    <!--                                     <div class="col-lg-3 " >
                                        </div>-->
                                    <!--</div>-->
                            <% if(kit.getActivo()==0){%>
                            <div class="col-lg-4">
                                <div class="form-group">
                                    <label for="activo">
                                    <input type="checkbox" class="checkbox checkbox-inline" name="activo" id="activo" value='1' <%=checked%>> Activo</label>
                                </div>
                           </div>
                                <% } else {%>
                                 <input type="hidden" class="checkbox checkbox-inline" name="activo" id="activo" value='1'>
                                <% }%>
                                       
                                    </div>
                            </fieldset>
                        </div>  
                        <div class="col-lg-6">
                              <div class="col-lg-12 " >
                                            <div class="form-group">
                                            <label for="id_rubro">Rubro</label>
                                            <select name="id_rubro" id="id_rubro_kit" class="form-control">
                                                <option value="0">Seleccione el rubro</option>
                                                <% for(Rubro r:lstRubro){ 
                                                    String selected = (r.getId().equals(kit.getId_rubro()))?"selected":"";
                                                %>
                                                    <option value="<%= r.getId()%>" <%= selected  %>>
                                                        <%= r.getCodigo() + " - " +  StringEscapeUtils.escapeHtml4(r.getDescripcion())%>
                                                    </option>
                                                <% } %>
                                            </select>
                                        </div>
                              </div>
                               <div class="col-lg-12 " >             
                                    <div class="form-group">
                                        <label for="id_subrubro">Subrubro</label>
                                        <select name="id_subrubro" id="id_subrubro_kit" class="form-control" >
                                             <option value="0">Seleccione el subrubro</option>
                                            <% for(Subrubro s:lstSubrubro){
                                                String selected = (s.getId().equals(kit.getId_subrubro()))?"selected":"";
                                                %>                                                
                                                <option value="<%= s.getId()%>"  <%= selected %>>
                                                    <%= s.getCodigo() + " - " + StringEscapeUtils.escapeHtml4(s.getDescripcion())%>
                                                </option>
                                            <% } %>

                                        </select>
                                    </div>
                                </div>
                        </div>
                   
                                                  
                        <!-- /.col-lg-6 (nested) -->
                            <!-- /.row (nested) -->
                        </div><!-- row-->
                       
                        <div class="row" >
                            
                            <div class="col-lg-12">
                                <h3>Activos del kit <span class="btn btn-default"  id="btnAgregar"> Agregar</span></h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblKit_detalle">
                                    <thead>
                                        <tr>
                                            <th>C&oacute;digo</th>
                                            <th>Descripci&oacute;n</th>
<!--                                            <th>Rubro</th>
                                            <th>Subrubro</th>-->
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for( Kit_detalle detalle:lstDetalle) {
                                           Activo a   = ta.getById(detalle.getId_activo());
                                           if(a==null) continue;
//                                           Rubro r    = mapRubros.get(a.getId_rubro());
//                                           Subrubro s = mapSubrubros.get(a.getId_subrubro());
                                        %>
                                        <tr>
                                            <td style="width:150px">
                                                <span class="input-group">
                                                    <span class="input-group-addon"  data-toggle="modal" data-target="#mdlActivo"><span class="fa fa-search fa-fw"></span></span>
                                                    <input type="text" class="form-control inCodigo" name="codigoActivo"  size="20" value="<%= a.getCodigo() %>">
                                                </span>
                                            </td>

                                            <td class="descripcion"><%= a.getDesc_larga()%></td>
                                            <td style="width:60px"><span class="btn btn-xs btn-circle btn-danger btnDelActivo"> <span class="fa fa-minus fw"></span></span></td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div
                
                        <div class="row">
                            <div class="col-lg-12">
                                <% if( kit.getId_estado()!=OptionsCfg.KIT_ESTADO_ALQUILADO) {%>
                                <button type="submit" class="btn btn-default" name="btnSubmit" id="btnSubmit">Guardar</button>                                
                                <%} else { %>
                                <h4>El kit se encuentra utilizado y no puede ser modificado</h4>
                                <% } %>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.KIT%>">Cancelar</a>
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
        var $invoker;
        $(document).ready(function(){            
            $('.date-picker').mask('99/99/9999');

            $('.date-picker').datepicker({
                language: 'es'
            }); 
//            $('.inCodigo').focusout(buscarActivo); 
             
            $('#btnSubmit').click(submitForm);
            $('#btnSelActivos').click(selActivos); 
            $('.btnDelActivo').click(deleteActivo);
            $('#mdlActivo').on('show.bs.modal', function (e) {
                
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
                $id_rubro = $('#id_rubro');
                $id_subrubro = $('#id_subrubro');
                
                loadDataActivo({ id_rubro: $id_rubro.val(),
                              id_subrubro: $id_subrubro.val(),
                             
                });
                
            });
             $('#mdlActivo').on('hide.bs.modal', function (e) {
                 if($invoker!==null)             
                    $invoker.parent().find('input').focus();    
                
            });
            $('#btnAgregar').click(function(){agregarActivo({})});
            $('#id_rubro').change(function(){
                rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val()})
            });
            $('#id_subrubro').change(function() {
                $id_rubro = $('#id_rubro');
                $id_contrato = $('#id_contrato');
                
                loadDataActivo({id_subrubro:$(this).val(),
                                id_rubro:$id_rubro.val(),
                                id_contrato: $id_contrato.val(),
                });
            });
            $('#id_rubro_kit').change(function(){
                rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val(),id_contrato:$('#id_contrato').val()},$('#id_subrubro_kit'))
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
      function buscarActivo(){            
        // Combino los parametros por si no viene definido ninguno                      
        var $tr = $(this).parent().parent().parent();          
        var codigo = $(this).val().trim();        
        
        var cant = 1;
        if (codigo==='') {completarActivo({desc:'',
                                            cant:1},$tr); 
                          return; }
        var data={codigo:codigo};
        searchData('<%= PathCfg.ACTIVO_SEARCH %>',data,function(result) {
                if(result.Result === "OK") {                       
                    if(result.TotalRecordCount===1){ 
                        completarActivo({desc:result.Record.desc_larga,
                                         cant:1},$tr)                                                
                    }else {
                        completarActivo({
                            desc:'',
                            cant:0
                        },$tr)                           
                    }
                }
            });
        } 
        function completarActivo(data,$tr){
            var $desc = $tr.find('td.descripcion');            
            $desc.text(data.desc);            
        } 
       function agregarActivo(data){
           
           var html = generarHtml(data);

            $('#tblKit_detalle').find('tbody').append(html);            
            $('.btnDelActivo').click(deleteActivo);
            var elem = $('#tblKit_detalle tbody tr:last').find('.inCodigo');
            //elem.focusout(buscarActivo);            
            elem.on('keypress keydown',function(e){
                var codigo = e.keyPress || e.which;
                if(codigo ===13) {
                    e.preventDefault();
                    buscarActivo.bind(this)();
                }
            });
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
           var descripcion = (data.descripcion!==undefined)?data.descripcion:"";           
           var rubro = (data.rubro!==undefined)?data.rubro:"";
           var subrubro = (data.subrubro!==undefined)?data.subrubro:"";
           var html = '<tr>';                
               html += '<td style="width:150px">' +
                            '<span class="input-group">' +
                                '<span class="input-group-addon"  data-toggle="modal" data-target="#mdlActivo"><span class="fa fa-search fa-fw"></span></span>' +
                                '<input type="text" class="form-control inCodigo" data-rubro="'+ rubro+'"  data-subrubro="'+ subrubro+'" name="codigoActivo" placeholder="C&oacute;digo" size="20" value="' + codigo +'">' +
                            '</span>' +
                        '</td>' +

                        '<td class="descripcion">'+ descripcion +'</td>' +                        
                        '<td style="width:60px"><span class="btn btn-xs btn-circle btn-danger btnDelActivo"> <span class="fa fa-minus fw"></span></span></td>' + 
                    '</tr>'; 
            return html;
        }
        function selActivos(){
            
            var $arr = $('input.chkSelActivo:checked');
            for(var i = 0;i<$arr.length;i++){
                var $codigo = $($arr[i]).data('codigo'); 
                var $descripcion = $($arr[i]).data('descripcion'); 
                var $pos = $($arr[i]).data('pos'); 
                var $cant = $tr.find('input[name="cantActivo"]');
                if ($cant.val()==="") $cant.val(1);
                var html = generarHtml({codigo:$codigo,pos:$pos ,cant:$cant.val(),descripcion:$descripcion,disabled:true});
                $tr.before(html);
                /* var $tabla = $('#tblKit_detalle');
                var html_ant = $tabla.find('tbody').html();
                $tabla.find('tbody').html(html_ant + html);
                */
                //agregarActivo({codigo:$codigo,pos:$pos.val(),cant:$cant.val(),descripcion:$descripcion});
            }
            //$tr.remove();
            $('.btnDelActivo').click(deleteActivo);
//            agregarActivo({});
            //$('#mdlActivo').modal('hide');
        } 
        function validar(){
//            var $fecha = $('#fecha');
//            var $codigo = $('#codigo');
            var $nombre = $('#nombre');
            var $id_rubro = $('#id_rubro_kit');
            var $id_subrubro = $('#id_subrubro_kit');
            
            
            
//           if(!validarCampo($fecha,"Ingrese la fecha",validarCampoFecha)) return false; 
           if(!validarCampo($nombre,"Ingrese el nombre del kit ",function(){return false;})) return false; 
           if(!validarCampo($id_rubro,"Ingrese el rubro del kit ",validarNoCero)) return false; 
           if(!validarCampo($id_subrubro,"Ingrese el subrubro del kit ",validarNoCero)) return false; 
           return true;
//           if(!validarCampo($fecha,"Ingrese la actividad",validarNoCero)) return false; 
//           if(!validarCampo($fecha,"Ingrese el resultado",validarNoCero)) return false; 
        }
    </script>
    <!-- Modal -->

    <%@include file="activo_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>    
</body>

</html>
