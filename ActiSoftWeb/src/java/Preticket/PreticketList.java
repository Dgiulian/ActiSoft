/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Preticket;

import bd.Cliente;
import bd.Contrato;
import bd.Preticket;
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
import transaccion.TPreticket;
import utils.JsonRespuesta;
import utils.OptionsCfg.Option;

/**
 *
 * @author Diego
 */
public class PreticketList extends HttpServlet {
     HashMap<Integer,Contrato> mapContratos;
     HashMap<Integer,Cliente> mapClientes;
     
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
               
        mapContratos = new TContrato().getMap();
        mapClientes  = new TCliente().getMap();
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        JsonRespuesta jr = new JsonRespuesta();
        try {
            String idEstado = request.getParameter("id_estado");
            String idTipo = request.getParameter("id_tipo");
            String strNumero = request.getParameter("numero");
            
            Integer id_estado = 0;
            Integer id_tipo = 0;
            Integer numero = 0;
            
            if (idEstado!=null && !idEstado.equals("")) id_estado = Integer.parseInt(idEstado);
            if (idTipo!=null && !idTipo.equals("")) id_tipo = Integer.parseInt(idTipo);
            if (strNumero!=null && !strNumero.equals("")) numero = Integer.parseInt(strNumero);
            
            List<Preticket> lista;
            TPreticket tr = new TPreticket();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            if(numero!=0){
                mapFiltro.put("numero",numero.toString());
            }
            else {
                if(id_estado!=0)mapFiltro.put("id_estado",id_estado.toString());
                if(id_tipo != 0) mapFiltro.put("id_tipo_remito",id_tipo.toString());
            }
            
            lista = tr.getListFiltro(mapFiltro);
            
            List<PreticketDet> listaDet = new ArrayList();
            for(Preticket activo:lista){
                listaDet.add(new PreticketDet(activo));
            }
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
            
          
        } finally {          
              String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }

    private class PreticketDet extends Preticket{
     public String tipo_remito;
     public String estado;
     public String cliente;
     public String contrato;
     
     public PreticketDet(Preticket preticket){
         super(preticket);
         // Por default devolvemos el Id
                 this.contrato = preticket.getId_contrato().toString();
         this.cliente = preticket.getId_cliente().toString();
                  
         Contrato c = mapContratos.get(preticket.getId_contrato());
         if(c!=null)
             this.contrato = c.getNumero();
         
         Cliente cli = mapClientes.get(preticket.getId_cliente());
         if(cli!=null)
             this.cliente = cli.getNombre();
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
}
