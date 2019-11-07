package appcloud.core.sdk.reader;

import appcloud.core.sdk.http.FormatType;

public class ReaderFactory {

	public static Reader createInstance(FormatType format) {
		if(FormatType.JSON == format) {
			return new JsonReader();
		}
		if(FormatType.XML == format) {
			return new XmlReader();
		}
		return null;
	}
}
