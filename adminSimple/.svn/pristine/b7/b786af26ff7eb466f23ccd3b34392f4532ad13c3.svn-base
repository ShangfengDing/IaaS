package appcloud.admin.action.price;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;

public class GetClusterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(GetClusterAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	private String name;
	private Integer pid = -1;
	
	public String execute() throws Exception{
		logger.info(pid+"----"+name);
		aggregates = aggregateClient.getAggregates();
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<AcAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
