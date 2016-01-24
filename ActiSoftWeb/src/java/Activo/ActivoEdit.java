/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activo;

import bd.Activo;
import bd.Parametro;
import bd.Rubro;
import bd.Subrubro;
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
import transaccion.TParametro;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.PathCfg;


/**
 *
 * @author Diego
 */
public class ActivoEdit extends HttpServlet {


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
        Activo activo = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                activo = new TActivo().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el activo");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (activo!=null){
            request.setAttribute("activo", activo);
        }
        request.getRequestDispatcher("activo_edit.jsp").forward(request, response);
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
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String idActivo        = null;
        String codigo          = "";
        String marca           = "";
        String str_estado      = "";
        String num_serie       = "";
        String idRubro         = "";
        String idSubrubro      = "";
        String stockMinimo     = "";
        String num_rfid        = "";
        String observaciones   = "";
        String anillo          = "";
        String str_peso        = "";
        String str_alto        = "";
        String str_ancho       = "";
        String str_profundidad = "";
        String medida          = "";
        String conexion        = "";
        String longitud        = "";
        String codigo_aduana   = "";
        String desc_corta      = "";
        String desc_opcional   = "";
        String precio          = "";
        String aplicaStock     = "";
        String aplicaCompra    = "";
        Integer id_rubro       = 0;
        Integer id_subrubro    = 0;
        Integer stock_minimo   = 0;
        Float stock           = 0f;
    try{
        Integer id_usuario = 0;
        Integer id_tipo_usuario = 0;
        HttpSession session = request.getSession();
        id_usuario = (Integer) session.getAttribute("id_usuario");
        id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
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
        Parametro activo_path = new TParametro().getByNumero(OptionsCfg.ACTIVO_PATH);
        Parametro activo_url = new TParametro().getByNumero(OptionsCfg.ACTIVO_URL);
        
        // constructs the folder where uploaded file will be stored
        String uploadFolder = null;
        if (activo_path!=null) uploadFolder = activo_path.getValor();
        else uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
        
        System.out.println(uploadFolder);
        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        String fileName1 = null;
        String fileName2 = null;
        String fileName3 = null;
        
        FileItem archivo1 = null;
        FileItem archivo2 = null;
        FileItem archivo3 = null;
        
        File uploadedFile = null;
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
                if(fieldName.equalsIgnoreCase("id"))             idActivo          = fieldValue;
                if(fieldName.equalsIgnoreCase("codigo"))         codigo          = fieldValue;
                if(fieldName.equalsIgnoreCase("marca"))          marca           = fieldValue;
                if(fieldName.equalsIgnoreCase("id_estado"))      str_estado      = fieldValue;
                if(fieldName.equalsIgnoreCase("num_serie"))      num_serie       = fieldValue;
                if(fieldName.equalsIgnoreCase("id_rubro"))       idRubro         = fieldValue;
                if(fieldName.equalsIgnoreCase("id_subrubro"))    idSubrubro      = fieldValue;
                if(fieldName.equalsIgnoreCase("stock_minimo"))   stockMinimo     = fieldValue;
                if(fieldName.equalsIgnoreCase("num_rfid"))       num_rfid        = fieldValue;
                if(fieldName.equalsIgnoreCase("observaciones"))  observaciones   = fieldValue;
                if(fieldName.equalsIgnoreCase("anillo"))         anillo          = fieldValue;
                if(fieldName.equalsIgnoreCase("peso"))           str_peso        = fieldValue;
                if(fieldName.equalsIgnoreCase("alto"))           str_alto        = fieldValue;
                if(fieldName.equalsIgnoreCase("ancho"))          str_ancho       = fieldValue;
                if(fieldName.equalsIgnoreCase("profundidad"))     str_profundidad = fieldValue;
                if(fieldName.equalsIgnoreCase("medida"))         medida          = fieldValue;
                if(fieldName.equalsIgnoreCase("conexion"))       conexion        = fieldValue;
                if(fieldName.equalsIgnoreCase("longitud"))       longitud        = fieldValue;
                if(fieldName.equalsIgnoreCase("codigo_aduana"))  codigo_aduana   = fieldValue;
                if(fieldName.equalsIgnoreCase("desc_corta"))     desc_corta      = fieldValue;
                if(fieldName.equalsIgnoreCase("desc_opcional"))  desc_opcional   = fieldValue;
                if(fieldName.equalsIgnoreCase("precio"))         precio          = fieldValue;
                if(fieldName.equalsIgnoreCase("aplica_stock"))   aplicaStock     = fieldValue;
                if(fieldName.equalsIgnoreCase("aplica_compra"))  aplicaCompra    = fieldValue;
                if(fieldName.equalsIgnoreCase("sock"))           stock           = Parser.parseFloat(fieldValue);
            } else {
                // Process form file field (input type="file").
                String fieldName = item.getFieldName();
                
                
                
                if(fieldName.equalsIgnoreCase("archivo_1")) {
                    archivo1 = item;
                    fileName1 = FilenameUtils.getName(item.getName());
                }
                if(fieldName.equalsIgnoreCase("archivo_2")){
                    archivo2 = item;
                    fileName2 = FilenameUtils.getName(item.getName());
                }
                if(fieldName.equalsIgnoreCase("archivo_3")) {
                    archivo3 = item;
                    fileName3 = FilenameUtils.getName(item.getName());
                }
              }
        }
        
        Integer id;
        TActivo tActivo = new TActivo();
        Activo activo;
        boolean nuevo = false;
        if(idActivo!=null) {
            try{
                id = Integer.parseInt(idActivo);            
                activo = tActivo.getById(id);
                if (activo==null) {
                    request.setAttribute("titulo","Error");
                    request.setAttribute("mensaje", "No se ha encontrado el activo");
                    request.getRequestDispatcher("message.jsp").forward(request, response);
                    return; 
                }
            } catch(NumberFormatException ex){
                throw new BaseException("ERROR", "No se ha encontrado el activo");
            }
        } else {
            id = 0;
            activo = new Activo();
            nuevo = true;
            activo.setStock(1f);
        }
