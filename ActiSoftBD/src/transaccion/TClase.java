/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Clase;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class TClase extends TransaccionBase<Clase>{
    public List<Clase> getList() {
        return super.getList("select * from clase");
    }
    public Clase getById(Integer id){
        String query = String.format("select * from clase where clase.id = %d",id);
        return super.getById(query);
    }
    public List<Clase> getByRubroId(Integer id_rubro){
        String query = String.format("select * from clase where clase.id_rubro = %d",id_rubro);
        return super.getList(query);
    }
    
    public Clase getByCodigo(String codigo){
        String query = String.format("select * from clase where clase.codigo = '%s'",codigo);
        System.out.println(query);
        return super.getById(query);
    }
    public Clase getByDescripcion(String descripcion){
        String query = String.format("select * from clase where clase.descripcion = '%s'",descripcion);
        //System.out.println(query);
        return super.getById(query);
    }
    public Clase getByDescripcion(Integer id_rubro,String descripcion){
        String query = String.format("select * from clase where clase.id_rubro = %d and clase.descripcion = '%s'",id_rubro,descripcion);
        //System.out.println(query);
        return super.getById(query);
    }
    public boolean actualizar(Clase rubro){
        return super.actualizar(rubro, "id");
    }
    public Integer countByRubroId(Integer id_rubro){
        String query = String.format("select count(*) as cant from clase where clase.id_rubro =  %d",id_rubro);
        
        conexion.conectarse();
        ResultSet rs = conexion.ejecutarSQLSelect(query);
        Integer cant = 0;
        if(rs!=null) {
            try {
                while(rs.next()){
                    cant = rs.getInt("cant");
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(TClase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        conexion.desconectarse();
        return cant;
    }
}
