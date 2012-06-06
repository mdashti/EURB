package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

public class ReportExportOption implements Serializable
{	
	private static final long serialVersionUID = -8911923598920065987L;
	
	private Integer id;
	
	private boolean xlsRemoveEmptySpaceBetweenRows;
	private boolean xlsOnePagePerSheet;	
	private boolean xlsAutoDetectCellType;
	private boolean xlsWhitePageBackground;	
	 
	private boolean htmlRemoveEmptySpaceBetweenRows;	
	private boolean htmlWhitePageBackground;
	private boolean htmlUsingImagesToAlign;
	private boolean htmlWrapBreakWord; 
 	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}	

	public boolean isHtmlRemoveEmptySpaceBetweenRows()
	{
		return htmlRemoveEmptySpaceBetweenRows;
	}

	public void setHtmlRemoveEmptySpaceBetweenRows(boolean htmlRemoveEmptySpaceBetweenRows)
	{
		this.htmlRemoveEmptySpaceBetweenRows = htmlRemoveEmptySpaceBetweenRows;
	}

	public boolean isXlsRemoveEmptySpaceBetweenRows()
	{
		return xlsRemoveEmptySpaceBetweenRows;
	}

	public void setXlsRemoveEmptySpaceBetweenRows(boolean xlsRemoveEmptySpaceBetweenRows)
	{
		this.xlsRemoveEmptySpaceBetweenRows = xlsRemoveEmptySpaceBetweenRows;
	}

	public boolean isXlsOnePagePerSheet()
	{
		return xlsOnePagePerSheet;
	}

	public void setXlsOnePagePerSheet(boolean xlsOnePagePerSheet)
	{
		this.xlsOnePagePerSheet = xlsOnePagePerSheet;
	}

	public boolean isHtmlUsingImagesToAlign()
	{
		return htmlUsingImagesToAlign;
	}

	public void setHtmlUsingImagesToAlign(boolean htmlUsingImagesToAlign)
	{
		this.htmlUsingImagesToAlign = htmlUsingImagesToAlign;
	}

	public boolean isHtmlWhitePageBackground()
	{
		return htmlWhitePageBackground;
	}

	public void setHtmlWhitePageBackground(boolean htmlWhitePageBackground)
	{
		this.htmlWhitePageBackground = htmlWhitePageBackground;
	}

	public boolean isHtmlWrapBreakWord()
	{
		return htmlWrapBreakWord;
	}

	public void setHtmlWrapBreakWord(boolean htmlWrapBreakWord)
	{
		this.htmlWrapBreakWord = htmlWrapBreakWord;
	}

	public boolean isXlsAutoDetectCellType()
	{
		return xlsAutoDetectCellType;
	}

	public void setXlsAutoDetectCellType(boolean xlsAutoDetectCellType)
	{
		this.xlsAutoDetectCellType = xlsAutoDetectCellType;
	}

	public boolean isXlsWhitePageBackground()
	{
		return xlsWhitePageBackground;
	}

	public void setXlsWhitePageBackground(boolean xlsWhitePageBackground)
	{
		this.xlsWhitePageBackground = xlsWhitePageBackground;
	}
}