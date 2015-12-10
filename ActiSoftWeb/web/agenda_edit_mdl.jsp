<%@page import="utils.PathCfg"%>
<div id="mdlAgenda" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Nuevo evento</h4>
      </div>
      <div class="modal-body container-fluid">
          <form action="<%=PathCfg.AGENDA_EDIT%>" method="POST">
                <div class="col-lg-8">
                  <div class="form-group">
                      <label for="titulo" >Titulo</label>
                      <input type="text" name="titulo" id="titulo" class="form-control" value="">
                  </div>
                  <div class="row">
                      <div class="col-lg-6">
                          <div class="form-group">
                            <label for="desde_fecha" >Desde</label>
                            <input type="text" name="desde_fecha" id="desde_fecha" class="form-control date-picker" value="">
                        </div>
                      </div>
                      <div class="col-lg-6">
                          <div class="form-group">
                              <label for="hasta_hora" >Hora</label>
                              <input type="text" class="form-control hora" name="desde_hora" id="hasta_hora" value="">
                          </div>
                      </div>
                  </div>
                    <div class="row">
                      <div class="col-lg-6">
                          <div class="form-group">
                            <label for="hasta_fecha" >Hasta</label>
                            <input type="text" name="hasta_fecha" id="hasta_fecha" class="form-control date-picker" value="">
                        </div>
                      </div>
                      <div class="col-lg-6">
                          <div class="form-group">
                              <label for="hasta_hora" >Hora</label>
                              <input type="text" class="form-control hora" name="hasta_hora" id="hasta_hora" value="">
                          </div>
                      </div>
                  </div>
              </div>    

              <div class="col-lg-4">                      
                    <div class="form-group">
                      <label for="titulo" >Descripci&oacute;n</label>
                      <textarea  class="form-control"></textarea>
                  </div>
            </div>
          </form>
        </div>
      <!--</div>-->
      <div class="modal-footer">
          <input type="submit" id="guardar" class="btn btn-primary" value="Guardar">
        
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>
<script>
$(document).ready(function(){
   
   $('#guardar').click(submitForm);    
   $('#mdlAgenda').on('show.bs.modal',function(){
        
   });
}) ;
function validar(){    
    return true;
}
</script>