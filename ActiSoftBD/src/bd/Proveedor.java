package bd;
public class Proveedor {

	public Integer id = 0;
	public String nombre_comercial = "";
	public String cuit = "";
    public String dni = "";
	public String razon_social = "";
	public String direccion_fisica = "";
	public String direccion_legal = "";
	public String codigo_postal = "";
	public Integer id_pais = 0;
	public Integer id_provincia = 0;
	public Integer id_localidad = 0;
	public String telefono = "";
	public String celular = "";
	public String contacto = "";
	public String observaciones = "";
	public String fecha_alta = "";
	public Integer id_estado = 1;
	public Float descuento_especial = 0f;
	public Float descuento_pronto_pago = 0f;
	public Integer id_divisa = 0;
	public Integer id_forma_pago = 0;
	public Float monto_maximo = 0f;
	public Integer id_iva = 0;
	public Integer id_tipo_proveedor = 0;
	public String banco1 = "";
	public String cuenta1 = "";
	public String banco2 = "";
	public String cuenta2 = "";
    public String conductores = "";
	public String vehiculos = "";
	public String dominios = "";
	public String dni_conductor = "";
    public String email = "";
    public String nombre_transportista = "";
    public String vencimiento_carnet = "";
    public Proveedor(){}
        public Proveedor(Proveedor proveedor){
            this.id                    = proveedor.getId();
            this.nombre_comercial      = proveedor.getNombre_comercial();
            this.cuit                  = proveedor.getCuit();
            this.dni                   = proveedor.getDni();
            this.razon_social          = proveedor.getRazon_social();
            this.direccion_fisica      = proveedor.getDireccion_fisica();
            this.direccion_legal       = proveedor.getDireccion_legal();
            this.codigo_postal         = proveedor.getCodigo_postal();
            this.id_pais               = proveedor.getId_pais();
            this.id_provincia          = proveedor.getId_provincia();
            this.id_localidad          = proveedor.getId_localidad();
            this.telefono              = proveedor.getTelefono();
            this.celular               = proveedor.getCelular();
            this.contacto              = proveedor.getContacto();
            this.observaciones         = proveedor.getObservaciones();
            this.fecha_alta            = proveedor.getFecha_alta();
            this.id_estado             = proveedor.getId_estado();
            this.descuento_especial    = proveedor.getDescuento_especial();
            this.descuento_pronto_pago = proveedor.getDescuento_pronto_pago();
            this.id_divisa             = proveedor.getId_divisa();
            this.id_forma_pago         = proveedor.getId_forma_pago();
            this.monto_maximo          = proveedor.getMonto_maximo();
            this.id_iva                = proveedor.getId_iva();
            this.id_tipo_proveedor     = proveedor.getId_tipo_proveedor();
            this.banco1                = proveedor.getBanco1();
            this.cuenta1               = proveedor.getCuenta1();
            this.banco2                = proveedor.getBanco2();
            this.cuenta2               = proveedor.getCuenta2();
            this.conductores           = proveedor.getConductores();
            this.vehiculos             = proveedor.getVehiculos();
            this.dominios              = proveedor.getDominios();
            this.dni_conductor         = proveedor.getDni_conductor();
            this.email                 = proveedor.getEmail();
            this.nombre_transportista  = proveedor.getNombre_transportista();
            this.vencimiento_carnet    = proveedor.getVencimiento_carnet();
        }
	public Integer getId() {
		return this.id;
	}

	public Proveedor setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getNombre_comercial() {
		return this.nombre_comercial;
	}

