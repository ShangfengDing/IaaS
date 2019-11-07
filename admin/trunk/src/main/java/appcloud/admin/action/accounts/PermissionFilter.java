package appcloud.admin.action.accounts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

import appcloud.admin.common.Constants;

/**
 * 检查用户权限，登录失败后跳转到指定的登录页面
 * 
 * <pre>
 * 在Filter的定义中需加入如下描述：
 * &lt;init-param&gt;
 *   &lt;description&gt;权限不够时转向的路径&lt;/description&gt;
 *   &lt;param-name&gt;failPage&lt;/param-name&gt;
 *   &lt;param-value&gt;权限不够的跳转页面&lt;/param-value&gt;
 * &lt;/init-param&gt;	
 * &lt;init-param&gt;
 *   &lt;description&gt;下面的url列表不进行权限检查,多个URI用;分开&lt;/description&gt;
 *   &lt;param-name&gt;exludeURLs&lt;/param-name&gt;
 *   &lt;param-value&gt;需要排除的URI&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * </pre>
 * 
 * @author hubaiyu
 * 
 */
public abstract class PermissionFilter implements Filter {
	protected Logger logger;
	private String failPage = null;
	private String permissionDenyPage = null;
	private HashSet<String> excludeURIs = new HashSet<String>();

	public void destroy() {
		failPage = null;
		excludeURIs = null;
		permissionDenyPage = null;
	}

	/**
	 * 子类验证权限或者检查是否在放行列表中<br/>
	 * 常量定义 {@link Constants}
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

        int isPermission = checkPermission(req, (HttpServletResponse)response);
        logger.info(isPermission);
        
        ((HttpServletRequest) request).getSession().setAttribute(Constants.ADMIN_IS_AJAX, "");
		if (isPermission == 0 || excludeURIs.contains(req.getRequestURI())) {
			chain.doFilter(request, response);
		} else if (isPermission == 1) {
			((HttpServletResponse) response).sendRedirect(failPage);
			return;
		} else if (isPermission == 2) {
			((HttpServletResponse) response).sendRedirect(permissionDenyPage);
			return;
		} else if (isPermission == 3) {//处理当是ajax请求时的页面跳转，给前端js发特殊标识，前端扑捉到此标识后,
			                           //跳转到特定页面，此工程相关代码在template/_common_js.jsp内
			PrintWriter printWriter = response.getWriter();  
		    printWriter.print(Constants.ADMIN_NO_LIMIT_AJAX);  
		    printWriter.flush();  
		    printWriter.close();
		    return;
		}
	}

	public void init(FilterConfig config) throws ServletException {
		logger = Logger.getLogger(this.getClass());
		String contextPath = config.getServletContext().getContextPath();

		failPage = contextPath + config.getInitParameter("failPage");
		permissionDenyPage = contextPath + config.getInitParameter("permissionDeny");
		String excludeURIString = config.getInitParameter("excludeURIs");
		StringBuilder uris = new StringBuilder();
		if (excludeURIString != null && !excludeURIString.trim().equals("")) {
			excludeURIString = excludeURIString.replaceAll("[\t\n]", "");
			for (String uri : excludeURIString.split(";")) {
				uri = contextPath + uri.trim();
				excludeURIs.add(uri.trim());
				uris.append(uri);
				uris.append(';');
			}
			if (uris.length() > 0) {
				uris.deleteCharAt(uris.length() - 1);
			}		
		}
		logger.debug("Set context path as:" + contextPath);
		logger.info("Set fail page :" + failPage);
		logger.info("Set permission check exlude uris:" + uris);
	}

	abstract protected int checkPermission(HttpServletRequest request, HttpServletResponse response);
}
