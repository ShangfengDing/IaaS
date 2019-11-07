package appcloud.admin.action.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;
import appcloud.api.client.AcHostClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class HostMonitorAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(HostMonitorAction.class);
	private AcHostClient hostClient = ClientFactory.getHostClient();	
	private String hostName;	//host的id
	private String type;
	private Date sTime;
	private Date eTime;
	private List<Load> loads = new ArrayList<Load>();
	private List<Float> cpuPercent = new ArrayList<Float>();
	private List<Float> memPercent = new ArrayList<Float>();
	private List<Float> diskReadRate = new ArrayList<Float>();
	private List<Float> diskWriteRate = new ArrayList<Float>();
	private List<Float> netInPercent = new ArrayList<Float>();
	private List<Float> netOutPercent = new ArrayList<Float>();
	private List<Float> diskPercent = new ArrayList<Float>();
	private List<Float> loadaverage = new ArrayList<Float>();
	
	private List<String> time = new ArrayList<String>();
	private String current = null;
	
	//host监控数据
	public String execute() {
//		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat matter2 = new SimpleDateFormat("HH:mm");	
//		SimpleDateFormat matter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat matter4 = new SimpleDateFormat("yyyy-MM");
//		SimpleDateFormat matter5 = new SimpleDateFormat("yyyy");
//		SimpleDateFormat matter6 = new SimpleDateFormat("MM");
//		SimpleDateFormat matter7 = new SimpleDateFormat("dd");
		logger.info("host id is:"+hostName);
		
		eTime=new Date();//取当前时间
//		sTime = eTime;//开始和结束时间相等
//		Date today = new Date();
		if(type.equals("second")){
			/*
			type = "day";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(eTime);
			calendar.add(Calendar.SECOND, -3);
			sTime = calendar.getTime();
			logger.info(sTime);
			logger.info(eTime);
			*/
			logger.info("second");
			Load load = hostClient.getCurrentLoad(hostName);
			loads.add(load);
		}else if(type.equals("day")){			
//			String s = matter1.format(today)+" 00:00:00";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(eTime);
			calendar.add(Calendar.HOUR, -1);
			sTime = calendar.getTime();
			loads = hostClient.getLoads(hostName, type,sTime, eTime);
//			try {
//				sTime = matter3.parse(s);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}		
		}/*else if(type.equals("month")){
			String s = matter4.format(today)+"-01 00:00:00"; 
			try {
				sTime = matter3.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			current = matter6.format(today);
		}else{
			String s = matter5.format(today)+"-01-01 00:00:00"; 
			try {
				sTime = matter3.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			current = matter5.format(today);
		}*/
		/*for(int i = 0;i<loads.size();i++){//显示整点数据
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(loads.get(i).time);
			if( (gc.get(Calendar.MINUTE)==0) && (gc.get(Calendar.SECOND)==0) ) {//整点
				cpuPercent.add(loads.get(i).cpuPercent);
				time.add(matter2.format(loads.get(i).time));
			}		
		}*/
		for(int i = 0;i<loads.size();i++){	
			if(type.equals("day") || type.equals("second")){
				time.add(matter2.format(loads.get(i).time));
			}/*else if(type.equals("month")){
				time.add(matter7.format(loads.get(i).time));
			}else{
				time.add(matter6.format(loads.get(i).time));
			}*/
			cpuPercent.add(loads.get(i).cpuPercent);
			memPercent.add(loads.get(i).memPercent);
			diskReadRate.add(loads.get(i).diskReadRate);
			diskWriteRate.add(loads.get(i).diskWriteRate);
			netInPercent.add(loads.get(i).netInPercent);
			netOutPercent.add(loads.get(i).netOutPercent);
			diskPercent.add(loads.get(i).diskPercent);
			loadaverage.add(loads.get(i).avgLoad);
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查询性能", "查询"+hostClient.get(hostName).ip+"的性能/监控", "HostMonitorAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		logger.info("列出成功");
		return SUCCESS;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getsTime() {
		return sTime;
	}
	public void setsTime(Date sTime) {
		this.sTime = sTime;
	}
	public Date geteTime() {
		return eTime;
	}
	public void seteTime(Date eTime) {
		this.eTime = eTime;
	}
	public List<Load> getLoads() {
		return loads;
	}
	public void setLoads(List<Load> loads) {
		this.loads = loads;
	}
	public List<Float> getCpuPercent() {
		return cpuPercent;
	}
	public void setCpuPercent(List<Float> cpuPercent) {
		this.cpuPercent = cpuPercent;
	}
	public List<Float> getMemPercent() {
		return memPercent;
	}
	public void setMemPercent(List<Float> memPercent) {
		this.memPercent = memPercent;
	}
	public List<Float> getDiskReadRate() {
		return diskReadRate;
	}
	public void setDiskReadRate(List<Float> diskReadRate) {
		this.diskReadRate = diskReadRate;
	}
	public List<Float> getDiskWriteRate() {
		return diskWriteRate;
	}
	public void setDiskWriteRate(List<Float> diskWriteRate) {
		this.diskWriteRate = diskWriteRate;
	}
	public List<Float> getNetInPercent() {
		return netInPercent;
	}
	public void setNetInPercent(List<Float> netInPercent) {
		this.netInPercent = netInPercent;
	}
	public List<Float> getNetOutPercent() {
		return netOutPercent;
	}
	public void setNetOutPercent(List<Float> netOutPercent) {
		this.netOutPercent = netOutPercent;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public List<Float> getDiskPercent() {
		return diskPercent;
	}
	public void setDiskPercent(List<Float> diskPercent) {
		this.diskPercent = diskPercent;
	}
	public List<Float> getLoadaverage() {
		return loadaverage;
	}
	public void setLoadaverage(List<Float> loadaverage) {
		this.loadaverage = loadaverage;
	}
	
}