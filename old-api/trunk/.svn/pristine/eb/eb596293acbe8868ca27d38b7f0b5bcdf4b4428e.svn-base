package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import appcloud.api.beans.Backup;
import appcloud.api.beans.Volume;
import appcloud.api.beans.VolumeAttachment;
import appcloud.api.enums.AcVolumeTypeEnum;
import appcloud.api.manager.VolumeManager;

public class FakeVolumeManager implements VolumeManager {

	@Override
	public List<Volume> getList(String tenantId, boolean detailed) {
		// 是否detail，在openstack好像是一样的
		HashMap<String, String> metas = new HashMap<String, String>();
		metas.put("key1", "values1");
		metas.put("key2", "values2");
		List<Volume> volumes = new ArrayList<Volume>();
		
		VolumeAttachment attachment1 = new VolumeAttachment("dev/sda", "server1","ID1", "ID1");
		VolumeAttachment attachment2 = new VolumeAttachment("dev/sda", "server1","ID2", "ID2");
		volumes.add(new Volume("ID1", "volume1","description1", "runing", 10, "type1", "zone1",metas, 
				"snap1", attachment1, new Date(), AcVolumeTypeEnum.SYSTEM));
		volumes.add(new Volume("ID2", "volume2","description2", "hang", 10, "type2", "zone1",metas, 
				"snap1", attachment2, new Date(), AcVolumeTypeEnum.NETWORK));

		return volumes;
	}

	@Override
	public Volume get(String tenantId, String volumeId) {
		HashMap<String, String> metas = new HashMap<String, String>();
		metas.put("get", "vvvvv");
		VolumeAttachment attachment = new VolumeAttachment("dev/sdg", "server3","ID3", "ID3");
		Volume volume = new Volume(volumeId, "volume3","description3", "runing", 10, 
				"type3", "zone2",metas, "snap3", attachment, new Date(), AcVolumeTypeEnum.DATA);
		return volume;
	}

	@Override
	public Volume create(String tenantId, Volume cReq) {
		HashMap<String, String> metas = new HashMap<String, String>();
		metas.put("create1", "values1");
		metas.put("create2", "values2");
		VolumeAttachment attachment = new VolumeAttachment("dev/sdc", "server5","ID5", "ID5");
		Volume volume = new Volume("ID5", cReq.displayName,cReq.displayDescription, "what", 10, 
				cReq.volumeType, cReq.availabilityZone, metas, "snap1", attachment, new Date(),
				AcVolumeTypeEnum.BACKUP);
		return volume;
	}

	@Override
	public void delete(String tenantId, String volumeId) {
		System.out.println("deleting " + tenantId + "'s volume:" + volumeId);
	}

	@Override
	public Backup getBackup(String tenantId, String backupId) throws Exception {
		return new Backup("id2", "name2", tenantId, "descrition2", "volumeId2", "staus2", 1, new Date(), null, null);
	}

	@Override
	public List<Backup> getBackupList(String tenantId, boolean detailed, String serverId) {
		List<Backup> backups = new ArrayList<Backup>();
		backups.add(new Backup("id1", "name1", tenantId, "descrition1", "volumeId1", "staus1", 1, new Date(), null, null));
		backups.add(new Backup("id2", "name2", tenantId, "descrition2", "volumeId2", "staus2", 1, new Date(), null, null));
		
		return backups;
	}

	@Override
	public Backup createBackup(String tenantId, Backup backup) throws Exception {
		return new Backup("id2", "name2", tenantId, "descrition2", "volumeId2", "staus2", 1, new Date(), null, null);
	}

	@Override
	public void deleteBackup(String tenantId, String backupId) throws Exception {
		System.out.println("deleting " + tenantId + "'s backup:" + backupId);
	}

	@Override
	public Volume update(String tenantId, String volumeId, Volume volume)
			throws Exception {
		System.out.println("update " + tenantId + "'s volume:" + volumeId);
		HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put("content", "not junk");
		Volume volumeUpdated = new Volume(volumeId, volume.displayName, volume.displayDescription,
				"updated", 100, "updated volume type",
				"zone", metadata,
				"snapshot Id", new VolumeAttachment (), new Date(), AcVolumeTypeEnum.BACKUP);
		return volumeUpdated;
	}

	@Override
	public void revertBackup(String tenantId, String backupId) throws Exception {
		System.out.println("restoring " + tenantId + "'s backup:" + backupId);
	}

	@Override
	public List<Volume> searchByProperties(String tenantId, String uuid,
			String userId, String serverId, String usageType, String status,
			String attachStatus, boolean isBackup, String displayName,
			Integer zoneId, Integer aggregateId, String hostId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String countByProperties(String tenantId, String uuid,
			String userId, String serverId, String usageType, String status,
			String attachStatus, boolean isBackup, String displayName,
			Integer zoneId, Integer aggregateId, String hostId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
