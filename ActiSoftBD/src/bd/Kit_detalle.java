package bd;
public class Kit_detalle {

	public Integer id = 0;
	public Integer id_kit = 0;
	public Integer id_activo = 0;
	public Integer cantidad = 0;

	public Integer getId() {
		return this.id;
	}

	public Kit_detalle setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_kit() {
		return this.id_kit;
	}

	public Kit_detalle setId_kit(Integer id_kit) {
		this.id_kit = id_kit;
		return this;
	}

	public Integer getId_activo() {
		return this.id_activo;
	}

	public Kit_detalle setId_activo(Integer id_activo) {
		this.id_activo = id_activo;
		return this;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public Kit_detalle setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
		return this;
	}
}