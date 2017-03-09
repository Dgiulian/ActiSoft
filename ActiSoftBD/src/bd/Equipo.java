package bd;
public class Equipo {

	public Integer id = 0;
	public Integer id_pozo = 0;
	public String nombre = "";
	public String descripcion = "";

	public Equipo() {
	}

	public Equipo(Equipo equipo) {
		this.id = equipo.getId();
		this.id_pozo = equipo.getId_pozo();
		this.nombre = equipo.getNombre();
		this.descripcion = equipo.getDescripcion();
	}

	public Integer getId() {
		return this.id;
	}

	public Equipo setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_pozo() {
		return this.id_pozo;
	}

	public Equipo setId_pozo(Integer id_pozo) {
		this.id_pozo = id_pozo;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Equipo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Equipo setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Equipo))
			return false;
		return ((bd.Equipo) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}