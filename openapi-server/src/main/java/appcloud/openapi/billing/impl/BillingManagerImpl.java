package appcloud.openapi.billing.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.billing.BillingManager;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.manager.BillingrateManager;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.Billingrate;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.free4lab.utils.account.AccountUtil;

public class BillingManagerImpl implements BillingManager {
	
	final static Logger logger = Logger.getLogger(BillingManagerImpl.class);
	private static final int  RECID = Integer.parseInt(com.appcloud.vm.fe.common.Constants.ADMIN_TENANT_ID);
	private static VmUserProxy vmUserProxy;
	private static VmInstanceProxy vmInstanceProxy;
	
	public BillingManagerImpl()
	{
		super();
		vmUserProxy  = (VmUserProxy)ConnectionFactory.getTipProxy(VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
				VmInstanceProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	public Map<String, String> pay (Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception{
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
        Integer userId = vmUserProxy.getByAppKeyId(appKeyId).getUserId();
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

        String message = BillingAPI.pay(userId, RECID, pname, payment_type, times, count, Constants.INSTANCE_TYPE, reason, amount, null);
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
        
		new VmHdEndtimeManager().createVmHdEndtime(userId, uuid, creattime, VmHdEndtime.TYPE_VM, paymentType, alivetime);
		
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
		String type = "vm";
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
        	if(type.equals("vm")){
        		item = "CPU/内存/硬盘/带宽";
        	}else if(type.equals("vmpackage")){
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
        if(type.equals("vm")){
        	item = "CPU/内存/硬盘/带宽";
            logger.debug("item = " + item);
        }else if(type.equals("vmpackage")){
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
			resultMap.put(Constants.ERRORMESSAGE,"instance timeout");
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
}
