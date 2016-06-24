package bd;
public class Transportista {

	public Integer id = 0;
        public Integer id_proveedor = 0;
	public String nombre = "";
<<<<<<< HEAD
        public String dni    = "";


=======
>>>>>>> f5e2778a05d4d42a4d981882bc57292ab29eb25e
	public String vencimiento_carnet = "";
	public String vencimiento_seguro = "";

	public Transportista() {
	}

	public Transportista(Transportista transportista) {
		this.id = transportista.getId();
		this.nombre = transportista.getNombre();
		this.vencimiento_carnet = transportista.getVencimiento_carnet();
		this.vencimiento_seguro = transportista.getVencimiento_seguro();
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

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
<<<<<<< HEAD
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
=======
>>>>>>> f5e2778a05d4d42a4d981882bc57292ab29eb25e
}