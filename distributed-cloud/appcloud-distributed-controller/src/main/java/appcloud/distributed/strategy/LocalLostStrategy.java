package appcloud.distributed.strategy;

import appcloud.distributed.CloudController;
import appcloud.distributed.common.Constants;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.header.ChkConnectionHeader;
import appcloud.distributed.util.Command;
import appcloud.distributed.util.FileUtil;
import appcloud.distributed.util.StringUtil;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.openapi.client.InstanceClient;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.entity.InstanceBackInfo;
import com.distributed.common.entity.VmBack;
import com.distributed.common.entity.VmImageBack;
import com.distributed.common.factory.DbFactory;
import com.distributed.common.service.db.VmBackupService;
import com.distributed.common.service.db.VmImageBackService;
import com.distributed.common.service.db.VmInstanceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zouji on 2017/12/10.
 */

/**
 * 如果是自己失效（云平台正常运作，但是网络断开，无法与其他云平台通信，他需要执行一些程序来保证网络联通后，整个系统正常运作）
 * 1.关闭或者删除带有备份的虚拟机
 * 2.其他云平台备份信息全部移动至垃圾站
 * 3.反复连接某一个节点，获取到master信息后，恢复服务。 新开线程
 */
public class LocalLostStrategy implements LostStrategy {

    private final static Logger LOGGER = LoggerFactory.getLogger(LocalLostStrategy.class);

    private CloudController cloudController;

    private VmBackupService vmBackupService = DbFactory.getVmBackUpService();
    private VmInstanceInfoService vmInstanceInfoService = DbFactory.getVmInstanceInfoService();
    private VmImageBackService vmImageBackService = DbFactory.getVmImageBackService();


    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();

    public LocalLostStrategy(CloudController cloudController) {
        this.cloudController = cloudController;
    }

    public void handlerLostConnection(RouteInfo monitor) {

        RouteInfoManager routeInfoManager = cloudController.routeInfoManager;
        /**
         * 必须clear，因为本地失效后，相当于重新注册。
         */
        List<RouteInfo> allInfo = routeInfoManager.getAllRouteInfo();
        routeInfoManager.clear();
        /**
         * 1.查询所份信息，挑选出本平台备份到其他平台的instanceUuid。关闭之(是否需要删除？最好删除的时候不要将增量镜像删除，这样如果出现其他云平台没有将应该启动的备份云主机启动，等待该云平台网络恢复后，可以通过管理员界面使用开发API恢复虚拟机)
         */
        List<VmBack> vmBacks = vmBackupService.findBySrcDataCenter(routeInfoManager.getOwnRouteInfo().getDataCenter());
        for (VmBack vmBack : vmBacks) {
            String instanceUuid = vmBack.getInstanceUuid();
            InstanceBackInfo instanceBackInfo = vmInstanceInfoService.findByUuid(instanceUuid);
            //TODO 这里只是测试，正式时需要修改代码
            LOGGER.info("开始执行删除虚拟机的操作， uuid:"+instanceUuid+"\r\n"+instanceBackInfo);
            instanceClient.DeleteInstance(Constants.REGION_ID, vmBack.getSourceDataCenter(), instanceUuid,false,instanceBackInfo.getAppkeyId(),instanceBackInfo.getAppkeySecret(),null);
        }

        /**
         * 2.删除其他主机在本地的备份信息
         */
        vmBacks = vmBackupService.findByDestDataCenter(routeInfoManager.getOwnRouteInfo().getDataCenter());
        for (VmBack vmBack : vmBacks) {
            List<VmImageBack> imageBacks = vmImageBackService.getByInstanceUuid(vmBack.getInstanceUuid());
            for (VmImageBack vmImageBack : imageBacks) {
                vmImageBackService.deleteById(vmImageBack.getId());
                String location = FileUtil.parseBackLocation(vmImageBack.getProviderLocation());
                if (location != null) {
                    LOGGER.info("删除备份位置为" + location);
                    Command command = new Command(Command.createDeleteCommand(location));
                    if (!StringUtil.isEmpty(command.getError())) {
                        LOGGER.error(command.getError());
                        command.handlerError();
                    } else LOGGER.info(command.getOutput());
                }
            }
        }

        //3.关闭服务
        cloudController.stop();

        Thread resumeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean interrupt = false;
                while (true) {
                    for (RouteInfo routeInfo : allInfo) {
                        if (!routeInfo.getUuid().equals(routeInfoManager.getOwnRouteInfo().getUuid())) {
                            RemoteCommand response = cloudController.clientWapper.sendChkConnectionRequest(routeInfo.getAddr());
                            if (response != null && response.getRemark().equals(ResponseCode.SUCCESSED)) {
                                response.decodeConsumerHeader(ChkConnectionHeader.class);
                                ChkConnectionHeader chk = (ChkConnectionHeader) response.getConsumHeader();
                                /**
                                 * 只需要更新master，而不需要加入到注册表，注册表是空的没问题，注册后，master节点进行分发的。
                                 */
                                routeInfoManager.updateMaster(chk.getMasterInfo());
                                if (routeInfoManager.getMasterRouteInfo().getUuid().equals(routeInfoManager.getOwnRouteInfo().getUuid())) {
                                    cloudController.isMaster = true;
                                    routeInfoManager.putRouteInfo(routeInfoManager.getOwnRouteInfo());
                                }
                                cloudController.restart();
                                interrupt = true;
                                break;
                            }
                        }
                    }
                    if (interrupt) break;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOGGER.error("Thread sleep exception", e);
                    }
                }
            }
        });
        resumeThread.start();
    }

    /**
     * 判断监控对象是否是master
     * @return
     */
    private boolean isMaster() {
        if (cloudController.routeInfoManager.getOwnAddr().equals(cloudController.routeInfoManager.getMasterAddr())) {
            return true;
        }
        else return false;
    }
}
