package com.sharifpro.eurb.builder.engine.input;

import java.util.Locale;
import java.util.Map;

import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.util.ReportConstants.ExportType;

/**
 * ReportEngineInput contains information needed to generate a report by the
 * ReportEngine. Report, parameters, and exportType are required.
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */

public class ReportEngineInput
{		
	private ReportDesign report;
	private Map<String,Object> parameters;
	private ExportType exportType;	
	private Locale locale;
    
    private String xmlInput;  
	
	// JasperReports only
	private Map imagesMap;	
	private boolean inlineImages;
	//
	
	public ReportEngineInput(ReportDesign report, Map<String,Object> parameters)
	{
		this.report = report;
		this.parameters = parameters;
	}

	public Map<String,Object> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String,Object> parameters)
	{
		this.parameters = parameters;
	}

	public ReportDesign getReport()
	{
		return report;
	}

	public void setReport(ReportDesign report)
	{
		this.report = report;
	}	

	public ExportType getExportType()
	{
		return exportType;
	}

	public void setExportType(ExportType exportType)
	{
		this.exportType = exportType;
	}

	public Map getImagesMap()
	{
		return imagesMap;
	}

	public void setImagesMap(Map imagesMap)
	{
		this.imagesMap = imagesMap;
	}

	public boolean isInlineImages()
	{
		return inlineImages;
	}

	public void setInlineImages(boolean inlineImages)
	{
		this.inlineImages = inlineImages;
	}
    
    public String getXmlInput() 
    {
        return xmlInput;
    }
    
    public void setXmlInput(String xmlInput) 
    {
        this.xmlInput = xmlInput;
    }

	public Locale getLocale() 
	{
		return locale;
	}

	public void setLocale(Locale locale) 
	{
		this.locale = locale;
	}	
}
