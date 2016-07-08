/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Diego
 */
public class OptionsCfg {
    
    public static final Integer ACTIVO_ESTADO_DISPONIBLE    = 1;
    public static final Integer ACTIVO_ESTADO_ALQUILADO     = 2;
    public static final Integer ACTIVO_ESTADO_BAJA          = 3;
    public static final Integer ACTIVO_ESTADO_FALLA         = 4;
    public static final Integer ACTIVO_ESTADO_INSPECCION    = 5;
    public static final Integer ACTIVO_ESTADO_PRESTADO      = 6;
    public static final Integer ACTIVO_ESTADO_NO_DISPONIBLE = 7;
    public static final Integer ACTIVO_ESTADO_NO_APTO       = 8;
    public static final Integer ACTIVO_ESTADO_KIT           = 9;
    public static final Integer ACTIVO_ESTADO_ELIMINADO     = 10;
    
    
    public static final Integer REMITO_ENTREGA = 1;
    public static final Integer REMITO_DEVOLUCION = 2;
    public static final Integer REMITO_DIARIO = 3;
    public static final Integer REMITO_PEDIDO = 4;
    public static final Integer REMITO_FACTURA = 5;
    public static final Integer REMITO_CONTRATO = 6;
    public static final Integer REMITO_ORDEN = 7;
    public static final Integer REMITO_NOTA = 8;
    public static final Integer REMITO_VENCIMIENTO = 9;
    public static final Integer REMITO_PRESUPUESTO = 10;
    
    public static final Integer REMITO_ESTADO_ABIERTO = 1;
    public static final Integer REMITO_ESTADO_CERRADO = 2;
    
    public static final Integer CERTIFICADO_APTO    = 1;
    public static final Integer CERTIFICADO_NO_APTO = 2;
    public static final Integer CERTIFICADO_REPARAR = 3;
    public static final Integer CERTIFICADO_VENCIDO = 4;

    public static final Integer CERTIFICADO_PATH = 1;
    public static final Integer REMITO_PATH = 2;
    
    public static final Integer ACTIVO_PATH = 3;
    public static final Integer ACTIVO_URL = 4;
    public static final String  COMPRA_PATH = "compra_path";
    public static final String  COMPRA_URL  = "compra_url";
    public static final Integer PRETICKET_IMAGE = 5;
    public static final String  ETIQUETA_IMAGE = "fondo_etiqueta";
    public static final String  REMITO_IMAGE = "fondo_remito";
    
    public static final Integer UNIDAD_DIAS = 1;
    public static final Integer UNIDAD_HORAS = 2;
    public static final Integer UNIDAD_MTS = 3;    
    public static final Integer UNIDAD_UNIDAD = 4;
    public static final Integer UNIDAD_KMS = 5;
    
    public static final Integer PROVEEDOR_MAYORISTA = 1;
    public static final Integer PROVEEDOR_TRANSPORTISTA = 2;
    public static final Integer PROVEEDOR_MINORISTA = 3;
    public static final Integer PROVEEDOR_UTE = 4;
    
    public static final Integer MODULO_ACTIVO         = 1;
    public static final Integer MODULO_ACTIVOHISTORIA = 2;
    public static final Integer MODULO_AUDITORIA      = 3;
    public static final Integer MODULO_CERTIFICADO    = 4;

    public static final Integer MODULO_CLIENTE        = 5;
    public static final Integer MODULO_COMPRA         = 6;
    public static final Integer MODULO_CONTRATO       = 7;
    public static final Integer MODULO_FITERS         = 8;
    public static final Integer MODULO_LOCALIDAD      = 9;
    public static final Integer MODULO_PRETICKET      = 10;
    public static final Integer MODULO_PROVEEDOR      = 11;
    public static final Integer MODULO_REMITO         = 12;
    public static final Integer MODULO_RUBRO          = 13;
    public static final Integer MODULO_SITE           = 14;
    public static final Integer MODULO_SUBRUBRO       = 15;
    public static final Integer MODULO_USUARIO        = 16;
    public static final Integer MODULO_CONFIGURACION  = 17;
    public static final Integer MODULO_CORRECTIVO     = 18;
    public static final Integer MODULO_AGENDA         = 19;
    
