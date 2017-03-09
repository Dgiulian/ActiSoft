package bd;
public class Site {

	public Integer id = 0;
	public Integer id_cliente = 0;
	public String nombre = "";
	public Integer id_pozo = 0;
	public Integer id_equipo = 1;
	public String area = "";
	public String pozo = "";
	public String equipo = "";
	public String fecha_creacion = "";
	public Integer id_estado = 0;
	public String telefono = "";
	public String email = "";
	public String encargado = "";
	public String horario = "";
	public String observaciones = "";
	public Float latitud = 0f;
	public Float longitud = 0f;

	public Site() {
	}

	public Site(Site site) {
		this.id = site.getId();
		this.id_cliente = site.getId_cliente();
		this.nombre = site.getNombre();
		this.id_pozo = site.getId_pozo();
		this.id_equipo = site.getId_equipo();
		this.area = site.getArea();
		this.pozo = site.getPozo();
		this.equipo = site.getEquipo();
		this.fecha_creacion = site.getFecha_creacion();
		this.id_estado = site.getId_estado();
		this.telefono = site.getTelefono();
		this.email = site.getEmail();
		this.encargado = site.getEncargado();
		this.horario = site.getHorario();
		this.observaciones = site.getObservaciones();
		this.latitud = site.getLatitud();
		this.longitud = site.getLongitud();
	}

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

	public Integer getId_pozo() {
		return this.id_pozo;
	}

	public Site setId_pozo(Integer id_pozo) {
		this.id_pozo = id_pozo;
		return this;
	}

	public Integer getId_equipo() {
		return this.id_equipo;
	}

	public Site setId_equipo(Integer id_equipo) {
		this.id_equipo = id_equipo;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Site))
			return false;
		return ((bd.Site) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}