package appcloud.admin.action.price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ClusterUtil {

	private static AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	
	public static HashMap<Integer, String> getClusterMap(){
		HashMap<Integer, String> clusterMap = new HashMap<Integer, String>();
		List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
		aggregates = aggregateClient.getAggregates();
		for(AcAggregate ag: aggregates){
			if(!clusterMap.containsKey(ag.id)){
				clusterMap.put(ag.id, ag.name);
			}
		}
		return clusterMap;
	}
}
