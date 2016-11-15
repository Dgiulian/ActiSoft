package bd;
public class Vehiculo {

	public Integer id = 0;
	public Integer id_proveedor = 0;
	public String dominio = "";
	public String vencimiento_vtv = "";
	public String seguro = "";
	public String poliza = "";
	public String vencimiento_poliza = "";
	public String rsv = "";
	public Integer numero_titulo = 0;
	public String vencimiento_cedula = "";
	public String modelo = "";
	public Integer seguro_xantrax = 0;
	public Integer servicio_mantenimiento = 0;
	public String servicio_fecha = "";
	public Integer servicio_xantrax = 0;

	public Vehiculo() {
	}

	public Vehiculo(Vehiculo vehiculo) {
		this.id = vehiculo.getId();
		this.id_proveedor = vehiculo.getId_proveedor();
		this.dominio = vehiculo.getDominio();
		this.vencimiento_vtv = vehiculo.getVencimiento_vtv();
		this.seguro = vehiculo.getSeguro();
		this.poliza = vehiculo.getPoliza();
		this.vencimiento_poliza = vehiculo.getVencimiento_poliza();
		this.rsv = vehiculo.getRsv();
		this.numero_titulo = vehiculo.getNumero_titulo();
		this.vencimiento_cedula = vehiculo.getVencimiento_cedula();
		this.modelo = vehiculo.getModelo();
		this.seguro_xantrax = vehiculo.getSeguro_xantrax();
		this.servicio_mantenimiento = vehiculo.getServicio_mantenimiento();
		this.servicio_fecha = vehiculo.getServicio_fecha();
		this.servicio_xantrax = vehiculo.getServicio_xantrax();
	}

	public Integer getId() {
		return this.id;
	}

	public Vehiculo setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_proveedor() {
		return this.id_proveedor;
	}

	public Vehiculo setId_proveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
		return this;
	}

	public String getDominio() {
		return this.dominio;
	}

	public Vehiculo setDominio(String dominio) {
		this.dominio = dominio;
		return this;
	}

	public String getVencimiento_vtv() {
		return this.vencimiento_vtv;
	}

	public Vehiculo setVencimiento_vtv(String vencimiento_vtv) {
		this.vencimiento_vtv = vencimiento_vtv;
		return this;
	}

	public String getSeguro() {
		return this.seguro;
	}

	public Vehiculo setSeguro(String seguro) {
		this.seguro = seguro;
		return this;
	}

	public String getPoliza() {
		return this.poliza;
	}

	public Vehiculo setPoliza(String poliza) {
		this.poliza = poliza;
		return this;
	}

	public String getVencimiento_poliza() {
		return this.vencimiento_poliza;
	}

	public Vehiculo setVencimiento_poliza(String vencimiento_poliza) {
		this.vencimiento_poliza = vencimiento_poliza;
		return this;
	}

	public String getRsv() {
		return this.rsv;
	}

	public Vehiculo setRsv(String rsv) {
		this.rsv = rsv;
		return this;
	}

	public Integer getNumero_titulo() {
		return this.numero_titulo;
	}

	public Vehiculo setNumero_titulo(Integer numero_titulo) {
		this.numero_titulo = numero_titulo;
		return this;
	}

	public String getVencimiento_cedula() {
		return this.vencimiento_cedula;
	}

	public Vehiculo setVencimiento_cedula(String vencimiento_cedula) {
		this.vencimiento_cedula = vencimiento_cedula;
		return this;
	}

	public String getModelo() {
		return this.modelo;
	}

	public Vehiculo setModelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	public Integer getSeguro_xantrax() {
		return this.seguro_xantrax;
	}

	public Vehiculo setSeguro_xantrax(Integer seguro_xantrax) {
		this.seguro_xantrax = seguro_xantrax;
		return this;
	}

	public Integer getServicio_mantenimiento() {
		return this.servicio_mantenimiento;
	}

	public Vehiculo setServicio_mantenimiento(Integer servicio_mantenimiento) {
		this.servicio_mantenimiento = servicio_mantenimiento;
		return this;
	}

	public String getServicio_fecha() {
		return this.servicio_fecha;
	}

	public Vehiculo setServicio_fecha(String servicio_fecha) {
		this.servicio_fecha = servicio_fecha;
		return this;
	}

	public Integer getServicio_xantrax() {
		return this.servicio_xantrax;
	}

	public Vehiculo setServicio_xantrax(Integer servicio_xantrax) {
		this.servicio_xantrax = servicio_xantrax;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Vehiculo))
			return false;
		return ((bd.Vehiculo) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}