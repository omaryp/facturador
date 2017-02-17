package pe.tallanes.sunat.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Omar Yarleque
 */

public class Cadena {

	public static String completar(String sCadena, int total,String sCadenaRelleno, boolean derecha) {
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
	
	public static String formatoDate(Date pFecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		return formato.format(pFecha);
	}

	public static String formatoSerie(int serie,int tipoComprobante) {
		StringBuilder ser = new StringBuilder();
		switch (tipoComprobante) {
			case 1:
			case 7:
			case 8:
				ser.append("F");
				break;
			case 3:
				ser.append("B");
				break;
		}
		ser.append(completar(String.valueOf(serie), 3, "0", false));
		return ser.toString();
	}
	
	public static int[] separarFecha(Date fecha){
		int [] dateInt = new int[3];
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha); // fecha es el Date de antes.
		dateInt[0] = calendario.get(Calendar.YEAR);
		dateInt[1] = calendario.get(Calendar.MONTH)+1;
		dateInt[2] = calendario.get(Calendar.DAY_OF_MONTH);
		return dateInt;
	}

}