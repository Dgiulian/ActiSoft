/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;

/**
 *
 * @author Diego
 */
public abstract class BasePdf {
    protected  PdfWriter docWriter;
    protected  BaseFont bfBold;
    protected  BaseFont bf;    
    protected  int pageNumber = 0;
    protected final String logoFileName = "e:\\ActiSoft\\LogoACTIgde.jpg";
    protected void createHeadings(PdfContentByte cb, float x, float y, String text){
        cb.beginText();
        cb.setFontAndSize(bfBold, 10);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.setFontAndSize(bfBold, 8);
        cb.endText();
    }
    protected void addText(PdfContentByte cb, float x, float y, String text){
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();
    }
    public BasePdf(){        
    }
     public boolean createPdf(String fileName){
         boolean todoOk;
        try {
            Document document = new Document();
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
     
   protected abstract void addContent(Document document);
  
  protected void initializeFonts(){
    try {
      bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
      bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); 
    } catch (DocumentException e) {
          Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, e);
    } catch (IOException e) {
         Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, e);
    }


   }
  public void imprimir(String filename){
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        paper.setSize(612.0, 832.0);
        double margin = 10;
        paper.setImageableArea(margin, margin, paper.getWidth() - margin, paper.getHeight() - margin);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.LANDSCAPE);
    
    try {
    // PDFBox
    PDDocument document = PDDocument.load(filename);
    job.setPageable(new PDPageable(document, job));

    job.setJobName("funciona?");
    
        job.print();
    } catch (PrinterException e) {
        System.out.println(e);
    }   catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  public void abrir(String filename ){
        try {
            File file = new File(filename);
          if (file.toString().endsWith(".pdf")) 
              Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
          else {
//              Desktop desktop = Desktop.getDesktop();
//              desktop.open(file);
      } } catch (IOException ex) {
            Logger.getLogger(BasePdf.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
//   public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {
//        BarcodeEAN barcode = new BarcodeEAN();
//        barcode.setCodeType(Barcode.EAN8);
//        barcode.setCode(code);
//        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
//        cell.setPadding(10);
//        return cell;
//    }         
    public static Image createBarcode(PdfContentByte cb, String code) {
//        BarcodeEAN barcode = new BarcodeEAN();
//        barcode.setCodeType(Barcode.EAN8);
//        barcode.setCode(code);
        Barcode39 code39 = new Barcode39();
        code39.setCode(code);
        return code39.createImageWithBarcode(cb, BaseColor.BLACK, BaseColor.GRAY);
        
    }         
}
