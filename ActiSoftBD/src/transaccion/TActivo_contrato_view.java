/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo_contrato_view;
import bd.Rubro;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TActivo_contrato_view extends TransaccionBase<Activo_contrato_view>{
    public List<Activo_contrato_view> getList() {
        return super.getList("select * from activo_contrato_view");
    }
    public List<Activo_contrato_view> getListByIdRubro(Integer id_rubro) {
        return super.getList(String.format("select * from activo_contrato_view where activo_contrato_view.id_rubro = '%d'",id_rubro));
    }
    public List<Activo_contrato_view> getListByIdSubrubro(Integer id_subrubro) {
        return super.getList(String.format("select * from activo_contrato_view where activo_contrato_view.id_subrubro = '%d'",id_subrubro));
    }
    
    public List<Activo_contrato_view>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from activo_contrato_view");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
    
    public Activo_contrato_view getById(Integer id){
        String query = String.format("select * from activo_contrato_view where activo_contrato_view.id = %d",id);
        return super.getById(query);
    }
    public Activo_contrato_view getByCodigo(String codigo){
        String query = String.format("select * from activo_contrato_view where lower(activo_contrato_view.codigo) = lower('%s')",codigo);
        return super.getById(query);
    }
     public Activo_contrato_view getByCodigo(Integer id_contrato,String codigo){
        String query = String.format("select * from activo_contrato_view where c_id_contrato = %d and lower(activo_contrato_view.codigo) = lower('%s')",id_contrato,codigo);
         System.out.println(query);
        return super.getById(query);
    }
    public Activo_contrato_view getByCodigoNew(String codigo){
        String query = String.format("select * from activo_contrato_view where lower(activo_contrato_view.codigoNew) = lower('%s')",codigo);
        return super.getById(query);
    }
    public boolean actualizar(Activo_contrato_view activo_contrato_view){
        return super.actualizar(activo_contrato_view, "id");
    }
    public String getCodigoDisponible(Activo_contrato_view activo_contrato_view){
        TSubrubro ts = new TSubrubro();
        TRubro tr = new TRubro();
        Integer cant = ts.countByRubroId(activo_contrato_view.getId_rubro());
        Rubro rubro = new TRubro().getById(activo_contrato_view.getId_rubro());
        String codigo = String.format("%s%04d",rubro.getCodigo() ,cant++);
        while(this.getByCodigoNew(codigo)!=null){
            codigo = String.format("%s%04d",rubro.getCodigo() , cant++);    
        }
        return codigo;
    }
    public static void main(String[] args){
        System.out.println(new TActivo_contrato_view().getByCodigo(11, "XCK 010"));
    }
    @Override
    public String getOrderBy(){
        return " order by codigo";
    }
}
