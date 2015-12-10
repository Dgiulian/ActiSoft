/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Site;
import java.util.List;

public class TSite extends TransaccionBase<Site>{
    public List<Site>getList(){
        return super.getList("select * from site");
    }
    public List<Site>getByClienteId(Integer id_cliente){
        return super.getList("select * from site where site.id_cliente = " + id_cliente);
    }
    public Site getById(Integer id){
        String query = String.format("select * from site where site.id = %d",id);        
        return super.getById(query);
    }
    public boolean actualizar(Site site){
        return super.actualizar(site, "id");
    }
    
    
}
