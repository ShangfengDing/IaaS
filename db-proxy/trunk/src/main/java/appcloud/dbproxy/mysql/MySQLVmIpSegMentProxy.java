/**
 * File: MySQLVmIpSegMentProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.VmIpSegMent;
import appcloud.common.proxy.VmIpSegMentProxy;
import appcloud.dbproxy.mysql.dao.VmIpSegmentDAO;
import appcloud.dbproxy.mysql.model.VmIpSegmentTable;

/**
 * @author weed
 *
 */
public class MySQLVmIpSegMentProxy implements VmIpSegMentProxy {

	VmIpSegmentDAO __vmIpSegmentDAO = new VmIpSegmentDAO();
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmIpSegMentProxy#findAll()
	 */
	@Override
	public List<VmIpSegMent> findAll() throws Exception {
      List<VmIpSegMent> ret = new ArrayList<VmIpSegMent>();
        List<VmIpSegmentTable> items = __vmIpSegmentDAO.findAll();
        for(VmIpSegmentTable item : items) {
            ret.add(new VmIpSegMent(item.getId(), 
                                    item.getDhcpId(), 
                                    item.getNetmask(), 
                                    item.getStartIpRange(),
                                    item.getEndIpRange(),
                                    item.getRouter(),
                                    item.getDns(),
                                    item.getSegment()));
        }
        return ret;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmIpSegMentProxy#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VmIpSegMent> findAll(Integer clusterId, Integer segment)
			throws Exception {
	    List<VmIpSegMent> ret = new ArrayList<VmIpSegMent>();
	    List<VmIpSegmentTable> items = __vmIpSegmentDAO.findByProperty2("dhcpId", clusterId, "segment", segment);
	    for(VmIpSegmentTable item : items) {
	        ret.add(new VmIpSegMent(item.getId(), 
	                                item.getDhcpId(),
	                                item.getNetmask(), 
	                                item.getStartIpRange(),
	                                item.getEndIpRange(),
	                                item.getRouter(),
	                                item.getDns(),
	                                item.getSegment()));
	    }
	    return ret;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmIpSegMentProxy#getById(java.lang.Integer)
	 */
	@Override
	public VmIpSegMent getById(Integer vmIpSegMentId) throws Exception {
	    VmIpSegmentTable item = __vmIpSegmentDAO.findById(vmIpSegMentId);
	    return new VmIpSegMent(item.getId(), 
                        item.getDhcpId(), 
                        item.getNetmask(), 
                        item.getStartIpRange(),
                        item.getEndIpRange(),
                        item.getRouter(),
                        item.getDns(),
                        item.getSegment());
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmIpSegMentProxy#save(appcloud.common.model.VmIpSegMent)
	 */
	@Override
	public void save(VmIpSegMent vmIpSegMent) throws Exception {
		__vmIpSegmentDAO.save(new VmIpSegmentTable(vmIpSegMent));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmIpSegMentProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer vmIpSegMentId) throws Exception {
		__vmIpSegmentDAO.deleteByPrimaryKey(vmIpSegMentId);
	}

}
