<%@page import="transaccion.TCliente"%>
<%@page import="bd.Cliente"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>


<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<%           
    List<Cliente> lstClientes = new TCliente().getList();
%>
<div id="mdlPreticketExport" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Exportaci&oacute;n de pretickets</h4>
      </div>
      <div class="modal-body">
         <div class="row">
              
              <div class="col-lg-5">
                    <div class="form-group">
                        <label for="">Cliente</label>
                        <select class="form-control" name="exp_cliente" id="exp_cliente">
                            <option value="0"> Todos</option>
                          <% for(Cliente cliente: lstClientes) {%> 
                          <option value="<%=cliente.getId()%>"><%=cliente.getNombre()%></option>
                          <% } %>
                        </select>
                    </div>
                </div>
<!--              <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_estado">Estado</label>
                        <select class="form-control" name="exp_estado" id="exp_estado">
                            <option value="0" selected>Todos</option>
                            
                            
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_preticket">Preticket</label>
                        <select class="form-control" name="exp_preticket" id="exp_preticket">
                            <option value="-1" selected>Todos</option>                            
                            <option value="1" selected>Si</option>
                            <option value="0" selected>No</option>                            
                        </select>
                    </div>
                </div>            
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_pozo">Pozo</label>
                        <select class="form-control" name="exp_pozo" id="exp_pozo">
                            <option value="0" selected>Todos</option>
                    
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_equipo">Equipo</label>
                        <select class="form-control" name="exp_equipo" id="exp_equipo">
                            <option value="0" selected>Todos</option>
                                                  
                        </select>
                    </div>
                </div>
                
             -->
          </div>
          <div class="row">
                 <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_reporte">Formato</label>
                        <input type="radio" name="exp_reporte" value="1" checked>Pdf
                        <input type="radio" name="exp_reporte" value="2">Excel
                    </div>
                </div>        
            </div>   
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-info" id="btnExportar">Exportar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script>
    $(document).ready(function(){
       $('#btnExportar').click(exportarPreticket);
       
    });
   function getDatosExport(){
        var data = {};
//        data.id_estado    = $('#exp_estado').val();
//        data.id_tipo      = $('#exp_tipo').val();
//        
          data.id_cliente   = $('#exp_cliente').val();
//        data.id_contrato  = $('#exp_contrato').val();
//        
//        data.id_pozo      = $('#exp_pozo').val();
//        data.id_equipo    = $('#exp_equipo').val();
//        data.id_preticket = $('#exp_preticket').val();
        
        
//        data.id_cliente   = $('#id_cliente').val();
//        data.id_contrato  = $('#id_contrato').val();

        data.id_reporte   = $('input[name="exp_reporte"]:checked').val();
        return data;
   }
    function exportarPreticket(){
       var data =  getDatosExport();
        
        var location = "<%=PathCfg.PRETICKET_EXPORT%>?id_reporte=" + data.id_reporte;        
            location += "&id_cliente="+data.id_cliente;
        window.location = location;
        
    }
     </script>