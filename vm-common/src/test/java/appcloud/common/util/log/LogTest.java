/**
 * File: LogTest.java
 * Author: weed
 * Create Time: 2012-11-23
 */
package appcloud.common.util.log;

import org.apache.log4j.Logger;

/**
 * @author weed
 *
 */
public class LogTest {
	private static Logger logger = Logger.getLogger(LogTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.debug("log test!");
	}

}
