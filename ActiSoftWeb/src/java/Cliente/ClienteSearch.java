/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import bd.Cliente;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCliente;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ClienteSearch extends HttpServlet {

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
       Cliente cliente = null;
       TCliente tc = new TCliente();
       JsonRespuesta jr = new JsonRespuesta();
        try {
            Integer id = Parser.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            
            if(id==0&& nombre==null) throw new BaseException("ERROR","No se defini&oacute; ning&uacute;n par&aacute;metro de busqueda");
            if(id!=0)
                cliente = new TCliente().getById(id);
            else if (nombre!=null)
                cliente = tc.getByNombre(nombre);
            
            if (cliente != null) {
                jr.setTotalRecordCount(1);
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecord(cliente);
            
        } catch (BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } catch(NumberFormatException ex){
            jr.setResult("ERROR");
            jr.setMessage("Error al parsear los parametros");
        } finally {       
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
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
