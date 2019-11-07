package appcloud.admin.action.vm;

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
import java.util.Random;
import java.util.UUID;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.Network;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;
import appcloud.api.constant.ServerMetadata;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.util.ClientFactory;

public class ExportVmExcelAction extends BaseAction{
	/**
	 * 导出云主机列表
	 * @author hgm
	 */
	InputStream excelStream;
	private String fileName;    //文件名
	private static final long serialVersionUID = 1L;
	private static final String tenantId = "admin";
	private Logger logger = Logger.getLogger(ExportVmExcelAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcUserClient userClient = ClientFactory.getAcUserClient();
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	private int page = 1;
    private int total = 0;
	
    private String email = "";
    private String name = "";
    private String status = "";
    private String uuid = "";
    private String zoneId;
    private String clusterId;
    private String hostId = "";
    private String ip = "";
    private String starttime = "";
    private String endtime = "";
    
	private List<Server> servers = new ArrayList<Server>();
	private HashMap<String, String> hostIdNameMap = new HashMap<String, String>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private StringBuffer searchCondition = new StringBuffer("");
	
	public String execute() {
		// 准备条件
		Date startDate = null;//构造日期
		Date endDate = null;
		DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer userIds = new StringBuffer("");
		if(email != null && !email.equals("")) {
			searchCondition.append("用户邮箱:"+email+", ");
			List<AcUser> users = userClient.search(null, email, null, null, null, null);
			if(users != null && users.size() != 0) {
				for(AcUser tmp : users){
					userIds.append(tmp.userId+",");
				}
			} else {
				return "excel";
			}
		}
		try {
			if(!starttime.equalsIgnoreCase("")){
				startDate = time.parse(starttime); 
				logger.info(startDate.toString());
			}
			if(!endtime.equalsIgnoreCase("")){
				endDate = time.parse(endtime);
				logger.info(endDate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();  
			logger.warn("获取时间失败");
		}
		
		Integer zid = null;
		Integer cid = null;
		
		if(zoneId!=null && !zoneId.equalsIgnoreCase("")){
			zid = Integer.parseInt(zoneId);
			searchCondition.append("数据中心:"+zoneClient.getZoneById(Integer.parseInt(zoneId)).name+", ");
		}
		if(clusterId!=null && !clusterId.equalsIgnoreCase("")){
			cid = Integer.parseInt(clusterId);
			searchCondition.append("集群:"+zoneClient.get(cid).name+", ");
		}
		search(searchCondition, name, "主机名称");
		search(searchCondition, uuid, "主机标识");
		search(searchCondition, status, "主机状态");
		if (hostId != null && !hostId.equals("")) {
			searchCondition.append("节点："+hostClient.get(hostId).ip+", ");
		}
		search(searchCondition, ip, "主机IP");
		search(searchCondition, starttime, "起始时间");
		search(searchCondition, endtime, "结束时间");
		total = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid, name, status, 
				zid, cid, hostId, ip, startDate, endDate).toString());
		logger.info(total);
		servers = serverClient.searchByProperties(tenantId, userIds.toString(), uuid, name, status,
				zid, cid, hostId, ip, startDate, endDate, page, total);
		if(servers==null && servers.isEmpty()){
			return "excel";
		}
		List<AcHost> hosts = hostClient.getAcHosts();
		for(AcHost acHost: hosts){
			hostIdNameMap.put(acHost.id, acHost.ip);
		}
		for (Iterator it = servers.iterator(); it.hasNext();){
			Server server = (Server) it.next();
			//状态改为中文
			String status = server.status;
			if(!statusMap.containsKey(status)){
				if(status.equalsIgnoreCase("active")){
					statusMap.put(status, "运行中");
				}else if(status.equalsIgnoreCase("stopped")){
					statusMap.put(status, "已关机");
				}else if(status.equalsIgnoreCase("building")){
					statusMap.put(status, "创建中");
				}else if(status.equalsIgnoreCase("deleted")){
					statusMap.put(status, "已删除");
				}else if(status.equalsIgnoreCase("error")){
					statusMap.put(status, "故障");
				}else if(status.equalsIgnoreCase("rebuilding")){
					statusMap.put(status, "ISO装机中");
				}else if(status.equalsIgnoreCase("suspended")){
					statusMap.put(status, "已挂起");
				}else{
					statusMap.put(status, "未知");
				}
			}
		}
		//获取输出文件流
		/*ExportVmExcelService es = new ExportVmExcelServiceImplement();
		excelStream = es.getExcelInputStream( servers, hostIdNameMap, statusMap, uuid);*/
		excelStream = getExcelInputStream( servers, hostIdNameMap, statusMap, uuid);
		//获取文件名
		Date date = new Date();
		String file = "Iaas云主机报表"+new SimpleDateFormat("yyyy-MM-dd").format(date)+".xls";
		try {
			this.setFileName(new String(file.getBytes(), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(searchCondition.length() == 0) {
			searchCondition.append("导出全部");
		}
		
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "导出云主机", "查询条件："+searchCondition, "ExportVmExcelAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		
		return "excel";
	}
	private void search(StringBuffer sb, String name, String des) {
		if (name != null && !name.equals("")) {
			sb.append(des+":"+name+", ");
		}
	}
	
	public InputStream getExcelInputStream( List<Server> servers, HashMap<String, String> hostIdNameMap,
			HashMap<String, String> statusMap, String uuid) {
		//将OutputStream转化为InputStream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		putDataOnOutputStream(out, servers, hostIdNameMap, statusMap, uuid);
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void putDataOnOutputStream(OutputStream os, List<Server> servers, HashMap<String, String> hostIdNameMap,
			HashMap<String, String> statusMap, String uuid) {
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			addCellOfServer(sheet, servers, hostIdNameMap, statusMap, uuid);
			
			logger.info("云主机列表导出成功");
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addCellOfServer(WritableSheet sheet, List<Server> servers, HashMap<String, String> hostIdNameMap,
							HashMap<String, String> statusMap, String uuid){
		//填充Label(列，行，值)
		String str_titles = "用户邮箱;主机名称;主机状态;所在集群;所在节点;私网IP;公网IP;主机配置;创建时间;停止时间;计费方式;价格";
    	String[] titles = str_titles.split(";");
		for (int index=0; index<titles.length; index++){
			try {
				sheet.addCell(new Label(index, 0, titles[index]));
			}catch(WriteException we){
				we.printStackTrace();
			}
		}
		Integer row = 1;  //行
		for (Iterator it = servers.iterator(); it.hasNext();){
			Integer col = 0;  //列
			Server server_instance = (Server) it.next();
			if (null != server_instance){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(server_instance.created); 
				String item="";
				String str_payType="";
				Integer payType = 1;
				VmHdEndtime vmEndtime = new VmHdEndtimeManager().getVmEndtimeByUuid(server_instance.id);
				Date endTime = null;
				if(vmEndtime!=null){
					item=vmEndtime.getType().toLowerCase();  //计费规则
					str_payType = vmEndtime.getPayType();    //计费方式
					payType = new VmHdEndtimeManager().convertToPaymentType( str_payType );
					endTime = vmEndtime.getEndTime();
				}
				Integer clusterId = server_instance.aggregate;  //获取所在集群
				//获取公网和私网IP
				String privateIp = "";
				String publicIp = "";
				for(Network address: server_instance.addresses) {
					for(int i=0; i<address.ips.size();i++) {
						Integer pri_ip = 0;
						Integer pub_ip = 0;
						if(pri_ip>0){
							privateIp = privateIp + ";";
						}
						if(pub_ip>0){
							publicIp = publicIp + ";";
						}
						if (address.id.equals("private")) {
							privateIp = privateIp + address.ips.get(pri_ip).addr;
							pri_ip++;
						} else {
							publicIp = publicIp + address.ips.get(pub_ip).addr;
							pub_ip++;
						}
					}
				}
				//获取硬件设备的数目
				Integer cpu = -1;
				Integer memory =-1 ;
				Integer harddisk =-1 ;
				Flavor flavor = flavorClient.get(Constants.ADMIN_TENANT_ID, server_instance.flavor.id);
				if(flavor != null){
					cpu = flavor.vcpus;
					memory = flavor.ram;
					harddisk = flavor.disk;
				}
				Integer bandwidth = -1;
				if((server_instance.metadata) != null && ((server_instance.metadata).get(ServerMetadata.MAX_BANDWIDTH)) != null){
					bandwidth = Integer.parseInt( (server_instance.metadata).get(ServerMetadata.MAX_BANDWIDTH) );  //带宽
				}
				//用户ID
				Integer uid = -1;
				if(server_instance.tenantId!=null && server_instance.tenantId!=""){
					uid = Integer.parseInt(server_instance.tenantId);  
				}
		        //向excel表中填写数据
				try {
					sheet.addCell(new Label(col++, row, userClient.get(uid+"").getUserEmail()));
					sheet.addCell(new Label(col++, row, server_instance.name));
					sheet.addCell(new Label(col++, row, statusMap.get(server_instance.status)));
					sheet.addCell(new Label(col++, row, clusterId+""));
					sheet.addCell(new Label(col++, row, hostIdNameMap.get(server_instance.hostId)));
					sheet.addCell(new Label(col++, row, privateIp));
					sheet.addCell(new Label(col++, row, publicIp));
					sheet.addCell(new Label(col++, row, cpu+"个CPU，" + memory+"G内存，" +	harddisk+"G硬盘，" + bandwidth+"M带宽"));
					sheet.addCell(new Label(col++, row, dateStr));
					sheet.addCell(new Label(col++, row, endTime+""));
					sheet.addCell(new Label(col++, row, str_payType));
			      //获取单价（单位为分，所以要除以100）
					if(item!=""){
						double vmPrice;
						try {
							vmPrice = BillingAPI.getPrice(item, clusterId, cpu, memory, harddisk, bandwidth, uid, payType, 1.0) / 100.00;
						}catch (Exception e){
							vmPrice = 0.0;
						}
						sheet.addCell(new Label(col++, row, vmPrice+""));
					}else{
						sheet.addCell(new Label(col++, row, ""));
					}
				}catch (WriteException ex) {
					ex.printStackTrace();
				}
			}
			row++;
		}
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}


	public HashMap<String, String> getHostIdNameMap() {
		return hostIdNameMap;
	}

	public void setHostIdNameMap(HashMap<String, String> hostIdNameMap) {
		this.hostIdNameMap = hostIdNameMap;
	}

	public HashMap<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> statusMap) {
		this.statusMap = statusMap;
	}

}
