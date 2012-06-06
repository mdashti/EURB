package com.sharifpro.eurb.builder.provider;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.util.PropertyProvider;

public class DirectoryProvider  extends AbstractDAO implements ServletContextAware
{

	public static final String BASE_DIRECTORY = "base.directory";
	public static final String TEMP_DIRECTORY = "temp.directory";
	public static final String REPORT_GENERATION_DIRECTORY = "report.generation.directory";
	
	protected static Logger log = Logger.getLogger(DirectoryProvider.class.getName());

	private String reportDirectory;
	private String reportImageDirectory;
	private String reportGenerationDirectory;
	private String tempDirectory;
	private String separator = System.getProperty("file.separator");
	private ServletContext context;

	
	public DirectoryProvider() throws PersistableObjectDaoException
	{
		init();
	}	
	
	protected void init() throws PersistableObjectDaoException
	{
		log.info("Loading BaseDirectory from OR_PROPERTIES table.");

		String baseDirectory = null;

		baseDirectory = PropertyProvider.get(BASE_DIRECTORY);
		
		if (baseDirectory == null || baseDirectory.trim().length() < 1)
		{
			log.info("BaseDirectory not set in OR_PROPERTIES table. Trying to get path from ServletContext.");

			try
			{
				baseDirectory = context.getRealPath("");
				baseDirectory = baseDirectory + separator + "reports";
			}
			catch (NullPointerException npe)
			{
				log.info("ServletActionContext not available.");
				baseDirectory = ".";
			}
		}
				
		reportDirectory = baseDirectory;
		if (!reportDirectory.endsWith(separator)) reportDirectory +=separator;		
		log.info("Report Directory: " + reportDirectory);
			
		reportImageDirectory = reportDirectory + "images" + separator;		
		log.info("Report Image Directory: " + reportImageDirectory);
		
		//set temp directory path for report virtualization and image generation
		String propertyValue = PropertyProvider.get(TEMP_DIRECTORY);		
		if (propertyValue != null && propertyValue.trim().length() > 0)
		{
			tempDirectory = propertyValue; 
			if (!tempDirectory.endsWith(separator)) tempDirectory +=separator;			
			log.info("TempDirectory: " + tempDirectory);
		}
		
		//set report generation directory path for storing generated reports
		propertyValue = PropertyProvider.get(REPORT_GENERATION_DIRECTORY);		
		if (propertyValue != null && propertyValue.trim().length() > 0)
		{
			reportGenerationDirectory = propertyValue;
			if (!reportGenerationDirectory.endsWith(separator)) reportGenerationDirectory +=separator;			
			log.info("ReportGenerationDirectory: " + reportGenerationDirectory);
		}		
		
		log.info("Created");
	}

	public String getReportDirectory()
	{
		return reportDirectory;
	}

	public void setReportDirectory(String reportDirectory)
	{
		this.reportDirectory = reportDirectory;
		if (!reportDirectory.endsWith(separator)) reportDirectory +=separator;
		
		reportImageDirectory = reportDirectory + "images" + separator;	
		
		log.info("Report Directory Changed To: " + reportDirectory); 
	}
	
	public String getReportImageDirectory()
	{
		return reportImageDirectory;
	}
	
	public String getTempDirectory()
	{
		return tempDirectory;
	}
	
	public void setTempDirectory(String tempDirectory)
	{
		this.tempDirectory = tempDirectory;
		if (!tempDirectory.endsWith(separator)) tempDirectory +=separator;		
		
		log.info("TempDirectory Changed To: " + tempDirectory); 
	}

	public String getReportGenerationDirectory()
	{
		return reportGenerationDirectory;
	}

	public void setReportGenerationDirectory(String reportGenerationDirectory)
	{
		this.reportGenerationDirectory = reportGenerationDirectory;
		if (!reportGenerationDirectory.endsWith(separator)) reportGenerationDirectory +=separator;
		
		log.info("ReportGenerationDirectory Changed To: " + reportGenerationDirectory); 
	}
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}