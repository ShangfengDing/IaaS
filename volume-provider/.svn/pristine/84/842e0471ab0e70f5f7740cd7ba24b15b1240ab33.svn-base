package appcloud.vp.qemuimg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

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
	
	private static final String IMG_PREFIX = "../../../";

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
			throws IOException, InterruptedException {
		CommandResult result = new CommandResult();		
		
		if(!Conf.initMountFs(imageServerHost)) {
			result.setError("mount " + imageServerHost + " failed!");
			return result;
		} else {
			logger.debug("mount " + imageServerHost + " successfully or it's already be mounted!");
		}
		
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
			logger.info("excute command:" + command);

			result = commandHelper.exec(command);
			return result;
			
		}
		
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile)) {
			if (NFSUtil.fileExists(imageServerHost, backingFile)) {
				FileUtil.copy(NFSUtil.getPath(imageServerHost, backingFile),
						NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
				CommandHelper commandHelperChmod = new CommandHelper();
				CommandResult resultChmod = commandHelperChmod.exec("chmod 666 " + NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
				
				if (!isEmptyString(resultChmod.getError())) {
					return resultChmod;
				}
			} else {
				result.setError("backingFile:" + NFSUtil.DEFAULT_NFS_SRV_PATH
						+ backingFile
						+ " not found in local filesystem or nfs!");
				return result;
			}
		}
		if (blockSize != null && blockSize <= 0) {
			result.setError("blockSize:parameter invalid");
			return result;
		}
		FileSystemUtil.createDirIfNotExist(NFSUtil.DEFAULT_NFS_SRV_PATH
				+ outPutFile);
		String format = QemuShell.CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE; 
				//"qemu-img create -f %s %s -b %s";
//		String command = String.format(format, fmt,
//				NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
//				NFSUtil.DEFAULT_NFS_SRV_PATH + backingFile);
		
		String command = String.format(format, fmt,
				NFSUtil.DEFAULT_NFS_SRV_PATH + outPutFile,
				IMG_PREFIX + backingFile);
		
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
		logger.info("excute command:" + command);

		result = commandHelper.execSupportRedirect(command);
		return result;
	}

	public CommandResult deleteVolume(String volumefilePath)
			throws IOException, InterruptedException {
		CommandResult result = new CommandResult();
		if (isEmptyString(volumefilePath)
				|| !volumefilePath.endsWith(Conf.VOLUME_SUFFIX)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath)) {
			result.setError("The image not exist, not need to delete");
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
//		logger.info("excute command:" + command);
//
//		result = commandHelper.exec(command);
//		if (isEmptyString(result.getError())) {
//			result.setOutput("delete volume successfully");
//		}
		return result;
	}

	public CommandResult createSnapshot(String snapshotTag, String imgFilePath)
			throws IOException, InterruptedException {
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

		logger.info("excute command:" + command);
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult applySnapshot(String snapshotTag, String imgFilePath)
			throws IOException, InterruptedException {
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

		logger.info("excute command:" + command);
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult deleteSnapshot(String snapshotTag, String imgFilePath)
			throws IOException, InterruptedException {
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

			logger.info("excute command:" + command);
			result = commandHelper.exec(command);
			if (!isEmptyString(result.getError())) {
				return result;
			}
		}
		return result;
	}

	public CommandResult ListSnapshots(String imgFilePath,
			Map<Integer, String> snapshotTags) throws IOException,
			InterruptedException {
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
		result = commandHelper.execSupportRedirect(command.toString());
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
			throws IOException, InterruptedException {
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

		logger.info("excute command:" + command);
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult convertImgFormat(String fmt,
			String srcVolumefilePath, String destVolumefilePath)
			throws IOException, InterruptedException {
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

		logger.info("excute command:" + command);
		result = commandHelper.exec(command);
		return result;
	}

	public CommandResult releaseImg(String volumefilePath, String imageHost,
			String destPath) throws IOException, InterruptedException {
		
		CommandResult result = new CommandResult();
		
		if(!Conf.initMountFs(imageHost)) {
			result.setError("mount " + imageHost + " failed!");
			return result;
		} else {
			logger.debug("mount " + imageHost + " successfully!");
		}
		if (isEmptyString(volumefilePath) || isEmptyString(imageHost)|| isEmptyString(destPath)) {
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
		
		String destImgPath = NFSUtil.getPath(imageHost, destPath);
		if(FileUtil.exists(destImgPath)) {
			result.setError("dest img:" + destImgPath
					+ " already exist! Please change a name.");
			return result;
		}
		
		boolean createDir = FileSystemUtil.createDirIfNotExist(destImgPath);
		if(!createDir) {
			result.setError("copy " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + " to " + destImgPath + " failed.");
		}
		
		try {
			FileUtils.copyFile(new File(NFSUtil.DEFAULT_NFS_SRV_PATH+volumefilePath), new File(destImgPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("copy " + NFSUtil.DEFAULT_NFS_SRV_PATH + volumefilePath + " to " + destImgPath + " failed.");
		}		
		
//		String format = "cp %s %s";
//		String command = String.format(format, NFSUtil.DEFAULT_NFS_SRV_PATH+volumefilePath, destImgPath);
//		CommandHelper commandHelper = new CommandHelper();
//
//		logger.info("excute command:" + command);
//		result = commandHelper.exec(command);
		return result;
	}

	@Deprecated
	public CommandResult moveImg(String volumefilePath, String volumeHost)
			throws IOException, InterruptedException {
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
//
//		logger.info("excute command:" + command);
//		result = commandHelper.exec(command);
		
		return result;
	}
	
	public CommandResult copyImg(String srcVolumefilePath, String volumeHost,
			String destVolumefilePath, String baseImage) throws IOException, InterruptedException {
		CommandResult result = new CommandResult();
		if (isEmptyString(srcVolumefilePath) || isEmptyString(destVolumefilePath)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		
		if(!Conf.initMountFs(volumeHost)) {
			result.setError("mount " + volumeHost + " failed!");
			return result;
		} else {
			logger.debug("mount " + volumeHost + " successfully!");
		}		
		
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
	 * @param srcIsoFile  iso/a/b/ubuntu.iso
	 * @param destIsoFile iso/a/b/ubuntu.iso
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public CommandResult copyIso(String imageServerHost,String srcIsoFile,String destIsoFile)
			throws IOException, InterruptedException {
		CommandResult result = new CommandResult();
		
		if (isEmptyString(srcIsoFile) || isEmptyString(destIsoFile) || isEmptyString(imageServerHost)) {
			result.setExitValue(CommandResult.PARAMS_WRONG);
			result.setError("Lack of parameter");
			return result;
		}
		
		if(!Conf.initMountFs(imageServerHost)) {
			result.setError("mount " + imageServerHost + " failed!");
			return result;
		} else {
			logger.debug("mount " + imageServerHost + " successfully or it's already be mounted!");
		}
		
		String srcIsoPath = NFSUtil.getPath(imageServerHost, srcIsoFile);
		if(!FileUtil.exists(srcIsoPath)) {
			result.setError("iso:" + srcIsoPath
					+ " not exist!");
			return result;
		}
			
		String destIsoPath  = NFSUtil.DEFAULT_NFS_SRV_PATH + destIsoFile;
//		if (FileUtil.exists(destIsoPath)) {
//			result.setOutput("dest iso:" + destIsoPath + " already exists, not need to copy!");
//			return result;
//		}
		
		boolean createDir = FileSystemUtil.createDirIfNotExist(destIsoPath);
		if(!createDir) {
			result.setError("create dir " + destIsoPath + " failed.");
		}
		
		try {
			FileUtils.copyFile(new File(srcIsoPath), new File(destIsoPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
			result.setError("move " + srcIsoPath + " to " + destIsoPath + " failed.");
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

	@Deprecated
	public static void main(String[] args) {
		try {
			// 创建一个volume
			CommandResult result = new VolumeManager().createVolume("qcow2",
					"volumes/output5.img", "192.168.11.132", "images/rhel.img",
					(float) 3.4);
			// Map<Integer,String> snapshotTags = new HashMap<Integer,String>();
			// CommandResult result = new
			// VolumeManager().ListSnapshots(Conf.NFS_ROOT,
			// "volumes/vm1.img",snapshotTags);
			// Iterator iter = snapshotTags.entrySet().iterator();
			// while (iter.hasNext()) {
			// Map.Entry entry = (Map.Entry) iter.next();
			// logger.info(entry.getKey()+" " + entry.getValue());
			// }
			if (result.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT
					|| !isEmptyString(result.getError())) {
				logger.error("Create volume failed! Caused by "
						+ result.getOutput() + " |" + result.getError());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

}
