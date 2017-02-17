package pe.tallanes.sunat.model;

import java.io.Serializable;

public class ComprobantePk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serie;
	private String numero;
	private String codigoComprobante;

	public ComprobantePk(){}
	
	
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

	public String getCodigoComprobante() {
		return codigoComprobante;
	}

	public void setCodigoComprobante(String codigoComprobante) {
		this.codigoComprobante = codigoComprobante;
	}	
}
