package bd;
public class Remito_contrato {

	public Integer id = 0;
	public Integer id_remito = 0;
	public Integer remito_id_tipo_remito = 0;
	public String remito_fecha = "";
	public Integer remito_punto_venta = 0;
	public Integer remito_numero = 0;
	public Integer id_usuario = 0;
	public Integer id_cliente = 0;
	public Integer id_site = 0;
	public Integer id_pozo = 0;
	public Integer id_equipo = 0;
	public Integer id_referencia = 0;
	public Integer remito_id_estado = 0;
	public Integer remito_facturado = 0;
	public Integer posicion = 0;
	public Float remito_detalle_cantidad = 0f;
	public String activo_codigonew = "";
	public Integer activo_id_rubro = 0;
	public Integer activo_id_subrubro = 0;
	public String activo_desc_larga = "";
	public Integer id_contrato = 0;
	public String contrato_numero = "";
	public String contrato_fecha = "";
	public String contrato_fecha_inicio = "";
	public String contrato_fecha_fin = "";
	public Integer contrato_id_divisa = 0;
	public Integer contrato_detalle_id = 0;
	public Integer contrato_detalle_posicion = 0;
	public String contrato_detalle_descripcion = "";
	public Integer contrato_detalle_id_subrubro = 0;
	public Integer contrato_detalle_id_divisa = 0;
	public Float contrato_detalle_precio = 0f;
	public Float contrato_detalle_porcentaje = 0f;
	public Integer contrato_detalle_id_unidad = 0;

	public Remito_contrato() {
	}

	public Remito_contrato(Remito_contrato remito_contrato) {
		this.id = remito_contrato.getId();
		this.id_remito = remito_contrato.getId_remito();
		this.remito_id_tipo_remito = remito_contrato.getRemito_id_tipo_remito();
		this.remito_fecha = remito_contrato.getRemito_fecha();
		this.remito_punto_venta = remito_contrato.getRemito_punto_venta();
		this.remito_numero = remito_contrato.getRemito_numero();
		this.id_usuario = remito_contrato.getId_usuario();
		this.id_cliente = remito_contrato.getId_cliente();
		this.id_site = remito_contrato.getId_site();
		this.id_pozo = remito_contrato.getId_pozo();
		this.id_equipo = remito_contrato.getId_equipo();
		this.id_referencia = remito_contrato.getId_referencia();
		this.remito_id_estado = remito_contrato.getRemito_id_estado();
		this.remito_facturado = remito_contrato.getRemito_facturado();
		this.posicion = remito_contrato.getPosicion();
		this.remito_detalle_cantidad = remito_contrato
				.getRemito_detalle_cantidad();
		this.activo_codigonew = remito_contrato.getActivo_codigonew();
		this.activo_id_rubro = remito_contrato.getActivo_id_rubro();
		this.activo_id_subrubro = remito_contrato.getActivo_id_subrubro();
		this.activo_desc_larga = remito_contrato.getActivo_desc_larga();
		this.id_contrato = remito_contrato.getId_contrato();
		this.contrato_numero = remito_contrato.getContrato_numero();
		this.contrato_fecha = remito_contrato.getContrato_fecha();
		this.contrato_fecha_inicio = remito_contrato.getContrato_fecha_inicio();
		this.contrato_fecha_fin = remito_contrato.getContrato_fecha_fin();
		this.contrato_id_divisa = remito_contrato.getContrato_id_divisa();
		this.contrato_detalle_id = remito_contrato.getContrato_detalle_id();
		this.contrato_detalle_posicion = remito_contrato
				.getContrato_detalle_posicion();
		this.contrato_detalle_descripcion = remito_contrato
				.getContrato_detalle_descripcion();
		this.contrato_detalle_id_subrubro = remito_contrato
				.getContrato_detalle_id_subrubro();
		this.contrato_detalle_id_divisa = remito_contrato
				.getContrato_detalle_id_divisa();
		this.contrato_detalle_precio = remito_contrato
				.getContrato_detalle_precio();
		this.contrato_detalle_porcentaje = remito_contrato
				.getContrato_detalle_porcentaje();
		this.contrato_detalle_id_unidad = remito_contrato
				.getContrato_detalle_id_unidad();
	}

