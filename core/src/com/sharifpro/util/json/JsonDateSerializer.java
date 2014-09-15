package com.sharifpro.util.json;

import java.io.IOException;
import java.util.Date;
 
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import com.sharifpro.util.DateUtil;
 
/**
 * Used to serialize Java.util.Date, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date>{
 
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        gen.writeString(DateUtil.convertGregorianToPersianString(date));
    }
 
}