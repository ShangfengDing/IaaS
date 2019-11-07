package appcloud.openapi.billing.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcloud.openapi.operate.impl.InstanceLogOperateImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Volume;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.common.model.*;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.billing.BillingManager;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.datatype.InstanceTypeItem;
import appcloud.openapi.datatype.InstanceTypeSet;
import appcloud.openapi.manager.util.BeansGenerator;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Bill_Constants;
import com.appcloud.vm.fe.manager.BillingrateManager;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.Billingrate;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.free4lab.utils.account.AccountUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BillingManagerImpl implements BillingManager {
	
	final static Logger logger = Logger.getLogger(BillingManagerImpl.class);
	private static final int  RECID = Integer.parseInt(com.appcloud.vm.fe.common.Constants.ADMIN_TENANT_ID);
	private InstanceLogOperateImpl instanceLogOperate = new InstanceLogOperateImpl();
	private VmUserProxy vmUserProxy;
	private VmInstanceProxy vmInstanceProxy;
	private VmGroupProxy vmGroupProxy;
	private VmVolumeProxy volumeProxy;

	public BillingManagerImpl()
	{
		super();
		vmUserProxy  = (VmUserProxy)ConnectionFactory.getTipProxy(VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
				VmInstanceProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
				VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	public Map<String, String> pay (Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
		switch (paramsMap.get(Constants.ACTION)) {
			case ActionConstants.CREATE_INSTANCE:
				resultMap = createInstancepay(paramsMap,defaultParamsMap);
				break;
			case ActionConstants.CREATE_DISK:
				resultMap = createDiskpay(paramsMap,defaultParamsMap);
				break;
			default :
				resultMap = null;
				break;
		}
		return resultMap;
	}
	public Map<String, String> createInstancepay (Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
		//获取必要参数
		int times = 0;
		double count = 1;
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		String instanceType = paramsMap.get(Constants.INSTANCE_TYPE);
		String instanceChargeType;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_TYPE))
			instanceChargeType = defaultParamsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		else
			instanceChargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		String instanceChargeLength;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))
			instanceChargeLength = defaultParamsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		else
			instanceChargeLength = paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		List<Billingrate> billingrateList = BillingrateManager.findBillingRateByNameAndPType(instanceType,
				Constants.INSTANCE_TYPE);
		Billingrate billingrate = billingrateList.get(0);
	    String reason = "购买" + instanceType + "套餐.配置:" + billingrate.getCpu() + "核CPU" + billingrate.getMemory() + "G内存" 
	    		+ billingrate.getHarddisk() + "G硬盘和" + billingrate.getBandwidth() + "M带宽";
        Integer alivetime = Integer.valueOf(instanceChargeLength).intValue();
		VmUser vmUser = vmUserProxy.getByAppKeyId(appKeyId);
        Integer preId = vmUser.getPreId();
		Integer userId = vmUser.getUserId();
        String pname = "CPU/内存/硬盘/带宽";
        
        int amount = 0;
        int paymentType = 4;
        if ( instanceChargeType.equals(Constants.PAY_BY_HOUR) && alivetime <= Constants.MAX_PAY_BY_HOUR )
        {
        	amount = alivetime*billingrate.getHourprice();
        	paymentType= 4;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_DAY) && alivetime <= Constants.MAX_PAY_BY_DAY )
        {
        	amount = alivetime*billingrate.getDayprice();
        	paymentType= 3;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_MONTH) && alivetime <= Constants.MAX_PAY_BY_MONTH )
        {
        	amount = alivetime*billingrate.getMonthprice();
        	paymentType= 2;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_YEAR) && alivetime <= Constants.MAX_PAY_BY_YEAR )
        {
        	amount = alivetime*billingrate.getYearprice();
        	paymentType= 1;
        }
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        /*select cluster*/
        QueryObject<VmUser> query = new QueryObject<VmUser>();
		//TODO 用的是userId
		query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
		@SuppressWarnings("unchecked")
		List<VmUser> vmUsers = (List<VmUser>) vmUserProxy.searchAll(query);
		if(vmUsers == null || vmUsers.size() == 0)
			throw new ItemNotFoundException("tenant does not exist");
		
		VmGroup vmGroup = vmGroupProxy.getById(vmUsers.get(0).getGroupId());
		if(vmGroup == null)
			throw new ItemNotFoundException("group does not exist");
		Integer clusterId = BeansGenerator.getInstance().groupToAcGroup(vmGroup).availableClusters.get(0);
		/*select cluster finish*/
		Integer diskprice = 0;
		Integer internetprice = 0;
		if(paramsMap.get(Constants.DATA_DISK1_SIZE) != null && !paramsMap.get(Constants.DATA_DISK1_SIZE).equals("0"))
		{
			int disksize = Integer.parseInt(paramsMap.get(Constants.DATA_DISK1_SIZE));
			diskprice = BillingAPI.getPrice(com.appcloud.vm.fe.common.Constants.BILLING_HD_ABBR.toLowerCase(), clusterId, -1, -1, disksize, -1, preId, paymentType, (double)(alivetime.intValue()));
			reason += "外加"+disksize+"G硬盘";
		}
		if(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)!="0")
		{
			int bandwidth = Integer.parseInt(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT));
			internetprice = BillingAPI.getPrice(com.appcloud.vm.fe.common.Constants.BILLING_BW_ABBR.toLowerCase(), clusterId, -1, -1, -1, bandwidth, preId, paymentType, (double)(alivetime.intValue()));
			reason += "外加"+bandwidth+"M带宽";
		}
        amount = amount+diskprice.intValue()+internetprice.intValue();
        String message = BillingAPI.pay(preId, RECID, pname, payment_type, times, count, Constants.INSTANCE_TYPE, reason, amount, null);
        if(!message.equalsIgnoreCase("SUCCESS")){
        	resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"maybe your money is not enough!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
 			return resultMap;
        }
	    resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	public Map<String, String> createDiskpay(Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		Integer userId = vmUserProxy.getByAppKeyId(appKeyId).getPreId();
		Integer size = Integer.parseInt(paramsMap.get(Constants.DISK_SIZE));
		String DiskChargeType;
		if (null == paramsMap.get(Constants.DISK_CHARGE_TYPE))
			DiskChargeType = defaultParamsMap.get(Constants.DISK_CHARGE_TYPE);
		else
			DiskChargeType = paramsMap.get(Constants.DISK_CHARGE_TYPE);
		String DiskChargeLength;
		if (null == paramsMap.get(Constants.DISK_CHARGE_LENGTH))
			DiskChargeLength = defaultParamsMap.get(Constants.DISK_CHARGE_LENGTH);
		else
			DiskChargeLength = paramsMap.get(Constants.DISK_CHARGE_LENGTH);
		String PTYPE = "vm";
		Integer times = 0;
		Double count = Double.valueOf(DiskChargeLength).doubleValue();
		int paymentType = 4;
        if ( DiskChargeType.equals(Constants.PAY_BY_HOUR) && count <= Constants.MAX_PAY_BY_HOUR )
        {
        	paymentType= 4;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_DAY) && count <= Constants.MAX_PAY_BY_DAY )
        {
        	paymentType= 3;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_MONTH) && count <= Constants.MAX_PAY_BY_MONTH )
        {
        	paymentType= 2;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_YEAR) && count <= Constants.MAX_PAY_BY_YEAR )
        {
        	paymentType= 1;
        }
		/*select cluster*/
        QueryObject<VmUser> query = new QueryObject<VmUser>();
		query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
		@SuppressWarnings("unchecked")
		List<VmUser> vmUsers = (List<VmUser>) vmUserProxy.searchAll(query);
		if(vmUsers == null || vmUsers.size() == 0)
			throw new ItemNotFoundException("tenant does not exist");
		
		VmGroup vmGroup = vmGroupProxy.getById(vmUsers.get(0).getGroupId());
		if(vmGroup == null)
			throw new ItemNotFoundException("group does not exist");
		Integer clusterId = BeansGenerator.getInstance().groupToAcGroup(vmGroup).availableClusters.get(0);
		/*select cluster finish*/
		logger.info("start payment!");
	    String PID="购买" + size + "G硬盘";
        logger.debug("PID = " + PID);
        String item = "硬盘";
        logger.debug("item = " + item);
        //计算应扣费金额
        Integer price = BillingAPI.getPrice(com.appcloud.vm.fe.common.Constants.BILLING_HD_ABBR.toLowerCase(), clusterId, -1, -1, size, -1, userId, paymentType, count);
        logger.info(price);
        //尝试扣费
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        String message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, price, null);
        if(!message.equalsIgnoreCase("SUCCESS")){
        	resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"maybe your money is not enough!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
 			return resultMap;
        }
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	@Override
	public Map<String, String> AddEndtime(Map<String, String> paramsMap,
			Map<String, String> defaultParamsMap,
			Map<String, String> serverMap) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		switch (paramsMap.get(Constants.ACTION)) {
			case ActionConstants.CREATE_INSTANCE:
				resultMap = createInstanceAddEndtime(paramsMap,defaultParamsMap,serverMap);
				break;
			case ActionConstants.CREATE_DISK:
				resultMap = createDiskAddEndtime(paramsMap,defaultParamsMap,serverMap);
				break;
			default :
				resultMap = null;
				break;
		}
		return resultMap;
	}
	public Map<String, String> createInstanceAddEndtime(Map<String, String> paramsMap,
			Map<String, String> defaultParamsMap,
			Map<String, String> serverMap) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		String uuid = "";
        if(null == serverMap.get(Constants.INSTANCE_ID))
        {
        	resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"instance's uuid missing!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
			return resultMap;
        }
        else
        {
        	uuid = serverMap.get(Constants.INSTANCE_ID);
        }
        Date creattime = vmInstanceProxy.getByUuid(uuid, false, false, false, false, true, false, false, false).getScheduledTime();
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		String instanceType = paramsMap.get(Constants.INSTANCE_TYPE);
		String instanceChargeType;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_TYPE))
			instanceChargeType = defaultParamsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		else
			instanceChargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		String instanceChargeLength;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))
			instanceChargeLength = defaultParamsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		else
			instanceChargeLength = paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		
		Integer userId = vmUserProxy.getByAppKeyId(appKeyId).getUserId();
		List<Billingrate> billingrateList = BillingrateManager.findBillingRateByNameAndPType(instanceType,
				Constants.INSTANCE_TYPE);
		Billingrate billingrate = billingrateList.get(0);
		Integer alivetime = Integer.valueOf(instanceChargeLength).intValue();
		int paymentType = 4;
        if ( instanceChargeType.equals(Constants.PAY_BY_HOUR) )
        {
        	paymentType= 4;
        	if ( 0 == billingrate.getHourprice() )
        		alivetime = Constants.ZERO_PRICE_FOR_YEAR * Constants.MAX_PAY_BY_MONTH * Constants.MAX_PAY_BY_DAY * Constants.MAX_PAY_BY_HOUR ;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_DAY) )
        {
        	paymentType= 3;
        	if ( 0 == billingrate.getDayprice() )
        		alivetime = Constants.ZERO_PRICE_FOR_YEAR * Constants.MAX_PAY_BY_MONTH * Constants.MAX_PAY_BY_DAY ;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_MONTH) )
        {
        	paymentType= 2;
        	if ( 0 == billingrate.getMonthprice() )
        		alivetime = Constants.ZERO_PRICE_FOR_YEAR * Constants.MAX_PAY_BY_MONTH;
        }
        else if ( instanceChargeType.equals(Constants.PAY_BY_YEAR) )
        {
        	paymentType= 1;
        	if (0 == billingrate.getYearprice())
        		alivetime = Constants.ZERO_PRICE_FOR_YEAR ; 
        }

		VmHdEndtime vmHdEndtime = new VmHdEndtimeManager().createVmHdEndtime(userId, uuid, creattime, VmHdEndtime.TYPE_INSTANCETYPE, paymentType, alivetime);
		instanceLogOperate.add(vmHdEndtime, new Timestamp(System.currentTimeMillis()), 1);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	public Map<String, String> createDiskAddEndtime(Map<String, String> paramsMap,
			Map<String, String> defaultParamsMap,
			Map<String, String> serverMap) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		Integer userId = vmUserProxy.getByAppKeyId(appKeyId).getUserId();
		String DiskChargeType;
		if (null == paramsMap.get(Constants.DISK_CHARGE_TYPE))
			DiskChargeType = defaultParamsMap.get(Constants.DISK_CHARGE_TYPE);
		else
			DiskChargeType = paramsMap.get(Constants.DISK_CHARGE_TYPE);
		String DiskChargeLength;
		if (null == paramsMap.get(Constants.DISK_CHARGE_LENGTH))
			DiskChargeLength = defaultParamsMap.get(Constants.DISK_CHARGE_LENGTH);
		else
			DiskChargeLength = paramsMap.get(Constants.DISK_CHARGE_LENGTH);
		Double count = Double.valueOf(DiskChargeLength).doubleValue();
		int paymentType = 4;
        if ( DiskChargeType.equals(Constants.PAY_BY_HOUR) && count <= Constants.MAX_PAY_BY_HOUR )
        {
        	paymentType= 4;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_DAY) && count <= Constants.MAX_PAY_BY_DAY )
        {
        	paymentType= 3;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_MONTH) && count <= Constants.MAX_PAY_BY_MONTH )
        {
        	paymentType= 2;
        }
        else if ( DiskChargeType.equals(Constants.PAY_BY_YEAR) && count <= Constants.MAX_PAY_BY_YEAR )
        {
        	paymentType= 1;
        }
		String volumeid = serverMap.get(Constants.DISK_ID);
		if(null == volumeid){
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"volumeid is null!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
 			return resultMap;
		}
		VmVolume vmVolume = volumeProxy.getByUUID(volumeid, false, false, false, false);
		Volume volume = BeansGenerator.getInstance().vmVolumeToVolume(vmVolume, true);
		if(volume != null) {
			VmHdEndtime vmHdEndtime = new VmHdEndtimeManager().createVmHdEndtime(userId, volume.id,
					volume.createdAt, VmHdEndtime.TYPE_HD, paymentType, count);
			instanceLogOperate.add(vmHdEndtime, new Timestamp(System.currentTimeMillis()), 1);
		}
		else{
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"volume is null!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
 			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> renew(Map<String, String> paramsMap,
			Map<String, String> defaultParamsMap, String requestId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		switch (paramsMap.get(Constants.ACTION)) {
			case ActionConstants.RENEW_INSTANCE:
				resultMap = Instancerenew(paramsMap,defaultParamsMap,requestId);
				break;
			case ActionConstants.RENEW_DISK:
				resultMap = Diskrenew(paramsMap,defaultParamsMap,requestId);
				break;
			default :
				resultMap = null;
				break;
		}
		return resultMap;
	}
	public Map<String, String> Instancerenew(Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId)throws Exception
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String uuid = paramsMap.get(Constants.INSTANCE_ID);
		VmInstance instance = vmInstanceProxy.getByUuid(uuid, false, true, false, true, true, true, false, false);
		Integer userId = vmuser.getUserId();
		Integer clusterid = instance.getCluster().getId();
		Integer cpuNum = instance.getVmInstanceType().getVcpus();
		Integer memNum = instance.getVmInstanceType().getMemoryMb();
		Integer bandNum = -1;
		Integer hdNum = instance.getVmInstanceType().getLocalGb();
		List<VmInstanceMetadata> bandNumlist = instance.getVmInstanceMetadatas();
		for(VmInstanceMetadata md : bandNumlist) {
			if(md.getKey().equals(Constants.PUBLIC_BANDWIDTH)) {
				bandNum = Integer.valueOf(md.getValue());
				break;
			}
		}
		if(-1 == bandNum)
		{
			logger.info("带宽获取失败！");
    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This instance do not have band");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap;
		}
		String instanceChargeType;
		Integer paymentType ;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_TYPE))
		{
			instanceChargeType = new VmHdEndtimeManager().getVmEndtimeByUuid(uuid).getPayType();
			paymentType  = CconvertToPaymentType(instanceChargeType);
		}
		else
		{
			instanceChargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
			paymentType  = convertToPaymentType(instanceChargeType);
		}
		String instanceChargeLength;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))
			instanceChargeLength = defaultParamsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		else
			instanceChargeLength = paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		Double count = Double.valueOf(instanceChargeLength);
		int times = 0;
		String type = new VmHdEndtimeManager().getVmEndtimeByUuid(uuid).getType().toLowerCase();
		String PTYPE = new VmHdEndtimeManager().getVmEndtimeByUuid(uuid).getPayType();
		
		
	 	logger.info("clusterid: " + clusterid + "cpu: " + cpuNum + " ,mem: " + memNum + " ,hd: " + hdNum + " ,band: " + bandNum);

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
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, reason, price, null);
            if(!message.equalsIgnoreCase("success")){
                logger.info("payment fail");
                resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
    			resultMap.put(Constants.ERRORMESSAGE,"payment fail!");
    			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
     			return resultMap;
            }
        }else if(type.equals("instancetype")){
        	com.appcloud.vm.fe.billing.Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
			item = rate.getName();
			price = BillingAPI.getPrice(type, clusterid, cpuNum, memNum, hdNum, bandNum, userId, paymentType, count);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, reason, price, null);
            if(!message.equalsIgnoreCase("success")){
                logger.info("payment fail");
                resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
    			resultMap.put(Constants.ERRORMESSAGE,"payment fail!");
    			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
     			return resultMap;
            }
        }
        logger.info("payment success!");
        
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
        }else if(type.equals("instancetype")){
            logger.info("instancetype");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            Date endTimestamp = vmHdEndtimeManager.getVmEndtimeByUuid(uuid).getEndTime();
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + "tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            if(!tempStamp.after(endTimestamp)){
                logger.info("use the former time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, endTimestamp, VmHdEndtime.TYPE_INSTANCETYPE, paymentType, count);
            }else{
                logger.info("use the now time for the start time");
                vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_INSTANCETYPE, paymentType, count);
            }
        }
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	public Map<String, String> Diskrenew(Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId)throws Exception
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String uuid = paramsMap.get(Constants.DISK_ID);
		VmVolume volume = volumeProxy.getByUUID(uuid,false,false,false,false);
		Integer clusterid = volume.getAvailabilityClusterId();
		Integer userId = vmuser.getUserId();
        Integer hdNum = volume.getSize();
        String diskChargeType;
		Integer paymentType ;
		if (null == paramsMap.get(Constants.DISK_CHARGE_TYPE))
		{
			diskChargeType = new VmHdEndtimeManager().getVmEndtimeByUuid(uuid).getPayType();
			paymentType  = CconvertToPaymentType(diskChargeType);
		}
		else
		{
			diskChargeType = paramsMap.get(Constants.DISK_CHARGE_TYPE);
			paymentType  = convertToPaymentType(diskChargeType);
		}
		String instanceChargeLength;
		if (null == paramsMap.get(Constants.DISK_CHARGE_LENGTH))
			instanceChargeLength = defaultParamsMap.get(Constants.DISK_CHARGE_LENGTH);
		else
			instanceChargeLength = paramsMap.get(Constants.DISK_CHARGE_LENGTH);
		Double count = Double.valueOf(instanceChargeLength);
		int times = 0;
		String PTYPE = "hd";
        
        
        
        //Step1:查询余额，余额不足则返回，否则扣费
        logger.info("start hd payment!");
        String PID="购买" + hdNum + "G硬盘";
        String item = "硬盘";
        logger.info("userId = " + userId + " item = " + item + " paymentType = " + paymentType 
                    + " count = " + count + " PID = " + PID);
        int price = BillingAPI.getPrice(com.appcloud.vm.fe.common.Constants.BILLING_HD_ABBR.toLowerCase().toLowerCase(), clusterid, -1, -1, hdNum, -1, userId, paymentType, count);
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        String message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, price, null);
        if(!message.equalsIgnoreCase("success")){
            logger.info("payment fail");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"payment fail!");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
 			return resultMap;
        }
        logger.info("payment hd success!");
        
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
        
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	@Override
	public Map<String, String> modifyInstanceChargeType(
			Map<String, String> paramsMap, Map<String, String> defaultParamsMap,String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String uuid = paramsMap.get(Constants.INSTANCE_ID);
		VmInstance instance = vmInstanceProxy.getByUuid(uuid, false, true, false, true, true, true, false, false);
		Integer userId = vmuser.getUserId();
		Integer clusterid = instance.getCluster().getId();
		Integer cpuNum = instance.getVmInstanceType().getVcpus();
		Integer memNum = instance.getVmInstanceType().getMemoryMb();
		Integer bandNum = -1;
		Integer hdNum = instance.getVmInstanceType().getLocalGb();
		List<VmInstanceMetadata> bandNumlist = instance.getVmInstanceMetadatas();
		for(VmInstanceMetadata md : bandNumlist) {
			if(md.getKey().equals(Constants.PUBLIC_BANDWIDTH)) {
				bandNum = Integer.valueOf(md.getValue());
				break;
			}
		}
		if(-1 == bandNum)
		{
			logger.info("带宽获取失败！");
    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This instance do not have band");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap;
		}
		String instanceChargeType;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_TYPE))
			instanceChargeType = defaultParamsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		else
			instanceChargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
		String instanceChargeLength;
		if (null == paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))
			instanceChargeLength = defaultParamsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		else
			instanceChargeLength = paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH);
		Integer paymentType = convertToPaymentType(instanceChargeType);
		Double count = Double.valueOf(instanceChargeLength);
		int times = 0;
		String type = new VmHdEndtimeManager().getVmEndtimeByUuid(uuid).getType().toLowerCase();
		String PTYPE = Constants.INSTANCE_TYPE;
		
		
        logger.info("clusterid: " + clusterid + "cpu: " + cpuNum + " ,mem: " + memNum + " ,hd: " + hdNum + " ,band: " + bandNum);
        
        //计算时间，算各种份数，然后扣费，设每个月份定为30天，每年定为360天
        //计算旧计费规则的份数
        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        VmHdEndtime vm = vmHdEndtimeManager.getVmEndtimeByUuid(uuid);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
	    long t1 = 0L;
	    long t2 = 0L;
      
        Timestamp endTime = new Timestamp(vm.getEndTime().getTime());
        t1 = timeformat.parse(getTimeStampNumberFormat(endTime)).getTime();
        t2 = timeformat.parse(getTimeStampNumberFormat(currentTime)).getTime();
        //账号余额
        HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", String.valueOf(userId));
		params.put("source", com.appcloud.vm.fe.common.Constants.CLIENT_ID);
		Integer balance = BillingAPI.balance(userId ,null, AccountUtil.getSignature(params, com.appcloud.vm.fe.common.Constants.CLIENT_SECRET_KEY),com.appcloud.vm.fe.common.Constants.CLIENT_ID);
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
        		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
    			resultMap.put(Constants.ERRORMESSAGE,"your money is not enough!");
    			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
        		return resultMap;
        	}
        	
        	//根据旧的计费规则退费
        	String PID="购买" + cpuNum + "核CPU" + memNum + "G内存" + hdNum +"G硬盘和" + bandNum + 
        			"M带宽，未到期，退费";
        	logger.debug("PID = " + PID);
        	String item = "";
        	if(type.equalsIgnoreCase("vm")){
        		item = "CPU/内存/硬盘/带宽";
        	}else if(type.equalsIgnoreCase("instancetype")){
        		com.appcloud.vm.fe.billing.Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
        		item = rate.getName();
        	}
        	logger.debug("item = " + item);
        	String message = "";
        	String payment_type = new VmHdEndtimeManager().convertToType(oldPaymentType);
        	message = BillingAPI.pay(RECID, userId, item, payment_type, times, oldCount, PTYPE, PID, reamount, null);
        	if(!message.equalsIgnoreCase("success")){
        		logger.info("payment fail");
        		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
    			resultMap.put(Constants.ERRORMESSAGE,"payment fail");
    			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
        		return resultMap; //退费不成功
        	} 
        	 //收取手续费
            item = "手续费";
            PID="修改付费规则的手续费";
            payment_type = new VmHdEndtimeManager().convertToType(paymentType);
            message = BillingAPI.pay(userId, RECID, item, payment_type, times, chargecount, PTYPE, PID, chargeamount, null);
            if(!message.equalsIgnoreCase("success")){
                logger.info("payment fail");
                return resultMap; 
            }
            logger.info("payment success!");
        } else {
        	if(balance < payamount){
        		logger.info("余额不足，返回");
        		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
    			resultMap.put(Constants.ERRORMESSAGE,"your money is not enough!");
    			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
        		return resultMap;
        	}
    	}
        
        //查询余额，扣费
        logger.info("start payment!");
        String PID="购买" + cpuNum + "核CPU" + memNum + "G内存" + hdNum + "G硬盘和" + bandNum + "M带宽";
        logger.debug("PID = " + PID);
        String item = "";
        String message = "";
        logger.info("type = " + type);
        if(type.equalsIgnoreCase("vm")){
        	item = "CPU/内存/硬盘/带宽";
            logger.debug("item = " + item);
        }else if(type.equalsIgnoreCase("instancetype")){
        	com.appcloud.vm.fe.billing.Billingrate rate = BillingAPI.getRate(type, clusterid, cpuNum, memNum, hdNum, bandNum).get(0);
			item = rate.getName();
        }
        String payment_type = new VmHdEndtimeManager().convertToType(paymentType);
        message = BillingAPI.pay(userId, RECID, item, payment_type, times, count, PTYPE, PID, payamount,null);
        if(!message.equalsIgnoreCase("success")){
            logger.info("payment fail");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"payment fail");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
            return resultMap; 
        }
        logger.info("payment success!");
        
        //修改虚拟机到期时间以及付费类型
        logger.info("start modify endtime!");
        if(type.equalsIgnoreCase("vm")){
            logger.info("vm");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + " tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_VM);
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_VM, paymentType, count);
        }else if(type.equalsIgnoreCase("instancetype")){
            logger.info("instancetype");
            Timestamp tempStamp = new Timestamp(System.currentTimeMillis());
            logger.info("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                        + " count = " + count + "tempStamp = " + tempStamp + "  " + VmHdEndtime.TYPE_INSTANCETYPE);
            vmHdEndtimeManager.updateVmHdEndtime(userId, uuid, tempStamp, VmHdEndtime.TYPE_INSTANCETYPE, paymentType, count);
        }
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
	}
	
	public static String getTimeStampNumberFormat(Timestamp formatTime) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss"/*, new Locale("zh", "cn")*/);
	    return m_format.format(formatTime);
	}
	public static Integer convertToPaymentType(String paymentType) {
		int payType = -1;
		if (paymentType.equalsIgnoreCase(Constants.PAY_BY_YEAR)) {
			payType = 1;
		}else if(paymentType.equalsIgnoreCase(Constants.PAY_BY_MONTH)){
			payType = 2;
		}else if(paymentType.equalsIgnoreCase(Constants.PAY_BY_DAY)){
			payType = 3;
		}else if(paymentType.equalsIgnoreCase(Constants.PAY_BY_HOUR)){
			payType = 4;
		}
		return payType;
	}
	
	public static Integer CconvertToPaymentType(String paymentType){
		int payType = 4;
		if (paymentType.equals("包年")) {
			payType = 1;
		}else if(paymentType.equals("包月")){
			payType = 2;
		}else if(paymentType.equals("包日")){
			payType = 3;
		}else if(paymentType.equals("按需")){
			payType = 4;
		}
		return payType;
	}

	@Override
	public Map<String, String> payForModifyInstanceResource(
			Map<String, String> paramsMap, String requestId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		Integer uid = vmuser.getUserId();
		String userId = uid.toString();
		String serverId = paramsMap.get(Constants.INSTANCE_ID);
		VmInstance instance = vmInstanceProxy.getByUuid(serverId, false, true, false, true, true, true, false, false);
		int clusterid = instance.getCluster().getId();
		Integer oldCpuNum = instance.getVmInstanceType().getVcpus();
		Integer oldMemNum = instance.getVmInstanceType().getMemoryMb();
		Integer oldBandNum = -1;
		List<VmInstanceMetadata> bandNumlist = instance.getVmInstanceMetadatas();
		for(VmInstanceMetadata md : bandNumlist) {
			if(md.getKey().equals(Constants.PUBLIC_BANDWIDTH)) {
				oldBandNum = Integer.valueOf(md.getValue());
				break;
			}
		}
		if(-1 == oldBandNum)
		{
			logger.info("带宽获取失败！");
    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This instance do not have band");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap;
		}
		Integer cpuNum = oldCpuNum ;
		Integer memNum = oldMemNum ;
		Integer bandNum = oldBandNum ;
		if (null != paramsMap.get(Constants.CPU_NUM))
			cpuNum = Integer.valueOf(paramsMap.get(Constants.CPU_NUM));
		if (null != paramsMap.get(Constants.RAM_SIZE))
			memNum = Integer.valueOf(paramsMap.get(Constants.RAM_SIZE));		
		if (null != paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT))
			bandNum = Integer.valueOf(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT));		
		logger.info("修改资源");
		logger.info("serverId:"+serverId+",cpuNum:"+cpuNum+",memNum:"+memNum+",bandNum:"+bandNum);
		
		logger.info("start payment!");
		//计算时间差
		VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
        VmHdEndtime vm = vmHdEndtimeManager.getVmEndtimeByUuid(serverId);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
	    long t1 = 0L;
	    long t2 = 0L;
	    
    	logger.info("tring to get endTime vm is:"+vm);
    	Timestamp endTime = new Timestamp(vm.getEndTime().getTime());
        t1 = timeformat.parse(getTimeStampNumberFormat(endTime)).getTime();
        logger.info("t1="+t1);
            
        t2 = timeformat.parse(getTimeStampNumberFormat(currentTime)).getTime();
        
        if(t1 - t2 <= 0) {
        	logger.info("虚拟机已过期");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"instance expired");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
        }
        //获取本配置的集群
       // Server server = serverClient.get(userId, serverId);
        //账号余额
        HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", String.valueOf(userId));
		params.put("source", com.appcloud.vm.fe.common.Constants.CLIENT_ID);
		Integer balance = BillingAPI.balance(uid ,null, AccountUtil.getSignature(params, com.appcloud.vm.fe.common.Constants.CLIENT_SECRET_KEY),com.appcloud.vm.fe.common.Constants.CLIENT_ID);
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

		reamount = BillingAPI.getPrice("flavor", clusterid, oldCpuNum,oldMemNum, -1, oldBandNum, uid, paymentType, count);
		payamount = BillingAPI.getPrice("flavor", clusterid, cpuNum, memNum,-1, bandNum, uid, paymentType, count);
		chargeamount = BillingAPI.getPrice("charge", clusterid, -1, -1, -1, -1,uid, paymentType, chargecount);

		logger.info("用户余额："+balance);
		logger.info("新修改配置需要付费："+payamount);
		logger.info("退费："+reamount);
		logger.info("手续费："+chargeamount);
		if (balance < (payamount - reamount + chargeamount)) {
			logger.info("余额不足，返回");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"your money is not enough");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
            return resultMap; 
		}

		
		int times = 0;
