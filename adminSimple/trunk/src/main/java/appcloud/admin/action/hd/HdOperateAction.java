package appcloud.admin.action.hd;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.VolumeAttachment;
import appcloud.api.client.VolumeAttachmentClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.util.ClientFactory;

public class HdOperateAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdOperateAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private VolumeAttachmentClient volumeAttachmentClient = ClientFactory.getVolumeAttachmentClient();
	
	private Integer uid;
	private String hdUuid;		//硬盘volume的uuid
	private String operation;	//操作类型
	private String serverId;	//挂载时，要挂载到的虚拟机uuid
	private Integer endTimeId;	//删除时，硬盘到期时间的记录id
	
	private final static String ATTACH = "attach";
	private final static String DETACH = "detach";
	private final static String DELETE = "delete";
	
	private boolean result = false;
	private boolean expired = false;
	private final static VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
	private boolean isExpired() {
		VmHdEndtime endTime = vmHdEndtimeManager.getHdEndtimeByUuid(hdUuid);
		if( new Date().getTime() < endTime.getEndTime().getTime())
			return false;
		else{
			logger.info("硬盘" + hdUuid + "已过期");
			expired = true;
			return true;
		}
	}
	
	public String execute() {
		//String userId = uid.toString();
		String userId = this.getUsername();
		expired = false;
		result = false;
		logger.info("hdUuid:"+hdUuid+",operation:"+operation+",serverId:"+serverId+",uid"+uid);
		if (operation.equals(ATTACH) && (!isExpired())) {
			VolumeAttachment attachment = new VolumeAttachment(null, serverId, null, hdUuid);
			volumeAttachmentClient.attachVolumeAttachment(userId, serverId, attachment);
			logger.info("挂载成功");
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), hdUuid, "挂载云硬盘", "挂载云硬盘["+hdUuid+"]到["+serverId+"]的云主机", "HdOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			result = true;
		} else if (operation.equals(DETACH)) {
			volumeAttachmentClient.detachVolumeAttachment(userId, serverId, hdUuid);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), hdUuid, "卸载云硬盘", "从["+serverId+"]的云主机卸载云硬盘["+hdUuid+"]", "HdOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			logger.info("卸载成功");
			result = true;
		} else if (operation.equals(DELETE)) {
			volumeClient.deleteVolume(userId, hdUuid);
			new VmHdEndtimeManager().delEndtimeById(endTimeId);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), hdUuid, "删除云硬盘", "删除云硬盘["+hdUuid+"]", "HdOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			logger.info("删除成功");
			result = true;
		}
		
		return SUCCESS;
	}

	public String getHdUuid() {
		return hdUuid;
	}

	public void setHdUuid(String hdUuid) {
		this.hdUuid = hdUuid;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Integer getEndTimeId() {
		return endTimeId;
	}

	public void setEndTimeId(Integer endTimeId) {
		this.endTimeId = endTimeId;
	}

	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public boolean getExpired( ) {
		return this.expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
