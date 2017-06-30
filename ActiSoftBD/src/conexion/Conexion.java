package conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection conexion;

    /**
     * Método utilizado para recuperar el valor del atributo conexion
     *
     * @return conexion contiene el estado de la conexión
     *
     */
    public Conexion() {
        conexion = null;
    }

    public Connection getConexion() {
        return conexion;
    }

    /**
     * Método utilizado para establecer la conexión con la base de datos
     *
     * @return estado regresa el estado de la conexión, true si se estableció la
     * conexión, falso en caso contrario
     */
    public boolean crearConexion() {
        try {
            /*String driver = "oracle.jdbc.OracleDriver";
            String url = "jdbc:oracle:thin:@";
            String host = "10.1.33.32";
            String port = ":1521";
            String nombrebd = ":OLTPNQN";
            String usr = "ACTISOFT";
            String password = "Actisoft";
            Class.forName(driver).newInstance();*/
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://";
            

           String propiedad = "zeroDateTimeBehavior=convertToNull";           
           propiedad += "&useSSL=true";
            /* Para subir a produccion */
//          String port = ":3306";
//          String host = "127.0.0.1";
//          String usr = "eventpass";
//          String password = "Eventpass$Neuquencom";
            /* Local con base produccion */ 
//          String port = ":3306";
//          String host = "138.219.40.110";           
//          String usr = "eventpass";
//          String password = "Eventpass$Neuquencom";
            
           /* Local con base local */
            String host = "127.0.0.1";
            String port = ":3306";
            String nombrebd = "/actisoft";
            String usr = "actisoft";
            String password = "4ct1soft";

            Class.forName(driver).newInstance();
            try {
                conexion = DriverManager.getConnection(url + host + port + nombrebd + "?" + propiedad, usr, password);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }


    }

    /**
     * METODO QUE CIERRA UNA CONEXION CON LA BASE DE DATOS
     *
     * @throws java.sql.SQLException
     */
    public void cerrar() throws SQLException {
        conexion.close();
    }

    /**
     *
     * Método utilizado para realizar las instrucciones: INSERT, DELETE y UPDATE
     *
     * @param sql Cadena que contiene la instrucción SQL a ejecutar
     * @return estado regresa el estado de la ejecución, true(éxito) o
     * false(error)
     *
     */
    public boolean ejecutarSQL(String sql) {

        try {
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public boolean EjecutarInsert(String consulta) {
        try {
            Statement sentencia;
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return !sentencia.execute(consulta);
        } catch (SQLException e) {
            /*Mensajes m = new Mensajes();
            m.mensajeError("Error al insertar en la base de datos");*/
            e.printStackTrace();
            return false;
        }
    }

    public int EjecutarInsertAutonumerico(String consulta) {
        try {
            //Statement sentencia;
            //sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, Statement.RETURN_GENERATED_KEYS);

            PreparedStatement ps = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.executeUpdate();

            System.out.println("Consulta:" + consulta);


            ResultSet rs2 = ps.getGeneratedKeys();

            if (rs2.next()) {
                System.out.println("por el primero");
                return (rs2.getInt(1));
            } else {
                System.out.println("por el segundo");
                return 0;
            }


        } catch (SQLException e) {
            /*Mensajes m = new Mensajes();
            m.mensajeError("Error al insertar en la base de datos");*/
            //e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     *
     * Método utilizado para realizar la instrucción SELECT
     *
     * @param sql Cadena que contiene la instrucción SQL a ejecutar
     * @return resultado regresa los registros generados por la consulta
     *
     */
    public ResultSet ejecutarSQLSelect(String sql) {

        try {
            Statement sentencia;
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return sentencia.executeQuery(sql);

        } catch (SQLException ex) {
            return null;
        }

    }

    public void AutoCommit() throws SQLException {
        conexion.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        conexion.commit();
    }

    public void rollback() throws SQLException {
        conexion.rollback();
    }

    public void conectarse() {
        crearConexion();
    }

    public boolean guardarSite(int idSite, String descripcion, String localidad, String organismo, String latitud, String longitud, String fecha) {
        try {
            PreparedStatement st = conexion.prepareStatement("insert into ACT_SITE(SIT_IDENTIFICADOR,SIT_DESCRIPCION,SIT_FCH_ALTA,SIT_ORGANISMO,SIT_LOCALIDAD, SIT_LATITUD, SIT_LONGITUD) values(?,?,?,?,?,?,?)");
            st.setInt(1, idSite);
            st.setString(2, descripcion);
            st.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            st.setString(4, organismo);
            st.setString(5, localidad);
            st.setFloat(6, Float.parseFloat(latitud));
            st.setFloat(7, Float.parseFloat(longitud));
            st.execute();
            st.close();
            boolean salida = true;
            return salida;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean guardarSite(int id_Site, String imagen1, String imagen2, String imagen3, String imagen4, String imagen5) {

        FileInputStream is1 = null;
        FileInputStream is2 = null;
        FileInputStream is3 = null;
        FileInputStream is4 = null;
        FileInputStream is5 = null;
        boolean salida = true;
        try {
            if (is1 != null) {
                is1 = new FileInputStream(imagen1);
            }

            if (is2 != null) {
                is2 = new FileInputStream(imagen2);
            }

            if (is3 != null) {
                is3 = new FileInputStream(imagen3);
            }

            if (is4 != null) {
                is4 = new FileInputStream(imagen4);
            }

            if (is5 != null) {
                is5 = new FileInputStream(imagen5);
            }

            PreparedStatement st = conexion.prepareStatement("UPDATE ACT_SITE SET ACT_SITE.sit_foto1 = ?, ACT_SITE.sit_fotoverano= ? , ACT_SITE.sit_fotootonio= ? , ACT_SITE.sit_fotoinvierno=?,  ACT_SITE.sit_fotoprimavera= ? where ACT_SITE.sit_identificador=" + id_Site);

            st.setBlob(1, is1);
            st.setBlob(2, is2);
            st.setBlob(3, is3);
            st.setBlob(4, is4);
            st.setBlob(5, is5);

            st.execute();
            is1.close();
            is2.close();
            is3.close();
            is4.close();
            is5.close();
            st.close();
            salida =
                    true;
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            salida = false;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            salida = false;
        } finally {
            try {
                is1.close();
                is2.close();
                is3.close();
                is4.close();
                is5.close();
                return salida;
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                salida = false;
            }

        }



        return salida;
    }

    public boolean guardarActivoFoto(int id, String caminoFoto) {

        FileInputStream is1 = null;
        boolean salida = true;
        try {
            is1 = new FileInputStream(caminoFoto);
            //System.out.println("CAMINO FOTO = " + caminoFoto);
            PreparedStatement st1 = conexion.prepareStatement("UPDATE ACT_EQUIPO SET EQU_FOTOGRAFIA = ? WHERE EQU_IDENTIFICADOR = ?");
            st1.setBlob(1, is1);
            st1.setInt(2, id);
            st1.execute();
            is1.close();
            st1.close();
            salida =
                    true;
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            salida = false;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            salida = false;
        } finally {
            try {
                is1.close();
                return salida;
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                salida = false;
            }

        }



        return salida;
    }

    public void desconectarse() {
        try {
            conexion.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
        }
    }

    public Connection recuperar() {
        return conexion;
    }
}
