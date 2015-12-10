package bd;
public class Correctivo {

	public Integer id = 0;
	public Integer id_activo = 0;
	public String fecha = "";
	public Integer id_actividad = 0;
        public Integer id_resultado = 0;
	public String detalle_problema = "";
	public String detalle_solucion = "";
        public Correctivo(){}
        
        public Correctivo(Correctivo correctivo){
            this.id = correctivo.getId();
            this.id_activo = correctivo.getId_activo();
            this.fecha = correctivo.getFecha();
            this.id_actividad = correctivo.getId_actividad();
            this.detalle_problema = correctivo.getDetalle_problema();
            this.detalle_solucion = correctivo.getDetalle_solucion();
            this.id_resultado = correctivo.getId_resultado();
        }
	public Integer getId() {
		return this.id;
	}

	public Correctivo setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId_activo() {
		return this.id_activo;
	}

	public Correctivo setId_activo(Integer id_activo) {
		this.id_activo = id_activo;
		return this;
	}

	public String getFecha() {
		return this.fecha;
	}

	public Correctivo setFecha(String fecha) {
		this.fecha = fecha;
		return this;
	}

	public Integer getId_actividad() {
		return this.id_actividad;
	}

	public Correctivo setId_actividad(Integer id_actividad) {
		this.id_actividad = id_actividad;
		return this;
	}

	public String getDetalle_problema() {
		return this.detalle_problema;
	}

	public Correctivo setDetalle_problema(String detalle_problema) {
		this.detalle_problema = detalle_problema;
		return this;
	}

	public String getDetalle_solucion() {
		return this.detalle_solucion;
	}

	public Correctivo setDetalle_solucion(String detalle_solucion) {
		this.detalle_solucion = detalle_solucion;
		return this;
	}
        public Integer  getId_resultado(){
            return this.id_resultado;
        }
        public Correctivo setId_resultado (Integer  id_resultado){
            this.id_resultado = id_resultado; 
        return this;
    }
}