/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actisoft;

import bd.Activo;
import bd.Kit_detalle;
import bd.Parametro;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TKit_detalle;
import transaccion.TParametro;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class AcomodaKit {
    
    public static void main(String[] args){         
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Parametro entorno = new TParametro().getByCodigo("entorno");
        try{
            String ent = (entorno!=null)?entorno.getValor():"Producción";
            
            System.out.println(String.format("Trabajando en el entorno: %s.\n Desea continuar?" ,ent ));            
            String readLine = br.readLine();
            if (!readLine.startsWith("s")) {
                System.out.println("Finaliza el proceso. No se actualizó la base");
                System.exit(0);
            }
            
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }  catch (IOException ex) {
               Logger.getLogger(UpdateStock.class.getName()).log(Level.SEVERE, null, ex);
           }
        TKit_detalle tk = new TKit_detalle();
        TActivo ta = new TActivo();
        
        List<Kit_detalle> lstDetalle = tk.getList();
                
        System.out.println(lstDetalle.size());
        for (Kit_detalle kd: lstDetalle){
            Activo activo = ta.getById(kd.getId_activo());
            if (activo.getBloqueado() == 0) continue;           
            String query = String.format("select * from activo where activo.codigo = '%s' and activo.bloqueado = 0",activo.getCodigo());            
            Activo activoNuevo = ta.getById(query);
            if(activoNuevo==null) {
                System.out.println("No se encontró el activo: " + activo.getCodigo());
                continue;
            }
            System.out.println(String.format("El activo %d stock: %.2f será reemplazado por el activo %d / %.2f",activo.getId(),activo.getStock(),activoNuevo.getId(),activoNuevo.getStock()));
           
            activoNuevo.setId_estado(OptionsCfg.ACTIVO_ESTADO_KIT);
            activoNuevo.setStock(activo.getStock());
            kd.setId_activo(activoNuevo.getId());
            activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_ELIMINADO);

            
            ta.actualizar(activoNuevo);
            ta.actualizar(activo);
            tk.actualizar(kd);
        }
        
        
   }   
}
