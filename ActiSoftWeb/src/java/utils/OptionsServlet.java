/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import bd.Rubro;
import bd.Subrubro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TRubro;
import transaccion.TSubrubro;


/**
 *
 * @author Diego
 */
//@WebServlet(name="OptionsServlet",urlPatterns={PathCfg.OPTIONS}) 
public class OptionsServlet extends HttpServlet {

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
        JsonRespuesta jr = new JsonRespuesta();
        List<Opcion> lstOpciones = new ArrayList();
        String type =  request.getParameter("type");        
        if (type.equalsIgnoreCase("rubro")){
            lstOpciones = getListRubro();
        } else if (type.equalsIgnoreCase("subrubro")){{
            Integer id_rubro;
            try{
                id_rubro = Integer.parseInt(request.getParameter("id_rubro"));
            } catch(NumberFormatException ex){
                id_rubro = 0;
            }
            lstOpciones = getListSubrubro(id_rubro);
        }
        }
        try {            
          if(lstOpciones!=null) {
            jr.setResult("OK");
            jr.setOptions(lstOpciones);
          } else {
              jr.setResult("OK");
          }
           out.println(new Gson().toJson(jr));
        } finally {            
            out.close();
        }
    }
    private List<Opcion> getListSubrubro(Integer id_rubro){
        List<Opcion> lstOpciones = null;
        List<Subrubro> list;
        if (id_rubro != 0)       list = new TSubrubro().getList();
        else list  = new TSubrubro().getByRubroId(id_rubro);
        if (list!=null){
          lstOpciones = new ArrayList();              
          for (Subrubro s:list){
              lstOpciones.add(new Opcion(s.getId(),s.getDescripcion()));
          }              
        } 
        return lstOpciones;
    }
    private List<Opcion> getListRubro(){
        List<Opcion> lstOpciones = null;
        List<Rubro> list = new TRubro().getList();
            
        if (list!=null){
          lstOpciones = new ArrayList();              
          for (Rubro r:list){
              lstOpciones.add(new Opcion(r.getId(),r.getDescripcion()));
          }              
        } 
        return lstOpciones;
    }
//    private List<Opcion> getListEspecialidades(){
        List<Opcion> lstOpciones = null;
//        List<Especialidad> list = new TEspecialidad().getList();
//            
//        if (list!=null){
//          lstOpciones = new ArrayList();              
//          for (Especialidad esp:list){
//              lstOpciones.add(new Opcion(esp.getEspec_id(),esp.getEspec_detalle()));                  
//          }              
//        } 
//        return lstOpciones;
//    }
        
    private List<Opcion> getListTipoDoc() {
        List<Opcion> lstOpciones = new ArrayList();
        lstOpciones.add(new Opcion(0,""));
        lstOpciones.add(new Opcion(1,"DNI"));
        lstOpciones.add(new Opcion(2,"CI"));
        lstOpciones.add(new Opcion(3,"LE"));
        lstOpciones.add(new Opcion(4,"CUIL"));      
        return lstOpciones;        
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

  

    
    
    private class Opcion{
        private Opcion(int id,String disp){
            this.Value = "" + id;
            this.DisplayText = disp;
        }
        String DisplayText;
        String Value;
    }
}

