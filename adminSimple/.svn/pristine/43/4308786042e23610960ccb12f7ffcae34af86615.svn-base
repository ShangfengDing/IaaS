package appcloud.admin.action.runtime;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import org.apache.log4j.Logger;

public class ChangeProfileAction extends BaseAction{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ChangeProfileAction.class);
    private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    private Integer id;
    private String userName;
    private String email;
    private String mobile;
    private String password;
    @Override
    public String execute() throws Exception {
        try {
            Admin admin = adminProxy.getById(id, false, false, false);
            admin.setEmail(email);
            admin.setMobile(mobile);
            admin.setUsername(userName);
            adminProxy.update(admin);
            return SUCCESS;
        }catch (Exception e){
            logger.error("admin id error");
            return ERROR;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
