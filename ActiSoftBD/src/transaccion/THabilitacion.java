package transaccion;

import bd.Habilitacion;
import java.util.List;
public class THabilitacion extends TransaccionBase<Habilitacion> {

	@Override
	public List<Habilitacion> getList() {
		return super.getList("select * from habilitacion ");
	}

	public Boolean actualizar(Habilitacion habilitacion) {
		return super.actualizar(habilitacion, "id");
	}

	public Habilitacion getById(Integer id) {
		String query = String.format(
				"select * from habilitacion where habilitacion.id = %d ", id);
		return super.getById(query);
	}

    public void bajaByResponsable(Integer id) {        
        String query = String.format("delete from habilitacion where habilitacion.id_responsable = %d", id);
        conexion.conectarse();
        conexion.ejecutarSQL(query);
        conexion.desconectarse();
    }
}