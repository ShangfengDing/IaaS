package appcloud.openapi.checkutils;

import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.model.Billingrate;
import com.appcloud.vm.fe.common.Constants;

import appcloud.api.enums.ServerOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.util.ConnectionFactory;

public class InstanceChecker {
	private static Logger logger = Logger.getLogger(InstanceChecker.class);
	private static VmInstanceProxy instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
			VmInstanceProxy.class,
			appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	
	public static VmInstance checkReady(String tenantId, String serverId) throws Exception {
		VmInstance instance = checkOwner(tenantId, serverId);
		
		if (instance.getTaskStatus() == null) {
			logger.info("in instance : task_status is NULL");
			throw new OperationFailedException("check tast status");
		}
		if (!instance.getTaskStatus().equals(TaskStatusEnum.READY)) {
			logger.info("server is not ready");
			throw new OperationFailedException("please wait");
		}
		return instance;
	}
	
	public static VmInstance checkOwner(String tenantId, String serverId) throws Exception {
		if(tenantId == null) {
			logger.info("in checkOwner : tenantId is NULL");
			throw new OperationFailedException("tenantId is NULL");
		}
		
		if(serverId == null) {
			logger.info("in checkOwner : serverId is NULL");
			throw new OperationFailedException("serverId is NULL");
		}
		
		boolean isAdmin = false;
		try {
			Integer.parseInt(tenantId);
		} catch(NumberFormatException e) {
			isAdmin = true;
		}
		
		VmInstance instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
		if (instance == null) {
			logger.info("server does not exist");
			throw new ItemNotFoundException("server does not exist");
		}
		
		if(!isAdmin) {
			if (instance.getUserId() == null) {
				logger.info("in vm_instance : user_id is NULL");
				throw new OperationFailedException("tenant id is NULL");
			}
			if (!instance.getUserId().equals(Integer.valueOf(tenantId))) {
				logger.info("server does not belong to the tenant");
				throw new OperationFailedException("check tenant id");
			}
		} 
		return instance;
	}

	public static Boolean checkOperation(String tenantId, String serverId, 
			ServerOperationEnum operation) throws Exception{
		VmInstance instance = checkReady(tenantId, serverId);
		VmStatusEnum vmStatus = instance.getVmStatus();
		boolean allowed = false;
		if(vmStatus != null){
			switch (operation) {
			case OS_START:
				if(vmStatus.equals(VmStatusEnum.STOPPED))
					allowed = true;
				break;
			case OS_STOP:
				 if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED))
					 allowed = true;
				 break;
			case OS_SUSPEND:
				if(vmStatus.equals(VmStatusEnum.ACTIVE))
					allowed = true;
				 break;
			case OS_RESUME:
				if(vmStatus.equals(VmStatusEnum.SUSPENDED))
					 allowed = true;
				break;
			case FORCE_DELETE:
				if(!vmStatus.equals(VmStatusEnum.BUILDING))
					allowed = true;
				break;
			case AC_FORCE_STOP:
				if(!vmStatus.equals(VmStatusEnum.BUILDING))
					allowed = true;
				break;
			case RESIZE:
				if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
						|| vmStatus.equals(VmStatusEnum.STOPPED))
					 allowed = true;
				 break;
			case AC_ISO_BOOT:
				if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
						|| vmStatus.equals(VmStatusEnum.STOPPED))
					 allowed = true;
				 break;
			case AC_ISO_DETACH:
				if(vmStatus.equals(VmStatusEnum.REBUILDING))
					 allowed = true;
				 break;
			case REBOOT:
				if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED))
					 allowed = true;
				 break;
			case REBUILD:
				if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
						|| vmStatus.equals(VmStatusEnum.STOPPED))
					 allowed = true;
				 break;
			case AC_FORCE_REFRESH:
				if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
						|| vmStatus.equals(VmStatusEnum.STOPPED))
					allowed = true;
				break;
			case DELETE:
				if(!vmStatus.equals(VmStatusEnum.BUILDING))
					allowed = true;
				break;
			case AC_RESET:
				if(!vmStatus.equals(VmStatusEnum.BUILDING))
					allowed = true;
			case AC_MODIFYPASSWORD:
				if(!vmStatus.equals(VmStatusEnum.BUILDING))
					allowed = true;
			case AC_MIGRATE:
				if(vmStatus.equals(VmStatusEnum.STOPPED))
					allowed = true;
			case AC_ONLINE_MIGRATE:
				if(vmStatus.equals(VmStatusEnum.ACTIVE))
					allowed = true;
			}
		}
		if(!allowed) {
			logger.info("operation is not allowed now, instance status is " + vmStatus);
			/*throw new OperationFailedException("check server status");*/
		}
		return allowed;
	}
	public static Boolean checkModify(String tenantId, String serverId, 
			ServerOperationEnum operation) throws Exception{
		VmInstance instance = checkReady(tenantId, serverId);
		VmStatusEnum vmStatus = instance.getVmStatus();
		boolean allowed = false;
		if(vmStatus != null){
			if(operation!=null)
			{
				switch (operation) {
				case REBUILD:
					if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
							|| vmStatus.equals(VmStatusEnum.STOPPED))
						 allowed = true;
					break;
				case AC_MODIFYPASSWORD:
					if(!vmStatus.equals(VmStatusEnum.BUILDING))
						allowed = true;
					break;
				}
			}
			else
				allowed = true;
		}
		if(!allowed) {
			logger.info("operation is not allowed now, instance status is " + vmStatus);
			/*throw new OperationFailedException("check server status");*/
		}
		return allowed;
	}
	public static boolean checkClustterAndResource(List<Billingrate> list , String ptype,int value)
	{
		boolean result = false;
		for(Billingrate br : list)
			if (br.getPtype().equals(ptype))
			{
				if(ptype.equals(Constants.BILLING_CPU_ABBR) && br.getCpu()==value)
					result = true ;
				if(ptype.equals(Constants.BILLING_MEM_ABBR) && br.getMemory()==value)
					result = true ;
				if(ptype.equals(Constants.BILLING_BW_ABBR) && br.getBandwidth()==value)
					result = true ;
			}
		return result;
	}
}
