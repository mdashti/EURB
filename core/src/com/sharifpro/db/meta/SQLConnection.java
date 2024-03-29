package com.sharifpro.db.meta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sharifpro.db.dialects.DialectFactory;
import com.sharifpro.db.dialects.DialectType;
import com.sharifpro.db.util.SQLUtilities;
import com.sharifpro.util.PropertyProvider;

/**
 * This represents a connection to an SQL server. it is basically a wrapper around
 * <TT>java.sql.Connection</TT>.
 */
public class SQLConnection implements ISQLConnection
{
	private ISQLDriver _sqlDriver;

	/** Logger for this class. */
	private final static Logger s_log = Logger.getLogger(SQLConnection.class);

	/** The <TT>java.sql.Connection</TT> this object is wrapped around. */
	private Connection _conn;

	/** Connectiopn properties specified when connection was opened. */
	private final SQLDriverPropertyCollection _connProps;

	private boolean _autoCommitOnClose = false;

	private Date _timeOpened;

	private Date _timeClosed;

	private SQLDatabaseMetaData metaData = null;

	public SQLConnection(Connection conn, SQLDriverPropertyCollection connProps, ISQLDriver sqlDriver)
	{
		super();
		_sqlDriver = sqlDriver;
		if (conn == null) { throw new IllegalArgumentException("SQLConnection == null"); }
		_conn = conn;
		_connProps = connProps;
		_timeOpened = Calendar.getInstance().getTime();
		metaData = new SQLDatabaseMetaData(this);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#close()
	 */
	public void close() throws SQLException
	{
		SQLException savedEx = null;
		if (_conn != null)
		{
			s_log.debug("Closing connection");
			try
			{
				if (!_conn.getAutoCommit())
				{
					if (_autoCommitOnClose)
					{
						_conn.commit();
					}
					else
					{
						_conn.rollback();
					}
				}
			}
			catch (SQLException ex)
			{
				savedEx = ex;
			}
			_conn.close();
			_conn = null;
			_timeClosed = Calendar.getInstance().getTime();
			if (savedEx != null)
			{
				s_log.debug("Connection close failed", savedEx);
				throw savedEx;
			}
			s_log.debug("Connection closed successfully");
		}
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#commit()
	 */
	public void commit() throws SQLException
	{
		validateConnection();
		_conn.commit();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#rollback()
	 */
	public void rollback() throws SQLException
	{
		validateConnection();
		_conn.rollback();
	}

	/**
	 * Retrieve the properties specified when connection was opened. This can be <TT>null</TT>.
	 * 
	 * @return Connection properties.
	 */
	public SQLDriverPropertyCollection getConnectionProperties()
	{
		return _connProps;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getAutoCommit()
	 */
	public boolean getAutoCommit() throws SQLException
	{
		validateConnection();
		return _conn.getAutoCommit();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setAutoCommit(boolean)
	 */
	public void setAutoCommit(boolean value) throws SQLException
	{
		validateConnection();
		final Connection conn = getConnection();
		final boolean oldValue = conn.getAutoCommit();
		if (oldValue != value)
		{
			_conn.setAutoCommit(value);
		}
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#isReadOnly()
	 */
	public boolean isReadOnly() throws SQLException
	{
		validateConnection();
		return _conn.isReadOnly();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setReadonly(boolean)
	 */
	public void setReadOnly(boolean value) throws SQLException
	{
		validateConnection();
		final Connection conn = getConnection();
		final boolean oldValue = conn.isReadOnly();
		if (oldValue != value)
		{
			_conn.setReadOnly(value);
		}
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getCommitOnClose()
	 */
	public boolean getCommitOnClose()
	{
		return _autoCommitOnClose;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getTransactionIsolation()
	 */
	public int getTransactionIsolation() throws SQLException
	{
		validateConnection();
		return _conn.getTransactionIsolation();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setTransactionIsolation(int)
	 */
	public void setTransactionIsolation(int value) throws SQLException
	{
		validateConnection();
		_conn.setTransactionIsolation(value);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setCommitOnClose(boolean)
	 */
	public void setCommitOnClose(boolean value)
	{
		_autoCommitOnClose = value;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#createStatement()
	 */
	public Statement createStatement() throws SQLException
	{
		validateConnection();
		return _conn.createStatement();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#prepareStatement(java.lang.String)
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		validateConnection();
		return _conn.prepareStatement(sql);
	}

	/**
	 * Retrieve the time that this connection was opened. Note that this time is the time that this
	 * <TT>SQLConnection</TT> was created, not the time that the <TT>java.sql.Connection</TT> object that it is
	 * wrapped around was opened.
	 * 
	 * @return Time connection opened.
	 */
	public Date getTimeOpened()
	{
		return _timeOpened;
	}

	/**
	 * Retrieve the time that this connection was closed. If this connection is still opened then <TT>null</TT>
	 * will be returned..
	 * 
	 * @return Time connection closed.
	 */
	public Date getTimeClosed()
	{
		return _timeClosed;
	}

	/**
	 * Retrieve the metadata for this connection.
	 * 
	 * @return The <TT>SQLMetaData</TT> object.
	 */
	public SQLDatabaseMetaData getSQLMetaData()
	{
		return metaData;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getConnection()
	 */
	public Connection getConnection()
	{
		/* This is extremely useful when trying to track down Swing UI freezing.
		 * However, it currently fills the log which obscures other debug 
		 * messages even though UI performance is acceptable, so it is commented 
		 * out until it is needed later.         
		if (s_log.isDebugEnabled()) {
		    try {
		        if (SwingUtilities.isEventDispatchThread() ) {
		            throw new Exception();
		        }
		    } catch (Exception e) {
		        s_log.debug("GUI thread doing database work", e);
		    }
		}
		*/
		return _conn;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getCatalog()
	 */
	public String getCatalog() throws SQLException
	{
		validateConnection();
		return getConnection().getCatalog();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#setCatalog(java.lang.String)
	 */
	public void setCatalog(String catalogName) throws SQLException
	{
		validateConnection();
		final Connection conn = getConnection();
		final String oldValue = conn.getCatalog();
		final DialectType dialectType = DialectFactory.getDialectType(metaData);
		if (!StringUtils.equals(oldValue, catalogName))
		{
			setDbSpecificCatalog(dialectType, catalogName);
		}
	}

	/**
	 * Decides which setCatalog method to call. Different databases have special requirements for this method
	 * so this just determines the database type and redirects to the appropriate db-specific or generic
	 * method.
	 * 
	 * @param dialectType
	 *           the type of database
	 * @param catalogName
	 *           the catalog name to use
	 * @throws SQLException
	 *            if an error occurs
	 */
	private void setDbSpecificCatalog(DialectType dialectType, String catalogName) throws SQLException
	{
		switch (dialectType)
		{
		case MSSQL:
			setMSSQLServerCatalog(catalogName);
			break;
		case INFORMIX:
			setInformixCatalog(catalogName);
			break;
		default:
			setGenericDbCatalog(catalogName);
			break;
		}
		;
	}

	/**
	 * @param catalogName
	 * @throws SQLException
	 */
	private void setGenericDbCatalog(String catalogName) throws SQLException
	{
		final Connection conn = getConnection();
		conn.setCatalog(catalogName);
	}

	/**
	 * MS SQL Server throws an exception if the catalog name contains a period without it being quoted.
	 * 
	 * @param catalogName
	 *           the catalog name to use
	 * @throws SQLException
	 *            if an error occurs
	 */
	private void setMSSQLServerCatalog(final String catalogName) throws SQLException
	{
		final Connection conn = getConnection();

		// Bug #1995728
		// MS-SQL is inconsistent with regard to setting the current catalog. If you have a database with
		// periods or spaces, then in some cases you must surround the catalog with quotes. For example, 
		// if you have a catalog named 'db with spaces' you must execute the following SQL:
		// 
		// use "db with spaces"
		//
		// However, the same is not always true for the JDBC API method Connection.setCatalog. For some old
		// versions of Microsoft drivers, you must quote the catalog as well. But for newer versions of the
		// driver, you must not quote the catalog. So here, we attempt to use the unquoted version first, then
		// if that fails, we will try quoting it.
		try
		{
			conn.setCatalog(catalogName);
			return;
		}
		catch (SQLException e)
		{
			s_log.error("Connection.setCatalog yielded an exception for catalog (" + catalogName + ") :"
				+ e.getMessage()+" - will try quoting the catalog next.", e);
		}
		conn.setCatalog(quote(catalogName));
	}

	/**
	 * Work-around for Informix catalog switching bugs.
	 * 
	 * @param catalogName
	 *           the catalog name to use
	 * @throws SQLException
	 *            if an error occurs
	 */
	private void setInformixCatalog(String catalogName) throws SQLException
	{
		final Connection conn = getConnection();
		Statement stmt = null;
		String sql = "DATABASE " + catalogName;
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
		}
		catch (SQLException e)
		{
			s_log.error("setInformixCatalog: failed to change database with the database SQL directive: " + sql);
		}
		finally
		{
			SQLUtilities.closeStatement(stmt);
		}
		// finally, try to set the catalog, which appears to be a NO-OP in the Informix driver.
		conn.setCatalog(catalogName);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getWarnings()
	 */
	public SQLWarning getWarnings() throws SQLException
	{
		validateConnection();
		return _conn.getWarnings();
	}

	protected void validateConnection() throws SQLException
	{
		if (_conn == null) { throw new SQLException(PropertyProvider.get("SQLConnection.noConn")); }
	}

	private String quote(String str)
	{
		// Bug #1995728 - Don't add quotes to an already quoted identifier
		if (str.startsWith("\"")) { return str; }

		String identifierQuoteString = "";
		try
		{
			identifierQuoteString = getSQLMetaData().getIdentifierQuoteString();
		}
		catch (SQLException ex)
		{
			s_log.debug("DBMS doesn't supportDatabasemetaData.getIdentifierQuoteString", ex);
		}
		if (identifierQuoteString != null && !identifierQuoteString.equals(" ")) { return identifierQuoteString
			+ str + identifierQuoteString; }
		return str;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLConnection#getSQLDriver()
	 */
	public ISQLDriver getSQLDriver()
	{
		return _sqlDriver;
	}

}
