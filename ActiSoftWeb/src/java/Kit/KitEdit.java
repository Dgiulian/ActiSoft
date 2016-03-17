/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import bd.Activo;
import bd.Kit;
import bd.Kit_detalle;
import bd.Kit_historia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TKit;
import transaccion.TKit_detalle;
import transaccion.TKit_historia;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class KitEdit extends HttpServlet {

   
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
        Kit kit = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                kit = new TKit().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el kit");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (kit!=null){
            request.setAttribute("kit", kit);
        }
        request.getRequestDispatcher("kit_edit.jsp").forward(request, response);
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
        String[] codigoActivos = request.getParameterValues("codigoActivo");        
        Kit kit ;
        TKit tk = new TKit();
        TActivo ta = new TActivo();
        TKit_detalle tkd = new TKit_detalle();
        TKit_historia th = new TKit_historia();
        boolean nuevo = false;
        ArrayList<Kit_detalle> lstDetalle   = new ArrayList<Kit_detalle>();
        ArrayList<Activo> lstActivos        = new ArrayList<Activo>();        
        ArrayList<Kit_historia> lstHistoria = new ArrayList<Kit_historia>();
        
        Map<Integer,Activo> mapActivos      = new HashMap<Integer,Activo>();
        Map<Integer,Kit_historia> mapHistoria = new HashMap<Integer,Kit_historia>();
        try{            
            Integer id_kit = Parser.parseInt(request.getParameter("id"));
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            Integer id_rubro = Parser.parseInt(request.getParameter("id_rubro"));
            Integer id_subrubro = Parser.parseInt(request.getParameter("id_subrubro"));
            Integer estado_activo = Parser.parseInt(request.getParameter("activo"));
            kit = tk.getById(id_kit);
            List<Kit_detalle> lstActual = tkd.getByKitId(id_kit);
            List<Kit_detalle> lstBaja   = new ArrayList<Kit_detalle>();
            if(kit==null){
                kit = new Kit();
                kit.setFecha_creacion(TFecha.ahora());
                nuevo = true;
                kit.setId_estado(OptionsCfg.KIT_ESTADO_DISPONIBLE);
            } //else { // Cuando se edita un kit, hay que incrementar 
                     // el stock de los activos que se eliminaron.
            if(kit.getId_estado()==OptionsCfg.KIT_ESTADO_ALQUILADO ) throw new BaseException("ERROR","El Kit está alquilado y no puede ser editado");
            
            if(nuevo && tk.getByCodigo(codigo)!=null) 
                 throw new BaseException("ERROR","Ya existe un kit con ese c&oacute;digo");
            
            kit.setCodigo(codigo);            
            kit.setNombre(nombre);            
            kit.setId_rubro(id_rubro);
            kit.setId_subrubro(id_subrubro);
            kit.setActivo(estado_activo);      
            
            // Recorro todos los activos que integran el kit.
            for (int i = 0;i<codigoActivos.length;i++){
             if (codigoActivos[i].equals("")) continue;
             String cod_activo = codigoActivos[i];
             Activo activo = ta.getByCodigo(cod_activo.trim());
             if(activo==null) {
                 throw new BaseException("Activo inexistente", String.format("No existe el activo: %s",cod_activo));
             }
             
             Kit_detalle detalle = new Kit_detalle();
             detalle.setId_activo(activo.getId());
             detalle.setCantidad(1);
             lstDetalle.add(detalle);
             mapActivos.put(activo.getId(),activo);
             activo.setStock(0f);
             activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_KIT);
             lstActivos.add(activo);
             Kit_historia historia = new Kit_historia();
             historia.setId_activo(activo.getId());
             historia.setId_accion(OptionsCfg.ACCION_ALTA);
             historia.setFecha(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
             lstHistoria.add(historia); 
             mapHistoria.put(activo.getId(),historia);
           }
            
            for(Kit_detalle d: lstActual){
                Activo a = mapActivos.get(d.getId_activo());
                
                if(a!=null){
                    // Si el activo esta en el map. Sigue formando parte del Kit, por lo tanto no hay que la historia.                    
                    Kit_historia historia = mapHistoria.get(a.getId());
                    lstHistoria.remove(historia);
                } else {
                    // Si el activo no esta en el map. Se eliminó y hay que volver a poner lo disponible.                
                    a = ta.getById(d.getId_activo());
                    if(a == null ) continue; // Si se elimino no hacemos nada 
                    
                    a.setStock( 1f);
                    a.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                    Kit_historia historia = new Kit_historia();
                    historia.setId_activo(d.getId_activo());
                    historia.setId_accion(OptionsCfg.ACCION_BAJA);
                    historia.setFecha(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
                    lstHistoria.add(historia);
                    lstActivos.add(a);
                    lstBaja.add(d);
                }
            }
         
         /*************** */ 
            if(nuevo){
                id_kit  = tk.alta(kit);
            } else {
                tk.actualizar(kit);                
            }   
            
            if(id_kit!=0){
               for (Kit_detalle d:lstActual){
                   //Borramos todo el detalle
                   boolean bajaOk = tkd.baja(d);                  
               }
               for (Kit_detalle kd :lstDetalle){
                   kd.setId_kit(id_kit);
                   tkd.alta(kd);
               }
               for(Activo a:lstActivos){ //Actualizamos los activos
                   ta.actualizar(a);
               }
               for(Kit_historia h:lstHistoria){ //Agregamos la historia del kit
                   h.setId_kit(id_kit);
                   th.alta(h);
               }
            }
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_KIT,OptionsCfg.ACCION_ALTA,kit.getId());
            response.sendRedirect(PathCfg.KIT);
        }
        catch(BaseException ex){
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
