package appcloud.api.manager.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;

public class DealException {
	private static Logger logger = Logger.getLogger(DealException.class);
	private static String ipAddress = null;
	private static LolLogUtil lol = null;
	static {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			ipAddress = addr.getHostAddress().toString();
			lol = new LolLogUtil(MessageLog.ModuleEnum.API_SERVER, DealException.class, ipAddress);
		} catch (UnknownHostException e) {
			logger.warn("get api-server ip address failed, use null instead");
			ipAddress = null;
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public static void isRSTimeoutException(RpcException e, String operation, RpcExtra rpcExtra) throws RpcException{
		if(e instanceof RpcTimeoutException) {
			logger.error("no response from resource scheduler");
			lol.error(operation, "no response from resource scheduler", rpcExtra);
		} else {
			throw e;
		}
	}
}
