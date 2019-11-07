package appcloud.distributed.process;

import appcloud.distributed.CloudController;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.header.ChkConnectionHeader;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.common.SerializeType;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.remote.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;
/**
 * Created by zouji on 2017/12/12.
 */
public class ChkConnectionProcess implements NettyRequestProcessor{

    private CloudController cloudController;

    public ChkConnectionProcess(CloudController cloudController) {
        this.cloudController = cloudController;
    }

    public RemoteCommand processRequest(ChannelHandlerContext channelHandlerContext, RemoteCommand remoteCommand) throws Exception {
        RemoteCommand response;
        int requestCode = remoteCommand.getCode();
        switch (requestCode) {
            case RequestCode.CHECK_CONNECTION:
                RouteInfoManager routeInfoManager = cloudController.routeInfoManager;
                ChkConnectionHeader chkConnectionHeader = new ChkConnectionHeader(routeInfoManager.getMasterRouteInfo());
                response = RemoteCommand.createResponseRemoteCommand(RequestCode.CHECK_CONNECTION, ResponseCode.SUCCESSED, SerializeType.JSON, chkConnectionHeader);
                break;

            default:
                response = null;
        }
        return response;
    }

    public boolean rejectRequest() {
        return false;
    }
}
