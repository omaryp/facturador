package pe.tallanes.sunat.model.json;

public class AdicionalDetalle {

	
	private String mtoValorUnitarioGratuito;
	private String numPlacaItem;
	
	public AdicionalDetalle(){
		this.mtoValorUnitarioGratuito = "0.00";
		this.numPlacaItem = "";
	}

	public String getMtoValorUnitarioGratuito() {
		return mtoValorUnitarioGratuito;
	}

	public void setMtoValorUnitarioGratuito(String mtoValorUnitarioGratuito) {
		this.mtoValorUnitarioGratuito = mtoValorUnitarioGratuito;
	}

	public String getNumPlacaItem() {
		return numPlacaItem;
	}

	public void setNumPlacaItem(String numPlacaItem) {
		this.numPlacaItem = numPlacaItem;
	}
	
}
