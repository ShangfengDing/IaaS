package appcloud.admin.interceptor;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import appcloud.admin.common.Constants;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminResource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ResolverUtil.IsA;

public class AdminPermissionInterceptor extends AbstractInterceptor{
	private static final Logger logger = Logger.getLogger(AdminPermissionInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();  
        String isAjax = (String) invocation.getInvocationContext().getSession().get(Constants.ADMIN_IS_AJAX);
        logger.info(isAjax);
        if (isAjax == null || isAjax.isEmpty() || !isAjax.equals(Constants.ADMIN_NO_LIMIT_AJAX)) {
        	return invocation.invoke();
        }
        invocation.getInvocationContext().getSession().remove(Constants.ADMIN_IS_AJAX);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();  
        printWriter.print(Constants.ADMIN_NO_LIMIT_AJAX);  
        printWriter.flush();  
        printWriter.close();
		logger.info("没有权限！");
		return null;
	}
}
