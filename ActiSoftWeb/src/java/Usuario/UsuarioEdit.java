/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import bd.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TUsuario;
import utils.BaseException;
import utils.OptionsCfg;
import utils.PathCfg;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class UsuarioEdit extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UsuarioEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuarioEdit at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Usuario usuario = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                usuario = new TUsuario().getById(id);
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el usuario");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
         if (usuario!=null){
            request.setAttribute("usuario", usuario);
        }
        request.getRequestDispatcher("usuario_edit.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idUsuario = request.getParameter("id");
        String email = request.getParameter("usu_mail");
        String password = request.getParameter("usu_password");
        String password2 = request.getParameter("usu_password2");
        String idTipo = request.getParameter("id_tipo_usuario");
        String activo = request.getParameter("usu_activo");
        
        Integer id_tipo_usuario = 0;
        try{
            Integer id = 0;
            TUsuario tu = new TUsuario();
            Usuario usuario;
            boolean nuevo = false;
            try{
                id = Integer.parseInt(idUsuario);
            } catch(NumberFormatException ex){
                id = 0;
            }
            usuario = tu.getById(id);
            if (usuario ==null) {
                usuario = new Usuario();
                nuevo = true;
                usuario.setUsu_fcreacion(TFecha.ahora(TFecha.formatoBD + " " + TFecha.formatoHora));
                usuario.setUsu_mail(email);
                String passwordHash = "";
                if(password==null || password2==null) throw new BaseException("ERROR","Debe ingresar el password");
                if(!password.equals(password2)) throw new BaseException("ERROR","El password no coincide con la confirmaci&oacute;n");
                try {
                    passwordHash = utils.PasswordHash.createHash(password);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UsuarioEdit.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(UsuarioEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
                usuario.setUsu_password(passwordHash);
            }
            try{
                id_tipo_usuario = Integer.parseInt(idTipo);
            } catch(NumberFormatException ex){
                id_tipo_usuario = 0;
            }
            
            if(activo!=null) usuario.setUsu_activo(1);
            else  usuario.setUsu_activo(0);
            
            usuario.setId_tipo_usuario(id_tipo_usuario);
            if(nuevo){
               usuario.setId( tu.alta(usuario));
            }else tu.actualizar(usuario);
            
            HttpSession session = request.getSession();
            Integer id_usuario_actual = (Integer) session.getAttribute("id_usuario");
            Integer id_tipo_usuario_actual = (Integer) session.getAttribute("id_tipo_usuario");
            TAuditoria.guardar(id_usuario_actual,id_tipo_usuario_actual,OptionsCfg.MODULO_USUARIO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,usuario.getId());

            response.sendRedirect(PathCfg.USUARIO);
        
        } catch(BaseException ex) {
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje",ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
        }        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
