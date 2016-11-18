package pe.tallanes.sunat.controller;

import java.io.Serializable;
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

import pe.tallanes.sunat.dao.DetalleComprobanteDao;
import pe.tallanes.sunat.model.Comprobante;
import pe.tallanes.sunat.model.DetalleComprobante;
import pe.tallanes.sunat.model.Usuario;
import pe.tallanes.sunat.util.Js;
import pe.tallanes.sunat.util.Message;
import pe.tallanes.sunat.util.controller.InitController;


@ManagedBean
@ViewScoped
public class DetalleComprobanteController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DetalleComprobanteController.class);

	@ManagedProperty(value = "#{InitController}")
	private InitController initController;
	private Comprobante seleccionado;
	private List<DetalleComprobante> detalleComprobantes;
	private LazyDataModel<DetalleComprobante> modelDetalleComprobante;
	private Usuario user;
	private int primero;
	private int ultimo;
	
	public DetalleComprobanteController(){
		
	}
	
	@PostConstruct
	public void cargaParametros(){
	}
	
	public void cargarDatos(){
		seleccionado = (Comprobante)initController.getSessionVars().get("COMPROBANTE");
		if(seleccionado != null){
			loadLazymodelDetalleComprobantes();
			Js.execute("PF('dlg_detalle').show()");
			Js.update("ver_detalle");
		}
	}
	
	public void loadLazymodelDetalleComprobantes() {
		modelDetalleComprobante = new LazyDataModel<DetalleComprobante>() {
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
		    public List<DetalleComprobante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				primero = first;
                ultimo = pageSize + first;
                loadData();
                LOGGER.info( "primero {0}, ultimo {1}", new Object[]{primero, ultimo});
                return detalleComprobantes;
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
        	map = DetalleComprobanteDao.getInstance().listarDetalleComprobantes(seleccionado, limites);
        	count = (Integer) map.get("TOTAL");
            if (count != 0) {
				detalleComprobantes = (List<DetalleComprobante>) map.get("BANDEJA");
                LOGGER.info( "Número de registros obtenidos  {0}", count);
	            modelDetalleComprobante.setRowCount(count);              
            }else{
            	Message.addAviso(null, "No se encontraron coincidencias!!");
            }
        } catch (Exception ex) {
            LOGGER.error("Error al cargar datos en el LazyDataModel.", ex);
        }
	}
	
	public void salirDetalle(){
		seleccionado = null;
		Js.execute("PF('dlg_detalle').hide()");
	}
	
	public InitController getInitController() {
		return initController;
	}
	
	public void setInitController(InitController initController) {
		this.initController = initController;
	}
	
	public List<DetalleComprobante> getDetalleComprobantes() {
		return detalleComprobantes;
	}
	
	public void setDetalleComprobantes(List<DetalleComprobante> detalleComprobantes) {
		this.detalleComprobantes = detalleComprobantes;
	}
	
	public LazyDataModel<DetalleComprobante> getModelDetalleComprobante() {
		return modelDetalleComprobante;
	}
	
	public void setModelDetalleComprobante(
			LazyDataModel<DetalleComprobante> modelDetalleComprobante) {
		this.modelDetalleComprobante = modelDetalleComprobante;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Comprobante getSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(Comprobante seleccionado) {
		this.seleccionado = seleccionado;
	}
	
}