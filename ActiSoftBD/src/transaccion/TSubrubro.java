/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Subrubro;
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
public class TSubrubro extends TransaccionBase<Subrubro>{
    public List<Subrubro> getList() {
        return super.getList("select * from subrubro");
    }
    public Subrubro getById(Integer id){
        String query = String.format("select * from subrubro where subrubro.id = %d",id);
        return super.getById(query);
    }
//    public
//    new HashMap<Integer,Subrubro>();        
//        for (Subrubro s : new TSubrubro().getList()) mapSubrubros.put(s.getId(),s);
        
    public List<Subrubro> getByRubroId(Integer id_rubro){
        String query = String.format("select * from subrubro where subrubro.id_rubro = %d",id_rubro);
        return super.getList(query);
    }
    
    public Subrubro getByCodigo(String codigo){
        String query = String.format("select * from subrubro where subrubro.codigo = '%s'",codigo);
        System.out.println(query);
        return super.getById(query);
    }
    public Subrubro getByDescripcion(String descripcion){
        String query = String.format("select * from subrubro where subrubro.descripcion = '%s'",descripcion);
        //System.out.println(query);
        return super.getById(query);
    }
    public Subrubro getByDescripcion(Integer id_rubro,String descripcion){
        String query = String.format("select * from subrubro where subrubro.id_rubro = %d and subrubro.descripcion = '%s'",id_rubro,descripcion);
        //System.out.println(query);
        return super.getById(query);
    }
    public boolean actualizar(Subrubro rubro){
        return super.actualizar(rubro, "id");
    }
    public Integer countByRubroId(Integer id_rubro){
        String query = String.format("select count(*) as cant from subrubro where subrubro.id_rubro =  %d",id_rubro);
        
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
                Logger.getLogger(TSubrubro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        conexion.desconectarse();
        return cant;
    }
}
