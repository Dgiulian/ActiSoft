/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contrato;

import bd.Cliente;
import bd.Contrato;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCliente;
import transaccion.TContrato;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class ContratoList extends HttpServlet {

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
    HashMap<Integer,Cliente> mapClientes;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        
        
        
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
         
        try {
            JsonRespuesta jr = new JsonRespuesta();           
           
            List<Contrato> lista ;
            String idCliente = request.getParameter("id_cliente");
            Integer id_cliente = 0;
            try{
                id_cliente = Integer.parseInt(idCliente);
            } catch(NumberFormatException ex) {
                id_cliente = 0;
            }            
                    
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            if(id_cliente!=0) mapFiltro.put("id_cliente", id_cliente.toString());
            
            if(page==0) lista = new TContrato().getListFiltro(mapFiltro);
            else lista = new TContrato().getList(page,10);
            
            mapClientes = new HashMap<Integer,Cliente>();
            for (Cliente cliente:new TCliente().getList()){
                mapClientes.put(cliente.getId(),cliente);
            }
                
            List<ContratoDet> listaDet = new ArrayList();
            for(Contrato activo:lista){
                listaDet.add(new ContratoDet(activo));
            }
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
    private class ContratoDet extends Contrato{
        public String nombre_cliente = "";
        public ContratoDet(Contrato contrato){
            super(contrato);
            nombre_cliente = contrato.getId_cliente().toString();
            Cliente cliente = mapClientes.get(contrato.getId_cliente());
            if (cliente !=null )
                nombre_cliente = cliente.getNombre();
        }
    }
}
