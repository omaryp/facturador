package pe.tallanes.sunat.util.json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.json.CabeceraDao;
import pe.tallanes.sunat.dao.json.DetalleDao;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.json.Cabecera;
import pe.tallanes.sunat.model.json.Detalle;
import pe.tallanes.sunat.util.Cadena;
import pe.tallanes.sunat.util.Extension;
import pe.tallanes.sunat.util.Params;

public class GenerarTxt {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarTxt.class);
	private static GenerarTxt txt = null;
	private GenerarTxt(){} 	
	
	public static GenerarTxt getInstance(){
		if(txt == null){
			txt = new GenerarTxt();
		}
		return txt;
	}
	
	public boolean toTxt(List<ComprobantePk> lista,int tipoComprobante){
		try {	
			for (ComprobantePk id : lista) {
				Cabecera cab = CabeceraDao.getInstance().getCabecera(id);
				String name = nombreArchivo(id, cab, tipoComprobante);
				String ruta = getRutaArchivo(name, Extension.CABECERA.value());
				List<String> cadenas = new ArrayList<String>();
				cadenas.add(cab.toString());
				crearArchivo(ruta, cadenas);
				
				cadenas.clear();
				List<Detalle> detalle = DetalleDao.getInstance().listarDetalle(id);
				for (Detalle det : detalle) {
					cadenas.add(det.toString());
				}
				ruta = getRutaArchivo(name, Extension.DETALLE.value());
				crearArchivo(ruta, cadenas);
				
				//GenerarJson.getInstance().toJsonFile(sunat,ruta);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al generar formato Json.", e);
		}
		return false;
	}
	
	public String nombreArchivo(ComprobantePk id,Cabecera cab,int tipoComprobante){
		StringBuilder fileName = new StringBuilder();
		fileName.append(cab.getNumDocUsuario());
		fileName.append("-");
		fileName.append(Cadena.completar(String.valueOf(tipoComprobante), 2, "0", false));
		fileName.append("-");
		fileName.append(Cadena.completar(String.valueOf(id.getSerie()), 4, "0", false));
		fileName.append("-");
		fileName.append(Cadena.completar(id.getNumero(), 8, "0", false));
		return fileName.toString();
	}
	
	public String getRutaArchivo(String fileName,String ext){
		StringBuilder ruta = new StringBuilder();
		ruta.append(Params.ruta);
		ruta.append(System.getProperty("file.separator"));
		ruta.append(fileName);
		ruta.append(".");
		ruta.append(ext);
		return ruta.toString();
	}
	
	public void crearArchivo(String ruta,List<String> cadenas){
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(ruta));
			for (String str : cadenas) {
				out.write(str);
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			LOGGER.error("Error al generar archivo de texto.", e);
		}
	}
	
}
