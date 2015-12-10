/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Diego
 */
public class GraphData {
    private Double x;
    private Double y;
    private String label;
    
    public Double getX(){
        return this.x;        
    }
    public Double getY(){
        return this.y;
    }
    public String getLabel(){
        return this.label;
    }
    public void setX(Double x){
        this.x = x;        
    }
    public void setY(Double y){
        this.y = y;
    }
    public void setLabel(String label){
        this.label = label;
    }
}
