package com.appcloud.vm.action.hd;

import java.sql.Timestamp;
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

public class ReNewHdAction extends BaseAction{

    private Logger logger = Logger.getLogger(ReNewHdAction.class);
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
    private int clusterid;      //集群id
    
    @Override
    public String execute() throws Exception {

        Integer userId = this.getSessionUserID();
        Volume volume = volumeClient.get(userId.toString(), uuid);
        
        //Step1:查询余额，余额不足则返回，否则扣费
        logger.info("start hd payment!");
        String PID="购买" + hdNum + "G硬盘";
        String item = "硬盘";
        logger.info("userId = " + userId + " item = " + item + " paymentType = " + paymentType 
                    + " count = " + count + " PID = " + PID);
        int price = BillingAPI.getPrice(Constants.BILLING_HD_ABBR.toLowerCase(), clusterid, -1, -1, hdNum, -1, userId, paymentType, count);
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        String message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, price, this.getAccessToken());
        if(!message.equalsIgnoreCase(SUCCESS)){
            logger.info("payment fail");
            this.result = message;
            addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
					"云硬盘续费", "由于扣费失败，云硬盘续费\""+volume.displayName+"\"失败", 
					"NewVmAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
            return SUCCESS; 
        }
        logger.info("payment hd success!");
        this.result = "success";
        
        //Step2:修改虚拟机到期时间以及付费类型，需要判断是否已过期
        logger.info("start modify hd endtime!");
        logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
             + " count = " + count);
        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
        Date endTimestamp = vmHdEndtimeManager.getVmEndtimeByUuid(uuid).getEndTime();
        if(!tempStamp.after(endTimestamp)){
            logger.info("use the former time for the start time");
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, endTimestamp, VmHdEndtime.TYPE_HD, paymentType, count);
        }else{
            logger.info("use the now time for the start time");
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, new Timestamp(System.currentTimeMillis()), VmHdEndtime.TYPE_HD, paymentType, count);
        }
        
        addAcMessageLog(AcModuleEnum.VM_FRONT, 
				UUID.randomUUID().toString().replace("-", ""), userId.toString(), uuid,
				"云硬盘续费", "云硬盘\""+volume.displayName+"\"续费成功", 
				"ReNewHdAction.class", null, AcLogLevelEnum.INFO,
				new Date(System.currentTimeMillis()));
        return SUCCESS;
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
