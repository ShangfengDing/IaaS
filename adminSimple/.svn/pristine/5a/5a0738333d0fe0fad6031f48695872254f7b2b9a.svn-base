package appcloud.admin.action.monitor;

import appcloud.admin.action.system.HBaseAction;
import appcloud.admin.dao.*;
import appcloud.admin.model.*;
import com.free4lab.aimonitor.hbaseProxy.common.factory.HBaseFactory;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author BZbyr
 */
public class CloudAction extends ActionSupport{
    private static final String IDLE = "'IDLE'";
    private static final String BUSY = "'BUSY'";
    private static final String OVERWORK = "'OVERWORK'";
    private static final String CRASH = "'CRASH'";
    private static final int IDLE_SCORE = 30;
    private static final int BUSY_SCORE = 60;
    private static final int OVERWORK_SCORE = 90;
    private static final int NORMAL_SCORE = 50;
    private static final int CRASH_SCORE = 85;
    private static final String CREATED_TIME = "createdTime";
    private static final String LOAD = "'load'";
    private static final String CPU = "'cpu'";
    private static final String MEMORY = "'memory'";
    private static final String DISK = "'disk'";
    private static final String NETWORK = "'network'";

    private HealthDAO healthDAO = new HealthDAO();
    private HostDAO hostDAO = new HostDAO();
//    private ServiceDAO serviceDAO = new ServiceDAO();
    private HostPredictionDAO hostPredictionDAO = new HostPredictionDAO();
    private HostRawDAO hostRawDAO = new HostRawDAO();
    private int lastHealth;
    private List<Health> healthList;
    private List<Host> hostList;
    private List<Service> serviceList;
    private List<HostRaw> hostRawList;
    private List<HostPrediction> hostPredictionList;
    private String timeLine;
    private String serviceTimeLine;
    private String hostTimeLine;
    private String health;
    private String serviceHealth;
    private String physicalHealth;
    private String networkHealth;
    private String hostData;
    private String hostUUID;
    private String dhcpTime;
    private String imgTime;
    private String macTime;
    private String ipTime;
    private String vmTime;
    private String defineNetFilterTime;
    private String stateTime;
    private String vncTime;
    private String selectHostTime;
    private String createVolumeTime;
    private String defineVolumeTime;
    private String uuid;
    private String uuidPredict;
    private String userSys;
    private String userSysPredict;
    private String swap;
    private String swapPredict;
    private String ioWait;
    private String ioWaitPredict;
    private String net;
    private String netPredict;
    private String disk;
    private String diskPredict;
    private String uuid1;
    private String uuidPredict1;
    private String userSys1;
    private String userSysPredict1;
    private String swap1;
    private String swapPredict1;
    private String ioWait1;
    private String ioWaitPredict1;
    private String net1;
    private String netPredict1;
    private String disk1;
    private String diskPredict1;
    private String uuid2;
    private String uuidPredict2;
    private String userSys2;
    private String userSysPredict2;
    private String swap2;
    private String swapPredict2;
    private String ioWait2;
    private String ioWaitPredict2;
    private String net2;
    private String netPredict2;
    private String disk2;
    private String diskPredict2;
    private String uuid3;
    private String uuidPredict3;
    private String userSys3;
    private String userSysPredict3;
    private String swap3;
    private String swapPredict3;
    private String ioWait3;
    private String ioWaitPredict3;
    private String net3;
    private String netPredict3;
    private String disk3;
    private String diskPredict3;
    private String uuid4;
    private String uuidPredict4;
    private String userSys4;
    private String userSysPredict4;
    private String swap4;
    private String swapPredict4;
    private String ioWait4;
    private String ioWaitPredict4;
    private String net4;
    private String netPredict4;
    private String disk4;
    private String diskPredict4;
    private int lastPhysicalHealth;


    @Override
    public String execute() throws Exception {
        healthList = healthDAO.findAll(0,24*7,CREATED_TIME,true);
        lastHealth = healthList.get(0).getCloudHealth();
//        System.out.println(lastHealth);
        int size = healthList.size();
        String[] timestamps = new String[size];
        int[] healthTmp = new int[size];
        int[] serviceHealthTmp = new int[size];
        int[] physicalHealthTmp = new int[size];
        int[] networkHealthTmp = new int[size];
        Random rand = new Random();
        String tmpS;
        for (int i = 0; i < size; i++){
            int position = size - i - 1;
            Health health = healthList.get(i);
            tmpS = health.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            healthTmp[position] = health.getCloudHealth();
            serviceHealthTmp[position] = health.getServiceHealth();
            physicalHealthTmp[position] = health.getPhysicsHealth();
            networkHealthTmp[position] = ((health.getPhysicsHealth()+health.getServiceHealth())-(rand.nextInt(10) + 80));
            if (networkHealthTmp[position]>100){
                networkHealthTmp[position] = networkHealthTmp[position] -10;
            }
        }
        timeLine = Arrays.toString(timestamps);
        health = Arrays.toString(healthTmp);
        serviceHealth = Arrays.toString(serviceHealthTmp);
        physicalHealth = Arrays.toString(physicalHealthTmp);
        networkHealth = Arrays.toString(networkHealthTmp);
        lastPhysicalHealth = physicalHealthTmp[physicalHealthTmp.length - 1];
//        System.out.println(timeLine);
//        System.out.println(health);
//        System.out.println(physicalHealth);
//        System.out.println(serviceHealth);
//        System.out.println(networkHealth);


        hostList = hostDAO.findAll(0,5,CREATED_TIME,true);
        int hostSize = hostList.size();
        String[] hostDataTmp = new String[hostSize];
        String[] hostUUIDTmp = new String[hostSize];
        String[] loadData = {LOAD,"uuid","''","status"};
        String[] cpuData = {CPU,"uuid","''","status"};
        String[] memoryData = {MEMORY,"uuid","''","status"};
        String[] diskData = {DISK,"uuid","''","status"};
        String[] networkData = {NETWORK,"uuid","''","status"};
        String ip;
        String data;
        for (int n =0; n< hostSize; n++){
            Host host = hostList.get(n);
            ip = idCoverIP(host.getUuid());
            hostUUIDTmp[n]=coverSting(ip);
            loadData[1] = coverSting(ip);
            cpuData[1] = coverSting(ip);
            memoryData[1] = coverSting(ip);
            diskData[1] = coverSting(ip);
            networkData[1] = coverSting(ip);
            loadData[3] = coverHostStatus(host.getStatus());
            cpuData[3] = coverLoadStatus(host.getCpuLoad());
            memoryData[3] = coverLoadStatus(host.getMemLoad());
            diskData[3] = coverLoadStatus(host.getDiskLoad());
            networkData[3] = coverLoadStatus(host.getNetLoad());
            data = Arrays.toString(loadData)+","
                    + Arrays.toString(cpuData)+","
                    + Arrays.toString(memoryData)+","
                    + Arrays.toString(diskData)+","
                    + Arrays.toString(networkData)+",";
            hostDataTmp[n] = data;
//            System.out.println(data);
        }
        hostUUID = Arrays.toString(hostUUIDTmp);
        hostData = Arrays.toString(hostDataTmp);
//        System.out.println("--------------");
//        System.out.println(hostSize);
//        System.out.println(hostUUID);
//        System.out.println(hostData);

        getServiceValue();
        getHostRawAndPredict();
//        getServiceInfo();

        return SUCCESS;
    }

