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
        public Compra(){}
        public Compra(Compra compra){
            this.id = compra.getId();
            this.id_activo = compra.getId_activo();
            this.fecha = compra.getFecha();
            this.id_divisa = compra.getId_divisa();
            this.cantidad = compra.getCantidad();
            this.precio_unit = compra.getPrecio_unit();
            this.precio_tot = compra.getPrecio_tot();
            this.id_proveedor = compra.getId_proveedor();
        }
	public Integer getId() {
		return this.id;
	}

	public Integer getId_activo() {
		return this.id_activo;
	}

	
	public String getFecha() {
		return this.fecha;
	}

	

	public Integer getId_divisa() {
		return this.id_divisa;
	}

	public Float getCantidad() {
		return this.cantidad;
	}

	
	public Float getPrecio_unit() {
		return this.precio_unit;
	}

	public Float getPrecio_tot() {
		return this.precio_tot;
	}
	public Compra setId(Integer id) {
		this.id = id;
		return this;
	}        
        public Compra setId_activo(Integer id_activo) {
		this.id_activo = id_activo;
		return this;
	}
        public Compra setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}
	public Compra setCantidad(Float cantidad) {
		this.cantidad = cantidad;
		return this;
	}

        public Compra setId_divisa(Integer id_divisa) {
		this.id_divisa = id_divisa;
		return this;
	}
	public Integer getId_proveedor() {
		return this.id_proveedor;
	}
        public Compra setPrecio_unit(Float precio_unit) {
		this.precio_unit = precio_unit;
		return this;
	}

        public Compra setPrecio_tot(Float precio_tot) {
		this.precio_tot = precio_tot;
		return this;
	}
	public Compra setId_proveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
		return this;
	}
}