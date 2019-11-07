package appcloud.admin.action.vm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Server;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;

import com.appcloud.vm.fe.common.Constants;
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
	private static final String NOTCHANGED = "-1";
	
	private String serverId;
	private String serverName;
	private Integer oldMaxBandNum;
	private Integer oldPriBandNum;
	private Integer mbandNum;
	private Integer pbandNum;
	private String vmUserId;	//虚拟机的用户id 
	private Server server = null;
	private String description;
	private Integer oldHdNum;
	private Integer oldCpuNum;
	private Integer oldMemNum;
	private Integer oldBandNum;
	private Integer cpuNum;
	private Integer memNum;
	private Integer bandNum;
	private String result ;
	private int times = 0; //暂时无用
	private String kind;//操作类型  netWork：修改带宽
	private String tenantId;
	private boolean mbandRelease;
	private boolean pbandReleasse;

	//修改虚拟机的硬件配置
	public String execute() throws Exception{
			//创建flavif(kind.equals("netWork")){
		tenantId = this.getUserId();
		logger.info("发送修改网络到后台。。。"+vmUserId+","+tenantId+","+serverId);
		logger.info(oldMaxBandNum+"->"+mbandNum+" "+oldPriBandNum+"->"+pbandNum);
		logger.info("mbandRelease="+mbandRelease+" pbandReleasse="+pbandReleasse);
		server = serverClient.get(vmUserId, serverId);
		logger.info("get server"+server.name);
		if(oldMaxBandNum != mbandNum){
			logger.info("管理员修改公网带宽"+oldMaxBandNum+"->"+mbandNum);
			if(server.metadata.containsKey("oldMaxBandNum")){
				String omb = server.metadata.get("oldMaxBandNum");
				logger.info("omb == "+omb);
				if(!omb.equals("-1")){//之前记录过就公网带宽，这次已经不是第一次修改了，这次不记录旧带宽了
					serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth", mbandNum.toString(),mbandRelease);
				}else{
					serverClient.updateMetadataItem(tenantId,serverId, "oldMaxBandNum", oldMaxBandNum.toString(),mbandRelease);
					serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth", mbandNum.toString(),mbandRelease);
				}
			}else{
				logger.info("omb == not found");
				serverClient.updateMetadataItem(tenantId,serverId, "oldMaxBandNum", oldMaxBandNum.toString(),mbandRelease);
				serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth", mbandNum.toString(),mbandRelease);
			}
		
		}
		if(oldPriBandNum != pbandNum){
			logger.info("管理员修改私网带宽"+oldPriBandNum+"->"+pbandNum);
			if(server.metadata.containsKey("oldPriBandNum")){
				String opb = server.metadata.get("oldPriBandNum");
				logger.info("opb == "+opb);
				if(!opb.equals("-1")){//之前记录过就私网带宽，这次已经不是第一次修改了，这次不记录旧带宽了
					serverClient.updateMetadataItem(tenantId,serverId, "priBandwidth", pbandNum.toString(),pbandReleasse);
				}else{
					serverClient.updateMetadataItem(tenantId,serverId, "oldPriBandNum", oldPriBandNum.toString(),pbandReleasse);
					serverClient.updateMetadataItem(tenantId,serverId, "priBandwidth", pbandNum.toString(),pbandReleasse);
				}
			}else{
				logger.info("opb == not found");
				serverClient.updateMetadataItem(tenantId,serverId, "oldPriBandNum", oldPriBandNum.toString(),pbandReleasse);
				serverClient.updateMetadataItem(tenantId,serverId, "priBandwidth", pbandNum.toString(),pbandReleasse);
			}
		}
		logger.info(oldMaxBandNum+"->"+mbandNum+" "+oldPriBandNum+"->"+pbandNum);
		
		//serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth", bandNum.toString());
		logger.info("修改虚拟机带宽配置成功");
		/*if(kind.equals("flavor")){
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
		}*/
		
        result = "success";
		return SUCCESS;
	}
	
	

	public String recoverNetwork(){
		
		tenantId = this.getUserId();
		logger.info("发送还原网络配置到后台。。。"+vmUserId+","+tenantId+","+serverId);
		
		server = serverClient.get(vmUserId, serverId);
		
		if(server.metadata.containsKey("oldPriBandNum")){
			String opb = server.metadata.get("oldPriBandNum");
			logger.info(" in recover opb == "+opb);
			if(!opb.equals("-1")){//恢复带宽，并将旧带宽置为 -1
				serverClient.updateMetadataItem(tenantId,serverId, "oldPriBandNum", NOTCHANGED,true);
				serverClient.updateMetadataItem(tenantId,serverId, "priBandwidth", opb,true);
				
				
			}else{
				logger.error("oldPriBandNum is -1 !!!!");
			}
		}else{
			logger.error("oldPriBandNum is not found !!!!");
		}
		
		
		if(server.metadata.containsKey("oldMaxBandNum")){
			String omb = server.metadata.get("oldMaxBandNum");
			logger.info(" in recover omb == "+omb);
			if(!omb.equals("-1")){//恢复带宽，并将旧带宽置为 -1
				serverClient.updateMetadataItem(tenantId,serverId, "oldMaxBandNum", NOTCHANGED,true);
				serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth",omb,true);
				
				
			}else{
				logger.error("oldMaxBandNum is -1 !!!!");
				
			}
		}else{
			logger.error("oldMaxBandNum is not found !!!!");
		}
		
		
		//serverClient.updateMetadataItem(tenantId,serverId, "maxBandwidth", bandNum.toString());
		logger.info("还原带宽成功");
		
		result = "success";
		//logger.info("result is  "+result);
		return SUCCESS;
	}
	public static void main(String[] args) {
		int a = 1;
		String s;
		s = a+"";
		System.out.println(s);
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
	public Integer getMbandNum() {
		return mbandNum;
	}

	public void setMbandNum(Integer mbandNum) {
		this.mbandNum = mbandNum;
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getVmUserId() {
		return vmUserId;
	}

	public void setVmUserId(String vmUserId) {
		this.vmUserId = vmUserId;
	}

	public ServerClient getServerClient() {
		return serverClient;
	}

	public void setServerClient(ServerClient serverClient) {
		this.serverClient = serverClient;
	}

	public Integer getOldMaxBandNum() {
		return oldMaxBandNum;
	}

	public void setOldMaxBandNum(Integer oldMaxBandNum) {
		this.oldMaxBandNum = oldMaxBandNum;
	}

	public Integer getOldPriBandNum() {
		return oldPriBandNum;
	}

	public void setOldPriBandNum(Integer oldPriBandNum) {
		this.oldPriBandNum = oldPriBandNum;
	}

	public Integer getMbandNm() {
		return mbandNum;
	}

	public void setMbandNm(Integer mbandNm) {
		this.mbandNum = mbandNm;
	}

	public Integer getPbandNum() {
		return pbandNum;
	}

	public void setPbandNum(Integer pbandNum) {
		this.pbandNum = pbandNum;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}



	public boolean isMbandRelease() {
		return mbandRelease;
	}



	public void setMbandRelease(boolean mbandRelease) {
		this.mbandRelease = mbandRelease;
	}



	public boolean isPbandReleasse() {
		return pbandReleasse;
	}



	public void setPbandReleasse(boolean pbandReleasse) {
		this.pbandReleasse = pbandReleasse;
	}



}
