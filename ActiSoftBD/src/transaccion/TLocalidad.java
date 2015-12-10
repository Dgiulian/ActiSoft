/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Localidad;
import bd.Provincia;
import conexion.Conexion;
import conexion.TransaccionRS;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Axioma
 */
public class TLocalidad extends TransaccionBase<Localidad>{

    public List<Localidad> recuperarLocalidades(int idprovincia) {
        //String provincia permite hacer un filtro en la consulta, si es distinta de Null

        //**************
        String where = "";
        boolean band = false;

        String sql = "select * from localidad where localidad.prov_id=" + idprovincia + " order by localidad.loc_descripcion";
        return this.getList(sql);
    }
    public List<Localidad> getList(){
        String sql = "select * from localidad order by localidad.loc_descripcion";
        return this.getList(sql);
    }
    public Localidad getById(int idlocalidad) {
        //String provincia permite hacer un filtro en la consulta, si es distinta de Null
        String where = "";
        boolean band = false;

        String sql = "select * from localidad where localidad.loc_id=" + idlocalidad + " order by localidad.loc_descripcion";

        return this.getById(where);

    }
    public boolean actualizar(Localidad localidad){
        return super.actualizar(localidad, "loc_id");
    }
    @Override
    public HashMap<Integer,Localidad> getMap() {
        HashMap<Integer,Localidad> mapa = new HashMap<>();
        for (Localidad l : this.getList()) mapa.put(l.getLoc_id(),l);
        return mapa;
    }
}
