package appcloud.api.manager.fake;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import appcloud.api.beans.Flavor;
import appcloud.api.beans.IP;
import appcloud.api.beans.Image;
import appcloud.api.beans.Load;
import appcloud.api.beans.Meta;
import appcloud.api.beans.Metadata;
import appcloud.api.beans.MetadataAdaptor;
import appcloud.api.beans.Network;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.beans.server.ServerUpdateReq;
import appcloud.api.manager.ServerManager;

public class FakeServerManager implements ServerManager {
	private Random rnd = new Random();
	MetadataAdaptor adaptor = new MetadataAdaptor();
	
	public List<Server> getList(String tenantId, boolean detailed, boolean allTenants){
		return _fakeGetServers(tenantId, detailed);
	}
	
	private List<Server> _fakeGetServers(String tenantId, boolean detailed) {
		List<Server> servers = new ArrayList<Server>();
		if (!detailed) {	
			servers.add(new Server(tenantId, "12311122", "dfsfd"));
			servers.add(new Server(tenantId, "12311123", "dfsfd"));
			return servers;
		} else {
			servers.add(this.getDetail(tenantId, "abc"));
			servers.add(this.getDetail(tenantId, "cde"));

			return servers;
		}
	}
	
	public Server getDetail(String tenantId, String serverId) {
		Server server = _fakeAServer(tenantId, serverId);
		return server;
	}
	
	public Server _fakeAServer(String tenantId, String serverId) {
		HashMap<String, String> metas = new HashMap<String, String>();
		metas.put("maxBandwidth",  String.format("%s", (int)(Math.random()*10000)%5 + 1));
		
		SecurityGroup group = new SecurityGroup((int)(Math.random()*10000)%5 + 1, 
				"default", null, null, "description");
		
		List<IP> ips = new ArrayList<IP>();
		ips.add(new IP(4, "1.1.1.1"));
		ips.add(new IP(6, "23:323:ab:32"));
		Network nt = new Network();
		nt.id = "public";
		nt.ips = ips;
		
		List<Network> nts = new ArrayList<Network>();
		nts.add(nt);
		Server server = new Server(serverId, "dfsfd", tenantId, "active", "", "", 0, "hhh-ooo-sss-ttt", 
				new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
				new Image(tenantId, "iddd", null), 
				new Flavor(tenantId, 123, null),"adminpass", nts,metas, group);
		server.availabilityZone = 1;
		return server;
	}
	
	public Server create(String tenantId, ServerCreateReq req) {
		Server s = _fakeAServer(tenantId, "nnn-eee-www");
		return s;
	}

	public void osStart(String tenantId, String serverId) {
		System.out.println("start " + tenantId + "'s server " + serverId + "!!");
	}
	
	public void osStop(String tenantId, String serverId) {
		System.out.println("stop " + tenantId + "'s server " + serverId + "!!");
	}

