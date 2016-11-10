package pe.tallanes.sunat.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pe.tallanes.sunat.dao.UsuarioDao;
import pe.tallanes.sunat.model.Usuario;
import pe.tallanes.sunat.util.ApplicationException;



/**
 * @author Omar Yarleque
 *
 */
public class WebServiceAuthenticationProvider implements AuthenticationProvider {

    public WebServiceAuthenticationProvider() {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user = authentication.getName();
        try {
        	if(!UsuarioDao.getInstance().existeUsuario(user)){
        		throw new ApplicationException(user+" no tienes permisos en la base de datos.");
        	}
        	if(!UsuarioDao.getInstance().validarUsuario(user, authentication.getCredentials().toString())){
        		throw new BadCredentialsException("Usuario o Contraseña inválidos");
        	}
        } catch (NullPointerException exception) {
            throw new ProviderNotFoundException("No se encontro el Servidor.");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority ga = new SimpleGrantedAuthority("PERFIL_TEST");
        authorities.add(ga);
        Usuario sesion = new Usuario(user, authentication.getCredentials().toString(),authorities);
        Map<String, String> datos_usuario  = UsuarioDao.getInstance().getDatosUsuario(user);
        if(datos_usuario.isEmpty())
        	throw new ApplicationException("Error al cargar datos del usuario");
        sesion.setNombre(datos_usuario.get("NOMBRE"));        
        sesion.setTipoUsuario(Integer.parseInt(datos_usuario.get("TIPO").trim()));
        return new UsernamePasswordAuthenticationToken(sesion, authentication.getCredentials(), authorities);
    }
    
    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
