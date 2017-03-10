/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Activo;
import bd.Certificado;
import bd.Equipo;
import bd.Parametro;
import bd.Pozo;
import bd.Remito;
import bd.Rubro;
import bd.Subrubro;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TCertificado;
import transaccion.TEquipo;
import transaccion.TParametro;
import transaccion.TPozo;
import transaccion.TRemito;
import transaccion.TRubro;
import transaccion.TSubrubro;

/**
 *
 * @author Diego
 */
public class ActivoExportPdf extends BasePdf{
    List<Activo> lista ;
    private Integer hoja = 0;
    private Integer num_hojas = 0;
    private HashMap<String,String> filtros;
    private final Integer NUM_ACTIVOS = 32;
    public ActivoExportPdf(List<Activo> lista){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_ACTIVOS) + 1 ;
        this.filtros = new HashMap<String,String>();        
    }
     public ActivoExportPdf(List<Activo> lista,HashMap filtros){
        this.lista = lista;
        this.num_hojas = (this.lista.size() / NUM_ACTIVOS) + 1;
        this.filtros = filtros;        
    }
    
    @Override
    protected void addContent(Document document) {
        
        newPage(document);
        
        PdfContentByte cb = docWriter.getDirectContent();        
        HashMap<Integer, Rubro> mapRubros        = new TRubro().getMap();
        HashMap<Integer, Subrubro> mapSubrubros  = new TSubrubro().getMap();
        HashMap<Integer, Certificado> mapValidos = new TCertificado().getMapValidos();
        HashMap<Integer, OptionsCfg.Option> mapEstados = OptionsCfg.getMap( OptionsCfg.getEstadoActivo());
        
        PdfPTable table = new PdfPTable(3);
        Integer start = 590;
        Integer cant = 0;        
        TRemito tr = new TRemito();
        TPozo tp   = new TPozo();
        TEquipo te = new TEquipo();
        Remito remito;
        String num_remito, pozo, equipo;
        
        for(Activo activo: this.lista ){
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
            if (r!=null && r.getAplica_certificado()==0){
                certificado = "No aplica";
            } else {
                Certificado cert = mapValidos.get(activo.getId());
                if(cert==null) certificado = "No";
                else { 
                    if(Objects.equals(cert.getId_resultado(), OptionsCfg.CERTIFICADO_VENCIDO))  certificado = "Vencido";                            
                    else certificado = "Si";
                }
            }
            if(cant >= NUM_ACTIVOS){
                newPage(document);
                cant = 0;
                start = 590;
            }
            if(OptionsCfg.ACTIVO_ESTADO_ALQUILADO.equals(activo.getId_estado()))
                remito = tr.getByIdActivo(activo.getId(), OptionsCfg.REMITO_ENTREGA, OptionsCfg.REMITO_ESTADO_ABIERTO);
            else if (OptionsCfg.ACTIVO_ESTADO_KIT.equals(activo.getId_estado()))
                remito = tr.getByIdActivoEnKit(activo.getId(), OptionsCfg.REMITO_ENTREGA, OptionsCfg.REMITO_ESTADO_ABIERTO);
            else remito = null;
            
            num_remito = "";
            pozo  = "";
            equipo = "";
            if(remito!=null){
                num_remito = remito.getNumero().toString();
                Pozo p = tp.getById(remito.getId_pozo());                
                Equipo e = te.getById(remito.getId_equipo());
                pozo = p!=null?p.getNombre():"";
                equipo = e!=null? e.getNombre():"";
                
            } 
            
            /*
             * Código
             * Descripción
             * Estado
             * Stock
             * Certificado
             * remito
             * pozo
             * equipo
            */
            addText(cb, 20,  start, activo.getCodigo());
            addText(cb, 80,  start, activo.getDesc_larga().substring(0, Math.min(71,activo.getDesc_larga().length())));
            
            addText(cb, 320, start, estado);
            addText(cb, 370, start, String.format("%d",activo.getStock().intValue()));
            addText(cb, 400, start, certificado);
            
            addText(cb, 445, start, num_remito);
            addText(cb, 490, start, pozo);
            addText(cb, 532, start, equipo);

            start -= 18;
            cant++;
        }

    }
    private void newPage(Document document) {
        PdfContentByte cb = docWriter.getDirectContent();
        try{
           document.newPage();
           String IMAGE = "";
           Parametro image_url = new TParametro().getByCodigo(OptionsCfg.ACTIVO_EXPORT_IMAGE);
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
        
        
        //new TActivo().getList();
        HashMap<String,String> mapFiltro = new HashMap<String,String>();       
        mapFiltro.put("id_estado","9");
//        mapFiltro.put("id_resultado",OptionsCfg.CERTIFICADO_VENCIDO.toString());
//        mapFiltro.put("id_cliente","5");
//        mapFiltro.put("id_contrato","12");
        
        
        List<Activo> lista = new ArrayList<Activo>();
        lista = new TActivo().getListaExport(mapFiltro);
        Parametro parametro = new TParametro().getByCodigo(OptionsCfg.EXPORT_PATH);
            
        boolean generado = false;
        String fileName  = "activos.pdf";
        String filePath  = parametro.getValor() + File.separator + fileName;
        System.out.println(filePath);
        System.out.println(lista.size());
        mapFiltro.clear();
        mapFiltro.put("Certificado","Si");
        mapFiltro.put("Estado","Disponible");
        mapFiltro.put("Stock","Todos");
        //mapFiltro.put("Contrato","Si");
        
        
        ActivoExportPdf activoPdf = new ActivoExportPdf(lista,mapFiltro);
        activoPdf.createPdf(filePath);
        activoPdf.abrir(filePath);
    }
}
