package appcloud.api.enums;

public enum AcStrategyTypeEnum {
	CPU_MEMORY, DISK;
	public String toString() {
		switch (this) {
			case CPU_MEMORY:
				return "cpu_memory";
			case DISK:
				return "disk";
		}
		return super.toString();
	}
}
