package pe.tallanes.sunat.dao.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.json.CabeceraNota;
import pe.tallanes.sunat.util.Cadena;
import pe.tallanes.sunat.util.Documento;
import pe.tallanes.sunat.util.Moneda;

public class CabeceraNotaDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CabeceraNotaDao.class);
	private static CabeceraNotaDao dao = null;
	private CabeceraNotaDao(){}
	
	public static CabeceraNotaDao getInstance(){
		if(dao == null){
			dao = new CabeceraNotaDao();
		}
		return dao;
	}
	
	public CabeceraNota getCabeceraNota(ComprobantePk id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		CabeceraNota cabecera = null;
		try {
			con = Conexiones.getConexion();
			sql.append("Select A.FechaEmision,'01' tipoNota,Observaciones,C.Cod_Sunat,B.SerieC,B.NumeroC,'' tipDoc ,A.RUC numDoc ,A.Cliente,");
			sql.append("A.Moneda,'0.00' otroCargo,cast(A.ValorVenta as varchar(15)) operGravadas,'0.00' operInafectas,'0.00' operExoneradas,cast(A.Impuesto as varchar(15)) Impuesto,");
			sql.append("'0.00' mtoIsc,'0.00' otroTrib,cast((A.ValorVenta+A.Impuesto) as varchar(15)) total ,B.Cod_ComprobanteC ");
			sql.append("From Comprobante A ");
			sql.append("Inner Join  Comprobante_Modifica B on B.Serie = A.Serie and B.Numero = A.Numero and B.Cod_Comprobante = A.Cod_Comprobante ");
			sql.append("Inner Join  TipoComprobante C on C.Cod_Comprobante = B.Cod_ComprobanteC ");
			sql.append("Where A.Serie = ? and A.Numero = ? ");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, id.getSerie());
			ps.setString(2, id.getNumero());
			rs =ps.executeQuery();
			if(rs.next()){
				cabecera = new CabeceraNota();
				cabecera.setFecEmision(Cadena.formatoFecha(rs.getDate("FechaEmision")));
				cabecera.setCodMotivo(rs.getString("tipoNota"));
				cabecera.setDesMotivo(rs.getString("Observaciones"));
				cabecera.setTipDocAfectado(rs.getString("Cod_Sunat"));
				int codComprobante = Integer.parseInt(rs.getString("Cod_ComprobanteC"));
				StringBuilder serComprobante = new StringBuilder();
				serComprobante.append(Cadena.formatoSerie(rs.getInt("SerieC"), codComprobante));
				serComprobante.append("-");
				serComprobante.append(Cadena.completar(rs.getString("NumeroC"), 8,"0",false));
				cabecera.setNumDocAfectado(serComprobante.toString());
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
				cabecera.setSumOtrosCargos(rs.getString("otroCargo"));
				cabecera.setMtoOperGravadas(rs.getString("operGravadas"));
				cabecera.setMtoOperInafectas(rs.getString("operInafectas"));
				cabecera.setMtoOperExoneradas(rs.getString("operExoneradas"));
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
	
	public ComprobantePk getDocumentoModifica(ComprobantePk id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ComprobantePk modifica = null;
		try {
			con = Conexiones.getConexion();
			sql.append("Select SerieC,NumeroC,Cod_ComprobanteC ");
			sql.append("From Comprobante_Modifica ");
			sql.append("Where Serie = ? and Numero = ? and Cod_Comprobante = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, id.getSerie());
			ps.setString(2, id.getNumero());
			ps.setString(3, id.getCodigoComprobante());
			rs =ps.executeQuery();
			if(rs.next()){
				modifica = new ComprobantePk();
				modifica.setSerie(rs.getInt("SerieC"));
				modifica.setNumero(rs.getString("NumeroC"));
				modifica.setCodigoComprobante(rs.getString("Cod_ComprobanteC"));
			}
		} catch (Exception e) {
			modifica = null;
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
		return modifica;
	}


}
