package appcloud.common.proxy;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;
public interface CommonLoadProxy {

    /**
     * 
     * @Title:
     * @Description:保存一个hostLoad Or vmLoad实例到数据库
     * @param vmLoad
     */
    public void save(HostLoad hostLoad);
    
    /**
     * 通过uuid获取Host或者虚拟机在某一段时间内的负载
     * @param uuid
     * @param startTime
     * @param endTime
     * @return
     */
    public List<? extends HostLoad> getLoads(String uuid, Date startTime, Date endTime);
    
    /**
     * 
     * @param uuid 通过uuid获取某一段时间的平均负载
     * @param startTime
     * @param endTime
     * @return
     */
    public HostLoad getAvgLoad(String uuid, Date startTime, Date endTime);
    
    /**
     * 获取一段时间内的评价负载
     * @param uuid
     * @param startTime
     * @param endTime
     * @return
     */
    public List<? extends HostLoad> getAvgLoads(Date startTime, Date endTime);
    
    /**
     * 删除某个vmload的历史数据
     * @param uuid vmuuid
     * @param date
     * @return
     */
    public int deleteBefore(String uuid, Date time);
    
    /**
     * 删除历史数据
     * @param date
     * @return
     */
    public int deleteAllBefore(Date time);
    
    /**
     * 删除一条记录
     * @param id
     */
    public void delete(Integer id);
    

    /**
     * 删除记录
     * @param time
     * @return
     */
    public int deleteByDate(Date time);
}
