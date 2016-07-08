/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Tabla_fecha;
import java.util.List;
import java.util.logging.Logger;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class TTabla_fecha extends TransaccionBase<Tabla_fecha>{
    public List<Tabla_fecha> getList() {
        return super.getList("select * from tabla_fecha");
    }
    public Tabla_fecha getById(Integer id){
        String query = String.format("select * from tabla_fecha where tabla_fecha.id = %d",id);
        return super.getById(query);
    }
    public List<Tabla_fecha> getByRubroId(Integer id_rubro){
        String query = String.format("select * from tabla_fecha where tabla_fecha.id_rubro = %d",id_rubro);
        return super.getList(query);
    }
       
    public boolean actualizar(Tabla_fecha rubro){
        return super.actualizar(rubro, "id");
    }
    
    public static void main(String[] args){
        TTabla_fecha ttf = new TTabla_fecha();
        for(Tabla_fecha tf:ttf.getList()){
            tf.setFecha(TFecha.ahora());
            ttf.actualizar(tf);
            System.out.println(tf.getId());
            System.out.println(tf.getFecha());
        }
//        Tabla_fecha tf = new Tabla_fecha();
//        tf.setFecha(TFecha.ahora());
//        ttf.alta(tf);
    }
    
    
}
