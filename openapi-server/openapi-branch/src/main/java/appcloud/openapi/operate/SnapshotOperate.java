package appcloud.openapi.operate;

import appcloud.api.beans.Snapshot;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.openapi.response.SnapshotsDetailReponse;

public interface SnapshotOperate {

	/**
	 * 创建快照
	 * @param appkeyId
	 * @param snapshot
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public Snapshot create(String appkeyId, Snapshot snapshot, String requestId) throws Exception;
	
	
	/**
	 * 删除快照
	 * @param appkeyId
	 * @param snapshotId
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public Boolean delete(String appkeyId, String snapshotId, String requestId) throws Exception;
	
	
	public SnapshotsDetailReponse describe (String appkeyId, String snapshotIds, String requestId, String diskId, String snapshotName, String description, String snapshotStatus, String pageNum, String pageSize) throws Exception;
}
