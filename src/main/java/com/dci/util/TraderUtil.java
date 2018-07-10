package com.dci.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TraderUtil {

	public static String getJsonValue(String body, String key) throws IOException {			
		JsonNode jsonNode = new ObjectMapper().readTree(body);
		return jsonNode.get(key).asText();		
	}
	
	public static String getJsonNestedValue(String body, String... keys) throws IOException {
		JsonNode jsonNode = new ObjectMapper().readTree(body);
		return jsonNode.get(keys[0]).get(keys[1]).asText();		
	}
}
