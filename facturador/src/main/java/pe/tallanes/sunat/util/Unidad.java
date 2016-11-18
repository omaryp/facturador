package pe.tallanes.sunat.util;

public enum Unidad {
	UNIDADES("EA"),
	PIES("FOT"),
	GALONES("GLL"),	
	GRAMOS("GRM"),	
	PULGADAS("INH"),	
	KILOS("KGM"),	
	LITROS("LTR"),	
	METROS("MTR");

	private final String tipo;

	private Unidad(String valor) {
		this.tipo = valor;
	}

	public String value() {
		return tipo;
	}

}
