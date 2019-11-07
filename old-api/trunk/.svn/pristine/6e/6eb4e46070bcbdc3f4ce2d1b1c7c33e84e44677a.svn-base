package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.AddressPool;
import appcloud.api.beans.AddressPool.IPUsage;
import appcloud.api.manager.AddressPoolManager;

public class FakeAddressPoolManager implements AddressPoolManager {

	@Override
	public List<AddressPool> getList(String adminId, Integer zoneId, Integer aggregateId) throws Exception {
		List<AddressPool> pools = new ArrayList<AddressPool>();
		
		pools.add(genPool(false));
		pools.add(genPool(false));
		pools.add(genPool(false));
		
		return pools;
	}

	@Override
	public AddressPool get(String adminId, Integer poolId) throws Exception {
		AddressPool pool = new AddressPool();
		pool.aggregateId = 1;
		pool.dns = "192.168.1.22";
		pool.gateway = "192.168.0.1";
		pool.type = "public";
		pool.netmask = "255.255.255.0";
		pool.id = poolId;
		
		pool.ips = new ArrayList<IPUsage>();
		pool.ips.add(new IPUsage("123.22.22.22", "USED", "AA:BB:CC", "serverA"));
		pool.ips.add(new IPUsage("123.22.22.23", "USED", "AA:BB:CD", "serverB"));
		pool.ips.add(new IPUsage("123.22.22.24", "USED", "AA:BB:CE", "serverB"));
		
		return pool;
	}
	
	private AddressPool genPool(boolean withIPs) {
		AddressPool pool = new AddressPool();
		pool.aggregateId = 1;
		pool.dns = "192.168.1.22";
		pool.gateway = "192.168.0.1";
		pool.type = "public";
		pool.netmask = "255.255.255.0";
		pool.id = 1;
		
		pool.ips = new ArrayList<IPUsage>();
		if (withIPs) {
			pool.ips.add(new IPUsage("123.22.22.22", "USED", "AA:BB:CC", "serverA"));
			pool.ips.add(new IPUsage("123.22.22.23", "USED", "AA:BB:CD", "serverB"));
			pool.ips.add(new IPUsage("123.22.22.24", "USED", "AA:BB:CE", "serverB"));
		}
		
		return pool;
	}

	@Override
	public AddressPool create(String adminId, AddressPool createReq)
			throws Exception {
		// TODO Auto-generated method stub
		return genPool(false);
	}

	@Override
	public void delete(String adminId, Integer poolId) throws Exception {
		// TODO Auto-generated method stub
	}

}
