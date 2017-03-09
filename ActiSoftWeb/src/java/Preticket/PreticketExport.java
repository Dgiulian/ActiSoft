/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preticket;

import Excel.PreticketExcel;
import bd.Cliente;
import bd.Equipo;
import bd.Parametro;
import bd.Pozo;
import bd.Preticket;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transaccion.TCliente;
import transaccion.TEquipo;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TPreticket;
import utils.BaseException;
import utils.JsonRespuesta;
import utils.OptionsCfg;
import utils.Parser;
import utils.PreticketExportPdf;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class PreticketExport extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       JsonRespuesta jr = new JsonRespuesta();
        try{
            //Integer id_rubro = Parser.parseInt(request.getParameter("id_rubro"));
            
            Integer id_cliente  = Parser.parseInt(request.getParameter("id_cliente"));
            Integer id_reporte  = Parser.parseInt(request.getParameter("id_reporte"));
            String strFacturado = request.getParameter("preticket");
            String  fecha       = TFecha.formatearFechaVistaBd(request.getParameter("fecha"));
            
            HashMap<String,String> mapFiltro = new HashMap<String,String>();
            HashMap<String,String> filtros   = new HashMap<String,String>();
            List<Preticket> lista;
            TPreticket tr = new TPreticket();
            
            
            if (id_cliente!=0) {
                mapFiltro.put("id_cliente",id_cliente.toString());                
                HashMap<Integer, Cliente> mapClientes = new TCliente().getMap( );
                filtros.put("Cliente",mapClientes.get(id_cliente).getNombre());
            } else filtros.put("Cliente","Todos");
                      
            lista = tr.getListFiltro(mapFiltro);
            
            if (lista==null) throw new BaseException("ERROR","Ocurrió un error al recuperar los pretickets");
            Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);
            
            boolean generado = false;
            String fileName  = id_reporte==1?"pretickets.pdf":"pretickets.xlsx";
            String filePath = parametro.getValor() + File.separator + fileName;
            System.out.println(filePath);
            System.out.println(lista.size());
            if(id_reporte==1){
                PreticketExportPdf pdf = new PreticketExportPdf(lista,filtros);
                     generado = pdf.createPdf(filePath);
            }else {
                PreticketExcel excel = new PreticketExcel("Listado de Pretickets",lista,filtros);
                generado = excel.createExcel(filePath);
            }
            System.out.println(generado);
            if (generado) {
                
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
                System.out.println("MIME type: " + mimeType);

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
            
            
            
        } catch(BaseException ex){
            jr.setResult(ex.getResult());
            jr.setMessage(ex.getMessage());
        } finally{
//            out.println(new Gson().toJson(jr));
//            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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

    /**
     * Handles the HTTP <code>POST</code> method.
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
