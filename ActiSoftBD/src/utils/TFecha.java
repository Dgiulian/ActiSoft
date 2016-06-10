/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Axioma
 */
public class TFecha {
    public static final String formatoBD = "yyyy-MM-dd";
    public static final String formatoVista = "dd/MM/yyyy";
    public static final String formatoHora = "HH:mm:ss";
    public static final String FECHA_DEFAULT = "0000-00-00";
    
    public static String formatearFecha(String fecha, String formatoviejo, String formatonuevo) {
        if (fecha.equals("")) return "";
        
        try {
            SimpleDateFormat sdfviejo = new SimpleDateFormat(formatoviejo);
            Date fechanueva = sdfviejo.parse(fecha);

            SimpleDateFormat sdfnuevo = new SimpleDateFormat(formatonuevo);
            return sdfnuevo.format(fechanueva);
        } catch (ParseException ex) {
            Logger.getLogger(TFecha.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public static String formatearFecha(Date fecha, String formatonuevo) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatonuevo);
        return sdf.format(fecha);
    }

    public static String convertirFecha(String fecha, String formatoViejo, String formatoNuevo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoViejo);

        if (fecha != null) {
            try {
                Date date = simpleDateFormat.parse(fecha);
                return new SimpleDateFormat(formatoNuevo).format(date);
            } catch (ParseException ex) {
                Logger.getLogger("TFecha").log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public static String ahora() {
        return ahora("yyyy-MM-dd hh:mm:ss");
    }

    public static String ahora(String formato) {
        Date fecha = new Date();
        return formatearFecha(fecha, formato);
    }

    public static int diferenciasDeFechas(String fechaInicial1) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicial = null;
        try {
            fechaInicial = df.parse(fechaInicial1);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        Date fechaFinal = null;
        String fechaFinalString = df.format(new Date());
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    public static int diferenciasDeFechas(String fechaInicial1, String fechahasta) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicial = null;        
        try {
            fechaInicial = df.parse(fechaInicial1);            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        Date fechaFinal = null;        
        try {
            fechaFinal = df.parse(fechahasta);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    public static boolean validaFecha(String StrFecNac, String formato) {

        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        try {
            sdf.parse(StrFecNac);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static Integer calcularEdad(String fecha) {
        Date fechaNac = null;
        try {
            /**
             * Se puede cambiar la mascara por el formato de la fecha que se
             * quiera recibir, por ejemplo año mes día "yyyy-MM-dd" en este caso
             * es día mes año
             */
            fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        //Se restan la fecha actual y la fecha de nacimiento
        int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if (mes < 0 || (mes == 0 && dia < 0)) {
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;
    }
    
    public static long sumarMinutos(String hora,int minutos){                
        try {
            Date date = new SimpleDateFormat("HH:mm").parse(hora);
            long time = date.getTime();
            time += minutos * 1000 * 60;
            return time;
        } catch (ParseException ex) {
            Logger.getLogger(TFecha.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }    
    }
    public static long convertirMS(String hora){
        try {
            return new SimpleDateFormat("HH:mm:ss").parse(hora).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(TFecha.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    public static List<Date> getListaDias(Date strDesde, Date strHasta,List filtro){
        ArrayList<Date> dias = new ArrayList();
        Calendar fecha = Calendar.getInstance();
        Calendar hasta  = Calendar.getInstance();        
        SimpleDateFormat sdf = new SimpleDateFormat(TFecha.formatoBD);    
        fecha.setTime(strDesde);
        hasta.setTime(strHasta);
        while(fecha.compareTo(hasta)<=0){                        
            if (filtro.contains(fecha.get(Calendar.DAY_OF_WEEK))){
                dias.add(fecha.getTime());
            }
            fecha.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dias;
    }
    public static String formatearHora(long time){
        return new SimpleDateFormat("HH:mm:ss").format(time);
    }
     
    public static String formatearFechaBdVista(String fecha){
        if (fecha!=null&&!fecha.equals(""))
            return TFecha.formatearFecha(fecha, TFecha.formatoBD, TFecha.formatoVista);
        else return null;
    }
     public static String formatearFechaVistaBd(String fecha){
        if (fecha!=null&&!fecha.equals(""))
            return TFecha.formatearFecha(fecha, TFecha.formatoVista, TFecha.formatoBD);
        else return null;
    }
}
