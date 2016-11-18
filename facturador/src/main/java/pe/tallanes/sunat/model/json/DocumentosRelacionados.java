package pe.tallanes.sunat.model.json;

public class DocumentosRelacionados {
	
	private String indDocRelacionado;
	private String tipDocRelacionado;
	private String numDocRelacionado;
	private String tipDocEmisor;
	private String numDocEmisor;
	private String mtoDocRelacionado;

	public DocumentosRelacionados(){
		this.indDocRelacionado = "";
		this.tipDocRelacionado = "";
		this.numDocRelacionado = "";
		this.tipDocEmisor = "";
		this.numDocEmisor = "";
		this.mtoDocRelacionado = "0.00";
	}

	public String getIndDocRelacionado() {
		return indDocRelacionado;
	}

	public void setIndDocRelacionado(String indDocRelacionado) {
		this.indDocRelacionado = indDocRelacionado;
	}

	public String getTipDocRelacionado() {
		return tipDocRelacionado;
	}

	public void setTipDocRelacionado(String tipDocRelacionado) {
		this.tipDocRelacionado = tipDocRelacionado;
	}

	public String getNumDocRelacionado() {
		return numDocRelacionado;
	}

	public void setNumDocRelacionado(String numDocRelacionado) {
		this.numDocRelacionado = numDocRelacionado;
	}

	public String getTipDocEmisor() {
		return tipDocEmisor;
	}

	public void setTipDocEmisor(String tipDocEmisor) {
		this.tipDocEmisor = tipDocEmisor;
	}

	public String getNumDocEmisor() {
		return numDocEmisor;
	}

	public void setNumDocEmisor(String numDocEmisor) {
		this.numDocEmisor = numDocEmisor;
	}

	public String getMtoDocRelacionado() {
		return mtoDocRelacionado;
	}

	public void setMtoDocRelacionado(String mtoDocRelacionado) {
		this.mtoDocRelacionado = mtoDocRelacionado;
	}
	
}