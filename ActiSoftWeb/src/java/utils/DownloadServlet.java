/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Certificado;
import bd.Habilitacion;
import bd.Parametro;
import bd.Remito;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCertificado;
import transaccion.THabilitacion;
import transaccion.TParametro;
import transaccion.TRemito;

/**
 *
 * @author Diego
 */
public class DownloadServlet extends HttpServlet {

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
            out.println("<title>Servlet DownloadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadServlet at " + request.getContextPath() + "</h1>");
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
        try {            
            
            String tipo = request.getParameter("type");
            String filePath = "";
            Parametro parametro;
            TParametro tp = new TParametro();
            
            if (tipo==null|| tipo.equals(""))
                throw new BaseException("Error","Debe seleccionar el tipo de documento a descargar");
            
            if (tipo.equalsIgnoreCase("certificado")) {
                parametro = tp.getByCodigo(OptionsCfg.CERTIFICADO_PATH);
                String idCertif = request.getParameter("id");
                Integer id_certif = (idCertif!=null && !idCertif.equals(""))?Integer.parseInt(idCertif):0;
                Certificado certificado = new TCertificado().getById(id_certif);
                if(certificado == null) throw new BaseException("ERROR","No se encontr&oacute; el certificado");
                
                String fileName = certificado.getArchivo().trim();
                filePath = parametro.getValor() + File.separator + fileName; 
                        
                if (filePath==null || filePath.equals("")) throw new BaseException("ERROR", "El certificado no tiene ning&uacute;n archivo asociado");
                
                //filePath = "c:\\Users\\Diego\\Documents\\NetBeansProjects\\ActiSoft\\ActiSoftWeb\\data\\HaxLogs.log";
            }else if(tipo.equalsIgnoreCase("remito")){                
                Integer id_remito = Parser.parseInt(request.getParameter("id"));
                Remito remito = new TRemito().getById(id_remito);
                if(remito == null) throw new BaseException("ERROR","No se encontr&oacute; el remito");
                
                String fileName = remito.getArchivo_url().trim();
                filePath = fileName;
//                filePath = parametro.getValor() + File.separator + fileName; 
                        
                if (filePath==null || filePath.equals("")) throw new BaseException("ERROR", "El remito no tiene ning&uacute;n archivo asociado");
            } else if(tipo.equalsIgnoreCase("habilitacion")){
                Integer id_habilitacion = Parser.parseInt(request.getParameter("id"));
                Habilitacion habilitacion = new THabilitacion().getById(id_habilitacion);
                if(habilitacion==null) throw new BaseException("ERROR", "No se encontr&oacute; la habilitaci&oacute;n");
                parametro = tp.getByCodigo(OptionsCfg.HABILITACION_PATH);
                if(parametro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del sistema");   
                filePath = parametro.getValor() + File.separator + habilitacion.getArchivo();
            }
            else   throw new BaseException("Error","El tipo de documento a descargar no es v&aacute;lido");
            
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
//            System.out.println("MIME type: " + mimeType);

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
        } catch (BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
            
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
