package bd;
public class Agenda {

	public Integer id = 0;
	public Integer id_tipo = 0;
	public String titulo = "";
	public String descripcion = "";
	public String desde = "";
	public String hasta = "";
        public Integer alerta = 0;
	public Integer id_estado = 0;
	public Integer id_usuario = 0;

	public Integer getId() {
		return this.id;
	}

	public Agenda setId(Integer id) {
            this.id = id;
            return this;
	}

	public Integer getId_tipo() {
            return this.id_tipo;
	}

	public Agenda setId_tipo(Integer id_tipo) {
            this.id_tipo = id_tipo;
            return this;
	}

	public String getTitulo() {
            return this.titulo;
	}

	public Agenda setTitulo(String titulo) {
            this.titulo = titulo;
            return this;
	}

	public String getDescripcion() {
            return this.descripcion;
	}

	public Agenda setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
	}

	public String getDesde() {
            return this.desde;
	}

	public Agenda setDesde(String desde) {
            this.desde = desde;
            return this;
	}

	public String getHasta() {
            return this.hasta;
	}

	public Agenda setHasta(String hasta) {
            this.hasta = hasta;
            return this;
	}

	public Integer getId_estado() {
            return this.id_estado;
	}

	public Agenda setId_estado(Integer id_estado) {
            this.id_estado = id_estado;
            return this;
	}
    public Integer getAlerta() {
                    return this.alerta;
            }

	public Agenda setAlerta(Integer alerta) {
            this.alerta = alerta;
            return this;
	}
	public Integer getId_usuario() {
            return this.id_usuario;
	}

	public Agenda setId_usuario(Integer id_usuario) {
            this.id_usuario = id_usuario;
            return this;
	}
}