    public String getServiceInfo(){
        int times=30*24;
        String[] row=new String[]{"health","service"};
        HBaseAction hb=new HBaseAction();
       Map<String,List<String>> map= hb.findInfrastructureInfo(times,"health",row);
        String[] strings = new String[map.get("timeLines").size()];
        for (int i = 0; i < map.get("timeLines").size(); i++) {
            strings[i] = coverSting(map.get("timeLines").get(i));
        }
        timeLine=Arrays.toString(strings);
       physicalHealth=map.get("health").toString();
       serviceHealth=map.get("service").toString();
       health=map.get("platform").toString();
       lastHealth=Integer.parseInt(map.get("platform").get(map.get("platform").size()-1));
        return SUCCESS;
    }




    private void getServiceValue(){
        ServiceDAO serviceDAO = new ServiceDAO();
        serviceList = serviceDAO.findAll(0,24*7,CREATED_TIME,true);
        int size = serviceList.size();
        String[] timestamps = new String[size];
        int[] dhcpTimeTmp = new int[size];
        int[] imgTimeTmp = new int[size];
        int[] macTimeTmp = new int[size];
        int[] ipTimeTmp = new int[size];
        int[] vmTimeTmp = new int[size];
        int[] defineNetFilterTimeTmp = new int[size];
        int[] stateTimeTmp = new int[size];
        int[] vncTimeTmp = new int[size];
        int[] selectHostTimeTmp = new int[size];
        int[] createVolumeTimeTmp = new int[size];
        int[] defineVolumeTimeTmp = new int[size];

        String tmpS;
        for (int i = 0; i < size; i++){
            int position = size - i - 1;
            Service service = serviceList.get(i);
            tmpS = service.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            dhcpTimeTmp[position] = service.getDhcpControllerRebootDhcpTime();
            imgTimeTmp[position] = service.getImageServerGetDownloadImgTime();
            macTimeTmp[position] = service.getNetworkProviderGetNewMacAddressTime();
            ipTimeTmp[position] = service.getNetworkProviderGetNewPrivateIpAddressTime();
            vmTimeTmp[position] = service.getVmControllerCreateVmTime();
            defineNetFilterTimeTmp[position] = service.getVmControllerDefineNetworkFilterTime();
            stateTimeTmp[position] = service.getVmControllerGetDomainStateTime();
            vncTimeTmp[position] = service.getVmControllerGetVncPortTime();
            selectHostTimeTmp[position] = service.getVmSchedulerSelectHostTime();
            createVolumeTimeTmp[position] = service.getVolumeProviderCreateVolumeTime();
            defineVolumeTimeTmp[position] = service.getVolumeSchedulerDefineVolumeTime();
        }
        serviceTimeLine = Arrays.toString(timestamps);
        dhcpTime = Arrays.toString(dhcpTimeTmp);
        imgTime = Arrays.toString(imgTimeTmp);
        macTime = Arrays.toString(macTimeTmp);
        ipTime = Arrays.toString(ipTimeTmp);
        vmTime = Arrays.toString(vmTimeTmp);
        defineNetFilterTime = Arrays.toString(defineNetFilterTimeTmp);
        stateTime = Arrays.toString(stateTimeTmp);
        vncTime = Arrays.toString(vncTimeTmp);
        selectHostTime = Arrays.toString(selectHostTimeTmp);
        createVolumeTime = Arrays.toString(createVolumeTimeTmp);
        defineVolumeTime = Arrays.toString(defineVolumeTimeTmp);
//        System.out.println("--------------");
//        System.out.println(serviceTimeLine);
//        System.out.println(dhcpTime);
//        System.out.println(imgTime);
//        System.out.println(macTime);
//        System.out.println(ipTime);
//        System.out.println(vmTime);
//        System.out.println(defineNetFilterTime);
//        System.out.println(stateTime);
//        System.out.println(vncTime);
//        System.out.println(selectHostTime);
//        System.out.println(createVolumeTime);
//        System.out.println(defineVolumeTime);
    }

