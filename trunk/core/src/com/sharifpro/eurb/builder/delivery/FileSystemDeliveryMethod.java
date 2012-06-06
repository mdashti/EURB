package com.sharifpro.eurb.builder.delivery;

import com.sharifpro.eurb.builder.engine.output.ReportEngineOutput;
import com.sharifpro.eurb.builder.model.DeliveredReport;
import com.sharifpro.eurb.builder.model.MailMessage;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.builder.provider.DirectoryProvider;
import com.sharifpro.eurb.builder.provider.MailProvider;
import com.sharifpro.eurb.management.security.model.User;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FileSystemDeliveryMethod implements DeliveryMethod
{  
    protected static Logger log = Logger.getLogger(FileSystemDeliveryMethod.class.getName());
    
    private MailProvider mailProvider;
    private DirectoryProvider directoryProvider;
    
    public void deliverReport(ReportSchedule reportSchedule, ReportEngineOutput reportOutput) throws DeliveryException 
    {
        ReportDesign report = reportSchedule.getReport();
        User user = reportSchedule.getUser();
        
        Date runDate = new Date();
        
        String fileName = runDate.getTime() + "-"
                + StringUtils.deleteWhitespace(user.getUsername()) + "-"
                + StringUtils.deleteWhitespace(report.getName());                       
        
        try
        {
            FileOutputStream file = new FileOutputStream(directoryProvider
                .getReportGenerationDirectory()
                + fileName + reportOutput.getContentExtension());
            
            file.write(reportOutput.getContent());  
            file.flush();
            file.close();
        }
        catch(IOException ioe)
        {
            throw new DeliveryException(ioe);
        }        
        
        DeliveredReport info = new DeliveredReport();             
        info.setParameters(reportSchedule.getReportParameters());
        info.setReportDescription(reportSchedule.getScheduleDescription());
        info.setReportName(report.getName());
        info.setReportFileName(fileName + reportOutput.getContentExtension());
        info.setRunDate(runDate);
        info.setUserName(user.getUsername());
        info.setDeliveryMethod("fileSystemDeliveryMethod");
        
        try
        {
            FileOutputStream file = new FileOutputStream(directoryProvider.getReportGenerationDirectory() + fileName + ".xml");

            XStream xStream = new XStream();
            xStream.alias("reportGenerationInfo", DeliveredReport.class);
            xStream.toXML(info, file);
        
            file.flush();
            file.close();
        }
        catch(IOException ioe)
        {
            throw new DeliveryException(ioe);
        }   
        
        MailMessage mail = new MailMessage();               
        mail.setSender(user.getEmail());
        mail.parseRecipients(reportSchedule.getRecipients());
        mail.setText(report.getName() + ": Generated on " + new Date());
        mail.setBounceAddress(reportSchedule.getDeliveryReturnAddress());
        
        if (reportSchedule.getScheduleDescription() != null && reportSchedule.getScheduleDescription().trim().length() > 0)
        {
            mail.setSubject(reportSchedule.getScheduleDescription());
        }
        else
        {
            mail.setSubject(reportSchedule.getReport().getName());
        }      
        
        try
        {
            mailProvider.sendMail(mail);    
        }
        catch(Exception pe)
        {
            throw new DeliveryException(pe);
        }   
        
        log.debug(report.getName() + " written to: " + fileName);
    }
   
    public List<DeliveredReport> getDeliveredReports(User user) throws DeliveryException
    {        
        IOFileFilter extensionFilter = FileFilterUtils.suffixFileFilter("xml");
        
        File directory = new File(directoryProvider.getReportGenerationDirectory());

        ArrayList<DeliveredReport> deliveredReports = new ArrayList<DeliveredReport>();

        Iterator iterator = FileUtils.iterateFiles(directory, extensionFilter, null);
        while (iterator.hasNext())
        {
            File file = (File) iterator.next();

            if (FilenameUtils.wildcardMatch(file.getName(), "*" + user.getUsername() + "*"))
            {
                XStream xStream = new XStream();
                xStream.alias("reportGenerationInfo", DeliveredReport.class);
                
                try
                {
                    FileInputStream inputStream = new FileInputStream(file);
            
                    DeliveredReport report = (DeliveredReport) xStream.fromXML(inputStream);                    
            
                    deliveredReports.add(report);
            
                    inputStream.close();
                }
                catch(IOException io)
                {
                    log.warn(io.toString());
                }
            }
        }   

        return deliveredReports;
    }    

    public byte[] getDeliveredReport(DeliveredReport deliveredReport) throws DeliveryException
    {
        try
        {
            File file = new File(directoryProvider.getReportGenerationDirectory() + deliveredReport.getReportFileName());        
            return FileUtils.readFileToByteArray(file);
        }
        catch(IOException ioe)
        {
            throw new DeliveryException(ioe);
        }        
    }

    public void setDirectoryProvider(DirectoryProvider directoryProvider)
    {
        this.directoryProvider = directoryProvider;
    }
    
    public void setMailProvider(MailProvider mailProvider) 
    {
        this.mailProvider = mailProvider;
    }
}
