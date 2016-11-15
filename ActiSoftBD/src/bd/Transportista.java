package bd;
public class Transportista {

	public Integer id = 0;
	public String nombre = "";
	public String dni = "";
	public Integer id_proveedor = 0;
	public String vencimiento_carnet = "";
	public String vencimiento_seguro = "";
	public String vencimiento_carnet_defensivo = "";
	public String vencimiento_credencial_ipf = "";
	public Integer rsv_presentado = 0;

	public Transportista() {
	}

	public Transportista(Transportista transportista) {
		this.id = transportista.getId();
		this.nombre = transportista.getNombre();
		this.dni = transportista.getDni();
		this.id_proveedor = transportista.getId_proveedor();
		this.vencimiento_carnet = transportista.getVencimiento_carnet();
		this.vencimiento_seguro = transportista.getVencimiento_seguro();
		this.vencimiento_carnet_defensivo = transportista
				.getVencimiento_carnet_defensivo();
		this.vencimiento_credencial_ipf = transportista
				.getVencimiento_credencial_ipf();
		this.rsv_presentado = transportista.getRsv_presentado();
	}

	public Integer getId() {
		return this.id;
	}

	public Transportista setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Transportista setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDni() {
		return this.dni;
	}

	public Transportista setDni(String dni) {
		this.dni = dni;
		return this;
	}

	public Integer getId_proveedor() {
		return this.id_proveedor;
	}

	public Transportista setId_proveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
		return this;
	}

	public String getVencimiento_carnet() {
		return this.vencimiento_carnet;
	}

	public Transportista setVencimiento_carnet(String vencimiento_carnet) {
		this.vencimiento_carnet = vencimiento_carnet;
		return this;
	}

	public String getVencimiento_seguro() {
		return this.vencimiento_seguro;
	}

	public Transportista setVencimiento_seguro(String vencimiento_seguro) {
		this.vencimiento_seguro = vencimiento_seguro;
		return this;
	}

	public String getVencimiento_carnet_defensivo() {
		return this.vencimiento_carnet_defensivo;
	}

	public Transportista setVencimiento_carnet_defensivo(
			String vencimiento_carnet_defensivo) {
		this.vencimiento_carnet_defensivo = vencimiento_carnet_defensivo;
		return this;
	}

	public String getVencimiento_credencial_ipf() {
		return this.vencimiento_credencial_ipf;
	}

	public Transportista setVencimiento_credencial_ipf(
			String vencimiento_credencial_ipf) {
		this.vencimiento_credencial_ipf = vencimiento_credencial_ipf;
		return this;
	}

	public Integer getRsv_presentado() {
		return this.rsv_presentado;
	}

	public Transportista setRsv_presentado(Integer rsv_presentado) {
		this.rsv_presentado = rsv_presentado;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Transportista))
			return false;
		return ((bd.Transportista) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}