/**
 * File: AmqpRpcAppender.java
 * Author: weed
 * Create Time: 2012-11-23
 */
package appcloud.common.util.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import appcloud.common.service.LogService;
import appcloud.common.util.ConnectionFactory;

/**
 * @author weed
 *
 */
public class AmqpRpcAppender extends AppenderSkeleton {

	private LogService __logger = ConnectionFactory.getLogger();
	private String moduleName;
	/* (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	protected void append(LoggingEvent event) {
		// TODO Auto-generated method stub
		if (event.getLevel().equals(Level.WARN)){
			
		}
		else if (event.getLevel().equals(Level.ERROR)){
			
		}
		else if (event.getLevel().equals(Level.FATAL)){
			
		}
		else{
			
		}
		
		__logger.log(moduleName + " : " + this.layout.format(event));  
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
