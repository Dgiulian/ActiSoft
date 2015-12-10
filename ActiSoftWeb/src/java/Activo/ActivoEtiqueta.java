/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activo;

import bd.Activo;
import bd.Parametro;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TActivo;
import transaccion.TParametro;
import utils.EtiquetasPdf;
import utils.OptionsCfg;
import utils.PathCfg;

/**
 *
 * @author Diego
 */
public class ActivoEtiqueta extends HttpServlet {
 
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
        request.getRequestDispatcher("etiqueta.jsp").forward(request, response);
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
        String[] codigoActivos = request.getParameterValues("codigoActivo");
        TActivo ta = new TActivo();
        Parametro parametro = new TParametro().getByNumero(OptionsCfg.ACTIVO_PATH);
        List<Activo> lstActivos = new ArrayList<Activo>();
        
        for(String codigo : codigoActivos) {
            Activo a = ta.getByCodigo(codigo);
            if(a!=null){
                lstActivos.add(a);
            }
        }
        String fileName = String.format("etiquetas.pdf");
            String filePath = parametro.getValor() + File.separator + fileName;
        
            boolean generado = new EtiquetasPdf(lstActivos).createPdf(filePath);
            if (generado) {
                //remitoPdf.imprimir(fileName);             
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
//                System.out.println("MIME type: " + mimeType);

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
        response.sendRedirect(PathCfg.ACTIVO);
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
