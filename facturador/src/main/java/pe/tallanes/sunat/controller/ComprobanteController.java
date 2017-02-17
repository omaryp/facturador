package pe.tallanes.sunat.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.ComprobanteDao;
import pe.tallanes.sunat.dao.TipoComprobanteDao;
import pe.tallanes.sunat.model.Comprobante;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.TipoComprobante;
import pe.tallanes.sunat.model.Usuario;
import pe.tallanes.sunat.util.Js;
import pe.tallanes.sunat.util.Message;
import pe.tallanes.sunat.util.controller.InitController;
import pe.tallanes.sunat.util.json.GenerarTxt;


@ManagedBean
@ViewScoped
public class ComprobanteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComprobanteController.class);

	@ManagedProperty(value = "#{InitController}")
	private InitController initController;
	@ManagedProperty(value = "#{detalleComprobanteController}")
	private DetalleComprobanteController detalle;
	private int tipoSeleccionado;
	private int tipoBusqueda;
	private ComprobantePk idBusqueda;
	private Comprobante seleccionado;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Comprobante> comprobantes;
	private List<TipoComprobante> tipos;
	private LazyDataModel<Comprobante> modelComprobante;
	private Usuario user;
	private int primero;
	private int ultimo;
	private Map<String,Object> parametros;
	
	public ComprobanteController(){}
	
	@PostConstruct
	public void cargaParametros(){
		user = (Usuario)initController.getSessionVars().get("USUARIO");
		tipos = TipoComprobanteDao.getInstance().listarTiposComprobantes();
		tipoBusqueda = 1;
		idBusqueda = new ComprobantePk();
	}
	
	public void loadLazymodelComprobantes() {
		modelComprobante = new LazyDataModel<Comprobante>() {
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
		    public List<Comprobante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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
			map = ComprobanteDao.getInstance().listarComprobantes(parametros,tipoBusqueda,limites);
        	count = (Integer) map.get("TOTAL");
            if (count != 0) {
				comprobantes = (List<Comprobante>) map.get("BANDEJA");
                LOGGER.info( "Número de registros obtenidos  {0}", count);
	            modelComprobante.setRowCount(count);              
            }else{
            	Message.addAviso(null, "No se encontraron coincidencias!!");
            }
        } catch (Exception ex) {
            LOGGER.error(null, ex);
        }
	}
	
	public void cargarComprobante(){
		initController.getSessionVars().put("COMPROBANTE", seleccionado);
		detalle.cargarDatos();
	}
	
	public void cargarComprobantes(){
		if(validarEntradas()){
			parametros = new HashMap<String,Object>();
			switch (tipoBusqueda) {
				case 1:
					parametros.put("INICIO", fechaInicio);
					parametros.put("FIN", fechaFin);
				break;
				case 2:
					parametros.put("ID", idBusqueda);
				break;
			}
			parametros.put("COMPROBANTE", tipoSeleccionado);
			loadLazymodelComprobantes();
		}
		Js.update("mensajes");
	}
	
	public boolean validarEntradas(){
		boolean ejecutar = true;
		if(tipoSeleccionado == -1){
			Message.addError(null, "No ha seleccionado tipo de comprobante !!");
			ejecutar = false;
		}
		switch (tipoBusqueda) {
			case 1:
			if(fechaInicio == null){
				Message.addError(null, "No ha ingresado fecha de inicio !!");
				ejecutar = false;
			}
			if(fechaFin == null){
				Message.addError(null, "No ha ingresado fecha de fin !!");
				ejecutar = false;
			}
			if(fechaInicio !=null && fechaFin != null){
				if(fechaFin.before(fechaInicio)){
					Message.addError(null, "Fecha fin debe ser mayor que fecha inicio !!");
					ejecutar = false;
				}
			}
			break;
			
			case 2:
			if(idBusqueda.getSerie().trim().equals("")){
				Message.addError(null, "No ha ingresado serie de comprobante !!");
				ejecutar = false;
			}
			if(idBusqueda.getNumero().trim().equals("")){
				Message.addError(null, "No ha ingresado número de comprobante !!");
				ejecutar = false;
			}	
			break;
		}
		
		
		return ejecutar;
	}
	
	public void generarTxt(){
		if(validarEntradas()){
			List<ComprobantePk> pks = ComprobanteDao.getInstance().listarComprobantes(parametros,tipoBusqueda);
			if(GenerarTxt.getInstance().toTxt(pks, tipoSeleccionado))
				Message.addInfo(null, "Archivos enviados correctamente al Facturador-SUNAT !!");
			else
				Message.addError(null, "No se ha generado los archivos !!");	
		}
		Js.update("mensajes");
	}
	
	public void verificarBusqueda(){
		Js.update("busqueda");
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

	public List<Comprobante> getcomprobantes() {
		return comprobantes;
	}

	public void setcomprobantes(List<Comprobante> comprobantes) {
		this.comprobantes = comprobantes;
	}
	
	public LazyDataModel<Comprobante> getmodelComprobante() {
		return modelComprobante;
	}
	
	public void setmodelComprobante(LazyDataModel<Comprobante> modelComprobante) {
		this.modelComprobante = modelComprobante;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<TipoComprobante> getTipos() {
		return tipos;
	}
	public void setTipos(List<TipoComprobante> tipos) {
		this.tipos = tipos;
	}
	public int getTipoSeleccionado() {
		return tipoSeleccionado;
	}
	public void setTipoSeleccionado(int tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}
	public Comprobante getSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(Comprobante seleccionado) {
		this.seleccionado = seleccionado;
	}
	public DetalleComprobanteController getDetalle() {
		return detalle;
	}
	public void setDetalle(DetalleComprobanteController detalle) {
		this.detalle = detalle;
	}

	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public ComprobantePk getIdBusqueda() {
		return idBusqueda;
	}

	public void setIdBusqueda(ComprobantePk idBusqueda) {
		this.idBusqueda = idBusqueda;
	}	
	
}
