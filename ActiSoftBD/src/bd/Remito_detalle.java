/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Remito_detalle {
    private Integer id = 0;
    private Integer id_remito = 0;
    private Integer id_activo = 0;
    private Float   precio_unit = 0f;
    private Integer divisa = 0;
    private Float   precio_tot = 0f;
    private Float   cantidad = 0f;
    private Integer posicion = 0;
    private Integer id_kit = 0;
    private Integer id_referencia = 0;
    public Remito_detalle(){}
    public Remito_detalle(Remito_detalle d){
        this.id = d.getId();
        this.id_remito = d.getId_remito();
        this.id_activo = d.getId_activo();
        this.id_kit = d.getId_kit();
        
        this.precio_unit = d.getPrecio_unit();
        this.divisa = d.getDivisa();
        this.precio_tot = d.getPrecio_tot();
        this.cantidad = d.getCantidad();
        this.posicion = d.getPosicion();
        this.id_referencia = d.getId_referencia();
    }
    public Integer getId(){
        return this.id;
    }
    public Integer getId_remito(){
        return this.id_remito;
    }
    public Integer getId_activo(){
        return this.id_activo;
    }
     public Integer getId_kit(){
        return this.id_kit;
    }
    public Float getPrecio_unit(){
        return this.precio_unit;
    }
     public Integer getDivisa(){
        return this.divisa;
    }
    public Float getPrecio_tot(){
        return this.precio_tot;
    }
    public Float getCantidad(){
        return this.cantidad;
    }
    public Integer getPosicion(){
        return this.posicion;        
    }
    public Integer getId_referencia(){
        return this.id_referencia;
    }
      
    public Remito_detalle setId(Integer id){
        this.id = id;
        return this;
    }
    public Remito_detalle setId_remito(Integer id_remito){
        this.id_remito = id_remito;
        return this;
    }
    public Remito_detalle setId_activo(Integer id_activo){
        this.id_activo = id_activo;
        return this;
    }   
     public Remito_detalle setId_kit(Integer id_kit){
        this.id_kit = id_kit;
        return this;
    }   
    public Remito_detalle setPrecio_unit (Float   precio_unit){
        this.precio_unit = precio_unit;
        return this;
    }
    public Remito_detalle setDivisa (Integer divisa){
        this.divisa = divisa;
        return this;
    }
    public Remito_detalle setPrecio_tot (Float   precio_tot){
        this.precio_tot = precio_tot;
        return this;
    }
    public Remito_detalle setCantidad(Float cantidad){
        this.cantidad = cantidad;
        return this;
    }
    public Remito_detalle setPosicion(Integer posicion){
        this.posicion = posicion;
        return this;
    }
      public Remito_detalle setId_referencia(Integer id_referencia){
        this.id_referencia = id_referencia;
        return this;
    }   
}
