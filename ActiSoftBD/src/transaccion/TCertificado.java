/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Certificado;
import java.util.List;

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
        String query = "select * from certificado\n" +
        " where certificado.id_activo = %d\n" +
        " and certificado.id_resultado = 1 " +
        " and certificado.fecha_vigencia >= CURDATE()";
        return super.getById(String.format(query,id_activo));
    }
    public static void main(String[] args){
        Certificado certificado = new TCertificado().getById(558);
        System.out.println(certificado.getFecha_vigencia());
        System.out.println(certificado.getFecha_efectiva());
    }
}