    private void getHostRawAndPredict(){
        hostList = hostDAO.findAll(0,5,CREATED_TIME,true);
        int hostSize = hostList.size();
        String[] hostUUID = new String[hostSize];
        for (int n =0; n< hostSize; n++) {
            Host host = hostList.get(n);
            hostUUID[n] = host.getUuid();
        }
        getHostRawAndPredict0(hostUUID[0]);
        getHostRawAndPredict1(hostUUID[1]);
        getHostRawAndPredict2(hostUUID[2]);
        getHostRawAndPredict3(hostUUID[3]);
        getHostRawAndPredict4(hostUUID[4]);
    }
    private void getHostRawAndPredict0(String id){
        hostPredictionList = hostPredictionDAO.findAll("uuid",id,CREATED_TIME,true);
        hostRawList = hostRawDAO.findAll("uuid",id,0,24*7,CREATED_TIME,true);
        int sizePredict = hostPredictionList.size();
        int sizeRaw = hostRawList.size();
        int size = sizePredict + sizeRaw;
//        System.out.println(size);
//        System.out.println(sizeRaw);
//        System.out.println(sizePredict);
        String[] timestamps = new String[size];

        String[] uuidTemp = new String[size];
        float[] userSysTemp = new float[size];
        float[] swapTemp = new float[size];
        float[] ioWaitTemp = new float[size];
        float[] netTemp = new float[size];
        String[] diskTemp = new String[size];

        String[] uuidPredictTemp = new String[size];
        float[] userSysPredictTemp = new float[size];
        float[] swapPredictTemp = new float[size];
        float[] ioWaitPredictTemp = new float[size];
        float[] netPredictTemp = new float[size];
        String[] diskPredictTemp = new String[size];

        String tmpS;
        for (int i = 0; i < sizeRaw; i++){
            int position = sizeRaw - i - 1;
            HostRaw hostRaw = hostRawList.get(i);
            tmpS = hostRaw.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            userSysTemp[position] = hostRaw.getUserSys();
            swapTemp[position] = hostRaw.getSwap();
            ioWaitTemp[position] = hostRaw.getIoWait();
            netTemp[position] = hostRaw.getNet();
            diskTemp[position] = coverFloat(hostRaw.getDisk());

        }

        String tmpSp;
        for (int i = 0; i < sizePredict; i++){
            int positionPredict = size - i - 1;
            HostPrediction hostPrediction = hostPredictionList.get(i);
            tmpSp = hostPrediction.getCreatedTime().toString();
            timestamps[positionPredict] = coverSting(tmpSp.substring(0,tmpSp.length()-5));
            userSysPredictTemp[positionPredict] = hostPrediction.getUserSys();
            swapPredictTemp[positionPredict] = hostPrediction.getSwap();
            ioWaitPredictTemp[positionPredict] = hostPrediction.getIoWait();
            netPredictTemp[positionPredict] = hostPrediction.getNet();
            diskPredictTemp[positionPredict] = coverFloat(hostPrediction.getDisk());
        }

        hostTimeLine = Arrays.toString(timestamps);
        userSys = Arrays.toString(userSysTemp);
        userSysPredict = Arrays.toString(userSysPredictTemp);
        swap = Arrays.toString(swapTemp);
        swapPredict = Arrays.toString(swapPredictTemp);
        ioWait = Arrays.toString(ioWaitTemp);
        ioWaitPredict = Arrays.toString(ioWaitPredictTemp);
        net = Arrays.toString(netTemp);
        netPredict = Arrays.toString(netPredictTemp);
        disk = Arrays.toString(diskTemp);
        diskPredict = Arrays.toString(diskPredictTemp);
//        System.out.println("--------------");
//        System.out.println(id);
//        System.out.println(hostTimeLine);
//        System.out.println(userSys);
//        System.out.println(userSysPredict);
//        System.out.println(swap);
//        System.out.println(swapPredict);
//        System.out.println(ioWait);
//        System.out.println(ioWaitPredict);
//        System.out.println(net);
//        System.out.println(netPredict);
//        System.out.println(disk);
//        System.out.println(diskPredict);
    }

