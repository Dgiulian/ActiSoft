/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actisoft;

import bd.Activo;
import bd.Remito;
import bd.Remito_detalle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TRemito;
import transaccion.TRemito_detalle;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class AjustaRemito {
    String line = "";
        final String DELIMITER = ";";
      public static void main(String[] args){
          String fileName = "e:\\Downloads\\Inventario200116.csv";           
          
          new AjustaRemito(fileName);
      }
      AjustaRemito(String fileName){
        Integer linea = 1;
        Integer cant_mal = 0;
        TActivo ta = new TActivo();
        String codigo = "";
        
        Remito remito = new TRemito().getByNumero(1);
        
        TRemito_detalle td = new TRemito_detalle();
        HashMap<String,String> filtro = new HashMap<String,String>();
        filtro.put("id_remito",remito.getId().toString());
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
           //bf.readLine();
           
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                codigo = tokens[0].trim();
                    Activo activo = ta.getByCodigo(line);
                    if(activo==null) {
                        System.out.println(String.format("No se encontró el activo %s",codigo));
                        cant_mal +=1;
                        continue;
                    }
                     activo.setStock(1f);
                            activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                            new TActivo().actualizar(activo);
                            
                    filtro.put("id_activo",activo.getId().toString());
                    List<Remito_detalle> listaDetalle = td.getListFiltro(filtro);
                    if (listaDetalle==null ||  listaDetalle.size() != 1){
                        System.out.println(String.format("No existe en el remito %s",activo.getCodigo()));
                        continue;
                    }
                    
                    for(Remito_detalle d:listaDetalle){
                        boolean baja = td.baja(d);
                        if(baja){
                            activo.setStock(1f);
                            activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);
                            new TActivo().actualizar(activo);
                        }else System.out.println("Error en la baja " + codigo);
                    }
               System.out.println(activo.getId());
            }
            System.out.println(String.format("Activos inexistentes: %d", cant_mal));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
     
      }
}
