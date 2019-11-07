package appcloud.admin.action.vm;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class VmModPasswd extends BaseAction {
	private Logger logger = Logger.getLogger(VmModPasswd.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private String userid;
	private String serverid;
	private String name;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		userid = this.getUsername();
		serverClient.modifyPasswd(userid, serverid, name, null, null);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					super.getUserId(), serverid, "修改云主机密码", "修改密码: 用户账户名"+name+"成功", "VmModPasswd.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
