/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Remito_detalle;
import java.util.List;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class TRemito_detalle extends TransaccionBase<Remito_detalle>{
    public List<Remito_detalle>getList(){
        return super.getList("select * from remito_detalle");
    }
    public Remito_detalle getById(Integer id){
        String query = String.format("select * from remito_detalle where remito_detalle.id = %d",id);
        return super.getById(query);
    }
    public List<Remito_detalle> getByRemitoId(Integer id_remito){
        String query = String.format("select * from remito_detalle where remito_detalle.id_remito = %d",id_remito);
        return super.getList(query);
    }
    public boolean actualizar(Remito_detalle detalle){
        return super.actualizar(detalle, "id");
    }
    public List<Remito_detalle> getSinReferencia(Integer id_remito){
        String query = String.format("select * from remito_detalle where remito_detalle.id_remito = %d and remito_detalle.id_referencia = 0",id_remito);
        System.out.println(query);
        return super.getList(query);
    }
    
}
