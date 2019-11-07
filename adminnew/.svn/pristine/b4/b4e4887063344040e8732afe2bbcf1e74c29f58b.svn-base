package appcloud.admin.action.accounts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rabbitmq.client.AMQP.Access.Request;

import appcloud.admin.common.Constants;
import appcloud.common.model.AdminResource;
import appcloud.common.model.AdminURL;

/**
 * 检查是否登录
 * 
 * @author hubaiyu
 * 
 */
public class LoginFilter extends PermissionFilter {
	/**
	 * 检查Session或Cookie[Constants.ADMIN_ID_KEY]是否存在
	 * 进一步检测是否有对应权限
	 * 0表示可以访问
	 * 1表示还没登录
	 * 2已登录，但是没有相应权限,为普通的http请求
	 * 3为ajax请求，
	 */
	@Override
	protected int checkPermission(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession().getAttribute(Constants.ADMIN_ID_KEY) == null) {
			return 1; 
		}
		String currentURL = request.getRequestURI(); 
		logger.info("currentUrl: "+ currentURL);
		if (!isAjaxRequest(request) && checkURL(request, currentURL)) {
			return 2;
		}
		if (isAjaxRequest(request) && checkURL(request, currentURL)) {
			return 3;
		}
		return 0;
	}
	
	private boolean checkURL(HttpServletRequest request, String url) {
		List<AdminResource> adminResources = (List<AdminResource>) request.getSession().getAttribute(Constants.ADMIN_RESOURCE);
		if (adminResources == null || adminResources.size() == 0) {
			return true;
		}
		for (AdminResource adminResource : adminResources) {
			if (adminResource.getAdminURLs() != null && !adminResource.getAdminURLs().isEmpty() && isPermission(url, adminResource.getAdminURLs())) {
				return false;
			}
		}
		return true;
	}
	/*
	 * currentUrl /admin/system/infrastructure
	 * targetUrl  system/,infrastructure,....
	 */
	private boolean isPermission(String currentUrl, List<AdminURL> targetUrls) {
		for (AdminURL url : targetUrls) {
			if (currentUrl.endsWith(url.getUrl()) ) {
				logger.info("　　　　currentUrl: "+currentUrl+", targetUrl: "+url.getUrl());
				return true;
			}
		}
		return false;
	}
	/*
	 * 判读此请求是否为ajax请求
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
}
