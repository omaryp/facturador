package pe.tallanes.sunat.util;

public enum Tipo {
	FACTURA(1), 
	BOLETA(3), 
	NOTA_DEBITO(8), 
	NOTA_CREDITO(7); 

	private final int tipo;

	private Tipo(int valor) {
		this.tipo = valor;
	}

	public int value() {
		return tipo;
	}

}
