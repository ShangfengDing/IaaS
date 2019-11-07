package appcloud.common.constant;

public enum FlowTableEnum {
	TABLE	,
	DL_SRC	,
	DL_TYPE	, 
	NW_SRC	,
	ACTIONS	;
	
	public String toString() {
		switch(this) {
			case TABLE:
				return "table";
			case DL_SRC:
				return "dl_src";
			case DL_TYPE:
				return "dl_type";
			case NW_SRC:
				return "nw_src";
			case ACTIONS:
				return "actions";
			default:
				return null;
		}
	}
	
	enum Actions {
		NORMAL, DROP;
		public String toString() {
			switch(this) {
				case NORMAL:
					return "normal";
				case DROP:
					return "drop";
				default:
					return "drop";
			}
		}
	}
}
