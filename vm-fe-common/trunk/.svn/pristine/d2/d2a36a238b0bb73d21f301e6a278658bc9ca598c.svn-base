package com.appcloud.vm.fe.manager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import com.appcloud.vm.fe.model.FilterBean;
import com.appcloud.vm.fe.model.FilterBean.FilterType;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.model.VmHdEndtimeDAO;

public class VmHdEndtimeManager{
	
	private Logger logger = Logger.getLogger(VmHdEndtimeManager.class);
	private VmHdEndtimeDAO dao = new VmHdEndtimeDAO();
	
	/*将前端传的1,2,3,4转换为String的年月日*/
	public String convertToType(int paymentType) {
		String payType;
		switch (paymentType) {
			case 1:
				payType = VmHdEndtime.PAYTYPE_YEAR;
				break;
			case 2:
				payType = VmHdEndtime.PAYTYPE_MONTH;
				break;
			case 3:
				payType = VmHdEndtime.PAYTYPE_DAY;
				break;
			case 4:
				payType = VmHdEndtime.PAYTYPE_HOUR;
				break;
			default:
				payType = VmHdEndtime.PAYTYPE_HOUR;
				break;
		}
		return payType;
	}
	
	/*根据type转换为123*/
	public Integer convertToPaymentType(String paymentType) {
		int payType = -1;
		if (paymentType.equalsIgnoreCase(VmHdEndtime.PAYTYPE_YEAR)) {
			payType = 1;
		}else if(paymentType.equalsIgnoreCase(VmHdEndtime.PAYTYPE_MONTH)){
			payType = 2;
		}else if(paymentType.equalsIgnoreCase(VmHdEndtime.PAYTYPE_DAY)){
			payType = 3;
		}else if(paymentType.equalsIgnoreCase(VmHdEndtime.PAYTYPE_HOUR)){
			payType = 4;
		}
		return payType;
	}

	/*根据创建时间，付费类型，以及购买时长，确定到期时间*/
	private Timestamp getEndTime(Date createTime, String payType, double payCount){
		if (createTime == null) {
			createTime = new Date();
		}
		Calendar calendar= new GregorianCalendar();  
        calendar.setTime(createTime); 
        Timestamp endTime = null;
		int addTime;
		if (payType.equalsIgnoreCase(VmHdEndtime.PAYTYPE_HOUR)) {
			addTime = (int) (payCount * 3600);//应该是1小时=3600秒
			calendar.add(calendar.SECOND, addTime);
		} else if (payType.equals(VmHdEndtime.PAYTYPE_DAY)) {
			addTime = (int) (payCount * 24);//1天= 24小时
			calendar.add(calendar.HOUR_OF_DAY, addTime);
		} else if (payType.equals(VmHdEndtime.PAYTYPE_MONTH)) {
			addTime = (int) (30 * payCount);
			calendar.add(calendar.DAY_OF_YEAR,addTime);
		} else {
			addTime = (int) (360 * payCount);
			calendar.add(calendar.DAY_OF_YEAR,addTime);
		}
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = Timestamp.valueOf(dateFormat.format(calendar.getTime()));
		return endTime;
	}
	
	/**
	 * 新建一条虚拟机/硬盘到期时间的记录
	 * @param userId	用户id
	 * @param uuid		vm或者hd的uuid
	 * @param type		vm，hd
	 * @param paymentType	付费类型：年月日
	 * @param count		付费时长
	 */
	public VmHdEndtime createVmHdEndtime(Integer userId, String uuid, Date createTime,
													 String type, int paymentType, double count) {
		String payType = convertToType(paymentType);
		Timestamp endTime = getEndTime(createTime, payType, count);
		VmHdEndtime vmHdEndtime = new VmHdEndtime(userId, uuid, type, endTime, payType);
		dao.save(vmHdEndtime);
		logger.info("创建"+type+"到期记录成功");
		return vmHdEndtime;
	}

	/**
     * 更新一条虚拟机/硬盘到期时间的记录，并修改付费类型
     * @param userId    用户id
     * @param uuid      vm或者hd的uuid
     * @param type      vm，hd
     * @param paymentType   付费类型：年月日
     * @param count     付费时长
     */
    public VmHdEndtime updateVmHdEndtime(Integer userId, String uuid, Date createTime,
            String type, int paymentType, double count) {
    	logger.debug("userId = " + userId + " uuid = " + uuid + " paymentType = " + paymentType 
                           + " count = " + count + "tempStamp = " + createTime + "  " + VmHdEndtime.TYPE_VM);
        String payType = convertToType(paymentType);
        Timestamp endTime = getEndTime(createTime, payType, count);
        VmHdEndtime vmHdEndtime = this.getVmEndtimeByUuid(uuid);
        vmHdEndtime.setEndTime(endTime);
        vmHdEndtime.setPayType(payType);
        dao.update(vmHdEndtime);
        logger.info("创建 " + type + "到期记录成功！"+"endTime = " + endTime);
        return vmHdEndtime;
    }
    
