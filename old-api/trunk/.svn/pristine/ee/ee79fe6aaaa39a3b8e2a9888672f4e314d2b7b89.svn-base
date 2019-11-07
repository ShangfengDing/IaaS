package appcloud.api.client;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcMessageLogClient extends AbstractClient<AcMessageLog>{
	
	public AcMessageLogClient(){
		super();
		acMailConfClient = new AcMailConfClient();
	}
	
	public AcMessageLogClient(String baseURI) {
		super(baseURI);
		acMailConfClient = new AcMailConfClient(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcMessageLog.class;
	}
	
	@Override
	protected GenericType<List<AcMessageLog>> getGenericType() {		
		GenericType<List<AcMessageLog>> type = new GenericType<List<AcMessageLog>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-message-log";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	public List<AcMessageLog> searchLog(AcMessageLog messageLog,  
			Date startDate, Date endDate, Integer count) {
		if(startDate != null && endDate != null ) {
			if(startDate.getTime() > endDate.getTime())
				return null;
		}
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(messageLog != null) {
			if(messageLog.module != null)
				params.add("module", messageLog.module.name());
			if(messageLog.transactionId != null)
				params.add("transaction_id", messageLog.transactionId);
			if(messageLog.userId != null)
				params.add("user_id", messageLog.userId);
			if(messageLog.vmHdUuid != null)
				params.add("vm_hd_uuid", messageLog.vmHdUuid);
			if(messageLog.operateDrpt != null)
				params.add("operation", messageLog.operateDrpt);
			if(messageLog.logContent != null)
				params.add("log_content", messageLog.logContent);
			if(messageLog.sourceClass != null)
				params.add("source_class", messageLog.sourceClass);
			if(messageLog.ipAddress != null)
				params.add("ip_address", messageLog.ipAddress);
			if(messageLog.logLevel != null)
				params.add("log_level", messageLog.logLevel.name());
		}
		if(startDate != null)
			params.add("start_date", startDate.getTime() + "");
		if(endDate != null)
			params.add("end_date", endDate.getTime() + "");
		params.add("count",count.toString());
		return super.getList(buildPath(), params);
	}
	
	public List<AcMessageLog> searchLog(AcMessageLog messageLog,  
			Date startDate, Date endDate, Integer count, Integer order) {
		if(startDate != null && endDate != null ) {
			if(startDate.getTime() > endDate.getTime())
				return null;
		}
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(messageLog != null) {
			if(messageLog.module != null)
				params.add("module", messageLog.module.name());
			if(messageLog.transactionId != null)
				params.add("transaction_id", messageLog.transactionId);
			if(messageLog.userId != null)
				params.add("user_id", messageLog.userId);
			if(messageLog.vmHdUuid != null)
				params.add("vm_hd_uuid", messageLog.vmHdUuid);
			if(messageLog.operateDrpt != null)
				params.add("operation", messageLog.operateDrpt);
			if(messageLog.logContent != null)
				params.add("log_content", messageLog.logContent);
			if(messageLog.sourceClass != null)
				params.add("source_class", messageLog.sourceClass);
			if(messageLog.ipAddress != null)
				params.add("ip_address", messageLog.ipAddress);
			if(messageLog.logLevel != null)
				params.add("log_level", messageLog.logLevel.name());
		}
		if(startDate != null)
			params.add("start_date", startDate.getTime() + "");
		if(endDate != null)
			params.add("end_date", endDate.getTime() + "");
		params.add("count",count.toString());
		
		if(order == null) {
			params.add("order", "0");//0顺序
		} else {
			params.add("order", order.toString());
		}
		return super.getList(buildPath(), params);
	}
	
	public AcMessageLog addLog(AcMessageLog log) {
		return super.postWithRet(buildPath(), log);
	}
	
	private AcMailConfClient acMailConfClient;
	public AcMailConf getMailConf() {
		return acMailConfClient.getMailConf();
	}
	
	public AcMailConf setMailConf(AcMailConf acMailConf) {
		return acMailConfClient.setMailConf(acMailConf);
	}
}
