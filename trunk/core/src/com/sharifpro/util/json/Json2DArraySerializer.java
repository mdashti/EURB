package com.sharifpro.util.json;

import java.io.IOException;
import java.util.Date;
import java.util.List;
 
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
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
public class Json2DArraySerializer extends JsonSerializer<String>{
 
    @Override
    public void serialize(String arr2d, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        gen.writeStartArray();
        JsonUtil jutil = new JsonUtil();
		try {
			List<List> strArrList = jutil.getListFromRequest(arr2d, List.class);
			for(List<String> strArr : strArrList) {
				gen.writeStartArray();
				for(String s : strArr) {
					gen.writeString(s);
				}
				gen.writeEndArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gen.writeEndArray();
    }
 
}