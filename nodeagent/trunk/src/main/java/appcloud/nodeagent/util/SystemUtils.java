package appcloud.nodeagent.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.nodeagent.info.DiskSpeed;
import appcloud.nodeagent.info.NetStat;
import appcloud.nodeagent.info.NetSpeed;

/**
 * 获取主机当前运行状态
 * @author wylazy
 *
 */
public class SystemUtils {

	private static final Logger logger = Logger.getLogger(SystemUtils.class);
	private static final String NFS_PATH= ":/srv/nfs4";
	
	public static final Pattern CPU_HZ_PARTTERN = Pattern
            .compile("cpu[\\s]*?MHz[\\s]*?:[\\s]*?([0-9]+)".toLowerCase());

    public static final Pattern TOTAL_MEM_PARTTERN = Pattern
            .compile("MemTotal:[\\s]*?([0-9]*) ([a-z ])B".toLowerCase());

    public static final Pattern DISK_PARTTERN = Pattern
            .compile("\\n([\\s\\S]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)%[\\s]+?([\\s\\S]+?)");

    public static final Pattern CPU_PARTTERN = Pattern
            .compile("cpu[\\s]+?([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?)");
    
    public static final Pattern FREE_MEM_PARTTERN = Pattern
            .compile("\\n((MemFree)|(Buffers)|(Cached)?):[\\s]*?([0-9]*) ([a-z ])B".toLowerCase());

    public static final Pattern IP_PATTERN = Pattern
    		.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
    
    /**
     * 获取主机当前运行快照
     * @param host
     * @return
     */
    public static HostLoad currentHostLoad(Host host) {

    	HostLoad hostLoad = new HostLoad();
    	hostLoad.setUuid(host.getUuid());
    	hostLoad.setCpuPercent(getCpuPercent());
    	
    	hostLoad.setDiskPercent(getDiskPercent());
    	hostLoad.setAvgLoad(getAvgLoad());
    	hostLoad.setMemPercent(getMemPercent());

    	NetSpeed net = getNetTrafficK();

    	//将Byte转换bit，然后计算百分比
    	hostLoad.setNetInPercent(net.getNetInTrafficK()/128/host.getNetworkMb()*100);
    	hostLoad.setNetOutPercent(net.getNetOutTrafficK()/128/host.getNetworkMb()*100);
    	
    	DiskSpeed diskSpeed = getDiskSpeed();
    	hostLoad.setDiskReadRate(diskSpeed.getDiskReadK());
    	hostLoad.setDiskWriteRate(diskSpeed.getDiskWriteK());

    	hostLoad.setTime(new Timestamp(System.currentTimeMillis()));
    	
    	return hostLoad;
    }
    
    /**
     *
     * @return 磁盘容量MB
     */
    public static int getDiskCap() {
    	
    	List<String> dirList = new ArrayList<String>();
        String command = "df";
        for (String dir : dirList) {
            command += " " + dir;
        }
        String msg = getCmd(command);

        Matcher matcher = DISK_PARTTERN.matcher(msg);

        long diskCap = 0L;
        while (matcher.find()) {
            String sys = matcher.group(1);
            if (sys.contains(getMountNFS())) {
            	diskCap = Long.parseLong(matcher.group(2));
            }
        }
        
        return (int) (diskCap/1024);
    }
    
    /**
     * 
     * @return 可用的磁盘空间，MB
     */
    public static int getAvailableDisk() {
    	
    	List<String> dirList = new ArrayList<String>();
        String command = "df";
        for (String dir : dirList) {
            command += " " + dir;
        }
        String msg = getCmd(command);

        Matcher matcher = DISK_PARTTERN.matcher(msg);

        long useDisk = 0l;
        while (matcher.find()) {
            String sys = matcher.group(1);
            if (sys.contains(getMountNFS())){
            	useDisk = Long.parseLong(matcher.group(4));
            }
        }

        return (int) (useDisk/1024);
    }
    
    /**
     * 
     * @return 磁盘使用百分比
     */
    public static float getDiskPercent() {
    	return (100 - (float) (getAvailableDisk()*1.0 / getDiskCap() * 100));
    }
    
    /**
     * 
     * @return 当前磁盘读写速率
     */
    public static DiskSpeed getDiskSpeed() {
    	
    	boolean findDevice = false;
    	float maxReadK = 0;
    	float maxWriteK = 0;
    	
    	String msg = getCmd("iostat -k");

    	StringTokenizer tokenizer = new StringTokenizer(msg, "\n");
    	while (tokenizer.hasMoreTokens()) {
    		String line = tokenizer.nextToken();
    		
    		if (findDevice) {
    			String [] tags = line.split("\\s+");
    			float readK = Float.parseFloat(tags[2]);
    			float writeK = Float.parseFloat(tags[3]);
    			if (readK > maxReadK) {
    				maxReadK = readK;
    			}
    			if (writeK > maxWriteK) {
    				maxWriteK = writeK;
    			}
    		} else if (line.startsWith("Device:")) {
    			findDevice = true;
    		}
    	}
    	
    	return new DiskSpeed(maxReadK, maxWriteK);
    }
    
