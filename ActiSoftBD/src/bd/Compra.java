package bd;
public class Compra {

	public Integer id = 0;
	public Integer id_activo = 0;
	public String fecha = "";
	public Integer id_divisa = 0;
	public Float cantidad = 0f;
	public Float precio_unit = 0f;
	public Float precio_tot = 0f;
	public Integer id_proveedor = 0;
	public Integer id_accion = 0;
	public Float stock_anterior = 0f;
	public String factura = "";

	public Compra() {
	}

	public Compra(Compra compra) {
		this.id = compra.getId();
		this.id_activo = compra.getId_activo();
		this.fecha = compra.getFecha();
		this.id_divisa = compra.getId_divisa();
		this.cantidad = compra.getCantidad();
		this.precio_unit = compra.getPrecio_unit();
		this.precio_tot = compra.getPrecio_tot();
		this.id_proveedor = compra.getId_proveedor();
		this.id_accion = compra.getId_accion();
		this.stock_anterior = compra.getStock_anterior();
		this.factura = compra.getFactura();
	}

	public Integer getId() {
		return this.id;
	}

	public Compra setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_activo() {
		return this.id_activo;
	}

	public Compra setId_activo(Integer id_activo) {
		this.id_activo = id_activo;
		return this;
	}

	public String getFecha() {
		return this.fecha;
	}

	public Compra setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}

	public Integer getId_divisa() {
		return this.id_divisa;
	}

	public Compra setId_divisa(Integer id_divisa) {
		this.id_divisa = id_divisa;
		return this;
	}

	public Float getCantidad() {
		return this.cantidad;
	}

	public Compra setCantidad(Float cantidad) {
		this.cantidad = cantidad;
		return this;
	}

	public Float getPrecio_unit() {
		return this.precio_unit;
	}

	public Compra setPrecio_unit(Float precio_unit) {
		this.precio_unit = precio_unit;
		return this;
	}

	public Float getPrecio_tot() {
		return this.precio_tot;
	}

	public Compra setPrecio_tot(Float precio_tot) {
		this.precio_tot = precio_tot;
		return this;
	}

	public Integer getId_proveedor() {
		return this.id_proveedor;
	}

	public Compra setId_proveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
		return this;
	}

	public Integer getId_accion() {
		return this.id_accion;
	}

	public Compra setId_accion(Integer id_accion) {
		this.id_accion = id_accion;
		return this;
	}

	public Float getStock_anterior() {
		return this.stock_anterior;
	}

	public Compra setStock_anterior(Float stock_anterior) {
		this.stock_anterior = stock_anterior;
		return this;
	}
	public String getFactura() {
		return this.factura;
	}

	public Compra setFactura(String factura) {
		this.factura = factura;
		return this;
	}
}