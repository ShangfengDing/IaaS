package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.datatype.ZoneItem;
import appcloud.openapi.operate.RegionOperate;
import appcloud.openapi.response.DescribeRegionsResponse;
import appcloud.openapi.response.DescribeZonesResponse;

@Component
public class RegionOperateImpl implements RegionOperate {

	private VmZoneProxy vmZoneProxy;
	
	public RegionOperateImpl() {
		this.vmZoneProxy = (VmZoneProxy)ConnectionFactory.getTipProxy(
				VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}


	@Override
	public DescribeRegionsResponse showAll(String appkeyId, String requestId) throws Exception {
		List<? extends VmZone> vmZones = vmZoneProxy.findAll();
		List<RegionItem> regions = new ArrayList<>();
		Set<String> regionIds = new HashSet<>();
		for (VmZone vmZone : vmZones) {
			regionIds.add(vmZone.getRegionId());
		}
		for (String rid : regionIds) {
			RegionItem regionItem = new RegionItem(rid);
			regions.add(regionItem);
		}
		return new DescribeRegionsResponse(requestId, regions);
	}


	@Override
	public DescribeZonesResponse showZones(String appkeyId, String regionId, String requestId) throws Exception {
		List<? extends VmZone> vmZones = vmZoneProxy.findAll();
		List<ZoneItem> zones = new ArrayList<>();
		for (VmZone vmZone : vmZones) {
			if (vmZone.getRegionId().equals(regionId)) {
				ZoneItem zoneItem = new ZoneItem(vmZone.getZoneId());
				zones.add(zoneItem);
			}
		}
		return new DescribeZonesResponse(requestId,zones);
	}

}
