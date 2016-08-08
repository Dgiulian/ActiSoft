/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RemitoContrato;

import bd.Remito;
import bd.Remito_contrato;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import transaccion.TRemito;
import transaccion.TRemito_contrato;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class RemitoContratoList extends HttpServlet {
    TRemito tr;
    HashMap<Integer,Option> mapUnidades;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        JsonRespuesta jr = new JsonRespuesta();
        tr = new TRemito();
        mapUnidades = OptionsCfg.getMap(OptionsCfg.getUnidades());
        try {
            Integer id_remito = Integer.parseInt(request.getParameter("id_remito"));            
            
            List<Remito_contrato> lista;
            TRemito_contrato trc = new TRemito_contrato();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            if(id_remito==0) throw new BaseException("ERROR","Indique el remito");

            Remito remito = new TRemito().getById(id_remito);
            if (remito ==null)  throw new BaseException("ERROR","El remito no existe");
            
            mapFiltro.put("id_remito", id_remito.toString());
            lista = trc.getListFiltro(mapFiltro);
            List<Remito_contratoDet> listaDet = new ArrayList();
            if (lista != null) {
                for(Remito_contrato remito_contrato:lista){
                    listaDet.add(new Remito_contratoDet(remito_contrato));
                }
                if(remito.getId_tipo_remito()==OptionsCfg.REMITO_DEVOLUCION) {
                    // Si es un remito de devolución. Se busca el remito de entrega (indicado por el campo id_referencia)
                    //
                    List<Remito_contrato> det_inicio  = trc.getByRemitoId(remito.getId_referencia());
                    for(Remito_contrato rc : det_inicio){
                        if(rc.getActivo_id_rubro()!=OptionsCfg.RUBRO_TRANSPORTE) continue;
                         rc.setId_referencia(rc.getId_remito());
                         listaDet.add(new Remito_contratoDet(rc));
                    }
                }
                
                jr.setTotalRecordCount(listaDet.size());                
            } else {
                jr.setTotalRecordCount(0);
            }
            jr.setResult("OK");
            jr.setRecords(listaDet);
        } catch(BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } finally {          
              String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }
private class Remito_contratoDet extends Remito_contrato{
    Remito remito_inicio;
    Remito remito_cierre;
    Integer dias;
    Float dias_herramienta;
    String divisa;
    String unidad;
    public Remito_contratoDet(Remito_contrato rc){
        super(rc);
        remito_inicio = tr.getById(rc.getId_referencia());        
        remito_cierre = tr.getById(rc.getId_remito());    
        if(rc.getActivo_id_rubro()!=OptionsCfg.RUBRO_TRANSPORTE)
            dias = TFecha.diferenciasDeFechas( remito_inicio.getFecha(),remito_cierre.getFecha()) + 1;
        else dias = !tr.esTransitorio(remito_cierre)?1:1;  //!tr.esTransitorio(remito_cierre)?1:2
        /*12/07/2016 Se modificó la cantidad de días si es un remito transitorio*/; 
        divisa = rc.getContrato_detalle_id_divisa()==0?"U$S":"$";
        
        Option o = mapUnidades.get(rc.getContrato_detalle_id_unidad());
        unidad = (o!=null)? o.getDescripcion():"";
    }
}
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
