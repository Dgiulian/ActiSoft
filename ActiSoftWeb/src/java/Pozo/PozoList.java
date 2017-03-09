/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pozo;

import bd.Pozo;
import bd.Provincia;
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
import transaccion.TPozo;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class PozoList extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        String nombre = request.getParameter("nombre");
       
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
         
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
//            if(nombre!=null && !"".equals(nombre)) mapFiltro.put("nombre",nombre);
            TPozo tp = new TPozo(); 
//            tp.setOrderBy("nombre");
            List<Pozo> lista = tp.getListFiltro(mapFiltro);
            
            List<PozoDet> listaDet = new ArrayList();            
            for(Pozo c:lista) listaDet.add(new PozoDet(c));
            
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
        } finally {            
            out.close();
        }
    }
 class PozoDet extends Pozo{
     public PozoDet(Pozo pozo){
         super(pozo);  
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
