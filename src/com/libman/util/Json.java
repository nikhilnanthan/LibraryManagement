//$Id$
package com.libman.util;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Json {
	public static ObjectMapper jsonObject() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
