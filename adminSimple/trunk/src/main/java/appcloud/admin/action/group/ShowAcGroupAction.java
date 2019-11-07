package appcloud.admin.action.group;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcGroupClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class ShowAcGroupAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(ShowAcGroupAction.class);
    private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
    private AcAggregateClient acAggregateClient = ClientFactory.getAggregateClient();
    private AcGroup acGroup;
    private int acGroupId;
    private List<AcAggregate> acAggregates;
    private int page;

    public String execute() { System.out.println("**********"+page+"********************");
        acGroup = acGroupClient.get(acGroupId);
        acAggregates = acAggregateClient.getAggregates();
        if(acGroup == null || acAggregates == null || acAggregates.size()==0) {
            this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
                    this.getUserId(), null, "查找群组", "查找群组"+acGroup.name+"错误", "ShowAcGroupAction.class",
                    null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
            logger.info("找不到ID为" + acGroupId + "的权限组，或者集群为空");
            return "error";
        } else {
            this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
                    this.getUserId(), null, "查找群组", "查找群组"+acGroup.name, "ShowAcGroupAction.class",
                    null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
            logger.info("查询权限组和集群完成");
            return SUCCESS;
        }
    }

    public List<AcAggregate> getAcAggregates() {
        return acAggregates;
    }

    public void setAcAggregates(List<AcAggregate> acAggregates) {
        this.acAggregates = acAggregates;
    }

    public AcGroup getAcGroup() {
        return acGroup;
    }

    public void setAcGroup(AcGroup acGroup) {
        this.acGroup = acGroup;
    }

    public int getAcGroupId() {
        return acGroupId;
    }

    public void setAcGroupId(int acGroupId) {
        this.acGroupId = acGroupId;
    }

    public int getPage(){return page;}

    public void setPage(int page){ this.page=page;}
}