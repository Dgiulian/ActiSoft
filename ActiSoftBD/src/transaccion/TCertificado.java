/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Certificado;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import org.joda.time.LocalDate;
import utils.OptionsCfg;
import utils.TFecha;

public class TCertificado extends TransaccionBase<Certificado>{
    public List<Certificado>getList(){
        return super.getList("select * from certificado");
    }
    public List<Certificado>getByObjetoId(Integer id_modulo, Integer id_objeto){
        return super.getList("select * from certificado where certificado.id_modulo = %d and certificado.id_objeto = " + id_objeto);
    }
    public Certificado getById(Integer id){
        String query = String.format("select * from certificado where certificado.id = %d",id);        
        return super.getById(query);
    }    
    public boolean actualizar(Certificado certificado){
        return super.actualizar(certificado, "id");
    }
    /* Devuelve un certificado vigente si existiere para un objeto */
    public Certificado getVigente(Integer id_modulo, Integer id_objeto){
        /* Fecha desde: Desde */
        /* Fecha hasta: Hasta */
        return this.getVigente(id_modulo,id_objeto,TFecha.ahora(TFecha.formatoBD));
        
    }
     /* Devuelve un certificado vigente si existiere para un objeto */
    public Certificado getVigente(Integer id_modulo, Integer id_objeto,String fecha){
        String query = String.format("select * from certificado\n" +
                        " where certificado.id_modulo = %d\n" +
                        " and certificado.id_objeto = %d\n" +                        
                        " and certificado.fecha_desde <= '%s'" +
                        " and certificado.fecha_hasta >= '%s'" +
//                      " and certificado.id_resultado = %d " +
                        " and certificado.archivo_url <> '' ",id_modulo,id_objeto,fecha,fecha);
        
        query = String.format("select * \n" +
                "  from certificado\n" +
                " where certificado.id_modulo = %d\n" +
                " and certificado.id_objeto = %d\n" +
                "   and certificado.fecha_desde <= '%s' \n" +
                " order by certificado.fecha_hasta desc,\n" +
                "	    certificado.id desc\n" +
                " limit 0,1 ",id_modulo,id_objeto,fecha);
        //System.out.println(query);
        return super.getById(query);
    }
    /*
     * Devuelve un Map con un certificado válido para cáda objeto
     * Donde la key del mapa es el Id de objeto y el valor es un certificado válido.
     * Pueden existir varios certificados válidos para un mismo objeto.      
     */
public HashMap<Integer,Certificado> getMapValidos(Integer id_modulo){
        HashMap<Integer,Certificado> mapa = new HashMap<Integer,Certificado>();
         String query = String.format("select * from certificado\n" +
                                        " where certificado.id in (select max(id)\n" +
                                        "         from certificado\n" +
                                        "        where certificado.id_modulo = %d " +
                                        "          and archivo <> '' \n" +
                                        "          and fecha_desde <= curdate()\n" +
                                        "        group by id_objeto);",id_modulo );
       //  System.out.println(query);
        List<Certificado> lstCertificados = this.getList(query);
        if (lstCertificados==null) return mapa;
        
        for(Certificado c:lstCertificados){
            mapa.put(c.getId_objeto(), c);
        }
        return mapa;
    }
//    public HashMap<Integer,Certificado> getMapValidos(){
//        HashMap<Integer,Certificado> mapa = new HashMap<Integer,Certificado>();
//         String query = String.format("select * from certificado\n" +
//        " where certificado.id_resultado = %d " +
//        " and certificado.fecha_desde <= curdate()" +
//        " and certificado.fecha_hasta >= CURDATE()",OptionsCfg.CERTIFICADO_APTO);
//        List<Certificado> lstCertificados = this.getList(query);
//        if (lstCertificados==null) return mapa;
//        
//        for(Certificado c:lstCertificados){
//            mapa.put(c.getId_objeto(), c);
//        }
//        return mapa;
//    }
    /*
       * Actualiza el resultado de los certificados que ya vencieron
       * Se considera como vencido los certificados con fecha hasta anterior a la fecha actual
       * y los que no tengan archivo de imagen 
    */
    public void vencerCertificados(){           
        String query = "update certificado\n" +
                       "   set id_resultado = %d\n" +
                       " where certificado.id_resultado = 1" + 
                       "  and certificado.fecha_hasta < CURDATE()" + 
                       "   or (certificado.fecha_desde > CURDATE() and certificado.archivo_url = '')";
        query = String.format(query,OptionsCfg.CERTIFICADO_VENCIDO);
        //System.out.println(query);
        conexion.conectarse();
        conexion.EjecutarInsert(query);
        conexion.desconectarse();
    }
    /*
     * Dada la fecha desde y hasta de un certificadfo, calcula el resultado con respecto a la fecha del sistema.
     * Si la fecha actual esta dentro de las fesde y hasta del certificado, devuelve válido (1)
     * si la fecha actual es antes de la fecha desde o despues de la fecha hasta, se considera vencido (2)
     * Si el resultado del certificado es distinto de Apto / Vencido, se mantiene sin considerar la fechas
     */
    public int calcularId_resultado(Certificado certificado){
        Integer id_resultado;
        if (certificado ==null) return 0;
        
        id_resultado = certificado.getId_resultado();
        if(id_resultado!=OptionsCfg.CERTIFICADO_APTO && id_resultado!= OptionsCfg.CERTIFICADO_VENCIDO) return id_resultado;
        
        LocalDate fecha_desde = new LocalDate(certificado.getFecha_desde());
        LocalDate fecha_hasta = new LocalDate(certificado.getFecha_hasta());
        LocalDate fecha_hoy = new LocalDate();
        if ((fecha_desde.isBefore(fecha_hoy) || fecha_desde.isEqual(fecha_hoy)) && (fecha_hasta.isAfter(fecha_hoy) || fecha_hasta.isEqual(fecha_hoy))
                && !certificado.getArchivo_url().equals("")) id_resultado =  OptionsCfg.CERTIFICADO_APTO;
        else id_resultado = OptionsCfg.CERTIFICADO_VENCIDO;
        return id_resultado;
//        if (fecha_desde.isAfter(fecha_hoy) || fecha_hasta.isBefore(fecha_hoy)
//                /*|| certificado.getArchivo_url().equals("")*/) return OptionsCfg.CERTIFICADO_VENCIDO;
//        else return OptionsCfg.CERTIFICADO_APTO;
    }
    public static void main(String[] args){
//      new TCertificado().vencerCertificados();
        
        TCertificado tc = new TCertificado();
        HashMap<Integer, Certificado> mapValidos = tc.getMapValidos(1);
        //System.out.println(mapValidos.get(717));
    }

    public Certificado getValido(Integer id_modulo,Integer id_objeto) {
        String query = "select * from certificado where certificado.id_modulo = %d " +
                       "and certificado.id_objeto = %d " + 
                       "and certificado.id_resultado /* and certificado.fecha_desde <= curdate() and certificado.fecha_hasta >= curdate() */ ";
        query = String.format(query,id_modulo,id_objeto,OptionsCfg.CERTIFICADO_APTO);        
        return this.getById(query);
    }
}
