/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Activo;
import bd.Cliente;
import bd.Contrato;
import bd.Parametro;
import bd.Remito;
import bd.Remito_detalle;
import bd.Remito_detalle_view;
import bd.Site;
import bd.Subrubro;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TParametro;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import transaccion.TRemito_detalle_view;
import transaccion.TSite;
import transaccion.TSubrubro;

/**
 *
 * @author Diego
 */
public class RemitoPdf extends BasePdf {
     private Remito remito;
     private Cliente cliente;
     private Contrato contrato; 
     
     private Site site;
     List<Remito_detalle_view> detalle;
     
     
     public RemitoPdf(Remito remito){
         this.remito = remito;
         this.cliente = new TCliente().getById(remito.getId_cliente());
         this.contrato = new TContrato().getById(remito.getId_contrato());
         this.detalle = new TRemito_detalle_view().getByRemitoId(remito.getId());    
         
         this.site    = new TSite().getById(remito.getId_site());
     }
     
     @Override
     protected void addContent(Document document){
           PdfContentByte cb = docWriter.getDirectContent();
//           Integer start = 670;

           
           printHeader(cb);
           Integer start = 570;
           Integer lineHeight = 16;
           Integer i = 0;
//           TActivo ta = new TActivo();
//           Activo a ;
           HashMap<Integer, Object[]> agrupado = groupBySubrubro(detalle);
           HashMap<Integer, Subrubro> mapaSubrubro = new TSubrubro().getMap();
           Set<Integer> subrubros = agrupado.keySet();
            for(Integer s:subrubros){
               Object[] value = agrupado.get(s);
               if(value==null) continue;
               Integer pos = (Integer) value[0];
               Float cant = (Float) value[1];
               
               Subrubro subrubro = mapaSubrubro.get(s);
               addText(cb,45,start - lineHeight * i ,pos.toString());
               addText(cb,84,start - lineHeight * i,subrubro.getCodigo());
               addText(cb,134,start - lineHeight * i ,subrubro.getDescripcion());
               addText(cb,514,start - lineHeight * i,cant.toString());
               i++;
             }
           printFooter(cb);
           
           // Segunda Hoja
           
           
           document.newPage();
           printHeader(cb);
           start = 570;
           lineHeight = 16;
           i = 0;
           for(Remito_detalle_view dm:this.detalle){
//            a = ta.getById(dm.getId_activo());
            addText(cb,45,start - lineHeight * i ,dm.getPosicion().toString());
            addText(cb,84,start - lineHeight * i,dm.getCodigo());
            addText(cb,134,start - lineHeight * i ,dm.getDesc_larga());
            addText(cb,514,start - lineHeight * i,dm.getCantidad().toString());
            i++;
//            if(i>=2){
//                printFooter(cb);
//                document.newPage();
//                printHeader(cb);
//                i=0;
//            }
           }
           start = 345;
           printFooter(cb);           
    }
     private HashMap<Integer,Object[]> groupBySubrubro(List<Remito_detalle_view> detalle){
         HashMap<Integer,Object[]> mapa = new HashMap<>();
         for(Remito_detalle_view d:detalle){
             Integer id_subrubro = d.getId_subrubro();
             Object [] value = mapa.get(d.getId_subrubro());
             
             if(value==null) {
                Object[] o = {d.getPosicion(),d.getCantidad()};
                 mapa.put(id_subrubro, o);
             }
             else {
                 Float cant = (Float) value[1];
                 value[1] = cant + d.getCantidad();
                mapa.put(id_subrubro,value);
             }
         }
         return mapa;
     }
     private void printHeader(PdfContentByte cb)    {
                     String IMAGE = "e:\\ActiSoft\\fondo_remito.jpg";
            
            Image image;
         try {
             image = Image.getInstance(IMAGE);
             image.setWidthPercentage(100);
             
             image.setAbsolutePosition(0, 0);
//             cb.addImage(image);
         } catch (BadElementException ex) {
             Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
         } catch (MalformedURLException ex) {
             Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
         } catch (DocumentException ex) {
             Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
         }
         Integer start = 678;
        Integer lineHeight = 15;
        if(this.cliente!=null) {
         addText(cb,80,start,cliente.getNombre_comercial());
         addText(cb,80,start - lineHeight,cliente.getDireccion_fisica());           
         addText(cb,425,start - lineHeight,cliente.getCuit());
        }
        // Contrato
        start -= 48;
//           start -= 43;
        if(this.contrato!=null)
             addText(cb,215,start  ,contrato.getNumero().toString());
//           addText(cb,205,start  ,remito.getNumero().toString());
        start = 636;
        if(this.site!=null){
             addText(cb,345,start,site.getArea());
             addText(cb,345,start - lineHeight,site.getPozo());
             addText(cb,480,start - lineHeight,site.getEquipo());
        }
 }
     private void printFooter(PdfContentByte cb){
           Integer start = 225;          
           if (remito.getId_tipo_remito()==OptionsCfg.REMITO_ENTREGA)
               addText(cb,140,start,"X");
           else if (remito.getId_tipo_remito()==OptionsCfg.REMITO_DEVOLUCION)
               addText(cb,480,start,"X");   
           if(this.remito.getId_tipo_remito()==OptionsCfg.REMITO_DIARIO){
             Remito referencia = new TRemito().getById(this.remito.getId_referencia());
              
            addText(cb,100,start - 17,String.format("Remito diario creado a partir del remito %s",referencia.getNumero()));
           }
     }
     public static void main(String[] args){
        
        Remito remito = new TRemito().getById(60);
        String fileName = String.format("c:\\remito_%d.pdf",remito.getNumero());
        RemitoPdf rm = new RemitoPdf(remito);
        rm.createPdf(fileName);
        System.out.println("Imprimiendo documento");
//        rm.abrir(fileName);
        rm.imprimir(fileName);
        
    }
}
