package pe.tallanes.sunat.util.json;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerarJson {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarJson.class);
	private static GenerarJson dao = null;
	private static ObjectMapper gson;
	private GenerarJson(){}
	
	public static GenerarJson getInstance(){
		if(dao == null){
			dao = new GenerarJson();
			gson = new ObjectMapper();
		}
		return dao;
	}
	
	public void toJsonFile(Object obj,String file){
		try {
			File out = new File(file);
			gson.writeValue(out, obj);
		} catch (Exception e) {
			LOGGER.error("Error al generar formato",e);
		}
	}
}
