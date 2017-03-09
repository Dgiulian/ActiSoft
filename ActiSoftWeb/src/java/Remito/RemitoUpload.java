/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remito;

import bd.Parametro;
import bd.Remito;
import com.google.gson.Gson;
import conexion.TransaccionRS;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import transaccion.TParametro;
import transaccion.TRemito;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class RemitoUpload extends HttpServlet {

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

    // Sets the directory used to temporarily store files that are larger
    // than the configured size threshold. We use temporary directory for
    // java
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    Parametro remito_path = new TParametro().getByCodigo(OptionsCfg.REMITO_PATH);
    // constructs the folder where uploaded file will be stored
    String uploadFolder = null;
    if (remito_path!=null) uploadFolder = remito_path.getValor();
    else uploadFolder = getServletContext().getRealPath("") + File.separator + "data";
    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    
    JsonRespuesta jr = new JsonRespuesta();
        
    try{
        try { // Parse the request
            String idRemito = null;
            String key = "";
            String keyvalue = "";
            Integer id_remito = 0;
            List<FileItem> items = upload.parseRequest(request);
            List<FileItem> archivos = new ArrayList();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldname = item.getFieldName();
                    String fieldvalue = item.getString();
                    if (fieldname.equalsIgnoreCase("id")) {                        
                        try{
                            id_remito = Integer.parseInt(fieldvalue);
                        } catch(NumberFormatException ex){
                            id_remito = 0;
                        }
                    } else if (fieldname.equalsIgnoreCase("key")) {
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
            TRemito tr = new TRemito();
            Remito remito = tr.getById(id_remito);
            if(remito==null) throw new BaseException("ERROR","No se encontr&oacute; el remito");
            
            for(FileItem item:archivos) {
                String fileName = new File(item.getName()).getName();
                String ext = FilenameUtils.getExtension(fileName);                                            
                String newName = String.format("CM_R%d.%s", remito.getNumero(),ext);
                String filePath = uploadFolder + File.separator + newName;
                File uploadedFile = new File(filePath);
                // saves the file to upload directory                
                item.write(uploadedFile);
                remito.setArchivo(fileName);
                remito.setArchivo_url(filePath);
                tr.actualizar(remito);
            }
            jr.setResult("OK");
            jr.setMessage("El archivo fue subido correctamente");
            jr.setRecord("");
        } catch(FileUploadBase.FileSizeLimitExceededException ex){
            throw new BaseException("ERROR", "El tama&ntilde;o de archivo supera el maximo permitido");
        }
        catch (FileUploadBase.SizeLimitExceededException ex){
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
