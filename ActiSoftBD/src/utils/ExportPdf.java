/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Diego
 */
public class ExportPdf extends BasePdf{
    
    public static void main(String[] args){
        String fileName = "c:\\remito.pdf";
        new ExportPdf().createPdf(fileName);
        new ExportPdf().imprimir(fileName);
        
    }
    public ExportPdf(){
        super();
    }
    
    
    public boolean createPdf(String fileName){
        try {
            Document document = new Document();
            docWriter = PdfWriter.getInstance(document,new FileOutputStream(fileName));
            document.open();
            initializeFonts();
            addContent(document);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    @Override
    protected void addContent(Document document){
           PdfContentByte cb = docWriter.getDirectContent();
           createHeadings(cb,22,633,"Item");
           //createLayout(document,cb);
    }
   private void createLayout(Document doc,PdfContentByte cb){
        try {
            final float leftMargin = 20f;
            final float topMargin = 20f;
            final float rightMargin = leftMargin;
            final float bottomMargin = topMargin;
            Integer lineHeight = 15;
            
            cb.setLineWidth(0.5f);
            
            Rectangle pageSize = doc.getPageSize();
            float width = pageSize.getWidth();
            float top = pageSize.getHeight();
            
           
           // Invoice Header box layout
            cb.rectangle(leftMargin,750,width - leftMargin - rightMargin,60);
            
            //Logo
            Image companyLogo = Image.getInstance(logoFileName);
            companyLogo.setAbsolutePosition(25,760);
            companyLogo.scalePercent(10);
            doc.add(companyLogo);
            
            cb.rectangle(leftMargin + (width - leftMargin - rightMargin)/2,650 ,(width - leftMargin - rightMargin)/2,40);
            
            // Cliente
            cb.rectangle(leftMargin,690,width - leftMargin - rightMargin,60);
            createHeadings(cb,leftMargin + 10,740 - (lineHeight * 0) , "Contratista:");
            createHeadings(cb,leftMargin + 10,740  - (lineHeight * 1), "Domicilio:");
            createHeadings(cb,leftMargin + 10,740  - (lineHeight * 2), "IVA:");            
            createHeadings(cb,leftMargin + 300,740 - (lineHeight * 1), "Cuit:");
           
            //Contrato
            cb.rectangle(leftMargin,650,(width - leftMargin - rightMargin)/2,40);
            createHeadings(cb,leftMargin + 10,670 - (lineHeight * 0) , "Contrato Nº:");
            
            createHeadings(cb,leftMargin + 300,670 - (lineHeight * 0) , "Area:");
            createHeadings(cb,leftMargin + 300,670 - (lineHeight * 1) , "Pozo:");
            createHeadings(cb,leftMargin + 420,670 - (lineHeight * 1) , "Equipo: ");
            
           // Invoice Detail box layout
            cb.rectangle(20,50,width - leftMargin - rightMargin,600);
            cb.moveTo(leftMargin,630);
            cb.lineTo(width - leftMargin,630);
            
            //Lineas verticales
            cb.moveTo(50,50);
            cb.lineTo(50,650);            
            cb.moveTo(150,50);
            cb.lineTo(150,650);
            cb.moveTo(500,50);
            cb.lineTo(500,650);
            cb.stroke();

            // Invoice Detail box Text Headings
            createHeadings(cb,22,633,"Item");
            createHeadings(cb,52,633,"Cod Activo");
            createHeadings(cb,152,633,"Descripción");
            createHeadings(cb,502,633,"Cantidad");
           
            
        } catch (BadElementException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
  
}