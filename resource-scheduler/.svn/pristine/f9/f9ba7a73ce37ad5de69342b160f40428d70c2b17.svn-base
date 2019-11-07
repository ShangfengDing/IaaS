// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.services;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.common.service.ImageServerService;
import appcloud.rpc.tools.RpcException;

;

public class ImageServerServiceStub implements ImageServerService{
	
	@Override
	public VmImage createImage(VmImage arg0, RpcExtra rpcExtra){
		return new VmImage();
	}

	@Override
	public VmImage authorizeImage(VmImage newImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException {
		return null;
	}

	@Override
	public VmImage updateAuthorizeImage(VmImage updateImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException {
		return null;
	}

	@Override
	public VmImage getDownloadImage(String arg0, RpcExtra rpcExtra){
		return new VmImage();
	}
	/* (non-Javadoc)
	 * @see appcloud.common.service.ImageServerService#deleteImage(java.lang.String)
	 */
	/*@Override
	public void deleteImage(String uuid, RpcExtra rpcExtra) {
		// TODO Auto-generated method stub
		
	}*/
	
	@Override
	public VmImage preCreateImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VmImage preCreateImage(VmImage vmImage) throws IllegalRpcArgumentException, RpcException {
		return null;
	}

	@Override
	public boolean deleteImageOnTable(String uuid, String groupId,
			boolean used, RpcExtra rpcExtra)
			throws IllegalRpcArgumentException, RpcException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String judgeUsed(String imageUuid, String groupId, RpcExtra rpcExtra) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean deleteImage(String imageUuid, String groupId,
			RpcExtra rpcExtra) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String KeepAlive() {
		// TODO Auto-generated method stub
		return null;
	}

}