    public static final Integer MODULO_KIT            = 20;
    public static final Integer MODULO_TRANSPORTISTA  = 21;
    public static final Integer MODULO_VEHICULO       = 22;
    
    public static final Integer MODULO_PARAMETRO      = 25;
    
    public static final Integer ACTIVIDAD_ENTREGA       = 1;
    public static final Integer ACTIVIDAD_DEVOLUCIÓN    = 2;
    public static final Integer ACTIVIDAD_CHECK_IN      = 3;
    public static final Integer ACTIVIDAD_CHECK_OUT     = 4;
    public static final Integer ACTIVIDAD_CERTIFICADO   = 5;
    public static final Integer ACTIVIDAD_CERTIFICACIÓN = 6;
    public static final Integer ACTIVIDAD_ENTRADA       = 7;
    public static final Integer ACTIVIDAD_SALIDA        = 8;
    public static final Integer ACTIVIDAD_PRETICKET     = 9;
    
    public static Integer ACCION_ALTA      = 1;
    public static Integer ACCION_BAJA      = 2;
    public static Integer ACCION_MODIFICAR = 3;
    
    public static final Integer KIT_ESTADO_DISPONIBLE = 1;
    public static final Integer KIT_ESTADO_ALQUILADO = 2;
    public static final Integer KIT_ESTADO_ELIMINADO = 3;
    
    public static final int COMPRA_NADA      = 0;
    public static final int COMPRA_REEMPLAZA = 1;
    public static final int COMPRA_SUMA      = 2;
    
    public static final Integer RUBRO_TRANSPORTE  = 14;
//    public static final Integer KIT_HISTORIA_ALTA  = 1;
//    public static final Integer KIT_HISTORIA_BAJA  = 2;
    
    public static ArrayList<Option> getTipoRemitos(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(REMITO_ENTREGA,String.format("%3d",REMITO_ENTREGA),"Entrega"));
        
        lista.add(new Option(REMITO_DEVOLUCION,String.format("%3d",REMITO_DEVOLUCION),"Devolución"));
        
        lista.add(new Option(REMITO_DIARIO,String.format("%3d",REMITO_DIARIO),"Diario"));
        lista.add(new Option(REMITO_PRESUPUESTO,String.format("%3d",REMITO_PRESUPUESTO),"Presupuesto"));
        lista.add(new Option(REMITO_PEDIDO,String.format("%3d",REMITO_PEDIDO),"Pedido"));        
        lista.add(new Option(REMITO_FACTURA,String.format("%3d",REMITO_FACTURA),"Factura"));
        lista.add(new Option(REMITO_VENCIMIENTO,String.format("%3d",REMITO_VENCIMIENTO),"Vencimiento"));
        lista.add(new Option(REMITO_CONTRATO,String.format("%3d",REMITO_CONTRATO),"Contrato"));
        lista.add(new Option(REMITO_ORDEN,String.format("%3d",REMITO_ORDEN),"Orden de Servicio"));
        lista.add(new Option(REMITO_NOTA,String.format("%3d",REMITO_NOTA),"Nota Pedido"));
        return lista;
    }
    
    public static ArrayList<Option> getPerfiles(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(1,"ADM","Administrador"));
        lista.add(new Option(2,"GES","Gestor"));
        lista.add(new Option(3,"OPE","Operador"));        
        return lista;
    }
    public static ArrayList<Option> getEstadoActivo(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(ACTIVO_ESTADO_DISPONIBLE,String.format("%03d",ACTIVO_ESTADO_DISPONIBLE),"Disponible"));
        lista.add(new Option(ACTIVO_ESTADO_ALQUILADO,String.format("%03d",ACTIVO_ESTADO_ALQUILADO),"Alquilado"));
        lista.add(new Option(ACTIVO_ESTADO_BAJA,String.format("%03d",ACTIVO_ESTADO_BAJA),"Baja"));
        lista.add(new Option(ACTIVO_ESTADO_FALLA,String.format("%03d",ACTIVO_ESTADO_FALLA),"Falla"));
        lista.add(new Option(ACTIVO_ESTADO_INSPECCION,String.format("%03d",ACTIVO_ESTADO_INSPECCION),"Inspección"));
        lista.add(new Option(ACTIVO_ESTADO_PRESTADO,String.format("%03d",ACTIVO_ESTADO_PRESTADO),"Prestado"));
        lista.add(new Option(ACTIVO_ESTADO_NO_DISPONIBLE,String.format("%03d",ACTIVO_ESTADO_NO_DISPONIBLE),"Prestado"));
        lista.add(new Option(ACTIVO_ESTADO_NO_APTO,String.format("%03d",ACTIVO_ESTADO_NO_APTO),"No Apto"));
        lista.add(new Option(ACTIVO_ESTADO_KIT,String.format("%03d",ACTIVO_ESTADO_KIT),"Kit"));
        lista.add(new Option(ACTIVO_ESTADO_ELIMINADO,String.format("%03d",ACTIVO_ESTADO_ELIMINADO),"Eliminado"));
        
