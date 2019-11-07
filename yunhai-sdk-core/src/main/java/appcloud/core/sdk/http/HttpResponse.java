package appcloud.core.sdk.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class HttpResponse extends HttpRequest{

	private static Logger logger = Logger.getLogger(HttpResponse.class);
	private int status;

	public HttpResponse(String strUrl) {
		super(strUrl);
	}

	public HttpResponse(){}

	public void setContent(byte[] content, String encoding, FormatType format) {
		this.content = content;
		this.encoding = encoding;
		this.contentType = format;
	}

	@Override
	public String getHeaderValue(String name) {
		String value = this.headers.get(name);
		if (null == value){
			value = this.headers.get(name.toLowerCase());
		}
		return value;
	}
	
	private static byte[] readContent(InputStream content) throws IOException {
        if (content == null){
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];

        while(true) {
           final int read = content.read(buff);
           if(read == -1) break;
           outputStream.write(buff,0,read);
        }

        return outputStream.toByteArray();
    }

	public static HttpResponse getResponse(HttpRequest httpRequest) throws IOException{
		// TODO Auto-generated method stub
		OutputStream out = null;
		InputStream content = null;
		HttpResponse response = null;
		HttpURLConnection httpConn = httpRequest.getHttpConnection();

		logger.info("httpConn = " + httpConn);
		try {
			// 建立TCP连接
			httpConn.connect();
			if(null != httpRequest.getContent() && httpRequest.getContent().length > 0) {
				out = httpConn.getOutputStream();
				out.write(httpRequest.getContent());
			}
			// 开始发送请求
			content = httpConn.getInputStream();
			response = new HttpResponse(httpConn.getURL().toString());
			parseHttpConn(response, httpConn, content);
			return response;
		}catch (IOException e) {
			logger.warn("send request failed!",e);
			content = httpConn.getErrorStream();
			response = new HttpResponse(httpConn.getURL().toString());
			parseHttpConn(response, httpConn, content);
			return response;
		}finally {
			if(null != content) {
				content.close();
			}
			httpConn.disconnect();
		}
	}

	//解析http响应
	private static void parseHttpConn(HttpResponse response, HttpURLConnection httpConn, 
			InputStream content) throws IOException {
		// TODO Auto-generated method stub
		byte[] buff = readContent(content);
		response.setStatus(httpConn.getResponseCode());
		Map<String, List<String>> headers = httpConn.getHeaderFields();
		for(Entry<String, List<String>> entry : headers.entrySet()) {
			String key = entry.getKey();
			if(null == key) {
				continue;
			}
			List<String> values = entry.getValue();
			StringBuilder builder = new StringBuilder(values.get(0));
			for(int i=1; i<values.size(); i++) {
				builder.append(",");
				builder.append(values.get(i));
			}
			response.putHeaderParameter(key, builder.toString());
		}
		String type = response.getHeaderValue("Content-Type");
		if(null != buff && null != type) {
			response.setEncoding("UTF-8");
			String[] split = type.split(";");
			response.setContentType(FormatType.mapAcceptToFormat(split[0].trim()));
			if(split.length > 1 && split[1].contains("=")) {
				String[] codings = split[1].split("=");
				response.setEncoding(codings[1].trim().toUpperCase());
			}
		}
		response.setStatus(httpConn.getResponseCode());
		response.setContent(buff, response.getEncoding(), response.getContentType());
		logger.info("response: status = " + response.getStatus() + "; content = " + response.getContent() + "; type = " + response.getContentType());
	}

	public boolean isSuccess() {
		// TODO Auto-generated method stub
		logger.info("Response success!");
		if(200 <= this.status && 300 >= this.status) {
			return true;
		}
		return false;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
