package appcloud.admin.action.system;

import appcloud.admin.manager.CloudInfoManager;
import com.distributed.common.entity.CloudInfo;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangzhi 2018-05-21 17:40
 **/
public class CloudAction extends ActionSupport {
    private List<CloudInfo> aliveCloudInfoList;
    private List<CloudInfo> deadCloudInfoList;
    private String uuid;
    private int page;
    private int size;
    private int totalPages;
    private int type; // 0 all,1 alive,2 dead

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String cloudInfoList() {
        aliveCloudInfoList = null;
        deadCloudInfoList = null;

        page = page - 1;

        if (page == 0) {                  //当page为0时更新
            try {
                CloudInfoManager.getInstance().update();
            } catch (Exception e) {
                e.printStackTrace();
                totalPages = 0;
                return SUCCESS;
            }
        }
        List<CloudInfo> aliveCloudInfos = CloudInfoManager.getInstance().getAliveCloudInfoList();
        List<CloudInfo> deadCloudInfos = CloudInfoManager.getInstance().getDeadCloudInfoList();

//        List<CloudInfo> aliveCloudInfos = new ArrayList<>();
//
//        List<CloudInfo> deadCloudInfos = new ArrayList<>();

        if (size <= 0) size = 10;       //size默认10
        switch (type) {
            case 0:                                                     //取全部
                totalPages = aliveCloudInfos.size() + deadCloudInfos.size() == 0 ? 0 : (aliveCloudInfos.size() + deadCloudInfos.size() - 1) / size + 1;
                if (page > totalPages - 1) {    //防出界
                    page = totalPages - 1;
                }
                if (aliveCloudInfos.size() <= page * size) {            //存活的数量小于开始值
                    aliveCloudInfoList = new ArrayList<>();             //alive设空
                    int start = page * size - aliveCloudInfos.size();   //取到dead表开始位置
                    if (deadCloudInfos.size() == 0) {
                        deadCloudInfoList = deadCloudInfos;
                    } else {
                        deadCloudInfoList = deadCloudInfos.subList(start, start + size > deadCloudInfos.size() ? deadCloudInfos.size() : start + size);
                    }
                } else {
                    if (aliveCloudInfos.size() == 0) {
                        aliveCloudInfoList = aliveCloudInfos;
                    } else {
                        aliveCloudInfoList = aliveCloudInfos.subList(page * size, page * size + size > aliveCloudInfos.size() ? aliveCloudInfos.size() : page * size + size);
                    }
                    //取dead表应取数量
                    int deadSize = page * size + size > aliveCloudInfos.size() ? page * size + size - aliveCloudInfos.size() : 0;
                    deadSize = deadSize > deadCloudInfos.size() ? deadCloudInfos.size() : deadSize;
                    if (deadSize == 0) {
                        deadCloudInfoList = new ArrayList<>();
                    } else {
                        deadCloudInfoList = deadCloudInfos.subList(0, deadSize);
                    }
                }
                break;
            case 1:
                totalPages = (aliveCloudInfos.size() - 1) / size + 1;
                if (page > totalPages - 1) {    //防出界
                    page = totalPages - 1;
                }
                if (aliveCloudInfos.size() == 0) {
                    aliveCloudInfoList = aliveCloudInfos;
                    totalPages = 0;
                } else {
                    aliveCloudInfoList = aliveCloudInfos.subList(page * size, page * size + size > aliveCloudInfos.size() ? aliveCloudInfos.size() : page * size + size);
                }
                break;
            case 2:
                totalPages = (deadCloudInfos.size() - 1) / size + 1;
                if (page > totalPages - 1) {    //防出界
                    page = totalPages - 1;
                }
                if (deadCloudInfos.size() == 0) {
                    deadCloudInfoList = deadCloudInfos;
                    totalPages = 0;
                } else {
                    deadCloudInfoList = deadCloudInfos.subList(page * size, page * size + size > deadCloudInfos.size() ? deadCloudInfos.size() : page * size + size);
                }
                break;
        }
        return SUCCESS;
    }


    public String removeDead() {
        CloudInfo cloudInfo = new CloudInfo();
        cloudInfo.setUuid(uuid);
        CloudInfoManager.getInstance().removeDeadCloud(cloudInfo);
        CloudInfoManager.getInstance().update();
        aliveCloudInfoList = CloudInfoManager.getInstance().getAliveCloudInfoList();
        deadCloudInfoList = CloudInfoManager.getInstance().getDeadCloudInfoList();
        return SUCCESS;
    }

    public List<CloudInfo> getAliveCloudInfoList() {
        return aliveCloudInfoList;
    }

    public void setAliveCloudInfoList(List<CloudInfo> aliveCloudInfoList) {
        this.aliveCloudInfoList = aliveCloudInfoList;
    }

    public List<CloudInfo> getDeadCloudInfoList() {
        return deadCloudInfoList;
    }

    public void setDeadCloudInfoList(List<CloudInfo> deadCloudInfoList) {
        this.deadCloudInfoList = deadCloudInfoList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
