package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.Comprobante;
import pe.tallanes.sunat.model.ComprobantePk;

public class ComprobanteDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComprobanteDao.class);
	private static ComprobanteDao dao = null;
	private ComprobanteDao(){}
	
	public static ComprobanteDao getInstance(){
		if(dao == null){
			dao = new ComprobanteDao();
		}
		return dao;
	}
	
	public Map<String, Object> listarComprobantes(Date fechaEmision,int tipo,int limites[]){
		int inicio = 0;
		int fin = 0;
		int total = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> datos = null;
		List<Comprobante> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		inicio = limites[0];
		fin = limites[1];
		
		try {
			datos = new HashMap<String, Object>();
			con = Conexiones.getConexion();
			sql.append("Select count(*) From Comprobante A ");
			sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante and B.Cod_Sunat = ? ");
			sql.append("Where cast(A.FechaEmision as Date) = ?  and A.EstadoDoc = 0  ");
			ps =  con.prepareStatement(sql.toString());
			ps.setInt(1,tipo);
			ps.setDate(2, new java.sql.Date(fechaEmision.getTime()));
			rs = ps.executeQuery();
			total = (rs.next())?rs.getInt(1):0;
			datos.put("TOTAL", total);
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(total != 0){
				sql.delete(0, sql.length());
				sql.append("Select * from (");
				sql.append("Select ROW_NUMBER() OVER (ORDER BY A.Cliente asc) as row,A.Serie,A.Cod_Comprobante,B.Descripción as Descri,A.Numero,A.RUC,A.Cliente,");
				sql.append("A.Moneda,C.NombreMoneda ,A.FechaEmision,A.FechaCancelacion,A.Dcto,A.ValorVenta,A.Impuesto,A.TCambio ");
				sql.append("From Comprobante A ");
				sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante and B.Cod_Sunat = ? ");
				sql.append("Inner join TipoMoneda C on C.Moneda = A.Moneda ");
				sql.append("Where cast(A.FechaEmision as Date) = ? and A.EstadoDoc = 0 ) T ");
				sql.append("Where T.row between ? and  ? ");
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, tipo);
				ps.setDate(2, new java.sql.Date(fechaEmision.getTime()));
				ps.setInt(3, inicio);
				ps.setInt(4, fin);
				rs =ps.executeQuery();
				comprobantes = new ArrayList<Comprobante>();
				while(rs.next()){
					Comprobante comprobante = new Comprobante();
					ComprobantePk id = new ComprobantePk();
					id.setSerie(rs.getInt("Serie"));
					id.setNumero(rs.getString("Numero"));
					id.setCodigoComprobante(rs.getString("Cod_Comprobante"));
					comprobante.setDesSerie(rs.getString("Descri"));
					comprobante.setRuc(rs.getString("RUC"));
					comprobante.setCliente(rs.getString("Cliente"));
					comprobante.setMoneda(rs.getInt("Moneda"));
					comprobante.setDesMoneda(rs.getString("NombreMoneda"));
					comprobante.setFechaEmision(new Date(rs.getDate("FechaEmision").getTime()));
					comprobante.setFechaCancelacion(new Date(rs.getDate("FechaCancelacion").getTime()));
					comprobante.setDescuento(rs.getDouble("Dcto"));
					comprobante.setValorVenta(rs.getDouble("ValorVenta"));
					comprobante.setImpuesto(rs.getDouble("Impuesto"));
					comprobante.setTCambio(rs.getDouble("TCambio"));
					comprobante.setId(id);
					comprobantes.add(comprobante);
				}	
			}
			datos.put("BANDEJA",comprobantes);
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

	public List<ComprobantePk> listarComprobantes(Date fechaEmision,int tipo){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ComprobantePk> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		try {
			con = Conexiones.getConexion();
			sql.delete(0, sql.length());
			sql.append("Select A.Serie,A.Numero,A.Cod_Comprobante ");
			sql.append("From Comprobante A ");
			sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante and B.Cod_Sunat = ? ");
			sql.append("Where cast(A.FechaEmision as Date) = ? and A.EstadoDoc = 0 ");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, tipo);
			ps.setDate(2, new java.sql.Date(fechaEmision.getTime()));
			rs =ps.executeQuery();
			comprobantes = new ArrayList<ComprobantePk>();
			while(rs.next()){
				ComprobantePk id = new ComprobantePk();
				id.setSerie(rs.getInt("Serie"));
				id.setNumero(rs.getString("Numero"));
				id.setCodigoComprobante(rs.getString("Cod_Comprobante"));
				comprobantes.add(id);
			}
		} catch (Exception e) {
			comprobantes = null;
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
		return comprobantes;
	}
}
