package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.Comprobante;
import pe.tallanes.sunat.model.DetalleComprobante;

public class DetalleComprobanteDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DetalleComprobanteDao.class);
	private static DetalleComprobanteDao dao = null;
	private DetalleComprobanteDao(){}
	
	public static DetalleComprobanteDao getInstance(){
		if(dao == null){
			dao = new DetalleComprobanteDao();
		}
		return dao;
	}
	
	public Map<String, Object> listarDetalleComprobantes(Comprobante com,int limites[]){
		int inicio = 0;
		int fin = 0;
		int total = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> datos = null;
		List<DetalleComprobante> detalles = null;
		StringBuilder sql = new StringBuilder();
		inicio = limites[0];
		fin = limites[1];
		
		try {
			datos = new HashMap<String, Object>();
			con = Conexiones.getConexion();
			sql.append("SELECT count(*) FROM Articulos_Comprobante where Serie = ? and Numero = ?");
			ps =  con.prepareStatement(sql.toString());
			ps.setString(1, com.getId().getSerie());
			ps.setString(2,com.getId().getNumero());
			rs = ps.executeQuery();
			total = (rs.next())?rs.getInt(1):0;
			datos.put("TOTAL", total);
			if(total != 0){
				if(ps!=null){
					ps.close();
					ps = null;
				}
				sql.delete(0, sql.length());
				sql.append("Select * from (");
				sql.append("Select ROW_NUMBER() OVER (ORDER BY A.Cod_Articulo) as row,A.Serie,A.Numero,A.Cod_Articulo,");
				sql.append("A.CodTipoArticulo,B.NombreArticulo,A.Cantidad,A.Precio,A.Total ");
				sql.append("From  Articulos_Comprobante A ");
				sql.append("inner join Articulos B on A.Cod_Articulo = B.Cod_Articulo and A.CodTipoArticulo = B.CodTipoArticulo ");
			    sql.append("Where A.Serie = ? and A.Numero = ? and A.Cod_Comprobante = ? ) T ");
				sql.append("Where T.row between ? and  ?");
				ps = con.prepareStatement(sql.toString());
				ps.setString(1, com.getId().getSerie() );
				ps.setString(2, com.getId().getNumero());
				ps.setString(3, com.getId().getCodigoComprobante());
				ps.setInt(4, inicio);
				ps.setInt(5, fin);
				rs =ps.executeQuery();
				detalles = new ArrayList<DetalleComprobante>();
				while(rs.next()){
					DetalleComprobante detalle = new DetalleComprobante();
					detalle.setSerie(rs.getString("Serie"));
					detalle.setNumero(rs.getString("Numero"));
					detalle.setCodigoArticulo(rs.getInt("Cod_Articulo"));
					detalle.setTipoArticulo(rs.getInt("CodTipoArticulo"));
					detalle.setNombreArticulo(rs.getString("NombreArticulo"));
					detalle.setCantidad(rs.getDouble("Cantidad"));
					detalle.setPrecio(rs.getDouble("Precio"));
					detalle.setTotal(rs.getDouble("Total"));
					detalles.add(detalle);
				}
			}
			datos.put("BANDEJA",detalles);
		} catch (Exception e) {
			datos = null;
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
		return datos;
	}
	
}
