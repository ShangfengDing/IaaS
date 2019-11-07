package appcloud.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;

public class TestObjectSerialization {
	private final static ObjectMapper mapper = new ObjectMapper();
	@Test
	public void infinitePointing() {
		Instance instance = new Instance();
		instance.setUuid("uuidiii1");
		J2EEApp app = new J2EEApp();
		app.setUuid("uuidapp1");
		List<Instance> instances = new ArrayList<Instance>();
		instances.add(instance);
		app.setInstances(instances);
		instance.setJ2eeApp(app);
		
		try {
			String result = mapper.writeValueAsString(instance);
			Instance i = mapper.readValue(result, Instance.class);
			Assert.assertEquals(i.getUuid(), instance.getUuid());
			Assert.assertEquals(i.getJ2eeApp().getUuid(), app.getUuid());
		} catch (JsonProcessingException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
		
	}

}
