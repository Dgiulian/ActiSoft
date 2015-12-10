/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actisoft;

import bd.Activo;
import bd.Clase;
import bd.Rubro;
import bd.Subrubro;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TClase;
import transaccion.TRubro;
import transaccion.TSubrubro;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class CargaActivos {
    public static void main(String[] args){
        cargaActivos3();
        actualizarSubrubros();
        //testCantSubrubros();
        //testCodigoNew();
    } 
private static void testCantSubrubros(){
    System.out.println(new TSubrubro().countByRubroId(2));
}
private static void testCodigoNew(){
    Activo activo = new Activo();
    activo.setId_rubro(1);
    activo = new TActivo().getByCodigoNew("XACC0002");
    System.out.println(activo!=null);
    String codigoDisponible = new TActivo().getCodigoDisponible(activo);
    System.out.println(codigoDisponible);
}
private static void cargaActivos3(){
        String line = "";
        final String DELIMITER = ";";
        String id;
        String salio;
        String codigo;
        String descripcion_compuesta;
        String observacion;
        String campo;
        String codigo_new;
        String codigo_fabrica;
        String anillo;
        String sello;
        String esparrago;
        String tuerca;
        String posicion;
        String cod_rubro;
        String str_rubro;
        String str_subrubro;
        String medida;
        String conexion;
        String longitud;
        String descripcion;
        String contrato = "";
        String str_clase = "";
        Integer linea = 1;
        try (BufferedReader bf = new BufferedReader(new FileReader("e:\\ActiSoft\\Inventario 22-07-2015.csv"))) {
           bf.readLine();
           
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                linea++;
                Integer col = 0;
                id = "";
                salio = "";
                codigo = "";
                descripcion_compuesta = "";
                observacion = "";
                campo = "";
                codigo_new = "";
                codigo_fabrica = "";
                anillo = "";
                sello = "";
                esparrago = "";
                tuerca = "";
                posicion = "";
                cod_rubro = "";
                str_rubro = "";
                str_subrubro = "";
                medida = "";
                conexion = "";
                longitud = "";
                descripcion = "";
                contrato = "";
                str_clase = "";
                try{
                    id = tokens[col++].trim();
                    salio = tokens[col++].trim();
                    codigo = tokens[col++].trim();
                    descripcion_compuesta = tokens[col++].trim();
                    codigo_new = tokens[col++].trim();
                    codigo_fabrica = tokens[col++].trim();
                    cod_rubro = tokens[col++].trim();
                    str_rubro = tokens[col++].trim();
                    str_clase = tokens[col++].trim();
                    str_subrubro = tokens[col++].trim();
                    medida = tokens[col++].trim();
                    conexion = tokens[col++].trim();
                    longitud = tokens[col++].trim();
                    contrato = tokens[col++].trim();

                    anillo = tokens[col++].trim();
                    sello = tokens[col++].trim();
                    esparrago = tokens[col++].trim();
                    tuerca = tokens[col++].trim();                    
                    contrato = tokens[col++].trim();
                    //Tuerca 
                    col++;  
                    col++;
                    //Posicion
                    posicion  = tokens[col++].trim();
                    //Ubicacion
                    col++;
                    //Observacion
                    observacion = tokens[col++].trim();
                
                } catch(Exception ex){
                    System.out.print("Exception");
                }
                
                System.out.println("Codigo: " + codigo);
                System.out.println("Descripcion_compuesta: " + descripcion_compuesta);
                System.out.println("Codigo_new: " + codigo_new);
                System.out.println("Codigo_fabrica: " + codigo_fabrica);


                System.out.println("Cod_rubro: " + cod_rubro);
                System.out.println("Str_rubro: " + str_rubro);
                System.out.println("Clase: " + str_clase);
                System.out.println("Str_subrubro: " + str_subrubro);
                System.out.println("Medida: " + medida);
                System.out.println("Conexion: " + conexion);
                System.out.println("Longitud: " + longitud);

                System.out.println("Anillo: " + anillo);
                System.out.println("Sello: " + sello);
                
                System.out.println("Esparrago: " + esparrago);
                System.out.println("Tuerca: " + tuerca);
                
               // if(codigo!="") continue;
                
                Integer idRubro = 0;
                Integer idClase = 0;
                Integer idSubrubro = 0;
                
                Rubro rubro = new TRubro().getByDescripcion(str_rubro);       
                
                if (rubro==null){ // Si el rubro no existe, lo creo.
                    rubro = new Rubro();
                    rubro.setCodigo(cod_rubro);
                    rubro.setDescripcion(str_rubro);
                    idRubro = new TRubro().alta(rubro);
                    rubro.setId(idRubro);
                } else  idRubro = rubro.getId();
               
                Clase clase = new TClase().getByDescripcion(idRubro,str_clase);
                if (clase==null){// Si el subrubro no existe, lo creo
                    clase = new Clase();
                    clase.setCodigo("");
                    clase.setId_rubro(idRubro);
                    clase.setDescripcion(str_clase);
                    idClase = new TClase().alta(clase);
                    clase.setId(idClase);
                } else idClase = clase.getId();
                
                Subrubro subrubro = new TSubrubro().getByDescripcion(idRubro,str_subrubro);
                if (subrubro==null){// Si el subrubro no existe, lo creo
                    subrubro = new Subrubro();
                    subrubro.setCodigo("");
                    subrubro.setId_rubro(idRubro);
                    subrubro.setId_clase(clase.getId());
                    subrubro.setDescripcion(str_subrubro);
                    idSubrubro = new TSubrubro().alta(subrubro);
                    subrubro.setId(idSubrubro);
                } else idSubrubro = subrubro.getId();
                // Busco que coincida el rubro y el subrubro. Si no esta cargado se cancela la carga 
                if (rubro.getId() != subrubro.getId_rubro()) {
                    System.out.println(String.format("Linea: %d Surubro: %s cargado como rubro:%d y rubro:%d ", linea, str_subrubro,rubro.getId(), subrubro.getId_rubro()));                    
                    subrubro = new Subrubro(subrubro); 
                    subrubro.setId_rubro(rubro.getId());
                    subrubro.setId(0);// Porque es uno nuevo
                    idSubrubro = new TSubrubro().alta(subrubro);
                    subrubro.setId(idSubrubro);
                    if ( idSubrubro==0) {System.out.println("Subrubro = 0");break; }
                }
//                if (!subrubro.getCodigo().equalsIgnoreCase(cod_subrubro)) {
//                    System.out.println("Linea: " + linea  + " - " + subrubro.getCodigo() + " no coincide con " + cod_subrubro);
//                    break;
//                }
                Activo activo = new Activo();
                if (salio.equalsIgnoreCase("x")){
                    activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_NO_DISPONIBLE);
                }
                activo.setStock(1f);
                activo.setCodigo(codigo);                
                activo.setCodigoNew(codigo_new);
                activo.setCodigo_fabrica(codigo_fabrica);
                activo.setObservaciones(observacion);
                activo.setAnillo(anillo); 
                activo.setSello(sello);
                activo.setEsparrago(esparrago);
                activo.setTuerca(tuerca);
//                activo.setPosicion(posicion);                      
                activo.setId_rubro(rubro.getId());
                activo.setId_subrubro(subrubro.getId());
                
                activo.setMedida(medida);
                activo.setConexion(conexion);
                activo.setLongitud(longitud);

                String desc_larga = rubro.getDescripcion() + " " + clase.getDescripcion()+ " " + activo.getMedida();
                if(!conexion.equals("")) desc_larga += " - " + activo.getConexion();
                if(!longitud.equals("")) desc_larga += " - "+ activo.getLongitud();
                
                activo.setDesc_larga(desc_larga);
              //  activo.setDesc_corta(desc_larga);
//                activo.setContrato(contrato);
               
                new TActivo().alta(activo);
                }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    } 
