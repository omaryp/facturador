package pe.tallanes.sunat.util.json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.json.CabeceraDao;
import pe.tallanes.sunat.dao.json.CabeceraNotaDao;
import pe.tallanes.sunat.dao.json.DetalleDao;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.json.Cabecera;
import pe.tallanes.sunat.model.json.CabeceraNota;
import pe.tallanes.sunat.model.json.Detalle;
import pe.tallanes.sunat.util.Cadena;
import pe.tallanes.sunat.util.Extension;
import pe.tallanes.sunat.util.Params;

public class GenerarTxt {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GenerarTxt.class);
	private static GenerarTxt txt = null;

	private GenerarTxt() {
	}

	public static GenerarTxt getInstance() {
		if (txt == null) {
			txt = new GenerarTxt();
		}
		return txt;
	}

	public boolean toTxt(List<ComprobantePk> lista, int tipoComprobante) {
		try {
			for (ComprobantePk id : lista) {
				ComprobantePk notaModifica = null;
				String name = nombreArchivo(id, tipoComprobante);
				String ruta = "";
				List<String> cadenas = new ArrayList<String>();
				switch (tipoComprobante) {
					case 1:
					case 3:
						Cabecera cab = CabeceraDao.getInstance().getCabecera(id);
						cadenas.add(cab.toString());
						ruta = getRutaArchivo(name, Extension.CABECERA.value());
						break;
					case 7:
					case 8:
						CabeceraNota cabn = CabeceraNotaDao.getInstance().getCabeceraNota(id);
						notaModifica = CabeceraNotaDao.getInstance().getDocumentoModifica(id);
						ruta = getRutaArchivo(name, Extension.NOTAS.value());
						cadenas.add(cabn.toString());
						break;
				}
				crearArchivo(ruta, cadenas);
				cadenas.clear();
				List<Detalle> detalle = DetalleDao.getInstance().listarDetalle((notaModifica == null )?id:notaModifica);
				for (Detalle det : detalle) {
					cadenas.add(det.toString());
				}
				ruta = getRutaArchivo(name, Extension.DETALLE.value());
				crearArchivo(ruta, cadenas);

				// GenerarJson.getInstance().toJsonFile(sunat,ruta);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al generar archivo de texto.", e);
		}
		return false;
	}

	public String nombreArchivo(ComprobantePk id,int tipoComprobante) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(Params.ruc);
		fileName.append("-");
		fileName.append(Cadena.completar(String.valueOf(tipoComprobante), 2,"0", false));
		fileName.append("-");
		fileName.append(id.getSerie());
		fileName.append("-");
		fileName.append(Cadena.completar(id.getNumero(), 8, "0", false));
		return fileName.toString();
	}

	public String getRutaArchivo(String fileName, String ext) {
		StringBuilder ruta = new StringBuilder();
		ruta.append(Params.ruta);
		ruta.append(System.getProperty("file.separator"));
		ruta.append(fileName);
		ruta.append(".");
		ruta.append(ext);
		return ruta.toString();
	}

	public void crearArchivo(String ruta, List<String> cadenas) {
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
