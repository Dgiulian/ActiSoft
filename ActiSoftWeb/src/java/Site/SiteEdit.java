/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Site;

import bd.Cliente;
import bd.Site;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TCliente;
import transaccion.TSite;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class SiteEdit extends HttpServlet {


    
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
        String idCertif = request.getParameter("id");        
        String idCliente = request.getParameter("id_cliente");
        
        
        Integer  id_site;
        Site site = null;     
        boolean nuevo = false;        
        try{
            id_site = Integer.parseInt(idCertif);            
        } catch (NumberFormatException ex){
            id_site = 0;
        }
        site = new TSite().getById(id_site);
        if(site==null) {
            site = new Site();            
            nuevo = true;
        }
        try{
            Integer id_cliente;
            if(nuevo){                
                try{
                    id_cliente = Integer.parseInt(idCliente);                
                } catch (NumberFormatException ex){
                    id_cliente = 0;
                }
            } else id_cliente = site.getId_cliente();
            
            Cliente cliente = new TCliente().getById(id_cliente);
            if (cliente == null) throw new BaseException("Cliente inexistente", "No se encontr&oacute; el cliente");
            
            if (nuevo) site.setId_cliente(cliente.getId());
            
            request.setAttribute("cliente", cliente);
            request.setAttribute("site", site);
            request.getRequestDispatcher("site_edit.jsp").forward(request, response);
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
        String idSite = request.getParameter("id");
        String idCliente = request.getParameter("id_cliente");        
        String nombre  = request.getParameter("nombre");
        String area = request.getParameter("area");
        String pozo = request.getParameter("pozo");
        String equipo = request.getParameter("equipo");
        String observaciones = request.getParameter("observaciones");
        
        String encargado = request.getParameter("encargado");
        String telefono  = request.getParameter("telefono");
        String horario   = request.getParameter("horario");
        String idEstado  = request.getParameter("id_estado");
        
        String strLatitud  = request.getParameter("latitud");
        String strLongitud = request.getParameter("longitud");
        String tipo        = request.getParameter("tipo");        
        try{
            Integer id_cliente;
            Integer id_site;
            Float latitud  = 0f;
            Float longitud = 0f;
            
            id_cliente = Parser.parseInt(idCliente);                
           
            
            latitud = Parser.parseFloat(strLatitud);
            longitud = Parser.parseFloat(strLongitud);
            
            
            Cliente cliente = new TCliente().getById(id_cliente);
            if (cliente == null) throw new BaseException("Cliente inexistente", "No se encontr&oacute; el cliente");
            
            Site site;
            TSite tc = new TSite();
            boolean nuevo = false;
            
            id_site = Parser.parseInt(idSite);
            site = tc.getById(id_site);
            if (site==null) {
                site = new Site();
                site.setFecha_creacion(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
                nuevo = true;
            }        

            Integer id_estado = idEstado!=null?1:0;

            try{
                site.setId_cliente(id_cliente);
                site.setNombre(nombre);
                site.setArea(area);
                site.setPozo(pozo);
                site.setEquipo(equipo);
                site.setEncargado(encargado);
                site.setTelefono(telefono);
                site.setHorario(horario);
                site.setId_estado(id_estado);
                site.setObservaciones(observaciones);
                site.setLatitud(latitud);
                site.setLongitud(longitud);                                        
            } catch (NumberFormatException ex){
                throw new BaseException("Error","Error de parseo de argumentos");
            }
            boolean todoOk = true;
            if(nuevo) {
                id_site= tc.alta(site);
                site.setId(id_site);
                todoOk = id_site!=0;
            } else todoOk = tc.actualizar(site);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar el site");
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_SITE,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,site.getId());
            if(tipo.equalsIgnoreCase("modal")){
                 response.setContentType("application/json;charset=UTF-8");
               PrintWriter out = response.getWriter();
               JsonRespuesta jr = new JsonRespuesta();
               String jsonResult = null;
               jr.setResult("OK");
               jr.setMessage("Site creado correctamente");
               jr.setRecord(site);
               out.print(new Gson().toJson(jr));
               out.close();
            }
            else {
                response.sendRedirect(PathCfg.SITE + "?id_cliente=" + site.getId_cliente());
            }
            
        } catch (BaseException ex){
            if(tipo.equalsIgnoreCase("modal")){
               response.setContentType("application/json;charset=UTF-8");
               PrintWriter out = response.getWriter();
               JsonRespuesta jr = new JsonRespuesta();
               String jsonResult = null;
               jr.setResult(ex.getResult());
               jr.setMessage(ex.getMessage());
               out.print(new Gson().toJson(jr));
               out.close();
            } else {
                request.setAttribute("titulo", ex.getResult());
                request.setAttribute("mensaje", ex.getMessage());
                request.getRequestDispatcher("message.jsp").forward(request,response);
            }
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
