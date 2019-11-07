/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */
package appcloud.common.util;

import java.io.*;
import java.util.UUID;

import java.util.List;

import appcloud.common.errorcode.RSEC;

/**
 * 平台的Control消息工具
 * @author yicou
 */
public class ControlUtils {

    private static final String APP_ID = "APP_ID";
    private static final String ACTION = "ACTION";
    private static final String TEMP_ID = "TEMP_ID";
    private static final String CPU = "CPU";
    private static final String MEM = "MEM";
    private static final String WAITING_TIME = "WAITING_TIME";
    private static final String NUM = "NUM";
    private static final String XMS = "XMS";
    private static final String XMX = "XMX";
    private static final String CONFIG = "CONFIG";
    private static final String MAC = "MAC";
    private static final String IP = "IP";
    private static final String LOCATION = "LOCATION";
    private static final String UUID = "UUID";
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_SUSPEND = "suspend";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_BACKUP = "backup";
    public static final String ACTION_UPTEMP = "uptemp";
    public static final String REQ_TYPE_STARTAPP = "startapp";
    public static final String REQ_TYPE_STOPAPP = "stopapp";
    public static final String REQ_TYPE_DEALAPP = "dealapp";
    public static final String DST_NUM = "DSTNUM";
    public static final String IP_CONF = "IP";
    public static final String PORT_CONF = "PORT";
    public static final String WEIGHT_CONF = "WEIGHT";
    public static final String TYPE = "TYPE";
    public static final String URL_SRC = "URLSRC";
    public static final String URL_CONF = "URLCONF";
    public static final String DUPLICATION = "DUPLICATION";

    public static final Integer VM_TIMOUT = 1000 * 60 * 60 * 24;//超时时间为一天

    /**
     * 使虚拟机开机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC bootVM(String epStr,
            String uuid)
            throws Exception {
    	return dealVM(epStr, uuid, ACTION_START, -1, -1, false);
    }


    /**
     * @deprecated
     * 使虚拟机开机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @param cpu 调整虚拟cpu数量
     * @param mem 调整虚拟内存数量
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC bootVM(String epStr,
            String uuid,
            int cpu,
            int mem)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_START, cpu, mem, true);
    }

    /**
     * 使虚拟机关机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC shutdownVM(String epStr,
            String uuid)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_STOP, -1, -1, false);
    }

    /**
     * 使虚拟机挂起（对于未付费用户操作）
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC suspendVM(String epStr,
            String uuid)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_SUSPEND, -1, -1, false);
    }

    /**
     * 使被挂起的虚拟机，重新运行
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC resumeVM(String epStr,
            String uuid)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_RESUME, -1, -1, false);
    }

    /**
     * 虚拟机备份
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC backupVM(String epStr,
            String uuid)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_BACKUP, -1);
    }


    /**
     * 虚拟机模板上传
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @param tempId 新模板id
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC uploadTemplateVM(String epStr,
            String uuid,
            Integer tempId)
            throws Exception {
        return dealVM(epStr, uuid, ACTION_UPTEMP, tempId);
    }

    /**
     * @deprecated
     */
    private static RSEC dealVM(String epStr,
            String uuid,
            String action,
            int cpu,
            int mem,
            boolean optionFlag)
            throws Exception {
    	return dealVM(epStr, uuid, action, -1);
    }
    
    private static RSEC dealVM(String epStr,
            String uuid,
            String action,
            Integer templateId)
            throws Exception {
    	return ConnectionFactory.getResourceScheduler().dealVM(uuid, action, templateId);
    }

    /**
     * 创建虚拟机应用
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid 虚拟机安装 uuid
     * @param templateId 虚拟机安装 templateId
     * @param mac 虚拟机安装 mac地址
     * @param ip 虚拟机安装 ip地址
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC createVM(String epStr,
            String uuid,
            Integer templateId,
            String mac,
            String ip)
            throws Exception {
    	return ConnectionFactory.getResourceScheduler().createVM(uuid, templateId, mac, ip);
    }

    /**
     * 销毁虚拟机
     * @param epStr AppMaster的tip地址 如tcp://59.64.179.210:9001
     * @param uuid uuid
     * @return App Master传回的响应
     * @throws Exception
     */
    public static RSEC deleteVM(String epStr,
            String uuid)
            throws Exception {
    	return ConnectionFactory.getResourceScheduler().deleteVM(uuid);
    }
    
    /**
     * xml获取函数
     * @param name
     * @return
     * @throws Exception
     */
    public static String getXmlContent(String name) throws Exception {
        InputStream inputStream = ControlUtils.class.getResourceAsStream(name);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputStream));

        String xmlDef = "";
        while (bufferedreader.ready()) {
            xmlDef += bufferedreader.readLine() + "\n";
        }

        return xmlDef;
    }

}
