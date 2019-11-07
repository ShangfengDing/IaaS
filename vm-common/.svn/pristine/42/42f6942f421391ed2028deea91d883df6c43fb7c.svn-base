package appcloud.common.util;

import appcloud.common.model.VmIpSegMent;

/**
 * @ClassName: IpUtils
 * @author wenchaoz361
 * @date 2013-4-9 下午4:17:11
 */
public class IpUtils {
    
    /**
     * @author haowei.yu
     * String ipRag = "172.16.0.0/23";
     * 得到起始ip
     */
    public static String getStartIp(String Ip, int netmask)
    {
        return getStartIp(Ip, getNetmask(netmask));
    }
    
    public static String getStartIp(String Ip, String netmask)
    {
        String[] start = OperAnd(Ip, netmask).split("\\.");
        String startIP = start[0] + "." + start[1] + "." + start[2] + "." + (Integer.valueOf(start[3]) + 1);
        return startIP;
    }
    
    private static String OperAnd(String Ip,String netmask)
    {
        String[] temp1 = Ip.trim().split("\\.");
        String[] temp2 = netmask.trim().split("\\.");
        int[] rets = new int[4];
        for (int i = 0; i < 4; i++) {
            rets[i] = Integer.parseInt(temp1[i])&Integer.parseInt(temp2[i]);
        }
        return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
    }
    
    /**
     * 
     * @author haowei.yu
     * String ipRag = "172.16.0.0/23";
     * 得到截至ip
     */
    public static String getEndIp(String Ip, int netmask)
    {
        return getEndIp(Ip, getNetmask(netmask));
    }
   
    
    public static String getEndIp(String Ip, String netmask){
        return OperOr( OperAnd ( Ip, netmask), netmask);
    }
    
    private static String OperOr(String StartIP,String netmask)
    {
        String[] temp1 = StartIP.trim().split("\\.");
        String[] temp2 = netmask.trim().split("\\.");
        int[] rets = new int[4];
        for (int i = 0; i < 4; i++) {
            rets[i] = 255-(Integer.parseInt(temp1[i])^Integer.parseInt(temp2[i]));
        }
        return rets[0] + "." + rets[1] + "." + rets[2] + "." + (rets[3]-1);
    }
    
   
    
    public static Integer getSegment(Integer ipSeg, Integer netmask) {
        return ipSeg & (-1 << (32-netmask));
    }

    
    public static String getNetmask(Integer netmask) {
        return ipToString(getSegment(0xffffffff, netmask));
    }
    
    
    public static boolean contain(Integer ipSeg, Integer netmask, Integer ip) {
        Integer seg1 = getSegment(ipSeg, netmask);
        Integer seg2 = getSegment(ip, netmask);
        if(seg1.equals(seg2)) return true;
        else return false;
    }
    
    public static boolean contain(VmIpSegMent vmIpSegment, Integer ip) {
    	Integer ipStart = stringToIp(vmIpSegment.getIpStartRange());
    	Integer ipEnd = stringToIp(vmIpSegment.getIpEndRange());
    	if(ip >= ipStart && ip <= ipEnd) return true;
    	else return false;
    }
    
    public static void main(String[] args) {
    	VmIpSegMent vmIpSegment = new VmIpSegMent();
    	vmIpSegment.setIpStartRange("192.168.0.2");
    	vmIpSegment.setIpEndRange("192.168.0.5");
    	Integer ip = IpUtils.stringToIp("192.168.0.7");
    	if(IpUtils.contain(vmIpSegment, ip)) {
    		System.out.println("ok");
    	}else {
    		System.out.println("no");
    	}
    }
    
    public static Integer getNetmaskInt(String netmask) {
        Integer netmaskInt = stringToIp(netmask);
        String str = Integer.toBinaryString(netmaskInt);
        int r = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1')
                r++;
            else
                break;
        }
        return r;
    }
    
    /**
     * @Title: ipToString
     * @Description: 将ip转换为字符串
     * @param ip
     * @return
     */ 
     public static String ipToString(Integer ip) {
         Integer pos[] = {0,0,0,0};
         for(int i = 0; i < 4; i++) {
             pos[3-i] = (ip & (0xff << (i * 8))) >>> (i*8);
         }
         
         StringBuilder sb = new StringBuilder();
         for(int i = 0; i < 4; i++) {
             sb.append(pos[i]);
             if(i != 3) sb.append('.');
         }
         
         return sb.toString();
     }
     
     /**
     * @Title: stringToIp
     * @Description: 将ip字符串转换为整数
     * @param ipStr
     * @return
     */ 
     public static Integer stringToIp(String ipStr) {
         Integer ip = 0;
         String[] strs = ipStr.split("\\.");
         for(int i = 0; i < 4; i++) {
             ip += Integer.parseInt(strs[i]) << ((3-i)*8);
         }
         return ip;
     }
}
