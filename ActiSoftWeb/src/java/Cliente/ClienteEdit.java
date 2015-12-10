/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import bd.Cliente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TCliente;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class ClienteEdit extends HttpServlet {

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
        Cliente cliente = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                cliente = new TCliente().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el cliente");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (cliente!=null){
                    request.setAttribute("cliente", cliente);
                }
        request.getRequestDispatcher("cliente_edit.jsp").forward(request, response);
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
        TCliente tCliente = new TCliente();
        Cliente cliente;
        boolean nuevo = false;
        try{
            if(request.getParameter("id")!=null) {
            try{
                id = Parser.parseInt(request.getParameter("id"));            
                cliente = tCliente.getById(id);
                if (cliente==null) 
                    throw new BaseException("Error", "No se ha encontrado el cliente");            
                }
            catch(NumberFormatException ex){
                throw new BaseException("Error", "No se ha encontrado el cliente");                
                
            }
        } else {
            id = 0;
            cliente = new Cliente();
            nuevo = true;
        }
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setCuit(request.getParameter("cuit"));
        cliente.setDni(request.getParameter("dni"));
        cliente.setNombre_comercial(request.getParameter("nombre_comercial"));
        cliente.setObservaciones(request.getParameter("observaciones"));
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setContacto(request.getParameter("contacto"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setCelular(request.getParameter("celular"));        
        cliente.setDireccion_legal(request.getParameter("direccion_legal"));
        cliente.setDireccion_fisica(request.getParameter("direccion_fisica"));
        cliente.setCodigo_postal(request.getParameter("codigo_postal"));
        if(request.getParameter("id_estado")!=null) cliente.setId_estado(1);
        else cliente.setId_estado(0);
        if(nuevo) cliente.setFecha_alta(TFecha.ahora(TFecha.formatoBD ));
        if (!request.getParameter("id_provincia").equals(""))
            cliente.setId_provincia(Integer.parseInt(request.getParameter("id_provincia")));
        if (!request.getParameter("id_localidad").equals(""))
            cliente.setId_localidad(Integer.parseInt(request.getParameter("id_localidad")));
        
        boolean todoOk = false;
        if(nuevo){
           id = tCliente.alta(cliente);
           todoOk = id!=0;
        } else {
            todoOk = tCliente.actualizar(cliente);
        }        
        if(!todoOk) 
            throw new BaseException("Error", "Ocurrio un error al guardar el cliente");            
        Integer id_usuario = 0;
        Integer id_tipo_usuario = 0;
        HttpSession session = request.getSession();
        id_usuario = (Integer) session.getAttribute("id_usuario");
        id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CLIENTE,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,cliente.getId());
        }
        catch(BaseException ex){
            request.setAttribute("titulo",ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
        response.sendRedirect(PathCfg.CLIENTE);
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
