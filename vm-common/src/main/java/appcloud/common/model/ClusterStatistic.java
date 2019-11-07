package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author lzc
 *	集群的一些信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class ClusterStatistic {
	private Integer id;
	private Integer devNum;			//开发者总数
	private Integer j2eeAppNum;		//j2ee应用总数
	private Integer j2eeInstanceNum;//j2ee实例总数
	private Integer vmNum;			//虚拟机总数
	private NetworkLoad networkLoad;//网络负载信息：各instance的NetworkLoad叠加
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDevNum() {
		return devNum;
	}
	public void setDevNum(Integer devNum) {
		this.devNum = devNum;
	}
	public Integer getJ2eeAppNum() {
		return j2eeAppNum;
	}
	public void setJ2eeAppNum(Integer j2eeAppNum) {
		this.j2eeAppNum = j2eeAppNum;
	}
	public Integer getJ2eeInstanceNum() {
		return j2eeInstanceNum;
	}
	public void setJ2eeInstanceNum(Integer j2eeInstanceNum) {
		this.j2eeInstanceNum = j2eeInstanceNum;
	}
	public Integer getVmNum() {
		return vmNum;
	}
	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}
	public NetworkLoad getNetworkLoad() {
		return networkLoad;
	}
	public void setNetworkLoad(NetworkLoad networkLoad) {
		this.networkLoad = networkLoad;
	}
	@Override
	public String toString() {
		return "ClusterStatistic [id=" + id + ", devNum=" + devNum
				+ ", j2eeAppNum=" + j2eeAppNum + ", j2eeInstanceNum="
				+ j2eeInstanceNum + ", vmNum=" + vmNum + ", networkLoad="
				+ networkLoad + "]";
	}	
}
