package pe.tallanes.sunat.model.json;


public class Baja {
	
	private String fecGeneracion;
	private String fecComunicacion;
	private String tipDocBaja;
	private String numDocBaja;
	private String desMotivoBaja;
	
	public Baja(){
		this.fecGeneracion = "";
		this.fecComunicacion = "";
		this.tipDocBaja = "";
		this.numDocBaja = "";
		this.desMotivoBaja = "";
	}

	public String getFecGeneracion() {
		return fecGeneracion;
	}

	public void setFecGeneracion(String fecGeneracion) {
		this.fecGeneracion = fecGeneracion;
	}

	public String getFecComunicacion() {
		return fecComunicacion;
	}

	public void setFecComunicacion(String fecComunicacion) {
		this.fecComunicacion = fecComunicacion;
	}

	public String getTipDocBaja() {
		return tipDocBaja;
	}

	public void setTipDocBaja(String tipDocBaja) {
		this.tipDocBaja = tipDocBaja;
	}

	public String getNumDocBaja() {
		return numDocBaja;
	}

	public void setNumDocBaja(String numDocBaja) {
		this.numDocBaja = numDocBaja;
	}

	public String getDesMotivoBaja() {
		return desMotivoBaja;
	}

	public void setDesMotivoBaja(String desMotivoBaja) {
		this.desMotivoBaja = desMotivoBaja;
	}

}
