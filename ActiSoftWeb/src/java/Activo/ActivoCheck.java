/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activo;

import bd.Activo;
import bd.Certificado;
import bd.Kit;
import bd.Rubro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TCertificado;
import transaccion.TKit;
import transaccion.TRubro;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class ActivoCheck extends HttpServlet {

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
        Integer id_activo = Parser.parseInt(request.getParameter("id_activo"));
        
        String codigo = request.getParameter("codigo");
        Activo activo = null;
        Kit kit = null;
        TRubro tr;
        TActivo ta = new TActivo();
        TCertificado tc = new TCertificado();
        JsonRespuesta jr = new JsonRespuesta();    
        
        try {
            if(id_activo == 0 && codigo ==null) throw new BaseException("ERROR","No se defini&oacute; ning&uacute;n par&aacute;metro de busqueda");
            
            if(id_activo!=0)
                activo = ta.getById(id_activo);
            else if(codigo!=null ) activo = ta.getByCodigo(codigo);
            
            if(activo==null){ 
                kit = new TKit().getByCodigo(codigo);
                if(kit==null)
                    throw new BaseException("ERROR","No se encontr&oacute; el activo o el kit");
            } else {
         
                tr = new TRubro();
                Rubro r = tr.getById(activo.getId_rubro());
                if (r.getAplica_certificado()!=0){

                    Certificado vigente = tc.getVigente(OptionsCfg.MODULO_ACTIVO,activo.getId()); //Buscamos el certificado vigente a la fecha
                    if(vigente == null || vigente.getId_resultado()!= OptionsCfg.CERTIFICADO_APTO) throw new BaseException("ERROR","El activo " + activo.getCodigo() + " no tiene un certificado v&aacute;lido");

                }
            }
            if (activo != null || kit!=null) {
                jr.setTotalRecordCount(1);
                if(activo!=null) jr.setRecord(activo);
                else if(kit !=null) jr.setRecord(kit);
            } else {
                jr.setTotalRecordCount(0);
            }

            jr.setResult("OK");
            
        } catch (BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        }finally { 
            String jsonResult = new Gson().toJson(jr);
            out.print(jsonResult);
            out.close();
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
