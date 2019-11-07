/**
 * File: FlavorServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import org.apache.log4j.Logger;

import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.FlavorService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 * 
 */
public class FlavorServiceImpl implements FlavorService {
	private static Logger logger = Logger.getLogger(FlavorServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, FlavorServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static VmInstanceTypeProxy vmInstanceTypeProxy = (VmInstanceTypeProxy) ConnectionManager.getInstance()
			.getDBProxy(VmInstanceTypeProxy.class);

	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "createFlavor: " + name + ", " + ram + ", " + disk
				+ ", " + vcpus;
		logger.info(paramInfos);
		
		String flavorUuid = UuidUtil.getRandomUuid();
		try {
			vmInstanceTypeProxy.save(new VmInstanceType(null, flavorUuid, name,
					vcpus, ram, disk));

			VmInstanceType vmInstanceType = null;
			vmInstanceType = vmInstanceTypeProxy.getByUuid(flavorUuid);
			loller.info(LolLogUtil.CREATE_FLAVOR, paramInfos, rpcExtra);
			if (vmInstanceType != null) {
				return vmInstanceType.getId();
			} else {
				return null;
			}
			
		} catch (Exception e) {
			String error = "createFlavor failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_FLAVOR, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("虚拟机配置创建失败", "输入参数为" + paramInfos, e);
			return null;
		}
	}

}
