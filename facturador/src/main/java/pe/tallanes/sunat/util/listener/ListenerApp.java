package pe.tallanes.sunat.util.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.util.Params;

public final class ListenerApp implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerApp.class);
    
    public ListenerApp() {

    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        try {    	
        	LOGGER.info("Iniciando el Pool de Conexiones");
        	Params.cargaParametros();
	        Conexiones.iniciarPool(Params.user, Params.pass);
	        LOGGER.info("Se termino de iniciar el Pool de Conexiones");
        } catch (Exception e) {
        	LOGGER.info("Error : ",e);
        }
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        Conexiones.detener();        
    }

} 