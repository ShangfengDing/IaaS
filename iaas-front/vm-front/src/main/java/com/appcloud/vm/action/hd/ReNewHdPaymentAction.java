package com.appcloud.vm.action.hd;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.Volume;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.util.ClientFactory;

public class ReNewHdPaymentAction extends BaseAction{

    private Logger logger = Logger.getLogger(ReNewHdPaymentAction.class);
    private static final long serialVersionUID = 3752241292660756346L;
    private static final VolumeClient volumeClient = ClientFactory.getVolumeClient();
    private static final int  RECID = Integer.parseInt(Constants.ADMIN_TENANT_ID);
    private static final String PTYPE="vm";
    
    private Integer hdNum;       //硬盘大小
    private String result;      //扣费是否成功
    private int paymentType;    //付费方式，年月日
    private Integer times;      //次数，目前无用
    private double count;       //购买时长
    private String uuid;        //硬盘的uuid
    private int clusterid;
    
    @Override
    public String execute() throws Exception {

        Integer userId = this.getSessionUserID();
        Volume volume = volumeClient.get(userId.toString(), uuid);
        //Step1：计算时间，算份数，然后扣费，设每个月份定为30天，每年定为360天
        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        VmHdEndtime vm = vmHdEndtimeManager.getVmEndtimeByUuid(uuid);
        Timestamp endTime = new Timestamp(vm.getEndTime().getTime());
        
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
	    long t1 = 0L;
	    long t2 = 0L;
        try {
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
        
        logger.info("start hd payment!");
        //账号余额
        double balance = BillingAPI.balance(userId ,this.getAccessToken(), null, null);
        //本次应付款金额
        Integer payamount = BillingAPI.getPrice(Constants.BILLING_HD_ABBR.toLowerCase(), clusterid, -1, -1, hdNum, -1, userId, paymentType, count);
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
        	//计算手续费的份数
        	double chargecount = 1.0;
        	//先判断余额是否充足，再进行扣费等操作
        	Integer reamount = BillingAPI.getPrice(Constants.BILLING_HD_ABBR.toLowerCase(), clusterid, -1, -1, hdNum, -1, userId, oldPaymentType, oldCount);
        	Integer chargeamount = BillingAPI.getPrice(Constants.BILLING_CHARGE.toLowerCase(), clusterid, -1, -1, -1, -1, userId, oldPaymentType, chargecount);
        	logger.info(balance);
        	logger.info(payamount);
        	logger.info(reamount);
        	logger.info(chargeamount);
        	if(balance < (payamount - reamount + chargeamount)){
        		logger.info("余额不足，返回");
        		this.result = "余额不足，请尽快充值！";
        		addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云硬盘付费方式", "由于余额不足，修改云硬盘\""+volume.displayName+"\"付费方式失败", 
    					"ReNewHdPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS;
        	}
        	
        	//退费
        	String PID="购买" +hdNum+ "G硬盘，未到期，退费";
        	logger.debug("PID = " + PID);
        	String item = "硬盘";
        	logger.debug("item = " + item);
        	String message = "";
        	String payment_type = new VmHdEndtimeManager().convertToType(oldPaymentType);
        	message = BillingAPI.pay(RECID, userId, item, payment_type, times, oldCount, PTYPE, PID, reamount, this.getAccessToken());
        	if(!message.equalsIgnoreCase("success")){
        		logger.info("payment fail");
        		this.result = message;
        		addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云硬盘付费方式", "由于扣费失败，修改云硬盘\""+volume.displayName+"\"付费方式失败", 
    					"ReNewHdPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS; //退费不成功
        	}
        	
        	 //收取手续费
            item = "手续费";
            PID="修改付费规则的手续费";
            payment_type = new VmHdEndtimeManager().convertToType(oldPaymentType);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, chargecount, PTYPE, PID, chargeamount, this.getAccessToken());
            if(!message.equalsIgnoreCase(SUCCESS)){
                logger.info("payment fail");
                this.result = message;
                addAcMessageLog(AcModuleEnum.VM_FRONT, 
    					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
    					"修改云硬盘付费方式", "由于扣费失败，修改云硬盘\""+volume.displayName+"\"付费方式失败", 
    					"ReNewHdPaymentAction.class", null, AcLogLevelEnum.WARN,
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
    					"修改云硬盘付费方式", "由于余额不足，修改云硬盘\""+volume.displayName+"\"付费方式失败", 
    					"ReNewHdPaymentAction.class", null, AcLogLevelEnum.WARN,
    					new Date(System.currentTimeMillis()));
        		return SUCCESS;
        	}
        }
        
        String PID="购买" + hdNum + "G硬盘";
        String item = "硬盘";
        logger.info("userId = " + userId + " item = " + item + " paymentType = " + paymentType 
                    + " count = " + count + " PID = " + PID);
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        String message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, payamount, this.getAccessToken());
        if(!message.equalsIgnoreCase(SUCCESS)){
            logger.info("payment fail");
            this.result = message;
            addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
					"修改云硬盘付费方式", "由于扣费失败，修改云硬盘\""+volume.displayName+"\"付费方式失败", 
					"ReNewHdPaymentAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
            return SUCCESS; 
        }
        logger.info("payment hd success!");
        
        //Step3:修改硬盘到期时间以及付费类型
        logger.info("start modify hd endtime!");
        logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
             + " count = " + count);
        vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, new Timestamp(System.currentTimeMillis()), VmHdEndtime.TYPE_HD, paymentType, count);
        this.result = "success";
        addAcMessageLog(AcModuleEnum.VM_FRONT, 
				UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
				"修改云硬盘付费方式", "修改云硬盘\""+volume.displayName+"\"付费方式", 
				"ReNewHdPaymentAction.class", null, AcLogLevelEnum.INFO,
				new Date(System.currentTimeMillis()));
        return SUCCESS;
    }

    public static String getTimeStampNumberFormat(Timestamp formatTime) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss"/*, new Locale("zh", "cn")*/);
	    return m_format.format(formatTime);
	}
    
    public Integer getHdNum() {
        return hdNum;
    }

    public void setHdNum(Integer hdNum) {
        this.hdNum = hdNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

	public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}
}
