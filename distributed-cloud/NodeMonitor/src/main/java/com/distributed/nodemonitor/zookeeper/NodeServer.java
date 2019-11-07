package com.distributed.nodemonitor.zookeeper;


import com.distributed.common.utils.ZkServer;
import com.distributed.nodemonitor.constant.Constants;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Idan on 2018/5/7.
 *
 * 一个平台内部有很多台物理机
 * 每台物理机定期向该平台的zookeeper集群中注册消息
 *
 */
public class NodeServer {

    private ZkServer zkServer;
    private ScheduledExecutorService zkRegisterExecutor;
    private String path;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(watchedEvent);
        }
    };

    public NodeServer() {
        try {
            zkServer = ZkServer.createZkServer(Constants.ZK_CLUTER_ADDR, this.watcher);
            if (zkServer.noExist(Constants.CLUSTER_NODE)) {
                zkServer.createZNode(Constants.CLUSTER_NODE, CreateMode.PERSISTENT);
            }
            this.path = Constants.CLUSTER_NODE+"/"+Constants.APP_NAME;
            zkServer.createZNode(path, CreateMode.EPHEMERAL);
            this.zkRegisterExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "the node registionThread of this cluster working……");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        zkRegisterExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //TODO 需要获取物理机的负载信息
                try {
                    int loadBalance = 0;
                    zkServer.setData(path, String.valueOf(loadBalance));
                    System.out.println("服务器上传负载："+loadBalance);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 2*60, TimeUnit.SECONDS);
    }

}
