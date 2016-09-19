/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Responsable;

import bd.Proveedor;
import bd.Responsable;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TProveedor;
import transaccion.TResponsable;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class ResponsableEdit extends HttpServlet {

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
            out.println("<title>Servlet ResponsableEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResponsableEdit at " + request.getContextPath() + "</h1>");
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonRespuesta jr = new JsonRespuesta();
        Integer id           = Parser.parseInt(request.getParameter("id"));
        Integer id_proveedor = Parser.parseInt(request.getParameter("id_proveedor"));
        String  nombre       = request.getParameter("nombre");
        String  apellido     = request.getParameter("apellido");        
        TProveedor tp        = new TProveedor();
        TResponsable tt      = new TResponsable();
        Proveedor proveedor ;
        Responsable responsable;
        
        boolean nuevo = false;
        boolean todoOk;
        try{
            proveedor = tp.getById(id_proveedor);
            if(proveedor ==null) throw new BaseException("ERROR","No existe el proveedor");
            responsable = tt.getById(id);
            if ( responsable ==null){
                responsable = new Responsable();
                responsable.setId_proveedor(id_proveedor);
                nuevo = true;
            }
            responsable.setNombre(nombre);
            responsable.setApellido(apellido);
            
            if(nuevo){
                id = tt.alta(responsable);
                todoOk = id!=0;
            }else{
                todoOk = tt.actualizar(responsable);
            }
            if(todoOk) {
                responsable.setId(id);
                jr.setResult("OK");
                jr.setRecord(responsable); 
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el responsable");
            
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
