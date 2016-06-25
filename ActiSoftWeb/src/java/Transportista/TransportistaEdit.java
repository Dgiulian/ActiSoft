/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportista;

import bd.Proveedor;
import bd.Transportista;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TProveedor;
import transaccion.TTransportista;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class TransportistaEdit extends HttpServlet {


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
        Transportista transportista = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                transportista = new TTransportista().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el transportista");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (transportista!=null){
            request.setAttribute("transportista", transportista);
        }
        request.getRequestDispatcher("transportista_edit.jsp").forward(request, response);
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
        Integer id           = Parser.parseInt(request.getParameter("id"));
        Integer id_proveedor = Parser.parseInt(request.getParameter("id_proveedor"));
        String  nombre       = request.getParameter("nombre");
        String  dni       = request.getParameter("dni");
        String  vencimiento_carnet = request.getParameter("vencimiento_carnet");
        String  vencimiento_seguro  =request.getParameter("vencimiento_seguro");
        TProveedor tp       = new TProveedor();
        TTransportista tt   = new TTransportista();
        Proveedor proveedor ;
        Transportista transportista;
        JsonRespuesta jr = new JsonRespuesta();
        boolean nuevo = false;
        boolean todoOk;
        try{
            proveedor = tp.getById(id_proveedor);
            if(proveedor ==null) throw new BaseException("ERROR","No existe el proveedor");
            transportista = tt.getById(id);
            if ( transportista ==null){
                transportista = new Transportista();
                transportista.setId_proveedor(id_proveedor);
                nuevo = true;
            }
            transportista.setNombre(nombre);
            transportista.setDni(dni);
            String carnet = TFecha.formatearFechaVistaBd(vencimiento_carnet);
            String seguro = TFecha.formatearFechaVistaBd(vencimiento_seguro);
            if(!"".equals(carnet)) transportista.setVencimiento_carnet(carnet);
            if(!"".equals(seguro)) transportista.setVencimiento_seguro(seguro);
            
            if(nuevo){
                id = tt.alta(transportista);
                todoOk = id!=0;
            }else{
                todoOk = tt.actualizar(transportista);
            }
            if(todoOk) {
                transportista.setId(id);
                jr.setResult("OK");
                jr.setRecord(transportista); 
            } else throw new BaseException("ERROR","Ocurri&oacute; un error al guardar el transportista");
            
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
