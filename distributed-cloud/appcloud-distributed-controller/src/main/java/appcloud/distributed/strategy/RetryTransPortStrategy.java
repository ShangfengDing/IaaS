package appcloud.distributed.strategy;

import appcloud.distributed.CloudController;
import appcloud.distributed.Transport.MappedReadFile;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.protocol.RemoteCommand;

/**
 * Created by lizhenhao on 2017/12/16.
 */
@Deprecated
public class RetryTransPortStrategy implements TransPortStrategy {

    private CloudController cloudController;

    private final static int reTryTimes = 3;

    public RetryTransPortStrategy(CloudController cloudController) {
        this.cloudController = cloudController;
    }

    public boolean handlerTransPortException(MappedReadFile mappedFile, String destAddress, byte[] message) {
        int retry = reTryTimes;
        while (retry > 0) {
//            RemoteCommand response = cloudController.clientWapper.sendImageBackRequest(message,destAddress,mappedFile.fileEnd(),mappedFile.getFilePath(),mappedFile.getPrevPosition());
//            if (response.getRemark().equals(ResponseCode.SUCCESSED)) {
//                return true;
//            }
//            else retry--;
        }
        return false;
    }
}
