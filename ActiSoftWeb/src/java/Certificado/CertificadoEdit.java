/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Certificado;

import bd.Activo;
import bd.Certificado;
import bd.Kit;
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
import transaccion.TKit;
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
        Integer  id_certif = Parser.parseInt(request.getParameter("id"));
        Integer  id_modulo = Parser.parseInt(request.getParameter("id_modulo"));        
        Integer id_objeto = Parser.parseInt(request.getParameter("id_objeto"));
        
        Certificado certificado = null;     
        boolean nuevo = false;  
        Integer id_rubro;

        certificado = new TCertificado().getById(id_certif);
        if(certificado==null) {
            certificado = new Certificado();
            certificado.setId_resultado(OptionsCfg.CERTIFICADO_APTO);
            certificado.setId_modulo(id_modulo);
            nuevo = true;
        } else {
            id_modulo = certificado.getId_modulo();
            id_objeto = certificado.getId_objeto();
        }
        try{            
            if (id_modulo.equals(OptionsCfg.MODULO_KIT) ) {
                Kit kit = new TKit().getById(id_objeto);
                if (kit == null) throw new BaseException("Kit inexistente", "No se encontr&oacute; el kit");
                id_rubro = kit.getId_rubro();
                request.setAttribute("kit", kit);
            } else{
                Activo activo = new TActivo().getById(id_objeto);
                if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
                id_rubro = activo.getId_rubro();
                if (nuevo) certificado.setId_objeto(activo.getId());
                request.setAttribute("activo", activo);
            }
            if (nuevo) {
                certificado.setId_modulo( id_modulo);
                certificado.setId_objeto(id_objeto);
            }
            Rubro rubro = new TRubro().getById(id_rubro);
            
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(rubro.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica certificado");
            
            request.setAttribute("id_modulo",id_modulo);
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
        //request.setCharacterEncoding("UTF-8");
        System.out.println(request.getCharacterEncoding());
        String idModulo = "";
        String idActivo = "";
        String idCertif = "";
        String codigo = "";
        String precinto = "";
        String fecha = "";
        String fecha_hasta = "";
        String fecha_desde = "";
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
        Integer estado_anterior  = 0;
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
        
        
        Parametro parametro = new TParametro().getByCodigo(OptionsCfg.CERTIFICADO_PATH);
        
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
                String fieldValue = item.getString("UTF-8");
               if (fieldName.equalsIgnoreCase("id_objeto"))                idActivo = fieldValue;
               if (fieldName.equalsIgnoreCase("id_modulo"))                idModulo = fieldValue;
               if (fieldName.equalsIgnoreCase("id"))                       idCertif = fieldValue;
               if (fieldName.equalsIgnoreCase("codigo"))                   codigo = fieldValue;
               if (fieldName.equalsIgnoreCase("precinto"))                 precinto = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha"))                    fecha = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha_desde"))              fecha_desde = fieldValue;
               if (fieldName.equalsIgnoreCase("fecha_hasta"))              fecha_hasta = fieldValue;               
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

            Integer id_modulo = Parser.parseInt(idModulo);
            Integer id_objeto = Parser.parseInt(idActivo);
            Integer id_certif = Parser.parseInt(idCertif); 
            Kit kit = null;
            Activo activo = null;
            if (id_modulo.equals(OptionsCfg.MODULO_KIT) ) {
                 kit = new TKit().getById(id_objeto);
                if (kit == null) throw new BaseException("Kit inexistente", "No se encontr&oacute; el kit");
                
             } else {
                activo = new TActivo().getById(id_objeto);
                if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            }                        
            
            Certificado certificado;
            TCertificado tc = new TCertificado();
            boolean nuevo = false;
            
            certificado = tc.getById(id_certif);
            if(certificado==null){
                certificado = new Certificado();
                nuevo = true;
            }                        
            certificado.setId_objeto(id_objeto);
            certificado.setId_modulo(id_modulo);
            certificado.setFecha(TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setFecha_hasta(TFecha.formatearFecha(fecha_hasta, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setFecha_desde(TFecha.formatearFecha(fecha_desde, TFecha.formatoVista, TFecha.formatoBD));
            certificado.setCodigo(codigo);
            certificado.setNombre_proveedor(nombre_proveedor);
            certificado.setPrecinto(precinto);
            Integer id_resultado = Integer.parseInt(idResult);
            certificado.setId_resultado(id_resultado);
            
            certificado.setObservaciones(observaciones);
            boolean externo = strExterno!=null && !strExterno.equals("");
            certificado.setExterno(externo?1:0);
            
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
            
            
            
            //Recalculo el id_resultado.
            id_resultado  = tc.calcularId_resultado(certificado);
            certificado.setId_resultado(id_resultado);
            
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
            
            if (id_modulo.equals(OptionsCfg.MODULO_KIT) ) {
                if(certificado.getId_resultado().equals(OptionsCfg.CERTIFICADO_NO_APTO)){
                    kit.setId_estado(OptionsCfg.KIT_ESTADO_NO_APTO);
                } else {
                    kit.setId_estado(OptionsCfg.KIT_ESTADO_DISPONIBLE);
                }
                new TKit().actualizar(kit);
            } else {
                if(certificado.getId_resultado().equals(OptionsCfg.CERTIFICADO_NO_APTO)){
                    activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_NO_APTO);
                } else {
                    activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                }
                new TActivo().actualizar(activo);
            }
            TAuditoria.guardar(id_usuario,id_tipo_usuario,id_modulo,OptionsCfg.ACCION_MODIFICAR,id_objeto);
            
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CERTIFICADO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,certificado.getId());
            response.sendRedirect(PathCfg.CERTIFICADO + "?id_modulo="+ certificado.getId_modulo()+"&id_objeto=" + certificado.getId_objeto());
            
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
