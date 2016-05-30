/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actisoft;

import bd.Activo;
import bd.Parametro;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TParametro;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class UpdateStock {
       public static void main(String[] args){
        String line = "";
        final String DELIMITER = ";";
        Integer linea = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Parametro entorno = new TParametro().getByCodigo("entorno");

        try{
            String ent = (entorno!=null)?entorno.getValor():"Producción";
            
            System.out.println(String.format("Trabajando en el entorno: %s.\n Desea continuar?" ,ent ));            
            String readLine = br.readLine();
            if (!readLine.startsWith("s")) {
                System.out.println("Finaliza el proceso. No se actualizó el stock");
                System.exit(0);
            }
            
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }  catch (IOException ex) {
               Logger.getLogger(UpdateStock.class.getName()).log(Level.SEVERE, null, ex);
           }
        Parametro parametro = new TParametro().getByCodigo("stock_file");
        if (parametro == null || parametro.getValor().equals("")) {
            System.out.println("No se encontró el path al archivo de stock");
            System.exit(0);
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(parametro.getValor()))) {
           bf.readLine();
           String codigo = "";
           TActivo ta = new TActivo();
            while((line = bf.readLine())!=null){
                String tokens[] = line.split(DELIMITER);
                linea++;
                codigo = tokens[0];
                Activo activo = ta.getByCodigo(codigo);
                if(activo==null) {
                    System.out.println(codigo);
                    continue;
                }
                activo.setId_estado(OptionsCfg.ACTIVO_ESTADO_DISPONIBLE);                
                activo.setBloqueado(0);
                ta.actualizar(activo);
                System.out.println(linea);
            }
    } catch (FileNotFoundException ex) {
              Logger.getLogger(ActiSoft.class.getName()).log(Level.SEVERE, null, ex);
          }
          catch(IOException ex){
              System.out.println("Error: " + ex.getMessage());
          }
       }
}
