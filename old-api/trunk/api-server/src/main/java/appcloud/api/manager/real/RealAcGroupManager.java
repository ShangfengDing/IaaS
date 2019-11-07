package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.manager.util.StringUtils;
import appcloud.common.util.UuidUtil;
import org.apache.log4j.Logger;

import appcloud.api.beans.AcGroup;
import appcloud.api.manager.AcGroupManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.VmGroup;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealAcGroupManager implements AcGroupManager{

	private static Logger logger = Logger.getLogger(RealAcGroupManager.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(RealAcGroupManager.class);
	
	private VmGroupProxy groupProxy;
	private BeansGenerator  generator;
	
	private static RealAcGroupManager manager = new RealAcGroupManager();
	public static RealAcGroupManager getInstance() {
		return manager;
	}
	private RealAcGroupManager () {
		super();
		generator = BeansGenerator.getInstance();
		groupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public List<AcGroup> getList(String adminId) throws Exception {
		logger.info(String.format("Admin %s request to GET GROUPS", adminId));
		List<VmGroup> vmGroups = (List<VmGroup>) groupProxy.findAll();
		List<AcGroup> acGroups = new ArrayList<AcGroup>();
		for(VmGroup vmGroup : vmGroups)
			acGroups.add(generator.groupToAcGroup(vmGroup));
		return acGroups;
	}

	@Override
	public AcGroup get(String adminId, Integer groupId) throws Exception {
		logger.info(String.format("Admin %s request to GET GROUP %s", adminId, groupId));
		VmGroup vmGroup = groupProxy.getById(groupId);
		return generator.groupToAcGroup(vmGroup);
	}

	@Override
	public AcGroup create(String adminId, AcGroup group) throws Exception {
		logger.info(String.format("Admin %s request to CREATE GROUP %s", adminId, group));
		
		VmGroup vmGroup = generator.acGroupToVmGroup(group);
		String secretKey = UuidUtil.getRandomUuid();
		vmGroup.setSecretKey(secretKey);

		groupProxy.save(vmGroup);
		return group;
	}

	@Override
	public AcGroup update(String adminId, Integer groupId, AcGroup group) throws Exception {
		logger.info(String.format("Admin %s request to UPDATE GROUP %s, %s", adminId, groupId, group));
		
		VmGroup vmGroup = generator.acGroupToVmGroup(group);
		vmGroup.setId(groupId);

		// 对是否有权修改该群组鉴权
		String secretKey = group.secretKey;
		VmGroup temp = groupProxy.getById(groupId);
		if (StringUtils.isEmpty(secretKey) || !secretKey.equals(temp.getSecretKey())) {
			return null;
		}

		groupProxy.update(vmGroup);
		
		vmGroup = groupProxy.getById(groupId);
		return generator.groupToAcGroup(vmGroup);
	}

	@Override
	public void delete(String adminId, Integer groupId) throws Exception {
		logger.info(String.format("Admin %s request to DELETE GROUP %s", adminId, groupId));
		groupProxy.deleteById(groupId);
	}
	
	@Override
	public List<AcGroup> getByClusterId(String adminId, Integer clusterId)
			throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search ResourceStrategy", adminId);
		QueryObject<VmGroup> query = new QueryObject<VmGroup>();
		if (clusterId != null && !"".equals(clusterId)) {
			logStr += ", clusterId:" + clusterId;
			query.addFilterBean(new FilterBean<VmGroup>("availableClusters", String.valueOf(clusterId),
					FilterBeanType.BOTH_LIKE));
			logger.info(logStr);
			List<VmGroup> vmGroups = (List<VmGroup>)groupProxy.searchAll(query);
			List<AcGroup> acGroups = new ArrayList<AcGroup>();
			for(VmGroup group : vmGroups) {
				acGroups.add(generator.vmGroupToAcGroup(group));
			}
			return acGroups;
		} else
			return null;
	}

}
