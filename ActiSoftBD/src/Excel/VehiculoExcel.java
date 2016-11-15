/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import bd.Parametro;
import bd.Proveedor;
import bd.Vehiculo;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import transaccion.TParametro;
import transaccion.TProveedor;
import transaccion.TVehiculo;
import utils.OptionsCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class VehiculoExcel extends BaseExcel<bd.Vehiculo>{
    private List<Vehiculo> lista ;
    private Proveedor proveedor;
    
     public VehiculoExcel(String sheetname){
        super(sheetname);
    }
    public VehiculoExcel(String sheetname,Proveedor proveedor, List<Vehiculo> lista){
        super(sheetname);
        this.lista = lista;
        this.proveedor = proveedor;
    }
    @Override
    public boolean createExcel(String filename) {
        return this.createExcel(filename, this.lista);
    }

    @Override
    public boolean createExcel(String filename, List<Vehiculo> lista) {
        this.estilo = setStyleBoldCenter();
        
        createHeader();
        Short rowIndex = 6;
        Short lastIndex = rowIndex;
        Integer i;
        this.estilo = estiloBase;
        Proveedor proveedor_ant = null;
        for(Vehiculo vehiculo: lista ){
           sheet.createRow(rowIndex);
           i= 0;
           
           if(!proveedor.equals(proveedor_ant)){            
            createStyledCell(rowIndex,i,proveedor.getNombre_comercial(),10,false);
            proveedor_ant = proveedor;
           }
           i++;
           createCell(rowIndex,i++, vehiculo.getDominio());
           createCell(rowIndex,i++, vehiculo.getNumero_titulo().toString());
           createCell(rowIndex,i++, TFecha.formatearFechaBdVista(vehiculo.getVencimiento_cedula()));
           createCell(rowIndex,i++, vehiculo.getModelo());
           createCell(rowIndex,i++, TFecha.formatearFechaBdVista(vehiculo.getVencimiento_vtv()));
           
           createCell(rowIndex,i++, vehiculo.getSeguro_xantrax()==1?"Si":"No");
           createCell(rowIndex,i++, vehiculo.getSeguro());
           createCell(rowIndex,i++, vehiculo.getPoliza());
           
           rowIndex++;
        }
        
        sheet.addMergedRegion(CellRangeAddress.valueOf(String.format("A%d:A%d",lastIndex+1,rowIndex)));
        System.out.println(String.format("A%d:A%d",lastIndex+1,rowIndex+1));
        lastIndex = rowIndex;
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

               //set width to n character widths = count characters * 256
               //int widthUnits = 20*256;
               //sheet.setColumnWidth(1, widthUnits);

               //set height to n points in twips = n * 20
               //short heightUnits = 60*20;
               //sheet.setColumnWidth(1, widthUnits);

               //Write the Excel file
               
                createCell(0,2,"SOLICITUD DOCUMENTACIÓN VEHÍCULOS ALQUILADOS");
                createCell(0,17,"X-FG-13");
                createCell(1,17,"Rev. 00");   
                
                createCell(2,17,"Fecha aprobación: 31/05/15");
                createCell(3,17,"Vigencia: 16/06/15");

                createCell(4,0,"Contratista");
                createCell(4,1,"VEHÍCULO ALQUILADO");
                createCell(4,6,"SEGURO");
                createCell(4,9,"SERVICE");

                createCell(5,1,"Patente");
                createCell(5,2,"Nº Inscripcion Titulo");
                createCell(5,3 ,"Cedula Verde vnto.");
                createCell(5,4 ,"Modelo");
                createCell(5,5 ,"VTV vnto.");
                createCell(5,6 ,"A cargo de\nXantrax");
                createCell(5,7 ,"Aseguradora");
                createCell(5,8 ,"Nº Póliza");
                createCell(5,9 ,"A cargo de\nXantrax");
                createCell(5,10,"Mantenimiento\nKM");
                createCell(5,11,"Fecha última\nvez");
                createCell(5,12,"Posee");
                createCell(5,13,"Mayo-16");
                createCell(5,14,"Jun-16");
                createCell(5,15,"Jul-16");
                createCell(5,16,"Ago-16");
                createCell(5,17,"Sep-16");
                createCell(5,18,"Oct-16");
                createCell(5,19,"Nov-16");
                createCell(5,20,"Dic-16");
                createCell(4,21,"Revisado Firma");
                List<CellRangeAddress> lstRegiones = new ArrayList<CellRangeAddress>();
                lstRegiones.add(CellRangeAddress.valueOf("A1:B4"));
                lstRegiones.add(CellRangeAddress.valueOf("C1:Q4"));
                
                lstRegiones.add(CellRangeAddress.valueOf("R1:V1"));
                lstRegiones.add(CellRangeAddress.valueOf("R2:V2"));
                lstRegiones.add(CellRangeAddress.valueOf("R3:V3"));
                lstRegiones.add(CellRangeAddress.valueOf("R4:V4"));
                
                lstRegiones.add(CellRangeAddress.valueOf("A5:A6"));
                lstRegiones.add(CellRangeAddress.valueOf("B5:F5"));
                lstRegiones.add(CellRangeAddress.valueOf("G5:I5"));
                lstRegiones.add(CellRangeAddress.valueOf("J5:L5"));
                lstRegiones.add(CellRangeAddress.valueOf("M5:U5"));
                lstRegiones.add(CellRangeAddress.valueOf("V5:V6"));

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
                lstRegiones.add(CellRangeAddress.valueOf("P6:P6"));
                lstRegiones.add(CellRangeAddress.valueOf("Q6:Q6"));
                lstRegiones.add(CellRangeAddress.valueOf("R6:R6"));
                lstRegiones.add(CellRangeAddress.valueOf("S6:S6"));
                lstRegiones.add(CellRangeAddress.valueOf("T6:T6"));
                lstRegiones.add(CellRangeAddress.valueOf("U6:U6"));
                
                
                CellStyle cellStyle = this.workbook.createCellStyle();    
                //XSSFCellStyle  cellStyle = (XSSFCellStyle) this.workbook.createCellStyle();    
                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(CellStyle.LEAST_DOTS);
                Font fuente = workbook.createFont();
                fuente.setFontHeightInPoints((short) 10);
                fuente.setBold(true);
                cellStyle.setFont(fuente);    
                CellRangeAddress reg = CellRangeAddress.valueOf("A1:V6");
                for (int row = reg.getFirstRow();row<=reg.getLastRow();row++){
                    Row r = this.sheet.getRow(row);
                    if (r==null)r = this.sheet.createRow(row);
                    for (int col = reg.getFirstColumn();col<=reg.getLastColumn();col++){
                        Cell cell = r.getCell(col);
                        if(cell==null) cell = r.createCell(col);
                         cell.setCellStyle(cellStyle);
                        //System.out.println(String.format("%d %d",row,col));
                        
                    }
                }
                 short borderStyle = CellStyle.BORDER_THIN;
                  for(CellRangeAddress region: lstRegiones){
                    sheet.addMergedRegion(region);
                    RegionUtil.setBorderBottom(borderStyle, region, this.sheet, this.workbook);
                    RegionUtil.setBorderTop(borderStyle,  region,this.sheet, this.workbook);
                    RegionUtil.setBorderLeft(borderStyle,  region,this.sheet, this.workbook);
                    RegionUtil.setBorderRight(borderStyle,  region,this.sheet, this.workbook);
                  
                }
                
                
                
        } catch (IOException ex) {
            Logger.getLogger(VehiculoExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
 }   
   
 public static void main(String[] args){
     List<Vehiculo> lista = new TVehiculo().getList();
     Proveedor proveedor = new TProveedor().getById(43);
     
     new VehiculoExcel("Vehiculos",proveedor,lista).createExcel("c:\\vehiculos.xlsx");
 }
}
