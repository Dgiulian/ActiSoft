/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Certificado;

import bd.Activo;
import bd.Certificado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TCertificado;
import utils.BaseException;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class CertificadoEditOld extends HttpServlet {

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
        
        String idActivo = request.getParameter("id_objeto");
        Integer  id_certif;
        Certificado certificado;
        boolean nuevo = false;
        try{
            id_certif = Integer.parseInt(idCertif);
            certificado = new TCertificado().getById(id_certif);
        } catch (NumberFormatException ex){
            certificado = new Certificado();
            nuevo = true;
        }
        try{
            Integer id_objeto;
            if(nuevo){                
                try{
                    id_objeto = Integer.parseInt(idActivo);                
                } catch (NumberFormatException ex){
                    id_objeto = 0;
                }
            } else id_objeto = certificado.getId_objeto();
            
            Activo activo = new TActivo().getById(id_objeto);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
           
                        
            request.setAttribute("activo", activo);
            request.setAttribute("certificado", certificado);
            request.getRequestDispatcher("certificado_edit.jsp").forward(request, response);
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
        String idActivo = request.getParameter("id_objeto");
        String idCertif = request.getParameter("id");
        String codigo = request.getParameter("codigo");
        String precinto = request.getParameter("precinto");
        String fecha = request.getParameter("fecha");
        String idResult = request.getParameter("id_resultado");
        String observaciones = request.getParameter("observaciones");
        String strExterno = request.getParameter("externo");
                
        try{
            Integer id_objeto;
            Integer  id_certif;
            try{
                id_objeto = Integer.parseInt(idActivo);                
            } catch (NumberFormatException ex){
                id_objeto = 0;
            }
            
            Activo activo = new TActivo().getById(id_objeto);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Certificado certificado;
            TCertificado tc = new TCertificado();
            boolean nuevo = false;
            try{
                id_certif = Integer.parseInt(idCertif);
                certificado = tc.getById(id_certif);
            } catch (NumberFormatException ex){
                certificado = new Certificado();
                nuevo = true;
            }            
            certificado.setId_objeto(id_objeto);
            certificado.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setCodigo(codigo);
            certificado.setPrecinto(precinto);
            certificado.setId_resultado(Integer.parseInt(idResult));
            certificado.setObservaciones(observaciones);
            boolean externo = strExterno!=null;
            certificado.setExterno(externo?1:0);
            boolean todoOk = true;
            if(nuevo) {
                todoOk = tc.alta(certificado)!=0;
            } else todoOk = tc.actualizar(certificado);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar el certificado");
            response.sendRedirect(PathCfg.CERTIFICADO + "?id_objeto=" + certificado.getId_objeto());
            
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
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
