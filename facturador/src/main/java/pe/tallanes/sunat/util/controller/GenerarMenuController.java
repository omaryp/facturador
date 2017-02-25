package pe.tallanes.sunat.util.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.model.Usuario;

/**
 * @author Omar Yarleque
 *
 */
@ManagedBean
@ViewScoped
public class GenerarMenuController implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarMenuController.class);

	@ManagedProperty(value = "#{InitController}")
	private InitController initController;
	private static final long serialVersionUID = 1L;
	private Usuario userIniSesion;
	private MenuModel menuModelBean;
	private String fecha;

	public GenerarMenuController() {
	}

	@PostConstruct
	public void init() {
		LOGGER.info("Entrando al postConstrut menu ....");
		userIniSesion = (Usuario) getInitController().getSessionVars().get("USUARIO");
		formateaFecha();
	}

	public void formateaFecha() {
		Date now = new Date();
		DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
		fecha = df4.format(now);
	}

	public MenuModel getMenuModelBean() {
		if (menuModelBean == null) {
			menuModelBean = new DefaultMenuModel();
			DefaultMenuItem menuItem = new DefaultMenuItem("Inicio");
			menuItem.setUrl("inicio.do");
			menuItem.setId("menu");
			menuModelBean.addElement(menuItem);
			DefaultMenuItem menuItem1 = new DefaultMenuItem("Comprobantes");
			menuItem1.setUrl("comprobantes.do");
			menuItem1.setId("menu01");
			menuModelBean.addElement(menuItem1);
			DefaultMenuItem menuItem2 = new DefaultMenuItem("Consulta Facturador Sunat");
			menuItem2.setUrl("consulta.do");
			menuItem2.setId("menu02");
			menuModelBean.addElement(menuItem2);
		}
		return menuModelBean;
	}

	public InitController getInitController() {
		return initController;
	}

	public void setInitController(InitController initController) {
		this.initController = initController;
	}

	public void setMenuModelBean(MenuModel menuModelBean) {
		this.menuModelBean = menuModelBean;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Usuario getUserIniSesion() {
		return userIniSesion;
	}

	public void setUserIniSesion(Usuario userIniSesion) {
		this.userIniSesion = userIniSesion;
	}
	
}