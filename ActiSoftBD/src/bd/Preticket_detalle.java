package bd;
public class Preticket_detalle {

	public Integer id = 0;
	public Integer id_preticket = 0;
	public Integer remito_inicio = 0;
	public Integer remito_cierre = 0;
	public Integer id_remito_inicio = 0;
	public Integer id_remito_cierre = 0;
	public String fecha_inicio = "";
	public String fecha_cierre = "";
	public Integer dias = 0;
	public Integer posicion = 0;
	public String descripcion = "";
	public Float cantidad = 0f;
	public Float precio = 0f;
	public Integer id_unidad = 0;
	public Integer id_divisa = 0;
	public Float subtotal = 0f;

	public Preticket_detalle() {
	}

	public Preticket_detalle(Preticket_detalle preticket_detalle) {
		this.id = preticket_detalle.getId();
		this.id_preticket = preticket_detalle.getId_preticket();
		this.remito_inicio = preticket_detalle.getRemito_inicio();
		this.remito_cierre = preticket_detalle.getRemito_cierre();
		this.id_remito_inicio = preticket_detalle.getId_remito_inicio();
		this.id_remito_cierre = preticket_detalle.getId_remito_cierre();
		this.fecha_inicio = preticket_detalle.getFecha_inicio();
		this.fecha_cierre = preticket_detalle.getFecha_cierre();
		this.dias = preticket_detalle.getDias();
		this.posicion = preticket_detalle.getPosicion();
		this.descripcion = preticket_detalle.getDescripcion();
		this.cantidad = preticket_detalle.getCantidad();
		this.precio = preticket_detalle.getPrecio();
		this.id_unidad = preticket_detalle.getId_unidad();
		this.id_divisa = preticket_detalle.getId_divisa();
		this.subtotal = preticket_detalle.getSubtotal();
	}

	public Integer getId() {
		return this.id;
	}

	public Preticket_detalle setId(Integer id) {
		this.id = id;
		return this;
	}

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

	public Integer getId_remito_inicio() {
		return this.id_remito_inicio;
	}

	public Preticket_detalle setId_remito_inicio(Integer id_remito_inicio) {
		this.id_remito_inicio = id_remito_inicio;
		return this;
	}

	public Integer getId_remito_cierre() {
		return this.id_remito_cierre;
	}

	public Preticket_detalle setId_remito_cierre(Integer id_remito_cierre) {
		this.id_remito_cierre = id_remito_cierre;
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

	public Integer getId_unidad() {
		return this.id_unidad;
	}

	public Preticket_detalle setId_unidad(Integer id_unidad) {
		this.id_unidad = id_unidad;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Preticket_detalle))
			return false;
		return ((bd.Preticket_detalle) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}