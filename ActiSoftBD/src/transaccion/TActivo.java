/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Certificado;
import bd.Rubro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.OptionsCfg;
import utils.Parser;

/**
 *
 * @author Diego
 */
public class TActivo extends TransaccionBase<Activo>{
    @Override
    public List<Activo> getList() {
        return super.getList("select * from activo where activo.bloqueado = 0");
    }
    public List<Activo> getListByIdRubro(Integer id_rubro) {
        return super.getList(String.format("select * from activo where activo.id_rubro = '%d'  and activo.bloqueado = 0",id_rubro));
    }
    public List<Activo> getListByIdSubrubro(Integer id_subrubro) {
        return super.getList(String.format("select * from activo where activo.id_subrubro = '%d'  and activo.bloqueado = 0",id_subrubro));
    }
    
    public List<Activo>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from activo  where activo.bloqueado = 0");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
    
    public Activo getById(Integer id){
        String query = String.format("select * from activo where activo.id = %d ",id);
        return super.getById(query);
    }
    public Activo getByCodigo(String codigo){
        String query = String.format("select * from activo where lower(activo.codigo) = lower('%s')",codigo);
        //System.out.println(query);
        return super.getById(query);
    }
    public Activo getByCodigoNew(String codigo){
        String query = String.format("select * from activo where lower(activo.codigoNew) = lower('%s')",codigo);
        return super.getById(query);
    }
    public boolean actualizar(Activo activo){
        return super.actualizar(activo, "id");
    }
    public String getCodigoDisponible(Activo activo){
        TSubrubro ts = new TSubrubro();
        TRubro tr = new TRubro();
        Integer cant = ts.countByRubroId(activo.getId_rubro());
        Rubro rubro = new TRubro().getById(activo.getId_rubro());
        String codigo = String.format("%s%04d",rubro.getCodigo() ,cant++);
        while(this.getByCodigoNew(codigo)!=null){
            codigo = String.format("%s%04d",rubro.getCodigo() , cant++);    
        }
        return codigo;
    }
    public List<Activo> getListaExport(HashMap<String,String> filtro){
        /*
         * query1
         * query2: 
         */
        List<Activo> lista;
        String query = "";
        if(filtro.containsKey("id_contrato") || filtro.containsKey("id_cliente") ){
            String query1 = "select activo.*\n" +
                    "  from remito_detalle,remito,activo\n" +
                    " where activo.bloqueado = 0" +
                    "   and remito_detalle.id_remito = remito.id\n" +
                    "   and remito_detalle.id_activo = activo.id\n" +
                    "   and remito.id_tipo_remito = 1\n" +
                    "   and remito.id_estado      = 1\n" +
                    "   and remito.id not in (select id_referencia\n" +
                    "                           from remito r\n" +
                    "			  where r.id_tipo_remito = 2)\n";
           
            String query2 = "select activo.*\n" +
                "  from remito_detalle,remito,activo,kit_detalle\n" +
                " where activo.bloqueado = 0 " +
                "   and remito_detalle.id_remito = remito.id\n" +
                "   and kit_detalle.id_activo    = activo.id\n" +
                "   and remito_detalle.id_kit    = kit_detalle.id_kit\n" +
                "   and remito.id_tipo_remito    = 1\n" +
                "   and remito.id_estado         = 1\n" +
                "   and remito.id not in (select id_referencia  from remito r where r.id_tipo_remito = 2)";
            if(filtro.containsKey("id_contrato")) {
                query1 += String.format(" and remito.id_contrato = %s",filtro.get("id_contrato"));
                query2 += String.format(" and remito.id_contrato = %s",filtro.get("id_contrato"));
            }
            if(filtro.containsKey("id_cliente"))  {
                query1 += String.format(" and remito.id_cliente  = %s",filtro.get("id_cliente"));
                query2 += String.format(" and remito.id_cliente  = %s",filtro.get("id_cliente"));
            }
            query = "select * from (( " + query1 + ") union (" + query2 + " )) as A";
//            query = "( " + query1 + ")";
            query += " where (A.id_rubro <> 11\n";
            query += "   and  A.id_rubro <> 12\n";
            query += "   and  A.id_rubro <> 14)";
        } 
        else {
            query = "select activo.*\n" +
                    " from  activo \n" +
                    " where true ";
            query += " and bloqueado = 0 \n";
            if (filtro.containsKey("id_estado")) {
                query += String.format(" and activo.id_estado  = %s", filtro.get("id_estado"));
            }
        }
        if (query.equals("")) return null;
        System.out.println(query);
        lista = this.getList(query);
        
        if(filtro.containsKey("id_resultado")){
            System.out.println(filtro.get("id_resultado"));
            // Filtramos los activos por certificado
            Integer id_resultado = Parser.parseInt(filtro.get("id_resultado"));
            if (lista!=null) {
                TCertificado tc = new TCertificado();
                ArrayList<Activo> listaArray = new ArrayList(lista);
                for(Activo activo:listaArray){
                    Certificado vigente = tc.getVigente(OptionsCfg.MODULO_ACTIVO,activo.getId());
                    System.out.println(activo.getId());
                    if (vigente==null) {
                        lista.remove(activo); 
                        continue;
                    }
                    // Quitamos los activos que no coincida el certificado                     
                    if(vigente.getId_resultado()!= id_resultado) lista.remove(activo);
                }                
            }
        }
        
        return lista;
    }
    
    
    public List<Activo> getListaVencimiento(HashMap<String,String> filtro){
        List<Activo> lista;
// String query = "set @fecha := '%s';\n" +
//                "select activo.*\n" +
//                "  from activo,certificado\n" +
//                "  where activo.id = certificado.id_activo\n" +
//                "  and certificado.fecha_hasta <= @fecha\n" +
//                "  and not activo.id  in ( \n" +
//                "  	select  id_activo from certificado\n" +
//                "  		where certificado.fecha_hasta >= @fecha\n" +
//                "	  )";
        
         String query = "select activo.*\n" +
                "  from activo,certificado\n" +
                "  where activo.id = certificado.id_activo\n" +
                "  and certificado.fecha_hasta <= '%s'\n" +
                "  and not activo.id  in ( \n" +
                "  	select  id_activo from certificado\n" +
                "  		where certificado.fecha_hasta >= '%s'\n" +
                "	  )";
        query = String.format(query,filtro.get("fecha_hasta"),filtro.get("fecha_hasta"));
        
        System.out.println(query);
        
        lista = this.getList(query);
        
        System.out.println(lista);
        return lista;
    }
    public static void main(String[] args){
//        HashMap<String,String> filtros = new HashMap<String,String>();
//        filtros.put("fecha_hasta","2016-12-31");
//        new TActivo().getListaVencimiento(filtros);
        
        /* Pruebas de Equal  */
        TActivo ta = new TActivo();
        List<Activo> lstActivos = new TActivo().getList();
        Activo a = lstActivos.get(3);
        Activo a2 = ta.getById(a.getId());
        Activo a3 = new Activo();
        Activo a4 = new Activo();
        Activo a5 = new Activo();
        a3.setId(a.getId());
        a4.setId(12);
        a5.setId(6);
        System.out.println(a.getId());
        System.out.println(lstActivos.contains(a));
        System.out.println(lstActivos.contains(a2));
        System.out.println(lstActivos.contains(a3));
        System.out.println(lstActivos.contains(a4));
        System.out.println(lstActivos.contains(a5));
        
    }
}
