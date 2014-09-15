package com.sharifpro.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Provides a connection by means of a DataSource configured in the
 * web container.
 * <p>
 * The data source JNDI name is <code>java:comp/env/jdbc/PnetDB</code>.
 * </p>
 */
public class DataSourceConnectionProvider implements IConnectionProvider, Serializable {
	private static final long serialVersionUID = -2306731639323838221L;

	DataSource ds;
	
	public DataSourceConnectionProvider(DataSource ds) {
		this.ds = ds;
	}

    /**
     * Returns a connection provided by the data source.
     * @return a connection
     * @throws SQLException if there is a problem looking up the data source
     * or fetching a connection
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
