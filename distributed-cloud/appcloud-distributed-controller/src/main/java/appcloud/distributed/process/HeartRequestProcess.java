package appcloud.distributed.process;

import appcloud.distributed.CloudController;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.remote.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizhenhao on 2017/11/25.
 */
public class HeartRequestProcess implements NettyRequestProcessor {

    protected final static Logger LOGGER = LoggerFactory.getLogger(HeartRequestProcess.class);

    private CloudController cloudController;

    public HeartRequestProcess(CloudController cloudController) {
        this.cloudController = cloudController;
    }

    public RemoteCommand processRequest(ChannelHandlerContext channelHandlerContext, RemoteCommand remoteCommand) throws Exception {
        RemoteCommand response;
        int requestCode = remoteCommand.getCode();
        switch (requestCode){
            case RequestCode.HEART_BEAT:
                response = RemoteCommand.createReponseRemoteCommand(RequestCode.HEART_BEAT, ResponseCode.SUCCESSED);
                LOGGER.info("心跳检测请求接收，返回");
                break;
            default:
                response = null;
                break;
        }
        return response;
    }

    public boolean rejectRequest() {
        return false;
    }
}
