/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo_remito;

import java.util.List;

/**
 *
 * @author Diego
 */
public class TActivo_remito extends TransaccionBase<Activo_remito> {
    public List<Activo_remito>getList(){
        return super.getList("select * from activo_remito");
    }
     public List<Activo_remito>getByContratoId(Integer id_contrato){
        return super.getList(String.format("select * from activo_remito where activo_remito.id_contrato = %d",id_contrato));
    }
    public Activo_remito getById(Integer id){
        String query = String.format("select * from activo_remito where activo_remito.id = %d",id);
        return super.getById(query);
    }
     public List<Activo_remito> getByIdActivo(Integer id_activo){
        String query = String.format("select * from activo_remito where activo_remito.id_activo = %d",id_activo);
        return super.getList(query);
    }
     public Activo_remito getByNumero(Integer numero){
        String query = String.format("select * from activo_remito where activo_remito.numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Activo_remito activo_remito){
        return super.actualizar(activo_remito, "id");
    }
    @Override
    public String getOrderBy(){
        return " order by activo_remito.fecha ";        
    }
    
}
