/**
 * File: LogServiceImpl.java
 * Author: weed
 * Create Time: 2012-11-23
 */
package appcloud.common.util.log;

import appcloud.common.service.LogService;

/**
 * @author weed
 *
 */
public class LogServiceImpl implements LogService {

	/* (non-Javadoc)
	 * @see appcloud.common.service.LogService#log(java.lang.String)
	 */
	public void log(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);  
	}

}
