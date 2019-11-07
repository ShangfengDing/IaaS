package appcloud.core.sdk.serialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXB;

import appcloud.core.sdk.exceptions.ClientException;

public class XmlSerialization implements Serialization {

	@Override
	public <T> T read(String content, Class<T> type) throws ClientException {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return JAXB.unmarshal(inputStream,type);
	}

}
