package pe.tallanes.sunat.util.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pe.tallanes.sunat.model.Usuario;


@ManagedBean(name = "InitController",eager = true)
@SessionScoped
public class InitController implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private Map<String, Object> sessionVars;
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarMenuController.class);
    
    public InitController() {
        sessionVars = new HashMap<String, Object>();        
    }
    
    @PostConstruct
    public void init(){
    	try {
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	Usuario user = (Usuario) authentication.getPrincipal();
            sessionVars.put("USUARIO", user);
		} catch (Exception e) {
			LOGGER.error("Error : ",e);
		}
    }

    public Map<String, Object> getSessionVars() {
        return sessionVars;
    }

    public void setSessionVars(Map<String, Object> sessionVars) {
        this.sessionVars = sessionVars;
    }
}