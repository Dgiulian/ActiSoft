package transaccion;

import bd.Transportista;
import java.util.List;
public class TTransportista extends TransaccionBase<Transportista> {

	@Override
	public List<Transportista> getList() {
		return super.getList("select * from transportista ");
	}

	public Boolean actualizar(Transportista transportista) {
		return super.actualizar(transportista, "id");
	}

	public Transportista getById(Integer id) {
		String query = String.format(
				"select * from transportista where transportista.id = %d ", id);
		return super.getById(query);
	}
}