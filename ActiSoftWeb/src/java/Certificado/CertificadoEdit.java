/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Certificado;

import bd.Activo;
import bd.Certificado;
import bd.Parametro;
import bd.Rubro;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TCertificado;
import transaccion.TParametro;
import transaccion.TRubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class CertificadoEdit extends HttpServlet {

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
        
        String idCertif = request.getParameter("id");        
        String idActivo = request.getParameter("id_activo");
        Integer  id_certif;
        Certificado certificado = null;     
        boolean nuevo = false;
        
        try{
            id_certif = Integer.parseInt(idCertif);            
        } catch (NumberFormatException ex){
            id_certif = 0;
        }
        certificado = new TCertificado().getById(id_certif);
        if(certificado==null) {
            certificado = new Certificado();
            certificado.setId_resultado(OptionsCfg.CERTIFICADO_NO_APTO);
            nuevo = true;
        }
        try{
            Integer id_activo;
            if(nuevo){                
               id_activo = Integer.parseInt(idActivo);                
               
            } else id_activo = certificado.getId_activo();
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Rubro rubro = new TRubro().getById(activo.getId_rubro());
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(rubro.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica certificado");
            
            if (nuevo) certificado.setId_activo(activo.getId());
            
            request.setAttribute("activo", activo);
            request.setAttribute("certificado", certificado);
            request.getRequestDispatcher("certificado_edit.jsp").forward(request, response);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
        } 
    }
    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 2;

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
        
        String idActivo = "";
        String idCertif = "";
        String codigo = "";
        String precinto = "";
        String fecha = "";
        String fecha_vigencia = "";
        String fecha_efectiva = "";
        String nombre_proveedor = "";
        String idResult = "";
        String observaciones = "";
        String strExterno = "";
        Integer desmontaje               = 0;
        Integer limpieza                 = 0;
        Integer inspeccion_visual        = 0;
        Integer ultrasonido              = 0;
        Integer calibres                 = 0;
        Integer particulas_magentizables = 0;
        Integer reemplazo_sellos         = 0;
        Integer rearmado                 = 0;
        Integer grasa                    = 0;
        Integer prueba_presion           = 0;
        Integer pintado                  = 0;
        Integer anillo_segmento          = 0;
        Integer segmentos                = 0;
        Integer tuerca                   = 0;
        try {
          
              // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

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
        
        
        Parametro parametro = new TParametro().getByNumero(OptionsCfg.CERTIFICADO_PATH);
        
        // constructs the folder where uploaded file will be stored
        String uploadFolder = null;
        if (parametro!=null) uploadFolder = parametro.getValor();
        else uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
        
        System.out.println(uploadFolder);
        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        String fileName = null;
        FileItem archivo = null;
        File uploadedFile = null;
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
               if (fieldName.equalsIgnoreCase("id_activo"))                idActivo = fieldValue;
               if (fieldName.equalsIgnoreCase("id"))                       idCertif = fieldValue;
               if (fieldName.equalsIgnoreCase("codigo"))                   codigo = fieldValue;
               if (fieldName.equalsIgnoreCase("precinto"))                 precinto = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha"))                    fecha = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha_vigencia"))           fecha_vigencia = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha_efectiva"))           fecha_efectiva = fieldValue;
               if (fieldName.equalsIgnoreCase("nombre_proveedor"))         nombre_proveedor = fieldValue;
               if (fieldName.equalsIgnoreCase("id_resultado"))             idResult = fieldValue;
               if (fieldName.equalsIgnoreCase("observaciones"))            observaciones = fieldValue;
               if (fieldName.equalsIgnoreCase("externo"))                  strExterno = fieldValue;
               if (fieldName.equalsIgnoreCase("desmontaje"))               desmontaje = 1;
               if (fieldName.equalsIgnoreCase("limpieza"))                 limpieza = 1;
               if (fieldName.equalsIgnoreCase("inspeccion_visual"))        inspeccion_visual = 1;
               if (fieldName.equalsIgnoreCase("ultrasonido"))              ultrasonido = 1;
               if (fieldName.equalsIgnoreCase("calibres"))                 calibres = 1;
               if (fieldName.equalsIgnoreCase("particulas_magentizables")) particulas_magentizables = 1;
               if (fieldName.equalsIgnoreCase("reemplazo_sellos"))         reemplazo_sellos = 1;
               if (fieldName.equalsIgnoreCase("rearmado"))                 rearmado = 1;
               if (fieldName.equalsIgnoreCase("grasa"))                    grasa = 1;
               if (fieldName.equalsIgnoreCase("prueba_presion"))           prueba_presion = 1;
               if (fieldName.equalsIgnoreCase("pintado"))                  pintado = 1;
               if (fieldName.equalsIgnoreCase("anillo_segmento"))          anillo_segmento = 1;
               if (fieldName.equalsIgnoreCase("segmentos"))                segmentos = 1;
               if (fieldName.equalsIgnoreCase("tuerca"))                   tuerca = 1;
            } else {
                // Process form file field (input type="file").
                //String fieldName = item.getFieldName();
                fileName = FilenameUtils.getName(item.getName());
                //String ext = FilenameUtils.getExtension(fileName);
                //InputStream fileContent = item.getInputStream();
                //String fileName = new File(item.getName()).getName();
                
                //Filtrar por extension! Solo permitir PDF y DOC
//                if(!(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("docx")))
//                    throw new BaseException("ERROR", "Solo se aceptaran CV en formato doc, docx o pdf");
                

                    // saves the file to upload directory       
                
                    archivo = item;
                    
                    
                }
            }

            Integer id_activo = Parser.parseInt(idActivo);
            Integer id_certif = Parser.parseInt(idCertif); 
            
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Certificado certificado;
            TCertificado tc = new TCertificado();
            boolean nuevo = false;
            
            certificado = tc.getById(id_certif);
            if(certificado==null){
                certificado = new Certificado();
                nuevo = true;
            }            
            certificado.setId_activo(id_activo);
            certificado.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setFecha_vigencia(TFecha.formatearFecha(fecha_vigencia, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setFecha_efectiva(TFecha.formatearFecha(fecha_efectiva, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setCodigo(codigo);
            certificado.setNombre_proveedor(nombre_proveedor);
            certificado.setPrecinto(precinto);
            certificado.setId_resultado(Integer.parseInt(idResult));
            certificado.setObservaciones(observaciones);
            boolean externo = strExterno!=null && !strExterno.equals("");
            certificado.setExterno(externo);
            
            certificado.setDesmontaje(desmontaje);
            certificado.setLimpieza(limpieza);
            certificado.setInspeccion_visual(inspeccion_visual);
            certificado.setUltrasonido(ultrasonido);
            certificado.setCalibres(calibres);
            certificado.setParticulas_magentizables(particulas_magentizables);
            certificado.setReemplazo_sellos(reemplazo_sellos);
            certificado.setRearmado(rearmado);
            certificado.setGrasa(grasa);
            certificado.setPrueba_presion(prueba_presion);
            certificado.setPintado(pintado);
            certificado.setAnillo_segmento(anillo_segmento);
            certificado.setSegmentos(segmentos);
            certificado.setTuerca(tuerca);

            if(archivo!=null && !"".equals(fileName)) {
                String filePath = uploadFolder + File.separator + fileName;
                uploadedFile = new File(filePath);
                archivo.write(uploadedFile);
                certificado.setArchivo_url(fileName);
                certificado.setArchivo(fileName);
            }
            
            boolean todoOk = true;
            if(nuevo) { 
                id_certif = tc.alta(certificado);
                todoOk = id_certif!=0;                
                certificado.setId(id_certif);
            } else todoOk = tc.actualizar(certificado);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar el certificado");
            Integer id_usuario      = 0;
            Integer id_tipo_usuario = 0;
            HttpSession session = request.getSession();
            id_usuario = (Integer) session.getAttribute("id_usuario");
            id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            
            
            if(certificado.getId_resultado()==OptionsCfg.CERTIFICADO_NO_APTO){
                activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_NO_APTO);
                new TActivo().actualizar(activo);
                TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CERTIFICADO,OptionsCfg.ACCION_MODIFICAR,activo.getId());
            }
            
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CERTIFICADO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,certificado.getId());
            response.sendRedirect(PathCfg.CERTIFICADO + "?id_activo=" + certificado.getId_activo());
            
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
        } catch (Exception ex) {
            Logger.getLogger(CertificadoEdit.class.getName()).log(Level.SEVERE, null, ex);
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
