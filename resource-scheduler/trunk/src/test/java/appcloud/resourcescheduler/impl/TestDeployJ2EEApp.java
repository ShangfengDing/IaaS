package appcloud.resourcescheduler.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.J2EEApp.J2EEAppStatusEnum;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.resourcescheduler.impl.ResourceSchedulerImpl;

public class TestDeployJ2EEApp {
	J2EEApp app;
	
	@Before
	public void setData() {		
		app = new J2EEApp();
		app.setCreateTime(new Timestamp(System.currentTimeMillis()));
		app.setDomainPrefix("hello");
		app.setDomainSuffix("free4lab.com");
		app.setId(1);
		app.setMaxMemory(512);
		app.setMinMemory(256);
		app.setStatus(J2EEAppStatusEnum.NOT_VERIFIED);
		app.setUuid(UUID.randomUUID().toString().replace("-", ""));
		app.setName("hello");
	}
	
	@Test
	public void TestDeploy() {
		//ResourceSchedulerService service = new ResourceSchedulerImpl();
		
		//boolean result = service.createJ2EEApp(app, 1, 1);
	}
	
	@After
	public void clearEnv() {
		
	}

}
