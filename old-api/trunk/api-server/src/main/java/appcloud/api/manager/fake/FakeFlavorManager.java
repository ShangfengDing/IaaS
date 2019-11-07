package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import appcloud.api.beans.Flavor;
import appcloud.api.manager.FlavorManager;

public class FakeFlavorManager implements FlavorManager{

	public List<Flavor> getList(String tenantId,
			boolean detailed) {
		List<Flavor> flavors = new ArrayList<Flavor>();
		
		flavors.add(createAFlavor(tenantId));
		flavors.add(createAFlavor(tenantId));
		flavors.add(createAFlavor(tenantId));
		flavors.add(createAFlavor(tenantId));	
		
		return flavors;
	}
	
	public Flavor get(String tenantId, Integer flavorId) {
		Flavor f = createAFlavor(tenantId);
		f.id = flavorId;
		
		return f;
	}
	
	public Flavor create(String tenantId, Flavor flavor){
		Flavor f = createAFlavor(tenantId);
		f.id = 123;
		return f;
	}

	private  Flavor createAFlavor(String tenantId) {
		Random rnd = new Random();
		return new Flavor(rnd.nextInt(1000), "fakename-"+rnd.nextInt(100),
				tenantId, rnd.nextInt(1024)+512, rnd.nextInt(100)+10, rnd.nextInt(4)+1); 
	}
	
}
