/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Compra;
import java.util.List;

public class TCompra extends TransaccionBase<Compra>{
    public String orderBy = " compra.fecha desc ";
    
    public List<Compra>getList(){
        return super.getList("select * from compra");
    }
    public List<Compra>getByActivoId(Integer activo_id){
        return super.getList("select * from compra where compra.activo_id = " + activo_id);
    }
    public Compra getById(Integer id){
        String query = String.format("select * from compra where compra.id = %d",id);
        return super.getById(query);
    }
    public boolean actualizar(Compra compra){
        return super.actualizar(compra, "id");
    }
    
    @Override
    public String getOrderBy(){
        if (this.orderBy.equals("")) return "";
        else return " order by " + this.orderBy;
    }
    public TCompra setOrderBy(String orderBy){
        this.orderBy = orderBy;
        return this;
    }
    public Compra getPrimerCompra(Integer id_activo){
        String query = String.format("select * from compra where compra.id_activo = %d order by compra.fecha asc", id_activo);
        return super.getById(query);
    }
    public Compra getCompraPosterior(Compra compra){
        String query;
        if(compra.getId()==0) {
            query  = String.format ("Select * from compra where compra.id_activo = %d and compra.fecha >= '%s'",compra.getId_activo(),compra.getFecha());
        }
        else { query  = String.format ("Select * from compra where compra.id_activo = %d and compra.fecha >= '%s' and compra.id <> %d ",compra.getId_activo(),compra.getFecha(),compra.getId());}
        System.out.println(query);
        return this.getById(query);
    }
     public Compra getCompraAnterior(Compra compra){
         String query;
         if(compra.getId()==0)
              query  = String.format ("Select * from compra where compra.id_activo = %d and compra.fecha <= '%s'",compra.getId_activo(),compra.getFecha());
         else query  = String.format ("Select * from compra where compra.id_activo = %d and compra.fecha <= '%s' and compra.id <> %d",compra.getId_activo(),compra.getFecha(),compra.getId());
        System.out.println(query);
        return this.getById(query);
    }
}
