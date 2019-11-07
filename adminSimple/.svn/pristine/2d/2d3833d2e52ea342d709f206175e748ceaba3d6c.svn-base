package appcloud.admin.action.hd;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Server;
import appcloud.api.client.ServerClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ShowAttachHdAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ShowAttachHdAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	
	private List<Server> servers = new ArrayList<Server>();
	private Integer uid;

    public String execute() throws Exception {
    	String userId = uid.toString();
    	servers = serverClient.getServers(userId, false);
    	logger.info("硬盘挂载，获取用户server列表成功");
    	return SUCCESS;
    }

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
    
}
