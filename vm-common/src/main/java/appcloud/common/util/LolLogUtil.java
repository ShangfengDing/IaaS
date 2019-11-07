package appcloud.common.util;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.MessageLog;
import appcloud.common.model.MessageLog.ModuleEnum;
import appcloud.common.model.RpcExtra;
import appcloud.common.service.LolLogService;
import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

public class LolLogUtil {
	
	private static Logger logger = Logger.getLogger(LolLogUtil.class);
	private static LolLogService lolService = null;
	private ModuleEnum MODULE = MessageLog.ModuleEnum.UN_KNOWN;
	private String sourceClass = "";
	private String ipAddress = "";
	
	public static final String CREATE_VM = "create_vm";
	public static final String CREATE_VOLUME = "create_volume";
	public static final String CREATE_VOLUME_IMAGEBACK = "create_volume_imageback";
	public static final String DELETE_VOLUME = "delete_volume";
	public static final String CREATE_SNAPSHOT = "create_snapshot";
	public static final String APPLY_SNAPSHOT = "apply_snapshot";
	public static final String DELETE_SNAPSHOT = "delete_snapshot";
	public static final String RESIZE_RAWIMG = "resize_rawimg";
	public static final String CONVERT_IMG_FORMAT = "convert_img_format";
	public static final String RELEASE_IMG = "release_img";
	public static final String COPY_IMG = "copy_img";
	public static final String MOVE_IMG = "move_img";
	public static final String DOWNLOAD_IMG = "download_img";
	public static final String CREATE_IMG = "create_img";
	public static final String GET_DOWNLOAD_IMG = "get_download_img";
	public static final String DELETE_IMG = "delete_img";
	public static final String GET_IMAGE_SERVER = "get_image_server";
	public static final String DEFINE_VOLUME = "define_volume";
	public static final String DEFINE_VOLUME_IMAGEBACK = "define_volume_imageback";
	public static final String SELECT_HOST = "select_host";
	public static final String DESTROY_VOLUME = "destroy_volume";
	public static final String RESIZE_VOLUME = "resize_volume";
	public static final String CLONE_VOLUME = "clone_volume";
	public static final String MOVE_VOLUME = "move_volume";
	public static final String DEFINE_SNAPSHOT = "define_snapshot";
	public static final String PUBLISH_IMAGE = "publish_image";
	public static final String REBOOT_DHCP = "reboot_dhcp";
	public static final String GET_NEW_MAC_ADDRESS = "get_new_mac_address";
	public static final String GET_NEW_PRIVATE_IPADDRESS = "get_new_private_ipaddress";
	public static final String GET_NEW_PUBLIC_IPADDRESS = "get_new_public_ipaddress";
	public static final String RELEASE_PRIVATE_IPADDRESS = "release_private_ipaddress";
	public static final String RELEASE_PUBLIC_IPADDRESS = "release_public_ipaddress";
	public static final String ADD_PUBLIC_NET_IP_SEGMENT = "add_public_net_ip_segment";
	public static final String ADD_PRIVATE_NET_IP_SEGMENT = "add_private_net_ip_segment";
	public static final String DEL_NET_IP_SEGMENT = "del_net_ip_segment";
	public static final String SET_VM_MAX_BANDWIDTH = "set_vm_max_bandwidth";
	public static final String CANCEL_VM_MAX_BANDWIDTH = "cancel_vm_max_bandwidth";
	public static final String START_VM = "start_vm";
	public static final String DELETE_VM = "delete_vm";
	public static final String STOP_VM = "stop_vm";
	public static final String FORCE_STOP_VM = "force_stop_vm";
	public static final String REBOOT_DOMAIN = "reboot_domain";
	public static final String SUSPEND_VM = "suspend_vm";
	public static final String RESUME_VM = "resume_vm";
	public static final String RESIZE_VM = "resize_vm";
	public static final String GET_VNC_PORT = "get_vnc_port";
	public static final String GET_DOMAIN_STATE = "get_domain_state";
	public static final String DEFINE_NETWORK_FILTER = "define_network_filter";
	public static final String DELETE_NETWORK_FILTER = "delete_network_filter";
	public static final String HANDLE_OP = "handle_op";
	public static final String GET_VM_STATE = "get_vm_state";
	public static final String HANDLE_VOLUME = "handle_volume";
	public static final String DEFINE_SECURITY_GROUP = "define_security_group";
	public static final String DEL_SECURITY_GROUP = "del_security_group";
	public static final String ATTACH_SECURITY_GROUP = "attatch security group";
	public static final String CREATE_AGGREGATE = "create_aggregate";
	public static final String ADD_HOST_TO_AGGREGATE = "add_host_to_aggregate";
	public static final String REMOVE_HOST_FROM_AGGREGATE = "remove_host_from_aggregate";
	public static final String CREATE_FLAVOR = "create_flavor";
	public static final String UPDATE_IMAGE = "update_image";
	public static final String CREATE_IP_SEGMENT = "create_ip_segment";
	public static final String DEL_NETIP_SEGMENT = "del_netip_segment";
	public static final String CREATE_SECURITY_GROUP = "create_security_group";
	public static final String DELETE_SECURITY_GROUP = "delete_security_group";
	public static final String CREATE_SECURITY_GROUP_RULE = "create_security_group_rule";
	public static final String DELETE_SECURITY_GROUP_RULE = "delete_security_group_rule";
	public static final String UPDATE_SECURITY_GROUP = "update_security_group";
	public static final String REBOOT_VM = "reboot_vm";
	public static final String REBUILD_VM = "rebuild_vm";
	public static final String TERMINATE_VM = "terminate_vm";
	public static final String BOOT_FROM_ISO = "boot_from_iso";
	public static final String DETACH_ISO = "detach_iso";
	public static final String ATTACH_VOLUME = "attach_volume";
	public static final String DETACH_VOLUME = "detach_volume";
	public static final String UPDATE_VOLUME = "update_volume";
	public static final String REVERT_SNAPSHOT = "revert_snapshot";
	public static final String UPDATE_SNAPSHOT = "update_snapshot";
	public static final String CREATE_BACKUP = "create_backup";
	public static final String DELETE_BACKUP = "delete_backup";
	public static final String REVERT_BACKUP = "revert_backup";
	public static final String UPDATE_BACKUP = "update_backup";
	public static final String FORCE_DELETE_VM = "force_delete_vm";	
	public static final String CREATE_AVAILABILITY_ZONE = "create_availability_zone ";
	public static final String UPDATE_AVAILABILITY_ZONE = "update_availability_zone ";
	public static final String DELETE_AVAILABILITY_ZONE = "delete_availability_zone ";
	public static final String REVERT_VOLUME = "revert_volume";
	public static final String UPDATE_SERVER = "update_volume";
	public static final String MOD_VM_PASSWD = "mod_vm_passwd";
	public static final String MOD_VM_HOSTNAME = "mod_vm_hostname";
	public static final String ONLINE_MIGRATION = "online_migration";
	public static final String OFFLINE_MIGRATION = "offline_migration";
	public static final String GET_VMGROUP = "get_vmgroup";
	public static final String GET_IMG_MD5SUM = "get_img_md5sum";
	public static final String GET_IMG_GROUP = "get_img_group";
	public static final String GET_GROUPCLUSTERIDS = "get_groupclusterids";
	public static final String GET_VOLUME = "get_volume";
	public static final String UPLOAD_IMAGE = "upload_image";
	public static final String AUTHORIZE_IMAGE = "authorize_image";
	public static final String UPDATE_SERVER_NAME = "update_server_name";
	public static final String UPDATE_SERVER_DESC = "update_server_desc";
	public static final String UPDATE_BANDWIDTH = "update_bandwidth";
	
	
	public LolLogUtil(ModuleEnum module, Class<?> sourceClazz, String ipAddress) throws RpcException {
		MODULE = module;
		this.sourceClass = sourceClazz.toString();
		this.ipAddress = ipAddress;
		init();
	}
	
