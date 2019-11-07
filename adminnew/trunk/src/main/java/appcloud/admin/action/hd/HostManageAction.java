package appcloud.admin.action.hd;

import appcloud.admin.action.base.BaseAction;
import org.apache.log4j.Logger;

public class HostManageAction extends BaseAction {
    private static final long serialVersionUID = -8978894090535415113L;
    private static final Logger logger = Logger.getLogger(HostManageAction.class);

    public String execute() throws Exception {
        logger.info("access success!");
            return SUCCESS;
    }
}
