package bd;
public class Auditoria {

    protected Integer id = 0;
    protected Integer id_usuario = 0;
    protected Integer id_tipo_usuario = 0;
    protected Integer id_modulo = 0;
    protected Integer id_accion = 0;
    protected Integer id_referencia = 0;
    protected String fecha = "";
    
        public Auditoria(){}

        public Auditoria(Auditoria auditoria){
            this.id              = auditoria.getId();
            this.id_usuario      = auditoria.getId_usuario();
            this.id_tipo_usuario = auditoria.getId_tipo_usuario();
            this.id_modulo       = auditoria.getId_modulo();
            this.id_accion       = auditoria.getId_accion();
            this.id_referencia   = auditoria.getId_referencia();
            this.fecha           =  auditoria.getFecha();
        }
	public Integer getId() {
		return this.id;
	}

	public Auditoria setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_usuario() {
		return this.id_usuario;
	}

	public Auditoria setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
		return this;
	}

	public Integer getId_tipo_usuario() {
		return this.id_tipo_usuario;
	}

	public Auditoria setId_tipo_usuario(Integer id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
		return this;
	}

	public Integer getId_modulo() {
		return this.id_modulo;
	}

	public Auditoria setId_modulo(Integer id_modulo) {
		this.id_modulo = id_modulo;
		return this;
	}

	public Integer getId_accion() {
		return this.id_accion;
	}

	public Auditoria setId_accion(Integer id_accion) {
		this.id_accion = id_accion;
		return this;
	}
        public Integer getId_referencia() {
		return this.id_referencia;
	}

	public Auditoria setId_referencia(Integer id_referencia) {
		this.id_referencia = id_referencia;
		return this;
	}
	public String getFecha() {
		return this.fecha;
	}

	public Auditoria setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}
}