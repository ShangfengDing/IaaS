package appcloud.admin.action.accounts;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author lzc
 *
 */

public class LogoutAction extends BaseAction {
	final static Logger logger = Logger.getLogger(LogoutAction.class);
	private static final long serialVersionUID = -8118105201207267532L;
	
	@Override
	public String execute() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userid = session.get(Constants.ADMIN_ID_KEY).toString();
		session.remove(Constants.ADMIN_ID_KEY);
		String devName = (String)session.remove(Constants.ADMIN_USERNAME_KEY); 	
		session.remove(Constants.ADMIN_DISPLAY_NAME_KEY);
		session.remove(Constants.TYPE_KEY);
		session.remove(Constants.ADMIN_RESOURCE);
		int flag = 0;
	    for(Cookie cookie : ServletActionContext.getRequest().getCookies()) {
	        if (cookie.getName().equals(Constants.AUTH_COOKIE_ID_NAME) || cookie.getName().equals(Constants.AUTH_COOKIE_NAME)) {
	        	cookie.setValue(null);
	        	cookie.setMaxAge(0);
	        	cookie.setPath("/");
	        	ServletActionContext.getResponse().addCookie(cookie);
	        	if (++flag == 2) break;
	        }
	    }
	    this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
	    		userid, null, "退出", "用户["+devName+"]退出", "LogoutAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
        logger.debug(devName + " has logout!");
		return SUCCESS;
	}
}
