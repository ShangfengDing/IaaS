package appcloud.api.manager.real;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.enums.AcStrategyTypeEnum;
import appcloud.api.manager.AcResourceStrategyManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;
import appcloud.common.proxy.ResourceStrategyProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class RealAcResourceStrategyManager implements AcResourceStrategyManager{
	private static Logger logger = Logger.getLogger(RealAcResourceStrategyManager.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(RealAcResourceStrategyManager.class);
	private ResourceStrategyProxy resourceStrategyProxy;
	private BeansGenerator generator;
	private static RealAcResourceStrategyManager manager = new RealAcResourceStrategyManager();
	
	public static RealAcResourceStrategyManager getInstance() {
		return manager;
	}
	
	private RealAcResourceStrategyManager () {
		super();
		generator = BeansGenerator.getInstance();
		resourceStrategyProxy = (ResourceStrategyProxy) ConnectionFactory.getTipProxy(ResourceStrategyProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public AcResourceStrategy get(String adminId, Integer resourceStrategyId)
			throws Exception {
		logger.info(String.format("Admin %s request to GET ResourceStrategys %s", adminId, resourceStrategyId));
		ResourceStrategy resourceStrategy = resourceStrategyProxy.getById(resourceStrategyId);
		if(resourceStrategy != null) 
			return generator.resourceStrategyToAcResourceStrategy(resourceStrategy);
		else 
			return new AcResourceStrategy();
	}
	@Override
	public List<AcResourceStrategy> getList(String adminId) throws Exception {
		logger.info(String.format("Admin %s request to GET ResourceStrategys", adminId));
		List<ResourceStrategy> resourceStrategys = (List<ResourceStrategy>) resourceStrategyProxy.findAll();
		List<AcResourceStrategy> acResourceStrategys = new ArrayList<AcResourceStrategy>();
		for(ResourceStrategy re : resourceStrategys) {
			acResourceStrategys.add(generator.resourceStrategyToAcResourceStrategy(re));
		}
		return acResourceStrategys;
	}
	
	@Override
	public AcResourceStrategy create(String adminId,
			AcResourceStrategy acResourceStrategy) throws Exception {
		logger.info(String.format("Admin %s request to CREATE ResourceStrategys %s", adminId, acResourceStrategy.name));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		StrategyTypeEnum strategyType = null;
		try {
			strategyType = StrategyTypeEnum.valueOf(acResourceStrategy.type.name());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResourceStrategy resourceStrategy = new ResourceStrategy(acResourceStrategy.id, acResourceStrategy.uuid,
				strategyType, acResourceStrategy.name, acResourceStrategy.description,
				acResourceStrategy.clazzs, acResourceStrategy.params, timestamp);
		
		resourceStrategyProxy.save(resourceStrategy);
		
		return acResourceStrategy;
	}

	@Override
	public AcResourceStrategy update(String adminId,
			Integer resourceStrategyId, AcResourceStrategy acResourceStrategy)
			throws Exception {
		logger.info(String.format("Admin %s request to UPDATE ResourceStrategys %s %s", adminId, resourceStrategyId, acResourceStrategy.name));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		StrategyTypeEnum strategyType = null;
		try {
			strategyType = StrategyTypeEnum.valueOf(acResourceStrategy.type.name());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResourceStrategy resourceStrategy = new ResourceStrategy(acResourceStrategy.id, acResourceStrategy.uuid,
				strategyType, acResourceStrategy.name, acResourceStrategy.description,
				acResourceStrategy.clazzs, acResourceStrategy.params, timestamp);
		
		resourceStrategyProxy.update(resourceStrategy);
		
		return generator.resourceStrategyToAcResourceStrategy(resourceStrategyProxy.getById(resourceStrategyId));
	}

	@Override
	public void delete(String adminId, Integer resourceStrategyId)
			throws Exception {
		logger.info(String.format("Admin %s request to DELETE ResourceStrategys %s", adminId, resourceStrategyId));
		resourceStrategyProxy.deleteById(resourceStrategyId);
	}

	@Override
	public List<AcResourceStrategy> searchByProperties(String adminId,
			Integer id, String uuid, StrategyTypeEnum type, String name,
			String description, String clazzs, String params, Timestamp time,
			Integer page, Integer size) throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search ResourceStrategy", adminId);
		QueryObject<ResourceStrategy> query = new QueryObject<ResourceStrategy>();
		if(id != null && !"".equals(id)) {
			logStr += ", id:" + id;
			query.addFilterBean(new FilterBean<ResourceStrategy>("id", id, FilterBeanType.EQUAL));
		}
		if(uuid != null && !"".equals(uuid)) {
			logStr += ", uuid:" + uuid;
			query.addFilterBean(new FilterBean<ResourceStrategy>("uuid", uuid, FilterBeanType.EQUAL));
		}
		if(type != null) {
			logStr += ", type:" + type.toString();
			query.addFilterBean(new FilterBean<ResourceStrategy>("type", type, FilterBeanType.EQUAL));
		}
		if(name != null && !"".equals(name)) {
			logStr += ", name:" + name;
			query.addFilterBean(new FilterBean<ResourceStrategy>("name", name, FilterBeanType.EQUAL));
		}
		if(description != null && !"".equals(description)) {
			logStr += ", description:" + description;
			query.addFilterBean(new FilterBean<ResourceStrategy>("description", description, FilterBeanType.EQUAL));
		}
		if(clazzs != null && !"".equals(clazzs)) {
			logStr += ", clazzs:" + clazzs;
			query.addFilterBean(new FilterBean<ResourceStrategy>("clazzs", clazzs, FilterBeanType.EQUAL));
		}
		if(params != null && !"".equals(params)) {
			logStr += ", params:" + params;
			query.addFilterBean(new FilterBean<ResourceStrategy>("params", params, FilterBeanType.EQUAL));
		}
		if(time != null) {
			logStr += ", time:" + time.toString();
			query.addFilterBean(new FilterBean<ResourceStrategy>("time", time, FilterBeanType.EQUAL));
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != null && page != 0)
			page -= 1;
		logger.info(logStr);
		//FIXME page size 暂未用到,ResourceStrategyProxy中未定义分页search接口
		List<ResourceStrategy> resourceStrategys = (List<ResourceStrategy>) resourceStrategyProxy.searchALL(query);//, page, size);
		List<AcResourceStrategy> acResourceStrategys = new ArrayList<AcResourceStrategy>();
		for(ResourceStrategy rs : resourceStrategys){
			acResourceStrategys.add(generator.resourceStrategyToAcResourceStrategy(rs));
		}
		return acResourceStrategys;
		
	}

	/**
	 * countByProperties暂不可用。ResourceStrategyProxy中未定义分页count接口
	 */
	@Override
	public String countByProperties(String adminId, Integer id, String uuid,
			AcStrategyTypeEnum type, String name, String description,
			String clazzs, String params, Timestamp time) throws Exception {
		// FIXME ResourceStrategyProxy中未定义分页count接口
		return null;
	}


}
