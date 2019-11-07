package appcloud.dbproxy.mysql;

import appcloud.common.model.VmSnapshot;
import appcloud.common.proxy.VmSnapshotProxy;

public class TestMySQLVmSnapshotProxy {
    static final Integer ID = 1;
    public static void main(String[] args) {
        VmSnapshotProxy vmSnapshotProxy = new MySQLVmSnapshotProxy();
        VmSnapshot vs1 = new VmSnapshot();
        vs1.setName("name1");
        
        VmSnapshot vs2 = new VmSnapshot();
        vs2.setName("name1");
     
        try {
            vmSnapshotProxy.save(vs1);
            vmSnapshotProxy.save(vs2);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            vmSnapshotProxy.findAll(false); 
            vmSnapshotProxy.countAll();
        }
        catch (Exception e) {
            // TODO: handle exception
        }
       
        
        try {
            vmSnapshotProxy.getById(ID, false);
            vmSnapshotProxy.getByName("name1", false);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
        try {
            vmSnapshotProxy.deleteById(ID);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
    }
}
