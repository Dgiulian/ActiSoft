/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proveedor;

import bd.Proveedor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TProveedor;
import utils.BaseException;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class ProveedorEdit extends HttpServlet {

    
   
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
        Proveedor proveedor = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                proveedor = new TProveedor().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el proveedor");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (proveedor!=null){
                    request.setAttribute("proveedor", proveedor);
                }
        request.getRequestDispatcher("proveedor_edit.jsp").forward(request, response);
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
        Integer id;
        String razon_social          = request.getParameter("razon_social");
        String cuit                  = request.getParameter("cuit");
        String dni                   = request.getParameter("dni");
        String nombre_comercial      = request.getParameter("nombre_comercial");
        String direccion_fisica      = request.getParameter("direccion_fisica");
        String direccion_legal       = request.getParameter("direccion_legal");
        String codigo_postal         = request.getParameter("codigo_postal");
        Integer id_pais              = Parser.parseInt(request.getParameter("id_pais"));
        Integer id_provincia         = Parser.parseInt(request.getParameter("id_provincia"));
        Integer id_localidad         = Parser.parseInt(request.getParameter("id_localidad"));
        String telefono              = request.getParameter("telefono");
        String celular               = request.getParameter("celular");
        String contacto              = request.getParameter("contacto");
        String email                 = request.getParameter("email");
        String observaciones         = request.getParameter("observaciones");
        
        String id_estado             = request.getParameter("id_estado");
        Float descuento_especial     = Parser.parseFloat(request.getParameter("descuento_especial"));
        Float descuento_pronto_pago  = Parser.parseFloat(request.getParameter("descuento_pronto_pago"));
        Integer id_divisa            = Parser.parseInt(request.getParameter("id_divisa"));
        Integer id_forma_pago        = Parser.parseInt(request.getParameter("id_forma_pago"));
        Float monto_maximo           = Parser.parseFloat(request.getParameter("monto_maximo"));
        Integer id_iva               = Parser.parseInt(request.getParameter("id_iva"));
        Integer id_tipo_proveedor    = Parser.parseInt(request.getParameter("id_tipo_proveedor"));
        String banco1                = request.getParameter("banco1");
        String cuenta1               = request.getParameter("cuenta1");
        String banco2                = request.getParameter("banco2");
        String cuenta2               = request.getParameter("cuenta2");
       
        String conductores           = request.getParameter("conductores");
        String vehiculos             = request.getParameter("vehiculos");
        String dominios              = request.getParameter("dominios");
        String dni_conductor         = request.getParameter("dni_conductor");
        String nombre_transportista  = request.getParameter("nombre_transportista");
        String vencimiento_carnet    = TFecha.formatearFechaVistaBd(request.getParameter("vencimiento_carnet"));
        boolean todoOk;
        
        TProveedor tp = new TProveedor();
        Proveedor proveedor;
        Proveedor byCuit;
        boolean nuevo = false;
        try{


            id = Parser.parseInt(request.getParameter("id"));            
            proveedor = tp.getById(id);
            if (proveedor==null)  {                
                proveedor = new Proveedor();
                nuevo = true;
            }
            byCuit = tp.getByCuit(cuit);
            if(cuit!=null && !"".equals(cuit)) {
                if(byCuit!=null && byCuit.getId()!=proveedor.getId()){
                    throw new BaseException("ERROR","Ya existe un proveedor con ese C.U.I.T");
                }
            }
            proveedor.setRazon_social(razon_social);
            proveedor.setCuit(cuit);
            proveedor.setDni(dni);
            proveedor.setNombre_comercial(nombre_comercial);
            proveedor.setDireccion_fisica(direccion_fisica);
            proveedor.setDireccion_legal(direccion_legal);
            proveedor.setCodigo_postal(codigo_postal);
            proveedor.setId_pais(id_pais);
            proveedor.setId_provincia(id_provincia);
            proveedor.setId_localidad(id_localidad);
            proveedor.setTelefono(telefono);
            proveedor.setCelular(celular);
            proveedor.setContacto(contacto);
            proveedor.setEmail(email);
            proveedor.setObservaciones(observaciones);
            if(id_estado!=null)
                proveedor.setId_estado(1);
            else proveedor.setId_estado(0);
            proveedor.setDescuento_especial(descuento_especial);
            proveedor.setDescuento_pronto_pago(descuento_pronto_pago);
            proveedor.setId_divisa(id_divisa);
            proveedor.setId_forma_pago(id_forma_pago);
            proveedor.setMonto_maximo(monto_maximo);
            proveedor.setId_iva(id_iva);
            proveedor.setId_tipo_proveedor(id_tipo_proveedor);
            proveedor.setBanco1(banco1);
            proveedor.setCuenta1(cuenta1);
            proveedor.setBanco2(banco2);
            proveedor.setCuenta2(cuenta2);
            proveedor.setConductores(conductores);
            proveedor.setVehiculos(vehiculos);
            proveedor.setDominios(dominios);
            proveedor.setDni_conductor(dni_conductor);
            proveedor.setNombre_transportista(nombre_transportista);
            if(vencimiento_carnet!=null && !"".equals(vencimiento_carnet))
                proveedor.setVencimiento_carnet(vencimiento_carnet);
            else proveedor.setVencimiento_carnet("2099-12-31");
            
            proveedor.setDni_conductor(dni_conductor);
            if(nuevo) proveedor.setFecha_alta(TFecha.ahora(TFecha.formatoBD ));
            if (!request.getParameter("id_provincia").equals(""))
                proveedor.setId_provincia(Integer.parseInt(request.getParameter("id_provincia")));
            if (!request.getParameter("id_localidad").equals(""))
                proveedor.setId_localidad(Integer.parseInt(request.getParameter("id_localidad")));

            if(nuevo){
                id = tp.alta(proveedor);
                todoOk = id!=0;
            } else {
                todoOk = tp.actualizar(proveedor);                
            }        
            if(!todoOk) 
                throw new BaseException("Error", "Ocurrio un error al guardar el proveedor");            
        
        }
        catch(BaseException ex){
            request.setAttribute("titulo",ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
        response.sendRedirect(PathCfg.PROVEEDOR);
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
