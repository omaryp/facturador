package pe.tallanes.sunat.util;

public enum Moneda {
	
	SOLES("PEN"),
	DOLARES("USD");
	
	private final String moneda;

    private Moneda(String valor) {
        this.moneda = valor;
    }

    public String value() {
        return moneda;
    }
}