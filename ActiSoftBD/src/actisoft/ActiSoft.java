/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actisoft;

import bd.Activo;
import bd.Activo_contrato_view;
import bd.Cliente;
import bd.Localidad;
import bd.Provincia;
import bd.Rubro;
import bd.Subrubro;
import bd.Usuario;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.TActivo;
import transaccion.TActivo_contrato_view;
import transaccion.TActivo_historia;
import transaccion.TCliente;
import transaccion.TLocalidad;
import transaccion.TProvincia;
import transaccion.TRubro;
import transaccion.TSubrubro;
import transaccion.TUsuario;
import utils.TFecha;

/**
 *
 * @author Diego
 */
public class ActiSoft {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //getListActivoTest();
        //bajaActivoTest();
        //getListClienteTest();
        //altaClienteTest();
        //buscaUsuarioByEmail();
    
        
        //buscaProvinciasTest();
        //buscaLocalidadesTest();
        //listaRubroTest();
        //listaSubrubroTest();
         //cargaActivos();
        
        //actualizaActivos();  
//        HashMap<String,String> mapFiltro = new HashMap<String,String>();
//        mapFiltro.put("id_rubro","7");
//        List<Activo_contrato_view> listFiltro = new TActivo_contrato_view().getListFiltro(mapFiltro);
//        System.out.println(listFiltro.size());
        HashMap<String,String> mapFiltro = new HashMap<String,String>();
        mapFiltro.put("id_cliente","2");
        new TActivo_historia().getListFiltro(mapFiltro);
    }
   
     private static void listaSubrubroTest(){
        List<Subrubro> list = new TSubrubro().getList();
        if (list.size() == 0 ){
            System.out.println("No existe ningún objeto");
        } else 
            for (Subrubro r: list){
                System.out.println(r.getCodigo() + r.getDescripcion());
            }
    }
    private static void listaRubroTest(){
        List<Rubro> list = new TRubro().getList();
        if (list.size() == 0 ){
            System.out.println("No existe ningún objeto");
        } else 
            for (Rubro r: list){
                System.out.println(r.getCodigo() + r.getDescripcion());
            }
    }
    private static void buscaLocalidadesTest(){
        List<Localidad> list = new TLocalidad().getList();
        if (list.size() == 0 ){
            System.out.println("No existe ningún objeto");
        } else
        for(Localidad l:list){
            System.out.println("Localidad: " + l.getLoc_descripcion());
            
        }
            
    }
     private static void buscaProvinciasTest(){
        List<Provincia> list = new TProvincia().getList();
        if (list.size() == 0 ){
            System.out.println("No existe ningún objeto");
        } else
        for(Provincia p:list){
            System.out.println("Provincia   : " + p.getProv_descripcion());
            
        }
            
    }
    private static void buscaUsuarioByEmail(){
        String email = "giuliani.diego@gmail.com";
        TUsuario tu = new TUsuario();
        Usuario u = tu.getByEmail(email);
        if (u !=null){
            System.out.println(u.getUsu_mail());
        }else System.out.println("No se encontro el usuario");
        
        
    }
    
    private static void getListActivoTest(){
        List<Activo> listActivos = new TActivo().getList();
        if (listActivos.size() == 0 ){
            System.out.println("No existe ningún objeto");
        }
        for(Activo activo:listActivos){
            System.out.println(activo.getId());
        }
    } 
    private static void getListClienteTest(){
        List<Cliente> listClientes = new TCliente().getList();
        if (listClientes.size() == 0 ){
            System.out.println("No existe ningún objeto");
        }
        for(Cliente cliente:listClientes){
            System.out.println(cliente.getId());
        }
    }
    private static void altaClienteTest(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Diego");
        cliente.setFecha_alta(TFecha.ahora(TFecha.formatoBD));
        int alta = new TCliente().alta(cliente); 
        if (alta != 0) System.out.println("El objecto fue dado de alta correctamente");
        else System.out.println("Ocurrió un error al intentar dar de alta el objecto");
    }
    
    private static void altaActivoTest(){
        Activo activo = new Activo();
        activo.setCodigo("0001");
        activo.setDesc_corta("Activo prueba 1");        
        new TActivo().alta(activo); 
    }
    private static void bajaClienteTest(){
        Integer id = 1;
        Cliente cliente = new Cliente();
        new TCliente().baja(cliente);
    }
    
    private static void bajaActivoTest(){
        Integer id = 1;
        Activo activo = new TActivo().getById(id);
        if (activo!=null){
            boolean baja = new TActivo().baja(activo);            
            if (baja) 
                 System.out.println("El objeto fue eliminado correctamente");
            else System.out.println("No se pudo eliminar");
            
        }else {System.out.println("No existe el objeto con id " + id); }
    }
    private static void getByIdActivoTest(){
        Activo activo = new TActivo().getById(1);
        
    }
}