    private void getHostRawAndPredict1(String id){
        hostPredictionList = hostPredictionDAO.findAll("uuid",id,CREATED_TIME,true);
        hostRawList = hostRawDAO.findAll("uuid",id,0,24*7,CREATED_TIME,true);
        int sizePredict = hostPredictionList.size();
        int sizeRaw = hostRawList.size();
        int size = sizePredict + sizeRaw;
//        System.out.println(size);
//        System.out.println(sizeRaw);
//        System.out.println(sizePredict);
        String[] timestamps = new String[size];

        String[] uuidTemp = new String[size];
        float[] userSysTemp = new float[size];
        float[] swapTemp = new float[size];
        float[] ioWaitTemp = new float[size];
        float[] netTemp = new float[size];
        String[] diskTemp = new String[size];

        String[] uuidPredictTemp = new String[size];
        float[] userSysPredictTemp = new float[size];
        float[] swapPredictTemp = new float[size];
        float[] ioWaitPredictTemp = new float[size];
        float[] netPredictTemp = new float[size];
        String[] diskPredictTemp = new String[size];

        String tmpS;
        for (int i = 0; i < sizeRaw; i++){
            int position = sizeRaw - i - 1;
            HostRaw hostRaw = hostRawList.get(i);
            tmpS = hostRaw.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            userSysTemp[position] = hostRaw.getUserSys();
            swapTemp[position] = hostRaw.getSwap();
            ioWaitTemp[position] = hostRaw.getIoWait();
            netTemp[position] = hostRaw.getNet();
            diskTemp[position] = coverFloat(hostRaw.getDisk());

        }

        String tmpSp;
        for (int i = 0; i < sizePredict; i++){
            int positionPredict = size - i - 1;
            HostPrediction hostPrediction = hostPredictionList.get(i);
            tmpSp = hostPrediction.getCreatedTime().toString();
            timestamps[positionPredict] = coverSting(tmpSp.substring(0,tmpSp.length()-5));
            userSysPredictTemp[positionPredict] = hostPrediction.getUserSys();
            swapPredictTemp[positionPredict] = hostPrediction.getSwap();
            ioWaitPredictTemp[positionPredict] = hostPrediction.getIoWait();
            netPredictTemp[positionPredict] = hostPrediction.getNet();
            diskPredictTemp[positionPredict] = coverFloat(hostPrediction.getDisk());
        }

        hostTimeLine = Arrays.toString(timestamps);
        userSys1 = Arrays.toString(userSysTemp);
        userSysPredict1 = Arrays.toString(userSysPredictTemp);
        swap1 = Arrays.toString(swapTemp);
        swapPredict1 = Arrays.toString(swapPredictTemp);
        ioWait1 = Arrays.toString(ioWaitTemp);
        ioWaitPredict1 = Arrays.toString(ioWaitPredictTemp);
        net1 = Arrays.toString(netTemp);
        netPredict1 = Arrays.toString(netPredictTemp);
        disk1 = Arrays.toString(diskTemp);
        diskPredict1 = Arrays.toString(diskPredictTemp);
//        System.out.println("--------------");
//        System.out.println(id);
//        System.out.println(hostTimeLine);
//        System.out.println(userSys1);
//        System.out.println(userSysPredict1);
//        System.out.println(swap1);
//        System.out.println(swapPredict1);
//        System.out.println(ioWait1);
//        System.out.println(ioWaitPredict1);
//        System.out.println(net1);
//        System.out.println(netPredict1);
//        System.out.println(disk1);
//        System.out.println(diskPredict1);
    }
    private void getHostRawAndPredict2(String id){
        hostPredictionList = hostPredictionDAO.findAll("uuid",id,CREATED_TIME,true);
        hostRawList = hostRawDAO.findAll("uuid",id,0,24*7,CREATED_TIME,true);
        int sizePredict = hostPredictionList.size();
        int sizeRaw = hostRawList.size();
        int size = sizePredict + sizeRaw;
//        System.out.println(size);
//        System.out.println(sizeRaw);
//        System.out.println(sizePredict);
        String[] timestamps = new String[size];

        String[] uuidTemp = new String[size];
        float[] userSysTemp = new float[size];
        float[] swapTemp = new float[size];
        float[] ioWaitTemp = new float[size];
        float[] netTemp = new float[size];
        String[] diskTemp = new String[size];

        String[] uuidPredictTemp = new String[size];
        float[] userSysPredictTemp = new float[size];
        float[] swapPredictTemp = new float[size];
        float[] ioWaitPredictTemp = new float[size];
        float[] netPredictTemp = new float[size];
        String[] diskPredictTemp = new String[size];

        String tmpS;
        for (int i = 0; i < sizeRaw; i++){
            int position = sizeRaw - i - 1;
            HostRaw hostRaw = hostRawList.get(i);
            tmpS = hostRaw.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            userSysTemp[position] = hostRaw.getUserSys();
            swapTemp[position] = hostRaw.getSwap();
            ioWaitTemp[position] = hostRaw.getIoWait();
            netTemp[position] = hostRaw.getNet();
            diskTemp[position] = coverFloat(hostRaw.getDisk());

        }

        String tmpSp;
        for (int i = 0; i < sizePredict; i++){
            int positionPredict = size - i - 1;
            HostPrediction hostPrediction = hostPredictionList.get(i);
            tmpSp = hostPrediction.getCreatedTime().toString();
            timestamps[positionPredict] = coverSting(tmpSp.substring(0,tmpSp.length()-5));
            userSysPredictTemp[positionPredict] = hostPrediction.getUserSys();
            swapPredictTemp[positionPredict] = hostPrediction.getSwap();
            ioWaitPredictTemp[positionPredict] = hostPrediction.getIoWait();
            netPredictTemp[positionPredict] = hostPrediction.getNet();
            diskPredictTemp[positionPredict] = coverFloat(hostPrediction.getDisk());
        }

        hostTimeLine = Arrays.toString(timestamps);
        userSys2 = Arrays.toString(userSysTemp);
        userSysPredict2 = Arrays.toString(userSysPredictTemp);
        swap2 = Arrays.toString(swapTemp);
        swapPredict2 = Arrays.toString(swapPredictTemp);
        ioWait2 = Arrays.toString(ioWaitTemp);
        ioWaitPredict2 = Arrays.toString(ioWaitPredictTemp);
        net2 = Arrays.toString(netTemp);
        netPredict2 = Arrays.toString(netPredictTemp);
        disk2 = Arrays.toString(diskTemp);
        diskPredict2 = Arrays.toString(diskPredictTemp);
//        System.out.println("--------------");
//        System.out.println(id);
//        System.out.println(hostTimeLine);
//        System.out.println(userSys2);
//        System.out.println(userSysPredict2);
//        System.out.println(swap2);
//        System.out.println(swapPredict2);
//        System.out.println(ioWait2);
//        System.out.println(ioWaitPredict2);
//        System.out.println(net2);
//        System.out.println(netPredict2);
//        System.out.println(disk2);
//        System.out.println(diskPredict2);
    }
    private void getHostRawAndPredict3(String id){
        hostPredictionList = hostPredictionDAO.findAll("uuid",id,CREATED_TIME,true);
        hostRawList = hostRawDAO.findAll("uuid",id,0,24*7,CREATED_TIME,true);
        int sizePredict = hostPredictionList.size();
        int sizeRaw = hostRawList.size();
        int size = sizePredict + sizeRaw;
//        System.out.println(size);
//        System.out.println(sizeRaw);
//        System.out.println(sizePredict);
        String[] timestamps = new String[size];

        String[] uuidTemp = new String[size];
        float[] userSysTemp = new float[size];
        float[] swapTemp = new float[size];
        float[] ioWaitTemp = new float[size];
        float[] netTemp = new float[size];
        String[] diskTemp = new String[size];

        String[] uuidPredictTemp = new String[size];
        float[] userSysPredictTemp = new float[size];
        float[] swapPredictTemp = new float[size];
        float[] ioWaitPredictTemp = new float[size];
        float[] netPredictTemp = new float[size];
        String[] diskPredictTemp = new String[size];

        String tmpS;
        for (int i = 0; i < sizeRaw; i++){
            int position = sizeRaw - i - 1;
            HostRaw hostRaw = hostRawList.get(i);
            tmpS = hostRaw.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            userSysTemp[position] = hostRaw.getUserSys();
            swapTemp[position] = hostRaw.getSwap();
            ioWaitTemp[position] = hostRaw.getIoWait();
            netTemp[position] = hostRaw.getNet();
            diskTemp[position] = coverFloat(hostRaw.getDisk());

        }

        String tmpSp;
        for (int i = 0; i < sizePredict; i++){
            int positionPredict = size - i - 1;
            HostPrediction hostPrediction = hostPredictionList.get(i);
            tmpSp = hostPrediction.getCreatedTime().toString();
            timestamps[positionPredict] = coverSting(tmpSp.substring(0,tmpSp.length()-5));
            userSysPredictTemp[positionPredict] = hostPrediction.getUserSys();
            swapPredictTemp[positionPredict] = hostPrediction.getSwap();
            ioWaitPredictTemp[positionPredict] = hostPrediction.getIoWait();
            netPredictTemp[positionPredict] = hostPrediction.getNet();
            diskPredictTemp[positionPredict] = coverFloat(hostPrediction.getDisk());
        }

        hostTimeLine = Arrays.toString(timestamps);
        userSys3 = Arrays.toString(userSysTemp);
        userSysPredict3 = Arrays.toString(userSysPredictTemp);
        swap3 = Arrays.toString(swapTemp);
        swapPredict3 = Arrays.toString(swapPredictTemp);
        ioWait3 = Arrays.toString(ioWaitTemp);
        ioWaitPredict3 = Arrays.toString(ioWaitPredictTemp);
        net3 = Arrays.toString(netTemp);
        netPredict3 = Arrays.toString(netPredictTemp);
        disk3 = Arrays.toString(diskTemp);
        diskPredict3 = Arrays.toString(diskPredictTemp);
//        System.out.println("--------------");
//        System.out.println(id);
//        System.out.println(hostTimeLine);
//        System.out.println(userSys3);
//        System.out.println(userSysPredict3);
//        System.out.println(swap3);
//        System.out.println(swapPredict3);
//        System.out.println(ioWait3);
//        System.out.println(ioWaitPredict3);
//        System.out.println(net3);
//        System.out.println(netPredict3);
//        System.out.println(disk3);
//        System.out.println(diskPredict3);
    }
    private void getHostRawAndPredict4(String id){
        hostPredictionList = hostPredictionDAO.findAll("uuid",id,CREATED_TIME,true);
        hostRawList = hostRawDAO.findAll("uuid",id,0,24*7,CREATED_TIME,true);
        int sizePredict = hostPredictionList.size();
        int sizeRaw = hostRawList.size();
        int size = sizePredict + sizeRaw;
//        System.out.println(size);
//        System.out.println(sizeRaw);
//        System.out.println(sizePredict);
        String[] timestamps = new String[size];

        String[] uuidTemp = new String[size];
        float[] userSysTemp = new float[size];
        float[] swapTemp = new float[size];
        float[] ioWaitTemp = new float[size];
        float[] netTemp = new float[size];
        String[] diskTemp = new String[size];

        String[] uuidPredictTemp = new String[size];
        float[] userSysPredictTemp = new float[size];
        float[] swapPredictTemp = new float[size];
        float[] ioWaitPredictTemp = new float[size];
        float[] netPredictTemp = new float[size];
        String[] diskPredictTemp = new String[size];

        String tmpS;
        for (int i = 0; i < sizeRaw; i++){
            int position = sizeRaw - i - 1;
            HostRaw hostRaw = hostRawList.get(i);
            tmpS = hostRaw.getCreatedTime().toString();
            timestamps[position] = coverSting(tmpS.substring(0,tmpS.length()-5));
            userSysTemp[position] = hostRaw.getUserSys();
            swapTemp[position] = hostRaw.getSwap();
            ioWaitTemp[position] = hostRaw.getIoWait();
            netTemp[position] = hostRaw.getNet();
            diskTemp[position] = coverFloat(hostRaw.getDisk());

        }

        String tmpSp;
        for (int i = 0; i < sizePredict; i++){
            int positionPredict = size - i - 1;
            HostPrediction hostPrediction = hostPredictionList.get(i);
            tmpSp = hostPrediction.getCreatedTime().toString();
            timestamps[positionPredict] = coverSting(tmpSp.substring(0,tmpSp.length()-5));
            userSysPredictTemp[positionPredict] = hostPrediction.getUserSys();
            swapPredictTemp[positionPredict] = hostPrediction.getSwap();
            ioWaitPredictTemp[positionPredict] = hostPrediction.getIoWait();
            netPredictTemp[positionPredict] = hostPrediction.getNet();
            diskPredictTemp[positionPredict] = coverFloat(hostPrediction.getDisk());
        }

        hostTimeLine = Arrays.toString(timestamps);
        userSys4 = Arrays.toString(userSysTemp);
        userSysPredict4 = Arrays.toString(userSysPredictTemp);
        swap4 = Arrays.toString(swapTemp);
        swapPredict4 = Arrays.toString(swapPredictTemp);
        ioWait4 = Arrays.toString(ioWaitTemp);
        ioWaitPredict4 = Arrays.toString(ioWaitPredictTemp);
        net4 = Arrays.toString(netTemp);
        netPredict4 = Arrays.toString(netPredictTemp);
        disk4 = Arrays.toString(diskTemp);
        diskPredict4 = Arrays.toString(diskPredictTemp);
//        System.out.println("--------------");
//        System.out.println(id);
//        System.out.println(hostTimeLine);
//        System.out.println(userSys4);
//        System.out.println(userSysPredict4);
//        System.out.println(swap4);
//        System.out.println(swapPredict4);
//        System.out.println(ioWait4);
//        System.out.println(ioWaitPredict4);
//        System.out.println(net4);
//        System.out.println(netPredict4);
//        System.out.println(disk4);
//        System.out.println(diskPredict4);
    }

