package com.sharifpro.util.json;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.sharifpro.util.DateUtil;
 
/**
 * Used to serialize Java.util.Date, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
		return DateUtil.convertPersianStringToGregorian(parser.getText());
	}
 
}