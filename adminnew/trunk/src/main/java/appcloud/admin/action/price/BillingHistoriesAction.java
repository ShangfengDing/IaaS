package appcloud.admin.action.price;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.BillingHistory;
import com.appcloud.vm.fe.billing.HistoryByPage;
import com.appcloud.vm.fe.users.UsersAPI;

import com.free4lab.utils.account.AccountUtil;
import appcloud.admin.common.Constants;

public class BillingHistoriesAction extends BaseAction {
	/**
	 * 获取用户账户数据
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(BillingHistoriesAction.class);
	
	private String email;
	private String begintime;
	private String endtime;
	private String ptype;
	private String billing_type;
	private int page;
	 
	String balance = "";
	private int uid = -1;
	private int total = 0;
	private int lastpage = 1;
	private static final int PAGE_SIZE = 10;
	private HashMap<Integer, String> amountMap = new HashMap<Integer, String>();
	private List<BillingHistory> billingHistories = new ArrayList<BillingHistory>();
	private int isEmail = 0;
	
	public String execute() throws Exception{
		if(!email.equalsIgnoreCase("")){//根据email查找用户userid
			uid = UsersAPI.getUidByEmail(email);
			logger.info(uid);
			if(uid == -1 || uid < 0){
				isEmail = 1;
				return SUCCESS;
			}
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", String.valueOf(uid));
		params.put("source", Constants.CLIENT_ID);
		Integer oldBalance = BillingAPI.balance(uid ,null, AccountUtil.getSignature(params, Constants.CLIENT_SECRET_KEY), Constants.CLIENT_ID);
		BigDecimal bd1 = new BigDecimal(String.valueOf((double)(oldBalance/100.0)));
		bd1 = bd1.setScale(2,BigDecimal.ROUND_HALF_UP);
		balance = bd1.toString();
		HistoryByPage historyByPage = BillingAPI.getBillingHistoriest(ptype, billing_type, 
				begintime, endtime, uid, page, PAGE_SIZE);
		if(historyByPage != null){
			total = historyByPage.getTotal();
			if(total % PAGE_SIZE == 0){
	    		lastpage = total / PAGE_SIZE;
	    	}else{
	    		lastpage = total / PAGE_SIZE + 1;
	    	}
			
			billingHistories = historyByPage.getBillingHistories();
			for(BillingHistory bHistory: billingHistories){
				if(!amountMap.containsKey(bHistory.getId())){
					BigDecimal bd = new BigDecimal(String.valueOf((double)(Math.abs(bHistory.getAmount())/100.0)));
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					String amount = bd.toString();
					amountMap.put(bHistory.getId(), amount);
				}
			}
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查询交易记录", "用户邮箱"+email, "BillingHistoriesAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getBilling_type() {
		return billing_type;
	}

	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
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

	public HashMap<Integer, String> getAmountMap() {
		return amountMap;
	}

	public void setAmountMap(HashMap<Integer, String> amountMap) {
		this.amountMap = amountMap;
	}

	public List<BillingHistory> getBillingHistories() {
		return billingHistories;
	}

	public void setBillingHistories(List<BillingHistory> billingHistories) {
		this.billingHistories = billingHistories;
	}

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
}
