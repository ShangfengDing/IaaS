package appcloud.admin.action.system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcResourceStrategyClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreChangeRSAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7103947993864494591L;
	private static final Logger logger = Logger.getLogger(PreChangeRSAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcResourceStrategyClient acResourceStrategyClient = ClientFactory.getAcResourceStrategyClient();
	private Map<String, String> rsDescriptionMap = new HashMap<String, String>();
	private Map<String, String> rsNameMap = new HashMap<String, String>();
	private Integer id;
	private AcAggregate aggregate;
	/**
	 * 获取所有资源调度策略描述
	 */
	@Override
	public String execute() throws Exception {
		aggregate = aggregateClient.get(id);
		List<AcResourceStrategy> rsList = acResourceStrategyClient.getAll();
		logger.info("rsList.size() = " + rsList.size());
		Set<String> uuidSet = new HashSet<String>();
		for(AcResourceStrategy ac : rsList) {
			uuidSet.add(ac.uuid);
		}
		for(String uuid : uuidSet){
			List<AcResourceStrategy> list = acResourceStrategyClient.getByUuid(uuid);
			String showDes = "", showName = "";
			for(AcResourceStrategy ac : list) {
				showDes += ac.description + ";";
				showName += ac.name + ";";
			}
			rsDescriptionMap.put(uuid, showDes);
			rsNameMap.put(uuid, showName);
		}
		
		return SUCCESS;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcAggregate getAggregate() {
		return aggregate;
	}

	public void setAggregate(AcAggregate aggregate) {
		this.aggregate = aggregate;
	}


	public Map<String, String> getRsDescriptionMap() {
		return rsDescriptionMap;
	}


	public void setRsDescriptionMap(Map<String, String> rsDescriptionMap) {
		this.rsDescriptionMap = rsDescriptionMap;
	}


	public Map<String, String> getRsNameMap() {
		return rsNameMap;
	}


	public void setRsNameMap(Map<String, String> rsNameMap) {
		this.rsNameMap = rsNameMap;
	}
	
}
