package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.TipoComprobante;

public class TipoComprobanteDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(TipoComprobanteDao.class);
	private static TipoComprobanteDao dao = null;
	private TipoComprobanteDao(){}
	
	public static TipoComprobanteDao getInstance(){
		if(dao == null){
			dao = new TipoComprobanteDao();
		}
		return dao;
	}
	
	public List<TipoComprobante> listarTiposComprobantes(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TipoComprobante> datos = null;
		try {
			con = Conexiones.getConexion();
			StringBuilder sql = new StringBuilder();
			sql.append("Select Cod_Comprobante,Descripción descri,Cod_Sunat ");
			sql.append("From TipoComprobante ");
			sql.append("Where Cod_Sunat <> '00' ");
			ps = con.prepareStatement(sql.toString());
			rs =ps.executeQuery();
			datos = new ArrayList<TipoComprobante>();
			while(rs.next()){
				TipoComprobante tipo = new TipoComprobante();
				tipo.setCodigo(rs.getInt("Cod_Comprobante"));
				tipo.setDescripcion(rs.getString("descri"));
				tipo.setCodigoSunat(rs.getInt("Cod_Sunat"));
				datos.add(tipo);
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
