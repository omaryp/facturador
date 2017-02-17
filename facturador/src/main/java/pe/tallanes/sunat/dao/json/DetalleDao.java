package pe.tallanes.sunat.dao.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.dao.util.Conexiones;
import pe.tallanes.sunat.model.ComprobantePk;
import pe.tallanes.sunat.model.json.Detalle;
import pe.tallanes.sunat.util.Unidad;

public class DetalleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DetalleDao.class);
	private static DetalleDao dao = null;
	private DetalleDao(){}
	
	public static DetalleDao getInstance(){
		if(dao == null){
			dao = new DetalleDao();
		}
		return dao;
	}
	
	public List<Detalle> listarDetalle(ComprobantePk id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Detalle> detalles = null;
		StringBuilder sql = new StringBuilder();
		try {
			con = Conexiones.getConexion();
			sql.append("Select cast(A.Cantidad as varchar(23)) cantidad,B.Cod_Articulo codPro,'' codProSunat,B.NombreArticulo,cast(A.Precio as varchar(23)) valUni,");
			sql.append("'0.00' dscto,cast(cast(((A.Precio*C.Porcentaje)/100) as decimal(12,2)) as varchar(15)) igv,");
			sql.append("'10' afecta,'0.00' montoIsc, '01' sistemaIsc, cast(A.Precio as varchar(23)) preUni  , cast(A.Total as varchar(15)) total ");
			sql.append("From  Articulos_Comprobante A ");
			sql.append("inner join Articulos B on A.Cod_Articulo = B.Cod_Articulo and A.CodTipoArticulo = B.CodTipoArticulo ");
			sql.append("inner join IGV C on C.Activado = 1 ");
		    sql.append("Where A.Serie = ? and A.Numero = ? and A.Cod_Comprobante = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, id.getSerie());
			ps.setString(2, id.getNumero());
			ps.setString(3, id.getCodigoComprobante());
			rs =ps.executeQuery();
			detalles = new ArrayList<Detalle>();
			while(rs.next()){
				Detalle detalle = new Detalle();
				detalle.setCodUnidadMedida(Unidad.GALONES.value());
				detalle.setCtdUnidadItem(rs.getString("cantidad"));
				detalle.setCodProducto(rs.getString("codPro"));
				detalle.setCodProductoSUNAT(rs.getString("codProSunat"));
				detalle.setDesItem(rs.getString("NombreArticulo"));
				detalle.setMtoValorUnitario(rs.getString("valUni"));
				detalle.setMtoDsctoItem(rs.getString("dscto"));
				detalle.setMtoIgvItem(rs.getString("igv"));
				detalle.setTipAfeIGV(rs.getString("afecta"));
				detalle.setMtoIscItem(rs.getString("montoIsc"));
				detalle.setTipSisISC(rs.getString("sistemaIsc"));
				detalle.setMtoPrecioVentaItem(rs.getString("preUni"));
				detalle.setMtoValorVentaItem(rs.getString("total"));
				detalles.add(detalle);
			}
		} catch (Exception e) {
			detalles = null;
			LOGGER.error("Error al cargar cabceras de los comprobantes.",e);
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
		return detalles;
	}

}
