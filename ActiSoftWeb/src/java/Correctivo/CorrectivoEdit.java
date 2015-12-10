/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Correctivo;

import bd.Activo;
import bd.Correctivo;
import bd.Rubro;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TCorrectivo;
import transaccion.TRubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class CorrectivoEdit extends HttpServlet {

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
            out.println("<title>Servlet CorrectivoEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CorrectivoEdit at " + request.getContextPath() + "</h1>");
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
         
        String idCertif = request.getParameter("id");        
        String idActivo = request.getParameter("id_activo");
        Integer  id_certif;
        Correctivo correctivo = null;     
        boolean nuevo = false;
        
        try{
            id_certif = Integer.parseInt(idCertif);            
        } catch (NumberFormatException ex){
            id_certif = 0;
        }
        correctivo = new TCorrectivo().getById(id_certif);
        if(correctivo==null) {
            correctivo = new Correctivo();            
            nuevo = true;
        }
        try{
            Integer id_activo;
            if(nuevo){                
                try{
                    id_activo = Integer.parseInt(idActivo);                
                } catch (NumberFormatException ex){
                    id_activo = 0;
                }
            } else id_activo = correctivo.getId_activo();
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Rubro rubro = new TRubro().getById(activo.getId_rubro());
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(rubro.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica correctivo");
            
            if (nuevo) correctivo.setId_activo(activo.getId());
            
            request.setAttribute("activo", activo);
            request.setAttribute("correctivo", correctivo);
            request.getRequestDispatcher("correctivo_edit.jsp").forward(request, response);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
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
        try {
            Integer id_correctivo = Parser.parseInt(request.getParameter("id"));
            Integer id_activo = Parser.parseInt(request.getParameter("id_activo"));
            Integer id_resultado     = Parser.parseInt(request.getParameter("id_resultado"));
            Integer id_actividad     = Parser.parseInt(request.getParameter("id_actividad"));
            String fecha             = request.getParameter("fecha");
            String  detalle_problema = request.getParameter("detalle_problema");
            String  detalle_solucion = request.getParameter("detalle_solucion");
            Correctivo correctivo;
            TCorrectivo tc = new TCorrectivo();
            boolean nuevo = false;

            correctivo = tc.getById(id_correctivo);            
            if(correctivo==null){
                correctivo = new Correctivo();
                nuevo = true;
            }            
            correctivo.setId_activo(id_activo);
            correctivo.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            correctivo.setDetalle_problema(detalle_problema);
            correctivo.setDetalle_solucion(detalle_solucion);
            correctivo.setId_actividad(id_actividad);
            correctivo.setId_resultado(id_resultado);
            
            boolean todoOk = true;
            if(nuevo) { 
                id_correctivo = tc.alta(correctivo);
                todoOk = id_correctivo!=0;                
                correctivo.setId(id_correctivo);
            } else todoOk = tc.actualizar(correctivo);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar el correctivo");
            Integer id_usuario = 0;
            Integer id_tipo_usuario = 0;
            HttpSession session = request.getSession();
            id_usuario = (Integer) session.getAttribute("id_usuario");
            id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CORRECTIVO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,correctivo.getId());
            response.sendRedirect(PathCfg.CORRECTIVO + "?id_activo=" + correctivo.getId_activo());
        } catch (BaseException ex) {
            Logger.getLogger(CorrectivoEdit.class.getName()).log(Level.SEVERE, null, ex);
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
