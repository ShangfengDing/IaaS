//package appcloud.admin.hbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.log4j.Logger;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
///**
// * Created by zouji on 2018/7/15.
// */
//public class HBaseConfigurationSingleton {
//    private static Logger logger = Logger.getLogger(HBaseConfigurationSingleton.class);
//
//    private static class SingletonHoldeer {
//        static Configuration conf;
//        static Connection hConnection;
//        static {
//            conf = HBaseConfiguration.create();
//            conf.addResource("hbase-site.xml");
//            logger.info("loading [conf: hbase-site.xml]");
//
//            try {
//                hConnection = ConnectionFactory.createConnection(conf);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static Configuration getConf() {
//        return SingletonHoldeer.conf;
//    }
//
//    public static Connection getConnection() {
//        return SingletonHoldeer.hConnection;
//    }
//
//    public static Table getHTable(String tableName) throws IOException {
//        return SingletonHoldeer.hConnection.getTable(TableName.valueOf(tableName));
//    }
//
//    public static void main(String[] args) {
//        System.out.println("123");
//        long createdTime = 1531476000000L;
//        Random rand = new Random(2018);
//        for (int i = 0; i < 14 * 24; i++) {
//            createdTime += i * 60000;
//            Map<String, String> map = new HashMap<>();
//            map.put("health", String.valueOf((70 + rand.nextInt() * 30)));
//        }
//    }
//}
