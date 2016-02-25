/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Rubro {
    Integer id = 0 ;
//    Integer tipo;
    String codigo = "";
    String descripcion = "";
    String desc_opcional = "";
    Integer aplica_certificado = 1;
    Integer aplica_compra = 1;
    Integer id_estado = 1;
    public Rubro(){
        
    }
    public Rubro(Rubro rubro){
        this.id = rubro.getId();
//        this.tipo = rubro.getTipo();
        this.codigo = rubro.getCodigo();
        this.descripcion = rubro.getDescripcion();
        this.desc_opcional = rubro.getDesc_opcional();
        this.aplica_certificado = rubro.getAplica_certificado();
        this.aplica_compra = rubro.getAplica_compra();
         this.id_estado = rubro.getId_estado();
    }
    public Rubro(Integer id,String codigo,String descripcion) {
        this.id = id;                
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aplica_compra = 1;
        this.aplica_certificado = 1;
    }
    public Integer getId(){
        return this.id;
    }
//    public Integer getTipo(){
//        return this.tipo;
//    }
//    
    public String getCodigo(){
        return this.codigo;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getDesc_opcional(){
        return this.desc_opcional;
    }
    public Integer getAplica_compra(){
        return this.aplica_compra;
    }
    public Integer getAplica_certificado() {
        return this.aplica_certificado;
    }
    
    public Rubro setId(Integer id){
        this.id = id;
        return this;
    }
//     public Rubro setTipo(Integer tipo){
//        this.tipo = tipo;
//        return this;
//    }
      public Integer getId_estado(){
        return this.id_estado;
    }
    public Rubro setCodigo(String codigo){
        this.codigo = codigo;
        return this;
    }
    public Rubro setDescripcion(String descripcion){
        this.descripcion = descripcion;
        return this;
    }
    public Rubro setDesc_opcional(String desc_opcional){
        this.desc_opcional = desc_opcional;
        return this;
    }
    public Rubro setAplica_certificado(Integer aplica_certificado){
        this.aplica_certificado = aplica_certificado;
        return this;
    }
    public Rubro setAplica_compra(Integer aplica_compra){
        this.aplica_compra = aplica_compra;
        return this;
    }
        
     public Rubro setId_estado(Integer id_estado){
        this.id_estado = id_estado;
        return this;
    }
}
