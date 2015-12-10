/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;


import bd.Remito_detalle_view;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class TRemito_detalle_view extends TransaccionBase<Remito_detalle_view>{
    public List<Remito_detalle_view>getList(){
        return super.getList("select * from remito_detalle_view");
    }
    public Remito_detalle_view getById(Integer id){
        String query = String.format("select * from remito_detalle_view where remito_detalle_view.id = %d",id);
        return super.getById(query);
    }
    public List<Remito_detalle_view> getByRemitoId(Integer id_remito){
        String query = String.format("select * from remito_detalle_view where remito_detalle_view.id_remito = %d",id_remito);
        return super.getList(query);
    }
    public boolean actualizar(Remito_detalle_view detalle){
        return super.actualizar(detalle, "id");
    }
    public HashMap<Integer,Float> getGroupBySubrubro(Integer id_remito){
        String query = String.format("select id_subrubro,sum(cantidad) from remito_detalle_view " +
                       " where remito_detalle_view.id_remito = %d" +
                       " group by remito_detalle_view.id_subrubro",id_remito);
        return this.getGroupBy(query);
    }
    public HashMap<Integer,Float> getGroupBy(String query){
        HashMap<Integer,Float> mapa = new HashMap<Integer,Float>();
        conexion.conectarse();
        ResultSet rs = conexion.ejecutarSQLSelect(query);
        try {
            while(rs.next()){
                Integer key = rs.getInt(1);
                Float   val = rs.getFloat(2);                     
            }
        } catch (SQLException ex) {
            Logger.getLogger(TRemito_detalle_view.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.desconectarse();
        return mapa;
    }
}
