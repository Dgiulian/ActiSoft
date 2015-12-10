/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego
 */
//@WebServlet(name = "JsonOk", urlPatterns = {PathCfg.JSONOK})
public class JsonOk extends HttpServlet {

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
        try {
            /* TODO output your page here. You may use following sample code. */
            JsonRespuesta jr = new JsonRespuesta();
            jr.setResult("OK");
            String mensaje = "";
            
            /*
            String[] parameterValues = request.getParameterValues(mensaje);
            for(Integer i=0; i < parameterValues.length;i++){
                
            }*/
            jr.setMessage(mensaje);
//            DataEntryRta rta = new DataEntryRta();
//            rta.setMeasure_id(20);
//            rta.setGrc_rate(1050.10);
            
            OilDataRta rta = new OilDataRta();
            rta.setOi_id(13);
            jr.setRecord(rta);
            out.print(new Gson().toJson(jr));
        } finally {            
            out.close();
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
private class DataEntryRta{
        private Integer measure_id;
        private Double grc_rate;
        
        public Integer getMeasure_id(){return this.measure_id;}        
        public Double getGrc_rate(){return this.grc_rate;}
        public DataEntryRta setMeasure_id(Integer measure_id){
            this.measure_id = measure_id;
            return this;
        }
        public DataEntryRta setGrc_rate(Double grc_rate){
            this.grc_rate = grc_rate;
            return this;
        }
        
    }
private class OilDataRta{
    private Integer oi_id;
    public Integer getOi_id(){return this.oi_id;}
    public OilDataRta setOi_id(Integer oi_id){
        this.oi_id = oi_id;
        return this;
    }
    
}
}
