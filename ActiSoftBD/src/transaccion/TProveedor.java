/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Proveedor;
import java.util.HashMap;
import java.util.List;

public class TProveedor extends TransaccionBase<Proveedor>{
    public List<Proveedor>getList(){
        return super.getList("select * from proveedor");
    }
    public Proveedor getById(Integer id){
        String query = String.format("select * from proveedor where proveedor.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Proveedor proveedor){
        return super.actualizar(proveedor, "id");
    }
//    public HashMap<Integer,Proveedor> getMap(){
//        HashMap<Integer,Proveedor> map = new HashMap<Integer,Proveedor>();
//        for(Proveedor proveedor:this.getList()){
//            map.put(proveedor.getId(), proveedor);
//        }
//        return map;
//    }
    
}
