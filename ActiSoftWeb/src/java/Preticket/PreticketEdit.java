/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Preticket;

import bd.Preticket;
import bd.Preticket_detalle;
import bd.Remito;
import bd.Remito_contrato;
import bd.Remito_detalle;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TPreticket;
import transaccion.TPreticket_detalle;
import transaccion.TRemito;
import transaccion.TRemito_contrato;
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
public class PreticketEdit extends HttpServlet {

   

    
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
             String idPreticket = request.getParameter("id");
             Integer id_preticket = 0;
             try{ 
                 id_preticket = Integer.parseInt(idPreticket);
             } catch(NumberFormatException ex){
                 id_preticket = 0;
             }
             Preticket preticket = new TPreticket().getById(id_preticket);
             if(preticket!=null){
                 request.setAttribute("preticket",preticket);
                 request.getRequestDispatcher("preticket_view.jsp").forward(request,response);
                 return;
             }
             
             if(request.getParameter("id_remito")==null) throw new BaseException( "Error","Debe seleccionar un remito");
             try{
                 Integer id_remito = Integer.parseInt(request.getParameter("id_remito"));
                 remito = new TRemito().getById(id_remito);                     
             } catch (NumberFormatException ex){
                 throw new BaseException( "Error","No se ha encontrado el remito");
             }                        
             if (remito==null) throw new BaseException("Error", "No se ha encontrado el remito");             
             if(remito.getFacturado()!=0) throw new BaseException("ERROR","El remito ya fu&eacute; facturado");
             request.setAttribute("remito", remito);
            List<Remito_contrato> detalle = new TRemito_contrato().getByRemitoId(remito.getId());
//             List<Remito_detalle> detalle = new TRemito_detalle().getByRemitoId(remito.getId());
             request.setAttribute("detalle", detalle);
             request.getRequestDispatcher("preticket_edit.jsp").forward(request, response);
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
       try{
            String fecha = request.getParameter("fecha");
            String ptoVta = request.getParameter("punto_venta");
            String strNumero  = request.getParameter("numero");
            String idCliente  = request.getParameter("id_cliente");
            String idContrato = request.getParameter("id_contrato");
            String idSite     = request.getParameter("id_site");
            String strTotal        = request.getParameter("total");
            String[] lstId_remito = request.getParameterValues("id_remito");
            
            String[] arrRemito_inicio = request.getParameterValues("remito_inicio");
            String[] arrFecha_inicio  = request.getParameterValues("fecha_inicio");
            String[] arrRemito_cierre = request.getParameterValues("remito_cierre");
            String[] arrFecha_cierre  = request.getParameterValues("fecha_cierre");
            String[] arrDias          = request.getParameterValues("dias");
            String[] arrPosicion      = request.getParameterValues("posicion");
            String[] arrDescripcion   = request.getParameterValues("descripcion");
            String[] arrCantidad      = request.getParameterValues("cantidad");
            String[] arrPrecio        = request.getParameterValues("precio");
            String[] arrId_divisa     = request.getParameterValues("id_divisa");
            String[] arrSubtotal      = request.getParameterValues("subtotal");
            String[] arrUnidad        = request.getParameterValues("id_unidad");

            Integer id_cliente;
            Integer id_contrato;
            Integer id_site;
            Integer numero;
            Integer punto_venta;
            Float total;
            Preticket preticket;
            boolean todoOk;
            TPreticket tp = new TPreticket();
            TPreticket_detalle tpd = new TPreticket_detalle();
            TRemito tr = new TRemito();
            
            id_cliente = Parser.parseInt(idCliente);
            id_contrato = Parser.parseInt(idContrato);
            id_site = Parser.parseInt(idSite);
            total = Parser.parseFloat(strTotal);
            
            try{
              punto_venta = Integer.parseInt(ptoVta);
              numero      = Integer.parseInt(strNumero);
            } catch(NumberFormatException ex){
                numero = 0;
                punto_venta = 0 ;
            }
            if (id_cliente == 0 ) throw new BaseException("ERROR","Seleccione el cliente");
            if (numero==0 || punto_venta == 0) throw new BaseException("ERROR","Indique el punto de venta y el n&uacute;mero de contrato");
            
            if(tp.getByNumero(numero)!=null) throw new BaseException("ERROR","Ya existe un preticket con ese n&uacute;mero");
            
            ArrayList<Remito> lstRemitos = new ArrayList<Remito>();
            
            for(int i=0;i<lstId_remito.length;i++){
                Integer id_remito = Integer.parseInt(lstId_remito[i]);
                Remito remito = tr.getById(id_remito);
                if(remito!=null) lstRemitos.add(remito);
            }
            
            ArrayList<Preticket_detalle> lstPreticket_detalle = new ArrayList<Preticket_detalle>();
            for( int i=0;i<arrRemito_inicio.length;i++) {
                Integer remito_inicio   = Parser.parseInt(arrRemito_inicio [i].trim());
                String fecha_inicio     = arrFecha_inicio [i].trim();
                Integer remito_cierre   = Parser.parseInt(arrRemito_cierre [i].trim());
                String fecha_cierre     = arrFecha_cierre [i].trim();
                Integer dias            = Parser.parseInt(arrDias [i].trim());
                Integer posicion        = Parser.parseInt(arrPosicion [i].trim());
                String descripcion      = arrDescripcion [i].trim();
                Float cantidad          = Parser.parseFloat(arrCantidad [i].trim());
                Float precio            = Parser.parseFloat(arrPrecio [i].trim());
                Integer id_divisa       = Parser.parseInt(arrId_divisa [i].trim());
                Float  subtotal         = Parser.parseFloat(arrSubtotal [i].trim());
                Float  dias_herramienta = dias * cantidad;
                Integer id_unidad = Parser.parseInt(arrUnidad[i].trim());
                Preticket_detalle pd = new Preticket_detalle();
                
                pd.setRemito_inicio(remito_inicio);
                pd.setFecha_inicio(TFecha.formatearFecha(fecha_inicio, TFecha.formatoVista,TFecha.formatoBD));
                pd.setRemito_cierre(remito_cierre);
                pd.setFecha_cierre(TFecha.formatearFecha(fecha_cierre, TFecha.formatoVista,TFecha.formatoBD));
                pd.setDias(dias);
                pd.setPosicion(posicion);
                pd.setDescripcion(descripcion);
                pd.setCantidad(cantidad);
                pd.setId_unidad(id_unidad);
                pd.setPrecio(precio);
                pd.setSubtotal(subtotal);
                
                lstPreticket_detalle.add(pd);                
            }
            preticket = new Preticket();
            preticket.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));       
            preticket.setId_cliente(id_cliente);
            preticket.setId_contrato(id_contrato);
            preticket.setId_site(id_site);
            preticket.setPunto_venta(punto_venta);
            preticket.setNumero(numero);
            preticket.setFecha_creacion(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
            preticket.setTotal(total);
            Integer id_preticket = tp.alta(preticket);
            preticket.setId(id_preticket);
            todoOk = (preticket.getId()!=0);
            if(todoOk) {
                for(Preticket_detalle pd: lstPreticket_detalle){
                    pd.setId_preticket(preticket.getId());
                    tpd.alta(pd);
                }
                for(Remito remito:lstRemitos){
                    remito.setFacturado(1);
                    tr.actualizar(remito);
                }
            }
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_PRETICKET,OptionsCfg.ACCION_ALTA,preticket.getId());

            response.sendRedirect(PathCfg.PRETICKET);
       } catch(BaseException ex){
           request.setAttribute("titulo", ex.getResult());
           request.setAttribute("mensaje",ex.getMessage());
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
