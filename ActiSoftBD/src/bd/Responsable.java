package bd;
public class Responsable {

	public Integer id = 0;
	public Integer id_proveedor = 0;
	public String nombre = "";
	public String apellido = "";
	public Integer id_estado = 0;

	public Responsable() {
	}

	public Responsable(Responsable responsable) {
		this.id = responsable.getId();
		this.id_proveedor = responsable.getId_proveedor();
		this.nombre = responsable.getNombre();
		this.apellido = responsable.getApellido();
		this.id_estado = responsable.getId_estado();
	}

	public Integer getId() {
		return this.id;
	}

	public Responsable setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_proveedor() {
		return this.id_proveedor;
	}

	public Responsable setId_proveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Responsable setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getApellido() {
		return this.apellido;
	}

	public Responsable setApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}

	public Integer getId_estado() {
		return this.id_estado;
	}

	public Responsable setId_estado(Integer id_estado) {
		this.id_estado = id_estado;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Responsable))
			return false;
		return ((bd.Responsable) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}