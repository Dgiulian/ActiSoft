/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Remito;
import bd.Remito_detalle;
import bd.Remito_detalle_view;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import transaccion.TRemito_detalle_view;
import utils.BaseException;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class RemitoDetList extends HttpServlet {

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
        
        try{
            Integer id_remito;
            
            try{
                id_remito = Integer.parseInt(request.getParameter("id_remito"));
            } catch (NumberFormatException ex){
                throw new BaseException("Error" ,"No se ha encontrado el remito");                                
            }
            remito = new TRemito().getById(id_remito);
            if(remito==null) throw new BaseException("Error" ,"No se ha encontrado el remito");   ;
            List<Remito_detalle_view >  lista = new TRemito_detalle_view().getByRemitoId(id_remito);
            
            if (lista != null) {
                jr.setTotalRecordCount(lista.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(lista);
            
                        
        
        } catch( BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
            
            
        } finally {            
            out.print(new Gson().toJson(jr));
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
