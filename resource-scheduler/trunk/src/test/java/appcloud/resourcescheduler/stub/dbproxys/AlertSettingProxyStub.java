// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.AlertSetting;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import appcloud.common.proxy.AlertSettingProxy;
;

public class AlertSettingProxyStub implements AlertSettingProxy{
	
	@Override
	public void save(AlertSetting arg0) throws Exception{
	}
	@Override
	public void update(AlertSetting arg0) throws Exception{
	}
	@Override
	public List findAll() throws Exception{
		return new ArrayList();
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public AlertSetting getById(Integer arg0) throws Exception{
		return new AlertSetting();
	}

}
