// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.services;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.*;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.tools.RpcException;

import java.io.IOException;
import java.util.List;

;

public class VolumeSchedulerServiceStub implements VolumeSchedulerService{

	@Override
	public VmVolume defineVolume(VmVolumeUsageTypeEnum usagetype,
			Integer userId, int size, VmZone zone, String imageUUID, Host host,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume defineVolumeOnImageBack(VmVolumeUsageTypeEnum usagetype, Integer userId, int size, VmZone zone, String imageBackUUID, Host host, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmImageBack defineVolumeImageBack(String volumeUuid, String displayname, String des, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmVolume createVolume(String volumeUUID, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume createDataVolume(String volumeUUID, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmVolume createVolumeOnImageBack(String volumeUUID, VmImageBack imageBack, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmImageBack createVolumeImageBack(String volumeUUID, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmVolume deleteVolume(String volumeUUID, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyVolume(String volumeUUID, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyVolumeImageBack(String volumeUUID, RpcExtra rpcExtra) throws RpcException {

	}

	@Override
	public VmVolume resizeVolume(String volumeUUID, Integer size,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume cloneVolume(String srcVolumeUUID, String destVolumeUUID,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume moveVolume(String volumeUUID, Host host, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmSnapshot defineSnapshot(String volumeUUID, String name,
			String displayDescription, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmSnapshot createSnapshot(String volumeUUID, Integer snapshotId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmSnapshot deleteSnapshot(String volumeUUID, Integer snapshotId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmSnapshot applySnapshot(String volumeUUID, Integer snapshotId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public VmImage publishImage(String volumeUUID, VmImage image,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	@RPCTimeout(timeout = 120)
	public VmVolume revertVolume(String volumeUUID, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Host> selectHost(Cluster cluster, int size, Host exhost,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume modVmPasswd(String volumeUUID, String name,
			String newPasswd, VmImageOSTypeEnum OSType, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmImage publishImage(String volumeUUID, VmImage image,
			Integer clusterId, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String strClusterIds(List<Integer> groupIdList, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume convertImgFormat(String volumeUUID, VmImage image,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmImage uploadImage(String volumeUUID, VmImage image,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmImage authorizeImage(String sourceDir, VmImage image, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public void deleteImageVolume(String volumeUUID, VmImage image,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> gainClusterIdList(List<Integer> imageGroupIdList,
			List<Integer> groupIdList, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String gainImageMd5sum(String volumeUUID, VmImage image,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> gainImageGroupIds(String imageMd5sum, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateImage(List<VmImage> newImages, String volumeUUID, VmImage.VmImageStatusEnum status,
			RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteImageOnServer(String imageUuid, String path)
			throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String KeepAlive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume modVmHostName(String volumeUUID, String newHostName,
			VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
