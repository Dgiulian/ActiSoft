/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import Kit.*;
import bd.Kit_contrato_view;
import bd.Subrubro;
import bd.Rubro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TKit_contrato_view;
import transaccion.TSubrubro;
import transaccion.TRubro;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;

/**
 *
 * @author Diego
 */
public class KitContratoList extends HttpServlet {

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
     
     HashMap<Integer,Rubro> mapRubros;
     HashMap<Integer,Subrubro> mapSubrubros;
     HashMap<Integer,Option> mapEstados;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagNro = request.getParameter("pagNro");
        String idRubro = request.getParameter("id_rubro");
        String idSubrubro = request.getParameter("id_subrubro");
        String idContrato = request.getParameter("id_contrato");
        
        mapRubros = new TRubro().getMap();
        mapSubrubros = new TSubrubro().getMap();
        
       
        Integer page = 0;
        Integer id_rubro = 0;
        Integer id_subrubro = 0;
        Integer id_contrato = 0;
        try{
             page = (pagNro!=null)?Integer.parseInt(pagNro):0;
             id_rubro = (idRubro!=null)?Integer.parseInt(idRubro):0;
             id_subrubro = (idSubrubro!=null)?Integer.parseInt(idSubrubro):0;  
             id_contrato = (idContrato!=null)?Integer.parseInt(idContrato):0;
        } catch(NumberFormatException ex) {
            page = 0;
            id_rubro = 0; 
            id_subrubro = 0;
            id_contrato = 0;
        }
        try {
            JsonRespuesta jr = new JsonRespuesta();           
           
            List<Kit_contrato_view> lista ;
            
            TKit_contrato_view ta = new TKit_contrato_view();
            HashMap<String,String> mapFiltro = new HashMap<String,String> ();
            
            if(id_subrubro !=0) { mapFiltro.put("id_subrubro",id_subrubro.toString());}            
            if (id_rubro!=0) { mapFiltro.put("id_rubro",id_rubro.toString());}
            if (id_contrato!=0){ mapFiltro.put("c_id_contrato",id_contrato.toString());}
            //else if(page!=0) lista = new TKit_contrato_view().getList(page,10);
            mapFiltro.put("activo", "1");
            lista =  ta.getListFiltro(mapFiltro);
            
            List<KitDet> listaDet = new ArrayList();
            for(Kit_contrato_view activo:lista){               
                listaDet.add(new KitDet(activo));
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
 private class KitDet extends Kit_contrato_view{
     String rubro = "";
     String cod_rubro = "";
     String cod_subrubro = "";
     String subrubro = "";
     public KitDet(Kit_contrato_view activo){
         super(activo);
         // Por default devolvemos el Id
         this.rubro = activo.getId_rubro().toString();         
         this.subrubro = activo.getId_subrubro().toString();
         
         Rubro r = mapRubros.get(activo.getId_rubro());
         if (r!=null){
             this.rubro = r.getDescripcion();
             this.cod_rubro = r.getCodigo();
         }
         Subrubro s = mapSubrubros.get(activo.getId_subrubro());       
         if (s!=null) {this.subrubro = s.getDescripcion(); this.cod_subrubro = s.getCodigo();};             
         
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
