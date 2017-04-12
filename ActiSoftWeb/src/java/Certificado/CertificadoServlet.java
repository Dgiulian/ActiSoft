/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Certificado;

import bd.Activo;
import bd.Kit;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TCertificado;
import transaccion.TKit;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class CertificadoServlet extends HttpServlet {

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
        /* Modificamos el resultado de los certificados que ya vencieron*/
        //new TCertificado().vencerCertificados();
        Integer  id_modulo = Parser.parseInt(request.getParameter("id_modulo"));
        Integer id_objeto = Parser.parseInt(request.getParameter("id_objeto"));
        
        try{
            
            if (id_modulo.equals(OptionsCfg.MODULO_KIT) ) {
                Kit kit = new TKit().getById(id_objeto);
                if (kit == null) throw new BaseException("Kit inexistente", "No se encontr&oacute; el kit");
                request.setAttribute("kit", kit);
                
             } else {
                Activo activo = new TActivo().getById(id_objeto);
                if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
                request.setAttribute("activo", activo);
            }
            
            
            request.setAttribute("id_modulo",id_modulo);
            request.setAttribute("id_objeto",id_objeto);
            request.getRequestDispatcher("certificado.jsp").forward(request, response);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
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
