/**
 * File: Context.java
 * Author: weed
 * Create Time: 2013-5-28
 */
package appcloud.common.transaction;

import java.util.LinkedList;
import java.util.List;

import appcloud.common.model.RpcExtra;
import appcloud.common.transaction.rollback.IRollbackable;

/**
 * @author weed
 *
 */
class Context {
	boolean completed = false;
	List<IRollbackable> rbResources= new LinkedList<IRollbackable>();
	
	public Context() {
	}
	
	public boolean isCompleted() {
		return (completed||rbResources.isEmpty());
	}
	public void setCompleted() {
		this.completed = true;
	}

	public void addRbResource(IRollbackable rbResource) {
		this.rbResources.add(rbResource);
	}

	public void rollback(RpcExtra rpcExtra) {
		for (IRollbackable rbResource : rbResources){
			rbResource.rollback(rpcExtra);
		}
	}
	
	public void reset() {
		rbResources.clear();
	}
	
	@Override
	public String toString() {
		return "Context [completed=" + completed + ", rbResources="
				+ rbResources + "]";
	}
}
