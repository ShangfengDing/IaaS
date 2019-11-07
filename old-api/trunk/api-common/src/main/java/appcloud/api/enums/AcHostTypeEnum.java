package appcloud.api.enums;

public enum AcHostTypeEnum {

	COMPUTE_NODE, FUNCTION_NODE, STORAGE_NODE;
	public String toString() {
		switch(this) {
		case COMPUTE_NODE: 
			return "COMPUTE_NODE";
		case FUNCTION_NODE: 
			return "FUNCTION_NODE";
		case STORAGE_NODE:
			return "STORAGE_NODE";
		}
		return super.toString();
	}
}
