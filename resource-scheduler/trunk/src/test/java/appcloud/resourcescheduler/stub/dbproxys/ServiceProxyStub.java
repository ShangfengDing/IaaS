// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.Service;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import appcloud.common.proxy.ServiceProxy;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.model.Service.ServiceTypeEnum;
;

public class ServiceProxyStub implements ServiceProxy{
	
	@Override
	public void save(Service arg0){
	}
	@Override
	public void delete(Integer arg0){
	}
	@Override
	public Service update(Service arg0){
		return new Service();
	}
	
	@Override
	public Service getLatestHostService(Integer arg0){
		return new Service();
	}
	@Override
	public List getServiceByType(ServiceTypeEnum arg0, boolean arg1){
		return new ArrayList();
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#getById(java.lang.Integer, boolean)
	 */
	@Override
	public Service getById(Integer id, boolean withHost) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#getUniqueService(java.lang.String, java.lang.Integer, boolean)
	 */
	@Override
	public Service getUniqueService(String hostIp, Integer monitorPort,
			boolean withHost) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#getHostServicesById(java.lang.Integer)
	 */
	@Override
	public List<? extends Service> getHostServicesById(Integer hostId) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#getHostServicesByUuid(java.lang.String)
	 */
	@Override
	public List<? extends Service> getHostServicesByUuid(String hostUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#searchAll(appcloud.common.util.query.QueryObject, boolean)
	 */
	@Override
	public List<? extends Service> searchAll(QueryObject<Service> queryObject,
			boolean withHost) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.ServiceProxy#findAll(boolean)
	 */
	@Override
	public List<? extends Service> findAll(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
