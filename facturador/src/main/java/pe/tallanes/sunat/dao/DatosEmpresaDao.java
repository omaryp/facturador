package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;

public class DatosEmpresaDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatosEmpresaDao.class);
	private static DatosEmpresaDao dao = null;

	private DatosEmpresaDao() {
	}

	public static DatosEmpresaDao getInstance() {
		if (dao == null) {
			dao = new DatosEmpresaDao();
		}
		return dao;
	}

	public Map<String, Object> getDatosEmpresa() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> datos = null;
		StringBuilder sql = new StringBuilder();
		try {
			datos = new HashMap<String, Object>();
			con = Conexiones.getConexion();
			sql.delete(0, sql.length());
			sql.append("Select Ruc,RazonSocial,Direccion from DatosEmpresa");
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()) {
				datos.put("RUC",rs.getString("Ruc"));
				datos.put("RAZONSOCIAL",rs.getString("RazonSocial"));
				datos.put("DIRECCION",rs.getString("Direccion"));
			}
		} catch (Exception e) {
			datos = null;
			LOGGER.error("Error al cargar datos de la empresa.");
		} finally {
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
