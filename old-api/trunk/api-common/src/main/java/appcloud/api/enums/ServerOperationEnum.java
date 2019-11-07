package appcloud.api.enums;

public enum ServerOperationEnum {
	OS_START, OS_STOP, OS_SUSPEND, OS_RESUME, FORCE_DELETE, AC_FORCE_STOP, RESIZE, 
	AC_ISO_BOOT, AC_ISO_DETACH, REBOOT, REBUILD, AC_FORCE_REFRESH, 
	DELETE, AC_RESET, AC_MODIFYPASSWORD, AC_MIGRATE, AC_ONLINE_MIGRATE;
	
	@Override
	public String toString() {
		switch (this) {
		case OS_START:
			return "os-start";
		case OS_STOP:
			 return "os-stop";
		case OS_SUSPEND:
			return"os-suspend";
		case OS_RESUME:
			return "os-resume";
		case FORCE_DELETE:
			return "forceDelete";
		case AC_FORCE_STOP:
			return "ac-force_stop";
		case RESIZE:
			return "resize";
		case AC_ISO_BOOT:
			return "ac-iso_boot";
		case AC_ISO_DETACH:
			return "ac-iso_detach";
		case REBOOT:
			return "reboot";
		case REBUILD:
			return "rebuild";
		case AC_FORCE_REFRESH:
			return "ac-force_refresh";
		case DELETE:
			return "delete";
		case AC_RESET:
			return "ac-rest";
		case AC_MODIFYPASSWORD:
			return "ac-modify_password";
		case AC_MIGRATE:
			return "ac-migrate";
		case AC_ONLINE_MIGRATE:
			return "ac-online_migrate";
		}
		return super.toString();
	}
	
}
