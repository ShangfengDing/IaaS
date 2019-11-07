// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
;

public class VmVolumeProxyStub implements VmVolumeProxy{
	
	@Override
	public void save(VmVolume arg0) throws Exception{
	}
	@Override
	public void update(VmVolume arg0) throws Exception{
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2, boolean arg3, Integer arg4, Integer arg5) throws Exception{
		return new ArrayList();
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2, boolean arg3) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countAll() throws Exception{
		return 0;
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public VmVolume getById(Integer arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) throws Exception{
		return new VmVolume();
	}
	/*@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) throws Exception{
		return new ArrayList();
	}*/

	/*@Override
	public long countSearch(QueryObject arg0) throws Exception{
		return 0;
	}*/
	@Override
	public VmVolume getByImageName(String arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) throws Exception{
		return new VmVolume();
	}
	@Override
	public VmVolume getByUUID(String arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) throws Exception{
		VmVolume volume = new VmVolume();
		volume.setSize(0);
		return volume;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVolumeProxy#getBySrcBackupUUID(java.lang.String, boolean, boolean, boolean, boolean)
	 */
	@Override
	public List<? extends VmVolume> getBySrcBackupUUID(String arg0,
			boolean arg1, boolean arg2, boolean arg3, boolean arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVolumeProxy#searchAll(appcloud.common.util.query.QueryObject, boolean, boolean, boolean, boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmVolume> searchAll(
			QueryObject<VmVolume> queryObject, boolean withHost,
			boolean withCluster, boolean withZone, boolean withImage,
			Integer page, Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends VmVolume> searchAll(
			QueryObject<VmVolume> queryObject, boolean withHost,
			boolean withCluster, boolean withZone, boolean withImage)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countSearch(QueryObject<VmVolume> queryObject) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getHostUuidByImageUuid(String imageUuid) {
		// TODO Auto-generated method stub
		return null;
	}


}