    /**
     * 
     * @return 主机描述
     */
    public static String getHostOS() {
    	final String DES_PREFIX = "Description";
    	final String DES_REG = ".*Description:*[\\s]+";
    	final int len = DES_PREFIX.length();
    	
    	String os = null;
    	try {
    		os = getCmd("lsb_release -d");
            if (os != null && os.length() > len) {
            	int index = os.indexOf(DES_PREFIX);
            	if ( index >= 0) {
            		os = os.replaceAll(DES_REG, "");
            	}
            } else {
            	os = "Unknown OS";
            }
    	} catch (Exception e) {
    		logger.warn(e.getMessage());
    		os = "Unknown OS";
    	}
    	
        
        return os;
	}
    
    /**
     * 
     * @return 总物理内存大小，（MB）
     */
    public static int getMaxMem() {
        String fileContent = getLocalFile("/proc/meminfo");
        Matcher matcher = TOTAL_MEM_PARTTERN.matcher(fileContent.toLowerCase());
        int maxMem = 0;
        if (matcher.find()) {
            String totalMem = matcher.group(1);
            String pri = matcher.group(2);
            Long total = Long.parseLong(totalMem);
            // 换算为MB
            if (pri.equalsIgnoreCase("k")) {
                total /= 1024;
            } else if (pri.equalsIgnoreCase(" ")) {
                total /= 1024 * 1024;
            } else if (pri.equalsIgnoreCase("g")) {
                total *= 1024;
            }
            maxMem = total.intValue();
        } else {
            logger.warn("max mem set FAILD");
        }
        return maxMem;
    }
    
    /**
     * 
     * @return 未使用的物理内存，（MB）
     */
    public static int getFreeMem() {
        int mem = 0;
        String fileContent = getLocalFile("/proc/meminfo");
        Matcher matcher = FREE_MEM_PARTTERN.matcher(fileContent.toLowerCase());
        while (matcher.find()) {
            String totalMem = matcher.group(5);
            String pri = matcher.group(6);
            Long total = Long.parseLong(totalMem);
            // 换算为MB
            if (pri.equalsIgnoreCase("k")) {
                total /= 1024;
            } else if (pri.equalsIgnoreCase(" ")) {
                total /= 1024 * 1024;
            } else if (pri.equalsIgnoreCase("g")) {
                total *= 1024;
            }
            mem += total.intValue();
        }
        return mem;
    }

    /**
     * 
     * @return 已使用的物理内存，（MB）
     */
    public static int getUseMem() {
        return getMaxMem() - getFreeMem();
    }
    
    /**
     * 
     * @return 内存使用百分比
     */
    public static float getMemPercent() {
    	return (float) (getUseMem()*1.0 / getMaxMem() * 100);
    }
    
    /**
     * 修改了个bug，直接读文件的话时间长了会报异常
     * /proc/stat (Too many open files)
     * java.io.FileNotFoundException: /proc/stat (Too many open files)
     * @param location
     * @return
     */
    public static String getLocalFile(String location) {
        return getCmd("cat " + location);
    }
    
    
    public static long getUsedCpuTime() {
        String msg = getLocalFile("/proc/stat");
        Matcher matcher = CPU_PARTTERN.matcher(msg);
        long time = 0;
        if (matcher.find()) {
            time += Long.parseLong(matcher.group(1));
            time += Long.parseLong(matcher.group(2));
            time += Long.parseLong(matcher.group(3));
        }
        return time * 10;   //换算为10^-3s
    }

    public static long getTotalCpuTime() {
        String msg = getLocalFile("/proc/stat");
        Matcher matcher = CPU_PARTTERN.matcher(msg);
        long time = 0;
        if (matcher.find()) {
            for (int i = 1; i <= 7; i++) {
                time += Long.parseLong(matcher.group(i));
            }
        }
        return time * 10;   //换算为10^-3s
    }
    
    public static float getCpuPercent() {
    	String msg = getCmd("sar 1 1");
    	
    	StringTokenizer tokenizer = new StringTokenizer(msg, "\n");

    	String line = null;
    	while (tokenizer.hasMoreTokens()) {
    		line = tokenizer.nextToken();
    	}
    	if (line != null) {
    		String [] tags = line.split("\\s+");
    		if (tags.length > 2) {
    			return Float.parseFloat(tags[2]);
    		}
    	}
    	
    	return 0;
    }
    