	/**
     * 更新一条虚拟机/硬盘到期时间的记录
     * @param userId    用户id
     * @param uuid      vm或者hd的uuid
     * @param delayDays 从createTime起延期的天数
     */
    public VmHdEndtime updateVmHdEndtime(Integer userId, String uuid, Date createTime,
            int delayDays) {
    	logger.debug("userId = " + userId + " uuid = " + uuid 
                           + "  delay days = " + delayDays + "tempStamp = " + createTime);
    	Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(createTime.getTime());
		calendar.add(Calendar.DATE, delayDays);
		Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        VmHdEndtime vmHdEndtime = this.getVmEndtimeByUuid(uuid);
        vmHdEndtime.setEndTime(endTime);
        dao.update(vmHdEndtime);
        logger.info("延期" + uuid + "成功！"+"from endTime = " + createTime +" to "+endTime);
        return vmHdEndtime;
    }
    
    public VmHdEndtime updateVmHdEndtimeType(Integer userId, String uuid, String type) {
    	logger.debug("userId = " + userId + " uuid = " + uuid + " type = " + type);
        VmHdEndtime vmHdEndtime = this.getVmEndtimeByUuid(uuid);
        String oldType = vmHdEndtime.getType();
        vmHdEndtime.setType(type);
        dao.update(vmHdEndtime);
        logger.info("修改旧的"+oldType+"为新的"+type+"！");
        return vmHdEndtime;
    }
    
	/**
	 * 取得某个虚拟机的到期时间记录
	 * @param vmUuid
	 * @return
	 */
	public VmHdEndtime getVmEndtimeByUuid(String vmUuid){
	    //TODO  为什么要通过两个属性筛选。
	    List<VmHdEndtime> endtimes = dao.findByProperty("uuid", vmUuid);
//		List<VmHdEndtime> endtimes = dao.findByProperty2("uuid", vmUuid, "type", VmHdEndtime.TYPE_VM);
		if (endtimes == null || endtimes.isEmpty()) {
			return null;
		} else {
			return endtimes.get(0);
		}
	}
	

