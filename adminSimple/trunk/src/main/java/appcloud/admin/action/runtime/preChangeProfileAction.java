package appcloud.admin.action.runtime;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import java.util.Map;

public class preChangeProfileAction extends BaseAction {
    private String name ;
    private String email;
    private Integer id;
    private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    private static final Logger logger = Logger.getLogger(preChangeProfileAction.class);

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    Map<String, Object> session = ActionContext.getContext().getSession();
    @Override
    public String execute() throws Exception{
        try{
        Admin admin = adminProxy.getById(id,false,false,false);

            session.put("email",admin.getEmail());
            session.put("mobile",admin.getMobile());
            session.put("name",admin.getUsername());
//              name = admin.getUsername();
//              email = admin.getEmail();
            return SUCCESS;
    }catch (Exception e){
        logger.error("admin id error");
        return ERROR;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
