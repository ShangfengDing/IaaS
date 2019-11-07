package appcloud.api.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.Responses;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import appcloud.api.beans.Load;
import appcloud.api.beans.Meta;
import appcloud.api.beans.Metadata;
import appcloud.api.beans.MetadataAdaptor;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.beans.server.ServerUpdateReq;
import appcloud.api.enums.ServerOperationEnum;
import appcloud.api.exception.ServerException;

public class ServerClient extends AbstractClient<Server>{
	private final static String OS_START = XML_HEADER + "<" + ServerOperationEnum.OS_START + "/>";
	private final static String OS_STOP = XML_HEADER + "<" + ServerOperationEnum.OS_STOP + "/>";
	private final static String OS_RESUME = XML_HEADER + "<" + ServerOperationEnum.OS_RESUME + "/>";
	private final static String OS_SUSPEND = XML_HEADER + "<" + ServerOperationEnum.OS_SUSPEND + "/>";
	private final static String FORCE_DELETE = XML_HEADER + "<" + ServerOperationEnum.FORCE_DELETE + "/>";
	private final static String RESIZE_PRE = XML_HEADER +"<" + ServerOperationEnum.RESIZE + " flavorRef=\"";
	private final static String RESIZE_POST = "\"/>";
	private final static String ISO_BOOT_PRE = XML_HEADER +"<" + ServerOperationEnum.AC_ISO_BOOT + " imageRef=\"";
	private final static String ISO_BOOT_POST  = "\"/>";
	private final static String ISO_DETACH = XML_HEADER +"<" + ServerOperationEnum.AC_ISO_DETACH + "/>";
	private final static String FORCE_STOP = XML_HEADER +"<" + ServerOperationEnum.AC_FORCE_STOP + "/>";
	private final static String AC_FORCE_REFRESH = XML_HEADER +"<" + ServerOperationEnum.AC_FORCE_REFRESH+"/>";
	private final static String AC_REST = XML_HEADER +"<" + ServerOperationEnum.AC_RESET+"/>";
	private final static String AC_MIGRATE_PRE = XML_HEADER + "<" +ServerOperationEnum.AC_MIGRATE;
	private final static String AC_MIGRATE_POST = "/>";
	private final static String AC_ONLINE_MIGRATE_PRE = XML_HEADER + "<" +ServerOperationEnum.AC_ONLINE_MIGRATE;
	private final static String AC_ONLINE_MIGRATE_POST = "/>";
	private final static String AC_MODIFYPASSWD_PRE = XML_HEADER +"<" + ServerOperationEnum.AC_MODIFYPASSWORD;
	private final static String AC_MODIFYPASSWD_POST = "/>";
	
	MetadataAdaptor adaptor = new MetadataAdaptor();
	
	public ServerClient(){
		super();
	}

