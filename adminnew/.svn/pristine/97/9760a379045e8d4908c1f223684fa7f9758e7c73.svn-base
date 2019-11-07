package appcloud.admin.action.user;

import java.math.BigDecimal;
import java.util.*;

import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;

import com.appcloud.vm.fe.billing.Balance;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.util.ClientFactory;
import com.free4lab.utils.account.AccountUtil;

public class UserManageAction extends BaseAction{
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(UserManageAction.class);
	
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	private List<AcUser> acUsers = new ArrayList<AcUser>();
	private List<String> keys = new ArrayList<>();
	private VmUserProxy vmproxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class, appcloud.common.constant
			.ConnectionConfigs.DB_PROXY_ADDRESS);
	private HashMap<Integer, Long> countMap = new HashMap<Integer, Long>();
	private HashMap<Integer, Long> countVolumeMap = new HashMap<Integer, Long>();
	private HashMap<Integer, String> balanceMap = new HashMap<Integer, String>();
	private HashMap<Integer, String> enterpriseMap = new HashMap<Integer, String>();
	

	private List<Balance> balances = new ArrayList<Balance>();
	
	private String email = "";
	private String group = "";
	private String enterpriseName = "";
	private int page = 1;

	private final static int PAGESIZE = 10;
    private int total = 0;
	private int lastpage = 1;
	
	private StringBuffer searchContion = new StringBuffer("");

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public String execute() throws Exception{
		vmproxy.findAll();
		String emailStr = null;
		Integer groupId = null;
		if(!email.equals("") && email != null) {
			emailStr = email;
			searchContion.append("用户邮箱："+email+", ");
		} 
		if(!group.equals("") && group != null) {
			groupId = Integer.valueOf(group);
			searchContion.append("所在群组："+acGroupClient.get(groupId).name+", ");
		} 
		String enterpriseIds = null;
		if(!enterpriseName.equals("") && enterpriseName != null) {
			List<Enterprise> enterprises = enterpriseClient.getByProperties(null, null, null, enterpriseName, 
					null, null, null, null, null, null, null, 0, 0);
			if(enterprises != null && enterprises.size() > 0) {
				enterpriseIds = "";
				for(Enterprise enterprise : enterprises) {
					enterpriseIds  += enterprise.id + ",";
				}
			} else {
				total = 0;
				return SUCCESS;
			}
		}
		
		acUsers = acUserClient.searchByEnterprise(groupId, emailStr, null, enterpriseIds, page, PAGESIZE);
		total = acUserClient.countByEnterprises(groupId, emailStr, null, enterpriseIds).intValue();

		if(acUsers != null) {
			if(total % PAGESIZE == 0){
        		lastpage = total / PAGESIZE;
        	}else{
        		lastpage = total / PAGESIZE + 1;
        	}
			
			long serverCount = 0;
			long volumeCount = 0;
			int balance = 0;
			for(AcUser acUser : acUsers) {
				acUser.appkeyId = vmproxy.getByUserId(new Integer(acUser.userId)).getAppKeyId();
				acUser.appkeySecret = vmproxy.getByUserId(new Integer(acUser.userId)).getAppKeySecret();
				serverCount = serverClient.countByProperties(null, acUser.userId, null, null, null, null, null, null, null, null, null);
				countMap.put(new Integer(acUser.userId), serverCount);
				volumeCount = volumeClient.countByProperties(null, null, acUser.userId, null, VmVolumeUsageTypeEnum.NETWORK.toString(), null, null, false, null, null, null, null);
				countVolumeMap.put(new Integer(acUser.userId), volumeCount);
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("uid", String.valueOf(acUser.userId));
				params.put("source", Constants.CLIENT_ID);
				String signature = AccountUtil.getSignature(params, Constants.CLIENT_SECRET_KEY);
				Integer temp = Integer.valueOf(acUser.userId);
				balance = BillingAPI.balance(temp, null, signature, Constants.CLIENT_ID);
				
				BigDecimal bd = new BigDecimal(String.valueOf((double)(balance/100.0)));
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
				String balanceStr = bd.toString();
				balanceMap.put(new Integer(acUser.userId), balanceStr);
				if(acUser.enterpriseId != null) {
					Integer enterpriseId = acUser.enterpriseId;
					Enterprise enterprise = enterpriseClient.get(enterpriseId);
					enterpriseMap.put(new Integer(acUser.userId), enterprise.name);
				}
			}
			logger.info(countMap);
			logger.info(countVolumeMap);
		}
		if (searchContion.length() == 0)
			searchContion.append("查询全部");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "用户查询", "查找条件:"+searchContion, "UserManageAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public List<Balance> getBalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public HashMap<Integer, Long> getCountMap() {
		return countMap;
	}

	public void setCountMap(HashMap<Integer, Long> countMap) {
		this.countMap = countMap;
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

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
	
	public List<AcUser> getAcUsers() {
		return acUsers;
	}

	public void setAcUsers(List<AcUser> acUsers) {
		this.acUsers = acUsers;
	}
	public HashMap<Integer, Long> getCountVolumeMap() {
		return countVolumeMap;
	}

	public void setCountVolumeMap(HashMap<Integer, Long> countVolumeMap) {
		this.countVolumeMap = countVolumeMap;
	}
	public HashMap<Integer, String> getBalanceMap() {
		return balanceMap;
	}

	public void setBalanceMap(HashMap<Integer, String> balanceMap) {
		this.balanceMap = balanceMap;
	}
	
	
	public HashMap<Integer, String> getEnterpriseMap() {
		return enterpriseMap;
	}

	public void setEnterpriseMap(HashMap<Integer, String> enterpriseMap) {
		this.enterpriseMap = enterpriseMap;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
