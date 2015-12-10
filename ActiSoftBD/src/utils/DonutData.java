/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Diego
 */
public class DonutData {
    public String label = "";
    public String value = "";
    public DonutData(){
        
    }
    public DonutData(String lbl,String val){
        this.label = lbl;
        this.value = val;
    }
    public String getLabel(){
        return this.label;
    }
    public String getValue(){
        return this.value;
    }
    public void setLabel(String lbl){
        this.label = lbl;
    }
    public void setValue(String val){
        this.value = val;
    }
            
}