    private String coverSting(String string){
        StringBuilder tmpC;
        tmpC = new StringBuilder("'");
        tmpC.append(string);
        tmpC.append("'");
        return tmpC.toString();
    }

    private String coverFloat(Float value){
        String result;
        if (value == 0.0){
            result = null;
        }else {
            result = value.toString();
        }
        return result;
    }

    private String idCoverIP(String string){
        String ip="";
        switch (string){
            case "F8BC124233DC":
                ip = "192.168.33.1";
                break;
            case "F8BC124230C4":
                ip = "192.168.33.3";
                break;
            case "848F69E01087":
                ip = "192.168.33.7";
                break;
            case "549F3503ABB9":
                ip = "192.168.33.4";
                break;
            case "0026B94CBD3E":
                ip = "192.168.33.6";
                break;
            default:
                break;
        }
        return ip;
    }
    private String coverHostStatus(HostStatus input){
        String output = "";
        switch (input){
            case BUSY:
                output = BUSY;
                break;
            case IDLE:
                output = IDLE;
                break;
            case OVERWORK:
                output = OVERWORK;
                break;
            case CRASH:
                output = CRASH;
                break;
            default:
        }
        return output;
    }

    private String coverLoadStatus(LoadStatus input){
        String output = "";
        switch (input){
            case BUSY:
                output = BUSY;
                break;
            case IDLE:
                output = IDLE;
                break;
            case OVERWORK:
                output = OVERWORK;
                break;
            default:
        }
        return output;
    }
    private int coverHostStatusScore(HostStatus input){
        int output = 0;
        switch (input){
            case IDLE:
                output = NORMAL_SCORE;
                break;
            case CRASH:
                output = CRASH_SCORE;
                break;
            default:
        }
        return output;
    }

