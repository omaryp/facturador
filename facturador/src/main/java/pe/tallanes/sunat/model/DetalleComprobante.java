package pe.tallanes.sunat.model;


public class DetalleComprobante  {
	
	private int serie;
	private String numero;
	private int codigoArticulo;
	private int tipoArticulo;
	private int nombreArticulo;
	private double cantidad;
	private double precio;
	private double total;
	
	public DetalleComprobante(){}
	
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

	public int getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(int codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public int getTipoArticulo() {
		return tipoArticulo;
	}

	public void setTipoArticulo(int tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	public int getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(int nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
