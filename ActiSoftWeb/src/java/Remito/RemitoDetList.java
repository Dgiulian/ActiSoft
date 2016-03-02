/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Activo;
import bd.Kit;
import bd.Remito;
import bd.Remito_detalle;
import bd.Rubro;
import bd.Subrubro;
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
import transaccion.TActivo;
import transaccion.TKit;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.BaseException;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class RemitoDetList extends HttpServlet {
    
    HashMap<Integer, Rubro> mapRubros       ;
    HashMap<Integer, Subrubro> mapSubrubros;
    TActivo ta;
    TKit    tk;
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
        Remito remito = null;
        JsonRespuesta jr = new JsonRespuesta();
        String jsonResult = new Gson().toJson(jr);
        mapRubros       = new TRubro().getMap();
        mapSubrubros    = new TSubrubro().getMap();
        ta = new TActivo();
        tk = new TKit();
        try{
            Integer id_remito;
            try{
                id_remito = Integer.parseInt(request.getParameter("id_remito"));
            } catch (NumberFormatException ex){
                throw new BaseException("Error" ,"No se ha encontrado el remito");                                
            }
            remito = new TRemito().getById(id_remito);
            if(remito==null) throw new BaseException("Error" ,"No se ha encontrado el remito");   ;
            List<Remito_detalle >  lista = new TRemito_detalle().getByRemitoId(id_remito);
            ArrayList<Remito_detalleDet> lstDet = new ArrayList<Remito_detalleDet>();
            if (lista != null) {
                for(Remito_detalle d:lista){
                    lstDet.add(new Remito_detalleDet(d));                    
                }
                jr.setRecords(lstDet);
                jr.setTotalRecordCount(lstDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }
            jr.setResult("OK");
        
        } catch( BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
            
            
        } finally {            
            out.print(new Gson().toJson(jr));
            out.close();
        }
        
    }

    
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
    
    private class Remito_detalleDet extends  Remito_detalle{
        private String  codigo       = "";
        private String  codigoNew     = "";
        private String  desc_corta          = "";
        private String  desc_larga          = "";
        private String  desc_opcional       = "";
        
        private String  longitud = "";
        
        private Integer id_rubro = 0;
        private Integer id_subrubro = 0;
        private String  rubro_opcional      = "";
        private String  subrubro_opcional   = "";
        
        Remito_detalleDet(Remito_detalle detalle){
            super(detalle);
            if(detalle.getId_activo()!=0){
                Activo activo = ta.getById(detalle.getId_activo());
                if (activo!=null){
                    codigo        = activo.getCodigo();
                    codigoNew     = activo.getCodigoNew();
                    desc_corta    = activo.getDesc_corta();
                    desc_larga    = activo.getDesc_larga();
                    desc_opcional = activo.getDesc_opcional();
                    longitud      = activo.getLongitud();
                    id_rubro      = activo.getId_rubro();
                    id_subrubro   = activo.getId_rubro();
                    
                }
            } else {
                Kit kit = tk.getById(detalle.getId_kit());
                if(kit!=null){
                    codigo        = kit.getCodigo();
                    codigoNew     = kit.getCodigo();
                    desc_corta    = kit.getNombre();
                    desc_larga    = kit.getNombre();
                    desc_opcional = kit.getNombre();
                    longitud      = "";
                    id_rubro      = kit.getId_rubro();
                    id_subrubro   = kit.getId_rubro();
                }
            }
            Rubro rubro = mapRubros.get(id_rubro);
            rubro_opcional = rubro!=null?rubro.getDesc_opcional():id_rubro.toString();
            
            Subrubro subrubro = mapSubrubros.get(id_subrubro);
            subrubro_opcional = (subrubro!=null)?subrubro.getDesc_opcional():subrubro.toString();
        }
    }
  
}
