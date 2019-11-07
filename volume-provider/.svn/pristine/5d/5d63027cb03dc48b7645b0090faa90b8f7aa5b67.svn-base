package appcloud.vp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/***
 * 初始化qemu-img的shell命令
 * 配置文件qemu.properties
 * @author huahui
 *
 */
public class QemuShell {
	private static Logger logger = Logger.getLogger(QemuShell.class);
	
	public static String CREATE_VOLUME_DISK;
	public static String CREATE_VOLUME_OS_WITH_BLOCKSIZE;
	public static String CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE;
	
	public static String CREATE_SNAPSHOT;
	public static String DELETE_SNAPSHOT;
	public static String APPLY_SNAPSHOT;
	public static String LIST_SNAPSHOT;

	public static String RESIZE_IMG;
	public static String CONVERT_IMG;
	static {
		Properties p = new Properties();
		InputStream is = QemuShell.class.getClassLoader().getResourceAsStream(
				"qemu.properties");
		try {
			p.load(is);
			CREATE_VOLUME_DISK = p.getProperty("CREATE_VOLUME_DISK");
			CREATE_VOLUME_OS_WITH_BLOCKSIZE = p.getProperty("CREATE_VOLUME_OS_WITH_BLOCKSIZE");
			CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE = p.getProperty("CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE");
			
			CREATE_SNAPSHOT = p.getProperty("CREATE_SNAPSHOT");
			DELETE_SNAPSHOT = p.getProperty("DELETE_SNAPSHOT");
			APPLY_SNAPSHOT = p.getProperty("APPLY_SNAPSHOT");
			LIST_SNAPSHOT = p.getProperty("LIST_SNAPSHOT");
			
			RESIZE_IMG = p.getProperty("RESIZE_IMG");
			CONVERT_IMG = p.getProperty("CONVERT_IMG");
			
			logger.info("Load qemu-img shell:");
			logger.info(CREATE_VOLUME_DISK);
			logger.info(CREATE_VOLUME_OS_WITH_BLOCKSIZE);
			logger.info(CREATE_VOLUME_OS_WITHOUT_BLOCKSIZE);
			logger.info(CREATE_SNAPSHOT);
			logger.info(DELETE_SNAPSHOT);
			logger.info(APPLY_SNAPSHOT);
			logger.info(LIST_SNAPSHOT);
			logger.info(RESIZE_IMG);
			logger.info(CONVERT_IMG);
			
		} catch (IOException e) {
			logger.error("Invalid qemu.properties found", e);
			logger.error(e.getMessage());
		}
		
	}
}
