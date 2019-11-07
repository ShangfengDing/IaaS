package appcloud.api.manager;

import java.util.Date;
import java.util.List;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;

public interface AcMessageLogManager {

	public AcMessageLog addLog(String tenantId, AcMessageLog log) throws Exception;
	
	public List<AcMessageLog> searchLog(String tenantId,
			AcMessageLog log, Date startDate, Date endDate, int count, int order)
	throws Exception;
	
	public AcMailConf getMailConf(String tenantId) throws Exception;
	
	public AcMailConf mailSet(String tenantId, AcMailConf mailConf) throws Exception;
	
}
