package appcloud.common.proxy;

import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;

public interface HostLoadProxy extends CommonLoadProxy {
    
    
   
    /**
     * 
     * @Title: getLatestLoad
     * @Description:通过uuid得到主机的一条负载信息
     * @param uuid 主机的uuid
     * @return
     */
    public HostLoad getLatestLoad(String uuid);
    
    /**
     * 
     * @Title: getLatestLoad
     * @Description: 通过uuid得到主机最近nums条的负载信息
     * @param uuid 主机的uuid
     * @param nums 取得负载信息的条数
     * @return
     */
    public List<? extends HostLoad> getLatestLoad(String uuid, int nums);
    
}
