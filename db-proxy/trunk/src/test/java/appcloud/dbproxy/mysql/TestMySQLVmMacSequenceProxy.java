package appcloud.dbproxy.mysql;

import appcloud.common.model.VmMacSequence;
import appcloud.common.proxy.VmMacSequenceProxy;

public class TestMySQLVmMacSequenceProxy {
    static final Integer MAX = 100;
    public static void main(String[] args) {
       
        VmMacSequenceProxy vmMacSequenceProxy = new MySQLVmMacSequenceProxy();
        try {
            vmMacSequenceProxy.save(new VmMacSequence(null, 1, "ipSegment1", MAX));
            vmMacSequenceProxy.save(new VmMacSequence(null, 2, "ipSegment2", MAX));
            vmMacSequenceProxy.save(new VmMacSequence(null, 2, "ipSegment3", MAX));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        try {
            vmMacSequenceProxy.findAll();
            
            vmMacSequenceProxy.getByClusterId(1);
            
            vmMacSequenceProxy.getById(1);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
        try {
            vmMacSequenceProxy.deleteById(1);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            vmMacSequenceProxy.deleteByClusterId(2);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        
      
    }
}
