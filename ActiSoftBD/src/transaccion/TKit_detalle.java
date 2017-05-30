/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Kit;
import bd.Kit_detalle;
import java.util.ArrayList;
import java.util.List;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class TKit_detalle extends TransaccionBase<Kit_detalle>{
    public List<Kit_detalle>getList(){
        return super.getList("select * from kit_detalle");
    }
    public Kit_detalle getById(Integer id){
        String query = String.format("select * from kit_detalle where kit_detalle.id = %d",id);
        return super.getById(query);
    }
    public List<Kit_detalle> getByKitId(Integer id_kit){
        String query = String.format("select * from kit_detalle where kit_detalle.id_kit = %d",id_kit);
        return super.getList(query);
    }
    public boolean actualizar(Kit_detalle detalle){
        return super.actualizar(detalle, "id");
    }
    public List<Activo> getActivos(Integer id_kit){
        List<Activo> lstActivos = new ArrayList<Activo>();
        TActivo ta = new TActivo();
        List<Kit_detalle> lstKit_detalle = this.getByKitId(id_kit);
        for(Kit_detalle kd : lstKit_detalle){
            Activo activo = ta.getById(kd.getId_activo());
            if (activo!=null) lstActivos.add(activo);
        }
        return lstActivos;
    }

    public boolean controlarActivos(Kit kit) {
        String query = String.format("select kit_detalle.* \n" +
                                     "  from kit_detalle,activo\n" +
                                     " where kit_detalle.id_activo = activo.id\n" +
                                     "   and kit_detalle.id_kit = %d\n" +
                                     " 	and activo.id_estado <> %d",kit.getId(),OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
        return this.getById(query)==null;
    }
    public static void main(String[] args){
        //Kit kit = new TKit().getById(35);
        //System.out.println(new TKit_detalle().controlarActivos(kit));
        List<Activo> lstActivos = new TKit_detalle().getActivos(14);
        System.out.println(lstActivos.size());
        
        
    }
}
