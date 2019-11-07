package appcloud.nodeagent.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigReader {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final TypeReference<Map<String,ServiceUnit>> listType =  new TypeReference<Map<String,ServiceUnit>>() {};
	private static Logger logger = Logger.getLogger(ConfigReader.class);
	
	@SuppressWarnings("unchecked")
	public static Map<String,ServiceUnit> readFromJson(URL url) {
		logger.info("reading: " + url);
		Map<String,ServiceUnit> map =  Collections.EMPTY_MAP;
		try {
			map = mapper.readValue(url, listType);
		} catch (Exception e1) {
			logger.error("failed to read config: naservices",e1);
		} 
		for (Entry<String, ServiceUnit> e : map.entrySet()) {
			logger.debug(e.getKey() + " service: " +  e.getValue().getMainClass());
		}
		return map;
	}
	
}
