/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import bd.Cliente;
import bd.Localidad;
import bd.Provincia;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCliente;
import transaccion.TLocalidad;
import transaccion.TProvincia;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class ClienteList extends HttpServlet {

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
            
            List<Cliente> lista = new TCliente().getList();
            List<ClienteDet> listaDet = new ArrayList();            
                        
            if (lista != null) {
                for(Cliente c:lista) listaDet.add(new ClienteDet(c));
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
 class ClienteDet extends Cliente{
     String provincia = "";
     String localidad = "";
     public ClienteDet(Cliente cliente){
         super(cliente);  
         Provincia p = mapProvincias.get(cliente.getId_provincia());
         if (p!=null)
            this.provincia = p.getProv_descripcion();
         Localidad l = mapLocalidades.get(cliente.getId_localidad());
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
