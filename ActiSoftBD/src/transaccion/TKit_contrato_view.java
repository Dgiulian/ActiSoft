/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Kit_contrato_view;
import bd.Rubro;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TKit_contrato_view extends TransaccionBase<Kit_contrato_view>{
    public List<Kit_contrato_view> getList() {
        return super.getList("select * from kit_contrato_view");
    }
    public List<Kit_contrato_view> getListByIdRubro(Integer id_rubro) {
        return super.getList(String.format("select * from kit_contrato_view where kit_contrato_view.id_rubro = '%d'",id_rubro));
    }
    public List<Kit_contrato_view> getListByIdSubrubro(Integer id_subrubro) {
        return super.getList(String.format("select * from kit_contrato_view where kit_contrato_view.id_subrubro = '%d'",id_subrubro));
    }
    
    public List<Kit_contrato_view>  getList(Integer page,Integer limit){
        Integer off = page * limit;        
        String query = String.format("select * from kit_contrato_view");
        if (limit>0) {
            query += String.format(" limit %d offset %d ",limit,off);
        }
        return super.getList(query);            
    }
    
    public Kit_contrato_view getById(Integer id){
        String query = String.format("select * from kit_contrato_view where kit_contrato_view.id = %d",id);
        return super.getById(query);
    }
    public Kit_contrato_view getByCodigo(String codigo){
        String query = String.format("select * from kit_contrato_view where lower(kit_contrato_view.codigo) = lower('%s')",codigo);
        return super.getById(query);
    }
     public Kit_contrato_view getByCodigo(Integer id_contrato,String codigo){
        String query = String.format("select * from kit_contrato_view where c_id_contrato = %d and lower(kit_contrato_view.codigo) = lower('%s')",id_contrato,codigo);
         System.out.println(query);
        return super.getById(query);
    }
    public Kit_contrato_view getByCodigoNew(String codigo){
        String query = String.format("select * from kit_contrato_view where lower(kit_contrato_view.codigoNew) = lower('%s')",codigo);
        return super.getById(query);
    }
    public boolean actualizar(Kit_contrato_view kit_contrato_view){
        return super.actualizar(kit_contrato_view, "id");
    }   
    public static void main(String[] args){
        System.out.println(new TKit_contrato_view().getByCodigo(11, "XCK 010"));
    }
    @Override
    public String getOrderBy(){
        return " order by codigo";
    }
}
