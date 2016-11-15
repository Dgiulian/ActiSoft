/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Cliente;
import bd.Contrato;
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
import transaccion.TRemito;
import transaccion.TSite;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class RemitoRelacionadoList extends HttpServlet {
    HashMap<Integer,OptionsCfg.Option> mapTipos;
    HashMap<Integer,OptionsCfg.Option> mapEstados;
    HashMap<Integer,Contrato> mapContratos;
    HashMap<Integer,Cliente> mapClientes;
    HashMap<Integer,Site> mapSites;
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
       response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonRespuesta jr = new JsonRespuesta();
        try{
            
            Integer id_remito = Parser.parseInt(request.getParameter("id_remito"));
             mapTipos = OptionsCfg.getMap(OptionsCfg.getTipoRemitos());
        mapEstados = OptionsCfg.getMap(OptionsCfg.getEstadoRemitos());
        mapContratos = new TContrato().getMap();
        mapClientes = new TCliente().getMap();
        mapSites = new TSite().getMap();
            List<Remito> lstRemitos = new ArrayList<Remito>();
            TRemito  tr = new TRemito();
            Remito r =  tr.getById(id_remito);
            if(r==null) throw new BaseException("ERROR","No se encontr&oacute; el remito");
            
//            while(r!=null){
//                lstRemitos.add(r);
//                id_remito = r.getId_referencia();
//                r =  tr.getById(id_remito);                
//            }
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            mapFiltro.put("id_referencia", id_remito.toString());
            //tr.setOrderBy(" fecha ");
            lstRemitos = tr.getListFiltro(mapFiltro);
            ArrayList<RemitoDet> listaDet = new ArrayList<RemitoDet>();
             if (lstRemitos != null) {
                for(Remito activo:lstRemitos){
                    listaDet.add(new RemitoDet(activo));
                }
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }
            jr.setResult("OK");
            jr.setRecords(listaDet);
            
        }catch(BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } finally{
            out.println(new Gson().toJson(jr));
            out.close();
            
        }
    }
private class RemitoDet extends Remito{
     public String tipo_remito;
     public String estado;
     public String cliente;
     public String contrato;
     public String site;
     public String equipo;
     public boolean tiene_devolucion = false;
     
     public RemitoDet(Remito remito){
         super(remito);
         // Por default devolvemos el Id
         this.tipo_remito = remito.getId_tipo_remito().toString();
         this.estado = remito.getId_estado().toString();
         this.contrato = remito.getId_contrato().toString();
         this.cliente = remito.getId_cliente().toString();
         this.site    = remito.getId_site().toString();
         this.equipo  = "";
         OptionsCfg.Option o     = mapTipos.get(remito.getId_tipo_remito());
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
         if(remito.getId_tipo_remito()==OptionsCfg.REMITO_ENTREGA){
             this.tiene_devolucion = new TRemito().tieneDevolucion(remito);
         }
         Site s = mapSites.get(remito.getId_site());
         if(s!=null){
             this.site = s.getNombre();
             this.equipo =  s.getEquipo();
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
