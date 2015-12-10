/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Activo_historia;
import java.util.List;

/**
 *
 * @author Diego
 */
public class TActivo_historia extends TransaccionBase<Activo_historia> {
    public List<Activo_historia>getList(){
        return super.getList("select * from activo_historia");
    }
     public List<Activo_historia>getByContratoId(Integer id_contrato){
        return super.getList(String.format("select * from activo_historia where activo_historia.id_contrato = %d",id_contrato));
    }
    public Activo_historia getById(Integer id){
        String query = String.format("select * from activo_historia where activo_historia.id = %d",id);
        return super.getById(query);
    }
     public List<Activo_historia> getByIdActivo(Integer id_activo){
        String query = String.format("select * from activo_historia where activo_historia.id_activo = %d",id_activo);
        return super.getList(query);
    }
     public Activo_historia getByNumero(Integer numero){
        String query = String.format("select * from activo_historia where activo_historia.numero = %d",numero);
        return super.getById(query);
    }
    public boolean actualizar(Activo_historia activo_historia){
        return super.actualizar(activo_historia, "id");
    }
    @Override
    public String getOrderBy(){
        return " order by activo_historia.fecha ";        
    }
}
