package pe.tallanes.sunat.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.ComprobanteFsDao;
import pe.tallanes.sunat.model.ComprobanteFs;
import pe.tallanes.sunat.model.ComprobanteFsDto;
import pe.tallanes.sunat.model.Usuario;
import pe.tallanes.sunat.util.Constante;
import pe.tallanes.sunat.util.FacturadorSunat;
import pe.tallanes.sunat.util.Message;
import pe.tallanes.sunat.util.controller.InitController;


@ManagedBean
@ViewScoped
public class ConsultaFsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaFsController.class);

	@ManagedProperty(value = "#{InitController}")
	private InitController initController;
	private int estado;
	private boolean historico;
	private String serie;
	private String numero;
	private List<ComprobanteFs> comprobantes;
	private LazyDataModel<ComprobanteFs> modelComprobanteFs;
	private Usuario user;
	private int primero;
	private int ultimo;
	private Map<String,Object> parametros;
	
	public ConsultaFsController(){}
	
	@PostConstruct
	public void cargaParametros(){
		user = (Usuario)initController.getSessionVars().get("USUARIO");
		estado = 1;
		historico = false;
		serie = "";
		numero = "";
		cargarComprobantes();
	}
	
	public void loadLazymodelComprobantes() {
		modelComprobanteFs = new LazyDataModel<ComprobanteFs>() {
			private static final long serialVersionUID = 1L;
			
			@Override
            public void setRowIndex(int rowIndex) {
                try {
                    super.setRowIndex(rowIndex);
                } catch (ArithmeticException e) {
                	LOGGER.error("Error al Dividir {0}",e);
                }
            }
			
			@Override
		    public List<ComprobanteFs> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				primero = first;
                ultimo = pageSize + first;
                loadData();
                LOGGER.info( "primero {0}, ultimo {1}", new Object[]{primero, ultimo});
                return comprobantes;
		    }
			
		};
    }
	
	@SuppressWarnings("unchecked")
	public void loadData() {
		int count;
        int[] limites = new int[2];
        Map<String, Object> map = null;
        limites[0] = primero;
        limites[1] = ultimo;
        try {
			map = ComprobanteFsDao.getInstance().listarComprobantesFs(parametros,limites);
        	count = (Integer) map.get("TOTAL");
            if (count != 0) {
				comprobantes = (List<ComprobanteFs>) map.get("BANDEJA");
                LOGGER.info( "Número de registros obtenidos  {0}", count);
	            modelComprobanteFs.setRowCount(count);              
            }else{
            	Message.addAviso(null, "No se encontraron coincidencias!!");
            }
        } catch (Exception ex) {
            LOGGER.error(null, ex);
        }
	}
	
	public void cargarComprobantes(){
		parametros = new HashMap<String,Object>();
		parametros.put("SERIE", serie);
		parametros.put("NUMERO", numero);
		parametros.put("HISTORICO", historico);
		parametros.put("ESTADO",(estado==1)?Constante.ENVIADOS:Constante.NO_ENVIADOS);
		loadLazymodelComprobantes();
	}
	
	public void seleccionarEstado(){
		cargarComprobantes();
	}
	
	public void limpiarBandeja(){
		List<ComprobanteFsDto> comprobantes_enviados = ComprobanteFsDao.getInstance().listarComprobantesEnviadosFs();
		FacturadorSunat.getInstance().limpiarArchivosBandejaFs(comprobantes_enviados);
		ComprobanteFsDao.getInstance().enviarComprobanteFsHistorico();
		ComprobanteFsDao.getInstance().eliminarComprobantesFs(FacturadorSunat.getEliminados());
		Message.addAviso(null, "Se Limpió bandeja con éxito !!");
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public InitController getInitController() {
		return initController;
	}

	public void setInitController(InitController initController) {
		this.initController = initController;
	}

	public List<ComprobanteFs> getcomprobantes() {
		return comprobantes;
	}

	public void setcomprobantes(List<ComprobanteFs> comprobantes) {
		this.comprobantes = comprobantes;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LazyDataModel<ComprobanteFs> getModelComprobanteFs() {
		return modelComprobanteFs;
	}

	public void setModelComprobanteFs(LazyDataModel<ComprobanteFs> modelComprobanteFs) {
		this.modelComprobanteFs = modelComprobanteFs;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
}
