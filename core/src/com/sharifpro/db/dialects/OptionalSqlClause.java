package com.sharifpro.db.dialects;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.lang.StringUtils;

/**
 * There are a number of dialects which produce DDL statements which have "optional" clauses such
 * that when a value is provided, then a keyword must be used to give context to that value; but when
 * no value is specified, then that keyword must not be present.  For example, consider a create 
 * statement for sequences of the form:
 * 
 * CREATE SEQUENCE mysequence START WITH 1  
 * INCREMENTBY 1 MAXVALUE 100 
 * MINVALUE 1 CACHE 20 NOCYCLE
 * 
 * In this case, if the default start value is to be used, then "START WITH 1" must not be present. Same
 * goes for using a default increment ( INCREMENTBY 1 must not appear ).  So there is a variable part, 
 * that can be specified or not - in both aforementioned cases the value happens to be 1.  Then there is 
 * also a static part (that is, "START WITH", "INCREMENTBY", etc.).  This class is intended to place the 
 * logic for switching on the presence / absence of the value to include/exclude the static part respectively.
 *
 */
public class OptionalSqlClause
{
	
	private String _staticPart = null;
	private String _variablePart = null;
	private StringTemplate _st = null;
	private String _key = null;
	private String _value = null;
	
	public OptionalSqlClause(String staticPart, String variablePart) {
		_staticPart = staticPart;
		_variablePart = variablePart;
	}
		
	public OptionalSqlClause(StringTemplate st, String key, String value) {
		this._st = st;
		this._key = key;
		this._value = value;
	}
	
	public String toString() {
		String result = "";
		if (_st != null) {
			if (_value != null) {
				_st.setAttribute(_key, _value);
				result = _st.toString();
			}
		} else {
			if (!StringUtils.isEmpty(_variablePart)) {
				result = new StringBuilder(_staticPart).append(" ").append(_variablePart).toString();
			}
		}
		return result;
		
	}
}
