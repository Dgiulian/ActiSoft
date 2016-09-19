package bd;
public class Habilitacion {

	public Integer id = 0;
	public Integer id_responsable = 0;
        public Integer id_proveedor   = 0;
	public String fecha = "";
	public String archivo = "";

	public Habilitacion() {
	}

	public Habilitacion(Habilitacion habilitacion) {
		this.id = habilitacion.getId();
		this.id_responsable = habilitacion.getId_responsable();
		this.id_proveedor = habilitacion.getId_proveedor();
		this.fecha = habilitacion.getFecha();
		this.archivo = habilitacion.getArchivo();
	}

	public Integer getId() {
		return this.id;
	}

	public Habilitacion setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_responsable() {
		return this.id_responsable;
	}

	public Habilitacion setId_responsable(Integer id_responsable) {
		this.id_responsable = id_responsable;
		return this;
	}
        public Integer getId_proveedor() {
           return id_proveedor;
       }

       public void setId_proveedor(Integer id_proveedor) {
           this.id_proveedor = id_proveedor;
       }
	public String getFecha() {
		return this.fecha;
	}

	public Habilitacion setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public Habilitacion setArchivo(String archivo) {
		this.archivo = archivo;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Habilitacion))
			return false;
		return ((bd.Habilitacion) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}