	private static synchronized void init() throws RpcException {
		if(lolService == null) {
			lolService = (LolLogService) ConnectionFactory.getAMQPService(LolLogService.class, RoutingKeys.LOL_SERVER);
			if(lolService == null) {
				logger.error("cannot init LolLogService, check the routingkey in rabbitmq or the java process of lol-server");
				throw new RpcException("cannot init LolLogService, check the routingkey in rabbitmq or the java process of lol-server");
			}
		}
	}
	private static void add(MessageLog message) throws RpcException {
		if(message == null) {
			return ;
		}
		init();
		lolService.add(message);
	}
	
	private static List<MessageLog> search(MessageLog message, Timestamp startTime, Timestamp endTime, Integer size) throws RpcException {
		init();
		List<MessageLog> result = lolService.search(message, startTime, endTime, size);
		return result;		
	}
	
	private static boolean isBlank(String value) {
		if(value == null || "".equals(value)) {
			return true;
		}
		return false;
	}
	
	public void info(String operateDrpt, String logContent, RpcExtra rpcExtra) throws RpcException {
		MessageLog message  = new MessageLog(MODULE, null, null, operateDrpt, logContent, this.sourceClass, ipAddress, MessageLog.LogLevelEnum.INFO);
		if(rpcExtra != null) {
			if(!isBlank(rpcExtra.getTranctionId())) {
				message.setTranctionId(rpcExtra.getTranctionId());
			}			
			if(!isBlank(rpcExtra.getUserId())) {
				message.setUserId(rpcExtra.getUserId());
			}
			if(!isBlank(rpcExtra.getVmUuid())) {
				message.setVmUuid(rpcExtra.getVmUuid());
			}
		}
		
		add(message);
	}
	
