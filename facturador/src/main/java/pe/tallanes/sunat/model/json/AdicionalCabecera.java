package pe.tallanes.sunat.model.json;

public class AdicionalCabecera {
	
	private String codRegPercepcion;
	private String mtoBaseImponiblePercepcion;
	private String mtoPercepcion;
	private String mtoTotalIncPercepcion;
	private String mtoOperGratuitas;
	private String mtoTotalAnticipo;
	private String codPaisCliente;
	private String codUbigeoCliente;
	private String desDireccionCliente;
	private String codPaisEntrega;
	private String codUbigeoEntrega;
	private String desDireccionEntrega;
	private String fecVencimiento;
	
	public AdicionalCabecera(){
		this.codRegPercepcion = "";
		this.mtoBaseImponiblePercepcion = "0.00";
		this.mtoPercepcion = "0.00";
		this.mtoTotalIncPercepcion = "0.00";
		this.mtoOperGratuitas = "0.00";
		this.mtoTotalAnticipo = "0.00";
		this.codPaisCliente = "";
		this.codUbigeoCliente = "";
		this.desDireccionCliente = "";
		this.codPaisEntrega = "";
		this.codUbigeoEntrega = "";
		this.desDireccionEntrega = "";
		this.fecVencimiento = "";
	}

	public String getCodRegPercepcion() {
		return codRegPercepcion;
	}

	public void setCodRegPercepcion(String codRegPercepcion) {
		this.codRegPercepcion = codRegPercepcion;
	}

	public String getMtoBaseImponiblePercepcion() {
		return mtoBaseImponiblePercepcion;
	}

	public void setMtoBaseImponiblePercepcion(String mtoBaseImponiblePercepcion) {
		this.mtoBaseImponiblePercepcion = mtoBaseImponiblePercepcion;
	}

	public String getMtoPercepcion() {
		return mtoPercepcion;
	}

	public void setMtoPercepcion(String mtoPercepcion) {
		this.mtoPercepcion = mtoPercepcion;
	}

	public String getMtoTotalIncPercepcion() {
		return mtoTotalIncPercepcion;
	}

	public void setMtoTotalIncPercepcion(String mtoTotalIncPercepcion) {
		this.mtoTotalIncPercepcion = mtoTotalIncPercepcion;
	}

	public String getMtoOperGratuitas() {
		return mtoOperGratuitas;
	}

	public void setMtoOperGratuitas(String mtoOperGratuitas) {
		this.mtoOperGratuitas = mtoOperGratuitas;
	}

	public String getMtoTotalAnticipo() {
		return mtoTotalAnticipo;
	}

	public void setMtoTotalAnticipo(String mtoTotalAnticipo) {
		this.mtoTotalAnticipo = mtoTotalAnticipo;
	}

	public String getCodPaisCliente() {
		return codPaisCliente;
	}

	public void setCodPaisCliente(String codPaisCliente) {
		this.codPaisCliente = codPaisCliente;
	}

	public String getCodUbigeoCliente() {
		return codUbigeoCliente;
	}

	public void setCodUbigeoCliente(String codUbigeoCliente) {
		this.codUbigeoCliente = codUbigeoCliente;
	}

	public String getDesDireccionCliente() {
		return desDireccionCliente;
	}

	public void setDesDireccionCliente(String desDireccionCliente) {
		this.desDireccionCliente = desDireccionCliente;
	}

	public String getCodPaisEntrega() {
		return codPaisEntrega;
	}

	public void setCodPaisEntrega(String codPaisEntrega) {
		this.codPaisEntrega = codPaisEntrega;
	}

	public String getCodUbigeoEntrega() {
		return codUbigeoEntrega;
	}

	public void setCodUbigeoEntrega(String codUbigeoEntrega) {
		this.codUbigeoEntrega = codUbigeoEntrega;
	}

	public String getDesDireccionEntrega() {
		return desDireccionEntrega;
	}

	public void setDesDireccionEntrega(String desDireccionEntrega) {
		this.desDireccionEntrega = desDireccionEntrega;
	}

	public String getFecVencimiento() {
		return fecVencimiento;
	}

	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

}