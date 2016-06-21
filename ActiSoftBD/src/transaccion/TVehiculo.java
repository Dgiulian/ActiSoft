package transaccion;

import bd.Vehiculo;
import java.util.List;
public class TVehiculo extends TransaccionBase<Vehiculo> {

	@Override
	public List<Vehiculo> getList() {
		return super.getList("select * from vehiculo ");
	}

	public Boolean actualizar(Vehiculo vehiculo) {
		return super.actualizar(vehiculo, "id");
	}

	public Vehiculo getById(Integer id) {
		String query = String.format(
				"select * from vehiculo where vehiculo.id = %d ", id);
		return super.getById(query);
	}
}