package bd;
public class Preticket {

	public Integer id = 0;
	public Integer id_cliente = 0;
	public Integer id_site = 0;
	public Integer id_contrato = 0;
	public Integer punto_venta = 0;
	public Integer numero = 0;
	public String fecha = "";
	public Integer observaciones = 0;
	public Float total = 0f;
	public Integer id_divisa = 0;
        public String fecha_creacion = "";
        public Integer id_usuario = 0;
        
        public Preticket(){
            
        }
        public Preticket(Preticket preticket) {
            this.id = preticket.getId();
            this.id_cliente = preticket.getId_cliente();
            this.id_site = preticket.getId_site();
            this.id_contrato = preticket.getId_contrato();
            this.punto_venta = preticket.getPunto_venta();
            this.numero = preticket.getNumero();
            this.fecha = preticket.getFecha();
            this.observaciones = preticket.getObservaciones();
            this.total = preticket.getTotal();
            this.id_divisa = preticket.getId_divisa();
            this.fecha_creacion  = preticket.getFecha_creacion();
        }
	public Integer getId() {
		return this.id;
	}

	public Preticket setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_cliente() {
		return this.id_cliente;
	}

	public Preticket setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
		return this;
	}

	public Integer getId_site() {
		return this.id_site;
	}

	public Preticket setId_site(Integer id_site) {
		this.id_site = id_site;
		return this;
	}

	public Integer getId_contrato() {
		return this.id_contrato;
	}

	public Preticket setId_contrato(Integer id_contrato) {
		this.id_contrato = id_contrato;
		return this;
	}

	public Integer getPunto_venta() {
		return this.punto_venta;
	}
        public String getFecha_creacion(){
            return this.fecha_creacion;
        }
        public Integer getId_usuario(){
            return this.id_usuario;
        }
	public Preticket setPunto_venta(Integer punto_venta) {
            this.punto_venta = punto_venta;
            return this;
	}

	public Integer getNumero() {
            return this.numero;
	}

	public Preticket setNumero(Integer numero) {
            this.numero = numero;
            return this;
	}

	public String getFecha() {
            return this.fecha;
	}

	public Preticket setFecha(String fecha) {
            this.fecha = fecha;
            return this;
	}

	public Integer getObservaciones() {
            return this.observaciones;
	}

	public Preticket setObservaciones(Integer observaciones) {
            this.observaciones = observaciones;
            return this;
	}

	public Float getTotal() {
            return this.total;
	}

	public Preticket setTotal(Float total) {
            this.total = total;
            return this;
	}

	public Integer getId_divisa() {
            return this.id_divisa;
	}

	public Preticket setId_divisa(Integer id_divisa) {
            this.id_divisa = id_divisa;
            return this;
	}
        public Preticket setFecha_creacion(String fecha_creacion){
            this.fecha_creacion = fecha_creacion;
            return this;
        }
        public Preticket setId_usuario(Integer id_usuario){
            this.id_usuario = id_usuario;
            return this;
        }
}