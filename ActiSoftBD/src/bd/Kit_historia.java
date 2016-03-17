package bd;
public class Kit_historia {

	public Integer id = 0;
	public Integer id_kit = 0;
	public Integer id_activo = 0;
	public String fecha = "";
	public Integer id_accion = 0;

	public Kit_historia() {
	}

	public Kit_historia(Kit_historia Kit_historia) {
		this.id = Kit_historia.getId();
		this.id_kit = Kit_historia.getId_kit();
		this.id_activo = Kit_historia.getId_activo();
		this.fecha = Kit_historia.getFecha();
		this.id_accion = Kit_historia.getId_accion();
	}

	public Integer getId() {
		return this.id;
	}

	public Kit_historia setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_kit() {
		return this.id_kit;
	}

	public Kit_historia setId_kit(Integer id_kit) {
		this.id_kit = id_kit;
		return this;
	}

	public Integer getId_activo() {
		return this.id_activo;
	}

	public Kit_historia setId_activo(Integer id_activo) {
		this.id_activo = id_activo;
		return this;
	}

	public String getFecha() {
		return this.fecha;
	}

	public Kit_historia setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}

	public Integer getId_accion() {
		return this.id_accion;
	}

	public Kit_historia setId_accion(Integer id_accion) {
		this.id_accion = id_accion;
		return this;
	}
}