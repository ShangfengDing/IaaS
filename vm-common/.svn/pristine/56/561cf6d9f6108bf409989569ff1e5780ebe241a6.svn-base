package appcloud.common.util.io;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class IpUtil {
	private static Logger logger = Logger.getLogger(IpUtil.class);
	public static String getIpv4Address() {
		Enumeration<NetworkInterface> netInterfaces = null;    
		try {    
		    netInterfaces = NetworkInterface.getNetworkInterfaces();    
		    while (netInterfaces.hasMoreElements()) {
		        NetworkInterface ni = netInterfaces.nextElement();
		        if(ni.getName() == null || !ni.getName().startsWith("eth")) {
		        	continue;
		        }
		        Enumeration<InetAddress> ips = ni.getInetAddresses();   
		        Pattern p = Pattern.compile("([0-9]+\\.)([0-9]+\\.)([0-9]+\\.)([0-9]+)");
		        while (ips.hasMoreElements()) {
		        	String ip = ips.nextElement().getHostAddress();
		        	if(ip!=null && ip.equalsIgnoreCase("127.0.0.1")) {
		        		continue;
		        	}
		        	Matcher m = p.matcher(ip);
		        	if(m.find()) {
		        		logger.info("ipv4 address:"+ip);
		        		return ip;
		        	}
		        }    
		    }    
		} catch (Exception e) {    
		    e.printStackTrace();    
		}  
		return "";
	}
}
