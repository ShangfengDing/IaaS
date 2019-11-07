package appcloud.openapi.query.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.query.InstanceQuery;

public class InstanceQueryImpl implements InstanceQuery{

	public static Logger logger = Logger.getLogger(InstanceQueryImpl.class);
	private static InstanceQueryImpl instanceQuery = new InstanceQueryImpl();
	public static InstanceQueryImpl getInstance() {
		return instanceQuery;
	}
	private static VmInstanceProxy instanceProxy;
	private static VmVirtualInterfaceProxy interfaceProxy;
	public InstanceQueryImpl(){
		super();
		instanceProxy = (VmInstanceProxy)ConnectionFactory.getTipProxy(
				VmInstanceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		interfaceProxy = (VmVirtualInterfaceProxy)ConnectionFactory.getTipProxy(
				VmVirtualInterfaceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@SuppressWarnings("unchecked")
	public List<VmInstance> searchInstancesByProperties(Integer userId, String serverId, 
			String serverName, String status, Integer zoneId, Integer aggregateId, String hostId,
			String serverIp, Date startdate, Date endDate, Integer page, Integer size) throws Exception {
		// TODO Auto-generated method stub
		logger.info("search instances by user id, userId=" + userId);
		String logStr = null;
		logStr = String.format("User %s request to search INSTANCES", userId);

		QueryObject<VmInstance> query = new QueryObject<VmInstance>();

		if(userId != null && userId>0){
			query.addFilterBean(new FilterBean<VmInstance>("userId", userId, FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(serverId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("uuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(serverName != null){
			query.addFilterBean(new FilterBean<VmInstance>("name", serverName, FilterBeanType.BOTH_LIKE));
			logStr += ", serverName:" + serverName;
		}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal");
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		//host id
		if(hostId != null) {
			logStr += ", hostId:" + hostId;
			query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBeanType.EQUAL));
		}

		//server ip
		if(serverIp != null) {
			@SuppressWarnings("unchecked")
			List<VmVirtualInterface> vifs = (List<VmVirtualInterface>)interfaceProxy.getByIp(serverIp);
			logStr += ", severIP:" + serverIp;
			if(vifs.size() == 0){
				logger.info(logStr);
				return null;
			}
			List<String> instanceUuids = new ArrayList<String>();
			for(VmVirtualInterface vf : vifs)
				instanceUuids.add(vf.getInstanceUuid());
			query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBeanType.IN));

		}
		if(startdate != null){
			query.addFilterBean(new FilterBean<VmInstance>("scheduledTime", startdate, FilterBeanType.MORE_THAN));
			logStr += ", startDate:" + startdate;
		}
		if(endDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("launchedTime", endDate, FilterBeanType.LESS_THAN));
			logStr += ", endDate:" + endDate;
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page == null)
			page = 0;
		else if (page > 0) {
			page -= 1;
		}
		if (size == null)
			size = 0;
		logger.info(logStr);
		// with metadata, network
		return (List<VmInstance>) instanceProxy.searchAll(query, false, false, false, false, true, false, true, true, page, size);
	}

}
