/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Remito {
    public Integer  id = 0;
    public Integer  id_tipo_remito = 0;
    public String   fecha = "";
    public Integer  punto_venta = 0;
    public Integer  id_forma_pago = 0;
    public Integer  id_usuario = 0;
    public Integer  id_cliente = 0;
    public Integer  id_site = 0;
    public Integer  id_contrato = 0;
    public Float    monto = 0f;
    public Integer  numero = 0;
    public Integer  id_referencia = 0;
    public Integer  id_estado = 0;
    public String   fecha_creacion = "";
    public Integer  facturado = 0;
    
    public String archivo = "";
    public String archivo_url = "";
    public String observaciones = "";
    
    public Remito(){}
    public Remito(Remito remito){
        this.id             = remito.getId();
        this.id_tipo_remito = remito.getId_tipo_remito();
        this.fecha          = remito.getFecha();
        this.punto_venta    = remito.getPunto_venta();
        this.id_forma_pago  = remito.getId_forma_pago();
        this.id_usuario     = remito.getId_usuario();
        this.id_cliente     = remito.getId_cliente();
        this.id_site        = remito.getId_site();
        this.id_contrato    = remito.getId_contrato();
        this.numero         = remito.getNumero();
        this.monto          = remito.getMonto();
        this.id_referencia  = remito.getId_referencia();        
        this.id_estado      = remito.getId_estado();
        this.fecha_creacion = remito.getFecha_creacion();        
        this.facturado      = remito.getFacturado();
        this.archivo = remito.getArchivo();
        this.archivo_url = remito.getArchivo_url();
        this.observaciones = remito.getObservaciones();
    }
    public Integer getId(){
        return id;
    }
    public Integer getId_tipo_remito(){
        return id_tipo_remito;
    }
    public String getFecha(){
        return fecha;
    }
    public Integer getPunto_venta(){
        return this.punto_venta;
    }
    public Integer getId_forma_pago(){
        return id_forma_pago;
    }
    public Integer getId_usuario(){
        return id_usuario;
    }
    public Float getMonto(){
        return monto;
    }
    public Integer getId_cliente(){
        return this.id_cliente;
    }
    public Integer getId_site(){
        return this.id_site;
    }
    public Integer getId_contrato(){
        return this.id_contrato;
    }
    public Integer getNumero(){
        return this.numero;
    }
    public Integer getId_referencia(){
        return this.id_referencia;
    }
    public Integer getId_estado(){
        return this.id_estado;
    }
    public String getFecha_creacion(){
        return this.fecha_creacion;
    }
    public Integer getFacturado(){
        return this.facturado;
    }
     public String getArchivo(){
        return this.archivo;
    }
    public String getArchivo_url(){
        return this.archivo_url;
    }
    public String getObservaciones(){
        return this.observaciones;
    }
    
    public Remito setId(Integer id){
        this.id = id;
        return this;
    }
    public Remito setId_tipo_remito(Integer id_tipo_remito){
        this.id_tipo_remito = id_tipo_remito;
        return this;
    }
    public Remito setFecha(String fecha){
        this.fecha = fecha;
        return this;
    }
    public Remito setPunto_venta(Integer punto_venta){
        this.punto_venta = punto_venta;
        return this;
    }
    public Remito setId_forma_pago(Integer id_forma_pago){
        this.id_forma_pago = id_forma_pago;
        return this;
    }
    public Remito setId_usuario(Integer id_usuario){
        this.id_usuario = id_usuario;
        return this;
    }
    public Remito setMonto(Float monto){
        this.monto = monto;
        return this;
    }  
    public Remito setId_cliente(Integer id_cliente){
        this.id_cliente = id_cliente;
        return this;
    }
    public Remito setId_site(Integer id_site){
        this.id_site = id_site;
        return this;
    }
    public Remito setId_contrato(Integer id_contrato){
        this.id_contrato = id_contrato;
        return this;
    }
    public Remito setNumero(Integer numero){
        this.numero = numero;
        return this;
    }
    public Remito setId_referencia(Integer id_referencia){
        this.id_referencia = id_referencia;
        return this;
    }
    public Remito setId_estado(Integer id_estado){
        this.id_estado = id_estado;
        return this;
    }
    public Remito setFecha_creacion(String fecha_creacion){
        this.fecha_creacion = fecha_creacion;
        return this;
    }
    public Remito setFacturado (Integer facturado){
        this.facturado = facturado;
        return this;
    }
     public Remito setArchivo(String archivo){
        this.archivo = archivo;
        return this;
    }
    public Remito setArchivo_url(String archivo_url){
        this.archivo_url = archivo_url;
        return this;
    }
     public Remito setObservaciones(String observaciones){
        this.observaciones = observaciones;
        return this;
    }
}
