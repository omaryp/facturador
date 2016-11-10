/**
 * 
 */
package pe.tallanes.sunat.util;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Omar Yarleque
 *
 */
public class ApplicationException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message){
		super(message);
	}
}
