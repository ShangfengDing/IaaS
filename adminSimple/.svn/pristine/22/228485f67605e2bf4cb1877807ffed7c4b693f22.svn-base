//package appcloud.admin.hbase;
//
//import org.apache.hadoop.hbase.*;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.log4j.Logger;
//
//import java.io.IOException;
//import java.util.*;
//
//
///**
// * Created by zouji on 2018/7/15.
// */
//public class HBaseOperator {
//    private static Logger logger = Logger.getLogger(HBaseOperator.class);
//    private static Admin hAdmin;
//    static {
//        try {
//            hAdmin = HBaseConfigurationSingleton.getConnection().getAdmin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 创建表
//     * @param tableName
//     * @param familys
//     * @throws IOException
//     * 如果存在创建的表，先删除再创建
//     */
//    public synchronized static void createTable(String tableName, Set<String> familys) {
//        logger.info("start creating new table: " + tableName);
//        try {
//            if (hAdmin.tableExists(TableName.valueOf(tableName))) {
//                hAdmin.disableTable(TableName.valueOf(tableName));
//                hAdmin.deleteTable(TableName.valueOf(tableName));
//                logger.info(tableName + " is exist, delete...");
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
//            for (String family : familys) {
//                tableDescriptor.addFamily(new HColumnDescriptor(family));
//            }
//            hAdmin.createTable(tableDescriptor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除表
//     * @param tableName
//     */
//    public synchronized static void dropTable(String tableName) {
//        try {
//            hAdmin.disableTable(TableName.valueOf(tableName));
//            hAdmin.deleteTable(TableName.valueOf(tableName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 插入数据
//     * @param tableName
//     * @param rowKey
//     * @param familyName
//     * @param keyValue
//     */
//    public static void put(String tableName, String rowKey, String familyName, Map<String, String> keyValue) {
//        Table table = null;
//        try {
//            table = HBaseConfigurationSingleton.getHTable(tableName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Put put = new Put(rowKey.getBytes());
//        for (Map.Entry<String, String> entry : keyValue.entrySet()) {
//            put.add(familyName.getBytes(), entry.getKey().getBytes(), entry.getValue().getBytes());
//        }
//        try {
//            table.put(put);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除记录
//     * @param tableName
//     * @param rowKeyList
//     */
//    public static void deleteRow(String tableName, List<byte[]> rowKeyList) {
//        Table table = null;
//        try {
//            table = HBaseConfigurationSingleton.getHTable(tableName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<Delete> deleteList = new ArrayList<>();
//        for (byte[] rowKey : rowKeyList) {
//            Delete dl = new Delete(rowKey);
//            deleteList.add(dl);
//        }
//        try {
//            table.delete(deleteList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 查找一条记录
//     * @param tableName
//     * @param rowKey
//     * @return
//     */
//    public static List<KeyValue> search(String tableName, byte[] rowKey) {
//        Table hTable = null;
//        try {
//            hTable = HBaseConfigurationSingleton.getHTable(tableName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Get get = new Get(rowKey);
//            Result r = hTable.get(get);
//            return Arrays.asList(r.raw());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 扫描一段数据
//     * @param tableName
//     * @param startRowKey
//     * @param stopRowKey
//     * @return
//     * @throws IOException
//     */
//    public static ResultScanner doScan(String tableName, byte[] startRowKey, byte[] stopRowKey) throws IOException {
//        Scan s = new Scan();
//        s.setStartRow(startRowKey);
//        s.setStopRow(stopRowKey);
//        return HBaseConfigurationSingleton.getHTable(tableName).getScanner(s);
//    }
//
//    /**
//     * 根据一端RowKey和查找数量，找到另一端RowKey
//     * @param tableName
//     * @param rowKey
//     * @param size
//     * @param timeasc
//     * @return byte[] RowKey
//     */
//    public static byte[] getOppoRowKey(String tableName, byte[] rowKey, int size, boolean timeasc) {
//        String rowKeyStr = Bytes.toString(rowKey);
//        //prex + 13位时间戳
//        String prex = rowKeyStr.length() > 13 ? rowKeyStr.substring(0, rowKeyStr.length() - 13): "";
//        String createdTime = rowKeyStr.substring(rowKeyStr.length() - 13, rowKeyStr.length());
//        Scan s = new Scan();
//        s.setStartRow(rowKey);
//        s.setReversed(!timeasc);
//        try {
//            ResultScanner rs = HBaseConfigurationSingleton.getHTable(tableName).getScanner(s);
//            Result startRowKey = null;
//            for (Result result : rs) {
//                size--;
//                if (size==0) {
//                    return result.getRow();
//                }
//                startRowKey = result;
//            }
//            return startRowKey == null? null:startRowKey.getRow();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//        long createdTime = 1531476000000L;
//        Random rand = new Random(2018);
//        for (int i = 0; i < 14 * 24; i++) {
//            createdTime += i * 60000;
//            Map<String, String> map = new HashMap<>();
//            map.put("health", String.valueOf((70 + rand.nextInt(10) * 3)));
//            put("aimonitor", String.valueOf(createdTime),"health", map);
//        }
//    }
//}
