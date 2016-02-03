/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Remito;
import bd.Remito_detalle;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class RemitoDel extends HttpServlet {

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
        
        Remito remito;       
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonRespuesta jr = new JsonRespuesta();
        try {  
           HttpSession session = request.getSession();
           Integer id_usuario = (Integer) session.getAttribute("id_usuario");
           Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario"); 
           if(id_tipo_usuario <1) throw new BaseException("Usuario no habilitado","El usuario no est&aacute; habilitado para eliminar remitos");
           
           Integer id = Parser.parseInt(request.getParameter("id"));
//           if(id!=0) throw new BaseException("Usuario no habilitado","El usuario no est&aacute; habilitado para eliminar remitos");
           TRemito tr = new TRemito();
           remito = tr.getById(id);
           if (remito==null) throw new BaseException("ERROR","No existe el registro");
           
           if(tr.existeReferencia(remito)) throw new BaseException("ERROR","Existe un remito de referencia para este remito. No puede eliminado");
           
           boolean baja = new TRemito().eliminar(remito);
           if ( baja){               
               jr.setResult("OK");
               TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_REMITO,OptionsCfg.ACCION_BAJA,remito.getId());
           } else throw new BaseException("ERROR","Ocurrio un error al eliminar el registro");                     
        } catch (BaseException ex) {
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());            
        }
        finally {
            out.print(new Gson().toJson(jr));
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
