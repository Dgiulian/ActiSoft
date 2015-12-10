/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Contrato {
    private Integer id = 0;
    private Integer id_cliente = 0;
    private String numero = "";
    private String fecha = "";
    private String fecha_inicio = "";
    private String fecha_fin = "";
    private Integer id_divisa = 0;
    private Float monto = 0f;
    private Float porcentaje = 0f;
    public Contrato(){}
    
    public Contrato(Contrato contrato){
        this.id = contrato.getId();
        this.id_cliente = contrato.getId_cliente();
        this.numero = contrato.getNumero();
        this.fecha = contrato.getFecha();
        this.fecha_inicio = contrato.getFecha_inicio();
        this.fecha_fin = contrato.getFecha_fin();
        this.id_divisa = contrato.getId_divisa();
        this.monto = contrato.getMonto();
        this.porcentaje = contrato.getPorcentaje();
    }
    public Integer getId(){
        return this.id;
    }
    public Integer getId_cliente(){
        return this.id_cliente;
    }
    public String getNumero(){
        return this.numero;
    }
    public String getFecha(){
        return this.fecha;
    }
    public String getFecha_inicio(){
        return this.fecha_inicio;
    }
    public String getFecha_fin(){
        return this.fecha_fin;
    }
    public Integer getId_divisa(){
        return this.id_divisa;
    }
    public Float getMonto(){
        return this.monto;
    }
    public Float getPorcentaje(){
        return this.porcentaje;
    }


    public Contrato setId(Integer id){
        this.id = id;
        return this;
    }
    public Contrato setId_cliente(Integer id_cliente){
        this.id_cliente = id_cliente;
        return this;
    }
    public Contrato setNumero(String numero){
        this.numero = numero;
        return this;
    }
    public Contrato setFecha(String fecha){
        this.fecha = fecha;
        return this;
    }
    public Contrato setFecha_inicio(String fecha_inicio){
        this.fecha_inicio = fecha_inicio;
        return this;
    }
    public Contrato setFecha_fin(String fecha_fin){
        this.fecha_fin = fecha_fin;
        return this;
    }
    public Contrato setId_divisa(Integer id_divisa){
        this.id_divisa = id_divisa;
        return this;
    }
    public Contrato setMonto(Float monto){
        this.monto = monto;
        return this;
    }
    public Contrato setPorcentaje(Float porcentaje){
        this.porcentaje = porcentaje;
        return this;
    }
}
