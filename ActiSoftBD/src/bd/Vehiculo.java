package bd;
public class Vehiculo {

	public Integer id = 0;
	public Integer id_proveedor = 0;
        public String  dominio = "";
	public String  vencimiento_vtv = "";
	public String  seguro = "";
	public String  poliza = "";
	public String  vencimiento_poliza = "";
	public String  rsv = "";

	public Vehiculo() {
	}

	public Vehiculo(Vehiculo vehiculo) {
		this.id = vehiculo.getId();
		this.id_proveedor = vehiculo.getId_proveedor();
		this.vencimiento_vtv = vehiculo.getVencimiento_vtv();
		this.seguro = vehiculo.getSeguro();
		this.poliza = vehiculo.getPoliza();
		this.vencimiento_poliza = vehiculo.getVencimiento_poliza();
		this.rsv = vehiculo.getRsv();
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

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }
}