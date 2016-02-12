/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subrubro;

import bd.Rubro;
import bd.Subrubro;
import bd.Subrubro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TRubro;
import transaccion.TSubrubro;
import transaccion.TSubrubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;

/**
 *
 * @author Diego
 */
public class SubrubroEdit extends HttpServlet {

   
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
        try{
            Integer id_rubro = Parser.parseInt(request.getParameter("id_rubro"));
            Rubro rubro = new TRubro().getById(id_rubro);
            
            if(rubro==null) throw new BaseException("ERROR","No se encontro&oacute; el rubro");

            Integer id_subrubro = Parser.parseInt(request.getParameter("id"));
            Subrubro  subrubro = new TSubrubro().getById(id_subrubro);

            if(subrubro!=null) request.setAttribute("subrubro", subrubro);
            if (rubro!=null) request.setAttribute("rubro", rubro);
            
            
            request.getRequestDispatcher("subrubro_edit.jsp").forward(request, response);
        }   catch(BaseException ex){
            request.setAttribute("titulo",ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
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
        Integer id_rubro = Parser.parseInt(request.getParameter("id_rubro"));
        Integer id_subrubro = Parser.parseInt(request.getParameter("id"));
        String codigo = request.getParameter("codigo");
        String descripcion = request.getParameter("descripcion");        
        String desc_opcional = request.getParameter("desc_opcional");
        String id_estado = request.getParameter("id_estado");
        
        TSubrubro  ts = new TSubrubro();
        TRubro tr = new TRubro();
        
        Subrubro subrubro = ts.getById(id_subrubro);
        Rubro rubro;
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        try{
            boolean nuevo = false;
            
            if(subrubro==null){
               subrubro = new Subrubro();
               nuevo = true;
               subrubro.setId_rubro(id_rubro);               
            }            
            if(nuevo && ts.getByCodigo(codigo)!=null) throw new BaseException("ERROR","Ya existe un subrubro con ese c&oacute;digo");
            
            subrubro.setCodigo(codigo);
            subrubro.setDescripcion(descripcion);        
            subrubro.setDesc_opcional(desc_opcional);        
            subrubro.setId_estado(id_estado!=null?1:0);
            
            if(nuevo){
                id_subrubro = ts.alta(subrubro);
                subrubro.setId(id_subrubro);
            }else ts.actualizar(subrubro);
             rubro = tr.getById(subrubro.getId_rubro());
             if(rubro!=null){
                 if (rubro.getId_estado()==0 && subrubro.getId_estado()!=0){
                     rubro.setId_estado(1);
                     tr.actualizar(rubro);
                     TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_RUBRO,OptionsCfg.ACCION_MODIFICAR,rubro.getId());
                 }
             }
             TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_SUBRUBRO,OptionsCfg.ACCION_ALTA,subrubro.getId());
             response.sendRedirect(PathCfg.RUBRO);
        } catch(BaseException ex){
            request.setAttribute("titulo",ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
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
