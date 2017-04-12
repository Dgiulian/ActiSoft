/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import bd.Activo;
import bd.Certificado;
import bd.Equipo;
import bd.Parametro;
import bd.Pozo;
import bd.Remito;
import bd.Rubro;
import bd.Subrubro;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import transaccion.TActivo;
import transaccion.TCertificado;
import transaccion.TEquipo;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TRemito;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.OptionsCfg;


/**
 *
 * @author Diego
 */
public class ActivoExcel extends BaseExcel<bd.Activo> {
    private List<Activo> lista ;   
    private HashMap<String,String> filtros;
    
    public ActivoExcel(String sheetname){
        super(sheetname);
    }
     public ActivoExcel(String sheetname, List<Activo> lista){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = new HashMap<String,String>();
    }
    public ActivoExcel(String sheetname, List<Activo> lista,HashMap filtros){
        super(sheetname);    
        this.lista = lista;        
        this.filtros = filtros;        
    }
    @Override
    public boolean createExcel(String filename) {
        return this.createExcel(filename,this.lista);
    }
    public boolean createExcel(String filename, Integer pageNro) {
        List<Activo> list = new TActivo().getList(pageNro, 10);
        return this.createExcel(filename,list);
    }
    @Override
    public boolean createExcel(String filename, List<Activo> lista){
        HashMap<Integer, Rubro> mapRubros        = new TRubro().getMap();
        HashMap<Integer, Subrubro> mapSubrubros  = new TSubrubro().getMap();
        HashMap<Integer, Certificado> mapValidos = new TCertificado().getMapValidos(OptionsCfg.MODULO_ACTIVO);
        HashMap<Integer, OptionsCfg.Option> mapEstados = OptionsCfg.getMap( OptionsCfg.getEstadoActivo());
        
        this.estilo = setStyleBoldCenter();
        createHeader();
        Short rowIndex = new Integer(filtros.size() + 2).shortValue();
        this.estilo = estiloBase;
        Integer i = 1;
        TRemito tr = new TRemito ();
        Remito remito;
        TPozo tp = new TPozo();
        TEquipo te = new TEquipo();
        
        for(Activo activo: lista ){
            String rubro = activo.getId_rubro().toString();         
            String subrubro = activo.getId_subrubro().toString();
            String estado = activo.getId_estado().toString();
            String certificado = "";
            
            Rubro r = mapRubros.get(activo.getId_rubro());
            if (r!=null){
                rubro = r.getDescripcion();

            }
            Subrubro s = mapSubrubros.get(activo.getId_subrubro());       
            if (s!=null) {subrubro = s.getDescripcion();};

            OptionsCfg.Option o = mapEstados.get(activo.getId_estado());
            if(o!=null)
                estado = o.getDescripcion();

            Certificado cert = mapValidos.get(activo.getId());
            if(cert==null) certificado = "No";
            else { 
                if(cert.getId_resultado()==OptionsCfg.CERTIFICADO_VENCIDO)  certificado = "Vencido";                            
                else certificado = "Si";
            }
            sheet.createRow(rowIndex);
            i= 0;
            
            if(OptionsCfg.ACTIVO_ESTADO_ALQUILADO.equals(activo.getId_estado()))
                remito = tr.getByIdActivo(activo.getId(), OptionsCfg.REMITO_ENTREGA, OptionsCfg.REMITO_ESTADO_ABIERTO);
            else if (OptionsCfg.ACTIVO_ESTADO_KIT.equals(activo.getId_estado()))
                remito = tr.getByIdActivoEnKit(activo.getId(), OptionsCfg.REMITO_ENTREGA, OptionsCfg.REMITO_ESTADO_ABIERTO);
            else remito = null;
            String num_remito = "",
                        pozo  = "",
                       equipo = "";
            if(remito!=null){
                num_remito = remito.getNumero().toString();
                Pozo p = tp.getById(remito.getId_pozo());                
                Equipo e = te.getById(remito.getId_equipo());
                pozo = p!=null?p.getNombre():"";
                equipo = e!=null? e.getNombre():"";
                
            } 
            createCell(rowIndex,i++, activo.getCodigo());
            createCell(rowIndex,i++, activo.getDesc_larga());
            createCell(rowIndex,i++, estado);
            createCell(rowIndex,i++, String.format("%d",activo.getStock().intValue()));
            createCell(rowIndex,i++, num_remito);
            createCell(rowIndex,i++, pozo);
            createCell(rowIndex,i++, equipo);
            createCell(rowIndex,i++, certificado);
            rowIndex++;
        }
        sheet.autoSizeColumn(1);
        return save(filename);
    }

 public boolean createExcel(String filename, List<Activo> lista,HashMap filtros){
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
        createCell(rowIndex,i++,"Código");
        createCell(rowIndex,i++,"Descripcion");        
        createCell(rowIndex,i++,"Estado");
        createCell(rowIndex,i++,"Stock");
        createCell(rowIndex,i++,"Remito");
        createCell(rowIndex,i++,"Pozo");
        createCell(rowIndex,i++,"Equipo");
        createCell(rowIndex,i++,"Certificado");
        
    }

    

 public static void main(String[] args){
    HashMap<String,String> mapFiltro = new HashMap<String,String>();       
    mapFiltro.put("id_estado","2");
    List<Activo> lista = new ArrayList<Activo>();
    lista = new TActivo().getListaExport(mapFiltro);
                Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);

    boolean generado = false;
    String fileName  = "activos.xlsx";
    String filePath  = parametro.getValor() + File.separator + fileName;
    
    mapFiltro.remove("id_estado");
    mapFiltro.put("Certificado","Si");
    mapFiltro.put("Estado","Disponible");
    mapFiltro.put("Stock","Todos");
    
    new ActivoExcel("Listado de Activos",lista,mapFiltro).createExcel(filePath);
 }


   
}
