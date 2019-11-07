package appcloud.openapi.check;

import java.util.Map;

public interface SnapshotOperateCheck {
	
	public Map<String, String> checkCreate(Map<String, String> paramsMap) throws Exception;
	
	public Map<String, String> checkDelete(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap) throws Exception;
}
