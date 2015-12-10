/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Diego
 */
public class Parser {
    public static Integer parseInt(String valor){
        Integer valor_nuevo = 0;
        try{
            valor_nuevo = Integer.parseInt(valor);
        } catch(NumberFormatException ex){
            valor_nuevo = 0;
        }
        return valor_nuevo;
    }
     public static Float parseFloat(String valor){
        Float valor_nuevo = 0f;
        try{
            valor_nuevo = Float.parseFloat(valor);
        } catch(NumberFormatException ex){
            valor_nuevo = 0f;
        }
        return valor_nuevo;
    }
}
