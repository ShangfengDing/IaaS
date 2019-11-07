package appcloud.dbproxy.mysql.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import appcloud.common.model.VmIpSegMent;
import appcloud.dbproxy.mysql.MySQLVmIpSegMentProxy;

/**
 * @ClassName: VmIpSegmentProxyTest
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-17 下午3:46:48
 */
public class VmIpSegmentProxyTest {
    
    MySQLVmIpSegMentProxy proxy = new MySQLVmIpSegMentProxy();
    
    @Test
    public void testGetNullData() {
        Integer clusterId = -1;
        try {
            List<VmIpSegMent> ipSegments = proxy.findAll(clusterId,0);
            Assert.assertEquals(0, ipSegments.size());
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    @Test
    public void testGetAllData() {
        Integer dhcpId = -2;
        try {
            //There should be 2 test data in database
            List<VmIpSegMent> ipSegments = proxy.findAll(dhcpId,0);
            Assert.assertEquals(2, ipSegments.size());
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    @Test
    public void testAddData() {
        Integer dhcpId = -3;
        try {
            VmIpSegMent vmIpSegment = new VmIpSegMent(0, dhcpId, "192.168.0.2", "255.255.255.0",
                                                      "192.168.0.24", "192.168.0.27", "192.168.0.1", "192.168.10.1", 0);
            proxy.save(vmIpSegment);
            List<VmIpSegMent> ret = proxy.findAll(dhcpId, 0);  
            Assert.assertEquals(1, ret.size());
            vmIpSegment = ret.get(0);            
            proxy.deleteById(vmIpSegment.getId());
            ret = proxy.findAll(dhcpId, 0);  
            Assert.assertEquals(0, ret.size());
        } 
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
