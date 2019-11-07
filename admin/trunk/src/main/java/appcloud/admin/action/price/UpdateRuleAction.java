package appcloud.admin.action.price;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.BillingAPI;

public class UpdateRuleAction extends BaseAction {
	/**
	 * 编辑计费规则，根据name区分是package还是单项的
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(UpdateRuleAction.class);
	
	private String name;
	private String packagename;
	private String description;
	private Integer clusterid = -1;
	private Integer cpu = -1;
	private Integer mem = -1;
	private Integer hd = -1;
	private Integer bd = -1;
	private String hourPrice;
	private String dayPrice;
	private String monthPrice;
	private String yearPrice;
	private Integer pid;
	private String result = "fail";
	
	public String execute() throws Exception{
		String ptype = "";
		if(name.equalsIgnoreCase(Constants.ITEM_CPU)){//根据不同的选项确定需要传的值
			ptype = Constants.ITEM_CPU;
			packagename = "CPU";
			description = name+cpu+"核";
			mem = -1;
			hd = -1;
			bd = -1;
		}else if(name.equalsIgnoreCase(Constants.ITEM_MEM)){
			ptype = Constants.ITEM_MEM;
			packagename = "内存";
			description = name+mem+"G";
			cpu = -1;
			hd = -1;
			bd = -1;
		}else if(name.equalsIgnoreCase(Constants.ITEM_HD)){
			ptype = Constants.ITEM_HD;
			packagename ="硬盘";
			description = name+hd+"G";
			cpu = -1;
			mem = -1;
			bd = -1;
		}else if(name.equalsIgnoreCase(Constants.ITEM_BW)){
			ptype = Constants.ITEM_BW;
			packagename = "带宽";
			description = name+bd+"M";
			cpu = -1;
			mem = -1;
			hd = -1;
		}else if(name.equalsIgnoreCase(Constants.ITEM_VMPACKAGE)){
			ptype = Constants.ITEM_VMPACKAGE;
			description = description.replaceAll("\n", "<br/>");
		}else if(name.equalsIgnoreCase(Constants.ITEM_CHARGE)){
			ptype = Constants.ITEM_CHARGE;
			packagename = "手续费";
			description = "手续费";
			cpu = -1;
			mem = -1;
			hd = -1;
			bd = -1;
		}else if(name.equalsIgnoreCase(Constants.ITEM_INSTANCETYPE)){
			ptype = Constants.ITEM_INSTANCETYPE;
			description = description.replaceAll("\n", "<br/>");
		}
		if(BillingAPI.updateRate(pid, packagename, description, clusterid, ptype, cpu, mem, hd, bd,
				hourPrice, dayPrice, monthPrice, yearPrice)){
			this.setResult(SUCCESS);
			LOGGER.info("更新计费规则成功！"+name);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "更新计费规则", "更新计费规则: "+description+"-"+hourPrice+","+dayPrice+","+monthPrice+","+yearPrice, "UpdateRuleAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			return SUCCESS;
		}else{
			LOGGER.info("更新计费规则失败！"+name);
			return SUCCESS;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMem() {
		return mem;
	}

	public void setMem(Integer mem) {
		this.mem = mem;
	}

	public Integer getHd() {
		return hd;
	}

	public void setHd(Integer hd) {
		this.hd = hd;
	}

	public Integer getBd() {
		return bd;
	}

	public void setBd(Integer bd) {
		this.bd = bd;
	}

	public String getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(String dayPrice) {
		this.dayPrice = dayPrice;
	}

	public String getMonthPrice() {
		return monthPrice;
	}

	public void setMonthPrice(String monthPrice) {
		this.monthPrice = monthPrice;
	}

	public String getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(String hourPrice) {
		this.hourPrice = hourPrice;
	}

	public String getYearPrice() {
		return yearPrice;
	}

	public void setYearPrice(String yearPrice) {
		this.yearPrice = yearPrice;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getClusterid() {
		return clusterid;
	}

	public void setClusterid(Integer clusterid) {
		this.clusterid = clusterid;
	}
	
}