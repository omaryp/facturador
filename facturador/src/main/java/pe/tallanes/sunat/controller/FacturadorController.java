package pe.tallanes.sunat.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.UsuarioDao;
import pe.tallanes.sunat.model.Comprobante;
import pe.tallanes.sunat.model.Usuario;
import pe.tallanes.sunat.util.controller.InitController;

public class FacturadorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{InitController}")
	private InitController initController;
	private List<FormatoPaseJudicialDTO> formatosSect;
	private LazyDataModel<FormatoPaseJudicialDTO> modelFormato;
	private Usuario user;
	private short estado;
	private int primero;
	private int ultimo;
	private long nroForSel;
	
	private static final Logger logger = Logger.getLogger(FormatoPaseJudicialDAO.class.getName());
	
	public BandejaSectoristaController(){
		
	}
	@PostConstruct
	public void cargaParametros(){
		setUser((Usuario)initController.getSessionVars().get("USUARIO"));
		loadLazyModelFormatos();
	}
	
	public void loadLazyModelFormatos() {
		modelFormato = new LazyDataModel<FormatoPaseJudicialDTO>() {
			private static final long serialVersionUID = 1L;
			@Override
            public void setRowIndex(int rowIndex) {
                try {
                    super.setRowIndex(rowIndex);
                } catch (ArithmeticException e) {
                	logger.log(Level.SEVERE, "Error al Dividir {0}",e);
                }
            }

            @Override
            public List<FormatoPaseJudicialDTO> load(int first, int pageSize, String string, SortOrder so, Map<String, String> map) {
                primero = first;
                ultimo = pageSize + first;
                loadData();
                logger.log(Level.INFO, "primero {0}, ultimo {1}", new Object[]{primero, ultimo});
                return formatosSect;
            }
        };
    }
	
	public void loadData() {
        int[] limites = new int[2];
        Map<String, Object> map = null;
        limites[0] = primero;
        limites[1] = ultimo;
        int count;
        try {
        	map = DelegateCOBRA.INSTANCE.getFormatos(user.getUsuario(),(short) 0,limites);     
            if (!map.isEmpty()) {
				formatosSect = (List<FormatoPaseJudicialDTO>) map.get("BANDEJA");                
                count = (Integer) map.get("TOTAL");
                logger.log(Level.INFO, "Número de registros obtenidos  {0}", count);
	            modelFormato.setRowCount(count);              
            }else{
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se encontraron coincidencias!!", ""));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
	}
		
	public void actualizaTabla(AjaxBehaviorEvent event) {
		loadLazyModelFormatos();		
	}
	
	public void visualizarFormato(){
		try {
			String url = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestContextPath();
			url = url + "/paseJudicial/frmFormatoPaseJudicial.php";
			// guardamos el codigo del cliente
			initController.getSessionVars().put("FORMATO",DelegateCOBRA.INSTANCE.getFormato(getNroForSel()));
			initController.getSessionVars().put("CODCLI", 0);
			if(getEstado()==1)
				initController.getSessionVars().put("ACCION", 1);
			else
				initController.getSessionVars().put("ACCION", 0);
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "No se pudo redireccionar");
		}
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public short getEstado() {
		return estado;
	}

	public void setEstado(short estado) {
		this.estado = estado;
	}

	public long getNroForSel() {
		return nroForSel;
	}

	public void setNroForSel(long nroForSel) {
		this.nroForSel = nroForSel;
	}

	public InitController getInitController() {
		return initController;
	}

	public void setInitController(InitController initController) {
		this.initController = initController;
	}

	public List<FormatoPaseJudicialDTO> getFormatosSect() {
		return formatosSect;
	}

	public void setFormatosSect(List<FormatoPaseJudicialDTO> formatosSect) {
		this.formatosSect = formatosSect;
	}
	
	public LazyDataModel<FormatoPaseJudicialDTO> getModelFormato() {
		return modelFormato;
	}
	
	public void setModelFormato(LazyDataModel<FormatoPaseJudicialDTO> modelFormato) {
		this.modelFormato = modelFormato;
	}
	
}
