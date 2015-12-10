/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activo;

import bd.Activo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;
import transaccion.TActivo;
import transaccion.TAuditoria;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ActivoDel extends HttpServlet {

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
            out.println("<title>Servlet ActivoDel</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActivoDel at " + request.getContextPath() + "</h1>");
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
        Integer id_usuario = 0;
        Integer id_tipo_usuario = 0;
        HttpSession session = request.getSession();
        id_usuario = (Integer) session.getAttribute("id_usuario");
        id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        try {
           Integer id = Parser.parseInt(request.getParameter("id"));
           Activo activo = new TActivo().getById(id);            
           if (activo==null) throw new BaseException("ERROR","No existe el registro");
           if(activo.getId_estado()==OptionsCfg.ACTIVO_ESTADO_ALQUILADO){
               throw new BaseException("Error","El activo est&aacute; alquilado y no puede eliminarse");
           }
//           boolean baja = new TActivo().baja(activo);
           activo.setBloqueado(1);
           boolean baja = new TActivo().actualizar(activo);
           
           if ( baja){
               jr.setResult("OK");               
           } else throw new BaseException("ERROR","Ocurrio un error al eliminar el registro");                     
           TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_ACTIVO,OptionsCfg.ACCION_BAJA,activo.getId());
        } catch (BaseException ex) {
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());            
        }
        finally {
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
