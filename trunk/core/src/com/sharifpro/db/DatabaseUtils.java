package com.sharifpro.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Provides a place to hold methods useful when using a database.
 *
 * @see com.sharifpro.db.DBBean
 */
public class DatabaseUtils {

    /**
     * Change a <code>Collection</code> of <code>String</code> objects into a comma
     * separated list suitable for inclusion in an "in" clause.
     *
     * @param stringCollection a <code>Collection</code> object containing zero
     * or more strings.
     * @return a <code>String</code> containing a csv of Strings.
     */
    public static String collectionToCSV(Collection<String> stringCollection) {
        return collectionToCSV(stringCollection, false);
    }


    /**
     * Change a <code>Collection</code> of <code>String</code> objects into a comma
     * separated list suitable for inclusion in an "in" clause.
     *
     * @param stringCollection a <code>Collection</code> object containing zero
     * or more strings.
     * @return a <code>String</code> containing a csv of Strings.
     */
    public static String collectionToCSV(Collection<String> stringCollection, boolean quoteValues) {
        if (stringCollection == null) {
            return "";
        }

        StringBuffer stringToReturn = new StringBuffer();
        for (Iterator<String> it = stringCollection.iterator(); it.hasNext();) {
            String listItem = it.next();

            if (stringToReturn.length() == 0) {
                stringToReturn.append((quoteValues?"'":"")+listItem+(quoteValues?"'":""));
            } else {
                stringToReturn.append(","+(quoteValues?"'":"")+listItem+(quoteValues?"'":""));
            }
        }

        return stringToReturn.toString();
    }

    /**
     * Sets a timestamp in a DBBean.  The values will be set to null
     * @param stmt a <code>PreparedStatement</code> or <code>CallableStatement</code>
     * which we are setting the timestamp for.  (CallableStatement is a subclass of
     * PreparedStatement.)
     *
     * @param index an <code>int</code> which indicates which parameter location
     * we are setting.
     * @param date a <code>Date</code> which we are going to set in the timestamp.
     * @throws SQLException if there is an error setting the timestamp in the
     * statement.
     */
    public static void setTimestamp(PreparedStatement stmt, int index, Date date) throws SQLException {
        if (date == null) {
            stmt.setNull(index, Types.TIMESTAMP);
        } else {
            stmt.setTimestamp(index, new Timestamp(date.getTime()));
        }
    }

    public static Date getTimestamp(ResultSet rs, int index) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(index);
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        } else {
            return null;
        }
    }

    public static void setInteger(PreparedStatement stmt, int index, String intString) throws SQLException {
        if (intString == null || intString.trim().length() == 0) {
            stmt.setNull(index, Types.INTEGER);
        } else {
            stmt.setInt(index, Integer.parseInt(intString));
        }
    }

    public static void setString(PreparedStatement stmt, int index, String stringValue) throws SQLException {
        stmt.setString(index, stringValue);
    }

    /**
     * Returns a Date constructed from the specified timestamp's millisecond
     * value, handling null values.
     * @param timestamp the timestamp from which to create the date
     * @return the date or null if the timestamp was null
     */
    public static Date makeDate(Timestamp timestamp) {
        return (timestamp == null ? null : new Date(timestamp.getTime()));
    }

    /**
     * Sets the given BigDecimal at the given index in the given statement.
     * If the BigDecimal is null, then null is set.
     * @param stmt the statement on which to set the bigdecimal
     * @param index the parameter index position to set the value
     * @param value the value; when null, <code>NUMERIC</code> null is set; otherwise <code>setBigDecimal</code>
     * is used
     * @throws SQLException if there is a problem setting the value
     */
    public static void setBigDecimal(PreparedStatement stmt, int index, BigDecimal value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NUMERIC);
        } else {
            stmt.setBigDecimal(index, value);
        }
    }


}
