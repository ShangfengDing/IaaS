package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmHostUsed;
import appcloud.common.proxy.VmHostUsedProxy;
import appcloud.dbproxy.mysql.dao.VmHostUsedDAO;
import appcloud.dbproxy.mysql.model.VmHostUsedTable;

public class MySQLVmHostUsedProxy implements VmHostUsedProxy {
	private static VmHostUsedDAO dao = new VmHostUsedDAO();

	/**
	 * 取得所有主机资源使用情况
	 * @return
	 */
	@Override
	public List<? extends VmHostUsed> findAll() throws Exception {
		List<? extends VmHostUsed> vmHostUseds = dao.findAll();
		return vmHostUseds;
	}

	/**
	 * 根据host uuid取得某个主机的资源使用情况
	 * @param hostUuid
	 * @return
	 */
	@Override
	public VmHostUsed getByHostUuid(String hostUuid) throws Exception {
		List<? extends VmHostUsed> vmHostUseds = dao.findByProperty("uuid", hostUuid);
		if(vmHostUseds.size()!=0){
			VmHostUsed vmHostUsed = vmHostUseds.get(0);
			return vmHostUsed;
		} else {
			return null;
		}
	}

	/**
	 * 保存一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	@Override
	public void save(VmHostUsed vmHostUsed) throws Exception {
		dao.save(new VmHostUsedTable(vmHostUsed));
		
	}

	/**
	 * 更新一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	@Override
	public void update(VmHostUsed vmHostUsed) throws Exception {
		dao.update(new VmHostUsedTable(vmHostUsed));
		
	}

	/**
	 * 根据host uuid删除一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	@Override
	public void deleteByHostUuid(String hostUuid) throws Exception {
		dao.deleteByProperty("hostUuid", hostUuid);
		
	}

	/**
	 * 根据id删除一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	@Override
	public void deleteById(Integer id) throws Exception {
		dao.deleteByProperty("id", id);
		
	}

}
