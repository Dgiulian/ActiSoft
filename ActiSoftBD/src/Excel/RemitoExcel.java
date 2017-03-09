/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import bd.Remito;
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
import transaccion.TRemito;
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
public class RemitoExcel extends BaseExcel<bd.Remito> {
    private List<Remito> lista ;   
    private HashMap<String,String> filtros;
    
    public RemitoExcel(String sheetname){
        super(sheetname);
    }
     public RemitoExcel(String sheetname, List<Remito> lista){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = new HashMap<String,String>();
    }
    public RemitoExcel(String sheetname, List<Remito> lista,HashMap filtros){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = filtros;        
    }
    @Override
    public boolean createExcel(String filename) {
        return this.createExcel(filename,this.lista);
    }
    public boolean createExcel(String filename, Integer pageNro) {
        List<Remito> list = new TRemito().getList();
        return this.createExcel(filename,list);
    }
    @Override
    public boolean createExcel(String filename, List<Remito> lista){
        
        HashMap<Integer, Pozo>         mapPozos = new TPozo().getMap();
        HashMap<Integer, Equipo>     mapEquipos = new TEquipo().getMap();
        HashMap<Integer, Cliente>   mapClientes = new TCliente().getMap();
        HashMap<Integer, Contrato> mapContratos = new TContrato().getMap();
        HashMap<Integer, OptionsCfg.Option> mapEstados = OptionsCfg.getMap( OptionsCfg.getEstadoRemitos());
        HashMap<Integer, OptionsCfg.Option> mapTipos = OptionsCfg.getMap( OptionsCfg.getTipoRemitos());
             
        this.estilo = setStyleBoldCenter();
        createHeader();
        Short rowIndex = new Integer(filtros.size() + 2).shortValue();
        this.estilo = estiloBase;
        Integer i = 1;
        for(Remito remito: lista ){
            String pozo = "",
                 equipo = "",
                 estado = "",
                cliente = "",
               contrato = "",
                   tipo = "",
              preticket = "";
            
            Pozo p = mapPozos.get(remito.getId_pozo());
            if (p!=null){
                pozo = p.getNombre();
            }
            Equipo e = mapEquipos.get(remito.getId_equipo());       
            if (e!=null) { equipo = e.getNombre(); };
            
            Cliente cli = mapClientes.get(remito.getId_cliente());       
            if (cli!=null) { cliente = cli.getNombre(); };
            
            Contrato c = mapContratos.get(remito.getId_contrato());       
            if (c!=null) { contrato = c.getNumero(); };

            OptionsCfg.Option o = mapEstados.get(remito.getId_estado());
            
            if(o!=null)
                estado = o.getDescripcion();

            o = mapTipos.get(remito.getId_tipo_remito());
            if(o!=null)
                tipo = o.getDescripcion();
            
            preticket = remito.getFacturado()==1?"Si":"No";
            sheet.createRow(rowIndex);
            i= 0;
        createCell(rowIndex,i++, remito.getNumero().toString());
        createCell(rowIndex,i++, tipo);
        createCell(rowIndex,i++, TFecha.formatearFechaBdVista(remito.getFecha()));
        createCell(rowIndex,i++, cliente);
        createCell(rowIndex,i++, contrato);
        createCell(rowIndex,i++, pozo);
        createCell(rowIndex,i++, equipo);            
        createCell(rowIndex,i++, estado);
        createCell(rowIndex,i++, preticket);
            rowIndex++;
        }
        sheet.autoSizeColumn(1);
        return save(filename);
    }

 public boolean createExcel(String filename, List<Remito> lista,HashMap filtros){
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
        createCell(rowIndex,i++, "Número");
        createCell(rowIndex,i++, "Tipo");
        createCell(rowIndex,i++, "Fecha");
        createCell(rowIndex,i++, "Cliente");
        createCell(rowIndex,i++, "Contrato");
        createCell(rowIndex,i++, "Pozo");
        createCell(rowIndex,i++, "Equipo");            
        createCell(rowIndex,i++, "Estado");
        createCell(rowIndex,i++, "Preticket");
        
    }

    

 public static void main(String[] args){
    HashMap<String,String> mapFiltro = new HashMap<String,String>();       
    mapFiltro.put("id_estado","2");
    List<Remito> lista = new ArrayList<Remito>();
    lista = new TRemito().getListaExport(mapFiltro);
                Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);

    boolean generado = false;
    String fileName  = "remitos.xlsx";
    String filePath  = parametro.getValor() + File.separator + fileName;
    
    mapFiltro.remove("id_estado");
    mapFiltro.put("Certificado","Si");
    mapFiltro.put("Estado","Disponible");
    mapFiltro.put("Stock","Todos");
    
    new RemitoExcel("Listado de Remitos",lista,mapFiltro).createExcel(filePath);
 }


   
}
