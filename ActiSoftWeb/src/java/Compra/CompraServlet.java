/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

import bd.Activo;
import bd.Rubro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TRubro;
import utils.BaseException;

/**
 *
 * @author Diego
 */
public class CompraServlet extends HttpServlet {

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
        String idActivo = request.getParameter("id_activo");
        try{
            Integer id_activo;
            try{
                id_activo = Integer.parseInt(idActivo);
            } catch (NumberFormatException ex){
                id_activo = 0;
            }
            Activo activo = new TActivo().getById(id_activo);
            
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            Rubro rubro = new TRubro().getById(activo.getId_rubro());
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(activo.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica compra");
            
            request.setAttribute("activo", activo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
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
