//$Id$
package com.libman.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertPostData {
	public static Map convertPostData(String json)
	{
//		StringBuilder stringBuilder = new StringBuilder();
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//		Map<String, Object> jsonMap = null;
//		  char[] charBuffer = new char[128];  
//          int bytesRead = -1;  
//
//          try {
//			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
//			      stringBuilder.append(charBuffer, 0, bytesRead);
//			}
//			      ObjectMapper objectMapper = Json.jsonObject();
//					String data = stringBuilder.toString();
//					jsonMap = objectMapper.readValue(data, Map.class);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ObjectMapper objectMapper = Json.jsonObject();
		Map<String, Object> jsonMap = null;
		try {
			jsonMap = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonMap;
	}
}
