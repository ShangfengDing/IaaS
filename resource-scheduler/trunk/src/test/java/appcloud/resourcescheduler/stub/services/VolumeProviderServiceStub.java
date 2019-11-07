// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.services;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmImageBack;
import appcloud.common.model.VmVolume;
import appcloud.common.service.VolumeProviderService;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

import java.io.IOException;

;

public class VolumeProviderServiceStub implements VolumeProviderService{

	@Override
	public VmVolume createVolume(String routingKey, String uuid,
			String imageServerHost, String baseImage, Float blockSize,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume createWindowsVolume(String routingKey, String uuid, String imageServerHost, String baseImage, Float blockSize, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmImageBack createVolumeImageBack(String routingKey, String backUuid, String activeUuid, Float size, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmVolume deleteVolume(String routingKey, String uuid,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume createSnapshot(String routingKey, String uuid,
			String snapshotTag, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume applySnapshot(String routingKey, String uuid,
			String snapshotTag, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume deleteSnapshot(String routingKey, String uuid,
			String snapshotTag, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume resizeRawImg(String routingKey, String uuid,
			Float blockSize, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume convertImgFormat(String routingKey, String targetFormat,
			String uuid, String destUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume releaseImg(String routingKey, String uuid, String host,
			String destPath, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume authorizeImg(String routingKey, String sourcefilePath, String hostIp, String destPath, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public VmVolume copyImg(String routingKey, String uuid, String host,
			String destUuid, String baseImage, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume downloadImg(String routingKey, String host, String srcPath,
			Boolean overwrite, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 90)
	public VmVolume revertVolume(String routingKey, String uuid,
			String imageServerHost, String baseImage, Float blockSize,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume modVmPasswd(String routingKey, String uuid, String name,
			String newPasswd, VmImageOSTypeEnum OSType, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImgMd5sum(String routingKey, String imageUuid,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gainImgMd5sum(String routingKey, VmVolume volume,
			VmImage image, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteImage(String routingKey, String path)
			throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String KeepAlive(String routingKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmVolume modVmHostName(String routingKey, String uuid,
			String newHostName, VmImageOSTypeEnum OSType, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
