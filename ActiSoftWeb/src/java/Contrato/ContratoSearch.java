/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contrato;

import bd.Contrato;
import bd.Contrato_detalle;
import bd.Contrato_detalle_view;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TContrato_detalle_view;
import utils.BaseException;
import utils.JsonRespuesta;

/**
 *
 * @author Diego
 */
public class ContratoSearch extends HttpServlet {

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

        JsonRespuesta jr = new JsonRespuesta();
        try {
            if(request.getParameter("id")==null &&
               request.getParameter("numero")==null ) throw new BaseException("ERROR","No se definió ningún parámetro de busqueda");
            
            Contrato contrato = null;
            if(request.getParameter("id")!=null){
                Integer id = Integer.parseInt(request.getParameter("id"));
                contrato = new TContrato().getById(id);
            }else if(request.getParameter("numero")!=null) {
                String numero = request.getParameter("numero");
                contrato = new TContrato().getByNumero(request.getParameter("numero"));
            }
            
            if (contrato != null) {
                jr.setTotalRecordCount(1);
            } else {
                jr.setTotalRecordCount(0);
            }            
            jr.setResult("OK");
            jr.setRecord(new ContratoDet(contrato));
            
        } catch (BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } catch(NumberFormatException ex){
            jr.setResult("ERROR");
            jr.setMessage("Error al parsear los parametros");
        } finally {       
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
    private class ContratoDet extends Contrato{
        public List<Contrato_detalle_view> detalle;
        public ContratoDet(Contrato contrato){
            super(contrato);
            detalle = new TContrato_detalle_view().getByContratoId(contrato.getId());
        }
    }
    
}
