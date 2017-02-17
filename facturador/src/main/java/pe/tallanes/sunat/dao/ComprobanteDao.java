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
import pe.tallanes.sunat.util.Cadena;

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
	
	public Map<String, Object> listarComprobantes(Map<String,Object> parametros,int tipoBusqueda,int limites[]){
		//Date fechaInicio,Date fechaFin,int tipoComprobante,int tipoBusqueda
		int inicio = 0;
		int fin = 0;
		int total = 0;
		int tipoComprobante = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> datos = null;
		List<Comprobante> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		inicio = limites[0];
		fin = limites[1];
		String where = "";
		//Busqueda por fecha
		int fechaI = 0;
		int fechaF = 0;
		Date fechaInicio;
		Date fechaFin;
		//busqueda por comprobante
		ComprobantePk idBusqueda = null ;
		
		tipoComprobante = (Integer)parametros.get("COMPROBANTE");
		switch (tipoBusqueda) {
			case 1:		
				fechaInicio = (Date) parametros.get("INICIO");
				fechaFin = (Date) parametros.get("FIN");
				fechaI = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaInicio.getTime())));
				fechaF = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaFin.getTime())));
				where = "Where YEAR(A.FechaEmision)*10000+MONTH(A.FechaEmision)*100 + DAY(A.FechaEmision) between ? and ? and ";
				break;
			case 2:		
				idBusqueda = (ComprobantePk)parametros.get("ID");
				where = "Where A.Serie = ? and A.Numero = ? and ";
				break;
		}
		
		//int [] fechaI = Cadena.separarFecha(new java.sql.Date(fechaInicio.getTime()));
		//int [] fechaF = Cadena.separarFecha(new java.sql.Date(fechaFin.getTime()));
		try {
			datos = new HashMap<String, Object>();
			con = Conexiones.getConexion();
			sql.append("Select count(*) From Comprobante A ");
			sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante  ");
			//sql.append("Where YEAR(A.FechaEmision) = ? and MONTH(A.FechaEmision) = ? and DAY(A.FechaEmision) = ? and " );
			sql.append(where);
			sql.append("A.EstadoDoc = 0 and B.Cod_Sunat = ? ");
			ps =  con.prepareStatement(sql.toString());
			switch (tipoBusqueda) {
				case 1:
					ps.setInt(1,fechaI);
					ps.setInt(2,fechaF);
					break;
				case 2:
					ps.setString(1,idBusqueda.getSerie());
					ps.setString(2,idBusqueda.getNumero());
					break;
			}
			ps.setInt(3,tipoComprobante);
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
				sql.append("Select ROW_NUMBER() OVER (ORDER BY A.Cliente asc) as row,A.Serie,A.Cod_Comprobante,B.Descripción as Descri,A.Numero,A.RUC,REPLACE(A.Cliente,'&',' ') Cliente,");
				sql.append("A.Moneda,C.NombreMoneda ,A.FechaEmision,A.FechaCancelacion,A.Dcto,A.ValorVenta,A.Impuesto,A.TCambio ");
				sql.append("From Comprobante A ");
				sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante ");
				sql.append("Inner join TipoMoneda C on C.Moneda = A.Moneda ");
				//sql.append("Where YEAR(A.FechaEmision)*10000+MONTH(A.FechaEmision)*100 + DAY(A.FechaEmision) between ? and ?  " );
				sql.append(where);
				sql.append(" A.EstadoDoc = 0 and B.Cod_Sunat = ? ) T ");
				//sql.append("Where YEAR(A.FechaEmision) = ? and MONTH(A.FechaEmision) = ? and DAY(A.FechaEmision) = ? and A.EstadoDoc = 0 and B.Cod_Sunat = ? ) T ");
				sql.append("Where T.row between ? and  ? ");
				ps = con.prepareStatement(sql.toString());
				switch (tipoBusqueda) {
					case 1:
						ps.setInt(1,fechaI);
						ps.setInt(2,fechaF);
						break;
					case 2:
						ps.setString(1,idBusqueda.getSerie());
						ps.setString(2,idBusqueda.getNumero());
						break;
				}
				//ps.setInt(1,fechaI);
				//ps.setInt(2,fechaF);
				//ps.setInt(1, fecha[0]);
				//ps.setInt(2, fecha[1]);
				//ps.setInt(3, fecha[2]);
				ps.setInt(3,tipoComprobante);
				ps.setInt(4, inicio);
				ps.setInt(5, fin);
				rs =ps.executeQuery();
				comprobantes = new ArrayList<Comprobante>();
				while(rs.next()){
					Comprobante comprobante = new Comprobante();
					ComprobantePk id = new ComprobantePk();
					id.setSerie(rs.getString("Serie"));
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

	public List<ComprobantePk> listarComprobantes(Map<String,Object> parametros,int tipoBusqueda){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ComprobantePk> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		int tipoComprobante = 0;
		String where = "";
		//Busqueda por fecha
		int fechaI = 0;
		int fechaF = 0;
		Date fechaInicio;
		Date fechaFin;
		//busqueda por comprobante
		ComprobantePk idBusqueda = null ;
		
		tipoComprobante = (Integer)parametros.get("COMPROBANTE");
		switch (tipoBusqueda) {
			case 1:		
				fechaInicio = (Date) parametros.get("INICIO");
				fechaFin = (Date) parametros.get("FIN");
				fechaI = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaInicio.getTime())));
				fechaF = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaFin.getTime())));
				where = "Where YEAR(A.FechaEmision)*10000+MONTH(A.FechaEmision)*100 + DAY(A.FechaEmision) between ? and ? and ";
				break;
			case 2:		
				idBusqueda = (ComprobantePk)parametros.get("ID");
				where = "Where A.Serie = ? and A.Numero = ? and ";
				break;
		}
		//int fechaI = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaInicio.getTime())));
		//int fechaF = Integer.parseInt(Cadena.formatoDate(new java.sql.Date(fechaFin.getTime())));
		//int [] fechaI = Cadena.separarFecha(new java.sql.Date(fechaInicio.getTime()));
		//int [] fechaF = Cadena.separarFecha(new java.sql.Date(fechaFin.getTime()));
		try {
			con = Conexiones.getConexion();
			sql.delete(0, sql.length());
			sql.append("Select A.Serie,A.Numero,A.Cod_Comprobante ");
			sql.append("From Comprobante A ");
			sql.append("Inner join TipoComprobante B on B.Cod_Comprobante = A.Cod_Comprobante and B.Cod_Sunat = ? ");
			//sql.append("Where YEAR(A.FechaEmision)*10000+MONTH(A.FechaEmision)*100 + DAY(A.FechaEmision) between ? and ? and A.EstadoDoc = 0 ");
			sql.append(where);
			sql.append(" A.EstadoDoc = 0 and B.Cod_Sunat = ? ");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1,tipoComprobante);
			switch (tipoBusqueda) {
				case 1:
					ps.setInt(2,fechaI);
					ps.setInt(3,fechaF);
					break;
				case 2:
					ps.setString(2,idBusqueda.getSerie());
					ps.setString(3,idBusqueda.getNumero());
					break;
			}
			//ps.setInt(1, tipo);
			//ps.setInt(2,fechaI);
			ps.setInt(4,tipoComprobante);
			//ps.setInt(2, fecha[0]);
			//ps.setInt(3, fecha[1]);
			//ps.setInt(4, fecha[2]);
			rs =ps.executeQuery();
			comprobantes = new ArrayList<ComprobantePk>();
			while(rs.next()){
				ComprobantePk id = new ComprobantePk();
				id.setSerie(rs.getString("Serie"));
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
