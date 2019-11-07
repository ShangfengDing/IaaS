package appcloud.admin.action.price;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;

public class GetRuleAction extends BaseAction {

	/**
	 * 查询item=all中对应的计费规则
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(GetRuleAction.class);
	
	private static final String item = Constants.ITEM_ALL;
	private List<Billingrate> cpus = new ArrayList<Billingrate>();
	private List<Billingrate> mems = new ArrayList<Billingrate>();
	private List<Billingrate> hds = new ArrayList<Billingrate>();
	private List<Billingrate> bds = new ArrayList<Billingrate>();
	private List<Billingrate> packages = new ArrayList<Billingrate>();
	private List<Billingrate> charges = new ArrayList<Billingrate>();
	private List<Billingrate> instancetype = new ArrayList<Billingrate>();
	private HashMap<Integer, String> clusterMap = new HashMap<Integer, String>();
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception{
		List<Billingrate> billingrates = BillingAPI.getRate(item, -1, -1, -1, -1, -1);
		for(Billingrate br : billingrates){
			if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_CPU)){
				cpus.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_MEM)){
				mems.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_HD)){
				hds.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_BW)){
				bds.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_VMPACKAGE)){
				packages.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_CHARGE)){
				charges.add(br);
			}else if(br.getPtype().equalsIgnoreCase(Constants.PTYPE_INSTANCETYPE)){
				instancetype.add(br);
			}
		}
		
		clusterMap = ClusterUtil.getClusterMap();
		
		ComparatorPackage comparatorPackage = new ComparatorPackage();
		Collections.sort(packages, comparatorPackage);
		Collections.sort(instancetype, comparatorPackage);
		ComparatorCpu comparatorCpu = new ComparatorCpu();
		Collections.sort(cpus, comparatorCpu);
		ComparatorMemory comparatorMemory = new ComparatorMemory();
		Collections.sort(mems, comparatorMemory);
		ComparatorHarddisk comparatorHarddisk = new ComparatorHarddisk();
		Collections.sort(hds, comparatorHarddisk);
		ComparatorBandwidth comparatorBandwidth = new ComparatorBandwidth();
		Collections.sort(bds, comparatorBandwidth);
		ComparatorCharge comparatorCharge = new ComparatorCharge();
		Collections.sort(charges, comparatorCharge);
		
		if(billingrates != null && billingrates.size() >= 0){
			LOGGER.info("获取计费规则成功！");
			return SUCCESS;
		}else{
			LOGGER.info("获取计费规则失败！");
			return ERROR;
		}
	}
	@SuppressWarnings("rawtypes")
	public class ComparatorPackage implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag0 = price0.getClusterid().compareTo(price1.getClusterid());
			if(flag0 == 0){
				int flag = price0.getCpu().compareTo(price1.getCpu());
				if(flag == 0){
					int flag1 = price0.getMemory().compareTo(price1.getMemory());
					if(flag1 == 0){
						int flag2 = price0.getHarddisk().compareTo(price1.getHarddisk());
						if(flag2 == 0){
							return price0.getBandwidth().compareTo(price1.getBandwidth());
						}else{
							return flag2;
						}
					}else{
						return flag1;
					}
				}else{
					return flag;
				}
			}else{
				return flag0;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorCpu implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag0 = price0.getClusterid().compareTo(price1.getClusterid());
			if(flag0 == 0){
				return price0.getCpu().compareTo(price1.getCpu());
			}else{
				return flag0;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorMemory implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag0 = price0.getClusterid().compareTo(price1.getClusterid());
			if(flag0 == 0){
				return price0.getMemory().compareTo(price1.getMemory());
			}else{
				return flag0;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorHarddisk implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag0 = price0.getClusterid().compareTo(price1.getClusterid());
			if(flag0 == 0){
				return price0.getHarddisk().compareTo(price1.getHarddisk());
			}else{
				return flag0;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorBandwidth implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag0 = price0.getClusterid().compareTo(price1.getClusterid());
			if(flag0 == 0){
				return price0.getBandwidth().compareTo(price1.getBandwidth());
			}else{
				return flag0;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public class ComparatorCharge implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getClusterid().compareTo(price1.getClusterid());
		}
	}
	
	public List<Billingrate> getCpus() {
		return cpus;
	}

	public void setCpus(List<Billingrate> cpus) {
		this.cpus = cpus;
	}

	public List<Billingrate> getMems() {
		return mems;
	}

	public void setMems(List<Billingrate> mems) {
		this.mems = mems;
	}

	public List<Billingrate> getHds() {
		return hds;
	}

	public void setHds(List<Billingrate> hds) {
		this.hds = hds;
	}

	public List<Billingrate> getBds() {
		return bds;
	}

	public void setBds(List<Billingrate> bds) {
		this.bds = bds;
	}

	public List<Billingrate> getPackages() {
		return packages;
	}

	public void setPackages(List<Billingrate> packages) {
		this.packages = packages;
	}

	public static String getItem() {
		return item;
	}

	public HashMap<Integer, String> getClusterMap() {
		return clusterMap;
	}

	public void setClusterMap(HashMap<Integer, String> clusterMap) {
		this.clusterMap = clusterMap;
	}

	public List<Billingrate> getCharges() {
		return charges;
	}

	public void setCharges(List<Billingrate> charges) {
		this.charges = charges;
	}

	public List<Billingrate> getInstancetype() {
		return instancetype;
	}

	public void setInstancetype(List<Billingrate> instancetype) {
		this.instancetype = instancetype;
	}
	
}