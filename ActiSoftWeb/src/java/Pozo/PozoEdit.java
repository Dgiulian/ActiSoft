/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pozo;

import bd.Pozo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TPozo;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class PozoEdit extends HttpServlet {

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
            out.println("<title>Servlet PozoEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PozoEdit at " + request.getContextPath() + "</h1>");
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
        Pozo pozo = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                pozo = new TPozo().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el pozo");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (pozo!=null){
            request.setAttribute("pozo", pozo);
        }
        request.getRequestDispatcher("pozo_edit.jsp").forward(request, response);
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
        Integer id           = Parser.parseInt(request.getParameter("id"));
        String  nombre       = request.getParameter("nombre");
        String  descripcion  = request.getParameter("descripcion");
        
        
        TPozo tt         = new TPozo();
        Pozo pozo;
        JsonRespuesta jr = new JsonRespuesta();
        boolean nuevo = false;
        boolean todoOk = true;
        try{
            pozo = tt.getById(id);
            
            if ( pozo ==null){
                pozo = new Pozo();
                nuevo = true;
            }
            
            pozo.setNombre(nombre);
            pozo.setDescripcion(descripcion);
          
            if(nuevo){
                id = tt.alta(pozo);
                todoOk = id!=0;
            }else{
                todoOk = tt.actualizar(pozo);
            }
            if(todoOk) {
                pozo.setId(id);
                jr.setResult("OK");
                jr.setRecord(pozo); 
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el pozo");
            
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
