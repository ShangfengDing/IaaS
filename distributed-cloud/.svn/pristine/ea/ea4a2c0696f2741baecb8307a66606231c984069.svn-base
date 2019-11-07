package appcloud.distributed.process.operate;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.Transport.MappedReadFile;
import appcloud.distributed.Transport.Position;
import appcloud.distributed.common.Constants;
import appcloud.distributed.util.FileUtil;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.protocol.RemoteCommand;
import com.distributed.common.entity.VmBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by lizhenhao on 2018/1/5.
 */
public class TransPortOperateImpl implements TransPortOperate {

    protected final static Logger LOGGER = LoggerFactory.getLogger(TransPortOperateImpl.class);

    private CloudControllerClientWapper clientWapper = CloudControllerClientWapper.getInstance();

    private final static int reTryTimes = 3;

    /**
     * 传输快照
     * @param filePath
     * @param destAddress
     * @param needToClean(是否从头开始传输)
     *
     * 传输方式有断点重传或者重新传输，传输过程中出错，会进行重试，如果重试失败，则将上一次传输偏移量保存到map中
     */
    @Override
    public void transportImageBack(String filePath, String destAddress, boolean needToClean, VmBack vmBack) {
        filePath = filePath.replace(":/", "/");
        LOGGER.info("开始传输镜像文件, filePath: "+filePath+", destAddress: "+destAddress+", needToClean:"+needToClean+", vmBack:"+vmBack.toString());
        // filePath = "D:\\ROOT.war";
        long position;
        if (needToClean) {
            position = 0;
            FileUtil.transportPostion.remove(filePath);
        } else {
            Position prevPosition = FileUtil.transportPostion.get(filePath);
            if (prevPosition != null) {
                position = prevPosition.getPosition();
                destAddress = prevPosition.getDestAddress();
                LOGGER.info("the position:"+position+", destAddress:"+destAddress);
            } else position = 0;
        }
        MappedReadFile mappedReadFile = null;
        try {
            mappedReadFile = MappedReadFile.createMappedFile(Constants.CAPACITY,filePath,position);
            while(mappedReadFile.isReadable()) {
                byte[] bytes = mappedReadFile.read();
                /**
                 * TODO 2018/3/18 不成功时应该更新position
                 */
                LOGGER.info("开始传输文件");
                RemoteCommand response = null;
                boolean judgeEnd = mappedReadFile.fileEnd();//用于传输最后一次的开关判断
                if(!judgeEnd){
                    response = clientWapper.sendImageBackRequest(bytes,destAddress,mappedReadFile.fileEnd(),filePath,mappedReadFile.getPrevPosition(), vmBack);
                } else {
                    response = clientWapper.sendImageBackRequest(bytes,destAddress,mappedReadFile.fileEnd(),filePath,mappedReadFile.getPrevPosition(), vmBack);
                    LOGGER.info("文件传输请求结束");
                    break;
                }
                if(response!=null){
                    if (!response.getRemark().equals(ResponseCode.SUCCESSED)) {
                        if (!handlerTransPortException(mappedReadFile,destAddress,bytes, vmBack)) {
                            LOGGER.error("transport fail,please retry or new start by manual operation");
                            mappedReadFile.savePostion(destAddress);
                            mappedReadFile.destroyMappedFile();
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("file is not exist",e);
        } catch (IOException e) {
            LOGGER.error("read file IO exception",e);
        } finally {
            try {
                mappedReadFile.destroyMappedFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void transportImageBack(String filePath, String destAddress, boolean needToClean, VmBack vmBack, CloudControllerClientWapper clientWapper) {
        filePath = filePath.replace(":/", "");
        long position;
        if (needToClean) {
            position = 0;
            FileUtil.transportPostion.remove(filePath);
        }
        else {
            Position prevPosition = FileUtil.transportPostion.get(filePath);
            if (prevPosition != null) {
                position = prevPosition.getPosition();
                destAddress = prevPosition.getDestAddress();
            }
            else position = 0;
        }
        try {
            MappedReadFile mappedFile = MappedReadFile.createMappedFile(Constants.CAPACITY,filePath,position);
            while(mappedFile.isReadable()) {
                byte[] bytes = mappedFile.read();
                /**
                 * TODO 2018/3/18 不成功时应该更新position
                 */
                RemoteCommand response = clientWapper.sendImageBackRequest(bytes,destAddress,mappedFile.fileEnd(),filePath,mappedFile.getPrevPosition(), vmBack);
                if (!response.getRemark().equals(ResponseCode.SUCCESSED)) {
                    if (!handlerTransPortException(mappedFile,destAddress,bytes, vmBack)) {
                        LOGGER.error("transport fail,please retry or new start by manual operation");
                        mappedFile.savePostion(destAddress);
                        mappedFile.destroyMappedFile();
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("file is not exist",e);
        } catch (IOException e) {
            LOGGER.error("read file IO exception",e);
        }
    }

    @Override
    public boolean handlerTransPortException(MappedReadFile mappedFile, String destAddress, byte[] message, VmBack vmBack) {
        int retry = reTryTimes;
        while (retry > 0) {
            RemoteCommand response = clientWapper.sendImageBackRequest(message,destAddress,mappedFile.fileEnd(),mappedFile.getFilePath(),mappedFile.getPrevPosition(), vmBack);
            if (response.getRemark().equals(ResponseCode.SUCCESSED)) {
                return true;
            }
            else retry--;
        }
        return false;
    }
}
