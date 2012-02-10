package com.sharifpro.db.dialects;

/**
 * A simple object to store user preferences regarding generated SQL scripts. When we are generating an SQL
 * script, we want to take into account the user's preferences.
 */
public class SqlGenerationPreferences
{
	private boolean qualifyTableNames = true;

	private boolean quoteColumnNames = true;

	private boolean quoteConstraintNames = true;
	
	/**
	 * @return the quoteConstraintNames
	 */
	public boolean isQuoteConstraintNames()
	{
		return quoteConstraintNames;
	}

	/**
	 * @param quoteConstraintNames the quoteConstraintNames to set
	 */
	public void setQuoteConstraintNames(boolean quoteConstraintNames)
	{
		this.quoteConstraintNames = quoteConstraintNames;
	}

	/**
	 * @return the quoteColumnNames
	 */
	public boolean isQuoteColumnNames()
	{
		return quoteColumnNames;
	}

	/**
	 * @param quoteColumnNames
	 *           the quoteColumnNames to set
	 */
	public void setQuoteColumnNames(boolean quoteColumnNames)
	{
		this.quoteColumnNames = quoteColumnNames;
	}

	private boolean quoteIdentifiers = true;

	private String sqlStatementSeparator = ";";

	/**
	 * Sets if table names have to be qualified.
	 * 
	 * @param qualifyTableNames
	 *           true if table names have to be qualified, false otherwise.
	 */
	public void setQualifyTableNames(boolean qualifyTableNames)
	{
		this.qualifyTableNames = qualifyTableNames;
	}

	/**
	 * @return true if table names have to be qualified, false otherwise.
	 */
	public boolean isQualifyTableNames()
	{
		return qualifyTableNames;
	}

	/**
	 * Sets if identifiers have to be quoted.
	 * 
	 * @param quoteIdentifiers
	 *           true if identifiers have to be quoted, false otherwise.
	 */
	public void setQuoteIdentifiers(boolean quoteIdentifiers)
	{
		this.quoteIdentifiers = quoteIdentifiers;
	}

	/**
	 * @return true if identifiers have to be quoted, false otherwise.
	 */
	public boolean isQuoteIdentifiers()
	{
		return quoteIdentifiers;
	}

	/**
	 * Sets the separator for sql statements.
	 * 
	 * @param sqlStatementSeparator
	 *           the separator for sql statements
	 */
	public void setSqlStatementSeparator(String sqlStatementSeparator)
	{
		this.sqlStatementSeparator = sqlStatementSeparator;
	}

	/**
	 * @return the separator for sql statements
	 */
	public String getSqlStatementSeparator()
	{
		return sqlStatementSeparator;
	}
}
