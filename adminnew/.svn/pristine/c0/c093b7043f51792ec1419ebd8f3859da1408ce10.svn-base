package appcloud.admin.action.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcGroupClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.admin.action.base.BaseAction;
public class NewAcGroupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NewAcGroupAction.class);
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private AcAggregateClient acAggregateClient = ClientFactory.getAggregateClient();
	
	private String name;
	private String description;
	private int max_number_of_instance;
	private int max_number_of_disk;
	private int max_number_of_backup;
	private int max_number_of_snapshot;
	private Boolean publish_image;
	private Boolean select_cluster;
	private String selected_aggregates;
	
	

	public String execute() {
		List<Integer> availableClusters = new ArrayList<Integer>();
		if(selected_aggregates != "") {
			String[] aggregates = selected_aggregates.split(",");
			for(int i=0; i<aggregates.length; i++) {
				availableClusters.add(new Integer(aggregates[i]));
			}
		}
		
		AcGroup acGroup = new AcGroup(null, name, select_cluster, publish_image,
				max_number_of_instance, max_number_of_disk,
				max_number_of_backup, max_number_of_snapshot,
				availableClusters, description,null);
		acGroupClient.create(acGroup);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "新建群组", "新建群组"+name, "NewAcGroupAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMax_number_of_instance() {
		return max_number_of_instance;
	}

	public void setMax_number_of_instance(int max_number_of_instance) {
		this.max_number_of_instance = max_number_of_instance;
	}

	public int getMax_number_of_disk() {
		return max_number_of_disk;
	}

	public void setMax_number_of_disk(int max_number_of_disk) {
		this.max_number_of_disk = max_number_of_disk;
	}

	public int getMax_number_of_backup() {
		return max_number_of_backup;
	}

	public void setMax_number_of_backup(int max_number_of_backup) {
		this.max_number_of_backup = max_number_of_backup;
	}

	public int getMax_number_of_snapshot() {
		return max_number_of_snapshot;
	}

	public void setMax_number_of_snapshot(int max_number_of_snapshot) {
		this.max_number_of_snapshot = max_number_of_snapshot;
	}

	public Boolean getPublish_image() {
		return publish_image;
	}

	public void setPublish_image(Boolean publish_image) {
		this.publish_image = publish_image;
	}

	public Boolean getSelect_cluster() {
		return select_cluster;
	}

	public void setSelect_cluster(Boolean select_cluster) {
		this.select_cluster = select_cluster;
	}

	public String getSelected_aggregates() {
		return selected_aggregates;
	}

	public void setSelected_aggregates(String selected_aggregates) {
		this.selected_aggregates = selected_aggregates;
	}
}
