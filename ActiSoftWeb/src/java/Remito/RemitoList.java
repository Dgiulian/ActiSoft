/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Cliente;
import bd.Contrato;
import bd.Equipo;
import bd.Pozo;
import bd.Remito;
import bd.Site;
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
import transaccion.TEquipo;
import transaccion.TPozo;
import transaccion.TRemito;
import transaccion.TSite;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class RemitoList extends HttpServlet {

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
     HashMap<Integer,Option> mapTipos;
     HashMap<Integer,Option> mapEstados;
     HashMap<Integer,Contrato> mapContratos;
     HashMap<Integer,Pozo> mapPozos;
     HashMap<Integer,Equipo> mapEquipos;
     HashMap<Integer,Cliente> mapClientes;
     HashMap<Integer,Site> mapSites;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        
        mapTipos = OptionsCfg.getMap(OptionsCfg.getTipoRemitos());
        mapEstados = OptionsCfg.getMap(OptionsCfg.getEstadoRemitos());
        mapContratos = new TContrato().getMap();
        mapPozos = new TPozo().getMap();
        mapEquipos = new TEquipo().getMap();
        mapClientes = new TCliente().getMap();
        mapSites = new TSite().getMap();
//        mapTipos = new HashMap<Integer,Option>();
//        for(Option o: OptionsCfg.getTipoRemitos()){ mapTipos.put(o.getId(),o); }
//        mapEstados = new HashMap<Integer,Option>();
//        for(Option o:OptionsCfg.getEstadoRemitos()){ mapEstados.put(o.getId(), o); }
//        mapContratos = new HashMap<Integer,Contrato>();
//        for(Contrato c: new TContrato().getList()){ mapContratos.put(c.getId(), c); }
//        mapClientes = new HashMap<Integer,Cliente>();
//        for(Cliente cli:new TCliente().getList()){ mapClientes.put(cli.getId(), cli); }
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        JsonRespuesta jr = new JsonRespuesta();
        try {
            
            Integer facturado = null;
            
            Integer id_estado  = Parser.parseInt(request.getParameter("id_estado"));
            Integer id_tipo    = Parser.parseInt(request.getParameter("id_tipo"));
            Integer numero     = Parser.parseInt(request.getParameter("numero"));
            Integer id_cliente = Parser.parseInt(request.getParameter("id_cliente"));
            Integer id_site    = Parser.parseInt(request.getParameter("id_site"));
            Integer id_pozo    = Parser.parseInt(request.getParameter("id_pozo"));
            Integer id_equipo  = Parser.parseInt(request.getParameter("id_equipo"));

            String strFacturado = request.getParameter("facturado");
            if (strFacturado!=null && !"".equals(strFacturado))facturado = Parser.parseInt(strFacturado);
                
                
            List<Remito> lista;
            TRemito tr = new TRemito();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            if(numero!=0){
                mapFiltro.put("numero",numero.toString());
            }
            else {
                if(id_estado!=0)mapFiltro.put("id_estado",id_estado.toString());
                if(id_tipo != 0) mapFiltro.put("id_tipo_remito",id_tipo.toString());
            }
            
            if(facturado!=null) mapFiltro.put("facturado",facturado.toString());
            if (id_cliente!=0)  mapFiltro.put("id_cliente",id_cliente.toString());
            if (id_site!=0)     mapFiltro.put("id_site",id_cliente.toString());
            if (id_pozo!=0)     mapFiltro.put("id_pozo",id_pozo.toString());
            if (id_equipo!=0)   mapFiltro.put("id_equipo",id_equipo.toString());
            
            lista = tr.getListFiltro(mapFiltro);
            
            List<RemitoDet> listaDet = new ArrayList();
            for(Remito activo:lista){
                listaDet.add(new RemitoDet(activo));
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
 private class RemitoDet extends Remito{
     public String tipo_remito;
     public String estado;
     public String cliente;
     public String contrato;
     public String pozo;
     public String equipo;
     public String site;
     
     public boolean tiene_devolucion = false;
     
     public RemitoDet(Remito remito){
         super(remito);
         // Por default devolvemos el Id
         this.tipo_remito = remito.getId_tipo_remito().toString();
         this.estado = remito.getId_estado().toString();
         this.contrato = remito.getId_contrato().toString();
         this.pozo     = remito.getId_pozo().toString();
         this.equipo   = remito.getId_equipo().toString();
         this.cliente  = remito.getId_cliente().toString();
         this.site     = remito.getId_site().toString();
         this.equipo   = "";
         Option o      = mapTipos.get(remito.getId_tipo_remito());
         if(o!=null)
             this.tipo_remito = o.getDescripcion();
         
         o = mapEstados.get(remito.getId_estado());
         if(o!=null)
             this.estado = o.getDescripcion();
         Contrato c = mapContratos.get(remito.getId_contrato());
         if(c!=null)
             this.contrato = c.getNumero();
         
         Pozo p = mapPozos.get(remito.getId_pozo());
         if(p!=null)
             this.pozo = p.getNombre();
         
         Equipo e = mapEquipos.get(remito.getId_equipo());
         if(e!=null)
             this.equipo = e.getNombre();
         
         Cliente cli = mapClientes.get(remito.getId_cliente());
         if(cli!=null)
             this.cliente = cli.getNombre();
         if(remito.getId_tipo_remito()==OptionsCfg.REMITO_ENTREGA){
             this.tiene_devolucion = new TRemito().tieneDevolucion(remito);
         }
         Site s = mapSites.get(remito.getId_site());
         if(s!=null){
             this.site = s.getNombre();
             //this.equipo =  s.getEquipo();
         }
         
         
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
