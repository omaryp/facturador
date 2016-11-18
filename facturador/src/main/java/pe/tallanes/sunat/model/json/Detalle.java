package pe.tallanes.sunat.model.json;


public class Detalle{
	
	private String codUnidadMedida;
	private String ctdUnidadItem;
	private String codProducto;
	private String codProductoSUNAT;
	private String desItem;
	private String mtoValorUnitario;
	private String mtoDsctoItem;
	private String mtoIgvItem;
	private String tipAfeIGV;
	private String mtoIscItem;
	private String tipSisISC;
	private String mtoPrecioVentaItem;
	private String mtoValorVentaItem;
	
	public Detalle(){}

	public String getCodUnidadMedida() {
		return codUnidadMedida;
	}

	public void setCodUnidadMedida(String codUnidadMedida) {
		this.codUnidadMedida = codUnidadMedida;
	}

	public String getCtdUnidadItem() {
		return ctdUnidadItem;
	}

	public void setCtdUnidadItem(String ctdUnidadItem) {
		this.ctdUnidadItem = ctdUnidadItem;
	}

	public String getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}

	public String getCodProductoSUNAT() {
		return codProductoSUNAT;
	}

	public void setCodProductoSUNAT(String codProductoSUNAT) {
		this.codProductoSUNAT = codProductoSUNAT;
	}

	public String getDesItem() {
		return desItem;
	}

	public void setDesItem(String desItem) {
		this.desItem = desItem;
	}

	public String getMtoValorUnitario() {
		return mtoValorUnitario;
	}

	public void setMtoValorUnitario(String mtoValorUnitario) {
		this.mtoValorUnitario = mtoValorUnitario;
	}

	public String getMtoDsctoItem() {
		return mtoDsctoItem;
	}

	public void setMtoDsctoItem(String mtoDsctoItem) {
		this.mtoDsctoItem = mtoDsctoItem;
	}

	public String getMtoIgvItem() {
		return mtoIgvItem;
	}

	public void setMtoIgvItem(String mtoIgvItem) {
		this.mtoIgvItem = mtoIgvItem;
	}

	public String getTipAfeIGV() {
		return tipAfeIGV;
	}

	public void setTipAfeIGV(String tipAfeIGV) {
		this.tipAfeIGV = tipAfeIGV;
	}

	public String getMtoIscItem() {
		return mtoIscItem;
	}

	public void setMtoIscItem(String mtoIscItem) {
		this.mtoIscItem = mtoIscItem;
	}

	public String getTipSisISC() {
		return tipSisISC;
	}

	public void setTipSisISC(String tipSisISC) {
		this.tipSisISC = tipSisISC;
	}

	public String getMtoPrecioVentaItem() {
		return mtoPrecioVentaItem;
	}

	public void setMtoPrecioVentaItem(String mtoPrecioVentaItem) {
		this.mtoPrecioVentaItem = mtoPrecioVentaItem;
	}

	public String getMtoValorVentaItem() {
		return mtoValorVentaItem;
	}

	public void setMtoValorVentaItem(String mtoValorVentaItem) {
		this.mtoValorVentaItem = mtoValorVentaItem;
	}
	
	@Override
	public String toString(){
		StringBuilder tostring  = new StringBuilder();
		tostring.append(codUnidadMedida);
		tostring.append("|");
		tostring.append(ctdUnidadItem);
		tostring.append("|");
		tostring.append(codProducto);
		tostring.append("|");
		tostring.append(codProductoSUNAT);
		tostring.append("|");
	 	tostring.append(desItem);
	 	tostring.append("|");
	 	tostring.append(mtoValorUnitario);
	 	tostring.append("|");
	 	tostring.append(mtoDsctoItem);
	 	tostring.append("|");
	 	tostring.append(mtoIgvItem);
	 	tostring.append("|");
	 	tostring.append(tipAfeIGV);
	 	tostring.append("|");
	 	tostring.append(mtoIscItem);
	 	tostring.append("|");
	 	tostring.append(tipSisISC);
	 	tostring.append("|");
	 	tostring.append(mtoPrecioVentaItem);
	 	tostring.append("|");
	 	tostring.append(mtoValorVentaItem);
	 	return tostring.toString();
	}


}
