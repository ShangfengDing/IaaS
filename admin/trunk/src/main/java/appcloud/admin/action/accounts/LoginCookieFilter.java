package appcloud.admin.action.accounts;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

import appcloud.admin.common.Constants;
import appcloud.common.model.Admin;
import appcloud.common.util.ConnectionFactory;

/**
 * 检查用户的登录cookie，如果用户选择记住登录状态，将登录状态写入session
 * @author hubaiyu
 *
 */
public class LoginCookieFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		if (request.getSession().getAttribute(Constants.AUTH_COOKIE_CHECKED) == null &&
				request.getSession().getAttribute(Constants.ADMIN_ID_KEY) == null && 
				request.getCookies() != null) {					
			int flag = 0;
			Integer adminId = null;
			String auth = null;
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getValue() == null || cookie.getValue().trim().equals("")) continue;
				if (cookie.getName().equals(Constants.AUTH_COOKIE_ID_NAME)) {
					adminId = Integer.parseInt(cookie.getValue());
					if (++flag == 2) break;
					continue;
				}
				
				if (cookie.getName().equals(Constants.AUTH_COOKIE_NAME)) {				
					auth = cookie.getValue();
					if (++flag == 2) break;
				}
			}
			if (adminId != null && auth != null) {
				try {
					Admin admin = ConnectionFactory.getAdminProxy().getById(adminId, false, false, false);
					if (DigestUtils.md5Hex((admin.getUsername() + admin.getPassword())).equals(auth)) {
						request.getSession().setAttribute(Constants.ADMIN_ID_KEY, admin.getId());
						request.getSession().setAttribute(Constants.ADMIN_USERNAME_KEY, admin.getUsername());
						request.getSession().setAttribute(Constants.ADMIN_DISPLAY_NAME_KEY, admin.getDisplayName());
						request.getSession().setAttribute(Constants.TYPE_KEY, admin.getType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			request.getSession().setAttribute(Constants.AUTH_COOKIE_CHECKED, true);
		}
		chain.doFilter(req, resp);
	}
	@Override
	public void destroy() {
	}

}
