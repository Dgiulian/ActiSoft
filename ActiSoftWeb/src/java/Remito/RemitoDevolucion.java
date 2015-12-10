/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Activo;
import bd.Remito;
import bd.Remito_detalle;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class RemitoDevolucion extends HttpServlet {

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
        try {
            Remito remito = null;
            
             if(request.getParameter("id")==null) throw new BaseException( "Error","Debe seleccionar un remito");
             try{
                 Integer id = Integer.parseInt(request.getParameter("id"));
                 remito = new TRemito().getById(id);                     
             } catch (NumberFormatException ex){
                 throw new BaseException( "Error","No se ha encontrado el remito");
             }                        
             if (remito==null) throw new BaseException("Error", "No se ha encontrado el remito");             
             request.setAttribute("remito", remito);
             List<Remito_detalle> detalle = new TRemito_detalle().getByRemitoId(remito.getId());
             request.setAttribute("detalle", detalle);
             request.getRequestDispatcher("remito_devolucion.jsp").forward(request, response);
        } catch (BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
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
       Remito remito = null;
       HttpSession session = request.getSession();
       Integer id_usuario = (Integer) session.getAttribute("id_usuario");
       Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
       try{
           TRemito tr = new TRemito();
           if(request.getParameter("id")==null)  throw new BaseException("Remito inexistente","Debe seleccionar el remito a devolver");
           
           Integer id = Parser.parseInt(request.getParameter("id"));
           remito = tr.getById(id);            
           if (remito==null)  
               throw new BaseException("ERROR","No se ha encontrado el remito");
           if(remito.getId_estado()!= OptionsCfg.REMITO_ESTADO_ABIERTO)
                throw new BaseException("Remito no abierto","El remito no est&aacute; abierto. No se puede crear un remito de devoluci&oacute;n");
           
           String numRemitoEnt  = request.getParameter("numero_entrega");
           String numRemitoDev  = request.getParameter("numero_devolucion");
           String tipoEntrega = request.getParameter("tipoEntrega");
           String fecha = request.getParameter("fecha");
           boolean parcial = tipoEntrega!=null && Integer.parseInt(tipoEntrega) ==1;
           
           if(numRemitoDev ==null || numRemitoDev.equals(""))
               throw new BaseException("ERROR","Debe ingresar el n&uacute;mero de remito de devoluci&oacute;n");
           
           if(fecha ==null || fecha.equals(""))
               throw new BaseException("ERROR","Debe ingresar la fecha del remito de devoluci&oacute;n");
           
           Integer numeroDev = Integer.parseInt(numRemitoDev);
           Integer numeroEnt = 0;
           if(parcial) {
               if(numRemitoEnt ==null || numRemitoEnt.equals(""))
               throw new BaseException("ERROR","Debe ingresar el n&uacute;mero del nuevo remito de entrega");
               numeroEnt = Integer.parseInt(numRemitoEnt);           
           }
           
           TRemito_detalle trd = new TRemito_detalle();
           TActivo ta = new TActivo();
           List<Remito_detalle> lstDetalle = trd.getByRemitoId(remito.getId());
           
           Remito dev = new Remito(remito);
           dev.setId(0);
           dev.setNumero(numeroDev);
           dev.setId_referencia(remito.getId());
           dev.setId_tipo_remito(OptionsCfg.REMITO_DEVOLUCION);
           dev.setId_estado(OptionsCfg.REMITO_ESTADO_CERRADO);
           dev.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
           dev.setFecha_creacion(TFecha.ahora(TFecha.formatoBD));
           dev.setId_usuario(id_usuario);
           Integer id_remito_dev = tr.alta(dev);
           if (id_remito_dev == 0) throw new BaseException("Error", "Ocurrió un error al crear el remito de devoluci&oacute;n");
           dev.setId(id_remito_dev);
           HashMap<Integer,Remito_detalle> mapDetalle = new HashMap<Integer,Remito_detalle>();
           HashMap<Integer,Activo> mapActivo = new HashMap<Integer,Activo>();
           
           for(Remito_detalle rd:lstDetalle){
                mapDetalle.put(rd.getId(),rd);
                Remito_detalle det_dev = new Remito_detalle(rd);
                det_dev.setId_remito(dev.getId());
                det_dev.setId(0);
                
                Activo act_dev = ta.getById(det_dev.getId_activo());
                if(act_dev!=null){
                    if (act_dev.getAplica_stock() == 1) {
                        act_dev.setStock(act_dev.getStock() + det_dev.getCantidad());                     
                    }
                    
                    if(act_dev.getId_estado()== OptionsCfg.ACTIVO_ESTADO_ALQUILADO)
                        act_dev.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE); 
                    ta.actualizar(act_dev);
                    mapActivo.put(act_dev.getId(),act_dev);
                }
                trd.alta(det_dev);                
            }
           
           
            remito.setId_estado(OptionsCfg.REMITO_ESTADO_CERRADO);
            tr.actualizar(remito);
            if(parcial) {
             /* Creamos el nuevo remito de entrega */
            
            Remito ent = new Remito(remito);
            ent.setId(0);
            ent.setNumero(numeroEnt);
            ent.setId_referencia(dev.getId());
            ent.setId_tipo_remito(OptionsCfg.REMITO_ENTREGA);
            ent.setId_estado(OptionsCfg.REMITO_ESTADO_ABIERTO);
            ent.setId_referencia(dev.getId());
            ent.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            ent.setFecha_creacion(TFecha.ahora(TFecha.formatoBD));
            ent.setId_usuario(id_usuario);
            int id_remito_ent = tr.alta(ent);
            if (id_remito_ent == 0) throw new BaseException("Error", "Ocurrió un error al crear el remito de entrega");
            ent.setId(id_remito_ent);
            String[] lstNumero = request.getParameterValues("detalle");
                for(int i=0;i<lstNumero.length;i++){
                   Integer id_rem_det = Integer.parseInt(lstNumero[i]);
                   Remito_detalle det_dev = mapDetalle.get(id_rem_det);
                   Remito_detalle det_ent = new Remito_detalle(det_dev);
                   det_ent.setId(0);
                   
                   det_ent.setId_remito(ent.getId());
                   Activo act_ent = mapActivo.get(det_ent.getId_activo());
                   if(act_ent!=null){
                    if (act_ent.getAplica_stock() == 1) {
                       act_ent.setStock(act_ent.getStock() - det_dev.getCantidad());
                       if (act_ent.getStock()<=0)
                           act_ent.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                       ta.actualizar(act_ent);
                    }                   
                   }
                   trd.alta(det_ent);
                }
                TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,ent.getId());
            }
            
            
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,remito.getId());

            response.sendRedirect("Remito");
            
       } catch(BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
            return;
               
       } finally{
           
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