	public void resume(String tenantId, String serverId) {
		System.out.println("resume " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public void reboot(String tenantId, String serverId,
			ServerActionReboot reboot) {
		System.out.println("reboot " + tenantId + "'s server " + serverId + "!!" + reboot.type);
	}

	@Override
	public Server rebuild(String tenantId, String serverId, ServerActionRebuild rebuild) {
		System.out.println(String.format("REBUID server:%s, user:%s," + 
			" new flavor:%s, new image:%s", serverId, tenantId,
			rebuild.flavorRef, rebuild.imageRef));
		
		if (rebuild.securityGroup != null ) {
			System.out.println("sgid:" + rebuild.securityGroup.id);
			System.out.println("avid:" + rebuild.availabilityZone);
		}
		return this._fakeAServer(tenantId, serverId);
	}

	@Override
	public void resize(String tenantId, String serverId, String flavorRef) {
		//System.out.println(String.format("resize: %s/%s", tenantId, serverId));
		System.out.println("resize " + tenantId +
				"'s server " + serverId + ", new flavor: " + flavorRef);
	}

	@Override
	public void suspend(String tenantId, String serverId) {
		//System.out.println(String.format("suspend: %s/%s", tenantId, serverId));
		System.out.println("suspend " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public void forceDelete(String tenantId, String serverId) {
		//System.out.println(String.format("forceDelete: %s/%s", tenantId, serverId));
		System.out.println("force delete " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public void terminate(String tenantId, String serverId) {
		//System.out.println(String.format("terminate: %s/%s", tenantId, serverId));
		System.out.println("terminate " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public List<Load> getMonitorData(String tenantId, String serverId,
			String type, Timestamp startTime, Timestamp endTime) {
		Date date = new Date();
		long diff = 0;
		if (type.equalsIgnoreCase("day")) {
			diff = 1000*30;			
		} else if (type.equalsIgnoreCase("month")) {
			diff = 1000*60*60*24;
		} else {
			diff = 1000*60*60*24;
			diff *= 30;
		}
		
		System.out.println(diff);
		
		List<Load> loads = new ArrayList<Load>();
		
		long times = (endTime.getTime() - startTime.getTime()) / diff; 
		
		if (times > 100) times = 100;
		
		date = new Date(startTime.getTime());
		for (int i = 0; i < times; i++) {
			date = new Date(date.getTime() + diff);
			loads.add(getLoad(date));
		}
		
		return loads;
	}
	
	private Load getLoad(Date date) {
		return new Load(date, rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f,
				rnd.nextFloat()%95f+5f, 
				rnd.nextFloat()%95f+5f);
	}


	@Override
	public Metadata getMetadata(String tenantId, String serverId)
			throws Exception {
		System.out.println("get " + tenantId +"'s " + serverId +  "'s metadata");
		HashMap<String, String> metas = new HashMap<String, String>();
		metas.put("securityGroupId", String.format("%s", (int)(Math.random()*10000)%5 + 1));
		metas.put("securityGroupName", "default");
		metas.put("maxBandwidth",  String.format("%s", (int)(Math.random()*10000)%5 + 1));
		return adaptor.marshal(metas);
	}

	@Override
	public Metadata updateMetadata(String tenantId, String serverId, Metadata metadata)
			throws Exception {
		System.out.println("update " + tenantId +"'s " + serverId +  "'s metadata");
		Map<String, String> metas = adaptor.unmarshal(metadata);
		metas.put("ori_meta_1", "v1");
		metas.put("ori_meta_2", "v2");
		return adaptor.marshal(metas);
	}

	@Override
	public Metadata setMetadata(String tenantId, String serverId, Metadata metadata)
			throws Exception {
		System.out.println("set " + tenantId +"'s " + serverId + "'s metadata");
		return metadata;
	}

	@Override
	public Meta getMetadataItem(String tenantId, String serverId, String key) throws Exception {
		System.out.println("get " + tenantId +"'s " + serverId +  "'s metadata item");
		Meta meta = new Meta(key, "value_getItem");
		return meta;
	}


	@Override
	public Meta setMetadataItem(String tenantId, String serverId, String key, Meta meta)
			throws Exception {
		System.out.println("set " + tenantId +"'s " + serverId + "'s metadata item");
		
		return meta;
	}

	@Override
	public void bootFromISO(String tenantId, String serverId, String imgRef)
			throws Exception {
		// TODO Auto-generated method stub

		System.out.println("boot from iso  :" + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public void detachISO(String tenantId, String serverId) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("detach iso from " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public void forceStop(String tenantId, String serverId) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("force stop " + tenantId + "'s server " + serverId + "!!");
	}

	@Override
	public String forceRefresh(String tenantId, String serverId)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("force refresh " + tenantId + "'s server " + serverId + "!!");
		return "OK";
	}

	@Override
	public List<Server> searchByProperties(String tenantId, String userId,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostIp, String serverIp,
			Date startdate, Date endDate, Integer page, Integer size)
			throws Exception {
		return _fakeGetServers(tenantId, true);
	}

	@Override
	public Long countByProperties(String tenantId, String userId,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostId, String serverIp,
			Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		return (long) 5;
	}

	@Override
	public String reset(String tenantId, String serverId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server update(String tenantId, String serverId, Server server)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modPasswd(String tenantId, String serverId, String userName,
			String passwd, String type) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Server> searchByProperties(String tenantId,
			List<Integer> userIds, String serverId, String serverName,
			String status, Integer zoneId, Integer aggregateId, String hostId,
			String serverIp, Date startdate, Date endDate, Integer page,
			Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByProperties(String tenantId, List<Integer> userIds,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostId, String serverIp,
			Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server migrate(String tenantId, String serverId, String hostUuid)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Server onlineMigrate(String tenantId, String serverId, String hostUuid)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server updateServerName(String tenantId, ServerUpdateReq uReq)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meta updateMetadataItem(String tenantId, String serverId,
			String key, boolean release, Meta meta) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
