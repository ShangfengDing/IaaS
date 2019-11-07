package appcloud.dbproxy.mysql;

import appcloud.common.model.VmInstance;
import appcloud.common.proxy.VmInstanceProxy;

public class TestMySQLVmInstanceProxy {
    public static void main(String[] args) {
        VmInstanceProxy vmInstanceProxy = new MySQLVmInstanceProxy();
        VmInstance vi1 = new VmInstance();
        vi1.setId(null);
        vi1.setUuid("vmInstancePrxy1");
        VmInstance vi2 = new VmInstance();
        vi2.setId(null);
        vi2.setUuid("vmInstancePrxy2");

        try {
            vmInstanceProxy.save(vi1);
            vmInstanceProxy.save(vi2);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
//        try {
//            vmInstanceProxy.findAll(true, true, true, true, true, true, true, true);
//        }
//        catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        try {
            vmInstanceProxy.findAll(false, false, false, false, false, false, false, false);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmInstanceProxy.countAll();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
//        try {
//            vmInstanceProxy.getByUuid("vmInstancePrxy1", true, true, true, true, true, true, true, true);
//        }
//        catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        try {
            vmInstanceProxy.getByUuid("vmInstancePrxy1", false, false, false, false, false, false, false, false);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmInstanceProxy.findAllUuid();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
//        try {
//            vmInstanceProxy.deleteById(1);
//        }
//        catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        try {
            vmInstanceProxy.deleteByUuid("vmInstancePrxy1");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            VmInstance instance = vmInstanceProxy.getByUuid("ReservedVmTest1", false, false, false, false, false, false, true, false);
            //System.out.println(instance.getVmVirtualInterfaces().get(0).getMac());		
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
