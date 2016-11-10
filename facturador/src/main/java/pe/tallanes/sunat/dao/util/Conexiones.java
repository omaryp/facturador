/*
 * Copyright (C) 2010 - 2020 CMAC Piura S.A.C. <www.cmacpiura.com.pe>
 * Este archivo es propiedad intelectual de la CMAC Piura S.A.C.
 * desarrollado en el Area de Sistemas - Desarrollo.
 */
package pe.tallanes.sunat.dao.util;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Conexiones {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Conexiones.class);

	private static ConnectionPoolImpl pool;

	private Conexiones() {
	}

	public static void iniciarPool(String sUsuario, String sPassword) throws SQLException{
		try {
			if (pool == null) {
				pool = new ConnectionPoolImpl("com.microsoft.sqlserver.jdbc.SQLServerDriver",sUsuario, sPassword);
			}
		} catch (Exception e) {
			LOGGER.error("Error al crear pool de conexiones",e);
		}
	}

	public static void detener() {
		if (pool != null) {
			pool.close();
			pool = null;
		}
	}

	public static Connection getConexion() throws SQLException {
		return pool.getConnection();
	}
	
}