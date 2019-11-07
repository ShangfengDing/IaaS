package appcloud.distributed.process;

import appcloud.distributed.Transport.MappedWriteFile;
import appcloud.distributed.common.Constants;
import appcloud.distributed.header.DefaultHeader;
import appcloud.distributed.header.TransportFileHeader;
import appcloud.distributed.header.VmBackCheckHeader;
import appcloud.distributed.util.FileUtil;
import appcloud.distributed.util.NumUtil;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.common.SerializeType;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.remote.NettyRequestProcessor;
import com.distributed.common.constant.EnumConstants;
import com.distributed.common.entity.*;
import com.distributed.common.factory.DbFactory;
import com.distributed.common.service.db.VmBackupService;
import com.distributed.common.service.db.VmHostService;
import com.distributed.common.service.db.VmImageBackService;
import com.distributed.common.service.db.VmInstanceInfoService;
import com.distributed.common.utils.CollectionUtil;
import com.distributed.common.utils.ModelUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by lizhenhao on 2017/12/17.
 */
public class TransportRequestProcess implements NettyRequestProcessor {

    protected final static Logger LOGGER = LoggerFactory.getLogger(TransportRequestProcess.class);

    private final VmInstanceInfoService vmInstanceInfoService = DbFactory.getVmInstanceInfoService();
    private final VmBackupService vmBackupService = DbFactory.getVmBackUpService();
    private final VmHostService vmHostService = DbFactory.getVmHostService();
    private final VmImageBackService vmImageBackService = DbFactory.getVmImageBackService();

    @Override
    public RemoteCommand processRequest(ChannelHandlerContext ctx, RemoteCommand request) throws Exception {
        RemoteCommand response = null;
        int requestCode = request.getCode();
        switch (requestCode) {
            case RequestCode.CHECK_BACKUP_DEST:
                request.decodeConsumerHeader(DefaultHeader.class);
                DefaultHeader<InstanceBackInfo> checkHeader = (DefaultHeader<InstanceBackInfo>) request.getConsumHeader();
                InstanceBackInfo instanceBackInfo = checkHeader.getValue();
                String requestId = checkHeader.getRequestId();
                LOGGER.info("the header is: "+checkHeader.toString());

                Boolean addRs = vmInstanceInfoService.add(instanceBackInfo);
                if (addRs) {
                    LOGGER.info("insert the backup info success");

                    // 采取一定的策略选择物理机
                    Host destHost = selectHost();
                    if (ModelUtil.isEmpty(destHost)) {
                        response = RemoteCommand.createReponseRemoteCommand(requestCode, ResponseCode.ERROR);
                        return response;
                    }
                    VmBackCheckHeader checkResponse = new VmBackCheckHeader(requestId, destHost);
                    response = RemoteCommand.createResponseRemoteCommand(requestCode, ResponseCode.SUCCESSED, SerializeType.JSON, checkResponse);
                } else {
                    LOGGER.info("fail to insert the backup info");
                    response = RemoteCommand.createReponseRemoteCommand(requestCode, ResponseCode.ERROR);
                }
                return response;
            /**
             * 接收传输的文件
             */
            case RequestCode.SEND_IMAGE_BACK:
                request.decodeConsumerHeader(TransportFileHeader.class);
                TransportFileHeader transportFileHeader = (TransportFileHeader) request.getConsumHeader();
                VmBack destVmBack = transportFileHeader.getVmBack(); //相关的备份信息，包括host
                if (ModelUtil.isEmpty(destVmBack)) {
                    return RemoteCommand.createErrorRemoteCommand();
                }
                System.out.println(destVmBack);
                String sourceFilePath = transportFileHeader.getFilePath();
                LOGGER.info("镜像源的地址："+sourceFilePath+" vmBack:"+destVmBack);
                LOGGER.info("传输进度："+transportFileHeader.getPosition());
                MappedWriteFile mappedWriteFile = FileUtil.writeOpenFile.get(sourceFilePath);
                if (transportFileHeader.getPosition() == 0) {
                    // TODO: 2017/12/17 需要测试这里，因为如果异常发生，服务端是不会关闭filechannel的
                    if (mappedWriteFile != null) mappedWriteFile.destroyMappedFile();
                    // TODO: 2017/12/25 这里的filepath是相对路径，需要根据策略来选择应该将备份放在哪台物理机上
                    String destFilePath = FileUtil.getDestFilePath(destVmBack.getBackIp(), sourceFilePath);
                    LOGGER.info("初次创建mapperWriteFile, 备份的地址："+destFilePath);
                    String destFolderPath = destFilePath.substring(0, destFilePath.lastIndexOf("/"));
                    File folder = new File(destFolderPath);
                    if (!folder.exists() && !folder.isDirectory()) {
                        folder.mkdirs();
                    }
                    File file = new File(destFilePath);  // 此处应该是目的文件地址
                    if (file.exists()) file.delete();

                    mappedWriteFile = MappedWriteFile.createMappedFile(Constants.CAPACITY, destFilePath);
                    FileUtil.writeOpenFile.put(sourceFilePath, mappedWriteFile);
                }
                mappedWriteFile.write(request.getBody(), transportFileHeader.getPosition());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (transportFileHeader.isTransportEnd()) {
                    FileUtil.writeOpenFile.remove(sourceFilePath);
                    new Thread(new AsyncForce(mappedWriteFile)).start();
                    // 修改数据库。如果vmBack为null，此时说明已经存在，不需要再次存储
                    // TODO 2018/3/18 是否应该修改 VmImageBacking
                    String destUuid = destVmBack.getUuid();
                    LOGGER.info("传输结束，目的数据中心添加数据，destUuid: "+destUuid);
                    if (ModelUtil.isEmpty(vmBackupService.findByUuid(destUuid))) {
                        destVmBack.setType(EnumConstants.DataCenterType.DEST.toString());
                        vmBackupService.add(destVmBack);
                    }
                    VmImageBack vmImageBack = destVmBack.getVmImageBack();
                    if (!ModelUtil.isEmpty(vmImageBack) && vmImageBackService.getByUuid(vmImageBack.getVolumeUuid()) == null) {
                        vmImageBackService.updateTop(vmImageBack.getInstanceUuid());
                        vmImageBack.setId(null);
                        vmImageBackService.save(vmImageBack);
                    }
                }
                response = RemoteCommand.createReponseRemoteCommand(requestCode, ResponseCode.SUCCESSED);
                return response;
            default:
                return null;
        }
    }

    public boolean rejectRequest() {
        return false;
    }


    class AsyncForce implements Runnable {

        private MappedWriteFile mappedWriteFile;

        public AsyncForce(MappedWriteFile mappedWriteFile) {
            this.mappedWriteFile = mappedWriteFile;
        }

        public void run() {
            try {
                mappedWriteFile.force();
                mappedWriteFile.destroyMappedFile();
            } catch (IOException e) {
                LOGGER.error("force mapped file error:filePath is ", mappedWriteFile.getFilePath());
            }
        }
    }

    private Host selectHost() {
        List<VmHost> vmHosts = vmHostService.findByParams(null, EnumConstants.HostType.COMPUTE_NODE.toString(), null, null, null);
        if (CollectionUtil.isEmpty(vmHosts)) {
            LOGGER.error("this datacenter has no vmHost");
            return null;
        }
        Integer size = vmHosts.size();
        Integer index = NumUtil.selectAB(0, size);
        VmHost vmHost = vmHosts.get(index);
        LOGGER.info("the select host: " + vmHost.toString());
        return new Host(vmHost.getUuid(), vmHost.getIp());
    }

}
