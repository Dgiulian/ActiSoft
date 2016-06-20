/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Activo;
import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
import bd.Kit;
import bd.Kit_detalle;
import bd.Parametro;
import bd.Remito;
import bd.Remito_detalle;
import bd.Remito_detalle_view;
import bd.Site;
import bd.Subrubro;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import java.sql.Array;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import transaccion.TActivo;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TKit;
import transaccion.TKit_detalle;
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
     private List<Contrato_detalle> contrato_detalle;
     private List<Remito_detalle> remito_detalle;
     private Site site;
     private List<Remito_detalle_view> detalle;
     private HashMap<Integer,Activo> mapActivos;
     public RemitoPdf(Remito remito){
         this.remito = remito;
         this.cliente = new TCliente().getById(remito.getId_cliente());
         this.contrato = new TContrato().getById(remito.getId_contrato());
         this.contrato_detalle = new TContrato_detalle().getByContratoId(remito.getId_contrato());
         this.detalle = new TRemito_detalle_view().setOrderBy(" order by remito_detalle_view.posicion ").getByRemitoId(remito.getId());
         this.site    = new TSite().getById(remito.getId_site());
         
         this.remito_detalle = new TRemito_detalle().getByRemitoId(remito.getId());
         mapActivos = new HashMap<Integer,Activo>();
     }
     
     @Override
     protected void addContent(Document document){
           PdfContentByte cb = docWriter.getDirectContent();
//           Integer start = 670;
           
           addText(cb,470,760,TFecha.formatearFechaBdVista(remito.getFecha()));
           
           Integer start = 690;
           printHeader(cb,start);
           printSite(cb,645);
           
           
           start = 577;
           Float lineHeight = 16f;
           Integer i = 0;
           TKit tk = new TKit();
           HashMap<Integer, Object[]> agrupado = groupByPosicion(detalle);
           HashMap<Integer, Subrubro> mapSubrubro = new TSubrubro().getMap();
           HashMap<Integer,Contrato_detalle> mapPosicion = new HashMap();
           HashMap<Integer,Kit> mapKit = new HashMap<Integer,Kit>();
           for(Contrato_detalle cd:this.contrato_detalle){
               mapPosicion.put(cd.getPosicion(),cd);
           }
           Set<Integer> posiciones = new TreeSet<>(agrupado.keySet());
           for(Integer s:posiciones){
               Object[] value = agrupado.get(s);
               if(value==null) continue;
               Integer pos = (Integer) value[0];
               Float cant  = (Float)   value[1];
               
               Subrubro subrubro = mapSubrubro.get(s);
               Contrato_detalle d = mapPosicion.get(pos);
               
               addText(cb,80,start - lineHeight * i ,pos.toString());
               if(d!=null){
                addText(cb,134,start - lineHeight * i ,d.getDescripcion());
               }
               addText(cb,514,start - lineHeight * i,String.format("%.2f",cant));
               i++;
             }
           
           
           for(Remito_detalle rd:this.remito_detalle){
               if (rd.getId_kit()==0) continue;
               
               Kit kit = tk.getById(rd.getId_kit());
               if(kit==null) continue;
               
               mapKit.put(kit.getId(),kit);
               addText(cb,80,start  - lineHeight * i,rd.getPosicion().toString());
               addText(cb,134,start - lineHeight * i,kit.getNombre());               
               addText(cb,514,start - lineHeight * i,String.format("%.2f",rd.getCantidad()));
               i++;
           }
           //
            printFooter(cb,225);
           
            // Segunda Hoja
            document.newPage();
            String IMAGE = "";
            Parametro image_url = new TParametro().getByCodigo(OptionsCfg.REMITO_IMAGE);
            if (image_url!=null) IMAGE = image_url.getValor();

            Image image = getImage(IMAGE);
            if (image!=null){
                try {
                    image.scaleAbsolute(PageSize.A4);
                    image.setAbsolutePosition(0, 0);
                    cb.addImage(image);
                } catch (DocumentException ex) {
                    Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           start = 685;           
           this.addText(cb, 503, 775, String.format("%03d-%d",this.remito.getPunto_venta(),this.remito.getNumero()));
           this.addText(cb, 503, 745, TFecha.formatearFecha(this.remito.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
           printHeader(cb,start);
           printSite(cb,646);
           start = 594;
           lineHeight = 20.8f;
                   ;
           i = 0;
           List<Remito_detalle_view> listaOpcional = new ArrayList();
           String descripcion = "";
           for(Remito_detalle_view dm:this.detalle){
//            a = ta.getById(dm.getId_activo());
            addText(cb,30,start - lineHeight * i ,dm.getPosicion().toString());
            addText(cb,73,start - lineHeight * i,dm.getCodigo());
            if (!dm.getDesc_opcional().equals("") ||
                !dm.getSubrubro_opcional().equals("") ||
                !dm.getRubro_opcional().equals("") )
            {
                
                listaOpcional.add(dm);
                if(!dm.getDesc_opcional().equals(""))
                   descripcion = dm.getDesc_opcional();
                else if(!dm.getSubrubro_opcional().equals(""))
                    descripcion = dm.getSubrubro_opcional();
                else if(!dm.getRubro_opcional().equals("") )
                   descripcion = dm.getRubro_opcional();
                 
            } else {descripcion = dm.getDesc_larga();}
            addText(cb,130,start - lineHeight * i ,descripcion);
            addText(cb,520,start - lineHeight * i,dm.getCantidad().toString());
            i++;

           }
           for(Remito_detalle rd:this.remito_detalle){
               if (rd.getId_kit()==0) continue;
               Kit kit = mapKit.get(rd.getId_kit());
               if(kit==null) continue;
               addText(cb,30, start - lineHeight * i, rd.getPosicion().toString());
               addText(cb,73, start - lineHeight * i, kit.getCodigo());
               addText(cb,130,start - lineHeight * i, getCodigos_detalle(kit));
               addText(cb,520,start - lineHeight * i, rd.getCantidad().toString());
            i++;

           }
           start = 208;
           printFooter(cb,start);     
           
           if(listaOpcional.size()>0) {
                // Tercer Hoja
                 document.newPage();            
                 if (image!=null){
                     try {
                         image.scaleAbsolute(PageSize.A4);
                         image.setAbsolutePosition(0, 0);
                         cb.addImage(image);
                     } catch (DocumentException ex) {
                         Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
                start = 685;           
                this.addText(cb, 503, 775, String.format("%03d-%d",this.remito.getPunto_venta(),this.remito.getNumero()));
                this.addText(cb, 503, 745, TFecha.formatearFecha(this.remito.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
                printHeader(cb,start);
                printSite(cb,646);
                start = 594;
                lineHeight = 20.8f;
                        ;
                i = 0;
                for(Remito_detalle_view dm:listaOpcional){
     //            a = ta.getById(dm.getId_activo());
                 addText(cb,35,start  - lineHeight * i, dm.getPosicion().toString());
                 addText(cb,73,start  - lineHeight * i, dm.getCodigo());
                 addText(cb,130,start - lineHeight * i, dm.getDesc_larga());
                 addText(cb,520,start - lineHeight * i, dm.getCantidad().toString());
                 i++;
     //            if(i>=2){
     //                printFooter(cb);
     //                document.newPage();
     //                printHeader(cb);
     //                i=0;
     //            }
                }
                start = 208;
                printFooter(cb,start);     
           }
           
    }
    
     private void printHeader(PdfContentByte cb,Integer start)    {
         String IMAGE = "";
        //Integer start = 678;
        Integer lineHeight = 15;
        if(this.cliente!=null) {
         addText(cb,75,start,cliente.getNombre_comercial());
         addText(cb,75,start - lineHeight,cliente.getDireccion_fisica());           
         addText(cb,425,start - lineHeight,cliente.getCuit());
        }
        // Contrato
        start -= 48;
//           start -= 43;
        if(this.contrato!=null)
//             addText(cb,210,start  ,contrato.getNumero().toString());
           addText(cb,205,start  ,contrato.getNumero().toString());
        
 }
     public void printSite(PdfContentByte cb,Integer start){
         //start = 636;
        Integer lineHeight = 13;
        if(this.site!=null){
             addText(cb,345,start,site.getArea());
             addText(cb,345,start - lineHeight,site.getPozo());
             addText(cb,480,start - lineHeight,site.getEquipo());
        }
     }
     private void printFooter(PdfContentByte cb,Integer start){
           //Integer start = 225;          
           if (remito.getId_tipo_remito()==OptionsCfg.REMITO_ENTREGA)
               addText(cb,135,start,"X");
           else if (remito.getId_tipo_remito()==OptionsCfg.REMITO_DEVOLUCION)
               addText(cb,480,start,"X");   
           if(this.remito.getId_tipo_remito()==OptionsCfg.REMITO_DIARIO){
             Remito referencia = new TRemito().getById(this.remito.getId_referencia());              
            addText(cb,100,start - 17,String.format("Remito diario creado a partir del remito %s",referencia.getNumero()));
           }
           //addText(cb,100,start - 27,remito.getObservaciones());
           imprimirObservaciones(cb, start - 27,remito.getObservaciones());
           
     }
     
     public void imprimirObservaciones(PdfContentByte cb,Integer start,String observaciones){
         Integer lineHeight = 10;
         Integer lineWidth = 170;
         Integer startIndex = 0;
         Integer lines = observaciones.length() / lineWidth;
         for(int i = 0;i<=lines;i++){
             
             startIndex = lineWidth * i;
             Integer endIndex = Math.min(startIndex + lineWidth,observaciones.length());
             addText(cb,30,start - i * lineHeight,observaciones.substring(startIndex,endIndex));
         }
     }
  private HashMap<Integer,Object[]> groupBySubrubro(List<Remito_detalle_view> detalle){
         HashMap<Integer,Object[]> mapa = new HashMap<>();
         for(Remito_detalle_view d:detalle){
             Integer id_subrubro = d.getId_subrubro();
             Object [] value = mapa.get(d.getId_subrubro());
             if(value==null) {
                Object[] o = new Object[2];
                o[0] = d.getPosicion();
                if (d.getLongitud().equals("")) {
                    o[1] = d.getCantidad();
                } else {
                    o[1] =  d.getCantidad() * d.getExtension(); 
                }
                mapa.put(id_subrubro, o);
             }
             else {
                Float cant = (Float) value[1];
                if(d.getLongitud().equals(""))
                    value[1] = cant + d.getCantidad();
                else 
                    value[1] = cant +  d.getCantidad() * d.getExtension();
                mapa.put(id_subrubro,value);
             }
         }
         return mapa;
     }
     private HashMap<Integer,Object[]> groupByPosicion(List<Remito_detalle_view> detalle){
         HashMap<Integer,Object[]> mapa = new HashMap<>();
         for(Remito_detalle_view d:detalle){             
             Integer posicion = d.getPosicion();             
             Object [] value = mapa.get(posicion);
             if(value==null) { // Si es nuevo
                Object[] o = new Object[2];
                o[0] = d.getPosicion();
                if (d.getLongitud().equals("")) {
                    o[1] = d.getCantidad();
                } else {
                    o[1] =  d.getCantidad() * d.getExtension(); 
                }
                mapa.put(posicion, o);
             }
             else {
                Float cant = (Float) value[1];
                if(d.getExtension()==0)
                    value[1] = cant + d.getCantidad();
                else 
                    value[1] = cant +  d.getCantidad() * d.getExtension();
                mapa.put(posicion,value);
             }
         }
         return mapa;
     }
     private String getCodigos_detalle(Kit kit){
         String codigos = "";
         TKit_detalle tkd = new TKit_detalle();
         List<Kit_detalle> detalle_kit = tkd.getByKitId(kit.getId());
         if(detalle_kit==null) return codigos;
         TActivo ta = new TActivo();
         
         for(Kit_detalle kd:detalle_kit){
            Activo activo = mapActivos.get(kd.getId_activo());
            if (activo == null){
                 activo = ta.getById(kd.getId_activo());
                 if (activo==null) continue;
                 mapActivos.put(kd.getId_activo(),activo);
            }              
            codigos += activo.getCodigo()  + " - ";
         } 
         if(codigos.length()>0) codigos = codigos.substring(0, codigos.length()-3);
         return codigos;
     }
//     private Float parsearLongitud(String longitud){
//        Float lng = 0f;
//        Pattern pattern = Pattern.compile("(\\d+,?\\d*) MTS"); //this clearly will find 13 consecutive numbers, but I need it to ignore the "-" character
//        Matcher matcher = pattern.matcher(longitud); 
//        
//        if(matcher.find()){            
//            lng = Parser.parseFloat(matcher.group(matcher.groupCount()).replace(",", "."));
//        }
//         //System.out.println(lng);
//        return lng;
//     }
    public static void main(String[] args){
       
        Remito remito = new TRemito().getById(194);
        String fileName = String.format("c:\\remito_%d.pdf",remito.getNumero());
        RemitoPdf rm = new RemitoPdf(remito);
        rm.createPdf(fileName);
        System.out.println("Imprimiendo documento");
        rm.abrir(fileName);
//        rm.imprimir(fileName);

//TActivo ta = new TActivo();
//    System.out.println(ta.getList("select * from activo").size());
//    System.out.println(ta.getList("select * from activo where activo.bloqueado = 0").size());
//    
//    HashMap<String,String> lstFiltro = new HashMap<String,String>();
//    lstFiltro.put("bloqueado","0");
//        System.out.println(ta.getListFiltro(lstFiltro).size());
    }
    
}