    /**
     * 调用系统命令top获取CPU使用率，需要500ms才能返回
     * @return CPU使用率
     */
    public static float getCpuPercent(int x) {

    	float idle = 100;
    	String msg = getCmd("top -b -d 1 -n 2");

    	StringTokenizer tokenizer = new StringTokenizer(msg, "\n");
    	while (tokenizer.hasMoreTokens()) {
    		String line = tokenizer.nextToken();
    		if (line.startsWith("%Cpu")) {
    			String [] tags = line.split(",\\s*");
    			for (String tag : tags) {
    				if (tag.endsWith("id")) {
    					float tmp = Float.parseFloat(tag.substring(0, tag.indexOf(' ')));
    					if (tmp < idle) {
    						idle = tmp;
    					}
    				}
    			}
    		}
    	}
    	
    	return 100 - idle;
    }
    
    /**
     * 最近一分钟的平均负载，load/cpuNum
     * @return 最近一分钟的load
     */
    public static float getAvgLoad() {
    	String msg = getCmd("cat /proc/loadavg");
    	return Float.parseFloat(msg.substring(0, msg.indexOf(' '))) / getCpuNum();
    }
    
    /**
     * 
     * @return CPU核心数
     */
    public static int getCpuNum() {
        String fileContent = getLocalFile("/proc/cpuinfo");
        int cpuNum = -1;
        int idx = -1;
        do {
            idx = fileContent.indexOf("processor", idx + 1);
            cpuNum++;
        } while (idx >= 0);
        return cpuNum;
    }
    
    /**
     * 
     * @return CPU 主频
     */
    public static int getCpuHz() {
        String fileContent = getLocalFile("/proc/cpuinfo");

        int num = 0;
        int total = 0;
        
        Matcher matcher = CPU_HZ_PARTTERN.matcher(fileContent.toLowerCase());
        while (matcher.find()) {
            num++;
            total += Integer.parseInt(matcher.group(1));
        }

        return total / num;
    }
    
    /**
     * 需要500ms才能返回
     * @return 网卡发送和接受速率，（k/s）
     */
    public static NetSpeed getNetTrafficK() {
    	
    	NetStat s0 = getNetStat();
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	NetStat s1 = getNetStat();

    	float inTrafficK = (s1.getInBytes() - s0.getInBytes())/1024f;
    	float outTrafficK = (s1.getOutBytes() - s0.getOutBytes())/1024f;
    	return new NetSpeed(inTrafficK, outTrafficK);
    }
    
    /**
     * 
     * @return 当前网卡以发送和接收的总字节数
     */
    public static NetStat getNetStat() {
    	String msg = getCmd("cat /proc/net/dev");
    	String [] lines = msg.split("\n");
    	long inBytes = 0, outBytes = 0;
    	for (int i = 2; i < lines.length; i++) {
    		String [] traffics = lines[i].split("\\s+");
    		if (traffics.length > 16 && (traffics[1].startsWith("lo"))) {
    			long ti = Long.parseLong(traffics[2], 10);
    			long to = Long.parseLong(traffics[10], 10);
    			if (ti > inBytes) {
    				inBytes = ti;
    			}
    			if (to > outBytes) {
    				outBytes = to;
    			}
    		}
    	}
    	
    	return new NetStat(inBytes, outBytes);
    }
    
    public static String getFirstIp() {
    	String msg = getCmd("ifconfig");
    	String [] lines = msg.split("\n");
    	if (lines.length > 2) {
    		Matcher matcher = IP_PATTERN.matcher(lines[1]);
    		if (matcher.find()) {
    			return matcher.group(0);
    		}
    	}
    	
    	return null;
    }
    
    public static String getUuid() {
    	String msg = getCmd("ifconfig");
    	String [] lines = msg.split("\n");
    	if (lines.length > 2) {
    		String [] token = lines[0].split("(\\s+)");
    		if (token.length > 3) {
    			String mac = token[token.length - 1];
    			return mac.replaceAll(":", "");
    		}
    	}
    	
    	return null;
    }
    
    /**
     * 执行SHELL命令
     * @param command
     * @return 命令的输出
     */
    public static String getCmd(String command) {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        try {
            Process proc = Runtime.getRuntime().exec(command);
            is = proc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            // destroy 此进程 可以回收 相关资源 特别是 文件句柄
            proc.destroy();
        } catch (Exception ex) {
            logger.warn("An Exception occur when exec " + command, ex);
        }

        try {
            br.close();
            is.close();
        } catch (Exception ex) {
        }
        
        return sb.toString();
    }
    
    private static String getMountNFS() {
		return getFirstIp()+NFS_PATH;
	}
	
    public static void main(String [] args) {
    	System.out.println(getDiskCap());
    	System.out.println(getAvailableDisk());
    	System.out.println(getDiskPercent());
    }
}
