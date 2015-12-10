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
    private String  codigo       = "";
    private String codigoNew     = "";
    private String desc_corta    = "";
    private String desc_larga    = "";
    private Integer id_rubro = 0;
    private Integer id_subrubro = 0;
    
    public Remito_detalle_view(){}
    public Remito_detalle_view(Remito_detalle_view d){
        super(d);
        this.codigo        = d.getCodigo();
        this.codigoNew     = d.getCodigoNew();
        this.desc_corta    = d.getDesc_corta();
        this.desc_larga    = d.getDesc_larga();
        this.id_rubro      = d.getId_rubro();
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
    public Integer getId_rubro(){
        return this.id_rubro;
    }
    public Integer getId_subrubro(){
        return this.id_subrubro;
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
    public Remito_detalle_view setId_rubro(Integer id_rubro){
        this.id_rubro = id_rubro;
        return this;
    }
    public Remito_detalle_view setId_subrubro(Integer id_subrubro){
        this.id_subrubro = id_subrubro;
        return this;
    }
}
