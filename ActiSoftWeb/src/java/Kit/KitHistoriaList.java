/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import bd.Activo;
import bd.Kit_historia;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TKit_historia;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class KitHistoriaList extends HttpServlet {
    HashMap<Integer, Option> mapAcciones;
    TActivo ta;
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
        Integer id_kit = Parser.parseInt(request.getParameter("id_kit"));
        mapAcciones = OptionsCfg.getMapAcciones();
        ta = new TActivo();
        Integer page = 0;
        Integer id_rubro = 0;        
        JsonRespuesta jr = new JsonRespuesta();
        try{
             page = (pagNro!=null)?Integer.parseInt(pagNro):0;             
        } catch(NumberFormatException ex) {
            page = 0;
            id_rubro = 0;             
        }
        try {
           
           
            List<Kit_historia> lista ;
            
            TKit_historia tk = new TKit_historia();
            HashMap<String,String> mapFiltro = new HashMap<String,String> ();
//            mapFiltro.put("bloqueado","0");
            if(id_kit!=0){
               mapFiltro.put("id_kit",id_kit.toString());
            }  else throw new BaseException("ERROR","Ingrese el id del Kit");
            
            //else if(page!=0) lista = new TKit_contrato_view().getList(page,10);
             
            lista =  tk.getListFiltro(mapFiltro);
            
            List<Kit_historiaDet> listaDet = new ArrayList();
            for(Kit_historia h:lista){
                listaDet.add(new Kit_historiaDet(h));
            }
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);
        } catch (BaseException ex) {
            jr.setResult(ex.getMessage());
            jr.setMessage(ex.getMessage());
        } finally {   
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }
private class Kit_historiaDet extends Kit_historia{
   
     String accion = "";
     String activo = "";
     public Kit_historiaDet(Kit_historia historia){
         super(historia);                  
         
         Option o = mapAcciones.get(historia.getId_accion());
         this.accion = (o!=null)?o.getDescripcion():historia.getId_accion().toString();
         Activo a = ta.getById(historia.getId_activo());
         if(a!=null) this.activo = a.getCodigo();
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
