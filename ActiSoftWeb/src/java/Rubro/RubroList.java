/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rubro;

import bd.Rubro;
import bd.Subrubro;
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
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.JsonRespuesta;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class RubroList extends HttpServlet {
     HashMap<Integer,Rubro> mapRubros;
     HashMap<Integer,ArrayList<Subrubro>> mapSubrubros;
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
        String rubro_id = request.getParameter("id_rubro");
        
        
//        mapRubros = new HashMap<Integer,Rubro>();        
//        for (Rubro r : new TRubro().getList()) mapRubros.put(r.getId(),r);
            
        mapSubrubros = new HashMap<Integer,ArrayList<Subrubro>>();        
        HashMap<String,String> mapFiltroSR = new HashMap<String,String>();
        mapFiltroSR.put("id_estado","1");
        for (Subrubro s : new TSubrubro().getListFiltro(mapFiltroSR)) {
            ArrayList<Subrubro> lst = mapSubrubros.get(s.getId_rubro());
            if (lst==null) {
                lst = new ArrayList<Subrubro>();
                mapSubrubros.put(s.getId_rubro(),lst);
            }            
            lst.add(s);
        }
        
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        Integer id_rubro = Parser.parseInt(rubro_id);
        try {
            JsonRespuesta jr = new JsonRespuesta();           
            List<Rubro> lista;
            TRubro tr = new TRubro();
            HashMap<String,String> mapFiltro = new HashMap<String,String>();

            if(id_rubro!=0) mapFiltro.put("id", id_rubro.toString());
            
            mapFiltro.put("id_estado", "1");
            
            lista = tr.getListFiltro(mapFiltro);
            
            List<RubroDet> listaDet = new ArrayList();
            for(Rubro rubro:lista){
                listaDet.add(new RubroDet(rubro));
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
    private class RubroDet extends Rubro{
        private List<Subrubro> subrubro;
        public RubroDet(Rubro rubro){
            super(rubro);
            this.subrubro = mapSubrubros.get(rubro.getId());
            if(this.subrubro==null) this.subrubro = new ArrayList();
        }
    }
    
}
