/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Equipo;

import bd.Equipo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TEquipo;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class EquipoEdit extends HttpServlet {

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
            out.println("<title>Servlet EquipoEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EquipoEdit at " + request.getContextPath() + "</h1>");
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
        Equipo equipo = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                equipo = new TEquipo().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el equipo");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (equipo!=null){
            request.setAttribute("equipo", equipo);
        }
        request.getRequestDispatcher("equipo_edit.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer id                = Parser.parseInt(request.getParameter("id"));
        String  nombre            = request.getParameter("nombre");
        String  descripcion       = request.getParameter("descripcion");
        
        
        TEquipo tt         = new TEquipo();
        Equipo equipo;
        JsonRespuesta jr = new JsonRespuesta();
        boolean nuevo = false;
        boolean todoOk = true;
        try{

            equipo = tt.getById(id);
            
            if ( equipo ==null){
                equipo = new Equipo();
                nuevo = true;
            }
            
            equipo.setNombre(nombre);
            equipo.setDescripcion(descripcion);
          
            if(nuevo){
                id = tt.alta(equipo);
                todoOk = id!=0;
            }else{
                todoOk = tt.actualizar(equipo);
            }
            if(todoOk) {
                equipo.setId(id);
                jr.setResult("OK");
                jr.setRecord(equipo); 
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el equipo");
            
        } catch (BaseException ex){
            jr.setMessage(ex.getMessage());
            jr.setResult(ex.getResult());
        }finally{            
            out.print(new Gson().toJson(jr));
            out.close();
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
