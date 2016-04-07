/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
import bd.Parametro;
import bd.Preticket;
import bd.Preticket_detalle;
import bd.Remito;
import bd.Remito_detalle;
import bd.Site;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TParametro;
import transaccion.TPreticket;
import transaccion.TPreticket_detalle;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import transaccion.TSite;

/**
 *
 * @author Diego
 */
public class PreticketPdf extends BasePdf{
    Remito remito_inicio;
    Remito remito_cierre;    
    List<Remito_detalle> detalle_ent;
    List<Remito_detalle> detalle_dev;
    List<Contrato_detalle> contrato_det;
    
    Preticket preticket;
    List<Preticket_detalle> detalle;
    Contrato contrato;
    Cliente cliente;
    Site site;
    public PreticketPdf( Integer id_remito){
        this.remito_cierre  = new TRemito().getById(id_remito);
        this.detalle_dev    = new TRemito_detalle().getByRemitoId(this.remito_cierre.getId());
        this.remito_inicio  = new TRemito().getById(remito_cierre.getId_referencia());
        this.contrato    = new TContrato().getById(remito_cierre.getId_contrato());
        this.contrato_det = new TContrato_detalle().getByContratoId(this.contrato.getId());        
        this.cliente     = new TCliente().getById(remito_cierre.getId_cliente());
        this.site        = new TSite().getById(remito_cierre.getId_site());
    }
     public PreticketPdf(Preticket preticket){
         this.preticket = preticket;
         this.cliente = new TCliente().getById(preticket.getId_cliente());
         this.contrato = new TContrato().getById(preticket.getId_contrato());
         this.detalle = new TPreticket_detalle().getByPreticketId(preticket.getId());    
         this.site    = new TSite().getById(preticket.getId_site());
    }
     
    public boolean generar(){
        Integer dias = TFecha.diferenciasDeFechas(remito_inicio.getFecha(), remito_cierre.getFecha()) + 1;
        
        System.out.println("Contrato: " + contrato.getNumero());
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Remito entrega: "    + remito_inicio.getNumero());
        System.out.println("Remito devolución: " +remito_cierre.getNumero());
        System.out.println("Inicio: " +remito_inicio.getFecha());
        System.out.println("Fin: " + remito_cierre.getFecha());
        
        System.out.println("Dias: " + dias);
//        List<Preticket_detalle> lista = new TPreticket_detalle().getByPreticketId(43);
//        
//        for(Preticket_detalle d: lista){
//            System.out.print(remito_inicio.getNumero());
//            System.out.print(" ");
//            System.out.print(TFecha.formatearFechaBdVista(remito_inicio.getFecha()));
//            System.out.print(" ");
//            System.out.print(remito_cierre.getNumero());            
//            System.out.print(" ");
//            System.out.print(TFecha.formatearFechaBdVista(remito_cierre.getFecha()));
//            System.out.print(" ");
//            System.out.print(dias);
//            System.out.print(" ");
//            System.out.print(d.getPosicion());
//            System.out.print(" ");
//            System.out.print(d.getActivo_desc_larga());
//            System.out.print(" ");
//            System.out.print(d.getRemito_detalle_cantidad());
//            System.out.print(" ");
//            System.out.print(d.getContrato_detalle_precio());
//            System.out.print(" ");
//            System.out.print(d.getContrato_detalle_id_divisa());
//            System.out.print(" ");
//            System.out.println("");
////            System.out.println(p.getActivo_codigonew() );
//        }
     return true;   
    }
    

