package pe.tallanes.sunat.util;

public enum Documento {
	DOC_TRIB_NO_DOM_SIN_RUC("0"), DOC_NACIONAL_DE_IDENTIDAD("1"), CARNET_DE_EXTRANJERIA("4"), 
	REG_UNICO_DE_CONTRIBUYENTES("6"), PASAPORTE("7"), CED_DIPLOMATICA_DE_IDENTIDAD("A");

	private final String tipo;

	private Documento(String valor) {
		this.tipo = valor;
	}

	public String value() {
		return tipo;
	}

}
