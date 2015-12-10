/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import bd.Agenda;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAgenda;
import transaccion.TAuditoria;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class AgendaEdit extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AgendaEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AgendaEdit at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String desde_fecha = TFecha.formatearFecha(request.getParameter("desde_fecha"), TFecha.formatoVista, TFecha.formatoBD);
        String hasta_fecha = TFecha.formatearFecha(request.getParameter("hasta_fecha"), TFecha.formatoVista, TFecha.formatoBD);
        String desde_hora = request.getParameter("desde_hora");
        String hasta_hora = request.getParameter("hasta_hora");
        
        Integer id_agenda = Parser.parseInt(request.getParameter("id"));
        String desde = "";
        String hasta = "";
       try{
            TAgenda ta  = new TAgenda();
            Agenda agenda = ta.getById(id_agenda);
            boolean nuevo = false;
            if(agenda==null) {
                agenda = new Agenda();
                nuevo = true;
            }
            if(desde_fecha==null && hasta_fecha==null) throw new BaseException("ERROR","Ingrese una fecha v&aacute,lida");

            agenda.setTitulo(titulo);
            agenda.setDescripcion(descripcion);        
            desde = desde_fecha;
            hasta = hasta_fecha;
            if(desde_hora!=null) desde += " " + desde_hora;
            if(hasta_hora!=null) hasta += " " + hasta_hora;
            agenda.setDesde(desde);        
            agenda.setHasta(hasta);
            
            if(nuevo){
                id_agenda = ta.alta(agenda);
                agenda.setId(id_agenda);
            } else ta.actualizar(agenda);
            
            Integer id_usuario = 0;
            Integer id_tipo_usuario = 0;
            HttpSession session = request.getSession();
            id_usuario = (Integer) session.getAttribute("id_usuario");
            id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_AGENDA,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,agenda.getId());
            response.sendRedirect(PathCfg.AGENDA);
        }
        catch(BaseException ex){
            request.setAttribute("titulo",ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
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