//        String codigo          = request.getParameter("codigo");
//        String marca           = request.getParameter("marca");
//        String str_estado      = request.getParameter("id_estado")!=null?request.getParameter("id_estado"):"";        
//        String num_serie       = request.getParameter("num_serie");
//        Integer id_rubro       = request.getParameter("id_rubro")!=null?Integer.parseInt(request.getParameter("id_rubro")):0;
//        Integer id_subrubro    = request.getParameter("id_subrubro")!=null?Integer.parseInt(request.getParameter("id_subrubro")):0;
//        Integer stock_minimo   = request.getParameter("stock_minimo")!=null?Integer.parseInt(request.getParameter("stock_minimo")):0;
//        String num_rfid        = request.getParameter("num_rfid");
//        String observaciones   = request.getParameter("observaciones");
//        String anillo          = request.getParameter("anillo");
//        String str_peso        = request.getParameter("peso")!=null?request.getParameter("peso"):"";
//        String str_alto        = request.getParameter("alto")!=null?request.getParameter("alto"):"";
//        String str_ancho       = request.getParameter("ancho")!=null?request.getParameter("ancho"):"";
//        String str_profundidad = request.getParameter("profundidad")!=null?request.getParameter("profundidad"):"";
//        String medida          = request.getParameter("medida");
//        String conexion        = request.getParameter("conexion");
//        String longitud        = request.getParameter("longitud");
//        String codigo_aduana   = request.getParameter("codigo_aduana");
//        String desc_corta      = request.getParameter("desc_corta");
//        String precio          = request.getParameter("precio");
//        String aplicaStock     = request.getParameter("aplica_stock");
        
            id_rubro     = Parser.parseInt(idRubro);
            id_subrubro  = Parser.parseInt(idSubrubro);
            stock_minimo = Parser.parseInt(stockMinimo);
     
        
        if (str_estado!=null) activo.setId_estado(Integer.parseInt(str_estado));
        if(codigo==null || codigo.equals(""))
            throw new BaseException("Error de C&oacute;digo","Debe ingresar el c&oacute;digo del activo");
        
        Rubro rubro = new TRubro().getById(id_rubro);
        if (rubro==null) throw new BaseException ("Rubro inexistente","Debe seleccionar el rubro del activo");

        Subrubro subrubro = new TSubrubro().getById(id_subrubro);
        if (subrubro==null) throw new BaseException ("Rubro inexistente","Debe seleccionar el subrubro del activo");
        
        if(subrubro.getId_rubro()!= rubro.getId())
             throw new BaseException ("Error de subrubro","El rubro y el subrubro no se corresponden");
        
        if(activo.getId_estado()==OptionsCfg.ACTIVO_ESTADO_ALQUILADO)
            throw new BaseException("Activo alquilado","El activo se encuentra en alquiler y no puede ser editado");
        
        activo.setCodigo(codigo);
        activo.setMarca(marca);        
        activo.setId_rubro(id_rubro);
        activo.setId_subrubro(id_subrubro);
        activo.setDesc_corta(desc_corta);
        activo.setDesc_opcional(desc_opcional);
        
        activo.setNum_serie(num_serie);
        activo.setStock_minimo(stock_minimo);
        activo.setNum_rfid(num_rfid);
        activo.setObservaciones(observaciones);
        activo.setCodigo_aduana(codigo_aduana);
        activo.setAnillo(anillo);
        activo.setPrecio(Float.parseFloat(precio));
        
        Integer aplica_stock  = (aplicaStock !=null && !aplicaStock.equals(""))?1:0;
        Integer aplica_compra = (aplicaCompra!=null && !aplicaCompra.equals(""))?1:0;
        
        activo.setAplica_stock(aplica_stock);            
        activo.setAplica_compra(aplica_compra);            
            
        if (!str_peso.equals(""))
            activo.setPeso(Float.parseFloat(str_peso));
        if(!str_alto.equals(""))
            activo.setAlto(Float.parseFloat(str_alto));
        if(!str_ancho.equals(""))
            activo.setAncho(Float.parseFloat(str_ancho));
        if(!str_profundidad.equals(""))
            activo.setProfundidad(Float.parseFloat(str_profundidad));
        activo.setMedida(medida);
        activo.setConexion(conexion);
        activo.setLongitud(longitud);
        
        String desc_larga = rubro.getDescripcion() + " " + subrubro.getDescripcion()+ " " + activo.getMedida() + " " + activo.getConexion() + " "+ activo.getLongitud();
        activo.setDesc_larga(desc_larga);
        if(nuevo) {
            String codigoDisponible = tActivo.getCodigoDisponible(activo);
            activo.setCodigoNew(codigoDisponible);
        }
        
        
        if(archivo1!=null && !"".equals(fileName1)) {
            String filePath = uploadFolder + File.separator + fileName1;
            String fileUrl =  activo_url.getValor() + File.separator + fileName1 ;
            uploadedFile = new File(filePath);
            try {
                archivo1.write(uploadedFile);
            } catch (Exception ex) {
                Logger.getLogger(ActivoEdit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            activo.setArchivo_1_url(fileUrl);
            activo.setArchivo_1(fileName1);
        }
        if(archivo2!=null && !"".equals(fileName2)) { 
            String filePath = uploadFolder + File.separator + fileName2;
            String fileUrl =  activo_url.getValor() + File.separator + fileName2 ;
            uploadedFile = new File(filePath);
            try {                
                archivo2.write(uploadedFile);
            } catch (Exception ex) {
                Logger.getLogger(ActivoEdit.class.getName()).log(Level.SEVERE, null, ex);
            }
            activo.setArchivo_2_url(fileUrl);
            activo.setArchivo_2(fileName2);
        }
        if(archivo3!=null && !"".equals(fileName3)) {            
            String filePath = uploadFolder + File.separator + fileName3;
            String fileUrl =  activo_url.getValor() + File.separator + fileName3 ;
            uploadedFile = new File(filePath);
            try {
                archivo3.write(uploadedFile);
            } catch (Exception ex) {
                Logger.getLogger(ActivoEdit.class.getName()).log(Level.SEVERE, null, ex);
            }
            activo.setArchivo_3_url(fileUrl);
            activo.setArchivo_3(fileName3);
        }
        boolean todoOk = false;
        if(!nuevo){
            todoOk = tActivo.actualizar(activo);
        } else {
            id = tActivo.alta(activo);
            activo.setId(id);
            todoOk = id!=0;
        }        
        if(!todoOk) throw new BaseException("Error", "Ocurrio un error al guardar el activo");
        
        TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_ACTIVO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,activo.getId());
        response.sendRedirect(PathCfg.ACTIVO);
        
    } catch(BaseException ex){
                request.setAttribute("titulo",ex.getResult());
                request.setAttribute("mensaje", ex.getMessage());
                request.getRequestDispatcher("message.jsp").forward(request, response);
            return;
            } catch (FileUploadException ex) {
            Logger.getLogger(ActivoEdit.class.getName()).log(Level.SEVERE, null, ex);
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
