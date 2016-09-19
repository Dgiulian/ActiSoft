/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Responsable;

import bd.Responsable;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TResponsable;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ResponsableList extends HttpServlet {

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
        Integer id_proveedor = Parser.parseInt(request.getParameter("id_proveedor"));
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
         
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            if(id_proveedor!=0) mapFiltro.put("id_proveedor",id_proveedor.toString());
            
            TResponsable tt = new TResponsable(); 
            List<Responsable> lista = tt.getListFiltro(mapFiltro);
            if (lista != null) {
                jr.setTotalRecordCount(lista.size());
            } else {    
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(lista);
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
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
}