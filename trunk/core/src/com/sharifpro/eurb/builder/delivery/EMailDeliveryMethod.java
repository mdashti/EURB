package com.sharifpro.eurb.builder.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sharifpro.eurb.builder.engine.output.ReportEngineOutput;
import com.sharifpro.eurb.builder.model.DeliveredReport;
import com.sharifpro.eurb.builder.model.MailMessage;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.builder.provider.MailProvider;
import com.sharifpro.eurb.builder.util.ByteArrayDataSource;
import com.sharifpro.eurb.builder.util.ReportConstants.ExportType;
import com.sharifpro.eurb.management.security.model.User;

public class EMailDeliveryMethod implements DeliveryMethod 
{
    protected static Logger log = Logger.getLogger(EMailDeliveryMethod.class.getName());
    
    private MailProvider mailProvider;
    
    public void deliverReport(ReportSchedule reportSchedule, ReportEngineOutput reportOutput) throws DeliveryException 
    {
        ArrayList<ByteArrayDataSource> htmlImageDataSources = new ArrayList<ByteArrayDataSource>();
        
        ByteArrayDataSource byteArrayDataSource = exportReport(reportOutput, reportSchedule, htmlImageDataSources);

        MailMessage mail = new MailMessage();               
        mail.setByteArrayDataSource(byteArrayDataSource);
        mail.addHtmlImageDataSources(htmlImageDataSources);          
        mail.setSender(reportSchedule.getUser().getEmail());
        mail.parseRecipients(reportSchedule.getRecipients());
        mail.setBounceAddress(reportSchedule.getDeliveryReturnAddress());
        
        if (reportSchedule.getScheduleDescription() != null && reportSchedule.getScheduleDescription().trim().length() > 0)
        {
            mail.setSubject(reportSchedule.getScheduleDescription());
        }
        else
        {
            mail.setSubject(reportSchedule.getReport().getName());
        }
        
        if (reportSchedule.getExportType() != ExportType.HTML.getCode())
        {
            mail.setText(reportSchedule.getReport().getName() + ": Generated on " + new Date());
        }

        try
        {
            mailProvider.sendMail(mail);
        }
        catch(Exception pe)
        {
            throw new DeliveryException(pe);
        }
        
        log.debug(byteArrayDataSource.getName() + " sent to: " + mail.formatRecipients(";"));        
    }
    
    public byte[] getDeliveredReport(DeliveredReport deliveredReport) throws DeliveryException 
    {        
        throw new DeliveryException("Method getDeliveredReport not implemented by EMailDeliveryMethod");
    }

    public List<DeliveredReport> getDeliveredReports(User user) throws DeliveryException 
    {        
        throw new DeliveryException("Method getDeliveredReports not implemented by EMailDeliveryMethod");
    }
    
    protected ByteArrayDataSource exportReport(ReportEngineOutput reportOutput, ReportSchedule reportSchedule,
            ArrayList<ByteArrayDataSource> htmlImageDataSources)
    {       
        String reportName = StringUtils.deleteWhitespace(reportSchedule.getReport().getName());

        ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(reportOutput.getContent(), reportOutput.getContentType());
        byteArrayDataSource.setName(reportName + reportOutput.getContentExtension());
        
        /*if (reportSchedule.getExportType() == ExportType.HTML.getCode()
                && reportSchedule.getReport().isJasperReport())
        {
            Map imagesMap = ((JasperReportEngineOutput) reportOutput).getImagesMap();

            for (Iterator entryIter = imagesMap.entrySet().iterator(); entryIter
                    .hasNext();)
            {
                Map.Entry entry = (Map.Entry) entryIter.next();

                ByteArrayDataSource imageDataSource = new ByteArrayDataSource(
                        (byte[]) entry.getValue(), getImageContentType((byte[]) entry
                                .getValue()));

                imageDataSource.setName((String) entry.getKey());

                htmlImageDataSources.add(imageDataSource);
            }
        }*/

        return byteArrayDataSource;
    }
    
    /**
     * Try to figure out the image type from its bytes.
     */
    /*private String getImageContentType(byte[] bytes)
    {
        String header = new String(bytes, 0, (bytes.length > 100) ? 100 : bytes.length);
        if (header.startsWith("GIF"))
        {
            return "image/gif";
        }

        if (header.startsWith("BM"))
        {
            return "image/bmp";
        }

        if (header.indexOf("JFIF") >= 0)
        {
            return "image/jpeg";
        }

        if (header.indexOf("PNG") >= 0)
        {
            return "image/png";
        }

        // We are out of guesses, so just guess tiff
        return "image/tiff";
    }*/
    
    public void setMailProvider(MailProvider mailProvider) 
    {
        this.mailProvider = mailProvider;
    }   
}
