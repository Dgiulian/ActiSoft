/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Kit_detalle;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TKit_detalle extends TransaccionBase<Kit_detalle>{
    public List<Kit_detalle>getList(){
        return super.getList("select * from kit_detalle");
    }
    public Kit_detalle getById(Integer id){
        String query = String.format("select * from kit_detalle where kit_detalle.id = %d",id);
        return super.getById(query);
    }
    public List<Kit_detalle> getByKitId(Integer id_kit){
        String query = String.format("select * from kit_detalle where kit_detalle.id_kit = %d",id_kit);
        return super.getList(query);
    }
    public boolean actualizar(Kit_detalle detalle){
        return super.actualizar(detalle, "id");
    }
    public List<Activo> getActivos(Integer id_kit){
        String query = "";
        return null;
    }
}
