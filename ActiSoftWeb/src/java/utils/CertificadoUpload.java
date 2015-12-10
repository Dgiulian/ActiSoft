/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Diego
 */
public class CertificadoUpload extends HttpServlet {
 //private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 2;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("uploadcv.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

      
        Respuesta r = new Respuesta();


        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String ext = FilenameUtils.getExtension(fileName);
                    //Filtrar por extension! Solo permitir PDF y DOC
                    if(!(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("docx"))){
                        r.setStatus("ERROR");
                        r.setMensaje("Solo se aceptaran CV en formato doc, docx o pdf");
                    }else{
                        String newName = String.format("cv_%d.%s", 0,ext);


                        String filePath = uploadFolder + File.separator + newName;
                        File uploadedFile = new File(filePath);
                        // saves the file to upload directory

                        item.write(uploadedFile);

                        r.setStatus("Ok");
                        r.setMensaje("El archivo fue subido correctamente");
                        r.setURL(newName);

                    }
                }
            }
        } catch(FileUploadBase.FileSizeLimitExceededException ex){
            r.setStatus("ERROR");
            r.setMensaje("El tama&ntilde;o de archivo supera el maximo permitido");
        }
        catch (FileUploadBase.SizeLimitExceededException ex){
            r.setStatus("ERROR");
            r.setMensaje("El tama&ntilde;o de archivo supera el maximo permitido");
        }
        catch (FileUploadException ex) {
            r.setStatus("ERROR");
            r.setMensaje("Ha ocurrido un error al intentar subir el archivo");
            ex.printStackTrace(System.out);

            //System.out.println(printStackTrace());
            //throw new ServletException(ex);
        }
        catch (Exception ex) {
            throw new ServletException(ex);
        }
        finally{
            try{
                Gson gs = new Gson();
                String toJson = gs.toJson(r);
                out.print(toJson);
            } finally{
                    out.close();
            }
        }

    }

    private static class Respuesta {
        String status;
        String mensaje;
        String fecha;
        String url;
        public Respuesta() {
        }
        public void setStatus(String status){
            this.status = status;
        }
        public void setMensaje(String mensaje){
            this.mensaje = mensaje;
        }

        private void setFecha(String fecha) {
            this.fecha = fecha;
        }

        private void setURL(String url) {
            this.url = url;
        }
    }
}
