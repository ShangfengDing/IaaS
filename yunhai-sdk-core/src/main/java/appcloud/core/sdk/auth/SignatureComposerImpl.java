package appcloud.core.sdk.auth;

import java.util.Map;

import com.free4lab.utils.enabler.AbstractAuthentication;

public class SignatureComposerImpl extends AbstractAuthentication implements ISignatureComposer{

	private static ISignatureComposer composer = null;

	@Override
	public String composeSignature(Map<String, String> paramsMap,
			Long timestamp, String appkeyId, String appkeySecret) {
		// TODO Auto-generated method stub
		//调用云海统一的签名算法获取签名值
		return getSign(appkeyId, appkeySecret, timestamp, paramsMap);
	}

	public static ISignatureComposer getComposer() {
		if(null == composer) {
			composer = new SignatureComposerImpl();
		}
		return composer;
	}

	//下面的函数是因继承了AbstractAuthentication自动产生的，目前没什么用
	@Override
	public String getSecretKey(String appKey) {
		// TODO Auto-generated method stub
		return null;
	}

	//下面的函数是因继承了AbstractAuthentication自动产生的，目前没什么用
	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

}
