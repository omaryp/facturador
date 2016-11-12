package pe.tallanes.sunat.model;

import java.io.Serializable;
import java.util.Date;

public class Comprobante implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComprobantePk id;
	private String desSerie;
	private String ruc;
	private String cliente;
	private int moneda;
	private String desMoneda;
	private Date fechaEmision;
	private Date fechaCancelacion;
	private double descuento;
	private double valorVenta;
	private double impuesto;
	private double TCambio;
	
	public Comprobante(){}
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getMoneda() {
		return moneda;
	}
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public double getValorVenta() {
		return valorVenta;
	}
	public void setValorVenta(double valorVenta) {
		this.valorVenta = valorVenta;
	}
	public double getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}
	public double getTCambio() {
		return TCambio;
	}
	public void setTCambio(double tCambio) {
		TCambio = tCambio;
	}

	public String getDesSerie() {
		return desSerie;
	}

	public void setDesSerie(String desSerie) {
		this.desSerie = desSerie;
	}

	public String getDesMoneda() {
		return desMoneda;
	}

	public void setDesMoneda(String desMoneda) {
		this.desMoneda = desMoneda;
	}

	public ComprobantePk getId() {
		return id;
	}

	public void setId(ComprobantePk id) {
		this.id = id;
	}
	
}
