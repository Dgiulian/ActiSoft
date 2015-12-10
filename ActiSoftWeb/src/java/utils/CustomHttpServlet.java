/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Diego
 */
public class CustomHttpServlet extends HttpServlet {
    public Map<String,String> procesarFiltro(HttpServletRequest request){
       HashMap filtro = new HashMap();
       Enumeration<String> parameterNames = request.getParameterNames();
       
       while(parameterNames.hasMoreElements()){
           String name = parameterNames.nextElement();           
           if(request.getParameter(name)!=null && !request.getParameter(name).equals(""))               
                filtro.put(name, request.getParameter(name));
       }

        
        return filtro;        
    }
    
}
