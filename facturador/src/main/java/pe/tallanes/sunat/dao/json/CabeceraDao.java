package pe.tallanes.sunat.dao.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.json.Cabecera;
import pe.tallanes.sunat.util.Cadena;
import pe.tallanes.sunat.util.Documento;
import pe.tallanes.sunat.util.Moneda;

public class CabeceraDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CabeceraDao.class);
	private static CabeceraDao dao = null;
	private CabeceraDao(){}
	
	public static CabeceraDao getInstance(){
		if(dao == null){
			dao = new CabeceraDao();
		}
		return dao;
	}
	
	public Cabecera getCabecera(ComprobantePk id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Cabecera cabecera = null;
		try {
			con = Conexiones.getConexion();
			sql.append("Select '01' tipOpe,A.FechaEmision,'' codLocal,'' tipDoc ,A.RUC numDoc ,A.Cliente,");
			sql.append("A.Moneda,'0.00' dsctoGlobal,'0.00' otroCargo,'0.00' totDscto,cast(A.ValorVenta as varchar(15)) ValorVenta,cast(A.Impuesto as varchar(15)) Impuesto,");
			sql.append("'0.00' mtoIsc,'0.00' otroTrib,cast((A.ValorVenta+A.Impuesto) as varchar(15)) total ,A.Cod_Comprobante ");
			sql.append("From Comprobante A ");
			sql.append("Where A.Serie = ? and A.Numero = ? ");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, id.getSerie());
			ps.setString(2, id.getNumero());
			rs =ps.executeQuery();
			if(rs.next()){
				cabecera = new Cabecera();
				cabecera.setTipOperacion(rs.getString("tipOpe"));
				cabecera.setFecEmision(Cadena.formatoFecha(rs.getDate("FechaEmision")));
				cabecera.setCodLocalEmisor(rs.getString("codLocal"));
				int codComprobante = Integer.parseInt(rs.getString("Cod_Comprobante")); 
				switch (codComprobante) {
					case 1:
						cabecera.setTipDocUsuario(Documento.DOC_NACIONAL_DE_IDENTIDAD.value());
						break;
					case 2:
						cabecera.setTipDocUsuario(Documento.REG_UNICO_DE_CONTRIBUYENTES.value());
						break;
				}
				cabecera.setNumDocUsuario(rs.getString("numDoc"));
				cabecera.setRznSocialUsuario(rs.getString("Cliente"));
				cabecera.setTipMoneda((rs.getString("Moneda").equals("01"))?Moneda.SOLES.value():Moneda.DOLARES.value());
				cabecera.setSumDsctoGlobal(rs.getString("dsctoGlobal"));
				cabecera.setSumOtrosCargos(rs.getString("otroCargo"));
				cabecera.setMtoDescuentos(rs.getString("totDscto"));
				cabecera.setMtoOperGravadas(rs.getString("ValorVenta"));
				cabecera.setMtoOperInafectas(rs.getString("ValorVenta"));
				cabecera.setMtoOperExoneradas(rs.getString("ValorVenta"));
				cabecera.setMtoIGV(rs.getString("Impuesto"));
				cabecera.setMtoISC(rs.getString("mtoIsc"));
				cabecera.setMtoOtrosTributos(rs.getString("otroTrib"));
				cabecera.setMtoImpVenta(rs.getString("total"));
			}
		} catch (Exception e) {
			cabecera = null;
			LOGGER.error("Error al cargar comprobantes.",e);
		}finally{
			try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            	LOGGER.error("No se pudo liberar el recurso");
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            	LOGGER.error("No se pudo liberar el recurso");
            }
		}
		return cabecera;
	}

}
