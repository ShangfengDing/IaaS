package appcloud.distributed.zookeeper;

import appcloud.distributed.common.Constants;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.process.thread.RecoveryThread;
import com.distributed.common.constant.EnumConstants;
import com.distributed.common.entity.VmBack;
import com.distributed.common.entity.VmHost;
import com.distributed.common.factory.DbFactory;
import com.distributed.common.service.db.VmBackupService;
import com.distributed.common.service.db.VmHostService;
import com.distributed.common.utils.CollectionUtil;
import com.distributed.common.utils.ZkServer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Idan on 2018/5/7.
 */
public class NodeMonitor implements Watcher {

    private final static Logger logger = LoggerFactory.getLogger(NodeMonitor.class);
    private volatile Map<String, ServerInfo> serverList = new TreeMap<>();
    private ZkServer zkServer;
    private VmBackupService vmBackupService = DbFactory.getVmBackUpService();
    private VmHostService vmHostService = DbFactory.getVmHostService();
    private RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NodeMonitor() {
        try {
            zkServer = ZkServer.createZkServer(Constants.ZK_CLUTER_ADDR, this);
            if (zkServer.noExist(Constants.CLUSTER_NODE)) {
                zkServer.createZNode(Constants.CLUSTER_NODE, CreateMode.PERSISTENT);
            }
            updateServerList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("\n监听到zookeeper事件-------eventType:" + watchedEvent.getType() + ", path:" + watchedEvent.getPath());
        // 新增结点，发生变化
        if (watchedEvent.getType().equals(Event.EventType.NodeChildrenChanged) &&
                watchedEvent.getPath().equals(Constants.CLUSTER_NODE)) {
            try {
                logger.info("NodeChildrenChanged: "+ watchedEvent.getPath());
                updateServerList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 结点被删除，也即是服务器断开连接
        if (watchedEvent.getType().equals(Event.EventType.NodeDeleted) &&
                watchedEvent.getPath().startsWith(Constants.CLUSTER_NODE)) {
            try {
                //TODO 2018-6-10 需要一定的等待重试机制
                String sourceIp = watchedEvent.getPath().substring(9);
                logger.info("NodeDeleted: "+sourceIp);
                List<VmHost> vmHosts = vmHostService.findByParams(null, null, sourceIp, null, null);
                if (CollectionUtil.isEmpty(vmHosts)) {
                    logger.warn("该物理机不存在");
                } else {
                    int size = vmHosts.size();
                    VmHost vmHost = vmHosts.get(size-1);
                    String localDataCenter = routeInfoManager.getOwnDataCenter();
                    List<VmBack> vmBackList = vmBackupService.findByParams(null, vmHost.getUuid(), null, localDataCenter, localDataCenter,
                            EnumConstants.SurvivalStatus.ALIVE.toString(), true);
                    if (!CollectionUtil.isEmpty(vmBackList)) {
                        new RecoveryThread(vmBackList.get(0), Constants.REGION_ID, localDataCenter).run();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 结点的数据发生改变
        if (watchedEvent.getType().equals(Event.EventType.NodeDataChanged) &&
                watchedEvent.getPath().startsWith(Constants.CLUSTER_NODE)) {
            logger.info("NodeDataChanged: "+ watchedEvent.getPath());
            updateServerLoadBalance(watchedEvent.getPath());
        }

    }

    public void updateServerList() {
        Map<String, ServerInfo> newServerList = new TreeMap<>();
        try {
            List<String> childrenList = zkServer.getChildren(Constants.CLUSTER_NODE);
            for (String subNode : childrenList) {
                ServerInfo serverInfo = new ServerInfo();
                String childrenPath = Constants.CLUSTER_NODE + "/" + subNode;
                serverInfo.setPath(childrenPath);
                serverInfo.setName(subNode);
                String data = zkServer.getData(childrenPath);
                serverInfo.setLoadBalance(data);
                logger.info(serverInfo.toString());
                newServerList.put(childrenPath, serverInfo);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        this.serverList = newServerList;

        logger.info("更新了注册表：" + serverList);
    }

    private void updateServerLoadBalance(String serverNodePath) {
        ServerInfo serverInfo = serverList.get(serverNodePath);
        if (null != serverInfo) {
            try {
                String data = zkServer.getData(serverInfo.getPath());
                serverInfo.setLoadBalance(data);
                serverList.put(serverNodePath, serverInfo);
                System.out.println("@@@更新了服务器的负载：" + serverInfo);
                System.out.println("###更新服务器负载后，服务器列表信息：" + serverList + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handle() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
