package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.rabbitmq.tools.json.JSONUtil;
import com.rabbitmq.tools.json.JSONWriter;

/**
 * @author lzc
 * 主机或者GC容器的网络负载信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class NetworkLoad {
	private Integer id;
	private Integer j2eeinfoId;
	private String appUuid; //app的uuid
	private String fatherUuid;	//host或者instance的uuid
	private String host;
	private Timestamp time;		//该条记录产生时间
	private Timestamp startTime;		//统计开始时间
	private Timestamp endTime;		//统计结束时间
	private Integer  netInByte;	//网络入流量
	private Integer  netOutByte;//网络出流量
	private Integer requestNum;	//在这段时间内的总的请求个数
	private Float averageCostSec;//平均每个请求的响应时间，单位second
	
	public NetworkLoad() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NetworkLoad(Long netInByte, Long netOutByte, Long requestNum, Double averageCostSec) {
		this.netInByte = netInByte == null ? 0 : (int)(long)netInByte;
		this.netOutByte = netOutByte == null ? 0 : (int)(long)netOutByte;
		this.requestNum = requestNum == null ? 0 : (int)(long)requestNum;
		this.averageCostSec = averageCostSec == null ? 0 : (float)(double)averageCostSec;
	}
	
	public NetworkLoad(Integer id,
					   Integer j2eeinfoId,
					   String appUuid,
					   String fatherUuid,
					   String host,
					   Timestamp time,
					   Timestamp startTime,
					   Timestamp endTime,
					   Integer netInByte, 
					   Integer netOutByte, 
					   Integer requestNum,
					   Float averageCostSec) {
		super();
		this.id = id;
		this.j2eeinfoId = j2eeinfoId;
		this.appUuid = appUuid;
		this.fatherUuid = fatherUuid;
		this.host = host;
		this.time = time;
		this.startTime = startTime;
		this.endTime = endTime;
		this.netInByte = netInByte;
		this.netOutByte = netOutByte;
		this.requestNum = requestNum;
		this.averageCostSec = averageCostSec;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJ2eeinfoId() {
		return this.j2eeinfoId;
	}
	public void setJ2eeinfoId(Integer j2eeinfoId) {
		this.j2eeinfoId = j2eeinfoId;
	}
	public String getAppUuid() {
		return this.appUuid;
	}
	public void setAppUuid(String appUuid) {
		this.appUuid = appUuid;
	}
	public String getFatherUuid() {
		return fatherUuid;
	}
	public void setFatherUuid(String fatherUuid) {
		this.fatherUuid = fatherUuid;
	}
	public String getHost() {
		return this.host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Integer getNetInByte() {
		return netInByte;
	}
	public void setNetInByte(Integer netInByte) {
		this.netInByte = netInByte;
	}
	public Integer getNetOutByte() {
		return netOutByte;
	}
	public void setNetOutByte(Integer netOutByte) {
		this.netOutByte = netOutByte;
	}
	public Integer getRequestNum() {
		return requestNum;
	}
	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}

	public Float getAverageCostSec() {
		return averageCostSec;
	}
	public void setAverageCostSec(Float averageCostSec) {
		this.averageCostSec = averageCostSec;
	}
	
	@Override
	public String toString() {
		return new JSONWriter().write(this).toString();
	}
	
}
