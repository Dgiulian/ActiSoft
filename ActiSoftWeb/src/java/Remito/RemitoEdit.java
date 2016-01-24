/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Activo;
import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
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
        
        String[] codigoActivos = request.getParameterValues("codigoActivo");
        String[] cantActivos   = request.getParameterValues("cantActivo");
        String[] codigoPos = request.getParameterValues("posicion");
        String strCliente = request.getParameter("idCliente");
        String strSite = request.getParameter("id_site");
        String ptoVta = request.getParameter("punto_venta");
        String numRemito = request.getParameter("numero");
        String fecha = request.getParameter("fecha");
        String observaciones = request.getParameter("observaciones");
       
        ArrayList<Remito_detalle> lstDetalle = new ArrayList<Remito_detalle>();
        ArrayList<Activo> lstActivo = new ArrayList<Activo>();
        Integer id_remito = Parser.parseInt("id");
        
        TRemito tr = new TRemito();
        TRemito_detalle dm = new TRemito_detalle();
        TActivo ta = new TActivo();
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        
        Remito remito;
        boolean nuevo = false;
        try{            
            remito = tr.getById(id_remito);
            if(remito==null) {
                remito = new Remito();
                nuevo = true;
            }
                       
            if(tr.existeReferencia(remito)) throw new BaseException("ERROR","Existe un remito de referencia para este remito. No se puede editar");
            
            if(strCliente ==null || strCliente.equals("")) {
                throw new BaseException("Cliente inexistente","Debe seleccionar el cliente del remito");
            }
            if(numRemito ==null || numRemito.equals("")) {
                throw new BaseException("N&uacute;mero de Remito","Debe ingresar el n&uacute;mero de remito");
            }
            Cliente cliente = new TCliente().getById(Parser.parseInt(strCliente));            
            if (cliente==null) throw new BaseException("Cliente inexistente","Seleccione el cliente del remito");
            
            String id_contrato  = request.getParameter("id_contrato");
            
            Contrato contrato = new TContrato().getById(Parser.parseInt(id_contrato));
            if(contrato==null) throw new BaseException("Contrato inexistente","Seleccione el contrato del remito");
            
            if(fecha==null||fecha.equals("")){
                throw new BaseException("ERROR","Ingrese la fecha del remito");
            }
            List<Contrato_detalle> c_detalle = new TContrato_detalle().getByContratoId(contrato.getId());
            HashMap <Integer,Contrato_detalle> mapDetalle = new HashMap<Integer,Contrato_detalle>();
            for(Contrato_detalle d:c_detalle){
                mapDetalle.put(d.getPosicion(),d);
            }
            
            Integer id_site = Parser.parseInt(strSite);                        
            Site site = new TSite().getById(id_site);
            // Controlar si existe el Site?
            
            Integer numero = Parser.parseInt(numRemito);
            Integer punto_venta = Parser.parseInt(ptoVta);            
            if(numero==0) throw new BaseException("ERROR","Ingrese el n&uacute;mero de remito");
            
            if(nuevo && new TRemito().getByNumero(numero)!=null) 
                 throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");
            
            remito.setId_cliente(cliente.getId());
            if(site!=null)
                remito.setId_site(site.getId());
            remito.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            remito.setFecha_creacion(TFecha.ahora());
            remito.setPunto_venta(punto_venta);
            remito.setNumero(numero);
            remito.setId_tipo_remito(OptionsCfg.REMITO_ENTREGA);
            remito.setId_estado(OptionsCfg.REMITO_ESTADO_ABIERTO);
            remito.setId_contrato(contrato.getId());
            remito.setId_usuario(id_usuario);
            remito.setObservaciones(observaciones);
         // Verificamos que la longitud de los vectores sea la misa
         if(cantActivos.length   != codigoActivos.length || 
            cantActivos.length   != codigoPos.length   ||
            codigoActivos.length != codigoPos.length ){
             throw new BaseException("Error de argumentos","Alguno de los argumentos del remito son incorrectos");
         }
         
         for (int i = 0;i<cantActivos.length;i++){
             
             if (codigoActivos[i].equals("")) continue;
             
             Float cant = Parser.parseFloat(cantActivos[i]);
             String codigo = codigoActivos[i];
             Integer pos = Parser.parseInt(codigoPos[i]);
             
             if (cant <= 0) continue;
             
             Activo activo = new TActivo().getByCodigo(codigo.trim());
             if(activo==null) {
                 throw new BaseException("Activo inexistente", String.format("No existe el activo seleccionado: %s",codigo));
             }
             if(activo.getId_estado()!=OptionsCfg.ACTIVO_ESTADO_DISPONIBLE){
                 throw new BaseException("Activo no disponible", String.format("El activo %s no est&aacute; disponible",activo.getCodigo()));
             }
             if(activo.getAplica_stock() == 1) {
                if(activo.getStock()<cant){
                    throw new BaseException("Cantidad insuficiente",String.format("El stock de %s es insuficiente. Stock actual: %.2f",activo.getCodigo(),activo.getStock()));
                }
                activo.setStock(activo.getStock() - cant);
             }
            // Contrato_detalle d = mapDetalle.get(pos);
            // if(d == null) throw new BaseException("Posici&oacute;n incorrecta","La posici&oacute;n " + pos +" del remito no corresponde al contrato");
             
             //if( d.getId_rubro() != activo.getId_rubro())  throw new BaseException("Posici&oacute;n incorrecta","El activo " + activo.getCodigo() + " no corresponde a la  posic&oacute;n " + pos + " del contrato");
            // if( d.getId_subrubro() != activo.getId_subrubro())  throw new BaseException("Posici&oacute;n incorrecta","El activo " + activo.getCodigo() + " no corresponde a la  posic&oacute;n " + pos + " del contrato");
             
             Remito_detalle detalle = new Remito_detalle();
             detalle.setId_activo(activo.getId());
             detalle.setCantidad(cant);
             detalle.setPosicion(pos);
             lstDetalle.add(detalle);
             lstActivo.add(activo);
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
                   dm.alta(dr);
               }
               /* Cambio el estado de los activos a prestado */
               for(Activo a: lstActivo){
                   //a.setStock(a.getStock() );
                   if (a.getAplica_stock() == 1 && a.getStock() <= 0){
                        a.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                        ta.actualizar(a);
                   }
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
