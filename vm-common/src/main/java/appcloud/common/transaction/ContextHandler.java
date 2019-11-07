/**
 * File: ContextHandler.java
 * Author: weed
 * Create Time: 2013-5-28
 */
package appcloud.common.transaction;

import appcloud.common.transaction.rollback.IRollbackable;

/**
 * @author weed
 *
 */
public class ContextHandler {
	private Context context;
	
	ContextHandler(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * @param rbResource 添加到context管理的资源
	 */
	public void addRollbackResource(IRollbackable rbResource){
		context.addRbResource(rbResource);
	}
	
	/**
	 * 确认Context中的资源无须回滚
	 */
	public void complete(){
		context.setCompleted();
	}
}
