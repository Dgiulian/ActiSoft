/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Subrubro {
    public Integer id  = 0;
    public Integer id_rubro = 0;
    public String codigo =  "";
    public String descripcion = "";
    public String desc_opcional = "";
    public Integer id_clase = 0;
    public Integer id_estado = 1;

    public Subrubro(){}
    public Subrubro(Subrubro subrubro){
        this.id = subrubro.getId();
        this.id_rubro = subrubro.getId_rubro();
        this.codigo = subrubro.getCodigo();
        this.descripcion = subrubro.getDescripcion();
        this.desc_opcional = subrubro.getDesc_opcional();
        this.id_clase = subrubro.getId_clase();
        this.id_estado = subrubro.getId_estado();
    }
    public Integer getId(){
        return this.id;
    }
    public Integer getId_rubro(){
        return this.id_rubro;
    }
    
    public String getCodigo(){
        return this.codigo;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getDesc_opcional(){
        return this.desc_opcional;
    }
    public Integer getId_clase() {
        return this.id_clase;
    }
    public Integer getId_estado(){
        return this.id_estado;
    }
    public Subrubro setId(Integer id){
        this.id = id;
        return this;
    }
     public Subrubro setId_rubro(Integer id_rubro){
        this.id_rubro = id_rubro;
        return this;
    }
    public Subrubro setCodigo(String codigo){
        this.codigo = codigo;
        return this;
    }
    public Subrubro setDescripcion(String descripcion){
        this.descripcion = descripcion;
        return this;
    }
    public Subrubro setDesc_opcional(String desc_opcional){
        this.desc_opcional = desc_opcional;
        return this;
    }

    public Subrubro setId_clase(Integer id_clase) {
            this.id_clase = id_clase;
            return this;
    }    
     public Subrubro setId_estado(Integer id_estado){
        this.id_estado = id_estado;
        return this;
    }
        
}