	public void warn(String operateDrpt, String logContent, RpcExtra rpcExtra) throws RpcException {
		MessageLog message  = new MessageLog(MODULE, null, null, operateDrpt, logContent, this.sourceClass, ipAddress, MessageLog.LogLevelEnum.WARN);
		if(rpcExtra != null) {
			if(!isBlank(rpcExtra.getTranctionId())) {
				message.setTranctionId(rpcExtra.getTranctionId());
			}			
			if(!isBlank(rpcExtra.getUserId())) {
				message.setUserId(rpcExtra.getUserId());
			}
			if(!isBlank(rpcExtra.getVmUuid())) {
				message.setVmUuid(rpcExtra.getVmUuid());
			}
		}
		add(message);
	}
	
	public void debug(String operateDrpt, String logContent, RpcExtra rpcExtra) throws RpcException {
		MessageLog message  = new MessageLog(MODULE, null, null, operateDrpt, logContent, this.sourceClass, ipAddress, MessageLog.LogLevelEnum.DEBUG);
		if(rpcExtra != null) {
			if(!isBlank(rpcExtra.getTranctionId())) {
				message.setTranctionId(rpcExtra.getTranctionId());
			}			
			if(!isBlank(rpcExtra.getUserId())) {
				message.setUserId(rpcExtra.getUserId());
			}
			if(!isBlank(rpcExtra.getVmUuid())) {
				message.setVmUuid(rpcExtra.getVmUuid());
			}
		}
		add(message);
	}
	
	public void error(String operateDrpt, String logContent, RpcExtra rpcExtra) throws RpcException {
		MessageLog message  = new MessageLog(MODULE, null, null, operateDrpt, logContent, this.sourceClass, ipAddress, MessageLog.LogLevelEnum.ERROR);
		if(rpcExtra != null) {
			if(!isBlank(rpcExtra.getTranctionId())) {
				message.setTranctionId(rpcExtra.getTranctionId());
			}			
			if(!isBlank(rpcExtra.getUserId())) {
				message.setUserId(rpcExtra.getUserId());
			}
			if(!isBlank(rpcExtra.getVmUuid())) {
				message.setVmUuid(rpcExtra.getVmUuid());
			}
		}
		message.setLogTime(new Timestamp(System.currentTimeMillis()));
		add(message);
	}
	
	public void error(String operateDrpt, String logContent) throws RpcException {
		MessageLog message  = new MessageLog(MODULE, null, null, operateDrpt, logContent, this.sourceClass, ipAddress, MessageLog.LogLevelEnum.ERROR);
		message.setLogTime(new Timestamp(System.currentTimeMillis()));
		add(message);
	}
}
