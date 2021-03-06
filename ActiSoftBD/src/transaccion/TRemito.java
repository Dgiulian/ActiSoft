/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Kit;
import bd.Remito;
import bd.Remito_detalle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class TRemito extends TransaccionBase<Remito> {
//    private String orderBy = " remito.fecha desc ";
    @Override
    public List<Remito>getList(){
        return super.getList("select * from remito");
    }
     public List<Remito>getByContratoId(Integer id_contrato){
        return super.getList(String.format("select * from remito where remito.id_contrato = %d",id_contrato));
    }
    public Remito getById(Integer id){
        String query = String.format("select * from remito where remito.id = %d",id);
        return super.getById(query);
    }
     public Remito getByNumero(Integer numero){
        String query = String.format("select * from remito where remito.numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Remito remito){
        return super.actualizar(remito, "id");
    }
    
    @Override
    public String getOrderBy(){
        if (this.orderBy.trim().equals("")) return "";
        else if (this.orderBy.trim().startsWith("order by")) return this.orderBy;
        else return " order by " + this.orderBy;
    }    
    public boolean esTransitorio(Remito r){
        String query = String.format("select count(*) from remito where remito.id_tipo_remito = 2 and remito.id_referencia = %d ",r.getId());
        

        conexion.conectarse();
        ResultSet rs = conexion.ejecutarSQLSelect(String.format(query,r.getId()));
        try {
            int cant = 0;
            while(rs!=null && rs.next()){
                cant = rs.getInt(1);
            }
            return cant!=0;
        } catch (SQLException ex) {
            Logger.getLogger(TRemito.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conexion.desconectarse();
        }
        return false;
    }
    
    public boolean eliminar(Remito remito){        
        TRemito_detalle td = new TRemito_detalle();        
        TActivo ta = new TActivo();
        TKit    tk = new TKit();
        List<Remito_detalle> lstDetalle = new TRemito_detalle().getByRemitoId(remito.getId());
        
        for(Remito_detalle d:lstDetalle){
            if(d.getId_activo()!=0){
                Activo activo = ta.getById(d.getId_activo());
                if(activo==null) continue;
                
                if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_ENTREGA)) { // Si es un remito de entrega, se devuelven los activos
                    if(activo.getAplica_stock() == 1) {                
                        activo.setStock(activo.getStock() + d.getCantidad());
                     }             
                    activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                } else if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DEVOLUCION)) {
                    if(activo.getAplica_stock() == 1) {
                        activo.setStock(activo.getStock() - d.getCantidad());
                     }

                    if(activo.getStock()<=0) 
                        activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                }
                ta.actualizar(activo);
            } else {
                Kit kit = tk.getById(d.getId_kit());
                if (kit==null) continue;
                List<Activo> activos = new TKit_detalle().getActivos(kit.getId());
                
                if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_ENTREGA)) { // Si es un remito de entrega, se devuelven los activos
                  kit.setId_estado(OptionsCfg.KIT_ESTADO_DISPONIBLE);                   
                  for(Activo activo:activos){
                    if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_ENTREGA)) { // Si es un remito de entrega, se devuelven los activos
                      if(activo.getAplica_stock() == 1) {                
                          activo.setStock(1f);
                       }             
                        activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                        } else if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DEVOLUCION)) {
                            if(activo.getAplica_stock() == 1) {
                                activo.setStock(0f);
                             }
                            if(activo.getStock()<=0) 
                                activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_ALQUILADO);
                        }
                  ta.actualizar(activo);
                  }
                } else if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DEVOLUCION)) {
                    
                    kit.setId_estado(OptionsCfg.KIT_ESTADO_ALQUILADO);
                }
                tk.actualizar(kit);
            }
            td.baja(d);
        }
        if(remito.getId_tipo_remito().equals(OptionsCfg.REMITO_DEVOLUCION)){
            // Si es un remito de devolución, se tiene que volver a atrás el estado del remito de entrega
            Remito entrega = this.getById(remito.getId_referencia());
            if(entrega!=null){
                entrega.setId_estado(OptionsCfg.REMITO_ESTADO_ABIERTO);
                this.actualizar(entrega);
            }
        }
        return this.baja(remito);        
    }
    public boolean existeReferencia(Remito remito){
        if (remito.getId()==0) return false;
        
        String query = String.format("select count(*) from remito where  remito.id_referencia = %d ",remito.getId());
        conexion.conectarse();
        ResultSet rs = conexion.ejecutarSQLSelect(query);
        try {
            int cant = 0;
            while(rs!=null && rs.next()){
                cant = rs.getInt(1);
            }
            return cant!=0;
        } catch (SQLException ex) {
            Logger.getLogger(TRemito.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conexion.desconectarse();
        }
        return false;
        
    }
    public boolean tieneDevolucion(Remito remito){
        boolean tiene_devolucion = false;
        String query = String.format("select * from remito where remito.id_tipo_remito=%d and remito.id_referencia=%d",OptionsCfg.REMITO_DEVOLUCION,remito.getId());       
        return tiene_devolucion;
    }

    public List<Remito> getListaExport(HashMap<String, String> mapFiltro) {
        List<Remito> lista = this.getListFiltro(mapFiltro);
        return lista;
    }
    public Remito getByIdActivo(Integer id_activo, Integer id_tipo_remito,Integer id_estado){
        String query = String.format("select remito.*\n" +
                                  " from remito_detalle,remito\n" +
                                  " where remito_detalle.id_remito = remito.id\n" +
                                  " and remito_detalle.id_activo = %d\n" +
                                  " and remito.id_estado = %d \n" +
                                  " and remito.id_tipo_remito = %d",id_activo,id_tipo_remito,id_estado);
        //if(id_activo == 1254) System.out.println(query);
        return this.getById(query);
    }
    
    public Remito getByIdActivoEnKit(Integer id_activo, Integer id_tipo_remito,Integer id_estado){
        String query = String.format("select remito.*\n" +
                                 " from remito_detalle, remito, kit_detalle\n" +
                                 " where remito_detalle.id_remito = remito.id\n" +
                                 " and kit_detalle.id_activo = %d\n" +
                                 " and remito.id_estado = %d\n" +
                                 " and remito.id_tipo_remito = %d\n" +
                                 " and remito_detalle.id_kit = kit_detalle.id_kit",id_activo,id_tipo_remito,id_estado);
        return this.getById(query);
    }

}
