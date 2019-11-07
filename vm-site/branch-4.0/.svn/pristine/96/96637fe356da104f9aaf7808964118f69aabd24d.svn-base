package com.appcloud.vm.action.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appcloud.vm.common.Constants;
import com.appcloud.vm.common.SessionConstants;
import com.free4lab.utils.account.NewBaseLoginFilter;

/**
 * 继承common中封装过的BaseLoginFilter
 * @author lzc
 */
public class LoginFilter extends NewBaseLoginFilter {
	@Override
	protected String getClientId() {
		//返回account分配给应用的client_id即原customId的值
		return Constants.CLIENT_ID;
	}
	
	@Override
	protected String getRedirectUri() {
		//返回应用login方法的url，注意login和logout方法要在同一个父目录下！
		return "/account/login";
	}
	@Override
	protected String getAccessTokenInSession(HttpServletRequest request,
           HttpServletResponse response) {
       //返回session中access_token的值
       String accessTokenInSession = (String) request.getSession().getAttribute(SessionConstants.AccessToken);
       return accessTokenInSession;
   }
}