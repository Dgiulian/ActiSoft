/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import bd.Preticket;
import bd.Certificado;
import bd.Cliente;
import bd.Contrato;
import bd.Equipo;
import bd.Parametro;
import bd.Pozo;
import bd.Rubro;
import bd.Subrubro;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.util.CellRangeAddress;
import transaccion.TPreticket;
import transaccion.TCertificado;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TEquipo;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.OptionsCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class PreticketExcel extends BaseExcel<bd.Preticket> {
    private List<Preticket> lista ;   
    private HashMap<String,String> filtros;
    
    public PreticketExcel(String sheetname){
        super(sheetname);
    }
     public PreticketExcel(String sheetname, List<Preticket> lista){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = new HashMap<String,String>();
    }
    public PreticketExcel(String sheetname, List<Preticket> lista,HashMap filtros){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = filtros;        
    }
    @Override
    public boolean createExcel(String filename) {
        return this.createExcel(filename,this.lista);
    }
    public boolean createExcel(String filename, Integer pageNro) {
        List<Preticket> list = new TPreticket().getList();
        return this.createExcel(filename,list);
    }
    @Override
    public boolean createExcel(String filename, List<Preticket> lista){
        
        HashMap<Integer, Pozo>         mapPozos = new TPozo().getMap();
        HashMap<Integer, Equipo>     mapEquipos = new TEquipo().getMap();
        HashMap<Integer, Cliente>   mapClientes = new TCliente().getMap();
        HashMap<Integer, Contrato> mapContratos = new TContrato().getMap();

             
        this.estilo = setStyleBoldCenter();
        createHeader();
        Short rowIndex = new Integer(filtros.size() + 2).shortValue();
        this.estilo = estiloBase;
        Integer i = 1;
        for(Preticket preticket: lista ){
            String cliente = "",
                  contrato = "",
                      tipo = "";
            
            
            Cliente cli = mapClientes.get(preticket.getId_cliente());       
            if (cli!=null) { cliente = cli.getNombre(); };
            
            Contrato c = mapContratos.get(preticket.getId_contrato());       
            if (c!=null) { contrato = c.getNumero(); };

            
            sheet.createRow(rowIndex);
            i= 0;
        createCell(rowIndex,i++, preticket.getNumero().toString());
        createCell(rowIndex,i++, tipo);
        createCell(rowIndex,i++, TFecha.formatearFechaBdVista(preticket.getFecha()));
        createCell(rowIndex,i++, cliente);
        createCell(rowIndex,i++, contrato);
        createCell(rowIndex,i++, preticket.getNro_certificado());
        createCell(rowIndex,i++, preticket.getNro_habilitacion());
        createCell(rowIndex,i++, preticket.getNro_factura());
        createCell(rowIndex,i++, preticket.getTotal().toString());
            rowIndex++;
        }
        sheet.autoSizeColumn(1);
        return save(filename);
    }

 public boolean createExcel(String filename, List<Preticket> lista,HashMap filtros){
        this.lista = lista;        
        this.filtros = filtros;     
        return createExcel(filename,lista);
    }
 
    private void createHeader() {   
        Short rowIndex = 0;
        if (filtros!=null) {
            Iterator<String> it = filtros.keySet().iterator();
            
            while(it.hasNext()){
                String key = it.next();
                String valor = filtros.get(key);            
                sheet.createRow(rowIndex);
                createCell(rowIndex,0,key);
                createCell(rowIndex,1,valor);
                rowIndex++;
            }   
        }         
        rowIndex++;
        Integer i = 0;
        sheet.createRow(rowIndex);       
        createCell(rowIndex,i++, "Punto Venta");
        createCell(rowIndex,i++, "Número");       
        createCell(rowIndex,i++, "Fecha");
        createCell(rowIndex,i++, "Cliente");
        createCell(rowIndex,i++, "Contrato");
        createCell(rowIndex,i++, "Certificado");
        createCell(rowIndex,i++, "Habilitacion");            
        createCell(rowIndex,i++, "Factura");
        createCell(rowIndex,i++, "Total");
        
    }

    

 public static void main(String[] args){
    HashMap<String,String> mapFiltro = new HashMap<String,String>();       
    mapFiltro.put("id_estado","2");
    List<Preticket> lista = new ArrayList<Preticket>();
    lista = new TPreticket().getListFiltro(mapFiltro);
                Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);

    boolean generado = false;
    String fileName  = "pretickets.xlsx";
    String filePath  = parametro.getValor() + File.separator + fileName;
    
    mapFiltro.remove("id_estado");
    mapFiltro.put("Certificado","Si");
    mapFiltro.put("Estado","Disponible");
    mapFiltro.put("Stock","Todos");
    
    new PreticketExcel("Listado de Pretickets",lista,mapFiltro).createExcel(filePath);
 }


   
}
