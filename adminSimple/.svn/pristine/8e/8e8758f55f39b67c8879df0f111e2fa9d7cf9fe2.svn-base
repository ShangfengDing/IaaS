package appcloud.admin.action.hd;

import aliyun.openapi.client.AliCommonClient;
import aliyun.openapi.client.AliInstanceClient;
import aliyun.openapi.client.AliSnapshotClient;
import aliyun.openapi.client.AliVolumeClient;
import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.client.CommonClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.SnapshotClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.datatype.InstanceTypeItem;
import appcloud.openapi.datatype.SnapshotDetailItem;
import appcloud.openapi.response.DescribeInstanceTypesResponse;
import appcloud.openapi.response.DescribeInstancesResponse;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.openapi.response.SnapshotsDetailReponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.ecs.model.v20140526.DescribeSnapshotsResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSnapshotsResponse.Snapshot;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.DiskDetail;
import com.appcloud.vm.fe.entity.ShotProfile;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class HdDetailAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdDetailAction.class);
	private CommonClient commonClient = OpenClientFactory.getCommonClient();
	private AliCommonClient aliCommonClient = OpenClientFactory.getAliCommonClient();
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
	private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
	private SnapshotClient snapshotClient = OpenClientFactory.getSnapshotClient();
	private AliSnapshotClient aliSnapshotClient = OpenClientFactory.getAliSnapshotClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private TransformAttribute transform = new TransformAttribute();
	private DiskDetail diskDetail;
	private Appkey appkey;
	private String diskId;
	private String provider;
	private String regionId;
	private String userEmail;
	private String zoneId;


	private String appName;
	private String result = "1";//0:參數出錯,1:正確
	private boolean Aliportable;
	
	private List<ShotProfile> snapshotList = new ArrayList<ShotProfile>();
	private List<Integer> hdPrices = new ArrayList<Integer>();//續費，硬盤的四種規格
	private String attachInstance = "";

	private Integer userIds;

	
	public String execute(){
		if(appName==null||appName=="") {
			VmUserProxy vmUserProxy = ConnectionFactory.getVmUserProxy();
			VmZoneProxy vmZoneProxy = ConnectionFactory.getVmZoneProxy();
			//TODO appname link the database

			try {
				List<VmZone> vmZonelist = (List<VmZone>) vmZoneProxy.findAll();
				VmZone vmZone = vmZonelist.get(0);
				zoneId = vmZone.getZoneId();
				regionId = vmZone.getRegionId();
				VmUser vmUser = vmUserProxy.getByUserId(userIds);
				String appKey = vmUser.getAppKeyId();
				String appSecretKey = vmUser.getAppKeySecret();
				userEmail = vmUser.getUserEmail();
				List<Appkey> appkeys = appkeyManager.getAppkeyByUserId(userIds);
				for (Appkey appkey : appkeys) {
					if (appkey.getAppkeyId().equals(appKey)) {
						appName = appkey.getAppname();
						break;
					}
				}
				//TODO
				provider="yunhai";
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		switch (provider.trim()) {
		case Constants.YUN_HAI:
			showYunhaiDiskDetails();
			break;
		case Constants.ALI_YUN:
			showAliyunDiskDetails();
			break;
		case Constants.AMAZON:
			showAmazonDiskDetails();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	public void showYunhaiDiskDetails(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userIds,appName);
		//用diskId獲得制定id的硬盤
		DisksDetailReponse disksDetail = volumeClient.DescribeDisks(regionId.trim(), null, "["+diskId.trim()+"]",
				null, null, null, null, null, null, null, null, null, null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
		//查询快照 
		SnapshotsDetailReponse snapshotsDetail = snapshotClient.DescribeSnapshot(regionId.trim(), zoneId,
				diskId.trim(), null, null, "AVAILABLE", null, null, null, null,null,null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		logger.info(snapshotsDetail.getTotalCount());
		List<SnapshotDetailItem> snapshotDetailItems = snapshotsDetail.getSnapshots();
		for(SnapshotDetailItem snapshot : snapshotDetailItems){
			snapshotList.add(new ShotProfile(snapshot.getSnapshotId(), snapshot.getSnapshotName(),
					new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(snapshot.getCreateTime()), transform.transformSnapshot(snapshot.getStatus())));			
		}
		if(null==disksDetail.getMessage()){
			DiskDetailItem disk = disksDetail.getDisks().get(0);
			String device = null;
			String status = null;//描述挂载（挂载要有主机名）
			String attachStatus = (null == disk.getAttachStatus())?"null":disk.getAttachStatus();	
			logger.info("挂载："+attachStatus);
			attachStatus = attachStatus.toLowerCase();
			switch (attachStatus) {
			case "attached":
				DescribeInstancesResponse instanceResponse = instanceClient.DescribeInstances(regionId.trim(), null,
						"["+disk.getInstanceId()+"]", null, null, null, null, null, null, null, null, null,
						appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
				logger.info(ToStringBuilder.reflectionToString(instanceResponse));
				if (instanceResponse.getInstances().size() > 0) {
					InstanceAttributes instanceAttributes = instanceResponse.getInstances().get(0);
					attachInstance = "("+instanceAttributes.getInstanceName()+")";
					status ="已挂载" + attachInstance;
				} else {
					logger.warn("instanceId:"+disk.getInstanceId()+" not found!");
				}			
				break;
			default:
				status = "未挂载";
				break;
			}
			
			if(disk.getDevice() == null)device = "null";
			else device = disk.getDevice();
			diskDetail = new DiskDetail(diskId.trim(), disk.getDiskName(), regionId.trim(), transform.transformRegion(regionId.trim()), provider.trim(),
					transform.transformProvider(provider.trim()), disk.getDescription(), disk.getAttachStatus(), status, disk.getSize(),
					disk.getDiskCategory(), transform.transformZone(disk.getZoneId()), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(disk.getCreateTime()),
					disk.getDiskType(), device, disk.getInstanceId(), disk.getChargeType(), disk.getExpiredTime(), snapshotList);
			PriceYunhai(appkey, disk.getSize());
		}else {
			result = "0";
		}
		
	}
	
	public void showAliyunDiskDetails(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userIds,appName);
		//用diskId獲得制定id的硬盤
		DescribeDisksResponse disksDetail = aliVolumeClient.DescribeDisks(regionId.trim(), null, "[\""+diskId.trim()+"\"]",
				null, null, null, null, null, null, null, null, null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		//查询快照 
		DescribeSnapshotsResponse snapshotsDetail = aliSnapshotClient.DescribeSnapshot(regionId.trim(), 
				diskId.trim(), null, null, null, null, null, null, null,null,null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		logger.info(snapshotsDetail.getTotalCount());
		List<Snapshot> snapshotDetailItems = snapshotsDetail.getSnapshots();
		for(Snapshot snapshot : snapshotDetailItems){
			snapshotList.add(new ShotProfile(snapshot.getSnapshotId(), snapshot.getSnapshotName(),
					snapshot.getCreationTime(), transform.transformSnapshot(snapshot.getStatus())));
		}

		//if(null==disksDetail.getMessage()){
		if(disksDetail.getDisks().size()>0){
			Disk disk = disksDetail.getDisks().get(0);			
			String device = null;
			String status = null;//描述挂载（挂载要有主机名）
			String attachStatus = (null == disk.getStatus())?"null":disk.getStatus();	
			logger.info("挂载："+attachStatus);
			attachStatus = attachStatus.toLowerCase();
			switch (attachStatus) {
			case "in_use":
				com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse instanceResponse = aliInstanceClient.DescribeInstances(regionId.trim(), null, 
						"[\""+disk.getInstanceId()+"\"]", null, null, null, null, null, null, null, null, null,
						appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
				logger.info(ToStringBuilder.reflectionToString(instanceResponse));
				if (instanceResponse.getInstances().size() > 0) {
					Instance instanceAttributes = instanceResponse.getInstances().get(0);
					attachInstance = "("+instanceAttributes.getInstanceName()+")";
					status ="已挂载" + attachInstance;
				} else {
					logger.warn("instanceId:"+disk.getInstanceId()+" not found!");
				}			
				break;
			default:
				status = "未挂载";
				break;
			}
			
			Aliportable = disk.getPortable();
			if(disk.getDevice() == null || disk.getDevice().equals(""))device = "null";
			else device = disk.getDevice();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
			try {
				diskDetail = new DiskDetail(diskId.trim(), disk.getDiskName(), regionId.trim(), transform.transformRegion(regionId.trim()), provider.trim(),
						transform.transformProvider(provider.trim()), disk.getDescription(), disk.getStatus(), status, disk.getSize(),
						disk.getCategory().getStringValue(), transform.transformZone(disk.getZoneId()), simpleDateFormat.parse(disk.getCreationTime()).toLocaleString(),
						disk.getType().getStringValue(), device, disk.getInstanceId(), disk.getDiskChargeType(), simpleDateFormat2.parse(disk.getExpiredTime()).toLocaleString(), snapshotList);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//PriceAli(appkey, disk.getSize());
		}else {
			result = "0";
		}
	}
	
	public void showAmazonDiskDetails(){}

	public void PriceYunhai(Appkey appkey, Integer diskSize) {
		//获得硬盤价格
		DescribeInstanceTypesResponse diskResponse = commonClient.DescribeDiskTypes(regionId.trim(), zoneId,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
		if (null==diskResponse.getMessage()){
			List<InstanceTypeItem> diskTypeItems = diskResponse.getInstanceTypes().getInstanceTypeItems();
			if (diskTypeItems.size()>0){
				for (InstanceTypeItem diskTypeItem:diskTypeItems){
					if (diskSize==diskTypeItem.getHardDisk()){
						hdPrices.add(diskTypeItem.getHourprice());
						hdPrices.add(diskTypeItem.getDayPrice());
						hdPrices.add(diskTypeItem.getMonthPrice());
						hdPrices.add(diskTypeItem.getYearPrice());
					}else{
						hdPrices.add(0);
					}
				}
			}else{
				result = "0";
				logger.warn("disk:为什么获取是空");
			}
		}else{
			result = "0";
		}
	}
	
	public void PriceAli(Appkey appkey, Integer diskSize) {
		
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAttachInstance() {
		return attachInstance;
	}

	public void setAttachInstance(String attachInstance) {
		this.attachInstance = attachInstance;
	}

	public DiskDetail getDiskDetail() {
		return diskDetail;
	}

	public void setDiskDetail(DiskDetail diskDetail) {
		this.diskDetail = diskDetail;
	}

	public List<Integer> getHdPrices() {
		return hdPrices;
	}

	public void setHdPrices(List<Integer> hdPrices) {
		this.hdPrices = hdPrices;
	}

	public boolean isAliportable() {
		return Aliportable;
	}

	public void setAliportable(boolean aliportable) {
		Aliportable = aliportable;
	}


	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getUserIds() {
		return userIds;
	}

	public void setUserIds(Integer userIds) {
		this.userIds = userIds;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
