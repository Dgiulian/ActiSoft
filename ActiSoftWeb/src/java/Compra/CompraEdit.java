/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compra;

import bd.Activo;
import bd.Compra;
import bd.Parametro;
import bd.Rubro;
import java.io.File;
import java.io.IOException;
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
import transaccion.TActivo;
import transaccion.TAuditoria;
import transaccion.TCompra;
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
public class CompraEdit extends HttpServlet {

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
        String idCompra = request.getParameter("id");        
        String idActivo = request.getParameter("id_activo");
        
        
        Integer  id_compra;
        Compra compra;
        boolean nuevo = false;
        try{
            id_compra = Integer.parseInt(idCompra);
        } catch (NumberFormatException ex){
            id_compra = 0;
        }
        compra = new TCompra().getById(id_compra);
        if(compra==null) {
            compra = new Compra();
            nuevo = true;
            
        }
        Integer id_activo;
        try{
            if (nuevo) {
                id_activo = Parser.parseInt(idActivo);
            } else id_activo = compra.getId_activo();
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            Rubro rubro = new TRubro().getById(activo.getId_rubro());
            if(rubro==null) throw new BaseException("ERROR","Error de configuraci&oacute;n del activo");
            if(activo.getAplica_compra()==0)  throw new BaseException("ERROR","El activo no aplica compra");
            
            request.setAttribute("activo", activo);
            request.setAttribute("compra", compra);
            request.getRequestDispatcher("compra_edit.jsp").forward(request, response);
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
        }
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
        String idCompra               = "";
        String idActivo               = "";
        String fecha                  = "";
        String id_divisa              = "";
        String cantidad               = "";
        String precio_unit            = "";
        String precio_tot             = "";
        String id_proveedor           = "";
        String factura                = "";
        Integer id_accion             = 0;
        String cetificado_fabricacion = "";
        String factura_compra         = "";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        FileItem archivo1 = null;
        FileItem archivo2 = null;
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
            Parametro compra_path = new TParametro().getByCodigo(OptionsCfg.COMPRA_PATH);

            // constructs the folder where uploaded file will be stored
            String uploadFolder = null;
            if (compra_path!=null) uploadFolder = compra_path.getValor();
            else uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;

            System.out.println(uploadFolder);

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
                         if(fieldName.equalsIgnoreCase("id"))           idCompra      = fieldValue;
                         if(fieldName.equalsIgnoreCase("id_activo"))    idActivo      = fieldValue;
                         if(fieldName.equalsIgnoreCase("fecha"))        fecha         = fieldValue;
                         if(fieldName.equalsIgnoreCase("id_divisa"))    id_divisa     = fieldValue;
                         if(fieldName.equalsIgnoreCase("cantidad"))     cantidad      = fieldValue;
                         if(fieldName.equalsIgnoreCase("precio_unit"))  precio_unit   = fieldValue;
                         if(fieldName.equalsIgnoreCase("precio_tot"))   precio_tot    = fieldValue;
                         if(fieldName.equalsIgnoreCase("id_proveedor")) id_proveedor  = fieldValue;
                         if(fieldName.equalsIgnoreCase("factura"))      factura       = fieldValue;
                         if(fieldName.equalsIgnoreCase("id_accion"))    id_accion     = Parser.parseInt(fieldValue);
                         
                    }else {
                        // Process form file field (input type="file").                  
                        if(fieldName.equalsIgnoreCase("certificado_fabricacion")) {
                            archivo1 = item;                    
                            cetificado_fabricacion = FilenameUtils.getName(item.getName());
                        }
                        if(fieldName.equalsIgnoreCase("factura_compra")) {
                            archivo2 = item;                    
                            factura_compra = FilenameUtils.getName(item.getName());
                        }
                        
                    }
                };           
            } catch (FileUploadException ex) {
                throw new BaseException("ERROR","Ocurrió un error al subir el archivo");
            }
            Integer id_activo = Parser.parseInt(idActivo);;
            Integer id_compra = Parser.parseInt(idCompra);;           
            
            Activo activo = new TActivo().getById(id_activo);
            if (activo == null) throw new BaseException("Activo inexistente", "No se encontr&oacute; el activo");
            
            Compra compra;
            TCompra tc = new TCompra();
            boolean nuevo = false;            
            compra = tc.getById(id_compra);
            if(compra==null) {             
                compra = new Compra();
                nuevo = true;
            }            
           
            String fechabd = TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD);
            String fechaAnt = compra.getFecha();
            compra.setFecha(fechabd);
            compra.setId_activo(id_activo);
            
            if(!fechaAnt.equalsIgnoreCase(fechabd) && tc.getCompraPosterior(compra)!=null) throw new BaseException("ERROR","La fecha de compra no puede ser anterior a las compras anteriores");
            if (activo.getStock()==0f) throw new BaseException("ERROR","No se puede crear una compra si el stock del activo es 0");
            
            compra.setId_divisa(Parser.parseInt(id_divisa));
            
            compra.setPrecio_unit(Parser.parseFloat(precio_unit));
            compra.setPrecio_tot(Parser.parseFloat(precio_tot));
            compra.setId_proveedor(Parser.parseInt(id_proveedor));
            compra.setFactura(factura);
            if(archivo1!=null && !"".equals(cetificado_fabricacion)) {
                String filePath = uploadFolder + File.separator + cetificado_fabricacion;
                //String fileUrl =  activo_url.getValor() + File.separator + cetificado_fabricacion ;
                File uploadedFile = new File(filePath);
                try {
                    archivo1.write(uploadedFile);
                    compra.setCertificado_fabricacion(cetificado_fabricacion);
                    
                } catch (Exception ex) {
                    throw new BaseException("ERROR","Ocurri&oacute; un error al cargar el el certificado de fabricaci&oacute;n");
                }                            
            }
            if(archivo2!=null && !"".equals(factura_compra)) {
                String filePath = uploadFolder + File.separator + factura_compra;
                File uploadedFile = new File(filePath);
                try {
                    archivo2.write(uploadedFile);
                    compra.setFactura_compra(factura_compra);                    
                } catch (Exception ex) {
                    throw new BaseException("ERROR","Ocurri&oacute; un error al cargar la factura de compra");
                }                            
            } 
            boolean todoOk = true;
            if(nuevo) {
                compra.setCantidad(Parser.parseFloat(cantidad)); 
                compra.setStock_anterior(activo.getStock());
                compra.setId_accion(id_accion);
                switch(id_accion) {
                    case OptionsCfg.COMPRA_NADA: break;
                    case OptionsCfg.COMPRA_REEMPLAZA: activo.setStock(compra.getCantidad()); break;
                    case OptionsCfg.COMPRA_SUMA: activo.setStock(activo.getStock() + compra.getCantidad());
                    break;
                default: break;
            }
                id_compra = tc.alta(compra);
                compra.setId(id_compra);
                todoOk = id_compra!=0;
            } else todoOk = tc.actualizar(compra);
            if(!todoOk) throw new BaseException ("Error","Ocurri&oacute; un error al guardar la compra");
            
            
            new TActivo().actualizar(activo);
            
            HttpSession session = request.getSession();
            Integer id_usuario = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_COMPRA,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,compra.getId());
        
            response.sendRedirect(PathCfg.COMPRA + "?id_activo=" + compra.getId_activo());
            
        } catch (BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
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
