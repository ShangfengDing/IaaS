/**
 * File: TransactionTask.java
 * Author: weed
 * Create Time: 2013-5-27
 */
package appcloud.common.transaction;


/**
 * @author weed
 *
 */
public abstract class TTask {
	protected String transactionUuid;
	protected ContextHandler context;
	
	/**
	 * 执行process之前的条件检查
	 * @return
	 */
	abstract public boolean preProcess() throws Exception;
	
	/**
	 * 执行具体作业
	 * @throws Exception
	 */
	abstract public void process() throws Exception;
	
	/**
	 * 正常执行process之后的收尾操作
	 */
	abstract public void postProcess() throws Exception;
	
	/**
	 * 执行process出错时的处理
	 * 资源回收操作会在OnError执行之后进行
	 */
	abstract public void onError();
	
	/**
	 * 执行process之后强制执行的操作
	 * 资源回收操作会在onCompleted执行之后进行
	 */
	abstract public void onCompleted();

	public ContextHandler getContext() {
		return context;
	}

	public void setContext(ContextHandler context) {
		this.context = context;
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}
}
