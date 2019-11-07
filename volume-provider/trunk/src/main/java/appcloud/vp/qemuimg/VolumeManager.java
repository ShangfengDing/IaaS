package appcloud.vp.qemuimg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.util.StringUtil;
import appcloud.common.util.io.*;
import appcloud.vp.Conf;
import appcloud.vp.QemuShell;
import appcloud.vp.util.CommandHelper;
import appcloud.vp.util.CommandResult;
import appcloud.vp.util.FileSystemUtil;
import appcloud.vp.util.FileUtil;
import appcloud.vp.util.VolumeFormatUtil;

public class VolumeManager {

	private static Logger logger = Logger.getLogger(VolumeManager.class);
	
	/***
	 * @param fmt
	 *            example: qcow2 or raw
	 * @param outPutFile
	 *            example: volumes/a/c/3435435.img
	 * @param backingFile
	 *            example: images/c/d/3434dd.img
	 * @param imageServer
	 *            example: 192.168.11.132
	 * @param blockSize
	 *            example: 4.5 or null
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public CommandResult createVolume(String fmt, String outPutFile,
			String imageServerHost, String backingFile, Float blockSize)
			throws IOException{
		logger.info("VolumeManage :"+fmt+", outputFile:"+ outPutFile+", imageServerHost:"+imageServerHost+", backingFile:"+backingFile+",blockSize: "+blockSize);
		CommandResult result = new CommandResult();		
		Conf.initMountFs(imageServerHost);
		
		if(!isEmptyString(backingFile)){			
			// qemu-img create -f qcow2 /volumes/uuid.img -o backing_file=/images/rhel.img 4.5G
			if (isEmptyString(fmt) || isEmptyString(outPutFile)
					|| isEmptyString(backingFile) || isEmptyString(imageServerHost)) {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setOutput("Example: " + QemuShell.CREATE_VOLUME_OS_WITH_BLOCKSIZE);
				result.setError("Lack of parameter");
				return result;
			}
			
			if (!VolumeFormatUtil.ValidImageFS(fmt)) {
				result.setError(fmt + " image system not support!");
				return result;
			}
		}
		
		if (FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile)) {
			result.setError("outPutImage "
					+ NFSUtil.DEFAULT_NFS_SRV_PATH
					+ outPutFile
					+ " exist. In order to prevent cover, please change its name!");
			return result;
		}
		
		//aim to create a disk space, blocksize needed
		if(backingFile == null) {
			if (blockSize == null || blockSize <= 0) {
				result.setError("blockSize:parameter invalid, Image creation needs a size parameter");
				return result;
			}
			String format = QemuShell.CREATE_VOLUME_DISK;
			//String format = "qemu-img create -f %s %s %sG";
			String command = String.format(format, fmt,
					NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
					blockSize.intValue());

			CommandHelper commandHelper = new CommandHelper();
			result = commandHelper.exec(command);
			return result;
			
		}
		dealBackingFile(imageServerHost, backingFile);
		
		if (blockSize != null && blockSize <= 0) {
			result.setError("blockSize:parameter invalid");
			return result;
		}
		FileSystemUtil.createDirIfNotExist(NFSUtil.DEFAULT_NFS_SRV_PATH
				+ outPutFile);
		String format = QemuShell.CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE; 
        //"qemu-img create -f %s %s -b %s";
		
		String command = String.format(format, fmt,
				NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
				Conf.IMG_PREFIX + backingFile);
		
		if (blockSize != null) {
			command += (" " + blockSize.intValue() + "G");
		}
		int lastIndex = (NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile).lastIndexOf(FileSystemUtil.FILE_SEPARATOR);
		if(lastIndex == -1) {
			result.setError("unable to redirect to the right place");
			logger.info("unable to redirect to the right place");
			return result;
		}
		
		command = "cd " + (NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile).substring(0,lastIndex) +  " && " + command;

		CommandHelper commandHelper = new CommandHelper();
		
		logger.info(command);
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult createWindowsVolume(String outPutFile, String imageServerHost, String backingFile)
			throws IOException{
		logger.info("Windows volume outputFile:"+ outPutFile+", imageServerHost:"+imageServerHost+", backingFile:"+backingFile);
		CommandResult result = new CommandResult();
		Conf.initMountFs(imageServerHost);

		if(!isEmptyString(backingFile)){
			if (isEmptyString(outPutFile) || isEmptyString(backingFile) || isEmptyString(imageServerHost)) {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setOutput("Example: " + QemuShell.CREATE_VOLUME_OS_WITH_BLOCKSIZE);
				result.setError("Lack of parameter");
				return result;
			}
		}

		if (FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile)) {
			result.setError("outPutImage "
					+ NFSUtil.DEFAULT_NFS_SRV_PATH
					+ outPutFile
					+ " exist. In order to prevent cover, please change its name!");
			return result;
		}
		dealBackingFile(imageServerHost, backingFile);

		String realbackingFile = getRealBackingFile(backingFile);

		String command = "cp " + NFSUtil.DEFAULT_NFS_SRV_PATH + realbackingFile + " " + NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile;
		logger.info(command);
		CommandHelper commandHelper = new CommandHelper();
		result = commandHelper.exec(command);
		return result;


	}

	/**
	 * 创建硬盘的增量镜像
	 * @param fmt 磁盘镜像格式，统一是qcow2
	 * @param activePathFile 硬盘将要创建的增量镜像
	 * @param imageBackFile 硬盘正在使用的磁盘镜像（activeimage会成为imageback，新创建出来的镜像作为硬盘当前镜像）
	 * @return
	 */
	public CommandResult createVolumeImageBack(String fmt,String activePathFile,String imageBackFile,Float blockSize) throws Exception{
		logger.info("VolumeManage createVolumeImageBack:"+fmt+", activePathFile:"+ activePathFile+", imageBackFile:"+imageBackFile+",blockSize: "+blockSize);
		CommandResult result = new CommandResult();

		//检查字符串
		if (isEmptyString(fmt) || isEmptyString(activePathFile)
					|| isEmptyString(imageBackFile)) {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setOutput("Example: " + QemuShell.CREATE_VOLUME_OS_WITH_BLOCKSIZE);
				result.setError("Lack of parameter");
				return result;
		}

			//检查镜像格式
		if (!VolumeFormatUtil.ValidImageFS(fmt)) {
			result.setError(fmt + " image system not support!");
			return result;
		}

			//检查activePathFile是否已经存在了
		if (FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + activePathFile)) {
			result.setError("imageBackFile "
						+ NFSUtil.DEFAULT_NFS_SRV_PATH
						+ imageBackFile
						+ " exit. In order to prevent cover, please change its name");
			return result;
		}

		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imageBackFile)) {
			result.setError("imageBackFile "
					+ NFSUtil.DEFAULT_NFS_SRV_PATH
					+ activePathFile
					+ "does not exits,can not create backfile");
		}

			//检查size是否符合要求
		if (blockSize == null || blockSize <= 0) {
				result.setError("blockSize:parameter invalid, Image creation needs a size parameter");
				return result;
		}

		FileSystemUtil.createDirIfNotExist(NFSUtil.DEFAULT_NFS_SRV_PATH
				+ activePathFile);

		//qemu-img create -f %s %s -b %s %sG
		String format = QemuShell.CREATE_VOLUME_OS_WITH_BLOCKSIZE;

		String command = String.format(format,fmt,NFSUtil.DEFAULT_NFS_SRV_PATH + activePathFile,NFSUtil.DEFAULT_NFS_SRV_PATH + imageBackFile,blockSize.intValue());

		int lastIndex = (NFSUtil.DEFAULT_NFS_SRV_PATH + activePathFile).lastIndexOf(FileSystemUtil.FILE_SEPARATOR);
		if(lastIndex == -1) {
			result.setError("unable to redirect to the right place");
			return result;
		}

		command = "cd " + (NFSUtil.DEFAULT_NFS_SRV_PATH + activePathFile).substring(0,lastIndex) + " && " + command;

		CommandHelper commandHelper = new CommandHelper();

		logger.info(command);
		result = commandHelper.exec(command);
		return result;
	}

	private String getRealBackingFile(String backingFile) {//  images/a/b/centos#@#1.img
		if (backingFile.contains(FileSystemUtil.SPLIT_STRING)) {
			String[] splitBackingFile = backingFile.split(FileSystemUtil.SPLIT_STRING);
			String suffix = backingFile.substring(backingFile.lastIndexOf('.'), backingFile.length());
			backingFile = splitBackingFile[0]+suffix;
		} 
		return backingFile;
	}

	/**
	 * @param imageServerHost: 192.168.1.24
	 * @param backingFile: 192.168.1.24:/srv/nfs4/:volumes/v/u/a5fee72f970b479cb9febf416c07a3cc.img
	 * @throws IOException
	 */
	private void dealBackingFile(String imageServerHost,String backingFile) throws IOException {
		logger.info("backingFile"+backingFile+" ; imageHost:"+imageServerHost);
		String realbackingFile = getRealBackingFile(backingFile);
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile)) {
			if (realbackingFile.equals(backingFile)) {//从服务器上拷贝母镜像
				if (NFSUtil.fileExists(imageServerHost, backingFile)) {
					FileUtil.copy(NFSUtil.getPath(imageServerHost, backingFile),
							NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					CommandHelper commandHelperChmod = new CommandHelper();
					CommandResult resultChmod = commandHelperChmod.exec("chmod 666 " + NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					
					if (!isEmptyString(resultChmod.getError())) {
						throw new IOException(resultChmod.getError());
					}
				} else {
					throw new IOException("backingFile:" + NFSUtil.DEFAULT_NFS_SRV_PATH
							+ backingFile + " not found in local filesystem or nfs!");
				}
			} else {//从本机拷贝母镜像
				if (FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + realbackingFile)) {
					FileUtil.copy(NFSUtil.DEFAULT_NFS_SRV_PATH + realbackingFile,
							NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					CommandHelper commandHelperChmod = new CommandHelper();
					CommandResult resultChmod = commandHelperChmod.exec("chmod 666 " + NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					
					if (!isEmptyString(resultChmod.getError())) {
						throw new IOException(resultChmod.getError());
					}
				} else {
					FileUtil.copy(NFSUtil.getPath(imageServerHost, realbackingFile),
							NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					CommandHelper commandHelperChmod = new CommandHelper();
					CommandResult resultChmod = commandHelperChmod.exec("chmod 666 " + NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
					
					if (!isEmptyString(resultChmod.getError())) {
						throw new IOException(resultChmod.getError());
					}
//					throw new IOException("backingFile:" + NFSUtil.DEFAULT_NFS_SRV_PATH
//							+ backingFile + " not found in local filesystem or nfs!");
				}
			}
		}
	}

	public CommandResult deleteVolume(String volumefilePath)
			throws IOException {
		CommandResult result = new CommandResult();
		if (isEmptyString(volumefilePath)
				|| !volumefilePath.endsWith(Conf.VOLUME_SUFFIX)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath)) {
			result.setOutput("The image not exist, not need to delete");
			return result;
		}
		
		boolean deleteFlag = new File(NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath).delete();
		if(deleteFlag) {
			result.setOutput("delete volume " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + " successfully");
		} else {
			result.setError("delete volume " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + " failed");
		}
//		String format = "rm -f %s";
//		String command = String.format(format, NFSUtil.DEFAULT_NFS_SRV_PATH
//				+ volumefilePath);
//		CommandHelper commandHelper = new CommandHelper();
//		
//
//		result = commandHelper.exec(command);
//		if (isEmptyString(result.getError())) {
//			result.setOutput("delete volume successfully");
//		}
		return result;
	}

	public CommandResult createSnapshot(String snapshotTag, String imgFilePath)
			throws IOException {
		// qemu-img snapshot -c vm-snapshot vm1.img
		CommandResult result = new CommandResult();
		if (isEmptyString(snapshotTag) || isEmptyString(imgFilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.CREATE_SNAPSHOT);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ " not exist!");
			return result;
		}
		if(!VolumeFormatUtil.ValidSnapShotTag(snapshotTag)) {
			result.setError("snapshotTag:" +snapshotTag + " invalid, only can contain a-z|A-Z|0-9|_|-");
			return result;
		}

		Map<Integer, String> snapshotTags = new HashMap<Integer, String>();
		CommandResult snapshotTagsExistResult = new VolumeManager()
				.ListSnapshots(imgFilePath, snapshotTags);
		if (isEmptyString(snapshotTagsExistResult.getError())) {
			logger.error(snapshotTagsExistResult.toString());
		}
		
		if (snapshotTags.containsValue(snapshotTag)) {
			result.setError(snapshotTag + " exists in "
					+ NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ ",please change a snapshotTag");
			return result;
		}

		String format = QemuShell.CREATE_SNAPSHOT; 
				//"qemu-img snapshot -c %s %s";
		String command = String.format(format, snapshotTag,
				NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath);
		CommandHelper commandHelper = new CommandHelper();

		
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult applySnapshot(String snapshotTag, String imgFilePath)
			throws IOException {
		// qemu-img snapshot -a vm-snapshot vm.img
		CommandResult result = new CommandResult();
		if (isEmptyString(snapshotTag) || isEmptyString(imgFilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.APPLY_SNAPSHOT);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ " not exist!");
			return result;
		}
		String format = QemuShell.APPLY_SNAPSHOT;
		//"qemu-img snapshot -a %s %s";
		String command = String.format(format, snapshotTag,
				NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath);
		CommandHelper commandHelper = new CommandHelper();

		
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult deleteSnapshot(String snapshotTag, String imgFilePath)
			throws IOException {
		// qemu-img snapshot -a vm-snapshot vm.img
		CommandResult result = new CommandResult();
		if (isEmptyString(snapshotTag) || isEmptyString(imgFilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.DELETE_SNAPSHOT);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ " not exist!");
			return result;
		}
		String format = QemuShell.DELETE_SNAPSHOT;
		//"qemu-img snapshot -d %s %s";
		Map<Integer, String> snapshotTags = new HashMap<Integer, String>();
		CommandResult snapshotTagsExistResult = new VolumeManager()
				.ListSnapshots(imgFilePath, snapshotTags);
		if (isEmptyString(snapshotTagsExistResult.getError())) {
			logger.error(snapshotTagsExistResult.toString());
		}
		int snapCount = snapshotTags.size();
		for (int snapIndex = 0; snapIndex < snapCount; snapIndex++) {
			String command = String.format(format, snapshotTag,
					NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath);
			CommandHelper commandHelper = new CommandHelper();

			
			result = commandHelper.exec(command);
			if (!isEmptyString(result.getError())) {
				return result;
			}
		}
		return result;
	}

	public CommandResult ListSnapshots(String imgFilePath,
			Map<Integer, String> snapshotTags) throws IOException {
		// qemu-img snapshot -a vm-snapshot vm.img
		CommandResult result = new CommandResult();
		if (isEmptyString(imgFilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.LIST_SNAPSHOT);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ " not exist!");
			return result;
		}
		if (snapshotTags == null) {
			result.setError("please initial the params snapshotTags: new HashMap<Integer,String>()");
			return result;
		}
		StringBuilder command = new StringBuilder();
		command.append(QemuShell.LIST_SNAPSHOT);
		command.append(" " + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath);
		command.append("| awk '$1~/^[0-9]+$/' | awk {'print $1,$2'}");
		CommandHelper commandHelper = new CommandHelper();

		logger.info("excute command:" + command.toString());
		// snapshotTags.put(key, value);
		result = commandHelper.exec(command.toString());
		if (result.getExitValue() != CommandResult.EXIT_VALUE_TIMEOUT
				&& isEmptyString(result.getError())
				&& !isEmptyString(result.getOutput())) {
			String tagString = result.getOutput();
			Pattern p = Pattern.compile("([0-9]+\\s)([\\w|\\_|\\-]+)");
			Matcher m = p.matcher(tagString);
			while (m.find()) {
				String[] entry = m.group(0).split("\\s");
				if(Integer.parseInt(entry[0]) != 0) {
					snapshotTags.put(Integer.parseInt(entry[0]), entry[1]);
				}
			}
		}
		return result;
	}

	public CommandResult resizeRawImg(String imgFilePath, Float blockSize)
			throws IOException {
		CommandResult result = new CommandResult();
		if (isEmptyString(imgFilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.RESIZE_IMG);
			result.setError("Lack of parameter");
			return result;
		}
		if (blockSize == null || blockSize <= 0) {
			result.setError("blockSize: parameter invalid, should be bigger than zero");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath
					+ " not exist!");
			return result;
		}
		String format = QemuShell.RESIZE_IMG;
		//"qemu-img resize %s +%sG";
		String command = String.format(format, NFSUtil.DEFAULT_NFS_SRV_PATH + imgFilePath, blockSize.toString());
		CommandHelper commandHelper = new CommandHelper();

		
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult convertImgFormat(String fmt,
			String srcVolumefilePath, String destVolumefilePath)
			throws IOException {
		CommandResult result = new CommandResult();
		if (isEmptyString(srcVolumefilePath) || isEmptyString(destVolumefilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setOutput("Example: " + QemuShell.CONVERT_IMG);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath
					+ " not exist!");
			return result;
		}
		if(!VolumeFormatUtil.ValidImageFS(fmt)) {
			result.setError(fmt + " image system not support!");
			return result;
		}
		if(FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + destVolumefilePath)) {
			result.setError("dest img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + destVolumefilePath
					+ " already exist! Please change a name.");
			return result;
		}
		FileSystemUtil.createDirIfNotExist(NFSUtil.DEFAULT_NFS_SRV_PATH + destVolumefilePath);
		String format = QemuShell.CONVERT_IMG;
		//"qemu-img convert -O %s %s %s";
		String command = String.format(format,fmt,NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath,NFSUtil.DEFAULT_NFS_SRV_PATH + destVolumefilePath );
		CommandHelper commandHelper = new CommandHelper();

		
		result = commandHelper.exec(command);
		return result;
	}

	/**
	 * TODO 修改成共用的发布
	 * @param sourcefilePath  example: volume:images/a/z……
	 * @param imageHost  主机ip
	 * @param destPath  images/a/x/……
	 * @return
	 * @throws IOException
	 */
	public CommandResult releaseImg(String sourcefilePath, String imageHost,
			String destPath) throws IOException {
		
		CommandResult result = new CommandResult();
		Conf.initMountFs(imageHost);
		if (isEmptyString(sourcefilePath) || isEmptyString(imageHost)|| isEmptyString(destPath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if(!NFSUtil.isMounted(imageHost)) {
			boolean commandResult  = NFSUtil.mount(imageHost, NFSUtil.DEFAULT_NFS_SRV_PATH);
			if (commandResult) {
				logger.info("mount " + imageHost + " successfully!");
			} else {
				result.setError("mount " + imageHost + " failed!");
				return result;
			}
		}
		
		String destImgPath = NFSUtil.getPath(imageHost, destPath);  // nfs目的地址
		if(FileUtil.exists(destImgPath)) {
			FileUtil.delete(destImgPath);
//			result.setError("dest img:" + destImgPath
//					+ " already exist! Please change a name.");
//			return result;
		}
		
		boolean createDir = FileSystemUtil.createDirIfNotExist(destImgPath);
		if(!createDir) {
			result.setError("copy " + NFSUtil.DEFAULT_NFS_SRV_PATH + sourcefilePath + " to " + destImgPath + " failed.");
		}
		
		try {
			FileUtils.copyFile(new File(NFSUtil.DEFAULT_NFS_SRV_PATH+sourcefilePath), new File(destImgPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("copy " + NFSUtil.DEFAULT_NFS_SRV_PATH + sourcefilePath + " to " + destImgPath + " failed.");
		}		
		
//		String format = "cp %s %s";
//		String command = String.format(format, NFSUtil.DEFAULT_NFS_SRV_PATH+volumefilePath, destImgPath);
//		CommandHelper commandHelper = new CommandHelper();
//
//		
//		result = commandHelper.exec(command);
		return result;
	}

	@Deprecated
	public CommandResult moveImg(String volumefilePath, String volumeHost)
			throws IOException {
		Conf.initMountFs(volumeHost);
		CommandResult result = new CommandResult();
		if (isEmptyString(volumefilePath) || isEmptyString(volumeHost)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if(!NFSUtil.isMounted(volumeHost)) {
			boolean commandResult  = NFSUtil.mount(volumeHost, NFSUtil.DEFAULT_NFS_SRV_PATH);
			if (commandResult) {
				logger.info("mount " + volumeHost + " successfully!");
			} else {
				result.setError("mount " + volumeHost + " failed!");
				return result;
			}
		}		
		
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath
					+ " not exist!");
			return result;
		}
		
		String destImgPath = NFSUtil.getPath(volumeHost, volumefilePath);
		if(FileUtil.exists(destImgPath)) {
			result.setError("dest img:" + destImgPath
					+ " already exist! Please change a name.");
			return result;
		}
		boolean createDir = FileSystemUtil.createDirIfNotExist(destImgPath);
		if(!createDir) {
			result.setError("move " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + "to " + destImgPath + " failed.");
		}
		
		try {
			FileUtils.moveFile(new File(NFSUtil.DEFAULT_NFS_SRV_PATH+volumefilePath), new File(destImgPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("move " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + " to " + destImgPath + " failed.");
		}
		
//		String format = "mv %s %s";
//		String command = String.format(format, NFSUtil.DEFAULT_NFS_SRV_PATH+volumefilePath, destImgPath);
//		CommandHelper commandHelper = new CommandHelper();
//		result = commandHelper.exec(command);
		
		return result;
	}
	
	public CommandResult copyImg(String srcVolumefilePath, String volumeHost,
			String destVolumefilePath, String baseImage) throws IOException {
		CommandResult result = new CommandResult();
		if (isEmptyString(srcVolumefilePath) || isEmptyString(destVolumefilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		Conf.initMountFs(volumeHost);
		
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath)) {
			result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath
					+ " not exist!");
			return result;
		}
		
		String destImgPath = NFSUtil.getPath(volumeHost, destVolumefilePath);
		if(FileUtil.exists(destImgPath)) {
			result.setError("dest img:" + destImgPath
					+ " already exist! Please change a name.");
			return result;
		}
		boolean createDir = FileSystemUtil.createDirIfNotExist(destImgPath);
		if(!createDir) {
			result.setError("move " + NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath + "to " + destImgPath + " failed.");
		}
		
		//拷贝volume
		try {
			FileUtils.copyFile(new File(NFSUtil.DEFAULT_NFS_SRV_PATH+srcVolumefilePath), new File(destImgPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("move " + NFSUtil.DEFAULT_NFS_SRV_PATH + srcVolumefilePath + " to " + destImgPath + " failed.");
		}
		
		//拷贝volume若成功，则按需拷贝母镜像
		if(!isEmptyString(baseImage)) {
			String destBaseImgPath = NFSUtil.getPath(volumeHost, baseImage);
			
			try {
				if (!isEmptyString(result.getError())) {
					if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH+baseImage)) {
						result.setError("img:" + NFSUtil.DEFAULT_NFS_SRV_PATH+baseImage
								+ " not exist!");
						//rollback
						new File(destImgPath).delete();
						return result;
					}
					FileUtils.copyFile(new File(NFSUtil.DEFAULT_NFS_SRV_PATH+baseImage), new File(destBaseImgPath));				
				}
				
			} catch (IOException e) {
				logger.error(e.getMessage());
				//rollback
				new File(destImgPath).delete();
				result.setError("move baseImg" + NFSUtil.DEFAULT_NFS_SRV_PATH+baseImage + " to " + destBaseImgPath + " failed.");
			}
		}
		return result;
	}
	
	/***
	 * 
	 * @param imageServerHost 
	 * @param srcImgFile  iso/a/b/ubuntu.iso
	 * @param destImgFile iso/a/b/ubuntu.iso
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public CommandResult downloadImg(String imageServerHost,String srcImgFile,String destImgFile, Boolean overwrite)
			throws IOException {
		CommandResult result = new CommandResult();
		
		if (isEmptyString(srcImgFile) || isEmptyString(destImgFile) || isEmptyString(imageServerHost)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		Conf.initMountFs(imageServerHost);
		
		String srcImgPath = NFSUtil.getPath(imageServerHost, srcImgFile);
		if(!FileUtil.exists(srcImgPath)) {
			result.setError("img:" + srcImgPath
					+ " not exist!");
			return result;
		}
			
		String destImgPath  = NFSUtil.DEFAULT_NFS_SRV_PATH + destImgFile;
		
		if (FileUtil.exists(destImgPath) && (overwrite==null || overwrite == false)) {
			result.setOutput("dest file:" + destImgPath + " already exists, not need to copy!");
			return result;
		}
		
		boolean createDir = FileSystemUtil.createDirIfNotExist(destImgPath);
		if(!createDir) {
			result.setError("create dir " + destImgPath + " failed.");
		}
		try {
			FileUtils.copyFile(new File(srcImgPath), new File(destImgPath));
			CommandHelper commandHelperChmod = new CommandHelper();
			CommandResult resultChmod = commandHelperChmod.exec("chmod 444 " +  destImgPath);
			
			if (!isEmptyString(resultChmod.getError())) {
				return resultChmod;
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("move " + srcImgPath + " to " + destImgPath + " failed.");
		}
		
		return result;
	}

	public static boolean isEmptyString(Object input) {
		if (input == null) {
			return true;
		} else if ("".equalsIgnoreCase(input.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public CommandResult revertVolume(String fmt, String outPutFile,
			String imageServerHost, String backingFile, Float blockSize)
			throws IOException{
		CommandResult result = new CommandResult();		
		Conf.initMountFs(imageServerHost);
		
		if(!isEmptyString(backingFile)){			
			// qemu-img create -f qcow2 /volumes/uuid.img -o backing_file=/images/rhel.img 4.5G
			if (isEmptyString(fmt) || isEmptyString(outPutFile)
					|| isEmptyString(backingFile) || isEmptyString(imageServerHost)) {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setOutput("Example: " + QemuShell.CREATE_VOLUME_OS_WITH_BLOCKSIZE);
				result.setError("Lack of parameter");
				return result;
			}
			
			if (!VolumeFormatUtil.ValidImageFS(fmt)) {
				result.setError(fmt + " image system not support!");
				return result;
			}
		}
		
		if (FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile)) {
			logger.info("delete exist file:"+NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile);
			FileUtils.forceDelete(new File(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile));
			//FileUtils.deleteDirectory(new File(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile));
			//FileUtils.deleteQuietly(new File(NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile));
//			result.setError("outPutImage "
//					+ NFSUtil.DEFAULT_NFS_SRV_PATH
//					+ outPutFile
//					+ " exist. In order to prevent cover, please change its name!");
//			return result;
		}
		
		//aim to create a disk space, blocksize needed
		if(backingFile == null) {
			if (blockSize == null || blockSize <= 0) {
				result.setError("blockSize:parameter invalid, Image creation needs a size parameter");
				return result;
			}
			String format = QemuShell.CREATE_VOLUME_DISK;
			//String format = "qemu-img create -f %s %s %sG";
			String command = String.format(format, fmt,
					NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
					blockSize.intValue());

			CommandHelper commandHelper = new CommandHelper();
			result = commandHelper.exec(command);
			return result;
			
		}
		dealBackingFile(imageServerHost,backingFile);
		
		if (blockSize != null && blockSize <= 0) {
			result.setError("blockSize:parameter invalid");
			return result;
		}
		FileSystemUtil.createDirIfNotExist(NFSUtil.DEFAULT_NFS_SRV_PATH
				+ outPutFile);
		String format = QemuShell.CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE; 
        //"qemu-img create -f %s %s -b %s";
		
		String command = String.format(format, fmt,
				NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
				Conf.IMG_PREFIX + backingFile);
		
		if (blockSize != null) {
			command += (" " + blockSize.intValue() + "G");
		}
		int lastIndex = (NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile).lastIndexOf(FileSystemUtil.FILE_SEPARATOR);
		if(lastIndex == -1) {
			result.setError("unable to redirect to the right place");
			return result;
		}
		
		command = "cd " + (NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile).substring(0,lastIndex) +  " && " + command;

		CommandHelper commandHelper = new CommandHelper();
		

		result = commandHelper.exec(command);
		return result;
	}
	
	/**
	 * modify vm hostname
	 * @param imgPath
	 * @param newHostName
	 * @return
	 * @throws IOException
	 */
	public CommandResult modVmHostName(String imgPath, String newHostName)
			throws IOException{
		CommandResult result = new CommandResult();		
		CommandHelper commandHelper = new CommandHelper();
		if (isEmptyString(imgPath) || !imgPath.endsWith(Conf.VOLUME_SUFFIX)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(imgPath)) {
			result.setError("The image not exist, not need to mod password");
			return result;
		}
		//判断操作系统类型
		String format = "virt-cat -a %s /etc/shadow";
		String OSTypeCmd = String.format(format, imgPath);
		result = commandHelper.exec(OSTypeCmd);
		if (result.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT) {
			return result;
		}
		if (result.getError() != null && result.getError().contains("error: case_sensitive_path: etc:")) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Windows can not modify hostname!");
			return result;
		} else {
			return linuxModHostName(imgPath, newHostName); // linux modify hostname
		}
	}
	
	//只实现了密码清空
	public CommandResult modVmPasswd(String imgPath, String name, String newPasswd)
			throws IOException{
		CommandResult result = new CommandResult();		
		CommandHelper commandHelper = new CommandHelper();
		if (isEmptyString(imgPath) || !imgPath.endsWith(Conf.VOLUME_SUFFIX)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(imgPath)) {
			result.setError("The image not exist, not need to mod password");
			return result;
		}
		//判断操作系统类型
		String format = "virt-cat -a %s /etc/shadow";
		String OSTypeCmd = String.format(format, imgPath);
		result = commandHelper.exec(OSTypeCmd);
		if (result.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT) {
			return result;
		}
		if (result.getError() != null && result.getError().contains("error: case_sensitive_path: etc:")) {
			return winModPasswd(imgPath, name);
		} else {
			return linuxModPasswd(imgPath, name, newPasswd);
		}
	}
	
	private CommandResult winModPasswd(String imgPath, String name) throws IOException {
		logger.info("mod passwd on Windows, imgPath is "+imgPath+", name is "+name);
		Pattern namePattern = Pattern.compile("^\\|\\s*?([\\d|\\w]{4})\\s*?\\|\\s*?(\\S+?)\\s*?\\|\\s*?([\\S|\\s]+?)\\s*?\\|\\s*?([\\S|\\s]+?)\\s*?\\|", Pattern.MULTILINE);
		CommandHelper commandHelper = new CommandHelper();
		boolean clearAll = false;
		if (name == null || name.trim().equals("")) {
			clearAll = true;
		} else if (name == "root" ) { //为了便于创建虚拟机的时候对管理员账号设定密码,采用统一账号命名
			name = "Administrator";
		}
		String SAMPath = "win:c:\\Windows\\System32\\config\\";
		String directory = imgPath.substring(0, imgPath.lastIndexOf('/')+1);
		String format = "virt-copy-out -a %s '"+SAMPath+"SAM' %s";
		String cmd = String.format(format, imgPath, directory);
		CommandResult result = commandHelper.exec(cmd);
		result = commandHelper.exec("chntpw -l "+directory+"SAM");
		if (result.getOutput() != null && result.getError() == null && result.getExitValue() == 0) {
			Matcher matcher = namePattern.matcher(result.getOutput());
			if (!clearAll) {//清空username的密码
				String lock = null;
				while(matcher.find()) {
					if (matcher.group(2).trim().equals(name)) {
						lock = matcher.group(4).trim();
						modSAM(lock, directory, name, imgPath, SAMPath);
						format = "virt-copy-in -a %s "+directory+"SAM  '"+SAMPath+"' && rm -f "+directory+"SAM";
						cmd = String.format(format, imgPath);
						result = commandHelper.exec(cmd);
						return result;
					}
				}
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setError("parameter error, no admin's name is "+name);
				new File(directory+"SAM").delete();//处理掉copy出来的SAM文件
				return result;
			} else {//清空除去Administrator以外的所有管理员密码
				while (matcher.find()) {
					if (matcher.group(3).trim().equals("ADMIN") && !matcher.group(2).trim().equals("Administrator")) {
						String lock = matcher.group(4).trim();
						modSAM(lock, directory, matcher.group(2).trim(), imgPath, SAMPath);
					}
				}
				format = "virt-copy-in -a %s "+directory+"SAM  '"+SAMPath+"' && rm -f "+directory+"SAM";
				cmd = String.format(format, imgPath);
				result = commandHelper.exec(cmd);
				return result;
			}
		}
		return result;
	}
	
	private CommandResult modSAM(String lock, String directory, String username, String imgPath, String SAMPath) throws IOException {
		String format, cmd;
		CommandResult commandResult;
		CommandHelper commandHelper = new CommandHelper();
		if (!lock.equals("")) {
			format = "echo -e '1\ny\n'  | chntpw -u %s "+directory+"SAM";
			cmd = String.format(format, username);
			commandResult = commandHelper.exec(cmd);
			if (commandResult.getError() != null || commandResult.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT) {
				return commandResult;
			}
			logger.info(commandResult);
		}
		format = "echo -e '1\ny\n'| chntpw -u %s "+directory+"SAM";
		cmd = String.format(format, username);
		commandResult = commandHelper.exec(cmd);
		logger.info(commandResult);
		return commandResult;
	}
	
	private CommandResult linuxModPasswd(String imgPath, String name, String newPassword) throws IOException {
		logger.info("mod passwd on Linux, imgPath is "+imgPath+", name is "+name+" newPassword is "+newPassword);
		CommandHelper commandHelper = new CommandHelper();
		String format = "virt-cat -a %s /etc/shadow | grep %s";
		String checkNameCmd = String.format(format, imgPath, name);
		CommandResult result = commandHelper.exec(checkNameCmd);
		if (result.getError() == null && result.getOutput() == null) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("parameter error, not admin's name is "+name);
			return result;
		} 
		if (null == newPassword || "".equals(newPassword.trim())) {
			format = "virt-edit -a %s /etc/shadow -e 's/^%s:[^:]*:/%s::/'";
		} else {
			Crypt crypt = new Crypt();
			@SuppressWarnings("static-access")
			String pwd = crypt.crypt(newPassword, "$6$e8skQbXe$");
			pwd = pwd.replace("$", "\\$");
			pwd = pwd.replace("/", "\\/");
			format = "virt-edit -a %s /etc/shadow -e 's/^%s:[^:]*:/%s:"+pwd+":/'";
		}
		String cmd = String.format(format, imgPath, name, name);
		result = commandHelper.exec(cmd);
		return result;
	}
	
	/**
	 * Linux modify hostname
	 * </br>
	 * <b>Centos 6</b>
	 * Modify file '/etc/sysconfig/network'</br>
	 * <b>Centos 7</b>
	 * Modify file '/etc/hostname'</br>
	 * <b>Ubuntu</b>
	 * Modify file '/etc/hostname' and replace hostname in file '/etc/hosts'
	 * (if not replace hostname, you may get warning 
	 * '<b>sudo: unable to resolve host</b>' when you command 'sudo')</br>
	 * @param imgPath
	 * @param newHostName
	 * @return
	 * @throws IOException
	 */
	private CommandResult linuxModHostName(String imgPath, String newHostName) throws IOException {
		logger.info("mod hostName on Linux, imgPath is "+imgPath+", newHostName is "+ newHostName);
		CommandHelper commandHelper = new CommandHelper();
		String format = "virt-cat -a %s /etc/issue";
		CommandResult result= commandHelper.exec(String.format(format, imgPath));
		String d = result.getOutput();
		if (d.startsWith("Ubuntu")) { //Ubuntu
			logger.info("Ubuntu");
			format = "virt-edit -a %s /etc/hostname -e '$_=\"\" if $. > 1'";
			result = commandHelper.exec(String.format(format, imgPath));
			if(result.getExitValue() >= 0) {
				logger.info("clear /etc/hostname 2-n line success");
				format = "virt-edit -a %s /etc/hostname -e '$_=\"%s\" if $. == 1'";
				result = commandHelper.exec(String.format(format, imgPath, newHostName));
				//replace hostname in file '/etc/hosts'
				if(result.getExitValue() >= 0) {
					logger.info("replace with newHostName success");
					format = "virt-edit -a %s /etc/hosts -e 's/127[.]0[.]1[.]1.*/127.0.1.1 %s/g'";
					result = commandHelper.exec(String.format(format, imgPath, newHostName));
				} else {
					result.setExitValue(CommandResult.PARAMS_WRONG);
					result.setError("edit file '/etc/hostname' failed ");
				}
			} else {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setError("edit file '/etc/hostname' failed ");
			}
			
		} else if(d.contains("CentOS release 6.")) { //Centos 6.x
			logger.info("Centos 6.x");
			format = "virt-cat -a %s /etc/sysconfig/network | grep %s";
			String checkNameCmd = String.format(format, imgPath, "HOSTNAME");
			result = commandHelper.exec(checkNameCmd);
			if (result.getError() == null && result.getOutput() == null) {
				logger.info("in file '/etc/sysconfig/network, no HOSTNAME line'");
				format = "virt-edit -a %s /etc/sysconfig/network -e 'print \"HOSTNAME\n\" if  $. == 1'";
				result = commandHelper.exec(String.format(format, imgPath, newHostName));
			} 
			format = "virt-edit -a %s /etc/sysconfig/network -e 's/HOSTNAME.*/HOSTNAME=%s/g'";
			result = commandHelper.exec(String.format(format, imgPath, newHostName));
			if(result.getExitValue() < 0) {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setError("edit file '/etc/sysconfig/network' failed ");
			} else {
				logger.info("edit /etc/sysconfig/network success");
			}
		} else { //Centos 7
			logger.info("Centos 7");
			format = "virt-edit -a %s /etc/hostname -e '$_=\"\" if $. > 1'";
			result = commandHelper.exec(String.format(format, imgPath));
			if(result.getExitValue() >= 0) {
				logger.info("edit /etc/hostname success");
				format = "virt-edit -a %s /etc/hostname -e '$_=\"%s\" if $. == 1'";
				result = commandHelper.exec(String.format(format, imgPath, newHostName));
			} else {
				result.setExitValue(CommandResult.PARAMS_WRONG);
				result.setError("edit file '/etc/hostname' failed ");
			}
		}
		logger.info(result.getOutput());
		logger.info(result.getError());
		logger.info(result.getExitValue());
		return result;
	}	
	
	/***
	 * 获取模板镜像的md5值
	 * @param imageFilePath  iso/a/b/ubuntu.iso
	 * @return strMd5sum 模板镜像的md5值
	 * @throws IOException
	 * @author hgm
	 */
	public CommandResult getImgMd5sum(String imagePath)
			throws IOException {
		CommandResult result = new CommandResult();

		if (isEmptyString(imagePath) ) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		/*Conf.initMountFs(imageServerHost);
		String srcImgPath = NFSUtil.getPath(imageServerHost, srcImgFile);
		if(!FileUtil.exists(srcImgPath)) {
			result.setError("img:" + srcImgPath
					+ " not exist!");
			return result;
		}*/

		try {
			CommandHelper commandHelperChmod = new CommandHelper();
			result = commandHelperChmod.exec("md5sum " + imagePath);

			/*if (isEmptyString(resultChmod.getError())) {
				return resultChmod;
			}*/
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("get " + imagePath + " md5sum failed.");
		}

		return result;
	}
}
