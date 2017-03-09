/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preticket;

import bd.Preticket;
import bd.Remito;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TPreticket;
import transaccion.TRemito;
import transaccion.TRemito_contrato;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;

/**
 *
 * @author Diego
 */
public class PreticketEdit extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PreticketEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PreticketEdit at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Integer id_preticket = Parser.parseInt(request.getParameter("id"));
        try{
            Preticket preticket = new TPreticket().getById(id_preticket);
            if(preticket==null)throw new BaseException("ERROR", "No se encontr&oacute; el preticket");
            request.setAttribute("preticket",preticket);
            request.getRequestDispatcher("preticket_edit.jsp").forward(request,response);
            return;        
        } catch (BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Integer id_preticket = Parser.parseInt(request.getParameter("id"));
         String nro_factura = request.getParameter("nro_factura");
         String nro_habilitacion = request.getParameter("nro_habilitacion");
         String nro_certificado = request.getParameter("nro_certificado");
         TPreticket tp = new TPreticket();
        try{
            Preticket preticket = tp.getById(id_preticket);
            if(preticket==null)throw new BaseException("ERROR", "No se encontr&oacute; el preticket");
            preticket.setNro_certificado(nro_certificado);
            preticket.setNro_factura(nro_factura);
            preticket.setNro_habilitacion(nro_habilitacion);
             boolean todoOk = tp.actualizar(preticket);
            if (!todoOk) throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el preticket");
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_PRETICKET,OptionsCfg.ACCION_MODIFICAR,preticket.getId());
            response.sendRedirect(PathCfg.PRETICKET);
            
        } catch (BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
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
