<%@page import="utils.PathCfg"%>

<script src="bower_components/jquery-form/jquery.form.min.js"></script>

<div id="mdlRemitoDiario" class="modal fade" role="dialog">
  <div class="modal-dialog">  
    <div class="modal-content">     <!-- Modal content-->     
        <form action="<%=PathCfg.REMITO_DIARIO%>" method="POST" name="" id="">
            <input type="hidden" name="id" id="id"   value="">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">Crear Remito diario</h4>
            </div>
       <div class="modal-body">
            <div class="row">
                <form class="form-vertical">
                    <input id="id" name="id" type="hidden" class="" value='' >
                    <div class="col-lg-12">
                       <div class="col-lg-6">
                            <div class="form-group ">
                               <label for="fecha_diario">Fecha</label>
                               <input class="form-control date-picker" name="fecha_diario" id="fecha_diario">
                            </div>
                       </div>
                      <div class="col-lg-6">
                            <div class="form-group ">
                              <label for="punto_venta">N&uacute;mero</label>
                              <input class="form-control" name="numero_diario" id="numero_diario">
                           </div>
                       </div>
                    </div>
                     <div class="col-lg-12">
                        <div class="form-group">
                            <label for="observaciones">Observaciones</label>
                            <textarea name="observaciones" id="observaciones" class="form-control" ></textarea>
                        </div>
                    </div>
                </form>
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btnRemitoDiario">Crear</button>
        <button type="cancel" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
        </form>
    </div>
  </div>
</div>
<script>
$(document).ready(function() {
    $('.date-picker').datepicker({
                   language: 'es'
   }); 
   $('.date-picker').mask('99/99/9999');
   $('#mdlRemitoDiario').on('shown.bs.modal',function(){
       $('#fecha_diario').val("");
       $('#numero_diario').val(""); 
       $('#observaciones').val(""); 
   });
   $('#btnRemitoDiario').click(function(){
       var fecha  = $('#fecha_diario').val();
       var numero = $('#numero_diario').val();      
       var observaciones = $('#observaciones').val();      
       var id = $('#id').val();      
      $('#mdlRemitoDiario').modal('hide');
      $.ajax({
            url:'<%= PathCfg.REMITO_DIARIO%>',
            data: {id:id,numero:numero,fecha:fecha,observaciones:observaciones},
            method:'POST',
            dataType:'json',
            success:function(data){
                if(data.Result === "OK") {
                   bootbox.alert("<b>El remito fue creado satisfactoriamente.</b><br> Se procede a su impresi&oacute;n")
                   location.href='<%= PathCfg.REMITO_PRINT %>?id=' + data.Record.id;
                   loadData({});
                } else if (data.Message) bootbox.alert(data.Message);
            }
       });
   });
   
});
</script>