package appcloud.api.beans.acservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcServiceActionEnum;
import appcloud.api.enums.AcServiceTypeEnum;

@XmlRootElement(name="ac-service-action")
public class AcServiceAction {
	@XmlAttribute
	public AcServiceActionEnum action;
	
	public List<AcServiceTypeEnum> types;
	
	public AcServiceAction(){
		super();
	}

	public AcServiceAction(AcServiceActionEnum action,
			List<AcServiceTypeEnum> types) {
		super();
		this.action = action;
		this.types = types;
	}
	
}
