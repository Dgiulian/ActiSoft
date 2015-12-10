/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

/**
 *
 * @author Diego
 */
import bd.Usuario;
//import conexion.TransaccionRS;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import java.text.SimpleDateFormat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PasswordHash;

public class TUsuario extends TransaccionBase<Usuario> {

     @Override
    public List<Usuario> getList() {
        return super.getList("select id,usu_mail,usu_fcreacion,usu_activo,id_tipo_usuario from usuario");
    }
     
    /*
     Dado el mail de un usuario, recupera el objeto de la BD.
     */

    public Usuario getByEmail(String email) {
        Usuario u = new Usuario();
        String query = String.format("SELECT id,usu_mail,usu_password,usu_activo,usu_hash_activa,usu_fcreacion,id_tipo_usuario from usuario where usuario.usu_mail = '%s'",email);
        List<Usuario> listaUsuario = this.getList(query);

        if (listaUsuario.size() > 0) return (Usuario) listaUsuario.get(0);
                                     else return null;        
    }
    
     public List<Usuario> getUsuarios() {
        String query = "SELECT * from usuario";
        return this.getList(query);
    }

    public Usuario getById(int id) {
        Usuario u = new Usuario();        
        String query = "SELECT id,usu_mail,usu_password,usu_activo,usu_hash_activa,usu_fcreacion,id_tipo_usuario from usuario where usuario.id = " + id;
        return this.getById(query);
        
    }
    /*
     * Inserta un usuario en la BD
     */

    public int altaUsuario(String email, String password, String pass_activa) {
        Usuario u = new Usuario();
        int id = 0;
        try {
            String password_hash = PasswordHash.createHash(password);
            //String pass_activa = TUsuario.createPassActiva(password);
            String hash_activa = PasswordHash.createHash(pass_activa);

            u.setUsu_mail(email);
            u.setUsu_password(password_hash);
            u.setUsu_hash_activa(hash_activa);
            u.setUsu_activo(0);

            java.util.Date dt = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            u.setUsu_fcreacion(sdf.format(dt));
            id = this.alta(u);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean cambiarPassword(Usuario u, String newPassword) {
        boolean todoOk = false;
        try {
            String password_hash = PasswordHash.createHash(newPassword);
            u.setUsu_password(password_hash);
            todoOk = this.actualizar(u);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todoOk;
    }

    public boolean activaUsuario(Usuario u, String pass_activa) {

        boolean todoOk = false;
        try {
            if (PasswordHash.validatePassword(pass_activa, u.getUsu_hash_activa())) {
                u.setUsu_activo(1);
                todoOk = this.actualizar(u);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todoOk;
    }

    public boolean desactivaUsuario(Usuario u) {

        u.setUsu_activo(0);
        boolean todoOk = this.actualizar(u);
        return todoOk;
    }

    /*
     Dado un usuario, valida si el password es correcto
     */
    public boolean validaPassword(Usuario u, String password) {

        String hash = u.getUsu_password();
        boolean valida = false;
        try {
            valida = PasswordHash.validatePassword(password, hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valida;
    }

    public final static String createPassActiva(String password) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public final static String createPassNueva() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public boolean actualizar(Usuario usuario) {
        return this.actualizar(usuario, "id");        
    }

    public boolean cambiarActiva(Usuario u, String validate) {
        boolean todoOk = false;
        try {
            String password_hash = PasswordHash.createHash(validate);
            u.setUsu_hash_activa(password_hash);
            todoOk = this.actualizar(u);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(TUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todoOk;
    }

   
}
