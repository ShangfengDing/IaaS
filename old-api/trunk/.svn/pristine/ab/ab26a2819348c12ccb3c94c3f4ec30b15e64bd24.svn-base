package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.api.manager.AcMessageLogManager;

public class FakeAcMessageLogManager implements AcMessageLogManager {

	@Override
	public AcMessageLog addLog(String tenantId, AcMessageLog log)
			throws Exception {
		return log;
	}

	@Override
	public List<AcMessageLog> searchLog(String tenantId, AcMessageLog log, 
			Date startDate, Date endDate, int count, int order)throws Exception {
		List<AcMessageLog> logs = new ArrayList<AcMessageLog>();
		logs.add(new AcMessageLog(AcModuleEnum.API_SERVER, "tranctionId1", "userId1","uuid1", "operateDrpt1", 
				"logContent1", "sourceClass1", "ipAddress1", AcLogLevelEnum.INFO, new Date()));
		logs.add(new AcMessageLog(AcModuleEnum.NETWORK_PROVIDER, "tranctionId2", "userId2", "uuid2", "operateDrpt2", 
				"logContent2", "sourceClass2", "ipAddress2", AcLogLevelEnum.WARN, new Date()));
		return logs;
	}

	@Override
	public AcMailConf getMailConf(String tenantId) throws Exception {
		Map<String, String> moduleInCharge = new HashMap<String, String>();
		moduleInCharge.put("api", "littlesnail90@gmail.com");
		moduleInCharge.put("s", "nidaye@gmail.com");
		System.out.println("fake get");
		return new AcMailConf("true", "smtp.163.com", "daye@126.com",
				"dayenihao", "hello daye", moduleInCharge);
	}

	@Override
	public AcMailConf mailSet(String tenantId, AcMailConf mailConf) throws Exception {
		System.out.println("fake set");
		return mailConf;
	}

}
