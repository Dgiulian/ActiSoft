/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */

public class Activo_historia extends Remito {
    
    protected Integer id_activo = 0;
    protected Float cantidad = 0f;
    protected String codigo       = "";
    protected String desc_larga   = "";
    public Activo_historia(Activo_historia activo_historia){
        super(activo_historia);
        
        this.id_activo = activo_historia.getId_activo();
        this.cantidad = activo_historia.getCantidad();
        this.codigo = activo_historia.getCodigo();
        this.desc_larga = activo_historia.getDesc_larga();
    }
    public Activo_historia(){
        super();
    }
    public Integer getId_activo(){
        return this.id_activo;
    }
     public String getCodigo(){
       return this.codigo;
    }
    public String getDesc_larga(){
       return this.desc_larga;
    }
    public Activo_historia setId_activo(Integer id_activo){
        this.id_activo = id_activo;
        return this;
    }
    public Float getCantidad(){
        return this.cantidad;        
    }
    public Activo_historia setCantidad(Float cantidad){
        this.cantidad = cantidad;
        return this;
    }
      public Activo_historia setCodigo(String codigo){
       this.codigo = codigo;
       return this;
    }
    public Activo_historia setDesc_larga(String desc_larga){
       this.desc_larga = desc_larga;
       return this;
    }
}
