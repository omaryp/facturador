/**
 * 
 */
package pe.tallanes.sunat.model;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;



/**
 * @author Omar Yarleque 
 *
 */
public class Usuario extends User {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private int tipoUsuario;

    public Usuario(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
    
}
