package transaccion;

import bd.Equipo;
import java.util.List;
public class TEquipo extends TransaccionBase<Equipo> {

	@Override
	public List<Equipo> getList() {
		return super.getList("select * from equipo order by nombre");
	}

	public Boolean actualizar(Equipo equipo) {
		return super.actualizar(equipo, "id");
	}

	public Equipo getById(Integer id) {
		String query = String.format(
				"select * from equipo where equipo.id = %d ", id);
		return super.getById(query);
	}
}