	/**
	 * 取得某个硬盘的到期时间记录
	 * @param hdUuid
	 * @return
	 */
	public VmHdEndtime getHdEndtimeByUuid(String hdUuid){
		List<VmHdEndtime> endtimes = dao.findByProperty("uuid", hdUuid);
//      List<VmHdEndtime> endtimes = dao.findByProperty2("uuid", hdUuid, "type", VmHdEndtime.TYPE_HD);
		if (endtimes == null || endtimes.isEmpty()) {
			return null;
		} else {
			return endtimes.get(0);
		}
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	public List<VmHdEndtime> getHdEndtimeByUserId(Integer userId){
		List<VmHdEndtime> endtimes = dao.findByProperty2("userId", userId, "type", VmHdEndtime.TYPE_HD);
		return endtimes;
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	public List<VmHdEndtime> getVmEndtimeByUserId(Integer userId){
		List<VmHdEndtime> endtimes = dao.findByProperty2("userId", userId, "type", VmHdEndtime.TYPE_VM);
		endtimes.addAll(dao.findByProperty2("userId", userId, "type", VmHdEndtime.TYPE_VMPACKAGE));
		return endtimes;
	}

	/**
	 * 根据uuid删除某条硬盘截止时间记录
	 * @param uuid
	 */
	public void delHdEndtimeByUuid(String uuid){
		dao.deleteByUuidAndType(uuid, VmHdEndtime.TYPE_HD);
		logger.info("删除hd："+uuid+"截止日期记录成功");
	}

	/**
	 * 根据uuid删除某条虚拟机截止时间记录
	 * @param uuid
	 * @return
	 */
	public void delVmEndtimeByUuid(String uuid){
		dao.deleteByUuidAndType(uuid, VmHdEndtime.TYPE_VM);
		logger.info("删除vm："+uuid+"截止日期记录成功");
	}

	/**
	 * 根据id删除某条vm/hd截止时间记录
	 * @param id
	 * @return
	 */
	public void delEndtimeById(Integer id){
		if (id == null) {
			logger.info("endtimeId为空，无法删除");
		} else {
			dao.deleteByPrimaryKey(id);
			logger.info("删除endtime记录："+id+"成功");
		}
	}
	
	/**
	 * 取得所有数据库记录
	 * @return
	 */
	public List<VmHdEndtime> getAllEndtime(){
		return dao.findAll();
	}
	
	/**
	 * 取得所有硬盘的纪录
	 */
	public List<VmHdEndtime> getAllHdEndtime() {
	    return dao.findByProperty("type", VmHdEndtime.TYPE_HD);
	}
	
	/**
	 * 取得所有非按需的硬盘虚拟机记录
	 */
	public List<VmHdEndtime> getAllEndtimeNotHour() {
		List <FilterBean> beans = new ArrayList<FilterBean>();
		beans.add(new FilterBean("payType", VmHdEndtime.PAYTYPE_HOUR, FilterType.NOTEQUAL));
		return dao.findByProperties(beans);
	}
	
	/**
	 * 取得所有非按需硬盘的纪录
	 */
	public List<VmHdEndtime> getAllHdEndtimeNotHour() {
		List <FilterBean> beans = new ArrayList<FilterBean>();
		beans.add(new FilterBean("type", VmHdEndtime.TYPE_HD, FilterType.EQUAL));
		beans.add(new FilterBean("payType", VmHdEndtime.PAYTYPE_HOUR, FilterType.NOTEQUAL));
		return dao.findByProperties(beans);
	}
	
	/**
	 * 取得所有非按需虚拟机的纪录
	 */
	public List<VmHdEndtime> getAllVmEndtimeNotHour() {
		List <FilterBean> beans = new ArrayList<FilterBean>();
		//beans.add(new FilterBean("type", VmHdEndtime.TYPE_VMPACKAGE+" , "+VmHdEndtime.TYPE_VM, FilterType.IN));
		beans.add(new FilterBean("type", VmHdEndtime.TYPE_HD, FilterType.NOTEQUAL));
		beans.add(new FilterBean("payType", VmHdEndtime.PAYTYPE_HOUR, FilterType.NOTEQUAL));
		return dao.findByProperties(beans);
	}
	
	/**
     * 取得所有虚拟机的纪录
     */
    public List<VmHdEndtime> getAllVmEndtime() {
        return dao.findByProperty("type", VmHdEndtime.TYPE_VM);
    }
    
    /**
     * 获取uids和types对应的记录
     * @param uids
     * @param types
     * @return
     */
	public List<VmHdEndtime> getByUidsAndType(List<Integer> uids,
			List<String> types) {
		List<VmHdEndtime> vmHdEndtimes = new ArrayList<VmHdEndtime>();
		vmHdEndtimes = dao.findByUidsAndTypes(uids, types);
		return vmHdEndtimes;
	}
    
//    public static void main(String[] args) {
//        VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
//        vmHdEndtimeManager.updateVmHdEndtimeType(721, "68f13a0157d740d69eb5139b3d5b58be", VmHdEndtime.TYPE_VMPACKAGE);
//        List <Integer> uids = new ArrayList<Integer>();
//        uids.add(17);
//        List<String> types = new ArrayList<String>();
//        types.add("VM");
//        types.add("VMPACKAGE");
//        vmHdEndtimeManager.getByUidsAndType(uids, types);
//        String payType = vmHdEndtimeManager.convertToType(4);
//        Timestamp endTimestamp = vmHdEndtimeManager.getEndTime(new Timestamp(System.currentTimeMillis()), payType, 1);
//        System.out.println(endTimestamp);
//    }
    
	public List<VmHdEndtime> getByEndtime(String type, String payType, Timestamp startDate, Timestamp endDate) {
		List <FilterBean> beans = new ArrayList<FilterBean>();
		if(type != null) {
			if (type.equals(VmHdEndtime.TYPE_VM) || type.equals(VmHdEndtime.TYPE_HD)
					|| type.equals(VmHdEndtime.TYPE_VMPACKAGE)) {
				beans.add(new FilterBean("type", type, FilterType.EQUAL));
			}
			else  {
				logger.info("type illegal");
				return null;
			}
		}
		if(payType != null) {
			if(payType.equals(VmHdEndtime.PAYTYPE_DAY) || payType.equals(VmHdEndtime.PAYTYPE_MONTH) 
					|| payType.equals(VmHdEndtime.PAYTYPE_YEAR) || payType.equals(VmHdEndtime.PAYTYPE_HOUR))
				beans.add(new FilterBean("payType", payType, FilterType.EQUAL));
			else if(payType.equals("prepaid"))
				beans.add(new FilterBean("payType", VmHdEndtime.PAYTYPE_HOUR, FilterType.NOTEQUAL));
			else  {
				logger.info("type illegal");
				return null;
			}
		}
		if(startDate != null)
			beans.add(new FilterBean("endTime", startDate, FilterType.MORE_EQUAL));
		if(endDate != null)
			beans.add(new FilterBean("endTime", endDate, FilterType.LESS_THAN));
		return dao.findByProperties(beans);
	}
	
	
	public List<VmHdEndtime> getByPayTypeHour(){
		return dao.findByProperty("payType", VmHdEndtime.PAYTYPE_HOUR);
	}
    
}
