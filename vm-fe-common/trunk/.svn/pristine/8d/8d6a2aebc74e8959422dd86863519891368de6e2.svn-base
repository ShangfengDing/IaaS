package com.appcloud.vm.fe.manager;
/**
 * author:lzh
 * 
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import com.appcloud.vm.fe.model.ServiceNum;
import com.appcloud.vm.fe.model.ServiceNumDAO;

public class ServiceNumManager {
	
	private static ServiceNumDAO dao = new ServiceNumDAO();
	private static Logger logger = Logger.getLogger(ServiceNumManager.class);

	/**
	 * 通过userId获取该userId对应的所有serviceNum信息
	 */
	public ServiceNum getServiceByUserId(Integer userId) {
		
		List<ServiceNum> serviceNums = dao.findByProperty("userId", userId);
		
		return serviceNums.isEmpty() ? null : serviceNums.get(0);
	}
	
	/**
	 * 更新相应userId的各个服务数量
	 */
	public void updateServiceNum(Integer userId,Integer instanceNum,
			Integer diskNum,Integer imageNum,Integer snapshotNum,Integer securitygroupNum,
			Integer instanceAcNum,Integer diskAcNum) {
		ServiceNum serviceNum = getServiceByUserId(userId);
		if (serviceNum == null) {
			serviceNum = new ServiceNum();
		}
		
		
		if(diskNum != null) {
			serviceNum.setDiskNum(diskNum);
			serviceNum.setDiskRefTime(new Date());
		}
		if(imageNum != null) {
			serviceNum.setImageNum(imageNum);
			serviceNum.setImageRefTime(new Date());
		}
		if(instanceNum != null) {
			serviceNum.setInstanceNum(instanceNum);
			serviceNum.setInstanceRefTime(new Date());
		}
		if(securitygroupNum != null) {
			serviceNum.setSecuritygroupNum(securitygroupNum);
			serviceNum.setSecuritygroupRefTime(new Date());
		}
		if(snapshotNum != null) {
			serviceNum.setSnapshotNum(snapshotNum);
			serviceNum.setSnapshotRefTime(new Date());
		}
		if(diskAcNum != null) {
			serviceNum.setDiskAcNum(diskAcNum);
		}
		if(instanceAcNum != null) {
			serviceNum.setInstanceAcNum(instanceAcNum);
		}
		
		if (serviceNum.getUserId()==null) {
			serviceNum.setUserId(userId);
			dao.save(serviceNum);
		} else {
			dao.update(serviceNum);
		}
	}
}
