package pe.tallanes.sunat.model;

import java.io.Serializable;
import java.util.Date;

public class ComprobanteFs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComprobanteFsPk id;
	private Date fechaCarga;
	private Date fechaGenerado;
	private Date fechaEnvio;
	private String observaciones;
	private String nombreArchivo;
	private String indicadorSituacion;
	private String extensionArchivo;
	
	public ComprobanteFs(){}
	
	public ComprobanteFsPk getId() {
		return id;
	}
    
	public void setId(ComprobanteFsPk id) {
		this.id = id;
	}
	
	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaGenerado() {
		return fechaGenerado;
	}

	public void setFechaGenerado(Date fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getIndicadorSituacion() {
		return indicadorSituacion;
	}

	public void setIndicadorSituacion(String indicadorSituacion) {
		this.indicadorSituacion = indicadorSituacion;
	}

	public String getExtensionArchivo() {
		return extensionArchivo;
	}

	public void setExtensionArchivo(String extensionArchivo) {
		this.extensionArchivo = extensionArchivo;
	}
	
}
