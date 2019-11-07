package appcloud.admin;

import appcloud.api.beans.AcUser;
import appcloud.api.client.AcUserClient;
import com.appcloud.vm.fe.billing.Balance;
import com.appcloud.vm.fe.billing.BalanceByPage;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.util.ClientFactory;

import java.util.List;

public class UpdateUserTable {
	private static AcUserClient userClient = ClientFactory.getAcUserClient();
	
	public static void main(String[] args) {
		BalanceByPage balanceByPage = BillingAPI.getBalances("", 1, 100000);
		List<Balance> accountUsers = balanceByPage.getBalances();

		for(int i=0; i<accountUsers.size(); i++) {
			AcUser acUser = userClient.get(String.valueOf(accountUsers.get(i).getUid()));
			if(acUser != null) {
				acUser.userEmail = accountUsers.get(i).getEmail();
				userClient.update(acUser.userId, acUser);
			}
		}

//
//		DelayQueue delayQueue = new DelayQueue();
//		PriorityBlockingQueue
//		ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue();
//		AtomicInteger
	}
}
