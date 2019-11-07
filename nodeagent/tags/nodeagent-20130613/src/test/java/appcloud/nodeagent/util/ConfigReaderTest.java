package appcloud.nodeagent.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ConfigReaderTest {

	@Test
	public void test(){
		Map<String, ServiceUnit> map = ConfigReader.readFromJson(ServiceManager.class.getClassLoader().getResource("naservices.json"));
		assertEquals(false, map.isEmpty());
	}

}
