package pe.tallanes.sunat.util;

import org.primefaces.context.RequestContext;

/**
 * Clase permite permite ejecutar js en el contexto
 * @author Omar Yarleque 
 */

public final class Js {
	
	/**
	 * Actualiza cualquier componente del DOM
	 * @param detail --> Id del DOM a actualizar
	 */
	public static void update(String id) {
		RequestContext.getCurrentInstance().update(id);
    }
    
    /**
	 * Ejecuta funciones js
	 * @param detail --> función a ejecutar
	 */
    public static void execute(String detail) {
    	RequestContext.getCurrentInstance().execute(detail);
    }
}
