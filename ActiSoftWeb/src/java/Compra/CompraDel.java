/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

import bd.Activo;
import bd.Compra;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TCompra;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class CompraDel extends HttpServlet {

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
            out.println("<title>Servlet CompraDel</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompraDel at " + request.getContextPath() + "</h1>");
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
        try {           
           Integer id = Integer.parseInt(request.getParameter("id"));
           TCompra tc = new TCompra();
           TActivo ta = new TActivo();
           Compra compra = tc.getById(id);
           
           if (compra==null) throw new BaseException("ERROR","No existe el registro");
           Activo activo = ta.getById(compra.getId_activo());
           
           if(tc.getCompraPosterior(compra)!=null) throw new BaseException("ERROR","Existe una compra posterior. No se puede eliminar la compra.");
           
           if (activo!=null && activo.getStock()==0f) throw new BaseException("ERROR","No se puede crear una compra si el stock del activo es 0");
           
           boolean baja = tc.baja(compra);
           if ( baja){
               
               if(activo!=null) {
                   activo.setStock(compra.getStock_anterior());
                   //activo.setStock(Math.max(0,activo.getStock() - compra.getCantidad()));
                   ta.actualizar(activo);
               }
               
               jr.setResult("OK");
               Integer id_usuario = 0;
               Integer id_tipo_usuario = 0;
               HttpSession session = request.getSession();
               id_usuario = (Integer) session.getAttribute("id_usuario");
               id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
               TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CLIENTE,OptionsCfg.ACCION_BAJA,compra.getId());
           } else throw new BaseException("ERROR","Ocurrio un error al eliminar el registro");                     
        } catch(NumberFormatException ex){
            jr.setResult("ERROR");
            jr.setMessage("Ocurio un error al intentar eliminar el registro");
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
