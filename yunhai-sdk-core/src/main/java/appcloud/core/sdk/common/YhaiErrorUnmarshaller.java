package appcloud.core.sdk.common;

import java.util.Map;

public class YhaiErrorUnmarshaller {

	public static YhaiError unmarshall(YhaiError error, UnmarshallerContext context) {
		Map<String, String> map = context.getResponseMap();
		error.setStatucCode(context.getHttpStatus());
		error.setRequestId(map.get("Error.RequestId"));
		error.setErrorCode(map.get("Error.ErrorCode"));
		error.setErrorMessage(map.get("Error.Message"));

		return error;
	}
}
