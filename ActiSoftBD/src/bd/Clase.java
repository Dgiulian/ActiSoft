package bd;
public class Clase {

	public Integer id = 0;
	public String codigo = "";
	public String descripcion = "";
	public Integer id_rubro = 0;

	public Integer getId() {
		return this.id;
	}

	public Clase setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Clase setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Clase setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Integer getId_rubro() {
		return this.id_rubro;
	}

	public Clase setId_rubro(Integer id_rubro) {
		this.id_rubro = id_rubro;
		return this;
	}
}