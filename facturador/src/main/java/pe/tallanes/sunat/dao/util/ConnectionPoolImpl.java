/*
 * Copyright (C) 2010 - 2020 CMAC Piura S.A.C. <www.cmacpiura.com.pe>
 * Este archivo es propiedad intelectual de la CMAC Piura S.A.C.
 * desarrollado en el Area de Sistemas - Desarrollo.
 */
/*
 * ConnectionPoolImpl.java
 *
 */
package pe.tallanes.sunat.dao.util;

/**
 *
 * @author Omar Yarleque
 */

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.tallanes.sunat.util.Params;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerPooledConnection;



public final class ConnectionPoolImpl  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolImpl.class);
	private SQLServerPooledConnection pool = null;

    public ConnectionPoolImpl(String driverName,String userName, String password){
        try {
            Class.forName(driverName);
            SQLServerConnectionPoolDataSource datasource = new SQLServerConnectionPoolDataSource();
            datasource.setUser(userName);
            datasource.setPassword(password);
            datasource.setServerName(Params.ip);
            datasource.setPortNumber(Params.port);
            datasource.setDatabaseName(Params.bd);
            pool = (SQLServerPooledConnection)datasource.getPooledConnection();
        }catch (ClassNotFoundException e) {
        	LOGGER.error("Error al cargar driver.",e);
        }
        catch (SQLException e) {
        	LOGGER.error("Error al crear pool de conexiones.",e);
        }
    }

    public void close() {
    	try {
    		pool.close();
            pool = null;
		} catch (Exception e) {
			LOGGER.error("Error al cerrar pool de conexiones.",e);
		}
        
    }

    public synchronized Connection getConnection(){
    	Connection con = null;
        try {
            con = pool.getConnection();
        } catch (SQLServerException e) {
        	LOGGER.error("Error al crear conexión del pool.",e);
        }
        return con;
    }

}