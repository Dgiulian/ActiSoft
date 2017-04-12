/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Certificado;

import bd.Certificado;
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
import transaccion.TCertificado;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.OptionsCfg.Option;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class CertificadoList extends HttpServlet {
    HashMap<Integer,Option> mapResultados ;
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
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String idActivo = request.getParameter("id_objeto");
        Integer  id_modulo = Parser.parseInt(request.getParameter("id_modulo"));
        Integer id_objeto = Parser.parseInt(request.getParameter("id_objeto"));
        String pagNro = request.getParameter("pagNro");
        mapResultados = new HashMap<Integer,Option>();        
        for (Option o : OptionsCfg.getEstadoCertificados()) mapResultados.put(o.getId(),o);

        HashMap<String,String> mapFiltro = new HashMap<String,String>();
        Integer page = (pagNro!=null)?Integer.parseInt(pagNro):0;
        
        JsonRespuesta jr = new JsonRespuesta();
        try {
            List<Certificado> lista;
            TCertificado tr = new TCertificado();
            tr.setOrderBy("fecha desc ");
            if(id_objeto==0) throw new BaseException("ERROR", "Debe seleccionar el kit o activo");
            mapFiltro.put("id_objeto",id_objeto.toString());
            mapFiltro.put("id_modulo",id_modulo.toString());
            lista = tr.getListFiltro(mapFiltro);
            ArrayList<CertificadoDet> listaDet = new ArrayList<CertificadoDet>();
            for(Certificado c: lista){
                listaDet.add(new CertificadoDet(c));
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
    private class CertificadoDet extends Certificado{
        String resultado = "";
        public CertificadoDet(Certificado certificado){
            super(certificado);
            this.resultado = certificado.getId_resultado().toString();
            Option o = mapResultados.get(certificado.getId_resultado());
            if (o!=null)
                this.resultado = o.getDescripcion();
        }
        
    }
}