	public Integer getId() {
		return this.id;
	}

	public Remito_contrato setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_remito() {
		return this.id_remito;
	}

	public Remito_contrato setId_remito(Integer id_remito) {
		this.id_remito = id_remito;
		return this;
	}

	public Integer getRemito_id_tipo_remito() {
		return this.remito_id_tipo_remito;
	}

	public Remito_contrato setRemito_id_tipo_remito(
			Integer remito_id_tipo_remito) {
		this.remito_id_tipo_remito = remito_id_tipo_remito;
		return this;
	}

	public String getRemito_fecha() {
		return this.remito_fecha;
	}

	public Remito_contrato setRemito_fecha(String remito_fecha) {
		this.remito_fecha = remito_fecha;
		return this;
	}

	public Integer getRemito_punto_venta() {
		return this.remito_punto_venta;
	}

	public Remito_contrato setRemito_punto_venta(Integer remito_punto_venta) {
		this.remito_punto_venta = remito_punto_venta;
		return this;
	}

	public Integer getRemito_numero() {
		return this.remito_numero;
	}

	public Remito_contrato setRemito_numero(Integer remito_numero) {
		this.remito_numero = remito_numero;
		return this;
	}

	public Integer getId_usuario() {
		return this.id_usuario;
	}

	public Remito_contrato setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
		return this;
	}

	public Integer getId_cliente() {
		return this.id_cliente;
	}

	public Remito_contrato setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
		return this;
	}

	public Integer getId_site() {
		return this.id_site;
	}

	public Remito_contrato setId_site(Integer id_site) {
		this.id_site = id_site;
		return this;
	}

	public Integer getId_pozo() {
		return this.id_pozo;
	}

	public Remito_contrato setId_pozo(Integer id_pozo) {
		this.id_pozo = id_pozo;
		return this;
	}

	public Integer getId_equipo() {
		return this.id_equipo;
	}

	public Remito_contrato setId_equipo(Integer id_equipo) {
		this.id_equipo = id_equipo;
		return this;
	}

	public Integer getId_referencia() {
		return this.id_referencia;
	}

	public Remito_contrato setId_referencia(Integer id_referencia) {
		this.id_referencia = id_referencia;
		return this;
	}

	public Integer getRemito_id_estado() {
		return this.remito_id_estado;
	}

	public Remito_contrato setRemito_id_estado(Integer remito_id_estado) {
		this.remito_id_estado = remito_id_estado;
		return this;
	}

	public Integer getRemito_facturado() {
		return this.remito_facturado;
	}

	public Remito_contrato setRemito_facturado(Integer remito_facturado) {
		this.remito_facturado = remito_facturado;
		return this;
	}

	public Integer getPosicion() {
		return this.posicion;
	}

	public Remito_contrato setPosicion(Integer posicion) {
		this.posicion = posicion;
		return this;
	}

	public Float getRemito_detalle_cantidad() {
		return this.remito_detalle_cantidad;
	}

	public Remito_contrato setRemito_detalle_cantidad(
			Float remito_detalle_cantidad) {
		this.remito_detalle_cantidad = remito_detalle_cantidad;
		return this;
	}

	public String getActivo_codigonew() {
		return this.activo_codigonew;
	}

	public Remito_contrato setActivo_codigonew(String activo_codigonew) {
		this.activo_codigonew = activo_codigonew;
		return this;
	}

	public Integer getActivo_id_rubro() {
		return this.activo_id_rubro;
	}

	public Remito_contrato setActivo_id_rubro(Integer activo_id_rubro) {
		this.activo_id_rubro = activo_id_rubro;
		return this;
	}

	public Integer getActivo_id_subrubro() {
		return this.activo_id_subrubro;
	}

	public Remito_contrato setActivo_id_subrubro(Integer activo_id_subrubro) {
		this.activo_id_subrubro = activo_id_subrubro;
		return this;
	}

	public String getActivo_desc_larga() {
		return this.activo_desc_larga;
	}

	public Remito_contrato setActivo_desc_larga(String activo_desc_larga) {
		this.activo_desc_larga = activo_desc_larga;
		return this;
	}

	public Integer getId_contrato() {
		return this.id_contrato;
	}

	public Remito_contrato setId_contrato(Integer id_contrato) {
		this.id_contrato = id_contrato;
		return this;
	}

	public String getContrato_numero() {
		return this.contrato_numero;
	}

	public Remito_contrato setContrato_numero(String contrato_numero) {
		this.contrato_numero = contrato_numero;
		return this;
	}

	public String getContrato_fecha() {
		return this.contrato_fecha;
	}

	public Remito_contrato setContrato_fecha(String contrato_fecha) {
		this.contrato_fecha = contrato_fecha;
		return this;
	}

	public String getContrato_fecha_inicio() {
		return this.contrato_fecha_inicio;
	}

	public Remito_contrato setContrato_fecha_inicio(String contrato_fecha_inicio) {
		this.contrato_fecha_inicio = contrato_fecha_inicio;
		return this;
	}

	public String getContrato_fecha_fin() {
		return this.contrato_fecha_fin;
	}

	public Remito_contrato setContrato_fecha_fin(String contrato_fecha_fin) {
		this.contrato_fecha_fin = contrato_fecha_fin;
		return this;
	}

	public Integer getContrato_id_divisa() {
		return this.contrato_id_divisa;
	}

	public Remito_contrato setContrato_id_divisa(Integer contrato_id_divisa) {
		this.contrato_id_divisa = contrato_id_divisa;
		return this;
	}

	public Integer getContrato_detalle_id() {
		return this.contrato_detalle_id;
	}

	public Remito_contrato setContrato_detalle_id(Integer contrato_detalle_id) {
		this.contrato_detalle_id = contrato_detalle_id;
		return this;
	}

	public Integer getContrato_detalle_posicion() {
		return this.contrato_detalle_posicion;
	}

	public Remito_contrato setContrato_detalle_posicion(
			Integer contrato_detalle_posicion) {
		this.contrato_detalle_posicion = contrato_detalle_posicion;
		return this;
	}

	public String getContrato_detalle_descripcion() {
		return this.contrato_detalle_descripcion;
	}

	public Remito_contrato setContrato_detalle_descripcion(
			String contrato_detalle_descripcion) {
		this.contrato_detalle_descripcion = contrato_detalle_descripcion;
		return this;
	}

	public Integer getContrato_detalle_id_subrubro() {
		return this.contrato_detalle_id_subrubro;
	}

	public Remito_contrato setContrato_detalle_id_subrubro(
			Integer contrato_detalle_id_subrubro) {
		this.contrato_detalle_id_subrubro = contrato_detalle_id_subrubro;
		return this;
	}

	public Integer getContrato_detalle_id_divisa() {
		return this.contrato_detalle_id_divisa;
	}

	public Remito_contrato setContrato_detalle_id_divisa(
			Integer contrato_detalle_id_divisa) {
		this.contrato_detalle_id_divisa = contrato_detalle_id_divisa;
		return this;
	}

	public Float getContrato_detalle_precio() {
		return this.contrato_detalle_precio;
	}

	public Remito_contrato setContrato_detalle_precio(
			Float contrato_detalle_precio) {
		this.contrato_detalle_precio = contrato_detalle_precio;
		return this;
	}

	public Float getContrato_detalle_porcentaje() {
		return this.contrato_detalle_porcentaje;
	}

	public Remito_contrato setContrato_detalle_porcentaje(
			Float contrato_detalle_porcentaje) {
		this.contrato_detalle_porcentaje = contrato_detalle_porcentaje;
		return this;
	}

	public Integer getContrato_detalle_id_unidad() {
		return this.contrato_detalle_id_unidad;
	}

	public Remito_contrato setContrato_detalle_id_unidad(
			Integer contrato_detalle_id_unidad) {
		this.contrato_detalle_id_unidad = contrato_detalle_id_unidad;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof bd.Remito_contrato))
			return false;
		return ((bd.Remito_contrato) obj).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}
}