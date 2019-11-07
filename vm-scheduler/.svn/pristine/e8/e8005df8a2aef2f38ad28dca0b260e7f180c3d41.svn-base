package appcloud.vmscheduler.strategy;

import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;

public interface SelectorService {
	
	/**
	 * 选择主机列表
	 * @param hostNum:需要多少个候选主机
	 * @param instance
	 * @param instanceType:虚拟机基本配置
	 * @param cluster所选集群
	 * @param zoneID:所选数据中心
	 * @return
	 */
	public List<Host> selectNodes(int hostNum, VmInstance instance, VmInstanceType instanceType, Integer cluster);

}
