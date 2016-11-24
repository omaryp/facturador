package pe.tallanes.sunat.model.json;

import java.io.Serializable;

public class CabeceraNota implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fecEmision;
	private String codMotivo;
	private String desMotivo;
	private String tipDocAfectado;
	private String numDocAfectado;
	private String tipDocUsuario;
	private String numDocUsuario;
	private String rznSocialUsuario;
	private String tipMoneda;
	private String sumOtrosCargos;
	private String mtoOperGravadas;
	private String mtoOperInafectas;
	private String mtoOperExoneradas;
	private String mtoIGV;
	private String mtoISC;
	private String mtoOtrosTributos;
	private String mtoImpVenta;
	
	public CabeceraNota(){}

	public String getFecEmision() {
		return fecEmision;
	}

	public void setFecEmision(String fecEmision) {
		this.fecEmision = fecEmision;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public String getDesMotivo() {
		return desMotivo;
	}

	public void setDesMotivo(String desMotivo) {
		this.desMotivo = desMotivo;
	}

	public String getTipDocAfectado() {
		return tipDocAfectado;
	}

	public void setTipDocAfectado(String tipDocAfectado) {
		this.tipDocAfectado = tipDocAfectado;
	}

	public String getNumDocAfectado() {
		return numDocAfectado;
	}

	public void setNumDocAfectado(String numDocAfectado) {
		this.numDocAfectado = numDocAfectado;
	}

	public String getTipDocUsuario() {
		return tipDocUsuario;
	}

	public void setTipDocUsuario(String tipDocUsuario) {
		this.tipDocUsuario = tipDocUsuario;
	}

	public String getNumDocUsuario() {
		return numDocUsuario;
	}

	public void setNumDocUsuario(String numDocUsuario) {
		this.numDocUsuario = numDocUsuario;
	}

	public String getRznSocialUsuario() {
		return rznSocialUsuario;
	}

	public void setRznSocialUsuario(String rznSocialUsuario) {
		this.rznSocialUsuario = rznSocialUsuario;
	}

	public String getTipMoneda() {
		return tipMoneda;
	}

	public void setTipMoneda(String tipMoneda) {
		this.tipMoneda = tipMoneda;
	}

	public String getSumOtrosCargos() {
		return sumOtrosCargos;
	}

	public void setSumOtrosCargos(String sumOtrosCargos) {
		this.sumOtrosCargos = sumOtrosCargos;
	}

	public String getMtoOperGravadas() {
		return mtoOperGravadas;
	}

	public void setMtoOperGravadas(String mtoOperGravadas) {
		this.mtoOperGravadas = mtoOperGravadas;
	}

	public String getMtoOperInafectas() {
		return mtoOperInafectas;
	}

	public void setMtoOperInafectas(String mtoOperInafectas) {
		this.mtoOperInafectas = mtoOperInafectas;
	}

	public String getMtoOperExoneradas() {
		return mtoOperExoneradas;
	}

	public void setMtoOperExoneradas(String mtoOperExoneradas) {
		this.mtoOperExoneradas = mtoOperExoneradas;
	}

	public String getMtoIGV() {
		return mtoIGV;
	}

	public void setMtoIGV(String mtoIGV) {
		this.mtoIGV = mtoIGV;
	}

	public String getMtoISC() {
		return mtoISC;
	}

	public void setMtoISC(String mtoISC) {
		this.mtoISC = mtoISC;
	}

	public String getMtoOtrosTributos() {
		return mtoOtrosTributos;
	}

	public void setMtoOtrosTributos(String mtoOtrosTributos) {
		this.mtoOtrosTributos = mtoOtrosTributos;
	}

	public String getMtoImpVenta() {
		return mtoImpVenta;
	}

	public void setMtoImpVenta(String mtoImpVenta) {
		this.mtoImpVenta = mtoImpVenta;
	}
	
	@Override
	public String toString(){
		StringBuilder tostring  = new StringBuilder();
		tostring.append(fecEmision);
		tostring.append("|");
		tostring.append(codMotivo);
		tostring.append("|");
		tostring.append(desMotivo);
		tostring.append("|");
		tostring.append(tipDocAfectado);
		tostring.append("|");
		tostring.append(numDocAfectado);
		tostring.append("|");
		tostring.append(tipDocUsuario);
		tostring.append("|");
	 	tostring.append(numDocUsuario);
	 	tostring.append("|");
	 	tostring.append(rznSocialUsuario);
	 	tostring.append("|");
	 	tostring.append(tipMoneda);
	 	tostring.append("|");
	 	tostring.append(sumOtrosCargos);
	 	tostring.append("|");
	 	tostring.append(mtoOperGravadas);
	 	tostring.append("|");
	 	tostring.append(mtoOperInafectas);
	 	tostring.append("|");
	 	tostring.append(mtoOperExoneradas);
	 	tostring.append("|");
	 	tostring.append(mtoIGV);
	 	tostring.append("|");
	 	tostring.append(mtoISC);
	 	tostring.append("|");
	 	tostring.append(mtoOtrosTributos);
	 	tostring.append("|");
	 	tostring.append(mtoImpVenta);
	 	tostring.append("|");
	 	return tostring.toString();
	}

}
