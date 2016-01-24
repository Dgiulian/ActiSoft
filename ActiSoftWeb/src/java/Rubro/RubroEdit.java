/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rubro;

import bd.Rubro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TRubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;

/**
 *
 * @author Diego
 */
public class RubroEdit extends HttpServlet {

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
        
//        try{
//            throw new BaseException("Funci&oacute;n no implementada","Esta funci&oacute;n a&uacute;n no ha sido implementada. Sepa discuplar las molestias");
            Integer id_rubro = Parser.parseInt(request.getParameter("id"));
            Rubro  rubro = new TRubro().getById(id_rubro);
            
            if(rubro!=null) request.setAttribute("rubro", rubro);
            request.getRequestDispatcher("rubro_edit.jsp").forward(request, response);
//        }
//        catch(BaseException ex){
//            request.setAttribute("titulo",ex.getResult());
//            request.setAttribute("mensaje", ex.getMessage());
//            request.getRequestDispatcher("message.jsp").forward(request, response);
//        }
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
        Integer id_rubro = Parser.parseInt(request.getParameter("id"));
        String codigo = request.getParameter("codigo");
        String descripcion = request.getParameter("descripcion");
        String desc_opcional = request.getParameter("desc_opcional");
        String aplicaCertificado = request.getParameter("aplica_certificado");
        String id_estado = request.getParameter("id_estado");
        TRubro  tr = new TRubro();
        Rubro rubro = tr.getById(id_rubro);
        boolean nuevo = false;
        if(rubro==null){
            rubro = new Rubro();
            nuevo = true;
        }
        Integer aplica_certificado = aplicaCertificado!=null?1:0;
        rubro.setCodigo(codigo);
        rubro.setDescripcion(descripcion);
        rubro.setDesc_opcional(desc_opcional);
        rubro.setAplica_certificado(aplica_certificado);
        rubro.setId_estado(id_estado!=null?1:0);
        
        if(nuevo){
            tr.alta(rubro);
        }else tr.actualizar(rubro);
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_RUBRO,OptionsCfg.ACCION_ALTA,rubro.getId());
        response.sendRedirect(PathCfg.RUBRO);
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
