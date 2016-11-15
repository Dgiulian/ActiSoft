/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vehiculo;

import bd.Proveedor;
import bd.Vehiculo;
import bd.Vehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TProveedor;
import transaccion.TVehiculo;
import transaccion.TVehiculo;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class VehiculoEdit extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VehiculoEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VehiculoEdit at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
        Vehiculo vehiculo = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                vehiculo = new TVehiculo().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el vehiculo");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (vehiculo!=null){
            request.setAttribute("vehiculo", vehiculo);
        }
        request.getRequestDispatcher("vehiculo_edit.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer id                    = Parser.parseInt(request.getParameter("id"));
        Integer id_proveedor          = Parser.parseInt(request.getParameter("id_proveedor"));
        String  dominio               = request.getParameter("dominio");
        String  vencimiento_vtv       = request.getParameter("vencimiento_vtv");
        String  seguro                = request.getParameter("seguro");
        String  poliza                = request.getParameter("poliza");
        String  vencimiento_poliza    = request.getParameter("vencimiento_poliza");
        String  rsv                   = request.getParameter("rsv");
        
        Integer numero_titulo         = Parser.parseInt(request.getParameter("numero_titulo"));
        String vencimiento_cedula     = request.getParameter("vencimiento_cedula");
        String modelo                 = request.getParameter("modelo");
        String seguro_xantrax         = request.getParameter("seguro_xantrax");
        String servicio_mantenimiento = request.getParameter("servicio_mantenimiento");
        String servicio_fecha         = request.getParameter("servicio_fecha");
        String servicio_xantrax       = request.getParameter("servicio_xantrax");
        
        
        
        TProveedor tp       = new TProveedor();
        TVehiculo tt   = new TVehiculo();
        Proveedor proveedor ;
        Vehiculo vehiculo;
        JsonRespuesta jr = new JsonRespuesta();
        boolean nuevo = false;
        boolean todoOk = true;
        try{
            proveedor = tp.getById(id_proveedor);
            if(proveedor ==null) throw new BaseException("ERROR","No existe el proveedor");
            vehiculo = tt.getById(id);
            
            if ( vehiculo ==null){
                vehiculo = new Vehiculo();
                vehiculo.setId_proveedor(id_proveedor);
                nuevo = true;
            }
            
            vehiculo.setDominio(dominio);
            vehiculo.setSeguro(seguro);
            vehiculo.setPoliza(poliza);
            vehiculo.setRsv(rsv);
            vehiculo.setModelo(modelo);
            vehiculo.setNumero_titulo(numero_titulo);
            
            String vtv = TFecha.formatearFechaVistaBd(vencimiento_vtv);
            String v_poliza = TFecha.formatearFechaVistaBd(vencimiento_poliza);
            String v_cedula = TFecha.formatearFechaVistaBd(vencimiento_cedula);
            String v_servicio = TFecha.formatearFechaVistaBd(servicio_fecha);
            
            if(!"".equals(vtv))        vehiculo.setVencimiento_vtv(vtv);
            if(!"".equals(v_poliza))   vehiculo.setVencimiento_poliza(v_poliza);
            if(!"".equals(v_cedula))   vehiculo.setVencimiento_cedula(v_cedula);
            if(!"".equals(v_servicio)) vehiculo.setServicio_fecha(v_servicio);
            
            if(seguro_xantrax!=null && !"".equals(seguro_xantrax)) vehiculo.setSeguro_xantrax(1);
            else vehiculo.setSeguro_xantrax(0);
            
            if(servicio_mantenimiento!=null && !"".equals(servicio_mantenimiento)  && "true".equals(servicio_mantenimiento)) vehiculo.setServicio_mantenimiento(1);
            else vehiculo.setServicio_mantenimiento(0);
                    
            if(servicio_xantrax!=null && !"".equals(servicio_xantrax) && "true".equals(servicio_xantrax) ) vehiculo.setServicio_xantrax(1);
            else vehiculo.setServicio_xantrax(0);
            
            if(nuevo){
                id = tt.alta(vehiculo);
                todoOk = id!=0;
            }else{
                todoOk = tt.actualizar(vehiculo);
            }
            if(todoOk) {
                vehiculo.setId(id);
                jr.setResult("OK");
                jr.setRecord(vehiculo); 
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el vehiculo");
            
        } catch (BaseException ex){
            jr.setMessage(ex.getMessage());
            jr.setResult(ex.getResult());
        }finally{            
            out.print(new Gson().toJson(jr));
            out.close();
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