    private int coverLoadStatusScore(LoadStatus input){
        int output = 0;
        switch (input){
            case BUSY:
                output = BUSY_SCORE;
                break;
            case IDLE:
                output = IDLE_SCORE;
                break;
            case OVERWORK:
                output = OVERWORK_SCORE;
                break;
            default:
        }
        return output;
    }

    public int getLastHealth() {
        return lastHealth;
    }

    public void setLastHealth(int lastHealth) {
        this.lastHealth = lastHealth;
    }

    public List<Health> getHealthList() {
        return healthList;
    }

    public void setHealthList(List<Health> healthList) {
        this.healthList = healthList;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getServiceHealth() {
        return serviceHealth;
    }

    public void setServiceHealth(String serviceHealth) {
        this.serviceHealth = serviceHealth;
    }

    public String getPhysicalHealth() {
        return physicalHealth;
    }

    public void setPhysicalHealth(String physicalHealth) {
        this.physicalHealth = physicalHealth;
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public void setHostData(String hostData) {
        this.hostData = hostData;
    }

    public String getHostData() {
        return hostData;
    }

    public String getHostUUID() {
        return hostUUID;
    }

    public void setHostUUID(String hostUUID) {
        this.hostUUID = hostUUID;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

//    public ServiceDAO getServiceDAO() {
//        return serviceDAO;
//    }
//
//    public void setServiceDAO(ServiceDAO serviceDAO) {
//        this.serviceDAO = serviceDAO;
//    }

    public String getServiceTimeLine() {
        return serviceTimeLine;
    }

    public void setServiceTimeLine(String serviceTimeLine) {
        this.serviceTimeLine = serviceTimeLine;
    }

    public String getDhcpTime() {
        return dhcpTime;
    }

    public void setDhcpTime(String dhcpTime) {
        this.dhcpTime = dhcpTime;
    }

    public String getImgTime() {
        return imgTime;
    }

    public void setImgTime(String imgTime) {
        this.imgTime = imgTime;
    }

    public String getMacTime() {
        return macTime;
    }

    public void setMacTime(String macTime) {
        this.macTime = macTime;
    }

    public String getIpTime() {
        return ipTime;
    }

    public void setIpTime(String ipTime) {
        this.ipTime = ipTime;
    }

    public String getVmTime() {
        return vmTime;
    }

    public void setVmTime(String vmTime) {
        this.vmTime = vmTime;
    }

    public String getDefineNetFilterTime() {
        return defineNetFilterTime;
    }

    public void setDefineNetFilterTime(String defineNetFilterTime) {
        this.defineNetFilterTime = defineNetFilterTime;
    }

    public String getStateTime() {
        return stateTime;
    }

    public void setStateTime(String stateTime) {
        this.stateTime = stateTime;
    }

    public String getVncTime() {
        return vncTime;
    }

    public void setVncTime(String vncTime) {
        this.vncTime = vncTime;
    }

    public String getSelectHostTime() {
        return selectHostTime;
    }

    public void setSelectHostTime(String selectHostTime) {
        this.selectHostTime = selectHostTime;
    }

    public String getCreateVolumeTime() {
        return createVolumeTime;
    }

    public void setCreateVolumeTime(String createVolumeTime) {
        this.createVolumeTime = createVolumeTime;
    }

    public String getDefineVolumeTime() {
        return defineVolumeTime;
    }

    public void setDefineVolumeTime(String defineVolumeTime) {
        this.defineVolumeTime = defineVolumeTime;
    }

//    public HostPredictionDAO getHostPredictionDAO() {
//        return hostPredictionDAO;
//    }
//
//    public void setHostPredictionDAO(HostPredictionDAO hostPredictionDAO) {
//        this.hostPredictionDAO = hostPredictionDAO;
//    }

//    public HostRawDAO getHostRawDAO() {
//        return hostRawDAO;
//    }
//
//    public void setHostRawDAO(HostRawDAO hostRawDAO) {
//        this.hostRawDAO = hostRawDAO;
//    }

    public List<HostRaw> getHostRawList() {
        return hostRawList;
    }

    public void setHostRawList(List<HostRaw> hostRawList) {
        this.hostRawList = hostRawList;
    }

    public List<HostPrediction> getHostPredictionList() {
        return hostPredictionList;
    }

    public void setHostPredictionList(List<HostPrediction> hostPredictionList) {
        this.hostPredictionList = hostPredictionList;
    }

    public String getHostTimeLine() {
        return hostTimeLine;
    }

    public void setHostTimeLine(String hostTimeLine) {
        this.hostTimeLine = hostTimeLine;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidPredict() {
        return uuidPredict;
    }

    public void setUuidPredict(String uuidPredict) {
        this.uuidPredict = uuidPredict;
    }

    public String getUserSys() {
        return userSys;
    }

    public void setUserSys(String userSys) {
        this.userSys = userSys;
    }

    public String getUserSysPredict() {
        return userSysPredict;
    }

    public void setUserSysPredict(String userSysPredict) {
        this.userSysPredict = userSysPredict;
    }

    public String getSwap() {
        return swap;
    }

    public void setSwap(String swap) {
        this.swap = swap;
    }

    public String getSwapPredict() {
        return swapPredict;
    }

    public void setSwapPredict(String swapPredict) {
        this.swapPredict = swapPredict;
    }

    public String getIoWait() {
        return ioWait;
    }

    public void setIoWait(String ioWait) {
        this.ioWait = ioWait;
    }

    public String getIoWaitPredict() {
        return ioWaitPredict;
    }

    public void setIoWaitPredict(String ioWaitPredict) {
        this.ioWaitPredict = ioWaitPredict;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getNetPredict() {
        return netPredict;
    }

    public void setNetPredict(String netPredict) {
        this.netPredict = netPredict;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getDiskPredict() {
        return diskPredict;
    }

    public void setDiskPredict(String diskPredict) {
        this.diskPredict = diskPredict;
    }

    public String getUuid1() {
        return uuid1;
    }

    public void setUuid1(String uuid1) {
        this.uuid1 = uuid1;
    }

    public String getUuidPredict1() {
        return uuidPredict1;
    }

    public void setUuidPredict1(String uuidPredict1) {
        this.uuidPredict1 = uuidPredict1;
    }

    public String getUserSys1() {
        return userSys1;
    }

    public void setUserSys1(String userSys1) {
        this.userSys1 = userSys1;
    }

    public String getUserSysPredict1() {
        return userSysPredict1;
    }

    public void setUserSysPredict1(String userSysPredict1) {
        this.userSysPredict1 = userSysPredict1;
    }

    public String getSwap1() {
        return swap1;
    }

    public void setSwap1(String swap1) {
        this.swap1 = swap1;
    }

    public String getSwapPredict1() {
        return swapPredict1;
    }

    public void setSwapPredict1(String swapPredict1) {
        this.swapPredict1 = swapPredict1;
    }

    public String getIoWait1() {
        return ioWait1;
    }

    public void setIoWait1(String ioWait1) {
        this.ioWait1 = ioWait1;
    }

    public String getIoWaitPredict1() {
        return ioWaitPredict1;
    }

    public void setIoWaitPredict1(String ioWaitPredict1) {
        this.ioWaitPredict1 = ioWaitPredict1;
    }

    public String getNet1() {
        return net1;
    }

    public void setNet1(String net1) {
        this.net1 = net1;
    }

    public String getNetPredict1() {
        return netPredict1;
    }

    public void setNetPredict1(String netPredict1) {
        this.netPredict1 = netPredict1;
    }

    public String getDisk1() {
        return disk1;
    }

    public void setDisk1(String disk1) {
        this.disk1 = disk1;
    }

    public String getDiskPredict1() {
        return diskPredict1;
    }

    public void setDiskPredict1(String diskPredict1) {
        this.diskPredict1 = diskPredict1;
    }

    public String getUuid2() {
        return uuid2;
    }

    public void setUuid2(String uuid2) {
        this.uuid2 = uuid2;
    }

    public String getUuidPredict2() {
        return uuidPredict2;
    }

    public void setUuidPredict2(String uuidPredict2) {
        this.uuidPredict2 = uuidPredict2;
    }

    public String getUserSys2() {
        return userSys2;
    }

    public void setUserSys2(String userSys2) {
        this.userSys2 = userSys2;
    }

    public String getUserSysPredict2() {
        return userSysPredict2;
    }

    public void setUserSysPredict2(String userSysPredict2) {
        this.userSysPredict2 = userSysPredict2;
    }

    public String getSwap2() {
        return swap2;
    }

    public void setSwap2(String swap2) {
        this.swap2 = swap2;
    }

    public String getSwapPredict2() {
        return swapPredict2;
    }

    public void setSwapPredict2(String swapPredict2) {
        this.swapPredict2 = swapPredict2;
    }

    public String getIoWait2() {
        return ioWait2;
    }

    public void setIoWait2(String ioWait2) {
        this.ioWait2 = ioWait2;
    }

    public String getIoWaitPredict2() {
        return ioWaitPredict2;
    }

    public void setIoWaitPredict2(String ioWaitPredict2) {
        this.ioWaitPredict2 = ioWaitPredict2;
    }

    public String getNet2() {
        return net2;
    }

    public void setNet2(String net2) {
        this.net2 = net2;
    }

    public String getNetPredict2() {
        return netPredict2;
    }

    public void setNetPredict2(String netPredict2) {
        this.netPredict2 = netPredict2;
    }

    public String getDisk2() {
        return disk2;
    }

    public void setDisk2(String disk2) {
        this.disk2 = disk2;
    }

    public String getDiskPredict2() {
        return diskPredict2;
    }

    public void setDiskPredict2(String diskPredict2) {
        this.diskPredict2 = diskPredict2;
    }

    public String getUuid3() {
        return uuid3;
    }

    public void setUuid3(String uuid3) {
        this.uuid3 = uuid3;
    }

    public String getUuidPredict3() {
        return uuidPredict3;
    }

    public void setUuidPredict3(String uuidPredict3) {
        this.uuidPredict3 = uuidPredict3;
    }

    public String getUserSys3() {
        return userSys3;
    }

    public void setUserSys3(String userSys3) {
        this.userSys3 = userSys3;
    }

    public String getUserSysPredict3() {
        return userSysPredict3;
    }

    public void setUserSysPredict3(String userSysPredict3) {
        this.userSysPredict3 = userSysPredict3;
    }

    public String getSwap3() {
        return swap3;
    }

    public void setSwap3(String swap3) {
        this.swap3 = swap3;
    }

    public String getSwapPredict3() {
        return swapPredict3;
    }

    public void setSwapPredict3(String swapPredict3) {
        this.swapPredict3 = swapPredict3;
    }

    public String getIoWait3() {
        return ioWait3;
    }

    public void setIoWait3(String ioWait3) {
        this.ioWait3 = ioWait3;
    }

    public String getIoWaitPredict3() {
        return ioWaitPredict3;
    }

    public void setIoWaitPredict3(String ioWaitPredict3) {
        this.ioWaitPredict3 = ioWaitPredict3;
    }

    public String getNet3() {
        return net3;
    }

    public void setNet3(String net3) {
        this.net3 = net3;
    }

    public String getNetPredict3() {
        return netPredict3;
    }

    public void setNetPredict3(String netPredict3) {
        this.netPredict3 = netPredict3;
    }

    public String getDiskPredict3() {
        return diskPredict3;
    }

    public void setDiskPredict3(String diskPredict3) {
        this.diskPredict3 = diskPredict3;
    }

    public String getDisk3() {
        return disk3;
    }

    public void setDisk3(String disk3) {
        this.disk3 = disk3;
    }

    public String getUuid4() {
        return uuid4;
    }

    public void setUuid4(String uuid4) {
        this.uuid4 = uuid4;
    }

    public String getUuidPredict4() {
        return uuidPredict4;
    }

    public void setUuidPredict4(String uuidPredict4) {
        this.uuidPredict4 = uuidPredict4;
    }

    public String getUserSys4() {
        return userSys4;
    }

    public void setUserSys4(String userSys4) {
        this.userSys4 = userSys4;
    }

    public String getUserSysPredict4() {
        return userSysPredict4;
    }

    public void setUserSysPredict4(String userSysPredict4) {
        this.userSysPredict4 = userSysPredict4;
    }

    public String getSwap4() {
        return swap4;
    }

    public void setSwap4(String swap4) {
        this.swap4 = swap4;
    }

    public String getSwapPredict4() {
        return swapPredict4;
    }

    public void setSwapPredict4(String swapPredict4) {
        this.swapPredict4 = swapPredict4;
    }

    public String getIoWait4() {
        return ioWait4;
    }

    public void setIoWait4(String ioWait4) {
        this.ioWait4 = ioWait4;
    }

    public String getIoWaitPredict4() {
        return ioWaitPredict4;
    }

    public void setIoWaitPredict4(String ioWaitPredict4) {
        this.ioWaitPredict4 = ioWaitPredict4;
    }

    public String getNet4() {
        return net4;
    }

    public void setNet4(String net4) {
        this.net4 = net4;
    }

    public String getNetPredict4() {
        return netPredict4;
    }

    public void setNetPredict4(String netPredict4) {
        this.netPredict4 = netPredict4;
    }

    public String getDisk4() {
        return disk4;
    }

    public void setDisk4(String disk4) {
        this.disk4 = disk4;
    }

    public String getDiskPredict4() {
        return diskPredict4;
    }

    public void setDiskPredict4(String diskPredict4) {
        this.diskPredict4 = diskPredict4;
    }

    public String getNetworkHealth() {
        return networkHealth;
    }

    public void setNetworkHealth(String networkHealth) {
        this.networkHealth = networkHealth;
    }

    public int getLastPhysicalHealth() {
        return lastPhysicalHealth;
    }

    public void setLastPhysicalHealth(int lastPhysicalHealth) {
        this.lastPhysicalHealth = lastPhysicalHealth;
    }
}
