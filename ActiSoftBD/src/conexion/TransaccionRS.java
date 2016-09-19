/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import com.mysql.jdbc.ResultSetMetaData;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author black
 */
public class TransaccionRS {

    public String recuperarListaDefault(Object objeto, String extensionSQL) {

        String clase = objeto.getClass().getSimpleName();
        String tabla = clase.toLowerCase();
        Field[] atributos = objeto.getClass().getDeclaredFields();
        if (atributos.length == 0 ) atributos = objeto.getClass().getFields();
        String where = "";
        Object from = null;
        boolean band = false;
        for (int i = 0; i <= atributos.length - 1; i++) {
            try {

                Method getterFrom = objeto.getClass().getMethod("sqlfrom");
                from = getterFrom.invoke(objeto, new Object[0]);

                Class tipoClass = atributos[i].getType();
                String tipo = tipoClass.getSimpleName();
                String getNombre = atributos[i].getName();
                getNombre = getNombre.substring(0, 1).toUpperCase() + getNombre.substring(1, getNombre.length());
                Method getter = objeto.getClass().getMethod("get" + getNombre);
                if (tipo.equals("String") == true) {
                    try {
                        Object valor = getter.invoke(objeto, new Object[0]);
                        if (valor == null || valor.equals("") || valor.equals("0000-01-01")) {
                        } else {
                            where += band ? " and " : "";
                            where += getNombre.toLowerCase() + " like '%" + valor + "%'";
                            band = true;
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (tipo.equals("Integer") == true || tipo.equals("int") == true || tipo.equals("float") == true || tipo.equals("double") == true) {
                    try {
                        Object valor = getter.invoke(objeto, new Object[0]);
                        if (valor != null) {
                            if (tipo.equals("Integer") || tipo.equals("int")) {
                                if (Integer.parseInt(String.valueOf(valor)) != 0) {
                                    where += band ? " and " : "";
                                    where += getNombre.toLowerCase() + " = " + valor;
                                    band = true;
                                }
                            }
                            if (tipo.equals("float")) {
                                if (Float.parseFloat(String.valueOf(valor)) != 0) {
                                    where += band ? " and " : "";
                                    where += getNombre.toLowerCase() + " = " + valor;
                                    band = true;
                                }
                            }
                            if (tipo.equals("double")) {
                                if (Double.parseDouble(String.valueOf(valor)) != 0) {
                                    where += band ? " and " : "";
                                    where += getNombre.toLowerCase() + " = " + valor;
                                    band = true;
                                }
                            }
                        } else { }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String consulta = "select * from " + String.valueOf(from);
        if (!where.equalsIgnoreCase("")) {
            consulta += " where " + where;
        }
        if (!extensionSQL.equalsIgnoreCase("")) {
            consulta += " " + extensionSQL;
        }
        return consulta;
    }

    public List recuperarLista(String clase, ResultSet rs) {
        Method metodo;
        String tipobd = "";

        try {

            if (rs != null) {

                String className = clase;
                Class claseGenerada = Class.forName(className);
                List lista = new ArrayList();


                while (rs.next()) {
                    Object objeto = claseGenerada.newInstance();
                    ResultSetMetaData rsm = (ResultSetMetaData) rs.getMetaData();
                    // The column count starts from 1
                    for (int i = 1; i <= rsm.getColumnCount(); i++) {
                        String name = rsm.getColumnLabel(i);
//                        String name = rsm.getColumnName(i);                        
                        int tipo = rsm.getColumnType(i);
                        Object valor = null;
                        tipobd = "";
//                        System.out.println(rsm.getColumnTypeName(i)  + ":" + tipo);
                        switch (tipo) {
                            case 1:
                                valor = rs.getString(i);
                                tipobd = "java.lang.String";
                                break;
                            case 3: /* Decimal */
                                valor = rs.getFloat(i);
                                tipobd = "java.lang.Float";
                                break;
                            case 4:
                                valor = rs.getInt(i);
                                tipobd = "java.lang.Integer";
                                break;
                            case 8:
                                valor = rs.getDouble(i);
                                tipobd = "java.lang.Double";
                                break;
                            case 12:                                
                                valor = new String(rs.getBytes(i)); //rs.getString(i).replace('"', '\"');                                                                                              
                               
                                tipobd = "java.lang.String";
                                break;
                            case 91: // Date
                                valor = rs.getString(i);
                                tipobd = "java.lang.String";
                                break;
                            case 92:
                                //valor = rs.getTime(i);
                                //tipobd = "java.sql.Time";
                                valor = rs.getString(i);
                                tipobd = "java.lang.String";
                                break;
                            case 93: // Timestamp
                                valor = rs.getString(i);
                                tipobd = "java.lang.String";
                                break;
                            case -5:
                                valor = rs.getInt(i);
                                tipobd = "java.lang.Integer";
                                break;
                            case -1:
                                valor = rs.getString(i);
                                tipobd = "java.lang.String";
                                break;
                            case 7:
                                valor = rs.getFloat(i);
                                tipobd = "java.lang.Float";
                                break;
                            default:
                                valor = "";
                                tipobd = "" + tipo;
                        }

                        Class[] clasesParamSetEmail = new Class[1];
                        clasesParamSetEmail[0] = Class.forName(tipobd);
                        name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

                        Method metSetEmail = claseGenerada.getMethod("set" + name, clasesParamSetEmail);
                        Object[] paramSetEmail = new Object[1];
                        paramSetEmail[0] = valor;
                        metSetEmail.invoke(objeto, paramSetEmail);
                        // Do stuff with name
                    }
                    lista.add(objeto);
                }
                return lista;
            } else {
                return null;
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchMethodException ex) {
            System.out.println("No se pudo recuperar el metodo " + tipobd + " / " + clase);
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SecurityException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("No se pudo recuperar la clase " + tipobd + " / " + clase);
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /*public Object recuperarObjeto(String objeto, ResultSet rs){


    }*/
    public ArrayList<Field> recuperarAtributos(Class clase){
        ArrayList<Field> fields = new ArrayList<Field>();
        Field[] atributos = clase.getFields();
        for (int i = 0; i <= atributos.length - 1; i++) {
            fields.add(atributos[i]);
        }
        return fields;
    }
    
    
//    public String queryInsert(Object objeto){
//        String clase = objeto.getClass().getSimpleName();
//        String tabla = clase.toLowerCase();
//        Class<? extends Object> aClass = objeto.getClass();
//        Field[] atributos = aClass.getFields();        
//        String query = "";
//        query +="insert into " + tabla + " (";
//        for (int i = 0; i <= atributos.length - 1; i++) {
//            query +=atributos[i].getName();
//            if (i != atributos.length - 1) {
//                query += ",";
//            }
//        }
//        query += ") values (";
//        for (int i = 0; i <= atributos.length - 1; i++) {
//            try {
//                Class tipoClass = atributos[i].getType();
//                String tipo = tipoClass.getSimpleName();
//                String getNombre = atributos[i].getName();
//                getNombre = getNombre.substring(0, 1).toUpperCase() + getNombre.substring(1, getNombre.length());
//                Method getter = objeto.getClass().getMethod("get" + getNombre);
//                if (tipo.equals("String") == true) {
//                    try {
//                        Object valor = getter.invoke(objeto, new Object[0]);
//                        query += "'" + valor + "'";
//                    } catch (IllegalAccessException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IllegalArgumentException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InvocationTargetException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else if (tipo.equals("Integer") == true || tipo.equals("int") == true || tipo.equals("float") == true || tipo.equals("double") == true) {
//                    try {
//                        Object valor = getter.invoke(objeto, new Object[0]);
//                        query += valor;
//                    } catch (IllegalAccessException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IllegalArgumentException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InvocationTargetException ex) {
//                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if (i != atributos.length - 1) {
//                    query += ",";
//                }
//            } catch (NoSuchMethodException ex) {
//                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SecurityException ex) {
//                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        query +=")";
//        return query;
//    }
    public String queryInsert(Object objeto){
         String clase = objeto.getClass().getSimpleName();
        String tabla = clase.toLowerCase();
        Field[] atributos = objeto.getClass().getFields();
        if(atributos.length==0) atributos = objeto.getClass().getDeclaredFields();
        
        ArrayList<String> lstCampos  = new ArrayList<String>();
        ArrayList<String> lstValores = new ArrayList<String>();
        String query = "";
        for (int i = 0; i <= atributos.length - 1; i++) {
            try {
                Field field = atributos[i];
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
                
                Class tipoClass  = atributos[i].getType();
                String tipo      = tipoClass.getSimpleName();
                String getNombre = atributos[i].getName();
                getNombre        = getNombre.substring(0, 1).toUpperCase() + getNombre.substring(1, getNombre.length());
                Method getter    = objeto.getClass().getMethod("get" + getNombre);
                Object valor;
                try {
                    valor = getter.invoke(objeto, new Object[0]);
                    if (tipo.equals("Fecha")) {
                        if( valor==null || "".equals(valor)) continue;
                        // No se guarda el campo fecha si esta vacio o nulo
                        lstCampos.add(field.getName());
                        lstValores.add("'" + valor + "'");
                    } else if (tipo.equals("String") == true) {
                            lstCampos.add(field.getName());
                            lstValores.add("'" + valor + "'");
                    } else if (tipo.equals("Integer") == true || tipo.equals("int") == true
                            || tipo.equals("Float") == true   || tipo.equals("float") == true
                            || tipo.equals("Double") == true  || tipo.equals("double") == true) {
                            lstCampos.add(field.getName());
                            lstValores.add(valor.toString());
                    } 
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query = "insert into " + tabla + " (";
        int i = 0;
        for(String campo:lstCampos){
            query += campo;
            i++;
            if (i < lstCampos.size() ) {
                query+=",";
            }            
        }
        query += ") values(";
        i = 0;
        for(String valor:lstValores){
            query += valor;
            i++;
            if (i < lstValores.size()) {
                query+=",";
            }
        }
        query+=")";
        return query;
    }
    
    public String queryDelete(Object objeto){
        String clase = objeto.getClass().getSimpleName();
        String tabla = clase.toLowerCase();
        Field[] atributos = objeto.getClass().getDeclaredFields();
        if (atributos.length == 0 ) atributos = objeto.getClass().getFields();
        String query = "";
        query += "delete from  " + tabla + " where ";
        if (atributos.length > 0) {
            query += atributos[0].getName();        
            query += " = ";

            try {
                Class tipoClass = atributos[0].getType();
                String tipo = tipoClass.getSimpleName();
                String getNombre = atributos[0].getName();
                getNombre = getNombre.substring(0, 1).toUpperCase() + getNombre.substring(1, getNombre.length());
                Method getter = objeto.getClass().getMethod("get" + getNombre);
                if (tipo.equals("String") == true) {
                    try {
                        Object valor = getter.invoke(objeto, new Object[0]);
                        query += "'" + valor + "'";
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (tipo.equals("Integer") == true || tipo.equals("int") == true
                        || tipo.equals("Float") == true || tipo.equals("float") == true) {
                    try {
                        Object valor = getter.invoke(objeto, new Object[0]);
                        query += valor;
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return query;
    }
    public String queryUpdate(Object objeto, String campoId){
        
        String where = "";
        String clase = objeto.getClass().getSimpleName();
        String tabla = clase.toLowerCase();
        Field[] atributos = objeto.getClass().getDeclaredFields();
        if (atributos.length == 0 ) atributos = objeto.getClass().getFields();
        String query = new String();
        query ="update " + tabla + " set ";        
        for (int i = 0; i <= atributos.length - 1; i++) {
            try {
                Class tipoClass = atributos[i].getType();
                String tipo = tipoClass.getSimpleName();
                Field field = atributos[i];
                String getNombre = field.getName();
                getNombre = getNombre.substring(0, 1).toUpperCase() + getNombre.substring(1, getNombre.length());
                Method getter = objeto.getClass().getMethod("get" + getNombre);
                try {
                    Object valor = getter.invoke(objeto, new Object[0]);
                if (tipo.equals("Fecha") == true) {
                    if(valor==null|| "".equals(valor)) continue;
                    query+=field.getName() + "='" + valor + "'";
                } else if (tipo.equals("String") == true) {
                    query+=field.getName() + "='" + valor + "'";
                    if (campoId.equalsIgnoreCase(field.getName())) {
                        where = campoId + "='" + valor.toString() + "'";
                    }
                } else if (tipo.equals("Integer") || 
                           tipo.equals("int")  || 
                           tipo.equalsIgnoreCase("float") ||
                           tipo.equalsIgnoreCase("double")) {
                           query+=field.getName() + "=" + valor;

                        if (campoId.equalsIgnoreCase(field.getName())) {
                            where = campoId + "=" + valor;
                        }
                }
                //en el caso de incorporar mas campos a actualizar los separo con , (coma)
                if (i != atributos.length - 1) {
                    query+=",";
                }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        query+=" where " + where;
        return query;
    }
    public boolean altaObjeto(Object objeto) {
        String sql = queryInsert(objeto);
        Conexion conexion = new Conexion();
        conexion.conectarse();
        boolean result = conexion.EjecutarInsert(sql);
        conexion.desconectarse();
        return result;
    }
    public int altaObjetoAutonumerico(Object objeto) {       
        String sql = queryInsert(objeto);
        System.out.println(sql);
        Conexion conexion = new Conexion();
        conexion.conectarse();
        int result = conexion.EjecutarInsertAutonumerico(sql);
        conexion.desconectarse();
        return result;
    }


    public boolean eliminarObjeto(Object objeto) {
        String sql = queryDelete(objeto);
        Conexion conexion = new Conexion();
        conexion.conectarse();
        boolean result = conexion.EjecutarInsert(sql);
        conexion.desconectarse();

        return result;
    }

    public boolean actualizarObjeto(Object objeto, String campoId) {
        String sql = queryUpdate(objeto,campoId);
        System.out.println(sql);
        Conexion conexion = new Conexion();
        conexion.conectarse();
        boolean result = conexion.EjecutarInsert(sql);
        conexion.desconectarse();
        return result;

    }

    public Object altaObjeto(String clase, Map<String, String[]> map) {
        try {

            Class claseGenerada = Class.forName(clase.trim());
            Object objeto = claseGenerada.newInstance();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (!key.equalsIgnoreCase("clase")) {
                    Object value = map.get(key)[0];
                    String name = key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
                    Method miMetodo = recuperarMetodo(claseGenerada, "set" + name);
                    if (miMetodo != null) {
                        String nameParameter = miMetodo.getParameterTypes()[0].getName();
                        Object[] parametro = new Object[1];
                        parametro[0] = recuperarValor(value, nameParameter);

                        miMetodo.invoke(objeto, parametro);
                    }
                }
            }
            return objeto;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            return false;
        } catch (SecurityException ex) {
            return null;
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }

    }

    public Method recuperarMetodo(Class clase, String metodo) {
        Method[] metodos = clase.getMethods();
        int i = 0;
        while (i < metodos.length) {
            if (metodos[i].getName().equalsIgnoreCase(metodo)) {
                return metodos[i];
            }
            i++;
        }
        return null;
    }

    public Object recuperarValor(Object value, String tipoParametro) {
        if (tipoParametro.equalsIgnoreCase("java.lang.Integer")) {
            return Integer.parseInt((String) value);
        }
        if (tipoParametro.equalsIgnoreCase("java.lang.String")) {
            return (String) value;
        }
        if (tipoParametro.equalsIgnoreCase("java.lang.Float")) {
            return Float.parseFloat((String) value);
        }

        return null;
    }
    public Object generarInstancia(String clase) {
        try {
            Class claseGenerada = Class.forName(clase.trim());
            Object objeto = claseGenerada.newInstance();
            return objeto;
        } catch (InstantiationException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransaccionRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
