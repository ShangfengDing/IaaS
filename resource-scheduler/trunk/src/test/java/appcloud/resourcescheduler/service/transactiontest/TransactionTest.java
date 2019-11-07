/**
 * File: TransactionTest.java
 * Author: weed
 * Create Time: 2013-5-28
 */
package appcloud.resourcescheduler.service.transactiontest;

import java.util.concurrent.Future;

import appcloud.common.model.RpcExtra;
import appcloud.common.transaction.ContextHandler;
import appcloud.common.transaction.TFuture;
import appcloud.common.transaction.TTask;
import appcloud.common.transaction.Transaction;
import appcloud.common.transaction.rollback.IRollbackable;


/**
 * @author weed
 *
 */
public class TransactionTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public void process() throws Exception {
				context.addRollbackResource(new IRollbackable(){
					@Override
					public void rollback(RpcExtra rpcExtra) {
						System.out.println("Resource 1 rollback.");
					}
				});
				
				context.addRollbackResource(new IRollbackable(){
					@Override
					public void rollback(RpcExtra rpcExtra) {
						System.out.println("Resource 2 rollback.");
					}
				});
				
//				context.reset();
//				context.complete();
				throw new Exception("test");
			}

			@Override
			public boolean preProcess() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postProcess() throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		
//		transaction.execute();
//		System.out.println(transaction.asyncExecute());
		
		TFuture tf = transaction.asyncExecute(null);
		
		tf.getFuture().get();
		tf.getException().printStackTrace();
	}

}
