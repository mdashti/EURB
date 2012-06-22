package com.sharifpro.db.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
/**
 * String handling utilities.
 * 
 * This class contains additional methods, that not exists in commons-lang from apache.
 * Methods, that are identical to commons-lang are removed. e.g. {@link StringUtils#isEmpty(String)}. 
 * Some other methods (e.g. {@link #join(String[], String)} are still available, because they have a slightly different behavior as the methods in commons-lang.
 * 
 * @see StringUtils
 */
public class StringUtilities
{
	/**
	 * Clean the passed string. Replace whitespace characters with a single
	 * space. If a <TT>null</TT> string passed return an empty string. E.G.
	 * replace
	 *
	 * [pre]
	 * \t\tselect\t* from\t\ttab01
	 * [/pre]
	 *
	 * with
	 *
	 * [pre]
	 * select * from tab01
	 * [/pre]
	 *
	 * @param	str	String to be cleaned.
	 *
	 * @return	Cleaned string.
	 */
	public static String cleanString(String str)
	{
		final StringBuffer buf = new StringBuffer(str.length());
		char prevCh = ' ';

		for (int i = 0, limit = str.length(); i < limit; ++i)
		{
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch))
			{
				if (!Character.isWhitespace(prevCh))
				{
					buf.append(' ');
				}
			}
			else
			{
				buf.append(ch);
			}
			prevCh = ch;
		}

		return buf.toString();
	}

	/**
	 * Return the number of occurences of a character in a string.
	 *
	 * @param str	The string to check.
	 * @param ch	The character check for.
	 *
	 * @return	The number of times <tt>ch</tt> occurs in <tt>str</tt>.
	 */
	public static int countOccurences(String str, int ch)
	{
		if (StringUtils.isEmpty(str))
		{
			return 0;
		}

		int count = 0;
		int idx = -1;
		do
		{
			idx = str.indexOf(ch, ++idx);
			if (idx != -1)
			{
				++count;
			}
		}
		while (idx != -1);
		return count;
	}

	/**
	 * Split a string based on the given delimiter, but don't remove
	 * empty elements.
	 *
	 * @param	str			The string to be split.
	 * @param	delimiter	Split string based on this delimiter.
	 * <p />
     * <b>Not compatible to {@link StringUtils#split(String)}<b>
	 * @return	Array of split strings. Guaranteeded to be not null.
	 */
	public static String[] split(String str, char delimiter)
	{
		return split(str, delimiter, false);
	}

	/**
	 * Split a string based on the given delimiter, optionally removing
	 * empty elements.
	 *
	 * @param	str			The string to be split.
	 * @param	delimiter	Split string based on this delimiter.
	 * @param	removeEmpty	If <tt>true</tt> then remove empty elements.
	 * <p />
     * <b>Not compatible to {@link StringUtils#split(String)}<b>
	 * @return	Array of split strings. Guaranteeded to be not null.
	 */
	public static String[] split(String str, char delimiter,
										boolean removeEmpty)
	{
		// Return empty list if source string is empty.
		final int len = (str == null) ? 0 : str.length();
		if (len == 0)
		{
			return new String[0];
		}

		final List<String> result = new ArrayList<String>();
		String elem = null;
		int i = 0, j = 0;
		while (j != -1 && j < len)
		{
			j = str.indexOf(delimiter,i);
			elem = (j != -1) ? str.substring(i, j) : str.substring(i);
			i = j + 1;
			if (!removeEmpty || !(elem == null || elem.length() == 0))
			{
				result.add(elem);
			}
		}
		return result.toArray(new String[result.size()]);
	}
    
    /**
     * Joins the specified parts separating each from one another with the 
     * specified delimiter.  If delim is null, then this merely returns the 
     * concatenation of all the parts.
     * <p />
     * <b>Could not be replaced by {@link StringUtils#join(Object[], char)} because the handling of <code>null</code> as value is different<b>
     * 
     * @param parts the strings to be joined
     * @param delim the char(s) that should separate the parts in the result
     * @return a string representing the joined parts.
     */
    public static String join(String[] parts, String delim) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            result.append(part);
            if (delim != null && i < parts.length-1) {
                result.append(delim);
            }        
        }
        return result.toString();
    }
    
    public static String[] segment(String source, int maxSegmentSize) {
        ArrayList<String> tmp = new ArrayList<String>();
        if (source.length() <= maxSegmentSize) {
            return new String[] { source };
        }
        boolean done = false;
        int currBeginIdx = 0;
        int currEndIdx = maxSegmentSize;
        while (!done) {
            String segment = source.substring(currBeginIdx, currEndIdx);
            tmp.add(segment);
            if (currEndIdx >= source.length()) {
                done = true;
                continue;
            }
            currBeginIdx = currEndIdx;
            currEndIdx += maxSegmentSize;
            if (currEndIdx > source.length()) {
                currEndIdx = source.length();
            }
        }
        return tmp.toArray(new String[tmp.size()]);
    }
    
    public static int getTokenBeginIndex(String selectSQL, String token)
    {
       String lowerSel = selectSQL.toLowerCase();
       String lowerToken = token.toLowerCase().trim();

       int curPos = 0;
       int count = 0;
       while(-1 != curPos)
       {
          curPos = lowerSel.indexOf(lowerToken, curPos + lowerToken.length());

          if(-1 < curPos
                  && (0 == curPos || Character.isWhitespace(lowerSel.charAt(curPos-1)))
                  && (lowerSel.length() == curPos + lowerToken.length() || Character.isWhitespace(lowerSel.charAt(curPos + lowerToken.length())))
            )
          {
             return curPos;
          }
          // If we've loop through one time for each character in the string, 
          // then something must be wrong.  Get out!
          if (count++ > selectSQL.length()) {
              break;
          }
       }

       return curPos;
    }
    
    public static Byte[] getByteArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0 ) {
            return new Byte[0];
        }
        Byte[] result = new Byte[bytes.length]; 
        for (int i = 0; i < bytes.length; i++) {
            result[i] = Byte.valueOf(bytes[i]);
        }

        return result;
    }
    
    /**
     * Chops off the very last character of the given string.
     * <p />
     * <b>Could not be replaced by {@link StringUtils#chop(String)} because the handling of <code>\r\n</code> are different<b>
     * @param aString a string to chop
     * @return the specified string minus it's last character, or null for null
     *         or empty string for a string with length == 0|1.
     */
    public static String chop(String aString) {
        if (aString == null) {
            return null;
        }
        if (aString.length() == 0) {
            return "";
        }
        if (aString.length() == 1) {
            return "";
        }
        return aString.substring(0, aString.length()-1);
    }
    
    /**
     * Returns the platform-specific line separator, or "\n" if it is not defined for some reason.
     * 
     * @return the platform-specific line separator.
     */
    public static String getEolStr() {
   	 return System.getProperty("line.separator", "\n");
    }

   public static String escapeHtmlChars(String sql)
   {
      String buf = sql.replaceAll("&", "&amp;");
      buf = buf.replaceAll("<", "&lt;");
      buf = buf.replaceAll(">", "&gt;");
      buf = buf.replaceAll("\"", "&quot;");
      return buf;
   }

public static String toTwoDigitNum(int num) {
	return num < 10 && num >= 0 ? "0"+num : ""+num;
}
}
