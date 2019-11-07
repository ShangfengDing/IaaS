package appcloud.vp;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.service.VolumeProviderService;
import appcloud.common.util.io.NFSUtil;
import appcloud.rpc.tools.RpcException;
import appcloud.vp.qemuimg.VolumeManager;
import appcloud.vp.util.CommandResult;
import appcloud.vp.util.FileSystemUtil;

public class VolumeProvider implements VolumeProviderService {

	private static Logger logger = Logger.getLogger(VolumeProvider.class);

	public VmVolume createVolume(String routingKey, String uuid,
			String imageServerHost, String baseImage, Float blockSize)
			throws RpcException {
		System.out.println("requset got:");
		logger.info("creating volume: " + uuid + " imageServerHost:" +imageServerHost + " baseImage:" + baseImage + " blockSize:"+blockSize);
		VmVolume result = new VmVolume();
		
		String fmt = VmVolumeTypeEnum.QCOW2.toString();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			File dir = new File(NFSUtil.DEFAULT_NFS_SRV_PATH + Conf.VOLUME_PREFIX + destImgDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().createVolume(fmt,
					volumefilePath, imageServerHost,baseImage, blockSize);
			
					
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Create volume failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				result.setVolumeType(VmVolumeTypeEnum.QCOW2);
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}

		return result;
	}

	public VmVolume deleteVolume(String routingKey, String uuid)
			throws RpcException {
		logger.info("deleting volume: " + uuid );
		
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().deleteVolume(
					volumefilePath);
			
					
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Delete volume failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		return result;
	}

	

