/**
 * File: NetworkLoadTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.NetworkLoad;

/**
 * @author weed
 *
 */
@Entity
@Table(name="network_load")
public class NetworkLoadTable extends NetworkLoad {

	public NetworkLoadTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NetworkLoadTable(Long netInByte, Long netOutByte, Long requestNum, Double averageCostSec) {
		super(netInByte, netOutByte, requestNum, averageCostSec);
	}
	
	public NetworkLoadTable(NetworkLoad networkLoad) {
		this.setId(networkLoad.getId());
		this.setJ2eeinfoId(networkLoad.getJ2eeinfoId());
		this.setAppUuid(networkLoad.getAppUuid());
		this.setFatherUuid(networkLoad.getFatherUuid());
		this.setHost(networkLoad.getHost());
		this.setTime(networkLoad.getTime());
		this.setStartTime(networkLoad.getStartTime());
		this.setEndTime(networkLoad.getEndTime());
		this.setNetInByte(networkLoad.getNetInByte());
		this.setNetOutByte(networkLoad.getNetOutByte());
		this.setRequestNum(networkLoad.getRequestNum());
		this.setAverageCostSec(networkLoad.getAverageCostSec());
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}
	
	@Column(name="j2eeinfo_id")
	public Integer getJ2eeinfoId() {
		return super.getJ2eeinfoId();
	}
	public void setJ2eeinfoId(Integer j2eeinfoId) {
		super.setJ2eeinfoId(j2eeinfoId);
	}
	
    @Column(name="app_uuid")
	public String getAppUuid() {
		return super.getAppUuid();
	}
	public void setAppUuid(String appUuid) {
		super.setAppUuid(appUuid);
	}
	
    @Column(name="father_uuid")
	public String getFatherUuid() {
		return super.getFatherUuid();
	}
	public void setFatherUuid(String fatherUuid) {
		super.setFatherUuid(fatherUuid);
	}
	
	@Column(name="host")
	public String getHost() {
		return super.getHost();
	}
	public void setHost(String host) {
		super.setHost(host);
	}
	
    @Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}

    @Column(name="start_time")
	public Timestamp getStartTime() {
		return super.getStartTime();
	}
	public void setStartTime(Timestamp startTime) {
		super.setStartTime(startTime);
	}

    @Column(name="end_time")
	public Timestamp getEndTime() {
		return super.getEndTime();
	}
	public void setEndTime(Timestamp endTime) {
		super.setEndTime(endTime);
	}
    @Column(name="net_in_byte")
	public Integer getNetInByte() {
		return super.getNetInByte();
	}
	public void setNetInByte(Integer netInByte) {
		super.setNetInByte(netInByte);
	}
	
    @Column(name="net_out_byte")
	public Integer getNetOutByte() {
		return super.getNetOutByte();
	}
	public void setNetOutByte(Integer netOutByte) {
		super.setNetOutByte(netOutByte);
	}
	
    @Column(name="request_num")
	public Integer getRequestNum() {
		return super.getRequestNum();
	}
	public void setRequestNum(Integer requestNum) {
		super.setRequestNum(requestNum);
	}
	
    @Column(name="average_cost_sec")
	public Float getAverageCostSec() {
		return super.getAverageCostSec();
	}
	public void setAverageCostSec(Float averageCostSec) {
		super.setAverageCostSec(averageCostSec);
	}
}
