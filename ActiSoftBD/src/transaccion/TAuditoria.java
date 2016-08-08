/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Auditoria;
import java.util.List;
import utils.OptionsCfg;
import utils.TFecha;

public class TAuditoria extends TransaccionBase<Auditoria>{
    public List<Auditoria>getList(){
        return super.getList("select * from auditoria");
    }
    public Auditoria getById(Integer id){
        String query = String.format("select * from auditoria where auditoria.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Auditoria auditoria){
        return super.actualizar(auditoria, "id");
    }    
    public static void guardar(Integer id_usuario,Integer id_tipo_usuario,Integer id_modulo, Integer id_accion,Integer id_referencia){
        Auditoria auditoria = new Auditoria();
        auditoria.setId_usuario(id_usuario);
        auditoria.setId_tipo_usuario(id_tipo_usuario);
        auditoria.setId_modulo(id_modulo);
        auditoria.setId_accion(id_accion);
        auditoria.setId_referencia(id_referencia);
        auditoria.setFecha(TFecha.ahora());
        //System.out.println(new TransaccionRS().queryInsert(auditoria));
        new TAuditoria().alta(auditoria);        
    }
   public static void main (String[] args){
       boolean nuevo = true;
       TAuditoria.guardar(1,2,OptionsCfg.MODULO_ACTIVO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,0);
   }
}
