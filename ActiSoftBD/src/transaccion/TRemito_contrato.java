/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Remito_contrato;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TRemito_contrato extends TransaccionBase<Remito_contrato> {
    public List<Remito_contrato>getList(){
        return super.getList("select * from Remito_contrato");
    }
    public List<Remito_contrato>getByRemitoId(Integer id_remito){
        return super.getList(String.format("select * from Remito_contrato where Remito_contrato.id_remito = %d",id_remito));
    }
    public Remito_contrato getById(Integer id){
        String query = String.format("select * from Remito_contrato where Remito_contrato.id = %d",id);
        return super.getById(query);
    }
    public Remito_contrato getByNumero(Integer numero){
        String query = String.format("select * from Remito_contrato where Remito_contrato.remito_numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Remito_contrato Remito_contrato){
        return super.actualizar(Remito_contrato, "id");
    }
    
}
