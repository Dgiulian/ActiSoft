/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Activo_contrato_view extends Activo{
    public String sr_codigo = "";
    public String sr_descripcion = "";
    public Integer c_id_contrato = 0;
    public Integer c_posicion = 0;
    public Integer c_id_divisa = 0;
    public Float c_precio = 0f;
    public Float c_porcentaje = 0f;

    public Activo_contrato_view(){}
    public Activo_contrato_view(Activo_contrato_view activo){
        super(activo);
        this.sr_codigo  = activo.getSr_codigo();
        this.sr_descripcion  = activo.getSr_descripcion();
        this.c_id_contrato  = activo.getC_id_contrato();
        this.c_posicion  = activo.getC_posicion();
        this.c_id_divisa  = activo.getC_id_divisa();
        this.c_precio  = activo.getC_precio();
        this.c_porcentaje  = activo.getC_porcentaje();
    }

    public String getSr_codigo(){
        return this.sr_codigo;
    }
    public String getSr_descripcion(){
        return this.sr_descripcion;
    }
    public Integer getC_id_contrato(){
        return this.c_id_contrato;
    }
    public Integer getC_posicion(){
        return this.c_posicion;
    }
    public Integer getC_id_divisa(){
        return this.c_id_divisa;
    }
    public Float getC_precio(){
        return this.c_precio;
    }
    public Float getC_porcentaje(){
        return this.c_porcentaje;
    }
     public Activo_contrato_view setSr_codigo(String sr_codigo){
         this.sr_codigo  = sr_codigo;
         return this;
     }
     public Activo_contrato_view setSr_descripcion(String sr_descripcion){
         this.sr_descripcion  = sr_descripcion;
         return this;
     }
     public Activo_contrato_view setC_id_contrato(Integer c_id_contrato){
         this.c_id_contrato  = c_id_contrato;
         return this;
     }
     public Activo_contrato_view setC_posicion(Integer c_posicion){
         this.c_posicion  = c_posicion;
         return this;
     }
     public Activo_contrato_view setC_id_divisa(Integer c_id_divisa){
         this.c_id_divisa  = c_id_divisa;
         return this;
     }
     public Activo_contrato_view setC_precio(Float c_precio){
         this.c_precio  = c_precio;
         return this;
     }
     public Activo_contrato_view setC_porcentaje(Float c_porcentaje){
         this.c_porcentaje  = c_porcentaje;
         return this;
     }
}
