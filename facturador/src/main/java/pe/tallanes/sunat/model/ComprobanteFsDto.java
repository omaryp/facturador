package pe.tallanes.sunat.model;

import java.io.Serializable;

public class ComprobanteFsDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComprobanteFsPk id;
	private String nombreArchivo;
	private String extensionArchivo;
	
	public ComprobanteFsDto(){}
	
	public ComprobanteFsPk getId() {
		return id;
	}
    
	public void setId(ComprobanteFsPk id) {
		this.id = id;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getExtensionArchivo() {
		return extensionArchivo;
	}

	public void setExtensionArchivo(String extensionArchivo) {
		this.extensionArchivo = extensionArchivo;
	}
	
}
