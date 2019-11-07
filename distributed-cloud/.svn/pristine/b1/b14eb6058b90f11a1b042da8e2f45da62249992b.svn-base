package appcloud.netty.remoting;

import appcloud.netty.remoting.remote.NettyRequestProcessor;

import java.util.concurrent.ExecutorService;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public interface RemoteServer extends RemoteService {

    public void registerProcessor(int code, NettyRequestProcessor processor, ExecutorService executor);

}
