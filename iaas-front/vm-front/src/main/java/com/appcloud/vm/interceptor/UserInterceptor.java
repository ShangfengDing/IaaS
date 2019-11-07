package com.appcloud.vm.interceptor;

import java.util.Map;

import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;

import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		AcUserClient acUserClient = ClientFactory.getAcUserClient();
		EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		if(ActionContext.getContext().getSession() != null){
			Boolean is_Actived = (Boolean)session.get(SessionConstants.IsActived);
			AcUser acUser = acUserClient.get(((Integer)session.get(SessionConstants.LoginUserID)).toString());
			Boolean enterpriseIsActive = null;
			if(acUser.enterpriseId != null) {
				Enterprise enterprise = enterpriseClient.get(acUser.enterpriseId);
				enterpriseIsActive = enterprise.isActive;
			}
			
			
			if(is_Actived == null){
				result = "error";
			}else if(!is_Actived){
				result = "nopermission";
			}else if(enterpriseIsActive != null && !enterpriseIsActive) {
				result = "nopermission";
			} else{
				try {
					//调用下一个拦截器，如果拦截器不存在，则执行Action
					result = invocation.invoke();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			try {
				//调用下一个拦截器，如果拦截器不存在，则执行Action
				result = invocation.invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	    return result;
	}
}
