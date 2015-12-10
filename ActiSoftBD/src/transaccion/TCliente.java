/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Cliente;
import java.util.List;

public class TCliente extends TransaccionBase<Cliente>{
    public List<Cliente>getList(){
        return super.getList("select * from cliente");
    }
    public Cliente getById(Integer id){
        String query = String.format("select * from cliente where cliente.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Cliente cliente){
        return super.actualizar(cliente, "id");
    }
    public Cliente getByNombre(String nombre){
        String query = String.format("select * from cliente where lower(nombre) = lower('%s')",nombre);
        System.out.println(query);
        return this.getById(query);
    }
//    public HashMap<Integer,Cliente> getMap(){
//        HashMap<Integer,Cliente> map = new HashMap<Integer,Cliente>();
//        for(Cliente cli:this.getList()){
//            map.put(cli.getId(), cli);
//        }
//        return map;
//    }
    public static void main(String[] args){
        
    }
}
