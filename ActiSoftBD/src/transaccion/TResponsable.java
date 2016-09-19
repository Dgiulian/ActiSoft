package transaccion;

import bd.Responsable;
import java.util.List;
public class TResponsable extends TransaccionBase<Responsable> {

	@Override
	public List<Responsable> getList() {
		return super.getList("select * from responsable ");
	}

	public Boolean actualizar(Responsable responsable) {
		return super.actualizar(responsable, "id");
	}

	public Responsable getById(Integer id) {
		String query = String.format(
				"select * from responsable where responsable.id = %d ", id);
		return super.getById(query);
	}
}