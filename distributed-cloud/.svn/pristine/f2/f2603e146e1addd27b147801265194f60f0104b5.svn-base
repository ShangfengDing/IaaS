package com.distributed.common.rpc;

import appcloud.netty.remoting.RemoteServer;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.remote.NettyRemotingServer;
import com.distributed.common.constant.Constants;
import com.distributed.common.utils.ConfigReader;
import com.distributed.common.utils.ProxyUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by zouji on 2018/1/15.
 */
public class RPCServiceServer {

    private static final Logger logger = LoggerFactory.getLogger(RPCServiceServer.class);

    protected RemoteServer remoteServer;
    private RPCProcess rpcProcess;
    private ExecutorService executorService;
    private Map<Class<?>, Object> instanceMap = new HashMap();


    public RPCServiceServer() {
        this(new NettyRemotingServer(Constants.RPC_PORT),0,null);
    }

    public RPCServiceServer(RemoteServer remotingServer,int coreThread,Class<?>[] clazzs) {
        if (clazzs != null) {
            initWorkerInstance(clazzs);
        }
        initService();
        this.remoteServer = remotingServer;
        this.rpcProcess = new RPCProcess(instanceMap);
        this.executorService = Executors.newFixedThreadPool(coreThread == 0?Constants.REQUEST_WORKER_THREAD_NUMBER:coreThread, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r, "rpc");
            }
        });
        remotingServer.registerProcessor(RequestCode.RPC_REQUEST, rpcProcess, executorService);
    }

    /**
     *这个可要可不要
     * @param clazzs
     */
    public void initWorkerInstance(Class<?>[] clazzs) {
        for (Class clazz : clazzs) {
            try {
                String impClass = Constants.serviceHashMap.get(clazz);
                Class<?> impInstance = Class.forName(impClass);
                Object instance = impInstance.newInstance();
                logger.info("create new instance: " + clazz);
                instanceMap.put(clazz, instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("IllegalAccessException:" + clazz);
            } catch (InstantiationException e) {
                e.printStackTrace();
                logger.error("InstantiationException:" + clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("ClassNotFoundException:" + clazz);
            }
        }
    }

    private void initService() {
        List<ProxyUnit> services;
        TypeReference<List<ProxyUnit>> listType =  new TypeReference<List<ProxyUnit>>() {};
        try {
            services = ConfigReader.readFromJson(RPCServiceServer.class.getClassLoader().getResource("services.json"), listType);
            for (ProxyUnit service : services) {
                logger.info("proxy:"+service.getProxyClass()+", class:"+service.getImplClass());
                instanceMap.put(Class.forName(service.getProxyClass()), Class.forName(service.getImplClass()).newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addService(Class<?> proxyClass, Object implInstance) {
        if (proxyClass != null && implInstance != null) {
            instanceMap.put(proxyClass, implInstance);
        }
    }


    public void start() {
        remoteServer.start();
    }

    public void shutdown() {
        remoteServer.shutdown();
    }

    public static void main(String[] args) {
        RPCServiceServer rpcServiceServer = new RPCServiceServer();
        rpcServiceServer.start();
    }
}
