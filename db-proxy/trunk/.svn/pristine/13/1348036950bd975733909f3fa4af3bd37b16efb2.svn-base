package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmZone;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.dbproxy.mysql.dao.HostDAO;
import appcloud.dbproxy.mysql.dao.VmImageDAO;
import appcloud.dbproxy.mysql.dao.VmVolumeDAO;
import appcloud.dbproxy.mysql.dao.ClusterDAO;
import appcloud.dbproxy.mysql.dao.VmZoneDAO;
import appcloud.dbproxy.mysql.model.VmVolumeTable;

public class MySQLVmVolumeProxy implements VmVolumeProxy{

	private static VmVolumeDAO dao = new VmVolumeDAO();
	@Override
	public List<? extends VmVolume> findAll(boolean withHost, boolean withCluster,boolean withZone, boolean withImage) throws Exception {
		return findAll(withHost, withCluster,withZone,withImage, 0, 0);
	}

	@Override
	public List<? extends VmVolume> findAll(boolean withHost, boolean withCluster,boolean withZone, boolean withImage, Integer page, Integer size)
			throws Exception {
		List <?extends VmVolume>volumes =  dao.findAll(page, size);
		for(VmVolume volume: volumes)
			fillUpVmVolume(volume, withHost, withCluster,withZone,withImage);
		return volumes;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmVolume> searchAll(
			QueryObject<VmVolume> queryObject,
			boolean withHost, boolean withCluster,boolean withZone, boolean withImage) throws Exception {
		return searchAll(queryObject, withHost, withCluster,withZone,withImage, 0, 0);
	}

	@Override
	public List<? extends VmVolume> searchAll(
			QueryObject<VmVolume> queryObject,
			boolean withHost, boolean withCluster,boolean withZone, boolean withImage,
			Integer page, Integer size)throws Exception {
		List <?extends VmVolume>volumes =  dao.findByProperties(queryObject, page, size);
		for(VmVolume volume: volumes)
			fillUpVmVolume(volume, withHost, withCluster,withZone,withImage);
		return volumes;
	}

	@Override
	public long countSearch(QueryObject<VmVolume> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmVolume getById(Integer volumeId, 
			boolean withHost, boolean withCluster,boolean withZone, boolean withImage) throws Exception {
		VmVolume volume = dao.findById(volumeId);
		fillUpVmVolume(volume, withHost, withCluster,withZone,withImage);
		return volume;
	}
	
	@Override
	public VmVolume getByImageName(String name, 
			boolean withHost, boolean withCluster,boolean withZone, boolean withImage) throws Exception{
		List<?extends VmVolume> volumes = dao.findByProperty("name", name);
		VmVolume volume = null;
		if(!volumes.isEmpty()){
			volume = volumes.get(0);
			fillUpVmVolume(volume, withHost, withCluster,withZone,withImage);
		}
		return volume;
	}
	@Override
	public void save(VmVolume volume) throws Exception {
		dao.save(new VmVolumeTable(volume));
	}

	@Override
	public void update(VmVolume volume) throws Exception {
		dao.update(new VmVolumeTable(volume));
	}

	@Override
	public void deleteById(Integer volumeId) throws Exception {
		dao.deleteByPrimaryKey(volumeId);
	}
	
	@Override
	public VmVolume getByUUID(String uuid, boolean withHost,
			boolean withCluster, boolean withZone, boolean withImage) throws Exception {
		List<VmVolumeTable> volumes = dao.findByProperty("volumeUuid", uuid);
		
		if (volumes.size() < 1){
			return null;
		}
		else{
			VmVolume volume = volumes.get(0);
			fillUpVmVolume(volume, withCluster, withZone,withZone,withImage);
			return volume;
		}
	}
	
	private void fillUpVmVolume(VmVolume volume, boolean withHost, boolean withCluster,boolean withZone, boolean withImage){
		if(volume == null)
			return;
		if(withHost && volume.getHostUuid()!=null){
			List<? extends Host> hosts = (new HostDAO()).findByProperty("uuid", (volume.getHostUuid()));
			if(!hosts.isEmpty())
				volume.setHost(hosts.get(0));
		}
		if(withCluster && volume.getAvailabilityClusterId()!=null){
			Cluster cluster = (new ClusterDAO()).findById(volume.getAvailabilityClusterId());
			volume.setCluster(cluster);
		}
		if(withZone && volume.getAvailabilityZoneId()!=null){
			VmZone zone = (new VmZoneDAO()).findById(volume.getAvailabilityZoneId());
			volume.setZone(zone);
		}
		if(withImage && volume.getImageUuid() !=null){
			List<? extends VmImage> iamges = (new VmImageDAO()).findByProperty("uuid",volume.getImageUuid());
			if(!iamges.isEmpty())
				volume.setImage(iamges.get(0));
			
		}
	}

	public List<? extends VmVolume> getBySrcBackupUUID(String uuid,
			boolean withHost, boolean withCluster, boolean withZone,
			boolean withImage) {
		List<?extends VmVolume> volumes = dao.findByProperty("backupVolumeUuid", uuid);
		for(VmVolume at:volumes) {
			fillUpVmVolume(at, withHost, withCluster,withZone,withImage);
		}
		return volumes;
	}

	public String getHostUuidByImageUuid(String imageUuid){
		return dao.findByProperty("imageUuid", imageUuid).get(0).getHostUuid();
		
	}
	public static void main(String[] args) throws Exception {
		QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
		queryObject.addFilterBean(new FilterBean<VmVolume>("instanceUuid", "45793fb527894feb893647189fee65b2", FilterBeanType.EQUAL));
		queryObject.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolumeUsageTypeEnum.SYSTEM, FilterBeanType.EQUAL));
		List<VmVolume> lists = (List<VmVolume>) new MySQLVmVolumeProxy().searchAll(queryObject, false, false, false, false);
		System.out.println(lists);
	}

}
