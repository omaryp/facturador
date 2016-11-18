package pe.tallanes.sunat.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Omar Yarleque
 *
 */
public class Params {

	private static final Logger LOGGER = LoggerFactory.getLogger(Params.class);
    public static String ip = null;
    public static int port = 0;
    public static String bd = null;
    public static String user = null;
    public static String pass = null;
    public static String ruta = null;
    
    public static Properties configuracion;

    static {
    	try {
    		configuracion = new Properties();
            configuracion.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator")+"facturador.properties"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
    }

    public Params() {}

    public static void cargaParametros() {
    	LOGGER.info("Cargando parametros de configuración....");
        ip = getProperty("HOST");
        port = Integer.parseInt(getProperty("PORT"));
        bd = getProperty("BD");
        user = getProperty("USER");
        pass = getProperty("PASS");
        ruta = getProperty("RUTA");
        LOGGER.info("Parametros de configuración cargados exitosamente.");
    }

    private static String getProperty(String key) {
        return configuracion.getProperty(key);
    }
}
