package appcloud.api.enums;

public enum VolumeOperationEnum {
	CREATE_BACKUP, REVERT_BACKUP, CREATE_SNAPSHOT, REVERT_SNAPSHOT, ATTACH, DETTACH, DELETE;
	
	@Override
	public String toString() {
		switch(this) {
		case CREATE_BACKUP:
			return "create_backup";
		case REVERT_BACKUP:
			return "revert_backup";
		case CREATE_SNAPSHOT:
			return "create_snapsht";
		case REVERT_SNAPSHOT:
			return "revert_snapshot";
		case ATTACH:
			return "attach";
		case DETTACH:
			return "detach";
		case DELETE:
			return "delete";
		}
		return super.toString();
	}
}
