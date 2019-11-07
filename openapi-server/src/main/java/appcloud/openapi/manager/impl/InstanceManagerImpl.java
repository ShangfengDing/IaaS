package appcloud.openapi.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Flavor;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.constant.ServerMetadata;
import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.manager.InstanceManager;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.operate.InstanceOperate;
import appcloud.openapi.operate.impl.InstanceOperateImpl;
import appcloud.openapi.query.InstanceQuery;
import appcloud.openapi.query.impl.InstanceQueryImpl;
/**
 *	此类主要用于构造一些对象参数，以传递给底层模块用于具体操作云主机实例
 *	@author hgm
 */
@Component
public class InstanceManagerImpl implements InstanceManager{

	private static Logger logger = Logger.getLogger(InstanceManagerImpl.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(InstanceManagerImpl.class);
	private static VmZoneProxy vmZoneProxy;
	private static VmUserProxy vmUserProxy;
	private static VmGroupProxy vmGroupProxy;
	private static VmSecurityGroupProxy vmSecurityGroupProxy;
	private InstanceQuery instanceQuery = new InstanceQueryImpl();
	private InstanceOperate instanceOperate = new InstanceOperateImpl();
	private static InstanceManagerImpl instanceManager = new InstanceManagerImpl();
	public static InstanceManagerImpl getInstance() {
		return instanceManager;
	}
	private InstanceManagerImpl() {
		super();
		vmZoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(
				VmZoneProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
				VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmSecurityGroupProxy= (VmSecurityGroupProxy) ConnectionFactory.getTipProxy(
				VmSecurityGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	public ServerCreateReq gainServerCreateReq(String appKeyId, Map<String, String> paramsMap) throws Exception {
		logger.info("gain create instance request, appKeyId=" + appKeyId);
		VmUser user = vmUserProxy.getByAppKeyId(appKeyId);
		if(null==user) {
			return null;
		}
		VmGroup userGroup = vmGroupProxy.getById(user.getGroupId());
		// Get the number of instance and ensure the current user has the privilege to create the new instance.
		List<VmInstance> instances = instanceQuery.searchInstancesByProperties(user.getUserId(), null, null, 
				null, null, null, null, null, null, null, null, null);
		if(instances.size()>userGroup.getMaxNumberOfInstance()) {
			logger.info("用户  " + user.getUserId() + " 申请的云主机数量已达到最大值！");
			return null;
		}
		/**
		 * create flavor
		 * 由于云主机名称和描述不是接口必填字段，故首先将云主机的名称和描述暂时置为用户userId，等云主机创建成功后，再将其修改过来
		 */
		Flavor newFlavor = instanceOperate.createFlavor(user.getUserId(), paramsMap.get(Constants.INSTANCE_TYPE), 
				Integer.parseInt(paramsMap.get(Constants.DATA_DISK1_SIZE)), paramsMap.get(Constants.IMAGE_ID) );
		logger.info("用户  " + user.getUserId() + " 第三方创建flavor成功");
		String flavorRef = newFlavor.id.toString();	//flavorRef传的是flavor的id
		String regionId = ( vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)) ) .getId() + "";
		String name = paramsMap.get(Constants.INSTANCE_NAME) == null ? user.getUserId()+"" : paramsMap.get(Constants.INSTANCE_NAME);
		String description = paramsMap.get(Constants.DESCRIPTION) == null ? user.getUserId()+"" : paramsMap.get(Constants.DESCRIPTION);;
		//将带宽参数放在metadata中，不申请带宽，公网带宽默认为0；将displayDescription放到metadata中
		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put(ServerMetadata.MAX_BANDWIDTH, paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT).toString());
		metadata.put(ServerMetadata.PRI_BANDWIDTH, ConnectionConfigs.PRIVATE_MAX_BANDWIDTH.toString());
		metadata.put(ServerMetadata.DISPLAY_DESCRIPTION, description);
		//选择防火墙
		SecurityGroup securityGroup = new SecurityGroup();
		securityGroup.id = vmSecurityGroupProxy.getByUuid(paramsMap.get(Constants.SECURITY_GROUP_ID), false).getId();
		ServerCreateReq serverCreateReq = new ServerCreateReq(
				paramsMap.get(Constants.IMAGE_ID), flavorRef, name, regionId, userGroup.getAvailableClusters(), metadata, securityGroup);
		return serverCreateReq;
	}

	public InstanceQuery getInstanceQuery() {
		return instanceQuery;
	}

	public void setInstanceQuery(InstanceQuery instanceQuery) {
		this.instanceQuery = instanceQuery;
	}

	public InstanceOperate getInstanceOperate() {
		return instanceOperate;
	}

	public void setInstanceOperate(InstanceOperate instanceOperate) {
		this.instanceOperate = instanceOperate;
	}
}
