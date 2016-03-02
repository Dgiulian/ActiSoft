/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import bd.Kit;
import bd.Rubro;
import bd.Subrubro;
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
import transaccion.TKit;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class KitList extends HttpServlet {
    HashMap<Integer,Rubro> mapRubros;
     HashMap<Integer,Subrubro> mapSubrubros;
     HashMap<Integer,OptionsCfg.Option> mapEstados;
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
        String idRubro = request.getParameter("id_rubro");
        String idSubrubro = request.getParameter("id_subrubro");
        String codigo = request.getParameter("codigo");
        
        mapRubros = new TRubro().getMap();
        
        mapSubrubros = new TSubrubro().getMap();
        
        mapEstados = new HashMap<Integer,OptionsCfg.Option>();
        for (Iterator<OptionsCfg.Option> it = OptionsCfg.getEstadoActivo().iterator(); it.hasNext();) {
             OptionsCfg.Option o = it.next();
             mapEstados.put(o.getId(),o);
        }
        Integer page = 0;
        Integer id_rubro = 0;
        Integer id_subrubro = 0;
        Integer id_contrato = 0;
        
        try{
             page = (pagNro!=null)?Integer.parseInt(pagNro):0;
             id_rubro = (idRubro!=null)?Integer.parseInt(idRubro):0;
             id_subrubro = (idSubrubro!=null)?Integer.parseInt(idSubrubro):0;               
        } catch(NumberFormatException ex) {
            page = 0;
            id_rubro = 0; 
            id_subrubro = 0;
            id_contrato = 0;
        }
        try {
            JsonRespuesta jr = new JsonRespuesta();           
           
            List<Kit> lista ;
            
            TKit tk = new TKit();
            HashMap<String,String> mapFiltro = new HashMap<String,String> ();
//            mapFiltro.put("bloqueado","0");
            if(codigo!=null){
               mapFiltro.put("codigo",codigo);
            } else {
                if(id_subrubro !=0) { mapFiltro.put("id_subrubro",id_subrubro.toString());}            
                if (id_rubro!=0){ mapFiltro.put("id_rubro",id_rubro.toString());}
            }
            
            //else if(page!=0) lista = new TKit_contrato_view().getList(page,10);
             
            lista =  tk.getListFiltro(mapFiltro);
            
            List<KitDet> listaDet = new ArrayList();
            for(Kit kit:lista){
                listaDet.add(new KitDet(kit));
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

   private class KitDet extends Kit{
     String rubro = "";
     String cod_rubro = "";
     String cod_subrubro = "";
     String subrubro = "";
     String estado = "";
     public KitDet(Kit kit){
         super(kit);
         // Por default devolvemos el Id
         this.rubro = kit.getId_rubro().toString();         
         this.subrubro = kit.getId_subrubro().toString();

         this.estado = kit.getId_estado().toString();
         
//         this.fecha_creacion = TFecha.convertirFecha(this.fecha_creacion, TFecha.formatoBD + " " + TFecha.formatoHora, TFecha.formatoBD);
         Rubro r = mapRubros.get(kit.getId_rubro());
         if (r!=null){
             this.rubro = r.getDescripcion();
             this.cod_rubro = r.getCodigo();
         }
         Subrubro s = mapSubrubros.get(kit.getId_subrubro());       
         if (s!=null) {this.subrubro = s.getDescripcion(); this.cod_subrubro = s.getCodigo();};             
         Option o = mapEstados.get(kit.getId_estado());
         this.estado = (o!=null)?o.getDescripcion():kit.getId().toString();
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
