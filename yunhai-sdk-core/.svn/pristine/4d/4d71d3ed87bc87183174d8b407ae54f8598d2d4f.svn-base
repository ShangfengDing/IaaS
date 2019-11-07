package appcloud.core.sdk.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import appcloud.core.sdk.utils.ParameterHelper;

public class HttpRequest {

	private static Logger logger = Logger.getLogger(HttpRequest.class);
	protected static final String CONTENT_TYPE = "Content-Type";
	protected static final String CONTENT_MD5 = "Content-MD5";
	protected static final String CONTENT_LENGTH = "Content-Length";
	
	private String url = null;
	private MethodType method = null;
	protected FormatType contentType = null;
	protected byte[] content = null;
	protected String encoding = null;
	protected Map<String, String> headers = null;
	protected Integer connectTimeout = null;
	protected Integer readTimeout = null;

	public HttpRequest(String strUrl) {
		this.url = strUrl;
		this.headers = new HashMap<String, String>();
	}

	public HttpRequest(String strUrl, Map<String, String> tmpHeaders) {
		this.url = strUrl;
		if(null != tmpHeaders) {
			this.headers = tmpHeaders;
		}
	}

	public HttpRequest(){}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MethodType getMethod() {
		return method;
	}

	public void setMethod(MethodType method) {
		this.method = method;
	}

	public FormatType getContentType() {
		return contentType;
	}

	public void setContentType(FormatType contentType) {
		this.contentType = contentType;
		if(null != this.content || null != contentType) {
			this.headers.put(CONTENT_TYPE, getContentTypeValue(this.contentType, this.encoding));
		}else {
			this.headers.remove(CONTENT_TYPE);
		}
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content, String encoding, FormatType format) {
		if(null == content) {
			this.headers.remove(CONTENT_MD5);
			this.headers.remove(CONTENT_LENGTH);
			this.headers.remove(CONTENT_TYPE);
			this.contentType = null;
			this.content = null;
			this.encoding = null;
			return;
		}
		this.content = content;
		this.encoding = encoding;
		String contentLength = String.valueOf(content.length);
		String contentMd5 = ParameterHelper.md5Sum(content);
		if(null != format) {
			this.contentType = format;
		}else {
			this.contentType = FormatType.RAW;
		}
		this.headers.put(CONTENT_MD5, contentMd5);
		this.headers.put(CONTENT_LENGTH, contentLength);
		this.headers.put(CONTENT_TYPE, getContentTypeValue(contentType, encoding));
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	public String getHeaderValue(String name) {
		return this.headers.get(name);
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void putHeaderParameter(String name, String value) {
		if(null != name && null != value) {
			this.headers.put(name, value);
		}
	}
	public HttpURLConnection getHttpConnection() throws IOException {
		Map<String, String> mappedHeaders = this.headers;
		logger.info("mappedHeaders = " + mappedHeaders);
		String strUrl = url;
		if(null == strUrl || null == this.method) {
			return null;
		}
		URL url = null;
		String[] urlArray = null;
		logger.info("this method = " + this.method);
		if(MethodType.POST.equals(this.method) && null == getContent()) {
			urlArray = strUrl.split(":\\?");
			url = new URL(urlArray[0]);
		}else {
			url = new URL(strUrl);
			logger.info("url = " + url);
		}
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(this.method.toString());
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		logger.info("connet time out = " + this.getConnectTimeout());
		if(null != this.getConnectTimeout()) {
			httpConn.setConnectTimeout(this.getConnectTimeout());
		}
		logger.info("read time out = " +this.getReadTimeout());
		if(null != this.getReadTimeout()) {
			httpConn.setReadTimeout(this.getReadTimeout());
		}

		for(Entry<String, String> entry : mappedHeaders.entrySet()) {
			httpConn.setRequestProperty(entry.getKey(), entry.getValue());
		}

		if(null != getHeaderValue(CONTENT_TYPE)) {
			httpConn.setRequestProperty(CONTENT_TYPE, getHeaderValue(CONTENT_TYPE));
		}else {
			String contentTypeValue = getContentTypeValue(contentType, encoding);
			if(null != contentTypeValue) {
				httpConn.setRequestProperty(CONTENT_TYPE, contentTypeValue);
			}
		}

		if(MethodType.POST.equals(this.method) && null != urlArray && 2 == urlArray.length) {
			httpConn.getOutputStream().write(urlArray[1].getBytes());
		}
		return httpConn;
	}

	private String getContentTypeValue(FormatType contentType, String encoding) {
		if(null != contentType && null != encoding) {
			return FormatType.mapFormatToAccept(contentType) + 
					";charset=" + encoding.toLowerCase();
		}else if(null != contentType) {
			return FormatType.mapFormatToAccept(contentType);
		}
		return null;
	}

	
	
}
