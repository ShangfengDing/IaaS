/**
 * File: IRollbackable.java
 * Author: weed
 * Create Time: 2013-4-21
 */
package appcloud.common.transaction.rollback;

import appcloud.common.model.RpcExtra;

/**
 * @author weed
 *
 */
public interface IRollbackable {
	void rollback(RpcExtra rpcExtra);
}
