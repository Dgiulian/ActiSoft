/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Contrato_detalle {
    public Integer id = 0;
    public Integer id_contrato = 0;
    public Integer posicion = 0;
    public String descripcion = "";
    public Integer id_rubro = 0;
    public Integer id_subrubro = 0;
    public Integer id_activo = 0;
    public Integer id_divisa = 0;
    public Float precio = 0f;
    public Float porcentaje = 0f;
    public Integer id_clase = 0;
    public Integer id_unidad = 0;

    public Integer getId(){
        return this.id;
    }
    public Integer getId_contrato(){
        return this.id_contrato;
    }
    public Integer getPosicion(){
        return this.posicion;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public Integer getId_rubro(){
        return this.id_rubro;
    }
    public Integer getId_clase() {
        return this.id_clase;
    }
    public Integer getId_subrubro(){
        return this.id_subrubro;
    }
    public Integer getId_activo(){
        return this.id_activo;
    }
    public Integer getId_divisa(){
        return this.id_divisa;
    }
    public Float getPrecio(){
        return this.precio;
    }
    public Float getPorcentaje(){
        return this.porcentaje;
    }
    public Integer getId_unidad(){
        return id_unidad;
    }

    public Contrato_detalle setId(Integer id){
        this.id = id;
        return this;
    }
    public Contrato_detalle setId_contrato(Integer id_contrato){
        this.id_contrato = id_contrato;
        return this;
    }
    public Contrato_detalle setPosicion(Integer posicion){
        this.posicion = posicion;
        return this;
    }
    public Contrato_detalle setDescripcion(String descripcion){
        this.descripcion = descripcion;
        return this;
    }
    public Contrato_detalle setId_rubro(Integer id_rubro){
        this.id_rubro = id_rubro;
        return this;
    }
    public Contrato_detalle setId_clase(Integer id_clase) {
        this.id_clase = id_clase;
        return this;
     }
    public Contrato_detalle setId_subrubro(Integer id_subrubro){
        this.id_subrubro = id_subrubro;
        return this;
    }
    public Contrato_detalle setId_activo(Integer id_activo){
        this.id_activo = id_activo;
        return this;
    }
    public Contrato_detalle setId_divisa(Integer id_divisa){
        this.id_divisa = id_divisa;
        return this;
    }
    public Contrato_detalle setPrecio(Float precio){
        this.precio = precio;
        return this;
    }
    public Contrato_detalle setPorcentaje(Float porcentaje){
        this.porcentaje = porcentaje;
        return this;
    }

    public Contrato_detalle setId_unidad(Integer id_unidad){
        this.id_unidad = id_unidad;
        return this;
    }
}
