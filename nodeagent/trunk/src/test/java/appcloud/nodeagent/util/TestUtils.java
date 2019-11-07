package appcloud.nodeagent.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestUtils {

	private static final ApplicationContext context;
	
	static {
		context = new FileSystemXmlApplicationContext(new String [] {
				"classpath:application*.xml"
		}, null);
	}
	
	/**
	 * 获取Spring ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
}
