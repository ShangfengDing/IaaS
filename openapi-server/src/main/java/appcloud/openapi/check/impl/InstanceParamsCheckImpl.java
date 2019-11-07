package appcloud.openapi.check.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.common.model.VmImage;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.InstanceParamsCheck;
import appcloud.openapi.checkutils.InstanceChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import appcloud.openapi.manager.util.StringUtil;

import com.appcloud.vm.fe.manager.BillingrateManager;
import com.appcloud.vm.fe.model.Billingrate;

/**
 *	此类用于操作云主机实例相关接口中参数的检查，但只检查接口特有的参数
 */
@Component
public class InstanceParamsCheckImpl implements InstanceParamsCheck{

	private static Logger logger = Logger.getLogger(InstanceParamsCheckImpl.class);
	private static InstanceParamsCheckImpl instanceParamsCheck = new InstanceParamsCheckImpl();
	public static InstanceParamsCheckImpl getInstance() {
		return instanceParamsCheck;
	}
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmZoneProxy vmZoneProxy;
	private static VmImageProxy vmImageProxy;
	private static VmUserProxy vmUserProxy;
	private static VmGroupProxy vmGroupProxy;
	private static VmInstanceProxy vmInstanceProxy;
	private static VmSecurityGroupProxy vmSecurityGroupProxy;
	public InstanceParamsCheckImpl() {
		super();
		vmZoneProxy = (VmZoneProxy)ConnectionFactory.getTipProxy(
				VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmImageProxy = (VmImageProxy)ConnectionFactory.getTipProxy(
				VmImageProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmGroupProxy = (VmGroupProxy)ConnectionFactory.getTipProxy(
				VmGroupProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceProxy = (VmInstanceProxy)ConnectionFactory.getTipProxy(
				VmInstanceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmSecurityGroupProxy = (VmSecurityGroupProxy)ConnectionFactory.getTipProxy(
				VmSecurityGroupProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check create instance params, RegionId=" + paramsMap.get(Constants.REGION_ID) + 
				"; ImageId=" + paramsMap.get(Constants.IMAGE_ID) + "; InstanceType=" + paramsMap.get(Constants.INSTANCE_TYPE) + 
				"; SecurityGroupId=" + paramsMap.get(Constants.SECURITY_GROUP_ID));
		if(null==paramsMap.get(Constants.REGION_ID)) {
			return commonGenerator.missing(null, Constants.REGION_ID);
		}else {
			if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
					null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
				return commonGenerator.inValid(null, Constants.REGION_ID);
			}
		}
		if(null==paramsMap.get(Constants.IMAGE_ID)) {
			return commonGenerator.missing(null, Constants.IMAGE_ID);
		}else {
			if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.IMAGE_ID))) {
				return commonGenerator.inValid(null, Constants.IMAGE_ID);
			}else if( null==vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID))) {
				return commonGenerator.operationDeny(null, Constants.IMAGE_ID);
			}
		}
		if(null==paramsMap.get(Constants.INSTANCE_TYPE)) {
			return commonGenerator.missing(null, Constants.INSTANCE_TYPE);
		}else if(paramsMap.get(Constants.INSTANCE_TYPE).equalsIgnoreCase("") ) {
			return commonGenerator.inValid(null, Constants.INSTANCE_TYPE);
		}
		//判断接口中的instanceType是否存在以及当前用户是否有权使用
		List<Billingrate> billingrateList = BillingrateManager.findBillingRateByNameAndPType(
						paramsMap.get(Constants.INSTANCE_TYPE), Constants.INSTANCE_TYPE_MYSQL);
		if(null==billingrateList || billingrateList.size()<1) {
			return commonGenerator.notFound(null, Constants.INSTANCE_TYPE);
		}else{
			//获取当前用户所在的群组ID
			Integer groupId = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID)).getGroupId();
			//获取当前用户所在群组的所有集群
			String[] clusters = vmGroupProxy.getById(groupId).getAvailableClusters().split(",");
			int flag = -1;
			for(Billingrate billingrate : billingrateList) {
				for(int index=0; index<clusters.length; index++) {
					if(billingrate.getClusterid()==Integer.parseInt(clusters[index])){
						flag=1;
						break;
					}
				}
			}
			if(-1==flag){
				return commonGenerator.unAuthorized(null, Constants.INSTANCE_TYPE);
			}
		}
		if(null==paramsMap.get(Constants.SECURITY_GROUP_ID)) {
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.SECURITY_GROUP_ID)) ){
				return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		}else if(null==vmSecurityGroupProxy.getByUuid(paramsMap.get(Constants.SECURITY_GROUP_ID), false)){
				return commonGenerator.notFound(null, Constants.SECURITY_GROUP_ID);
		}
		if(null!=paramsMap.get(Constants.ZONE_ID)) {
			if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
					null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
				return commonGenerator.inValid(null, Constants.ZONE_ID);
			}
		}
		if(null!=paramsMap.get(Constants.INSTANCE_NAME) && 
				!Pattern.matches("((\\w|[\u4e00-\u9fa5])+(\\.|_|-|[\u4e00-\u9fa5])?){"+Constants.MIN_NAME+","+
						Constants.MAX_NAME + "}", paramsMap.get(Constants.INSTANCE_NAME))) {
			return commonGenerator.inValid(null, Constants.INSTANCE_NAME);
		}
		if(null!=paramsMap.get(Constants.DESCRIPTION) && 
				!Pattern.matches("(.|[\u4e00-\u9fa5]){"+ Constants.MIN_DESCRIPTION + ","+
						Constants.MAX_DESCRIPTION+"}",paramsMap.get(Constants.DESCRIPTION)) ) {
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		}
		//判断云主机按需付费方式的合法性
		if(null!=paramsMap.get(Constants.INSTANCE_CHARGE_TYPE) ) {
			String chargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
			if(null==chargeType || 
					(!chargeType.equals(Constants.PAY_BY_HOUR) && !chargeType.equals(Constants.PAY_BY_DAY) &&
					 !chargeType.equals(Constants.PAY_BY_MONTH) && !chargeType.equals(Constants.PAY_BY_YEAR)) ) {
				return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_TYPE);
			}
		}
		//判断云主机按需付费时长的合法性
		if(null!=paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH) ) {
			if(!Pattern.matches("\\d+", paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))) {
				return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
			}
			Integer chargeLen = Integer.parseInt(paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH));
			String chargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
			//默认是按小时计费
			if( null==chargeType || chargeType.equals(Constants.PAY_BY_HOUR) ){
				if(chargeLen > Constants.MAX_PAY_BY_HOUR) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_DAY)) {
				if(chargeLen > Constants.MAX_PAY_BY_DAY) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_MONTH)) {
				if(chargeLen > Constants.MAX_PAY_BY_MONTH) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_YEAR)) {
				if(chargeLen > Constants.MAX_PAY_BY_MONTH) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}
		}
		if(null!=paramsMap.get(Constants.INTERNET_CHARGE_TYPE) &&
				!paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT).equals(Constants.PAY_BY_BANDWIDTH)) {
			return commonGenerator.inValid(null, Constants.INTERNET_CHARGE_TYPE);
		}
		if(null!=paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT) &&
				!(Pattern.matches("(\\d+", paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)) &&
				Integer.parseInt(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)) <= Constants.MAX_BANDWIDTHOUT)) {
			return commonGenerator.inValid(null, Constants.INTERNET_MAX_BANDWIDTH_OUT);
		}
		/**
		 * HostName表示云主机的主机名，最少 2 字符，“.”和“-”是不能作为 hostname的首尾字符，且不能连续使用。
		 * Windows 平台最长为 15 字符，允许字母（不限制大小写）、数字和“-”组成，不支持点号（"."），不能全是数字。
		 * 其他（Linux 等）平台最长为 30 字符，允许支持多个点号，点之间为一段，每段允许字母（不限制大小写）、数字和“-”组成。
		 */
		if(null!=paramsMap.get(Constants.HOST_NAME) ) {
			if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_WINDOWS)) {
				if(paramsMap.get(Constants.HOST_NAME).length()<Constants.MIN_HOSTNAME || 
						paramsMap.get(Constants.HOST_NAME).length()>Constants.MAX_WINDOWS_HOSTNAME ||
						Pattern.matches("\\d+", paramsMap.get(Constants.HOST_NAME)) ||
						!Pattern.matches("(\\w+-?\\w*)+", paramsMap.get(Constants.HOST_NAME))) {
					return commonGenerator.inValid(null, Constants.HOST_NAME);
				}
			}else if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_LINUX)){
				if(paramsMap.get(Constants.HOST_NAME).length()<Constants.MIN_HOSTNAME || 
						paramsMap.get(Constants.HOST_NAME).length()>Constants.MAX_LINUX_HOSTNAME ||
						!Pattern.matches("(\\w+(\\.|-)?\\w*)+", paramsMap.get(Constants.HOST_NAME))) {
					return commonGenerator.inValid(null, Constants.HOST_NAME);
				}
			}else {
				String message = "The specified image is not found OS type";
				return commonGenerator.internalError(message, Constants.DESCRIPTION);
			}
		}
		if(null!=paramsMap.get(Constants.PASSWORD) &&
				!Pattern.matches("(\\w{"+Constants.MIN_PASSWORD+","+Constants.MAX_PASSWORD+"})", paramsMap.get(Constants.PASSWORD))) {
			return commonGenerator.inValid(null, Constants.PASSWORD);
		}
		if(null!=paramsMap.get(Constants.SYSTEM_DISK_CATEGORY) &&
				!paramsMap.get(Constants.SYSTEM_DISK_CATEGORY).equals(Constants.DISK_CLOUD)) {
			return commonGenerator.inValid(null, Constants.SYSTEM_DISK_CATEGORY);
		}
		if(null!=paramsMap.get(Constants.DATA_DISK1_CATEGORY) &&
				!paramsMap.get(Constants.DATA_DISK1_CATEGORY).equals(Constants.DISK_CLOUD)) {
			return commonGenerator.inValid(null, Constants.DATA_DISK1_CATEGORY);
		}
		//指定云主机硬盘大小
		Integer diskSize = 0;
		if(null!=paramsMap.get(Constants.DATA_DISK1_SIZE)) {
			if((!StringUtil.isNumeric(paramsMap.get(Constants.DATA_DISK1_SIZE))) ||
				 Integer.parseInt(paramsMap.get(Constants.DATA_DISK1_SIZE))>Constants.MAX_DATADISK) {
				return commonGenerator.inValid(null, Constants.DATA_DISK1_SIZE);
			}
			diskSize = Integer.valueOf(paramsMap.get(Constants.DATA_DISK1_SIZE));
		}
		Billingrate billingrate = billingrateList.get(0);
		Integer totalDisk = billingrate.getHarddisk() + diskSize;
		//云主机硬盘空间大小至少是镜像所在的最小空间
		if(totalDisk<vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getSize()) {
			String message = "The specified image does not support the specified instance type.";
			return commonGenerator.operationDeny(message, Constants.INSTANCE_TYPE);
		}
		if(null!=paramsMap.get(Constants.DATA_DISK1_DELETE_WITHINSTANCE) &&
				!paramsMap.get(Constants.DATA_DISK1_DELETE_WITHINSTANCE).equals("true")) {
			return commonGenerator.inValid(null, Constants.DATA_DISK1_DELETE_WITHINSTANCE);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check create instance params successfully");
		return resultMap;
	}

	public Map<String, String> checkStartParams(Map<String, String> paramsMap)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check start instance params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Start instance params successfully!");
		return resultMap;
	}

	public Map<String, String> checkStopParams(Map<String, String> paramsMap)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check stop instance params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) + ", ForceStop=" + paramsMap.get(Constants.FORCE_STOP));
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		//是否强制关机
		if(null!=paramsMap.get(Constants.FORCE_STOP)) {
			String forceStop = paramsMap.get(Constants.FORCE_STOP);
			if(null==forceStop || forceStop.equals("") || 
					( !forceStop.equals("true") && !forceStop.equals("false")) ) {
				return commonGenerator.inValid(null, Constants.FORCE_STOP);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Stop instance params successfully!");
		return resultMap;
	}

	public Map<String, String> checkRebootParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check reboot instance params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	public Map<String, String> checkDescribeInstanceStatusParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check params, RegionId=" + 
				paramsMap.get(Constants.REGION_ID) );
		if(null==paramsMap.get(Constants.REGION_ID)) {
			return commonGenerator.missing(null, Constants.REGION_ID);
		}else {
			if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
					null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
				return commonGenerator.inValid(null, Constants.REGION_ID);
			}
		}
		if(null!=paramsMap.get(Constants.ZONE_ID)) {
			if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
					null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
				return commonGenerator.inValid(null, Constants.ZONE_ID);
			}
		}
		if(null!=paramsMap.get(Constants.PAGE_NUMBER) &&
				!Pattern.matches("(\\d+", paramsMap.get(Constants.PAGE_NUMBER)) &&
				Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER)) < 1) {
			return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
		}
		if(null!=paramsMap.get(Constants.PAGE_SIZE) &&
				!Pattern.matches("(\\d+", paramsMap.get(Constants.PAGE_SIZE)) &&
				Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)) < Constants.DEFAULT_PAGE_SIZE &&
				Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)) > Constants.MAX_PAGE_SIZE) {
			return commonGenerator.inValid(null, Constants.PAGE_SIZE);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkDeleteInstanceParams(
			Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check delete instance params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) + ", ForceDelete=" + paramsMap.get(Constants.FORCE_DELETE));
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		//是否强制删除
		if(null!=paramsMap.get(Constants.FORCE_DELETE)) {
			String forceDelete = paramsMap.get(Constants.FORCE_DELETE);
			if(null==forceDelete || forceDelete.equals("") || 
					( !forceDelete.equals("true") && !forceDelete.equals("false")) ) {
				return commonGenerator.inValid(null, Constants.FORCE_DELETE);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Delete instance params successfully!");
		return resultMap;
	}
	@Override
	public Map<String, String> checkResetInstanceParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check reset instance params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	@Override
	public Map<String, String> checkIsoDetachParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check iso detach params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkIsoBootParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check iso boot params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		if(null==paramsMap.get(Constants.IMAGE_ID)) {
			return commonGenerator.missing(null, Constants.IMAGE_ID);
		}else {
			if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.IMAGE_ID))) {
				return commonGenerator.inValid(null, Constants.IMAGE_ID);
			}else if( null==vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID))
					|| vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getDiskFormat() != VmImage.VmImageDiskFormatEnum.ISO) {
				return commonGenerator.operationDeny(null, Constants.IMAGE_ID);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	@Override
	public Map<String, String> checkModifyInstanceAttributeParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check modify instance attribute params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		if(null!=paramsMap.get(Constants.INSTANCE_NAME) && 
				!Pattern.matches("((\\w|[\u4e00-\u9fa5])+(\\.|_|-|[\u4e00-\u9fa5])?){"+Constants.MIN_NAME+","+
						Constants.MAX_NAME + "}", paramsMap.get(Constants.INSTANCE_NAME))) {
			return commonGenerator.inValid(null, Constants.INSTANCE_NAME);
		}
		if(null!=paramsMap.get(Constants.DESCRIPTION) && 
				!Pattern.matches("(.|[\u4e00-\u9fa5]){"+ Constants.MIN_DESCRIPTION + ","+
						Constants.MAX_DESCRIPTION+"}",paramsMap.get(Constants.DESCRIPTION)) ) {
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		}
		if(null!=paramsMap.get(Constants.PASSWORD) &&
				!Pattern.matches("(\\w{"+Constants.MIN_PASSWORD+","+Constants.MAX_PASSWORD+"})", paramsMap.get(Constants.PASSWORD))) {
			return commonGenerator.inValid(null, Constants.PASSWORD);
		}
		/**
		 * HostName表示云主机的主机名，最少 2 字符，“.”和“-”是不能作为 hostname的首尾字符，且不能连续使用。
		 * Windows 平台最长为 15 字符，允许字母（不限制大小写）、数字和“-”组成，不支持点号（"."），不能全是数字。
		 * 其他（Linux 等）平台最长为 30 字符，允许支持多个点号，点之间为一段，每段允许字母（不限制大小写）、数字和“-”组成。
		 */
		if(null!=paramsMap.get(Constants.HOST_NAME) ) {
			if(paramsMap.get(Constants.HOST_NAME).length()<Constants.MIN_HOSTNAME || 
					paramsMap.get(Constants.HOST_NAME).length()>Constants.MAX_WINDOWS_HOSTNAME ||
					Pattern.matches("\\d+", paramsMap.get(Constants.HOST_NAME)) ||
					!Pattern.matches("(\\w+-?\\w*)+", paramsMap.get(Constants.HOST_NAME))) {
				return commonGenerator.inValid(null, Constants.HOST_NAME);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check modify instance attribute params successfully");
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyInstanceResourceParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check modify instance resource params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		Integer groupId = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID)).getGroupId();
		//获取当前用户所在群组的所有集群
		String[] clusters = vmGroupProxy.getById(groupId).getAvailableClusters().split(",");
		List<Billingrate> billingrateList = new ArrayList<Billingrate>();
		for(int i = 0 ; i<clusters.length ; i ++)
			billingrateList.addAll(BillingrateManager.findBillingRateList(Integer.valueOf(clusters[i])));
		if(null!=paramsMap.get(Constants.CPU_NUM) &&
				!(Pattern.matches("\\d+", paramsMap.get(Constants.CPU_NUM)) &&
				Integer.parseInt(paramsMap.get(Constants.CPU_NUM)) <= Constants.MAX_CPUNUM &&
				InstanceChecker.checkClustterAndResource(billingrateList, com.appcloud.vm.fe.common.Constants.BILLING_CPU_ABBR, Integer.parseInt(paramsMap.get(Constants.CPU_NUM)))
				)) {
			return commonGenerator.inValid(null, Constants.CPU_NUM);
		}
		if(null!=paramsMap.get(Constants.RAM_SIZE) &&
				!(Pattern.matches("\\d+", paramsMap.get(Constants.RAM_SIZE)) &&
				Integer.parseInt(paramsMap.get(Constants.RAM_SIZE)) <= Constants.MAX_RAMSIZE &&
				InstanceChecker.checkClustterAndResource(billingrateList, com.appcloud.vm.fe.common.Constants.BILLING_MEM_ABBR, Integer.parseInt(paramsMap.get(Constants.RAM_SIZE)))
				)) {
			return commonGenerator.inValid(null, Constants.RAM_SIZE);
		}
		if(null!=paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT) &&
				!(Pattern.matches("\\d+", paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)) &&
				Integer.parseInt(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)) <= Constants.MAX_BANDWIDTHOUT &&
				InstanceChecker.checkClustterAndResource(billingrateList, com.appcloud.vm.fe.common.Constants.BILLING_BW_ABBR, Integer.parseInt(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)))
				)) {
			return commonGenerator.inValid(null, Constants.INTERNET_MAX_BANDWIDTH_OUT);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check modify instance resource params successfully");
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyInstanceSecurityGroupParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check modify instance security group params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		if(null==paramsMap.get(Constants.SECURITY_GROUP_ID)) {
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.SECURITY_GROUP_ID)) ){
				return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		}else if(null==vmSecurityGroupProxy.getByUuid(paramsMap.get(Constants.SECURITY_GROUP_ID), false)){
				return commonGenerator.notFound(null, Constants.SECURITY_GROUP_ID);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check modify instance security group params successfully");
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyInstanceChargeTypeParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check modify instance charge type params, InstanceId=" + 
				paramsMap.get(Constants.INSTANCE_ID) );
		if(null==paramsMap.get(Constants.INSTANCE_ID)) {
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		}else if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.INSTANCE_ID)) ){
				return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		}else if(null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), 
				false, false, false, false, false, false, false, false)){
				return commonGenerator.notFound(null, Constants.INSTANCE_ID);
		}
		//判断云主机按需付费方式的合法性
		if(null!=paramsMap.get(Constants.INSTANCE_CHARGE_TYPE) ) {
			String chargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
			if(null==chargeType || 
					(!chargeType.equals(Constants.PAY_BY_HOUR) && !chargeType.equals(Constants.PAY_BY_DAY) &&
					 !chargeType.equals(Constants.PAY_BY_MONTH) && !chargeType.equals(Constants.PAY_BY_YEAR)) ) {
				return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_TYPE);
			}
		}
		//判断云主机按需付费时长的合法性
		if(null!=paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH) ) {
			if(!Pattern.matches("\\d+", paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH))) {
				return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
			}
			Integer chargeLen = Integer.parseInt(paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH));
			String chargeType = paramsMap.get(Constants.INSTANCE_CHARGE_TYPE);
			//默认是按小时计费
			if( null==chargeType || chargeType.equals(Constants.PAY_BY_HOUR) ){
				if(chargeLen > Constants.MAX_PAY_BY_HOUR) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_DAY)) {
				if(chargeLen > Constants.MAX_PAY_BY_DAY) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_MONTH)) {
				if(chargeLen > Constants.MAX_PAY_BY_MONTH) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}else if(chargeType.equals(Constants.PAY_BY_YEAR)) {
				if(chargeLen > Constants.MAX_PAY_BY_MONTH) {
					return commonGenerator.inValid(null, Constants.INSTANCE_CHARGE_LENGTH);
				}
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check modify instance charge type params successfully");
		return resultMap;
	}
}
