/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import bd.Activo;
import bd.Parametro;
import bd.Rubro;
import bd.Subrubro;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TParametro;
import transaccion.TRubro;
import transaccion.TSubrubro;

/**
 *
 * @author Diego
 */
public class EtiquetasPdf extends BasePdf{
    List<Activo> lstActivos;
    HashMap<Integer, Rubro> mapRubro;
    HashMap<Integer, Subrubro> mapSubrubro;
    RectangleReadOnly etiquetaSize = new RectangleReadOnly(250,97);
    float etiquetaBorder = 0f;
    public EtiquetasPdf( List<Activo> lstActivos){
        this.lstActivos = lstActivos;
        this.mapRubro = new TRubro().getMap();
        this.mapSubrubro = new TSubrubro().getMap();
    }    

    @Override
    protected void addContent(Document document) {
        document.setMargins(10, 10, 10, 10);
        Rectangle pageSize = document.getPageSize();        
        document.newPage();

            
        PdfContentByte cb = docWriter.getDirectContent();
        String IMAGE = "e:\\ActiSoft\\fonto_etiquetas.png";        
        Parametro image_url = new TParametro().getByCodigo(OptionsCfg.ETIQUETA_IMAGE);
        if (image_url!=null) IMAGE = image_url.getValor();
       
        
       
        Image image = getImage(IMAGE);
        if (image!=null){
//            image.scaleAbsolute(PageSize.A4.rotate());
            image.setAbsolutePosition(0, 0);            
            image.setDpi(120, 120);
            image.scaleAbsolute(document.getPageSize());
            image.setAbsolutePosition(0, 0);
//        cb.addImage(image);
        }
       
        Float start = pageSize.getTop() - 50;  
        Float pageMid = pageSize.getWidth() / 3;
        Integer lineHeight = 14;
        
        Integer cant = 0;
        Float col = 25f;
        for(Activo a: lstActivos){
            System.out.println(a.getCodigo());
            if(start<etiquetaSize.getHeight()) {
                document.newPage();
                start = pageSize.getTop() - 50;  
//                cb.addImage(image);
            }
            if( cant % 3 ==0){
                imprimirEtiqueta(a,cb, col,start);
            } else if( cant % 3 ==1){
                imprimirEtiqueta(a,cb, col + (pageMid - 7),start);
//                start -= etiquetaSize.getHeight() + etiquetaBorder ;
            } else {
                imprimirEtiqueta(a,cb, col + (pageMid - 7) * 2  ,start);
                start -= etiquetaSize.getHeight() ;
            }
            cant++;
        }
        
       
    }
    public void imprimirEtiqueta(Activo a,PdfContentByte cb,float x, float y){
        Rubro r = mapRubro.get(a.getId_rubro());
        Subrubro s = mapSubrubro.get(a.getId_subrubro());
        
        float lineHeight = 11f;
        createHeadings(cb,x + 40,y, a.getCodigo());
//        addText(cb,x,y -  lineHeight,a.getDesc_larga().substring(0,Math.min(a.getDesc_larga().length(),40)));
        
          addText(cb,x,y -  lineHeight ,r.getDescripcion());
          addText(cb,x,y -  lineHeight * 2,s.getDescripcion());
          addText(cb,x,y -  lineHeight * 3,a.getConexion());
        try {
            Image imgCode = createBarcode(cb,a.getCodigo());
            imgCode.setAbsolutePosition(x + 40, y - 78);
            cb.addImage(imgCode);
    //        Rectangle rect = new Rectangle(x - 10, y-(etiquetaSize.getHeight()-lineHeight - 10), x + etiquetaSize.getWidth() + 10, y +lineHeight + 10);
    //        rect.setBorder(Rectangle.BOX);
    //        rect.setBorderWidth(0.7f);        
    //        cb.rectangle(rect);
        } catch (DocumentException ex) {
            Logger.getLogger(EtiquetasPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createTitle(PdfContentByte cb, float x, float y, String text){
        cb.beginText();
        
        cb.setFontAndSize(bfBold, 14);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.setFontAndSize(bfBold, 8);
        cb.endText();
    }
//    @Override
//    public boolean createPdf(String fileName){
//         boolean todoOk;
//        try {
//            Document document = new Document(PageSize.A4.rotate(),0,0,0,0);
//            docWriter = PdfWriter.getInstance(document,new FileOutputStream(fileName));
//            document.open();
//            initializeFonts();
//            addContent(document);
//            document.close();
//            todoOk = true;
//        } catch (DocumentException ex) {
//            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
//            todoOk = false;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
//            todoOk = false;
//        }
//        return todoOk;
//    }
    public static void main(String[] args){                
        List<Activo> lstActivos = new ArrayList<>();
        TActivo ta = new TActivo();
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        lstActivos.add(ta.getById(1));
        lstActivos.add(ta.getById(10));
        lstActivos.add(ta.getById(100));
        lstActivos.add(ta.getById(50));
        lstActivos.add(ta.getById(110));
        lstActivos.add(ta.getById(60));
        lstActivos.add(ta.getById(51));
        EtiquetasPdf etiquetasPdf = new EtiquetasPdf(lstActivos);
        etiquetasPdf.createPdf("c:\\etiquetas.pdf");
        etiquetasPdf.abrir("c:\\etiquetas.pdf");
    }    
}