	public ServerClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Server.class;
	}

	@Override
	protected GenericType<List<Server>> getGenericType() {		
		GenericType<List<Server>> type = new GenericType<List<Server>>() {};
		return type;
	}

	private Class<Metadata> getMetadataType() {
		return Metadata.class;
	}

	private Class<Meta> getMetaType() {
		return Meta.class;
	}

	private String getPath() {
		return  "servers";
	}

	//@Override
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}

	//@Override
	protected String buildPathWithId(String tenantId, String serverId) {
		return String.format("%s/%s", buildPath(tenantId), serverId);
	}

	private String buildActionPath(String tenantId, String serverId) {
		return String.format("%s/%s/%s/action", tenantId, getPath(), serverId);
	}

	private String buildServerNamePath(String tenantId) {
		return String.format("%s/%s/servername", tenantId, getPath());
	}
	
	private String buildMonitorPath(String tenantId, String serverId) {
		return String.format("%s/%s/%s/ac-monitors", tenantId, getPath(), serverId);
	}

	private String buildMetadataPath(String tenantId, String serverId) {
		return String.format("%s/metadata", buildPathWithId(tenantId, serverId));
	}

	private String buildMetadataItemPath(String tenantId, String serverId, String metaKey, boolean release) {
		return String.format("%s/%s/%s", buildMetadataPath(tenantId, serverId), metaKey, release);
	}

	public Server get(String tenantId, String serverId) {
		return super.get(buildPathWithId(tenantId, serverId));
	}

	public List<Server> getServers(String tenantId, boolean detailed) {
		if(detailed)
			return super.getList(buildPath(tenantId) + "/detail", null);
		return super.getList(buildPath(tenantId), null);
	}
	public List<Server> getAllTenantsServers(String tenantId, boolean detailed) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("all_tenants", "true");
		if(detailed)
			return super.getList(buildPath(tenantId) + "/detail", params);
		return super.getList(buildPath(tenantId), params);
	}

	public Server createServer(String tenantId, ServerCreateReq cReq){
		return super.postWithRet(buildPath(tenantId), cReq);
	}

	public Server migrateServer(String tenantId, String serverId, String hostUuid) {
		String req = AC_MIGRATE_PRE;
		if(hostUuid != null && !hostUuid.equals(""))
			req = req + " hostUuid=\"" + hostUuid + "\"" + AC_MIGRATE_POST;
		else 
			req = req + " hostUuid=\"\"" + AC_MIGRATE_POST;
		return super.postWithRet(buildActionPath(tenantId, serverId), req);
	}
	
	public Server onlineMigrateServer(String tenantId, String serverId, String hostUuid) {
		String req = AC_ONLINE_MIGRATE_PRE;
		if(hostUuid != null && !hostUuid.equals(""))
			req = req + " hostUuid=\"" + hostUuid + "\"" + AC_MIGRATE_POST;
		else 
			req = req + " hostUuid=\"\"" + AC_ONLINE_MIGRATE_POST;
		return super.postWithRet(buildActionPath(tenantId, serverId), req);
	}

	public boolean terminateServer(String tenantId, String serverId) {
		return super.delete(buildPathWithId(tenantId, serverId));
	}

	public boolean osStart(String tenantId, String serverId) {
		return super.postWithoutRet(buildActionPath(tenantId, serverId), OS_START);
	}

	public boolean osStop(String tenantId, String serverId){
		return super.postWithoutRet(buildActionPath(tenantId, serverId), OS_STOP);
	}

	public boolean resume(String tenantId, String serverId){
		return super.postWithoutRet(buildActionPath(tenantId, serverId), OS_RESUME);
	}

	public boolean reboot(String tenantId, String serverId, ServerActionReboot reboot){
		return super.postWithoutRet(buildActionPath(tenantId, serverId), reboot);
	}

	/**
	 * 修改计算资源
	 * @param tenantId
	 * @param serverId
	 * @param rebuild <strong>ATTENTIONS:</strong> 注意，不更改字段标记为NULL
	 * @return
	 */
	public Server rebuild(String tenantId, String serverId, ServerActionRebuild rebuild){
		return super.postWithRet(buildActionPath(tenantId, serverId), rebuild);
	}

	
	public boolean resize(String tenantId, String serverId, String flavorRef){
		String resizeReq = RESIZE_PRE + flavorRef + RESIZE_POST;
		return super.postWithoutRet(buildActionPath(tenantId, serverId), resizeReq);
	}

	public boolean reset(String tenantId, String serverId) {
		return super.postWithoutRet(buildActionPath(tenantId, serverId), AC_REST);
	}

	public boolean suspend(String tenantId, String serverId){
		return super.postWithoutRet(buildActionPath(tenantId, serverId), OS_SUSPEND);
	}

	public boolean forceDelete(String tenantId, String serverId){
		return super.postWithoutRet(buildActionPath(tenantId, serverId), FORCE_DELETE);
	}

	@SuppressWarnings("unchecked")
	public List<Load> getLoads(String tenantId, String serverId, String type, Date sTime, Date eTime) {
		@SuppressWarnings("rawtypes")
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		queryParams.add("stime", dateFormat.format(sTime));
		queryParams.add("etime", dateFormat.format(eTime));
		queryParams.add("type", type);

		List<Load> loads = resource.path(buildMonitorPath(tenantId, serverId)).queryParams(queryParams).get(new GenericType<List<Load>>() {});

		return loads;

	}
	/**
	 * 在不关机的条件下，修改虚拟机名称
	 */
	public Server updateServerName(String tenantId, ServerUpdateReq uReq) {
		return super.postWithRet(buildServerNamePath(tenantId), uReq);
	}

	public boolean changeSecurityGroup(String tenantId, String serverId, Integer sgId) {
		ServerActionRebuild rebuild = new ServerActionRebuild();

		// make sure all others is null
		rebuild.metadata = null;
		rebuild.availabilityZone = null;
		rebuild.flavorRef = null;
		rebuild.name = null;
		rebuild.imageRef = null;

		// change sg
		rebuild.securityGroup = new SecurityGroup(sgId, null, null, null, null);

		return super.postWithoutRet(buildActionPath(tenantId, serverId), rebuild);
	}

	/**
	 * 修改云主机的用户Id，用于将用户的云主机充公
	 */
	public Server updateOwner(String tenantId, String serverId, String userId) {
		return this.update(tenantId, serverId, userId, null, null, null, null, null, null);
	}

	private Server update(String tenantId, String serverId, String userId, String serverName, 
			String status, Integer aggregateId, String hostId, String serverIP, Date createTime) {
		Server server = new Server();
		if(!isBlank(userId)) 
			server.tenantId = userId;
		if(!isBlank(serverName)) 
			server.name = serverName;

		return super.update(buildPathWithId(tenantId, serverId), server);
	}

	private boolean isBlank(String param) {
		if(param == null || param.equals(""))
			return true;
		else 
			return false;
	}

	public Map<String, String> getMetadata(String tenantId, String serverId) throws Exception{
		Metadata metadata = 
		resource.path(buildMetadataPath(tenantId, serverId)).get(getMetadataType());
		return adaptor.unmarshal(metadata);
	}

	public Map<String, String>  updateMetadata(String tenantId, String serverId, 
			Map<String, String> metadata) throws Exception{
		Metadata metadataGot = resource.path(buildMetadataPath(tenantId, serverId))
				.type(MediaType.APPLICATION_XML).post(getMetadataType(), adaptor.marshal(metadata));
		return adaptor.unmarshal(metadataGot);
	}

	public Map<String, String>  setMetadata(String tenantId, String serverId, 
			Map<String, String> metadata) throws Exception{
		Metadata metadataGot = resource.path(buildMetadataPath(tenantId, serverId))
				.type(MediaType.APPLICATION_XML).put(getMetadataType(), adaptor.marshal(metadata));
		return adaptor.unmarshal(metadataGot);
	}

	public String getMetadataItem(String tenantId, String serverId, String key){
		Meta meta =  resource.path(buildMetadataItemPath(tenantId, serverId, key,true)).get(getMetaType());
		if(meta != null)
			return meta.value;
		else
			return null;
	}
	/**
	 * 在不关机的条件下，修改虚拟机的描述和带宽
	 * @return 修改后的值
	 * @author hgm
	 */
	public String updateMetadataItem(String tenantId, String serverId, String key, String value, boolean release){
		Meta metaPost = new Meta(key, value);
		Meta metaGot =  resource.path(buildMetadataItemPath(tenantId, serverId, key, release))
				.type(MediaType.APPLICATION_XML).post(getMetaType(), metaPost);
		if(metaGot != null)
			return metaGot.value;
		else
			return null;
	}

	public String setMetadataItem(String tenantId, String serverId, String key, String value){
		Meta metaPut = new Meta(key, value);
		Meta metaGot =  resource.path(buildMetadataItemPath(tenantId, serverId, key,true))
				.type(MediaType.APPLICATION_XML).put(getMetaType(), metaPut);
		if(metaGot != null)
			return metaGot.value;
		else
			return null;
	}

	public boolean bootFromISO(String tenantId, String serverId, String isoUuid) {
		String isoReq = ISO_BOOT_PRE + isoUuid + ISO_BOOT_POST;
		return super.postWithoutRet(buildActionPath(tenantId, serverId), isoReq);
	}

	public boolean detachISO(String tenantId, String serverId) {
		return super.postWithoutRet(buildActionPath(tenantId, serverId), ISO_DETACH);
	}

	public boolean forceStop(String tenantId, String serverId) {
		return super.postWithoutRet(buildActionPath(tenantId, serverId), FORCE_STOP);
	}

	public String forceRefresh(String tenantId, String serverId) {
		try{
			return resource.path(buildActionPath(tenantId, serverId)).
					type(MediaType.APPLICATION_XML).post(String.class, AC_FORCE_REFRESH);
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print(" forceRefresh Item Not Found");
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error");
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error");
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	public List<Server>searchByProperties(String tenantId, String userId, String serverId, String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startDate, Date endDate, Integer page, Integer size){
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if(serverId != null)
			params.add("server_id", serverId);
		if(serverName != null)
			params.add("server_name", serverName);
		if(status != null)
			params.add("status", status); 
		if(zoneId != null)
			params.add("zone_id",String.valueOf(zoneId));
		if(aggregateId != null)
			params.add("aggregate_id",String.valueOf(aggregateId)); 
		if(hostId != null)
			params.add("host_id", hostId);
		if(serverIp != null)
			params.add("server_ip", serverIp);
		if(startDate != null)
			params.add("start_date", String.valueOf(startDate.getTime()));
		if(endDate != null)
			params.add("end_date", String.valueOf(endDate.getTime()));
		if(page!= null)
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));
		return super.getList(String.format("%s/search", buildPath(tenantId)), params);
	}

	public Long countByProperties(String tenantId, String userId, String serverId,  String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startDate, Date endDate) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if(serverId != null)
			params.add("server_id", serverId);
		if(serverName != null)
			params.add("server_name", serverName);
		if(status != null)
			params.add("status", status); 
		if(zoneId != null)
			params.add("zone_id",String.valueOf(zoneId));
		if(aggregateId != null)
			params.add("aggregate_id",String.valueOf(aggregateId)); 
		if(hostId != null)
			params.add("host_id", hostId);
		if(serverIp != null)
			params.add("server_ip", serverIp);
		if(startDate != null)
			params.add("start_date", String.valueOf(startDate.getTime()));
		if(endDate != null)
			params.add("end_date", String.valueOf(endDate.getTime()));
		
		return super.count(String.format("%s/search/count", buildPath(tenantId)), params);
	}

	public boolean modifyPasswd(String tenantId, String serverId, String userName, String newPasswd, String osType) {
		String req = AC_MODIFYPASSWD_PRE ;
		if(userName != null)
			req += " userName=\"" + userName + "\"";
		else
			req += " userName=\"\"";
		if(newPasswd != null)
			req += " newPasswd=\"" + newPasswd + "\"";
		else
			req += " newPasswd=\"\"";
		if(osType != null)
			req += " osType=\"" + osType + "\"";
		else
			req += " osType=\"\"";
		req += AC_MODIFYPASSWD_POST;
		return super.postWithoutRet(buildActionPath(tenantId, serverId), req);
	}
	public static void main(String[] args) {
		/*ServerUpdateReq uReq = new ServerUpdateReq("688c9ff6780f419ca964d5252f9232e8", "mytest11111",null);
		Server server = new ServerClient().updateServerName("775", uReq);
		System.out.println(server.name);*/
//		new ServerClient().osStart("721", "45793fb527894feb893647189fee65b2");
//		new ServerClient().modifyPasswd("721", "bc750ba65b6743fda777eba7d1543234", "yunhai", null, null);
		new ServerClient().onlineMigrateServer("admin", "f89646ec64c54dd4b7d6fa3fd90845d2", "549F3503ABB9");//to 192.168.1.14
	}
}
