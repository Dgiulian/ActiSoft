package bd;
public class Certificado {

	public Integer id = 0;
	public Integer id_modulo = 0;
	public Integer id_objeto = 0;
	public String fecha = "";
	public String fecha_desde = "";
	public String fecha_hasta = "";
	public String nombre_proveedor = "";
	public String codigo = "";
	public String precinto = "";
	public String observaciones = "";
	public Integer id_resultado = 0;
	public Integer externo = 0;
	public String archivo = "";
	public String archivo_url = "";
	public Integer desmontaje = 0;
	public Integer limpieza = 0;
	public Integer inspeccion_visual = 0;
	public Integer ultrasonido = 0;
	public Integer calibres = 0;
	public Integer particulas_magentizables = 0;
	public Integer reemplazo_sellos = 0;
	public Integer rearmado = 0;
	public Integer grasa = 0;
	public Integer prueba_presion = 0;
	public Integer pintado = 0;
	public Integer anillo_segmento = 0;
	public Integer segmentos = 0;
	public Integer tuerca = 0;

	public Certificado() {
	}

	public Certificado(Certificado certificado) {
		this.id = certificado.getId();
		this.id_modulo = certificado.getId_modulo();
		this.id_objeto = certificado.getId_objeto();
		this.fecha = certificado.getFecha();
		this.fecha_desde = certificado.getFecha_desde();
		this.fecha_hasta = certificado.getFecha_hasta();
		this.nombre_proveedor = certificado.getNombre_proveedor();
		this.codigo = certificado.getCodigo();
		this.precinto = certificado.getPrecinto();
		this.observaciones = certificado.getObservaciones();
		this.id_resultado = certificado.getId_resultado();
		this.externo = certificado.getExterno();
		this.archivo = certificado.getArchivo();
		this.archivo_url = certificado.getArchivo_url();
		this.desmontaje = certificado.getDesmontaje();
		this.limpieza = certificado.getLimpieza();
		this.inspeccion_visual = certificado.getInspeccion_visual();
		this.ultrasonido = certificado.getUltrasonido();
		this.calibres = certificado.getCalibres();
		this.particulas_magentizables = certificado
				.getParticulas_magentizables();
		this.reemplazo_sellos = certificado.getReemplazo_sellos();
		this.rearmado = certificado.getRearmado();
		this.grasa = certificado.getGrasa();
		this.prueba_presion = certificado.getPrueba_presion();
		this.pintado = certificado.getPintado();
		this.anillo_segmento = certificado.getAnillo_segmento();
		this.segmentos = certificado.getSegmentos();
		this.tuerca = certificado.getTuerca();
	}

	public Integer getId() {
		return this.id;
	}

	public Certificado setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_modulo() {
		return this.id_modulo;
	}

	public Certificado setId_modulo(Integer id_modulo) {
		this.id_modulo = id_modulo;
		return this;
	}

	public Integer getId_objeto() {
		return this.id_objeto;
	}

	public Certificado setId_objeto(Integer id_objeto) {
		this.id_objeto = id_objeto;
		return this;
	}

	public String getFecha() {
		return this.fecha;
	}

	public Certificado setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}

	public String getFecha_desde() {
		return this.fecha_desde;
	}

	public Certificado setFecha_desde(String fecha_desde) {
		this.fecha_desde = fecha_desde;
		return this;
	}

	public String getFecha_hasta() {
		return this.fecha_hasta;
	}

	public Certificado setFecha_hasta(String fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
		return this;
	}

	public String getNombre_proveedor() {
		return this.nombre_proveedor;
	}

	public Certificado setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
		return this;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Certificado setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getPrecinto() {
		return this.precinto;
	}

	public Certificado setPrecinto(String precinto) {
		this.precinto = precinto;
		return this;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public Certificado setObservaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}

	public Integer getId_resultado() {
		return this.id_resultado;
	}

	public Certificado setId_resultado(Integer id_resultado) {
		this.id_resultado = id_resultado;
		return this;
	}

	public Integer getExterno() {
		return this.externo;
	}

	public Certificado setExterno(Integer externo) {
		this.externo = externo;
		return this;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public Certificado setArchivo(String archivo) {
		this.archivo = archivo;
		return this;
	}

	public String getArchivo_url() {
		return this.archivo_url;
	}

	public Certificado setArchivo_url(String archivo_url) {
		this.archivo_url = archivo_url;
		return this;
	}

	public Integer getDesmontaje() {
		return this.desmontaje;
	}

	public Certificado setDesmontaje(Integer desmontaje) {
		this.desmontaje = desmontaje;
		return this;
	}

	public Integer getLimpieza() {
		return this.limpieza;
	}

	public Certificado setLimpieza(Integer limpieza) {
		this.limpieza = limpieza;
		return this;
	}

	public Integer getInspeccion_visual() {
		return this.inspeccion_visual;
	}

	public Certificado setInspeccion_visual(Integer inspeccion_visual) {
		this.inspeccion_visual = inspeccion_visual;
		return this;
	}

	public Integer getUltrasonido() {
		return this.ultrasonido;
	}

	public Certificado setUltrasonido(Integer ultrasonido) {
		this.ultrasonido = ultrasonido;
		return this;
	}

	public Integer getCalibres() {
		return this.calibres;
	}

	public Certificado setCalibres(Integer calibres) {
		this.calibres = calibres;
		return this;
	}

	public Integer getParticulas_magentizables() {
		return this.particulas_magentizables;
	}

	public Certificado setParticulas_magentizables(
			Integer particulas_magentizables) {
		this.particulas_magentizables = particulas_magentizables;
		return this;
	}

	public Integer getReemplazo_sellos() {
		return this.reemplazo_sellos;
	}

	public Certificado setReemplazo_sellos(Integer reemplazo_sellos) {
		this.reemplazo_sellos = reemplazo_sellos;
		return this;
	}

	public Integer getRearmado() {
		return this.rearmado;
	}

	public Certificado setRearmado(Integer rearmado) {
		this.rearmado = rearmado;
		return this;
	}

	public Integer getGrasa() {
		return this.grasa;
	}

	public Certificado setGrasa(Integer grasa) {
		this.grasa = grasa;
		return this;
	}

	public Integer getPrueba_presion() {
		return this.prueba_presion;
	}

	public Certificado setPrueba_presion(Integer prueba_presion) {
		this.prueba_presion = prueba_presion;
		return this;
	}

	public Integer getPintado() {
		return this.pintado;
	}

	public Certificado setPintado(Integer pintado) {
		this.pintado = pintado;
		return this;
	}

	public Integer getAnillo_segmento() {
		return this.anillo_segmento;
	}

	public Certificado setAnillo_segmento(Integer anillo_segmento) {
		this.anillo_segmento = anillo_segmento;
		return this;
	}

	public Integer getSegmentos() {
		return this.segmentos;
	}

	public Certificado setSegmentos(Integer segmentos) {
		this.segmentos = segmentos;
		return this;
	}

	public Integer getTuerca() {
		return this.tuerca;
	}

	public Certificado setTuerca(Integer tuerca) {
		this.tuerca = tuerca;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Certificado))
			return false;
		return ((bd.Certificado) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}