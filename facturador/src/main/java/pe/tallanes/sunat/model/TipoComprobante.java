package pe.tallanes.sunat.model;

import java.io.Serializable;

public class TipoComprobante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	private String descripcion;
	private int codigoSunat;
	
	public TipoComprobante(){}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCodigoSunat() {
		return codigoSunat;
	}

	public void setCodigoSunat(int codigoSunat) {
		this.codigoSunat = codigoSunat;
	}
		
}
