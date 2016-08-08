/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parametro;

import bd.Parametro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
import transaccion.TParametro;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ParametroEdit extends HttpServlet {

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
            out.println("<title>Servlet ParametroEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ParametroEdit at " + request.getContextPath() + "</h1>");
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
         
         Integer id = Parser.parseInt(request.getParameter("id"));
         String codigo = request.getParameter("codigo");
         Integer numero = Parser.parseInt(request.getParameter("numero"));
         String nombre = request.getParameter("nombre");
         String valor  = request.getParameter("valor");
         String activo = request.getParameter("activo");
         JsonRespuesta jr = new JsonRespuesta();
         try {
            TParametro tp = new TParametro();
            Parametro p = tp.getById(id);
            boolean nuevo = false;
            if (p==null){
                p = new Parametro();
                nuevo = true;
            }
            if(codigo ==null) throw new BaseException("ERROR","Ingrese el c&oacute;digo del par&aacute;metro");
            p.setNumero(numero);
            p.setCodigo(codigo);
            p.setNombre(nombre);
            p.setValor(StringEscapeUtils.escapeJava(valor));
   //         if(activo!=null && !"".equals(activo))
   //             p.setActivo(1);
   //         else p.setActivo(0);
            boolean todoOk;
            if(nuevo){
                id = tp.alta(p);
                p.setId(id);
                todoOk = id!=0;
            } else todoOk = tp.actualizar(p);
            if (todoOk) {
                jr.setResult("OK");
                jr.setRecord(p);
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el par&aacute;metro");
         } catch(BaseException ex){
             jr.setMessage(ex.getMessage());
             jr.setResult(ex.getResult());
         } finally{
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
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
