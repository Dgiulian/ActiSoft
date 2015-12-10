package bd;
public class Usuario {

	public Integer id = 0;
	public Integer id_tipo_usuario = 0;
	public String usu_mail = "";
	public String usu_password = "";
	public String usu_fcreacion = "";
	public Integer usu_activo = 0;
	public String usu_hash_activa = "";
        public Usuario(){}
        public Usuario(Usuario usuario){
            this.id = usuario.getId();
            this.id_tipo_usuario = usuario.getId_tipo_usuario();
            this.usu_mail = usuario.getUsu_mail();
            this.usu_password = usuario.getUsu_password();
            this.usu_fcreacion = usuario.getUsu_fcreacion();
            this.usu_activo = usuario.getUsu_activo();
            this.usu_hash_activa = usuario.getUsu_hash_activa();
        }
	public Integer getId() {
		return this.id;
	}

	public Usuario setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_tipo_usuario() {    
            return this.id_tipo_usuario;
	}

	public Usuario setId_tipo_usuario(Integer id_tipo_usuario) {
            this.id_tipo_usuario = id_tipo_usuario;
            return this;
	}

	public String getUsu_mail() {
		return this.usu_mail;
	}

	public Usuario setUsu_mail(String usu_mail) {
		this.usu_mail = usu_mail;
		return this;
	}

	public String getUsu_password() {
		return this.usu_password;
	}

	public Usuario setUsu_password(String usu_password) {
		this.usu_password = usu_password;
		return this;
	}

	public String getUsu_fcreacion() {
		return this.usu_fcreacion;
	}

	public Usuario setUsu_fcreacion(String usu_fcreacion) {
		this.usu_fcreacion = usu_fcreacion;
		return this;
	}

	public Integer getUsu_activo() {
		return this.usu_activo;
	}

	public Usuario setUsu_activo(Integer usu_activo) {
		this.usu_activo = usu_activo;
		return this;
	}

	public String getUsu_hash_activa() {
		return this.usu_hash_activa;
	}

	public Usuario setUsu_hash_activa(String usu_hash_activa) {
		this.usu_hash_activa = usu_hash_activa;
		return this;
	}
}