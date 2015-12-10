/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Contrato_detalle_view;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TContrato_detalle_view extends TransaccionBase<Contrato_detalle_view>{
      public List<Contrato_detalle_view>getList(){
        return super.getList("select * from contrato_detalle_view");
    }
    public Contrato_detalle_view getById(Integer id){
        String query = String.format("select * from contrato_detalle_view where contrato_detalle_view.id = %d",id);
        return super.getById(query);
    }
    public List<Contrato_detalle_view> getByContratoId(Integer id_contrato){
        String query = String.format("select * from contrato_detalle_view where contrato_detalle_view.id_contrato = %d order by posicion",id_contrato);
        return super.getList(query);
    }
    public boolean actualizar(Contrato_detalle_view contrato_detalle_view){
        return super.actualizar(contrato_detalle_view, "id");
    }
}
