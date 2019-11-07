package appcloud.core.sdk.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.core.sdk.auth.Credential;
import appcloud.core.sdk.auth.SignatureComposerImpl;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.http.HttpRequest;
import appcloud.core.sdk.http.MethodType;
import appcloud.core.sdk.regions.ProductDomain;

public abstract class RpcYhaiRequest extends YhaiRequest {

	public static Logger logger = Logger.getLogger(RpcYhaiRequest.class);

	public RpcYhaiRequest(String product) {
		super(product);
		initialize();
	}

	public RpcYhaiRequest(String product, String version) {
		super(product);
		this.setVersion(version);
		initialize();
	}

	public RpcYhaiRequest(String product, String version, String actionName) {
		super(product);
		this.setVersion(version);
		this.setActionName(actionName);
		initialize();
	}

	//请求方法默认为GET，请求类型默认为JSON
	private void initialize() {
		this.setMethod(MethodType.GET);
		this.setAcceptFormat(FormatType.XML);
		this.composer = SignatureComposerImpl.getComposer();
	}

	public void setActionName(String actionName) {
		super.setActionName(actionName);
		this.putQueryParameters("Action", actionName);
	}

	public void setVersion(String version) {
		super.setVersion(version);
		this.putQueryParameters("Version", version);
	}

	public void setSecurityToken(String securityToken) {
		super.setSecurityToken(securityToken);
		this.putQueryParameters("SecurityToken", securityToken);
	}

	public void setAcceptFormat(FormatType format) {
		super.setAcceptFormat(format);
		this.putQueryParameters("Format", format.toString());
	}

	//构造请求URL
	public String composerUrl(String endPoint, Map<String, String> queries) throws UnsupportedEncodingException{
		Map<String, String> mapQueries = (queries == null) ? this.getQueryParameters() : queries;
		StringBuilder urlBuilder = new StringBuilder("");
		logger.info("endpoint = " + endPoint);
		urlBuilder.append(this.getProtocol().toString());
		urlBuilder.append("://").append(endPoint);
		if( -1 == urlBuilder.indexOf("?")) {
			urlBuilder.append("/?");
		}
		String query = concatQueryString(mapQueries);
		return urlBuilder.append(query).toString();
	}

	@Override
	public HttpRequest signRequest(Credential credential, FormatType format, ProductDomain domain)
						throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

		Map<String, String> immutableMap = new HashMap<String, String>(this.getQueryParameters());
		if(null != credential) {
			String appkeyId = credential.getAppkeyId();
			String appkeySecret = credential.getAppkeySecret();
			Long timestamp = System.currentTimeMillis();
			logger.info("sign params = " + immutableMap);
			String signature = this.composer.composeSignature(immutableMap, timestamp, appkeyId, appkeySecret);
			//时间戳和appkeyId不参与签名
			immutableMap.put("Timestamp", String.valueOf(timestamp));
			immutableMap.put("AppkeyId", appkeyId);
			immutableMap.put("Signature", signature);
		}
		setUrl(this.composerUrl(domain.getDomianName(), immutableMap));
		logger.info("request url = " + this.getUrl());
		return this;
	}
}
