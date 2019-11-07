package appcloud.admin.action.vm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Load;
import appcloud.api.client.AcHostClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class HostsLoadAction extends BaseAction{
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(HostsLoadAction.class);
	private AcHostClient hostClient = ClientFactory.getHostClient();

	private String type;  //当前host加载的类型
	private String hostId;
	private Date sTime;
	private Date eTime;
	private List<Load> loads = new ArrayList<Load>();
	private List<Float> cpuPercent = new ArrayList<Float>();
	private List<Float> memPercent = new ArrayList<Float>();
	private List<Float> diskPercent = new ArrayList<Float>();
	private List<String> time = new ArrayList<String>();
	
	public String execute(){
		
		SimpleDateFormat matter2 = new SimpleDateFormat("HH:mm");	
		logger.info("host id is:"+hostId);
		
		eTime=new Date();//取当前时间
		if(type.equals("second")){
			Load load = hostClient.getCurrentLoad(hostId);
			loads.add(load);
		}else if(type.equals("day")){			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(eTime);
			calendar.add(Calendar.HOUR, -1);
			sTime = calendar.getTime();
			loads = hostClient.getLoads(hostId, type,sTime, eTime);
		}
		for(int i = 0;i<loads.size();i++){	
			if(type.equals("day") || type.equals("second")){
				time.add(matter2.format(loads.get(i).time));
			}
			cpuPercent.add(loads.get(i).cpuPercent);
			memPercent.add(loads.get(i).memPercent);
			diskPercent.add(loads.get(i).diskPercent);
		}
		return SUCCESS;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
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
	public List<Float> getDiskPercent() {
		return diskPercent;
	}
	public void setDiskPercent(List<Float> diskPercent) {
		this.diskPercent = diskPercent;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
	
}
