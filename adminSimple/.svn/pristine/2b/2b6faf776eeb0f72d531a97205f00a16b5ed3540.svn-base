package appcloud.admin.action.system;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import com.free4lab.aimonitor.hbaseProxy.common.factory.HBaseFactory;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;


import javax.print.DocFlavor;
import java.text.SimpleDateFormat;
import java.util.*;


public class HBaseAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(HBaseAction.class);
    private List<String> uuids = new ArrayList<String>();
    Map<String, String> mapMetrics = new HashMap<>();
    Map<String, String> mapHealth = new HashMap<>();
    private String uuidstr = null;
    private String uuidOne = null;


    public String findState() {

        System.out.println("The uuidOne is " + uuidOne);
//        String tableName = "iaas-aimonitor";
//        String tableName = "aimonitor";
        String tableName = Constants.HBASE_TABLENAME;

        String rowKey = "HOUR" + uuidOne + System.currentTimeMillis();
        logger.info("concat rowKey " + rowKey);
        String stopRowKey = HBaseFactory.getDataService().getImmediateRowKey(tableName, rowKey, true);
        logger.info("stopRowKey:" + stopRowKey);

        Map<String, String> map = HBaseFactory.getDataService().search(tableName, stopRowKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {

            String key = entry.getKey();
            if (key.startsWith("health")) {
                mapHealth.put(entry.getKey().split(":")[1], entry.getValue());
            } else {
                mapMetrics.put(entry.getKey().split(":")[1], entry.getValue());
            }
        }
//        logger.info(mapHealth);
//        logger.info(mapMetrics);

        return Action.SUCCESS;
    }

    public List<List<String>> findVmSummaryInfo(String uuid, int size) {
        String[] row = new String[]{"cpuUsed", "diskErrorCount", "diskUsed", "memUsed", "memSwap", "netDownload", "netUpload"};
//        String tableName = "aimonitor";
//        String tableName = "iaas-aimonitor";
        String tableName = Constants.HBASE_TABLENAME;
        Map<String, Map<String, String>> result = new TreeMap<>();
        List<List<String>> vmSummaryInfo = new ArrayList<>();
        List<String> total = new ArrayList<>();
        List<String> cpuUsed = new ArrayList<>();
        List<String> diskErrorCount = new ArrayList<>();
        List<String> diskUsed = new ArrayList<>();
        List<String> memUsed = new ArrayList<>();
        List<String> memSwap = new ArrayList<>();
        List<String> netDownload = new ArrayList<>();
        List<String> netUpload = new ArrayList<>();
//        List<String> timeLine = new ArrayList<>();
        List<String> timeLines = new ArrayList<>();
        Date dates;
        Integer times = 0;
        String rowKey = "HOUR" + uuid + System.currentTimeMillis();
        String startKey = HBaseFactory.getDataService().getOppositeRowKey(tableName, rowKey, 30*24, true);
        String startTime = startKey.substring(16, startKey.length());//...........
        long timestart = Long.parseLong(startTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date;
//        try {
//            //横坐标
//            for (long i = 0; i < 30 * 24; i++) {
////            for (long i = 30*24; i >= 0; i--) {
//                long time = (timestart + (i * 60 * 60 * 1000));
//                date = sdf.parse(sdf.format(time));
//                timeLine.add(sdf.format(date));
////                System.out.print(time + "**" + sdf.format(date) + "**");
//            }
////           System.out.println("%%%%%%%%%%%%%%%%%"+timeLine);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        logger.info("find startKey success:" + startKey);
        result = HBaseFactory.getDataService().search(tableName, startKey, rowKey, "metrics", Arrays.asList(row), false);
//        Map<String,String> a = new HashMap<>();
//        a.put("memSwap","10");
//        a.put("netDownload","10");
//        a.put("netUpload","10");
//        a.put("cpuUsed","10");
//        a.put("diskUsed","10");
//        a.put("diskErrorCount","10");
//        a.put("memUsed","10");
//        result.put("HOUR549F3503ABB91532062800000",a);
//        result.put("HOUR549F3503ABB91530324000000",a);
//        result.put("HOUR549F3503ABB91531008000000",a);
//        result.put("HOUR549F3503ABB91532361600000",a);
//        result.put("HOUR549F3503ABB91531774800000",a);
//        result.put("HOUR549F3503ABB91530208800000",a);
//        result.put("HOUR549F3503ABB91530410400000",a);
        Map<String,Map<String,String>> results = new TreeMap<>(result);
        for (Map.Entry<String,Map<String, String>> value : results.entrySet()) {
            times++;
            String key = value.getKey();
            String keys = key.substring(16,key.length());
            long timestarts = Long.parseLong(keys);
//            System.out.println("..............."+timestarts);
            try {
                dates = sdf.parse(sdf.format(timestarts));
                timeLines.add(sdf.format(dates));
            } catch (Exception e){
                e.printStackTrace();
            }

        }
//        logger.info("search " + times + "hours metrics success: "+result);
        for (Map<String, String> value : results.values()) {
//        for (Map.Entry<String,Map<String, String>> value : results.entrySet()) {
//               System.out.println(value.size());
            if (!value.containsKey("memUsed")) value.put("memUsed","0");
            if (!value.containsKey("memSwap")) value.put("memSwap","0");
            if (!value.containsKey("netDownload")) value.put("netDownload","0");
            if (!value.containsKey("netUpload")) value.put("netUpload","0");
            if (!value.containsKey("cpuUsed")) value.put("cpuUsed","0");
            if (!value.containsKey("diskUsed")) value.put("diskUsed","0");
            if (!value.containsKey("diskErrorCount")) value.put("diskErrorCount","0");
            for (Map.Entry<String, String> entry : value.entrySet()) {
                total.add(entry.getValue());
            }
        }

        try {
            for (int j = 0; j < times; j++) {
                memSwap.add(total.get(j * 7));
                netDownload.add(total.get((j * 7) + 1));
                netUpload.add(total.get((j * 7) + 2)); //"cpuUsed", "diskErrorCount", "diskUsed", "memUsed", "memSwap", "netDownload", "netUpload"
                cpuUsed.add(total.get((j * 7) + 3));
                diskUsed.add(total.get((j * 7) + 4));
                diskErrorCount.add(total.get((j * 7) + 5));
                memUsed.add(total.get((j * 7) + 6));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("info error");
        }
        vmSummaryInfo.add(cpuUsed);
        vmSummaryInfo.add(diskErrorCount);
        vmSummaryInfo.add(diskUsed);
        vmSummaryInfo.add(memUsed);
        vmSummaryInfo.add(memSwap);
        vmSummaryInfo.add(netDownload);
        vmSummaryInfo.add(netUpload);
        vmSummaryInfo.add(timeLines);
//        System.out.println("vmSummaryInfo:"+vmSummaryInfo);
        return vmSummaryInfo;
    }

    public Map<String,List<String>> findInfrastructureInfo(int times,String familyName,String[] row){
        List<String> timeLines=new ArrayList<>();
        List<String> health=new ArrayList<>();
        List<String> service=new ArrayList<>();
        List<String> platform=new ArrayList<>();
        Map<String,List<String>> map=new HashMap<>();
        String tableName=Constants.HBASE_TABLENAME;
        long currentTime=System.currentTimeMillis();
        String rowKey=currentTime+"";
        String startKey=HBaseFactory.getDataService().getOppositeRowKey(tableName, rowKey, times, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TreeMap<String,Map<String,String>> grades=new TreeMap<>(HBaseFactory.getDataService().search(tableName, startKey, rowKey, familyName, Arrays.asList(row), false));
        for (Map.Entry<String,Map<String, String>> value : grades.entrySet()) {
            times++;
            String key = value.getKey();
            long timestarts = Long.parseLong(key);
//            System.out.println("..............."+timestarts);
            try {
                timeLines.add(sdf.format(timestarts));
            } catch (Exception e){
                e.printStackTrace();
            }
            Map<String,String> valueMap=value.getValue();
            if(!valueMap.containsKey("health")){
                valueMap.put("health","60");
            }
            if(!valueMap.containsKey("service")){
                valueMap.put("service","60");
            }
            health.add(valueMap.get("health"));
            service.add(valueMap.get("service"));
            try {
                int data =(int)((Integer.parseInt(valueMap.get("health"))+Integer.parseInt(valueMap.get("service")))/2);
                platform.add(data+"");
            }catch(Exception e){
                platform.add("0");
            }
        }
        map.put("timeLines",timeLines);
        map.put("health",health);
        map.put("service",service);
        map.put("platform",platform);
        return map;
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }


    public String getUuidstr() {
        return uuidstr;
    }

    public void setUuidstr(String uuidstr) {
        this.uuidstr = uuidstr;
    }

    public String getUuidOne() {
        return uuidOne;
    }

    public void setUuidOne(String uuidOne) {
        this.uuidOne = uuidOne;
    }

    public Map<String, String> getMapMetrics() {
        return mapMetrics;
    }

    public void setMapMetrics(Map<String, String> mapMetrics) {
        this.mapMetrics = mapMetrics;
    }

    public Map<String, String> getMapHealth() {
        return mapHealth;
    }

    public void setMapHealth(Map<String, String> mapHealth) {
        this.mapHealth = mapHealth;
    }
}
