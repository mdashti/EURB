package com.sharifpro.eurb.management.mapping.dao.impl;

import java.io.*;
import java.sql.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Generic Base class for DAO classes.
 *
 * This is a customizable template within FireStorm/DAO.
 */
public class AbstractDAO
{
	public final static int DEFAULT_PAGE_SIZE=25;
	public final static String DEFAULT_PAGE_SIZE_STR=DEFAULT_PAGE_SIZE+"";
	public static final String ASCENDING_SORT_ORDER = "ASC";
	public static final String DESCENDING_SORT_ORDER = "DESC";
	
	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

    public static byte[] getBlobColumn(ResultSet rs, int columnIndex)
            throws SQLException
    {
        try {
            Blob blob = rs.getBlob( columnIndex );
            if (blob == null) {
                return null;
            }

            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    bos.write( buffer, 0, c );
                    c = is.read( buffer );
                }
                return bos.toByteArray();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read BLOB column due to IOException: " + e.getMessage() );
        }
    }

    public static void setBlobColumn(PreparedStatement stmt, int parameterIndex, byte[] value)
            throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.BLOB );
        }
        else {
            stmt.setBinaryStream( parameterIndex, new ByteArrayInputStream(value), value.length );
        }
    }

    public static String getClobColumn(ResultSet rs, int columnIndex)
        throws SQLException
    {
        try {
            Clob clob = rs.getClob( columnIndex );
            if (clob == null) {
                return null;
            }

            StringBuffer ret = new StringBuffer();
            InputStream is = clob.getAsciiStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    ret.append( new String(buffer, 0, c) );
                    c = is.read( buffer );
                }
                return ret.toString();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read CLOB column due to IOException: " + e.getMessage() );
        }
    }

    public static void setClobColumn(PreparedStatement stmt, int parameterIndex, String value)
        throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.CLOB );
        }
        else {
            stmt.setAsciiStream( parameterIndex, new ByteArrayInputStream(value.getBytes()), value.length() );
        }
    }
}
