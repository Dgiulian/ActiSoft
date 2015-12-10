/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Provincia;
import conexion.Conexion;
import conexion.TransaccionRS;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Juan Manuel
 */
public class TProvincia extends TransaccionBase<Provincia>{

    public int AltaProvincia(String descripcion) {
        //Devuelve 0 si no se pudo insertar el registro
        // Si se inserto correctamente devuelve el id generado
        Provincia provincia = new Provincia();
        provincia.setProv_descripcion(descripcion);
        return this.alta(provincia);
    }
    public List<Provincia> getList(){
        String sql = "select * from provincia order by provincia.prov_descripcion";
        return this.getList(sql);
    }
    public List<Provincia> getById(int idprovincia, String provincia) {
        //String provincia permite hacer un filtro en la consulta, si es distinta de Null

        //**************
        String where = "";
        boolean band = false;

        if (idprovincia != 0) {
            where = "provincia.prov_id= " + idprovincia;
            band = true;
        }

        if (provincia != null) {
            if (!band) {
                where = "provincia.prov_descripcion like '%" + provincia + "%'";
            } else {
                where = where + " and provincia.prov_descripcion= '%" + provincia + "%'";
            }
            band = true;
        }
        //**************
        String sql="";
        if (band) {
            sql = "select * from provincia where " + where + " order by provincia.prov_descripcion";
        } else {
            sql = "select * from provincia order by provincia.prov_descripcion";
        }
        
        return this.getList(sql);

    }
    
    
    public Provincia getById(int idprovincia) {
        //String provincia permite hacer un filtro en la consulta, si es distinta de Null
        
        //**************
        String where = "";
        boolean band = false;

        String sql = "select * from provincia where provincia.prov_id=" + idprovincia + " order by provincia.prov_descripcion";       
        return  this.getById(sql);
    }

    public boolean baja(int provincia_id) {

        Provincia provincia = new Provincia();
        provincia.setProv_id(provincia_id);

        return super.baja(provincia);

    }
    public boolean actualizar(Provincia provincia){
        return super.actualizar(provincia, "prov_id");
    }
    @Override
    public HashMap<Integer,Provincia> getMap(){
        HashMap<Integer,Provincia> mapa =   new HashMap<>();        
        for (Provincia p : this.getList()) mapa.put(p.getProv_id(),p);
        return mapa;
    }

   
}
