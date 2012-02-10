package com.sharifpro.db.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

/**
 * Indicates that during a persistence operation, no data was found in the
 * database.  Typically thrown as the result of a <code>load()</code> where
 * data was expected.
 */
public class NoDataFoundException extends PersistableObjectDaoException {
	private static final long serialVersionUID = -4491792591511762530L;

	/**
     * Constructs a NoDataFoundException with the specified message.
     * @param message the message
     */
    public NoDataFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a NoDataFoundException with the specified message, indicating
     * the exception was caused by the specified throwable.
     * @param message the message
     * @param cause the exception that caused this exception
     */
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
