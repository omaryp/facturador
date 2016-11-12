package pe.tallanes.sunat.model;

import java.io.Serializable;

public class ComprobantePk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serie;
	private String numero;
	
	
	public ComprobantePk(){}
	
	public int getSerie() {
		return serie;
	}
	public void setSerie(int serie) {
		this.serie = serie;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
