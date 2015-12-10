package bd;
public class Site {

	public Integer id = 0;
	public Integer id_cliente = 0;
	public String nombre = "";
	public String area = "";
	public String pozo = "";
	public String equipo = "";
	public String fecha_creacion = "";
	public Integer id_estado = 1;
	public String telefono = "";
	public String email = "";
	public String encargado = "";
	public String horario = "";
	public String observaciones = "";
	public Float latitud = 0f;
	public Float longitud = 0f;

	public Integer getId() {
		return this.id;
	}

	public Site setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_cliente() {
		return this.id_cliente;
	}

	public Site setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
		return this;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Site setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getArea() {
		return this.area;
	}

	public Site setArea(String area) {
		this.area = area;
		return this;
	}

	public String getPozo() {
		return this.pozo;
	}

	public Site setPozo(String pozo) {
		this.pozo = pozo;
		return this;
	}

	public String getEquipo() {
		return this.equipo;
	}

	public Site setEquipo(String equipo) {
		this.equipo = equipo;
		return this;
	}

	public String getFecha_creacion() {
		return this.fecha_creacion;
	}

	public Site setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
		return this;
	}

	public Integer getId_estado() {
		return this.id_estado;
	}

	public Site setId_estado(Integer id_estado) {
		this.id_estado = id_estado;
		return this;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public Site setTelefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public String getEmail() {
		return this.email;
	}

	public Site setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getEncargado() {
		return this.encargado;
	}

	public Site setEncargado(String encargado) {
		this.encargado = encargado;
		return this;
	}

	public String getHorario() {
		return this.horario;
	}

	public Site setHorario(String horario) {
		this.horario = horario;
		return this;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public Site setObservaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}

	public Float getLatitud() {
		return this.latitud;
	}

	public Site setLatitud(Float latitud) {
		this.latitud = latitud;
		return this;
	}

	public Float getLongitud() {
		return this.longitud;
	}

	public Site setLongitud(Float longitud) {
		this.longitud = longitud;
		return this;
	}
}