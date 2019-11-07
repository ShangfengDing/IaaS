package appcloud.dbproxy.mysql;

import appcloud.common.model.ServiceMonitor;
import appcloud.common.proxy.ServiceMonitorProxy;
import appcloud.dbproxy.mysql.dao.ServiceMonitorDAO;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static appcloud.common.model.ServiceMonitor.ServiceMonitorEnum.VM_DATA_COLLECT;
import static appcloud.common.model.ServiceMonitor.ServiceMonitorStatus.RUNNING;


public class MySQLServiceMornitorProxy implements ServiceMonitorProxy {

    public static final ServiceMonitorDAO serviceMonitorDao = new ServiceMonitorDAO();

    @Override
    public ServiceMonitor getServicesByUuid(String uuid) {
        ServiceMonitor serviceMonitor = serviceMonitorDao.findByProperty("host_uuid",uuid).get(0);
        return serviceMonitor;
    }

    @Override
    public List<? extends ServiceMonitor> findAll() throws Exception{
        return serviceMonitorDao.findAll();
    }


}
