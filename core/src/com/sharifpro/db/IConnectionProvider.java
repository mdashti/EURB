package com.sharifpro.db;

import java.sql.SQLException;

/**
 * Provides database connections.
 */
public interface IConnectionProvider {

    /**
     * Provides an open database connection.
     * An implementing class may choose to return null if no connections
     * are available.
     * @return the connection
     * @throws java.sql.SQLException if there is a problem getting the connection
     */
    public java.sql.Connection getConnection() throws SQLException;

}