//		String type = "vm";
		String PTYPE = Constants.INSTANCE_TYPE;
		// 尝试扣费，先退费再扣费
		// 退费
		String PID = "购买" + oldCpuNum + "核CPU" + oldMemNum + "G内存和"
				+ oldBandNum + "M带宽，未到期，退费";
		logger.debug("PID = " + PID);
		String item = "CPU/内存/带宽";
		logger.debug("item = " + item);
		String message = "";
		message = BillingAPI.pay(RECID, uid, item, payment_type, times, count, PTYPE, PID, reamount, null);
		if (!message.equalsIgnoreCase("success")) {
			logger.info("payment fail");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"payment fail");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
            return resultMap; // 退费不成功
		}
		// 收取手续费
		item = "手续费";
		PID = "修改硬件配置的手续费";
		chargecount = 1.0;
		message = BillingAPI.pay(uid, RECID, item, payment_type, times, chargecount, PTYPE, PID, chargeamount, null);
		if (!message.equalsIgnoreCase("success")) {
			logger.info("payment fail");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"payment fail");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
            return resultMap;
		}
        //扣费
	    PID="修改配置" + cpuNum + "核CPU" + memNum + "G内存和" + bandNum + 
	    		"M带宽，重新计算价格，扣费";
        logger.debug("PID = " + PID);
        item = "CPU/内存/带宽";
        logger.debug("item = " + item);
        message = "";
        //如果已过期，则此处的count为1.0
			payamount = BillingAPI.getPrice("flavor", clusterid, cpuNum, memNum,
					-1, bandNum, uid, paymentType, count);
        
        message = BillingAPI.pay(uid, RECID, item, payment_type, times, count, PTYPE, PID, payamount, null);
        if(!message.equalsIgnoreCase("success")){
            logger.info("payment fail");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This instance do not have band");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap; //扣费不成功
        }
        logger.info("payment success");
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
	}

	@Override
	public Map<String, Object> DescribeInstanceTypes(
			Map<String, String> paramsMap, String requestId) throws Exception {
		return DescribeTypes(paramsMap,requestId,Bill_Constants.BILLING_ITEM_INSTANCETYPE);
	}
	@Override
	public Map<String, Object> DescribeDiskTypes(
			Map<String, String> paramsMap, String requestId) throws Exception {
		return DescribeTypes(paramsMap,requestId,Bill_Constants.BILLING_ITEM_HD);
	}
	@Override
	public Map<String, Object> DescribeInternetTypes(
			Map<String, String> paramsMap, String requestId) throws Exception {
		return DescribeTypes(paramsMap,requestId,Bill_Constants.BILLING_ITEM_BW);
	}	
	@Override
	public Map<String, Object> DescribeCpuTypes(
			Map<String, String> paramsMap, String requestId) throws Exception {
		return DescribeTypes(paramsMap,requestId,Bill_Constants.BILLING_ITEM_CPU);
	}	
	@Override
	public Map<String, Object> DescribeMemoryTypes(
			Map<String, String> paramsMap, String requestId) throws Exception {
		return DescribeTypes(paramsMap,requestId,Bill_Constants.BILLING_ITEM_MEM);
	}	
	public Map<String, Object> DescribeTypes(
			Map<String, String> paramsMap, String requestId,String Types) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		try{
			VmGroup vmgroup = vmGroupProxy.getById(vmuser.getGroupId());
			String clusters[] = vmgroup.getAvailableClusters().split(",");
			InstanceTypeSet set = new InstanceTypeSet();
			List<InstanceTypeItem> tmplist = new ArrayList<InstanceTypeItem>();
			for(int i = 0 ; i < 1 ; i++)
			{
				List<Billingrate> list = BillingrateManager.findByPtypeAndClusterid(Types,Integer.parseInt(clusters[i]));
				if(null == list)
				{
					logger.info("fail");
					resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
					resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
					resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
					return resultMap;
				}
				for(Billingrate obj:list)
				{
					InstanceTypeItem tmp = new InstanceTypeItem();
					tmp.setInstanceTypeId(obj.getName());
					if(-1 == obj.getCpu())
						tmp.setCpuCoreCount(null);
					else
						tmp.setCpuCoreCount(obj.getCpu());
					if(-1 == obj.getMemory())
						tmp.setMemorySize(null);
					else
						tmp.setMemorySize(obj.getMemory());
					if(-1 == obj.getHarddisk())
						tmp.setHardDisk(null);
					else
						tmp.setHardDisk(obj.getHarddisk());
					if(-1 == obj.getBandwidth())
						tmp.setBandWidth(null);
					else
						tmp.setBandWidth(obj.getBandwidth());
					tmp.setYearPrice(obj.getYearprice());
					tmp.setMonthPrice(obj.getMonthprice());
					tmp.setDayPrice(obj.getDayprice());
					tmp.setHourprice(obj.getHourprice());
					tmplist.add(tmp);
				}
			}
			set.setInstanceTypeItems(tmplist);
			resultMap.put(Constants.INSTANCE_TYPES, set);
		}catch(Exception e)
		{
			logger.info("fail");
			e.printStackTrace();
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}
}
