package appcloud.core.sdk.http;

public enum FormatType {
	XML,
	JSON,
	RAW;
	
	public static String mapFormatToAccept(FormatType format) {
		if(FormatType.XML == format) {
			return "application/xml";
		}else if(FormatType.JSON == format) {
			return "application/json";
		}
		return "application/octet-stream";
	}

	public static FormatType mapAcceptToFormat(String accept) {
		if(accept.toLowerCase().equals("application/xml") ||
				accept.toLowerCase().equals("text/xml")) {
			return FormatType.XML;
		}
		if(accept.toLowerCase().equals("application/json")) {
			return FormatType.JSON;
		}
		return FormatType.RAW;
	}
	
}
