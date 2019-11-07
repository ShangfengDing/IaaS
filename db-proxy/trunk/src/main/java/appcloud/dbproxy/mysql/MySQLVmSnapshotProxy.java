package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmSnapshotProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmSnapshotDAO;
import appcloud.dbproxy.mysql.dao.VmVolumeDAO;
import appcloud.dbproxy.mysql.model.VmSnapshotTable;

public class MySQLVmSnapshotProxy implements VmSnapshotProxy{

	private static VmSnapshotDAO dao = new VmSnapshotDAO();
	@Override
	public List<? extends VmSnapshot> findAll(boolean withVolume)
			throws Exception {
		return findAll(withVolume, 0, 0);
	}

	@Override
	public List<? extends VmSnapshot> findAll(boolean withVolume, Integer page,
			Integer size) throws Exception {
		List <?extends VmSnapshot>snapshots =  dao.findAll(page, size);
		for(VmSnapshot snapshot: snapshots)
			fillUpVmSnapshot(snapshot, withVolume);
		return snapshots;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmSnapshot> searchAll(
			QueryObject<VmSnapshot> queryObject, boolean withVolume)
			throws Exception {
		return searchAll(queryObject, withVolume, 0, 0);
	}

	@Override
	public List<? extends VmSnapshot> searchAll(
			QueryObject<VmSnapshot> queryObject, boolean withVolume,
			Integer page, Integer size) throws Exception {
		List <?extends VmSnapshot>snapshots =  dao.findByProperties(queryObject, page, size);
		for(VmSnapshot snapshot: snapshots)
			fillUpVmSnapshot(snapshot, withVolume);
		return snapshots;
	}

	@Override
	public long countSearch(QueryObject<VmSnapshot> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmSnapshot getById(Integer snapshotId, boolean withVolume)
			throws Exception {
		VmSnapshot snapshot = dao.findById(snapshotId);
		fillUpVmSnapshot(snapshot, withVolume);
		return snapshot;
	}
	
	@Override
	public VmSnapshot getByUuid(String uuid, boolean withVolume)
			throws Exception {
		List<?extends VmSnapshot> snapshots = dao.findByProperty("uuid", uuid);
		VmSnapshot snapshot = null;
		if(!snapshots.isEmpty()){
			snapshot = snapshots.get(0);
			fillUpVmSnapshot(snapshot, withVolume);
		}
		return snapshot;
	}
	
	@Override
	public VmSnapshot getByName(String name, boolean withVolume)
			throws Exception {
		List<?extends VmSnapshot> snapshots = dao.findByProperty("name", name);
		VmSnapshot snapshot = null;
		if(!snapshots.isEmpty()){
			snapshot = snapshots.get(0);
			fillUpVmSnapshot(snapshot, withVolume);
		}
		return snapshot;
	}

	@Override
	public void save(VmSnapshot snapshot) throws Exception {
		dao.save(new VmSnapshotTable(snapshot));
	}

	@Override
	public void update(VmSnapshot snapshot) throws Exception {
		dao.update(new VmSnapshotTable(snapshot));
	}

	@Override
	public void deleteById(Integer snapshotId) throws Exception {
		dao.deleteByPrimaryKey(snapshotId);
	}
	
	private void fillUpVmSnapshot(VmSnapshot snapshot, boolean withVolume){
		if(snapshot == null)
			return;
		if(withVolume){
			VmVolume volume = (new VmVolumeDAO()).findById(snapshot.getVolumeId());
			snapshot.setVmVolume(volume);
		}
	}

}
