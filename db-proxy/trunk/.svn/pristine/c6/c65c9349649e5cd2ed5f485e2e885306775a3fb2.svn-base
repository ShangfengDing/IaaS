/**
 * File: MySQLVmUsedIpProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.VmUsedIp;
import appcloud.common.proxy.VmUsedIpProxy;
import appcloud.dbproxy.mysql.dao.VmUsedIpDAO;
import appcloud.dbproxy.mysql.model.VmMacSequenceTable;
import appcloud.dbproxy.mysql.model.VmUsedIpTable;

/**
 * @author weed
 *
 */
public class MySQLVmUsedIpProxy implements VmUsedIpProxy {
	
	VmUsedIpDAO __VmUsedIpDAO = new VmUsedIpDAO();

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmUsedIpProxy#findAll()
	 */
	@Override
	public List<VmUsedIp> findAll(Integer ipSegmentId) throws Exception {
	    List<VmUsedIp> ret = new ArrayList<VmUsedIp>();
	    List<VmUsedIpTable> items = __VmUsedIpDAO.findByProperty("ipSegmentId", ipSegmentId);
	    for(VmUsedIpTable item : items ) {
	        ret.add(new VmUsedIp(item.getId(), item.getIpSegmentId(), item.getIpAddr(), item.getMac()));
	    }
	    return ret;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmUsedIpProxy#save(appcloud.common.model.VmUsedIp)
	 */
	@Override
	public void save(VmUsedIp VmUsedIp) throws Exception {
		__VmUsedIpDAO.save(new VmUsedIpTable(VmUsedIp));
	}

    /* 
    * <p>Title: deleteIp</p>
    * <p>Description: </p>
    * @param arg0
    * @param arg1
    * @throws Exception
    * @see appcloud.common.proxy.VmUsedIpProxy#deleteIp(java.lang.Integer, java.lang.String)
    */ 
    @Override
    public void deleteIp(Integer ipSegmentId, String ip) throws Exception {
        List<VmUsedIpTable> vmUsedIps = __VmUsedIpDAO.findByProperty("ipSegmentId", ipSegmentId);
        for(VmUsedIpTable vmUsedIp : vmUsedIps) {
        	// TODO: fix this fucking bug!
        	if (vmUsedIp.getIpAddr().equals(ip)) {
        		__VmUsedIpDAO.deleteByPrimaryKey(vmUsedIp.getId());
        	}
        }
    }
    
    @Override
    public Long countUsedIp(Integer ipSegmentId) throws Exception {
    	return __VmUsedIpDAO.countByProperty("ipSegmentId", ipSegmentId);
    }

}
