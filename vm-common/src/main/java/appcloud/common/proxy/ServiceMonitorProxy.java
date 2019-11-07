package appcloud.common.proxy;


import appcloud.common.model.ServiceMonitor;

import java.util.List;

public interface ServiceMonitorProxy {

    /**
     *获取某个uuid主机上的全部Service信息
     *@param uuid
     *@return
     */
    public ServiceMonitor getServicesByUuid(String uuid);

    /**
     *
     */
    public List<? extends ServiceMonitor> findAll() throws Exception;

    /**
     *
     */
//    public ServiceMonitor update(ServiceMonitor serviceMonitor);

    /**
     *
     *
     */
//    public ServiceMonitor getById(Integer id);

    /**
     *
     *
     */

}
