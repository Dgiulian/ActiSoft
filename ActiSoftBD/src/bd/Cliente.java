/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Diego
 */
public class Cliente {
    
private Integer id = 0;
private String nombre = "";
private String cuit = "";
private String dni = "";
private String nombre_comercial = "";
private String direccion_fisica = "";
private String direccion_legal = "";
private Integer id_provincia =0;
private Integer id_localidad = 0;
private String codigo_postal = "";
private String telefono = "";
private String celular = "";
private String contacto = "";
private String observaciones = "";
private String fecha_alta = "";
private Integer id_estado = 1;
private Integer id_iva = 0;

public Cliente(){}
public Cliente(Cliente cliente){
    this.id = cliente.getId();
    this.nombre = cliente.getNombre();
    this.cuit = cliente.getCuit();
    this.dni = cliente.getDni();
    this.nombre_comercial = cliente.getNombre_comercial();
    this.direccion_fisica = cliente.getDireccion_fisica();
    this.direccion_legal = cliente.getDireccion_legal();
    this.id_provincia = cliente.getId_provincia();
    this.id_localidad = cliente.getId_localidad();
    this.codigo_postal = cliente.getCodigo_postal();
    this.telefono = cliente.getTelefono();
    this.celular = cliente.getCelular();
    this.contacto = cliente.getContacto();
    this.observaciones = cliente.getObservaciones();
    this.fecha_alta = cliente.getFecha_alta();
    this.id_estado = cliente.getId_estado();
    this.id_iva = 0;

}
public Integer getId(){
    return id;
}
public String getNombre(){
    return nombre;
}
public String getCuit(){
    return cuit;
}
public String getDni(){
    return dni;
}
public String getNombre_comercial(){
    return nombre_comercial;
}
public String getDireccion_fisica(){
    return direccion_fisica;
}
public String getDireccion_legal(){
    return direccion_legal;
}
public Integer getId_provincia(){
    return id_provincia;
}
public Integer getId_localidad(){
    return id_localidad;
}
public String getCodigo_postal(){
    return codigo_postal;
}
public String getTelefono(){
    return telefono;
}
public String getCelular(){
    return celular;
}
public String getContacto(){
    return contacto;
}
public String getObservaciones(){
    return observaciones;
}
public String getFecha_alta(){
    return fecha_alta;
}
public Integer getId_estado(){
    return id_estado;
}
public Integer getId_iva(){
    return this.id_iva;
}
public Cliente setId(Integer id){
    this.id = id;
    return this;
}
public Cliente setNombre(String nombre){
    this.nombre = nombre;
    return this;
}
public Cliente setCuit(String cuit){
    this.cuit = cuit;
    return this;
}
public Cliente setDni(String dni){
    this.dni = dni;
    return this;
}
public Cliente setNombre_comercial(String nombre_comercial){
    this.nombre_comercial = nombre_comercial;
    return this;
}
public Cliente setDireccion_fisica(String direccion_fisica){
    this.direccion_fisica = direccion_fisica;
    return this;
}
public Cliente setDireccion_legal(String direccion_legal){
    this.direccion_legal = direccion_legal;
    return this;
}
public Cliente setId_provincia(Integer id_provincia){
    this.id_provincia = id_provincia;
    return this;
}
public Cliente setId_localidad(Integer id_localidad){
    this.id_localidad = id_localidad;
    return this;
}
public Cliente setCodigo_postal(String codigo_postal){
    this.codigo_postal = codigo_postal;
    return this;
}
public Cliente setTelefono(String telefono){
    this.telefono = telefono;
    return this;
}
public Cliente setCelular(String celular){
    this.celular = celular;
    return this;
}
public Cliente setContacto(String contacto){
    this.contacto = contacto;
    return this;
}
public Cliente setObservaciones(String observaciones){
    this.observaciones = observaciones;
    return this;
}
public Cliente setFecha_alta(String fecha_alta){
    this.fecha_alta = fecha_alta;
    return this;
}
public Cliente setId_estado(Integer id_estado){
    this.id_estado = id_estado;
    return this;
}
public Cliente setId_iva(Integer id_iva){
    this.id_iva = id_iva;
    return this;
}
}
