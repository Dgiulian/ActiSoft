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

<%@page import="utils.OptionsCfg.Option"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="bd.Localidad"%>
<%@page import="bd.Provincia"%>
<%@page import="transaccion.TProvincia"%>
<%@page import="java.util.List"%>
<%@page import="bd.Cliente"%>
<%        
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
                    <h1>Etiquetas de archivos</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                <form action="<%= PathCfg.ACTIVO_ETIQUETA%>" method="POST"  role="form">                    
                <div class="col-lg-12">
                    <div class="panel panel-default">
                      <div class="panel-heading"> Impresi&oacute;n de etiquetas de activos </div>            
                      <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h3>Activos <span class="btn btn-default" id="btnAgregar"> Agregar</span> </h3>
                                <table class="table table-bordered table-condensed table-responsive table-striped" id="tblEtiqueta">
                                    <thead>
                                        <tr>
                                            <th>C&oacute;digo</th>
                                            <th>Descripci&oacute;n</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-content">
                                                                                                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </tfoot>
                                </table>
                                
                                <button type="submit" class="btn btn-default" id="btnSubmit">Generar</button>
                                <a type="reset" class="btn btn-default" href="<%=PathCfg.ACTIVO%>">Cancelar</a>
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
        var $invoker = null;

    $(document).ready(function(){
            $('#btnSelActivos').click(selActivos);                       
            $('#selTodos').change(function(e){
                selTodos('input.chkSelActivo',$(this).prop('checked'));
            });

            $('#mdlActivo').on('show.bs.modal', function (e) {
                 $id_contrato = $('#id_contrato') ;
                 if ($id_contrato===null || $id_contrato.val()===""){
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
                $id_rubro = $('#id_rubro');
                $id_subrubro = $('#id_subrubro');
                $id_contrato = $('#id_contrato');
                
                
                loadDataActivo({ id_rubro: $id_rubro.val(),
                              id_subrubro: $id_subrubro.val(),
                              id_contrato: $id_contrato.val(),
                },true);
                

            });
             $('#mdlActivo').on('hide.bs.modal', function (e) {
                 if($invoker!==null)             
                    $invoker.parent().find('input').focus();    
                
            });
            
            //$('#mdlActivo').modal('show');
            
           // $('.inCodigo').focusout(buscarActivo);
            $('#btnAgregar').click(function(){agregarActivo({})});
            $('#id_rubro').change(function(){
                rubroChange("<%= PathCfg.SUBRUBRO_LIST%>",{id_rubro:$(this).val(),id_contrato:$('#id_contrato').val()})
            });
            $('#id_subrubro').change(function() {
                $id_rubro = $('#id_rubro');
                $id_contrato = $('#id_contrato');
                
                loadDataActivo({id_subrubro:$(this).val(),
                                id_rubro:$id_rubro.val(),
                                id_contrato: $id_contrato.val(),
                                
                },true);
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
                $('form').submit();
            });
            agregarActivo({});
        });    

        function selActivos(){
            var $arr = $('input.chkSelActivo:checked');
            for(var i = 0;i<$arr.length;i++){
                $codigo = $($arr[i]).data('codigo'); 
                $descripcion = $($arr[i]).data('descripcion'); 
                $pos = $($arr[i]).data('pos'); 
                $cant = $tr.find('input[name="cantActivo"]');
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
            return true;
        }
        function totalActivos(){
            var $tabla = $('#tblEtiqueta');
            var cant = 0;
            $tabla.find('tbody tr').each(function(){
                console.log($(this).val());
                if($(this).find('input').val()!=='') cant++;
            }); 
            var html = '<tr><td colspan="2"><span class="pull-left">Total de activos seleccionados:</td><td>'+ cant + '</td></tr>';
            $tabla.find('tfoot').html(html);
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
           
           html = generarHtml(data);
                
            $('#tblEtiqueta tbody').append(html);
            $('.inCodigo').focusout(buscarActivo);
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
                html += '</span></td>';               
                html += '<td class="descripcion">'+ descripcion +'</td>' + 
                        '<td style="width:60px"><span class="btn btn-xs btn-circle btn-danger btnDelActivo"> <span class="fa fa-minus fw"></span></span></td>' + 
                    '</tr>'; 
            return html;
        }
        
        
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
    </script>
    <!-- Modal -->

    <%@include file="activo_mdl.jsp" %>
    <%@include file="tpl_footer.jsp"%>
</body>

</html>
