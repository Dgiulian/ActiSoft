/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activo;

import bd.Activo;
import bd.Subrubro;
import bd.Rubro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TSubrubro;
import transaccion.TRubro;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class ActivoSearch extends HttpServlet {

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
     
     HashMap<Integer,Rubro> mapRubros;
     HashMap<Integer,Subrubro> mapSubrubros;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        
        String codigo = request.getParameter("codigo");
        
        mapRubros = new TRubro().getMap();
        
        mapSubrubros = new TSubrubro().getMap();
        
        Activo activo = null;
        JsonRespuesta jr = new JsonRespuesta();           
        try {            
            if(!codigo.equals("")){
                activo = new TActivo().getByCodigo(codigo);
            } else {
                throw new BaseException("ERROR","No se definió ningún parámetro de busqueda");
            }
            
//            if(activo.getId_estado()!=OptionsCfg.ACTIVO_DISPONIBLE){
//                throw new BaseException("Error","Activo no disponible");
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
 private class ActivoDet extends Activo{
     String rubro = "";
     String cod_subrubro = "";
     String subrubro = "";
     
     public ActivoDet(Activo activo){
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
