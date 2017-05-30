/*
 * Esta versión de remito de devolución considera que lo que está en pantalla
 * son los items a devolver.
 * Se crea un remito de devolución y se cierran todos los remitos.
 * Si es una devolución transitoria, se crea un remito de entrega con los items
 * que no estan en pantalla.
 */
package Remito;

import bd.Activo;
import bd.Kit;
import bd.Remito;
import bd.Remito_detalle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TKit;
import transaccion.TKit_detalle;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.BaseException;
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
       Remito remito;
       HttpSession session           = request.getSession();
       Integer id_usuario            = (Integer) session.getAttribute("id_usuario");
       Integer id_tipo_usuario       = (Integer) session.getAttribute("id_tipo_usuario");
       String numRemitoEnt           = request.getParameter("numero_entrega");
       String numRemitoDev           = request.getParameter("numero_devolucion");
       String tipoEntrega            = request.getParameter("tipoEntrega");
       String fecha                  = request.getParameter("fecha");
       String fecha_entrega          = request.getParameter("fecha_entrega");
       String observaciones          = request.getParameter("observaciones");
       String cod_activo_transporte  = request.getParameter("id_activo_transporte");
       Float transporte_cantidad     = Parser.parseFloat(request.getParameter("transporte_cantidad"));
       Integer pos_activo_transporte = Parser.parseInt(request.getParameter("pos_activo_transporte"));
       String obs_ent = "Los remitos de entrega que intervienen son: ";
       try{
           TRemito tr = new TRemito();
           TRemito_detalle trd = new TRemito_detalle();
           TActivo ta          = new TActivo();
           TKit tk             = new TKit();
           TKit_detalle tkd    = new TKit_detalle();
           HashMap<Integer,Remito> mapRemitos         = new HashMap<Integer,Remito>();
           HashMap<Integer,Remito_detalle> mapDetalle = new HashMap<Integer,Remito_detalle>();
           HashMap<Integer,Activo> mapActivo          = new HashMap<Integer,Activo>();
           HashMap<Integer,Kit> mapKit                = new HashMap<Integer,Kit>();
           List<Remito_detalle> lstEntrega            = new ArrayList<Remito_detalle>();
           List<Activo> lstActivo = new ArrayList<Activo>();
           
           if(request.getParameter("id")==null)  throw new BaseException("Remito inexistente","Debe seleccionar el remito a devolver");
           Integer id = Parser.parseInt(request.getParameter("id"));
           remito = tr.getById(id);
           if (remito==null)
               throw new BaseException("ERROR","No se ha encontrado el remito");
           if(!OptionsCfg.REMITO_ESTADO_ABIERTO.equals(remito.getId_estado()))
                throw new BaseException("Remito no abierto","El remito no est&aacute; abierto. No se puede crear un remito de devoluci&oacute;n");
           
           boolean transitorio = tipoEntrega!=null && Integer.parseInt(tipoEntrega) ==1;
           String[] lstNumero = request.getParameterValues("detalle");

           if(numRemitoDev ==null || numRemitoDev.equals(""))
               throw new BaseException("ERROR","Debe ingresar el n&uacute;mero de remito de devoluci&oacute;n");

           if(fecha ==null || fecha.equals(""))
               throw new BaseException("ERROR","Debe ingresar la fecha del remito de devoluci&oacute;n");

           Integer numeroDev = Parser.parseInt(numRemitoDev);
           Integer numeroEnt = 0;
           
//           if( new TRemito().getByNumero(numeroDev)!=null) 
//                throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");
                        
            
           if(transitorio) {
               numeroEnt = Parser.parseInt(numRemitoEnt);
               if(numeroEnt==0)
                    throw new BaseException("ERROR","Debe ingresar el n&uacute;mero del nuevo remito de entrega");
               
//               if( new TRemito().getByNumero(numeroEnt)!=null) 
//                throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");
           }
           
           //Creo el remito de devolución como una copia del remito original
           Remito dev = new Remito(remito);
           dev.setId(0);
           dev.setNumero(numeroDev);
           dev.setId_referencia(remito.getId());
           dev.setId_tipo_remito(OptionsCfg.REMITO_DEVOLUCION);
           dev.setId_estado(OptionsCfg.REMITO_ESTADO_CERRADO);
           dev.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
           dev.setFecha_creacion(TFecha.ahora(TFecha.formatoBD));
           dev.setId_usuario(id_usuario);
           dev.setObservaciones(observaciones);
           
           Activo activo_transporte = ta.getByCodigo(cod_activo_transporte);
           
           
           Integer id_remito_dev = tr.alta(dev);
           if (id_remito_dev == 0) throw new BaseException("Error", "Ocurrió un error al crear el remito de devoluci&oacute;n");
           dev.setId(id_remito_dev);
           TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,dev.getId());
           
           for(int i=0;i<lstNumero.length;i++){
                  //Creo el detalle del remito de devolución y actualizo el activo
                   Integer id_rem_det = Parser.parseInt(lstNumero[i]);
                   Remito_detalle rd = trd.getById(id_rem_det);  // Recupero el detalle
                   if(rd==null) continue;
                   
                   lstEntrega.add(new Remito_detalle(rd));
                   
                   if(mapRemitos.get(rd.getId_remito())==null) {
                       Remito remitoById = tr.getById(rd.getId_remito()); 
                       if (remitoById==null) continue;
                       mapRemitos.put(remitoById.getId(), remitoById); // Guardo el remito original para después cerrarlo
                       remitoById.setId_estado(OptionsCfg.REMITO_ESTADO_CERRADO);
                       obs_ent += String.format("%d - ",remitoById.getNumero());
                       //remitoById.setId_referencia(dev.getId());
                       tr.actualizar(remitoById);
                       for(Remito_detalle det:trd.getByRemitoId(remitoById.getId())){ //Cierro todo el remito
                           
                             Remito_detalle det_dev = new Remito_detalle(det);
                             det_dev.setId_remito(dev.getId());
                             det_dev.setId(0);
                             det_dev.setId_referencia(0);
                             det.setId_referencia(dev.getId()); // Actualizo al referencia
                             if(det_dev.getId_activo()!=0) {
                                Activo act_dev = ta.getById(det_dev.getId_activo());
                                if(act_dev!=null){
                                    if (OptionsCfg.RUBRO_TRANSPORTE.equals(act_dev.getId_rubro())) continue;
                                    /* En los remitos de devolución, no se duplica el transporte */ 
                                    
                                    if (act_dev.getAplica_stock() == 1) {
                                        act_dev.setStock(act_dev.getStock() + det_dev.getCantidad());
                                    }
                                    /*                                    
                                    if(OptionsCfg.ACTIVO_ESTADO_ALQUILADO.equals(act_dev.getId_estado()))
                                        act_dev.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                                     ta.actualizar(act_dev);
                                    */
                                    lstActivo.add(act_dev);
                                    mapActivo.put(act_dev.getId(),act_dev);
                                }
                             } else {
                                 Kit kit_dev = tk.getById(det_dev.getId_kit());
                                 if(kit_dev!=null){
                                    kit_dev.setId_estado(OptionsCfg.KIT_ESTADO_DISPONIBLE);
                                    tk.actualizar(kit_dev);
                                    mapKit.put(kit_dev.getId(),kit_dev);
                                    List<Activo> activos = tkd.getActivos(kit_dev.getId());
                                    
                                    for (Activo act_dev:activos){
                                       if(act_dev.getAplica_stock() == 1) {
                                            act_dev.setStock(act_dev.getStock() + det_dev.getCantidad());                                            
                                            lstActivo.add(act_dev);
                                            mapActivo.put(act_dev.getId(),act_dev);                                            
                                        }
                                    }
                                }                             
                             trd.alta(det_dev);      // Creo el detalle de la devolucion             
                             trd.actualizar(det); // Actualizo los items del remito de entrega
                       }
                }
           }
        }
           if(activo_transporte!=null && OptionsCfg.RUBRO_TRANSPORTE.equals(activo_transporte.getId_rubro())){
               Remito_detalle det_transp = new Remito_detalle();
               det_transp.setId_activo(activo_transporte.getId());
               det_transp.setCantidad(transporte_cantidad);
               det_transp.setPosicion(pos_activo_transporte);
               det_transp.setId_remito(dev.getId());
               det_transp.setId(0);
               det_transp.setId_referencia(0);
               det_transp.setId_referencia(dev.getId()); // Actualizo al referencia
               trd.alta(det_transp);
           }
           for(Activo act_dev: lstActivo){
                if(OptionsCfg.ACTIVO_ESTADO_ALQUILADO.equals(act_dev.getId_estado())) {
                    act_dev.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                    TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_ACTIVO,OptionsCfg.ACCION_DEVOLUCION,act_dev.getId());
                    ta.actualizar(act_dev);
                }
            }
    
           if(transitorio) {
                /* Creamos el nuevo remito de entrega como una copia del otro*/
               Remito ent = new Remito(remito);
               ent.setId(0);
               ent.setNumero(numeroEnt);               
               ent.setId_tipo_remito(OptionsCfg.REMITO_ENTREGA);
               ent.setId_estado(OptionsCfg.REMITO_ESTADO_ABIERTO);
               ent.setId_referencia(dev.getId());
               ent.setFecha(TFecha.formatearFecha(fecha_entrega!=null?fecha_entrega:fecha, TFecha.formatoVista, TFecha.formatoBD));
               ent.setFecha_creacion(TFecha.ahora(TFecha.formatoBD));
               ent.setId_usuario(id_usuario);
               ent.setObservaciones(obs_ent);
               
               int id_remito_ent = tr.alta(ent);
               if (id_remito_ent == 0) throw new BaseException("Error", "Ocurrió un error al crear el remito de entrega");
               ent.setId(id_remito_ent);
               
               for (Remito_detalle det_ent:lstEntrega){
                   //Creamos el detalle del remito de entrega con el detalle de los que figuran en pantalla
                   det_ent.setId(0);
                   det_ent.setId_remito(ent.getId());
                   if(det_ent.getId_activo()!=0) {
                    Activo act_ent = mapActivo.get(det_ent.getId_activo());
                    
                    if(act_ent!=null && OptionsCfg.RUBRO_TRANSPORTE.equals(act_ent.getId_rubro())) continue;
                    
                    if(act_ent!=null){
                     if (act_ent.getAplica_stock() == 1) {
                        act_ent.setStock(act_ent.getStock() - det_ent.getCantidad());
                        if (act_ent.getStock()<=0)
                            act_ent.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                        ta.actualizar(act_ent);
                     }
                    }else {
                      Kit kit_ent = mapKit.get(det_ent.getId_kit());
                      if(kit_ent!=null){
                          kit_ent.setId_estado(OptionsCfg.KIT_ESTADO_ALQUILADO);
                          tk.actualizar(kit_ent);
                          List<Activo> activos = tkd.getActivos(kit_ent.getId());
                          for (Activo a:activos){
                            if(a.getAplica_stock() == 1) {
                                a.setStock(a.getStock() - det_ent.getCantidad());
                                if (a.getAplica_stock() == 1 && a.getStock() <= 0){
                                    a.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                                    TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_ACTIVO,OptionsCfg.ACCION_ALQUILER,a.getId());
                                    ta.actualizar(a);
                                }
                                mapActivo.put(a.getId(),a);
                                 //lstActivo.add(act_dev);
                             }

                          }
                      }
                    }
                   }
                   trd.alta(det_ent);
                }
                TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,ent.getId());
            }
            response.sendRedirect("Remito");

       } catch(BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
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
