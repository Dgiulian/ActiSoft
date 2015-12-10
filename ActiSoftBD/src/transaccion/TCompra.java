/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Compra;
import java.util.List;

public class TCompra extends TransaccionBase<Compra>{
    public List<Compra>getList(){
        return super.getList("select * from compra");
    }
    public List<Compra>getByActivoId(Integer activo_id){
        return super.getList("select * from compra where compra.activo_id = " + activo_id);
    }
    public Compra getById(Integer id){
        String query = String.format("select * from compra where compra.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Compra compra){
        return super.actualizar(compra, "id");
    }
    
    
}
