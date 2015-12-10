/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Kit;
import bd.Rubro;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TKit extends TransaccionBase<Kit>{
    public List<Kit> getList() {
        return super.getList("select * from kit");
    }
    public List<Kit> getListByIdRubro(Integer id_rubro) {
        return super.getList(String.format("select * from kit where kit.id_rubro = '%d' ",id_rubro));
    }
    public List<Kit> getListByIdSubrubro(Integer id_subrubro) {
        return super.getList(String.format("select * from kit where kit.id_subrubro = '%d' ",id_subrubro));
    }
    
    public List<Kit>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from kit");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
    
    public Kit getById(Integer id){
        String query = String.format("select * from kit where kit.id = %d ",id);
        return super.getById(query);
    }  
     public Kit getByCodigo(String codigo){
        String query = String.format("select * from kit where lower(kit.codigo) = '%s' ",codigo.toLowerCase());
        return super.getById(query);
    }  
    public boolean actualizar(Kit kit){
        return super.actualizar(kit, "id");
    }
   
}
