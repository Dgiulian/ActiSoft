package bd;
public class Kit {

	public Integer id = 0;
        public String  codigo = "";
	public String  nombre = "";
	public String  fecha_creacion = "";
	public Integer id_usuario  = 0;
	public Integer id_rubro    = 0;
	public Integer id_clase    = 0;
	public Integer id_subrubro = 0;
//        public Float   stock       = 0f;
        public Integer id_estado   = 0;
        public Kit(){}
        public Kit(Kit kit){
            this.id             = kit.getId();
            this.codigo         = kit.getCodigo();
            this.nombre         = kit.getNombre();
            this.fecha_creacion = kit.getFecha_creacion();
            this.id_usuario     = kit.getId_usuario();
            this.id_rubro       = kit.getId_rubro();
            this.id_clase       = kit.getId_clase();
            this.id_subrubro    = kit.getId_subrubro();
//            this.stock          = kit.getStock();
            this.id_estado      = kit.getId_estado();
        }
	public Integer getId() {
		return this.id;
	}

	public Kit setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Kit setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	public String getNombre() {
		return this.nombre;
	}

	public Kit setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getFecha_creacion() {
		return this.fecha_creacion;
	}

	public Kit setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
		return this;
	}

	public Integer getId_usuario() {
		return this.id_usuario;
	}

	public Kit setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
		return this;
	}

	public Integer getId_rubro() {
		return this.id_rubro;
	}

	public Kit setId_rubro(Integer id_rubro) {
		this.id_rubro = id_rubro;
		return this;
	}

	public Integer getId_clase() {
		return this.id_clase;
	}

	public Kit setId_clase(Integer id_clase) {
		this.id_clase = id_clase;
		return this;
	}

	public Integer getId_subrubro() {
		return this.id_subrubro;
	}

	public Kit setId_subrubro(Integer id_subrubro) {
		this.id_subrubro = id_subrubro;
		return this;
	}
//        public Float getStock(){
//            return this.stock;
//        }
//        public Kit setStock(Float stock){
//            this.stock = stock;
//            return this;
//        }
        public Integer getId_estado(){
            return this.id_estado;
        }
        public Kit setId_estado(Integer id_estado){
            this.id_estado = id_estado;
            return this;
        }
}