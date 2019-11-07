package appcloud.core.sdk.utils;

import appcloud.core.sdk.common.YhaiRequest;
import appcloud.core.sdk.common.YhaiResponse;
import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.HttpResponse;

public interface YhaiClient {

	public <T extends YhaiResponse> HttpResponse doAction(YhaiRequest request) throws ClientException, ServerException;

	//public <T extends YhaiResponse> T getYhaiResponse(YhaiRequest<T> request) throws ClientException, ServerException;

	public <T> T getYhaiResponse(YhaiRequest request, Class<T> type) throws ClientException, ServerException;;
}
