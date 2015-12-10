/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Correctivo;
import java.util.List;

public class TCorrectivo extends TransaccionBase<Correctivo>{
    public List<Correctivo>getList(){
        return super.getList("select * from correctivo");
    }
    public List<Correctivo>getByClienteId(Integer id_activo){
        return super.getList("select * from correctivo where correctivo.id_activo = " + id_activo);
    }
    public Correctivo getById(Integer id){
        String query = String.format("select * from correctivo where correctivo.id = %d",id);        
        return super.getById(query);
    }
    public boolean actualizar(Correctivo correctivo){
        return super.actualizar(correctivo, "id");
    }
    
    
}
