/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Activo;
import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
import bd.Kit;
import bd.Remito_detalle;
import bd.Remito;
import bd.Site;
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
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TKit;
import transaccion.TKit_detalle;
import transaccion.TRemito_detalle;
import transaccion.TRemito;
import transaccion.TSite;
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
        Remito remito = null;
        TRemito tr = new TRemito();
        
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Parser.parseInt(request.getParameter("id"));
                remito = tr.getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el remito");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        }
        
        Integer id_contrato = Parser.parseInt(request.getParameter("id_contrato"));
        Contrato contrato = new TContrato().getById(id_contrato);        
        if(contrato!=null){
            request.setAttribute("contrato", contrato);
        }
        
        String view = "remito_edit.jsp";
        if (remito!=null){
            String idRef = request.getParameter("ref");
            Integer id_ref = 0;
            try{
                id_ref = Parser.parseInt(idRef);
            } catch (NumberFormatException ex) {
                id_ref = 0;
            }
            Remito remito_ref = tr.getById(id_ref);
            
            request.setAttribute("remito", remito);
            request.setAttribute("referencia", remito_ref);
            
            List<Remito_detalle> detalle = new TRemito_detalle().getByRemitoId(remito.getId());
            request.setAttribute("detalle", detalle);
//            view = "remito_view.jsp";
        }
        
        request.getRequestDispatcher(view).forward(request, response);
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
        
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        Integer id_remito      = Parser.parseInt(request.getParameter("id"));
        Integer id_cliente     = Parser.parseInt(request.getParameter("idCliente"));
        Integer id_site        = Parser.parseInt(request.getParameter("id_site"));
        Integer id_contrato    = Parser.parseInt(request.getParameter("id_contrato"));
        String ptoVta          = request.getParameter("punto_venta");
        Integer numero         = Parser.parseInt(request.getParameter("numero"));        
        String fecha           = request.getParameter("fecha");
        String observaciones   = request.getParameter("observaciones");
        String[] codigoActivos = request.getParameterValues("codigoActivo");
        String[] arrId_activos = request.getParameterValues("id_activo");
        String[] arrId_kits    = request.getParameterValues("id_kit");
        String[] cantActivos   = request.getParameterValues("cantActivo");
        String[] arrPos        = request.getParameterValues("posicion");
        
        ArrayList<Remito_detalle> lstDetalle = new ArrayList<Remito_detalle>();
        ArrayList<Activo> lstActivo = new ArrayList<Activo>();
        ArrayList<Kit> lstKit = new ArrayList<Kit>();
        
        
        TRemito tr         = new TRemito();
        TRemito_detalle trd = new TRemito_detalle();
        TActivo ta         = new TActivo();
        TKit    tk         = new TKit();
        TKit_detalle tkd   = new TKit_detalle();
        
        Remito remito;
        boolean nuevo = false;
        try{            
            remito = tr.getById(id_remito);
            if(remito==null) {
                remito = new Remito();
                nuevo = true;
            }
            //Que signific que existe referencia?           
            if(!nuevo && tr.existeReferencia(remito)) throw new BaseException("ERROR","Existe un remito de referencia para este remito. No se puede editar");
            
           
            Cliente cliente = new TCliente().getById(id_cliente);            
            if (cliente==null) throw new BaseException("Cliente inexistente","Seleccione el cliente del remito");
            
            
            Contrato contrato = new TContrato().getById(id_contrato);
            if(contrato==null) throw new BaseException("Contrato inexistente","Seleccione el contrato del remito");
            
            if(fecha==null||fecha.equals("")){
                throw new BaseException("ERROR","Ingrese la fecha del remito");
            }
            List<Contrato_detalle> c_detalle = new TContrato_detalle().getByContratoId(contrato.getId());
            HashMap <Integer,Contrato_detalle> mapDetalle = new HashMap<Integer,Contrato_detalle>();
            for(Contrato_detalle d:c_detalle){
                mapDetalle.put(d.getPosicion(),d);
            }
            
           
            // Controlar si existe el Site?
            
            
            Integer punto_venta = Parser.parseInt(ptoVta);            
            if(numero==0) throw new BaseException("ERROR","Ingrese el n&uacute;mero de remito");
            
            if(nuevo && new TRemito().getByNumero(numero)!=null) 
                 throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");
            
            
                                    
            Site site = new TSite().getById(id_site);
            if(site!=null) {
                remito.setId_site(site.getId());
                remito.setId_pozo(site.getId_pozo());
                remito.setId_equipo(site.getId_equipo());
            }
            remito.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            remito.setFecha_creacion(TFecha.ahora());
            remito.setPunto_venta(punto_venta);
            remito.setNumero(numero);
            remito.setId_tipo_remito(OptionsCfg.REMITO_ENTREGA);
            remito.setId_estado(OptionsCfg.REMITO_ESTADO_ABIERTO);
            remito.setId_contrato(contrato.getId());
            remito.setId_cliente(cliente.getId());
            remito.setId_usuario(id_usuario);
            remito.setObservaciones(observaciones);
         // Verificamos que la longitud de los vectores sea la misa
         if(cantActivos.length   != codigoActivos.length || 
            cantActivos.length   != arrPos.length   ||
            codigoActivos.length != arrPos.length ){
             throw new BaseException("Error de argumentos","Alguno de los argumentos del remito son incorrectos");
         }
         
         for (int i = 0;i<cantActivos.length;i++){
             
             if (codigoActivos[i].equals("")) continue;
             
             Float cant        = Parser.parseFloat(cantActivos[i]);
             String codigo     = codigoActivos[i].trim();
             Integer pos       = Parser.parseInt(arrPos[i]);
             Integer id_activo = Parser.parseInt(arrId_activos[i]);
             Integer id_kit    = Parser.parseInt(arrId_kits[i]);
             
             if (cant <= 0) continue;
             Remito_detalle detalle = new Remito_detalle();
             
             Activo activo = ta.getById(id_activo);
             if(activo!=null) {
                if (lstActivo.contains(activo)) throw new BaseException("ERROR","Existe al menos un Activo compartido en un Kit. No se puede generar el remito");
                if(!OptionsCfg.ACTIVO_ESTADO_DISPONIBLE.equals(activo.getId_estado())){
                    throw new BaseException("Activo no disponible", String.format("El activo %s no est&aacute; disponible",activo.getCodigo()));
                }
                if(activo.getAplica_stock().equals(1)) {
                   if(activo.getStock()<cant){
                       throw new BaseException("Cantidad insuficiente",String.format("El stock de %s es insuficiente. Stock actual: %.2f",activo.getCodigo(),activo.getStock()));
                   }
                   activo.setStock(activo.getStock() - cant);
                }
                detalle.setId_activo(activo.getId());
               lstActivo.add(activo);
             } else { // KIT
                Kit kit = tk.getById(id_kit);
                if (kit==null)
                    throw new BaseException("Activo o Kit inexistente", String.format("No existe el activo o el kit seleccionado: %s",codigo));
                 
                if(!OptionsCfg.KIT_ESTADO_DISPONIBLE.equals(kit.getId_estado())){
                    throw new BaseException("Kit no disponible", String.format("El kit %s no est&aacute; disponible",activo.getCodigo()));
                }
                // Controla si todos los activos del kit estan disponibles
                if(!tkd.controlarActivos(kit)){
                    List<Activo> activos = tkd.getActivos(kit.getId());
                    String lstError = "";
                    for (Activo a:activos) {
                        if(!a.getId_estado().equals(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE))
                            lstError+="<li>" + a.getCodigo() + "</li>";
                    }
                    throw new BaseException("ERROR","Alguno de los activos que componen el kit no est&aacue; disponible. No se puede generar el remito <ul>"+lstError+"</ul>");
                }
                detalle.setId_kit(kit.getId());
                
                // Recorremos cada activo del Kit
                List<Activo> activos = tkd.getActivos(kit.getId());
                for (Activo a:activos){
                   if (lstActivo.contains(a)) throw new BaseException("ERROR","Existe al menos un Activo compartido en un Kit. No se puede generar el remito");
                   if(a.getAplica_stock().equals(1)) {
                        if(a.getStock()<cant){
                            throw new BaseException("Cantidad insuficiente",String.format("El stock de %s es insuficiente. Stock actual: %.2f",a.getCodigo(),a.getStock()));
                        }
                        a.setStock(a.getStock() - cant);
                        
                        lstActivo.add(a);
                    }
                   
                }
                 
                
                lstKit.add(kit);
             } 
             
             detalle.setCantidad(cant);
             detalle.setPosicion(pos);
             lstDetalle.add(detalle);
             
         }
         
         if(nuevo) {
           id_remito = tr.alta(remito);
            
         } else {
             tr.eliminar(remito);
             tr.actualizar(remito);
         }
            
        if(id_remito!=0){
            remito.setId(id_remito);
            for (Remito_detalle dr :lstDetalle){
                dr.setId_remito(id_remito);
                trd.alta(dr);
            }
            /* Cambio el estado de los activos a prestado */
            for(Activo a: lstActivo){
                //a.setStock(a.getStock() );
                if (a.getAplica_stock().equals(1) && a.getStock() <= 0){
                     a.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                     TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_ACTIVO,OptionsCfg.ACCION_ALQUILER,a.getId());
                     ta.actualizar(a);
                }
            }
            
            for(Kit k:lstKit){
//                   if ( k.getStock() <= 0)
                k.setId_estado(OptionsCfg.KIT_ESTADO_ALQUILADO);
                tk.actualizar(k);
                TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_KIT,OptionsCfg.ACCION_ALQUILER,k.getId());
            }
        }
           
        TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_ALTA,remito.getId());
        response.sendRedirect(PathCfg.REMITO);
         
            
        }catch(NumberFormatException ex){
            //throw new BaseException("Cliente inexistente","Debe seleccionar el cliente del remito");
        }
        catch(BaseException ex){
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
