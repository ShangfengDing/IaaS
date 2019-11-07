package appcloud.core.sdk.serialization;

import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.reader.JsonReader;
import appcloud.core.sdk.reader.Reader;
import appcloud.core.sdk.reader.XmlReader;

public class SerializationFactory {

	public static Serialization createInstance(FormatType format) {
		if(FormatType.JSON == format) {
			return new XmlSerialization(); //TODO add json support
		}
		if(FormatType.XML == format) {
			return new XmlSerialization();
		}
		return null;
	}
}
