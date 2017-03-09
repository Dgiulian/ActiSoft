<%@page import="transaccion.TContrato"%>
<%@page import="bd.Contrato"%>
<%@page import="bd.Cliente"%>
<%@page import="transaccion.TCliente"%>
<%@page import="utils.OptionsCfg.Option"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="transaccion.TSubrubro"%>
<%@page import="bd.Subrubro"%>
<%@page import="transaccion.TRubro"%>
<%@page import="bd.Rubro"%>
<%@page import="bd.Rubro"%>
<%@page import="java.util.List"%>
<%@page import="utils.OptionsCfg"%>
<%@page import="utils.PathCfg"%>
<%   
    List<Cliente>  lstCliente  = new TCliente().getList();
    List<Contrato> lstContrato = new TContrato().getList();
%>
<div id="mdlActivoExport" class="modal fade " role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
     <!--modal-lg--> 
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Exportaci&oacute;n de activos</h4>
      </div>
      <div class="modal-body">
          <div class="row">
              <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_estado">Estado</label>
                        <select class="form-control" name="exp_estado" id="exp_estado">
                            <option value="0" selected>Todos</option>
                            <% for(Option o: OptionsCfg.getEstadoActivo()){%>
                              <option value="<%=o.getId()%>"><%=StringEscapeUtils.escapeHtml4(o.getDescripcion())%></option>
                            <%}%>
                            
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_resultado">Certificado</label>
                        <select class="form-control" name="exp_resultado" id="exp_resultado">
                            <option value="0" selected>Todos</option>
                            <% for(Option o: OptionsCfg.getEstadoCertificados()){%>
                              <option value="<%=o.getId()%>"><%=StringEscapeUtils.escapeHtml4(o.getDescripcion())%></option>
                            <%}%>
                            
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_cliente">Cliente</label>
                        <select class="form-control" name="exp_cliente" id="exp_cliente">
                            <option value="0" selected>Todos</option>
                            <% for(Cliente c: lstCliente){%>
                              <option value="<%=c.getId()%>"><%=StringEscapeUtils.escapeHtml4(c.getNombre())%></option>
                            <%}%>                            
                        </select>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="form-group">
                        <label class="" for="exp_contrato">Contrato</label>
                        <select class="form-control" name="exp_contrato" id="exp_contrato">
                            <option value="0" selected>Todos</option>
                            <% for(Contrato c: lstContrato){%>
                              <option value="<%=c.getId()%>"><%=StringEscapeUtils.escapeHtml4(c.getNumero())%></option>
                            <%}%>                            
                        </select>
                    </div>
                </div>
             
          </div>    
        <hr>
          <div class="row">
              <div class="col-lg-3">
                  <div class="form-group">
                      <label for="fecha_venc">Fecha Vencimiento</label>
                      <input type="text" name="fecha_venc" id="fecha_venc" class="form-control date-picker" >
                  </div>
              </div>
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
       $('#btnExportar').click(exportarActivo);
       
    });
    function exportarActivo(){
        var id_estado      = $('#exp_estado').val();
        var id_resultado   = $('#exp_resultado').val();
        var id_cliente     = $('#exp_cliente').val();
        var id_contrato    = $('#exp_contrato').val();
        var fecha_venc     = $('#fecha_venc').val();
        var id_reporte     = $('input[name="exp_reporte"]:checked').val();
        
        var location = "<%=PathCfg.ACTIVO_EXPORT%>?id_reporte=" + id_reporte;
        location += "&id_estado="+id_estado;    
        location += "&id_resultado="+id_resultado;
        location += "&id_cliente="+id_cliente;
        location += "&id_contrato="+id_contrato;
        location += "&fecha_venc="+fecha_venc;
        console.log(location);    
        window.location = location;
        
    }
     </script>