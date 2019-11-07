package appcloud.common.proxy;

import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;

/**
 * 
* @ClassName: VmDailyLoadProxy
* @author haowei.yu
* @date 2013-4-10 上午10:07:42
 */
public interface DailyLoadProxy extends CommonLoadProxy { 
    /**
     * 
     * @Title: getALlDayLoadOfMonth
     * @Description: 通过uuid和date取得虚拟机一月中所有天的负载信息
     * @param uuid  虚拟机的uuid
     * @param date  负载信息的纪录日期
     * @return
     */
    public List<? extends HostLoad> getAllDayLoadOfMonth(String uuid, Date date);
    
    /**
     * 
     * @Title: getOneDayLoad
     * @Description: 通过uuid和date取得虚拟机一月中某天的负载信息
     * @param uuid  虚拟机的uuid
     * @param date  负载信息的纪录日期
     * @return
     */
    public HostLoad getOneDayLoad(String uuid, Date date);
    
}
