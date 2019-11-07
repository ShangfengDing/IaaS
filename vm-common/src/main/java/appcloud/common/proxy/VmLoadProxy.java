package appcloud.common.proxy;

import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;

public interface VmLoadProxy extends CommonLoadProxy {

    /**
     * 
     * @Title: getLatestLoad
     * @Description:通过uuid得到虚拟机的一条负载信息
     * @param uuid 虚拟机的uuid
     * @return
     */
    public HostLoad getLatestLoad(String uuid);

    /**
     * 
     * @Title: getLatestLoad
     * @Description: 通过uuid得到虚拟机最近nums条的负载信息
     * @param uuid 虚拟机的uuid
     * @param nums 取得负载信息的条数
     * @return
     */
    public List<? extends HostLoad> getLatestLoad(String uuid, int nums);

    
    /**
     * 
     * @Title: getOneDayLoad
     * @Description:  通过uuid和日期得到虚拟机一天所有的负载信息
     * @param uuid 虚拟机的uuid
     * @param date 负载信息的时间
     * @return
     */
    public List<? extends HostLoad> getOneDayLoad(String uuid,Date date);
    

    
    /**
     * 
     * @Title: getOneDayLoad
     * @Description: 通过uuid和日期得到虚拟机一天内的平均负载信息
     * @param uuid 虚拟机的uuid
     * @param date 负载信息的时间
     * @return
     */
    public HostLoad getOneDayAvgLoad(String uuid, Date date);

}
