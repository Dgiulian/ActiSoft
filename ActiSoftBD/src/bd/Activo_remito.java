/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Activo_remito {
   Integer id_activo   = 0;
    String codigo       = "";
    String desc_larga   = "";
    Integer id_remito   = 0;
    Integer punto_venta = 0;
    Integer numero      = 0;
    Integer id_site     = 0;
    Integer id_estado   = 0;
    String nombre       = "";
    String area         = "";
    String pozo         = "";
    String equipo       = "";
    Float  cantidad     = 0f;
    String fecha        = "";


    public Integer getId_activo(){
       return this.id_activo;
    }
    public String getCodigo(){
       return this.codigo;
    }
    public String getDesc_larga(){
       return this.desc_larga;
    }
    public Integer getId_remito(){
       return this.id_remito;
    }
    public Integer getPunto_venta(){
       return this.punto_venta;
    }
    public Integer getNumero(){
       return this.numero;
    }
    public Integer getId_site(){
       return this.id_site;
    }
    public Integer getId_estado(){
       return this.id_estado;
    }
    public String getNombre(){
       return this.nombre;
    }
    public String getArea(){
       return this.area;
    }
    public String getPozo(){
       return this.pozo;
    }
    public String getEquipo(){
       return this.equipo;
    }
    public Float  getCantidad(){
       return this.cantidad;
    }
    public String getFecha(){
       return this.fecha;
    }


    public Activo_remito setId_activo(Integer id_activo){
       this.id_activo = id_activo;
       return this;
    }
    public Activo_remito setCodigo(String codigo){
       this.codigo = codigo;
       return this;
    }
    public Activo_remito setDesc_larga(String desc_larga){
       this.desc_larga = desc_larga;
       return this;
    }
    public Activo_remito setId_remito(Integer id_remito){
       this.id_remito = id_remito;
       return this;
    }
    public Activo_remito setPunto_venta(Integer punto_venta){
       this.punto_venta = punto_venta;
       return this;
    }
    public Activo_remito setNumero(Integer numero){
       this.numero = numero;
       return this;
    }
    public Activo_remito setId_site(Integer id_site){
       this.id_site = id_site;
       return this;
    }
      public Activo_remito setId_estado(Integer id_estado){
       this.id_estado = id_estado;
       return this;
    }
    public Activo_remito setNombre(String nombre){
       this.nombre = nombre;
       return this;
    }
    public Activo_remito setArea(String area){
       this.area = area;
       return this;
    }
    public Activo_remito setPozo(String pozo){
       this.pozo = pozo;
       return this;
    }
    public Activo_remito setEquipo(String equipo){
       this.equipo = equipo;
       return this;
    }
    public Activo_remito setCantidad(Float  cantidad){
        this.cantidad = cantidad;
        return this;
    }
    public Activo_remito setFecha(String fecha){
       this.fecha = fecha;
       return this;
    }
}
