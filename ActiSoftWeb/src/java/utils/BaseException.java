/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Diego
 */
// Make this class abstract so that developers are forced to create
// suitable exception types only
public class BaseException extends Exception{
    //Each exception message will be hold here
    private String message;
    private String result;
 
    public BaseException(String result,String msg)
    {
        this.result = result;
        this.message = msg;
    }
    //Message can be retrieved using this accessor method
    public String getMessage() {
        return this.message;
    }
    public String getResult(){
        return this.result;
    }
}