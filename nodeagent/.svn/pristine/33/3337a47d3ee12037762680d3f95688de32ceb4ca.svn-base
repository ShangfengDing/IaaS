/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package appcloud.nodeagent.info;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import appcloud.common.constant.HostCapacities;
import appcloud.common.model.Host;
import appcloud.common.model.Host.HostTypeEnum;
/**
 *
 * @author yicou
 * @author jianglei
 */
public class NodeAgent {
	private static NodeAgent agent;    
	
	private String uuid = "";
	private String nfsLocation;
	
	private int hitInterval;
    private int hostInfoInterval;
    private int gcInfoInterval;
    
    private long lastUseCpuTime = 0;
    private long lastTotalCpuTime = 0;
    private long lastCpuTime = 0;
    
    private int hostId = -1;
    
    private String capability;
    
    private int diskCap;
    private int cpuNum;
    private int cpuHz;
    private int maxMem;
    
    private String url;
    private String location;
    private String os;
    
    private static Logger logger = Logger.getLogger(NodeAgent.class);
    
    public static NodeAgent getInstance() {
        if (agent == null) {
            agent = new NodeAgent();
        }
        return agent;
    }
    
    private NodeAgent() {
        InputStream is =
                NodeAgent.class.getClassLoader().getResourceAsStream("na.properties");
        java.util.Properties p = new java.util.Properties();
        try {
            p.load(is);
            
            url = p.getProperty("url");
            String typeString = p.getProperty("type");
            if(typeString.trim().equalsIgnoreCase("COMPUTE_NODE")) {
            	type = HostTypeEnum.COMPUTE_NODE;
            } else if (typeString.trim().equalsIgnoreCase("FUNCTION_NODE")){
            	type = HostTypeEnum.FUNCTION_NODE;
            } else {
            	logger.error("type not supportted");
            	System.exit(1);
            }
            

            capability = p.getProperty("capability");
            if (capability == null) {
            	logger.error("capability must be filled");
            	System.exit(1);
            }

            vmEpStr = p.getProperty("vm");
            j2eeEpStr = p.getProperty("j2ee");

            nfsLocation = p.getProperty("nfs");
            
            location = p.getProperty("location");
            location = new String(location.getBytes("ISO8859-1"), "UTF-8");

            hitInterval = Integer.parseInt(p.getProperty("hitInterval"));
            hostInfoInterval = Integer.parseInt(p.getProperty("hostInfoInterval"));
            gcInfoInterval = Integer.parseInt(p.getProperty("gcInfoInterval"));
            
            // TODO os info setter
            //os = p.getProperty("os");
            os = getHostOS();
            
            uuid = p.getProperty("uuid");
            if (uuid == null) {
            	logger.error("uuid  must be filled");
            	System.exit(1);
            	//uuid = UUID.randomUUID().toString().replace("-", "");
            }     

            for (String pro : p.stringPropertyNames()) {
                if (pro.contains("Dir")) {
                    dirList.add(p.getProperty(pro));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setCpuInfo();
        setMemInfo();
        setDiskCap();
        hit();
    }

    
    private String getHostOS() {
    	final String DES_PREFIX = "Description";
    	final String DES_REG = ".*Description:*[\\s]+";
    	final int len = DES_PREFIX.length();
    	
    	String os = getCmd("lsb_release -d");
        if (os != null && os.length() > len) {
        	int index = os.indexOf(DES_PREFIX);
        	if ( index >= 0) {
        		os = os.replaceAll(DES_REG, "");
        	}
        } else {
        	os = "Unknown OS";
        }
        
        return os;
	}

	public String getNfsLocation() {
        return nfsLocation;
    }
    

    public int getHitInterval() {
        return hitInterval;
    }
    public int getHostInfoInterval() {
        return hostInfoInterval;
    }
    public int getGcInfoInterval() {
        return gcInfoInterval;
    }

    
    public int getHostId() {
        return hostId;
    }
    public void setHostId(int id) {
        hostId = id;
    }
    
    public String getCapability() {
    	return capability;
    }

    public boolean containsVm() {
        return capability.toUpperCase().contains(HostCapacities.VM);
    }
    public boolean containsJ2ee() {
    	return capability.toUpperCase().contains(HostCapacities.J2EE);
    }

    private List<String> dirList = new ArrayList<String>();

    public static final Pattern DISK_PARTTERN = Pattern
            .compile("\\n([\\s\\S]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)[\\s]+?([0-9]+?)%[\\s]+?([\\s\\S]+?)");

    private void setDiskCap() {
        String command = "df";
        for (String dir : dirList) {
            command += " " + dir;
        }
        String msg = getCmd(command);

        Matcher matcher = DISK_PARTTERN.matcher(msg);

        Map<String, Long> tMap = new HashMap<String, Long>();
        tMap.clear();
        while (matcher.find()) {
            String sys = matcher.group(1);
            long cap = Long.parseLong(matcher.group(2));
            tMap.put(sys, cap);
        }

        diskCap = 0;
        for (String sys : tMap.keySet()) {
            diskCap += tMap.get(sys) / 1024;
        }
    }

    public int getUseDisk() {
        String command = "df";
        for (String dir : dirList) {
            command += " " + dir;
        }
        String msg = getCmd(command);

        Matcher matcher = DISK_PARTTERN.matcher(msg);

        Map<String, Long> tMap = new HashMap<String, Long>();
        tMap.clear();
        while (matcher.find()) {
            String sys = matcher.group(1);
            long cap = Long.parseLong(matcher.group(3));
            tMap.put(sys, cap);
        }

        int useDisk = 0;
        for (String sys : tMap.keySet()) {
            useDisk += tMap.get(sys) / 1024;
        }
        return useDisk;
    }

    public int getFreeDisk() {
        return getDiskCap() - getUseDisk();
    }
    
    public int getDiskCap() {
        return diskCap;
    }

    public static final Pattern CPU_HZ_PARTTERN = Pattern
            .compile("cpu[\\s]*?MHz[\\s]*?:[\\s]*?([0-9]+)".toLowerCase());

    private void setCpuInfo() {
        String fileContent = getLocalFile("/proc/cpuinfo");
        cpuNum = -1;
        int idx = -1;
        do {
            idx = fileContent.indexOf("processor", idx + 1);
            cpuNum++;
        } while (idx >= 0);

        int num = 0;
        int total = 0;
        Matcher matcher = CPU_HZ_PARTTERN.matcher(fileContent.toLowerCase());
        while (matcher.find()) {
            num++;
            total += Integer.parseInt(matcher.group(1));
        }

        cpuHz = total / num;
    }

    public static final Pattern TOTAL_MEM_PARTTERN = Pattern
            .compile("MemTotal:[\\s]*?([0-9]*) ([a-z ])B".toLowerCase());

    private void setMemInfo() {
        String fileContent = getLocalFile("/proc/meminfo");
        Matcher matcher = TOTAL_MEM_PARTTERN.matcher(fileContent.toLowerCase());
        maxMem = 0;
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
    }

    
    public int getMaxMem() {
        return maxMem;
    }

    public static final Pattern FREE_MEM_PARTTERN = Pattern
            .compile("\\n((MemFree)|(Buffers)|(Cached)?):[\\s]*?([0-9]*) ([a-z ])B".toLowerCase());
    public int getFreeMem() {
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

    public int getUseMem() {
        return getMaxMem() - getFreeMem();
    }
    
    public int getCpuNum() {
        return cpuNum;
    }
    public int getCpuHz() {
        return cpuHz;
    }
    
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public String getLocation() {
		return location;
	}

	public String getOs() {
		return os;
	}

	private HostTypeEnum type;
    public HostTypeEnum getType() {
        return type;
    }

    private String vmEpStr;
    public String getVmEpStr() {
        return vmEpStr;
    }

    private String j2eeEpStr;
    public String getJ2eeEpStr() {
        return j2eeEpStr;
    }

    private String getCmd(String command) {
        String msg = "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            Process proc = Runtime.getRuntime().exec(command);
            is = proc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            msg += br.readLine();
            while (br.ready()) {
                msg += "\n" + br.readLine();
            }
            // destroy 此进程 可以回收 相关资源 特别是 文件句柄
            proc.destroy();
        } catch (Exception ex) {
            msg = "";
        }

        try {
            br.close();
            is.close();
        } catch (Exception ex) {
        }
        
        return msg;
    }

    /**
     * 修改了个bug，直接读文件的话时间长了会报异常
     * /proc/stat (Too many open files)
     * java.io.FileNotFoundException: /proc/stat (Too many open files)
     * @param location
     * @return
     */
    private String getLocalFile(String location) {
        return getCmd("cat " + location);
    }

    public static final Pattern CPU_PARTTERN = Pattern
            .compile("cpu[\\s]+?([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?) ([0-9]+?)");

    public long getUseCpuTime() {
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

    public long getTotalCpuTime() {
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

    public long getCpuTime() {
        return System.currentTimeMillis();
    }

    

    public void hit() {
        lastUseCpuTime = getUseCpuTime();
        lastTotalCpuTime = getTotalCpuTime();
        lastCpuTime = getCpuTime();
    }
    
    public int getHitCpuTime() {
        long thisCpuTime = getCpuTime();
        return (int)(thisCpuTime - lastCpuTime);
    }

    public int getHitUseCpuTime() {
        long thisUseCpuTime = getUseCpuTime();
        return (int)(thisUseCpuTime - lastUseCpuTime);
    }
    
    public float getUseCpuLoad() {
        long thisUseCpuTime = getUseCpuTime();
        long thisTotalCpuTime = getTotalCpuTime();
        double load = thisUseCpuTime - lastUseCpuTime;
        load *= 100;
        load /= thisTotalCpuTime - lastTotalCpuTime;
        return (float) load;
    }
    
    public Host getHostInfoWithUuid() {
    	Host host = new Host();
    	host.setUuid(this.getUuid());
    	host.setType(this.getType());
    	
    	return host;
    }
}
