package transaccion;

import bd.Pozo;
import java.util.List;
public class TPozo extends TransaccionBase<Pozo> {

	@Override
	public List<Pozo> getList() {
		return super.getList("select * from pozo order by nombre");
	}

	public Boolean actualizar(Pozo pozo) {
		return super.actualizar(pozo, "id");
	}

	public Pozo getById(Integer id) {
		String query = String.format("select * from pozo where pozo.id = %d ",
				id);
		return super.getById(query);
	}
}