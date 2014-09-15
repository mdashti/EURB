package com.sharifpro.db.meta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Date;

public interface ISQLConnection {

    public interface IPropertyNames
    {
        String AUTO_COMMIT = "autocommit";
        String CATALOG = "catalog";
    }    
    
    void close() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    /**
     * Retrieve the properties specified when connection was opened. This can
     * be <TT>null</TT>.
     * 
     * @return	Connection properties.
     */
    SQLDriverPropertyCollection getConnectionProperties();

    boolean getAutoCommit() throws SQLException;

    void setAutoCommit(boolean value) throws SQLException;

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#isReadOnly()
	 */
	public boolean isReadOnly() throws SQLException;

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setReadonly(boolean)
	 */
	public void setReadOnly(boolean value) throws SQLException;

    boolean getCommitOnClose();

    int getTransactionIsolation() throws SQLException;

    void setTransactionIsolation(int value) throws SQLException;

    void setCommitOnClose(boolean value);

    Statement createStatement() throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;

    /**
     * Retrieve the time that this connection was opened. Note that this time
     * is the time that this <TT>SQLConnection</TT> was created, not the time
     * that the <TT>java.sql.Connection</TT> object that it is wrapped around
     * was opened.
     * 
     * @return	Time connection opened.
     */
    Date getTimeOpened();

    /**
     * Retrieve the time that this connection was closed. If this connection
     * is still opened then <TT>null</TT> will be returned..
     * 
     * @return	Time connection closed.
     */
    Date getTimeClosed();

    /**
     * Retrieve the metadata for this connection.
     * 
     * @return	The <TT>SQLMetaData</TT> object.
     */
    SQLDatabaseMetaData getSQLMetaData();

    Connection getConnection();

    String getCatalog() throws SQLException;

    void setCatalog(String catalogName) throws SQLException;

    SQLWarning getWarnings() throws SQLException;

    ISQLDriver getSQLDriver();

}