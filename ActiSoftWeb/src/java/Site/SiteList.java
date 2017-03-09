/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Site;

import bd.Cliente;
import bd.Equipo;
import bd.Pozo;
import bd.Site;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCliente;
import transaccion.TEquipo;
import transaccion.TPozo;
import transaccion.TSite;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class SiteList extends HttpServlet {
    HashMap<Integer, Pozo> mapPozos;
    HashMap<Integer, Equipo> mapEquipos;
    
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
        Integer id_cliente = Parser.parseInt(request.getParameter("id_cliente"));
        Integer id_pozo   = Parser.parseInt(request.getParameter("id_pozo"));
        Integer id_equipo = Parser.parseInt(request.getParameter("id_equipo"));
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        
        mapPozos = new TPozo().getMap();
        mapEquipos = new TEquipo().getMap();
        HashMap<String,String> mapFiltro = new HashMap<String,String>();
        
        JsonRespuesta jr = new JsonRespuesta();
        try {
            List<Site> lista;
            TSite tr = new TSite();
            
            Cliente cliente = new TCliente().getById(id_cliente);
            if (id_cliente!=0 && cliente==null) throw new BaseException("ERROR","No se encontr&oacute; el cliente");
            if(id_cliente!=0)
                mapFiltro.put("id_cliente",id_cliente.toString());
            
            if(id_pozo!=0)
                mapFiltro.put("id_pozo",id_pozo.toString());

            if (id_equipo!=0)
                mapFiltro.put("id_equipo",id_equipo.toString());
            
            lista = tr.getListFiltro(mapFiltro);            
            if (lista != null) {
                jr.setTotalRecordCount(lista.size());
            } else {
                jr.setTotalRecordCount(0);
            }
            jr.setResult("OK");
            jr.setRecords(lista);
            

            
        } catch(BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
            
        }
        finally {
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }
    private class SiteDet extends Site {
        public SiteDet(Site site){
            super(site);
            Pozo p =  mapPozos.get(site.getId_pozo());
            Equipo e = mapEquipos.get(site.getId_equipo());
            if(p!=null) this.pozo = p.getNombre();
            if(e!=null) this.equipo = e.getNombre();
            
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
