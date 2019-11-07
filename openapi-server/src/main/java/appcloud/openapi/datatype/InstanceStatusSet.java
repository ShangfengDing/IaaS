package appcloud.openapi.datatype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class InstanceStatusSet {

	private List<InstanceStatusItem> InstanceStatusItems;

	public InstanceStatusSet(){}

	public InstanceStatusSet(List<InstanceStatusItem> instanceStatusItems) {
		setInstanceStatusItems(instanceStatusItems);
	}

	@XmlElement(name="InstanceStatus")
	public List<InstanceStatusItem> getInstanceStatusItems() {
		return InstanceStatusItems;
	}

	public void setInstanceStatusItems(List<InstanceStatusItem> instanceStatusItems) {
		InstanceStatusItems = instanceStatusItems;
	}

	
}
