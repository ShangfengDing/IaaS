/**
 * Author: weed
 * Create Time: 2013-5-28
 */
package appcloud.common.transaction;

import java.util.concurrent.Future;

/**
 * 封装一个Future对象，用于返回异步执行的Future对象和运行异常
 * 
 * @author weed
 *
 */
public class TFuture{

	Future<?> future;
	Exception e = null;
	
	public TFuture() {
		
	}

	public Future<?> getFuture() {
		return future;
	}

	public void setFuture(Future<?> future) {
		this.future = future;
	}

	public Exception getException() {
		return e;
	}

	public void setException(Exception e) {
		this.e = e;
	}
}
