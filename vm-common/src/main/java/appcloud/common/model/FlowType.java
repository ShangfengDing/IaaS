package appcloud.common.model;

public enum FlowType {
	IN, OUT, UNKNOWN;
	
	@Override
	public String toString() {
		switch(this) {
			case IN:
				return "in";
			case OUT:
				return "out";
			default:
				return "unknown";
		}
	}
	
}
