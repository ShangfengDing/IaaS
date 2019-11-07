package appcloud.vs.strategy;

import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;


public interface SelectorService {
	
	/**
	 * 选择主机列表
	 * @param cluster 集群
	 * @param size 硬盘大小
	 * @param exHost vm所用主机
	 * @return
	 */
	public List<Host> selectNodes(Cluster cluster, int size, Host exHost);

}
