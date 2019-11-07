package appcloud.dbproxy.mysql.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import appcloud.common.model.VmUsedIp;
import appcloud.dbproxy.mysql.MySQLVmUsedIpProxy;

/**
 * @ClassName: VmUsedIpTest
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-17 下午2:45:36
 */
public class VmUsedIpProxyTest {
    MySQLVmUsedIpProxy proxy = new MySQLVmUsedIpProxy();

    @Test
    public void testGetNULLUsedIp() {
        Integer ipSegmentId = -1;
        try {
            List<? extends VmUsedIp> usedIp = proxy.findAll(ipSegmentId);
            Assert.assertEquals(0, usedIp.size());
        }
        	catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetAUsedIp() {
        // there should be 2 test data in the cluster2dhcp table
    	Integer ipSegmentId = -2;
        try {
            List<? extends VmUsedIp> usedIp = proxy.findAll(ipSegmentId);
            Assert.assertEquals(2, usedIp.size());
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testAddRecord() {
    	Integer ipSegmentId = -3;
        try {
            VmUsedIp usedIp = new VmUsedIp(0, ipSegmentId , "192.168.0.1", "AA:AA:AA:AA:AA:AA");
            proxy.save(usedIp);            
            List<? extends VmUsedIp> usedIps = proxy.findAll(ipSegmentId);
            Assert.assertEquals(1, usedIps.size());
            proxy.deleteIp(ipSegmentId, "192.168.0.1");
            
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    @Test
    public void testDelIp() {
    	Integer ipSegmentId = -3;
        try {
            VmUsedIp usedIp = new VmUsedIp(0, ipSegmentId,  "192.168.0.1", "AA:AA:AA:AA:AA:AA");
            proxy.save(usedIp);            
            List<? extends VmUsedIp> usedIps = proxy.findAll(ipSegmentId);
            Assert.assertEquals(1, usedIps.size());
            proxy.deleteIp(ipSegmentId, "192.168.0.1");
            usedIps = proxy.findAll(ipSegmentId);
            Assert.assertEquals(0, usedIps.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
