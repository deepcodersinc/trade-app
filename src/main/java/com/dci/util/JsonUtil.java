package com.dci.util;

import java.io.IOException;

import com.dci.bot.exception.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum JsonUtil {
	
	INSTANCE;

	public String getJsonValue(String body, String key) throws ApplicationException {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(body);
			return jsonNode.get(key).asText();			
		} catch (IOException e) {
			throw new ApplicationException("Could not get data from Json String", e.getMessage(), ""); 
		}		
	}
	
	public String getJsonNestedValue(String body, String... keys) throws ApplicationException {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(body);
			return jsonNode.get(keys[0]).get(keys[1]).asText();
		} catch (IOException e) {
			throw new ApplicationException("Could not get data from Json String", e.getMessage(), ""); 
		}	
	}
	
	public String getJson(Object object) throws ApplicationException {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {			
			throw new ApplicationException("Could not convert to Json", e.getMessage(), ""); 
		}
	}
}
