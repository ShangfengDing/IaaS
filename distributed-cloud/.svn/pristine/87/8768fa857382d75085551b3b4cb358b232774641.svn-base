package com.distributed.nodemonitor;

import com.distributed.nodemonitor.zookeeper.NodeServer;

/**
 * 平台内每个节点定期向zkServer发送负载信息
 * nodemonitor 能够检测每台物理服务器的状态
 */
public class NodeMonitorApplication {

	public static void main(String[] args) {
		NodeServer nodeServer = new NodeServer();
		nodeServer.start();
	}
}
