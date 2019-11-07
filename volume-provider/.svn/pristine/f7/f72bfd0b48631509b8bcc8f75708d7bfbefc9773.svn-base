package appcloud.vp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.util.io.*;
import appcloud.vp.util.FileUtil;

public class Conf {
	public static String VOLUME_PROVIDER_HOST;
	public static String LIBVIRT_CONN = "qemu:///system";
	public static final String VOLUME_SUFFIX = ".img";
	public static final String VOLUME_QCOW2 = "qcow2";
	public static final String VOLUME_PREFIX = "volumes/";
	public static final String ISO_PREFIX = "iso/";
	public static final String ISO_SUFFIX = ".iso";
	public static Boolean DEBUG = false;

	private static Logger logger = Logger.getLogger(Conf.class);

	static {
		Properties p = new Properties();
		InputStream is = Conf.class.getClassLoader().getResourceAsStream(
				"vp.properties");
		try {
			p.load(is);
			String property = p.getProperty("DEBUG");
			if (property != null) {
				DEBUG = Boolean.parseBoolean(property);
			}
//			VOLUME_PROVIDER_HOST = IpUtil.getIpv4Address();			
			
		} catch (IOException e) {
			logger.warn("Invalid vp.properties found", e);
			
		}
		try {
			Host volumeHost = VolumeProviderServer.getHost();
			VOLUME_PROVIDER_HOST = volumeHost.getIp();
		} catch (Exception e) {
			logger.warn("########Pay Attention Here########");
			logger.warn("If the ip info in vp.properties is available, we can ingore this exception!");
			logger.warn("VolumeProviderServer get ip failed, try to load the vp.properties",e);			
			VOLUME_PROVIDER_HOST = p.getProperty("VOLUME_PROVIDER_HOST");
		}
		logger.info("Get ip address:" + VOLUME_PROVIDER_HOST);
		
		if (!FileUtil.exists(NFSUtil.DEFAULT_NFS_SRV_PATH)) {
			boolean result = ShellUtil.run("mkdir -p "
					+ NFSUtil.DEFAULT_NFS_SRV_PATH);
			if (result) {
				logger.info("mkdir " + NFSUtil.DEFAULT_NFS_SRV_PATH
						+ " successfully!");
			} else {
				logger.error("mkdir " + NFSUtil.DEFAULT_NFS_SRV_PATH
						+ " failed!");
			}
		}
		
	}

	public static boolean initMountFs(String nfsHost) {
		if(nfsHost != null && !"".equals(nfsHost)) {
			if (!NFSUtil.isMounted(nfsHost)) {
				boolean result = NFSUtil.mount(nfsHost,
						NFSUtil.DEFAULT_NFS_SRV_PATH);
				if (result) {
					logger.info("mount " + nfsHost + ":"
							+ NFSUtil.DEFAULT_NFS_SRV_PATH + " successfully!");
					return true;
				} else {
					logger.error("mount " + nfsHost + ":"
							+ NFSUtil.DEFAULT_NFS_SRV_PATH + " failed!");
					return false;
				}
			}
		}
		return true;
	}
}