	public VmVolume createSnapshot(String routingKey, String uuid,
			String snapshotTag) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().createSnapshot(snapshotTag, volumefilePath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Create snapshot: " +snapshotTag+ " failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				commandResult.setOutput("Create snapshot:"+ snapshotTag+ " successfully");
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume applySnapshot(String routingKey, String uuid,
			String snapshotTag) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().applySnapshot(snapshotTag, volumefilePath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Apply snapshot: " +snapshotTag+ " failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				commandResult.setOutput("Apply snapshot:"+ snapshotTag+ " successfully");
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume deleteSnapshot(String routingKey, String uuid,
			String snapshotTag) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().deleteSnapshot(snapshotTag, volumefilePath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Delete snapshot: " +snapshotTag+ " failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				commandResult.setOutput("Delete snapshot:"+ snapshotTag + " successfully");
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume resizeRawImg(String routingKey, String uuid,
			Float blockSize) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().resizeRawImg(volumefilePath, blockSize);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Resize image failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				result.setMessage(commandResult.toString());
				
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume convertImgFormat(String routingKey, String targetFormat,
			String uuid, String destUuid) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String srcImgDir = FileSystemUtil.getImgDir(uuid);
			String srcVolumefilePath = Conf.VOLUME_PREFIX + srcImgDir+uuid+Conf.VOLUME_SUFFIX;
			
			String destImgDir = FileSystemUtil.getImgDir(destUuid);
			String destVolumefilePath = Conf.VOLUME_PREFIX + destImgDir+destUuid+Conf.VOLUME_SUFFIX;
			
			CommandResult commandResult = new VolumeManager().convertImgFormat(targetFormat,srcVolumefilePath,destVolumefilePath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Resize image failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,destVolumefilePath);
				result.setMessage(commandResult.toString());
				if(targetFormat != null) {
					result.setVolumeType(VmVolumeTypeEnum.valueOf(targetFormat.toUpperCase()));
				}
				
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume releaseImg(String routingKey, String uuid, String host,
			String destPath) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().releaseImg(volumefilePath, host, destPath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Release image failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(host,NFSUtil.DEFAULT_NFS_SRV_PATH,destPath);
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}

	public VmVolume copyImg(String routingKey, String uuid, String host,
			String destUuid, String baseImage) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String srcImgDir = FileSystemUtil.getImgDir(uuid);
			String srcVolumefilePath = Conf.VOLUME_PREFIX + srcImgDir+uuid+Conf.VOLUME_SUFFIX;
			
			String destImgDir = FileSystemUtil.getImgDir(destUuid);
			String destVolumefilePath = Conf.VOLUME_PREFIX + destImgDir+destUuid+Conf.VOLUME_SUFFIX;
			if(host == null) {
				host = Conf.VOLUME_PROVIDER_HOST;
			}
			CommandResult commandResult = new VolumeManager().copyImg(srcVolumefilePath,host,destVolumefilePath,baseImage);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Resize image failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				
				result.setProviderLocation(host,NFSUtil.DEFAULT_NFS_SRV_PATH,destVolumefilePath);
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}
	
	@Deprecated
	public VmVolume moveImg(String routingKey, String uuid, String host) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destImgDir = FileSystemUtil.getImgDir(uuid);
			String volumefilePath = Conf.VOLUME_PREFIX + destImgDir+uuid+Conf.VOLUME_SUFFIX;
			CommandResult commandResult = new VolumeManager().moveImg(volumefilePath,host);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("Resize image failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(host,NFSUtil.DEFAULT_NFS_SRV_PATH,volumefilePath);
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}
	
	public VmVolume copyIso(String routingKey,String host, String srcPath, String destFileName) throws RpcException {
		VmVolume result = new VmVolume();
		try {
			String destIsoDir = FileSystemUtil.getImgDir(destFileName);
			String isofilePath = Conf.ISO_PREFIX + destIsoDir+destFileName;
			CommandResult commandResult = new VolumeManager().copyIso(host, srcPath, isofilePath);
			if (commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !VolumeManager.isEmptyString(commandResult.getError())) {
				logger.error("copy iso failed! Caused by "
						+ commandResult.getOutput() + " |"
						+ commandResult.getError());
				result.setMessage(commandResult.toString());
				throw new RpcException(commandResult.toString());
			} else {
				result.setProviderLocation(Conf.VOLUME_PROVIDER_HOST,NFSUtil.DEFAULT_NFS_SRV_PATH,isofilePath);
				result.setMessage(commandResult.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("IOException",e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
			throw new RpcException("InterruptedException",e);
		}
		logger.info("result: "+result.toString());
		return result;
	}
	
	public static void main(String[] args) throws RpcException {
		long starttime = System.currentTimeMillis();
		//VmVolume result = new VolumeProvider().createVolume("123", "87654321", "images/c/e/rhel.img", (float)5.5, null);
		VmVolume result = new VmVolume();
		if("createVolume".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().createVolume("now unused", args[1], args[2], args[3], null);
		} else if("deleteVolume".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().deleteVolume("now unused", args[1]);
		} else if("createSnapshot".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().createSnapshot("now unused", args[1], args[2]);
		} else if("applySnapshot".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().applySnapshot("now unused", args[1], args[2]);
		} else if("deleteSnapshot".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().deleteSnapshot("now unused", args[1], args[2]);
		} else if("convertImgFormat".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().convertImgFormat("now unused", args[1], args[2], args[3]);
		} else if("copyImg".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().copyImg("now unused", args[1], args[2], args[3], args[4]);
		} else if("releaseImg".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().releaseImg("now unused", args[1], args[2], args[3]);
		} else if("resizeRawImg".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().resizeRawImg("now unused", args[1], Float.parseFloat(args[2]));
		} else if("copyIso".equalsIgnoreCase(args[0])) {
			result = new VolumeProvider().copyIso("now unused", args[1], args[2], args[3]);
		}else if("--help".equalsIgnoreCase(args[0])) {
			System.out.println("DESCRIPTION:");
			System.out.println("createVolume uuid imageHost images/c/e/rhel.img");
			System.out.println("deleteVolume uuid");
			System.out.println("createSnapshot uuid snapshotTag");
			System.out.println("applySnapshot uuid snapshotTag");
			System.out.println("deleteSnapshot uuid snapShotTag");
			System.out.println("convertImgFormat fmt uuid destUuid");
			System.out.println("copyImg uuid volumeHost destUuid baseImg");
			System.out.println("releaseImg uuid imageHost images/d/e/user.img");
			System.out.println("resizeRawImg uuid blockSize");
			System.out.println("copyIso imghost srcIsoPath[iso/a/c/ubuntu.iso] destIsoName[ubuntu.iso]");
		} else {
			System.out.print("not supported opperation!");
			System.out.print("Try '--help' for more information.");
		}
		
		logger.info("result: "+result.toString());
		logger.info(args[0] + " takes time:" + (System.currentTimeMillis() - starttime));
	}

	
}