//        lista.add(new Option(6,"003","Cabeza fractura 001"));        
        return lista;
    }
    public static ArrayList<Option> getEstadoRemitos(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(REMITO_ESTADO_ABIERTO,String.format("%03d",REMITO_ESTADO_ABIERTO),"Abierto"));
        lista.add(new Option(REMITO_ESTADO_CERRADO,String.format("%03d",REMITO_ESTADO_CERRADO),"Cerrado"));                
        return lista;
    }
    public static ArrayList<Option> getEstadoCertificados(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(CERTIFICADO_APTO,String.format("%03d",CERTIFICADO_APTO),"Apto"));
        lista.add(new Option(CERTIFICADO_NO_APTO,String.format("%03d",CERTIFICADO_NO_APTO),"No Apto"));
//        lista.add(new Option(CERTIFICADO_REPARAR,String.format("%03d",CERTIFICADO_REPARAR),"Reparar"));                
        lista.add(new Option(CERTIFICADO_VENCIDO,String.format("%03d",CERTIFICADO_VENCIDO),"Vencido"));                
        return lista;
    }
     
    public static ArrayList<Option> getUnidades(){
        ArrayList<Option> lista = new ArrayList();        
        lista.add(new Option(UNIDAD_DIAS,String.format("%03d",UNIDAD_DIAS),"Dias"));
        lista.add(new Option(UNIDAD_HORAS,String.format("%03d",UNIDAD_HORAS),"Horas"));
        lista.add(new Option(UNIDAD_MTS,String.format("%03d",UNIDAD_MTS),"Mts"));
        lista.add(new Option(UNIDAD_KMS,String.format("%03d",UNIDAD_KMS),"Kms"));
//        lista.add(new Option(UNIDAD_UNIDAD,String.format("%03d",UNIDAD_UNIDAD),"Unidad"));
        return lista;
    }
     public static ArrayList<Option> getTiposProveedor(){
        ArrayList<Option> lista = new ArrayList();        
        lista.add(new Option(PROVEEDOR_MAYORISTA,String.format("%3d",PROVEEDOR_MAYORISTA),"Mayorista"));
        lista.add(new Option(PROVEEDOR_TRANSPORTISTA,String.format("%3d",PROVEEDOR_TRANSPORTISTA),"Transportista"));
        lista.add(new Option(PROVEEDOR_MINORISTA,String.format("%3d",PROVEEDOR_MINORISTA),"Minorista"));
        lista.add(new Option(PROVEEDOR_UTE,String.format("%3d",PROVEEDOR_UTE),"Ute"));
        return lista;
     }
    public static ArrayList<Option> getFormasPagos(){
         ArrayList<Option> lista = new ArrayList();
         lista.add(new Option(1,"TAR","Tarjeta de Crédito"));
         lista.add(new Option(2,"P30","Pago a 30 días"));
         lista.add(new Option(3,"P60","Pago a 60 días"));
         lista.add(new Option(4,"P90","pago a 90 días"));
         lista.add(new Option(5,"CON","Pago contado"));
         lista.add(new Option(6,"CUO","Cuotas"));
         lista.add(new Option(7,"CC","Cuenta Corriente"));
         return lista;
     }
     public static ArrayList<Option> getClasesIva(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(1,"001", "Responsable Inscripto"));
        lista.add(new Option(2,"002", "Consumidor Final"));
        lista.add(new Option(3,"003", "IVA No Responsable"));
        lista.add(new Option(4,"004", "IVA Exento"));
        lista.add(new Option(5,"005", "Responsable Monotributista"));
        return lista;
    }
