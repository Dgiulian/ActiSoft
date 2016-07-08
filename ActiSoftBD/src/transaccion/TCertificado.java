/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Certificado;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import utils.OptionsCfg;

public class TCertificado extends TransaccionBase<Certificado>{
    public List<Certificado>getList(){
        return super.getList("select * from certificado");
    }
    public List<Certificado>getByActivoId(Integer id_activo){
        return super.getList("select * from certificado where certificado.id_activo = " + id_activo);
    }
    public Certificado getById(Integer id){
        String query = String.format("select * from certificado where certificado.id = %d",id);        
        return super.getById(query);
    }
    public boolean actualizar(Certificado certificado){
        return super.actualizar(certificado, "id");
    }
    public Certificado getVigente(Integer id_activo){
        /* Fecha desde: Desde */
        /* Fecha hasta: Hasta */
        String query = String.format("select * from certificado\n" +
        " where certificado.id_activo = %d\n" +
        " and certificado.id_resultado = %d " +
        " and certificado.fecha_desde <= curdate()" +
        " and certificado.fecha_hasta >= CURDATE()" +
        " and certificado.archivo_url <> '' ",id_activo,OptionsCfg.CERTIFICADO_APTO);
        return super.getById(query);
    }
    public HashMap<Integer,Certificado> getMapValidos(){
        HashMap<Integer,Certificado> mapa = new HashMap<Integer,Certificado>();
         String query = String.format("select * from certificado\n" +
        " where certificado.id_resultado = %d " +
        " and certificado.fecha_desde <= curdate()" +
        " and certificado.fecha_hasta >= CURDATE()" +
        " and certificado.archivo_url <> '' ",OptionsCfg.CERTIFICADO_APTO);
        List<Certificado> lstCertificados = this.getList(query);
        if (lstCertificados==null) return mapa;
        
        for(Certificado c:lstCertificados){
            mapa.put(c.getId_activo(), c);
        }
        return mapa;
    }
    public void vencerCertificados(){
        /*
         * Actualiza el resultado de los certificados que ya vencieron
         * Se considera como vencido los certificados con fecha hasta anterior a la fecha actual
         * y los que no tengan archivo de imagen 
         */
        String query = "update certificado\n" +
                       "   set id_resultado = %d\n" +
                       " where certificado.fecha_hasta < CURDATE()" + 
                       "   or (certificado.fecha_desde >= CURDATE() and certificado.archivo_url = '')";
        query = String.format(query,OptionsCfg.CERTIFICADO_VENCIDO);
        System.out.println(query);
        conexion.conectarse();
        conexion.EjecutarInsert(query);
        conexion.desconectarse();
    }
    public static void main(String[] args){
        new TCertificado().vencerCertificados();
        
    }
}
