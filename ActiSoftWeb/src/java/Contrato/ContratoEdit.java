package Contrato;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import bd.Cliente;
import bd.Contrato;
import bd.Contrato_detalle;
import bd.Subrubro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transaccion.TAuditoria;
import transaccion.TCliente;
import transaccion.TContrato;
import transaccion.TContrato_detalle;
import transaccion.TRemito;
import transaccion.TSubrubro;
import utils.BaseException;
import utils.OptionsCfg;
import utils.Parser;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class ContratoEdit extends HttpServlet {
   
   
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
        Contrato contrato = null;
        if(request.getParameter("id")!=null) {
            try{
                Integer id = Integer.parseInt(request.getParameter("id"));
                contrato = new TContrato().getById(id);                                
            } catch (NumberFormatException ex){
                request.setAttribute("titulo", "Error");
                request.setAttribute("mensaje","No se ha encontrado el contrato");
                request.getRequestDispatcher("message.jsp").forward(request, response);
                return;
            }            
        } 
        if (contrato!=null){
            request.setAttribute("contrato", contrato);
        }
        request.getRequestDispatcher("contrato_edit.jsp").forward(request, response);
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
       
       
       String id = request.getParameter("id");
       String numero = request.getParameter("numero").trim();
       String fecha = request.getParameter("fecha");
       String fecha_inicio = request.getParameter("fecha_inicio");
       String fecha_fin = request.getParameter("fecha_fin");
       String divisa_contrato = request.getParameter("divisa_contrato");
       String monto_contrato = request.getParameter("monto_contrato");
       String porcentaje_contrato = request.getParameter("porcentaje_contrato");
       String idCliente = request.getParameter("idCliente");

       String[] arr_posicion = request.getParameterValues("posicion");
       String[] arr_codigo = request.getParameterValues("codigo");
       String[] arr_descripcion = request.getParameterValues("descripcion");
       String[] arr_divisa = request.getParameterValues("divisa");
       String[] arr_precio = request.getParameterValues("precio");
       String[] arr_porcentaje = request.getParameterValues("porcentaje");
       String[] arr_unidad = request.getParameterValues("id_unidad");
              
       try{
//           if(id==null) request.getRequestDispatcher("contrato_edit.jsp").forward(request, response);
           
           if (arr_posicion == null) throw new BaseException("ERROR","Error en la posici&oacute;n");
           if (arr_codigo == null) throw new BaseException("ERROR","Error en codigo");
           if (arr_descripcion == null) throw new BaseException("ERROR","Error en descripci&oacute;n");
           if (arr_divisa == null) throw new BaseException("ERROR","Error en divisa");
           if (arr_precio == null) throw new BaseException("ERROR","Error en precio");
           if (arr_porcentaje == null) throw new BaseException("ERROR","Error en porcentaje");
   

           if (!chequearIguales(new Integer[] {arr_posicion.length,
                                            arr_codigo.length,
                                            arr_descripcion.length,
                                            arr_divisa.length,
                                            arr_precio.length,
                                            arr_porcentaje.length})){
           throw new BaseException("ERROR","La cantidad de par&aacute;metros no coincide");
       }
       
       Contrato contrato = null;
       TContrato tc = new TContrato();
       boolean nuevo = false;
       Integer idContrato = 0;
       if(id!=null && !id.equals("")){
           idContrato = Integer.parseInt(id);
           contrato = tc.getById(idContrato);
           
       }
       
       if (contrato==null) {
           contrato = new Contrato();
           nuevo = true;
       } else {
           /*if(new TRemito().getByContratoId(idContrato).size()>0)
               throw new BaseException("ERROR","El contrato fu&eacute; utilizado. No puede ser modificado");
               */
       }
       
       try{
           if(numero==null||numero.equals("")) throw new BaseException("Error de contrato","Debe ingresar el n&uacute;mero de contrato");
           
           Contrato contrato2 =tc.getByNumero(numero);
           if(contrato2 !=null && contrato.getId()!=contrato2.getId()){
               throw new BaseException("Contrato ya cargado","Ya existe un contrato con ese n&uacute;mero");
           }
           Integer id_cli =0;
           if(idCliente==null || idCliente.equals("")) idCliente="0";
           id_cli = Integer.parseInt(idCliente);
           Cliente cliente = new TCliente().getById(id_cli);
           if(cliente==null) throw new BaseException("Cliente inexistente","Debe seleccionar el cliente del contrato");
           
           contrato.setNumero(numero);
           try{
            contrato.setFecha(TFecha.convertirFecha(fecha, TFecha.formatoVista, TFecha.formatoBD));
            contrato.setFecha_inicio(TFecha.convertirFecha(fecha_inicio, TFecha.formatoVista, TFecha.formatoBD));
            contrato.setFecha_fin(TFecha.convertirFecha(fecha_fin, TFecha.formatoVista, TFecha.formatoBD));
           } catch (NumberFormatException ex){
               throw new BaseException("Error","Error de parseo de argumentos");
           } 
           
           contrato.setId_divisa(Parser.parseInt(divisa_contrato));
           contrato.setMonto(Parser.parseFloat(monto_contrato));
           contrato.setPorcentaje(Parser.parseFloat(porcentaje_contrato));
           contrato.setId_cliente(cliente.getId());
           ArrayList<Contrato_detalle> lista_detalle = new ArrayList<Contrato_detalle>();
           TSubrubro ts = new TSubrubro();
           for(int i=0;i<arr_posicion.length;i++){
               if (arr_posicion[i].equals("")) continue;
               Float precio = Float.parseFloat(arr_precio[i]);
               Float porcentaje = Parser.parseFloat(arr_porcentaje[i]);
               Integer id_unidad = Parser.parseInt(arr_unidad[i]);
               Integer id_divisa = Parser.parseInt(arr_divisa[i]);
               String codigo = arr_codigo[i].trim();
               Subrubro subrubro = ts.getByCodigo(codigo);
               if (subrubro==null){
                   throw new BaseException("ERROR","No se encontr&oacute; el subrubro");
               }
               /*
                *  Verificar si el subrubro se corresponde con el item de contrato
                */
               Contrato_detalle detalle = new Contrato_detalle();
               detalle.setId_activo(0);
               detalle.setPrecio(precio);
               detalle.setPosicion(Integer.parseInt(arr_posicion[i]));
               detalle.setId_divisa(id_divisa);
               detalle.setId_subrubro(subrubro.getId());
               detalle.setId_rubro(subrubro.getId_rubro());
               detalle.setId_clase(subrubro.getId_clase());
               detalle.setDescripcion(arr_descripcion[i]);    
               detalle.setPorcentaje(porcentaje);
               detalle.setId_unidad(id_unidad);
               lista_detalle.add(detalle);
           }
           if(nuevo){
               idContrato = tc.alta(contrato);
               contrato.setId(idContrato);
           } else tc.actualizar(contrato);
           
           TContrato_detalle td = new TContrato_detalle();
           
           /* Borramos todos los items anteriores */    
           List<Contrato_detalle> lstDetalleAnt = td.getByContratoId(idContrato);
           for (Contrato_detalle d:lstDetalleAnt){
               ts.baja(d);
           }
           /* Cargamos los nuevos items */
           for(Contrato_detalle detalle:lista_detalle){                
                detalle.setId_contrato(idContrato);
                if(detalle.getId()==0) td.alta(detalle);
                else td.actualizar(detalle);
           }
        HttpSession session = request.getSession();
        Integer id_usuario = (Integer) session.getAttribute("id_usuario");
        Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
        TAuditoria.guardar(id_usuario,id_tipo_usuario,OptionsCfg.MODULO_CONTRATO,nuevo?OptionsCfg.ACCION_ALTA:OptionsCfg.ACCION_MODIFICAR,contrato.getId());
        
           response.sendRedirect("Contrato");
       } catch(NumberFormatException ex){
            request.setAttribute("titulo", "Error");
            request.setAttribute("mensaje","Error de parseo de par&aacute;metros");
            request.getRequestDispatcher("message.jsp").forward(request, response);
            return;
       }
       }catch(BaseException ex){
            request.setAttribute("titulo", ex.getResult());
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("message.jsp").forward(request,response);
            return;
       }
    }
    private boolean chequearIguales(Integer[] arr){
        if (arr.length==0) return true;
        Integer first = arr[0];
        for(int i = 1;i<arr.length;i++){
            if (arr[i]!=first) return false;
        }
        return true;
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
