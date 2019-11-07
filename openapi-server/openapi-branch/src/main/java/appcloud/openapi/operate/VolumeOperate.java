package appcloud.openapi.operate;

import appcloud.api.beans.Volume;
import appcloud.openapi.response.DescribeDiskImageBackResponse;
import appcloud.openapi.response.DisksDetailReponse;

public interface VolumeOperate {

	
	/**
	 * 创建磁盘
	 * @param appkeyId
	 * @param aggregateId
	 * @param zoneId
	 * @param size
	 * @param displayName
	 * @param description
	 * @param type
	 * @param requestId 
	 * @return
	 * @throws Exception
	 */
	public Volume create (String appkeyId, String aggregateId, String zoneId, Integer size, String displayName,String description,String type, String requestId) throws Exception;


	public String createImageBack(String appkeyId, String displayName, String description, String volumeUuid, String requestId) throws Exception;
	/**
	 * 挂载硬盘
	 * @param appkeyId
	 * @param requestId
	 * @param diskId
	 * @return
	 */
	public Boolean attach (String appkeyId, String instanceId, String diskId, String requestId) throws Exception;
	
	/**
	 * 卸载硬盘
	 * @param appkeyId
	 * @param instanceId
	 * @param diskId
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public Boolean detach (String appkeyId, String instanceId, String diskId, String requestId) throws Exception;
	
	/**
	 * 删除硬盘
	 * @param appkeyId
	 * @param requestId
	 * @param diskId
	 * @return
	 */
	public Boolean delete (String appkeyId, String diskId, String requestId) throws Exception;
	
	/**
	 * 查看硬盘
	 * @param appkeyId
	 * @param diskIds
	 * @param requestId
	 * @param pageSize 
	 * @param pageNum 
	 * @param diskStatus 
	 * @param diskType 
	 * @param description 
	 * @param diskName 
	 * @param instanceId 
	 * @param zoneId 
	 * @return
	 */
	public DisksDetailReponse describe (String appkeyId, String diskIds, String requestId, String zoneId, String instanceId, String diskName, String description, String diskType, String diskStatus,String diskAttachStatus, String pageNum, String pageSize) throws Exception;

	/**
	 * 获取镜像的相关
	 * @return
	 * @throws Exception
	 */
	public DescribeDiskImageBackResponse describeImageBack (String requestId, String appkeyId, String volumeUuid, String activeVolumeId, String zoneId, String instanceId, String diskName,
															String imageBackStatus, String volumeType, String usageType,  String backUp, String top) throws Exception;

	/**
	 * 回滚硬盘
	 * @param appkeyId
	 * @param snapshotId
	 * @param diskId
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	public Boolean reset (String appkeyId, String snapshotId, String diskId, String requestId) throws Exception;

	/**
	 * 硬盘信息修改
	 * @param appkeyId
	 * @param diskId
	 * @param diskName
	 * @param description
	 * @return
	 */
	public Boolean modify(String appkeyId, String diskId, String diskName, String description);

	/**
	 * 修改imageBack的信息
	 * @param appkeyId
	 * @param diskId
	 * @param diskName
	 * @param status
	 * @param volumeType
	 * @param volumeUsageType
	 * @param backUp
	 * @param top
	 * @return
	 */
	public Boolean modifyImageBack(String appkeyId, String diskId, String diskName, String status, String volumeType, String volumeUsageType, String backUp, String top);
}
