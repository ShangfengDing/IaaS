package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @deprecated 请使用{@link HostLoad}
 * @author wylazy
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmLoad extends HostLoad {

	public VmLoad() {
	}

	public VmLoad(Integer id, String uuid, Timestamp time, Float cpuPercent,
			Float avgLoad, Float memPercent, Float diskPercent,
			Float netInPercent, Float netOutPercent, Float diskReadRate,
			Float diskWriteRate) {
		super.setId(id);
		super.setUuid(uuid);
		super.setTime(time);
		super.setCpuPercent(cpuPercent);
		super.setAvgLoad(avgLoad);
		super.setMemPercent(memPercent);
		super.setDiskPercent(diskPercent);
		super.setNetInPercent(netInPercent);
		super.setNetOutPercent(netOutPercent);
		super.setDiskReadRate(diskReadRate);
		super.setDiskWriteRate(diskWriteRate);
	}

	public VmLoad(Double cpuPercent, Double avgLoad, Double memPercent,
			Double diskPercent, Double netInPercent, Double netOutPercent,
			Double diskReadRate, Double diskWriteRate) {
		this(null, cpuPercent, avgLoad, memPercent, diskPercent, netInPercent, netOutPercent, diskReadRate, diskWriteRate);
	}
	
	public VmLoad(String uuid, Double cpuPercent, Double avgLoad, Double memPercent,
			Double diskPercent, Double netInPercent, Double netOutPercent,
			Double diskReadRate, Double diskWriteRate) {
		
		setUuid(uuid);
		setCpuPercent(cpuPercent == null ? 0 : cpuPercent.floatValue());
		setAvgLoad(avgLoad == null ? 0 : avgLoad.floatValue());
		setMemPercent(memPercent == null ? 0 : memPercent.floatValue());
		setDiskPercent(diskPercent == null ? 0 : diskPercent.floatValue());
		setNetInPercent(netInPercent == null ? 0 : netInPercent.floatValue());
		setNetOutPercent(netOutPercent == null ? 0 : netOutPercent.floatValue());
		setDiskReadRate(diskReadRate == null ? 0 : diskReadRate.floatValue());
		setDiskWriteRate(diskWriteRate == null ? 0 : diskWriteRate.floatValue());
	}

}
