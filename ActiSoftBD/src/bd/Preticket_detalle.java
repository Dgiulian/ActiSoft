package bd;
public class Preticket_detalle {

	public Integer id_preticket = 0;
	public Integer remito_inicio = 0;
	public Integer remito_cierre = 0;
	public String fecha_inicio = "";
	public String fecha_cierre = "";
	public Integer dias = 0;
	public Integer posicion = 0;
	public String descripcion = "";
	public Float cantidad = 0f;
	public Float precio = 0f;
	public Integer id_divisa = 0;
	public Float subtotal = 0f;

	public Integer getId_preticket() {
		return this.id_preticket;
	}

	public Preticket_detalle setId_preticket(Integer id_preticket) {
		this.id_preticket = id_preticket;
		return this;
	}

	public Integer getRemito_inicio() {
		return this.remito_inicio;
	}

	public Preticket_detalle setRemito_inicio(Integer remito_inicio) {
		this.remito_inicio = remito_inicio;
		return this;
	}

	public Integer getRemito_cierre() {
		return this.remito_cierre;
	}

	public Preticket_detalle setRemito_cierre(Integer remito_cierre) {
		this.remito_cierre = remito_cierre;
		return this;
	}

	public String getFecha_inicio() {
		return this.fecha_inicio;
	}

	public Preticket_detalle setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
		return this;
	}

	public String getFecha_cierre() {
		return this.fecha_cierre;
	}

	public Preticket_detalle setFecha_cierre(String fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
		return this;
	}

	public Integer getDias() {
		return this.dias;
	}

	public Preticket_detalle setDias(Integer dias) {
		this.dias = dias;
		return this;
	}

	public Integer getPosicion() {
		return this.posicion;
	}

	public Preticket_detalle setPosicion(Integer posicion) {
		this.posicion = posicion;
		return this;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Preticket_detalle setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Float getCantidad() {
		return this.cantidad;
	}

	public Preticket_detalle setCantidad(Float cantidad) {
		this.cantidad = cantidad;
		return this;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public Preticket_detalle setPrecio(Float precio) {
		this.precio = precio;
		return this;
	}

	public Integer getId_divisa() {
		return this.id_divisa;
	}

	public Preticket_detalle setId_divisa(Integer id_divisa) {
		this.id_divisa = id_divisa;
		return this;
	}

	public Float getSubtotal() {
		return this.subtotal;
	}

	public Preticket_detalle setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
		return this;
	}
}