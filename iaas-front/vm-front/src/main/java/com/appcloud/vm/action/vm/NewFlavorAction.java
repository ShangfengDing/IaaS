package com.appcloud.vm.action.vm;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.Flavor;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;
import appcloud.api.constant.ServerMetadata;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.util.ClientFactory;

public class NewFlavorAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(NewFlavorAction.class);
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private static final int  RECID = Integer.parseInt(Constants.ADMIN_TENANT_ID);
	private static final String PTYPE="vm";
	
	private String serverId;
	private String serverName;
	private String description;
	private Integer oldHdNum;
	private Integer oldCpuNum;
	private Integer oldMemNum;
	private Integer oldBandNum;
	private Integer cpuNum;
	private Integer memNum;
	private Integer bandNum;
	private String result = "fail";
	private int times = 0; //暂时无用
	private String kind;//操作类型 flavor:修改计算资源 netWork：修改带宽

	//修改虚拟机的硬件配置
	public String execute() throws Exception{
		Integer uid = this.getSessionUserID();
		String userId = uid.toString();
		if(kind.equals("flavor")){
			logger.info("修改计算资源");
			logger.info("serverId:"+serverId+",cpuNum:"+cpuNum+",memNum:"+memNum);
		}else{
			logger.info("修改网络资源");
			logger.info("serverId:"+serverId+",bandNum:"+bandNum);
		}
		
		
		logger.info("start payment!");
		//计算时间差
		VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        VmHdEndtime vm = vmHdEndtimeManager.getVmEndtimeByUuid(serverId);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
	    long t1 = 0L;
	    long t2 = 0L;
        try {
        	logger.info("tring to get endTime vm is:"+vm);
        	Timestamp endTime = new Timestamp(vm.getEndTime().getTime());
            t1 = timeformat.parse(getTimeStampNumberFormat(endTime)).getTime();
            logger.info("t1="+t1);
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
        
        if(t1 - t2 <= 0) {
        	logger.info("虚拟机已过期");
			this.result = "timeout";
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "由于云主机过期，修改云主机\""+serverName+"\"失败", 
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
			return SUCCESS;
        }
        //获取本配置的集群
        Server server = serverClient.get(userId, serverId);
    	int clusterid = server.aggregate;
        //账号余额
        Integer balance = BillingAPI.balance(uid, this.getAccessToken(), null, null);
        Integer payamount = 0;
        Integer paymentType = vmHdEndtimeManager.convertToPaymentType(vm.getPayType());
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        double count = 1.0;//如果过期，则按原来申请的一个单位(年、月、日、小时)续费
		if (paymentType == 1) {
			count = (t1 - t2) / (Constants.yearDays * 24 * 3600 * 1000.0);
		} else if (paymentType == 2) {
			count = (t1 - t2) / (Constants.monthDays * 24 * 3600 * 1000.0);
		} else if (paymentType == 3) {
			count = (t1 - t2) / (24 * 3600 * 1000.0);
		} else if (paymentType == 4) {
			count = (t1 - t2) / (3600 * 1000.0);
		}

		logger.info("count:" + count);

		// 计算手续费的份数
		double chargecount = 1.0;
		// 先判断余额是否充足，再进行扣费等操作
		Integer reamount = 0;
		Integer chargeamount = 0;

		reamount = BillingAPI.getPrice("flavor", clusterid, oldCpuNum,
				oldMemNum, -1, oldBandNum, uid, paymentType, count);
		if(kind.equals("flavor")){//修改计算资源，带宽部分用旧值
			payamount = BillingAPI.getPrice("flavor", clusterid, cpuNum, memNum,
					-1, oldBandNum, uid, paymentType, count);
		}else if(kind.equals("netWork")){////修改网络资源，cpu和内存部分用旧值
			payamount = BillingAPI.getPrice("flavor", clusterid, oldCpuNum, oldMemNum,
					-1, bandNum, uid, paymentType, count);
		}
		chargeamount = BillingAPI.getPrice("charge", clusterid, -1, -1, -1, -1,
				uid, paymentType, chargecount);

		logger.info("用户余额："+balance);
		logger.info("新修改配置需要付费："+payamount);
		logger.info("退费："+reamount);
		logger.info("手续费："+chargeamount);
		if (balance < (payamount - reamount + chargeamount)) {
			logger.info("余额不足，返回");
			this.result = "余额不足，请尽快充值！";
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "由于余额不足，修改云主机\""+serverName+"\"配置失败", 
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
			return SUCCESS;
		}

		// 尝试扣费，先退费再扣费
		// 退费
		String PID = "购买" + oldCpuNum + "核CPU" + oldMemNum + "G内存和"
				+ oldBandNum + "M带宽，未到期，退费";
		logger.debug("PID = " + PID);
		String item = "CPU/内存/带宽";
		logger.debug("item = " + item);
		String message = "";
		message = BillingAPI.pay(RECID, uid, item, payment_type, times, count, PTYPE, PID, reamount, this.getAccessToken());
		if (!message.equalsIgnoreCase("success")) {
			logger.info("payment fail");
			this.result = message;
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "由于付费失败，修改云主机\""+serverName+"\"配置失败", 
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
			return SUCCESS; // 退费不成功
		}
		// 收取手续费
		item = "手续费";
		PID = "修改硬件配置的手续费";
		chargecount = 1.0;
		message = BillingAPI.pay(uid, RECID, item, payment_type, times, chargecount, PTYPE, PID, chargeamount, this.getAccessToken());
		if (!message.equalsIgnoreCase(SUCCESS)) {
			logger.info("payment fail");
			this.result = message;
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "由于付费失败，修改云主机\""+serverName+"\"配置失败", 
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
			return SUCCESS;
		}
        //扣费
	    PID="修改配置" + cpuNum + "核CPU" + memNum + "G内存和" + bandNum + 
	    		"M带宽，重新计算价格，扣费";
        logger.debug("PID = " + PID);
        item = "CPU/内存/带宽";
        logger.debug("item = " + item);
        message = "";
        //如果已过期，则此处的count为1.0
        if(kind.equals("flavor")){//修改计算资源，带宽部分用旧值
			payamount = BillingAPI.getPrice("flavor", clusterid, cpuNum, memNum,
					-1, oldBandNum, uid, paymentType, count);
		}else if(kind.equals("netWork")){////修改网络资源，cpu和内存部分用旧值
			payamount = BillingAPI.getPrice("flavor", clusterid, oldCpuNum, oldMemNum,
					-1, bandNum, uid, paymentType, count);
		}
        
        message = BillingAPI.pay(uid, RECID, item, payment_type, times, count, PTYPE, PID, payamount, this.getAccessToken());
        if(!message.equalsIgnoreCase("success")){
            logger.info("payment fail");
            this.result = message;
            addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "由于付费失败，修改云主机\""+serverName+"\"配置失败", 
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
            return SUCCESS; //扣费不成功
        }
        logger.info("payment success");
        if(kind.equals("flavor")){
			//创建flavor
			Flavor newFlavor = new Flavor(null, serverName+"-flavor", userId, memNum, oldHdNum, cpuNum);
			newFlavor = flavorClient.createFlavor(userId, newFlavor);
			logger.info("创建flavor成功");
			String flavorRef = newFlavor.id.toString();	//flavorRef传的是flavor的id
					
			//将带宽参数放在metadata中
	//		HashMap<String, String> metadata = new HashMap<String, String>();
	//		metadata.put(ServerMetadata.MAX_BANDWIDTH, bandNum.toString());
	//		metadata.put(ServerMetadata.DISPLAY_DESCRIPTION, description);
	
			ServerActionRebuild rebuild = new ServerActionRebuild();
			rebuild.flavorRef = flavorRef;
			logger.info("发送修改计算资源请求到后台。。。"+uid+","+serverId);
			serverClient.rebuild(userId, serverId, rebuild);//修改计算资源
        }else if(kind.equals("netWork")){
			logger.info("发送修改网络到后台。。。"+uid+","+serverId);
			serverClient.updateMetadataItem(uid.toString(), serverId, "maxBandwidth", bandNum.toString(),true);
        }
        vmHdEndtimeManager.updateVmHdEndtimeType(uid, serverId, VmHdEndtime.TYPE_VM);
		logger.info("修改虚拟机配置成功");
		if(kind.equals("flavor")){
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "修改云主机\""+serverName+"\"配置，原配置：" + oldCpuNum + "核CPU、" + 
					oldMemNum + "G内存和" + oldBandNum + "M带宽，新配置："
					+ cpuNum + "核CPU、" + memNum + "G内存和" + oldBandNum + "M带宽",
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
		}else if(kind.equals("netWork")){
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机配置", "修改云主机\""+serverName+"\"配置，原配置：" + oldCpuNum + "核CPU、" + 
					oldMemNum + "G内存和" + oldBandNum + "M带宽，新配置："
					+ oldCpuNum + "核CPU、" + oldMemNum + "G内存和" + bandNum + "M带宽",
					"NewFlavorAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
		}
        this.result = "success";
		return SUCCESS;
	}

	public static String getTimeStampNumberFormat(Timestamp formatTime) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss"/*, new Locale("zh", "cn")*/);
	    return m_format.format(formatTime);
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

	public Integer getBandNum() {
		return bandNum;
	}

	public void setBandNum(Integer bandNum) {
		this.bandNum = bandNum;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Integer getOldHdNum() {
		return oldHdNum;
	}

	public void setOldHdNum(Integer oldHdNum) {
		this.oldHdNum = oldHdNum;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getOldCpuNum() {
		return oldCpuNum;
	}

	public void setOldCpuNum(Integer oldCpuNum) {
		this.oldCpuNum = oldCpuNum;
	}

	public Integer getOldMemNum() {
		return oldMemNum;
	}

	public void setOldMemNum(Integer oldMemNum) {
		this.oldMemNum = oldMemNum;
	}

	public Integer getOldBandNum() {
		return oldBandNum;
	}

	public void setOldBandNum(Integer oldBandNum) {
		this.oldBandNum = oldBandNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
