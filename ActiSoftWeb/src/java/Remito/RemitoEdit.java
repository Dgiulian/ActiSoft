/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Remito;
import bd.Remito_detalle;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class RemitoEdit extends HttpServlet {

   

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Remito remito = null;
            TRemito tr = new TRemito();
                
            Integer id = Parser.parseInt(request.getParameter("id"));
            remito = tr.getById(id);
            if(remito==null) throw new BaseException( "Error" ,"No se ha encontrado el remito");

            String idRef = request.getParameter("ref");
            Integer id_ref = 0;
            
            id_ref = Parser.parseInt(idRef);
            
            Remito remito_ref = tr.getById(id_ref);
            request.setAttribute("remito", remito);            
            request.setAttribute("referencia", remito_ref);

            List<Remito_detalle> detalle = new TRemito_detalle().getByRemitoId(remito.getId());
            request.setAttribute("detalle", detalle);
            
            request.getRequestDispatcher("remito_view.jsp").forward(request, response);
        } catch (BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        Integer id_remito      = Parser.parseInt(request.getParameter("id_remito"));
        Integer punto_venta    = Parser.parseInt(request.getParameter("punto_venta"));
        Integer numero         = Parser.parseInt(request.getParameter("numero"));        
        String fecha           = request.getParameter("fecha");
        String observaciones   = request.getParameter("observaciones");
        TRemito tr         = new TRemito();
        Remito remito;
        
        try{            
            remito = tr.getById(id_remito);
            if(remito==null) throw new BaseException("ERROR","No se encontr&oacute; el remito");
                
            if(fecha==null||fecha.equals(""))
                throw new BaseException("ERROR","Ingrese la fecha del remito");
            
            if(numero==0) throw new BaseException("ERROR","Ingrese el n&uacute;mero de remito");
            
            Remito otro = tr.getByNumero(numero);
            if(otro!=null && !otro.equals(remito)) 
                 throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");

            remito.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            remito.setFecha_creacion(TFecha.ahora());
            remito.setPunto_venta(punto_venta);
            remito.setNumero(numero);
            remito.setId_usuario(id_usuario);
            remito.setObservaciones(observaciones);
            boolean todoOk = tr.actualizar(remito);
            
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,remito.getId());
            response.sendRedirect(PathCfg.REMITO);       
        }catch(BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
            return;
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
