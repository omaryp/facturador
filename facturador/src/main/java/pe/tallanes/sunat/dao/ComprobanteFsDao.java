package pe.tallanes.sunat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.ComprobanteFs;
import pe.tallanes.sunat.model.ComprobanteFsDto;
import pe.tallanes.sunat.model.ComprobanteFsPk;
import pe.tallanes.sunat.util.Cadena;
import pe.tallanes.sunat.util.Constante;

public class ComprobanteFsDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComprobanteFsDao.class);
	private static ComprobanteFsDao dao = null;
	private ComprobanteFsDao(){}
	
	public static ComprobanteFsDao getInstance(){
		if(dao == null){
			dao = new ComprobanteFsDao();
		}
		return dao;
	}
	
	public Map<String, Object> listarComprobantesFs(Map<String,Object> parametros,int limites[]){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Constante estado = null;
		Map<String, Object> datos = null;
		List<ComprobanteFs> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		int inicio = 0;
		int fin = 0;
		int total = 0;
		int index = 1;
		boolean camposEnBlanco = false;
		boolean historico = false; 
		String serie = "";
		String numero = "";
		StringBuilder documento = new StringBuilder();
		String where = "";
		inicio = limites[0];
		fin = limites[1];
		estado = (Constante)parametros.get("ESTADO");
		serie = parametros.get("SERIE").toString();
		numero = parametros.get("NUMERO").toString();
		historico = new Boolean(parametros.get("HISTORICO").toString()).booleanValue();
		camposEnBlanco = (serie.equals("") || numero.equals("")); 
		if(!camposEnBlanco){
			documento.append(serie);
			documento.append("-");
			documento.append(Cadena.completar(numero, 8, "0", false));
		}
		switch (estado) {
			case ENVIADOS:
				where = "Where FEC_ENVI <> '' and DES_OBSE = '-' ";
				break;
			case NO_ENVIADOS:
				where = "Where FEC_ENVI = '' and DES_OBSE <> '-' ";
				break;
			default:
				break;
		}
		try {
			datos = new HashMap<String, Object>();
			con = Conexiones.getConexionSQLite();
			sql.append("Select count(*) from (");
			sql.append("Select * From TXXXX_BANDFACT ");
			if(historico){
				sql.append("Union All ");
				sql.append("Select * From TXXXX_HISTORICA ");
			}
			sql.append(") T ");
			sql.append(where);
			if(!camposEnBlanco)
				sql.append(" and NUM_DOCU = ? ");
			ps =  con.prepareStatement(sql.toString());
			if(!camposEnBlanco)
				ps.setString(1,documento.toString());
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
				sql.append("Select NUM_RUC,TIP_DOCU,NUM_DOCU,FEC_CARG,FEC_GENE,FEC_ENVI,DES_OBSE,NOM_ARCH,IND_SITU,TIP_ARCH ");
				sql.append("From TXXXX_BANDFACT ");
				if(historico){
					sql.append("Union All ");
					sql.append("Select NUM_RUC,TIP_DOCU,NUM_DOCU,FEC_CARG,FEC_GENE,FEC_ENVI,DES_OBSE,NOM_ARCH,IND_SITU,TIP_ARCH ");
					sql.append("From TXXXX_HISTORICA ");
				}
				sql.append(") T ");
				sql.append(where);
				if(!camposEnBlanco)
					sql.append(" and NUM_DOCU = ? ");
				sql.append(" Order by FEC_ENVI ");
				sql.append(" LIMIT ?,? ");
				ps = con.prepareStatement(sql.toString());
				if(!camposEnBlanco){
					ps.setString(index,documento.toString());
					index++;
				}
				ps.setInt(index,inicio);
				index++;
				ps.setInt(index,fin);
				rs =ps.executeQuery();
				comprobantes = new ArrayList<ComprobanteFs>();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				while(rs.next()){
					ComprobanteFsPk id = new ComprobanteFsPk();
					ComprobanteFs comprobante = new ComprobanteFs();
					id.setRuc(rs.getString("NUM_RUC"));
					id.setTipoDocumento(rs.getString("TIP_DOCU"));
					id.setNumeroDocumento(rs.getString("NUM_DOCU"));
					comprobante.setFechaCarga(format.parse(rs.getString("FEC_CARG")));
					comprobante.setFechaGenerado(format.parse(rs.getString("FEC_GENE")));
					comprobante.setFechaEnvio(format.parse(rs.getString("FEC_ENVI")));
					comprobante.setObservaciones(rs.getString("DES_OBSE"));
					comprobante.setNombreArchivo(rs.getString("NOM_ARCH"));
					comprobante.setIndicadorSituacion(rs.getString("IND_SITU"));
					comprobante.setExtensionArchivo(rs.getString("TIP_ARCH"));
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

	public List<ComprobanteFsDto> listarComprobantesEnviadosFs(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ComprobanteFsDto> comprobantes = null;
		StringBuilder sql = new StringBuilder();
		try {
			con = Conexiones.getConexionSQLite();
			sql.append("Select NUM_RUC,TIP_DOCU,NUM_DOCU,NOM_ARCH,TIP_ARCH ");
			sql.append("From TXXXX_BANDFACT ");
			sql.append("Where FEC_ENVI <> '' and DES_OBSE = '-' ");
			ps = con.prepareStatement(sql.toString());
			rs =ps.executeQuery();
			comprobantes = new ArrayList<ComprobanteFsDto>();
			while(rs.next()){
				ComprobanteFsPk id = new ComprobanteFsPk();
				ComprobanteFsDto dto = new ComprobanteFsDto();
				id.setRuc(rs.getString("NUM_RUC"));
				id.setTipoDocumento(rs.getString("TIP_DOCU"));
				id.setNumeroDocumento(rs.getString("NUM_DOCU"));
				dto.setId(id);
				dto.setNombreArchivo(rs.getString("NOM_ARCH"));
				dto.setExtensionArchivo(rs.getString("TIP_ARCH"));
				comprobantes.add(dto);
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
	
	public void eliminarComprobantesFs(List<String> enviados){
		Connection con = null;
		PreparedStatement ps = null;
		int consulta = 0;
		try {
			con = Conexiones.getConexionSQLite();
			for (String sql : enviados) {
				ps = con.prepareStatement(sql.toString());
				ps.addBatch();
				consulta ++;
				if (consulta >= 100){
					ps.executeBatch();
					consulta = 0;
					ps.clearBatch();
				}
			}
			ps.addBatch();
		} catch (Exception e) {
			LOGGER.error("Error al eliminar los comprobantes.",e);
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
	}
	
	public void enviarComprobanteFsHistorico(){
		Connection con = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("Insert into TXXXX_HISTORICA ");
			sql.append("Select * from TXXXX_BANDFACT ");
			sql.append("Where FEC_ENVI <> '' and DES_OBSE = '-' ");
			con = Conexiones.getConexionSQLite();
			ps = con.prepareStatement(sql.toString());
			ps.addBatch();
			ps.executeBatch();
		} catch (Exception e) {
			LOGGER.error("Error al enviar al histórico !.",e);
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
	}
}
