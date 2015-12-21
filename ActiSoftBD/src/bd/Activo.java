/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Activo {
    public Integer id           = 0;
    public String  codigo       = "";
    public String codigoNew     = "";
    public Integer numero       = 0;
    public String familia       = "";
    public Integer id_rubro     = 0;
    public Integer id_subrubro  = 0;
    public String num_serie     = "";
    public String num_rfid      = "";
    public String desc_corta    = "";
    public String desc_larga    = "";
    public Integer stock_minimo = 0;
    public String anillo        = "";
    public Float peso           = 0f;
    public Float alto           = 0f;
    public Float ancho          = 0f;
    public Float profundidad    = 0f;
    public String observaciones = "";
    public String marca         = "";
    public Integer id_estado    = 1;
    public String codigo_aduana = "";
    public String codigo_fabrica = "";
    public String medida        = "";
    public String conexion      = "";
    public String longitud      = "";
    public String sello = "";
    public String esparrago = "";
    public String tuerca = "";
    public Float precio = 0f;
    public Integer id_divisa = 0;
    public Float   stock = 0f;
    public Integer aplica_stock = 1;
    public Integer aplica_compra = 1;
    public Integer bloqueado = 0;
    
    public String archivo_1 = "";
    public String archivo_2 = "";
    public String archivo_3 = "";
    
    public String archivo_1_url = "";
    public String archivo_2_url = "";
    public String archivo_3_url = "";
    

public Activo(){}
    public Activo(Activo activo){
        this.id             = activo.getId();
        this.codigo         = activo.getCodigo();
        this.codigoNew      = activo.getCodigoNew();
        this.numero         = activo.getNumero();
        this.familia        = activo.getFamilia();
        this.id_rubro       = activo.getId_rubro();
        this.id_subrubro    = activo.getId_subrubro();
        this.num_serie      = activo.getNum_serie();
        this.num_rfid       = activo.getNum_rfid();
        this.desc_corta     = activo.getDesc_corta();
        this.desc_larga     = activo.getDesc_larga();
        this.stock_minimo   = activo.getStock_minimo();
        this.anillo         = activo.getAnillo();
        this.peso           = activo.getPeso();
        this.alto           = activo.getAlto();
        this.ancho          = activo.getAncho();
        this.profundidad    = activo.getProfundidad();
        this.observaciones  = activo.getObservaciones();
        this.marca          = activo.getMarca();
        this.id_estado      = activo.getId_estado();
        this.codigo_aduana  = activo.getCodigo_aduana();
        this.codigo_fabrica = activo.getCodigo_fabrica();
        this.medida         = activo.getMedida();
        this.conexion       = activo.getConexion();
        this.longitud       = activo.getLongitud();
        this.sello          = activo.getSello();
        this.esparrago      = activo.getEsparrago();
        this.tuerca         = activo.getTuerca();
        this.precio         = activo.getPrecio();
        this.id_divisa      = activo.getId_divisa();
        this.stock          = activo.getStock();
        this.aplica_stock   = activo.getAplica_stock();
        this.aplica_compra   = activo.getAplica_compra();
        this.bloqueado      = activo.getBloqueado();
        this.archivo_1      = activo.getArchivo_1();
        this.archivo_2      = activo.getArchivo_2();
        this.archivo_3      = activo.getArchivo_3();
    }
    public Integer getId(){
        return id;
    }
    public String getCodigo(){
        return codigo;
    }
    public String getCodigoNew(){
        return codigoNew;
    }
    public Integer getNumero(){
        return numero;
    }
    public Integer getId_rubro(){
        return this.id_rubro;
    }
    public Integer getId_subrubro(){
        return this.id_subrubro;
    }
    public String getFamilia(){
        return familia;
    }
    public String getNum_serie(){
        return num_serie;
    }
    public String getNum_rfid(){
        return num_rfid;
    }
    public String getDesc_corta(){
        return desc_corta;
    }
    public String getDesc_larga(){
        return desc_larga;
    }
    public Integer getStock_minimo(){
        return stock_minimo;
    }
    public String getAnillo(){
        return anillo;
    }
    public Float getPeso(){
        return peso;
    }
    public Float getAlto(){
        return alto;
    }
    public Float getAncho(){
        return ancho;
    }
    public Float getProfundidad(){
        return profundidad;
    }
    public String getObservaciones(){
        return observaciones;
    }
    public String getMedida(){
        return this.medida;
    }
    public String getConexion(){
        return this.conexion;
    }
    public String getLongitud(){
        return this.longitud;
    }
    public String getArchivo_1(){
        return this.archivo_1;
    }
    public String getArchivo_2(){
        return this.archivo_2;
    }
    public String getArchivo_3(){
        return this.archivo_3;
    }
    public String getArchivo_1_url(){
        return this.archivo_1_url;
    }
    public String getArchivo_2_url(){
        return this.archivo_2_url;
    }
    public String getArchivo_3_url(){
        return this.archivo_3_url;
    }
  
    public String getMarca(){
        return marca;
    }
    public Integer getId_estado(){
        return id_estado;
    }
    public String getCodigo_aduana(){
        return codigo_aduana;
    }
    public String getCodigo_fabrica(){
        return codigo_fabrica;
    }
    public String getSello(){
        return this.sello;
    }
    public String getEsparrago(){
        return this.esparrago;
    }
    public String  getTuerca(){
        return this.tuerca;
    }

    public Float getPrecio() {
        return this.precio;
    }

    public Integer getId_divisa() {
        return this.id_divisa;
    }
    public Float getStock(){
        return this.stock;
    }
    public Integer getAplica_stock(){
        return this.aplica_stock;
    }
    public Integer getAplica_compra(){
        return this.aplica_compra;
    }
    public Integer getBloqueado(){
        return this.bloqueado;
    }


    public Activo setId (Integer id){
        this.id = id; 
        return this;
    }
    public Activo setCodigo (String codigo){
        this.codigo = codigo; 
        return this;
    }
    public Activo setCodigoNew (String codigoNew){
        this.codigoNew = codigoNew; 
        return this;
    }
    public Activo setNumero(Integer numero){
        this.numero = numero;
        return this;
    }
    public Activo setId_rubro(Integer id_rubro){
        this.id_rubro = id_rubro; 
        return this;
    }
    public Activo setId_subrubro(Integer id_subrubro){
         this.id_subrubro = id_subrubro; 
         return this;
    }
    public Activo setFamilia (String familia){
        this.familia = familia; 
        return this;
    }
    public Activo setNum_serie (String num_serie){
        this.num_serie = num_serie; 
        return this;
    }
    public Activo setNum_rfid (String num_rfid){
        this.num_rfid = num_rfid; 
        return this;
    }
    public Activo setDesc_corta (String desc_corta){
        this.desc_corta = desc_corta; 
        return this;
    }
    public Activo setDesc_larga (String desc_larga){
        this.desc_larga = desc_larga; 
        return this;
    }
    public Activo setStock_minimo (Integer stock_minimo){
        this.stock_minimo = stock_minimo; 
        return this;
    }
    public Activo setAnillo (String anillo){
        this.anillo = anillo; 
        return this;
    }
    public Activo setPeso (Float peso){
        this.peso = peso; 
        return this;
    }
    public Activo setAlto (Float alto){
        this.alto = alto; 
        return this;
    }
    public Activo setAncho (Float ancho){
        this.ancho = ancho; 
        return this;
    }
    public Activo setProfundidad (Float profundidad){
        this.profundidad = profundidad; 
        return this;
    }
    public Activo setObservaciones (String observaciones){
        this.observaciones = observaciones; 
        return this;
    }
    public Activo setMarca (String marca){
        this.marca = marca; 
        return this;
    }
    public Activo setId_estado (Integer id_estado){
        this.id_estado = id_estado; 
        return this;
    }
    public Activo setCodigo_aduana (String codigo_aduana){
        this.codigo_aduana = codigo_aduana; 
        return this;
    }
    public Activo setCodigo_fabrica (String codigo_fabrica){
        this.codigo_fabrica = codigo_fabrica; 
        return this;
    }
    public Activo setMedida(String medida){
        this.medida = medida;
        return this;
    }
    public Activo setConexion(String conexion){
        this.conexion = conexion;
        return this;
    }
    public Activo setLongitud(String longitud){
        this.longitud = longitud;
        return this;
    }
    public Activo setSello(String sello){
        this.sello =  sello;
        return this;
    }
    public Activo setEsparrago(String esparrago){
        this.esparrago =  esparrago;
        return this;
    }
    public Activo setTuerca(String tuerca){
        this.tuerca =  tuerca;
        return this;
    }
    //public Activo setPosicion(String posicion){
    //    this.posicion =  posicion;
    //    return this;
    //}
    //public Activo setContrato(String contrato){
    //    this.contrato =  contrato;
    //    return this;
    //}
    public Activo setPrecio(Float precio){
        this.precio  = precio;
        return this;
    }
    public Activo setId_divisa(Integer id_divisa){
        this.id_divisa = id_divisa;
        return this;    
    }
    public Activo setStock(Float stock){
        this.stock = stock;
        return this;

    }
    public Activo setAplica_stock(Integer aplica_stock){
        this.aplica_stock = aplica_stock;
        return this;
    }
     public Activo setAplica_compra(Integer aplica_compra){
        this.aplica_compra = aplica_compra;
        return this;
    }
    public Activo setBloqueado (Integer bloqueado){
        this.bloqueado = bloqueado;
        return this;
    }
    public Activo setArchivo_1(String archivo_1) {
        this.archivo_1 = archivo_1;
        return this;
    }
    public Activo setArchivo_2(String archivo_2) {
        this.archivo_2 = archivo_2;
        return this;
    }
    public Activo setArchivo_3(String archivo_3) {
        this.archivo_3 = archivo_3;
        return this;
    }
    public Activo setArchivo_1_url(String archivo_1_url) {
        this.archivo_1_url = archivo_1_url;
        return this;
    }
    public Activo setArchivo_2_url(String archivo_2_url) {
        this.archivo_2_url = archivo_2_url;
        return this;
    }
    public Activo setArchivo_3_url(String archivo_3_url) {
        this.archivo_3_url = archivo_3_url;
        return this;
    }
}
