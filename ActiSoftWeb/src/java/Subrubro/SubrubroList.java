/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subrubro;

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
import transaccion.TSubrubro;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class SubrubroList extends HttpServlet {

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
        String rubro_id = request.getParameter("id_rubro");
        
        
       
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        Integer id_rubro = (rubro_id!=null)?Integer.parseInt(rubro_id):0;
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            List<Subrubro> lista;
            TSubrubro tr = new TSubrubro();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            
            if(id_rubro!=0){
                mapFiltro.put("id_rubro",id_rubro.toString());
                //lista =  tr.getByRubroId(id_rubro);
            } 
            //else lista = tr.getList();  
            mapFiltro.put("id_estado", "1");
            lista = tr.getListFiltro(mapFiltro);            
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
