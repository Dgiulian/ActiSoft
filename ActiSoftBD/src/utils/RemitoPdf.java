/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bd.Activo;
import bd.Certificado;
import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
import bd.Equipo;
import bd.Kit;
import bd.Kit_detalle;
import bd.Parametro;
import bd.Pozo;
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
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger; 
import transaccion.TActivo;
import transaccion.TCertificado;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TEquipo;
import transaccion.TKit;
import transaccion.TKit_detalle;
import transaccion.TParametro;
import transaccion.TPozo;
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
           
           addText(cb,480,745,TFecha.formatearFechaBdVista(remito.getFecha()));
           
            
           printHeader(cb,675);
           printSite(cb,625);
           
           Integer start = 553;
           Float lineHeight = 19.5f;
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
           for(Integer s: posiciones){
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
               addText(cb,534,start - lineHeight * i,String.format("%.2f",cant));
               i++;
             }
        
           List<String> lstCodigosActivos = new ArrayList<String>();
           TActivo ta = new TActivo();
           for(Remito_detalle rd:this.remito_detalle){
               if (rd.getId_kit()==0) {
                   Activo activo = ta.getById(rd.getId_activo());
                   if (activo==null) continue;
                   lstCodigosActivos.add(activo.getCodigo());
                   
               } else {
                    Kit kit = tk.getById(rd.getId_kit());
                    if(kit==null) continue;
                    mapKit.put(kit.getId(),kit);
                    lstCodigosActivos.addAll(getCodigos_detalle(kit));
                    addText(cb,80,start  - lineHeight * i,rd.getPosicion().toString());
                    addText(cb,134,start - lineHeight * i,kit.getNombre());               
                    addText(cb,534,start - lineHeight * i,String.format("%.2f",rd.getCantidad()));
                    i++;
               }
               
           }
           String codigosActivos = "";
           for(String codigo: lstCodigosActivos){
               
                if(codigosActivos.length() + codigo.length()> 100){
                    codigosActivos = codigosActivos.substring(0, codigosActivos.length()-3); // extraigo el - final
                    addText(cb,130,start - lineHeight * i, codigosActivos);
                    codigosActivos = "";
                    i++;                       
                }
                codigosActivos += codigo + " - ";
            }
            if(!codigosActivos.equals("")) {
                codigosActivos = codigosActivos.substring(0, codigosActivos.length()-3); // extraigo el - final
                    addText(cb,130,start - lineHeight * i, codigosActivos);                    
            }
            
            printFooter(cb,177);
           
            // Segunda Hoja           
           String IMAGE_FILE = "";
           Parametro image_url = new TParametro().getByCodigo(OptionsCfg.REMITO_IMAGE);
           if (image_url!=null) IMAGE_FILE = image_url.getValor();
           document.newPage();
           addBackgroundImg(cb,IMAGE_FILE);           
           this.addText(cb, 503, 775, String.format("%03d-%d",this.remito.getPunto_venta(),this.remito.getNumero()));
           this.addText(cb, 503, 745, TFecha.formatearFecha(this.remito.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
           printHeader(cb,685);
           printSite(cb,646);
           
           start = 594;
           lineHeight = 20.8f;
                   
           i = 0;
           List<Remito_detalle_view> listaOpcional = new ArrayList();
           String descripcion = "";
           for(Remito_detalle_view dm: this.detalle){
//            a = ta.getById(dm.getId_activo());
            if (i >= 19){
                document.newPage();
                addBackgroundImg(cb,IMAGE_FILE);           
                this.addText(cb, 503, 775, String.format("%03d-%d",this.remito.getPunto_venta(),this.remito.getNumero()));
                this.addText(cb, 503, 745, TFecha.formatearFecha(this.remito.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
                printHeader(cb,685);
                printSite(cb,646);
                i = 0;
            }
            descripcion = dm.getDescripcion();
            if(!descripcion.equals(dm.getDesc_larga())) listaOpcional.add(dm);
            addText(cb,30,start - lineHeight * i,dm.getPosicion().toString());
            addText(cb,73,start - lineHeight * i,dm.getCodigo());
            addText(cb,130,start - lineHeight * i ,descripcion);
            addText(cb,520,start - lineHeight * i,dm.getCantidad().toString());
            i++;
           }
           /* Imprime los códigos de Kit */
           List<String> lstCodigosKit;
           for(Remito_detalle rd:this.remito_detalle){
               if (rd.getId_kit()==0) continue;
               Kit kit = mapKit.get(rd.getId_kit());
               if(kit==null) continue;
               addText(cb,30, start - lineHeight * i, rd.getPosicion().toString());
               addText(cb,73, start - lineHeight * i, kit.getCodigo());
               addText(cb,520,start - lineHeight * i, rd.getCantidad().toString());
               String codigos = "";
               lstCodigosKit = getCodigos_detalle(kit);
               for(String codigo: lstCodigosKit){  
                   if(codigos.length() + codigo.length()> 100){      
                       codigos = codigos.substring(0, codigos.length()-3); // extraigo el - final
                       addText(cb,130,start - lineHeight * i, codigos);
                       codigos = "";
                       i++;
                       
                   } 
                   codigos += codigo + " - ";
               }
               if(!codigos.equals("")) addText(cb,130,start - lineHeight * i, codigos);
               
            i++;

           }
           start = 208;
           printFooter(cb,start);     
           
           if(listaOpcional.size()>0) {
                // Tercer Hoja
                 document.newPage();            
                addBackgroundImg(cb,IMAGE_FILE);
                
                start = 685;           
                this.addText(cb, 503, 775, String.format("%03d-%d",this.remito.getPunto_venta(),this.remito.getNumero()));
                this.addText(cb, 503, 735, TFecha.formatearFecha(this.remito.getFecha(), TFecha.formatoBD, TFecha.formatoVista));
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
//                     document.newPage();
     //                printHeader(cb);
     //                i=0;
     //            }
                }
                start = 208;
                printFooter(cb,start);     
           }
          combinarCertificados(document);
    }
    private void addBackgroundImg(PdfContentByte cb, String IMAGE_FILE){
        Image image = getImage(IMAGE_FILE);
        if (image!=null){
            try {
                image.scaleAbsolute(PageSize.A4);
                image.setAbsolutePosition(0, 0);
                cb.addImage(image);
            } catch (DocumentException ex) {
                Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     private void printHeader(PdfContentByte cb,Integer start) {
        Integer lineHeight = 15;
        if(this.cliente!=null) {
         addText(cb,70,start,cliente.getNombre_comercial());
         addText(cb,70,start - lineHeight,cliente.getDireccion_fisica());           
         addText(cb,445,start - lineHeight,cliente.getCuit());
        }
        // Contrato
        start -= 50;
        if(this.contrato!=null)
           addText(cb,205,start  ,contrato.getNumero().toString());
 }
     public void printSite(PdfContentByte cb,Integer start){
        Integer lineHeight = 13;
        Pozo p   = new TPozo().getById(this.remito.getId_pozo());
        Equipo e = new TEquipo().getById(this.remito.getId_equipo());
        if(this.site!=null){
            String pozo = p!=null?p.getNombre():site.getPozo();
            String equipo = e!=null?e.getNombre():site.getEquipo();
            addText(cb,345,start,site.getArea());
            addText(cb,345,start - lineHeight,pozo);
            addText(cb,480,start - lineHeight,equipo);
        }
     }
     private void printFooter(PdfContentByte cb,Integer start){
           //Integer start = 225;          
           if (remito.getId_tipo_remito().equals(OptionsCfg.REMITO_ENTREGA))
               addText(cb,135,start,"X");
           else if (remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DEVOLUCION))
               addText(cb,480,start,"X");   
           if(this.remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DIARIO)){
             Remito referencia = new TRemito().getById(this.remito.getId_referencia());              
            addText(cb,100,start - 13,String.format("Remito diario creado a partir del remito %s",referencia.getNumero()));
           }
           //addText(cb,100,start - 27,remito.getObservaciones());
           imprimirObservaciones(cb, start - 27,remito.getObservaciones());
           
     }
     
     public void imprimirObservaciones(PdfContentByte cb,Integer start,String observaciones){
         Integer lineHeight = 10;
         Integer lineWidth = 130;
         Integer startIndex = 0;
         Integer lines = observaciones.length() / lineWidth;
         for(int i = 0;i<=lines;i++){
             startIndex = lineWidth * i;
             Integer endIndex = Math.min(startIndex + lineWidth,observaciones.length());
             addText(cb,100,start - i * lineHeight,observaciones.substring(startIndex,endIndex));
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
    
     private List<String> getCodigos_detalle(Kit kit){
         String codigos = "";
         TKit_detalle tkd = new TKit_detalle();
         List<Kit_detalle> detalle_kit = tkd.getByKitId(kit.getId());
         ArrayList arrCodigos = new ArrayList<String>();
         if(detalle_kit==null) return arrCodigos ;
         
         TActivo ta = new TActivo();
         
         for(Kit_detalle kd:detalle_kit){
            Activo activo = mapActivos.get(kd.getId_activo());
            if (activo == null){
                 activo = ta.getById(kd.getId_activo());
                 if (activo==null) continue;
                 mapActivos.put(kd.getId_activo(),activo);
            }           
            arrCodigos.add(activo.getCodigo());            
         } 
         
         return arrCodigos;
     }
     private List<Certificado> getListaCertificados(){
         ArrayList<Activo> lstActivos = new ArrayList<Activo>();
         List<Certificado> lstCertificados = new ArrayList<Certificado>();
         TActivo ta = new TActivo();
         TKit_detalle tkd = new TKit_detalle();
         TCertificado tc  = new TCertificado();
         
         for(Remito_detalle d : this.remito_detalle){
             if(d.getId_activo()!=0){
                 Activo activo = ta.getById(d.getId_activo());
                if(activo==null) continue;
                lstActivos.add(activo);
             }else {
                for(Kit_detalle kd:tkd.getByKitId(d.getId_kit())){
                    Activo activo = ta.getById(kd.getId_activo());
                    if (activo==null) continue;
                    lstActivos.add(activo);
                } 
             }
             
         }
         
         for(Activo a:lstActivos){             
             Certificado certificado = tc.getValido(OptionsCfg.MODULO_ACTIVO, a.getId());
             if(certificado==null) continue;
             if(lstCertificados.contains(certificado)) continue;
             lstCertificados.add(certificado);
         }
                 
         return lstCertificados;
     }
     public void combinarCertificados(Document document){         
         PdfContentByte cb = docWriter.getDirectContent();
         Parametro parametro =  new TParametro().getByCodigo(OptionsCfg.CERTIFICADO_PATH);
         String fileDir = parametro.getValor();
         List<Certificado> lstCertificados = getListaCertificados();
         for(Certificado c:lstCertificados){
             if(c.getArchivo().equals("")) continue;
             String fileName = fileDir + File.separator + c.getArchivo();                     
             if(!new File(fileName).exists()) {
                 Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, "No existe el certificado  " + fileName);
                 continue;
             }    
             try {
                 PdfReader reader = new PdfReader(fileName);
                 int pagesCount = reader.getNumberOfPages();
                 PdfImportedPage page;
                 for (int i = 0; i < pagesCount; i++) {
                    document.newPage();
                    page = this.docWriter.getImportedPage(reader, i + 1); //document.getPageNumber() + 1);                    
                    cb.addTemplate(page, 0, 0);
                }
             } catch (IOException ex) {
                 Logger.getLogger(RemitoPdf.class.getName()).log(Level.SEVERE, null, ex);
             }
         } 
     }

    public static void main(String[] args){
       
        Remito remito = new TRemito().getById(810);
        String fileName = String.format("c:\\remito_%d.pdf",remito.getNumero());
        RemitoPdf rm = new RemitoPdf(remito);
        
        rm.createPdf(fileName);
       // rm.combinarCertificados(fileName);
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
