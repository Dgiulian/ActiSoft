package transaccion;

import bd.Kit_historia;
import java.util.List;
public class TKit_historia extends TransaccionBase<Kit_historia> {

	@Override
	public List<Kit_historia> getList() {
		return super.getList("select * from kit_historia ");
	}

	public Boolean actualizar(Kit_historia kit_historia) {
		return super.actualizar(kit_historia, "id");
	}

	public Kit_historia getById(Integer id) {
		String query = String.format(
				"select * from kit_historia where kit_historia.id = %d ", id);
		return super.getById(query);
	}
}