/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Rubro;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TRubro extends TransaccionBase<Rubro>{
    public List<Rubro> getList() {
        return super.getList("select * from rubro");
    }
    public HashMap<Integer,Rubro> getMap(){ 
        HashMap<Integer,Rubro>  mapa=new HashMap<Integer,Rubro> ();
        for (Rubro r : this.getList()) mapa.put(r.getId(),r);
        return mapa;
    }
            
    public Rubro getById(Integer id){
        String query = String.format("select * from rubro where rubro.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Rubro rubro){
        return super.actualizar(rubro, "id");
    }
    public Rubro getByDescripcion(String descripcion){
        String query = String.format("select * from rubro where rubro.descripcion = '%s'",descripcion);
        return super.getById(query);
    }
}
