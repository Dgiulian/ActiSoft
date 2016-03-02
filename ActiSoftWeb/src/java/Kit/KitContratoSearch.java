/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import Kit.*;
import bd.Kit;
import bd.Kit_contrato_view;
import bd.Rubro;
import bd.Subrubro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TKit;
import transaccion.TKit_contrato_view;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class KitContratoSearch extends HttpServlet {
    HashMap<Integer,Rubro> mapRubros;
    HashMap<Integer,Subrubro> mapSubrubros;
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
        
        String codigo = request.getParameter("codigo");
        Integer id_contrato = Parser.parseInt(request.getParameter("id_contrato"));
        
        mapRubros = new TRubro().getMap();
        
        mapSubrubros = new TSubrubro().getMap();
        
        Kit_contrato_view activo = null;
        JsonRespuesta jr = new JsonRespuesta();           
        try {            
            if(!codigo.equals("")){
                activo = new TKit_contrato_view().getByCodigo(id_contrato,codigo);
            } else {
                throw new BaseException("ERROR","No se definió ningún parámetro de busqueda");
            }
            
//            if(activo.getId_estado()!=OptionsCfg.ACTIVO_DISPONIBLE){
//                throw new BaseException("Error","Kit no disponible");
//            }
            
            if (activo != null) {
                jr.setTotalRecordCount(1);
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecord(activo);
        } catch (BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        }finally { 
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }
 private class KitDet extends Kit{
     String rubro = "";
     String cod_subrubro = "";
     String subrubro = "";
     
     public KitDet(Kit activo){
         super(activo);
         this.rubro = activo.getId_rubro().toString();
         this.subrubro = activo.getId_subrubro().toString();
         Rubro r = mapRubros.get(activo.getId_rubro());
         if (r!=null){
             this.rubro = r.getDescripcion();
         }
         Subrubro s = mapSubrubros.get(activo.getId_subrubro());       
         if (s!=null) {this.subrubro = s.getDescripcion(); this.cod_subrubro = s.getCodigo();};             
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
}
