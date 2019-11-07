package com.appcloud.vm.action.vm;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;

public class ReNewVmAction extends BaseAction{

    private static final int  RECID = Integer.parseInt(Constants.ADMIN_TENANT_ID);
    private static final String PTYPE="vm";
    private static final long serialVersionUID = 443539771643856835L;
    private Logger logger = Logger.getLogger(ReNewVmAction.class);
    private Integer cpuNum;
    private Integer memNum;
    private Integer hdNum;
    private Integer bandNum;
    
    private String result;      //扣费是否成功
    private String type;        //vm，vmpackage
    private int paymentType;    //付费方式，年月日时
    private Integer times;      //次数，目前无用
    private double count;       //购买时长
    private String uuid;        //虚拟机或硬盘的uuid
    private int clusterid;
    private String serverName;
    
    @Override
    public String execute() throws Exception {
        logger.info("clusterid: " + clusterid + "cpu: " + cpuNum + " ,mem: " + memNum + " ,hd: " + hdNum + " ,band: " + bandNum);
        Integer userId = this.getSessionUserID();

        //Step1:查询余额，余额不足则返回，否则扣费
        logger.info("start payment!");
        String reason="续费：购买" + cpuNum + "核CPU" + memNum + "G内存" + hdNum + "G硬盘和" + bandNum + "M带宽";
        logger.debug("reason = " + reason);
        String item = "";
        String message = "";
        int price = 0;
        logger.info("type = " + type);
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        if(type.equals("vm")){
        	item = "CPU/硬盘/内存/带宽";
            logger.debug("item = " + item);
            price = BillingAPI.getPrice(type, clusterid, cpuNum, memNum, hdNum, bandNum, userId, paymentType, count);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, reason, price, this.getAccessToken());
            if(!message.equalsIgnoreCase(SUCCESS)){
                logger.info("payment fail");
                addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"云主机续费", "由于扣费失败，云主机\""+serverName+"\"续费失败", 
    					"ReNewVmAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
                this.result = message;
                return SUCCESS; 
            }
        }else if(type.equals("vmpackage")){
        	Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
			item = rate.getName();
			price = BillingAPI.getPrice(type, clusterid, cpuNum, memNum, hdNum, bandNum, userId, paymentType, count);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, reason, price, this.getAccessToken());
            if(!message.equalsIgnoreCase(SUCCESS)){
                logger.info("payment fail");
                this.result = message;
                addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"云主机续费", "由于扣费失败，云主机\""+serverName+"\"续费失败", 
    					"ReNewVmAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
                return SUCCESS; 
            }
        }
        logger.info("payment success!");
        this.result = "success";
        
        //Step2:修改虚拟机到期时间以及付费类型（其实没变），需要判断是否已过期
        logger.info("start modify endtime!");
        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        if(type.equals("vm")){
            logger.info("vm");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            Date endTimestamp = vmHdEndtimeManager.getVmEndtimeByUuid(uuid).getEndTime();
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + " tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            if(!tempStamp.after(endTimestamp)){
                logger.info("use the former time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, endTimestamp, VmHdEndtime.TYPE_VM, paymentType, count);
            }else{
                logger.info("use the now time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_VM, paymentType, count);
            }
        }else if(type.equals("vmpackage")){
            logger.info("vmpackage");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            Date endTimestamp = vmHdEndtimeManager.getVmEndtimeByUuid(uuid).getEndTime();
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + "tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            if(!tempStamp.after(endTimestamp)){
                logger.info("use the former time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, endTimestamp, VmHdEndtime.TYPE_VMPACKAGE, paymentType, count);
            }else{
                logger.info("use the now time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_VMPACKAGE, paymentType, count);
            }
        }
        addAcMessageLog(AcModuleEnum.VM_FRONT, 
				UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
				"云主机续费", "云主机\""+serverName+"\"续费", 
				"ReNewVmAction.class", null, AcLogLevelEnum.INFO,
				new Date(System.currentTimeMillis()));
        return SUCCESS;
    }
    
    public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public Integer getCpuNum() {
        return cpuNum;
    }
    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }
    public Integer getMemNum() {
        return memNum;
    }
    public void setMemNum(Integer memNum) {
        this.memNum = memNum;
    }
    public Integer getHdNum() {
        return hdNum;
    }
    public void setHdNum(Integer hdNum) {
        this.hdNum = hdNum;
    }
    public Integer getBandNum() {
        return bandNum;
    }
    public void setBandNum(Integer bandNum) {
        this.bandNum = bandNum;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
    public Integer getTimes() {
        return times;
    }
    public void setTimes(Integer times) {
        this.times = times;
    }
    public double getCount() {
        return count;
    }
    public void setCount(double count) {
        this.count = count;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
