package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.dbproxy.mysql.dao.VmZoneDAO;
import appcloud.dbproxy.mysql.model.VmZoneTable;

public class MySQLVmZoneProxy implements VmZoneProxy{

	private VmZoneDAO dao = new VmZoneDAO();
	@Override
	public List<? extends VmZone> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmZone> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public VmZone getById(Integer zoneId) throws Exception {
		return dao.findById(zoneId);
	}

	@Override
	public VmZone getByName(String zoneName) throws Exception {
		List<? extends VmZone> vmZones = dao.findByProperty("name", zoneName);
		VmZone vmZone = null;
		if (!vmZones.isEmpty()) {
			vmZone = vmZones.get(0);
		}
		return vmZone;
	}

	@Override
	public VmZone getByRegionId(String regionId) throws Exception {
		List<? extends VmZone> vmZones = dao.findByProperty("regionId", regionId);
		VmZone vmZone = null;
		if(!vmZones.isEmpty()) {
			vmZone = vmZones.get(0);
		}
		return vmZone;
	}

	@Override
	public VmZone getByZoneId(String zoneId) throws Exception {
		List<? extends VmZone> vmZones = dao.findByProperty("zoneId", zoneId);
		VmZone vmZone = null;
		if(!vmZones.isEmpty()) {
			vmZone = vmZones.get(0);
		}
		return vmZone;
	}

	@Override
	public void save(VmZone zone) throws Exception {
		dao.save(new VmZoneTable(zone));
	}

	@Override
	public void update(VmZone zone) throws Exception {
		dao.update(new VmZoneTable(zone));
	}

	@Override
	public void deleteById(Integer zoneId) throws Exception {
		dao.deleteByPrimaryKey(zoneId);
	}
	
	public static void main(String[] args) throws Exception {
		MySQLVmZoneProxy mySQLVmZoneProxy = new MySQLVmZoneProxy();
		System.out.println(mySQLVmZoneProxy.findAll().get(0));
		VmZone zone = mySQLVmZoneProxy.getByZoneId("bj-bupt");
		System.out.println(zone.getId());
		zone = mySQLVmZoneProxy.getByRegionId("beijing");
		System.out.println(zone.getId());
	}

}
