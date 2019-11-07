package appcloud.api.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="load")
public class Load {
	@XmlAttribute
    public Date time;
    public Float cpuPercent;
    public Float avgLoad;
    public Float memPercent;
    public Float diskPercent;
    public Float netInPercent;
    public Float netOutPercent;
    public Float diskReadRate;
    public Float diskWriteRate;
    
    public Load() {}
    
	public Load(Date time, Float cpuPercent, Float avgLoad,
			Float memPercent, Float diskPercent, Float netInPercent,
			Float netOutPercent, Float diskReadRate, Float diskWriteRate) {
		super();
		this.time = time;
		this.cpuPercent = cpuPercent;
		this.avgLoad = avgLoad;
		this.memPercent = memPercent;
		this.diskPercent = diskPercent;
		this.netInPercent = netInPercent;
		this.netOutPercent = netOutPercent;
		this.diskReadRate = diskReadRate;
		this.diskWriteRate = diskWriteRate;
	}
}
