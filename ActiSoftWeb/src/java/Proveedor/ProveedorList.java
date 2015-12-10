/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proveedor;

import bd.Localidad;
import bd.Proveedor;
import bd.Provincia;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TLocalidad;
import transaccion.TProveedor;
import transaccion.TProvincia;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class ProveedorList extends HttpServlet {

    List<Provincia> lstProvincias;
    
    Map<Integer,Provincia> mapProvincias;
    Map<Integer,Localidad> mapLocalidades ;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        mapProvincias = new TProvincia().getMap();
        mapLocalidades = new TLocalidad().getMap();
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
         
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            
            List<Proveedor> lista = new TProveedor().getList();
            List<ProveedorDet> listaDet = new ArrayList();            
            for(Proveedor c:lista) listaDet.add(new ProveedorDet(c));
            
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
        } finally {            
            out.close();
        }
    }
 class ProveedorDet extends Proveedor{
     String provincia = "";
     String localidad = "";
     public ProveedorDet(Proveedor proveedor){
         super(proveedor);  
         Provincia p = mapProvincias.get(proveedor.getId_provincia());
         if (p!=null)
            this.provincia = p.getProv_descripcion();
         Localidad l = mapLocalidades.get(proveedor.getId_localidad());
         if(l!=null)
             this.localidad = l.getLoc_descripcion();
     }
 }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
