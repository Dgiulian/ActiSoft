/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import java.io.FileOutputStream;
import java.util.List;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Diego
 */
public abstract class  BaseExcel<E> {
    protected Sheet sheet;
    protected CellStyle estilo;
    protected Workbook workbook;
    protected CellStyle estiloBase;
    public BaseExcel(String sheetname){
        this.workbook = new XSSFWorkbook();
        this.sheet =  workbook.createSheet(sheetname);  
        this.estiloBase = workbook.getCellStyleAt((short)0);
//        this.estilo = workbook.createCellStyle();
    }
    public abstract boolean createExcel(String filename);
    public abstract boolean createExcel(String filename,List<E> lista);
    
    protected Cell createCell(int row,int column,String value){
        Row fila = this.sheet.getRow(row);
        if(fila==null) fila = this.sheet.createRow(row);
        Cell celda = fila.createCell(column);
        celda.setCellStyle(this.estilo);
        celda.setCellValue(value);
        return celda;
    }
    protected Cell createCell(int row,int column,Double value,String formato){
        Row fila = this.sheet.getRow(row);
        
        Cell celda = fila.createCell(column);
        short fontIndex = this.estilo.getFontIndex();
        this.estilo = workbook.createCellStyle();
        this.estilo.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(formato));
        this.estilo.setFont(workbook.getFontAt(fontIndex));
        celda.setCellValue(value);
        celda.setCellStyle(this.estilo);
        
        return celda;
    }
    public CellStyle setStyleBoldCenter(){
        CellStyle estiloCelda = workbook.createCellStyle();
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        Font fuente = workbook.createFont();
        fuente.setBold(true);
        estiloCelda.setFont(fuente);
        return estiloCelda;
    }
  
    
    
    public boolean save(String filename){
        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
             workbook.write(fileOut);                    
        } catch ( Exception ex ) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
     public boolean save(String filename,String formato){
         
        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
             workbook.write(fileOut);
             
        } catch ( Exception ex ) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
   
    protected void createStyledCell(int num_row,int num_col,String texto,int size,boolean bold){
        Row row   = sheet.getRow(num_row);
        if(row==null) {
            row = sheet.createRow((short) num_row);        
        }

        Cell cell = row.createCell((short) num_col);
        cell.setCellValue(texto);

    //    XSSFCellStyle  cellStyle = (XSSFCellStyle) this.workbook.createCellStyle();    
    //    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    //    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


    //    cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    //    cellStyle.setFillPattern(CellStyle.LEAST_DOTS);
    //    Font fuente = workbook.createFont();
    //    fuente.setFontHeightInPoints((short) size);
    //    fuente.setBold(bold);
    //    cellStyle.setFont(fuente);    
    //    cell.setCellStyle(cellStyle); 
        }
}