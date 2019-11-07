//package appcloud.monitor;
//
//import appcloud.admin.hbase.HBaseOperator;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
///**
// * Created by zouji on 2018/7/16.
// */
//public class InsertDataTest {
//
//    public static void main(String[] args) {
//        long createdTime = 1531476000000L;
//        String[] uuids = new String[]{"549F3503ABB9", "F8BC124230C4", "F8BC124233DC", "0026B94CBD3E", "848F69E01087"};
//        String prex = "HOUR";
//        Random rand = new Random(2018);
//        for (String uuid:uuids) {
//            for (int i = 0; i < 14 * 24; i++) {
//                createdTime -= 60 * 60000;
//                Map<String, String> map = new HashMap<>();
//                map.put("cpuUsed", String.valueOf(10 + rand.nextInt(10) * 3));
//                map.put("memUsed", String.valueOf(50 + rand.nextInt(10) * 4));
//                map.put("swap", String.valueOf(10 + rand.nextInt(5) * 2));
//                map.put("diskUsed", String.valueOf(30 + rand.nextInt(2) * 4));
//                map.put("diskErrorCount", String.valueOf(3 + rand.nextInt(2) * 4));
//                map.put("netUpload", String.valueOf(10240 + rand.nextInt(10000) * 3));
//                map.put("netDownload", String.valueOf(20480 + rand.nextInt(10000) * 3));
//                HBaseOperator.put("aimonitor", prex + uuid + createdTime, "health", map);
//            }
//        }
//    }
//}
