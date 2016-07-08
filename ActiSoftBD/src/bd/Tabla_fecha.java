/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import utils.Fecha;

/**
 *
 * @author Diego
 */
public class Tabla_fecha {
    public Integer id =0;
    public Fecha fecha = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
    
     public void setFecha(String fecha) {
         
        this.fecha = new Fecha(fecha);
    }
}
