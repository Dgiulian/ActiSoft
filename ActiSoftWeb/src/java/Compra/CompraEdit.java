/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

import bd.Activo;
import bd.Compra;
import bd.Rubro;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TCompra;
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
public class CompraEdit extends HttpServlet {

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
        String idCompra = request.getParameter("id");        
        String idActivo = request.getParameter("id_activo");
        
        
        Integer  id_compra;
        Compra compra;
        boolean nuevo = false;
        try{
            id_compra = Integer.parseInt(idCompra);
        } catch (NumberFormatException ex){
            id_compra = 0;
        }
        compra = new TCompra().getById(id_compra);
        if(compra==null) {
            compra = new Compra();
            nuevo = true;
            
        }
        Integer id_activo;
        try{
            if (nuevo) {
                id_activo = Parser.parseInt(idActivo);
            } else id_activo = compra.getId_activo();
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            Rubro rubro = new TRubro().getById(activo.getId_rubro());
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(activo.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica compra");
            
            request.setAttribute("activo", activo);
            request.setAttribute("compra", compra);
            request.getRequestDispatcher("compra_edit.jsp").forward(request, response);
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
        String idCompra     = request.getParameter("id");
        String idActivo     = request.getParameter("id_activo");        
        String fecha        = request.getParameter("fecha");
        String id_divisa    = request.getParameter("id_divisa");
        String cantidad     = request.getParameter("cantidad");
        String precio_unit  = request.getParameter("precio_unit");
        String precio_tot   = request.getParameter("precio_tot");
        String id_proveedor = request.getParameter("id_proveedor");
        String factura      = request.getParameter("factura");
        Integer id_accion   = Parser.parseInt(request.getParameter("id_accion"));        
        try{
            Integer id_activo;
            Integer  id_compra;
            try{
                id_activo = Integer.parseInt(idActivo);                
            } catch (NumberFormatException ex){
                id_activo = 0;
            }
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Compra compra;
            TCompra tc = new TCompra();
            boolean nuevo = false;
            try{
                id_compra = Integer.parseInt(idCompra);
                compra = tc.getById(id_compra);
            } catch (NumberFormatException ex){
                compra = new Compra();
                nuevo = true;
            }            
           
            String fechabd = TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD);
            String fechaAnt = compra.getFecha();
            compra.setFecha(fechabd);
            compra.setId_activo(id_activo);
            
            if(!fechaAnt.equalsIgnoreCase(fechabd) && tc.getCompraPosterior(compra)!=null) throw new BaseException("ERROR","La fecha de compra no puede ser anterior a las compras anteriores");
            if (activo.getStock()==0f) throw new BaseException("ERROR","No se puede crear una compra si el stock del activo es 0");
            
            compra.setId_divisa(Parser.parseInt(id_divisa));
            
            compra.setPrecio_unit(Parser.parseFloat(precio_unit));
            compra.setPrecio_tot(Parser.parseFloat(precio_tot));
            compra.setId_proveedor(Parser.parseInt(id_proveedor));
            compra.setFactura(factura);
            boolean todoOk = true;
            if(nuevo) {
                compra.setCantidad(Parser.parseFloat(cantidad)); 
                compra.setStock_anterior(activo.getStock());
                compra.setId_accion(id_accion);
                switch(id_accion) {
                    case OptionsCfg.COMPRA_NADA: break;
                    case OptionsCfg.COMPRA_REEMPLAZA: activo.setStock(compra.getCantidad()); break;
                    case OptionsCfg.COMPRA_SUMA: activo.setStock(activo.getStock() + compra.getCantidad());
                    break;
                default: break;
            }
                id_compra = tc.alta(compra);
                compra.setId(id_compra);
                todoOk = id_compra!=0;
            } else todoOk = tc.actualizar(compra);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar la compra");
            
            
            new TActivo().actualizar(activo);
            
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_COMPRA,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,compra.getId());
        
            response.sendRedirect(PathCfg.COMPRA + "?id_activo=" + compra.getId_activo());
            
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
