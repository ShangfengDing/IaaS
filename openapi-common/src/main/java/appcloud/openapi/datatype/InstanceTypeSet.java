package appcloud.openapi.datatype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class InstanceTypeSet {
	private List<InstanceTypeItem> InstanceTypeItems;
	
	public InstanceTypeSet()
	{
		
	}
	
	public InstanceTypeSet(List<InstanceTypeItem> InstanceTypeItems)
	{
		this.InstanceTypeItems=InstanceTypeItems;
	}

	@XmlElement(name="InstanceType")
	public List<InstanceTypeItem> getInstanceTypeItems() {
		return InstanceTypeItems;
	}

	public void setInstanceTypeItems(List<InstanceTypeItem> instanceTypeItems) {
		InstanceTypeItems = instanceTypeItems;
	}
	
}
