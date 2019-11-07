/**
 * File: VolumeServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import java.util.Map;

import appcloud.resourcescheduler.service.VolumeService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class VolumeServiceImpl implements VolumeService {

	@Override
	public Integer createVolume(String name, String displayName, Integer size,
			String type, Map<String, String> metadata) throws RpcException {
		return null;
	}

	@Override
	public String attachVolume(String volumeId, String instanceId)
			throws RpcException {
		return null;
	}

	@Override
	public void detachVolume(String volumeId, String instanceId)
			throws RpcException {
	}

	@Override
	public String createSnapshot(String displayName, String displayDescription,
			String volumeId, Boolean force) throws RpcException {
		return null;
	}

}
