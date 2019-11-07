package appcloud.common.util.io;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * util for nfs filesystem management.
 * remote: 
 *        192.168.1.1:/srv/nfs4/:volumes/a/z/uuid.img
 * local:  
 *        /nfs/192.168.1.1/volumes/a/z/uuid.img
 * 
 * @author wangchao
 *
 */
public class NFSUtil {
	static private String NFS_PATH = "/nfs/";
	static private long TIMEOUT = 1000;
	static private Logger logger = Logger.getLogger(NFSUtil.class);
	static final public String DEFAULT_NFS_SRV_PATH = "/srv/nfs4/";

	static private boolean run(String c) {
		logger.info("running cmd:" + c);
		String[] cmd = { "/bin/bash", "-c", c };
		String user = System.getProperty("user.name");
		if (!user.equals("root")) {
			// try use the username as sudo passwd
			cmd[2] = String.format("echo %s | sudo -S %s", user, c);
		}
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
			try {
				synchronized (p) {
					p.wait(TIMEOUT);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				int ret = p.exitValue();
				if (ret == 0)
					return true;
			} catch (IllegalThreadStateException e) {
				logger.warn(e);

			}
		} catch (IOException e) {

		}
		if (p != null)
			p.destroy();
		logger.info("cmd:" + c + " returns false");
		return false;
	}
	
	/**
	 * returns the local path of a volume 
	 * @param host
	 * @param filepath
	 * @return  
	 */
	static public String getPath(String host, String filepath) {
		return NFS_PATH + host + "/" + filepath;
	}

	synchronized static public boolean mount(String host, String nfsRoot) {

		CommandResult commandResult = null;
		try {
			commandResult = new CommandHelper().execSupportRedirect("showmount -e " + host);
			if(commandResult.getOutput() != null && !"".equals(commandResult.getOutput())) {
				if(!commandResult.getOutput().contains(DEFAULT_NFS_SRV_PATH.substring(0, DEFAULT_NFS_SRV_PATH.length()-1))) {
					logger.error("please confirm that " + host + "'s nfs service is running, besides " + DEFAULT_NFS_SRV_PATH + " is able to share.");
					return false;
				}
			} else {
				logger.error("please confirm that " + host + "'s nfs service is running, besides" + DEFAULT_NFS_SRV_PATH + "is able to share.");
				return false;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		
		
		String hostdirstr = NFS_PATH + host;
		File hostdir = new File(hostdirstr);

		if (!hostdir.exists()) {
			run("mkdir -p " + hostdirstr);
		}

		String format = "mount -t nfs %s:%s %s";
		String cmd = String.format(format, host, nfsRoot, hostdirstr);

		return run(cmd);
	}

	/***
	 * 首先：showmount -e host 看返回结果
	 * 然后：mount | grep "host:/srv/nfs4 on /nfs/host" 看是否有结果
	 * 最后：检查本地是否有挂载点
	 * @param host
	 * @return
	 */
	static public boolean isMounted(String host) {
		try {
			CommandResult commandResult = new CommandHelper().execSupportRedirect("showmount -e " + host);
			logger.info(commandResult.getOutput());
			if(commandResult.getOutput() != null && !"".equals(commandResult.getOutput())) {
				if(commandResult.getOutput().contains(DEFAULT_NFS_SRV_PATH.substring(0, DEFAULT_NFS_SRV_PATH.length()-1))) {
					commandResult = new CommandHelper().execSupportRedirect("mount | grep \"" + host + ":" +
							DEFAULT_NFS_SRV_PATH.substring(0, DEFAULT_NFS_SRV_PATH.length()-1)+"\"");
					logger.info(commandResult.getOutput());
					if(commandResult.getOutput() != null && !"".equals(commandResult.getOutput())) {
						String hostdirstr = NFS_PATH + host;
						File hostdir = new File(hostdirstr);
						return hostdir.exists();
					}			
				}
			} else {
				throw new IOException(host + "'s nfs service is down! [pay attention to nfs|iptables...]");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		return false;
		
	}

	static public boolean fileExists(String host, String filepath) {
		File f = new File(NFS_PATH + host + "/" + filepath);
		return f.exists();
	}
	
	/**
	 * check file exists or not, automatically mount the file system
	 * @param host  the ip of remote nfs 
	 * @param nfsroot
	 * @param filepath  file relative path in remote nfs 
	 * @return
	 */
	static public boolean fileExists(String host,String nfsroot, String filepath){
		if(!isMounted(host)){
			if(!mount(host, nfsroot))
				return false;;
		}
		return fileExists(host, filepath);
	}

	synchronized static public boolean umount(String host) {
		String cmd = String.format("umount " + NFS_PATH + host);
		String cmd1 = "rmdir " + NFS_PATH + host;
		if (run(cmd) && run(cmd1))
			return true;
		return false;
	}
}
