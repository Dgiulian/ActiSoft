/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Remito;
import bd.Certificado;
import bd.Cliente;
import bd.Contrato;
import bd.Parametro;
import bd.Pozo;
import bd.Equipo;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TRemito;
import transaccion.TCertificado;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TEquipo;

/**
 *
 * @author Diego
 */
public class RemitoExportPdf extends BasePdf{
    List<Remito> lista ;
    private Integer hoja = 0;
    private Integer num_hojas = 0;
    private HashMap<String,String> filtros;
    private final Integer NUM_REMITOS = 32;
    public RemitoExportPdf(List<Remito> lista){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_REMITOS) + 1 ;
        this.filtros = new HashMap<String,String>();        
    }
     public RemitoExportPdf(List<Remito> lista,HashMap<String,String> filtros){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_REMITOS) + 1;
        this.filtros = filtros;        
    }
    
    @Override
    protected void addContent(Document document) {
        
        newPage(document);
        
        PdfContentByte cb = docWriter.getDirectContent();        
        HashMap<Integer, Pozo>         mapPozos = new TPozo().getMap();
        HashMap<Integer, Equipo>     mapEquipos = new TEquipo().getMap();
        HashMap<Integer, Cliente>   mapClientes = new TCliente().getMap();
        HashMap<Integer, Contrato> mapContratos = new TContrato().getMap();
        
        HashMap<Integer, OptionsCfg.Option> mapEstados = OptionsCfg.getMap( OptionsCfg.getEstadoRemitos());
        HashMap<Integer, OptionsCfg.Option> mapTipos = OptionsCfg.getMap( OptionsCfg.getTipoRemitos());
        
        PdfPTable table = new PdfPTable(3);
        Integer start = 590;
        Integer cant = 0;        
        
        for(Remito remito: this.lista ){
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

            if(cant >= NUM_REMITOS){
                newPage(document);
                cant = 0;
                start = 590;
            }
           /*
                Numero
                Tipo remito
                Fecha
                Cliente
                Contrato
                Equipo
                Preticket
                Estado
                */
            addText(cb, 20,  start, remito.getNumero().toString());
            addText(cb, 80,  start, tipo);
            addText(cb, 140,  start, TFecha.formatearFechaBdVista(remito.getFecha()));
            addText(cb, 220,  start, cliente);
            addText(cb, 270,  start, contrato);
            addText(cb, 340,  start, pozo);
            addText(cb, 410,  start, equipo);            
            addText(cb, 470,  start, estado);
            addText(cb, 545,  start, preticket);

            start -= 18;
            cant++;
        }

    }
    private void newPage(Document document) {
        PdfContentByte cb = docWriter.getDirectContent();
        try{
           document.newPage();
           String IMAGE = "";
           Parametro image_url = new TParametro().getByCodigo(OptionsCfg.REMITO_EXPORT_IMAGE);
           if (image_url!=null) IMAGE = image_url.getValor();
           Image image = Image.getInstance(IMAGE);
           image.scaleAbsolute(PageSize.A4);
           image.setAbsolutePosition(0, 0);
           cb.addImage(image);
           cb.addImage(image);
           this.hoja++;
           Rectangle pageSize = document.getPageSize();
           addText(cb,pageSize.getWidth()/ 2,pageSize.getHeight() - 130,String.format("Hoja %02d/%02d",this.hoja,this.num_hojas));
           encabezado(cb);
        } catch (BadElementException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PreticketPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void encabezado(PdfContentByte cb){
        if (filtros==null) return;
        Integer start = 690;
        Integer lineHeight = 15;        
        Iterator<String> it = filtros.keySet().iterator();
        Integer i = 0;
        while(it.hasNext()){
            String key = it.next();
            String valor = filtros.get(key);
            addTextAligned(cb, 20, start - lineHeight * i,9,String.format("%s: %s",key,valor),Element.ALIGN_LEFT);
            i++;
        }        
    }
    
    public static void main(String[ ] args){
        
        
        //new TRemito().getList();
        HashMap<String,String> mapFiltro = new HashMap<String,String>();       
//        mapFiltro.put("id_estado","1");
//        mapFiltro.put("id_resultado",OptionsCfg.CERTIFICADO_VENCIDO.toString());
//        mapFiltro.put("id_cliente","5");
        mapFiltro.put("id_contrato","12");
        
        
        List<Remito> lista = new ArrayList<Remito>();
        lista = new TRemito().getListaExport(mapFiltro);
        Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);
            
        boolean generado = false;
        String fileName  = "remitos.pdf";
        String filePath  = parametro.getValor() + File.separator + fileName;
        System.out.println(filePath);
        System.out.println(lista.size());
        mapFiltro.clear();
        mapFiltro.put("Certificado","Si");
        mapFiltro.put("Estado","Disponible");
        mapFiltro.put("Stock","Todos");
        //mapFiltro.put("Contrato","Si");
        
        
        RemitoExportPdf remitoPdf = new RemitoExportPdf(lista,mapFiltro);
        remitoPdf.createPdf(filePath);
        remitoPdf.abrir(filePath);
    }
}
