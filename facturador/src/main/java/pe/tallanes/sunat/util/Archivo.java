package pe.tallanes.sunat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archivo {
	private static final Logger LOGGER = LoggerFactory.getLogger(Archivo.class);
	private static Archivo file = null;
	private Archivo() {
	}

	public static Archivo getInstance() {
		if (file == null) {
			file = new Archivo();
		}
		return file;
	}
	
	public boolean moveFile(String fromFile, String toFile) {
	    File origin = new File(fromFile);
	    File destination = new File(toFile);
	    if (origin.exists()) {
	        try {
	            InputStream in = new FileInputStream(origin);
	            OutputStream out = new FileOutputStream(destination);
	            byte[] buf = new byte[1024];
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	            in.close();
	            out.close();
	            return origin.delete();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	            LOGGER.info("Error de entrada y salida .", ioe);
	            return false;
	        }
	    } else {
	    	LOGGER.info("Origen no existe.", fromFile);
	        return false;
	    }
	}
}
