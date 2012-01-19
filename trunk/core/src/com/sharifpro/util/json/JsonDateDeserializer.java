package com.sharifpro.util.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;
 
/**
 * Used to serialize Java.util.Date, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Date>{
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
		try {
			return df.parse(parser.getText()+"+0330");
		} catch (ParseException e) {
			return null;
		}
	}
 
}