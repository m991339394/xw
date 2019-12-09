package io.renren.common.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    
    public static String getJsonValue(String JsonStr,String key){
        try {
        	JSONObject jsonObj = new JSONObject(JsonStr);
//            System.out.println(jsonObj);
            return jsonObj.getString(key);
        } catch (JSONException e) {
        	return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
	private final static ObjectMapper objectMapper = new ObjectMapper();

	public static ObjectMapper getInstance() {

		return objectMapper;
	}

	/**
	 * javaBean,list,array convert to json string
	 */
	public static String toJson2(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * json string convert to javaBean
	 */
	public static <T> T toBean(String jsonStr, Class<T> clazz) throws Exception {
		return objectMapper.readValue(jsonStr, clazz);
	}

	/**
	 * json string convert to map
	 */
	public static <T> Map<String, Object> toMap(String jsonStr) throws Exception {
		if (jsonStr != null && !"".equals(jsonStr)) {
			return objectMapper.readValue(jsonStr, Map.class);
		} else {
			return null;
		}
			
		
	}

	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> toMapBean(String jsonStr, Class<T> clazz) throws Exception {
		Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
		});
		Map<String, T> result = new HashMap<String, T>();
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
		}
		return result;
	}

	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) throws Exception {
		List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
		});
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(mapToBean(map, clazz));
		}
		return result;
	}

	/**
	 * map convert to javaBean
	 */
	public static <T> T mapToBean(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}
	
	/*
	 * 有问题：缺少方法
	 * public static MappingJacksonValue jsonp(Object value,String callback){
	 * MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
	 * mappingJacksonValue.setJsonpFunction(callback); return mappingJacksonValue; }
	 */

}
