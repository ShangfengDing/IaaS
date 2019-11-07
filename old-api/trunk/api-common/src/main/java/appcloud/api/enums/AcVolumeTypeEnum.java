package appcloud.api.enums;

public enum AcVolumeTypeEnum {	
	SYSTEM, DATA, NETWORK, ISO, BACKUP ;
	public String toString() {
		switch(this) {
		case SYSTEM: 
			return "SYSTEM";
		case DATA: 
			return "DATA";
		case NETWORK: 
			return "NETWORK";
		case ISO: 
			return "ISO";
		case BACKUP: 
			return "BACKUP";
		}
		return super.toString();
	}
}
