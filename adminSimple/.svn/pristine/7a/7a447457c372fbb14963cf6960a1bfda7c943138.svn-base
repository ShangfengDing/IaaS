package appcloud.admin.action.monitor;

import appcloud.admin.manager.HostDetailManager;
import appcloud.admin.manager.HostMetricsManager;
import appcloud.admin.model.HostDetail;
import appcloud.admin.model.HostMetrics;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/4/11.
 * @author zouji
 */
public class HostAction extends ActionSupport{

    Logger logger = Logger.getLogger(HostAction.class);
    private String test;
    private String ipaddress;
    private String district;
    private HostDetail hostDetail;
    private List<HostMetrics> hostMetricsList;
    private List<Timestamp> timeList = new ArrayList<>();
    private List<Integer> cpuList = new ArrayList<>();
    private List<Integer> memList = new ArrayList<>();
    private List<Integer> iowaitList = new ArrayList<>();
    private List<Integer> netList = new ArrayList<>();
    private List<Float> diskList = new ArrayList<>();
    private Integer intevalDay = 7;

    public String findHostDetail() {
        hostDetail = HostDetailManager.findByIp(ipaddress).get(0);
        logger.info("require detail host: " + hostDetail);
        Long currentTimestamp = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(currentTimestamp);
        Timestamp startTime = new Timestamp(currentTimestamp - intevalDay * 24 * 60 * 60 * 1000);
        hostMetricsList = HostMetricsManager.findByCreatedTime(startTime, currentTime, hostDetail.getUuid());
        for (HostMetrics hostMetrics: hostMetricsList) {
            timeList.add(hostMetrics.getCreatedTime());
            cpuList.add(hostMetrics.getCpu());
            memList.add(hostMetrics.getSwap());
            iowaitList.add(hostMetrics.getIoWait());
            netList.add(hostMetrics.getNet());
            diskList.add(hostMetrics.getDiskUsed());
        }
        return SUCCESS;
    }

    public String test() {
        test="Hello World!";
        return "success";
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public HostDetail getHostDetail() {
        return hostDetail;
    }

    public void setHostDetail(HostDetail hostDetail) {
        this.hostDetail = hostDetail;
    }

    public List<HostMetrics> getHostMetricsList() {
        return hostMetricsList;
    }

    public void setHostMetricsList(List<HostMetrics> hostMetricsList) {
        this.hostMetricsList = hostMetricsList;
    }

    public List<Timestamp> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Timestamp> timeList) {
        this.timeList = timeList;
    }

    public List<Integer> getCpuList() {
        return cpuList;
    }

    public void setCpuList(List<Integer> cpuList) {
        this.cpuList = cpuList;
    }

    public List<Integer> getMemList() {
        return memList;
    }

    public void setMemList(List<Integer> memList) {
        this.memList = memList;
    }

    public List<Integer> getIowaitList() {
        return iowaitList;
    }

    public void setIowaitList(List<Integer> iowaitList) {
        this.iowaitList = iowaitList;
    }

    public List<Integer> getNetList() {
        return netList;
    }

    public void setNetList(List<Integer> netList) {
        this.netList = netList;
    }

    public List<Float> getDiskList() {
        return diskList;
    }

    public void setDiskList(List<Float> diskList) {
        this.diskList = diskList;
    }
}
