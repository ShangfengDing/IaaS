package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.protocol.RemoteCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public interface NettyRequestProcessor {

    RemoteCommand processRequest(ChannelHandlerContext ctx,RemoteCommand request) throws Exception;

    boolean rejectRequest();
}
