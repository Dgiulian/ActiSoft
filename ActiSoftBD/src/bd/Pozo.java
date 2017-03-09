package bd;
public class Pozo {

	public Integer id = 0;
	public String nombre = "";
	public String descripcion = "";

	public Pozo() {
	}

	public Pozo(Pozo pozo) {
		this.id = pozo.getId();
		this.nombre = pozo.getNombre();
		this.descripcion = pozo.getDescripcion();
	}

	public Integer getId() {
		return this.id;
	}

	public Pozo setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Pozo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Pozo setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Pozo))
			return false;
		return ((bd.Pozo) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}