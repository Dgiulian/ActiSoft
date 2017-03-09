/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Preticket;
import bd.Certificado;
import bd.Cliente;
import bd.Contrato;
import bd.Parametro;
import bd.Pozo;
import bd.Equipo;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TPreticket;
import transaccion.TCertificado;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TEquipo;

/**
 *
 * @author Diego
 */
public class PreticketExportPdf extends BasePdf{
    List<Preticket> lista ;
    private Integer hoja = 0;
    private Integer num_hojas = 0;
    private HashMap<String,String> filtros;
    private final Integer NUM_REMITOS = 32;
    public PreticketExportPdf(List<Preticket> lista){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_REMITOS) + 1 ;
        this.filtros = new HashMap<String,String>();        
    }
     public PreticketExportPdf(List<Preticket> lista,HashMap<String,String> filtros){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_REMITOS) + 1;
        this.filtros = filtros;        
    }
    
    @Override
    protected void addContent(Document document) {
        
        newPage(document);
        
        PdfContentByte cb = docWriter.getDirectContent();                
        HashMap<Integer, Cliente>   mapClientes = new TCliente().getMap();
        HashMap<Integer, Contrato> mapContratos = new TContrato().getMap();
                
        
        PdfPTable table = new PdfPTable(3);
        Integer start = 590;
        Integer cant = 0;        
        
        for(Preticket preticket: this.lista ){
            String pozo = "",
                 equipo = "",
                 estado = "",
                cliente = "",
               contrato = "",
                   tipo = "";
                        
           
            
            Cliente cl = mapClientes.get(preticket.getId_cliente());
            cliente = cl!=null?cl.getNombre():"";
            Contrato co = mapContratos.get(preticket.getId_contrato());
            contrato = co!=null?co.getNumero():"";
       

            if(cant >= NUM_REMITOS){
                newPage(document);
                cant = 0;
                start = 590;
            }
           /*
                Numero
                Tipo preticket
                Fecha
                Cliente
                Contrato
                Equipo
                Preticket
                Estado
                */
            addText(cb, 50,  start, preticket.getPunto_venta().toString());
            addText(cb, 100,  start, preticket.getNumero().toString());            
            addText(cb, 150,  start, TFecha.formatearFechaBdVista(preticket.getFecha()));
            addText(cb, 210,  start, cliente);
            addText(cb, 270,  start, contrato);
            addText(cb, 340,  start, preticket.getNro_certificado());
            addText(cb, 410,  start, preticket.getNro_habilitacion());            
            addText(cb, 470,  start, preticket.getNro_factura());
            addTextAligned(cb, 565,  start,8, preticket.getTotal().toString(),Element.ALIGN_RIGHT);

            start -= 18;
            cant++;
        }

    }
    private void newPage(Document document) {
        PdfContentByte cb = docWriter.getDirectContent();
        try{
           document.newPage();
           String IMAGE = "";
           Parametro image_url = new TParametro().getByCodigo(OptionsCfg.PRETICKET_EXPORT_IMAGE);
           if (image_url!=null) IMAGE = image_url.getValor();
           Image image = Image.getInstance(IMAGE);
           image.scaleAbsolute(PageSize.A4);
           image.setAbsolutePosition(0, 0);
           cb.addImage(image);
           cb.addImage(image);
           this.hoja++;
           Rectangle pageSize = document.getPageSize();
           addText(cb,pageSize.getWidth()/ 2,pageSize.getHeight() - 130,String.format("Hoja %02d/%02d",this.hoja,this.num_hojas));
           encabezado(cb);
        } catch (BadElementException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void encabezado(PdfContentByte cb){
        if (filtros==null) return;
        Integer start = 690;
        Integer lineHeight = 15;        
        Iterator<String> it = filtros.keySet().iterator();
        Integer i = 0;
        while(it.hasNext()){
            String key = it.next();
            String valor = filtros.get(key);
            addTextAligned(cb, 20, start - lineHeight * i,9,String.format("%s: %s",key,valor),Element.ALIGN_LEFT);
            i++;
        }        
    }
    
    public static void main(String[ ] args){
        
        
        //new TPreticket().getList();
        HashMap<String,String> mapFiltro = new HashMap<String,String>();       
//        mapFiltro.put("id_estado","1");
//        mapFiltro.put("id_resultado",OptionsCfg.CERTIFICADO_VENCIDO.toString());
//        mapFiltro.put("id_cliente","5");
//        mapFiltro.put("id_contrato","12");
        
        
        List<Preticket> lista = new ArrayList<Preticket>();
        lista = new TPreticket().getListFiltro(mapFiltro);
        Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);
            
        boolean generado = false;
        String fileName  = "pretickets.pdf";
        String filePath  = parametro.getValor() + File.separator + fileName;
        System.out.println(filePath);
        System.out.println(lista.size());
        mapFiltro.clear();
        mapFiltro.put("Certificado","Si");
        mapFiltro.put("Estado","Disponible");
        mapFiltro.put("Stock","Todos");
        //mapFiltro.put("Contrato","Si");
        
        
        PreticketExportPdf preticketPdf = new PreticketExportPdf(lista,mapFiltro);
        preticketPdf.createPdf(filePath);
        preticketPdf.abrir(filePath);
    }
}
