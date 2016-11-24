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


public class UsuarioDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);
	private static UsuarioDao dao = null;
	private UsuarioDao(){
		
	}
	
	public static UsuarioDao getInstance(){
		if(dao == null){
			dao = new UsuarioDao();
		}
		return dao;
	}
	
	public Map<String, String> getDatosUsuario(String user){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> datos = null;
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select u.Nom_User,u.Tip_User,t.Des_Tip ");
			sql.append("From AQUSUARIOS u ");
			sql.append("inner join AQTIPOUSUARIO t on u.Tip_User = t.Tip_user ");		
			sql.append("Where u.Log_User = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, user);
			rs =ps.executeQuery();
			datos = new HashMap<String, String>();
			if(rs.next()){
				datos.put("NOMBRE",rs.getString("Nom_User"));
				datos.put("TIPO",String.valueOf(rs.getInt("Tip_User")));
				datos.put("DES_TIPO",rs.getString("Des_Tip"));
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
	
	public boolean existeUsuario(String user){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total = 0;
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select count(*) from sysusers ");
			sql.append("Where name = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, user);
			rs =ps.executeQuery();
			if(rs.next())
				total = rs.getInt(1);
			return (total==1);
		} catch (Exception e) {
			LOGGER.error("Error verificar usuario.",e);
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
		return false;
	}
	
	
	public boolean validarUsuario(String user,String pass){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total = 0;
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select count(*) from AQUSUARIOS ");
			sql.append("Where Nom_User = ? and clave = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, user);
			ps.setString(2, pass);
			rs =ps.executeQuery();
			if(rs.next())
				total = rs.getInt(1);
			return (total==1);
		} catch (Exception e) {
			LOGGER.error("Error al validar usuario.",e);
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
		return false;
	}
	
}
