/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import bd.Tipo_usuario;
import bd.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TTipo_usuario;
import transaccion.TUsuario;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class UsuarioList extends HttpServlet {
    HashMap<Integer, Tipo_usuario> mapTipos ; 
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
        String pagNro = request.getParameter("pagNro");
        
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        mapTipos = new TTipo_usuario().getMap();
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            
            List<Usuario> lista = new TUsuario().getList();
            List<UsuarioDet> listaDet = new ArrayList();            
            for(Usuario c:lista) listaDet.add(new UsuarioDet(c));
            
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
        } finally {            
            out.close();
        }
    }

class UsuarioDet extends Usuario{
     String tipo_usuario = "";     
     public UsuarioDet(Usuario usuario){
         super(usuario);  
         Tipo_usuario t = mapTipos.get(usuario.getId_tipo_usuario());
         if (t!=null)
            this.tipo_usuario = t.getCodigo();         
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
