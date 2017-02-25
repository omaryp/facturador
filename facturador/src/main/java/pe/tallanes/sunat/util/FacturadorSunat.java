package pe.tallanes.sunat.util;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.tallanes.sunat.model.ComprobanteFsDto;

public class FacturadorSunat {
	private static final Logger LOGGER = LoggerFactory.getLogger(FacturadorSunat.class);
	private static FacturadorSunat txt = null;
	private static List<String> deletes = new ArrayList<String>();
	private static List<String> origen ;
	private static List<String> destino ;
	
	private FacturadorSunat() {
	}

	public static FacturadorSunat getInstance() {
		if (txt == null) {
			txt = new FacturadorSunat();
		}
		return txt;
	}
	
	public static List<String> getEliminados(){
		return deletes;
	}

	public void limpiarArchivosBandejaFs(List<ComprobanteFsDto> lista) {
		try {
			for (ComprobanteFsDto fsDto : lista) {
				getDatosArchivos(fsDto);
				int i = 0;
				for (String rutaOrigen : origen)
					Archivo.getInstance().moveFile(rutaOrigen, destino.get(i++));
			}
		} catch (Exception e) {
			LOGGER.error("Error al generar archivo de texto.", e);
		}
	}

	public void getDatosArchivos(ComprobanteFsDto fsDto) {
		origen = new ArrayList<String>();
		destino = new ArrayList<String>();
		String pathOrigin = Params.ruta;
		String pathDestino = Params.rutaDestino;
		origen.add(completarRuta(pathOrigin,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.CABECERA.value()));
		origen.add(completarRuta(pathOrigin,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.DETALLE.value()));
		origen.add(completarRuta(pathOrigin,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.NOTAS.value()));
		destino.add(completarRuta(pathDestino,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.CABECERA.value()));
		destino.add(completarRuta(pathDestino,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.DETALLE.value()));
		destino.add(completarRuta(pathDestino,System.getProperty("file.separator"),fsDto.getNombreArchivo(),Extension.NOTAS.value()));
		generarScriptDelete(fsDto);
	}
	
	public String completarRuta(String...datos){
		StringBuilder ruta = new StringBuilder();
		ruta.append(datos[0]).append(datos[1]).append(datos[2]).append(datos[3]);
		return ruta.toString();
	}
	
	public void generarScriptDelete(ComprobanteFsDto fsDto){
		StringBuilder sql = new StringBuilder();
		sql.append("Delete from TXXXX_BANDFACT Where ");
		sql.append("NUM_RUC = '");
		sql.append(fsDto.getId().getRuc());
		sql.append("' and TIP_DOCU = '");
		sql.append(fsDto.getId().getTipoDocumento());
		sql.append("' and NUM_DOCU = '");
		sql.append(fsDto.getId().getNumeroDocumento());
		deletes.add(sql.toString());
	}
	
}
