/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Activo;
import bd.Remito;
import bd.Remito_detalle;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class RemitoDiario extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RemitoDiario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemitoDiario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
       final Integer RUBRO_TRANSPORTE = 14;
       
       response.setContentType("application/json;charset=UTF-8");
       PrintWriter out = response.getWriter();
       JsonRespuesta jr = new JsonRespuesta();
       String jsonResult = new Gson().toJson(jr);
       
       Remito remito = null;
       HttpSession session = request.getSession();
       Integer id_usuario = (Integer) session.getAttribute("id_usuario");
       Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
       
       String numero  = request.getParameter("numero");       
       String fecha = request.getParameter("fecha"); 
       String observaciones = request.getParameter("observaciones"); 
       try{
           TRemito tr = new TRemito();
           TRemito_detalle trd = new TRemito_detalle();
           TActivo ta = new TActivo();
           
           if(request.getParameter("id")==null)  throw new BaseException("Remito inexistente","Debe seleccionar el remito a devolver");
           
           Integer id = Parser.parseInt(request.getParameter("id"));
           remito = tr.getById(id);            
           if (remito==null)  
               throw new BaseException("ERROR","No se encontro el remito");
           if(remito.getId_estado()!= OptionsCfg.REMITO_ESTADO_ABIERTO)
                throw new BaseException("ERROR","El remito no est&aacute; abierto. No se puede crear un remito de devoluci&oacute;n");
           if(Parser.parseInt(numero)==0) throw new BaseException("ERROR","Indique un n&uacute;mero de remito");
           
           if(tr.getByNumero(Parser.parseInt(numero))!=null) 
                 throw new BaseException("ERROR","Ya existe un remito con ese n&uacute;mero");
           //TODO -  Buscar si existe un remito Diario para ese mismo remito en la misma fecha
           
           Map<String,String> mapFiltro = new HashMap();
           mapFiltro.put("id",remito.getId().toString());
           mapFiltro.put("fecha",TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
           //mapFiltro.put("id_tipo_remito",OptionsCfg.REMITO_DIARIO.toString());
           if(tr.getListFiltro(mapFiltro).size()>0){
               throw new BaseException("ERROR","Ya existe un remito diario para esa fecha");
           }
           List<Remito_detalle> lstDetalle = trd.getByRemitoId(remito.getId());
           
           Remito diario = new Remito(remito);
           diario.setId(0);
           diario.setNumero(Parser.parseInt(numero));
           diario.setId_referencia(remito.getId());
           diario.setId_tipo_remito(OptionsCfg.REMITO_DIARIO);
           diario.setId_estado(OptionsCfg.REMITO_ESTADO_CERRADO);
           diario.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
           diario.setFecha_creacion(TFecha.ahora(TFecha.formatoBD));
           diario.setId_usuario(id_usuario);
//           diario.setObservaciones(remito.getObservaciones() + " " + observaciones);
           diario.setObservaciones( observaciones);
           
           Integer id_remito_diario = tr.alta(diario);
           if (id_remito_diario == 0) throw new BaseException("Error", "Ocurrió un error al crear el remito de devoluci&oacute;n");
           diario.setId(id_remito_diario);
           for(Remito_detalle rd:lstDetalle){
                Activo activo = ta.getById(rd.getId_activo());
                if (activo.getId_rubro()== RUBRO_TRANSPORTE ){
                    continue;
                }
                Remito_detalle det_dia = new Remito_detalle(rd);
                det_dia.setId_remito(diario.getId());
                det_dia.setId(0);
                trd.alta(det_dia);                
            }

           jr.setResult("OK");
           jr.setRecord(diario);
           
       } catch( BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } finally {            
            out.print(new Gson().toJson(jr));
            out.close();
        }
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
