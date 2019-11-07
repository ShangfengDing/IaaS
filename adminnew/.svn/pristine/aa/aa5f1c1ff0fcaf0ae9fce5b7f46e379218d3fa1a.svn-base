package appcloud.admin.manager;

import appcloud.openapi.client.ControllerClient ;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.entity.CloudInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by wangzhi 2018-05-22 11:37
 **/
public class CloudInfoManager {
    private Map<String/*uuid*/, CloudInfo> deadCloudInfoMap;

    //分页，转为有序的
    private List<CloudInfo> aliveCloudInfoList;
    private List<CloudInfo> deadCloudInfoList;

    private static CloudInfoManager cloudInfoManager = null;

    private CloudInfoManager() {
        deadCloudInfoMap = new HashMap<String, CloudInfo>();
        aliveCloudInfoList = new ArrayList<>();
        deadCloudInfoList = new ArrayList<>();
    }

    /**
     * 把所有存活的云设为不存活
     */
    private void killAllCloud() {
        for (CloudInfo cloudInfo:aliveCloudInfoList){
            if (!deadCloudInfoMap.containsKey(cloudInfo.getUuid())) {
                deadCloudInfoMap.put(cloudInfo.getUuid(),cloudInfo);
            }
        }
    }

    /**
     * 删除在死亡列表中存活的云
     */
    private void raiseCloud() {
        for (CloudInfo cloudInfo:aliveCloudInfoList){
            if (deadCloudInfoMap.containsKey(cloudInfo.getUuid())) {
                deadCloudInfoMap.remove(cloudInfo.getUuid());
            }
        }
    }

    /**
     * 单例模式
     * @return Instance
     */
    public static CloudInfoManager getInstance() {
        if (cloudInfoManager == null) {
            cloudInfoManager = new CloudInfoManager();
        }
        return cloudInfoManager;
    }


    /**
     * 使用ControllerClient获取存活列表
     */
    public void update() {
        killAllCloud();
        ControllerClient controllerClient = OpenClientFactory.getControllerClient();
        aliveCloudInfoList = controllerClient.findAllCloudInfo();
        raiseCloud();
        deadCloudInfoList.clear();
        for (Map.Entry entry:deadCloudInfoMap.entrySet()) {
            deadCloudInfoList.add((CloudInfo) entry.getValue());
        }
    }

    /**
     * 获取当前存活的云信息
     * @return cloudInfoList
     */
    public List<CloudInfo> getAliveCloudInfoList(){
        return aliveCloudInfoList;
    }

    public List<CloudInfo> getDeadCloudInfoList() {
        return deadCloudInfoList;
    }

    /**
     * 删除一条死掉的云信息
     * @param cloudInfo 删除的云信息，以uuid删除
     */
    public void removeDeadCloud(CloudInfo cloudInfo) {
        deadCloudInfoMap.remove(cloudInfo.getUuid());
    }

    /**
     * 获取作为master的云信息
     * @return masterCloudInfo
     */
    public CloudInfo getMasterCloudInfo() {
        for (CloudInfo cloudInfo : aliveCloudInfoList) {
            if (cloudInfo.getRole().equals("master")) {
                return cloudInfo;
            }
        }
        return null;
    }

}
