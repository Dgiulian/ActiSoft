/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import bd.Certificado;
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
import transaccion.TCertificado;
import transaccion.TKit;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class KitList extends HttpServlet {
    HashMap<Integer,Rubro> mapRubros;
    HashMap<Integer,Subrubro> mapSubrubros;
    HashMap<Integer,OptionsCfg.Option> mapEstados;
    HashMap<Integer, Certificado> mapValidos;
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
        Integer id_activo = Parser.parseInt(request.getParameter("id_activo"));
        mapRubros = new TRubro().getMap();
        mapValidos = new TCertificado().getMapValidos(OptionsCfg.MODULO_KIT);
        mapSubrubros = new TSubrubro().getMap();
        
        mapEstados = new HashMap<Integer,OptionsCfg.Option>();
        for (Iterator<OptionsCfg.Option> it = OptionsCfg.getEstadoKit().iterator(); it.hasNext();) {
             OptionsCfg.Option o = it.next();
             mapEstados.put(o.getId(),o);
        }
        Integer page = 0;
        
        
        JsonRespuesta jr = new JsonRespuesta();
      
        page = Parser.parseInt(pagNro);
        Integer id_rubro    = Parser.parseInt(idRubro);
        Integer id_subrubro = Parser.parseInt(idSubrubro);
        Integer id_estado = Parser.parseInt(request.getParameter("id_estado"));
        Integer activo = Parser.parseInt(request.getParameter("activo"));
        try {
           
           
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
            
            if(activo!=1) mapFiltro.put("activo","1");
            if(id_estado!=0) mapFiltro.put("id_estado",id_estado.toString());
            
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
            
           
        } finally {   
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
        }
    }

   private class KitDet extends Kit{
     String rubro = "";
     String cod_rubro = "";
     String cod_subrubro = "";
     String subrubro = "";
     String estado = "";
     String certificado = "";
     Integer id_certificado = 0;
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
         if(kit.getActivo()==1){
         Option o = mapEstados.get(kit.getId_estado());
         this.estado = (o!=null)?o.getDescripcion():kit.getId().toString();
        } else this.estado = "Eliminado";
        
        if (r!=null && r.getAplica_certificado()==0) {
             this.certificado = "No aplica";
         } else {
            Certificado cert = mapValidos.get(kit.getId());
            if(cert==null) this.certificado = "No";
            else { this.id_certificado = cert.getId();
                if(cert.getId_resultado()==OptionsCfg.CERTIFICADO_VENCIDO)  this.certificado = "Vencido";                            
                else certificado = "Si";
             }
        } 
         
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
