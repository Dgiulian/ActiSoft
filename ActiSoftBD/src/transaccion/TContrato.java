/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Contrato;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TContrato extends TransaccionBase<Contrato>{
     public List<Contrato>getList(){
        return super.getList("select * from contrato");
    }
     public List<Contrato>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from contrato");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
     
     public HashMap<Integer,Contrato> getMap(){
        HashMap<Integer,Contrato> map = new HashMap<Integer,Contrato>();
        for(Contrato c: this.getList()){
            map.put(c.getId(), c);
        }
        return map;
    }
    public Contrato getById(Integer id){
        String query = String.format("select * from contrato where contrato.id = %d",id);
        return super.getById(query);
    }
    public Contrato getByNumero(String numero){
        String query = String.format("select * from contrato where lower(contrato.numero) = lower('%s')",numero);
        return super.getById(query);
    }
    public boolean actualizar(Contrato contrato){
        return super.actualizar(contrato, "id");
    }
}
