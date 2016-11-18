package pe.tallanes.sunat.model.json;

import java.util.List;

public class FacturaBoleta {
	
	private Cabecera cabecera;
	private List<Detalle> detalle;
	private DocumentosRelacionados relacion;
	private Leyenda leyendas;
	private Baja resumenBajas;
	
	public FacturaBoleta(){}

	public Cabecera getCabecera() {
		return cabecera;
	}

	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}

	public List<Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}

	public DocumentosRelacionados getRelacion() {
		return relacion;
	}

	public void setRelacion(DocumentosRelacionados relacion) {
		this.relacion = relacion;
	}

	public Leyenda getLeyendas() {
		return leyendas;
	}

	public void setLeyendas(Leyenda leyendas) {
		this.leyendas = leyendas;
	}

	public Baja getResumenBajas() {
		return resumenBajas;
	}

	public void setResumenBajas(Baja resumenBajas) {
		this.resumenBajas = resumenBajas;
	}

}