public static ArrayList<Option> getTipoActividades(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(ACTIVIDAD_ENTREGA,String.format("%3d",ACTIVIDAD_ENTREGA),"Entrega"));
        lista.add(new Option(ACTIVIDAD_DEVOLUCIÓN,String.format("%3d",ACTIVIDAD_DEVOLUCIÓN),"Devolución"));
        lista.add(new Option(ACTIVIDAD_CHECK_IN,String.format("%3d",ACTIVIDAD_CHECK_IN),"Check In"));
        lista.add(new Option(ACTIVIDAD_CHECK_OUT,String.format("%3d",ACTIVIDAD_CHECK_OUT),"Check Out"));
        lista.add(new Option(ACTIVIDAD_CERTIFICADO,String.format("%3d",ACTIVIDAD_CERTIFICADO),"Certificado"));
        lista.add(new Option(ACTIVIDAD_CERTIFICACIÓN,String.format("%3d",ACTIVIDAD_CERTIFICACIÓN),"Certificación"));
        lista.add(new Option(ACTIVIDAD_ENTRADA,String.format("%3d",ACTIVIDAD_ENTRADA),"Entrada"));
        lista.add(new Option(ACTIVIDAD_SALIDA,String.format("%3d",ACTIVIDAD_SALIDA),"Salida"));
        lista.add(new Option(ACTIVIDAD_PRETICKET,String.format("%3d",ACTIVIDAD_PRETICKET),"Preticket"));
        return lista;
    }     
    public static HashMap<Integer,Option> getMapModulos(){
        HashMap<Integer,Option> mapa = new HashMap<>();
        mapa.put(MODULO_ACTIVO,         new Option(MODULO_ACTIVO,String.format("%3d",MODULO_ACTIVO),"Activo"));
        mapa.put(MODULO_ACTIVOHISTORIA, new Option(MODULO_ACTIVOHISTORIA,String.format("%3d",MODULO_ACTIVOHISTORIA),"Activohistoria"));
        mapa.put(MODULO_AUDITORIA,      new Option(MODULO_AUDITORIA,String.format("%3d",MODULO_AUDITORIA),"Auditoria"));
        mapa.put(MODULO_CERTIFICADO,    new Option(MODULO_CERTIFICADO,String.format("%3d",MODULO_CERTIFICADO),"Certificado"));
        mapa.put(MODULO_CLIENTE,        new Option(MODULO_CLIENTE,String.format("%3d",MODULO_CLIENTE),"Cliente"));
        mapa.put(MODULO_COMPRA,         new Option(MODULO_COMPRA,String.format("%3d",MODULO_COMPRA),"Compra"));
        mapa.put(MODULO_CONTRATO,       new Option(MODULO_CONTRATO,String.format("%3d",MODULO_CONTRATO),"Contrato"));
        mapa.put(MODULO_FITERS,         new Option(MODULO_FITERS,String.format("%3d",MODULO_FITERS),"Fiters"));
        mapa.put(MODULO_LOCALIDAD,      new Option(MODULO_LOCALIDAD,String.format("%3d",MODULO_LOCALIDAD),"Localidad"));
        mapa.put(MODULO_PRETICKET,      new Option(MODULO_PRETICKET,String.format("%3d",MODULO_PRETICKET),"Preticket"));
        mapa.put(MODULO_PROVEEDOR,      new Option(MODULO_PROVEEDOR,String.format("%3d",MODULO_PROVEEDOR),"Proveedor"));
        mapa.put(MODULO_REMITO,         new Option(MODULO_REMITO,String.format("%3d",MODULO_REMITO),"Remito"));
        mapa.put(MODULO_RUBRO,          new Option(MODULO_RUBRO,String.format("%3d",MODULO_RUBRO),"Rubro"));
        mapa.put(MODULO_SITE,           new Option(MODULO_SITE,String.format("%3d",MODULO_SITE),"Site"));
        mapa.put(MODULO_SUBRUBRO,       new Option(MODULO_SUBRUBRO,String.format("%3d",MODULO_SUBRUBRO),"Subrubro"));
        mapa.put(MODULO_USUARIO,        new Option(MODULO_USUARIO,String.format("%3d",MODULO_USUARIO),"Usuario"));
        mapa.put(MODULO_CONFIGURACION,  new Option(MODULO_CONFIGURACION,String.format("%3d",MODULO_CONFIGURACION),"Configuracion"));
        mapa.put(MODULO_CORRECTIVO,  new Option(MODULO_CORRECTIVO,String.format("%3d",MODULO_CORRECTIVO),"Correctivo"));
        return mapa;
    }
    
    
    public static HashMap<Integer,Option> getMapAcciones(){
        HashMap<Integer,Option> mapa = new HashMap<>();
        mapa.put(ACCION_ALTA,     new Option(ACCION_ALTA,String.format("%3d",ACCION_ALTA),"Alta"));
        mapa.put(ACCION_BAJA,     new Option(ACCION_BAJA,String.format("%3d",ACCION_BAJA),"Baja"));
        mapa.put(ACCION_MODIFICAR,new Option(ACCION_MODIFICAR,String.format("%3d",ACCION_MODIFICAR),"Modificar"));
        return mapa;
    }
    public static ArrayList<Option> getEstadoKit(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(KIT_ESTADO_DISPONIBLE,String.format("%03d",KIT_ESTADO_DISPONIBLE),"Disponible"));
        lista.add(new Option(KIT_ESTADO_ALQUILADO,String.format("%03d",KIT_ESTADO_ALQUILADO),"Alquilado"));
        lista.add(new Option(KIT_ESTADO_ELIMINADO,String.format("%03d",KIT_ESTADO_ELIMINADO),"Eiiminado"));
//        lista.add(new Option(6,"003","Cabeza fractura 001"));        
        return lista;
   }
   public static HashMap<Integer,Option> getMap(List<Option> lista){
        HashMap<Integer,Option> mapa = new HashMap<>();
        for(Option o:lista){
            mapa.put(o.getId(), o);
        }
        return mapa;
   } 
    public static ArrayList<Option> getCompraAcciones(){
        ArrayList<Option> lista = new ArrayList();
        lista.add(new Option(COMPRA_NADA,"Nada","Nada"));
        lista.add(new Option(COMPRA_REEMPLAZA,"Reemplaza","Reemplaza"));
        lista.add(new Option(COMPRA_SUMA,"Suma","Suma"));        
        return lista;
    }
    public static class Option{
        Integer id ;
        String codigo;
        String descripcion;    
        public Option(Integer id,String codigo,String descripcion) {
            this.id = id;
            this.codigo = codigo;
            this.descripcion = descripcion;
        }
        public Integer getId(){
            return this.id;
        }
        public String getCodigo(){
            return this.codigo;
        }
        public String getDescripcion(){
            return this.descripcion;
        }
                
    }
}
