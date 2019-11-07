/**
 * File: DefaultConnectFactory.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.common;

import appcloud.common.util.ConnectionFactory;

/**
 * @author weed
 *
 */
class DefaultConnectionFactory extends AbstractConnectionFactory{

	@Override
	public Object getDBProxy(Class<?> clazz) {
		return ConnectionFactory.getDBProxy(clazz);
	}

	@Override
	public Object getAMQPService(Class<?> clazz, String routingKeyPrefix) {
		return ConnectionFactory.getAMQPService(clazz, routingKeyPrefix);
	}
}
