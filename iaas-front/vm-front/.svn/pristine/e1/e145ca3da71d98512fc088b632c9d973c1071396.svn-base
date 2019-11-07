package com.appcloud.vm.action.vm;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class ReNewVmPaymentAction extends BaseAction{
	private static final long serialVersionUID = 443539771643856835L;
    private Logger logger = Logger.getLogger(ReNewVmPaymentAction.class);
	private static final int  RECID = Integer.parseInt(Constants.ADMIN_TENANT_ID);
    private static final String PTYPE = "vm";
    
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
    public String execute() throws Exception {//根据旧的计费规则退费，再重新收取新的费用
        logger.info("clusterid: " + clusterid + "cpu: " + cpuNum + " ,mem: " + memNum + " ,hd: " + hdNum + " ,band: " + bandNum);
        Integer userId = this.getSessionUserID();
        
        //计算时间，算各种份数，然后扣费，设每个月份定为30天，每年定为360天
        //计算旧计费规则的份数
        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        VmHdEndtime vm = vmHdEndtimeManager.getVmEndtimeByUuid(uuid);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
	    long t1 = 0L;
	    long t2 = 0L;
        try {
        	Timestamp endTime = new Timestamp(vm.getEndTime().getTime());
            t1 = timeformat.parse(getTimeStampNumberFormat(endTime)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.debug(e);
        }
        try {
            t2 = timeformat.parse(getTimeStampNumberFormat(currentTime)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.debug(e);
        }
        //账号余额
        Integer balance = BillingAPI.balance(userId, this.getAccessToken(), null, null);
        //本次应付款金额
        Integer payamount = BillingAPI.getPrice(type, clusterid, cpuNum, memNum, hdNum, bandNum, userId, paymentType, count);
        if (t1 - t2 >= 0) {//不过期才对其退费
        	Integer oldPaymentType = vmHdEndtimeManager.convertToPaymentType(vm.getPayType());
        	double oldCount = 1.00000;
        	if(oldPaymentType == 1){
        		oldCount = (t1 - t2) / (Constants.yearDays*24*3600*1000.0);
        	}else if(oldPaymentType == 2){
        		oldCount = (t1 - t2) / (Constants.monthDays*24*3600*1000.0); 
        	}else if(oldPaymentType == 3){
        		oldCount = (t1 - t2) / (24*3600*1000.0); //因为t1-t2得到的是毫秒级，所以要除3600000得出小时
        	}else if(oldPaymentType == 4){
        		oldCount = (t1 - t2) / (3600*1000.0); //因为t1-t2得到的是毫秒级，所以要除1000得出秒
        	}
        	logger.info("oldCount:"+oldCount);
        	//计算手续费的份数
        	double chargecount = 1.0;
        	//先判断余额是否充足，再进行扣费等操作
        	Integer reamount = 0;
        	Integer chargeamount = 0;
        	reamount = BillingAPI.getPrice(type, clusterid, cpuNum, memNum, hdNum, bandNum, userId, oldPaymentType, oldCount);
        	chargeamount = BillingAPI.getPrice("charge", clusterid, -1, -1, -1, -1, userId, oldPaymentType, chargecount);
        	logger.info(balance);
        	logger.info(payamount);
        	logger.info(reamount);
        	logger.info(chargeamount);
        	if(balance < (payamount - reamount + chargeamount)){
        		logger.info("余额不足，返回");
        		this.result = "余额不足，请尽快充值！";
        		addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云主机付费方式", "由于余额不足，修改云主机\""+serverName+"\"付费方式失败", 
    					"ReNewVmPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS;
        	}
        	
        	//根据旧的计费规则退费
        	String PID="购买" + cpuNum + "核CPU" + memNum + "G内存" + hdNum +"G硬盘和" + bandNum + 
        			"M带宽，未到期，退费";
        	logger.debug("PID = " + PID);
        	String item = "";
        	if(type.equals("vm")){
        		item = "CPU/内存/硬盘/带宽";
        	}else if(type.equals("vmpackage")){
        		Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
        		item = rate.getName();
        	}
        	logger.debug("item = " + item);
        	String message = "";
        	String payment_type = new VmHdEndtimeManager().convertToType(oldPaymentType);
        	message = BillingAPI.pay(RECID, userId, item, payment_type, times, oldCount, PTYPE, PID, reamount, this.getAccessToken());
        	if(!message.equalsIgnoreCase("success")){
        		logger.info("payment fail");
        		this.result = message;
        		addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云主机付费方式", "由于余扣费失败，修改云主机\""+serverName+"\"付费方式失败", 
    					"ReNewVmPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS; //退费不成功
        	} 
        	 //收取手续费
            item = "手续费";
            PID="修改付费规则的手续费";
            payment_type = new VmHdEndtimeManager().convertToType(paymentType);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, chargecount, PTYPE, PID, chargeamount, this.getAccessToken());
            if(!message.equalsIgnoreCase(SUCCESS)){
                logger.info("payment fail");
                this.result = message;
                addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云主机付费方式", "由于扣费失败，修改云主机\""+serverName+"\"付费方式失败",
    					"ReNewVmPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
                return SUCCESS; 
            }
            logger.info("payment success!");
        } else {
        	if(balance < payamount){
        		logger.info("余额不足，返回");
        		this.result = "余额不足，请尽快充值！";
        		addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云主机付费方式", "由于余额不足，修改云主机\""+serverName+"\"付费方式失败",
    					"ReNewVmPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS;
        	}
    	}
        
        //查询余额，扣费
        logger.info("start payment!");
        String PID="购买" + cpuNum + "核CPU" + memNum + "G内存" + hdNum + "G硬盘和" + bandNum + "M带宽";
        logger.debug("PID = " + PID);
        String item = "";
        String message = "";
        logger.info("type = " + type);
        if(type.equals("vm")){
        	item = "CPU/内存/硬盘/带宽";
            logger.debug("item = " + item);
        }else if(type.equals("vmpackage")){
        	Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
			item = rate.getName();
        }
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, payamount, this.getAccessToken());
        if(!message.equalsIgnoreCase(SUCCESS)){
            logger.info("payment fail");
            this.result = message;
            addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
					"修改云主机付费方式", "由于扣费失败，修改云主机\""+serverName+"\"付费方式失败",
					"ReNewVmPaymentAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
            return SUCCESS; 
        }
        logger.info("payment success!");
        
        //修改虚拟机到期时间以及付费类型
        logger.info("start modify endtime!");
        if(type.equals("vm")){
            logger.info("vm");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + " tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_VM, paymentType, count);
        }else if(type.equals("vmpackage")){
            logger.info("vmpackage");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + "tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_VMPACKAGE, paymentType, count);
        }
        addAcMessageLog(AcModuleEnum.VM_FRONT, 
				UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
				"修改云主机付费方式", "修改云主机\""+serverName+"\"付费方式为"+payment_type, 
				"ReNewVmPaymentAction.class", null, AcLogLevelEnum.INFO,
				new Date(System.currentTimeMillis()));
        this.result = "success";
        return SUCCESS;
    }
    
    public static String getTimeStampNumberFormat(Timestamp formatTime) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss"/*, new Locale("zh", "cn")*/);
	    return m_format.format(formatTime);
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
