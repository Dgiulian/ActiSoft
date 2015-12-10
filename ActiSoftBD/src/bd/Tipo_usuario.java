package bd;
public class Tipo_usuario {

	public Integer id = 0;
	public String codigo = "";
	public String descripcion = "";
	public Integer activo = 0;

	public Integer getId() {
		return this.id;
	}

	public Tipo_usuario setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Tipo_usuario setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Tipo_usuario setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public Tipo_usuario setActivo(Integer activo) {
		this.activo = activo;
		return this;
	}
}