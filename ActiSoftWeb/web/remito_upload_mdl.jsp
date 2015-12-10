<%@page import="utils.PathCfg"%>
<%
    
%>
<script src="bower_components/jquery-form/jquery.form.min.js"></script>

<div id="mdlRemitoUpload" class="modal fade" role="dialog">
  <div class="modal-dialog">  
    <div class="modal-content">     <!-- Modal content-->     
        <form action="<%=PathCfg.REMITO_UPLOAD%>" method="POST" enctype="multipart/form-data" name="upload_form" id="upload_form">
            <input type="hidden" name="id" id="id"   value="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Upload de archivo</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-lg-12">
              <div class="form-group fileupload fileupload-new" data-provides="fileupload">
                  
                      <label class="" for="file_name">Archivo</label>       
                      <input type="file" size="60" name="file_name" id="file_name">
                  
                  <div id="progress">
                    <div id="bar"></div>
                    <div id="percent">0%</div >
                  </div>
                  <div id="message"></div>
              </div>
          </div>
        </div>
    </div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-default" id="btnUpload" value="Upload">
        <button type="cancel" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
        </form>
    </div>
  </div>
</div>
<script>
$(document).ready(function() {
    var MAX_FILE_SIZE = 1024 * 1024 * 2;
    $('#file_name').change(function(e) {
        // Chequear extension!!
        //Chequeamos el tamaño de archivo
//        if (this.files[0].size < MAX_FILE_SIZE) {
//            $("#submit").removeAttr('disabled');
//        } else {
//            $("#submit").attr('disabled', 'disabled');
//            $("#progress").show(200);
//            $("#message").html("<font color='red'> ERROR: El tama&ntilde;o de archivo supera el maximo permitido </font>");
//        }
    });
    
    $("#upload_form").ajaxForm({
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
            if (response.Result == "OK") {
                $("#message").html("<font color='green'>" + response.Message + "</font>");
                $("#bar").css("background-color", "#B4F5B4");                                
//              var html = "<div class='controls' id='fechaCV'>";
//              html += "<span class='lead'>Fecha Ultimo CV:" + data.fecha + "</span><br>"
//              html += "<a target='_' href='data/" + data.url + "'>Descargar mi CV</a>";
//              html += "</div>";
                var html = "";
                location.reload();
//                                $("#inPerfilUltimoCV").html(html);
            } else {
                $("#bar").css("background-color", "red");
                $("#message").html("<font color='red'> " + response.Result + ":" + response.Message + " </font>");
            }
        },
        complete: function(response) {
            console.log(response);
            var data = JSON.parse(response.responseText);
           
        },
        error: function() {

        }
    });

});
</script>