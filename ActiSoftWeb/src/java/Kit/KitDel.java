/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import bd.Activo;
import bd.Kit;
import bd.Kit_detalle;
import bd.Kit_historia;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TKit;
import transaccion.TKit_detalle;
import transaccion.TKit_historia;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class KitDel extends HttpServlet {

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
       response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonRespuesta jr = new JsonRespuesta();
        try {           
           Integer id = Parser.parseInt(request.getParameter("id"));
           Kit kit = new TKit().getById(id);            
           if (kit==null) throw new BaseException("ERROR","No existe el registro");
           
           if(OptionsCfg.KIT_ESTADO_ALQUILADO.equals(kit.getId_estado())){
               throw new BaseException("ERROR","El kit se encuentra alquilado y no puede ser eliminado");
           }
           kit.setActivo(0);
           boolean baja = new TKit().actualizar(kit);           
           if ( baja){
               TKit_detalle tkd =  new TKit_detalle();
               List<Kit_detalle> lstDetalle =tkd.getByKitId(kit.getId());
               TActivo ta = new TActivo();
               TKit_historia th = new TKit_historia();
               for(Kit_detalle d:lstDetalle){
                    // Cuando se elimina un kit, hay que devolver el stock del activo.
                   Activo activo = ta.getById(d.getId_activo());
                   if(activo==null) continue;
                   if(!activo.getId_estado().equals(OptionsCfg.ACTIVO_ESTADO_KIT)) continue;
                   
                   activo.setStock(activo.getStock() + 1);
                   activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                   ta.actualizar(activo);
                    
                   Kit_historia historia = new Kit_historia();
                   historia.setId_activo(d.getId_activo());
                   historia.setId_accion(OptionsCfg.ACCION_BAJA);
                   historia.setFecha(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
                   th.alta(historia);                   
                   //tkd.baja(d);
                   /* No se borra el detalle, ya que es un borrado lógico */
               }
               jr.setResult("OK");
                Integer id_usuario = 0;
                Integer id_tipo_usuario = 0;
                HttpSession session = request.getSession();
                id_usuario = (Integer) session.getAttribute("id_usuario");
                id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
                TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_KIT,OptionsCfg.ACCION_BAJA,kit.getId());
           } else throw new BaseException("ERROR","Ocurrio un error al eliminar el registro");                     
        }  catch (BaseException ex) {
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());            
        }
        finally {
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
