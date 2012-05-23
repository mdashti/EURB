package com.sharifpro.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;

import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.util.json.SharifProObjectMapper;

/**
 * Util class. Contains some common methods that can be used
 * for any class
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Component
public class JsonUtil {
	/**
	 * Get list of Contacts from request.
	 * @param data - json data from request 
	 * @return list of Contacts
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public <T> List<T> getListFromRequest(Object data, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{

		List<T> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getObjectListFromJSON(data, clazz);

		} else { //it is only one object - cast to object/bean

			T contact = getObjectFromJSON(data, clazz);

			list = new ArrayList<T>();
			list.add(contact);
		}

		return list;
	}

	/**
	 * Transform json data format into object
	 * @param data - json data from request
	 * @return 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private <T> T getObjectFromJSON(Object data, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		T newContact = (T) objectMapper.readValue(data.toString(), clazz);
		return newContact;
	}

	/**
	 * Transform json data format into list of objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> getObjectListFromJSON(Object data, Class<T> clazz){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<T> newContacts = (List<T>) JSONArray.toCollection(jsonArray,clazz);
		return newContacts;
	}

	/**
	 * Tranform array of numbers in json data format into
	 * list of Integer
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListIdFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Integer> idList = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idList;
	}
	
	public String getJSONFromObject(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		return objectMapper.writeValueAsString(obj);
	}
	
	@Autowired
	public void setSharifProObjectMapper(SharifProObjectMapper sharifProObjectMapper) {
		this.objectMapper = sharifProObjectMapper;
	}
	
	private SharifProObjectMapper objectMapper;
	
	


	/**
	 * Generates modelMap to return in the modelAndView
	 * @param contacts
	 * @return
	 */
	public static Map<String,Object> getSuccessfulMap(List<? extends Object> objList, int total){

		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("totalCount", total);
		modelMap.put("data", objList);
		modelMap.put("success", true);

		return modelMap;
	}
	
	public static Map<String,Object> getSuccessfulMap(List<? extends Object> objList){
		return getSuccessfulMap(objList, objList.size());
	}
	
	public static Map<String,Object> getSuccessfulMapAfterStore(List<? extends Object> affectedIds){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("affectedIds", affectedIds);
		modelMap.put("success", true);

		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	public static Map<String,Object> getModelMapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("error", msg);
		modelMap.put("success", false);

		return modelMap;
	}
	
	public static void main(String[] args) {
		JsonUtil jutil = new JsonUtil();
		try {
			List<String[]> colMappingList = jutil.getListFromRequest("[[\"hhhh\",\"jjjj\"],[\"kkkk\",\"hhhh\"],[\"bbb\",\"hhhhkkk\"]]", String[].class);
			System.out.println(colMappingList);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
