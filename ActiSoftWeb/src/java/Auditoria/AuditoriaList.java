/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auditoria;

import bd.Auditoria;
import bd.Tipo_usuario;
import bd.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TAuditoria;
import transaccion.TTipo_usuario;
import transaccion.TUsuario;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class AuditoriaList extends HttpServlet {
    HashMap<Integer, Usuario> mapUsuarios;
    HashMap<Integer, Tipo_usuario> mapTipos;
    HashMap<Integer, Option> mapModulos;
    HashMap<Integer, Option> mapAcciones;
    
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
        Map<String,String> mapFiltro =  new HashMap<String,String>(); 
        
        mapUsuarios = new TUsuario().getMap();
        mapTipos    = new TTipo_usuario().getMap();
        mapModulos  = OptionsCfg.getMapModulos();
        mapAcciones = OptionsCfg.getMapAcciones();
        
        Integer id_usuario = Parser.parseInt(request.getParameter("id_usuario"));        
        if(id_usuario!=0) mapFiltro.put("id_usuario", id_usuario.toString());
        
        Integer id_tipo_usuario = Parser.parseInt(request.getParameter("id_tipo_usuario"));
        if(id_tipo_usuario!=0) mapFiltro.put("id_tipo_usuario",id_tipo_usuario.toString());
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        
        JsonRespuesta jr = new JsonRespuesta();           
        try {
            List<Auditoria> lista = new TAuditoria().getListFiltro(mapFiltro);
            List<AuditoriaDet> listaDet = new ArrayList();            
            for(Auditoria c:lista) listaDet.add(new AuditoriaDet(c));
            
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
                
            } else {
                jr.setMessage("No se encontr&ooacute; ning&uacute;n registro");
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
            
        } finally {
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }
class AuditoriaDet extends Auditoria{
     String tipo = "";
     
     String modulo = "";
     String accion = "";
     String email = "";     
     public AuditoriaDet(Auditoria auditoria){
         super(auditoria);  
         email   = auditoria.getId_usuario().toString();
         tipo    = auditoria.getId_tipo_usuario().toString();
         modulo  = auditoria.getId_modulo().toString();
         accion  = auditoria.getId_accion().toString();
         Usuario u = mapUsuarios.get(auditoria.getId_usuario());
         if(u!=null) this.email = u.getUsu_mail();
         
         Tipo_usuario tu = mapTipos.get(auditoria.getId_tipo_usuario());
         if(tu!=null) this.tipo = tu.getCodigo();
         
         Option o = mapModulos.get(auditoria.getId_modulo());
         if (o!=null)
             this.modulo = o.getDescripcion();
         
         o = mapAcciones.get(auditoria.getId_accion());
         if (o!=null)
             this.accion = o.getDescripcion();         
     }
 }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
