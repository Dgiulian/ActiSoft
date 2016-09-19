/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Habilitacion;

import bd.Habilitacion;
import bd.Parametro;
import bd.Responsable;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import transaccion.THabilitacion;
import transaccion.TParametro;
import transaccion.TResponsable;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class HabilitacionEdit extends HttpServlet {

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
            out.println("<title>Servlet HabilitacionEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HabilitacionEdit at " + request.getContextPath() + "</h1>");
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
    private static final String DATA_DIRECTORY  = "data";
    private static final int   MAX_MEMORY_SIZE  = 1024 * 1024 * 2;
    private static final int   MAX_REQUEST_SIZE = 1024 * 1024 * 2;
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
        Integer id               = 0;
        Integer id_responsable   = 0;
        Integer id_proveedor     = 0;
        String fecha             = "";        
        String archivo           = "";
        String modo              = "json";
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        FileItem archivo1 = null;
        try{
            if (!isMultipart) 
                throw new BaseException("ERROR","El formulario no es multiparte. <br> No se pueden subir archivos");
        
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Sets the size threshold beyond which files are written directly to disk.
            factory.setSizeThreshold(MAX_MEMORY_SIZE);

            // Sets the directory used to temporarily store files that are larger
            // than the configured size threshold. We use temporary directory for
            // java
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));            
            Parametro habilitacion_path = new TParametro().getByCodigo(OptionsCfg.HABILITACION_PATH);

            // constructs the folder where uploaded file will be stored
            String uploadFolder = null;
            if (habilitacion_path!=null) uploadFolder = habilitacion_path.getValor();
            else uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;

            System.out.println(uploadFolder);
            System.out.println(System.getProperty("java.io.tmpdir"));
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Set overall request size constraint
            upload.setSizeMax(MAX_REQUEST_SIZE);            
                                
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {
                    String fieldName = item.getFieldName();
                    if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).                        
                        String fieldValue = item.getString();
                         if(fieldName.equalsIgnoreCase("modo"))           modo           = fieldValue;
                         if(fieldName.equalsIgnoreCase("id"))             id             = Parser.parseInt(fieldValue);
                         if(fieldName.equalsIgnoreCase("id_responsable")) id_responsable = Parser.parseInt(fieldValue);
                         if(fieldName.equalsIgnoreCase("id_proveedor"))   id_proveedor   = Parser.parseInt(fieldValue);
                         if(fieldName.equalsIgnoreCase("fecha"))          fecha          = fieldValue;                                                  
                    }else {
                        // Process form file field (input type="file").                  
                        if(fieldName.equalsIgnoreCase("archivo")) {
                            archivo1 = item;                    
                            archivo = FilenameUtils.getName(item.getName());
                        }
                       
                    }
                };           
            } catch (FileUploadException ex) {
                throw new BaseException("ERROR","Ocurrió un error al subir el archivo");
            }
            
            Responsable responsable = new TResponsable().getById(id_responsable);
            if (responsable == null) throw new BaseException("Responsable inexistente", "No se encontr&oacute; el responsable");
            
            Habilitacion habilitacion;
            THabilitacion th = new THabilitacion();
            boolean nuevo = false;            
            habilitacion = th.getById(id);
            if(habilitacion==null) {             
                habilitacion = new Habilitacion();
                nuevo = true;
            }
           
            String fechabd = TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD);            
            habilitacion.setFecha(fechabd);
            habilitacion.setId_responsable(id_responsable);
            habilitacion.setId_proveedor(responsable.getId_proveedor());

            if(archivo1!=null && !"".equals(archivo)) {
                String filePath = uploadFolder + File.separator + archivo;
                System.out.println(filePath);
                //String fileUrl =  activo_url.getValor() + File.separator + cetificado_fabricacion ;
                File uploadedFile = new File(filePath);
                try {
                    archivo1.write(uploadedFile);
                    habilitacion.setArchivo(archivo);                    
                } catch (Exception ex) {
                    throw new BaseException("ERROR","Ocurri&oacute; un error al cargar el el certificado de fabricaci&oacute;n");
                }                            
            }            
            boolean todoOk = true;
            if(nuevo) {
                
                id = th.alta(habilitacion);
                habilitacion.setId(id);
                todoOk = id!=0;
            } else todoOk = th.actualizar(habilitacion);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar la habilitacion");
            
            jr.setResult("OK");
            jr.setRecord(habilitacion);
            
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            //TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_HABILITACION,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,habilitacion.getId());
//             if(!modo.equals("json")) {
            //response.sendRedirect(PathCfg.RESPONSABLE + "?id_responsable=" + habilitacion.getId_activo());            
        } catch (BaseException ex){
            jr.setMessage(ex.getMessage());
            jr.setResult(ex.getResult());
//          if(!modo.equals("json")) {
//            request.setAttribute("titulo", ex.getResult());
//            request.setAttribute("mensaje", ex.getMessage());
//            request.getRequestDispatcher("message.jsp").forward(request,response);
//          }
        }finally{            
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
