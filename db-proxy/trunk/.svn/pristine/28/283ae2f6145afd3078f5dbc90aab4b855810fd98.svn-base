package appcloud.dbproxy.mysql;

import appcloud.common.model.HostLoad;

import java.sql.Timestamp;

public class TestMySQLHostLoadProxy {
    public static void main(String[] args) {
        MySQLHostLoadProxy proxy = new MySQLHostLoadProxy();      
        proxy.save(new HostLoad(null,"uuid111",new Timestamp(System.currentTimeMillis()),
              0.78f,89f, 89f, 89f, 89f, 89f ,89f ,89f));
        proxy.save(new HostLoad(null,"uuid111",new Timestamp(System.currentTimeMillis()),
                                0.78f,89f, 89f, 89f, 89f, 89f ,89f ,89f));
        proxy.save(new HostLoad(null,"uuid111",new Timestamp(System.currentTimeMillis()),
                                0.78f,89f, 89f, 89f, 89f, 89f ,89f ,89f));
        proxy.save(new HostLoad(null,"uuid111",new Timestamp(System.currentTimeMillis()),
                                0.78f,89f, 89f, 89f, 89f, 89f ,89f ,89f));
        
        proxy.getLatestLoad("uuid111");
        proxy.getLatestLoad("uuid111", 10);
        

    }
}
