/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Preticket_detalle;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TPreticket_detalle extends TransaccionBase<Preticket_detalle> {
    public List<Preticket_detalle>getList(){
        return super.getList("select * from preticket_detalle");
    }
    public List<Preticket_detalle>getByPreticketId(Integer id_preticket){
        return super.getList(String.format("select * from preticket_detalle where preticket_detalle.id_preticket = %d",id_preticket));
    }
    public Preticket_detalle getById(Integer id){
        String query = String.format("select * from preticket_detalle where preticket_detalle.id = %d",id);
        return super.getById(query);
    }
    public Preticket_detalle getByNumero(Integer numero){
        String query = String.format("select * from preticket_detalle where preticket_detalle.numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Preticket_detalle preticket_detalle){
        return super.actualizar(preticket_detalle, "id");
    }
    
}
