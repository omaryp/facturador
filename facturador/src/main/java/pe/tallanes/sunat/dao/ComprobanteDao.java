package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;

public class ComprobanteDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);
	private static ComprobanteDao dao = null;
	private ComprobanteDao(){}
	
	public static ComprobanteDao getInstance(){
		if(dao == null){
			dao = new ComprobanteDao();
		}
		return dao;
	}
	
	public Map<String, Object> listarComprobantes(Map<String, Object> params){
		int tipoComprobante = 0;
		Date fechaEmision = null;
		int inicio = 0;
		int fin = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> datos = null;
		tipoComprobante = Integer.parseInt(params.get("TIPCOM").toString());
		fechaEmision = (Date)params.get("FECEMI");
		inicio = Integer.parseInt(params.get("INI").toString());
		fin = Integer.parseInt(params.get("FIN").toString());
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select [Serie],[Numero],[RUC],[Cliente],[Moneda],[FechaEmision],[FechaCancelacion],[Dcto],[ValorVenta],[Impuesto],[TCambio] ");
			sql.append("From [Comprobante] Where FechaEmision = ? and serie = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setDate(1, new java.sql.Date(fechaEmision.getTime()));
			ps.setInt(2, tipoComprobante);
			rs =ps.executeQuery();
			datos = new HashMap<String, Object>();
			if(rs.next()){
				datos.put("NOMBRE",rs.getString("Nom_User"));
				datos.put("TIPO",String.valueOf(rs.getInt("Tip_User")));
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener usuario.",e);
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
		return datos;
	}
	
	public Map<String, Object> listarDetalleComprobantes(Map<String, Object> params){
		int tipoComprobante = 0;
		int inicio = 0;
		int fin = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> datos = null;
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select Nom_User,Tip_User ");
			sql.append("From AQUSUARIOS Where Log_User = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, user);
			rs =ps.executeQuery();
			datos = new HashMap<String, String>();
			if(rs.next()){
				datos.put("NOMBRE",rs.getString("Nom_User"));
				datos.put("TIPO",String.valueOf(rs.getInt("Tip_User")));
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener usuario.",e);
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
		return datos;
	}
	
}
