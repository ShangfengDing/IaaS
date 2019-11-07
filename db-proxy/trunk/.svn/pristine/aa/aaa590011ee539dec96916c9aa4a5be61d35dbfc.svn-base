package appcloud.dbproxy.mysql.dao;

import java.util.Date;
import java.util.List;
import appcloud.common.model.VmLoad;
import appcloud.dbproxy.mysql.model.VmDailyLoadTable;

public class VmDailyLoadDao extends CommonLoadDAO<VmDailyLoadTable> {

    static final String PU_NAME = "AppcloudPU";

    @Override
    public Class<VmDailyLoadTable> getEntityClass() {
        return VmDailyLoadTable.class;
    }

    public VmLoad getOneDayLoad(String uuid, Date date) {
    	VmLoadDao.clearTime(date);
    	List<VmDailyLoadTable> list = findByProperty2("uuid", uuid, "time", date);
    	if (!list.isEmpty()) {
    		return list.get(0);
    	}
    	return null;
    }
    
}
