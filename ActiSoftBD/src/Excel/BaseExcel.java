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
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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
    
    protected Cell createCell(Short row,Integer column,String value){
        Row fila = this.sheet.getRow(row);
        
        Cell celda = fila.createCell(column);
        celda.setCellStyle(this.estilo);
        celda.setCellValue(value);
        return celda;
    }
    protected Cell createCell(Short row,Integer column,Double value,String formato){
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
        estiloCelda.setAlignment(CellStyle.ALIGN_CENTER);
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
}