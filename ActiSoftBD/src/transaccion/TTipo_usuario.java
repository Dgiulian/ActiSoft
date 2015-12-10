/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Tipo_usuario;
import java.util.HashMap;
import java.util.List;

public class TTipo_usuario extends TransaccionBase<Tipo_usuario>{
    public List<Tipo_usuario>getList(){
        return super.getList("select * from tipo_usuario");
    }
    public Tipo_usuario getById(Integer id){
        String query = String.format("select * from tipo_usuario where tipo_usuario.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Tipo_usuario tipo_usuario){
        return super.actualizar(tipo_usuario, "id");
    }
    public HashMap<Integer,Tipo_usuario> getMap(){
        HashMap<Integer,Tipo_usuario> map = new HashMap<Integer,Tipo_usuario>();
        for(Tipo_usuario cli:this.getList()){
            map.put(cli.getId(), cli);
        }
        return map;
    }
    
}
