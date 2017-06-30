/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Remito_detalle_view extends Remito_detalle{
    private String  codigo             = "";
    private String codigoNew     = "";
    private String desc_corta          = "";
    private String desc_larga          = "";
    private String longitud            = "";
    private Float extension            = 0f;

    private String desc_opcional       = "";
    private String rubro_opcional      = "";
    private String subrubro_opcional   = "";
    private Integer id_rubro = 0;
    private Integer id_subrubro = 0;
    
    
    public Remito_detalle_view(){}
    public Remito_detalle_view(Remito_detalle_view d){
        super(d);
        this.codigo            = d.getCodigo();
        this.codigoNew         = d.getCodigoNew();
        this.desc_corta        = d.getDesc_corta();
        this.desc_larga        = d.getDesc_larga();
        this.desc_opcional     = d.getDesc_opcional();
        this.rubro_opcional    = d.getRubro_opcional();
        this.subrubro_opcional = d.getSubrubro_opcional();
        this.id_rubro          = d.getId_rubro();
        this.longitud          = d.getLongitud();
    }
    public String getCodigo(){
        return codigo;
    }
    public String getCodigoNew(){
        return codigoNew;
    }
    public String getDesc_corta(){
        return desc_corta;
    }
    public String getDesc_larga(){
        return desc_larga;
    }
    public String getDesc_opcional(){
        return desc_opcional;
    }
     public String getRubro_opcional(){
        return rubro_opcional;
    }
      public String getSubrubro_opcional(){
        return subrubro_opcional;
    }
    public Integer getId_rubro(){
        return this.id_rubro;
    }
    public Integer getId_subrubro(){
        return this.id_subrubro;
    }
    public String getLongitud(){
        return longitud;
    }
    
    public Remito_detalle_view setCodigo (String codigo){
        this.codigo = codigo; 
        return this;
    }
    public Remito_detalle_view setCodigoNew (String codigoNew){
        this.codigoNew = codigoNew; 
        return this;
    }
    public Remito_detalle_view setDesc_corta (String desc_corta){
        this.desc_corta = desc_corta; 
        return this;
    }
    public Remito_detalle_view setDesc_larga (String desc_larga){
        this.desc_larga = desc_larga; 
        return this;
    }
    public Remito_detalle_view setDesc_opcional (String desc_opcional){
        this.desc_opcional = desc_opcional; 
        return this;
    }
    public Remito_detalle_view setRubro_opcional (String rubro_opcional){
        this.rubro_opcional = rubro_opcional; 
        return this;
    }
    public Remito_detalle_view setSubrubro_opcional (String subrubro_opcional){
        this.subrubro_opcional = subrubro_opcional; 
        return this;
    }
    public Remito_detalle_view setId_rubro(Integer id_rubro){
        this.id_rubro = id_rubro;
        return this;
    }
    public Remito_detalle_view setId_subrubro(Integer id_subrubro){
        this.id_subrubro = id_subrubro;
        return this;
    }
    public Remito_detalle_view setLongitud (String longitud){
        this.longitud = longitud; 
        return this;
    }
    
    public Float getExtension() {
        return extension;
    }

    public void setExtension(Float extension) {
        this.extension = extension;
    }
    public String getDescripcion(){
     String descripcion = "";
     if (!this.getDesc_opcional().equals("") ||
        !this.getSubrubro_opcional().equals("") ||
        !this.getRubro_opcional().equals("") ) {                                
        if(!this.getDesc_opcional().equals(""))
           descripcion = this.getDesc_opcional();
        else if(!this.getSubrubro_opcional().equals(""))
            descripcion = this.getSubrubro_opcional();
        else if(!this.getRubro_opcional().equals("") )
           descripcion = this.getRubro_opcional();
        
     } else {descripcion = this.getDesc_larga();}
     return descripcion;
    }
}
