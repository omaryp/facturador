package pe.tallanes.sunat.util;

public enum Extension {
	
	CABECERA("CAB"),
	DETALLE("DET"),
	DOC_RELACIONADOS("REL"),
	ADICIONALES_DE_CABECERA("ACA"),
	ADICIONAL_DE_DETALLE("ADE"),
	LEYENDAS("LEY"),
	GUIA_REMITENTE("GRE"),
	GUIA_TRANBSPORTISTA("GTR"),
	VEHICULOS("VEH"),
	CONDUCTORES("CON"),
	NOTAS("NOT"),
	JSON("JSON");
	
	private final String moneda;

    private Extension(String valor) {
        this.moneda = valor;
    }

    public String value() {
        return moneda;
    }
}