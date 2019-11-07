package appcloud.admin.action.system;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.VmSummary;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.proxy.VmSummaryProxy;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.*;

public class VmSummaryAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(VmSummaryAction.class);
    private List<VmSummary> vmlist=new ArrayList<VmSummary>();
    private List<String> cpuUsed = new ArrayList<>();
    private List<String> diskErrorCount = new ArrayList<>();
    private List<String> diskUsed = new ArrayList<>();
    private List<String> memUsed = new ArrayList<>();
    private List<String> memSwap = new ArrayList<>();
    private List<String> netDownload = new ArrayList<>();
    private List<String> netUpload = new ArrayList<>();
    private String uuid=null;
    private Integer id;
    private String name;                //设备名称
    private String type;       //类型：服务器，存储设备，网络设备，非受控虚拟机
    private String model;               //型号与规格
    private String position;            //位置
    private String description;
    private VmSummary vmSummary;
    private String result;
    private List<String> timeLine=new ArrayList<>();
    private List<Integer> cpuInt=new ArrayList<>();
    public int netcount = 0;
    public int diskcount = 0;



    private String cpustr=null;
    List<List<String>> vmSummaryInfo = new ArrayList<>();
    VmSummaryProxy vm=ConnectionFactory.getVmSummaryProxy();

    private long count;
    public String searchVmAll(){
        try {
            vmlist = (List<VmSummary>) vm.findAll();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("find all vm error");
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    public String delteService(){
        logger.info("uuid:"+uuid);
        try{
            vm.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("delete service error");
            return Action.ERROR;
        }
        logger.info("delte success");
        result="true";
        return Action.SUCCESS;
    }

    public String addService(){
        VmSummary vmSummary=new VmSummary();
        vmSummary.setName(name);
        if(type.equals("NETDEVICE")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.NETDEVICE);
        }else if(type.equals("DISKDEVICE")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.DISKDEVICE);
        }else if(type.equals("OTHER")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.OTHER);
        }else{
            logger.error("type error");
            result="error";
            return Action.ERROR;
        }
        vmSummary.setUuid(uuid);
        vmSummary.setModel(model);
        vmSummary.setPosition(position);
        vmSummary.setDescription(description);
        try{
            vm.save(vmSummary);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("save error");
        }
        result="true";
        return Action.SUCCESS;
    }

    public String findServiceByUUID(){
        if(id<=0){
            logger.error("id error");
            result="error";
            return Action.ERROR;
        }
        try{
            vmSummary=vm.findById(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("find by uuid error");
            result="error";
            return Action.ERROR;
        }
        result="true";
        return Action.SUCCESS;
    }
    public String modifyService(){
        try{
        VmSummary vmSummary=vm.findById(id);
        vmSummary.setDescription(description);
        vmSummary.setPosition(position);
        vmSummary.setModel(model);
        vmSummary.setUuid(uuid);
        if(type.equals("NETDEVICE")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.NETDEVICE);
        }else if(type.equals("DISKDEVICE")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.DISKDEVICE);
        }else if(type.equals("OTHER")){
            vmSummary.setType(VmSummary.VmSummaryTypeEnum.OTHER);
        }else{
            logger.error("type error");
            result="error";
            return Action.ERROR;
        }
            vmSummary.setName(name);
            vm.update(vmSummary);
        }catch(Exception e){
            logger.error("update error");
            result="error";
            return Action.ERROR;
        }
        result="true";
        return Action.SUCCESS;
    }

    public String vmInfo(){

//        if(uuid.length()<=0){
//            logger.error("uuid error");
//            result="error";
//            return Action.ERROR;
//        }
        try{

                System.out.println("here");
                HBaseAction hbase = new HBaseAction();
                vmSummaryInfo = hbase.findVmSummaryInfo(uuid, 0);
                cpuUsed = vmSummaryInfo.get(0);
                diskErrorCount = vmSummaryInfo.get(1);
                diskUsed = vmSummaryInfo.get(2);
                memUsed = vmSummaryInfo.get(3);
                memSwap = vmSummaryInfo.get(4);
                netDownload = vmSummaryInfo.get(5);
                netUpload = vmSummaryInfo.get(6);
                timeLine = vmSummaryInfo.get(7);
                String[] strings = new String[timeLine.size()];
                for (int i = 0; i < timeLine.size(); i++) {
                    strings[i] = coverSting(timeLine.get(i));
                }
//            timeLine.toArray(strings);
                cpustr = Arrays.toString(strings);
                System.out.println(vmSummaryInfo);
                System.out.println(cpuUsed);

//                vmSummary=vm.findByUuid(uuid);
        }catch(Exception e){
            if(vmSummary!=null) {
                e.printStackTrace();
                logger.error("find vmSummaryInfo error");
                result = "error";
                return Action.ERROR;
            }
        }
        result="true";
        return Action.SUCCESS;
    }
    public String countVm() {
        Map map = new HashMap<>();
        try {
            vmlist = (List<VmSummary>) vm.findAll();
            for (int i = 0;i < vmlist.size();i++) {
                if (vmlist.get(i).getType().toString()=="存储") netcount++;
                else if (vmlist.get(i).getType().toString()=="网络设备") diskcount++;
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("count vm error");
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    private String coverSting(String string){
        StringBuilder tmpC;
        tmpC = new StringBuilder("'");
        tmpC.append(string);
        tmpC.append("'");
        return tmpC.toString();
    }

    public String findDeviceByType(){
        VmSummaryProxy vmSummaryProxy=ConnectionFactory.getVmSummaryProxy();
        try {
            vmlist =( List<VmSummary>)vmSummaryProxy.findByType(type);
        }catch(Exception e){
            logger.error("find vm by type error");
            return Action.ERROR;
        }
        count=vmlist.size();
        return Action.SUCCESS;
    }

    public String countNum(){
        return Action.SUCCESS;
    }

    public List<VmSummary> getVmlist() {
        return vmlist;
    }

    public void setVmlist(List<VmSummary> vmlist) {
        this.vmlist = vmlist;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VmSummary getVmSummary() {
        return vmSummary;
    }

    public void setVmSummary(VmSummary vmSummary) {
        this.vmSummary = vmSummary;
    }

    public List<List<String>> getVmSummaryInfo() {
        return vmSummaryInfo;
    }

    public void setVmSummaryInfo(List<List<String>> vmSummaryInfo) {
        this.vmSummaryInfo = vmSummaryInfo;
    }

    public List<String> getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(List<String> cpuUsed) {
        this.cpuUsed = cpuUsed;
    }

    public List<String> getDiskErrorCount() {
        return diskErrorCount;
    }

    public void setDiskErrorCount(List<String> diskErrorCount) {
        this.diskErrorCount = diskErrorCount;
    }

    public List<String> getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(List<String> diskUsed) {
        this.diskUsed = diskUsed;
    }

    public List<String> getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(List<String> memUsed) {
        this.memUsed = memUsed;
    }

    public List<String> getMemSwap() {
        return memSwap;
    }

    public void setMemSwap(List<String> memSwap) {
        this.memSwap = memSwap;
    }

    public List<String> getNetDownload() {
        return netDownload;
    }

    public void setNetDownload(List<String> netDownload) {
        this.netDownload = netDownload;
    }

    public List<String> getNetUpload() {
        return netUpload;
    }

    public void setNetUpload(List<String> netUpload) {
        this.netUpload = netUpload;
    }

    public List<String> getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(List<String> timeLine) {
        this.timeLine = timeLine;
    }

    public List<Integer> getCpuInt() {
        return cpuInt;
    }

    public void setCpuInt(List<Integer> cpuInt) {
        this.cpuInt = cpuInt;
    }

    public String getCpustr() {
        return cpustr;
    }

    public void setCpustr(String cpustr) {
        this.cpustr = cpustr;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getNetcount() {
        return netcount;
    }

    public void setNetcount(int netcount) {
        this.netcount = netcount;
    }

    public int getDiskcount() {
        return diskcount;
    }

    public void setDiskcount(int diskcount) {
        this.diskcount = diskcount;
    }



}

