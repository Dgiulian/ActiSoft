/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Contrato_detalle;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TContrato_detalle extends TransaccionBase<Contrato_detalle>{
      public List<Contrato_detalle>getList(){
        return super.getList("select * from contrato_detalle");
    }
    public Contrato_detalle getById(Integer id){
        String query = String.format("select * from contrato_detalle where contrato_detalle.id = %d",id);
        return super.getById(query);
    }
    public List<Contrato_detalle> getByContratoId(Integer id_contrato){
        String query = String.format("select * from contrato_detalle where contrato_detalle.id_contrato = %d order by posicion",id_contrato);
        return super.getList(query);
    }
    public boolean actualizar(Contrato_detalle contrato_detalle){
        return super.actualizar(contrato_detalle, "id");
    }
    @Override
    public String getOrderBy(){
        return " order  by contrato_detalle.id_contrato, contrato_detalle.posicion";
    }
}
