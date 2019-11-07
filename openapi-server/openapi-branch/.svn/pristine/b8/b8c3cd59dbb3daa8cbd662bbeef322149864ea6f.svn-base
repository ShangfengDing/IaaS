package appcloud.openapi.manager.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import appcloud.common.model.MessageLog;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;

/**
 * @author hgm
 */

public class LolHelper {
	private static Logger logger = Logger.getLogger(LolHelper.class);
	
	//TODO 本机ip
	private static String ipAddress = getIp();
	
	public static LolLogUtil getLolLogUtil(Class<?> clazz) {
		LolLogUtil loller = null;
		try {
			loller = new LolLogUtil(MessageLog.ModuleEnum.API_SERVER, clazz, ipAddress);		
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
		return loller;
	}
	
	private static String getIp() {
		Enumeration <NetworkInterface>allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements())
			{
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				//System.out.println(netInterface.getName());
				Enumeration <InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements())
				{
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address && ip.getHostAddress().startsWith("192"))
					{
						//System.out.println("本机的IP = " + ip.getHostAddress());
						return ip.getHostAddress();
					} 
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "127.0.0.1";
	}
	
}