    @Override
    protected void addContent(Document document) {
        HashMap<Integer, OptionsCfg.Option> mapUnidades = OptionsCfg.getMap(OptionsCfg.getUnidades());
        HashMap<Integer, OptionsCfg.Option> mapIVA = OptionsCfg.getMap(OptionsCfg.getClasesIva());
        try {
            PdfContentByte cb = docWriter.getDirectContent();
    //           Integer start = 670;
            String IMAGE = "";
            Parametro image_url = new TParametro().getById(OptionsCfg.PRETICKET_IMAGE);
            if (image_url!=null) IMAGE = image_url.getValor();
            Image image = Image.getInstance(IMAGE);
            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);
            cb.addImage(image);
        
        Integer start = 550;  
        Integer lineHeight = 14;
        createHeadings(cb,665,start,String.format("%03d - %d",this.preticket.getPunto_venta(),this.preticket.getNumero() ));
        createHeadings(cb,665,start - lineHeight*1,TFecha.formatearFecha(this.preticket.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
        createHeadings(cb,665,start - lineHeight*2,"30-71457180-6");
        createHeadings(cb,665,start - lineHeight*3,"915-483236-9");
        createHeadings(cb,665,start - lineHeight*4,TFecha.formatearFecha("2014-08-25", TFecha.formatoBD, TFecha.formatoVista));
//        createHeadings(cb,93,start,cliente.getNombre_comercial());
//        createHeadings(cb,93,start,cliente.getNombre_comercial());
        
        start = 475;
        lineHeight = 11;        
        if(this.cliente!=null) {
           createHeadings(cb,80,start,cliente.getNombre_comercial());
           createHeadings(cb,80,start - lineHeight,cliente.getDireccion_fisica());     
           cliente.setCuit(cliente.getCuit());
           createHeadings(cb,665,start - lineHeight,cliente.getCuit());
            OptionsCfg.Option optIva = mapIVA.get(cliente.getId_iva());
           String sitIVA = optIva!=null?optIva.getDescripcion():"";
           createHeadings(cb,80,start - lineHeight * 2,sitIVA);     
        }
        
           // Contrato
//           start -= 48;
        start -= 39;
        if(this.contrato!=null)
             createHeadings(cb,190,start  ,contrato.getNumero().toString());
//           createHeadings(cb,205,start  ,remito.getNumero().toString());
        start = 442;
        lineHeight= 15;
        if(this.site!=null){
             createHeadings(cb,495,start,site.getArea());
             createHeadings(cb,495,start - lineHeight,site.getPozo());
             createHeadings(cb,640,start - lineHeight,site.getEquipo());
        }
        
        start = 390;        
        lineHeight=17;
        String divisa = "";
        Float total = 0f;
        Float dias_herr;
        String unidad = "";
        
        for(Preticket_detalle d: this.detalle){
            dias_herr = (d.getDias() * d.getCantidad());
            divisa = (d.getId_divisa()==1)?" $":" USD";
            OptionsCfg.Option optUnidad = mapUnidades.get(d.getId_unidad());
            unidad=(optUnidad!=null)?optUnidad.getDescripcion():"";
            addText(cb,35,start,d.getRemito_inicio().toString());            
            addText(cb,70,start,TFecha.formatearFechaBdVista(d.getFecha_inicio()));            
            addText(cb,125,start,d.getRemito_cierre().toString());            
            addText(cb,155,start,TFecha.formatearFechaBdVista(d.getFecha_cierre()));            
            addText(cb,220,start,d.getDias().toString());
            addText(cb,250,start,d.getPosicion().toString());
            addText(cb,285,start,d.getDescripcion());
            addText(cb,615,start,d.getCantidad().toString());
            addText(cb,660,start,dias_herr.toString());
            addTextAligned(cb,715,start,7,unidad,Element.ALIGN_RIGHT);
            
            addTextAligned(cb,768,start,7,d.getPrecio().toString() + divisa,Element.ALIGN_RIGHT)  ;
            addTextAligned(cb,818,start,7,d.getSubtotal().toString() + divisa,Element.ALIGN_RIGHT)  ;            
            //addTextAligned(cb,820,start,7,total.toString() + divisa,Element.ALIGN_RIGHT);
            total += d.getSubtotal();
            start -= lineHeight;
        }
        start = 105;
        addTextAligned(cb,820,start,7,total.toString() + divisa,Element.ALIGN_RIGHT);
        //createHeadings(cb,770,start,total.toString() + divisa )  ;
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
    public boolean createPdf(String fileName){
         boolean todoOk;
        try {
            Document document = new Document(PageSize.A4.rotate(),0,0,0,0);
            docWriter = PdfWriter.getInstance(document,new FileOutputStream(fileName));
            document.open();
            initializeFonts();
            addContent(document);
            document.close();
            todoOk = true;
        } catch (DocumentException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
            todoOk = false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
            todoOk = false;
        }
        return todoOk;
    }
    public static void main(String[] args){                
        Preticket r = new TPreticket().getById(8);
        if(r!=null) {
            PreticketPdf p = new PreticketPdf(r);
            p.createPdf("c:\\preticket.pdf");
            p.abrir("c:\\preticket.pdf");
        }else{
            System.out.println("No se encontró el remito");
        }
    }    
}
