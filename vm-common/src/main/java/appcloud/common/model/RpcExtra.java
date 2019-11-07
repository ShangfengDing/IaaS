package appcloud.common.model;

import java.util.HashMap;
import java.util.Map;

import appcloud.common.util.UuidUtil;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class RpcExtra {
	
	public RpcExtra() {
		super();
	}
	String tranctionId = "";
	
	String userId = "";
	
	String vmUuid = "";
	
	Map<String, String> map = new HashMap<String, String>();
	
	public static String genTranctionId() {
		return UuidUtil.getRandomUuid();
	}

	public String getTranctionId() {
		return tranctionId;
	}

	public void setTranctionId(String tranctionId) {
		this.tranctionId = tranctionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVmUuid() {
		return vmUuid;
	}

	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public RpcExtra(String tranctionId, String userId, String vmUuid) {
		super();
		this.tranctionId = tranctionId;
		this.userId = userId;
		this.vmUuid = vmUuid;
	}
	
	public RpcExtra(String tranctionId, String userId) {
		super();
		this.tranctionId = tranctionId;
		this.userId = userId;
	}
	
}
