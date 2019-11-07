package com.appcloud.vm.action.account;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.SessionConstants;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

public class LogoutAction extends BaseAction {
	/**
	 *登录account的login方法
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove(SessionConstants.LoginUserID);
		session.remove(SessionConstants.UserID);
		session.remove(SessionConstants.UserEmail);
		session.remove(SessionConstants.USER_NAME);
		session.remove(SessionConstants.KEY_SCREEN_NAME);
		session.remove(SessionConstants.KEY_PROFILE_IMAGE_URL);
		session.remove(SessionConstants.AccessToken);
		session.remove(SessionConstants.AccToken);
		return SUCCESS;
	}
}