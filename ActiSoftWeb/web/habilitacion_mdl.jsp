<%@page import="utils.OptionsCfg"%>
<%@page import="transaccion.TParametro"%>
<%@page import="bd.Parametro"%>

<script src="bower_components/jquery-form/jquery.form.min.js"></script>
<%@page import="utils.PathCfg"%>
<div id="mdlHabilitacion" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Habilitaciones <span class="btn btn-primary" id="nuevaHabilitacion"><span  class="fa  fa-file-o fa-fw"> </span>Nuevo</span></h4>
        <input type="hidden" id="id_responsable" name="id_responsable" value="">
      </div>
      <div class="modal-body container-fluid">
          <div class="col-lg-12">
              <table class="table table-bordered table-condensed" id="tblHabilitacion">
                  <colgroup>
                      <col style="width: 5%;"></col>
                      <col style=""></col>
                      <col style="width: 15%;"></col>
                  </colgroup>
                  <thead>
                      <tr>
                        <th>Fecha</th>
                        <th>Archivo</th>
                        <th></th>
                      </tr>
                  </thead>
                  <tbody class="table-content"></tbody>
              </table>
          </div>
      </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>

<script>
    $(document).ready(function(){
       $('#nuevaHabilitacion').click(function(){
            var id_proveedor    = $('#id_proveedor').val();
            var id_responsable  = $('#id_responsable').val();
            var index    = 0;
            var fecha   = "";
            var archivo = "";
            agregarHabilitacion({id:index,id_proveedor: id_proveedor,id_responsable:id_responsable,fecha: fecha,archivo: archivo});
        });



    });
    function loadDataHabilitacion(data){
        var $tabla = $('#tblHabilitacion');
        $.ajax({
               url: '<%= PathCfg.HABILITACION_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
               beforeSend:function(){
                    var cant_cols = $tabla.find('thead th').length;
                    $tabla.find('tbody').html("<tr><td colspan='" + cant_cols + "'><center><img src='images/ajax-loader.gif'/></center></td></tr>");
               },
               success: function(data) {
                   if(data.Result === "OK") {
                       $tabla.find('tbody').html(createTableHabilitacion(data.Records));
                       $tabla.find('.btn-del').click(borrarHabilitacion);
                       $tabla.find('.btn-edit').click(editarHabilitacion);
                   } else {
                       bootbox.alert(data.Message);
                       $('#mdlHabilitacion').modal('hide');
                   }
               }
        });
    }
    function createTableHabilitacion(data){
        console.log(data);
        var html = "";
        var objPos = {};
        for(var i = 0;i< data.length;i++){

           html +="<tr class=''>";
           d = data[i];
          html += wrapTag('td',convertirFecha(d.fecha),'');
          var htmlDownload = '<a class="btn btn-primary" href="Download?type=habilitacion&id=' + d.id + '">' + d.archivo+ '</a>';
          html += wrapTag('td',htmlDownload,'');
          var htmlEdit ='<span data-index="' + d.id + '" data-nombre="data.nombre" data-apellido="data.apellido"  class="btn btn-xs btn-circle btn-warning btn-edit"><span class="fa fa-edit fw"></span></span>';
          var htmlDel = '<span data-index="' + d.id + '" class="btn btn-xs btn-danger btn-circle btn-del"><span class="fa fa-trash fw"></span></span>';
          html += wrapTag('td',htmlEdit + " " + htmlDel,'');
           html +="</tr>";
       }
       return html;
    }
    function borrarHabilitacion(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%=PathCfg.HABILITACION_DEL%>' ,{id:id},function(result) {
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
     function editarHabilitacion(){
        var index  = $(this).data('index');
        var id_proveedor = $('#id_proveedor').val();
        var id_responsable = $('#id_responsable').val();
        var fecha       = $(this).data('fecha');
        var archivo      = $(this).data('archivo');

        agregarHabilitacion({id:index,id_proveedor:id_proveedor,id_responsable: id_responsable,fecha: fecha,archivo: archivo});
    }
   function agregarHabilitacion(data){
        var titulo = data.id?"Editar Habilitacion":"Nueva Habilitacion";
        bootbox.dialog({
                title: titulo,
                message: Handlebars.templates['habilitacion.edit'](data),
                buttons: {
                    success: {
                        label: "Guardar",
                        className: "btn-success",
                        callback: function () {
                            var data = recuperarCamposHabilitacion();
                            if (!validarHabilitacion(data)) return;
                            subirForm(data);
                        }
                    },
                    cancel: {
                        label: "Cancelar",
                        callback: function () {}
                    }
                }
            }).init(function(){
                if($().datepicker) {
                    $('.date-picker').datepicker({
                        language: 'es',
                        locale:'es-AR',
                        format:'dd/mm/yyyy',
                        dateFormat:'dd/mm/yyyy',
                        autoclose: true
                    });
                  }
        });
    }
    function validarHabilitacion(data){
         if(data.fecha===undefined || data.fecha==="" || !validarFecha(data.fecha)){
            bootbox.alert("Debe ingresar la fecha de la habilitacion");
            return false;
        } 
        if( data.archivo===""){
                bootbox.alert("Debe ingresar el archivo de la habilitacion");                
                return false;
        }
        return true;
    }
    function recuperarCamposHabilitacion(){
        var data = {};
        data.id           = $('#id').val();
        data.id_proveedor = $('#id_proveedor').val();
        data.id_responsable = $('#id_responsable').val();
        data.fecha          = $('#fecha').val();
        data.archivo        = $('#archivo').val();
        return data;
    }
    function subirForm(data){
         $("#upload_form").ajaxSubmit({
        method:"POST",
        dataType: "json",
        beforeSend: function() {
            $("#progress").show(); //clear everything
            $("#bar").width('0%').css("background-color", "#B4F5B4");
            $("#message").html("");
            $("#percent").html("0%");
        },
        uploadProgress: function(event, position, total, percentComplete)
        {
            $("#bar").width(percentComplete + '%');
            $("#percent").html(percentComplete + '%');
        },
        success: function(response) {
            $("#bar").width('100%');
            $("#percent").html('100%');
            if (response.status === "OK") {
                $("#message").html("<font color='green'>" + response.message + "</font>");
                $("#bar").css("background-color", "#B4F5B4");
                var html = "";
            } else {
                $("#bar").css("background-color", "red");
                $("#message").html("<font color='red'> " + response.Result + ":" + response.Message + " </font>");
            }
            loadDataHabilitacion(data);
        },
        complete: function(response) {
            data = JSON.parse(response.responseText);
        },
        error: function() {}
    });
    }
     </script>