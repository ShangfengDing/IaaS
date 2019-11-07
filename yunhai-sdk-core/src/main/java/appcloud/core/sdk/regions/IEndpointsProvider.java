package appcloud.core.sdk.regions;

import java.util.List;

import appcloud.core.sdk.exceptions.ClientException;

public interface IEndpointsProvider {

	/**
	 * 通过endpoint.xml文件获取所有的endpoint
	 * @return
	 * @throws ClientException
	 */
	public List<Endpoint> getEndpoints() throws ClientException;
}
