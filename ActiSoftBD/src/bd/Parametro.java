package bd;
public class Parametro {

	public Integer id = 0;
	public Integer numero = 0;
	public String codigo = "";
	public String nombre = "";
	public String valor = "";

	public Integer getId() {
		return this.id;
	}

	public Parametro setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public Parametro setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Parametro setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Parametro setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getValor() {
		return this.valor;
	}

	public Parametro setValor(String valor) {
		this.valor = valor;
		return this;
	}
}