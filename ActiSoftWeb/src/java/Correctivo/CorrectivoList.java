/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Correctivo;

import Correctivo.CorrectivoList;
import bd.Correctivo;
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
import transaccion.TCorrectivo;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class CorrectivoList extends HttpServlet {

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
        String idActivo = request.getParameter("id_activo");
        
        
       
        HashMap<String,String> mapFiltro = new HashMap<String,String>();
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        Integer id_activo = (idActivo!=null)?Integer.parseInt(idActivo):0;
        
        JsonRespuesta jr = new JsonRespuesta();
        try {
            List<Correctivo> lista;
            TCorrectivo tr = new TCorrectivo();
            if(id_activo==0) throw new BaseException("ERROR", "Debe seleccionar el activo");
            mapFiltro.put("id_activo",id_activo.toString());
            lista = tr.getListFiltro(mapFiltro);
            ArrayList<CorrectivoDet> listaDet = new ArrayList<CorrectivoDet>();
            for(Correctivo c: lista){
                listaDet.add(new CorrectivoDet(c));
            }
            if (lista != null) {
                jr.setTotalRecordCount(listaDet.size());
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecords(listaDet);            
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
     private class CorrectivoDet extends Correctivo{
        String actividad = "";
        String resultado = "";
        public CorrectivoDet(Correctivo correctivo){
            super(correctivo);            
        }
        
    }
}