	public Proveedor setNombre_comercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
		return this;
	}

	public String getCuit() {
            return this.cuit;
	}

	public Proveedor setCuit(String cuit) {
            this.cuit = cuit;
            return this;
	}
        public String getDni() {
            return this.dni;
	}

	public Proveedor setDni(String dni) {
            this.dni = dni;
            return this;
	}
	public String getRazon_social() {
            return this.razon_social;
	}

	public Proveedor setRazon_social(String razon_social) {
            this.razon_social = razon_social;
            return this;
	}

	public String getDireccion_fisica() {
		return this.direccion_fisica;
	}

	public Proveedor setDireccion_fisica(String direccion_fisica) {
            this.direccion_fisica = direccion_fisica;
            return this;
	}

	public String getDireccion_legal() {
            return this.direccion_legal;
	}

	public Proveedor setDireccion_legal(String direccion_legal) {
            this.direccion_legal = direccion_legal;
            return this;
	}

	public String getCodigo_postal() {
            return this.codigo_postal;
	}

	public Proveedor setCodigo_postal(String codigo_postal) {
            this.codigo_postal = codigo_postal;
            return this;
	}

	public Integer getId_pais() {
            return this.id_pais;
	}

	public Proveedor setId_pais(Integer id_pais) {
            this.id_pais = id_pais;
            return this;
	}

	public Integer getId_provincia() {
            return this.id_provincia;
	}

	public Proveedor setId_provincia(Integer id_provincia) {
            this.id_provincia = id_provincia;
            return this;
	}

	public Integer getId_localidad() {
            return this.id_localidad;
	}

	public Proveedor setId_localidad(Integer id_localidad) {
            this.id_localidad = id_localidad;
            return this;
	}

	public String getTelefono() {
            return this.telefono;
	}

	public Proveedor setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
	}

	public String getCelular() {
            return this.celular;
	}

	public Proveedor setCelular(String celular) {
            this.celular = celular;
            return this;
	}

	public String getContacto() {
            return this.contacto;
	}

	public Proveedor setContacto(String contacto) {
            this.contacto = contacto;
            return this;
	}

	public String getObservaciones() {
            return this.observaciones;
	}

	public Proveedor setObservaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
	}

	public String getFecha_alta() {
            return this.fecha_alta;
	}

	public Proveedor setFecha_alta(String fecha_alta) {
            this.fecha_alta = fecha_alta;
            return this;
	}

	public Integer getId_estado() {
            return this.id_estado;
	}

	public Proveedor setId_estado(Integer id_estado) {
            this.id_estado = id_estado;
            return this;
	}

	public Float getDescuento_especial() {
            return this.descuento_especial;
	}

	public Proveedor setDescuento_especial(Float descuento_especial) {
            this.descuento_especial = descuento_especial;
            return this;
	}

	public Float getDescuento_pronto_pago() {
            return this.descuento_pronto_pago;
	}

	public Proveedor setDescuento_pronto_pago(Float descuento_pronto_pago) {
            this.descuento_pronto_pago = descuento_pronto_pago;
            return this;
	}

	public Integer getId_divisa() {
            return this.id_divisa;
	}

	public Proveedor setId_divisa(Integer id_divisa) {
            this.id_divisa = id_divisa;
            return this;
	}

	public Integer getId_forma_pago() {
            return this.id_forma_pago;
	}

	public Proveedor setId_forma_pago(Integer id_forma_pago) {
            this.id_forma_pago = id_forma_pago;
            return this;
	}

	public Float getMonto_maximo() {
            return this.monto_maximo;
	}

	public Proveedor setMonto_maximo(Float monto_maximo) {
            this.monto_maximo = monto_maximo;
            return this;
	}

	public Integer getId_iva() {
            return this.id_iva;
	}

	public Proveedor setId_iva(Integer id_iva) {
            this.id_iva = id_iva;
            return this;
	}

	public Integer getId_tipo_proveedor() {
            return this.id_tipo_proveedor;
	}

	public Proveedor setId_tipo_proveedor(Integer id_tipo_proveedor) {
            this.id_tipo_proveedor = id_tipo_proveedor;
            return this;
	}

	public String getBanco1() {
            return this.banco1;
	}

	public Proveedor setBanco1(String banco1) {
            this.banco1 = banco1;
            return this;
	}

	public String getCuenta1() {
            return this.cuenta1;
	}

	public Proveedor setCuenta1(String cuenta1) {
            this.cuenta1 = cuenta1;
            return this;
	}

	public String getBanco2() {
            return this.banco2;
	}

	public Proveedor setBanco2(String banco2) {
            this.banco2 = banco2;
            return this;
	}

	public String getCuenta2() {
            return this.cuenta2;
	}

	public Proveedor setCuenta2(String cuenta2) {
            this.cuenta2 = cuenta2;
            return this;
	}

	public String getConductores() {
		return this.conductores;
	}

	public Proveedor setConductores(String conductores) {
		this.conductores = conductores;
		return this;
	}

	public String getVehiculos() {
		return this.vehiculos;
	}

	public Proveedor setVehiculos(String vehiculos) {
		this.vehiculos = vehiculos;
		return this;
	}

	public String getDominios() {
		return this.dominios;
	}

	public Proveedor setDominios(String dominios) {
		this.dominios = dominios;
		return this;
	}

	public String getDni_conductor() {
		return this.dni_conductor;
	}

	public Proveedor setDni_conductor(String dni_conductor) {
		this.dni_conductor = dni_conductor;
		return this;
	}
        public String getEmail(){
            return this.email;
        }

        public Proveedor setEmail(String email){
            this.email = email;
            return this;
        }
        public String getNombre_transportista(){
            return this.nombre_transportista;
        }

        public Proveedor setNombre_transportista(String nombre_transportista){
            this.nombre_transportista = nombre_transportista;
            return this;
        }
         public String getVencimiento_carnet(){
            return this.vencimiento_carnet;
        }

        public Proveedor setVencimiento_carnet(String vencimiento_carnet){
            this.vencimiento_carnet = vencimiento_carnet;
            return this;
        }
}