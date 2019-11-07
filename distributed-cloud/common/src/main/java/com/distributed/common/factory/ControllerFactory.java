package com.distributed.common.factory;

import com.distributed.common.constant.Constants;
import com.distributed.common.rpc.RPCClient;
import com.distributed.common.service.controller.AccountClient;
import com.distributed.common.service.controller.BackUpVmClient;
import com.distributed.common.service.controller.RouteInfoService;

/**
 * Created by zouji on 2018/1/15.
 * 与controller连接的rpc客户端工厂类
 */
public class ControllerFactory {

    public static RPCClient rpcClient = new RPCClient(Constants.RPC_HOST, Constants.RPC_PORT);
    private static volatile RouteInfoService routeInfoService;
    private static volatile AccountClient accountClient;
    private static volatile BackUpVmClient backUpVmClient;

    /**
     * 关于路由的接口
     * @return
     */
    public static RouteInfoService getRouteInfoService() {
        if (routeInfoService == null) {
            synchronized (ControllerFactory.class) {
                routeInfoService = (RouteInfoService) rpcClient.createProxy(RouteInfoService.class);
            }
        }
        return routeInfoService;
    }


    /**
     * 关于用户操作的接口
     * @return
     */
    public static AccountClient getAccountClient() {
        if (accountClient == null) {
            synchronized (ControllerFactory.class) {
                accountClient = (AccountClient) rpcClient.createProxy(AccountClient.class);
            }
        }
        return accountClient;
    }

    /**
     * 关于传输相关的接口
     * @return
     */
    public static BackUpVmClient getBackUpVmClient() {
        if (backUpVmClient == null) {
            synchronized (ControllerFactory.class) {
                backUpVmClient = (BackUpVmClient) rpcClient.createProxy(BackUpVmClient.class);
            }
        }
        return backUpVmClient;
    }

}
