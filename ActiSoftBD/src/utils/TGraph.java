/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class TGraph {
    Conexion conexion = new Conexion();
    public List<DonutData> getActivosxEstado(){
        List<DonutData> lista = new ArrayList<DonutData>();
        HashMap<Integer, OptionsCfg.Option> mapa = OptionsCfg.getMap(OptionsCfg.getEstadoActivo());
        try {
            String query = "SELECT activo.id_estado,count(*)\n" +
            "  from activo\n" +
            " group by activo.id_estado";
            conexion.conectarse();
            ResultSet rs = conexion.ejecutarSQLSelect(query);
            Integer id_estado;
            Integer cant;
            String label = "";
            while(rs.next()){
                id_estado = rs.getInt(1);
                cant = rs.getInt(2);
                OptionsCfg.Option o = mapa.get(id_estado);
                if(o!=null) label = o.getDescripcion();
                else label = id_estado.toString();
                lista.add(new DonutData(label,cant.toString()));
            }
            conexion.desconectarse();
            
        } catch (SQLException ex) {
            Logger.getLogger(TGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
