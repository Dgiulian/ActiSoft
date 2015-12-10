/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo;
import bd.Rubro;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TActivo extends TransaccionBase<Activo>{
    public List<Activo> getList() {
        return super.getList("select * from activo where activo.bloqueado != 0");
    }
    public List<Activo> getListByIdRubro(Integer id_rubro) {
        return super.getList(String.format("select * from activo where activo.id_rubro = '%d'  and activo.bloqueado != 0",id_rubro));
    }
    public List<Activo> getListByIdSubrubro(Integer id_subrubro) {
        return super.getList(String.format("select * from activo where activo.id_subrubro = '%d'  and activo.bloqueado != 0",id_subrubro));
    }
    
    public List<Activo>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from activo  where activo.bloqueado != 0");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
    
    public Activo getById(Integer id){
        String query = String.format("select * from activo where activo.id = %d ",id);
        return super.getById(query);
    }
    public Activo getByCodigo(String codigo){
        String query = String.format("select * from activo where lower(activo.codigo) = lower('%s')",codigo);
        System.out.println(query);
        return super.getById(query);
    }
    public Activo getByCodigoNew(String codigo){
        String query = String.format("select * from activo where lower(activo.codigoNew) = lower('%s')",codigo);
        return super.getById(query);
    }
    public boolean actualizar(Activo activo){
        return super.actualizar(activo, "id");
    }
    public String getCodigoDisponible(Activo activo){
        TSubrubro ts = new TSubrubro();
        TRubro tr = new TRubro();
        Integer cant = ts.countByRubroId(activo.getId_rubro());
        Rubro rubro = new TRubro().getById(activo.getId_rubro());
        String codigo = String.format("%s%04d",rubro.getCodigo() ,cant++);
        while(this.getByCodigoNew(codigo)!=null){
            codigo = String.format("%s%04d",rubro.getCodigo() , cant++);    
        }
        return codigo;
    }
}
