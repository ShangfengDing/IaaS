package appcloud.api.beans;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="mail_configure")
public class AcMailConf {
	@XmlAttribute
	public String lolEmail;
	@XmlAttribute
	public String emailHost;
	@XmlAttribute
	public String emailFrom;
	@XmlAttribute
	public String emailPassword;
	@XmlAttribute
	public String emailSubject;
	
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public Map<String, String> moduleInCharge;
	
	public AcMailConf() {
		super();
	}
	
	public AcMailConf(String lolEmail, String emailHost, String emailFrom,
			String emailPassword, String emailSubject,
			Map<String, String> moduleInCharge) {
		super();
		this.lolEmail = lolEmail;
		this.emailHost = emailHost;
		this.emailFrom = emailFrom;
		this.emailPassword = emailPassword;
		this.emailSubject = emailSubject;
		this.moduleInCharge = moduleInCharge;
	}

	@Override
	public String toString() {
		return "AcMailConf [lolEmail=" + lolEmail + ", emailHost=" + emailHost
				+ ", emailFrom=" + emailFrom + ", emailPassword="
				+ emailPassword + ", emailSubject=" + emailSubject
				+ ", moduleInCharge=" + moduleInCharge + "]";
	}
	
}
