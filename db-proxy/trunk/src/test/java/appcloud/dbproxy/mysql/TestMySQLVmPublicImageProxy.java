package appcloud.dbproxy.mysql;

import appcloud.common.model.VmImage;
import appcloud.common.proxy.VmImageProxy;

public class TestMySQLVmPublicImageProxy {
    public static void main(String[] args) {
        VmImageProxy VmImageProxy = new MySQLVmImageProxy();
        VmImage vpi1 = new VmImage();
        vpi1.setUuid("uuid1");
        vpi1.setName("name1");
        VmImage vpi2 = new VmImage();
        vpi2.setUuid("uuid2");
        vpi2.setName("name2");
        VmImage vpi3 = new VmImage();
        vpi3.setUuid("uuid3");
        vpi3.setName("name3");
        try {
            VmImageProxy.save(vpi1);
            VmImageProxy.save(vpi2);
            VmImageProxy.save(vpi3);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        try {
            VmImageProxy.countAll();
            VmImageProxy.findAll();           
            VmImageProxy.getById(1);
            VmImageProxy.getByImageName("name1");
            VmImageProxy.getByUuid("uuid1");
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
        try {
            VmImageProxy.deleteById(1);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       
    }
    
}
