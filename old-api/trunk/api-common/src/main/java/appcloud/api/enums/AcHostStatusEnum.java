package appcloud.api.enums;

public enum AcHostStatusEnum {
	HIGH_LOAD, NORMAL_LOAD, LOW_LOAD, CRASH;
	
	public String toString() {
		switch (this) {
			case HIGH_LOAD:
				return "HIGH_LOAD";
			case NORMAL_LOAD:
				return "NORMAL_LOAD";
			case LOW_LOAD:
				return "LOW_LOAD";
			case CRASH:
				return "CRASH";
		}
		return super.toString();
	}
}
