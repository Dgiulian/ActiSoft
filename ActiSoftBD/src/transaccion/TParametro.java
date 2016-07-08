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
     public Parametro getByCodigo(String codigo){     
        String sql = String.format("select * from parametro where lower(parametro.codigo) = lower('%s')" , codigo);
        return super.getById(sql);
    }
    public Parametro getById(Integer id){
        String query = String.format("select * from parametro where parametro.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Parametro parametro){
        return super.actualizar(parametro, "id");
    }
    public static void main(String[] args){
        String cadena = "c:\\Program Files (x86)\\Java\\apache-tomcat-6.0.37\\webapps\\compras\\";
    }
    
}
