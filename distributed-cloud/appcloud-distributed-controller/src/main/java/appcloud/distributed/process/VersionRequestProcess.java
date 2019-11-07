package appcloud.distributed.process;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.configmanager.VersionInfo;
import appcloud.distributed.configmanager.VersionInfoManager;
import appcloud.distributed.header.*;
import appcloud.distributed.util.VersionUtil;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.common.SerializeType;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.remote.NettyRequestProcessor;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idan on 2018/1/16.
 * 版本控制的处理
 * 1. 版本分发处理
 * 2. 版本同步检查的处理
 */
public class VersionRequestProcess implements NettyRequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(VersionRequestProcess.class);

    private final VersionInfoManager versionInfoManager = VersionInfoManager.getInstance();
    private final RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();
    private final CloudControllerClientWapper clientWapper = CloudControllerClientWapper.getInstance();

    @Override
    public RemoteCommand processRequest(ChannelHandlerContext ctx, RemoteCommand request) throws Exception {
        RemoteCommand response = RemoteCommand.createReponseRemoteCommand(RequestCode.SUCCESS, ResponseCode.SUCCESSED);
        int requestCode = request.getCode();
        switch (requestCode) {
            // 版本分发
            case RequestCode.VERSION_DISPATCH:
                VersionInfoHeader header = (VersionInfoHeader) request.decodeConsumerHeader(VersionInfoHeader.class);
                VersionInfo versionInfo = header.getInfo();
                String requestId = header.getRequestId();
                logger.info("requestId: "+requestId+",版本是："+versionInfo.toString());
                Integer diffNum=versionInfoManager.compare(versionInfo);
                // 相同则不做处理
                if (diffNum == 0) {
                    logger.warn("版本没有差错,应该是同一个台主机上的原因");
                    return response;
                } else if (diffNum == 1) {
                    logger.info("版本相差一个，同步新版本");
                    versionInfoManager.synInfo(versionInfo);
                } else {
                    VersionInfo selfVersion = versionInfoManager.getLatest();
                    logger.info("自检的最新版本是："+selfVersion);
                    String addr = routeInfoManager.getMasterAddr();
                    if (selfVersion == null) {
                        clientWapper.sendVersionRequest(requestId, null, addr);
                    } else {
                        Long versionNum = selfVersion.getVersionNum();
                        clientWapper.sendVersionRequest(requestId, versionNum, addr);
                    }

                }
                break;
            // 版本同步，与master比较，判断是否相同
            case RequestCode.VERSION_SYN:
                VersionHeader versionHeader = (VersionHeader) request.decodeConsumerHeader(VersionHeader.class);
                Long requestVersion = versionHeader.getVersion() == null? 0:versionHeader.getVersion();  // 源数据中心发送的版本号
                String synRequestId = versionHeader.getRequestId();
                logger.info("requestId:"+synRequestId+",发送过来的版本是："+requestVersion);
                VersionInfo selfVersion = versionInfoManager.getLatest();
                VersionInfoListHeader infoListHeader = new VersionInfoListHeader(null);
                response = RemoteCommand.createResponseRemoteCommand(RequestCode.VERSION_SYN, ResponseCode.SUCCESSED, SerializeType.JSON, infoListHeader);
                if (selfVersion != null) {
                    Boolean rs = VersionUtil.isEqual(requestVersion, selfVersion.getVersionNum());
                    // 测试，手动添加一个list
//                    Boolean rs = false;
                    if (!rs) {
                        List<VersionInfo> result = versionInfoManager.getByVersion(requestVersion, selfVersion.getVersionNum());
//                        List<VersionInfo> result = new ArrayList<>();
//                        result.add(new VersionInfo());
//                        result.add(new VersionInfo());
                        infoListHeader = new VersionInfoListHeader(result);
                        response = RemoteCommand.createResponseRemoteCommand(RequestCode.VERSION_SYN, ResponseCode.SUCCESSED, SerializeType.JSON, infoListHeader);
                    }
                }
                break;
            case RequestCode.VERSION_REFRESH:
                DefaultHeader defaultHeader = (DefaultHeader<String>) request.decodeConsumerHeader(DefaultHeader.class);
                String defaultRequestId = defaultHeader.getRequestId();
                Long latestVersion = versionInfoManager.getLatestVersionNum();
                String latestAddr = routeInfoManager.getOwnAddr();
                logger.info("请求的requestId："+defaultRequestId + "本地最新的版本是："+latestVersion+" 地址是："+latestAddr);
                VersionRefreshHeader refreshHeader = new VersionRefreshHeader(defaultRequestId, latestVersion, latestAddr);
                response = RemoteCommand.createResponseRemoteCommand(RequestCode.VERSION_REFRESH, ResponseCode.SUCCESSED, SerializeType.JSON, refreshHeader);
                break;
            default:
                break;
        }
        return response;
    }

    @Override
    public boolean rejectRequest() {
        return false;
    }
}
