package com.appcloud.vm.action.account;

import java.util.List;

import appcloud.api.client.KeepAliveClient;

import com.appcloud.vm.fe.util.ClientFactory;
import com.free4lab.utils.action.BaseAction;

public class KeepAliveAction extends BaseAction{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
 *	该类访问数据库，并返回响应值，依次来确定account的正常运行
 *	@author hgm
 */
	public String execute() throws Exception {
		KeepAliveClient client = ClientFactory.getKeepAliveClient();
		String result = "";
		try
		{
			result = client.KeepAlive();
		}catch(Exception e)
		{
			return "fail";
		}
		if(result.equals("success"))
			return "success";
		return "fail";
	}
}
