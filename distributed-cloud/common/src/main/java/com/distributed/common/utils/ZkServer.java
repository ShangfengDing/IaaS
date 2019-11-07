package com.distributed.common.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Idan on 2018/5/7.
 *
 */
public class ZkServer {

    private static final int SESSION_TIMEOUT=5*1000;
    private ZooKeeper zooKeeper;

    public ZkServer(String zkClusterAddr, Watcher watcher) throws IOException {
        this.zooKeeper = new ZooKeeper(zkClusterAddr, SESSION_TIMEOUT, watcher );
    }

    public static ZkServer createZkServer(String zkCluster, Watcher watcher) throws IOException {
        ZkServer zkServer = new ZkServer(zkCluster, watcher);
        return zkServer;
    }

    public void createZNode(String path, CreateMode mode) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        if (null == stat) {
            String result = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
            System.out.println("创建了结点："+result);
        }
    }

    public boolean noExist(String path) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path, false) == null;
    }

    public void setData(String path, String data) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        Stat stat = zooKeeper.exists(path, false);
        if (null == stat) {
            zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zooKeeper.setData(path, data.getBytes("utf-8"), -1);
        System.out.println("修改了数据："+data);
    }

    public String getData(String path) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        Stat stat = new Stat();
        byte[] databytes = zooKeeper.getData(path, true, stat);
        if (databytes == null) return "0";
        return new String(databytes, "utf-8");
    }

    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, true);
    }

    public void close() throws InterruptedException {
        if (null != zooKeeper) {
            zooKeeper.close();
        }
    }

}
