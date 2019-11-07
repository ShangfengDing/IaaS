/**
 * File: Transaction.java
 * Author: weed
 * Create Time: 2013-5-27
 */
package appcloud.common.transaction;

import appcloud.common.model.RpcExtra;
import appcloud.common.util.UuidUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author weed
 *
 */
public class Transaction {
	private static Logger logger = Logger
			.getLogger(Transaction.class);
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	public enum TransactionState{
		READY,
		PROCESSING,
		FINISHED,
		ERROR
	};
	
	private TTask task;
	private TransactionState state;
	private String uuid;
	
	public Transaction(TTask task) {
		super();
		uuid = UuidUtil.getRandomUuid();
		task.setTransactionUuid(uuid);
		this.task = task;
		this.state = TransactionState.READY;

	}
	
	protected boolean canProcess(){
		synchronized (state){
			if (state.compareTo(TransactionState.READY) == 0){
				state = TransactionState.PROCESSING;
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	protected void process(RpcExtra rpcExtra) throws Exception{
		logger.info("Transaction " + uuid + " is processing.");
		
		Context context = new Context();
		task.setContext(new ContextHandler(context));
		
		try {
			if (!task.preProcess()){
				logger.info("Transaction " + uuid + " preconditionCheck == false!");
				state = TransactionState.ERROR;
				return;
			}
			
			long startTime = System.currentTimeMillis();
			task.process();
			logger.info("Transaction " + uuid + " will finish.");
			state = TransactionState.FINISHED;
			logger.info("Transaction " + uuid + " finished in " + (System.currentTimeMillis() - startTime) + "ms.");
		
			task.postProcess();
		} catch (Exception e) {
			logger.info(e);
			task.onError();
			state = TransactionState.ERROR;
			logger.info("Transaction " + uuid + " catches exception!", e);
			throw e;
		}
		finally{
			task.onCompleted();
			
			if (state.compareTo(TransactionState.FINISHED) != 0
					|| !context.isCompleted()){
				logger.info("Transaction " + uuid + " rollback.");
				context.rollback(rpcExtra);
				task.setContext(null);
			}
		}
	}
	
	public void execute(RpcExtra rpcExtra) throws Exception{
		if (canProcess()){
			process(rpcExtra);
		}
		else{
			return;
		}
	}
	
	public TFuture asyncExecute(final RpcExtra rpcExtra){
		if (canProcess()){
			final TFuture tf = new TFuture();
			
			Future<?> f = executor.submit(new Runnable(){
				@Override
				public void run() {
					try {
						process(rpcExtra);
					} catch (Exception e) {
						logger.error("Transaction " + uuid + " catches exception in asyncExecute!");
						tf.setException(e);
					}
				}
			});
			
			tf.setFuture(f);
			
			return tf;
		}
		else{
			return null;
		}
	}

	public TransactionState getState() {
		return state;
	}

	public String getUuid() {
		return uuid;
	}
}

/*class TransactionWrapper{
	private TransactionTask task;
	
	TransactionWrapper(TransactionTask task){
		this.task = task;
	}
}*/