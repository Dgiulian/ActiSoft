/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Agenda;
import java.util.HashMap;
import java.util.List;

public class TAgenda extends TransaccionBase<Agenda>{
    public List<Agenda>getList(){
        return super.getList("select * from agenda");
    }
    public Agenda getById(Integer id){
        String query = String.format("select * from agenda where agenda.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Agenda agenda){
        return super.actualizar(agenda, "id");
    }
    public Agenda getByNombre(String nombre){
        String query = String.format("select * from agenda where lower(nombre) = lower('%s')",nombre);
        System.out.println(query);
        return this.getById(query);
    }
//    public HashMap<Integer,Agenda> getMap(){
//        HashMap<Integer,Agenda> map = new HashMap<Integer,Agenda>();
//        for(Agenda cli:this.getList()){
//            map.put(cli.getId(), cli);
//        }
//        return map;
//    }
    
}
