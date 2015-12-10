/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TransaccionBase;

/**
 *
 * @author Diego
 */
public class TGraphData extends TransaccionBase<GraphData> {

  public List<GraphData> getList(String query){
      List<GraphData> lista = new ArrayList<GraphData>();
        conexion.conectarse();
        ResultSet rs = conexion.ejecutarSQLSelect(query);
        Double i = 0.0;
        try {
            while(rs.next()){
                GraphData gd = new GraphData();
                gd.setX(i);                
                gd.setLabel(rs.getString("label"));
                gd.setY(rs.getDouble("y"));
                i++; 
                lista.add(gd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TGraphData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conexion.desconectarse();
        }
    
    return lista;
  }
    public List<GraphData> getGasRate( Integer device_id){
        String query = "";
        query += "select (@row = @row + 1) as x, concat(grc_date,' ',grc_time) as label, rate as y ";
        query += "from grc_data ";
        query += "where grc_data.device_id = %d ";
        query += "order by grc_date,grc_time";
//        System.out.println(String.format(query,device_id));
        
        return this.getList(String.format(query,device_id));
    }
    public List<GraphData> getWaterRate(Integer device_id){
        String query = "";
        query += "select (@row = @row + 1) as x, ";
        query += "concat (owrc_data.owrc_date, ' ', owrc_data.owrc_time) as label, ";
        query += "owrc_data.water_rate as y ";
        query += "from owrc_data ";
        query += "where owrc_data.device_id = %d ";
        query += "order by owrc_date,owrc_time";
//        System.out.println(String.format(query,device_id));
        return this.getList(String.format(query,device_id));
    }
     public List<GraphData> getOilRate(Integer device_id){
        String query = "";
        query += "select (@row = @row + 1) as x, ";
        query += "concat (owrc_data.owrc_date, ' ', owrc_data.owrc_time) as label, ";
        query += "owrc_data.oil_rate as y ";
        query += "from owrc_data ";
        query += "where owrc_data.device_id = %d ";
        query += "order by owrc_date,owrc_time ";
//        System.out.println(String.format(query,device_id));
        return this.getList(String.format(query,device_id));
    }

    @Override
    public List<GraphData> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