private static void cargaActivos2(){
        String line = "";
        final String DELIMITER = ";";
        String id;
        String salio;
        String codigo;
        String descripcion_compuesta;
        String observacion;
        String campo;
        String codigo_new;
        String codigo_fabrica;
        String anillo;
        String sello;
        String esparrago;
        String tuerca;
        String posicion;
        String cod_rubro;
        String str_rubro;
        String str_subrubro;
        String medida;
        String conexion;
        String longitud;
        String descripcion;
        String contrato = "";
        Integer linea = 1;
        try (BufferedReader bf = new BufferedReader(new FileReader("e:\\ActiSoft\\Inventario-Migrar 19072015.csv"))) {
           bf.readLine();
           
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                linea++;
                id     = tokens[0].trim();
                salio  = tokens[1].trim();
                codigo = tokens[2].trim();
                descripcion_compuesta = tokens[3].trim();
                campo       = tokens[4].trim();
                observacion = tokens[5].trim();
                codigo_new  = tokens[6].trim();
                codigo_fabrica = tokens[7].trim();
                anillo      = tokens[8].trim();
                sello       = tokens[9].trim();
                esparrago   = tokens[10].trim();
                tuerca      = tokens[11].trim();
                posicion    = tokens[12];
                cod_rubro   =  tokens[13].trim();
                str_rubro   = tokens[14].trim();
                str_subrubro = tokens[15].trim();
                medida      = tokens[16].trim();
                conexion    = tokens[17].trim();
                longitud    = tokens[18].trim();
                descripcion = tokens[19].trim();
                
                if (tokens.length==21){
                    contrato = tokens[20].trim();
                }

                Integer idRubro = 0;
                Integer idSubrubro = 0;
                Rubro rubro = new TRubro().getByDescripcion(str_rubro);       
                
                if (rubro==null){ // Si el rubro no existe, lo creo.
                    rubro = new Rubro();
                    rubro.setCodigo(cod_rubro);
                    rubro.setDescripcion(str_rubro);
                    idRubro = new TRubro().alta(rubro);
                    rubro.setId(idRubro);
                }else  idRubro = rubro.getId();
                 Subrubro subrubro = new TSubrubro().getByDescripcion(idRubro,str_subrubro);
                 if (subrubro==null){// Si el subrubro no existe, lo creo
                    subrubro = new Subrubro();
                    subrubro.setCodigo("");
                    subrubro.setId_rubro(idRubro);
                    subrubro.setDescripcion(str_subrubro);
                    idSubrubro = new TSubrubro().alta(subrubro);
                    subrubro.setId(idSubrubro);
                }else idSubrubro = subrubro.getId();
                // Busco que coincida el rubro y el subrubro. Si no esta cargado se cancela la carga 
                if (rubro.getId() != subrubro.getId_rubro()) {
                    System.out.println(String.format("Linea: %d Surubro: %s cargado como rubro:%d y rubro:%d ", linea, str_subrubro,rubro.getId(), subrubro.getId_rubro()));                    
                    subrubro = new Subrubro(subrubro); 
                    subrubro.setId_rubro(rubro.getId());
                    subrubro.setId(0);// Porque es uno nuevo
                    idSubrubro = new TSubrubro().alta(subrubro);
                    subrubro.setId(idSubrubro);
                    if ( idSubrubro==0) break;
                }
//                if (!subrubro.getCodigo().equalsIgnoreCase(cod_subrubro)) {
//                    System.out.println("Linea: " + linea  + " - " + subrubro.getCodigo() + " no coincide con " + cod_subrubro);
//                    break;
//                }
                Activo activo = new Activo();
                if (salio.equalsIgnoreCase("x")){
                    activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_NO_DISPONIBLE);
                }
                activo.setCodigo(codigo);                
                activo.setCodigoNew(codigo_new);
                activo.setCodigo_fabrica(codigo_fabrica);
                activo.setObservaciones(observacion);
                activo.setAnillo(anillo); 
                activo.setSello(sello);
                activo.setEsparrago(esparrago);
                activo.setTuerca(tuerca);
//                activo.setPosicion(posicion);                      
                activo.setId_rubro(rubro.getId());
                activo.setId_subrubro(subrubro.getId());
                activo.setMedida(medida);
                activo.setConexion(conexion);
                activo.setLongitud(longitud);
                activo.setDesc_corta(descripcion);
                String desc_larga = rubro.getDescripcion() + " " + subrubro.getDescripcion()+ " " + activo.getMedida() + " " + activo.getConexion() + " "+ activo.getLongitud();
                activo.setDesc_larga(desc_larga);
//                activo.setContrato(contrato);
               
                new TActivo().alta(activo);
                }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex){}
    }     
    private static void actualizarSubrubros(){
        List<Rubro> lstRubro = new TRubro().getList();
        Integer i = 0;
        String cod_subrubro;
        TSubrubro ts = new TSubrubro();
        for(Rubro r:lstRubro){
            i = 1;
            String cod_rubro = r.getCodigo();
            List<Subrubro> lstSubrubros = ts.getByRubroId(r.getId());
            for(Subrubro s:lstSubrubros){
                cod_subrubro = cod_rubro + i++;
                s.setCodigo(cod_subrubro);
                ts.actualizar(s);
            }
            
        }
        
    }
    private static void actualizaActivos(){
        String line = "";
        final String DELIMITER = ";";
        String codigo;
        String descripcion_compuesta;
        String codigo_new;
        String codigo_fabrica;
        String anillo;
        String sello;
        String esparrago;
        String tuerca;
        String posicion;
        String cod_subrubro;
        String str_rubro;
        String str_subrubro;
        String medida;
        String conexion;
        String longitud;
        String descripcion;
        String contrato = "";
        try (BufferedReader bf = new BufferedReader(new FileReader("e:\\ActiSoft\\BM Mestre.csv"))) {
           bf.readLine();
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                
                codigo = tokens[0];
                descripcion_compuesta = tokens[1];
                codigo_new = tokens[2];
                codigo_fabrica = tokens[3];
                anillo = tokens[4];
                sello = tokens[5]; // No estaba
                esparrago = tokens[6]; // No estaba
                tuerca = tokens[7]; // No estaba
                posicion = tokens[8]; // No estaba
                cod_subrubro = tokens[9]; 
                str_rubro    = tokens[10];
                str_subrubro = tokens[11];
                medida = tokens[12];
                conexion = tokens[13];
                longitud = tokens[14];
                descripcion = tokens[15];
                
                if (tokens.length==17)
                    contrato = tokens[16];
                
                Activo activo = new TActivo().getByCodigo(codigo_new);
                if (activo==null){
                    System.out.println("No se encuentra: " + codigo_new);
                    break;
                }
                String desc_corta = activo.getDesc_corta();
                String desc_larga = activo.getDesc_larga();
                
                if(desc_corta.charAt(0) == '"') desc_corta = desc_corta.substring(1);
                if(desc_corta.charAt(desc_corta.length()- 1) == '"') desc_corta = desc_corta.substring(0, desc_corta.length() - 1);
                
                medida = activo.getMedida();
                if (!medida.equals("")){
                    if(medida.charAt(0) == '"') medida = medida.substring(1);
                    if(medida.charAt(medida.length()- 1) == '"') medida = medida.substring(0, medida.length() - 1);
                }
                sello = activo.getSello();
                if (!sello.equals("")){
                    if(sello.charAt(0) == '"') sello = sello.substring(1);
                    if(sello.charAt(sello.length()- 1) == '"') sello = sello.substring(0, sello.length() - 1);
                }
                
                Rubro rubro = new TRubro().getById(activo.getId_rubro());
                Subrubro subrubro = new TSubrubro().getById(activo.getId_subrubro());
                desc_larga = rubro.getDescripcion() + " " + subrubro.getDescripcion() + activo.getMedida() + activo.getConexion() + activo.getLongitud();
                System.out.println(activo.getDesc_corta() + " - " + desc_corta);
                System.out.println(activo.getDesc_larga() + " - " + desc_larga);
                activo.setDesc_corta(desc_corta);
                activo.setDesc_larga(desc_larga);
                activo.setMedida(medida);
                activo.setSello(sello);
                new TActivo().actualizar(activo);
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex){}
    }
    private static void cargaActivos(){
        String line = "";
        final String DELIMITER = ";";
        String codigo;
        String descripcion_compuesta;
        String codigo_new;
        String codigo_fabrica;
        String anillo;
        String sello;
        String esparrago;
        String tuerca;
        String posicion;
        String cod_rubro;
        String str_rubro;
        String str_subrubro;
        String medida;
        String conexion;
        String longitud;
        String descripcion;
        String contrato = "";
        Integer linea = 1;
        try (BufferedReader bf = new BufferedReader(new FileReader("e:\\ActiSoft\\BM Mestre.csv"))) {
           bf.readLine();
           
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                linea++;
                codigo = tokens[0];
                descripcion_compuesta = tokens[1];
                codigo_new = tokens[2];
                codigo_fabrica = tokens[3].trim();
                anillo = tokens[4];
                sello = tokens[5]; 
                esparrago = tokens[6];
                tuerca = tokens[7].trim();
                posicion = tokens[8].trim();
                cod_rubro = tokens[9].trim(); 
                str_rubro    = tokens[10].trim();
                str_subrubro = tokens[11].trim();
                medida = tokens[12].trim();
                conexion = tokens[13].trim();
                longitud = tokens[14].trim();
                descripcion = tokens[15].trim();
                
                if (tokens.length==17)
                    contrato = tokens[16];
                Integer idRubro = 0;
                Integer idSubrubro = 0;
                Rubro rubro = new TRubro().getByDescripcion(str_rubro);       
                
                if (rubro==null){ // Si el rubro no existe, lo creo.
                    rubro = new Rubro();
                    rubro.setCodigo(cod_rubro);
                    rubro.setDescripcion(str_rubro);
                    idRubro = new TRubro().alta(rubro);
                    rubro.setId(idRubro);
                }else  idRubro = rubro.getId();
                 Subrubro subrubro = new TSubrubro().getByDescripcion(idRubro,str_subrubro);
                 if (subrubro==null){// Si el subrubro no existe, lo creo
                    subrubro = new Subrubro();
                    subrubro.setCodigo("");
                    subrubro.setId_rubro(idRubro);
                    subrubro.setDescripcion(str_subrubro);
                    idSubrubro = new TSubrubro().alta(subrubro);
                    subrubro.setId(idSubrubro);
                }else idSubrubro = subrubro.getId();
                // Busco que coincida el rubro y el subrubro. Si no esta cargado se cancela la carga 
                if (rubro.getId() != subrubro.getId_rubro()) {
                    System.out.println(String.format("Linea: %d Surubro: %s cargado como rubro:%d y rubro:%d ", linea, str_subrubro,rubro.getId(), subrubro.getId_rubro()));                    
                    subrubro = new Subrubro(subrubro); 
                    subrubro.setId_rubro(rubro.getId());
                    subrubro.setId(0);// Porque es uno nuevo
                    Integer id = new TSubrubro().alta(subrubro);
                    subrubro.setId(id);
                    if ( id==0) break;
                }
//                if (!subrubro.getCodigo().equalsIgnoreCase(cod_subrubro)) {
//                    System.out.println("Linea: " + linea  + " - " + subrubro.getCodigo() + " no coincide con " + cod_subrubro);
//                    break;
//                }
                Activo activo = new Activo();
                activo.setCodigo(codigo);                
                activo.setCodigoNew(codigo_new);
                activo.setCodigo_fabrica(codigo_fabrica);
                activo.setAnillo(anillo); 
                activo.setSello(sello);
                activo.setEsparrago(esparrago);
                activo.setTuerca(tuerca);
//                activo.setPosicion(posicion);                      
                activo.setId_rubro(rubro.getId());
                activo.setId_subrubro(subrubro.getId());
                activo.setMedida(medida);
                activo.setConexion(conexion);
                activo.setLongitud(longitud);
                activo.setDesc_corta(descripcion);
                String desc_larga = rubro.getDescripcion() + " " + subrubro.getDescripcion()+ " " + activo.getMedida() + " " + activo.getConexion() + " "+ activo.getLongitud();
                activo.setDesc_larga(desc_larga);
//                activo.setContrato(contrato);
                new TActivo().alta(activo);
                }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex){}
    }
}
