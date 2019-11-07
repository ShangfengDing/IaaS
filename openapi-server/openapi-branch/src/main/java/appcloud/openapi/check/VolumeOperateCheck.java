package appcloud.openapi.check;

import java.util.Map;

public interface VolumeOperateCheck {
	
	public Map<String, String> checkCreate(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkCreateDiskImageBack(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkAttach(Map<String, String> paramsMap) throws Exception;
	
	public Map<String, String> checkDetach(Map<String, String> paramsMap) throws Exception;
	
	public Map<String, String> checkDelete(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkDescribeImageBackParams(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkResetParams(Map<String, String> paramsMap) throws Exception;
	
	public Map<String, String> checkModifyAttributesParams(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkModifyImageBackParams(Map<String, String> paramsMap) throws Exception;

	public Map<String, String> checkRenewDisk(Map<String, String> paramsMap) throws Exception;
	
}
