package com.distributed.common.rpc;

import appcloud.netty.remoting.common.RequestCode;

/**
 * Created by zouji on 2018/1/9.
 */
public class RPCClient<T> {
    private String host;
    private Integer port;
    private Integer requestCode;

    public RPCClient(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.requestCode = RequestCode.RPC_REQUEST;
    }

    public RPCClient(String host, Integer port, Integer requestCode) {
        this.host = host;
        this.port = port;
        this.requestCode = requestCode;
    }

    public T createProxy(Class<?> classz) {
        return (T) ClientWrapper.getInstance(host, port, requestCode).createProxy(classz);
    }
}
