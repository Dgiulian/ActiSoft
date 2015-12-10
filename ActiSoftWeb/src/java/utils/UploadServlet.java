package utils;

import com.google.gson.Gson;
import conexion.TransaccionRS;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import utils.TFecha;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 2;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("titulo", "Error");
        request.setAttribute("mensaje","Esta pagina no deber&iacute;a ser accedida");
        request.getRequestDispatcher("message.jsp").forward(request, response);        
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();

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
    JsonRespuesta jr = new JsonRespuesta();
        
    try{
        try { // Parse the request
            String tipoObjeto = "";
            String key = "";
            String keyvalue = "";
            List<FileItem> items = upload.parseRequest(request);
            List<FileItem> archivos = new ArrayList();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldname = item.getFieldName();
                    String fieldvalue = item.getString();
                    if (fieldname.equalsIgnoreCase("objeto")) {
                        tipoObjeto = fieldvalue;
                    }else if (fieldname.equalsIgnoreCase("key")) {
                        key = fieldvalue;
                    }else if (fieldname.equalsIgnoreCase("keyvalue")) {
                        keyvalue = fieldvalue;
                    }
                } else{
                   //Filtrar por extension! Solo permitir PDF y DOC
//                    if(!(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("docx")))
//                        throw new BaseException("ERROR","Solo se aceptaran CV en formato doc, docx o pdf");
                        archivos.add(item);
                }
            }
            TransaccionRS tr = new TransaccionRS();
            Object instancia = tr.generarInstancia(tipoObjeto);
            
            for(FileItem item:archivos) {
                String fileName = new File(item.getName()).getName();
                String ext = FilenameUtils.getExtension(fileName);                                            
                String newName = String.format("cv_%d.%s", 0,ext);
                String filePath = uploadFolder + File.separator + newName;
                File uploadedFile = new File(filePath);
                // saves the file to upload directory
                
                item.write(uploadedFile);
            }
            jr.setResult("OK");
            jr.setMessage("El archivo fue subido correctamente");
            jr.setRecord("");
        } catch(FileSizeLimitExceededException ex){
            throw new BaseException("ERROR", "El tama&ntilde;o de archivo supera el maximo permitido");
        }
        catch (SizeLimitExceededException ex){
            throw new BaseException("ERROR","El tama&ntilde;o de archivo supera el maximo permitido");
        }
        catch (FileUploadException ex) {
            throw new BaseException("ERROR","Ha ocurrido un error al intentar subir el archivo");            
        }
        catch (Exception ex) {                    
            throw new ServletException(ex);
        }
    } catch(BaseException ex) {
        jr.setResult(ex.getResult());
        jr.setMessage(ex.getMessage());
    } finally{
            Gson gs = new Gson();
            String toJson = gs.toJson(jr);
            out.print(toJson);            
            out.close();            
        }
   
    }
}

