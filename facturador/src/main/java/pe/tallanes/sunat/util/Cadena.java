package pe.tallanes.sunat.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Omar Yarleque
 */

public class Cadena {

    public static String completar(String sCadena, int total, String sCadenaRelleno, boolean derecha) {
        String x = sCadena;
        if (x.length() >= total) {
            x = sCadena.substring(0, total);
        } else {
            while (x.length() < total) {
                if (derecha) {
                    x = x + sCadenaRelleno;
                } else {
                    x = sCadenaRelleno + x;
                }
            }
        }
        return x;
    }
    
    public static String formatoFecha(Date pFecha) {	
    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(pFecha);
    }
    
}