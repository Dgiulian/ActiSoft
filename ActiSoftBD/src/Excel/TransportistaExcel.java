/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import bd.Parametro;
import bd.Proveedor;
import bd.Transportista;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import transaccion.TParametro;
import transaccion.TProveedor;
import transaccion.TTransportista;
import utils.OptionsCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class TransportistaExcel extends BaseExcel<bd.Transportista>{
    private List<Transportista> lista ;
    private Proveedor proveedor;
    
     public TransportistaExcel(String sheetname){
        super(sheetname);
    }
    public TransportistaExcel(String sheetname,Proveedor proveedor, List<Transportista> lista){
        super(sheetname);
        this.lista = lista;
        this.proveedor = proveedor;
    }
    @Override
    public boolean createExcel(String filename) {
        return this.createExcel(filename, this.lista);
    }

    @Override
    public boolean createExcel(String filename, List<Transportista> lista) {
       this.estilo = setStyleBoldCenter();
        
        createHeader();
        Short rowIndex = 6;
        Short lastIndex = rowIndex;
        Integer i;
        this.estilo = estiloBase;
        
        Proveedor proveedor_ant = null;
        for(Transportista transportista: lista ){
           sheet.createRow(rowIndex);
           i= 0;
           
           if(!proveedor.equals(proveedor_ant)){            
            createStyledCell(rowIndex,i,proveedor.getNombre_comercial(),10,false);
            proveedor_ant = proveedor;
           }
           i++;
           createCell(rowIndex,i++,transportista.getNombre());           
           createCell(rowIndex,i++,transportista.getDni());        
           createCell(rowIndex,i++,TFecha.formatearFechaBdVista(transportista.getVencimiento_carnet()));
           createCell(rowIndex,i++,TFecha.formatearFechaBdVista(transportista.getVencimiento_carnet_defensivo()));
           createCell(rowIndex,i++,TFecha.formatearFechaBdVista(transportista.getVencimiento_credencial_ipf()));
           rowIndex++;
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf(String.format("A%d:A%d",lastIndex+1,rowIndex)));
        System.out.println(String.format("A%d:A%d",lastIndex+1,rowIndex+1));
        sheet.autoSizeColumn(1);
        return save(filename);
    }

 public void  createHeader(){
     try {     
           
               Parametro parametro = new TParametro().getByCodigo(OptionsCfg.LOGO_IMAGE);
              //FileInputStream obtains input bytes from the image file
               String logoFile = parametro.getValor();
               InputStream inputStream = new FileInputStream(logoFile);
               //Get the contents of an InputStream as a byte[].
               byte[] bytes = IOUtils.toByteArray(inputStream);
               //Adds a picture to the workbook
               int pictureIdx = this.workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
               //close the input stream
               inputStream.close();
               //Returns an object that handles instantiating concrete classes
               CreationHelper helper = this.workbook.getCreationHelper();
               //Creates the top-level drawing patriarch.
               Drawing drawing = sheet.createDrawingPatriarch();

               //Create an anchor that is attached to the worksheet
               ClientAnchor anchor = helper.createClientAnchor();

               //create an anchor with upper left cell _and_ bottom right cell
               anchor.setCol1(0); //Column A
               anchor.setRow1(0); //Row 1
               anchor.setCol2(2); //Column B
               anchor.setRow2(4); //Row 4

               //Creates a picture
               Picture pict = drawing.createPicture(anchor, pictureIdx);

               //Create the Cell B3
               Cell cell = sheet.createRow(2).createCell(1);

               //set width to n character widths = count characters * 256
               //int widthUnits = 20*256;
               //sheet.setColumnWidth(1, widthUnits);

               //set height to n points in twips = n * 20
               //short heightUnits = 60*20;
               //sheet.setColumnWidth(1, widthUnits);

               //Write the Excel file
                

                createCell(0,2,"SSOLICITUD DOCUMENTACIÓN SUBCONTRATISTA DE TRANSPORTE");
                createCell(0,11,"X-FG-12");
                createCell(1,11,"Rev. 00");   
                
                createCell(2,11,"Fecha aprobación: 30/05/15");
                createCell(3,11,"Vigencia: 15/06/15");


                createCell(4,0,"Contratista");
                createCell(4,1,"Choferes");
                createCell(4,3,"DNI");
                createCell(4,4,"Carnet: Conducir\nManejo Defensivo\nIngreso Yacimiento");   
                createCell(4,7,"Rastreo Satelital 2016: Presentado SI/NO");   

                createCell(5,1,"Apellido");
                createCell(5,2,"Nombres");
                createCell(5,3 ,"Número");
                createCell(5,4 ,"Carnet Conducir:\nFecha vnto.");
                createCell(5,5 ,"Carnet Manejo Defensivo:\nFecha vnto.");
                createCell(5,6 ,"Credencial YPF Ingreso Yac.:\nFecha vnto.");
                createCell(5,7 ,"Mayo-16");
                createCell(5,8,"Jun-16");
                createCell(5,9,"Jul-16");
                createCell(5,10,"Ago-16");
                createCell(5,11,"Sep-16");
                createCell(5,12,"Oct-16");
                createCell(5,13,"Nov-16");
                createCell(5,14,"Dic-16");
                createCell(4,15,"Revisado Firma");
                
                List<CellRangeAddress> lstRegiones = new ArrayList<CellRangeAddress>();
                
                lstRegiones.add(CellRangeAddress.valueOf("A1:B4"));                
                lstRegiones.add(CellRangeAddress.valueOf("C1:K4"));
                
                lstRegiones.add(CellRangeAddress.valueOf("L1:P1"));
                lstRegiones.add(CellRangeAddress.valueOf("L2:P2"));
                lstRegiones.add(CellRangeAddress.valueOf("L3:P3"));
                lstRegiones.add(CellRangeAddress.valueOf("L4:P4"));
                
                lstRegiones.add(CellRangeAddress.valueOf("A5:A6"));
                lstRegiones.add(CellRangeAddress.valueOf("B5:C5"));
                lstRegiones.add(CellRangeAddress.valueOf("H5:O5"));
                lstRegiones.add(CellRangeAddress.valueOf("P5:P6"));
                 
                lstRegiones.add(CellRangeAddress.valueOf("B6:B6"));
                lstRegiones.add(CellRangeAddress.valueOf("C6:C6"));
                lstRegiones.add(CellRangeAddress.valueOf("D6:D6"));
                lstRegiones.add(CellRangeAddress.valueOf("E6:E6"));
                lstRegiones.add(CellRangeAddress.valueOf("F6:F6"));
                lstRegiones.add(CellRangeAddress.valueOf("G6:G6"));
                lstRegiones.add(CellRangeAddress.valueOf("H6:H6"));
                lstRegiones.add(CellRangeAddress.valueOf("I6:I6"));
                lstRegiones.add(CellRangeAddress.valueOf("J6:J6"));
                lstRegiones.add(CellRangeAddress.valueOf("K6:K6"));
                lstRegiones.add(CellRangeAddress.valueOf("L6:L6"));
                lstRegiones.add(CellRangeAddress.valueOf("M6:M6"));
                lstRegiones.add(CellRangeAddress.valueOf("N6:N6"));
                lstRegiones.add(CellRangeAddress.valueOf("O6:O6"));
                

                CellStyle cellStyle = this.workbook.createCellStyle();    
                //XSSFCellStyle  cellStyle = (XSSFCellStyle) this.workbook.createCellStyle();    
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(FillPatternType.LEAST_DOTS);
                Font fuente = workbook.createFont();
                fuente.setFontHeightInPoints((short) 10);
                fuente.setBold(true);
                cellStyle.setFont(fuente);    
                CellRangeAddress reg = CellRangeAddress.valueOf("A1:P6");
                for (int row = reg.getFirstRow();row<=reg.getLastRow();row++){
                    Row r = this.sheet.getRow(row);
                    if (r==null)r = this.sheet.createRow(row);
                    for (int col = reg.getFirstColumn();col<=reg.getLastColumn();col++){
                        cell = r.getCell(col);
                        if(cell==null) cell = r.createCell(col);
                         cell.setCellStyle(cellStyle);
                        //System.out.println(String.format("%d %d",row,col));
                    }
                }
                //this.sheet.autoSizeColumn(1,true);
                for(CellRangeAddress region: lstRegiones){
                    sheet.addMergedRegion(region);
                    RegionUtil.setBorderBottom(BorderStyle.THIN, region, this.sheet);
                    RegionUtil.setBorderTop(BorderStyle.THIN,  region,this.sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THIN,  region,this.sheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN,  region,this.sheet);
                }
                
        } catch (IOException ex) {
            Logger.getLogger(VehiculoExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
 }   
 public static void main(String[] args){
     List<Transportista> lista = new TTransportista().getList();
     Proveedor proveedor = new TProveedor().getById(43);
     new TransportistaExcel("Transportistas",proveedor,lista).createExcel("c:\\transportistas.xlsx");
 }
}
