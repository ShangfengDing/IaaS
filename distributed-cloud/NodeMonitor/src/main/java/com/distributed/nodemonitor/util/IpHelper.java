package com.distributed.nodemonitor.util;

import java.net.InetAddress;

/**
 * Created by Idan on 2018/6/8.
 */
public class IpHelper {

    public static String getHostIp() throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return ip;
    }

}
