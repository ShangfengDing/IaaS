package appcloud.admin.action.vm;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.util.ClientFactory;

public class VmOperateAction extends BaseAction{
	private Logger logger = Logger.getLogger(VmOperateAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	
	private String userId;		//vm的userId
	private String uuid;		//vm的uuid
	private String operation;	//操作类型
	private Integer endTimeId;	//删除时有用，虚拟机到期时间的记录id
	
	private final static String START = "start";
	private final static String STOP = "stop";
	private final static String RESTART = "restart";
	private final static String SUSPEND = "suspend";
	private final static String RESUME = "resume";
	private final static String DELETE = "delete";
	private final static String FORCE_STOP = "forceStop";
	private final static String FORCE_DELETE = "forceDelete";
	private final static String DETACH_ISO = "detachIso";
	
	public String execute() {
		logger.info("uuid:"+uuid+",operation:"+operation);
		String operationStr = null;
		userId = this.getUsername();
		if (operation.equals(START)) {
			serverClient.osStart(userId, uuid);
			operationStr = "启动";
		} else if (operation.equals(STOP)) {
			serverClient.osStop(userId, uuid);
			operationStr = "停止";
		} else if (operation.equals(RESTART)) {
			serverClient.reboot(userId, uuid, new ServerActionReboot("reboot"));
			operationStr = "重启";
		} else if (operation.equals(SUSPEND)) {
			serverClient.suspend(userId, uuid);
			operationStr = "挂起";
		} else if (operation.equals(RESUME)) {
			serverClient.resume(userId, uuid);
			operationStr = "恢复";
		} else if (operation.equals(DELETE)) {
			serverClient.terminateServer(userId, uuid);
			operationStr = "删除";
			new VmHdEndtimeManager().delEndtimeById(endTimeId);
		} else if (operation.equals(FORCE_STOP)) {
			serverClient.forceStop(userId, uuid);
			operationStr = "强制关机";
		} else if (operation.equals(FORCE_DELETE)) {
			serverClient.forceDelete(userId, uuid);
			operationStr = "强制删除";
			new VmHdEndtimeManager().delEndtimeById(endTimeId);
		} else if (operation.equals(DETACH_ISO)) {
			serverClient.detachISO(userId, uuid);
			operationStr = "ISO弹出";
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				super.getUserId(), uuid, operationStr+"云主机", userId+"的云主机被"+operationStr, "VmOperateAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getEndTimeId() {
		return endTimeId;
	}
	public void setEndTimeId(Integer endTimeId) {
		this.endTimeId = endTimeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
