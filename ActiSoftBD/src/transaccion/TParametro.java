/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Parametro;
import java.util.List;

public class TParametro extends TransaccionBase<Parametro>{
    public List<Parametro>getList(){
        return super.getList("select * from parametro");
    }
    public Parametro getByNumero(Integer numero){
        return super.getById("select * from parametro where parametro.numero = " + numero);
    }
    public Parametro getById(Integer id){
        String query = String.format("select * from parametro where parametro.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Parametro parametro){
        return super.actualizar(parametro, "id");
    }
    
    
}
