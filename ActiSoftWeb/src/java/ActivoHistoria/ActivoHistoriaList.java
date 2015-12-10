/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivoHistoria;

import bd.Activo_historia;
import bd.Cliente;
import bd.Contrato;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo_historia;
import transaccion.TCliente;
import transaccion.TContrato;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ActivoHistoriaList extends HttpServlet {
     HashMap<Integer,OptionsCfg.Option> mapTipos;
     HashMap<Integer,OptionsCfg.Option> mapEstados;
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
        
        mapTipos = new HashMap<Integer,OptionsCfg.Option>();
        for(OptionsCfg.Option o: OptionsCfg.getTipoRemitos()){
            mapTipos.put(o.getId(),o);
        }
        mapEstados = new HashMap<Integer,OptionsCfg.Option>();
        for(OptionsCfg.Option o:OptionsCfg.getEstadoRemitos()){
            mapEstados.put(o.getId(), o);
        }
        
        mapContratos = new TContrato().getMap();
        mapClientes = new TCliente().getMap();
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        JsonRespuesta jr = new JsonRespuesta();
        try {
            Integer id_activo  = Parser.parseInt(request.getParameter("id_activo"));
            Integer id_tipo    = Parser.parseInt(request.getParameter("id_tipo"));
            Integer id_cliente = Parser.parseInt(request.getParameter("id_cliente"));
            Integer id_site    = Parser.parseInt(request.getParameter("id_site"));
            Integer id_estado  = Parser.parseInt(request.getParameter("id_estado"));
            
            
            List<Activo_historia> lista;
            TActivo_historia tr = new TActivo_historia();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            Activo_historia a = new Activo_historia();            
            if(id_activo==0 && id_tipo==0 && id_cliente == 0 && id_site == 0 && id_estado==0) throw new BaseException("ERROR","Debe ingresar alg&uacute;n par&aacute;metro de busqueda");
            if(id_activo!=0)
                mapFiltro.put("id_activo",id_activo.toString());

            if(id_tipo != 0)
                mapFiltro.put("id_tipo_remito",id_tipo.toString());

            if(id_cliente != 0)
                mapFiltro.put("id_cliente",id_cliente.toString());

            if(id_site != 0)
                mapFiltro.put("id_site",id_site.toString());
            if(id_estado != 0)
                mapFiltro.put("id_estado",id_estado.toString());
            
            lista = tr.getListFiltro(mapFiltro);
            
            List<Activo_historiaDet> listaDet = new ArrayList();
            for(Activo_historia activo:lista){
                listaDet.add(new Activo_historiaDet(activo));
            }
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
        } catch (BaseException ex) {          
             jr.setResult(ex.getResult());
             jr.setMessage(ex.getMessage());
         } finally {          
              String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
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
private class Activo_historiaDet extends Activo_historia{
     public String tipo_remito;
     public String estado;
     public String cliente;
     public String contrato;
     
     public Activo_historiaDet(Activo_historia remito){
         super(remito);
         // Por default devolvemos el Id
         this.tipo_remito = remito.getId_tipo_remito().toString();
         this.estado = remito.getId_estado().toString();
         this.contrato = remito.getId_contrato().toString();
         this.cliente = remito.getId_cliente().toString();
         
         OptionsCfg.Option o = mapTipos.get(remito.getId_tipo_remito());
         if(o!=null)
             this.tipo_remito = o.getDescripcion();
         
         o = mapEstados.get(remito.getId_estado());
         if(o!=null)
             this.estado = o.getDescripcion();
         Contrato c = mapContratos.get(remito.getId_contrato());
         if(c!=null)
             this.contrato = c.getNumero();
         
         Cliente cli = mapClientes.get(remito.getId_cliente());
         if(cli!=null)
             this.cliente = cli.getNombre();
     }
 }
}
