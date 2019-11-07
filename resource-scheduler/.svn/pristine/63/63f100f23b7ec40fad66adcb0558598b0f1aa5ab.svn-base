/**
 * File: ConnectionManager.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.common;


/**
 * @author weed
 *
 */
public class ConnectionManager extends AbstractConnectionFactory{
	
	public enum TYPE {DEFAULT, TEST};
	
	private static TYPE __type = TYPE.DEFAULT;
	public static void initialize(TYPE type){
		__type = type;
	}
	
	public static ConnectionManager getInstance(){
		return Lazyman.instance;
	}
	
	private AbstractConnectionFactory connectionFactory = null;
	
	private ConnectionManager() {
		super();
		switch (__type){
		case TEST:
			connectionFactory = new TestConnectionFactory();
			break;
		default:
			connectionFactory = new DefaultConnectionFactory();
		}
	}
	
	@Override
	public Object getDBProxy(Class<?> clazz) {
		return connectionFactory.getDBProxy(clazz);
	}

	@Override
	public Object getAMQPService(Class<?> clazz, String routingKeyPrefix) {
		return connectionFactory.getAMQPService(clazz, routingKeyPrefix);
	}
	
	static class Lazyman{
		private static ConnectionManager instance = new ConnectionManager();
	}

}
