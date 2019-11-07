/**
 * @(#)ServerTest.java, 2011-4-14. 
 * 
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package test.appcloud.nodeagent;

import org.junit.Test;
import appcloud.nodeagent.info.NodeAgent;

/**
 *
 * @author wangchao
 * @version 2011-4-14
 */
public class NodeAgentTest {
    @Test
    public void test(){
        NodeAgent na = NodeAgent.getInstance();
        System.out.println(na.getCpuNum());
        System.out.println(na.getMaxMem());
        System.out.println(na.getFreeMem());
        System.out.println(na.getDiskCap());
        System.out.println(na.getUseDisk());
        System.out.println(na.getUseCpuTime());
        System.out.println(na.getTotalCpuTime());
        System.out.println(na.getUseCpuLoad());
        System.out.println(na.getCpuHz());
    }
    
    @Test
    public void testOsInfo() {
    	NodeAgent na = NodeAgent.getInstance();
    	System.out.println(na.getOs());
    }
}
