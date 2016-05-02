/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Preticket;
import bd.Preticket_detalle;
import bd.Remito;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TPreticket extends TransaccionBase<Preticket> {
    public List<Preticket>getList(){
        return super.getList("select * from preticket");
    }
     public List<Preticket>getByRemitoId(Integer id_remito){
        return super.getList(String.format("select * from preticket where preticket.id_remito = %d",id_remito));
    }
    public Preticket getById(Integer id){
        String query = String.format("select * from preticket where preticket.id = %d",id);
        return super.getById(query);
    }
    public Preticket getByNumero(Integer numero){
        String query = String.format("select * from preticket where preticket.numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Preticket preticket){
        return super.actualizar(preticket, "id");
    }
    public boolean eliminar(Preticket preticket){
        TPreticket_detalle tpd = new TPreticket_detalle();
        List<Preticket_detalle> lstDetalle = tpd.getByPreticketId(preticket.getId());
        TRemito tr = new TRemito();        
        HashMap<Integer,Remito> mapRemitos =  new HashMap<Integer,Remito>();
        for(Preticket_detalle d:lstDetalle){
            Remito r_cierre =  mapRemitos.get(d.getRemito_cierre());
            if (r_cierre!=null) continue; // Si existe en el map, ya se facturó
            
            r_cierre = tr.getByNumero(d.getRemito_cierre());
            if (r_cierre!=null) { //Desmarco el remito como facturado
                mapRemitos.put(d.getRemito_cierre(),r_cierre);
                r_cierre.setFacturado(0);
                tr.actualizar(r_cierre);
            }
            tpd.baja(d);
        }
        return this.baja(preticket);
    }
   

}
