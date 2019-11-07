package com.appcloud.vm.fe.util;

import com.appcloud.vm.fe.common.Bill_Constants;
import com.appcloud.vm.fe.model.Billingrate;

import java.math.BigDecimal;
import java.util.List;;

public class ConvertBillingrate {
	/**
	 * 原vm-billingrate工程的com.appcloud.vm.billingrate.util.ConvertBillingrate类
	 * 合并至此 
	 */
	/*
	 * 将单个Billingrate类转化为Billingrates
	 */
	public static void class2class(Billingrate rate, Billingrates rates) {
		rates.setId(rate.getId());
		rates.setName(rate.getName());
		rates.setDescription(rate.getDescription());
		rates.setPtype(rate.getPtype());
		rates.setClusterid(rate.getClusterid());
		rates.setCpu(rate.getCpu());
		rates.setMemory(rate.getMemory());
		rates.setHarddisk(rate.getHarddisk());
		rates.setBandwidth(rate.getBandwidth());
		rates.setHourprice(round2Str(rate.getHourprice()));
		rates.setDayprice(round2Str(rate.getDayprice()));
		rates.setMonthprice(round2Str(rate.getMonthprice()));
		rates.setYearprice(round2Str(rate.getYearprice()));
	}
	/*
	 * 转换多个Billingrate为Billingrates类
	 */
	public static void list2list(List<Billingrate> rateList, List<Billingrates> ratesList) {
		for (int i = 0; i < rateList.size(); i++) {
			Billingrates rates = new Billingrates();
			class2class(rateList.get(i), rates);
			ratesList.add(rates);
		}
	}
	
	public static int round2Int(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		int result = (int)bd.doubleValue();
		return result;
	}
	
	/*
	 * 给定int类型，返回除以100之后的带两位小数的String类型
	 */
	public static String round2Str(int value) {
		double valueDouble = (double)value/Bill_Constants.BILLING_CONSTANT;
		if (valueDouble == 0) {
			return "0";
		}
		BigDecimal bd = new BigDecimal(valueDouble);
		bd = bd.setScale(Bill_Constants.BILLING_DOUBLE_BIT, BigDecimal.ROUND_HALF_UP);
		String str = bd.doubleValue() + "";
		if (str.length() - str.lastIndexOf(".") == 2) {
			StringBuffer buffer = new StringBuffer(str);
			buffer.append('0');
			str = buffer.toString();
		}
		return str;
	}
	
	/*
	 * 给定String类型，返回乘以100之后int类型
	 */
	public static int round2Int(String value) {
		double valueDouble = Double.parseDouble(value) * Bill_Constants.BILLING_CONSTANT;
		if (valueDouble == 0) {
			return 0;
		}
		BigDecimal bd = new BigDecimal(valueDouble);
		bd = bd.setScale(Bill_Constants.BILLING_DOUBLE_BIT, BigDecimal.ROUND_HALF_UP);
		int result = (int)(bd.doubleValue());
		return result;
	}
	
	public static boolean isItemOfAdd(String item) {
		if (item.equals(Bill_Constants.BILLING_ITEM_CPU) || item.equals(Bill_Constants.BILLING_ITEM_MEM) || item.equals(Bill_Constants.BILLING_ITEM_HD)
				|| item.equals(Bill_Constants.BILLING_ITEM_BW) || item.equals(Bill_Constants.BILLING_ITEM_VMPACKAGE) || item.equals(Bill_Constants.BILLING_ITEM_CHARGE)
				|| item.equals(Bill_Constants.BILLING_ITEM_INSTANCETYPE))
			return true;
		return false;
	}
	
	public static boolean isItemOfGet(String item) {
		if (isItemOfAdd(item) || item.equals(Bill_Constants.BILLING_ITEM_ALL) || item.equals(Bill_Constants.BILLING_ITEM_VM))
			return true;
		return false;
	}
	
	public static boolean isItemOfPrice(String item) {
		if (isItemOfGet(item) || item.equals(Bill_Constants.BILLING_ITEM_FLAVOR))
			return true;
		return false;
	}
}
