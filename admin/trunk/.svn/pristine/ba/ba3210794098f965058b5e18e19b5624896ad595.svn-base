package appcloud.admin.action.hd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.Volume;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.constant.VolumeMetadata;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.users.UsersAPI;
import com.appcloud.vm.fe.util.ClientFactory;

public class ExportHdExcelAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdListAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcUserClient userClient = ClientFactory.getAcUserClient();
	public final static String PAYTYPE_HOUR = "按需";
	public final static String PAYTYPE_DAY = "包日";
	public final static String PAYTYPE_MONTH = "包月";
	public final static String PAYTYPE_YEAR = "包年";
	InputStream excelStream;
	private String fileName;    //文件名
	private static final String tenantId = "admin";
	private static final String usageType = "network";
	private int page = 1;
    private int total = 0;
    private int isEmail = 0;
	
    private String email = "";
    private String name = "";
    private String status = "";
    private String attachstatus = "";
    private String uuid = "";
    private String zoneId ="";
    private String vmuuid = "";
    
	private List<Volume> volumes = new ArrayList<Volume>();
	private HashMap<String, String> volumeIdServerNameMap = new HashMap<String, String>();
	private HashMap<String, String> zoneIdNameMap = new HashMap<String, String>();
	private HashMap<String, String> hostIdNameMap = new HashMap<String, String>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private HashMap<String, Integer> endtimeMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> uidMap = new HashMap<String, Integer>();
	private HashMap<String, String> timeMap = new HashMap<String, String>();
	
	private StringBuffer searchContion = new StringBuffer("");
	
	public String execute() {
		logger.info("进来了");
		Integer uid = null;
		logger.info("email:"+email);
		if(!email.equalsIgnoreCase("")){//根据email查找用户userid
			uid = UsersAPI.getUidByEmail(email);
			searchContion.append("用户邮箱："+email+", ");
			logger.info(uid);
			if(uid.toString().equalsIgnoreCase("-1")){
				isEmail = 1;
				return "excel";
			}
		}
		
		Integer zid = null;
		String userId = null;
		if(!zoneId.equalsIgnoreCase("")){
			zid = Integer.parseInt(zoneId);
			searchContion.append("数据中心："+zoneClient.getZoneById(zid).name+", ");
		}
		if(uid != null){
			userId = uid.toString();
		}
		
		logger.info("uuid:"+uuid+"; userId:"+userId+"; vmuuid:"+vmuuid+"; stautus:"+status+
				"; attachstatus:"+attachstatus+"; name:"+name+"; zid:"+zid);
		searchContion = search(searchContion, name, "硬盘名称");
		searchContion = search(searchContion, uuid, "硬盘标识");
		searchContion = search(searchContion, status, "硬盘状态");
		searchContion = search(searchContion, attachstatus, "挂载状态");
		searchContion = search(searchContion, vmuuid, "虚拟机标识");
		total = Integer.valueOf(volumeClient.countByProperties(tenantId, uuid, 
				userId, vmuuid, usageType, status, attachstatus, false,
				name, zid, null, null).toString());
		
		logger.info("total"+total);
		
		volumes = volumeClient.searchByProperties(tenantId, uuid, 
				userId, vmuuid, usageType, status, attachstatus, false,
				name, zid, null, null, page, total);
		
		if(volumes == null || volumes.size() == 0){
			return "excel";
		}
		
		logger.info("读取用户硬盘列表成功， 硬盘数为："+volumes.size());
		
		List<AcHost> hosts = hostClient.getAcHosts();
		for(AcHost acHost: hosts){
			hostIdNameMap.put(acHost.id, acHost.ip);
		}
		HashMap<String, String> serverIdNameMap = new HashMap<String, String>();
		serverIdNameMap.put(null, "无");
		for (Volume volume : volumes) {
			//查询硬盘所属的数据中心名称
			String zoneId = volume.availabilityZone;
			if (!zoneIdNameMap.containsKey(zoneId)) {
				String zoneName = zoneClient.getZoneById(Integer.valueOf(zoneId)).name;
				zoneIdNameMap.put(zoneId, zoneName);
			}
			/*hostIdNameMap = volume.metadata.get(hosts);*/

			//查询硬盘挂载的虚拟机
			String device = volume.attachments.device;
			String serverName = "无";
			if (!device.contains("null")) {	//硬盘现在是挂载状态
				String serverId = volume.attachments.serverId;
				serverName = serverIdNameMap.get(serverId);
				if (serverName == null) {
					serverName = serverClient.get(userId, serverId).name;
					serverIdNameMap.put(serverId, serverName);
				}
			}
			volumeIdServerNameMap.put(volume.id, serverName);
			
			String volumeStatus = volume.status;
			if(!statusMap.containsKey(volumeStatus)){
				if(volumeStatus.equalsIgnoreCase("available")){
					statusMap.put(volumeStatus, "正常");
				}else if(volumeStatus.equalsIgnoreCase("creating")){
					statusMap.put(volumeStatus, "创建中");
				}else if(volumeStatus.equalsIgnoreCase("defined")){
					statusMap.put(volumeStatus, "格式化");
				}else if(volumeStatus.equalsIgnoreCase("deleted")){
					statusMap.put(volumeStatus, "已删除");
				}else if(volumeStatus.equalsIgnoreCase("deleting")){
					statusMap.put(volumeStatus, "删除中");
				}else if(volumeStatus.equalsIgnoreCase("error")){
					statusMap.put(volumeStatus, "故障");
				}else if(volumeStatus.equalsIgnoreCase("error_deleting")){
					statusMap.put(volumeStatus, "删除错误");
				}else{
					statusMap.put(volumeStatus, "未知错误");
				}
			}
			
			//时间改为string
			Date createTime = volume.createdAt;
			if(!timeMap.containsKey(volume.id)){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(createTime); 
				timeMap.put(volume.id, dateStr);
			}
		}
		
		/*读取硬盘截止时间*/
		List<VmHdEndtime> endtimes = new ArrayList<VmHdEndtime>();
		if(uid != null){
			endtimes = new VmHdEndtimeManager().getHdEndtimeByUserId(uid);
		}else{
			endtimes = new VmHdEndtimeManager().getAllHdEndtime();
		}
		for (VmHdEndtime endtime : endtimes) {
			endtimeMap.put(endtime.getUuid(), endtime.getId());
			uidMap.put(endtime.getUuid(), endtime.getUserId());
		}
		if (searchContion.length() == 0) {
			searchContion.append("导出全部");
		}
		
		//获取输出文件流
		/*ExportHdExcelService es = new ExportHdExcelServiceImplement();*/
		excelStream = getExcelInputStream( volumes, statusMap);
		//获取文件名
		Date date = new Date();
		String file = "Iaas云硬盘报表"+new SimpleDateFormat("yyyy-MM-dd").format(date)+".xls";
		try {
			this.setFileName(new String(file.getBytes(), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("读取硬盘所属虚拟机名称成功");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "导出云硬盘", "查询条件为："+searchContion, "ExportHdExcelAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		
		return "excel";
	}

	private StringBuffer search(StringBuffer sb, String name, String des) {
		if (name != null && !name.equals("")) {
			sb.append(des+":"+name+", ");
		}
		return sb;
	}
	
	public InputStream getExcelInputStream( List<Volume> volumes, HashMap<String, String> statusMap) {
		//将OutputStream转化为InputStream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		putDataOnOutputStream(out, volumes, statusMap);
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void putDataOnOutputStream(OutputStream os, List<Volume> volumes, HashMap<String, String> statusMap) {
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			addCellOfServer(sheet, volumes, statusMap);
			
			logger.info("云硬盘列表导出成功");
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//填写报表内容
	public void addCellOfServer(WritableSheet sheet, List<Volume> volumes, HashMap<String, String> statusMap){
		//填充Label(列，行，值)
		String str_titles = "用户邮箱;硬盘名称;挂载状态;硬盘大小;所在集群;所在节点;创建时间;计费方式;价格";
    	String[] titles = str_titles.split(";");
		for (int index=0; index<titles.length; index++){
			try {
				sheet.addCell(new Label(index, 0, titles[index]));
			}catch(WriteException we){
				we.printStackTrace();
			}
		}
		Integer row = 1;  //行
		for (Iterator it = volumes.iterator(); it.hasNext();){
			Integer col = 0;  //列
			Volume volume = (Volume) it.next();
			Integer clusterId = -1;  //集群
			String hostIp = "";   //所在节点的ip
			String uid = "";  //用户uid
			if(volume.metadata!=null){
				String clusterIdStr = volume.metadata.get(VolumeMetadata.AGGREGATE_ID);
				if(clusterIdStr!=null && clusterIdStr!=""){
					clusterId = Integer.parseInt(clusterIdStr);
				}
				hostIp = hostClient.get(volume.metadata.get("hostUuid")).ip;
				uid = volume.metadata.get("userId");
			}
			//查询硬盘挂载的虚拟机
			String device = volume.attachments.device;
			String serverName = "未挂载";
			if (!device.contains("null")) {	//硬盘现在是挂载状态
				String serverId = volume.attachments.serverId;
				if(serverId!=null && uid!=null){
				serverName = "挂载到" + serverClient.get(uid, serverId).name;
				}
			}
			if (null != volume){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = sdf.format(volume.createdAt); 
				String payType="";   //获取计费方式
				VmHdEndtime vmEndtime = new VmHdEndtimeManager().getHdEndtimeByUuid(volume.id);
				if(vmEndtime!=null){
					payType = vmEndtime.getPayType();
				}

				//获取单价
				String price = "";   //价格
				try {
			    List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_HD_ABBR.toLowerCase(), clusterId, -1, -1, volume.size, -1);
			    for (Billingrate rate : all) {
					if(payType.equalsIgnoreCase(PAYTYPE_DAY)){
						price = price + Double.parseDouble(rate.getDayPrice());
					}else if(payType.equalsIgnoreCase(PAYTYPE_HOUR)){
						price = price + Double.parseDouble(rate.getHourPrice());
					}else if(payType.equalsIgnoreCase(PAYTYPE_MONTH)){
						price = price + Double.parseDouble(rate.getMonthPrice());
					}else price = price + Double.parseDouble(rate.getYearPrice());
				}
				}catch (Exception e){
					price = "0.0";
				}

		        //向excel表中填写数据
				try {
					sheet.addCell(new Label(col++, row, userClient.get(uid).getUserEmail()));
					sheet.addCell(new Label(col++, row, volume.displayName));
					sheet.addCell(new Label(col++, row, serverName));
					sheet.addCell(new Label(col++, row, volume.size+"G"));
					sheet.addCell(new Label(col++, row, clusterId+""));
					sheet.addCell(new Label(col++, row, hostIp));
					sheet.addCell(new Label(col++, row, createTime));
					sheet.addCell(new Label(col++, row, payType));
					sheet.addCell(new Label(col++, row, price));
				}catch (WriteException ex) {
					ex.printStackTrace();
				}
			}
			row++;
		}
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttachstatus() {
		return attachstatus;
	}

	public void setAttachstatus(String attachstatus) {
		this.attachstatus = attachstatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	public HashMap<String, String> getHostIdNameMap() {
		return hostIdNameMap;
	}

	public void setHostIdNameMap(HashMap<String, String> hostIdNameMap) {
		this.hostIdNameMap = hostIdNameMap;
	}

	public String getVmuuid() {
		return vmuuid;
	}

	public void setVmuuid(String vmuuid) {
		this.vmuuid = vmuuid;
	}
	
	public List<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}
	
	public HashMap<String, String> getVolumeIdServerNameMap() {
		return volumeIdServerNameMap;
	}

	public void setVolumeIdServerNameMap(
			HashMap<String, String> volumeIdServerNameMap) {
		this.volumeIdServerNameMap = volumeIdServerNameMap;
	}
	
	public HashMap<String, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

	public void setZoneIdNameMap(HashMap<String, String> zoneIdNameMap) {
		this.zoneIdNameMap = zoneIdNameMap;
	}
	
	public HashMap<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	public HashMap<String, String> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(HashMap<String, String> timeMap) {
		this.timeMap = timeMap;
	}
	
	
	public HashMap<String, Integer> getUidMap() {
		return uidMap;
	}

	public void setUidMap(HashMap<String, Integer> uidMap) {
		this.uidMap = uidMap;
	}
	
	public HashMap<String, Integer> getEndtimeMap() {
		return endtimeMap;
	}

	public void setEndtimeMap(HashMap<String, Integer> endtimeMap) {
		this.endtimeMap = endtimeMap;
	}
}
