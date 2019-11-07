package appcloud.core.sdk.auth;

import java.util.Map;

public interface ISignatureComposer {

	/**
	 *	根据请求参数和appkey等信息获取签名值
	 *	@param paramsMap 存放的是一些用于签名的参数值
	 *	@param timestamp 请求时间戳，由系统产生的毫秒数
	 *  @param appkeyId appkeySecret
	 *  @author heguimin
	 */
	public String composeSignature(Map<String, String> paramsMap, Long timestamp, String appkeyId, String appkeySecret);
}
