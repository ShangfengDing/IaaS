package appcloud.dbproxy.mysql;

import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.VmInstanceTypeProxy;

public class TestMySQLVmInstanceTypeProxy {
    public static void main(String[] args) {
        VmInstanceTypeProxy vmInstanceTypeProxy = new MySQLVmInstanceTypeProxy();
        try {
            vmInstanceTypeProxy.save(new VmInstanceType(null, "flavorUuid1", "name1", 1, 20485, 20));
            vmInstanceTypeProxy.save(new VmInstanceType(null, "flavorUuid2", "name2", 1, 20485, 20));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmInstanceTypeProxy.countAll();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmInstanceTypeProxy.findAll();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        try {
            vmInstanceTypeProxy.getById(1);
            vmInstanceTypeProxy.getByName("name1");
            vmInstanceTypeProxy.getByUuid("flavorUuid1");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmInstanceTypeProxy.deleteById(1);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            vmInstanceTypeProxy.deleteByUuid("flavorUuid2");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
    }
}
