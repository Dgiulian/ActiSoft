/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import bd.Certificado;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.OptionsCfg;

/**
 *
 * @author Diego
 */
public class TCertificadoTest {
    
    public TCertificadoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcularId_resultado method, of class TCertificado.
     */
    @Test
    public void testCalcularId_resultadoVencido_Apto() {
        System.out.println("calcularId_resultado Apto");
        Certificado certificado = new Certificado();
        certificado.setArchivo_url("url");
        certificado.setFecha_desde("2016-05-01");
        certificado.setFecha_hasta("2016-08-30");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_VENCIDO);
        TCertificado instance = new TCertificado();
        int expResult = OptionsCfg.CERTIFICADO_APTO;
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
       
    }
    /**
     * Test of calcularId_resultado method, of class TCertificado.
     */
    @Test
    public void testCalcularId_resultadoApto_Apto() {
        System.out.println("calcularId_resultado Apto");
        Certificado certificado = new Certificado();
        certificado.setArchivo_url("url");
        certificado.setFecha_desde("2016-05-01");
        certificado.setFecha_hasta("2016-08-30");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_APTO);
        TCertificado instance = new TCertificado();
        int expResult = OptionsCfg.CERTIFICADO_APTO;
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
       
    }
     @Test
    public void testCalcularId_resultadoApto_Vencido() {
        System.out.println("calcularId_resultado Apto");
        Certificado certificado = new Certificado();
        certificado.setArchivo_url("url");
        certificado.setFecha_desde("2016-05-01");
        certificado.setFecha_hasta("2016-05-31");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_APTO);
        TCertificado instance = new TCertificado();
        int expResult = OptionsCfg.CERTIFICADO_VENCIDO;
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
      
    }
    @Test
    public void testCalcularId_resultadoVencido_Vencido() {
        System.out.println("calcularId_resultado Apto");
        Certificado certificado = new Certificado();
        certificado.setArchivo_url("url");
        certificado.setFecha_desde("2016-01-01");
        certificado.setFecha_hasta("2016-04-30");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_VENCIDO);
        TCertificado instance = new TCertificado();
        int expResult = OptionsCfg.CERTIFICADO_VENCIDO;
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
        
    }
//     /**
//     * Test of calcularId_resultado method, of class TCertificado.
//     */
//    @Test
//    public void testCalcularId_resultadoSinArchivo() {
//        System.out.println("calcularId_resultado");
//        Certificado certificado = new Certificado();
//        TCertificado instance   = new TCertificado();
//        int expResult = 0;
//        int result = instance.calcularId_resultado(certificado);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    @Test
    public void testCalcularId_resultadoNoApto_Vencido() {
        System.out.println("calcularId_resultado");
        Certificado certificado = new Certificado();
        certificado.setArchivo_url("url");
        certificado.setFecha_desde("2016-01-01");
        certificado.setFecha_hasta("2016-04-30");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_NO_APTO);
        TCertificado instance = new TCertificado();
        int expResult = OptionsCfg.CERTIFICADO_NO_APTO;
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);        
    }
    @Test
    public void testCalcularId_resultadoNoApto_Apto() {
        System.out.println("calcularId_resultado");
        Certificado certificado = new Certificado();
        certificado.setFecha_desde("2016-01-01");
        certificado.setFecha_hasta("2016-12-31");
        certificado.setArchivo_url("url");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_NO_APTO);
        TCertificado instance = new TCertificado();        
        int expResult = OptionsCfg.CERTIFICADO_NO_APTO;        
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
    }
      @Test
    public void testCalcularId_resultadoApto_Sin_Archivo() {
        System.out.println("calcularId_resultado");
        Certificado certificado = new Certificado();
        certificado.setFecha_desde("2016-01-01");
        certificado.setFecha_hasta("2016-12-31");
        certificado.setArchivo_url("");
        certificado.setId_resultado(OptionsCfg.CERTIFICADO_APTO);
        TCertificado instance = new TCertificado();        
        int expResult = OptionsCfg.CERTIFICADO_VENCIDO;        
        int result = instance.calcularId_resultado(certificado);
        assertEquals(expResult, result);
    }
}