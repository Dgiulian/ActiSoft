/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vehiculo;


import Excel.VehiculoExcel;
import bd.Parametro;
import bd.Proveedor;
import bd.Vehiculo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TParametro;
import transaccion.TProveedor;
import transaccion.TVehiculo;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class VehiculoExport extends HttpServlet {

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
       JsonRespuesta jr = new JsonRespuesta();
        try{
            
            Integer id_proveedor = Parser.parseInt(request.getParameter("id_proveedor"));
            Integer id_reporte   = Parser.parseInt(request.getParameter("id_reporte"));            
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            
            Proveedor proveedor = new TProveedor().getById(id_proveedor);
            if (proveedor==null) throw new BaseException("ERROR","No se encontr&oacute; el proveedor");            
            
            mapFiltro.put("id_proveedor",id_proveedor.toString());           
            List<Vehiculo> lista = new TVehiculo().getListFiltro(mapFiltro);
            if (lista==null) throw new BaseException("ERROR","Ocurrió un error al recuperar los vehiculos");
            Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);
            
            boolean generado = false;
            
            String fileName  = id_reporte==1?"vehiculos.pdf":"X-FG-13 documentacion vehiculos alquilados.xlsx";
            
            String filePath = parametro.getValor() + File.separator + fileName;
            
            if(id_reporte==1){
//                VehiculoPdf pdf = new VehiculoPdf(lista,filtros);
//                generado = pdf.createPdf(filePath);
            }else {
                VehiculoExcel excel = new VehiculoExcel("Listado de Vehiculos",proveedor,lista);
                generado = excel.createExcel(filePath);
            }
            System.out.println(generado);
            if (generado) {
                
                // reads input file from an absolute path        
                File downloadFile = new File(filePath);
                FileInputStream inStream = new FileInputStream(downloadFile);

                // if you want to use a relative path to context root:
                String relativePath = getServletContext().getRealPath("");
                System.out.println("relativePath = " + relativePath);

                // obtains ServletContext
                ServletContext context = getServletContext();

                // gets MIME type of the file
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    // set to binary type if MIME mapping not found
                    mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);

                // modifies response
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());

                // forces download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                response.setHeader(headerKey, headerValue);

                // obtains response's output stream
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                outStream.close();// obtains response's output stream

            }    
            
            
            
        } catch(BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } finally{
//            out.println(new Gson().toJson(jr));
//            out.close();
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
