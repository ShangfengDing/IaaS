package appcloud.dbproxy.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigReader {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final TypeReference<List<ProxyUnit>> listType =  new TypeReference<List<ProxyUnit>>() {};
	
	@SuppressWarnings("unchecked")
	public static List<ProxyUnit> readFromJson(URL url) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(url);
		return (List<ProxyUnit>)mapper.readValue(url, listType);
	}
}
